module nts.uk.com.view.jmm018.y.viewmodel {
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;
    import error = nts.uk.ui.dialog.error;
    import info = nts.uk.ui.dialog.info;
    
    export class ScreenModel {
        
        items1: KnockoutObservableArray<any>;
        selectedCode: KnockoutObservableArray<any>;
        selectedCodes2: KnockoutObservableArray<any>;
        constructor() {
            let self = this;
            self.items1 = ko.observableArray([('B0001', ['サービス部1', 'サービス部2', 'サービス部3']), ('0002', [])]);
            self.selectedCode = ko.observableArray([]);
            self.selectedCodes2 = ko.observable([]);
            self.columns2 = ko.observableArray([{ headerText: getText('JMM027_Y2_4'), width: "250px", key: 'code', dataType: "string", hidden: false },
        }
        
        startPage(): JQueryPromise<any> {
            let self = this,
            dfd = $.Deferred();
            block.grayout();
            let param = getShared('shareToJMM018Y');
            
            block.clear();
            dfd.resolve();
            return dfd.promise();
        }


        decision(): void {
            nts.uk.ui.windows.setShared("shareToJMM018Z", undefined);
            nts.uk.ui.windows.close();
        }

        close(): void {
            nts.uk.ui.windows.setShared("shareToJMM018Z", undefined);
            nts.uk.ui.windows.close();
        }

    }
}