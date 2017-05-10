module nts.uk.pr.view.qpp007.a {
    
    export module service {

        // Service paths.
        var servicePath = {
            findAllSalaryOutputSetting: "ctx/pr/report/salary/outputsetting/findall",
            saveAsPdf: "screen/pr/QPP007/saveAsPdf"
        };
        /**
         * get All Output Setting
         */
        export function findAllSalaryOutputSetting(): JQueryPromise<model.SalaryOutputSettingHeaderDto[]> {
            return nts.uk.request.ajax(servicePath.findAllSalaryOutputSetting);
        }

        export function saveAsPdf(command: any): JQueryPromise<any> {
            let dfd = $.Deferred<void>();
            nts.uk.request.exportFile(servicePath.saveAsPdf, command).done(function() {
                    dfd.resolve();
                }).fail(function(res) {
                    dfd.reject();
                });
            return dfd.promise();
        }

        export module model {
            export class SalaryOutputSettingHeaderDto {
                code: string;
                name: string;
            }
        }

    }
}
