module nts.uk.at.view.ksu006.a {
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            findExternalBudgetList: "at/schedule/budget/external/findallexternalbudget",
            
            findDataPreview: "at/schedule/budget/external/find/preview",
            validateFile: "at/schedule/budget/external/import/validate",
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
        
        export function validateFile(fileId: string): JQueryPromise<void> {
            return nts.uk.request.ajax(servicePath.validateFile + "/" + fileId);
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
            
            export class DataPreviewModel {
                isDailyUnit: boolean;
                data: Array<ExternalBudgetValueModel>;
                totalRecord: number;
                
                constructor(isDailyUnit: boolean, data: Array<ExternalBudgetValueModel>, totalRecord: number) {
                    let self = this;
                    self.isDailyUnit = isDailyUnit;
                    self.data = data;
                    self.totalRecord = totalRecord;
                }
            }
            
            export class ExternalBudgetValueModel {
                code: string;
                date: string;
                listValue: Array<any>;
                
                
                constructor(code: string, date: string, listValue: Array<any>) {
                    let self = this;
                    self.code = code;
                    self.date = date;
                    self.listValue = listValue;
                }
            }
        }
    }
}
