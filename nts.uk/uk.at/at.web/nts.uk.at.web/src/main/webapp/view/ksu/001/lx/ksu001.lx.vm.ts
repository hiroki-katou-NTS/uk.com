module nts.uk.at.view.ksu001.lx.viewmodel {
    import getShare = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        listTeam: KnockoutObservableArray<any>;
        selectedTeam: KnockoutObservable<any> = ko.observable();
        columnsTeam: KnockoutObservableArray<NtsGridListColumn> = ko.observableArray([
            { headerText: nts.uk.resource.getText("KSU001_1110"), key: 'code', width: 60 },
            { headerText: nts.uk.resource.getText("KSU001_1111"), key: 'name', width: 120, formatter: _.escape }
        ]);
        teamCode: KnockoutObservable<string>;
        teamName: KnockoutObservable<string>;
        workPlaceId: string;
        isEnable: KnockoutObservable<boolean>;
        isCreated: KnockoutObservable<boolean>;
        enableCode: KnockoutObservable<boolean>;
        index: KnockoutObservable<number>;
        
        constructor() {
            let self = this;
            
            self.teamCode = ko.observable('');
            self.teamName = ko.observable('');
            self.listTeam = ko.observableArray([]);
            self.workPlaceId = nts.uk.ui.windows.getShared('workPlaceId');
            self.isEnable = ko.observable(true);
            self.isCreated = ko.observable(false);
            self.enableCode = ko.observable(false);
            self.selectedTeam = ko.observable();
            self.index = ko.observable(0);
            self.selectedTeam.subscribe(function(newValue) {
                nts.uk.ui.errors.clearAll();
                let currentItem = _.find(self.listTeam(), { 'code': newValue });
                if (newValue) {
                    self.enableCode(false);
                    self.isCreated(false);
                    self.index(_.findIndex(self.listTeam(), ['code', newValue]));
                    self.teamCode(currentItem.code);
                    self.teamName(currentItem.name);
                    $("#input-teamName").focus();
                } else {
                    self.isCreated(true);
                    self.enableCode(true);
                    $("#input-teamCode").focus();
                }
            });
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            let dfd = $.Deferred();
            self.getAllTeam().done(() => {
                if (self.listTeam().length > 0) {
                    self.selectedTeam(self.listTeam()[0].code);
                } else {
                    self.cleanForm();
                }
                dfd.resolve();
            }).fail(() => {
                dfd.reject();
            });
            return dfd.promise();
        }
        
        /**
         * Close dialog
         */
        closeDialog(): void {
            nts.uk.ui.windows.close();
        }
        
        /**
         * clean form
         */
        cleanForm(): void {
            var self = this;
            nts.uk.ui.errors.clearAll();
            self.enableCode(true);
            self.teamCode('');
            self.teamName('');
            self.selectedTeam("");
        }
        
        /**
         * save team
         */
        saveData(): any {
            let self = this;
            $("#input-teamCode").trigger("validate");
            $("#input-teamName").trigger("validate");
            if (!nts.uk.ui.errors.hasError()) {
                nts.uk.ui.block.invisible();
                let teamCode = self.teamCode();
                let teamName = self.teamName();
                let team = {
                    workPlaceId: self.workPlaceId,
                    teamCode: teamCode,
                    teamName: teamName
                };
                service.saveTeam(self.isCreated(), team).done(function() {
                    self.isCreated(false);
                    self.getAllTeam().done(function() {
                        self.selectedTeam(teamCode);
                    });
                    nts.uk.ui.dialog.info(nts.uk.resource.getMessage('Msg_15')).then(() => {
                        $("#input-teamName").focus();
                    });;
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message).then(() => {
                        nts.uk.ui.block.clear();
                        $("#input-teamCode").focus();
                    });
                }).then(function() {
                    nts.uk.ui.block.clear();
                });
            }
        }
        
        /**
         * remove team 
         */
        removeData(): any {
            let self = this;
            nts.uk.ui.block.grayout();
            let teamCode = self.teamCode();
            let teamName = self.teamName();
            let team = {
                workPlaceId: self.workPlaceId,
                teamCode: teamCode,
                teamName: teamName
            };
            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                service.removeTeam(team).done(function() {
                    nts.uk.ui.dialog.info(nts.uk.resource.getMessage('Msg_16')).then(function() {
                        self.getAllTeam().done(function() {
                            if (self.listTeam().length == 0) {
                                self.cleanForm();
                            } else if (self.index() == self.listTeam().length) {
                                self.selectedTeam(self.listTeam()[self.index() - 1].code);
                            } else {
                                self.selectedTeam(self.listTeam()[self.index()].code);
                            }
                        });
                    });
                }).fail(function(error) {
                    self.isCreated(false);
                    nts.uk.ui.dialog.alertError(error.messageId);
                });
            }).then(function() {
                nts.uk.ui.block.clear();
            });
        }
        
        /**
         * get all team
         */
        getAllTeam(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            service.findAllByWorkPlace(self.workPlaceId).done(data => {
                let arrayTeam = [];
                _.forEach(data, value => {
                    let item = new ItemModel(value.teamCode, value.teamName);
                    arrayTeam.push(item);
                });
                self.listTeam(arrayTeam);
                dfd.resolve();
            })
                .fail(() => {
                    dfd.reject();
                });
            return dfd.promise();
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
} 