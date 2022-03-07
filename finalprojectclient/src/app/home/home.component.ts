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
      }).catch(() => {
        console.info("Top nine games not found")
      });

    this.firstObsSub = this.authSvc.customObs.subscribe(boolean => {
      this.isUserLoggedIn = boolean
    })

    this.userName = this.getUserName();
  }

  getUserName() {
    return sessionStorage.getItem("username")
  }

  goToGameDetail(gameId: number) {
    if (this.authSvc.isUserLoggedIn()) {
      this.router.navigate(["/gamedetail/", gameId])
    } else {
      alert("Please log in.")
      this.router.navigate(["/login"])
    }
  }

  saveToWishlist(game: GameCard) {
    if (this.authSvc.isUserLoggedIn()) {
      this.gameDetailSvc.saveToWishlist(game)
      .then(() => alert(
        game.name +" added to wishlist."))
      .catch(() => alert(
        "Failed to save to wishlist or game may already be inside wishlist."))
    } else alert("Please log in.")
  }
}
