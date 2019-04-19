const min = function (value: number, min: number, rule: { charType: string }) {
    return value < min ? 'error_min' : null;
};

export { min };