module nts.uk.com.view.cmf002.g.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import dialog = nts.uk.ui.dialog;
    import model = cmf002.share.model;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import modal = nts.uk.ui.windows.sub.modal;

    export class ScreenModel {
        items: KnockoutObservableArray<OutputCodeConvert>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        
        currentCode: KnockoutObservable<string>;
        currentItem: KnockoutObservable<CurrentOutputCodeConvertDetail>;
        
        cdConvertDetailList: KnockoutObservableArray<CdConvertDetail>;

        constructor() {
            let self = this;

            self.cdConvertDetailList = ko.observableArray();
            $("#fixed-table").ntsFixedTable({ height: 300, width: 600 });

            self.items = ko.observableArray([]);

            for (let i = 0; i < 5; i++) {
                self.items.push(new OutputCodeConvert('00' + i, '基本給'));
            }

            self.columns = ko.observableArray([
                { headerText: 'コード', key: 'code', width: 100, hidden: false },
                { headerText: '名称', key: 'name', width: 150, hidden: false }
            ]);

            self.currentCode = ko.observable();
            self.currentItem = ko.observable(new CurrentOutputCodeConvertDetail('', '', []));
            self.currentCode.subscribe(function(currentCode) {
                let result = _.find(self.items(), function(o) { return o.code === currentCode; });
                self.currentItem(new CurrentOutputCodeConvertDetail(result.code, result.name, [])); 
            });
        }

        addItem() {
            let self = this;
            self.cdConvertDetailList.push(new CdConvertDetail(0, '', ''));
        }

        removeItem() {
            let self = this;
            self.cdConvertDetailList.pop();
        }

        start(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }
    }
    
    
    export class CurrentOutputCodeConvertDetail {
        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        cdConvertDetailList: KnockoutObservableArray<CdConvertDetail>;
        
         constructor(code: string, name: string, cdConvertDetail: Array<CdConvertDetail>){
             this.code = ko.observable(code);
             this.name = ko.observable(name);
             this.cdConvertDetailList = ko.observableArray(cdConvertDetail);
         }
    }
    

    export class OutputCodeConvert {
        code: string;
        name: string;
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    export class CdConvertDetail {
        lineNumber: KnockoutObservable<number>;
        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;

        constructor(lineNumber: number, code: string, name: string) {
            this.lineNumber = ko.observable(lineNumber);
            this.code = ko.observable(code);
            this.name = ko.observable(name);
        }
    }
}
