module nts.uk.com.view.cas001.a.viewmodel {

    export class ScreenModel {
        personRoleList: KnockoutObservableArray<PersonRole> = ko.observableArray([]);
        currentRole: KnockoutObservable<PersonRole> = ko.observable(new PersonRole(null));
        currentRoleCode: KnockoutObservable<string> = ko.observable('');
        roundingRules: KnockoutObservableArray<any> = ko.observableArray([
            { code: '1', name: '可' },
            { code: '0', name: '不可' }
        ]);
        itemListCbb: KnockoutObservableArray<any> = ko.observableArray([
            { code: 0, name: '非表示' },
            { code: 1, name: '参照のみ' },
            { code: 2, name: '更新' }
        ]);
        anotherSelectedAll: KnockoutObservable<number> = ko.observable(1);
        seftSelectedAll: KnockoutObservable<number> = ko.observable(1);
        checkBoxSelectedAll: KnockoutObservable<boolean> = ko.observable(false);
        constructor() {
            let self = this;
            self.currentRoleCode.subscribe(function(newRoleCode) {
                let newPersonRole = _.find(self.personRoleList(), function(role) { return role.roleCode === newRoleCode });
                newPersonRole.loadRoleCategories(newPersonRole.roleCode);
                self.currentRole(newPersonRole);
            });
            self.checkBoxSelectedAll.subscribe(function(newValue) {
                for (let item of self.currentRole().currentCategory().roleItemList()) {
                    item.IsSelected = newValue;
                }
                $("#item_role_table_body").igGrid("option", "dataSource", self.currentRole().currentCategory().roleItemList());
            });
            self.seftSelectedAll.subscribe(function(newValue) {
                for (let item of self.currentRole().currentCategory().roleItemList()) {
                    item.SelfAuthority = newValue;
                }
                $("#item_role_table_body").igGrid("option", "dataSource", self.currentRole().currentCategory().roleItemList());

            });
            self.anotherSelectedAll.subscribe(function(newValue) {
                for (let item of self.currentRole().currentCategory().roleItemList()) {
                    item.OtherPeopleAuthority = newValue;
                }
                $("#item_role_table_body").igGrid("option", "dataSource", self.currentRole().currentCategory().roleItemList());

            });
        }
        InitializationItemGrid() {
            let self = this;
            $("#item_role_table_body").ntsGrid({
                features: [{ name: 'Resizing' }],
                ntsFeatures: [{ name: 'CopyPaste' }],
                showHeader: false,
                width: '800px',
                height: '261px',
                dataSource: self.currentRole().currentCategory().roleItemList(),
                primaryKey: 'ItemName',
                virtualization: true,
                virtualizationMode: 'continuous',
                columns: [
                    { headerText: 'FLAG', key: 'IsSelected', dataType: 'boolean', width: '34px', ntsControl: 'Checkbox' },
                    { headerText: 'IsConfig', key: 'IsConfig', dataType: 'string', width: '34px', formatter: makeIcon },
                    { headerText: 'ItemName', key: 'ItemName', dataType: 'string', width: '255px' },
                    { headerText: 'RULECODE', key: 'OtherPeopleAuthority', dataType: 'string', width: '232px', ntsControl: 'SwitchButtons' },
                    { headerText: 'RULECODE', key: 'SelfAuthority', dataType: 'string', width: '232px', ntsControl: 'SwitchButtons' },
                ],
                ntsControls: [
                    { name: 'Checkbox', options: { value: 1, text: '' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true },
                    {
                        name: 'SwitchButtons', options: [{ value: '0', text: '非表示' }, { value: '1', text: '参照のみ' }, { value: '2', text: '更新' }],
                        optionsValue: 'value', optionsText: 'text', controlType: 'SwitchButtons', enable: true
                    },
                ]
            });

        }
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.InitializationItemGrid();
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
                let newCategory = _.find(self.RoleCategoryList(), function(roleCategory) { return roleCategory.PersonInfoCategoryID === newCategoryCode; });
                newCategory.loadRoleItems(newCategory.PersonInfoCategoryID);
                self.currentCategory(newCategory);
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
            self.RoleCategoryList([]);
            for (let i = 1; i < 100; i++) {
                let referenceNumber = i % 3;
                let object = {
                    PersonInfoCategoryID: 'id' + i,
                    PersonInfoCategoryName: 'name' + i,
                    IsConfig: i % 2,
                    PersonRoleType: {
                        code: referenceNumber,
                        name: referenceNumber == 1 ? 'pika' : referenceNumber == 2 ? 'mieo' : 'chu'
                    },
                    AllowPersonReference: 1,
                    AllowOthersReference: 0,
                    AllowAnotherCompanyReference: 1,
                    PastHistoryAuthority: 0,
                    FutureHistoryAuthority: 1,
                    AllowDeleteHistory: 0,
                    AllowAddHistory: 1,
                    OtherPastHistoryAuthority: 0,
                    OtherFutureHistoryAuthority: 1,
                    OtherAllowDeleteHistory: 0,
                    OtherAllowAddHistory: 1,
                    AllowDeleteMulti: 1,
                    AllowAddMulti: 0,
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
        IsConfig: number;
        PersonRoleType: KnockoutObservable<any>;
        AllowPersonReference: KnockoutObservable<number>;
        AllowOthersReference: KnockoutObservable<number>;
        AllowAnotherCompanyReference: KnockoutObservable<number>;
        PastHistoryAuthority: KnockoutObservable<number>;
        FutureHistoryAuthority: KnockoutObservable<number>;
        AllowDeleteHistory: KnockoutObservable<number>;
        AllowAddHistory: KnockoutObservable<number>;
        OtherPastHistoryAuthority: KnockoutObservable<number>;
        OtherFutureHistoryAuthority: KnockoutObservable<number>;
        OtherAllowDeleteHistory: KnockoutObservable<number>;
        OtherAllowAddHistory: KnockoutObservable<number>;
        AllowDeleteMulti: KnockoutObservable<number>;
        AllowAddMulti: KnockoutObservable<number>;
        AllowOtherDeleteMulti: KnockoutObservable<number>;
        AllowOtherAddMulti: KnockoutObservable<number>;
        roleItemList: KnockoutObservableArray<PersonRoleItem> = ko.observableArray([]);
        currentItem: KnockoutObservable<PersonRoleItem> = ko.observable(new PersonRoleItem(null));
        currentItemCodes: KnockoutObservableArray<string> = ko.observableArray([]);
        constructor(param: service.IPersonRoleCategory) {
            let self = this;
            self.PersonInfoCategoryID = param ? param.PersonInfoCategoryID : '';
            self.PersonInfoCategoryName = param ? param.PersonInfoCategoryName : '';
            self.IsConfig = param ? param.IsConfig : 0;
            self.PersonRoleType = ko.observable(param ? param.PersonRoleType : 0);
            self.AllowPersonReference = ko.observable(param ? param.AllowPersonReference : 0);
            self.AllowOthersReference = ko.observable(param ? param.AllowOthersReference : 0);
            self.AllowAnotherCompanyReference = ko.observable(param ? param.AllowAnotherCompanyReference : 0);
            self.PastHistoryAuthority = ko.observable(param ? param.PastHistoryAuthority : 0);
            self.FutureHistoryAuthority = ko.observable(param ? param.FutureHistoryAuthority : 0);
            self.AllowDeleteHistory = ko.observable(param ? param.AllowDeleteHistory : 0);
            self.AllowAddHistory = ko.observable(param ? param.AllowAddHistory : 0);
            self.OtherPastHistoryAuthority = ko.observable(param ? param.OtherPastHistoryAuthority : 0);
            self.OtherFutureHistoryAuthority = ko.observable(param ? param.OtherFutureHistoryAuthority : 0);
            self.OtherAllowDeleteHistory = ko.observable(param ? param.OtherAllowDeleteHistory : 0);
            self.OtherAllowAddHistory = ko.observable(param ? param.OtherAllowAddHistory : 0);
            self.AllowDeleteMulti = ko.observable(param ? param.AllowDeleteMulti : 0);
            self.AllowAddMulti = ko.observable(param ? param.AllowAddMulti : 0);
            self.AllowOtherDeleteMulti = ko.observable(param ? param.AllowOtherDeleteMulti : 0);
            self.AllowOtherAddMulti = ko.observable(param ? param.AllowOtherAddMulti : 0);
        }
        loadRoleItems(CategoryCode): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.roleItemList([]);
            for (let i = 1; i < 100; i++) {
                let object = {
                    PersonInfoItemDefinitionID: 'id' + i,
                    IsConfig: i % 2,
                    IsRequired: i % 2,
                    ItemName: 'name' + i,
                    PersonInfoItemAuthorityID: 'id' + i,
                    PersonInfoCategoryID: 'id' + i,
                    OtherPeopleAuthority: i % 3,
                    SelfAuthority: i % 3
                };
                self.roleItemList().push(new PersonRoleItem(object));
            }
            if (self.roleItemList().length > 0)
                self.currentItemCodes([self.roleItemList()[0].PersonInfoItemDefinitionID]);
            $("#item_role_table_body").igGrid("option", "dataSource", self.roleItemList());

            dfd.resolve();
            return dfd.promise();
        }
    }
    export class PersonRoleItem {
        PersonInfoItemDefinitionID: string;
        IsConfig: number;
        IsRequired: number;
        ItemName: string;
        IsSelected: boolean = false;
        PersonInfoItemAuthorityID: string;
        PersonInfoCategoryID: string;
        OtherPeopleAuthority: number;
        SelfAuthority: number;
        constructor(param: service.IPersonRoleItem) {
            let self = this;
            self.PersonInfoItemDefinitionID = param ? param.PersonInfoItemDefinitionID : '';
            self.IsConfig = param ? param.IsConfig : 0;
            self.IsRequired = param ? param.IsRequired : 0;
            self.ItemName = param ? param.ItemName : '';
            self.PersonInfoItemAuthorityID = param ? param.PersonInfoItemAuthorityID : '';
            self.PersonInfoCategoryID = param ? param.PersonInfoCategoryID : '';
            self.OtherPeopleAuthority = param ? param.OtherPeopleAuthority : 0;
            self.SelfAuthority = param ? param.SelfAuthority : 0;
        }

    }
}
function makeIcon(value, row) {
    if (value == 1)
        return '&#8226;'
    return '';
}

