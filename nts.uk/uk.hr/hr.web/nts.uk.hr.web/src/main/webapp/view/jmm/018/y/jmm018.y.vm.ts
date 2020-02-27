module nts.uk.com.view.jmm018.y.viewmodel {
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;
    import error = nts.uk.ui.dialog.error;
    import info = nts.uk.ui.dialog.info;
    
    export class ScreenModel {
        departmentList: KnockoutObservableArray<any>;
        departmentCode: KnockoutObservable<string>;
        columns: KnockoutObservableArray<any>;
        columns2: KnockoutObservableArray<any>;
        check: KnockoutObservable<boolean>;
        searchBoxSelected: KnockoutObservable<string>;
        
        key: KnockoutObservable<boolean>;
        employeeList: KnockoutObservableArray<any>;
        selectedCode: KnockoutObservable<string>;
        constructor() {
            let self = this;
            self.departmentList = ko.observableArray([]);
            self.departmentCode = ko.observable('');
            self.check = ko.observable(true);
            self.searchBoxSelected = ko.observable('');
            self.columns = ko.observableArray([
                { headerText: '', key: 'departmentId', dataType: "string", hidden: true },
                { headerText: getText('JMM018_Y2_4'), key: 'name', width: '100%', dataType: "string" }
            ]);
            
            self.key = ko.observable('');
            self.employeeList = ko.observableArray([]);
            self.columns2 = ko.observableArray([
                { headerText: '', key: 'sid', dataType: "string", hidden: true },
                { headerText: getText('JMM018_Y2_4'), key: 'employeeCode', dataType: "string", width: '30%', },
                { headerText: getText('JMM018_Y2_4'), key: 'employeeName', width: '70%', dataType: "string" }
            ]);
            self.selectedCode = ko.observable('');
            self.departmentCode.subscribe(function(val){
                if(val != null){
                    self.findByDepartmentId();    
                }
            });
        }
        
        startPage(): JQueryPromise<any> {
            let self = this,
            dfd = $.Deferred();
            block.grayout();
            new service.start().done(function(data: any) {
                console.log(data);
                let tg = [];
                _.forEach(data.departmentInforList, (item) => {
                    tg.push(new Department(item));
                });
                self.departmentList(tg);
                self.departmentCode(data.departmentIdSelect);
            }).fail(function(err) {
                error({ messageId: err.messageId });
            }).always(function() {
                block.clear();
                dfd.resolve();
            });
            return dfd.promise();
        }

        findByDepartmentId(): void{
            let self = this;
            let param = {departmentId: self.departmentCode(), checks: true};
            block.grayout();
            new service.getEmployeeByDepartmentId(param).done(function(data: any) {
                console.log(data);
                self.employeeList(data);
            }).fail(function(err) {
                error({ messageId: err.messageId });
            }).always(function() {
                block.clear();
                dfd.resolve();
            });
        }
        
        search(): void{
            let self = this;
            let param = {key: self.key()};
            block.grayout();
            new service.searchEmployeeBykey(param).done(function(data: any) {
                console.log(data);
                self.employeeList(data);
            }).fail(function(err) {
                error({ messageId: err.messageId });
            }).always(function() {
                block.clear();
            });
        }

        decision(): void {
            nts.uk.ui.windows.setShared("shareToJMM018Z", undefined);
            nts.uk.ui.windows.close();
        }

        close(): void {
            nts.uk.ui.windows.setShared("shareToJMM018Z", undefined);
            nts.uk.ui.windows.close();
        }

    }
    
    class Department{
        departmentId: string;
        name: string;
        childs: any;
        constructor(param: any){
            this.departmentId = param.departmentId;
            this.name = param.departmentCode + ' ' + param.departmentName;
            this.childs = param.childs;
        }
    }
}