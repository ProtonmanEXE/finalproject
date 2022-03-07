import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom, map, Observable, Observer } from 'rxjs';
import { ResponseMessage, UserLogin } from './model';

export class User {
  constructor(public status: string) {}
}

@Injectable({
  providedIn: 'root'
})

export class AuthenticationService {

  customObs: Observable<boolean>;

  constructor(private http: HttpClient) {
    this.customObs = new Observable((observer: Observer<any>) => {
      setInterval(() => {
        observer.next(this.isUserLoggedIn());
      }, 1000)
    });
  }

  // Provide username and password for authentication, and once authentication is successful,
  // store JWT token in session
  authenticate(user: UserLogin):Promise<void> {
    console.info("user >>> " +user);

    return lastValueFrom(this.http.post<any>("/authenticate", user)
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
    return !(user === null);
  }

  logOut() {
    sessionStorage.removeItem("username");
    sessionStorage.removeItem("token");
  }
}
