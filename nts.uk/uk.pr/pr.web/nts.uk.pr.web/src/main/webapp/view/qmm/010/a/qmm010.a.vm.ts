module nts.uk.pr.view.qmm010.a.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import dialog = nts.uk.ui.dialog;
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;
    import model = nts.uk.pr.view.qmm010.share.model;
    export class ScreenModel {
        isOnStartUp: boolean = true;
        laborInsuranceOfficeList: KnockoutObservableArray<model.ILaborInsuranceOffice> = ko.observableArray([]);
        selectedLaborOfficeCode: KnockoutObservable<string> = ko.observable(null);
        selectedLaborOffice: KnockoutObservable<model.LaborInsuranceOffice> = ko.observable(new model.LaborInsuranceOffice(null));
        screenMode: KnockoutObservable<number> = ko.observable(model.SCREEN_MODE.NEW);
        constructor() {
            let self = this;
            self.selectedLaborOfficeCode.subscribe(newValue => {
                nts.uk.ui.errors.clearAll();
                if (newValue) self.showDataByOfficeCode(newValue);
            });
        }
        startPage(): JQueryPromise<any> {
            let self = this;
            return self.showAllOffice();
        }

        showAllOffice(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            service.findAllOffice().done(function(data) {
                self.laborInsuranceOfficeList(data);
                if (data && data.length) {
                    if (self.isOnStartUp) {
                        self.selectedLaborOfficeCode(data[0].laborOfficeCode);
                        self.isOnStartUp = false;
                    }
                } else {
                    self.changeToNewMode();
                    setTimeout(function(){
                        $('#A3_2').focus();
                    }, 100);
                }
                dfd.resolve();
            }).fail(function() {
                dfd.reject();
            }).always(function() {
                block.clear();
            });
            return dfd.promise();
        }

        showDataByOfficeCode (officeCode: string) {
            let self = this;
            block.invisible();
            service.findByOfficeCode(officeCode).done(function(data: model.ILaborInsuranceOffice) {
                nts.uk.ui.errors.clearAll();
                if (data) {
                    self.screenMode(model.SCREEN_MODE.UPDATE);
                    self.selectedLaborOffice(new model.LaborInsuranceOffice(data));
                    $('#A3_3').focus();
                }
                block.clear();
            }).fail(function(err) {
                dialog.alertError(err.message);
            }).always(function() {
                block.clear();
            });
        }

        searchPostalCode () {
            // TODO
        }

        createNewOffice () {
            let self = this;
           self.changeToNewMode();
        }

        changeToNewMode () {
            let self = this;
            nts.uk.ui.errors.clearAll();
            self.screenMode(model.SCREEN_MODE.NEW);
            self.selectedLaborOfficeCode(null);
            self.selectedLaborOffice(new model.LaborInsuranceOffice(null));
            $('#A3_2').focus();
        }

        registerOffice () {
            let self = this, command;
            nts.uk.ui.errors.clearAll();
            $('.nts-input').trigger("validate");
            if (nts.uk.ui.errors.hasError()) {
                return;
            }
            command = self.formatOptionalDataBeforeSubmit(ko.toJS(self.selectedLaborOffice));
            if (self.screenMode() == model.SCREEN_MODE.NEW) {
                self.addLaborInsuranceOffice(command);
            } else {
                self.updateLaborInsuranceOffice(command);
            }
        }

        addLaborInsuranceOffice (command) {
            let self = this;
            block.invisible();
            service.addLaborOffice(command).done(function(){
                dialog.info({ messageId: 'Msg_15' }).then(function() {
                    self.showAllOffice();
                    self.selectedLaborOfficeCode(command.laborOfficeCode);
                });
            }).fail(function(error){
                dialog.alertError({ messageId: error.messageId });
            }).always(function(){
                block.clear();
            });
        }

        updateLaborInsuranceOffice (command) {
            let self = this;
            block.invisible();
            service.updateLaborOffice(command).done(function(){
                dialog.info({ messageId: 'Msg_15' }).then(function() {
                    self.showAllOffice();
                    self.selectedLaborOfficeCode.valueHasMutated();
                });
            }).fail(function(error){
                block.clear();
                dialog.alertError(error.message);
            });
        }

        formatOptionalDataBeforeSubmit (command) {
            command.address1 = command.address1 == "" ? null : command.address1;
            command.address2 = command.address2 == "" ? null : command.address2;
            command.addressKana1 = command.addressKana1 == "" ? null : command.addressKana1;
            command.addressKana2 = command.addressKana2 == "" ? null : command.addressKana2;
            command.cityCode = command.cityCode == "" ? null : command.cityCode;
            command.employmentOfficeCode = command.employmentOfficeCode == "" ? null : command.employmentOfficeCode;
            command.employmentOfficeNumber1 = command.employmentOfficeNumber1 == "" ? null : command.employmentOfficeNumber1;
            command.employmentOfficeNumber2 = command.employmentOfficeNumber2 == "" ? null : command.employmentOfficeNumber2;
            command.employmentOfficeNumber3 = command.employmentOfficeNumber3 == "" ? null : command.employmentOfficeNumber3;
            command.notes = command.notes == "" ? null : command.notes;
            command.phoneNumber = command.phoneNumber == "" ? null : command.phoneNumber;
            command.postalCode = command.postalCode == "" ? null : command.postalCode;
            command.representativeName = command.representativeName == "" ? null : command.representativeName;
            return command;
        }

        printPDF () {
            // TODO
        }

        readFromSocialInsuranceOffice () {
            let self = this;
            modal("/view/qmm/010/b/index.xhtml").onClosed(() => {
                let params = getShared("QMM010_A_PARAMS");
                if (params){
                    nts.uk.ui.errors.clearAll();
                    self.selectedLaborOffice(new model.LaborInsuranceOffice(_.extend(ko.toJS(self.selectedLaborOffice), params.socialOfficeInfo)));
                }
            });
        }

        masterCorrection () {
            // TODO
        }

        deleteOffice () {
            dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                let self = this, command = ko.toJS(self.selectedLaborOffice), listOffice = ko.toJS(self.laborInsuranceOfficeList), currentIndex, newOfficeCode;
                block.invisible()
                service.removeLaborOffice(command).done(function(){
                    if (listOffice.length>1)
                    currentIndex  = _.findIndex(listOffice, {laborOfficeCode: self.selectedLaborOfficeCode()});
                    if (listOffice.length > 1) {
                        if (currentIndex == listOffice.length - 1) {
                            newOfficeCode = listOffice[currentIndex - 1].laborOfficeCode;
                        } else {
                            newOfficeCode = listOffice[currentIndex + 1].laborOfficeCode;
                        }
                    }
                    dialog.info({ messageId: 'Msg_16' }).then(function() {
                        self.showAllOffice();
                        self.selectedLaborOfficeCode(newOfficeCode);
                    });
                }).fail(function(error){
                    block.clear();
                    dialog.alertError(error.message);
                });
            }).ifNo(() => {
            });
        }
    }
}

