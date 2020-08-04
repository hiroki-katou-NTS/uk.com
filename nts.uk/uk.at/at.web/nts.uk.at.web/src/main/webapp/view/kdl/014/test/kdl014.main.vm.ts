module kdl014.main.viewmodel {
    import modal = nts.uk.ui.windows.sub.modal;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    export class ScreenModel {
        //Param for A sub-screen
        startDateA: KnockoutObservable<string>;
        endDateA: KnockoutObservable<string>;
        employeeCode: KnockoutObservable<string>;
        
        //Param for B sub-screen
        startDateB: KnockoutObservable<string>;
        endDateB: KnockoutObservable<string>;
        listEmployee: KnockoutObservable<string>;

        constructor() {
            var self = this;
            //Init param for A sub-screen.
            self.startDateA = ko.observable('');
            self.endDateA = ko.observable('');
            self.employeeCode = ko.observable('');
            
            //Init param for B sub-screen.
            self.startDateB = ko.observable('');
            self.endDateB = ko.observable('');
            self.listEmployee = ko.observableArray([]);
        };

        openKDL014A() {
            var self = this;
            modal('/view/kdl/014/a/index.xhtml', { title: '打刻参照', });

            //Send 3 param for A sub-screen.
            setShared("KDL014A_PARAM", {
                startDate: self.startDateA(),
                endDate: self.endDateA(),
                employeeID: self.employeeCode()
            });
        };

        openKDL014B() {
            var self = this;
            modal('/view/kdl/014/b/index.xhtml', { title: '打刻参照B', });
            let arrEmpCode: Array<string> = [];
            arrEmpCode = _.split(self.listEmployee(), ',');

            //Send 3 param for B sub-screen.
            setShared("KDL014B_PARAM", {
                startDate: self.startDateB(),
                endDate: self.endDateB(),
                lstEmployee: arrEmpCode
            });
        }
    }
}