module cps008.a.viewmodel {


    export class ViewModel {

        items: KnockoutObservableArray<MaintenanceLayout>;
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        A_INP_LAYOUT_CODE: KnockoutObservable<string>;
        A_INP_LAYOUT_NAME: KnockoutObservable<string>;
        A_INP_LAYOUT_CODE_ENABLE: KnockoutObservable<boolean>;
        A_INP_LAYOUT_NAME_ENABLE: KnockoutObservable<boolean>;
        enableBtnCoppy: KnockoutObservable<boolean>;
        enableBtnDelete: KnockoutObservable<boolean>;

        constructor() {
            var self = this;
            self.items = ko.observableArray([]);

            self.columns = ko.observableArray([
                { headerText: 'コード', key: 'layoutCode', width: 120, hidden: false },
                { headerText: '名称', key: 'layoutName', width: 171, hidden: false }
            ]);

            self.currentCode = ko.observable();
            self.A_INP_LAYOUT_CODE = ko.observable(null);
            self.A_INP_LAYOUT_NAME = ko.observable(null);
            self.A_INP_LAYOUT_CODE_ENABLE = ko.observable(false);
            self.A_INP_LAYOUT_NAME_ENABLE = ko.observable(true);
            self.enableBtnCoppy = ko.observable(true);
            self.enableBtnDelete = ko.observable(true);

            self.currentCode.subscribe((function(codeChanged) {
                //self.currentItem(self.findObj(codeChanged));
                if (codeChanged == null) {
                    return;
                }

                let obj = self.findObj(codeChanged);
                self.A_INP_LAYOUT_CODE(obj.layoutCode);
                self.A_INP_LAYOUT_NAME(obj.layoutName);
            }));
        }


        findObj(value: string): any {
            let self = this;
            var itemModel = null;
            _.find(self.items(), function(obj: viewmodel.MaintenanceLayout) {
                if (obj.layoutCode == value) {
                    itemModel = obj;
                }
            })
            return itemModel;
        }
        
        


        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();
            service.getAllMaintenanceLayout().done(function(layout_arr: Array<viewmodel.MaintenanceLayout>) {
                if (layout_arr.length > 0) {
                    self.items(layout_arr);
                    self.currentCode(layout_arr[0].layoutCode);
                }else{
                    self.newModeBtn();
                }
                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
            })
            dfd.resolve();
            return dfd.promise();
        }

        newModeBtn() {
            var self = this;
            self.enableBtnCoppy(false);
            self.enableBtnDelete(false);
            self.A_INP_LAYOUT_CODE_ENABLE(true);
            self.A_INP_LAYOUT_CODE("");
            self.A_INP_LAYOUT_NAME("");
            $("#A_INP_CODE").focus();
        }

        registerBtn() {
            var self = this;
            
        }
        
        deleteBtn() {
            var self = this;    
        }

        openDialogCoppy() {
            var self = this;
            nts.uk.ui.windows.sub.modal('/view/cps/008/c/index.xhtml', { title: '他のレイアウトへ複製' }).onClosed(function(): any {
            });
        }
    }


    export class MaintenanceLayout {
        companyId: string;
        layoutCode: string;
        layoutName: string;
        maintenanceLayoutID: string;
        constructor(companyId?: string, layoutCode?: string, layoutName?: string, maintenanceLayoutID?: string) {
            this.companyId = companyId;
            this.layoutCode = layoutCode;
            this.layoutName = layoutName;
            this.maintenanceLayoutID = maintenanceLayoutID;
        }
    }
}