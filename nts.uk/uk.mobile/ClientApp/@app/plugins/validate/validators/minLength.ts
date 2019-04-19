const minLength = function (value: string, length: number) {
    return value.length == 0 ? null : (value.length < length ? 'error_minLength' : null);
};

export { minLength };