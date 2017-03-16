module nts.uk.pr.view.qpp007.b {
    export module service {
        /**
         *  Service paths
         */
        var paths: any = {
            saveSalaryPrintSetting: "ctx/pr/report/salarydetail/printsetting/save",
            findSalaryPrintSetting: "ctx/pr/report/salarydetail/printsetting/find"
        };

        /**
         *  Save SalaryPrintSetting.
         */
        export function saveSalaryPrintSetting(data: model.SalaryPrintSettingDto): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            nts.uk.request.ajax(paths.saveSalaryPrintSetting, data).done(() =>
                dfd.resolve());
            return dfd.promise();
        }

        /**
         *  Load SalaryPrintSetting.
         */
        export function findSalaryPrintSetting(): JQueryPromise<model.SalaryPrintSettingDto> {
            var dfd = $.Deferred<Array<any>>();
            nts.uk.request.ajax(paths.findSalaryPrintSetting)
                .done(res => {
                    dfd.resolve(res);
                })
                .fail(res => {
                    dfd.reject(res);
                })
            return dfd.promise();
        }

        /**
        * Model namespace.
        */
        export module model {

            export interface SalaryPrintSettingDto {
                outputDistinction: string;
                showDepartmentMonthlyAmount: boolean;
                showDetail: boolean;
                showDivisionMonthlyTotal: boolean;
                showDivisionTotal: boolean;
                showHierarchy1: boolean;
                showHierarchy2: boolean;
                showHierarchy3: boolean;
                showHierarchy4: boolean;
                showHierarchy5: boolean;
                showHierarchy6: boolean;
                showHierarchy7: boolean;
                showHierarchy8: boolean;
                showHierarchy9: boolean;
                showHierarchyAccumulation: boolean;
                showHierarchyMonthlyAccumulation: boolean;
                showMonthlyAmount: boolean;
                showPersonalMonthlyAmount: boolean;
                showPersonalTotal: boolean;
                showSectionalCalculation: boolean;
                showTotal: boolean;
            }

        }
    }
}