module nts.uk.com.view.cmm018.m {
    export module service {
        // Service paths.
        var servicePath = {
            saveAsExcel: "approval/report/masterData"

        }

        export function saveAsExcel(data: MasterApproverRootQuery) {
            return request.exportFile(servicePath.saveAsExcel, data);
        }

        export class MasterApproverRootQuery {
            baseDate: Date;
            chkCompany: boolean;
            chkWorkplace: boolean;
            chkPerson: boolean;
            sysAtr: number;
            lstAppName: Array<any>;
            constructor(baseDate: Date, chkCompany: boolean, chkWorkplace: boolean, chkPerson: boolean,
                sysAtr: number, lstAppName: Array<any>) {
                this.baseDate = baseDate;
                this.chkCompany = chkCompany;
                this.chkWorkplace = chkWorkplace;
                this.chkPerson = chkPerson;
                this.sysAtr = sysAtr;
                this.lstAppName = lstAppName;
            }
        }
    }


}
