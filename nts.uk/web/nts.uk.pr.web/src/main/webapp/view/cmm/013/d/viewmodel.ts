module cmm013.d.viewmodel {
    import option = nts.uk.ui.option;
    export class ScreenModel {
        selectPosition: KnockoutObservable<service.model.JobHistDto>;
        selectedPositionAtr: KnockoutObservable<number>;
      
      
        selectPositionStartDate: KnockoutObservable<string>;
        selectPositionEndDate: KnockoutObservable<string>;
        positionStartDate: KnockoutObservable<string>;
        timeEditorOption: KnockoutObservable<any>;
        historyId: KnockoutObservable<string>;
        enableDate: KnockoutObservable<boolean>;
        //---radio        
        isRadioCheck: KnockoutObservable<number>;
        itemsRadio: KnockoutObservableArray<any>;
        /**
         * Init screen model.
         */
        constructor() {
            var self = this;
            self.selectedPositionAtr = ko.observable(null);
           
            self.selectPositionStartDate = ko.observable(null);
            self.selectPositionEndDate = ko.observable(null);
            self.selectPosition = ko.observable(null);
            self.positionStartDate = ko.observable(null);
            self.timeEditorOption = ko.mapping.fromJS(new option.TimeEditorOption({inputFormat: "yearmonth"}));
            self.historyId = ko.observable(null);
            self.enableDate = ko.observable(true);
            //---radio
            self.itemsRadio = ko.observableArray([
                {value: 1, text: '履歴を削除する'},
                {value: 2, text: '履歴を修正する'}
            ]);
            self.isRadioCheck = ko.observable(2);
            
        }
        
         // start function
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();
            var layoutCode = nts.uk.ui.windows.getShared('stmtCode');
            var startYm = nts.uk.ui.windows.getShared('startYm');
            self.historyId(nts.uk.ui.windows.getShared('historyId'));
            self.positionStartDate(nts.uk.time.formatYearMonth(startYm));
            service.getPosition( self.historyId()).done(function(position: service.model.JobHistDto){
                 self.selectPosition(position);
                 self.startDiaglog();                 
                 dfd.resolve();
             }).fail(function(res){
                alert(res);    
             })
            //checkbox change
            self.isRadioCheck.subscribe(function(newValue){
                if(newValue === 2){
                    self.enableDate(true);    
                }else{
                    self.enableDate(false);    
                }
            })
            // Return.
            return dfd.promise();    
        }
        
        startDiaglog(): any{
            var self = this;
            var position = self.selectPosition();
           
            self.selectPositionStartDate(nts.uk.time.formatYearMonth(position.startDate));
            self.selectPositionEndDate(nts.uk.time.formatYearMonth(position.endDate));
        }
        
        positionProcess(): any{
            var self = this;
            if(self.isRadioCheck() === 1){
                self.dataDelete();
            }else{
                self.dataUpdate();    
            }
        }
        
        dataDelete():any{
            var self = this;
            service.deletePosition(self.selectPosition()).done(function(){
                 nts.uk.ui.windows.close();
            }).fail(function(res){
                alert(res);    
            })
        }
        
        dataUpdate(): any{
            var self = this;
            var positionInfor = self.selectPosition();
            var inputYm = $("#INP_001").val();
            if(!nts.uk.time.parseYearMonth(inputYm).success){
               alert(nts.uk.time.parseYearMonth(inputYm).msg);
               return false;    
            }
            positionInfor.startDate = +$("#INP_001").val().replace('/','');
            if(positionInfor.startDate > +self.selectPositionEndDate().replace('/','')){
                alert("履歴の期間が重複しています。");
                return false;
            
            
            }else{
                service.updatePosition(positionInfor).done(function(){
                     nts.uk.ui.windows.close();
                }).fail(function(res){
                    alert(res);    
                })
            }
        }
        
        closeDialog() {
            nts.uk.ui.windows.close();    
        }
    }
}