import { IRule } from 'declarations';

export const valueType = function (value: string | number | Date, type: 'Decimal' | 'Integer' | 'HalfInt', rule: IRule) {
    let $value = `${value}`;

    if (value !== null && value !== undefined && value !== '') {
        switch (type) {
            case 'Decimal':
                if (!/\d+(\.\d{1,})?/.test($value)) {
                    let min = `${rule.min}`,
                        max = `${rule.max}`;


                    if (rule.mantissaMaxLength) {
                        if (!min.match(/\./) && !max.match(/\./)) {
                            return ['MsgB_11', min, max, `${rule.mantissaMaxLength}`];
                        } else {
                            return ['MsgB_12', `${max.split('.')[0].length}`, `${rule.mantissaMaxLength}`];
                        }
                    } else {
                        return ['MsgB_10', `${rule.min}`, `${rule.mantissaMaxLength}`];
                    }
                }
                break;
            case 'HalfInt':
            case 'Integer':
                if (!/^\d+$/.test($value)) {
                    if (rule.min >= 0) {
                        return ['MsgB_10', `${rule.max}`];
                    } else {
                        return ['MsgB_8', `${rule.min}`, `${rule.max}`];
                    }
                }
                break;
        }
    }

    return null;
};