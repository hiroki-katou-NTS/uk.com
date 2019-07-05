import { _ } from '@app/provider';

export const dateRange = function (value: { start: Number, end: Number }, checkOr: boolean) {
    if (checkOr && !_.isNil(value) && value.end < value.start) {
        return 'MsgB_49';
    }

    return null;
};