module nts.uk.pr.view.qet002.a.viewmodel {
    export class ScreenModel {
        targetYear: KnockoutObservable<number>;
        isLowerLimit: KnockoutObservable<boolean>;
        lowerLimitValue: KnockoutObservable<number>;
        isUpperLimit: KnockoutObservable<boolean>;
        upperLimitValue: KnockoutObservable<number>;
        
        constructor() {
            this.targetYear = ko.observable(2016);
            this.isLowerLimit = ko.observable(true);
            this.isUpperLimit = ko.observable(true);
            this.lowerLimitValue = ko.observable(null);
            this.upperLimitValue = ko.observable(null);
            
        }
        
        /**
         * Start screen.
         */
        public start(): JQueryPromise<void>{
            var dfd = $.Deferred<void>();
            dfd.resolve();
            return dfd.promise();
        }
        

        /**
         * Print Report
         */
        public printData(){
            var hasError=false;
            if(this.targetYear() == null){   
                hasError = true;
                $('#target-year-input').ntsError('set', '未入力エラー');                 
            }   
            if(this.isLowerLimit()==true){
                if(this.lowerLimitValue()==null){  
                    hasError = true;
                    $('#lower-limit-input').ntsError('set', '未入力エラー');                   
                }    
            } 
            if(this.isUpperLimit()==true){
                if(this.upperLimitValue()==null){    
                    hasError = true;
                    $('#upper-limit-input').ntsError('set', '未入力エラー');                
                }    
            }
            if((this.isLowerLimit()==true) && (this.isUpperLimit()==true)){
                if(this.lowerLimitValue()>this.upperLimitValue()){
                    hasError = true;
                    $('#lower-limit-input').ntsError('set', '未入力エラー');                      
                }
            }
            if(hasError) {
                return;
            }  
        }
    }
    export class AccumulatedPaymentResultViewModel{
          
    }
}