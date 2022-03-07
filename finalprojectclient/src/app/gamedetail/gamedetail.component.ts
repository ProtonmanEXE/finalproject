import { GamedetailsService } from './../shared/gamedetails.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { GameDetails } from './../shared/model';
import { AuthenticationService } from '../shared/authentication.service';

@Component({
  selector: 'app-gamedetail',
  templateUrl: './gamedetail.component.html',
  styleUrls: ['./gamedetail.component.css']
})
export class GamedetailComponent implements OnInit {

  htmlString!: String;

  game!: GameDetails

  gameId!: number

  constructor(private activatedRoute: ActivatedRoute,
              private gameDetailSvc: GamedetailsService,
              private authSvc: AuthenticationService) { }

  ngOnInit(): void {
    this.gameId = this.activatedRoute.snapshot.params["gameId"];
    console.log("id >>> " +this.gameId)

    this.gameDetailSvc.getGameDetails(this.gameId)
      .then(game => {
        this.game = game
        this.htmlString = this.game.description
      }).catch(() => {
        console.log("Game not found")
      })
  }

  saveToWishlist(game: GameDetails) {
    if (this.authSvc.isUserLoggedIn()) {
      this.gameDetailSvc.saveToWishlistTwo(game)
      .then(() => alert(
        game.name +" added to wishlist."))
      .catch(() => alert(
        "Failed to save to wishlist or game may already be inside wishlist."))
    } else alert("Please log in.")
  }

}
