module cps008.c.viewmodel {
    
    export class ViewModel {
        layoutCode: KnockoutObservable<string>;
        layoutName: KnockoutObservable<string>;
        A_INP_LAYOUT_CODE: KnockoutObservable<string>;
        A_INP_LAYOUT_NAME: KnockoutObservable<string>;
        A_INP_LAYOUT_CODE_ENABLE: KnockoutObservable<boolean>;
        A_INP_LAYOUT_NAME_ENABLE: KnockoutObservable<boolean>;
        checked: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;

        constructor() {
            var self = this;
            self.layoutCode = ko.observable('001');
            self.layoutName = ko.observable('test');
            self.A_INP_LAYOUT_CODE = ko.observable(null);
            self.A_INP_LAYOUT_NAME = ko.observable(null);
            self.A_INP_LAYOUT_CODE_ENABLE = ko.observable(true);
            self.A_INP_LAYOUT_NAME_ENABLE = ko.observable(true);
            self.checked = ko.observable(false);
            self.enable = ko.observable(true);

            let obj = nts.uk.ui.windows.getShared('modelToCoppy');
            if (obj != null) {
                self.layoutCode(obj.layoutCode);
                self.layoutName(obj.layoutName);
            }
        }

        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();
            return dfd.promise();
        }
        /**
         *  Update Button
         */
        updateBtn() {
            var self = this;
            self.checkValidate();
            let obj = new UpdateMaintenanceLayoutCommand("", self.A_INP_LAYOUT_CODE(),self.layoutCode() ,self.A_INP_LAYOUT_NAME(), "", self.checked());
            var dfd = $.Deferred<any>();
            service.updateOrRegisterlMaintenanceLayout(obj)
                .done(function() {
                    dfd.resolve();
                })
                .fail(function() {
                    dfd.reject();
                });
            dfd.promise();
        }

        /**
         * check validate
         */
        checkValidate() {
            var self = this;
            if (self.A_INP_LAYOUT_CODE() == "" || self.A_INP_LAYOUT_NAME() == "") {
                if (self.A_INP_LAYOUT_CODE() == "") {
                    $("#C_INP_CODE").focus();
                } else {
                    $("#C_INP_NAME").focus();
                }
                return;
            }
            if (self.checked() && (self.layoutCode() == self.A_INP_LAYOUT_CODE())) {
                nts.uk.ui.dialog.alert("#Msg_355#");
                $("#C_INP_CODE").focus();
                return;
            }
        }


    }
    class ItemModel {
        code: string;
        name: string;
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
    /**
     *  model to update or Register 
     */
    export class UpdateMaintenanceLayoutCommand {

        companyId: string;
        layoutCode_sou: string;
        layoutCode_des : string;
        layoutName: string;
        maintenanceLayoutID: string;
        checked: boolean;
        constructor(companyId?: string, layoutCode_sou?: string, layoutCode_des? :string , layoutName?: string, maintenanceLayoutID?: string, checked?: boolean) {
            this.companyId = companyId;
            this.layoutCode_sou = layoutCode_sou;
            this.layoutCode_des = layoutCode_des;
            this.layoutName = layoutName;
            this.maintenanceLayoutID = maintenanceLayoutID;
            this.checked = checked;
        }
    }
}