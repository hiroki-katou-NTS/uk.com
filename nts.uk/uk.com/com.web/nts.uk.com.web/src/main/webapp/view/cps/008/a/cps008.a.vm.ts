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
                if (codeChanged == null) {
                    return;
                }
                self.updateMode();
                let obj = self.findObj(codeChanged);
                self.A_INP_LAYOUT_CODE(obj.layoutCode);
                self.A_INP_LAYOUT_NAME(obj.layoutName);
            }));
        }

        /**
         *  find Object Maintenance Layout when item selected
         */
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
                } else {
                    self.newModeBtn();
                }
                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
            })
            dfd.resolve();
            return dfd.promise();
        }
        /**
         *  get all domain Maintenence Layout
         */
        getAllData(layoutCode: String) {
            var self = this;
            var dfd = $.Deferred<any>();
            service.getAllMaintenanceLayout().done(function(layout_arr: Array<viewmodel.MaintenanceLayout>) {
                self.items(layout_arr);
                self.currentCode(layoutCode);
                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
            })
            dfd.resolve();
            return dfd.promise();
        }

        /**
         *  mode Update cho Screen
         */
        updateMode() {
            var self = this;
            self.enableBtnCoppy(true);
            self.enableBtnDelete(true);
            self.A_INP_LAYOUT_CODE_ENABLE(false);
        }

        /**
         *  check Input code, name
         */
        checkValidateInput(): boolean {
            let self = this;
            if (self.A_INP_LAYOUT_CODE() && self.A_INP_LAYOUT_NAME()) {
                return true;
            } else {
                return false;
            }
        }

        /**
         *  set mode new or when click button New
         */
        newModeBtn() {
            var self = this;
            self.enableBtnCoppy(false);
            self.enableBtnDelete(false);
            self.A_INP_LAYOUT_CODE_ENABLE(true);
            self.A_INP_LAYOUT_CODE("");
            self.A_INP_LAYOUT_NAME("");
            $("#A_INP_CODE").focus();
        }
        /**
         *  Register Maintenance Layout
         */
        registerBtn() {
            var self = this;
            var dfd = $.Deferred<any>();
            if (self.checkValidateInput()) {
                let obj = new AddMaintenanceLayoutCommand("000-000000000001 ", self.A_INP_LAYOUT_CODE(), self.A_INP_LAYOUT_NAME(), "1");
                service.addMaintenanceLayout(obj)
                    .done(function(error) {
                        nts.uk.ui.dialog.alert("#Msg_15#");
                        self.getAllData(obj.layoutCode);
                        dfd.resolve();
                    }).fail(function(error) {
                        alert(error.message);
                    })
                dfd.resolve();
                return dfd.promise();
            } else {
                console.log("");
            }
        }

        /**
         *  Delete Maintenance Layout
         */
        deleteBtn() {
            var self = this;
        }

        /**
         *  Open Screen C
         */
        openDialogCoppy() {
            var self = this;
            if (self.currentCode() == null) {
                return;
            }
            let obj = self.findObj(self.currentCode());
            nts.uk.ui.windows.setShared('modelToCoppy', obj);
            nts.uk.ui.windows.sub.modal('/view/cps/008/c/index.xhtml', { title: '他のレイアウトへ複製' }).onClosed(function(): any {
            });
        }
    }

    /**
     *  model Dto
     */
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

    /**
     *  model command 
     */
    export class AddMaintenanceLayoutCommand {
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