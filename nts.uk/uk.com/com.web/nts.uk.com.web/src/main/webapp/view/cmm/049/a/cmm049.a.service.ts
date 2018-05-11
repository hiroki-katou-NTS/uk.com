module nts.uk.com.view.cmm049.a {

    export module service {

        /**
            *  Service paths
            */
        var servicePath: any = {
            getAllEnum: "sys/env/userinfoset/getAllEnum",
            saveUserinfoUseMethod: "sys/env/userinfoset/save/userInfoSetting"
        }

        // Screen B
        export function getAllEnum(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.getAllEnum);
        }

        export function saveUserinfoUseMethod(data: cmm049.a.service.ListUserInfoUseMethodDto): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.saveUserinfoUseMethod, data);
        }

        //        export function findListWindowAccByUserId(userId: string): JQueryPromise<model.WindownAccountFinderDto[]> {
        //            return nts.uk.request.ajax(servicePath.findListWindowAccByUserId, { userId: userId });
        //        }
        //
        //        export function saveWindowAccount(saveWindowAcc: model.SaveWindowAccountCommand): JQueryPromise<any> {
        //            return nts.uk.request.ajax(servicePath.souWindowAcc);
        //        }
            
        
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