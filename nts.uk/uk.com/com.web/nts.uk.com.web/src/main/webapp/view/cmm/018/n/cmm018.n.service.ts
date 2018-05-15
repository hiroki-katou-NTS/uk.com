module nts.uk.com.view.cmm018.n {
    export module service {
        let servicePath = {
            getRightList: 'workflow/approvermanagement/workroot/find/applicationType',
            getInforRoot: "workflow/approvermanagement/workroot/getEmployeeRegisterApprovalRoot",
            saveAsExcel: "approval/report/employee"
        };

        export function getRightList() {
            return nts.uk.request.ajax('com', servicePath.getRightList);
        }

        export function getInforRoot(data: model.appInfor) {
            return nts.uk.request.ajax('com', servicePath.getInforRoot, data);
        }

        export function saveAsExcel(data: model.appInfor) {
            return request.exportFile(servicePath.saveAsExcel, data);
        }

        export module model {
            export class appInfor {
                baseDate: any;
                lstEmpIds: string[];
                rootAtr: number;
                lstApps: number[];
                constructor(baseDate: any, lstEmpIds: string[], rootAtr: number, lstApps: number[]) {
                    this.baseDate = baseDate;
                    this.lstEmpIds = lstEmpIds;
                    this.rootAtr = rootAtr;
                    this.lstApps = lstApps;
                }
            }
        }
    }
}
