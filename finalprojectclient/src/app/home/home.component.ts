import { Router } from '@angular/router';
import { GameCard } from './../shared/model';
import { GamedetailsService } from './../shared/gamedetails.service';
import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { AuthenticationService } from '../shared/authentication.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  topTenGames!: GameCard[]

  firstObsSub!: Subscription;

  userName!: string | null
  isUserLoggedIn!: boolean

  constructor(private gameDetailSvc: GamedetailsService,
              private authSvc: AuthenticationService,
              private router: Router) { }

  ngOnInit(): void {
    this.gameDetailSvc.getTopTenGames()
      .then(topten => {
        this.topTenGames = topten
        console.info("top ten = ", this.topTenGames )
      }).catch(() => {
        console.info("Top ten games not found")
      });

    this.firstObsSub = this.authSvc.customObs.subscribe(boolean => {
      this.isUserLoggedIn = boolean
    })

    this.userName = this.getUserName();
  }

  getUserName() {
    console.log(sessionStorage.getItem("username"))
    return sessionStorage.getItem("username")
  }

  saveToWishlist(gameId: number) {
    if (this.authSvc.isUserLoggedIn()) {
      this.gameDetailSvc.saveToWishlist(gameId)
      this.router.navigate(["/wishlist"])
    } else console.log("find game print" +this.isUserLoggedIn)
  }
}
