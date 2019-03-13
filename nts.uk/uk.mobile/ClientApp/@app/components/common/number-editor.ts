import { component, Prop } from '@App/core/component'
import { Vue } from '@app/provider'
import * as _ from 'lodash';

@component({
    template: `
    <span :class="getClass()" :data-content="unitInfo.text">
        <input :class="{ editor: true, error: isError }"
                        :placeholder="placeholder" :name="name"
                        :value="formatted" ref="input" @change="parseValue"
                        :style="unitInfo.style">
        </input>
    </span>
    `
})
export class NumberEditor extends Vue {

    @Prop()
    value: any;

    @Prop()
    separate: any;

    @Prop()
    decimalLength: any;

    @Prop()
    defaultValue: any;

    @Prop()
    placeholder: any;

    @Prop()
    name: any;

    @Prop()
    unit: any;

    @Prop()
    primativeType: any;

    @Prop()
    required: any;

    @Prop()
    min: any;

    @Prop()
    max: any;

    unitInfo: this.getUnit();

    internalValue: this.value;

    get formatted() {
        return this.formatValue(this.internalValue);
    }

    set formatted(value) {
        this.internalValue = value;
        this.$emit("input", value);
    }

    get isError() {
        return this.checkError(this.internalValue);
    }

    checkError(value) {
        /** TODO: advanced check */
        if (!_.isNil(this.required) && this.required === true) {
            if (_.isEmpty(value)) {
                return true;
            }
        }
        value = _.trim(value).replace(/,/g, "");
        if (_.isEmpty(value)) {
            return false;
        }
        if (!/^[-+]?\d+(\.\d*)?$/.test(value)) {
            return true;
        }
        let numberV = _.toNumber(value);
        if (!_.isNil(this.min) && _.toNumber(this.min) > numberV) {
            return true;
        }
        if (!_.isNil(this.max) && _.toNumber(this.max) < numberV) {
            return true;
        }
        return false;
    }

    parseValue(event) {
        let value = _.trim(event.target.value).replace(/,/g, "");
        if (this.checkError(value)) {
            this.formatted = event.target.value;
            return;
        }
        this.formatted = value;
    }

    formatValue(target) {
        if (_.isEmpty(_.trim(target))) {
            return target;
        }
        let value = _.trim(target).replace(/,/g, ""), parts = value.split(".");
        if (parts.length > 2) {
            return target;
        }
        let intPart = parts[0], decimalPart = parts.length > 1 ? parts[1] : "",
            decimalLength = _.toNumber(this.decimalLength),
            intPartCheck = this.isInteger(intPart),
            decimalCheck = this.isInteger(decimalPart);
        if (!intPartCheck || !decimalCheck) {
            return value;
        }
        if (_.isNumber(decimalLength)) {
            decimalPart = decimalPart.substr(0, decimalLength);
            if (decimalPart.length < decimalLength) {
                decimalPart = _.padEnd(decimalPart, decimalLength, '0');
            }
        }
        if (_.toNumber(intPart) < 0) {
            intPart = "-" + _.trimStart(intPart.substr(1), '0');
        } else {
            intPart = _.trimStart(intPart, '0');
        }

        if (!_.isNil(this.separate) && this.separate > 0) {
            intPart = intPart.replace(this.getRegularFormat(this.separate), '$1,')
        }
        return intPart + (_.isEmpty(decimalPart) ? "" : "." + decimalPart);
    }

    getUnit() {
        let padding = this.countHalf(this.unit) * 25;
        if (padding < 20) {
            padding = 20;
        }
        /** TODO: check with unit ID */
        return {
            text: this.unit,
            inLeft: true,
            style: {
                "padding-left": padding + "px",
                "text-align": "left"
            }
        };
    }

    countHalf(text) {
        var count = 0;
        for (var i = 0; i < text.length; i++) {
            var c = text.charCodeAt(i);

            // 0x20 ～ 0x80: 半角記号と半角英数字
            // 0xff61 ～ 0xff9f: 半角カタカナ
            if ((0x20 <= c && c <= 0x7e) || (0xff61 <= c && c <= 0xff9f)) {
                count += 1;
            } else {
                count += 2;
            }
        }
        return count;
    }

    getClass() {
        let className = "nts-number-editor";
        if (!_.isEmpty(this.unitInfo.text)) {
            className += " symbol";
            if (this.unitInfo.inLeft) {
                className += " symbol-left";
            } else {
                className += " symbol-right";
            }
        }
        return className;
    }

    getRegularFormat(length) {
        return new RegExp('(\\d)(?=(\\d{' + length + '})+$)', 'g');
    }

    isInteger(value) {
        return (_.isEmpty(value) || (!_.isEmpty(value) && /^[-+]?\d+$/.test(value)));
    }

}

Vue.component('nts-number-editor', NumberEditor);