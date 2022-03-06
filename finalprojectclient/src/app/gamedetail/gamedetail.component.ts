import { GamedetailsService } from './../shared/gamedetails.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { GameCard, GameDetails } from './../shared/model';

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
              private gameDetailSvc: GamedetailsService) { }

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

}
