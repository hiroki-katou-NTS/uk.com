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
        anotherSelectedAll: KnockoutObservable<number> = ko.observable(0);
        seftSelectedAll: KnockoutObservable<number> = ko.observable(0);
        currentCategoryId: KnockoutObservable<string> = ko.observable('');

        constructor() {
            let self = this;
            self.currentRoleId.subscribe(function(newRoleId) {

                let newPersonRole = _.find(self.personRoleList(), function(role) { return role.roleId === newRoleId });
                if (newPersonRole) {
                    service.getPersonRoleAuth(newPersonRole.roleId).done(function(result: IPersonRole) {

                        newPersonRole.loadRoleCategoriesList(newPersonRole.roleId).done(function() {
                            self.currentCategoryId("");
                            newPersonRole.setRoleAuth(result);
                            self.currentRole(newPersonRole);
                            if (self.currentRole().RoleCategoryList().length > 0) {
                                self.currentCategoryId(self.currentRole().RoleCategoryList()[0].categoryId);
                            }
                            else {
                                alert(text('Msg_217'));
                            }

                        });
                    });
                }
            });

            self.currentCategoryId.subscribe(function(categoryId) {
                if (categoryId == "") {
                    return;
                }

                let newCategory = _.find(self.currentRole().RoleCategoryList(), function(roleCategory) {

                    return roleCategory.categoryId === categoryId;

                });

                service.getAuthDetailByPId(categoryId).done(function(result: IPersonRoleCategory) {

                    newCategory.loadRoleItems(self.currentRoleId(), categoryId).done(function() {

                        newCategory.setCategoryAuth(result);

                        self.currentRole().currentCategory(newCategory);

                    });
                });
            });
            //register click change all event
            $(function() {
                $('#anotherSelectedAll_auth, #seftSelectedAll_auth').on('click', '.nts-switch-button', function() {

                    let parrent = $(this).parent().attr('id');

                    for (let item of self.currentRole().currentCategory().roleItemList()) {
                        parrent == 'anotherSelectedAll_auth' ? item.otherAuth = self.anotherSelectedAll() : item.selfAuth = self.seftSelectedAll();
                    }

                    $("#item_role_table_body").igGrid("option", "dataSource", self.currentRole().currentCategory().roleItemList());

                });
            });
        }
        changeItemListValue(attribute) {
            let self = this;
            for (let item of self.currentRole().currentCategory().roleItemList()) {
                attribute == 'other' ? item.otherAuth = self.anotherSelectedAll() : item.selfAuth = self.seftSelectedAll();
            }

            $("#item_role_table_body").igGrid("option", "dataSource", self.currentRole().currentCategory().roleItemList());
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

                primaryKey: 'itemName',

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
                            if (el.requiredAtr == '1') {
                                $(CheckboxCell).addClass('requiredCell');
                                $(IsConfigCell).addClass('requiredCell');
                                $(NameCell).addClass('requiredCell');
                            }
                        });
                },
                columns: [
                    { headerText: text('CAS001_69'), key: 'setting', dataType: 'string', width: '48px', formatter: makeIcon },
                    { headerText: '', key: 'requiredAtr', dataType: 'string', width: '34px', hidden: true },
                    { headerText: '', key: 'personItemDefId', dataType: 'string', width: '34px', hidden: true },
                    { headerText: text('CAS001_47'), key: 'itemName', dataType: 'string', width: '255px' },
                    { headerText: text('CAS001_48'), key: 'otherAuth', dataType: 'string', width: '232px', ntsControl: 'SwitchButtons' },
                    { headerText: text('CAS001_52'), key: 'selfAuth', dataType: 'string', width: '232px', ntsControl: 'SwitchButtons' },
                ],
                ntsControls: [
                    {
                        name: 'SwitchButtons', options: [{ value: '0', text: '非表示' }, { value: '1', text: '参照のみ' }, { value: '2', text: '更新' }],
                        optionsValue: 'value', optionsText: 'text', controlType: 'SwitchButtons', enable: true
                    },
                ],

            });
            //add switch to table header
            let switchString = "<div id=\'{0}_auth\' class=\'selected_all_auth\'"
                + "data-bind=\"ntsSwitchButton: {options: itemListCbb"
                + ",optionsValue:\'code\',optionsText: \'name\',value: {0},enable: true }\">"
                + "</div><span id=\'selected_all_caret\' class=\'caret-bottom outline\'></span>"

            let selectedAllString = nts.uk.text.format(switchString, 'anotherSelectedAll');

            let seftSelectedAllString = nts.uk.text.format(switchString, 'seftSelectedAll');

            nts.uk.ui.ig.grid.header.getCell('item_role_table_body', 'otherAuth').append($(selectedAllString));

            nts.uk.ui.ig.grid.header.getCell('item_role_table_body', 'selfAuth').append($(seftSelectedAllString));
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
        categoryId: string;
        categoryName: string;
        setting: number;
        categoryType: number;
        allowPersonRef: number;
        allowOtherRef: number;
        allowOtherCompanyRef: number;
        selfPastHisAuth: number;
        selfFutureHisAuth: number;
        selfAllowDelHis: number;
        selfAllowAddHis: number;
        otherPastHisAuth: number;
        otherFutureHisAuth: number;
        otherAllowDelHis: number;
        otherAllowAddHis: number;
        selfAllowDelMulti: number;
        selfAllowAddMulti: number;
        otherAllowDelMulti: number;
        otherAllowAddMulti: number;

    }
    export interface IPersonRoleItem {
        personItemDefId: string;
        setting: number;
        requiredAtr: string;
        itemName: string;
        otherAuth: number;
        selfAuth: number;
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
            service.getCategoryRoleList(RoleId).done(function(result: Array<IPersonRoleCategory>) {

                self.RoleCategoryList.removeAll();

                _.forEach(result, function(iPersonRoleCategory: IPersonRoleCategory) {
                    self.RoleCategoryList.push(new PersonRoleCategory(iPersonRoleCategory));
                });
                dfd.resolve();
            });
            return dfd.promise();
        }
    }

    export class PersonRoleCategory {

        categoryId: string;
        categoryName: string;
        categoryType: number;
        setting: number;
        allowPersonRef: KnockoutObservable<number>;
        allowOtherRef: KnockoutObservable<number>;
        allowOtherCompanyRef: KnockoutObservable<number>;
        selfPastHisAuth: KnockoutObservable<number>;
        selfFutureHisAuth: KnockoutObservable<number>;
        selfAllowDelHis: KnockoutObservable<number>;
        selfAllowAddHis: KnockoutObservable<number>;
        otherPastHisAuth: KnockoutObservable<number>;
        otherFutureHisAuth: KnockoutObservable<number>;
        otherAllowDelHis: KnockoutObservable<number>;
        otherAllowAddHis: KnockoutObservable<number>;
        selfAllowDelMulti: KnockoutObservable<number>;
        selfAllowAddMulti: KnockoutObservable<number>;
        otherAllowDelMulti: KnockoutObservable<number>;
        otherAllowAddMulti: KnockoutObservable<number>;
        roleItemList: KnockoutObservableArray<PersonRoleItem> = ko.observableArray([]);
        currentItem: KnockoutObservable<PersonRoleItem> = ko.observable(new PersonRoleItem(null));
        currentItemCodes: KnockoutObservableArray<string> = ko.observableArray([]);

        constructor(param: IPersonRoleCategory) {
            let self = this;
            self.categoryId = param ? param.categoryId : '';
            self.categoryName = param ? param.categoryName : '';
            self.categoryType = param ? param.categoryType : 0;
            self.setting = param ? param.setting : 0;
            self.allowPersonRef = ko.observable(param ? param.allowPersonRef : 0);
            self.allowOtherRef = ko.observable(param ? param.allowOtherRef : 0);
            self.allowOtherCompanyRef = ko.observable(param ? param.allowOtherCompanyRef : 0);
            self.selfPastHisAuth = ko.observable(param ? param.selfPastHisAuth : 0);
            self.selfFutureHisAuth = ko.observable(param ? param.selfFutureHisAuth : 0);
            self.selfAllowDelHis = ko.observable(param ? param.selfAllowDelHis : 0);
            self.selfAllowAddHis = ko.observable(param ? param.selfAllowAddHis : 0);
            self.otherPastHisAuth = ko.observable(param ? param.otherPastHisAuth : 0);
            self.otherFutureHisAuth = ko.observable(param ? param.otherFutureHisAuth : 0);
            self.otherAllowDelHis = ko.observable(param ? param.otherAllowDelHis : 0);
            self.otherAllowAddHis = ko.observable(param ? param.otherAllowAddHis : 0);
            self.selfAllowDelMulti = ko.observable(param ? param.selfAllowDelMulti : 0);
            self.selfAllowAddMulti = ko.observable(param ? param.selfAllowAddMulti : 0);
            self.otherAllowDelMulti = ko.observable(param ? param.otherAllowDelMulti : 0);
            self.otherAllowAddMulti = ko.observable(param ? param.otherAllowAddMulti : 0);
        }

        setCategoryAuth(param: IPersonRoleCategory) {

            let self = this;
            self.allowPersonRef = ko.observable(param ? param.allowPersonRef : 0);
            self.allowOtherRef = ko.observable(param ? param.allowOtherRef : 0);
            self.allowOtherCompanyRef = ko.observable(param ? param.allowOtherCompanyRef : 0);
            self.selfPastHisAuth = ko.observable(param ? param.selfPastHisAuth : 0);
            self.selfFutureHisAuth = ko.observable(param ? param.selfFutureHisAuth : 0);
            self.selfAllowDelHis = ko.observable(param ? param.selfAllowDelHis : 0);
            self.selfAllowAddHis = ko.observable(param ? param.selfAllowAddHis : 0);
            self.otherPastHisAuth = ko.observable(param ? param.otherPastHisAuth : 0);
            self.otherFutureHisAuth = ko.observable(param ? param.otherFutureHisAuth : 0);
            self.otherAllowDelHis = ko.observable(param ? param.otherAllowDelHis : 0);
            self.otherAllowAddHis = ko.observable(param ? param.otherAllowAddHis : 0);
            self.selfAllowDelMulti = ko.observable(param ? param.selfAllowDelMulti : 0);
            self.selfAllowAddMulti = ko.observable(param ? param.selfAllowAddMulti : 0);
            self.otherAllowDelMulti = ko.observable(param ? param.otherAllowDelMulti : 0);
            self.otherAllowAddMulti = ko.observable(param ? param.otherAllowAddMulti : 0);
        }

        loadRoleItems(roleId, CategoryId): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            service.getPersonRoleItemList(roleId, CategoryId).done(function(result: Array<IPersonRoleItem>) {

                self.roleItemList.removeAll();
                _.forEach(result, function(iPersonRoleItem: IPersonRoleItem) {
                    self.roleItemList.push(new PersonRoleItem(iPersonRoleItem));
                });

                if (self.roleItemList().length < 1) {
                    alert(text('Msg_217'));
                }

                $("#item_role_table_body").igGrid("option", "dataSource", self.roleItemList());

                dfd.resolve();

            });
            return dfd.promise();
        }
    }

    export class PersonRoleItem {
        personItemDefId: string;
        setting: number;
        requiredAtr: string;
        itemName: string;
        otherAuth: number;
        selfAuth: number;

        constructor(param: IPersonRoleItem) {
            let self = this;
            self.personItemDefId = param ? param.personItemDefId : '';
            self.setting = param ? param.setting : 0;
            self.requiredAtr = param ? param.requiredAtr : 'false';
            self.itemName = param ? param.itemName : '';
            self.otherAuth = param ? param.otherAuth : 0;
            self.selfAuth = param ? param.selfAuth : 0;
        }
    }
}

function makeIcon(value, row) {
    if (value == "true")
        return '&#8226;'
    return '';
}

