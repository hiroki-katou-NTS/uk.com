module nts.uk.com.view.cas001.a.viewmodel {

    export class ScreenModel {
        personRoleList: KnockoutObservableArray<PersonRole> = ko.observableArray([]);
        currentRole: KnockoutObservable<PersonRole> = ko.observable(new PersonRole(null));
        currentRoleCode: KnockoutObservable<string> = ko.observable('');
        roundingRules: KnockoutObservableArray<any> = ko.observableArray([
            { code: '1', name: '可' },
            { code: '0', name: '不可' }
        ]);;
        constructor() {
            let self = this;
            self.currentRoleCode.subscribe(function(newRoleCode) {
                let newPersonRole = _.find(self.personRoleList(), function(role) { return role.roleCode === newRoleCode });
                newPersonRole.loadRoleCategories(newPersonRole.roleCode);
                self.currentRole(newPersonRole);
            });
        }

        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.loadPersonRoleList().done(function() {
                dfd.resolve();
            });
            return dfd.promise();
        }

        loadPersonRoleList(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            for (let i = 1; i < 100; i++) {
                let object = {
                    roleCode: '00' + i,
                    roleName: '基本給' + i,
                    AllowMapBrowsing: 1,
                    AllowMapUpLoad: 0,
                    AllowDocumentUpload: 1,
                    AllowDocumentReference: 1,
                    AllowAvatarUpload: 0,
                    AllowAvatarReference: 1,
                };
                self.personRoleList.push(new PersonRole(object));
            }
            if (self.personRoleList().length > 0)
                self.currentRoleCode(self.personRoleList()[0].roleCode);
            dfd.resolve();
            return dfd.promise();
        }

    }
    export class PersonRole {
        roleCode: string;
        roleName: string;
        AllowMapBrowsing: KnockoutObservable<number>;
        AllowMapUpLoad: KnockoutObservable<number>;
        AllowDocumentUpload: KnockoutObservable<number>;
        AllowDocumentReference: KnockoutObservable<number>;
        AllowAvatarUpload: KnockoutObservable<number>;
        AllowAvatarReference: KnockoutObservable<number>;

        RoleCategoryList: KnockoutObservableArray<PersonRoleCategory> = ko.observableArray([]);
        currentCategory: KnockoutObservable<PersonRoleCategory> = ko.observable(new PersonRoleCategory(null));
        currentRoleCategoryCode: KnockoutObservable<string> = ko.observable('');
        constructor(param: service.IPersonRole) {
            let self = this;
            self.roleCode = param ? param.roleCode : '';
            self.roleName = param ? param.roleName : '';
            self.AllowMapBrowsing = ko.observable(param ? param.AllowMapBrowsing : 0);
            self.AllowMapUpLoad = ko.observable(param ? param.AllowMapUpLoad : 0);
            self.AllowDocumentUpload = ko.observable(param ? param.AllowDocumentUpload : 0);
            self.AllowDocumentReference = ko.observable(param ? param.AllowDocumentReference : 0);
            self.AllowAvatarUpload = ko.observable(param ? param.AllowAvatarUpload : 0);
            self.AllowAvatarReference = ko.observable(param ? param.AllowAvatarReference : 0);
            self.currentRoleCategoryCode.subscribe(function(newCategoryCode) {
                let newCategory = _.find(self.RoleCategoryList(), function(roleCategory) { return roleCategory.PersonInfoCategoryID === newCategoryCode; })
                self.currentCategory(newCategory);
            });
            self.currentCategory.subscribe(function(newCategory) {
                if (newCategory)
                    self.currentCategory().loadRoleItems(newCategory.PersonInfoCategoryID);
            });
        }
        ExpandSymbolGen() {
            let self = this;
            //'&#8896;' : '&#8897;';
            // return self.itemRegExpand() ? '&#8896;' : '&#8897;';
            return '&#8896;'
        }
        loadRoleCategories(RoleCode) {
            var self = this;
            for (let i = 1; i < 100; i++) {
                let object = {
                    PersonInfoCategoryID: 'id' + i,
                    PersonInfoCategoryName: 'name' + i,
                    AllowPersonReference: 1,
                    AllowOthersReference: 1,
                    AllowAnotherCompanyReference: 1,
                    PastHistoryAuthority: 1,
                    FutureHistoryAuthority: 1,
                    AllowDeleteHistory: 1,
                    AllowAddHistory: 1,
                    OtherPastHistoryAuthority: 1,
                    OtherFutureHistoryAuthority: 1,
                    OtherAllowDeleteHistory: 1,
                    OtherAllowAddHistory: 1,
                    AllowDeleteMulti: 1,
                    AllowAddMulti: 1,
                    AllowOtherDeleteMulti: 1,
                    AllowOtherAddMulti: 1,
                };
                self.RoleCategoryList.push(new PersonRoleCategory(object));
            }
            if (self.RoleCategoryList().length > 0)
                self.currentRoleCategoryCode(self.RoleCategoryList()[0].PersonInfoCategoryID);
        }

    }
    export class PersonRoleCategory {
        PersonInfoCategoryID: string;
        PersonInfoCategoryName: string;
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
        RoleItemList: KnockoutObservableArray<PersonRoleItem> = ko.observableArray([]);
        currentItem: KnockoutObservable<PersonRoleItem> = ko.observable(new PersonRoleItem(null));
        constructor(param: any) {
            let self = this;
            self.PersonInfoCategoryID = param ? param.PersonInfoCategoryID : '';
            self.PersonInfoCategoryName = param ? param.PersonInfoCategoryName : '';
            self.AllowPersonReference = param ? param.AllowPersonReference : 0;
            self.AllowOthersReference = param ? param.AllowOthersReference : 0;
            self.AllowAnotherCompanyReference = param ? param.AllowAnotherCompanyReference : 0;
            self.PastHistoryAuthority = param ? param.PastHistoryAuthority : 0;
            self.FutureHistoryAuthority = param ? param.FutureHistoryAuthority : 0;
            self.AllowDeleteHistory = param ? param.AllowDeleteHistory : 0;
            self.AllowAddHistory = param ? param.AllowAddHistory : 0;
            self.OtherPastHistoryAuthority = param ? param.OtherPastHistoryAuthority : 0;
            self.OtherFutureHistoryAuthority = param ? param.OtherFutureHistoryAuthority : 0;
            self.OtherAllowDeleteHistory = param ? param.OtherAllowDeleteHistory : 0;
            self.OtherAllowAddHistory = param ? param.OtherAllowAddHistory : 0;
            self.AllowDeleteMulti = param ? param.AllowDeleteMulti : 0;
            self.AllowAddMulti = param ? param.AllowAddMulti : 0;
            self.AllowOtherDeleteMulti = param ? param.AllowOtherDeleteMulti : 0;
            self.AllowOtherAddMulti = param ? param.AllowOtherAddMulti : 0;


        }
        loadRoleItems(CategoryCode): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            for (let i = 1; i < 100; i++) {
                let object = {
                    PersonInfoItemDefinitionID: 'id' + i,
                    PersonInfoItemAuthorityID: 'id' + i,
                    PersonInfoCategoryID: 'id' + i,
                    OtherPeopleAuthority: 1,
                    SelfAuthority: 1
                };
                self.RoleItemList.push(new PersonRoleItem(object));
            }
            if (self.RoleItemList().length > 0)
                self.currentItem(self.RoleItemList()[0]);
            dfd.resolve();
            return dfd.promise();
        }
    }
    export class PersonRoleItem {
        PersonInfoItemDefinitionID: KnockoutObservable<string>;
        PersonInfoItemAuthorityID: KnockoutObservable<string>;
        PersonInfoCategoryID: KnockoutObservable<number>;
        OtherPeopleAuthority: KnockoutObservable<number>;
        SelfAuthority: KnockoutObservable<number>;
        constructor(param: any) {
            let self = this;
            self.PersonInfoItemDefinitionID = ko.observable(param ? param.PersonInfoItemDefinitionID : '');
            self.PersonInfoItemAuthorityID = ko.observable(param ? param.PersonInfoItemAuthorityID : '');
            self.PersonInfoCategoryID = ko.observable(param ? param.PersonInfoCategoryID : '');
            self.OtherPeopleAuthority = ko.observable(param ? param.OtherPeopleAuthority : 0);
            self.SelfAuthority = ko.observable(param ? param.SelfAuthority : 0);
        }

    }
}
function makeIcon(value, row) {
    if (value == 1)
        return '&#8226;'
}
