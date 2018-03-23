module nts.uk.com.view.cps001.i.vm {
    import getText = nts.uk.resource.getText;
    let __viewContext: any = window['__viewContext'] || {},
        block = window["nts"]["uk"]["ui"]["block"]["grayout"],
        unblock = window["nts"]["uk"]["ui"]["block"]["clear"],
        invisible = window["nts"]["uk"]["ui"]["block"]["invisible"];
    export class ScreenModel {

        enaBtnNew : KnockoutObservable<boolean> = ko.observable(true);
        enaBtnRemove : KnockoutObservable<boolean> = ko.observable(true);
        visibleGrant : KnockoutObservable<boolean> = ko.observable(true);
        visibleUse : KnockoutObservable<boolean> = ko.observable(true);
        visibleOver : KnockoutObservable<boolean> = ko.observable(true);
        visibleReam : KnockoutObservable<boolean> = ko.observable(true);

        // list data
        listData: KnockoutObservableArray<SpecialLeaveRemaining>;
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any> = ko.observable();
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

            self.currentCode.subscribe((newValue) => {
                console.log(newValue);

                self.dayNumberOfGrantsTitle(newValue);
                self.dayNumberOfGrants(newValue);
                self.grantTimeTitle(newValue);
                self.grantTime(newValue);

                // detail of Use
                self.dayNumberOfUseTitle(newValue);
                self.dayNumberOfUse(newValue);
                self.useTimeTitle(newValue);
                self.useTime(newValue);

                // Exeeded detail
                self.dayNumberOverTitle(newValue);
                self.dayNumberOver(newValue);
                self.timeOverTitle(newValue);
                self.timeOver(newValue);

                // Reaming detail
                self.dayNumberOfReamTitle(newValue);
                self.dayNumberOfReam(newValue);
                self.timeReamTitle(newValue);
                self.timeReam(newValue);
            });

        }



        public startPage(): JQueryPromise<any> {

            let self = this,
                dfd = $.Deferred(),
                lstData = self.listData,
                currentCode = self.currentCode();

            self.currentCode(10);

            lstData.removeAll();
            service.getData().done((data) => {
                self.listData(data);
                self.currentCode(self.listData()[0].code);
            });



            return dfd.promise();
        }

        public newMode(): void {
            let self = this;
            self.enaBtnNew(false);
            self.enaBtnRemove(false);
            self.visibleGrant(false);
            self.visibleUse(false);
            self.visibleOver(false);
            self.visibleReam(false);
        }


        public registerData(): void {
            let _self = this;


        }


        public close(): void {
            nts.uk.ui.windows.close();
        }

        public remove(): void {
            let _self = this;

            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" })
                .ifYes(() => {


                }).ifNo(() => {
                    // Nothing happen
                })
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
        numberOfDayGrant: string;
        timeGrant: string;
        numberOfDayUse: string;
        numberOfDayUseToLose: string;
        timeUse: string;
        numberOfExceededDays: string;
        timeExceeded: string;
        numberOfDayRemain: string;
        timeRemain: string;

    }

    class SpecialLeaveRemaining {
        specialid: string;
        code: string;
        grantDate: string;
        deadline: string;
        dayNumberOfGrants: string;
        grantTime: string;
        dayNumberOfUse: string;
        useTime: string;
        dayNumberOfExeeded: string;
        timeExeeded: string;
        dayNumberOfReam: string;
        timeReam: string;
        leavExpStatus: string;
        constructor(data: ISpecialLeaveRemaining) {
            this.specialid = data.specialid;
            this.code = data.specialLeaCode;
            this.grantDate = data.grantDate;
            this.deadline = data.deadlineDate;
            this.dayNumberOfGrants = data.numberOfDayGrant;
            this.grantTime = data.timeGrant;
            this.dayNumberOfUse = data.numberOfDayUse;
            this.useTime = data.timeUse;
            this.dayNumberOfExeeded = data.numberOfExceededDays;
            this.timeExeeded = data.timeExceeded;
            this.dayNumberOfReam = data.numberOfDayRemain;
            this.timeReam = data.timeRemain;
            this.leavExpStatus = data.expStatus;
        }
    }

}