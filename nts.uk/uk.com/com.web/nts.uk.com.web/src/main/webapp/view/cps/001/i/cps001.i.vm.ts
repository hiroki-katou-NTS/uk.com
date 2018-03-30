module nts.uk.com.view.cps001.i.vm {
    import getText = nts.uk.resource.getText;
    import getShared = nts.uk.ui.windows.getShared;
    import info = nts.uk.ui.dialog.info;
    import alert = nts.uk.ui.dialog.alert;
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

        statusOfUse: KnockoutObservable<number> = ko.observable(0);

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
                { code: 0, name: '四捨五入' },
                { code: 1, name: '切り上げ' }
            ]);
            self.selectedRuleCode = ko.observable(0);

            // Subsribe table
            self.currentValue.subscribe(value => {
                if (value) {
                    let currentRow: ISpecialLeaveRemaining = _.find(ko.toJS(self.listData), function(item: any) { return item.specialid == value });
                    service.getDetail(currentRow.specialid).done((result: ISpecialLeaveRemaining) => {
                        if (result) {
                            self.bindingData(result);
                        }
                    });
                    $('#idDateGrantInp').focus();
                }

            });

            // Subscribe checkbox
            self.checked.subscribe(value => {
                console.log(value);
                let sID = __viewContext.user.employeeId;

            });


        }

        loadData(index?: number): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            service.getAllList("1B3D3CC4-90FD-4992-9566-12EC72827E4C", 1).done((data: Array<ISpecialLeaveRemaining>) => {
                if (data && data.length > 0) {
                    self.listFullData(data);
                    self.listData(self.convertTreeToArray(data));
                    self.currentValue(data[index].specialid);
                    // Set focus
                } else {
                    self.newMode();
                }
                unblock();
            }).fail((_data) => {
                unblock();

            });
            return dfd.promise();
        }



        public startPage() {

            let self = this;
            block();
            self.getItemDef();
            self.loadData(0);
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

        newMode() {
            let self = this;
            self.enbbtnNewmode(false);
            self.enbbtnDelete(false);
            self.listData([]);
            self.dateGrantInp('');
            self.deadlineDateInp('');
            self.dayNumberOfGrants('');
            self.grantTime('');
            self.dayNumberOfUse('');
            self.useTime('');
            self.dayNumberOfReam('');
            self.timeReam('');
            self.dayNumberOver('');
            self.timeOver('');
            $('#idDateGrantInp').focus();
        }

        Save() {
            let self = this;
            $("#idDateGrantInp").trigger("validate");
            $("#deadlineDateInp").trigger("validate");
            $("#dayNumberOfGrants").trigger("validate");
            $("#grantTime").trigger("validate");
            $("#dayNumberOfUse").trigger("validate");
            $("#useTime").trigger("validate");
            $("#dayNumberOver").trigger("validate");
            $("#timeOver").trigger("validate");
            $("#dayNumberOfReam").trigger("validate");
            $("#timeReam").trigger("validate");

            if (nts.uk.ui.errors.hasError()) {
                return;
            }
            let currentRow: ISpecialLeaveRemaining = _.find(ko.toJS(self.listData), function(item: any) { return item.specialid == self.currentValue(); });
            //sid = "1B3D3CC4-90FD-4992-9566-12EC72827E4C" || __viewContext.user.employeeId
            let command = {
                specialid: currentRow == undefined ? null : currentRow.specialid,
                sid: "1B3D3CC4-90FD-4992-9566-12EC72827E4C",
                specialLeaCode: 1,
                grantDate: self.dateGrantInp(), deadlineDate: self.deadlineDateInp(),
                expStatus: self.selectedRuleCode(), registerType: null,
                numberDayGrant: self.dayNumberOfGrants(), timeGrant: self.grantTime(),
                numberDayUse: self.dayNumberOfUse(), timeUse: self.useTime(),
                numberDaysOver: self.dayNumberOver(), timeOver: self.timeOver(),
                numberDayRemain: self.dayNumberOfReam(), timeRemain: self.timeReam()
            };
            // call service savedata
            block();

            let saveItemIndex = _.findIndex(self.listData(), (item) => {
                return item.specialid == self.currentValue();
            });

            service.saveData(command).done((_data: any) => {
                info({ messageId: "Msg_15" }).then(function() {
                    self.loadData(saveItemIndex);
                });
                unblock();
            }).fail((error: any) => {
                unblock();
            });

        }

        Delete() {
            let self = this;
            if (nts.uk.ui.errors.hasError()) {
                return;
            }

            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" })
                .ifYes(() => {

                    let currentRow: ISpecialLeaveRemaining = _.find(ko.toJS(self.listData), function(item: any) { return item.specialid == self.currentValue(); });

                    if (currentRow != undefined) {
                        let itemListLength = self.listData().length;
                        service.remove(currentRow.specialid).done((_data: any) => {
                            if (itemListLength === 1) {
                                self.loadData(0);
                                unblock();
                            } else if (itemListLength - 1 === indexItemDelete) {
                                self.loadData(indexItemDelete - 1);
                                unblock();
                            } else if (itemListLength - 1 > indexItemDelete) {
                                self.loadData(indexItemDelete + 1);
                                unblock();
                            }

                            showDialog.info({ messageId: "Msg_16" }).then(function() {
                                unblock();
                            });
                        }).fail((error: any) => {
                            unblock();
                        });

                    }

                }).ifCancel(() => {
                });


        }

        closeDialog() {
            nts.uk.ui.windows.close();
        }


        bindingData(result: ISpecialLeaveRemaining): void {
            let self = this;

            self.dateGrantInp(result.grantDate);
            self.deadlineDateInp(result.deadlineDate);
            self.selectedRuleCode(result.expStatus);

            // detail of grant
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

        formatTime(value) {
            if (value) {
                return;
            }
        }


        formatDate(value) {
            if (value) {
                return value + '日';
            }
        }

        formatEnum(value: number) {
            return value == 0 ? '使用可能' : '期限切れ';
        }



        getItemDef() {
            let self = this;
            let ctgCode: string = "CS00039";
            service.getItemDef(ctgCode).done((data) => {
                self.setItemDefValue(data).done(() => {
                    self.setGridList();
                });
            }).fail((data) => {
                self.setGridList();
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
                { headerText: nts.uk.resource.getText('CPS001_118'), key: 'specialid', width: 0 },
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
            let table: string = '<table tabindex="5" id="sel_item_grid" data-bind="ntsGridList: { height: 282, options: listData, primaryKey:\'specialid\',showNumbering: true,columns:columns,multiple: false, value: currentValue , rows :10}"></table>';
            $("#tbl").html(table);
            ko.applyBindings(self, $("#tbl")[0]);
        }


    }

    interface ISpecialLeaveRemaining {
        specialid: string;
        sid: string;
        specialLeaCode: string;
        grantDate: string;
        deadlineDate: string;
        expStatus: number;
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
        expStatus: number;
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