module nts.uk.at.view.kmk010.b {

    import OvertimeDto = nts.uk.at.view.kmk010.a.service.model.OvertimeDto;
    import OvertimeModel = nts.uk.at.view.kmk010.a.viewmodel.OvertimeModel;
    import OvertimeNameLangDto = nts.uk.at.view.kmk010.a.service.model.OvertimeNameLangDto;

    export module viewmodel {

        export class ScreenModel {
            lstOvertimeModel: OvertimeModel[];
            languageId: string;
            static LANGUAGE_ID_JAPAN = 'ja';
            textOvertimeName: KnockoutObservable<string>;
            enableCheckbox: KnockoutObservable<boolean>;

            constructor() {
                var self = this;
                self.lstOvertimeModel = [];
                self.languageId = nts.uk.ui.windows.getShared("languageId");
                self.textOvertimeName = ko.observable(nts.uk.resource.getText('KMK010_38'));
                self.enableCheckbox = ko.observable(true);
            }
            /**
             * start page when init data
             */
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                nts.uk.at.view.kmk010.a.service.findAllOvertime().done(function(data) {
                    self.lstOvertimeModel = [];
                    for (var dto of data) {
                        var model: OvertimeModel = new OvertimeModel();
                        model.updateData(dto);
                        model.updateEnableCheck(self.languageId === ScreenModel.LANGUAGE_ID_JAPAN);
                        model.setUpdateData(self.languageId === ScreenModel.LANGUAGE_ID_JAPAN);
                        self.lstOvertimeModel.push(model);
                    }
                    // equal language id 
                    if (self.languageId === ScreenModel.LANGUAGE_ID_JAPAN) {
                        self.textOvertimeName(nts.uk.resource.getText('KMK010_38'));
                        self.enableCheckbox(true);
                    } else {
                        self.textOvertimeName(nts.uk.resource.getText('KMK010_63'));
                        self.enableCheckbox(false);
                        nts.uk.at.view.kmk010.a.service.findAllOvertimeNameLanguage(self.languageId).done(function(dataLanguageName) {
                            if (dataLanguageName && dataLanguageName.length > 0) {
                                for (var dataLang of dataLanguageName) {
                                    for (var model of self.lstOvertimeModel) {
                                        if (model.overtimeNo() == dataLang.overtimeNo) {
                                            model.name(dataLang.name);
                                        }
                                    }
                                }
                            } else {
                                for (var model of self.lstOvertimeModel) {
                                    model.name('');
                                }
                            }
                        });
                    }
                    dfd.resolve(self);
                });
                return dfd.promise();
            }
            /**
             * save overtime on lick button save
             */
            private saveOvertime(): void {
                var self = this;

                if (self.validateDomainSave()) {
                    return;
                }
                // block ui.
                nts.uk.ui.block.invisible();
                if (self.languageId === ScreenModel.LANGUAGE_ID_JAPAN) {
                    // convert model to dto
                    var overtimes: OvertimeDto[] = [];
                    for (var model of self.lstOvertimeModel) {
                        overtimes.push(model.toDto());
                    }

                    // call service save all overtime
                    service.saveAllOvertime(overtimes).done(function() {
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                            nts.uk.ui.windows.setShared("isSave", 1);
                            nts.uk.ui.block.clear();
//                            nts.uk.ui.windows.close();
                        });
                    }).fail(function(error) {
                        nts.uk.ui.dialog.alertError(error);
                    });
                } else {
                    var overtimeLangNames: OvertimeNameLangDto[] = [];
                    for (var model of self.lstOvertimeModel) {
                        var dto: OvertimeNameLangDto = {
                            name: model.name(),
                            languageId: self.languageId,
                            overtimeNo: model.overtimeNo()
                        };
                        overtimeLangNames.push(dto);
                    }
                    // call service save all overtime language name
                    service.saveAllOvertimeLanguageName(overtimeLangNames).done(function() {
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                            nts.uk.ui.windows.setShared("isSave", 1);
                            nts.uk.ui.block.clear();
//                            nts.uk.ui.windows.close();
                        });
                    }).fail(function(error) {
                        nts.uk.ui.dialog.alertError(error);
                    });
                }
                self.switchNameResource();
            }
            /**
             * function validate domain save
             */
            private validateDomainSave(): boolean {
                var self = this;
                for (var model of self.lstOvertimeModel) {
                    $('#overtimeNo_' + model.overtimeNo()).ntsError("clear");
                }
                for (var model of self.lstOvertimeModel) {
                    if (model.requiredText()) {
                        $('#overtimeNo_' + model.overtimeNo()).ntsEditor("validate");
                    }
                }
                for (var model of self.lstOvertimeModel) {
                    if (model.requiredText() && $('#overtimeNo_' + model.overtimeNo()).ntsError('hasError')) {
                        return true;
                    }
                }
                return false;

            }
            
            /**
             * swtich name for element when change language
             */
            private switchNameResource(): string {
                let self = this;
                
                if (self.languageId === ScreenModel.LANGUAGE_ID_JAPAN) { 
                    return '#[KMK010_39]';
                } else {
                    return '#[KMK010_63]'; 
                }
            }
            
            /**
             * function by click button close dialog
             */
            private closeSaveOvertime(): void {
                nts.uk.ui.windows.close();
            }
        }

    }
}