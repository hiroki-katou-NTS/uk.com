module nts.uk.com.view.cmm048.b {
    
    export module service {
        
        let servicePath: any = {
            findCompanyPcMail: "sys/env/userinfo/find/companypcmail",
            findPersonalPcMail: "sys/env/userinfo/find/personalpcmail",
            findCompanyMobileMail: "sys/env/userinfo/find/companymobilemail",
            findPersonalMobileMail: "sys/env/userinfo/find/personalmobilemail"
        };

        export function findCompanyPcMail(): JQueryPromise<Array<model.FunctionSettingDto>> {
            return nts.uk.request.ajax(servicePath.findCompanyPcMail);
        }
        
        export function findPersonalPcMail(): JQueryPromise<Array<model.FunctionSettingDto>> {
            return nts.uk.request.ajax(servicePath.findPersonalPcMail);
        }
        
        export function findCompanyMobileMail(): JQueryPromise<Array<model.FunctionSettingDto>> {
            return nts.uk.request.ajax(servicePath.findCompanyMobileMail);
        }
        
        export function findPersonalMobileMail(): JQueryPromise<Array<model.FunctionSettingDto>> {
            return nts.uk.request.ajax(servicePath.findPersonalMobileMail);
        }
        
    }
    
    export module model {
        
        export interface MainDto {
            mailFunction: MailFunctionDto;
            listFunctionSetting: Array<FunctionSettingDto>;
        }
        
        export interface MailFunctionDto {
            functionId: number;
            functionName: string;
            proprietySendMailSettingAtr: boolean;
            sortOrder: number;
        }
        
        export interface FunctionSettingDto {
            functionId: number;
            functionName: string;
            sendSetting: boolean;
        }
    }
}