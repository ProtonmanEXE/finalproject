import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-games',
  templateUrl: './games.component.html',
  styleUrls: ['./games.component.css']
})
export class GamesComponent implements OnInit {

  games: string[] = ["Creature of Havoc", "Warlock of Firetop Mountain",
    "House of Hell", "Dungeons and Dragons"];

  userName!: string | null

  constructor() { }

  ngOnInit(): void {
    this.userName = this.getUserName();
  }

  getUserName() {
    return sessionStorage.getItem("username")
  }

}
