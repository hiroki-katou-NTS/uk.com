module nts.uk.pr.view.qpp018.c {
    export module service {
        
        // Service paths.
        var paths: any = {
            findCheckListPrintSetting: "ctx/pr/report/insurance/checklist/find",
//            findHealthInsuranceTypeList: "screen/pr/QPP018/healthinsurancetypelist",
            saveCheckListPrintSetting: "ctx/pr/report/insurance/checklist/save"
        };
        
        /**
         * get All CheckList Print Setting
         */
        export function findCheckListPrintSetting(): JQueryPromise<model.CheckListPrintSettingDto> {
            return nts.uk.request.ajax(paths.findCheckListPrintSetting);
        }
        
        export function saveCheckListPrintSetting(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.saveCheckListPrintSetting, command)
        }
        
//        export function findHealthInsuranceTypeList(): JQueryPromise<model.HealthInsuranceTypeItem[]> {
//            var dfd = $.Deferred();
//            nts.uk.request.ajax(paths.findHealthInsuranceTypeList).done(function (res: any) {
//                var list = _.map(res, function (item: model.HealthInsuranceTypeItem) {
//                    return new model.HealthInsuranceTypeItem(item.fieldName, item.localizedName);
//                });
//                dfd.resolve(list);
//            });
//            return dfd.promise();
//        }
        
        export module model {
            
            export class CheckListPrintSettingDto {
                
                showCategoryInsuranceItem: boolean;
                showDetail: boolean;
                showOffice: boolean;
                showTotal: boolean;
                showDeliveryNoticeAmount: boolean;
            }
            
//            export class HealthInsuranceTypeItem {
//                
//                code: string;
//                name: string;
//                
//                constructor(code: string, name: string) {
//                    let self = this;
//                    self.code = code;
//                    self.name = name;
//            }
        }
    }
}
