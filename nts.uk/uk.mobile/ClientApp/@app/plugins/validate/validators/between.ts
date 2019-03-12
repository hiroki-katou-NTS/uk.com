const between = function (value: Date | number, min: number, max: number) {
    return +min <= +value && +max >= +value ? null : 'not_between';
};

export { between };