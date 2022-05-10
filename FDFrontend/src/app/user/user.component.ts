import { HttpErrorResponse, HttpEvent, HttpEventType } from '@angular/common/http';
import { Component,ViewChild, ElementRef, OnDestroy, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import {jsPDF} from 'jspdf';
import { BehaviorSubject, Subscription } from 'rxjs';
import { NotificationType } from '../enum/notification-type.enum';
import { Menu } from '../model/menu';
import { Restaurant } from '../model/restaurant';
import { User } from '../model/user';
import { AuthenticationService } from '../service/authentication.service';
import { NotificationService } from '../service/notification.service';
import { UserService } from '../service/user.service';
@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit, OnDestroy {
  private titleSubject = new BehaviorSubject<string>('Users');
  public titleAction$ = this.titleSubject.asObservable();

  public user: User | undefined;

  public selectCategory: boolean | undefined;
  public categoryName: string | undefined;

  public meniuri : Menu[] | undefined;

  public restaur : Restaurant;
  public restaurants: Restaurant[] | undefined;
  public restaurantsCustomer: Restaurant[] | undefined;
  
  public refreshing: boolean | undefined;
  
  public selectedRestaurant: Restaurant | undefined;
  
  private subscriptions: Subscription[] = [];
  
  public editUser = new User();


  constructor(private router: Router, private authenticationService: AuthenticationService,         
    private userService: UserService, private notificationService: NotificationService) {
      this.restaur = new Restaurant();
    }

  ngOnInit(): void {
    this.user = this.authenticationService.getUserFromLocalCache();
    if(this.isAdmin){
      this.getRestaurants(true);
    }else{
      this.getRestaurantsCustomer(!this.isAdmin);
    }
  }

  public changeTitle(title: string): void {
    this.titleSubject.next(title);
  }

  // users

  public onSelectRestaurant(selectedRestaurant: Restaurant): void {
    this.selectedRestaurant = selectedRestaurant;
    if(this.selectCategory == false){
      this.subscriptions.push(
        this.userService.getMenuByRestaurant(selectedRestaurant.id || 93).subscribe(
          (response: Menu[]) => {
            this.meniuri = response;
          },
          (errorResponse: HttpErrorResponse) => {
            this.sendNotification(NotificationType.ERROR, errorResponse.error.message);
            this.refreshing = false;
          }
        )
      );
      this.clickButton('openRestaurantInfo');
    }else{
      this.subscriptions.push(
        this.userService.getMenuByRestaurantAndCategory(selectedRestaurant.id || 93, this.categoryName || '').subscribe(
          (response: Menu[]) => {
            this.meniuri = response;
          },
          (errorResponse: HttpErrorResponse) => {
            this.sendNotification(NotificationType.ERROR, errorResponse.error.message);
            this.refreshing = false;
          }
        )
      );
      this.clickButton('openRestaurantInfo');
    }
  }

  public onLogOut(): void {
    this.authenticationService.logOut();
    this.router.navigate(['/login']);
    this.sendNotification(NotificationType.SUCCESS, `You've been successfully logged out`);
  }
  
  public getRestaurantsCustomer(showNotification: boolean): void {
    this.refreshing = true;
    this.subscriptions.push(
      this.userService.getRestaurants().subscribe(
        (response: Restaurant[]) => {
          this.userService.addRestaurantsToLocalCache(response);
          this.restaurantsCustomer = response;
          this.refreshing = false;
          if (showNotification) {
            this.sendNotification(NotificationType.SUCCESS, `${response.length} restaurant(s) loaded successfully.`);
          }
        },
        (errorResponse: HttpErrorResponse) => {
          this.sendNotification(NotificationType.ERROR, errorResponse.error.message);
          this.refreshing = false;
        }
      )
    );

  }

  public getMenuCustomer(showNotification: boolean): void {
    this.refreshing = true;
    this.subscriptions.push(
      this.userService.getRestaurants().subscribe(
        (response: Restaurant[]) => {
          this.userService.addRestaurantsToLocalCache(response);
          this.restaurantsCustomer = response;
          this.refreshing = false;
          if (showNotification) {
            this.sendNotification(NotificationType.SUCCESS, `${response.length} restaurant(s) loaded successfully.`);
          }
        },
        (errorResponse: HttpErrorResponse) => {
          this.sendNotification(NotificationType.ERROR, errorResponse.error.message);
          this.refreshing = false;
        }
      )
    );

  }
  public getRestaurants(showNotification: boolean): void {
    this.refreshing = true;
    this.subscriptions.push(
      this.userService.getRestaurantById(this.user?.administrator.id || 1).subscribe(
        (response: Restaurant) => {
          this.userService.addRestaurantToLocalCache(response);
          this.restaur = response;
          this.refreshing = false;
          if (showNotification) {
            this.sendNotification(NotificationType.SUCCESS, `${response.name} restaurant loaded successfully.`);
          }
        },
        (errorResponse: HttpErrorResponse) => {
          this.sendNotification(NotificationType.ERROR, errorResponse.error.message);
          this.refreshing = false;
        }
      )
    );

  }

  public saveNewRestaurant(): void {
    this.clickButton('new-restaurant-save');
  }

  public onAddNewRestaurant(restaurantForm: NgForm): void {
    const formData = this.userService.createTemporaryRestaurantFormData(restaurantForm.value);
    this.subscriptions.push(
      this.userService.addRestaurant(formData,this.user?.administrator.id || 1).subscribe(
        (_response: Restaurant) => {
          this.clickButton('new-user-close');
          this.getRestaurants(false);
          restaurantForm.reset();
          this.sendNotification(NotificationType.SUCCESS, `Added successfully`);
        },
        (errorResponse: HttpErrorResponse) => {
          this.sendNotification(NotificationType.ERROR, errorResponse.error.message);
        }
      )
      );
  }

  public get isAdmin(): boolean {
    return this.getUserRole() === 'Admin';
  }

  private getUserRole(): string {
    return this.authenticationService.getUserFromLocalCache().userType.name || '';
  }

  private sendNotification(notificationType: NotificationType, message: string): void {
    if (message) {
      this.notificationService.notify(notificationType, message);
    } else {
      this.notificationService.notify(notificationType, 'An error occurred. Please try again.');
    }
  }

  private clickButton(buttonId: string): void {
    document.getElementById(buttonId)?.click();
  }

  public searchRestaurants(searchTerm: string): void {
    if(searchTerm == 'all'){
      this.selectCategory = false;
    }else{
      this.selectCategory = true;
      this.categoryName = searchTerm;
    }
  }

  public downloadAsPDF() {
    const doc = new jsPDF();
    const data = document.getElementById('viewRestaurantModal')?.innerText;
    doc.text(data+'', 10, 10);
    doc.save("menu.pdf");
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

}

