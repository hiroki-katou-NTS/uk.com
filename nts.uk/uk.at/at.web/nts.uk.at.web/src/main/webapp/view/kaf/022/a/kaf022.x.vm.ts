module nts.uk.at.view.kaf022.x {
    export module viewmodel {
        export class ScreenModel {

            public goToCompanySettings(): void {
                nts.uk.request.jump("/view/kaf/022/b/index.xhtml");
            }

            public goToEmploymentSettings(): void {
                nts.uk.request.jump("/view/kaf/022/c/index.xhtml");
            }

            public goToWorkplaceSettings(): void {
                nts.uk.request.jump("/view/kaf/022/d/index.xhtml");
            }

            exportExcel() {
                const self = this;
                let __viewContext: any = window["__viewContext"] || {};
                nts.uk.ui.block.grayout();
                let program = nts.uk.ui._viewModel.kiban.programName().split(" ");
                let domainType = "KAF022" + nts.uk.resource.getText("KAF022_768");

                nts.uk.request.exportFile('/masterlist/report/print', {
                    domainId: "PreparationBeforeApply",
                    domainType: domainType,
                    languageId: __viewContext.user.selectedLanguage.basicLanguageId,
                    reportType: 0,
                    data: null
                }).done(() => {
                    console.log("Export done!");
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError(error);
                }).always(function() {
                    nts.uk.ui.block.clear();
                });
            }

        }
    }
}