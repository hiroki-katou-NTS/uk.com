const between = function (value: Date | number, min: Date | number, max: Date | number, rule?: any) {
    return +min <= +value && +max >= +value ? null : 'not_between';
};

export { between };