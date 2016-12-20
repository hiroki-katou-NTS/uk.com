module nts.uk.ui {
    export interface NtsGridListColumn {
        headerText: string;
        prop: string;
        width: number;
    }
    
    export interface NtsTabPanelModel {
        id: string;
        title: string;
        content: string;
        visible: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
    }
}