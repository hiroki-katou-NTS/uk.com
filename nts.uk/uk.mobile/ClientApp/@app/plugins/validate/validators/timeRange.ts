import { _ } from '@app/provider';
import { IRule } from 'declarations';

export const timeRange = function (value: { start: Number, end: Number }, checkOr: boolean, rule: IRule) {
    
    if (value == null) {
        return 'MsgB_99';
    }

    if ( value.start === null && value.end === null) {
        return 'MsgB_99';
    }
    
};