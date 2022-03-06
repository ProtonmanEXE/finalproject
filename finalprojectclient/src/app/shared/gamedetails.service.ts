import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { GameCard, GameDetails } from './model';

@Injectable({
  providedIn: 'root'
})
export class GamedetailsService {

  constructor(private http: HttpClient) { }

  getTopTenGames(): Promise<GameCard[]> {
    return lastValueFrom(this.http.get<GameCard[]>("/api/toptengames"))
  }

  getGameDetails(gameId: number): Promise<GameDetails> {
    return lastValueFrom(
      this.http.get<GameDetails>("/api/gamedetails/".concat(gameId.toString())))
  }

  saveToWishlist(gameId: number): Promise<any> {
    return lastValueFrom(
      this.http.get<GameDetails>("/api/savegamedetails/".concat(gameId.toString())))
  }

  testOnly(): Promise<any> {
    console.log("test test")
		// return lastValueFrom(this.http.get<Todo[]>("/api/todos/".concat(userName)))
    return lastValueFrom(this.http.get<any>("http://localhost:8080/hello"))
	}
}
