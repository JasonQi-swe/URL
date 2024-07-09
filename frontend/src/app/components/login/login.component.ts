import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginRequest = {
    email: '',
    password: ''
  };

  constructor(private authService: AuthService, private router: Router, private snackBar: MatSnackBar) {}

  onSubmit() {
    this.authService.login(this.loginRequest).subscribe(
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
      }
    );
  }
}
