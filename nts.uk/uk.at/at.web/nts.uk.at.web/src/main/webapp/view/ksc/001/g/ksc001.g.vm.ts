module nts.uk.at.view.ksc001.g {
    import blockUI = nts.uk.ui.block;
    export module viewmodel {
        export class ScreenModel {
            enable: KnockoutObservable<boolean>;
            required: KnockoutObservable<boolean>;
            dateValue: KnockoutObservable<any>;
            startDateString: KnockoutObservable<string>;
            endDateString: KnockoutObservable<string>;
            items: KnockoutObservableArray<GridItem>;
            constructor() {
                let self = this;
                self.enable = ko.observable(true);
                self.required = ko.observable(true);

                self.startDateString = ko.observable("");
                self.endDateString = ko.observable("");
                self.dateValue = ko.observable({
                    //get previous 1 year
                    startDate: moment().subtract(1, 'years').format("YYYY/MM/DD"),
                    endDate: moment().format("YYYY/MM/DD"),
                });

                self.startDateString.subscribe(function(value) {
                    self.dateValue().startDate = value;
                    self.dateValue.valueHasMutated();
                });

                self.endDateString.subscribe(function(value) {
                    self.dateValue().endDate = value;
                    self.dateValue.valueHasMutated();
                });
//                let list: Array<GridItem> = [];
//                for (var i = 0; i < 50; i++) {
//                    list.push(new GridItem(i));
//                }
                self.items = ko.observableArray([]);
            }
            /**
             * get data on start page
             */
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                service.findExecutionList({ startDate: new Date(self.dateValue().startDate), endDate: new Date(self.dateValue().endDate) }).done(function(data: any) {
                    self.pushDataToList(data);
                    dfd.resolve();
                });
                return dfd.promise();
            }
            
            pushDataToList(data: Array<any>) {
                var self = this;
                data.forEach(function(item, index) {
                    self.items.push(new GridItem(moment(item.executionDateTime.executionStartDate).format('YYYY/MM/DD').toString(), item.employeeCode, item.employeeName, item.period, item.completionStatus,item.executionId));
                });
            }
            
//            covertToRangeString(data:any):string{
//                return ;
//            }
            /**
             * request to create creation screen
             */
            openDialog(): void {
                let self = this;
                blockUI.invisible();
                // the default value of categorySet = undefined
                //nts.uk.ui.windows.setShared('', );
                nts.uk.ui.windows.sub.modal("/view/ksc/001/h/index.xhtml", { dialogClass: "no-close" }).onClosed(() => {
                });
                blockUI.clear();
            }
        }

        export class GridItem {
            id: string;
            exeDay: string;
            exeEmployeeCode: string;
            exeEmployeeName: string;
            targetPeriod: string;
            status: string;
            exeId: string;
            
            constructor(exeDay: any, exeEmployeeCode: string, exeEmployeeName: string, targetPeriod: any, status: string, exeId: string) {
                this.id = exeId;
                this.exeDay = exeDay;
                this.exeEmployeeCode = exeEmployeeCode;
                this.exeEmployeeName = exeEmployeeName;
                this.targetPeriod = nts.uk.resource.getText("KSC001_46", [targetPeriod.startDate, targetPeriod.endDate]);
                this.status = status;
                this.exeId = exeId;
            }
        }
    }
}