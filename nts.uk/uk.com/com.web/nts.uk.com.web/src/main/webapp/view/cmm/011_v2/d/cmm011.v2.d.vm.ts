module nts.uk.com.view.cmm011.v2.d.viewmodel {
    import getText = nts.uk.resource.getText;
    import modal = nts.uk.ui.windows.sub.modal;

    export class ScreenModel {
        itemList: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number>;
        enable: KnockoutObservable<boolean>;

        code: KnockoutObservable<string> = ko.observable(null);
        name: KnockoutObservable<string> = ko.observable(null);
        displayName: KnockoutObservable<string> = ko.observable(null);
        genericName: KnockoutObservable<string> = ko.observable(null);
        externalCode: KnockoutObservable<string> = ko.observable(null);

        tagretHistory: number = 1;
        screenMode: number = 0;
        hireachyCode: number = 999;
        hireachyLevel: number = 10;

        constructor() {
            var self = this;
            self.itemList = ko.observableArray([
                new BoxModel(1,
                    ko.computed(function () {
                        if (self.screenMode == SCREEN_MODE.WORKPLACE)
                            return getText('CMM011_211');
                        if (self.screenMode == SCREEN_MODE.DEPARTMENT)
                            return getText('CMM011-311');
                    }),
                    ko.computed(function () {
                        return self.hireachyCode <= 999 ? true : false;
                    })
                ),
                new BoxModel(2,
                    ko.computed(function () {
                        if (self.screenMode == SCREEN_MODE.WORKPLACE)
                            return getText('CMM011_212');
                        if (self.screenMode == SCREEN_MODE.DEPARTMENT)
                            return getText('CMM011_312');
                    }),
                    ko.computed(function () {
                        return self.hireachyCode <= 999 ? true : false;
                    })
                ),
                new BoxModel(3,
                    ko.computed(function () {
                        if (self.screenMode == SCREEN_MODE.WORKPLACE)
                            return getText('CMM011_213');
                        if (self.screenMode == SCREEN_MODE.DEPARTMENT)
                            return getText('CMM011_313');
                    }),
                    ko.computed(function () {
                        return self.hireachyLevel < 10 ? true : false;
                    })
                )
            ]);
            self.selectedId = ko.observable(1);
            self.enable = ko.observable(true);

            let params = nts.uk.ui.windows.getShared("CMM011AParams");
            if (params) {
                self.screenMode = params.initMode;
//                self.code = params.selectedCode;
//                self.name = params.selectedName;
                self.tagretHistory = 2;
            }

        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        openScreenCDialog() {
            modal("/view/cmm/011_v2/c/index.xhtml").onClosed(() => {

            });
        }

        register() {
            let self = this;
            $(".nts-input").trigger("validate");
            let itemRegister = new RegisterInfor("1", self.code(), self.name(), self.displayName(), self.genericName(), self.externalCode(), self.hireachyCode);
            if (self.screenMode == 1)
                this.workplaceRegister(itemRegister);
            if (self.screenMode == 2)
                this.departmentRegister(itemRegister);
        }

        workplaceRegister(itemRegister: RegisterInfor) {
            let self = this;
            service.workplaceRegister(itemRegister).done(function () {
                nts.uk.ui.dialog.info({messageId: "Msg_15"}).then(() => {
                    nts.uk.ui.windows.close();
                });
            }).fail(function (res) {
                nts.uk.ui.dialog.bundledErrors(res);
            })
        }

        departmentRegister(itemRegister: RegisterInfor) {
            let self = this;
            service.departmentRegister(itemRegister).done(function () {
                nts.uk.ui.dialog.info({messageId: "Msg_15"}).then(() => {
                    nts.uk.ui.windows.close();
                });
            }).fail(function (res) {
                nts.uk.ui.dialog.bundledErrors(res);
            })
        }
        
        cancel() {
            nts.uk.ui.windows.close();
        }
    }
    
    enum SCREEN_MODE {
        WORKPLACE = 0,
        DEPARTMENT = 1
    }

    class BoxModel {
        id: number;
        name: string;
        enable: boolean;

        constructor(id, name, enable) {
            var self = this;
            self.id = id;
            self.name = name;
            self.enable = enable;
        }
    }

    class RegisterInfor {
        id: string;
        code: string;
        name: string;
        displayName: string;
        genericName: string;
        externalCode: string;
        hierarchycode: number;

        constructor(id: string, code: string, name: string, displayName: string, genericName: string, externalCode: string, hierarchycode: number) {
            this.id = id;
            this.code = code;
            this.name = name;
            this.displayName = displayName;
            this.genericName = genericName;
            this.externalCode = externalCode;
            this.hierarchycode = hierarchycode;
        }
    }
}