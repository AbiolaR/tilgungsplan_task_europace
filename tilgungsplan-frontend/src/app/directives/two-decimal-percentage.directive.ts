import { Directive, ElementRef, HostListener } from "@angular/core";

@Directive({
    selector: '[twoDecimalPercentage]'
})
export class TwoDecimalPercentageDirective {
    private regex: RegExp = new RegExp(/^\d*\.?\d{0,2}$/g);
    private specialKeys: Array<string> = ['ArrowLeft', 'ArrowRight', 'Backspace', 'Tab', 'Home', 'End', 'Delete', 'Del'];

    constructor(private element: ElementRef) {}

    @HostListener('input', ['$event'])
    onKeyDown(event: KeyboardEvent) {
        if (this.specialKeys.includes(event.key)) return;

        

        if (!this.element.nativeElement.value.match(this.regex)
        || parseFloat(this.element.nativeElement.value) > 99) {
            event.preventDefault();
            const position = this.element.nativeElement.selectionStart;
            const value = this.element.nativeElement.value;
            this.element.nativeElement.value = value.slice(0, position - 1) + value.slice(position);
        }
    }

}