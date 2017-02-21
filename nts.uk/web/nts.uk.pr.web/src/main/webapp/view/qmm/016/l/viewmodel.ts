module nts.uk.pr.view.qmm016.l {
    import option = nts.uk.ui.option;
    import MultipleTargetSettingDto = service.model.MultipleTargetSettingDto;
    import MultipleTargetSetting = service.model.MultipleTargetSetting;
    import CertificationFindInDto = service.model.CertificationFindInDto;
    import CertifyGroupFindInDto = service.model.CertifyGroupFindInDto;
    import CertifyGroupFindOutDto = service.model.CertifyGroupFindOutDto;
    import CertifyGroupDto = service.model.CertifyGroupDto;
    import TypeActionCertifyGroup = service.model.TypeActionCertifyGroup;
    import CertifyGroupDeleteDto = service.model.CertifyGroupDeleteDto;
    export module viewmodel {
        export class ScreenModel {
            textSearch: any;
            enableButton: KnockoutObservable<boolean>;
            //update add LaborInsuranceOffice
            typeAction: KnockoutObservable<number>;
            selectedMultipleTargetSetting: KnockoutObservable<number>;
            selectLstCodeLstCertification: KnockoutObservableArray<string>;
            lstCertifyGroup: KnockoutObservableArray<CertifyGroupFindInDto>;
            columnsLstCertifyGroup: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            selectCodeLstLstCertifyGroup: KnockoutObservable<string>;
            lstCertification: CertificationFindInDto[];
            //Info CertifyGroup (DTO View)
            certifyGroupModel: KnockoutObservable<CertifyGroupModel>;
            textEditorOption: KnockoutObservable<any>;
            isEmpty: KnockoutObservable<boolean>;
            constructor() {
                var self = this;
                self.columnsLstCertifyGroup = ko.observableArray([
                    { headerText: 'コード', prop: 'code', width: 120 },
                    { headerText: '名称', prop: 'name', width: 120 }
                ]);
                self.currentCode = ko.observable();
                self.currentCodeList = ko.observableArray([]);
                self.textSearch = {
                    valueSearch: ko.observable(""),
                    option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                        textmode: "text",
                        placeholder: "コード・名称で検索・・・",
                        width: "270",
                        textalign: "left"
                    }))
                }
                self.enableButton = ko.observable(true);
                self.selectedMultipleTargetSetting = ko.observable(MultipleTargetSetting.BigestMethod);
                self.selectCodeLstLstCertifyGroup = ko.observable('');
                self.selectLstCodeLstCertification = ko.observableArray([]);
                self.lstCertificationInfo = ko.observableArray([]);
                self.selectLstCodeLstCertificationInfo = ko.observableArray([]);
                self.typeAction = ko.observable(TypeActionCertifyGroup.update);
                self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                self.isEmpty = ko.observable(true);
            }
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                self.findAllCertifyGroup().done(data => {
                    self.findAllCertification().done(data => {
                        dfd.resolve(self);
                    });

                });
                return dfd.promise();
            }

            private findAllCertification(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.findAllCertification().done(data => {
                    self.lstCertification = data;
                    dfd.resolve(self);
                });
                return dfd.promise();
            }
            private findAllCertifyGroup(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.findAllCertifyGroup().done(data => {
                    if (data != null && data.length > 0) {
                        self.lstCertifyGroup = ko.observableArray<CertifyGroupFindInDto>(data);
                        self.selectCodeLstLstCertifyGroup = ko.observable(data[0].code);
                        self.selectCodeLstLstCertifyGroup.subscribe(function(selectionCodeLstLstCertifyGroup: string) {
                            self.showchangeCertifyGroup(selectionCodeLstLstCertifyGroup);
                        });
                        self.findCertifyGroup(data[0].code).done(data => {
                            dfd.resolve(self);
                        });
                    } else {
                        self.newmodeEmptyData();
                        self.lstCertifyGroup = ko.observableArray([]);
                        dfd.resolve(self);
                    }
                });
                return dfd.promise();
            }
            private findCertifyGroup(code: string): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.findCertifyGroup(code).done(data => {
                    self.certifyGroupModel = ko.observable(new CertifyGroupModel(data));
                    var dataClear: CertificationFindInDto = data.certifies;
                    service.findAllCertification().done(data => {
                        self.certifyGroupModel().lstCertification(self.clearCertifyGroupFind(data, dataClear));
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
                            self.selectCodeLstLstCertifyGroup = ko.observable(code);
                            self.selectCodeLstLstCertifyGroup.subscribe(function(selectionCodeLstLstCertifyGroup: string) {
                                self.showchangeCertifyGroup(selectionCodeLstLstCertifyGroup);
                            });
                            self.isEmpty(false);
                        }
                        self.certifyGroupModel().code(data.code);
                        self.certifyGroupModel().name(data.name);
                        self.certifyGroupModel().multiApplySet(data.multiApplySet);
                        self.certifyGroupModel().certifies(data.certifies);
                        self.certifyGroupModel().lstCertification(self.clearCertifyGroupFind(self.lstCertification, data.certifies));
                        self.typeAction(TypeActionCertifyGroup.update);
                    });
                }
            }
            private clearCertifyGroupFind(datafull: CertificationFindInDto[], dataClear: CertificationFindInDto[]): CertificationFindInDto[] {
                var i = -1;
                var dataSetup: CertificationFindInDto[] = [];
                for (var itemOfDataFull of datafull) {
                    i++;
                    var exitClear = 1;
                    for (var itemOfDataClear of dataClear) {
                        if (itemOfDataFull.code === itemOfDataClear.code) {
                            exitClear = 0;
                            break;
                        }
                    }
                    if (exitClear == 1) {
                        dataSetup.push(itemOfDataFull);
                    }
                }
                return dataSetup;
            }
            //show CertifyGroup (change event)
            private showchangeCertifyGroup(selectionCodeLstLstCertifyGroup: string) {
                var self = this;
                self.detailCertifyGroup(selectionCodeLstLstCertifyGroup);
            }
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
                self.certifyGroupModel().certifies([]);
                service.findAllCertification().done(data => {
                    self.certifyGroupModel().lstCertification(data);
                });

            }
            private saveCertifyGroup() {
                var self = this;
                if (self.typeAction() == TypeActionCertifyGroup.add) {
                    service.addCertifyGroup(self.convertDataModel()).done(data => {
                        self.reloadDataByAction(self.certifyGroupModel().code());
                    });
                } else {
                    service.updateCertifyGroup(self.convertDataModel()).done(data => {
                        self.reloadDataByAction(self.certifyGroupModel().code());
                    });
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
                });
            }
            private deleteCertifyGroup() {
                var self = this;
                var certifyGroupDeleteDto: CertifyGroupDeleteDto = new CertifyGroupDeleteDto();
                certifyGroupDeleteDto.groupCode = self.certifyGroupModel().code();
                certifyGroupDeleteDto.version = 12;
                service.deleteCertifyGroup(certifyGroupDeleteDto).done(data => {
                    self.reloadDataByAction('');
                });
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
        }
        export class CertifyGroupModel {
            code: KnockoutObservable<string>;
            name: KnockoutObservable<string>;
            multiApplySet: KnockoutObservable<number>;
            certifies: KnockoutObservableArray<CertificationFindInDto>;
            lstCertification: KnockoutObservableArray<CertificationFindInDto>;
            columnsCertification: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            selectionMultipleTargetSetting: KnockoutObservableArray<MultipleTargetSettingDto>;
            constructor(certifyGroupDto: CertifyGroupDto) {
                this.code = ko.observable(certifyGroupDto.code);
                this.name = ko.observable(certifyGroupDto.name);
                this.multiApplySet = ko.observable(certifyGroupDto.multiApplySet);
                this.columnsCertification = ko.observableArray([
                    { headerText: 'コード', prop: 'code', width: 60 },
                    { headerText: '名称', prop: 'name', width: 180 }
                ]);
                this.selectionMultipleTargetSetting = ko.observableArray<MultipleTargetSettingDto>(
                    [new MultipleTargetSettingDto(MultipleTargetSetting.BigestMethod, "BigestMethod"),//"BigestMethod 
                        new MultipleTargetSettingDto(MultipleTargetSetting.TotalMethod, "TotalMethod")//TotalMethod
                    ]);//TotalMethod
                this.certifies = ko.observableArray<CertificationFindInDto>(certifyGroupDto.certifies);
                this.lstCertification = ko.observableArray<CertificationFindInDto>([]);
            }
            setLstCertification(lstCertification: CertificationFindInDto[]) {
                this.lstCertification = ko.observableArray<CertificationFindInDto>(lstCertification);
            }
        }
    }