import { SearchComponent } from './search/search.component';
import { WishlistComponent } from './wishlist/wishlist.component';
import { SignupComponent } from './signup/signup.component';
import { GamedetailComponent } from './gamedetail/gamedetail.component';
import { GamesComponent } from './games/games.component';
import { HomeComponent } from './home/home.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';

const routes: Routes = [
  { path: "", component: HomeComponent },
  { path: "login", component: LoginComponent },
  { path: "signup", component: SignupComponent },
  { path: "games", component: GamesComponent },
  { path: "wishlist", component: WishlistComponent },
  { path: "gamedetail/:gameId", component: GamedetailComponent },
  { path: "search/:title", component: SearchComponent },
  { path: "**", redirectTo: "", pathMatch: "full" }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: "reload"})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
