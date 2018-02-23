module nts.uk.com.view.cmf001.f.viewmodel {
    import close = nts.uk.ui.windows.close;
    import model = cmf001.share.model;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export class ScreenModel {
        convertItems: KnockoutObservableArray<model.AcceptanceCodeConvert> = ko.observableArray([]);
        detailConvertItems: KnockoutObservableArray<model.CodeConvertDetail> = ko.observableArray([]);
        currentConvertCode: KnockoutObservable<string>;
        currentConvertDetail: KnockoutObservable<string>;
        convertCode: KnockoutObservable<string>;
        convertName: KnockoutObservable<string>;
        selectedSettingKbn: any = ko.observable(0);
        constructor() {
            var self = this;
            self.detailConvertItems = ko.observableArray([]);
            self.convertItems = ko.observableArray([
                new model.AcceptanceCodeConvert('001', 'Item 1', self.detailConvertItems(), 1),
                new model.AcceptanceCodeConvert('002', 'Item 1', self.detailConvertItems(), 1),
                new model.AcceptanceCodeConvert('003', 'Item 1', self.detailConvertItems(), 1),
                new model.AcceptanceCodeConvert('004', 'Item 1', self.detailConvertItems(), 1),
                new model.AcceptanceCodeConvert('005', 'Item 1', self.detailConvertItems(), 1),
                new model.AcceptanceCodeConvert('006', 'Item 1', self.detailConvertItems(), 1),
                new model.AcceptanceCodeConvert('008', 'Item 1', self.detailConvertItems(), 1),
                new model.AcceptanceCodeConvert('009', 'Item 1', self.detailConvertItems(), 1),
                new model.AcceptanceCodeConvert('010', 'Item 1', self.detailConvertItems(), 1),
                new model.AcceptanceCodeConvert('011', 'Item 1', self.detailConvertItems(), 1),
                new model.AcceptanceCodeConvert('012', 'Item 1', self.detailConvertItems(), 1),
                new model.AcceptanceCodeConvert('013', 'Item 1', self.detailConvertItems(), 1),
                new model.AcceptanceCodeConvert('014', 'Item 1', self.detailConvertItems(), 1),
                new model.AcceptanceCodeConvert('015', 'Item 2', self.detailConvertItems(), 1),
                new model.AcceptanceCodeConvert('016', 'Item 12', self.detailConvertItems(), 1)
            ]);
            
            self.currentConvertCode   = ko.observable('');
            self.currentConvertDetail = ko.observable('');
            self.convertCode          = ko.observable('001');
            self.convertName          = ko.observable('Item 1');
            $("#fixed-table").ntsFixedTable({ height: 200});
        }
        
        OpenFModal() {
            let self = this;
            nts.uk.ui.windows.sub.modal('/view/cmf/001/f/index.xhtml', { title: '' }).onClosed(function(): any {
            });
        }
        
        start(): JQueryPromise<any> {
            let self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }
        
        addDetail(){
            let self = this;
            self.detailConvertItems.push(new model.CodeConvertDetail(1, '', ''));    
        }
        
        removeDetail(){
            var self = this;
            self.detailConvertItems.pop();    
        }
        
        closeDialog() {
            close();
        }
        
    }//end screenModel
}//end module