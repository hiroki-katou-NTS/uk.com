module nts.uk.at.view.ksu006.a {
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
//            findManageDistinct: 'ctx/at/share/vacation/setting/annualpaidleave/find/managedistinct',
            findExternalBudgetList: "at/schedule/budget/external/findallexternalbudget",
        };
        
//        export function findManageDistinct(): JQueryPromise<any> {
//            return nts.uk.request.ajax(servicePath.findManageDistinct);
//        }
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
