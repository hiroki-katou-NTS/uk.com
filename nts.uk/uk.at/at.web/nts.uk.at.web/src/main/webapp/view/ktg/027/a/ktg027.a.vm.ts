module nts.uk.at.view.ktg027.a.viewmodel {

    export class ScreenModel {
        /**YM Picker **/
        yearMonth: KnockoutObservable<number>;
        cssRangerYM = ko.observable({});
        /**ComboBox**/
        itemList: KnockoutObservableArray<ItemModel>;
        selectedCBB: KnockoutObservable<string>;
        inforOvertime: KnockoutObservableArray<InforOvertime>;
        constructor() {
            var self = this;

            self.yearMonth = ko.observable(200002);
            self.itemList = ko.observableArray([
                new ItemModel('1', '基本給'),
                new ItemModel('2', '役職手当'),
                new ItemModel('3', '基本給ながい文字列ながい文字列ながい文字列')
            ]);
            this.selectedCBB = ko.observable('1');
            var inforOvertime: Array<InforOvertime> = [];

            self.inforOvertime = ko.observableArray([
            { employee: 'emp1', timeLimit: 1 ,actualTime: 1 ,applicationTime : 1 , totalTime: 1},
            { employee: 'emp2', timeLimit: 2,actualTime: 2, applicationTime : 2 ,totalTime: 2},
            { employee: 'emp3', timeLimit: 3 ,actualTime: 3 , applicationTime : 3 ,totalTime:3 }
            ]);
        }

        startPage(): JQueryPromise<any> {
            var self = this;

            var dfd = $.Deferred();

            dfd.resolve();
            // Init Fixed Table
            $("#fixed-table").ntsFixedTable({ height: 300, width: 600 });
            return dfd.promise();
        }
    }
    class ItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
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
