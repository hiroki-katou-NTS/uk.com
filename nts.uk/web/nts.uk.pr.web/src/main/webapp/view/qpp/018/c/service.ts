module nts.uk.pr.view.qpp018.c {
    export module service {
        // Service paths.
        var paths: any = {
            findCheckListPrintSetting: "ctx/pr/report/insurance/checklist/find",
            saveCheckListPrintSetting: "ctx/pr/report/insurance/checklist/save"
        };
        /**
         * get All CheckList Print Setting
         */
        export function findCheckListPrintSetting(): JQueryPromise<model.CheckListPrintSettingDto> {
            var dfd = $.Deferred<model.CheckListPrintSettingDto>();
            nts.uk.request.ajax(paths.findCheckListPrintSetting)
                .done(function(res: model.CheckListPrintSettingDto) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        //Function connection service save CheckListPrintSetting
        export function saveCheckListPrintSetting(checkListPrintSettingDto: model.CheckListPrintSettingDto): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            var data = { checkListPrintSettingDto: checkListPrintSettingDto };
            nts.uk.request.ajax(paths.saveCheckListPrintSetting, data)
                .done(function(res: any) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        export module model {
            export class CheckListPrintSettingDto {
                showCategoryInsuranceItem: boolean;
                showDeliveryNoticeAmount: boolean;
                showDetail: boolean;
                showOffice: boolean;
            }
        }
    }
}
