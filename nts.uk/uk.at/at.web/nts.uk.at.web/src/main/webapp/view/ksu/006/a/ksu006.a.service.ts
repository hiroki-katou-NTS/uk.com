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
        
        /**
         * findCharsetList
         */
        export function findCharsetList(): JQueryPromise<Array<model.EnumerationModel>> {
            return nts.uk.request.ajax(servicePath.findCharsetList);
        }
        
        /**
         * findExternalBudgetList
         */
        export function findExternalBudgetList(): JQueryPromise<any> {
            let dfd = $.Deferred();
            nts.uk.request.ajax(servicePath.findExternalBudgetList).done(function(res: any) {
                let list = _.map(res, function(item: any) {
                    return new model.ExternalBudgetModel(item.externalBudgetCode, item.externalBudgetName);
                });

                dfd.resolve(list);
            });
            return dfd.promise();
        }
        
        /**
         * checkUnitAtr: check is daily or time zone?
         */
        export function checkUnitAtr(externalBudgetCd: string): JQueryPromise<boolean> {
            return nts.uk.request.ajax(servicePath.checkUnitAtr + "/" + externalBudgetCd);
        }
        
        /**
         * findDataPreview
         */
        export function findDataPreview(extractCondition: any): JQueryPromise<model.DataPreviewModel> {
            return nts.uk.request.ajax(servicePath.findDataPreview, extractCondition);
        }
        
        /**
         * validateFile
         */
        export function validateFile(extractCondition: any): JQueryPromise<void> {
            return nts.uk.request.ajax(servicePath.validateFile, extractCondition);
        }
        
        /**
        * Model namespace.
        */
        export module model {
            
            /**
             * EnumerationModel
             */
            export class EnumerationModel {
                value: number;
                fieldName: string;
                localizedName: string;
            }
            
            /**
             * ExternalBudgetModel
             */
            export class ExternalBudgetModel {
                code: string;
                name: string;
                
                constructor(code: string, name: string) {
                    let self = this;
                    self.code = code;
                    self.name = name;
                }
            }
            
            /**
             * DataPreviewModel
             */
            export interface DataPreviewModel {
                isDailyUnit: boolean;
                data: Array<ExternalBudgetValueModel>;
                totalRecord: number;
            }
            
            /**
             * ExternalBudgetValueModel
             */
            export interface ExternalBudgetValueModel {
                code: string;
                date: string;
                listValue: Array<any>;
            }
        }
    }
}
