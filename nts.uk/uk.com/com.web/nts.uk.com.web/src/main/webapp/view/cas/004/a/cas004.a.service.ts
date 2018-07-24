module nts.uk.com.view.cas004.a {
    export module service {

        var paths: any = {
            getCompanyImportList: "ctx/sys/auth/regis/user/getAllCom",
            getUserListByCid: "ctx/sys/auth/regis/user/getlistUser",
            getAllUser: "ctx/sys/auth/regis/user/getAllUser",
            registerUser: "ctx/sys/auth/regis/user/register",
        };

        export function getCompanyImportList(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getCompanyImportList);
        };

        export function getUserList(cid: string): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getUserList, cid);
        }
        
        export function getAllUser(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getAllUser);
        }
        
        export function registerUser(userCmd: model.UserDto): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.registerUser, userCmd);
        }
    }

    export module model {
        export class CompanyImport {
            companyCode: string;
            companyName: string;
            companyId: string;

            constructor(companyCode: string, companyName: string, companyId: string) {
                this.companyCode = companyCode;
                this.companyName = companyName;
                this.companyId = companyId;
            };
        }

        export class UserDto {
            loginID: string;
            userName: string;
            password: string;
            expirationDate: string;
            mailAddress: string;
            personID: string;
            specialUser: boolean;
            multiCompanyConcurrent: boolean;
            
            constructor(loginID: string, userName: string, password: string, expirationDate: string, mailAddress: string, personID: string, specialUser: boolean, multiCompanyConcurrent: boolean) {
                this.loginID = loginID;
                this.userName = userName;
                this.password = password;
                this.expirationDate = expirationDate;
                this.mailAddress = mailAddress;
                this.personID = personID;
                this.specialUser = specialUser;
                this.multiCompanyConcurrent = multiCompanyConcurrent;
            }
        }
    }
}