module nts.uk.com.view.cmm018.m {
    export module service {
        // Service paths.
        var servicePath = {
            searchModeEmployee: "workflow/approvermanagement/workroot/testMasterDat",
            saveAsExcel: "approval/report/masterData"

        }
        export function searchModeEmployee(data: MasterApproverRootQuery) {
            return request.ajax('com', servicePath.searchModeEmployee, data);
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
            constructor(baseDate: Date, chkCompany: boolean, chkWorkplace: boolean, chkPerson: boolean, sysAtr: number) {
                this.baseDate = baseDate;
                this.chkCompany = chkCompany;
                this.chkWorkplace = chkWorkplace;
                this.chkPerson = chkPerson;
                this.sysAtr = sysAtr;
            }
        }
    }


}


function BreadCrumb(str: string) {
    let items = str.split(','),
        html: string = '';
    for (let i in items) {
        html += "<span>" + items[i] + "</span>";
    }
    
    return html;
}
