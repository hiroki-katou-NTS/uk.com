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
    export interface IPersonRoleCategory {
        PersonInfoCategoryID: string;
        PersonInfoCategoryName: string;
        IsConfig: number;
        PersonRoleType: any;
        AllowPersonReference: number;
        AllowOthersReference: number;
        AllowAnotherCompanyReference: number;
        PastHistoryAuthority: number;
        FutureHistoryAuthority: number;
        AllowDeleteHistory: number;
        AllowAddHistory: number;
        OtherPastHistoryAuthority: number;
        OtherFutureHistoryAuthority: number;
        OtherAllowDeleteHistory: number;
        OtherAllowAddHistory: number;
        AllowDeleteMulti: number;
        AllowAddMulti: number;
        AllowOtherDeleteMulti: number;
        AllowOtherAddMulti: number;

    }

    export interface IPersonRoleItem {
        PersonInfoItemDefinitionID: string;
        IsConfig: number;
        IsRequired: number;
        ItemName: string;
        PersonInfoItemAuthorityID: string;
        PersonInfoCategoryID: string;
        OtherPeopleAuthority: number;
        SelfAuthority: number;
    }
}
