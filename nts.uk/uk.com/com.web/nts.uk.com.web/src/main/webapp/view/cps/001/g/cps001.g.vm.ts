module nts.uk.com.view.cps001.g.vm {
    import getText = nts.uk.resource.getText;
    import info = nts.uk.ui.dialog.info;
    import alert = nts.uk.ui.dialog.alert;
    export class ScreenModel {

        // Store create/update mode
        createMode: KnockoutObservable<boolean>;
        columns: KnockoutObservableArray<any>;
        currentValue: KnockoutObservable<Date>;
        date: KnockoutObservable<string>;
        expirationStatus: KnockoutObservableArray<any>;
        value: KnockoutObservable<number>;
        checked: KnockoutObservable<boolean>;

        listAnnualLeaveGrantRemainData: KnockoutObservableArray<AnnualLeaveGrantRemainingData> = ko.observableArray([]);
        currentItem: KnockoutObservable<AnnualLeaveGrantRemainingData> = ko.observable(new AnnualLeaveGrantRemainingData(<IAnnualLeaveGrantRemainingData>{}));

        constructor() {
            let _self = this;

            _self.createMode = ko.observable(null);

            _self.checked = ko.observable(false);
            _self.expirationStatus = ko.observableArray([
                { code: '0', name: '使用可能' },
                { code: '1', name: '期限切れ' }
            ]);

            this.columns = ko.observableArray([
                { headerText: getText('CPS001_110'), type: 'date', key: 'grantDate', width: 100 },
                { headerText: getText('CPS001_111'), type: 'date', key: 'deadline', width: 100 },
                { headerText: getText('CPS001_120'), type: 'number', formatter: formatEnum, key: 'expirationStatus', width: 80 },
                { headerText: getText('CPS001_128'), type: 'string', formatter: formatDate, key: 'grantDays', width: 60 },
                { headerText: getText('CPS001_121'), key: 'grantMinutes', width: 60 },
                { headerText: getText('CPS001_122'), type: 'string', formatter: formatDate, key: 'usedDays', width: 60 },
                { headerText: getText('CPS001_123'), key: 'usedMinutes', width: 60 },
                { headerText: getText('CPS001_124'), type: 'string', formatter: formatDate, key: 'remainingDays', width: 60 },
                { headerText: getText('CPS001_129'), type: 'string', key: 'remainingMinutes', width: 60 }
            ]);
            _self.currentValue = ko.observable(moment().toDate());

            // Subsribe table
            _self.currentValue.subscribe(value => {
                if (value) {
                    service.getDetail(value).done((result: IAnnualLeaveGrantRemainingData) => {
                        if (result) {
                            _self.currentItem(new AnnualLeaveGrantRemainingData(result));
                        }
                    });
                    $('#idGrantDate').focus();
                }

            });

            // Subscribe checkbox
            _self.checked.subscribe(value => {
                console.log(value);
                let sID = __viewContext.user.employeeId;
                service.getAllListByCheckState(sID, value).done((data: Array<IAnnualLeaveGrantRemainingData>) => {
                    if (data && data.length > 0) {
                         _self.createMode(false);
                        _.each(data, (item) => {
                            _self.listAnnualLeaveGrantRemainData.push(item);
                        });
                        // Set focus
                        _self.currentValue(_self.listAnnualLeaveGrantRemainData()[0].grantDate);
                        // Set to update mode
                    } else {
                        // Set to create mode
                        _self.createMode(true);
                    }
                });
            });


        }

        /**
         * Run after page loaded
         */
        public startPage(): JQueryPromise<any> {
            let _self = this;
            let sID = __viewContext.user.employeeId;

            service.getAllList().done((data: Array<IAnnualLeaveGrantRemainingData>) => {
                if (data && data.length > 0) {
                    // Set to update mode
                    _self.createMode(false);
                    
                    _.each(data, (item) => {
                        _self.listAnnualLeaveGrantRemainData.push(item);
                    });
                    // Set focus
                    _self.currentValue(_self.listAnnualLeaveGrantRemainData()[0].grantDate);
                } else {
                    // Set to cr eate mode
                    _self.createMode(true);
                }
                $('#idGrantDate').focus();

            });
        }

        /**
         * Start create mode
         */
        public startCreateMode(): void {
            let _self = this;
            _self.createMode(true);
        }

        // Lost focus
          public lostFocus(): void {
            let _self = this;
            if (_self.createMode()) {
                return;
            } else {


            }
        }
        public create(): void {
            let _self = this;
            _self.createMode(true);
            _self.currentItem(new AnnualLeaveGrantRemainingData(<IAnnualLeaveGrantRemainingData>{}));
            $('#id-grantDate').focus();
        }
        /**
         * Save sequence
         */
        public save(): void {
            let _self = this,
                command = ko.toJS(_self.currentItem());

            if (_self.createMode()) {
                service.add(command).done((message: string) => {
                    info({ messageId: "Msg_15" }).then(function() {
                        _self.startPage();
                    });
                }).fail((message) => {
                    alert(message.message);
                });
            } else {
                service.update(command).done((message: string) => {
                    info({ messageId: "Msg_15" }).then(function() {
                        _self.startPage();
                    });
                }).fail((message) => {
                    alert(message.message);
                });
            }
        }

        /**
         * Close this dialog
         */
        public close(): void {
            nts.uk.ui.windows.close();
        }

        /**
         * Remove sequence
         */
        public remove(): void {
            let _self = this,
                sID = __viewContext.user.employeeId;
            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" })
                .ifYes(() => {
                    let command = {
                        annLeavId: ko.toJS(_self.currentItem()).annLeavId,
                        employeeId: sID,
                        grantDate: ko.toJS(_self.currentItem()).grantDate
                    };
                    service.deleteLeav(command).done((message: string) => {
                        info({ messageId: "Msg_15" }).then(function() {
                            _self.startPage();
                        });
                    }).ifNo(() => {
                        // Nothing happen
                    })
                });
        }
    }
    export interface IAnnualLeaveGrantRemainingData {
        annLeavId: string;
        grantDate: Date;
        deadline: Date;
        expirationStatus: number;
        grantDays: number;
        grantMinutes: number;
        usedDays: number;
        usedMinutes: number;
        remainingDays: number;
        remainingMinutes: number;
    }

    export class AnnualLeaveGrantRemainingData {
        annLeavId: KnockoutObservable<string> = ko.observable(null);
        grantDate: KnockoutObservable<Date> = ko.observable(null);
        deadline: KnockoutObservable<Date> = ko.observable(null);
        expirationStatus: KnockoutObservable<number> = ko.observable(null);
        grantDays: KnockoutObservable<number> = ko.observable(null);
        grantMinutes: KnockoutObservable<number> = ko.observable(null);
        usedDays: KnockoutObservable<number> = ko.observable(null);
        usedMinutes: KnockoutObservable<number> = ko.observable(null);
        remainingDays: KnockoutObservable<number> = ko.observable(null);
        remainingMinutes: KnockoutObservable<number> = ko.observable(null);
        constructor(param?: IAnnualLeaveGrantRemainingData) {
            let self = this;
            if (param) {
                self.annLeavId(param.annLeavId || null);
                self.grantDate(param.grantDate || null);
                self.deadline(param.deadline || null);
                self.expirationStatus(param.expirationStatus || 0);
                self.grantDays(param.grantDays || null);
                self.grantMinutes(param.grantMinutes || null);
                self.usedDays(param.usedDays || null);
                self.usedMinutes(param.usedMinutes || null);
                self.remainingDays(param.remainingDays || null);
                self.remainingMinutes(param.remainingMinutes || null);
            }
            // Subcribe grantDate
            self.grantDate.subscribe(value => {
                console.log(value);
                if (value && __viewContext.viewModel.createMode()) {
                    service.lostFocus(value).done((data: Date) => {
                        console.log(data);
                    });
                }
            });
        }
    }

    function formatDate(value, row) {
        if (value) {
            return value + '日';
        }
    }

    function formatEnum(value, row) {
        if (value && value === '0') {
            return '使用可能';
        } else if (value && value === '1') {
            return '期限切れ';
        }
    }

}