import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom, map } from 'rxjs';
import { ResponseMessage, UserLogin } from './model';

export class User {
  constructor(public status: string) {}
}

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http: HttpClient) { }

  // Provide username and password for authentication, and once authentication is successful,
  // store JWT token in session
  authenticate(user: UserLogin):Promise<void> {
    console.info("user >>> " +user);

    return lastValueFrom(this.http.post<any>("http://localhost:8080/authenticate", user)
      .pipe(map((userData: { jwt: string; }) => {
          sessionStorage.setItem("username", user.userName);
          let tokenStr = "Bearer " + userData.jwt;
          sessionStorage.setItem("token", tokenStr);
          console.info("login success");
        })
      ));
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem("username");
    console.log(!(user === null));
    return !(user === null);
  }

  logOut() {
    sessionStorage.removeItem("username");
    sessionStorage.removeItem("token");
  }}
