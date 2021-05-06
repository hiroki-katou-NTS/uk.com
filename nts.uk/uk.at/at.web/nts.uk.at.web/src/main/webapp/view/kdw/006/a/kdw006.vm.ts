module nts.uk.at.view.kdw006 {
    export module viewmodel {
        export class ScreenModel extends ko.ViewModel {

            ootsuka: KnockoutObservable<boolean>;

            constructor(dataShare: any) {
                super();

                var self = this;
                self.ootsuka = ko.observable(false);
                service.start().done(function(data) {
                    self.ootsuka(data);
                });
            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }

            //---common---
            openC() {
                nts.uk.request.jump("/view/kdw/006/c/index.xhtml");
            }

            openD() {
                nts.uk.request.jump("/view/kdw/006/d/index.xhtml");
            }

            //---daily---
            open002Control() {
                let settingUnit = SettingUnit.BUSINESSTYPE;
                let isDaily = true;
                nts.uk.request.jump("/view/kdw/002/a/index.xhtml", { 
                    ShareObject: {
                        settingUnit,
                        isDaily
                    } 
                });
            }

            open002Setting() {
                let settingUnit = SettingUnit.BUSINESSTYPE;
                let isDaily = true;
                nts.uk.request.jump("/view/kdw/002/c/index.xhtml", { 
                    ShareObject: {
                        settingUnit,
                        isDaily
                    } 
                });
            }

            open008() {
                var self = this;
                
                let settingUnit = SettingUnit.BUSINESSTYPE;
                let isDaily = true;
                if (self.ootsuka()) {
                    nts.uk.request.jump("/view/kdw/008/b/index.xhtml", { 
                        ShareObject: {
                            settingUnit,
                            isDaily
                        }
                    });
                } else {
                    nts.uk.request.jump("/view/kdw/008/d/index.xhtml", { 
                        ShareObject: {
                            settingUnit,
                            isDaily
                        }
                    });
                }
            }

            openG() {
                nts.uk.request.jump("/view/kdw/006/g/index.xhtml");
            }

            open007() {
                let isDaily = true;
                nts.uk.request.jump("/view/kdw/007/a/index.xhtml", { ShareObject: isDaily });
            }

            openI() {
                nts.uk.request.jump("/view/kdw/006/i/index.xhtml");
            }

            //---monthly---
            open002ControlMonth() {
                let settingUnit = SettingUnit.BUSINESSTYPE;
                let isDaily = false;
                nts.uk.request.jump("/view/kdw/002/a/index.xhtml", { 
                    ShareObject: {
                        settingUnit,
                        isDaily
                    } 
                });
            }

            open002SettingMonth() {
                let settingUnit = SettingUnit.BUSINESSTYPE;
                let isDaily = false;
                nts.uk.request.jump("/view/kdw/002/c/index.xhtml", { 
                    ShareObject: {
                        settingUnit,
                        isDaily
                    } 
                });
            }

            open008Month() {
                var self = this;
                
                let settingUnit = SettingUnit.BUSINESSTYPE;
                let isDaily = false;
                if (self.ootsuka()) {
                    nts.uk.request.jump("/view/kdw/008/b/index.xhtml", { 
                        ShareObject: {
                            settingUnit,
                            isDaily
                        }
                    });
                } else {
                    nts.uk.request.jump("/view/kdw/008/d/index.xhtml", { 
                        ShareObject: {
                            settingUnit,
                            isDaily
                        }
                    });
                }
            }

            exportExcel(): void {
                var self = this;
                self.$blockui('grayout');
                let langId = "ja";
                service.saveAsExcelCommon(langId).done(function() {
                }).fail(function(error) {
                    self.$dialog.alert({ messageId: error.messageId });
                }).always(function() {
                    self.$blockui('hide');
                });
            }
        }

        enum SettingUnit {
            AUTHORITY,
            BUSINESSTYPE,
            EMPLOYMENT
        }
    }
}
