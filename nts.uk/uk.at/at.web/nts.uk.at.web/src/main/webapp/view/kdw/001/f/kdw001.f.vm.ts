module nts.uk.at.view.kdw001.f {
    import getText = nts.uk.resource.getText;
    export module viewmodel {
        export class ScreenModel {
            enable: KnockoutObservable<boolean>;
            required: KnockoutObservable<boolean>;
            dateValue: KnockoutObservable<any>;
            startDateString: KnockoutObservable<any>;
            endDateString: KnockoutObservable<any>;
            //table
            columns: Array<any>;//nts.uk.ui.NtsGridListColumn
            currentSelectedRow: KnockoutObservable<any>;

            //InputEmpCalAndSumByDate
            inputEmpCalAndSumByDate: KnockoutObservable<model.InputEmpCalAndSumByDate>;
            //obj EmpCalAndSumExeLog 
            empCalAndSumExeLog: KnockoutObservableArray<model.EmpCalAndSumExeLog>;

            constructor() {
                let self = this;
                //
                self.enable = ko.observable(true);
                self.required = ko.observable(true);
                self.dateValue = ko.observable({});
                self.dateValue().startDate = moment.utc().subtract(1, "y").add(1, "d").format("YYYY/MM/DD");
                self.dateValue().endDate = moment.utc().format("YYYY/MM/DD");
                self.startDateString = ko.observable('');
                self.endDateString = ko.observable(new Date());
                //table
                self.currentSelectedRow = ko.observable(null);

                //inputEmpCalAndSumByDate (startDate and endDate)
                self.inputEmpCalAndSumByDate = ko.observable(
                    new model.InputEmpCalAndSumByDate(self.dateValue().startDate, self.dateValue().endDate));
                //obj EmpCalAndSumExeLog
                self.empCalAndSumExeLog = ko.observableArray([]);

                self.columns = [
                    { headerText: getText('KDW001_73'), key: 'executionDate', width: 100 },
                    { headerText: getText('KDW001_74'), key: 'empCalAndSumExecLogID', width: 120 },
                    { headerText: getText('KDW001_75'), key: 'caseSpecExeContentID', width: 100 },
                    { headerText: getText('KDW001_76'), key: 'processingMonthName', width: 150 },
                    { headerText: getText('KDW001_77'), key: 'executedMenuName', width: 200 },
                    { headerText: getText('KDW001_78'), key: 'executionStatusName', width: 160 },
                    {
                        headerText: getText('KDW001_79'), key: 'executionStatus', width: 100,
                        template: '<button class="open-dialog-i" data-id="${empCalAndSumExecLogID}">参照</button>',
                        columnCssClass: "colStyleButton",
                    }
                ];
            }

            /**
             * functiton start page
             */
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                //get all EmpCalAndSumExeLog by date
                let dfdAllEmpCalAndSumExeLog = self.getAllEmpCalAndSumExeLog(self.inputEmpCalAndSumByDate());

                $.when(dfdAllEmpCalAndSumExeLog).done((dfdAllEmpCalAndSumExeLogData) => {

                    dfd.resolve();
                });

                return dfd.promise();
            }//end start page

            /**
             * function get all EmpCalAndSumExeLog by startDate and endDate
             */
            getAllEmpCalAndSumExeLog(inputEmpCalAndSumByDate: model.InputEmpCalAndSumByDate) {
                let self = this;
                let dfd = $.Deferred<any>();
                service.getAllEmpCalAndSumExeLog(inputEmpCalAndSumByDate).done(function(data: Array<model.IEmpCalAndSumExeLog>) {
                    //_.sortBy(self.empCalAndSumExeLog(data), 'executionDate');
                    data = _.orderBy(data, ['executionDate'], ['desc']);
                    let temp = [];
                    _.each(data, (value) => {
                        temp.push(new model.EmpCalAndSumExeLog(value));
                    });
                    self.empCalAndSumExeLog(temp);
                    dfd.resolve(data);
                }).fail(function(res: any) {
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res.message).then(function() { nts.uk.ui.block.clear(); });
                });
                return dfd.promise();
            }//end function getAllEmpCalAndSumExeLog

            //button search
            search() {
                let self = this;
                self.inputEmpCalAndSumByDate(new model.InputEmpCalAndSumByDate(self.dateValue().startDate, self.dateValue().endDate));
                self.getAllEmpCalAndSumExeLog(self.inputEmpCalAndSumByDate());
            }

            //open dialog I
            openDialogI() {
                var empCalAndSumExecLogID = this.currentSelectedRow();
                nts.uk.ui.windows.setShared("openI", this.currentSelectedRow());
                nts.uk.ui.windows.sub.modal("/view/kdw/001/i/index.xhtml");
            }
        }//end screenModel
    }//end viewmodel

    //module model
    export module model {
        export interface IEmpCalAndSumExeLog {
            empCalAndSumExecLogID: string;
            processingMonth: number;
            processingMonthName: string;
            executedMenu: number;
            executedMenuName: string;
            executedMenuJapan: string;
            executionDate: string;
            executionStatus: number;
            executionStatusName : string;
            employeeID: string;
            closureID: number;
            caseSpecExeContentID: string;
            executionLogs: Array<IExecutionLog>;
        }

        export interface IExecutionLog {
            empCalAndSumExecLogID: string;
            executionContent: number;
            executionContentName :string;
            existenceError: number;
            executionTime: IExecutionTime;
            processStatus: number;
            objectPeriod: ObjectPeriod;
            calExecutionSetInfoID :string;
        }

        export interface IExecutionTime {
            startTime: string;
            endTime: string;
        }

        /**
         * class EmpCalAndSumExeLog
         */
        export class EmpCalAndSumExeLog {
            empCalAndSumExecLogID: string;
            processingMonth: number;
            processingMonthName: string;
            executedMenu: number;
            executedMenuName: string;
            executedMenuJapan: string;
            executionDate: string;
            executionStatus: number;
            executionStatusName : string;
            employeeID: string;
            closureID: number;
            caseSpecExeContentID: string;
            executionLogs: Array<ExecutionLog>;
            constructor(data: IEmpCalAndSumExeLog) {
                this.empCalAndSumExecLogID = data.empCalAndSumExecLogID;
                this.processingMonth = data.processingMonth;
                this.processingMonthName = data.processingMonth + "月度";
                this.executedMenu = data.executedMenu;
                if (data.executedMenu == 0) {
                    this.executedMenuName = "詳細実行";
                } else {
                    this.executedMenuName = "domain4";
                }

                if (data.executedMenu == 0) {
                    this.executedMenuJapan = "選択して実行";
                } else {
                    this.executedMenuJapan = "ケース別実行";
                }
                this.executionDate = data.executionDate;
                this.executionStatus = data.executionStatus;
                this.executionStatusName = data.executionStatusName;
                this.employeeID = data.employeeID;
                this.closureID = data.closureID;
                this.caseSpecExeContentID = data.caseSpecExeContentID;
                this.executionLogs = data.executionLogs;
            }

        }//end class EmpCalAndSumExeLog

        /**
         * class ExecutionLog
         */
        export class ExecutionLog {
            empCalAndSumExecLogID: string;
            executionContent: number;
            executionContentName :string;
            existenceError: number;
            executionTime: ExecutionTime;
            processStatus: number;
            objectPeriod: ObjectPeriod;
            calExecutionSetInfoID :string;
            
            constructor(data: IExecutionLog) {
                this.empCalAndSumExecLogID = data.empCalAndSumExecLogID;
                this.executionContent = data.executionContent;
                this.executionContentName = data.executionContentName;   
                this.existenceError = data.existenceError;
                this.executionTime = new ExecutionTime(data.executionTime);
                this.processStatus = data.processStatus;
                this.objectPeriod = data.objectPeriod;
                this.calExecutionSetInfoID = data.calExecutionSetInfoID;
            }
        }//end class ExecutionLog
        /**
         * class ExecutionTime
         */
        export class ExecutionTime {
            startTime: string;
            endTime: string;
            timeSpan: string;
            constructor(data: IExecutionTime) {
                var momentStart = moment.utc(data.startTime, "YYYY/MM/DD HH:mm:ss");
                var momentEnd = moment.utc(data.endTime, "YYYY/MM/DD HH:mm:ss");
                
                this.startTime = momentStart.format("YYYY/MM/DD HH:mm:ss");
                this.endTime = momentEnd.format("YYYY/MM/DD HH:mm:ss");
                var ms = moment(momentEnd,"DD/MM/YYYY HH:mm:ss").diff(moment(momentStart,"DD/MM/YYYY HH:mm:ss"));
                var d = moment.duration(ms);
                this.timeSpan = Math.floor(d.asHours()) + moment.utc(ms).format(":mm:ss");
            }
        }//end class ExecutionTime

        /**
         * class CalExeSettingInfor
         */
        export class CalExeSettingInfor {
            executionContent: number;
            executionType: number;
            executionTypeName : String
            constructor(executionContent: number, executionType: number,executionTypeName : String) {
                this.executionContent = executionContent;
                this.executionType = executionType;
                this.executionTypeName = executionTypeName;
            }//end class ExecutionTime
        }//end class CalExeSettingInfor

        /**
         * class ObjectPeriod
         */
        export class ObjectPeriod {
            startDate: string;
            endDate: string;
            constructor(startDate: string, endDate: string) {
                this.startDate = moment.utc(startDate, "YYYY/MM/DD").toISOString();
                this.endDate = moment.utc(endDate, "YYYY/MM/DD").toISOString();
            }
        }//end class ObjectPeriod

        /**
         * class InputEmpCalAndSumByDate 
         */
        export class InputEmpCalAndSumByDate {
            startDate: string;
            endDate: string;
            constructor(startDate: string, endDate: string) {
                this.startDate = moment.utc(startDate, "YYYY/MM/DD").toISOString();
                this.endDate = moment.utc(endDate, "YYYY/MM/DD").toISOString();
            }
        }//end class InputEmpCalAndSumByDate

    }//end module model

}//end module