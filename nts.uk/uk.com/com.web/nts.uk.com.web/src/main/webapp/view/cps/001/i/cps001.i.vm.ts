module nts.uk.com.view.cps001.i.vm {
    import getText = nts.uk.resource.getText;
    import getShared = nts.uk.ui.windows.getShared;
    let __viewContext: any = window['__viewContext'] || {},
        block = window["nts"]["uk"]["ui"]["block"]["grayout"],
        unblock = window["nts"]["uk"]["ui"]["block"]["clear"],
        invisible = window["nts"]["uk"]["ui"]["block"]["invisible"];

    export class ScreenModel {

        createMode: KnockoutObservable<boolean>;

        enbbtnNewmode: KnockoutObservable<boolean> = ko.observable(true);
        enbbtnDelete: KnockoutObservable<boolean> = ko.observable(true);


        checked: KnockoutObservable<boolean> = ko.observable(false);

        // list data
        listData: KnockoutObservableArray<ISpecialLeaveRemaining> = ko.observableArray([]);
        listFullData: KnockoutObservableArray<ISpecialLeaveRemaining> = ko.observableArray([]);
        currentValue: KnockoutObservable<any> = ko.observable();
        //grant
        grantDateTitle: KnockoutObservable<string> = ko.observable(null);
        dateGrantInp: KnockoutObservable<string> = ko.observable(null);

        //exp
        expDateTitle: KnockoutObservable<string> = ko.observable(null);
        deadlineDateInp: KnockoutObservable<string> = ko.observable(null);

        expStateTitle: KnockoutObservable<string> = ko.observable(null);
        roundingRules: KnockoutObservableArray<any>;

        selectedRuleCode: any;

        statusOfUse: KnockoutObservable<string> = ko.observable(null);

        // grant detail
        dayNumberOfGrantsTitle: KnockoutObservable<string> = ko.observable(null);
        dayNumberOfGrants: KnockoutObservable<number> = ko.observable(null);
        grantTimeTitle: KnockoutObservable<string> = ko.observable(null);
        grantTime: KnockoutObservable<number> = ko.observable(null);

        //  use detail
        dayNumberOfUseTitle: KnockoutObservable<string> = ko.observable(null);
        dayNumberOfUse: KnockoutObservable<number> = ko.observable(null);
        useTimeTitle: KnockoutObservable<string> = ko.observable(null);
        useTime: KnockoutObservable<number> = ko.observable(null);

        // Over detail
        dayNumberOverTitle: KnockoutObservable<string> = ko.observable(null);
        dayNumberOver: KnockoutObservable<number> = ko.observable(null);
        timeOverTitle: KnockoutObservable<string> = ko.observable(null);
        timeOver: KnockoutObservable<number> = ko.observable(null);

        // Reaming detail
        dayNumberOfReamTitle: KnockoutObservable<string> = ko.observable(null);
        dayNumberOfReam: KnockoutObservable<number> = ko.observable(null);
        timeReamTitle: KnockoutObservable<string> = ko.observable(null);
        timeReam: KnockoutObservable<number> = ko.observable(null);

        columns: KnockoutObservableArray<any>;
        useTimeH: KnockoutObservable<boolean>;
        timeExeededH: KnockoutObservable<boolean>;
        timeReamH: KnockoutObservable<boolean>;
        grantTimeH: KnockoutObservable<boolean>;

        //data recive from cps001.a
        specialCode: KnockoutObservable<string>;

        constructor() {
            let self = this;
            let sid = __viewContext.user.employeeId;

            self.specialCode = getShared('CPS001I_PARAM');

            self.expStateTitle = ko.observable('expDateTitle');
            self.roundingRules = ko.observableArray([
                { code: '0', name: '四捨五入' },
                { code: '1', name: '切り上げ' }
            ]);
            self.selectedRuleCode = ko.observable(0);

            // Subsribe table
            self.currentValue.subscribe(value => {
                if (value) {
                    let currentRow: ISpecialLeaveRemaining = _.find(ko.toJS(self.listData), function(item: any) { return item.grantDate == value });
                    service.getDetail(currentRow.specialid).done((result: ISpecialLeaveRemaining) => {
                        if (result) {
                            self.bindingData(result);
                        }
                    });
                    $('#idGrantDate').focus();
                }

            });

            // Subscribe checkbox
            self.checked.subscribe(value => {
                console.log(value);
                let sID = __viewContext.user.employeeId;

            });


        }



        public startPage() {

            let self = this;
            block();
            service.getAllList().done((data: Array<ISpecialLeaveRemaining>) => {
                if (data && data.length > 0) {
                    self.listFullData(data);
                    _.forEach
                    self.listData(self.convertTreeToArray(data));
                    self.currentValue(data[0].grantDate);
                    // Set focus
                } else {
                    self.newMode();
                }
                $('#idGrantDate').focus();
                self.getItemDef();
                unblock();
            });


        }

        /**
     * Convert data to new array.
     */
        private convertTreeToArray(dataList: Array<ISpecialLeaveRemaining>): Array<any> {
            let self = this,
                res: Array<any> = _.map(dataList, item => {
                    return {
                        specialid: item.specialid, sid: item.sid, specialLeaCode: item.specialLeaCode,
                        grantDate: item.grantDate, deadlineDate: item.deadlineDate,
                        expStatus: self.formatEnum(item.expStatus), registerType: item.registerType,
                        numberDayGrant: self.formatDate(item.numberDayGrant), timeGrant: item.timeGrant,
                        numberDayUse: self.formatDate(item.numberDayUse), timeUse: item.timeUse,
                        numberDaysOver: self.formatDate(item.numberDaysOver), timeOver: item.timeOver,
                        numberDayRemain: self.formatDate(item.numberDayRemain), timeRemain: item.timeRemain
                    }
                });

            return res;
        }

        Save() {
            let self = this;
            if (nts.uk.ui.errors.hasError()) {
                return;
            }
            let currentRow: ISpecialLeaveRemaining = _.find(ko.toJS(self.listData), function(item: any) { return item.grantDate == self.currentValue(); });
            let command = {
                specialid: currentRow.specialid, sid: currentRow.sid, specialLeaCode: currentRow.specialLeaCode,
                grantDate: self.dateGrantInp(), deadlineDate: self.deadlineDateInp(),
                expStatus: self.selectedRuleCode(), registerType: null,
                numberDayGrant: self.dayNumberOfGrants(), timeGrant: self.grantTime(),
                numberDayUse: self.dayNumberOfUse(), timeUse: self.useTime(),
                numberDaysOver: self.dayNumberOver(), timeOver: self.timeOver(),
                numberDayRemain: self.dayNumberOfReam(), timeRemain: self.timeReam()
            };
            // call service savedata
            block();
            service.saveData(command).done((_data: any) => {
                unblock();
                self.startPage();
            }).fail((error: any) => {
                unblock();
            });

        }

        Delete() {
            let self = this;
            if (nts.uk.ui.errors.hasError()) {
                return;
            }

        }

        closeDialog() {
            nts.uk.ui.windows.close();
        }


        bindingData(result: ISpecialLeaveRemaining): void {
            let self = this;
            self.dayNumberOfGrants(result.numberDayGrant);
            self.grantTime(result.timeGrant);

            // detail of Use
            self.dayNumberOfUse(result.numberDayUse);
            self.useTime(result.timeUse);

            // Exeeded detail
            self.dayNumberOver(result.numberDaysOver);
            self.timeOver(result.timeOver);

            // Reaming detail
            self.dayNumberOfReam(result.numberDayRemain);
            self.timeReam(result.timeRemain);
        }

        formatDate(value) {
            if (value) {
                return value + '日';
            }
        }

        formatEnum(value) {
            if (value && value === '0') {
                return '使用可能';
            } else if (value && value === '1') {
                return '期限切れ';
            }
        }



        getItemDef() {
            let self = this;
            let ctgCode: string = "CS00040";
            service.getItemDef(ctgCode).done((data) => {
                self.setItemDefValue(data).done(() => {
                    self.setGridList();
                });
            });
        }
        setItemDefValue(data: any): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            $("td[data-itemCode]").each(function() {
                let itemCodes = $(this).attr('data-itemcode');
                if (itemCodes) {
                    let itemCodeArray = itemCodes.split(" ");
                    _.forEach(itemCodeArray, (itemCode) => {
                        let itemDef = _.find(data, (item) => {
                            return item.itemCode == itemCode;
                        });
                        if (itemDef) {
                            if (itemDef.display) {
                                $(this).children().first().html("<label>" + itemDef.itemName + "</label>");
                            } else {
                                $(this).parent().css("display", "none");
                            }
                            let timeType = itemCodeArray[itemCodeArray.length - 1];
                            switch (timeType) {
                                case "grantTime":
                                    self.grantTimeH = ko.observable(!itemDef.display);
                                    break;
                                case "useTime":
                                    self.useTimeH = ko.observable(!itemDef.display);
                                    break;
                                case "timeOver":
                                    self.timeExeededH = ko.observable(!itemDef.display);
                                    break;
                                case "timeReam":
                                    self.timeReamH = ko.observable(!itemDef.display);
                                    break;
                            }
                        }
                    });
                }
                dfd.resolve();
            });

            return dfd.promise();
        }
        setGridList() {
            let self = this;
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText('CPS001_118'), key: 'grantDate', width: 80 },
                { headerText: nts.uk.resource.getText('CPS001_119'), key: 'deadlineDate', width: 80 },
                { headerText: nts.uk.resource.getText('CPS001_120'), key: 'numberDayGrant', width: 80 },
                { headerText: nts.uk.resource.getText('CPS001_128'), key: 'timeGrant', width: 80, hidden: self.grantTimeH() },
                { headerText: nts.uk.resource.getText('CPS001_121'), key: 'numberDayUse', width: 80 },
                { headerText: nts.uk.resource.getText('CPS001_122'), key: 'timeUse', width: 80, hidden: self.useTimeH() },
                { headerText: nts.uk.resource.getText('CPS001_130'), key: 'numberDaysOver', width: 80 },
                { headerText: nts.uk.resource.getText('CPS001_131'), key: 'timeOver', width: 80, hidden: self.timeExeededH() },
                { headerText: nts.uk.resource.getText('CPS001_123'), key: 'numberDayRemain', width: 80 },
                { headerText: nts.uk.resource.getText('CPS001_124'), key: 'timeRemain', width: 80, hidden: self.timeReamH() },
                { headerText: nts.uk.resource.getText('CPS001_129'), key: 'expStatus', width: 80 }
            ]);
            let table: string = '<table tabindex="5" id="sel_item_grid" data-bind="ntsGridList: { height: 282, options: listData, primaryKey:\'grantDate\',showNumbering: true,columns:columns,multiple: false, value: currentValue}"></table>';
            $("#tbl").html(table);
            ko.applyBindings(self, $("#tbl")[0]);
        }

        newMode() {
            let self = this;
            self.enbbtnNewmode(false);
            self.enbbtnDelete(false);
            self.listData(null);
            self.dateGrantInp(null);
            self.deadlineDateInp(null);
            self.dayNumberOfGrants(null);
            self.grantTime(null);
            self.dayNumberOfUse(null);
            self.useTime(null);
            self.dayNumberOfReam(null);
            self.timeReam(null);
            self.dayNumberOver(null);
            self.timeOver(null);
        }
    }

    interface ISpecialLeaveRemaining {
        specialid: string;
        sid: string;
        specialLeaCode: string;
        grantDate: string;
        deadlineDate: string;
        expStatus: string;
        registerType: string;
        numberDayGrant: number;
        timeGrant: number;
        numberDayUse: number;
        useSavingDays: number;
        timeUse: number;
        numberDaysOver: number;
        timeOver: number;
        numberDayRemain: number;
        timeRemain: number;

    }

    class SpecialLeaveRemaining {
        specialid: string;
        sid: string;
        specialLeaCode: string;
        grantDate: string;
        deadlineDate: string;
        expStatus: string;
        registerType: string;
        numberDayGrant: number;
        timeGrant: number;
        numberDayUse: number;
        useSavingDays: number;
        timeUse: number;
        numberOverDays: number;
        timeOver: number;
        numberDayRemain: number;
        timeRemain: number;
        constructor(data: ISpecialLeaveRemaining) {
            this.specialid = data.specialid;
            this.sid = data.sid;
            this.specialLeaCode = data.specialLeaCode;
            this.grantDate = data.grantDate;
            this.deadlineDate = data.deadlineDate;
            this.expStatus = data.expStatus;
            this.registerType = data.registerType;
            this.numberDayGrant = data.numberDayGrant;
            this.timeGrant = data.timeGrant;
            this.numberDayUse = data.numberDayUse;
            this.useSavingDays = data.useSavingDays;
            this.timeUse = data.timeUse;
            this.numberOverDays = data.numberDaysOver;
            this.timeOver = data.timeOver;
            this.numberDayRemain = data.numberDayRemain;
            this.timeRemain = data.timeRemain;
        }
    }

}