import { input, InputComponent } from "@app/components/common/inputs/input";
import { Prop, Emit } from '@app/core/component';
import { Vue } from '@app/provider';
import { $ } from "@app/utils";

@input()
class DateComponent extends InputComponent {

    type: string = 'date';

    editable: boolean = true;

    mounted() {
        this.icons.after = 'far fa-calendar-alt';
    }

    get rawValue() {
       if ($.isNil(this.value)) {
           return '';
       }
       return this.computeRawValue(this.value);
    }

    private computeRawValue(value: Date): string {
        var year: number = value.getFullYear();
        var month: number = value.getMonth() + 1;
        var date: number = value.getDate();
        var monthText = month < 10 ? '0' + month : month;
        var dateText = date < 10 ? '0' + date : date;
        return year + '-' + monthText + '-' + dateText;
    }

    input() {
        let value = (<HTMLInputElement>this.$refs.input).value;

        if (value) {
            let numb = new Date(value);

            if(numb.getFullYear() < 1000) {
                return;
            }

            if(isNaN(numb.getTime())){
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