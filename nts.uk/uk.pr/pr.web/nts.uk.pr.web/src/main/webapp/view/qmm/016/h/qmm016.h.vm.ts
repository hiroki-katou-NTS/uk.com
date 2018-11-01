module nts.uk.pr.view.qmm016.h.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import model = nts.uk.pr.view.qmm016.share.model;
    import dialog = nts.uk.ui.dialog;
    import service = nts.uk.pr.view.qmm016.h.service;
    export class ScreenModel {
        screenMode: KnockoutObservable<model.SCREEN_MODE> = ko.observable(model.SCREEN_MODE.NEW);
        qualificationGroupList: KnockoutObservableArray<model.QualificationGroupSetting> = ko.observableArray([]);
        selectedQualificationGroupCode: KnockoutObservable<string> = ko.observable(null);
        selectedQualification: KnockoutObservable<model.QualificationGroupSetting> = ko.observable(new model.QualificationGroupSetting(null));
        isOnStartUp = true;
        columns: any;
        qualificationInformationList: KnockoutObservableArray<model.IQualificationInformation> = ko.observableArray([]);
        qualificationInformationListInString: string;
        selectedEligibleQualificationCode: KnockoutObservableArray<model.IQualificationInformation> = ko.observableArray([]);
        constructor() {
            let self = this;
            this.columns = ko.observableArray([
                { headerText: getText('QMM016_61'), key: 'qualificationCode', width: 50 },
                { headerText: getText('QMM016_62'), key: 'qualificationName', width: 150 }
            ]);
            self.selectedQualificationGroupCode.subscribe(newValue => {
                if (newValue) {
                    self.getQualificationGroupData(newValue);
                    self.qualificationInformationList((JSON.parse(self.qualificationInformationListInString)));
                }
            })
            self.screenMode.subscribe(newValue => {
                if (newValue == model.SCREEN_MODE.NEW){
                    self.changeToNewMode();
                    $('#H3_1').focus();
                }
                nts.uk.ui.errors.clearAll();
            })
        }
        startPage () : JQueryPromise<any> {
            let self = this;
            return self.getAllQualificationGroupAndInformation();
        }
        getAllQualificationGroupAndInformation () : JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            block.invisible();
            service.getAllQualificationGroupAndInformation().done(function(data) {
                let qualificationGroupData: any = [
                    {
                        qualificationGroupCode: '01',
                        paymentMethod: 0,
                        eligibleQualificationCode: ['001','002'],
                        qualificationGroupName: 'Group Name 1'
                    },
                    {
                        qualificationGroupCode: '02',
                        paymentMethod: 0,
                        eligibleQualificationCode: ['001','002'],
                        qualificationGroupName: 'Group Name 2'
                    },
                    {
                        qualificationGroupCode: '03',
                        paymentMethod: 0,
                        eligibleQualificationCode: ['004','005'],
                        qualificationGroupName: 'Group Name 3'
                    }
                ]
                self.qualificationGroupList(qualificationGroupData);
                let qualificationInformationData = [];
                for(var i = 1; i < 20; i++) {
                    qualificationInformationData.push({qualificationCode: nts.uk.text.padLeft(i+"", '0', 3), qualificationName: 'Name ' + i})
                }
                self.qualificationInformationList(qualificationInformationData);
                self.qualificationInformationListInString = JSON.stringify(qualificationInformationData);

                if (qualificationGroupData.length > 0){
                    self.selectedQualificationGroupCode(qualificationGroupData[0].qualificationGroupCode);
                } else {
                    self.screenMode(model.SCREEN_MODE.NEW);
                }
                dfd.resolve();
            }).fail(function() {
                dfd.reject();
            }).always(function() {
                block.clear();
            });
            return dfd.promise();
        }
        getAllQualificationGroup () : JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            block.invisible();
            service.getAllQualificationGroup().done(function(data) {
                dfd.resolve();
            }).fail(function() {
                dfd.reject();
            }).always(function() {
                block.clear();
            });
            return dfd.promise();
        }
        getQualificationGroupData (qualificationGroupCode) {
            let self = this;
            block.invisible();
            service.getQualificationGroupByCode(qualificationGroupCode).done(function(data) {
                let selectedQualificationGroup: any = _.find(self.qualificationGroupList(), {qualificationGroupCode: self.selectedQualificationGroupCode()});
                self.selectedEligibleQualificationCode(JSON.parse(self.qualificationInformationListInString).filter(item => selectedQualificationGroup.eligibleQualificationCode.indexOf(item.qualificationCode) >=0));
                self.selectedQualification(new model.QualificationGroupSetting(selectedQualificationGroup));
                self.screenMode(model.SCREEN_MODE.UPDATE);
                $('#H3_2').focus();
                block.clear();
            }).fail(function() {
            }).always(function() {
                block.clear();
                nts.uk.ui.errors.clearAll();
            });
        }

        addQualificationGroup (command) {
            let self = this;
            block.invisible();
            service.addQualificationGroup(command).done(function(){
                dialog.info({ messageId: 'Msg_15' }).then(function() {
                    // update after add
                    self.getAllQualificationGroup();

                    self.selectedQualificationGroupCode(command.qualificationGroupCode);
                });
            }).fail(function(error){
                dialog.alertError({ messageId: error.messageId });
            }).always(function(){
                block.clear();
            });
        }

        updateQualificationGroup (command) {
            let self = this;
            block.invisible();
            service.updateQualificationGroup(command).done(function(){
                dialog.info({ messageId: 'Msg_15' }).then(function() {
                    // update after add
                    self.getAllQualificationGroup();
                    self.selectedQualificationGroupCode(command.qualificationGroupCode);
                });
            }).fail(function(error){
                dialog.alertError({ messageId: error.messageId });
            }).always(function(){
                block.clear();
            });
        }
        createQualificationGroup () {
            let self = this;
            self.screenMode(model.SCREEN_MODE.NEW);
        }
        registerQualificationGroup () {
            let self = this;
            let selectedQualification = ko.toJS(self.selectedQualification), selectedEligibleQualificationCode = ko.toJS(self.selectedEligibleQualificationCode);
            selectedQualification.eligibleQualificationCode = selectedEligibleQualificationCode.map(item => item.qualificationCode);
            if (self.screenMode() == model.SCREEN_MODE.NEW) self.addQualificationGroup(selectedQualification);
            else self.updateQualificationGroup(selectedQualification);
        }
        deleteQualificationGroup () {
            let self = this;
            block.invisible();
            let selectedQualificationGroup = ko.toJS(self.selectedQualification);
            service.deleteQualificationGroup(selectedQualificationGroup).done(function(){
                dialog.info({ messageId: 'Msg_15' }).then(function() {
                    // update after add
                    self.getAllQualificationGroup();
                    // select new item
                    // tam thoi chua lam
                });
            }).fail(function(error){
                dialog.alertError({ messageId: error.messageId });
            }).always(function(){
                block.clear();
            });
        }
        closeDialog () {
            nts.uk.ui.windows.close();
        }
        changeToNewMode () {
            let self = this;
            self.selectedQualificationGroupCode(null);
            self.selectedQualification(new model.QualificationGroupSetting(null));
        }
    }
}


