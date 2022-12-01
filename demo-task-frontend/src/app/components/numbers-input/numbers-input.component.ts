import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { SortCommandRequestBody } from 'src/app/common/sort-command-request-body';
import { NumbersService } from 'src/app/services/numbers.service';

@Component({
  selector: 'app-numbers-input',
  templateUrl: './numbers-input.component.html',
  styleUrls: ['./numbers-input.component.css']
})
export class NumbersInputComponent implements OnInit {

  numbers: number[] = [];

  constructor(private numbersService: NumbersService) { }

  ngOnInit(): void {
  }

  addNumber(form: NgForm) {

    let currentValue:number = form.controls['number'].value;

    if (currentValue && Number.isInteger(currentValue) && currentValue <= 2147483647 && currentValue >= -2147483648) {
      this.numbers.push(currentValue);
    }

    form.reset();
  }

  sortNumbers(form: NgForm) {
    let order: string = form.controls['btnradio'].value;
    if(order.length == 0) {
      order = "ASC"
    }
    let request = new SortCommandRequestBody(this.numbers, order);
    this.numbersService.sortNumbers(request).subscribe(
      data => {
        this.numbers = data;
      }
    );
  }

  clean() {
      this.numbers = [];
    }

}
