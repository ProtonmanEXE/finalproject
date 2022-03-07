import { HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class HtppInterceptorService implements HttpInterceptor {

  constructor() { }

  intercept(req: HttpRequest<any>, next: HttpHandler) {

    const jwt = sessionStorage.getItem("token");

    if (sessionStorage.getItem("username") && sessionStorage.getItem("token")) {
      const cloned = req.clone({
        headers: req.headers.set("Authorization", jwt!)
      })

      return next.handle(cloned);
    } else {
      return next.handle(req);
    }

  }
}
