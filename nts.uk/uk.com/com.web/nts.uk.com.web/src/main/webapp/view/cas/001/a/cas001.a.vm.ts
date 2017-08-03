module nts.uk.com.view.cas001.a.viewmodel {
    import alert = nts.uk.ui.dialog.alert;
    import text = nts.uk.resource.getText;
    export class ScreenModel {
        personRoleList: KnockoutObservableArray<PersonRole> = ko.observableArray([]);
        currentRole: KnockoutObservable<PersonRole> = ko.observable(new PersonRole(null));
        currentRoleId: KnockoutObservable<string> = ko.observable('');
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
        constructor() {
            let self = this;
            self.currentRoleId.subscribe(function(newRoleId) {
                let newPersonRole = _.find(self.personRoleList(), function(role) { return role.roleId === newRoleId });
                service.getPersonRoleAuth(newPersonRole.roleId).done(function(result: IPersonRole) {
                    newPersonRole.loadRoleCategoriesList(newPersonRole.roleId).done(function() {
                        newPersonRole.setRoleAuth(result);
                        self.currentRole(newPersonRole);
                    });
                });
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
        OpenDModal() {
            nts.uk.ui.windows.sub.modal('/view/cas/001/d/index.xhtml', { title: '明細レイアウトの作成＞履歴追加' }).onClosed(function(): any {
            });
        }
        OpenCModal() {
            nts.uk.ui.windows.sub.modal('/view/cas/001/c/index.xhtml', { title: '明細レイアウトの作成＞履歴追加' }).onClosed(function(): any {
            });
        }
        InitializationItemGrid() {
            let self = this;
            $("#item_role_table_body").ntsGrid({
                features: [{ name: 'Resizing' },
                    {
                        name: "RowSelectors",
                        enableCheckBoxes: true,
                        enableRowNumbering: false,
                        rowSelectorColumnWidth: 34
                    }
                ],
                ntsFeatures: [{ name: 'CopyPaste' }],
                showHeader: true,
                width: '800px',
                height: '261px',
                dataSource: self.currentRole().currentCategory() === null ? null : self.currentRole().currentCategory().roleItemList(),
                primaryKey: 'ItemName',
                virtualization: true,
                virtualizationMode: 'continuous',
                virtualrecordsrender: function(evt, ui) {
                    var ds = ui.owner.dataSource.data();
                    $(ds)
                        .each(
                        function(index, el: any) {
                            let CheckboxCell = $("#item_role_table_body").igGrid("cellAt", 0, index);
                            let IsConfigCell = $("#item_role_table_body").igGrid("cellAt", 1, index);
                            let NameCell = $("#item_role_table_body").igGrid("cellAt", 2, index);
                            if (el.IsRequired == '1') {
                                $(CheckboxCell).addClass('requiredCell');
                                $(IsConfigCell).addClass('requiredCell');
                                $(NameCell).addClass('requiredCell');
                            }
                        });
                },
                columns: [
                    { headerText: text('CAS001_69'), key: 'IsConfig', dataType: 'string', width: '48px', formatter: makeIcon },
                    { headerText: 'IsRequired', key: 'IsRequired', dataType: 'string', width: '34px', hidden: true },
                    { headerText: text('CAS001_47'), key: 'ItemName', dataType: 'string', width: '255px' },
                    { headerText: text('CAS001_48'), key: 'OtherPeopleAuthority', dataType: 'string', width: '232px', ntsControl: 'SwitchButtons' },
                    { headerText: text('CAS001_52'), key: 'SelfAuthority', dataType: 'string', width: '232px', ntsControl: 'SwitchButtons' },
                ],
                ntsControls: [
                    {
                        name: 'SwitchButtons', options: [{ value: '0', text: '非表示' }, { value: '1', text: '参照のみ' }, { value: '2', text: '更新' }],
                        optionsValue: 'value', optionsText: 'text', controlType: 'SwitchButtons', enable: true
                    },
                ],

            });
            //add switch to table header

            let switchString = "<div id=\'auth_of_info_selected_all\'"
                + "data-bind=\"ntsSwitchButton: {options: itemListCbb"
                + ",optionsValue:\'code\',optionsText: \'name\',value: {0},enable: true }\">"
                + "</div><span id=\'selected_all_caret\' class=\'caret-bottom outline\'></span>"
            let selectedAllString = nts.uk.text.format(switchString, 'anotherSelectedAll');
            let seftSelectedAllString = nts.uk.text.format(switchString, 'seftSelectedAll');
            nts.uk.ui.ig.grid.header.getCell('item_role_table_body', 'OtherPeopleAuthority').append($(selectedAllString));
            nts.uk.ui.ig.grid.header.getCell('item_role_table_body', 'SelfAuthority').append($(seftSelectedAllString));
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
            service.getPersonRoleList().done(function(result: Array<IPersonRole>) {
                _.forEach(result, function(iPersonRole: IPersonRole) {
                    self.personRoleList().push(new PersonRole(iPersonRole));
                });
                if (self.personRoleList().length > 0) {
                    self.currentRoleId(self.personRoleList()[0].roleId);
                }
                else {
                    alert(text('Msg_217'));
                }
                dfd.resolve();
            });
            return dfd.promise();
        }

        saveData() {
            let self = this;
            let item = self.currentRole().currentCategory().roleItemList()[0];
            console.log(item.OtherPeopleAuthority);
        }
    }
    export interface IPersonRole {
        roleId: string;
        roleCode: string;
        roleName: string;
        allowMapBrowse: number;
        allowMapUpload: number;
        allowDocUpload: number;
        allowDocRef: number;
        allowAvatarUpload: number;
        allowAvatarRef: number;
    }
    export interface IPersonRoleCategory {
        PersonInfoCategoryId: string;
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
    export class PersonRole {
        roleId: string;
        roleCode: string;
        roleName: string;
        allowMapBrowse: KnockoutObservable<number>;
        allowMapUpload: KnockoutObservable<number>;
        allowDocUpload: KnockoutObservable<number>;
        allowDocRef: KnockoutObservable<number>;
        allowAvatarUpload: KnockoutObservable<number>;
        allowAvatarRef: KnockoutObservable<number>;
        RoleCategoryList: KnockoutObservableArray<PersonRoleCategory> = ko.observableArray([]);
        currentCategory: KnockoutObservable<PersonRoleCategory> = ko.observable(null);
        currentRoleCategoryId: KnockoutObservable<string> = ko.observable('');
        constructor(param: IPersonRole) {
            let self = this;
            self.roleId = param ? param.roleId : '';
            self.roleCode = param ? param.roleCode : '';
            self.roleName = param ? param.roleName : '';
            self.allowMapBrowse = ko.observable(param ? param.allowMapBrowse : 0);
            self.allowMapUpload = ko.observable(param ? param.allowMapUpload : 0);
            self.allowDocUpload = ko.observable(param ? param.allowDocUpload : 0);
            self.allowDocRef = ko.observable(param ? param.allowDocRef : 0);
            self.allowAvatarUpload = ko.observable(param ? param.allowAvatarUpload : 0);
            self.allowAvatarRef = ko.observable(param ? param.allowAvatarRef : 0);

            self.currentRoleCategoryId.subscribe(function(newCategoryId) {
                let newCategory = _.find(self.RoleCategoryList(), function(roleCategory) { return roleCategory.PersonInfoCategoryId === newCategoryId; });
                newCategory.loadRoleItems(newCategory.PersonInfoCategoryId);
                self.currentCategory(newCategory);
            });

        }
        setRoleAuth(param: IPersonRole) {
            let self = this;
            self.allowMapBrowse = ko.observable(param ? param.allowMapBrowse : 0);
            self.allowMapUpload = ko.observable(param ? param.allowMapUpload : 0);
            self.allowDocUpload = ko.observable(param ? param.allowDocUpload : 0);
            self.allowDocRef = ko.observable(param ? param.allowDocRef : 0);
            self.allowAvatarUpload = ko.observable(param ? param.allowAvatarUpload : 0);
            self.allowAvatarRef = ko.observable(param ? param.allowAvatarRef : 0);
        }
        loadRoleCategoriesList(RoleId): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.RoleCategoryList([]);
            service.getAllPersonCategoryAuthByRoleId(RoleId).done(function(result: Array<IPersonRoleCategory>) {
                _.forEach(result, function(iPersonRoleCategory: IPersonRoleCategory) {
                    self.RoleCategoryList.push(new PersonRoleCategory(iPersonRoleCategory));
                });
                if (self.RoleCategoryList().length > 0) {
                    self.currentRoleCategoryId(self.RoleCategoryList()[0].PersonInfoCategoryId);
                }
                else {
                    alert(text('Msg_217'));
                }
                dfd.resolve();
            });
            return dfd.promise();
        }

    }
    export class PersonRoleCategory {
        PersonInfoCategoryId: string;
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
        constructor(param: IPersonRoleCategory) {
            let self = this;
            self.PersonInfoCategoryId = param ? param.PersonInfoCategoryId : '';
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
            for (let i = 1; i < 1000; i++) {
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
            if (self.roleItemList().length < 1) {
                alert(text('Msg_217'));
            }
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
        PersonInfoItemAuthorityID: string;
        PersonInfoCategoryID: string;
        OtherPeopleAuthority: number;
        SelfAuthority: number;
        constructor(param: IPersonRoleItem) {
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

