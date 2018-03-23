module nts.uk.com.view.cps001.i.vm {
    import getText = nts.uk.resource.getText;
    import getShared = nts.uk.ui.windows.getShared;
    let __viewContext: any = window['__viewContext'] || {},
        block = window["nts"]["uk"]["ui"]["block"]["grayout"],
        unblock = window["nts"]["uk"]["ui"]["block"]["clear"],
        invisible = window["nts"]["uk"]["ui"]["block"]["invisible"];
    
    export class ScreenModel {
        
        createMode: KnockoutObservable<boolean>;

        enaBtnNew: KnockoutObservable<boolean> = ko.observable(true);
        enaBtnRemove: KnockoutObservable<boolean> = ko.observable(true);
        visibleGrant: KnockoutObservable<boolean> = ko.observable(true);
        visibleUse: KnockoutObservable<boolean> = ko.observable(true);
        visibleOver: KnockoutObservable<boolean> = ko.observable(true);
        visibleReam: KnockoutObservable<boolean> = ko.observable(true);

        checked: KnockoutObservable<boolean> = ko.observable(false);

        // list data
        listData: KnockoutObservableArray<SpecialLeaveRemaining>;
        currentValue: KnockoutObservable<any> = ko.observable();
        //grant
        grantDateTitle: KnockoutObservable<string>;
        dateGrantInp: KnockoutObservable<string>;

        //exp
        expDateTitle: KnockoutObservable<string>;
        deadlineDateInp: KnockoutObservable<string>;

        expStateTitle: KnockoutObservable<string>;
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;

        statusOfUse: KnockoutObservable<string>;

        // grant detail
        dayNumberOfGrantsTitle: KnockoutObservable<string>;
        dayNumberOfGrants: KnockoutObservable<number>;
        grantTimeTitle: KnockoutObservable<string>;
        grantTime: KnockoutObservable<number>;

        //  use detail
        dayNumberOfUseTitle: KnockoutObservable<string>;
        dayNumberOfUse: KnockoutObservable<number>;
        useTimeTitle: KnockoutObservable<string>;
        useTime: KnockoutObservable<number>;

        // Over detail
        dayNumberOverTitle: KnockoutObservable<string>;
        dayNumberOver: KnockoutObservable<number>;
        timeOverTitle: KnockoutObservable<string>;
        timeOver: KnockoutObservable<number>;

        // Reaming detail
        dayNumberOfReamTitle: KnockoutObservable<string>;
        dayNumberOfReam: KnockoutObservable<number>;
        timeReamTitle: KnockoutObservable<string>;
        timeReam: KnockoutObservable<number>



        constructor() {
            let self = this;

            self.createMode = ko.observable(null);
            
            self.grantDateTitle = ko.observable('grantDateTitle');
            self.dateGrantInp = ko.observable('20000101');

            self.expDateTitle = ko.observable('expDateTitle');
            self.deadlineDateInp = ko.observable('20000101');

            self.expStateTitle = ko.observable('expDateTitle');
            self.roundingRules = ko.observableArray([
                { code: '0', name: '四捨五入' },
                { code: '1', name: '切り上げ' }
            ]);
            self.selectedRuleCode = ko.observable(0);

            self.statusOfUse = ko.observable('statusOfUse');

            // detail of Grant
            self.dayNumberOfGrantsTitle = ko.observable('日数');
            self.dayNumberOfGrants = ko.observable(12);
            self.grantTimeTitle = ko.observable('時間');
            self.grantTime = ko.observable(120);

            // detail of Use
            self.dayNumberOfUseTitle = ko.observable('日数');
            self.dayNumberOfUse = ko.observable(12);
            self.useTimeTitle = ko.observable('時間');
            self.useTime = ko.observable(120);

            // Over detail
            self.dayNumberOverTitle = ko.observable('日数');
            self.dayNumberOver = ko.observable(12);
            self.timeOverTitle = ko.observable('時間');
            self.timeOver = ko.observable(120);

            // Reaming detail
            self.dayNumberOfReamTitle = ko.observable('日数');
            self.dayNumberOfReam = ko.observable(12);
            self.timeReamTitle = ko.observable('時間');
            self.timeReam = ko.observable(120);


            self.listData = ko.observableArray([]);

            self.currentValue.subscribe((newValue) => {
                console.log(newValue);


            });

            self.currentValue = ko.observable(moment().toDate());

            // Subsribe table
            self.currentValue.subscribe(value => {
                if (value) {
                    service.getDetail(value).done((result: ISpecialLeaveRemaining) => {
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



        public startPage(): JQueryPromise<any> {

            let self = this,
                dfd = $.Deferred(),
                lstData = self.listData,
                currentValue = self.currentValue();

            lstData.removeAll();
            service.getAllList().done((data: Array<ISpecialLeaveRemaining>) => {
               if (data && data.length > 0) {
                    _.each(data, (item) => {
                        self.listData.push(item);
                    });
                    // Set focus
                    self.currentValue(self.listData()[0].grantDate);
                } else {
                    // Set to cr eate mode
                }
                $('#idGrantDate').focus();
            });



            return dfd.promise();
        }

        public newMode(): void {
            let self = this;
            self.enaBtnNew(false);
            self.enaBtnRemove(false);
        }

        


        public saveData(): void {
            let self = this,
                dataShare: any = getShared('CPS001B_PARAMS') || null;
            let obj = {
                sid: __viewContext.user.employeeId,
                specialLeaCode: dataShare,
                grantDate : self.dateGrantInp(),
                deadlineDate : self.deadlineDateInp(),
                expStatus : self.selectedRuleCode(),
                registerType : 0,
                numberDayGrant: self.dayNumberOfGrants(),
                timeGrant : self.grantTime(),
                numberDayUse: self.dayNumberOfUse(),
                timeUse : self.useTime(),
                useSavingDays : null,
                numberDaysOver : self.dayNumberOver(),
                timeOver : self.timeOver(),
                numberDayRemain : self.dayNumberOfReam(),
                timeRemain : self.timeReam()
            };
            
            


        }

        public remove(): void {
            let _self = this;

            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" })
                .ifYes(() => {


                }).ifNo(() => {
                    // Nothing happen
                })
        }
        
        public close(): void {
            nts.uk.ui.windows.close();
        }
        
        public bindingData(result: ISpecialLeaveRemaining): void {
            let self = this;
            self.dayNumberOfGrantsTitle("");
            self.dayNumberOfGrants(result.numberDayGrant);
            self.grantTimeTitle("");
            self.grantTime(result.timeGrant);

            // detail of Use
            self.dayNumberOfUseTitle("");
            self.dayNumberOfUse(result.numberDayUse);
            self.useTimeTitle("");
            self.useTime(result.timeUse);

            // Exeeded detail
            self.dayNumberOverTitle("");
            self.dayNumberOver(result.numberOverDays);
            self.timeOverTitle("");
            self.timeOver(result.timeOver);

            // Reaming detail
            self.dayNumberOfReamTitle("");
            self.dayNumberOfReam(result.numberDayRemain);
            self.timeReamTitle("");
            self.timeReam(result.timeRemain);
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