module nts.uk.com.view.cps013.b.viewmodel {

    export class ScreenModel {
        executionTotal: KnockoutObservable<string>;
        executionError: KnockoutObservable<string>;
        executionStartDate: string;
        executionEndDate: KnockoutObservable<string> = ko.observable("");
        periodInfo: string;
        finish: KnockoutObservable<boolean> = ko.observable(true);
        error: KnockoutObservable<boolean> = ko.observable(false);
        errorLogs: KnockoutObservableArray<any> = ko.observableArray([]);
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any> = ko.observable();
        firstTime:  KnockoutObservable<boolean> = ko.observable(true);
        constructor() {
            let self = this;
            self.columns = ko.observableArray([
                { headerText: '', key: 'noID', hidden: true },
                { headerText: nts.uk.resource.getText("KSC001_59"), key: 'stt', width: 50 },
                { headerText: nts.uk.resource.getText("CPS013_26"), key: 'employeeCode', width: 150 },
                { headerText: nts.uk.resource.getText("CPS013_27"), key: 'employeeName', width: 200 },
                { headerText: nts.uk.resource.getText("CPS013_28"), key: 'check', width: 200 },
                { headerText: nts.uk.resource.getText("CPS013_29"), key: 'category', width: 200 },
                { headerText: nts.uk.resource.getText("CPS013_30"), key: 'error', width: 400, formatter: makeIcon},
            ]);
        }

        /** get data to list **/
        getData(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
                dfd.resolve();
                dfd.reject();
            return dfd.promise();
        }

        /** get data when start dialog **/
        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            let self = this;
//            let inputData: ScheduleExecutionLogSaveRespone = nts.uk.ui.windows.getShared('inputData');
//            if (inputData) {
//                service.findScheduleExecutionLogById(inputData.executionId).done(function(data) {
//                    self.scheduleExecutionLogModel.updateStatus(data.completionStatus);
                    self.executionTotal = ko.observable('0');
                    self.executionError = ko.observable('0');
                    self.executionStartDate = moment(new Date()).format("YYYY/MM/DD HH:mm:ss");
                    $('.countdown').startCount();
                    let param : ItemDto = {
                        noID: 1,
                        stt: '1',
                        employeeCode: 'A000001',
                        employeeName: 'yen',
                        check: 'checkornotcheck',
                        category: '0909999',
                        error: 'uuuuuuuuuuuuuuuuuuuuuuuuu',    
                    }
                    self.errorLogs().push(new item(param));
                    
                    if(self.error()){
                        nts.uk.ui.windows.getSelf().setHeight(730);
                        nts.uk.ui.windows.getSelf().setWidth(1250);
                    }
//                    self.periodInfo = nts.uk.resource.getText("KSC001_46",
//                        [data.period.startDate,
//                        data.period.endDate])
//                    self.inputData = inputData;
                    dfd.resolve();
//                });
//            }
            return dfd.promise();
        }
        
        stop() {
            let self = this;
            if(self.firstTime()){
                $('.countdown').stopCount();
                self.executionEndDate(moment(new Date()).format("YYYY/MM/DD HH:mm:ss"));
                self.firstTime(false);
            }
        }

        public cancel(): void {
            nts.uk.ui.windows.close();
        }
        
    }
    
    export interface ItemDto {
        noID: number;
        stt: string;
        employeeCode: string;
        employeeName: string;
        check: string;
        category: string;
        error: string;
    }
    
    export class item{
        noID: number;
        stt: string;
        employeeCode: string;
        employeeName: string;
        check: string;
        category: string;
        error: string;
        constructor(item: ItemDto) {
            this.noID = item.noID;
            this.stt = item.stt;
            this.employeeCode = item.employeeCode;
            this.employeeName = item.employeeName;
            this.check = item.check;
            this.category = item.category;
            this.error = item.error;
        }
    }
    
    function makeIcon(value, row) {
        if (value == '1')
            return '<img style="margin-left: 15px; width: 20px; height: 20px;" />';
        return '<div style="display: inline-block">' + '<div class="limit-custom">'+ value +'</div>' + '<button tabindex = "0" class="open-dialog-i">'+ nts.uk.resource.getText("CPS013_31")+'</button>'+ '</div>';
    }
    
       // Object truyen tu man A sang
    interface IDataShare {
            perInfoCheck: boolean,
            masterCheck: boolean,
            scheduleMngCheck: boolean,
            dailyPerforMngCheckL: boolean ,
            monthPerforMngCheck: boolean ,
            payRollMngCheck: boolean ,
            bonusMngCheck: boolean ,
            yearlyMngCheck: boolean ,
            monthCalCheck: boolean
    }
}




