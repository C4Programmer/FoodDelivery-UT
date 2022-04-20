import { animate } from '@angular/animations';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { DeliveryZone } from '../model/deliveryZone';
import { Menu } from '../model/menu';
import { Restaurant } from '../model/restaurant';
import { TemporaryRestaurant } from '../model/temporaryRestaurant';
import { User } from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private host = environment.apiUrl;

  constructor(private http: HttpClient) { }

  public getRestaurantById(id: number): Observable<Restaurant>{
    return this.http.get<Restaurant>(`${this.host}/restaurants/${id}`);
  }

  public getRestaurants(): Observable<Restaurant[]>{
    return this.http.get<Restaurant[]>(`${this.host}/restaurants`)
  }

  public getMenuByRestaurant(id: number): Observable<Menu[]>{
    return this.http.get<Menu[]>(`${this.host}/menus/${id}`);
  }

  public getMenuByRestaurantAndCategory(id: number, categoryName: string): Observable<Menu[]>{
    return this.http.get<Menu[]>(`${this.host}/menus/${id}/${categoryName}`)
  }

  public addRestaurant(formData: FormData, administratorId: number): Observable<Restaurant>{
    var restaurant = new Restaurant();
    restaurant.administrator.id = administratorId;
    restaurant.name =  formData.get('name')?.toString() || '';
    restaurant.location = formData.get('location')?.toString() || '';
    var menu1 = new Menu();
    var menu2 = new Menu();
    var menu3 = new Menu();

    menu1.name = formData.get('menu1Name')?.toString() || '';
    menu1.description = formData.get('menu1Description')?.toString() || '';
    menu1.category.id = +(formData.get('menu1Category')?.toString() || '1');
    menu1.price = +(formData.get('menu1Price')?.toString() || '1');

    menu2.name = formData.get('menu2Name')?.toString() || '';
    menu2.description = formData.get('menu2Description')?.toString() || '';
    menu2.category.id = +(formData.get('menu2Category')?.toString() || '1');
    menu2.price = +(formData.get('menu2Price')?.toString() || '1');

    menu3.name = formData.get('menu3Name')?.toString() || '';
    menu3.description = formData.get('menu3Description')?.toString() || '';
    menu3.category.id = +(formData.get('menu3Category')?.toString() || '1');
    menu3.price = +(formData.get('menu3Price')?.toString() || '1');
    

    restaurant.menus?.push(menu1);
    restaurant.menus?.push(menu2);
    restaurant.menus?.push(menu3);

    var deliveryZone1 = new DeliveryZone();
    var deliveryZone2 = new DeliveryZone();
    var deliveryZone3 = new DeliveryZone();

    deliveryZone1.name = formData.get('deliveryZone1Name')?.toString() || '';
    deliveryZone1.location = formData.get('deliveryZone1Location')?.toString() || '';

    deliveryZone2.name = formData.get('deliveryZone2Name')?.toString() || '';
    deliveryZone2.location = formData.get('deliveryZone2Location')?.toString() || '';

    deliveryZone3.name = formData.get('deliveryZone3Name')?.toString() || '';
    deliveryZone3.location = formData.get('deliveryZone3Location')?.toString() || '';

    console.log(restaurant);

    restaurant.deliveryZones?.push(deliveryZone1);
    restaurant.deliveryZones?.push(deliveryZone2);
    restaurant.deliveryZones?.push(deliveryZone3);

    return this.http.post<Restaurant>(`${this.host}/restaurants`,restaurant);
  }

  public addRestaurantsToLocalCache(restaurants: Restaurant[]): void{
    localStorage.setItem('restaurants', JSON.stringify(restaurants));
  }

  public addRestaurantToLocalCache(restaurants: Restaurant): void{
    localStorage.setItem('restaurants', JSON.stringify(restaurants));
  }

  public getRestaurantsFromLocalCache(): Restaurant[] {
    if(localStorage.getItem('restaurants')){
      return JSON.parse(localStorage.getItem('restaurants') || '');
    }
    return null as any;
  }


  public createUserFormData(loggedInUsername: string, user: User): FormData {
    const formData = new FormData();
    formData.append('currentUserName', loggedInUsername);
    formData.append('firstName', user.firstName || '');
    formData.append('password',user.password || '');
    formData.append('lastName', user.lastName || '');
    formData.append('username', user.username || '');
    formData.append('email', user.email || '');
    formData.append('phone',user.phone || '');
    if(user.userTypeId != null){
      formData.append('userTypeId', user.userTypeId.toString() );
    }
    return formData;
  } 

  public createTemporaryRestaurantFormData(restaurant: TemporaryRestaurant): FormData{
    const formData = new FormData();
    formData.append('name',restaurant.name || ''); 
    formData.append('location',restaurant.location || ''); 
    if(restaurant.administratorId != null){
      formData.append('administratorId',restaurant.administratorId.toString());
    } 
    formData.append('deliveryZone1Name',restaurant.deliveryZone1Name || ''); 
    formData.append('deliveryZone1Location',restaurant.deliveryZone1Location || ''); 
    formData.append('deliveryZone2Name',restaurant.deliveryZone2Name || ''); 
    formData.append('deliveryZone2Location',restaurant.deliveryZone2Location || ''); 
    formData.append('deliveryZone3Name',restaurant.deliveryZone3Name || ''); 
    formData.append('deliveryZone3Location',restaurant.deliveryZone3Location || ''); 

    formData.append('menu1Name',restaurant.menu1Name || ''); 
    formData.append('menu1Description',restaurant.menu1Description || ''); 
    if(restaurant.menu1Price != null)
      formData.append('menu1Price',restaurant.menu1Price.toString()); 
    if(restaurant.menu1Category != null)
    formData.append('menu1Category',restaurant.menu1Category.toString()); 

    formData.append('menu2Name',restaurant.menu2Name || ''); 
    formData.append('menu2Description',restaurant.menu2Description || ''); 
    if(restaurant.menu2Price != null)
      formData.append('menu2Price',restaurant.menu2Price.toString()); 
    if(restaurant.menu2Category != null)
    formData.append('menu2Category',restaurant.menu2Category.toString()); 

    formData.append('menu3Name',restaurant.menu3Name || ''); 
    formData.append('menu3Description',restaurant.menu3Description || ''); 
    if(restaurant.menu3Price != null)
      formData.append('menu3Price',restaurant.menu3Price.toString()); 
    if(restaurant.menu3Category != null)
    formData.append('menu3Category',restaurant.menu3Category.toString()); 

    return formData;
  }
}
