module nts.uk.at.view.ksu006.a {
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            findExternalBudgetList: "at/schedule/budget/external/findallexternalbudget",
            
            findDataPreview: "at/schedule/budget/external/find/preview",
            executeImportFile: "at/schedule/budget/external/import/execute",
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
        
        export function executeImportFile(command: any): JQueryPromise<any> {
            let dfd = $.Deferred();
            nts.uk.request.ajax(servicePath.executeImportFile, command).then((taskId: any) => {
                dfd.resolve(taskId);
            }).done((res: any) => {
                dfd.resolve(res);
            }).fail(res => {
                dfd.reject(res);
            });
            return dfd.promise();
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
