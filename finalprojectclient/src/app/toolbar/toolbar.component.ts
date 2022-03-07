import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthenticationService } from '../shared/authentication.service';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent implements OnInit {

  @ViewChild("search")
  search!: any; // accessing the reference element

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

  findGames(search: string) {
    if (this.authSvc.isUserLoggedIn()) {
      console.log("form search value >>> " +search)

      if (!search || search.trim().length == 0) {
        this.router.navigate(["/"])
      } else {
        console.log("this is not blank >>> " +search)
        this.router.navigate(["/search/", search])
      }
      this.search.nativeElement.value = "";
    } else alert("You are not logged in.")
  }

  goToKaboomGames() {
    if (this.authSvc.isUserLoggedIn()) {
      this.router.navigate(["/games"])
    } else alert("You are not logged in.")
  }

  goToWishlist() {
    if (this.authSvc.isUserLoggedIn()) {
      this.router.navigate(["/wishlist"])
    } else alert("You are not logged in.")
  }

  logout() {
    this.authSvc.logOut();
    this.router.navigate(["/"])
  }
}
