import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { GameCard, GameDetails, ResponseMessage, GameSummary } from './model';

@Injectable({
  providedIn: 'root'
})
export class GamedetailsService {

  constructor(private http: HttpClient) { }

  getTopTenGames(): Promise<GameCard[]> {
    return lastValueFrom(this.http.get<GameCard[]>("/api/toptengames"))
  }

  getSearchResults(search: string): Promise<GameCard[]> {
    console.log("Before going to sb >>> " +search)
    return lastValueFrom(this.http.get<GameCard[]>(
      "/api/locked/searchbytitle/".concat(search.toString())))
  }

  getGameDetails(gameId: number): Promise<GameDetails> {
    return lastValueFrom(
      this.http.get<GameDetails>(
        "/api/locked/gamedetails/".concat(gameId.toString())))
  }

  saveToWishlist(gameCard: GameCard): Promise<ResponseMessage> {
    return lastValueFrom(
      this.http.post<ResponseMessage>("/api/locked/savegamedetails", gameCard))
  }

  saveToWishlistTwo(gameCard: GameDetails): Promise<ResponseMessage> {
    return lastValueFrom(
      this.http.post<ResponseMessage>("/api/locked/savegamedetails", gameCard))
  }

  getWishList(): Promise<GameSummary[]> {
    return lastValueFrom(this.http.get<GameSummary[]>("/jdbc/locked/wishlist"))
  }

  deleteGameWish(gameId: number): Promise<any> {
    return lastValueFrom(
      this.http.get<any>(
        "/jdbc/locked/deletewish/".concat(gameId.toString())))
  }
}
