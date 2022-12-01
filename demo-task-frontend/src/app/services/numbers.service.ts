import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { SortCommandRequestBody } from '../common/sort-command-request-body';

@Injectable({
  providedIn: 'root'
})
export class NumbersService {

  private baseUrl = 'http://localhost:8080/numbers/sort-command';

  constructor(private httpClient: HttpClient)  { }

  sortNumbers(request: SortCommandRequestBody): Observable<number[]> {

    return this.httpClient.post<GetResponse>(this.baseUrl, request).pipe(
      map(response => response.numbers)
    );
  }
}

interface GetResponse {
  numbers: number[];
}

