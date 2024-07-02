import { Directive, ElementRef, HostListener } from "@angular/core";

@Directive({
    selector: '[minZeroNumber]'
})
export class MinZeroNumberDirective {
    private specialKeys: Array<string> = ['ArrowLeft', 'ArrowRight', 'Backspace', 'Tab', 'Home', 'End', 'Delete', 'Del'];

    constructor(private element: ElementRef) {}

    @HostListener('input', ['$event'])
    onKeyDown(event: KeyboardEvent) {
        if (this.specialKeys.includes(event.key)) return;

        const intValue = parseInt(this.element.nativeElement.value);
        if (intValue < 1 || intValue % 1 !== 0) {
            event.preventDefault();
            const position = this.element.nativeElement.selectionStart;
            const value = this.element.nativeElement.value;
            this.element.nativeElement.value = value.slice(0, position - 1) + value.slice(position);
        }
    }

}