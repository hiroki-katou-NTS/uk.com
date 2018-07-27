module nts.uk.com.view.cmm049.a {

    export module service {

        /**
            *  Service paths
            */
        var servicePath: any = {
            getAllEnum: "sys/env/userinfoset/getAllEnum",
            saveUserinfoUseMethod: "sys/env/userinfoset/save/userInfoSetting",
            findUserinfoUseMethod: "sys/env/userinfoset/find"
        }

        // Screen B
        export function getAllEnum(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.getAllEnum);
        }
        
        //save screen A
        export function saveUserinfoUseMethod(data: cmm049.a.service.ListUserInfoUseMethodDto): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.saveUserinfoUseMethod, data);
        }

        //find screen A
        export function findUserinfoUseMethod(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findUserinfoUseMethod);
        }
        
        export class ListUserInfoUseMethodDto {
            lstUserInfoUseMethodDto: Array<UserInfoUseMethodDto>;
        }

        export class UserInfoUseMethodDto {
            settingItem: number;
            selfEdit: number;
            settingUseMail: number;


            constructor( settingItem: number, selfEdit: number, settingUseMail: number) {
                this.settingItem = settingItem;
                this.selfEdit = selfEdit;
                this.settingUseMail = settingUseMail;
            }
        }

    }
}