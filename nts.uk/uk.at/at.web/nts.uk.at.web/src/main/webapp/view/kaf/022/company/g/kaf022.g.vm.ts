module nts.uk.at.view.kaf022.g.viewmodel {
    import getText = nts.uk.resource.getText;

    export class ScreenModelG {
        itemListB4: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 1, name: getText('KAF022_420')},
            {code: 0, name: getText('KAF022_421')}
        ]);
        itemListB6: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 1, name: getText('KAF022_36')},
            {code: 0, name: getText('KAF022_37')}
        ]);
        itemListB8: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 1, name: getText('KAF022_100')},
            {code: 0, name: getText('KAF022_101')}
        ]);
        itemListB24: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 0, name: getText('KAF022_173')},
            {code: 1, name: getText('KAF022_174')}
        ]);
        itemListB30: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 0, name: getText('KAF022_173')},
            {code: 1, name: getText('KAF022_175')},
            {code: 2, name: getText('KAF022_651')}
        ]);
        itemListB26: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 0, name: getText('KAF022_173')},
            {code: 1, name: getText('KAF022_174')},
            {code: 2, name: getText('KAF022_175')}
        ]);
        itemListB28: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 0, name: getText('KAF022_709')},
            {code: 1, name: getText('KAF022_710')},
        ]);

        itemListB242: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 0, name: getText('KAF022_75')},
            {code: 1, name: getText('KAF022_82')},
        ]);

        itemListB362: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 0, name: getText('KAF022_173')},
            {code: 1, name: getText('KAF022_712')},
        ]);

        itemListB372: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 0, name: getText('KAF022_173')},
            {code: 1, name: getText('KAF022_174')},
        ]);

        itemListB5: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 0, name: getText('KAF022_44')},
            {code: 1, name: getText('KAF022_396')},
        ]);

        selectedIdB4: KnockoutObservable<number>;
        selectedIdB8: KnockoutObservable<number>;
        selectedIdB10: KnockoutObservable<number>;
        selectedIdB12: KnockoutObservable<number>;
        selectedIdB6: KnockoutObservable<number>;

        selectedIdB15: KnockoutObservable<number>;
        selectedIdB17: KnockoutObservable<number>;
        selectedIdB19: KnockoutObservable<number>;
        selectedIdB21: KnockoutObservable<number>;

        selectedIdB24: KnockoutObservable<number>;
        selectedIdB26: KnockoutObservable<number>;
        selectedIdB28: KnockoutObservable<number>;
        selectedIdB30: KnockoutObservable<number>;
        selectedIdB32: KnockoutObservable<number>;

        selectedValueF13: KnockoutObservable<number> = ko.observable(0);

        constructor() {
            const self = this;
            self.selectedIdB4 = ko.observable(0);
            self.selectedIdB8 = ko.observable(0);
            self.selectedIdB10 = ko.observable(0);
            self.selectedIdB12 = ko.observable(0);
            self.selectedIdB6 = ko.observable(0);

            self.selectedIdB15 = ko.observable(0);
            self.selectedIdB17 = ko.observable(0);
            self.selectedIdB19 = ko.observable(0);
            self.selectedIdB21 = ko.observable(0);

            self.selectedIdB24 = ko.observable(0);
            self.selectedIdB26 = ko.observable(0);
            self.selectedIdB28 = ko.observable(0);
            self.selectedIdB30 = ko.observable(0);
            self.selectedIdB32 = ko.observable(0);

            $("#fixed-table-g1").ntsFixedTable({});
            $("#fixed-table-g5").ntsFixedTable({});
            $("#fixed-table-g3").ntsFixedTable({});
        }

        initData(allData: any): void {
            const self = this;
            let data = allData.appOt;
            let otRest = allData.otRestAppCom;
            if (data && otRest) {
                self.selectedIdB4(data.workTypeChangeFlag);
                self.selectedIdB6(data.flexJExcessUseSetAtr);
                self.selectedIdB8(otRest.bonusTimeDisplayAtr);
                self.selectedIdB10(otRest.divergenceReasonFormAtr);
                self.selectedIdB12(otRest.divergenceReasonInputAtr);

                self.selectedIdB15(otRest.performanceDisplayAtr);
                self.selectedIdB17(otRest.preDisplayAtr);
                self.selectedIdB19(otRest.calculationOvertimeDisplayAtr);
                self.selectedIdB21(otRest.extratimeDisplayAtr);

                self.selectedIdB24(otRest.preExcessDisplaySetting);
                self.selectedIdB26(otRest.performanceExcessAtr);
                self.selectedIdB28(data.priorityStampSetAtr);
                self.selectedIdB30(otRest.extratimeExcessAtr);
                self.selectedIdB32(otRest.appDateContradictionAtr);
                //                    self.selectedIdB18(data.flexExcessUseSetAtr);
                //                    self.selectedIdB19(data.priorityStampSetAtr);
                // self.selectedIdR2_8(data.preTypeSiftReflectFlg);
                // self.selectedIdR2_11(data.preOvertimeReflectFlg);
                // self.selectedIdR2_15(data.postTypesiftReflectFlg);
                // self.selectedIdR2_18(data.postWorktimeReflectFlg);
                // self.selectedIdR2_21(data.postBreakReflectFlg);
                // self.selectedCodeR2_22(data.restAtr);
                //                    self.selectedIdB31(data.calendarDispAtr);
                //                    self.selectedIdB32(data.instructExcessOtAtr);
                //                    self.selectedCodeB33(data.unitAssignmentOvertime);
                //                    self.selectedIdB34(data.useOt);
                //                    self.selectedIdB35(data.earlyOverTimeUseAtr);
                //                    self.selectedIdB36(data.normalOvertimeUseAtr);
            }
        }

    }

    class ItemModel {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }

}