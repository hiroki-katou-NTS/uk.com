import { IRule } from 'declarations';

const mantissaMaxLength = function (value: number, mantiss: number, rule?: IRule) {
    let max = `${rule.max}`,
        $value = `${value}`,
        $regex = new RegExp(`\\.\\d{0,${mantiss}}$`);

    if ($value && $value.indexOf('.') > -1 && !$regex.test(`${value}`)) {
        return ['MsgB_12', `${max.split('.')[0].length}`, `${mantiss}`];
    }

    return null;
};

export { mantissaMaxLength };