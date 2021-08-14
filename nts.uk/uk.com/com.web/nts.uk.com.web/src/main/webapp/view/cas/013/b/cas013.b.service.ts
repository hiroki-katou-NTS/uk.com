module nts.uk.com.view.cas013.b.service {
    var paths: any = {
        searchUser : "ctx/sys/auth/user/findByKey",
        searchCompanyInfo:"ctx/sys/auth/grant/roleindividual/searchCompanyInfo",
        searchEmployyeList:"ctx/sys/auth/grant/roleindividual/searchEmployyeList",
        getWorkPlacePub: "ctx/sys/auth/grant/roleindividual/getWorkPlacePub",
        getSyJobTitlePub: "ctx/sys/auth/grant/roleindividual/getSyJobTitlePub",
        getCompanyList: "ctx/sys/auth/grant/roleindividual/getCompanyList"
    }

    export function searchUser(key: string, Special: boolean, Multi: boolean, roleType: number): JQueryPromise<any> {
        var userKeyDto = {
                    key: key,
                    Special: Special,
                    Multi: Multi,
                    roleType: roleType
                };
        return nts.uk.request.ajax("com", paths.searchUser, userKeyDto);
    }
    export function searchCompanyInfo(companyId: string): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.searchCompanyInfo, companyId);
    }
    export function getCompanyList(): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.getCompanyList);
    }
    export function searchEmployyeList(employyeid: Array<string>): JQueryPromise<any> {
        var EiDDto = new Params(employyeid);
        return nts.uk.request.ajax("com", paths.searchEmployyeList, EiDDto);
    }
    export function getWorkPlacePub(employyeid: Array<string>): JQueryPromise<any> {
        var EiDDto = new Params(employyeid);
        return nts.uk.request.ajax("com", paths.getWorkPlacePub, EiDDto);
    }
    export function getSyJobTitlePub(employyeid: Array<string>): JQueryPromise<any> {
        var EiDDto = new Params(employyeid);
        return nts.uk.request.ajax("com", paths.getSyJobTitlePub, EiDDto);
    }
     class  Params {
         employyeId: string[];
         constructor (employyeId: string[]){
            this.employyeId = employyeId;
        }
    }

}