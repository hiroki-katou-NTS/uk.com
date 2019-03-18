import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';

@component({
    template: `<div class="time-picker">
        <div class="time-title text-center">
            {{preview}}
        </div>
        <div class="row">
            <div class="col-6">
                <button tabindex="-1" :disabled="value >= 4320" class="btn btn-link btn-block" v-on:click="upday()">
                    <i class="fas fa-chevron-up"></i>
                </button>
            </div>
            <div class="col-3">
                <button tabindex="-1" class="btn btn-link btn-block" v-on:click="uphour()">
                    <i class="fas fa-chevron-up"></i>
                </button>
            </div>
            <div class="col-3">
                <button tabindex="-1" class="btn btn-link btn-block" v-on:click="upminute()">
                    <i class="fas fa-chevron-up"></i>
                </button>
            </div>
        </div>
        <div class="row">
            <div class="col-6">
                <input type="text" ref="day" class="form-control text-center" v-focus readonly/>
            </div>
            <div class="col-3">
                <input type="text" ref="hour" class="form-control text-center" readonly v-bind:value="hour" />
            </div>
            <div class="col-3">
                <input type="text" ref="minute" class="form-control text-center" readonly v-bind:value="minute" />
            </div>
        </div>
        <div class="row">
            <div class="col-6">
                <button tabindex="-1" :disabled="value < 720" class="btn btn-link btn-block" v-on:click="downday()">
                    <i class="fas fa-chevron-down"></i>
                </button>
            </div>
            <div class="col-3">
                <button tabindex="-1" class="btn btn-link btn-block" v-on:click="downhour()">
                    <i class="fas fa-chevron-down"></i>
                </button>
            </div>
            <div class="col-3">
                <button tabindex="-1" class="btn btn-link btn-block" v-on:click="downminute()">
                    <i class="fas fa-chevron-down"></i>
                </button>
            </div>
        </div>
        <div class="modal-footer">
            <div class="row">
                <div class="col-6 text-left">
                    <button class="btn btn-link" v-on:click="$close(null)">{{ 'delete' | i18n }}</button>
                </div>
                <div class="col-6 text-right">
                    <button class="btn btn-link" v-on:click="close()">{{ 'accept' | i18n }}</button>
                    <button class="btn btn-link" v-on:click="$close()">{{ 'cancel' | i18n }}</button>
                </div>
            </div>
        </div>
    </div>`,
    style: `.time-picker .time-title{ font-size: 1.3rem; }
    .time-picker .fas { font-size: 1.2rem; }
    .time-picker input[readonly] { background-color: #fff; }
    .time-picker .btn { padding-top: 15px; padding-bottom: 15px;}`
})
export class TimeWDPickerComponent extends Vue {
    @Prop({ default: 0 })
    params?: number;

    get value() {
        if (!isNaN(Number(this.params))) {
            return this.params;
        }
        else {
            return 0;
        }
    }

    set value(value: number) {
        this.params = value;
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

    upday() {
        if (!this.valid) {
            this.value = 0;
        }

        this.value += 24 * 60;

        (<HTMLInputElement>this.$refs.day).focus();
    }

    downday() {
        if (!this.valid) {
            this.value = 0;
        }

        this.value -= 24 * 60;

        (<HTMLInputElement>this.$refs.day).focus();
    }

    uphour() {
        if (!this.valid) {
            this.value = 0;
        }

        this.value += 60;

        (<HTMLInputElement>this.$refs.hour).focus();
    }

    downhour() {
        if (!this.valid) {
            this.value = 0;
        }

        this.value -= 60;

        (<HTMLInputElement>this.$refs.hour).focus();
    }

    upminute() {
        if (!this.valid) {
            this.value = 0;
        }

        this.value += 1;

        (<HTMLInputElement>this.$refs.minute).focus();
    }

    downminute() {
        if (!this.valid) {
            this.value = 0;
        }

        this.value -= 1;

        (<HTMLInputElement>this.$refs.minute).focus();
    }

    close() {
        this.$close(this.value);
    }
}