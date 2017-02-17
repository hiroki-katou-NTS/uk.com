module nts.uk.pr.view.qmm016.l {
    import option = nts.uk.ui.option;
    import MultipleTargetSettingDto = service.model.MultipleTargetSettingDto;
    import MultipleTargetSetting = service.model.MultipleTargetSetting;
    import CertificationFindInDto = service.model.CertificationFindInDto;
    import CertifyGroupFindInDto = service.model.CertifyGroupFindInDto;
    import CertifyGroupFindOutDto = service.model.CertifyGroupFindOutDto;
    import CertifyGroupDto = service.model.CertifyGroupDto;
    import TypeActionCertifyGroup = service.model.TypeActionCertifyGroup;
    export module viewmodel {
        export class ScreenModel {
            textSearch: any;
            enableButton: KnockoutObservable<boolean>;
            //update add LaborInsuranceOffice
            typeAction: KnockoutObservable<number>;
            selectedMultipleTargetSetting: KnockoutObservable<number>;
            selectLstCodeLstCertification: KnockoutObservableArray<string>;

            //Info Certification (DTO View)
            lstCertificationInfo: KnockoutObservableArray<CertificationFindInDto>;
            selectLstCodeLstCertificationInfo: KnockoutObservableArray<string>;

            lstCertifyGroup: KnockoutObservableArray<CertifyGroupFindInDto>;
            columnsLstCertifyGroup: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            selectCodeLstLstCertifyGroup: KnockoutObservable<string>;
            lstCertification: KnockoutObservableArray<CertificationFindInDto>;
            //Info CertifyGroup (DTO View)
            certifyGroupModel: KnockoutObservable<CertifyGroupModel>;
            textEditorOption: KnockoutObservable<any>;
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
            }
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                self.findAllCertifyGroup().done(data => {
                    dfd.resolve(self);
                });
                return dfd.promise();
            }

            private findAllCertification(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.findAllCertification("CCD1").done(data => {
                    self.lstCertification = ko.observableArray<CertificationFindInDto>(data);
                    dfd.resolve(self);
                });
                return dfd.promise();
            }
            private findAllCertifyGroup(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.findAllCertifyGroup("CCD1").done(data => {
                    self.lstCertifyGroup = ko.observableArray<CertifyGroupFindInDto>(data);
                    self.findCertifyGroup(data[0].code).done(data => {
                        dfd.resolve(self);
                    });
                });
                return dfd.promise();
            }
            private findCertifyGroup(code: string): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.findCertifyGroup(code).done(data => {
                    self.certifyGroupModel = ko.observable(new CertifyGroupModel(data));
                    self.selectCodeLstLstCertifyGroup = ko.observable(code);
                    service.findAllCertification("CCD1").done(data => {
                        self.certifyGroupModel().setLstCertification(data);
                        dfd.resolve(self);
                    });

                });
                return dfd.promise();
            }
            private resetValueCertifyGroup() {
                var self = this;
                self.certifyGroupModel().code('');
                self.certifyGroupModel().name('');
                self.certifyGroupModel().certifies([]);
                self.certifyGroupModel().multiApplySet(MultipleTargetSetting.BigestMethod);
                self.typeAction(TypeActionCertifyGroup.add);
            }
            private saveCertifyGroup() {
                var self = this;
                if (self.typeAction() == TypeActionCertifyGroup.add) {
                    service.addCertifyGroup(self.convertDataModel());
                } else {

                }
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
            certifies: KnockoutObservableArray<string>;
            lstCertification: KnockoutObservableArray<CertificationFindInDto>;
            columnsCertification: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            selectionMultipleTargetSetting: KnockoutObservableArray<MultipleTargetSettingDto>;
            constructor(certifyGroupDto: CertifyGroupDto) {
                this.code = ko.observable(certifyGroupDto.code);
                this.name = ko.observable(certifyGroupDto.name);
                this.multiApplySet = ko.observable(certifyGroupDto.multiApplySet);
                var lstCertifies: string[];
                lstCertifies = [];
                for (var itemCertifies of certifyGroupDto.certifies) {
                    lstCertifies.push(itemCertifies.code);
                }
                this.certifies = ko.observableArray<string>(lstCertifies);
                this.columnsCertification = ko.observableArray([
                    { headerText: 'コード', prop: 'code', width: 60 },
                    { headerText: '名称', prop: 'name', width: 180 }
                ]);
                this.selectionMultipleTargetSetting = ko.observableArray<MultipleTargetSettingDto>(
                    [new MultipleTargetSettingDto(MultipleTargetSetting.BigestMethod, "BigestMethod"),//"BigestMethod 
                        new MultipleTargetSettingDto(MultipleTargetSetting.TotalMethod, "TotalMethod")//TotalMethod
                    ]);//TotalMethod
            }
            setLstCertification(lstCertification: CertificationFindInDto[]) {
                this.lstCertification = ko.observableArray<CertificationFindInDto>(lstCertification);
            }
        }
    }
