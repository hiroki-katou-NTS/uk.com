import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';

@component({
    template: `
        <div class="time-picker">

            

            <div class="row">
                <div class="col-6">
                    <select class="form-control" :value="hourValue" ref="hour1" @input="whenHourChange">
                        <option v-for="hour in hourList">{{hour}}</option>
                    </select>
                </div>
                    
                <div class="col-6">
                    <select class="form-control" :value="minuteValue" ref="minute1" @input="whenMinuteChange">
                        <option v-for="minute in minuteList">{{minute}}</option>
                    </select>
                </div>
            </div>

            <div class="modal-footer">
                <div class="row">
                    <div class="text-left col-6">
                        <button class="btn btn-link" @click="cancel()">Cancel</button>
                    </div>

                    <div class="text-right col-6">
                        <button class="btn btn-link" @click="ok()">Ok</button>
                    </div>
                </div>
            </div>
        </div>    
    `
})
export class TimePickerComponent extends Vue {

    @Prop({
        default: {
            value: 0,
            minValue: 0,
            maxValue: 1349
        }
    })
    readonly params: {
        minValue: number;
        maxValue: number;
        value: number;
    };

    minHour = this.computeHour(this.params.minValue);
    maxHour = this.computeHour(this.params.maxValue);
    
    minMinute = this.computeMinute(this.params.minValue);
    maxMinute = this.computeMinute(this.params.maxValue);

    get hourList(): Array<number> {
        return this.generateArray(this.minHour, this.maxHour);
    }

    get minuteList(): Array<number> {
        var minMinute = 0;
        var maxMinute = 59;

        if(this.hourValue == this.minHour) {
            
            if(this.minHour >= 0 ) {
                minMinute = this.minMinute;
            } else {
                maxMinute = this.minMinute;
            }
            

            if(this.minuteValue < minMinute) {
                this.minuteValue = minMinute;
            }
        }

        if(this.hourValue == this.maxHour) {
            maxMinute = this.maxMinute;

            if (this.minuteValue > maxMinute) {
                this.minuteValue = maxMinute;
            }
        }
        return this.generateArray(minMinute, maxMinute);       
    }

    computeHour(value: number): number {
        if (value >= 0) {
            return Math.floor(value / 60)
        } else {
            return 0 - Math.floor(Math.abs(value) / 60);
        }
    }

    computeMinute(value: number): number {
        if (value >= 0) {
            var hour = Math.floor(value / 60)
            return value - hour * 60;
        } else {
            var hour = 0 - Math.floor(Math.abs(value) / 60);
            return Math.abs(value) + hour * 60;
        }
    }

    generateArray(min: number, max: number): Array<number> {
        var minuteList = new Array<number>();
        for (var m = min; m<= max; m++) {
            minuteList.push(m);
        }
        return minuteList;
    }

    minutes: number= this.params.value;

    

    ok() {
        this.$close(this.minutes);
    }

    cancel() {
        this.$close();
    }

    get hourValue(): number {
        return this.computeHour(this.minutes);
    }

    set hourValue(newHour: number) {
        this.updateValue(newHour, this.minuteValue);
    }

    get minuteValue() : number {
        return this.computeMinute(this.minutes);
    }

    set minuteValue(newMinute: number) {
        this.updateValue(this.hourValue, newMinute);
    } 

    whenHourChange() {
        let newHour = (<HTMLInputElement>this.$refs.hour1).value;
        this.hourValue = Number(newHour);
    }

    whenMinuteChange() {
        let newMinute = (<HTMLInputElement>this.$refs.minute1).value;
        this.minuteValue = Number(newMinute);
    }

    updateValue(newHour: number, newMinute: number) {
        if (newHour >= 0 ) {
            this.minutes = newHour * 60 + newMinute;
        } else {
            this.minutes = newHour * 60 - newMinute;
        }
    }
}