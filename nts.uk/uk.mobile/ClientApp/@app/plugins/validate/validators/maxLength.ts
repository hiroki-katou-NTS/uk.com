const maxLength = function (value: string, length: number) {
    return value.length == 0 ? null : (value.length > length ? 'error_maxLength' : null);
};

export { maxLength };