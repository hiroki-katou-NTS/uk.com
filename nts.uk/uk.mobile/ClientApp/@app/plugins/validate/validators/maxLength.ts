const maxLength = function (value: string, length: number) {
    return value.length == 0 ? null : (value.length > Number(length) ? 'error_maxLength' : null);
};

export { maxLength };