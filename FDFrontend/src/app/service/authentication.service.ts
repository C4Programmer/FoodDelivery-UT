import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { HttpErrorResponse } from '@angular/common/http';
import { User } from '../model/user';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  public host = environment.apiUrl;
  private token: string | null | undefined;
  private loggedInUsername: string | null | undefined;
  private jwtHelper = new JwtHelperService();

  constructor(private http: HttpClient) { 
  }

  public login(user: User): Observable<HttpResponse<User>> {
    return this.http.post<User>(`${this.host}/users/login`, user, {observe: 'response'});
  }

  public register(formData: FormData): Observable<User>{
    var user = new User();
    user.username = formData.get('username')?.toString() || '';
    user.password = formData.get('password')?.toString() || '';
    user.firstName = formData.get('firstName')?.toString() || '';
    user.lastName = formData.get('lastName')?.toString() || '';
    user.email = formData.get('email')?.toString() || '';
    user.phone = formData.get('phone')?.toString() || '';
    user.userType.id = +(formData.get('userTypeId')?.toString() || '1');
    console.log(user);
    return this.http.post<User>(`${this.host}/users/register`,user);
  }

  public logOut(): void {
    this.token = null;
    this.loggedInUsername = null;
    localStorage.removeItem('user');
    localStorage.removeItem('token');
  }

  public saveToken(token: string): void{
    this.token = token;
    localStorage.setItem('token',token);
  }

  public addUserToLocalCache(user: User): void{
    localStorage.setItem('user',JSON.stringify(user));
  }

  public getUserFromLocalCache(): User{
    return JSON.parse(localStorage.getItem('user') || '{}');
  }

  public loadToken(): void{
    this.token = localStorage.getItem('token');
  }

  public getToken(): string {
    return this.token || "";
  }

  public isUserLoggedIn(): boolean{
    this.loadToken();
    if(this.token != null && this.token !== ''){
      if(this.jwtHelper.decodeToken(this.token).sub != null || ''){
        if(!this.jwtHelper.isTokenExpired(this.token)){
          this.loggedInUsername = this.jwtHelper.decodeToken(this.token).sub;
          return true;
        }
      }
    }else{
      this.logOut();
      return false;
    }
    return false;
  }

}
