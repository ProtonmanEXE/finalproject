import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from './shared/authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  title = 'finalprojectclient';

  isUserLoggedIn: boolean;
  activateGuard: boolean;

  constructor(private router: Router,
              private authSvc: AuthenticationService) {
    this.isUserLoggedIn = authSvc.isUserLoggedIn();
    this.activateGuard = false;
  }

  findGame() {
    this.activateGuard = true;
    this.router.navigate(["/gamedetail"])
  }

  goToKaboomGames() {
    this.activateGuard = true;
    console.log("this.activateGuard?" +this.activateGuard)
    this.router.navigate(["/games"])
  }

  logout() {
    this.authSvc.logOut();
    this.isUserLoggedIn = this.authSvc.isUserLoggedIn();
  }

}
