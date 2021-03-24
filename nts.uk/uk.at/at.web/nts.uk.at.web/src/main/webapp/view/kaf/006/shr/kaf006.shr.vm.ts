module nts.uk.at.view.kaf006.shr.viewmodel {
    import modal = nts.uk.ui.windows.sub.modal;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;

    export class Kaf006ShrViewModel extends ko.ViewModel {
        create() {

        }

        mounted() {

        }

        public static openDialogKDL035(params: any, vm: any) {
            console.log("Open KDL035");

            let linkingDates: any[] = [];
            setShared("KDL035_PARAMS", params);
            modal("/view/kdl/035/a/index.xhtml").onClosed(() => {
                // get List<振休振出紐付け管理> from KDL035
                linkingDates = getShared('KDL035_RESULT');
                if (linkingDates) {
                    vm.payoutSubofHDManagements(linkingDates);
                } 
                // else {
                //     vm.payoutSubofHDManagements([]);
                // }
            });

        }

        public static openDialogKDL036(params: any, vm: any) {
            console.log("Open KDL036");

            let listParam: any[] = [];
            setShared("KDL036_PARAMS", params)
            modal("/view/kdl/036/a/index.xhtml").onClosed(() => {
            listParam = getShared("KDL036_RESULT");
            if (listParam) {
                vm.leaveComDayOffManas(listParam);
            } 
            // else {
            //     vm.leaveComDayOffManas([]);
            // }
          });

        }
    }

    export class WorkType {
        workTypeCode: string;
        name: string;
        
        constructor(iWorkType: IWorkType) {
            this.workTypeCode = iWorkType.workTypeCode;
            this.name = iWorkType.name;
        }
    }

    export interface IWorkType {
        workTypeCode: string;
        name: string;
    }

    export class ApplicationDto {
        version: number;
        appId: string;
        prePostAtr: number;
        employeeID: string;
        appType: number;
        appDate: string;
        enteredPerson: string;
        inputDate: string;
        reflectionStatus;
        opStampRequestMode: number;
        opReversionReason: string;
        opAppStartDate: string;
        opAppEndDate: string;
        opAppReason: string;
        opAppStandardReasonCD: number;

        constructor(version, appId, prePostAtr, employeeID, appType, appDate, enteredPerson,
            inputDate, reflectionStatus, opStampRequestMode, opReversionReason, opAppStartDate,
            opAppEndDate, opAppReason, opAppStandardReasonCD) {
                this.version = version;
                this.appId = appId;
                this.prePostAtr = prePostAtr;
                this.employeeID = employeeID;
                this.appType = appType;
                this.appDate = appDate;
                this.enteredPerson = enteredPerson;
                this.inputDate = inputDate;
                this.reflectionStatus = reflectionStatus;
                this.opStampRequestMode = opStampRequestMode;
                this.opReversionReason = opReversionReason;
                this.opAppStartDate = opAppStartDate;
                this.opAppEndDate = opAppEndDate;
                this.opAppReason = opAppReason;
                this.opAppStandardReasonCD = opAppStandardReasonCD
        }
    }
}