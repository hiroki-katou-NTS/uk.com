const max = function (value: number, max: number, rule: { charType: string }) {
    return value > max ? 'error_max' : null;
};

export { max };