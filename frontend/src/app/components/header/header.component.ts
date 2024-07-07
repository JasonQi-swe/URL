import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isAuthenticated = false;

  constructor(private authService: AuthService, private router: Router, private snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.authService.getAuthState().subscribe(authState => {
      this.isAuthenticated = authState;
    });
  }

  logout(): void {
    this.authService.logout();
    this.snackBar.open('Logout successful', 'Close', {
      duration: 3000,
    });
    this.router.navigate(['/']);
  }
}
