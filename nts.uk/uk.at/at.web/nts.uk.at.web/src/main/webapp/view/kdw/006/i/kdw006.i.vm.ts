module nts.uk.at.view.kdw006.i.viewmodel {
    let __viewContext: any = window["__viewContext"] || {};

    export class ScreenModelI extends ko.ViewModel {

        //huytodo temp root team B
        empRefRange: KnockoutObservableArray<any>;
        empRefRangeValue: KnockoutObservable<any>;
        time: KnockoutObservable<string>;
        text: KnockoutObservable<string>;
        color: KnockoutObservable<string>;
        checkBox: KnockoutObservable<boolean>;

        constructor() {
            super();
            let self = this;

            let empRefRangeTemp: any[] = [
                {value: 0, name: "test 1"},
                {value: 1, name: "test 2"},
            ];

            // _.forEach(__viewContext.enums.EmployeeReferenceRange, (item) => {
            //     empRefRangeTemp.push({value: item.value, name: item.name});
            // });
            self.empRefRange = ko.observableArray(empRefRangeTemp);

            self.empRefRangeValue = ko.observable(0);
            self.time = ko.observable("0:00");
            self.text = ko.observable("test");
            self.color = ko.observable("");
            self.checkBox = ko.observable(true);
        }

        start(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            self.$blockui("grayout");
            // $.when(self.start()).done(() => {
            //     dfd.resolve();
            // }).always(() => {
            //     nts.uk.ui.errors.clearAll();
            //     self.$blockui("hide");
            // });
            
            //temp
            setTimeout(() => {
                dfd.resolve(); //huytodo delete this
                nts.uk.ui.errors.clearAll();
                self.$blockui("hide");
            }, 1000);
            return dfd.promise();
        }

        jumpTo() {
            let self = this;
            nts.uk.request.jump("/view/kdw/006/a/index.xhtml");
        }

        register() {
            
        }
    }

    interface Example { 
        //huytodo delete this
    }
}