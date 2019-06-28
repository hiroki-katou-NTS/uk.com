import { IRule } from 'declarations';

export const dateRange = function (value: { start: Number, end: Number }, checkOr: boolean, rule: IRule) {
    if (value === undefined || value === null || value.start === null || value.end === null) {
        return false;
    }

    if ( value.end < value.start ) {
        return 'MsgB_21';
    }
    
};