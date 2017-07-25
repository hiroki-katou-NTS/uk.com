module nts.uk.pr.view.ksu006.c {
    export module service {
        /**
         *  Service paths
         */
        var paths: any = {
//            updateAcquisitionRule: 'ctx/at/share/vacation/setting/acquisitionrule/update',
        };
        
//        export function updateAcquisitionRule(command: any): JQueryPromise<any> {
//            return nts.uk.request.ajax(paths.updateAcquisitionRule, command);
//        }

        export module model {
            
            export interface ExternalBudgetLogModel {
                executeId: string;
                startDate: Date;
                endDate: Date;
                target: string;
                fileName: string;
                status: string;
                numberSuccess: number;
                numberFail: number;
                download: string;
            }
        }

    }
}