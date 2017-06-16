module kdl014.main.viewmodel {
    export class ScreenModel {
        startDateA: KnockoutObservable<string>;
        startDateB: KnockoutObservable<string>;
        endDateA: KnockoutObservable<string>;
        endDateB: KnockoutObservable<string>;
        employeeCode: KnockoutObservable<string>;
        listEmployee: KnockoutObservable<string>;
        constructor() {
            var self = this;
            self.startDateA = ko.observable('');
            self.startDateB = ko.observable('');
            self.endDateA = ko.observable('');
            self.endDateB = ko.observable('');
            self.employeeCode = ko.observable('');
            self.listEmployee = ko.observableArray([]);
        };
        openKDL014A() {
            var self = this;
            console.log(self.startDateA());
            nts.uk.ui.windows.sub.modal('/view/kdl/014/a/index.xhtml', { title: '打刻参照', });
            nts.uk.ui.windows.setShared("kdl014startDateA", self.startDateA());
            nts.uk.ui.windows.setShared("kdl014endDateA", self.endDateA());
            nts.uk.ui.windows.setShared("kdl014employeeCodeA", self.employeeCode());
        };
        openKDL014B() {
            var self = this;
            nts.uk.ui.windows.sub.modal('/view/kdl/014/b/index.xhtml', { title: '打刻参照B', });
            nts.uk.ui.windows.setShared("kdl014startDateB", self.startDateB());
            nts.uk.ui.windows.setShared("kdl014endDateB", self.endDateB());
            let arrEmpCode : Array<string> =[];
            arrEmpCode = _.split(self.listEmployee(),',');
            nts.uk.ui.windows.setShared("kdl014lstEmployeeB", arrEmpCode);
        }
    }
}