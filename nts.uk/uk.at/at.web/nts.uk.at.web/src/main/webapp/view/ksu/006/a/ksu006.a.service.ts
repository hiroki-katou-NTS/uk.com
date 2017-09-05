module nts.uk.at.view.ksu006.a {
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            
            findCharsetList: "at/schedule/budget/external/find/charsetlist",
            findExternalBudgetList: "at/schedule/budget/external/findallexternalbudget",
            checkUnitAtr: "at/schedule/budget/external/validate/isDailyUnit",
            findDataPreview: "at/schedule/budget/external/find/preview",
            validateFile: "at/schedule/budget/external/import/validate",
            exportDetailError: "at/schedule/budget/external/log/export",
        };
        
        export function findCharsetList(): JQueryPromise<Array<model.EnumerationModel>> {
            return nts.uk.request.ajax(servicePath.findCharsetList);
        }
        
        export function findExternalBudgetList(): JQueryPromise<any> {
            let dfd = $.Deferred();
            nts.uk.request.ajax("at", servicePath.findExternalBudgetList).done(function(res: model.ExternalBudgetModel) {
                let list = _.map(res, function(item) {
                    return new model.ExternalBudgetModel(item.externalBudgetCode, item.externalBudgetName);
                });

                dfd.resolve(list);
            });
            return dfd.promise();
        }
        
        export function checkUnitAtr(externalBudgetCd: string): JQueryPromise<boolean> {
            return nts.uk.request.ajax("at", servicePath.checkUnitAtr, externalBudgetCd);
        }
        
        export function findDataPreview(extractCondition: any): JQueryPromise<model.DataPreviewModel> {
            return nts.uk.request.ajax(servicePath.findDataPreview, extractCondition);
        }
        
        export function validateFile(extractCondition: any): JQueryPromise<void> {
            return nts.uk.request.ajax(servicePath.validateFile, extractCondition);
        }
        
        /**
        * Model namespace.
        */
        export module model {
            
            export class EnumerationModel {
                value: number;
                fieldName: string;
                localizedName: string;
            }
            
            
            export class ExternalBudgetModel {
                code: string;
                name: string;
                
                constructor(code: string, name: string) {
                    let self = this;
                    self.code = code;
                    self.name = name;
                }
            }
            
            export interface DataPreviewModel {
                isDailyUnit: boolean;
                data: Array<ExternalBudgetValueModel>;
                totalRecord: number;
            }
            
            export interface ExternalBudgetValueModel {
                code: string;
                date: string;
                listValue: Array<any>;
            }
        }
    }
}
