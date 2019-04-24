import { $ } from '@app/utils';
import { Vue } from '@app/provider';
import { input, InputComponent } from './input';

@input()
class DateComponent extends InputComponent {
    public type: string = 'date';

    public editable: boolean = true;

    public mounted() {
        this.icons.after = 'far fa-calendar-alt';
    }

    get rawValue() {
       if ($.isNil(this.value)) {
           return '';
       }
       return this.computeRawValue(this.value);
    }

    private computeRawValue(value: Date): string {
        let year: number = value.getFullYear();
        let month: number = value.getMonth() + 1;
        let date: number = value.getDate();
        let monthText = month < 10 ? '0' + month : month;
        let dateText = date < 10 ? '0' + date : date;
        return year + '-' + monthText + '-' + dateText;
    }

    public input() {
        let value = ( this.$refs.input as HTMLInputElement).value;

        if (value) {
            let numb = new Date(value);

            if (numb.getFullYear() < 1000) {
                return;
            }

            if (isNaN(numb.getTime())) {
                return;
            }

            this.$emit('input', numb);
        }

    }

    // click() {
    //     this.$modal('datepicker', {
    //         value: this.value
    //     }, {
    //             type: "popup",
    //             title: this.name,
    //             animate: {
    //                 show: 'zoomIn',
    //                 hide: 'zoomOut'
    //             }
    //         }).onClose(v => {
    //             if (v !== undefined) {
    //                 this.$emit('input', v);
    //             }
    //         });
    // }
}

Vue.component('nts-date-input', DateComponent);