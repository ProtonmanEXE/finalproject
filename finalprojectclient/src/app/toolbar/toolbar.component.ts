import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../shared/authentication.service';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent implements OnInit {

  isUserLoggedIn: boolean;
  activateGuard: boolean;

  constructor(private router: Router,
              private authSvc: AuthenticationService) {
    this.isUserLoggedIn = authSvc.isUserLoggedIn();
    this.activateGuard = false;
  }

  ngOnInit(): void {
  }

  findGame() {
    if (this.authSvc.isUserLoggedIn()) {
      this.router.navigate(["/gamedetail"])
    } else null
  }

  goToKaboomGames() {
    if (this.authSvc.isUserLoggedIn()) {
      this.router.navigate(["/games"])
    } else null
  }

  logout() {
    this.authSvc.logOut();
  }
}
