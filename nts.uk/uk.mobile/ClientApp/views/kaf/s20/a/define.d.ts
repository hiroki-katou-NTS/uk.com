
export interface IOptionalItem {
    controlOfAttendanceItemsDto: IControlOfAttendanceItemsDto;
    optionalItemDto: IOptionalItemDto;
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

export interface IControlOfAttendanceItemsDto {
    companyID: string;
    headerBgColorOfDailyPer: string;
    inputUnitOfTimeItem: number | null;
    itemDailyID: number | null;
}

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

export interface OptionalItemApplication {
    amountLower: null;
    amountUpper: null;
    lowerCheck: true;
    numberLower: null;
    numberUpper: null;
    timeLower: number | null;
    timeUpper: number | null;
    upperCheck: boolean;
    unit: string;
    inputUnitOfTimeItem: number | null;
    optionalItemName: string;
    optionalItemNo: number | null;
    optionalItemAtr: number | null;
    time: number | null;
    number: number | null;
    amount: number | null;
}

interface optionalItems {
    itemNo: number | null;
    times: number | null;
    amount: number | null;
    time: number | null;
}