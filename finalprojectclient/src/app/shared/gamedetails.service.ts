import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GamedetailsService {

  constructor(private http: HttpClient) { }

  testOnly(): Promise<any> {
    console.log("test test")
		// return lastValueFrom(this.http.get<Todo[]>("/api/todos/".concat(userName)))
    return lastValueFrom(this.http.get<any>("http://localhost:8080/hello"))
	}
}
