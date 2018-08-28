module nts.uk.com.view.cmm021.a {

    export module service {

        /**
         *  Service paths
         */
        var servicePath: any = {
            findAlreadySettingWindow: "ctx/sys/gateway/single/signon/find/window/alreadysetting",
            findAlreadySettingOtherAcc: "ctx/sys/gateway/single/signon/find/otheracc/alreadysetting",
            findListUserInfo: "ctx/sys/gateway/single/signon/find/userInfo",
            findListWindowAccByUserId: "ctx/sys/gateway/single/signon/find/window/account",
            saveWindowAccount: "ctx/sys/gateway/single/signon/save/windowAcc",
            removeWindowAccount: "ctx/sys/gateway/single/signon/remove/windowAcc",
                        
            saveOtherSysAccount: "ctx/sys/gateway/single/signon/save/otherSysAcc",
            removeOtherSysAccount: "ctx/sys/gateway/single/signon/remove/otherSysAcc",
            findOtherSysAccByUserId: "ctx/sys/gateway/single/signon/find/otherSysAcc",


        }
        

        export function findAlreadySettingWindow(sIds: Array<string>): JQueryPromise<Array<string>> {
            return nts.uk.request.ajax(servicePath.findAlreadySettingWindow, sIds);
        }

        export function findAlreadySettingOtherAcc(sIds: Array<string>): JQueryPromise<Array<string>> {
            return nts.uk.request.ajax(servicePath.findAlreadySettingOtherAcc, sIds);
        }

        // Screen B
        export function findListUserInfo(sIds: Array<string>, isScreenC: boolean): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findListUserInfo, { employeeIds: sIds, isScreenC: isScreenC});
        }

        export function findListWindowAccByUserId(userId: string): JQueryPromise<model.WindownAccountFinderDto[]> {
            return nts.uk.request.ajax(servicePath.findListWindowAccByUserId, { userId: userId });
        }

        export function saveWindowAccount(saveWindowAcc: model.SaveWindowAccountCommand): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.saveWindowAccount, saveWindowAcc);
        }
        
        export function removeWindowAccount(userIdDelete: string): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.removeWindowAccount, { userIdDelete: userIdDelete });
        }
        
        
        //Screen C
        export function findOtherSysAccByUserId(userId: string): JQueryPromise<model.OtherSysAccFinderDto> {
            return nts.uk.request.ajax(servicePath.findOtherSysAccByUserId, { userId: userId });
        }
        
        export function removeOtherSysAccount(userId: string): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.removeOtherSysAccount, { userId: userId });
        }
        
       export function saveOtherSysAccount(saveWindowAcc: model.SaveOtherSysAccountCommand): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.saveOtherSysAccount, saveWindowAcc);
        } 
        
        
        

        /**
         * Model namespace.
         */
        export module model {

            export class WindownAccountFinderDto {
                userId: string;
                hostName: string;
                userName: string;
                no: number;
                useAtr: number;                            

            }

            export class UserDto {
                userId: string;
                employeeCode: string;
                loginId: string;
                businessName: string;
                employeeId: string;
                isSetting: boolean;
                isScreenC: boolean;
                other: number;
            }
            
            export class SaveWindowAccountCommand {
                winAcc1: WindowAccountDto;
                winAcc2: WindowAccountDto;
                winAcc3: WindowAccountDto;
                winAcc4: WindowAccountDto;
                winAcc5: WindowAccountDto;
                userId: string;
            }           
            
            export class WindowAccountDto {
                userId: string;
                hostName: string;
                userName: string;
                no: number;
                useAtr: number;
                isChange: boolean;
                
                constructor (userId: string, hostName: string, userName: string, no: number, useAtr: number) {
                    this.userId = userId;
                    this.hostName = hostName;
                    this.userName = userName;
                    this.no = no;
                    this.useAtr = useAtr;
                    this.isChange = true;
                }                   
            }
            
            export class OtherSysAccFinderDto{
                userId: string;
                companyCode: string;
                userName: string;
                useAtr: number;
                isChange: boolean;
                
                constructor (userId: string, companyCode: string, userName: string, useAtr: number) {
                    this.userId = userId;
                    this.companyCode = companyCode;
                    this.userName = userName;
                    this.useAtr = useAtr;
                    this.isChange = true;
                }                              
            }
            
            export class SaveOtherSysAccountCommand {
                userId: string;
                companyCode: string;
                userName: string;
                useAtr: number;
               
            }

        }





    }
}