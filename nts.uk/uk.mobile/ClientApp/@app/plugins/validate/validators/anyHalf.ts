import { text } from '@app/utils/text'

const anyHalf = function (value: string, check: boolean) {
    return value.length === text.countHalf(value) ? null : 'MsgB_4';
};

export { anyHalf };