module cmm014.a.viewmodel {

    export class ScreenModel {
        classificationCode: KnockoutObservable<string>;
        classificationName: KnockoutObservable<string>;
        classificationMemo: KnockoutObservable<string>;
        classificationList: KnockoutObservableArray<Classification>;
        selectedClassificationCode: any;

        constructor() {
            var self = this;
            this.classificationCode = ko.observable("");
            this.classificationName = ko.observable("");
            this.classificationMemo = ko.observable("");
            this.classificationList = ko.observableArray([]);
            this.selectedClassificationCode = ko.observable(null);
        }

        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();
            dfd.resolve();
            return dfd.promise();
             }
    }

    class Classification {
        classificationCode: string;
        classificationName: string;
        classificationMemo: string;
        constructor(classificationCode: string, classificationName: string) {
            this.classificationCode = classificationCode;
            this.classificationName = classificationName;
        }
    }
}