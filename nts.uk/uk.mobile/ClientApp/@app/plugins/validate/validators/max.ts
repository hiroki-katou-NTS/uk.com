import { IRule } from 'declarations';
import { constraint } from '@app/utils';
import { TimeWithDay, TimeDuration, TimePoint } from '@app/utils/time';

const max = function (value: number | Date, max: number | Date, rule: IRule) {
    let lgt: boolean = value > max,
        constr: string = constraint.getMinMax(rule);

    if (lgt) {
        switch (rule.valueType) {
            case 'Integer':
                if (rule.min >= 0) {
                    return ['MsgB_10', `${rule.max}`];
                } else {
                    return ['MsgB_8', `${rule.min}`, `${rule.max}`];
                }
            case 'Decimal':
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
            case 'Date':
                return ['FND_E_INTEGER_MIN', constr];
            case 'Time':
                return ['FND_E_INTEGER_MAX', constr];
            case 'Clock':
                return ['MsgB_16', TimeWithDay.toString(rule.min as number), TimeWithDay.toString(rule.max as number)];
            case 'TimePoint':
                return ['MsgB_16', TimePoint.toString(rule.min as number), TimePoint.toString(rule.max as number)];
            case 'Duration':
                return ['MsgB_15', TimeDuration.toString(rule.min as number), TimeDuration.toString(rule.max as number)];
            default:
                break;
        }
    }

    return null;
};

export { max };