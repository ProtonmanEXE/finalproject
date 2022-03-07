import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthenticationService } from '../shared/authentication.service';
import { GamedetailsService } from '../shared/gamedetails.service';
import { GameCard } from '../shared/model';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  gameSearchResultsbyTitle!: GameCard[]

  userName!: string | null
  search!: string;
  isUserLoggedIn!: boolean

  constructor(private gameDetailSvc: GamedetailsService,
              private authSvc: AuthenticationService,
              private activatedRoute: ActivatedRoute,
              private router: Router) { }

  ngOnInit(): void {
    this.search = this.activatedRoute.snapshot.params["title"];
    this.gameDetailSvc.getSearchResults(this.search)
      .then(searchResults => {
        this.gameSearchResultsbyTitle = searchResults
      }).catch(() => {
        console.info("Games not found")
      });

    this.userName = this.getUserName();

    this.isUserLoggedIn = this.authSvc.isUserLoggedIn()
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
