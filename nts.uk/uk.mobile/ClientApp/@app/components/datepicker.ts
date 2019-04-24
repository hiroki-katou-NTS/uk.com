import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';

@component({
    template: `<div class="date-picker">
        <div class="date-title text-center">
            {{preview}}
        </div>
        <div class="row">
            <div class="col-4">
                <button tabindex="-1" :disabled="value >= 4320" class="btn btn-link btn-block" v-on:click="upday()">
                    <i class="fas fa-chevron-up"></i>
                </button>
            </div>
            <div class="col-4">
                <button tabindex="-1" class="btn btn-link btn-block" v-on:click="uphour()">
                    <i class="fas fa-chevron-up"></i>
                </button>
            </div>
            <div class="col-4">
                <button tabindex="-1" class="btn btn-link btn-block" v-on:click="upminute()">
                    <i class="fas fa-chevron-up"></i>
                </button>
            </div>
        </div>
        <div class="row">
            <div class="col-4">
                <input type="text" ref="day" class="form-control text-center" v-focus readonly/>
            </div>
            <div class="col-4">
                <input type="text" ref="hour" class="form-control text-center" readonly v-bind:value="hour" />
            </div>
            <div class="col-4">
                <input type="text" ref="minute" class="form-control text-center" readonly v-bind:value="minute" />
            </div>
        </div>
        <div class="row">
            <div class="col-4">
                <button tabindex="-1" :disabled="value < 720" class="btn btn-link btn-block" v-on:click="downday()">
                    <i class="fas fa-chevron-down"></i>
                </button>
            </div>
            <div class="col-4">
                <button tabindex="-1" class="btn btn-link btn-block" v-on:click="downhour()">
                    <i class="fas fa-chevron-down"></i>
                </button>
            </div>
            <div class="col-4">
                <button tabindex="-1" class="btn btn-link btn-block" v-on:click="downminute()">
                    <i class="fas fa-chevron-down"></i>
                </button>
            </div>
        </div>
        <div class="modal-footer">
            <div class="row">
                <div class="col-6 text-left">
                    <button class="btn btn-link" v-if="delete1" v-on:click="$close(null)">{{ 'delete1' | i18n }}</button>
                </div>
                <div class="col-6 text-right">
                    <button class="btn btn-link" v-on:click="close()">{{ 'accept' | i18n }}</button>
                    <button class="btn btn-link" v-on:click="$close()">{{ 'cancel' | i18n }}</button>
                </div>
            </div>
        </div>
    </div>`,
    style: `.date-picker .date-title{ font-size: 1.3rem; }
    .date-picker .fas { font-size: 1.2rem; }
    .date-picker input[readonly] { background-color: #fff; }
    .date-picker .btn { padding-top: 15px; padding-bottom: 15px;}`
})
export class DatePickerComponent extends Vue {
    @Prop({
        default: {
            value: 0,
            required: true
        }
    })
    public readonly params: {
        value: number | undefined;
        required?: boolean;
    };

    get delete1() {
        return !this.params.required;
    }

    get value() {
        if (!isNaN(Number(this.params.value))) {
            return this.params.value;
        } else {
            return 0;
        }
    }

    set value(value: number) {
        this.params.value = value;
    }

    get valid() {
        return this.value != undefined;
    }

    get preview() {
        return this.value;
    }

    get day() {
        return this.value;
    }

    get hour() {
        return this.value;
    }

    get minute() {
        return this.value;
    }

    public upday() {
        if (!this.valid) {
            this.value = 0;
        }

        this.value += 24 * 60;

        ( this.$refs.day as HTMLInputElement).focus();
    }

    public downday() {
        if (!this.valid) {
            this.value = 0;
        }

        this.value -= 24 * 60;

        ( this.$refs.day as HTMLInputElement).focus();
    }

    public uphour() {
        if (!this.valid) {
            this.value = 0;
        }

        this.value += 60;

        ( this.$refs.hour as HTMLInputElement).focus();
    }

    public downhour() {
        if (!this.valid) {
            this.value = 0;
        }

        this.value -= 60;

        ( this.$refs.hour as HTMLInputElement).focus();
    }

    public upminute() {
        if (!this.valid) {
            this.value = 0;
        }

        this.value += 1;

        ( this.$refs.minute as HTMLInputElement).focus();
    }

    public downminute() {
        if (!this.valid) {
            this.value = 0;
        }

        this.value -= 1;

        ( this.$refs.minute as HTMLInputElement).focus();
    }

    public close() {
        this.$close(this.value);
    }
}