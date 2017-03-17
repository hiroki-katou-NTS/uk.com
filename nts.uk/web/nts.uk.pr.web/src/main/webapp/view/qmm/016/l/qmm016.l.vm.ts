module nts.uk.pr.view.qmm016.l {

    import option = nts.uk.ui.option;
    import MultipleTargetSettingDto = service.model.MultipleTargetSettingDto;
    import MultipleTargetSetting = service.model.MultipleTargetSetting;
    import CertificationFindInDto = service.model.CertificationFindInDto;
    import CertifyGroupFindOutDto = service.model.CertifyGroupFindOutDto;
    import CertifyGroupDto = service.model.CertifyGroupDto;
    import TypeActionCertifyGroup = service.model.TypeActionCertifyGroup;
    import CertifyGroupDeleteDto = service.model.CertifyGroupDeleteDto;

    export module viewmodel {

        export class ScreenModel {

            enableButton: KnockoutObservable<boolean>;
            //update add LaborInsuranceOffice
            typeAction: KnockoutObservable<number>;
            selectedMultipleTargetSetting: KnockoutObservable<number>;
            selectLstCodeLstCertification: KnockoutObservableArray<string>;
            lstCertifyGroup: KnockoutObservableArray<CertifyGroupFindOutDto>;
            columnsLstCertifyGroup: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            selectCodeLstLstCertifyGroup: KnockoutObservable<string>;
            lstCertification: CertificationFindInDto[];
            //Info CertifyGroup (DTO View)
            certifyGroupModel: KnockoutObservable<CertifyGroupModel>;
            textEditorOption: KnockoutObservable<option.TextEditorOption>;
            isEmpty: KnockoutObservable<boolean>;

            constructor() {
                var self = this;
                self.columnsLstCertifyGroup = ko.observableArray<nts.uk.ui.NtsGridListColumn>([
                    { headerText: 'コード', prop: 'code', width: 120 },
                    { headerText: '名称', prop: 'name', width: 120 }
                ]);
                self.selectedMultipleTargetSetting = ko.observable(MultipleTargetSetting.BigestMethod);
                self.selectCodeLstLstCertifyGroup = ko.observable('');
                self.selectLstCodeLstCertification = ko.observableArray([]);
                self.typeAction = ko.observable(TypeActionCertifyGroup.update);
                self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                self.isEmpty = ko.observable(true);
            }

            //start page init data begin load page
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                self.findAllCertifyGroup().done(function() {
                    self.findAllCertification().done(data => {
                        dfd.resolve(data);
                    });

                });
                return dfd.promise();
            }

            // find all Certification connection service
            private findAllCertification(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.findAllCertification().done(data => {
                    self.lstCertification = data;
                    dfd.resolve(self);
                });
                return dfd.promise();
            }

            //find all CertifyGroup connection service
            private findAllCertifyGroup(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.findAllCertifyGroup().done(data => {
                    if (data != null && data.length > 0) {
                        self.lstCertifyGroup = ko.observableArray<CertifyGroupFindOutDto>(data);
                        self.selectCodeLstLstCertifyGroup(data[0].code);
                        self.selectCodeLstLstCertifyGroup.subscribe(function(selectionCodeLstLstCertifyGroup: string) {
                            self.showchangeCertifyGroup(selectionCodeLstLstCertifyGroup);
                        });
                        self.findCertifyGroup(data[0].code).done(res => {
                            dfd.resolve(res);
                        });
                    } else {
                        self.newmodeEmptyData();
                        self.lstCertifyGroup = ko.observableArray([]);
                        dfd.resolve(self);
                    }
                });
                return dfd.promise();
            }

            //detail CertifyGroup by code
            private findCertifyGroup(code: string): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.findCertifyGroup(code).done(data => {
                    self.certifyGroupModel = ko.observable(new CertifyGroupModel(data));
                    service.findAllCertification().done(data => {
                        self.certifyGroupModel().lstCertification(data);
                        self.lstCertification = data;
                        dfd.resolve(self);
                    });

                });
                return dfd.promise();
            }

            private detailCertifyGroup(code: string) {
                if (code != null && code != undefined && code != '') {
                    var self = this;
                    service.findCertifyGroup(code).done(data => {
                        if (self.isEmpty()) {
                            self.selectCodeLstLstCertifyGroup(code);
                            self.selectCodeLstLstCertifyGroup.subscribe(function(selectionCodeLstLstCertifyGroup: string) {
                                self.showchangeCertifyGroup(selectionCodeLstLstCertifyGroup);
                            });
                            self.isEmpty(false);
                        }
                        self.certifyGroupModel().code(data.code);
                        self.certifyGroupModel().name(data.name);
                        self.certifyGroupModel().multiApplySet(data.multiApplySet);
                        self.certifyGroupModel().certifies(data.certifies);
                        self.typeAction(TypeActionCertifyGroup.update);
                        self.certifyGroupModel().setReadOnly(true);
                        service.findAllCertification().done(dataCertification => {
                            self.certifyGroupModel().setLstCertification(dataCertification);
                        });
                    });
                }
            }

            //show CertifyGroup (change event)
            private showchangeCertifyGroup(selectionCodeLstLstCertifyGroup: string) {
                var self = this;
                self.detailCertifyGroup(selectionCodeLstLstCertifyGroup);
            }

            //reset value => begin add button
            private resetValueCertifyGroup() {
                var self = this;
                if (self.certifyGroupModel == null || self.certifyGroupModel == undefined) {
                    self.certifyGroupModel = ko.observable(new CertifyGroupModel(new CertifyGroupDto()));
                }
                self.certifyGroupModel().code('');
                self.certifyGroupModel().name('');
                self.certifyGroupModel().multiApplySet(MultipleTargetSetting.BigestMethod);
                self.typeAction(TypeActionCertifyGroup.add);
                self.selectCodeLstLstCertifyGroup('');
                self.certifyGroupModel().setReadOnly(false);
                self.certifyGroupModel().certifies([]);
                service.findAllCertification().done(data => {
                    self.certifyGroupModel().lstCertification(data);
                });
            }

            private saveCertifyGroup() {
                var self = this;
                if (self.typeAction() == TypeActionCertifyGroup.add) {
                    service.addCertifyGroup(self.convertDataModel()).done(function() {
                        self.reloadDataByAction(self.certifyGroupModel().code());
                    }).fail(function(error) {
                        self.showMessageSave(error.message);
                        self.reloadDataByAction('');
                    })
                } else {
                    service.updateCertifyGroup(self.convertDataModel()).done(function() {
                        self.reloadDataByAction(self.certifyGroupModel().code());
                        self.clearErrorSave();
                    }).fail(function(error) {
                        self.showMessageSave(error.message);
                        self.reloadDataByAction(self.certifyGroupModel().code());
                    })
                }
            }

            //reload action
            private reloadDataByAction(code: string) {
                var self = this;
                service.findAllCertifyGroup().done(data => {
                    self.lstCertifyGroup(data);
                    if (code != null && code != undefined && code != '') {
                        self.selectCodeLstLstCertifyGroup(code);
                        self.detailCertifyGroup(code);
                    } else {
                        if (data != null && data.length > 0) {
                            self.selectCodeLstLstCertifyGroup(data[0].code);
                            self.detailCertifyGroup(data[0].code);
                        } else {
                            self.newmodeEmptyData();
                            self.lstCertifyGroup = ko.observableArray([]);
                        }
                    }
                });
            }

            //new mode empty data
            private newmodeEmptyData() {
                var self = this;
                service.findAllCertification().done(data => {
                    self.lstCertification = data;
                    self.resetValueCertifyGroup();
                    self.isEmpty(true);
                    self.certifyGroupModel().setReadOnly(false);
                });
            }

            private deleteCertifyGroup() {
                var self = this;
                nts.uk.ui.dialog.confirm("Do you delete Item?").ifYes(function() {
                    var certifyGroupDeleteDto: CertifyGroupDeleteDto = new CertifyGroupDeleteDto();
                    certifyGroupDeleteDto.groupCode = self.certifyGroupModel().code();
                    certifyGroupDeleteDto.version = 12;
                    service.deleteCertifyGroup(certifyGroupDeleteDto).done(data => {
                        self.reloadDataByAction('');
                    });
                }).ifNo(function() {
                    self.reloadDataByAction(self.selectCodeLstLstCertifyGroup());
                }).ifCancel(function() {
                    self.reloadDataByAction(self.selectCodeLstLstCertifyGroup());
                }).then(function() {
                    self.reloadDataByAction(self.selectCodeLstLstCertifyGroup());
                })
            }

            private closeCertifyGroup() {
                nts.uk.ui.windows.close();
            }

            //convert data model => Dto
            private convertDataModel(): CertifyGroupDto {
                var self = this;
                var certifyGroupDto: CertifyGroupDto = new CertifyGroupDto();
                certifyGroupDto.code = self.certifyGroupModel().code();
                certifyGroupDto.name = self.certifyGroupModel().name();
                certifyGroupDto.multiApplySet = self.certifyGroupModel().multiApplySet();
                certifyGroupDto.certifies = self.certifyGroupModel().certifies();
                return certifyGroupDto;
            }

            //show message by connection service => respone error
            private showMessageSave(message: string) {
                $('#btn_saveCertifyGroup').ntsError('set', message);
                nts.uk.ui.dialog.alert(message)
            }

            //clear error view 
            private clearErrorSave() {
                var self = this;
                $('.save-error').ntsError('clear');
                $('#btn_saveCertifyGroup').ntsError('clear');
            }
        }

        export class CertifyGroupModel {

            code: KnockoutObservable<string>;
            name: KnockoutObservable<string>;
            isReadOnly: KnockoutObservable<boolean>;
            isEnable: KnockoutObservable<boolean>;
            multiApplySet: KnockoutObservable<number>;
            certifies: KnockoutObservableArray<CertificationFindInDto>;
            lstCertification: KnockoutObservableArray<CertificationFindInDto>;
            columnsCertification: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            selectionMultipleTargetSetting: KnockoutObservableArray<MultipleTargetSettingDto>;

            constructor(certifyGroupDto: CertifyGroupDto) {
                this.code = ko.observable(certifyGroupDto.code);
                this.name = ko.observable(certifyGroupDto.name);
                this.multiApplySet = ko.observable(certifyGroupDto.multiApplySet);
                this.columnsCertification = ko.observableArray<nts.uk.ui.NtsGridListColumn>([
                    { headerText: 'コード', prop: 'code', width: 60 },
                    { headerText: '名称', prop: 'name', width: 180 }
                ]);
                this.selectionMultipleTargetSetting = ko.observableArray<MultipleTargetSettingDto>(
                    [new MultipleTargetSettingDto(MultipleTargetSetting.BigestMethod, "BigestMethod"),//"BigestMethod 
                        new MultipleTargetSettingDto(MultipleTargetSetting.TotalMethod, "TotalMethod")//TotalMethod
                    ]);//TotalMethod
                this.certifies = ko.observableArray<CertificationFindInDto>(certifyGroupDto.certifies);
                this.lstCertification = ko.observableArray<CertificationFindInDto>([]);
                this.isReadOnly = ko.observable(true);
                this.isEnable = ko.observable(false);
            }

            setLstCertification(lstCertification: CertificationFindInDto[]) {
                this.lstCertification(lstCertification);
            }

            setReadOnly(readonly: boolean) {
                this.isReadOnly(readonly);
                this.isEnable(!readonly);
            }
        }
    }
}