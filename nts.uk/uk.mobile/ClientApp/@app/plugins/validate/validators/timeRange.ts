import { _ } from '@app/provider';
import { IRule } from 'declarations';

export const timeRange = function (value: { start: Number, end: Number }, checkOr: boolean, rule: IRule) {

    if (value === undefined || value === null) {
        return false;
    }

    if ( value.end < value.start ) {
        return 'MsgB_21';
    }
    
};