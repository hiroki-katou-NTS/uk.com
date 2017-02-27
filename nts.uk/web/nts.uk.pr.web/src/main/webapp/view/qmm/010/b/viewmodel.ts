module nts.uk.pr.view.qmm010.b {
    import option = nts.uk.ui.option;
    import SocialInsuranceOfficeImportDto = service.model.SocialInsuranceOfficeImportDto;
    import LaborInsuranceOfficeCheckImportDto = service.model.LaborInsuranceOfficeCheckImportDto;
    import LaborInsuranceOfficeImportDto = service.model.LaborInsuranceOfficeImportDto;
    export module viewmodel {
        export class ScreenModel {
            textSearch: any;
            lstSocialInsuranceOffice: KnockoutObservableArray<SocialInsuranceOfficeImportDto>;
            selectLstSocialInsuranceOffice: KnockoutObservableArray<any>;
            columnsLstSocialInsuranceOffice: KnockoutObservableArray<any>;
            multilineeditor: any;
            employmentName: KnockoutObservable<string>;
            constructor() {
                var self = this;
                self.employmentName = ko.observable("");
                self.textSearch = {
                    valueSearch: ko.observable(""),
                    option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                        textmode: "text",
                        placeholder: "コード・名称で検索・・・",
                        width: "340",
                        textalign: "left"
                    }))
                }
                self.selectLstSocialInsuranceOffice = ko.observableArray([]);
                self.columnsLstSocialInsuranceOffice = ko.observableArray([
                    { headerText: 'コード', prop: 'code', width: 120 },
                    { headerText: '名称', prop: 'name', width: 120 }
                ]);
                self.multilineeditor = {
                    value: ko.observable(''),
                    constraint: 'ResidenceCode',
                    option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                        resizeable: true,
                        placeholder: "Placeholder for text editor",
                        width: "",
                        textalign: "left"
                    })),
                    required: ko.observable(true),
                    enable: ko.observable(true),
                    readonly: ko.observable(false)
                };
            }
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                self.findAllInsuranceOffice().done(data => {
                    dfd.resolve(self);
                });
                return dfd.promise();
            }
            //Connection service find All InsuranceOffice
            findAllInsuranceOffice(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.findAllSocialInsuranceOffice().done(data => {
                    self.lstSocialInsuranceOffice = ko.observableArray<SocialInsuranceOfficeImportDto>(data);
                    dfd.resolve(self);
                });
                return dfd.promise();
            }
            private showConfirm(laborInsuranceOfficeCheckImportDto: LaborInsuranceOfficeCheckImportDto,
                lstSocialInsuranceOfficeImport: SocialInsuranceOfficeImportDto[]) {
                var self = this;
                if (laborInsuranceOfficeCheckImportDto.code === "1") {
                    nts.uk.ui.dialog.confirm("Duplicate Code ! Do you replace All?").ifYes(function() {
                        self.importData(0, lstSocialInsuranceOfficeImport);
                    }).ifNo(function() {
                        self.importData(1, lstSocialInsuranceOfficeImport);
                    }).ifCancel(function() {
                        self.importData(1, lstSocialInsuranceOfficeImport);
                    }).then(function() {
                        self.importData(1, lstSocialInsuranceOfficeImport);
                    })
                } else {
                    self.importData(0, lstSocialInsuranceOfficeImport);
                }
            }
            private importData(checkUpdateDuplicateCode: number, lstSocialInsuranceOfficeImport: SocialInsuranceOfficeImportDto[]) {
                var laborInsuranceOfficeImportDto: LaborInsuranceOfficeImportDto;
                laborInsuranceOfficeImportDto = new LaborInsuranceOfficeImportDto();
                laborInsuranceOfficeImportDto.lstSocialInsuranceOfficeImport = lstSocialInsuranceOfficeImport;
                laborInsuranceOfficeImportDto.checkUpdateDuplicateCode = checkUpdateDuplicateCode;
                service.importData(laborInsuranceOfficeImportDto).done(data => {
                    nts.uk.ui.windows.close();
                });
            }
            //Find code data lst by check import data
            private findCode(code: string): SocialInsuranceOfficeImportDto {
                var self = this;
                for (var itemOfLst of self.lstSocialInsuranceOffice()) {
                    if (itemOfLst.code === code) {
                        return itemOfLst;
                    }
                }
                return null;
            }
            private checkDuplicateCodeByImportData() {
                var self = this;
                if (self.selectLstSocialInsuranceOffice() != null && self.selectLstSocialInsuranceOffice().length > 0) {
                    var lstSocialInsuranceOfficeImport: SocialInsuranceOfficeImportDto[];
                    lstSocialInsuranceOfficeImport = [];
                    for (var item: string of self.selectLstSocialInsuranceOffice()) {
                        lstSocialInsuranceOfficeImport.push(self.findCode(item));
                    }
                    var laborInsuranceOfficeImportDto: LaborInsuranceOfficeImportDto;
                    laborInsuranceOfficeImportDto = new LaborInsuranceOfficeImportDto();
                    service.checkDuplicateCodeByImportData(lstSocialInsuranceOfficeImport).done(data => {
                        self.showConfirm(data, lstSocialInsuranceOfficeImport);
                    });
                }
            }
        }
    }
}
