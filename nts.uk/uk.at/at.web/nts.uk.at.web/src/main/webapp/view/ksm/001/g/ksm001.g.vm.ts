module nts.uk.at.view.ksm001.g {

    import CommonGuidelineSettingDto = service.model.CommonGuidelineSettingDto;
    import UsageSettingDto = nts.uk.at.view.ksm001.a.service.model.UsageSettingDto;
    import UsageSettingModel = nts.uk.at.view.ksm001.a.viewmodel.UsageSettingModel;
    import EstimatedConditionDto = service.model.EstimatedConditionDto;
    import EstimatedAlarmColorDto = service.model.EstimatedAlarmColorDto;
    import ReferenceConditionDto = service.model.ReferenceConditionDto;

    export module viewmodel {

        export class ScreenModel {
            itemsSwap: KnockoutObservableArray<ItemModel>;
            columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            currentCodeListSwap: KnockoutObservableArray<any>;
            test: KnockoutObservableArray<any>;
            
            checked1: KnockoutObservable<boolean>;
            checked2: KnockoutObservable<boolean>;
            checked3: KnockoutObservable<boolean>;
           
            constructor() {
                var _self = this;
                _self.itemsSwap = ko.observableArray([]);
           
                var array = [];
                for (var i = 0; i < 10; i++) {
                   array.push(new ItemModel(i, '基本給'));
                }
                _self.itemsSwap(array);
    
                _self.columns = ko.observableArray([
                   { headerText: 'コード', key: 'code', width: 100 },
                   { headerText: '名称', key: 'name', width: 150 }
                ]);
    
                _self.currentCodeListSwap = ko.observableArray([]);
                _self.test = ko.observableArray([]);
                
                _self.checked1 = ko.observable(false);
                _self.checked2 = ko.observable(false);
                _self.checked3 = ko.observable(false);
             }

            /**
            * start page data 
            */
            public startPage(): JQueryPromise<any> {
                var _self = this;
                var dfd = $.Deferred();
                
                 dfd.resolve();

                return dfd.promise();
            }
            
            /**
             * function on click save CommonGuidelineSetting
             */
            public save(): void {
                var _self = this;

                nts.uk.ui.block.invisible();
            }

            /**
             * Event on click cancel button.
             */
            public cancelSetting(): void {
                nts.uk.ui.windows.close();
            }

        }
        
        export class ItemModel {
           code: number;
           name: string;
           constructor(code: number, name: string) {
               this.code = code;
               this.name = name;
           }
       }
        
       //  目安利用条件
       export enum EstimatedCondition {
            // 条件1
            CONDITION_1ST = 1,
            // 条件2
            CONDITION_2ND = 2,
            // 条件3
            CONDITION_3RD = 3,
            // 条件4
            CONDITION_4TH = 4,
            // 条件5
            CONDITION_5TH = 5,
        }
    }
}