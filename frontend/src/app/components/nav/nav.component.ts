import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth/auth.service';


@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit {
  isAuthenticated = false;

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    this.authService.getAuthState().subscribe(authState => {
      this.isAuthenticated = authState;
    });
  }
}
