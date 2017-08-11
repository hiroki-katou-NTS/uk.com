module nts.uk.at.view.ksu006.a {
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            findExternalBudgetList: "at/schedule/budget/external/findallexternalbudget",
            findDataPreview: "at/schedule/budget/external/find/preview",
            validateFile: "at/schedule/budget/external/import/validate",
            exportDetailError: "at/schedule/budget/external/log/export",
        };
        
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
        
        export function findDataPreview(extractCondition: any): JQueryPromise<model.DataPreviewModel> {
            return nts.uk.request.ajax(servicePath.findDataPreview, extractCondition);
        }
        
        export function validateFile(extractCondition: any): JQueryPromise<void> {
            return nts.uk.request.ajax(servicePath.validateFile, extractCondition);
        }
        
        export function downloadDetailError(executeId: string): JQueryPromise<Array<model.ExternalBudgetLogModel>> {
            return nts.uk.request.exportFile(paths.exportDetailError + "/" +  executeId);
        }
        
        /**
        * Model namespace.
        */
        export module model {
            
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
