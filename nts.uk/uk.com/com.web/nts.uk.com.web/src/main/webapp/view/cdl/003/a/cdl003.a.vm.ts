module nts.uk.com.view.cdl003.a {

    import ClassificationFindDto = service.model.ClassificationFindDto;
    export module viewmodel {

        export class ScreenModel {
            columns: KnockoutObservableArray<NtsGridListColumn>;
            classifications: KnockoutObservableArray<ClassificationFindDto>;
            selectedCode: KnockoutObservableArray<string>;
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
                self.selectedCode = ko.observableArray([]);
                self.searchOption = {
                    searchMode: 'filter',
                    targetKey: 'code',
                    comId: 'classificationSelect',
                    items: self.classifications,
                    selected: self.selectedCode,
                    selectedKey: 'code',
                    fields: fields,
                    mode: 'igGrid'
                }
                self.isMultiple = ko.observable(true);
                var inputCDL003 = nts.uk.ui.windows.getShared('inputCDL003');
                if(inputCDL003){
                    self.selectedCode(inputCDL003.canSelected);
                    self.isMultiple(inputCDL003.isMultiple);    
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
                nts.uk.ui.windows.setShared('outputCDL003',{selectedCode: self.selectedCode()});
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