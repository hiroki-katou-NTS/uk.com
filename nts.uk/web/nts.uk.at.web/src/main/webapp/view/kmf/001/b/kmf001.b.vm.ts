module nts.uk.pr.view.kmf001.b {
    export module viewmodel {
        import EnumertionModel = service.model.EnumerationModel;
        export class ScreenModel {
            listAcquisitionDto: KnockoutObservableArray<ListAcquisitionModel>;
            priority: KnockoutObservableArray<EnumertionModel>;
            selectedPriority: KnockoutObservable<number>;
            enableInputPriority: KnockoutObservable<boolean>;
            annualPaidLeave: KnockoutObservable<number>;
            compensatoryDayOff: KnockoutObservable<number>;
            substituteHoliday: KnockoutObservable<number>;
            fundedPaidHoliday: KnockoutObservable<number>;
            exsessHoliday: KnockoutObservable<number>;
            specialHoliday: KnockoutObservable<number>;

            constructor() {
                let self = this;
                self.priority = ko.observableArray([
                    { value: 0, name: "設定する" },
                    { value: 1, name: "設定しない" }
                ]);
                self.selectedPriority = ko.observable(0);
                self.enableInputPriority = ko.computed(function() {
                    return self.selectedPriority() == 0;
                }, self);
                self.annualPaidLeave = ko.observable(null);
                self.compensatoryDayOff = ko.observable(null);
                self.substituteHoliday = ko.observable(null);
                self.fundedPaidHoliday = ko.observable(null);
                self.exsessHoliday = ko.observable(null);
                self.specialHoliday = ko.observable(null);
            }
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<void>();
                $.when(self.loadAcquisitionRule()).done(function(res) {
                    if (res) {
                        self.initUI(res);
                    }
                    dfd.resolve();
                });
                return dfd.promise();
            }
            private setList(): void {
                let self = this;
                let command: any = {}; 
                command.settingClassification =  self.selectedPriority();             
                let acquisitionOrderList: Array<any>;
                let acquisition: any = {};
                acquisition.vacationType = 1;
                acquisition.priority = self.annualPaidLeave();
                acquisitionOrderList.push(acquisition);
                acquisition.vacationType = 2;
                acquisition.priority = self.compensatoryDayOff();
                acquisitionOrderList.push(acquisition);
                acquisition.vacationType = 3;
                acquisition.priority = self.substituteHoliday();
                acquisitionOrderList.push(acquisition);
                acquisition.vacationType = 4;
                acquisition.priority = self.annualPaidLeave();
                acquisitionOrderList.push(acquisition);
                acquisition.vacationType = 5;
                acquisition.priority = self.exsessHoliday();
                acquisitionOrderList.push(acquisition);
                acquisition.vacationType = 6;
                acquisition.priority = self.specialHoliday();
                acquisitionOrderList.push(acquisition);
                
                command.vaAcRule = acquisitionOrderList;
                return command;
            }
            private loadAcquisitionRule(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                service.findAcquisitionRule().done(function(res: any) {
                    if (res) {
                        self.initUI(res);
                    }
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });
                return dfd.promise();
            }
            private closeCheck(): void {
                var self = this;
                self.closeDialog();
            }
            private update(): void {
                let self = this;
                let dfd = $.Deferred();
                if (!self.validate()) {
                    return;
                }
                let command = self.setList();
                service.updateAcquisitionRule(command).done(function() {
                    self.loadAcquisitionRule().done(function(res) {
                        // Msg_15
                        nts.uk.ui.dialog.alert("年休設定の登録");
                        dfd.resolve();
                    });
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });
            }
 
            /**
             * Close dialog.
             */
            private closeDialog(): void {
                nts.uk.ui.windows.close();
            }
            private initUI(res: any): any {
                let self = this;
                self.selectedPriority(res.settingClass);
                res.vaAcOrders.forEach(item => {
                    if (item.vacationType == 1) {
                        self.annualPaidLeave(item.priority);
                    }
                    if (item.vacationType == 2) {
                        self.compensatoryDayOff(item.priority);
                    }
                    if (item.vacationType == 3) {
                        self.substituteHoliday(item.priority);
                    }
                    if (item.vacationType == 4) {
                        self.fundedPaidHoliday(item.priority);
                    }
                    if (item.vacationType == 5) {
                        self.exsessHoliday(item.priority);
                    }
                    if (item.vacationType == 6) {
                        self.specialHoliday(item.priority);
                    }
                });
            }
        }
        export class AcquisitionOrder {
            vacationType: number;
            priority: number;
        }
    }
}