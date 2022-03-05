import { GamedetailsService } from './../shared/gamedetails.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from '../shared/authentication.service';
import { UserLogin } from '../shared/model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  userLogin!: FormGroup

  error!: string;

  constructor(private fb: FormBuilder,
              private gameSvc: GamedetailsService,
              private authSvc: AuthenticationService,
              private router: Router) { }

  ngOnInit(): void {
    this.userLogin = this.fb.group({
      userName: [""],
      password: [""]
    });
  }

  login() {
    console.log(this.userLogin)

    const user: UserLogin = this.userLogin.value as UserLogin
    this.authSvc.authenticate(user)
      .then(() => {
        console.info("login success");
        this.router.navigate(["/todos", this.userLogin.get("userName")?.value]);
        this.userLogin.reset();
      }).catch(error => {
        console.info("error >>> " +error);
        this.error = "Invalid username and/or password"
      })
  }

  testOnly() {
    console.log("test only")
    this.gameSvc.testOnly();
  }

}
