import { Router } from '@angular/router';
import { GameSummary } from './../shared/model';
import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../shared/authentication.service';
import { GamedetailsService } from '../shared/gamedetails.service';

@Component({
  selector: 'app-wishlist',
  templateUrl: './wishlist.component.html',
  styleUrls: ['./wishlist.component.css']
})
export class WishlistComponent implements OnInit {

  wishlist!: GameSummary[];

  userName!: string | null

  constructor(private gameDetailSvc: GamedetailsService,
              private authSvc: AuthenticationService,
              private router: Router) { }

  ngOnInit(): void {
    this.gameDetailSvc.getWishList()
      .then(list => {
        this.wishlist = list
      }).catch(() => {
        console.info("Wishlist not found")
      });

    this.userName = this.getUserName();
  }

  getUserName() {
    console.log(sessionStorage.getItem("username"))
    return sessionStorage.getItem("username")
  }

  deleteGameWish(gameId: number) {
    if (this.authSvc.isUserLoggedIn()) {
      this.gameDetailSvc.deleteGameWish(gameId)
      .then(msg => {
        console.log("return msg >>> " +msg)
        this.gameDetailSvc.getWishList()
          .then(list => {this.wishlist = list})
      })
      .catch(msg => {
        console.info("Wish not deleted")
      });
    } else alert("Please log in.")
  }

}
