import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthenticationService } from '../shared/authentication.service';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent implements OnInit {

  private firstObsSub!: Subscription;

  isUserLoggedIn!: boolean;

  constructor(private router: Router,
              private authSvc: AuthenticationService) {
  }

  ngOnInit(): void {
    this.firstObsSub = this.authSvc.customObs.subscribe(boolean => {
      this.isUserLoggedIn = boolean
    })
  }

  findGame() {
    if (this.authSvc.isUserLoggedIn()) {
      this.router.navigate(["/gamedetail"])
    } else console.log("find game print" +this.isUserLoggedIn)
  }

  goToKaboomGames() {
    if (this.authSvc.isUserLoggedIn()) {
      this.router.navigate(["/games"])
    } else console.log("goToKaboomGames" +this.isUserLoggedIn)
  }

  logout() {
    this.authSvc.logOut();
    this.router.navigate(["/"])
  }
}
