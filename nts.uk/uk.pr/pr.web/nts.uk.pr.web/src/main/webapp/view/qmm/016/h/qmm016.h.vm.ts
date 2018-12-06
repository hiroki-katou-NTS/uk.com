module nts.uk.pr.view.qmm016.h.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import model = nts.uk.pr.view.qmm016.share.model;
    import dialog = nts.uk.ui.dialog;
    import service = nts.uk.pr.view.qmm016.h.service;

    export class ScreenModel {
        screenMode: KnockoutObservable<model.SCREEN_MODE> = ko.observable(null);
        qualificationGroupList: KnockoutObservableArray<model.QualificationGroupSetting> = ko.observableArray([]);
        selectedQualificationGroupCode: KnockoutObservable<string> = ko.observable(null);
        selectedQualification: KnockoutObservable<model.QualificationGroupSetting> = ko.observable(new model.QualificationGroupSetting(null));
        columns: any;
        qualificationInformationList: KnockoutObservableArray<model.IQualificationInformation> = ko.observableArray([]);
        qualificationInformationListInString: string;
        selectedEligibleQualificationCode: KnockoutObservableArray<model.IQualificationInformation> = ko.observableArray([]);

        constructor() {
            let self = this;
            this.columns = ko.observableArray([
                {headerText: getText('QMM016_61'), key: 'qualificationCode', width: 50},
                {headerText: getText('QMM016_62'), key: 'qualificationName', width: 150}
            ]);
            self.selectedQualificationGroupCode.subscribe(newValue => {
                if (newValue) {
                    self.getQualificationGroupData(newValue);
                }
            })
            self.screenMode.subscribe(newValue => {
                if (newValue == model.SCREEN_MODE.NEW) {
                    self.changeToNewMode();
                }
                nts.uk.ui.errors.clearAll();
            })
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            return self.getAllQualificationGroupAndInformation();
        }

        getAllQualificationGroupAndInformation(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            block.invisible();
            service.getAllQualificationGroupAndInformation().done(function (data) {
                dfd.resolve();
                let qualificationGroupData = data[0], qualificationInformationData = data[1];
                self.qualificationGroupList(qualificationGroupData);
                self.qualificationInformationList(qualificationInformationData);
                self.qualificationInformationListInString = JSON.stringify(qualificationInformationData);
                if (qualificationGroupData.length > 0) {
                    self.selectedQualificationGroupCode(qualificationGroupData[0].qualificationGroupCode);
                } else {
                    self.screenMode(model.SCREEN_MODE.NEW);
                }
            }).fail(function () {
                dfd.reject();
            }).always(function () {
                block.clear();
            });
            return dfd.promise();
        }

        getAllQualificationGroup() {
            let self = this;
            block.invisible();
            service.getAllQualificationGroup().done(function (data) {
                let qualificationGroupData = data;
                self.qualificationGroupList(qualificationGroupData);
                if (qualificationGroupData.length == 0) {
                    self.screenMode(model.SCREEN_MODE.NEW);
                }
            }).fail(function () {
            }).always(function () {
                block.clear();
            });
        }

        getQualificationGroupData(qualificationGroupCode) {
            let self = this;
            block.invisible();
            service.getQualificationGroupByCode(qualificationGroupCode).done(function (data) {
                let selectedQualificationGroup: any = data;
                self.selectedEligibleQualificationCode(JSON.parse(self.qualificationInformationListInString).filter(item => selectedQualificationGroup.eligibleQualificationCode.indexOf(item.qualificationCode) >= 0));
                self.selectedQualification(new model.QualificationGroupSetting(selectedQualificationGroup));
                self.screenMode(model.SCREEN_MODE.UPDATE);
                self.qualificationInformationList((JSON.parse(self.qualificationInformationListInString)));
                $('#H3_2').focus();
                block.clear();
            }).fail(function () {
            }).always(function () {
                block.clear();
                nts.uk.ui.errors.clearAll();
            });
        }

        addQualificationGroup(command) {
            let self = this;
            block.invisible();
            service.addQualificationGroup(command).done(function () {
                dialog.info({messageId: 'Msg_15'}).then(function () {
                    // update after add
                    self.getAllQualificationGroup();
                    self.selectedQualificationGroupCode(command.qualificationGroupCode);
                });
            }).fail(function (error) {
                dialog.alertError({messageId: error.messageId});
            }).always(function () {
                block.clear();
            });
        }

        updateQualificationGroup(command) {
            let self = this;
            block.invisible();
            service.updateQualificationGroup(command).done(function () {
                dialog.info({messageId: 'Msg_15'}).then(function () {
                    self.getAllQualificationGroup();
                    self.selectedQualificationGroupCode.valueHasMutated();
                });
            }).fail(function (error) {
                dialog.alertError({messageId: error.messageId});
            }).always(function () {
                block.clear();
            });
        }

        createQualificationGroup() {
            let self = this;
            self.screenMode(model.SCREEN_MODE.NEW);
        }

        registerQualificationGroup() {
            let self = this;
            let selectedQualification = ko.toJS(self.selectedQualification),
                selectedEligibleQualificationCode = ko.toJS(self.selectedEligibleQualificationCode);
            selectedQualification.eligibleQualificationCode = selectedEligibleQualificationCode.map(item => item.qualificationCode);
            if (self.screenMode() == model.SCREEN_MODE.NEW) self.addQualificationGroup(selectedQualification);
            else self.updateQualificationGroup(selectedQualification);
        }

        deleteQualificationGroup() {
            let self = this;
            dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                block.invisible();
                let selectedQualificationGroup = ko.toJS(self.selectedQualification), qualificationGroupList = ko.toJS(self.qualificationGroupList), currentIndex, newQualificationGroupCode;
                service.deleteQualificationGroup(selectedQualificationGroup).done(function () {
                    // find new item
                    if (qualificationGroupList.length > 1)
                        currentIndex = _.findIndex(qualificationGroupList, {qualificationGroupCode: self.selectedQualificationGroupCode()});
                    if (currentIndex == qualificationGroupList.length - 1) {
                        newQualificationGroupCode = qualificationGroupList[currentIndex - 1].qualificationGroupCode;
                    } else {
                        newQualificationGroupCode = qualificationGroupList[currentIndex + 1].qualificationGroupCode;
                    }
                    dialog.info({messageId: 'Msg_16'}).then(function () {
                        // update after delete
                        self.getAllQualificationGroup();
                        self.selectedQualificationGroupCode(newQualificationGroupCode);
                    });
                }).fail(function (error) {
                    dialog.alertError({messageId: error.messageId});
                }).always(function () {
                    block.clear();
                });
            }).ifNo(() => {
            });

        }

        closeDialog() {
            nts.uk.ui.windows.close();
        }

        changeToNewMode() {
            let self = this;
            self.selectedQualificationGroupCode(null);
            self.selectedQualification(new model.QualificationGroupSetting(null));
            self.selectedEligibleQualificationCode([]);
            self.qualificationInformationList((JSON.parse(self.qualificationInformationListInString)));
            $('#H3_1').focus();
        }
    }
}


