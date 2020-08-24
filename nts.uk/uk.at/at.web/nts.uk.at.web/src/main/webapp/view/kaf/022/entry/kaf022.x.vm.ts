module nts.uk.at.view.kaf022.x {
    export module viewmodel {
        export class ScreenModel {
            constructor() {
                var self = this;
            }

            public goToCompanySettings(): void {
                nts.uk.request.jump("/view/kaf/022/company/index.xhtml");
            }

            public goToEmploymentSettings(): void {
                nts.uk.request.jump("/view/kaf/022/employment/index.xhtml");
            }

            public goToWorkplaceSettings(): void {
                nts.uk.request.jump("/view/kaf/022/workplace/index.xhtml");
            }
        }
    }
}