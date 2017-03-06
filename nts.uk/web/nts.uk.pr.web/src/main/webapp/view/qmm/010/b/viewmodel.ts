module nts.uk.pr.view.qmm010.b {
    import option = nts.uk.ui.option;
    import SocialInsuranceOfficeImportDto = service.model.SocialInsuranceOfficeImportDto;
    import LaborInsuranceOfficeCheckImportDto = service.model.LaborInsuranceOfficeCheckImportDto;
    import LaborInsuranceOfficeImportDto = service.model.LaborInsuranceOfficeImportDto;
    export module viewmodel {
        export class ScreenModel {
            textSearch: any;
            lstSocialInsuranceOffice: KnockoutObservableArray<SocialInsuranceOfficeImportDto>;
            selectLstSocialInsuranceOffice: KnockoutObservable<string>;
            columnsLstSocialInsuranceOffice: KnockoutObservableArray<any>;
            constructor() {
                var self = this;
                self.selectLstSocialInsuranceOffice = ko.observable('');
                self.columnsLstSocialInsuranceOffice = ko.observableArray([
                    { headerText: 'コード', prop: 'code', width: 100 },
                    { headerText: '名称', prop: 'name', width: 150 }
                ]);
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
                var data = nts.uk.ui.windows.getShared("dataInsuranceOffice");
                self.lstSocialInsuranceOffice = ko.observableArray<SocialInsuranceOfficeImportDto>(data);
                dfd.resolve(self);
                return dfd.promise();
            }
            private showConfirm(laborInsuranceOfficeCheckImportDto: LaborInsuranceOfficeCheckImportDto,
                socialInsuranceOfficeImport: SocialInsuranceOfficeImportDto) {
                var self = this;
                if (laborInsuranceOfficeCheckImportDto.code === "1") {
                    nts.uk.ui.dialog.confirm("Duplicate Code ! Do you replace All?").ifYes(function() {
                        self.importData(0, socialInsuranceOfficeImport);
                    }).ifNo(function() {
                        self.importData(1, socialInsuranceOfficeImport);
                    })
                } else {
                    self.importData(0, socialInsuranceOfficeImport);
                }
            }
            private importData(checkUpdateDuplicateCode: number, socialInsuranceOfficeImport: SocialInsuranceOfficeImportDto) {
                var laborInsuranceOfficeImportDto: LaborInsuranceOfficeImportDto;
                laborInsuranceOfficeImportDto = new LaborInsuranceOfficeImportDto();
                laborInsuranceOfficeImportDto.socialInsuranceOfficeImport = socialInsuranceOfficeImport;
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
                if (self.selectLstSocialInsuranceOffice() != null) {
                    var socialInsuranceOfficeImport: SocialInsuranceOfficeImportDto;
                    socialInsuranceOfficeImport = self.findCode(self.selectLstSocialInsuranceOffice());
                    service.checkDuplicateCodeByImportData(socialInsuranceOfficeImport).done(data => {
                        self.showConfirm(data, socialInsuranceOfficeImport);
                    });
                }
            }
        }
    }
}
