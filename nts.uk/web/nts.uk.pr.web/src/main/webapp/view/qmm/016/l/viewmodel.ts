module nts.uk.pr.view.qmm016.l {
    import option = nts.uk.ui.option;
    import MultipleTargetSettingDto = service.model.MultipleTargetSettingDto;
    import MultipleTargetSetting = service.model.MultipleTargetSetting;
    import CertificationFindInDto = service.model.CertificationFindInDto;
    import CertifyGroupFindInDto = service.model.CertifyGroupFindInDto;
    export module viewmodel {
        export class ScreenModel {
            currentCode: KnockoutObservable<any>;
            currentCodeList: KnockoutObservableArray<any>;
            selectionMultipleTargetSetting: KnockoutObservableArray<MultipleTargetSettingDto>;

            selectCodeLstlaborInsuranceOffice: KnockoutObservable<string>;
            textSearch: any;
            enableButton: KnockoutObservable<boolean>;
            //update add LaborInsuranceOffice
            typeAction: KnockoutObservable<number>;

            selectedMultipleTargetSetting: KnockoutObservable<number>;

            lstCertification: KnockoutObservableArray<CertificationFindInDto>;
            selectLstCodeLstCertification: KnockoutObservableArray<string>;
            columnsCertification: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            //Info Certification (DTO View)
            lstCertificationInfo: KnockoutObservableArray<CertificationFindInDto>;
            selectLstCodeLstCertificationInfo: KnockoutObservableArray<string>;

            lstCertifyGroup: KnockoutObservableArray<CertifyGroupFindInDto>;
            columnsLstCertifyGroup: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            selectCodeLstLstCertifyGroup: KnockoutObservable<string>;
            constructor() {
                var self = this;
                self.columnsCertification = ko.observableArray([
                    { headerText: 'コード', prop: 'code', width: 60 },
                    { headerText: '名称', prop: 'name', width: 180 }
                ]);
                self.columnsLstCertifyGroup = ko.observableArray([
                    { headerText: 'コード', prop: 'code', width: 120 },
                    { headerText: '名称', prop: 'name', width: 120 }
                ]);
                self.currentCode = ko.observable();
                self.currentCodeList = ko.observableArray([]);


                self.selectionMultipleTargetSetting = ko.observableArray<MultipleTargetSettingDto>(
                    [new MultipleTargetSettingDto(MultipleTargetSetting.BigestMethod, "BigestMethod"),//"BigestMethod 
                        new MultipleTargetSettingDto(MultipleTargetSetting.TotalMethod, "TotalMethod")//TotalMethod
                    ]);//TotalMethod
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
            }
            private readFromSocialTnsuranceOffice() {
                var self = this;
                self.enableButton(false);
                nts.uk.ui.windows.sub.modal("/view/qmm/010/b/index.xhtml", { height: 800, width: 500, title: "社会保険事業所から読み込み" }).onClosed(() => {
                    self.enableButton(true);
                    //OnClose => call
                });
            }
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                self.findAllCertification().done(data => {
                    self.findAllCertifyGroup().done(data => {
                        dfd.resolve(self);
                    });
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
                    dfd.resolve(self);
                });
                return dfd.promise();
            }
            private lefttorightCertification() {
                var self = this;
                var selectLstCodeLstCertification: string[];
                var lstCertification: CertificationFindInDto[];
                var lstCertificationInfo: CertificationFindInDto[];
                lstCertification = self.lstCertification();
                lstCertificationInfo = self.lstCertificationInfo();
                selectLstCodeLstCertification = self.selectLstCodeLstCertification();
                if (selectLstCodeLstCertification != null && selectLstCodeLstCertification != undefined) {
                    for (var itemSelectLstCodeLstCertification of selectLstCodeLstCertification) {
                        //Add to right
                        //Delete to left
                        var indexDelete: number = 0;
                        for (var itemDelete of lstCertification) {
                            indexDelete++;
                            if (itemDelete.code === itemSelectLstCodeLstCertification) {
                                lstCertificationInfo.push(itemDelete);
                                indexDelete--;
                                //start number 0
                                break;
                            }
                        }
                        lstCertification.splice(indexDelete, 1);
                    }
                    self.lstCertification(lstCertification);
                    self.lstCertificationInfo(lstCertificationInfo)
                }

            }
            private righttoleftCertification() {
                var self = this;
                var selectLstCodeLstCertificationInfo: string[];
                var lstCertification: CertificationFindInDto[];
                var lstCertificationInfo: CertificationFindInDto[];
                lstCertification = self.lstCertification();
                lstCertificationInfo = self.lstCertificationInfo();
                selectLstCodeLstCertificationInfo = self.selectLstCodeLstCertificationInfo();
                if (selectLstCodeLstCertificationInfo != null && selectLstCodeLstCertificationInfo != undefined) {
                    for (var itemSelectLstCodeLstCertification of selectLstCodeLstCertificationInfo) {
                        //Add to right
                        //Delete to left
                        var indexDelete: number = 0;
                        for (var itemDelete of lstCertificationInfo) {
                            indexDelete++;
                            if (itemDelete.code === itemSelectLstCodeLstCertification) {
                                lstCertification.push(itemDelete);
                                indexDelete--;
                                //start number 0
                                break;
                            }
                        }
                        lstCertificationInfo.splice(indexDelete, 1);
                    }
                    self.lstCertification(lstCertification);
                    self.lstCertificationInfo(lstCertificationInfo)
                    self.selectLstCodeLstCertificationInfo([]);
                    self.selectLstCodeLstCertification([]);
                }

            }
        }
    }
