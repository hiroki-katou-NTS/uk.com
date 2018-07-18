module nts.uk.com.view.cas004.a {
    export module service {

        var paths: any = {
            getCompanyImportList: "ctx/sys/auth/regis/user/findAllCom",
        };

        export function getCompanyImportList(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getCompanyImportList);
        };
    }

    export module model {
        export class CompanyImport {
            companyCode: string;
            companyName: string;
            companyId: string;

            constructor(companyCode: string ,companyName: string ,companyId: string) {
                this.companyCode = companyCode;
                this.companyName = companyName;
                this.companyId = companyId;
            };
        }
    }
}