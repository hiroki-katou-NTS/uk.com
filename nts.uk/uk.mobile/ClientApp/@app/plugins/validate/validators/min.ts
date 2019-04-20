import { IRule } from 'declarations';
import { constraint } from '@app/utils';

const min = function (value: number | Date, min: number | Date, rule: IRule) {
    let lgt: boolean = value < min,
        constr: string = constraint.getMinMax(rule);

    if (lgt) {
        switch (rule.valueType) {
            case 'Integer':
                return ['MsgB_8', `${rule.min}`, `${rule.max}`];
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
                return ['FND_E_INTEGER_MIN', constr];
            case 'Clock':
                return ['FND_E_INTEGER_MIN', constr];
            case 'TimePoint':
                return ['FND_E_INTEGER_MIN', constr];
            case 'Duration':
                return ['FND_E_INTEGER_MIN', constr];
        }
    }

    return null;
};

export { min };