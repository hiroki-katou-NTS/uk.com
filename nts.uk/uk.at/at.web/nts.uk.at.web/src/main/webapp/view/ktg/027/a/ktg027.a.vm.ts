module nts.uk.at.view.ktg027.a.viewmodel {

    export class ScreenModel {
        /**YM Picker **/
        targetMonth: KnockoutObservable<string>;
        cssRangerYM = ko.observable({});
        /**ComboBox**/
        selectedClosureID: KnockoutObservable<string>;
        inforOvertime: KnockoutObservableArray<InforOvertime>;
        closureResultModel : KnockoutObservableArray<ClosureResultModel> = ko.observableArray([]);
        constructor() {
            var self = this;
            var today = moment();
            var month = today.month() + 1;
            var year = today.year();
            var targetMonth = year + "" + month
            self.targetMonth = ko.observable(201812);
            this.selectedClosureID = ko.observable('1');
            var inforOvertime: Array<InforOvertime> = [];

            self.inforOvertime = ko.observableArray([
                { employee: 'emp1', timeLimit: 1, actualTime: 1, applicationTime: 1, totalTime: 1 },
                { employee: 'emp2', timeLimit: 2, actualTime: 2, applicationTime: 2, totalTime: 2 },
                { employee: 'emp3', timeLimit: 3, actualTime: 3, applicationTime: 3, totalTime: 3 }
            ]);
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            service.getListClosure().done((closureResultModel) => {
               self.closureResultModel(closureResultModel); 
               dfd.resolve();
           });
             service.getOvertimeHours(self.targetMonth()).done((data) => {
               console.log(data); 
               dfd.resolve();
           });
            // Init Fixed Table
            $("#fixed-table").ntsFixedTable({ height: 300, width: 600 });
            return dfd.promise();
        }
        
        clickExtractionBtn(){
            var self = this;
            var dfd = $.Deferred();
            service.buttonPressingProcess(self.targetMonth(), self.selectedClosureID()).done((data : any) =>{
                console.log(data)})
            }

    }
    class ClosureResultModel {
        closureId: string;
        closureName: string;

        constructor(closureId: string, closureName: string) {
            this.closureId = closureId;
            this.closureName = closureName;
        }
    }
    //define
    export class InforOvertime {
        employee: string;
        timeLimit: number;
        actualTime: number;
        applicationTime: number;
        totalTime: number;
        constructor(employee: string, timeLimit: number, actualTime: number, applicationTime: number, totalTime: number) {
            this.employee = employee;
            this.timeLimit = timeLimit;
            this.actualTime = actualTime;
            this.applicationTime = applicationTime;
            this.totalTime = actualTime + totalTime;
        }
    }
}
