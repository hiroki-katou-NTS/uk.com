module nts.uk.com.view.cdl003.a {

    import ClassificationFindDto = service.model.ClassificationFindDto;
    export module viewmodel {

        export class ScreenModel {
            columns: KnockoutObservableArray<any>;
            classifications: KnockoutObservableArray<ClassificationFindDto>;
            selectedMulCode: KnockoutObservableArray<string>;
            selectedSelCode: KnockoutObservable<string>;
            isMultiple: KnockoutObservable<boolean>;
            searchOption: any;
            constructor() {
                var self = this;
                var fields: Array<string> = ['name', 'code'];
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText("KCP002_2"), key: 'code', width: 100 },
                    { headerText: nts.uk.resource.getText("KCP002_3"), key: 'name', width: 150 }
                ]);
                self.classifications = ko.observableArray([]);
                self.selectedMulCode = ko.observableArray([]);
                self.selectedSelCode = ko.observable('');
                
                
                self.isMultiple = ko.observable(true);
                var inputCDL003 = nts.uk.ui.windows.getShared('inputCDL003');
                self.searchOption = {
                    searchMode: 'filter',
                    targetKey: 'code',
                    comId: 'classificationSelect',
                    items: self.classifications,
                    selected: null,
                    selectedKey: 'code',
                    fields: fields,
                    mode: 'igGrid'
                }
                if(inputCDL003){
                    self.isMultiple(inputCDL003.isMultiple);
                    if (self.isMultiple()) {
                        self.selectedMulCode(inputCDL003.canSelected);
                        self.searchOption.selected = self.selectedMulCode; 
                    } else {
                        self.selectedSelCode(inputCDL003.canSelected);
                        self.searchOption.selected = self.selectedSelCode;
                    }
                }
           }
            /**
             * start page when init data
             */
           public startPage(): JQueryPromise<any> {
               var self = this;
               var dfd = $.Deferred();
               service.findAllClassifications().done(function(data) {
                   self.classifications(data);
                   dfd.resolve(self);
               });
               return dfd.promise();
           }
            /**
             * save classification code to parent
             */
            private saveClassificationCodes(): void{
                var self = this;
                 if(self.isMultiple()){
                    if(!self.selectedMulCode() || self.selectedMulCode().length == 0){
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_641" });
                        return;    
                    }    
                }else {
                     if(!self.selectedSelCode || !self.selectedSelCode()){
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_641" });
                        return;    
                    }      
                }
                 var selectedCode : any = self.selectedMulCode();
                if (!self.isMultiple()) {
                    selectedCode = self.selectedSelCode();
                }
                nts.uk.ui.windows.setShared('outputCDL003',{selectedCode: selectedCode});
                nts.uk.ui.windows.close();
            }
            
            /**
             * close windows
             */
            private closeClassificationCodes(): void{
                nts.uk.ui.windows.close();  
            }
        }

    }
}