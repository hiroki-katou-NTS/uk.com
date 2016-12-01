module nts.uk.pr.view.qpp005.f {
    
    export module viewmodel {
        export class ScreenModel {
            totalCommuteCheck : CheckBox;
            totalCommuteEditor: NumberEditor;
            taxCommuteCheck: CheckBox;
            taxCommuteEditor: NumberEditor;
            oneMonthCheck: CheckBox;
            oneMonthCommuteEditor: NumberEditor;
            oneMonthRemainderEditor: NumberEditor;
            commuteDividedByMonth: CommuteDividedByMonth;
            
            constructor() {
                var self = this;
                
                /**
                 * 通勤費　合計
                 */
                self.totalCommuteCheck = new CheckBox();
                
                /**
                 * １ヵ月分通勤費
                 */
                self.oneMonthCheck = new CheckBox();
                self.oneMonthCheck.isChecked = ko.observable(false);
                
                /**
                 * 課税通勤費
                 */
                self.taxCommuteCheck = new CheckBox();
                self.taxCommuteCheck.isChecked = ko.observable(false);
                
                /**
                 * 通勤費合計 テキストボックス（金額）            
                 */
                self.totalCommuteEditor = new NumberEditor();
                self.totalCommuteEditor.isEnable = self.totalCommuteCheck.isChecked;
                self.totalCommuteEditor.value = ko.observable("10000");
                
                /**
                 * 課税通勤費 テキストボックス（金額）
                 */
                self.taxCommuteEditor = new NumberEditor();
                self.taxCommuteEditor.isEnable = ko.computed(function(){
                    return  self.totalCommuteCheck.isChecked() && self.taxCommuteCheck.isChecked();    
                });
                self.taxCommuteEditor.value = ko.observable("200000");
                
                /**
                 * 1か月分通勤費 テキストボックス（金額）
                 */
                self.oneMonthCommuteEditor = new NumberEditor();
                self.oneMonthCommuteEditor.isEnable = self.oneMonthCheck.isChecked;
                self.oneMonthCommuteEditor.value = ko.observable("250000");
                
                /**
                 * 余り（端数）
                 */
                self.oneMonthRemainderEditor = new NumberEditor();
                self.oneMonthRemainderEditor.isEnable = self.oneMonthCheck.isChecked;
                self.oneMonthRemainderEditor.value = ko.observable("300000");
                
                /**
                 * Commute items
                 */
                self.commuteDividedByMonth = new CommuteDividedByMonth();
                self.commuteDividedByMonth.isEnable = self.oneMonthCheck.isChecked;
            }
            
            start(): JQueryPromise<any>{
                var self = this;
                var dfd=$.Deferred();
                
                return dfd.promise();
            }
        }
        
        export class CheckBox{
            isChecked: KnockoutObservable<boolean>;
            
            constructor() {
                var self = this;
                self.isChecked = ko.observable(true);    
            }
        }
        
        export class NumberEditor{
            isEnable: KnockoutObservable<boolean>;
            value: KnockoutObservable<string>;
            width: string;
            option: ui.option.CurrencyEditorOption;
            
            constructor() {
               var self = this;
                self.option = ko.mapping.fromJS(new ui.option.CurrencyEditorOption({grouplength: 3, 
                                                                                    currencyformat: 'JPY', 
                                                                                    width: "80"}));
            }           
        }
        
        class CommuteDividedByMonth{
            commuteItems: KnockoutObservable<any>;
            selectedCode: KnockoutObservable<string>;
            isEnable: KnockoutObservable<boolean>;
            
            constructor(){
                var self = this;
                self.commuteItems = ko.observableArray([
                                        new CommuteDividedItemsByMonth("1","１ヶ月"),
                                        new CommuteDividedItemsByMonth("2","２ヶ月"),
                                        new CommuteDividedItemsByMonth("3","３ヶ月"),
                                        new CommuteDividedItemsByMonth("4","４ヶ月"),
                                        new CommuteDividedItemsByMonth("5","５ヶ月"),
                                        new CommuteDividedItemsByMonth("6","６ヶ月"),
                                    ])
                self.selectedCode = ko.observable("1");
            }            
        }
        
        function CommuteDividedItemsByMonth(code, name){
            this.code = code;
            this.name = name;
        }
    }
}