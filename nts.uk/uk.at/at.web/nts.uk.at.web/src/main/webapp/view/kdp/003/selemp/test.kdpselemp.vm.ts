module nts.uk.at.view.kdpselemp.viewmodel {
    export class ScreenModel {
        input: any;
        constructor() {
            var self = this;
            self.input = {};
        }
        
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }
        public seletedEmployee(data): void{
            console.log(data);
        }
    }
 
}
