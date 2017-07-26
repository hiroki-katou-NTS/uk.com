module nts.uk.com.view.cas001.a.service {
    import ajax = nts.uk.request.ajax;
    var paths = {
    }
    export function getPersonRoleList(): JQueryPromise<any> {

        return;
    }


    export interface IPersonRole {
        roleCode: string;
        roleName: string;
        AllowMapBrowsing: number;
        AllowMapUpLoad: number;
        AllowDocumentUpload: number;
        AllowDocumentReference: number;
        AllowAvatarUpload: number;
        AllowAvatarReference: number;
    }
}
