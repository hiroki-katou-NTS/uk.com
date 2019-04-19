const max = function (value: number, max: number, rule: { charType: string }) {
    // decimal:     FND_E_REALNUMBER_MAX
    // haflint:     FND_E_HALFINT_MAX
    // int:         FND_E_INTEGER_MAX
    
    return value > max ? 'error_max' : null;
};

export { max };