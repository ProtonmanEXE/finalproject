import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from '../shared/authentication.service';
import { UserFullDetails } from '../shared/model';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  userSignUp!: FormGroup

  error!: string;

  constructor(private fb: FormBuilder,
              // private gameSvc: GamedetailsService,
              private authSvc: AuthenticationService,
              private router: Router) { }

  ngOnInit(): void {
    this.userSignUp = this.fb.group({
      userName: ["", Validators.required],
      password: ["", Validators.required],
      email: ["", [ Validators.required, Validators.email ]]
    });
  }

  signUp() {
    const user: UserFullDetails = this.userSignUp.value as UserFullDetails
    this.authSvc.saveUserDetails(user)
      .then(() => {
        alert("Thank you " +this.userSignUp.value.userName
          +" for signing up with MegaRaves. Please check your email")
        this.router.navigate(["/"]);
      }).catch(() => {
        alert(this.userSignUp.value.userName +" already exists.")
      });
  }
}
