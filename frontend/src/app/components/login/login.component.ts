import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from 'src/app/services/auth/auth.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{

  loginFormGroup!: FormGroup;
  submitted: boolean = false;
  errorMessage!: string;

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router, private snackBar: MatSnackBar) {}
  
  ngOnInit(): void {
    this.loginFormGroup = this.fb.group({
      email: ["", [Validators.required, Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]],
      password: ["", Validators.required],
    });
  }

  onSubmit() {
    this.submitted = true;

    if (this.loginFormGroup.invalid) {
      return;
    }

    const loginRequest = {
      email: this.loginFormGroup.get('email')?.value,
      password: this.loginFormGroup.get('password')?.value
    };

    this.authService.login(loginRequest).subscribe(
      (response: any) => {
        this.snackBar.open('Login successful', 'Close', {
          duration: 3000,
        });
        this.router.navigate(['/analyse']);
      },
      (error) => {
        this.snackBar.open('Login failed. Please check your credentials and try again.', 'Close', {
          duration: 3000,
        });
        console.error('Login failed', error);
        this.errorMessage = "An error occurred";
      }
    );
  }
}
