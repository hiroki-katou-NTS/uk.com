import { IRule } from 'declarations';
import { text, constraint } from '@app/utils';

const maxLength = function (value: string, length: number, rule: IRule) {
    if (value.length && text.countHalf(value) > Number(length)) {
        let cl: string = `${constraint.getCharLength(rule)}`;

        switch (rule.charType) {
            case 'Any':
                return ['MsgB_4', cl];
            case 'Kana':
                return ['MsgB_7', cl];
            case 'Numeric':
                return ['MsgB_5', cl];
            case 'AlphaNumeric':
                return ['MsgB_6', cl];
            case 'AnyHalfWidth':
                return ['MsgB_4', cl];
        }
    }

    return null;
};

export { maxLength };