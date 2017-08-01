module nts.uk.at.view.ksu006.a {
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            findExternalBudgetList: "at/schedule/budget/external/findallexternalbudget",
            
            findDataPreview: "at/schedule/budget/external/find/preview",
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
                data: Array<ExternalBudgetValueModel>;
                totalRecord: number;
                
                constructor(data: Array<ExternalBudgetValueModel>, totalRecord: number) {
                    let self = this;
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
