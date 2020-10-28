
export interface IOptionalItemAppSet {
    code: string;
    name: string;
    useAtr: number | null;
    note: string;
    settingItems: IOptItemSet[];
}

export interface IOptItemSet {
    no: number | null;
    dispOrder: number | null;
}

export interface IOptionalItemDto {
    calcResultRange: {
        amountLower: null;
        amountUpper: null;
        lowerCheck: true;
        numberLower: null;
        numberUpper: null;
        timeLower: number | null;
        timeUpper: number | null;
        upperCheck: boolean;
    };
    empConditionAtr: number | null;
    formulas: any[];
    optionalItemAtr: number | null;
    optionalItemName: string;
    optionalItemNo: number | null;
    performanceAtr: number | null;
    unit: string;
    usageAtr: number | null;
}