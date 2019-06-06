module nts.uk.hr.view.jhc002.a {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    export module service {
        var paths: any = {
            saveCareer: "careermgmt/careerpath/saveCareer",
            checkDataCareer: "careermgmt/careerpath/checkDataCareer",
            getMaxClassLevel: "careermgmt/careerpath/getMaxClassLevel",
            getLatestCareerPathHist: "careermgmt/careerpath/getLatestCareerPathHist",
            getCareerPart: "careermgmt/careerpath/getCareerPart",
            getAccountLockPolicy: "ctx/sys/gateway/securitypolicy/getAccountLockPolicy",
            getPasswordPolicy: "ctx/sys/gateway/securitypolicy/getPasswordPolicy",
            updateAccountPolicy: "ctx/sys/gateway/securitypolicy/updateAccountPolicy"
        }

        export function getAccountLockPolicy(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getAccountLockPolicy);
        }
        export function getMaxClassLevel(): JQueryPromise<any> {
            return ajax(paths.getMaxClassLevel);
        }
        export function getLatestCareerPathHist(): JQueryPromise<any> {
            return ajax(paths.getLatestCareerPathHist);
        }
        export function getCareerPart(param: any): JQueryPromise<any> {
            return ajax(paths.getCareerPart, param);
        }
        export function checkDataCareer(param: any): JQueryPromise<any> {
            return ajax(paths.checkDataCareer, param);
        }
        export function saveCareer(param: any): JQueryPromise<any> {
            return ajax(paths.saveCareer, param);
        }

    }
}