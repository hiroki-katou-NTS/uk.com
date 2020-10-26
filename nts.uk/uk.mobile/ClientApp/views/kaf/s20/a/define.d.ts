
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