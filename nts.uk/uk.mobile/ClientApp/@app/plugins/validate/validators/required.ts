const required = function (value: any, checkOr: boolean) {
    let msg = false;

    if (Array.isArray(value)) {
        msg = value.length === 0;
    } else if (value === undefined || value === null) {
        msg = true;
    } else if (value === false) {
        msg = false;
    } else if (value instanceof Date) {
        // invalid date won't pass
        msg = isNaN(value.getTime());
    } else if (typeof value === 'object') {
        msg = Object.keys(value).length === 0;
    } else {
        msg = String(value).length === 0;
    }

    return !msg ? null : 'MsgB_1';
};

export { required };