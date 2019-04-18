import { obj, text, constraint } from '@app/utils';

const maxLength = function (value: string, length: number, rule: { charType: string }) {
    return value.length == 0 ? null : (text.countHalf(value) > Number(length) ? 'error_maxLength' : null);
};

export { maxLength };