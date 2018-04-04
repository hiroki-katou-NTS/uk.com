module nts.uk.com.view.cas001.a.viewmodel {
    import alert = nts.uk.ui.dialog.alert;
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import dialog = nts.uk.ui.dialog.info;
    import ccg = nts.uk.com.view.ccg025.a;
    import model = nts.uk.com.view.ccg025.a.component.model;
    import color = nts.uk.ui.jqueryExtentions.ntsGrid.color;
    export class ScreenModel {

        personRoleList: KnockoutObservableArray<PersonRole> = ko.observableArray([]);
        currentRole: KnockoutObservable<PersonRole> = ko.observable(new PersonRole(null));
        currentRoleId: KnockoutObservable<string> = ko.observable('');
        roundingRules: KnockoutObservableArray<any> = ko.observableArray([
            { code: 1, name: getText('Enum_PersonInfoPermissionType_YES') },
            { code: 0, name: getText('Enum_PersonInfoPermissionType_NO') }
        ]);
        itemListCbb: KnockoutObservableArray<any> = ko.observableArray([
            { code: 1, name: getText('Enum_PersonInfoAuthTypes_HIDE') },
            { code: 2, name: getText('Enum_PersonInfoAuthTypes_REFERENCE') },
            { code: 3, name: getText('Enum_PersonInfoAuthTypes_UPDATE') }
        ]);
        anotherSelectedAll: KnockoutObservable<number> = ko.observable(1);
        seftSelectedAll: KnockoutObservable<number> = ko.observable(1);
        currentCategoryId: KnockoutObservable<string> = ko.observable('');
        allowPersonRef: KnockoutObservable<number> = ko.observable(1);
        allowOtherRef: KnockoutObservable<number> = ko.observable(1);
        roleCategoryList: KnockoutObservableArray<PersonRoleCategory> = ko.observableArray([]);
        checkboxSelectedAll: KnockoutObservable<boolean> = ko.observable(false);
        component: ccg.component.viewmodel.ComponentModel = new ccg.component.viewmodel.ComponentModel({
            roleType: 8,
            multiple: false
        });
        listRole: KnockoutObservableArray<PersonRole> = ko.observableArray([]);

        constructor() {
            let self = this;

            self.component.columns([
                { headerText: getText("CCG025_3"), prop: 'roleId', width: 50, hidden: true },
                { headerText: getText("CCG025_3"), prop: 'roleCode', width: 50 },
                { headerText: getText("CCG025_4"), prop: 'name', width: 150 }
            ]);

            self.component.currentCode.subscribe(function(newRoleId) {
                if (self.personRoleList().length < 1) {
                    return;
                }
                self.currentRoleId(newRoleId);
            });

            self.currentRoleId.subscribe(function(newRoleId) {

                if (newRoleId == "" || self.personRoleList().length < 1) {
                    return;
                }


                let newPersonRole = _.find(self.personRoleList(), (role) => { return role.roleId === newRoleId });


                service.getPersonRoleAuth(newRoleId).done((result: IPersonRole) => {

                    newPersonRole.setRoleAuth(result);

                    self.currentRole(newPersonRole);
                });

                newPersonRole.loadRoleCategoriesList(newRoleId, false).done(() => {
                    if (!self.currentCategoryId()) {
                        newPersonRole.setCtgSelectedId(self.roleCategoryList());
                    }


                });

            });

            self.currentCategoryId.subscribe((categoryId) => {

                if (!categoryId) {
                    return;
                }

                let newCategory = _.find(self.roleCategoryList(), (roleCategory) => {

                    return roleCategory.categoryId === categoryId;

                });

                service.getCategoryAuth(self.currentRoleId(), categoryId).done((result: IPersonRoleCategory) => {

                    newCategory.loadRoleItems(self.currentRoleId(), categoryId).done(() => {

                        newCategory.setCategoryAuth(result);

                        self.currentRole().currentCategory(newCategory);

                    }).always(() => {

                    });

                });
            });



            self.checkboxSelectedAll.subscribe((newValue) => {


                if (!self.currentRole().currentCategory()) {
                    return;
                }

                let currentList = self.currentRole().currentCategory().roleItemList();

                _.forEach(currentList, (item) => {
                    item.isChecked = newValue;
                });

                $("#item_role_table_body").igGrid("option", "dataSource", currentList);
            });

            self.allowOtherRef.subscribe((newValue) => {

                let grid = $("#item_role_table_body");
                let ds = grid.igGrid("option", "dataSource");
                if (ds == null) {
                    return;
                }

                grid.ntsGrid(newValue == 0 ? "disableNtsControls" : "enableNtsControls", "otherAuth", "SwitchButtons");

                grid.ntsGrid(self.isDisableAll() ? "disableNtsControls" : "enableNtsControls", "isChecked", "CheckBox");

            });

            self.allowPersonRef.subscribe((newValue) => {

                let grid = $("#item_role_table_body");
                let ds = grid.igGrid("option", "dataSource");
                if (ds == null) {
                    return;
                }

                grid.ntsGrid(newValue == 0 ? "disableNtsControls" : "enableNtsControls", "selfAuth", "SwitchButtons");

                grid.ntsGrid(self.isDisableAll() ? "disableNtsControls" : "enableNtsControls", "isChecked", "CheckBox");


            });


        }


        changeAll(parrentId, changeValue) {
            let self = this,
                currentList = self.currentRole().currentCategory().roleItemList(),
                selectItemList = _.find(currentList, (i) => {
                    return i.isChecked;
                }),
                changeVal = changeValue < 1 ? 1 : changeValue > 3 ? 3 : changeValue;


            if (!selectItemList) {
                dialog({ messageId: "Msg_664" });
                return;
            }

            _.forEach(currentList, (item) => {
                if (item.isChecked) {
                    parrentId == 'anotherSelectedAll_auth' ? item.otherAuth = changeVal : item.selfAuth = changeVal;
                }
            });

            $("#item_role_table_body").igGrid("option", "dataSource", currentList);
        }

        openDModal() {

            let self = this;

            setShared('personRole', self.currentRole());



            nts.uk.ui.windows.sub.modal('/view/cas/001/d/index.xhtml', { title: '' }).onClosed(function(): any {

                if (!getShared('isCanceled')) {
                    self.reload().always(() => {
                    });
                }
            });
        }

        openCModal() {

            let self = this,
                currentRole = {
                    roleList: self.personRoleList(),
                    personRole: self.currentRole()
                };

            setShared('currentRole', currentRole);



            nts.uk.ui.windows.sub.modal('/view/cas/001/c/index.xhtml', { title: '' }).onClosed(function(): any {

                if (!getShared('isCanceled')) {
                    self.reload().always(() => {



                    });
                }
            });
        }

        reload(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred(),
                personRole = self.currentRole(),
                selectedId = self.currentCategoryId(),
                grid = $("#item_role_table_body");;

            personRole.loadRoleCategoriesList(personRole.roleId, true).done(function() {

                if (self.roleCategoryList().length > 0) {

                    self.currentRole().currentCategory().loadRoleItems(self.currentRoleId(), selectedId).done(function() {
                        self.checkboxSelectedAll(false);
                    });

                }
                else {
                    dialog({ messageId: "Msg_217" });
                }

                dfd.resolve();

            });

            return dfd.promise();
        }
        start(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();

//            self.initializationItemGrid();

            self.loadPersonRoleList().done(function() {

                if (self.personRoleList().length > 0) {
                    let selectedId = self.currentRoleId() !== '' ? self.currentRoleId() : self.personRoleList()[0].roleId;

                    self.currentRoleId(selectedId);


                } else {

                    dialog({ messageId: "Msg_364" }).then(function() {
                        nts.uk.request.jump("/view/ccg/008/a/index.xhtml");
                    });

                }

                dfd.resolve();

            });

            return dfd.promise();
        }

        loadPersonRoleList(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();


            self.component.startPage().done(() => {
                self.personRoleList.removeAll();

                _.forEach(self.component.listRole(), function(iPersonRole: IPersonRole) {

                    self.personRoleList(_.map(self.component.listRole(), x => new PersonRole(x)));

                });



                dfd.resolve();

            });
            return dfd.promise();
        }

        saveData() {
            let self = this,

                command = self.createSaveCommand();

            service.savePersonRole(command).done(function() {

                dialog({ messageId: "Msg_15" }).then(function() {

                    self.reload().always(() => {

                    });
                });

            }).fail(function(res) {

                alert(res);

            });
        }
        createSaveCommand() {

            let self = this;

            return new PersonRoleCommand(self.currentRole());

        }

        isHistoryNotCons() {
            let self = this,
                currentCtg = self.currentRole().currentCategory();
            return (currentCtg.categoryType !== 1 && currentCtg.personEmployeeType === 2);
        }

        genCategoryTypeText() {
            let self = this,
                currentCtgType = self.currentRole().currentCategory().categoryType;

            switch (currentCtgType) {
                case 1: return getText('Enum_CategoryType_SINGLE_INFO');
                case 2: return getText('Enum_CategoryType_MULTI_INFO');
                case 3: return getText('Enum_CategoryType_CONTINUOUS_HISTORY');
                case 4: return getText('Enum_CategoryType_NODUPLICATE_HISTORY');
                case 5: return getText('Enum_CategoryType_DUPLICATE_HISTORY');
                case 6: return getText('Enum_CategoryType_CONTINUOUS_HISTORY');
                default: return '';

            }
        }

        isDisableAll() {
            let self = this;
            return self.allowPersonRef() === 0 && self.allowOtherRef() === 0;
        }
    }
    export interface IPersonRole {
        roleId: string;
        roleCode: string;
        name: string;
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
        setting: boolean;
        categoryType: number;
        personEmployeeType: number;
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
        setting: boolean;
        requiredAtr: string;
        itemName: string;
        parrentCd: string;
        otherAuth: number;
        selfAuth: number;
        dataType: number;
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
        currentCategory: KnockoutObservable<PersonRoleCategory> = ko.observable(null);

        constructor(param: IPersonRole) {
            let self = this;
            self.roleId = param ? param.roleId : '';
            self.roleCode = param ? param.roleCode : '';
            self.roleName = param ? param.name : '';
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


        loadRoleCategoriesList(RoleId, isReload): JQueryPromise<any> {
            var self = this,
                dfd = $.Deferred();
            let screenModel = __viewContext['screenModel'];

            service.getCategoryRoleList(RoleId).done(function(result: Array<IPersonRoleCategory>) {



                if (result.length <= 0) {
                    screenModel.roleCategoryList(_.map(result, x => new PersonRoleCategory(x)));
                    dialog({ messageId: "Msg_217" });
                    dfd.resolve();
                }

                if (!isReload) {
                    if (screenModel.currentCategoryId()) {

                        self.setCtgSelectedId(result);
                    }
                }
                screenModel.roleCategoryList(_.map(result, x => new PersonRoleCategory(x)));




                dfd.resolve();
            });
            return dfd.promise();
        }

        setCtgSelectedId(result) {
            let screenModel = __viewContext['screenModel'];
            let oldValue = screenModel.currentCategoryId();

            screenModel.currentCategoryId(result[0].categoryId);

            if (screenModel.currentCategoryId() == oldValue) {

                screenModel.currentCategoryId.valueHasMutated();

            }
        }
    }

    export class PersonRoleCategory {

        categoryId: string;
        categoryName: string;
        categoryType: number;
        setting: boolean;
        personEmployeeType: number;
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
        roleItemDatas: KnockoutObservableArray<PersonRoleItem> = ko.observableArray([]);
        itemListCbb: KnockoutObservableArray<any> = ko.observableArray([
            { code: 1, name: getText('Enum_PersonInfoAuthTypes_HIDE') },
            { code: 2, name: getText('Enum_PersonInfoAuthTypes_REFERENCE') },
            { code: 3, name: getText('Enum_PersonInfoAuthTypes_UPDATE') }
        ]);

        anotherSelectedAll: KnockoutObservable<number> = ko.observable(1);
        seftSelectedAll: KnockoutObservable<number> = ko.observable(1);
        allowPersonRef: KnockoutObservable<number> = ko.observable(1);
        allowOtherRef: KnockoutObservable<number> = ko.observable(1);

        constructor(param: IPersonRoleCategory) {
            let self = this;
            self.categoryId = param ? param.categoryId : '';
            self.categoryName = param ? param.categoryName : '';
            self.categoryType = param ? param.categoryType : 0;
            self.setting = param ? param.setting : false;
            self.personEmployeeType = param ? param.personEmployeeType : 0;
            self.allowOtherCompanyRef = ko.observable(param ? param.allowOtherCompanyRef : 0);
            self.selfPastHisAuth = ko.observable(param ? param.selfPastHisAuth : 1);
            self.selfFutureHisAuth = ko.observable(param ? param.selfFutureHisAuth : 1);
            self.selfAllowDelHis = ko.observable(param ? param.selfAllowDelHis : 0);
            self.selfAllowAddHis = ko.observable(param ? param.selfAllowAddHis : 0);
            self.otherPastHisAuth = ko.observable(param ? param.otherPastHisAuth : 1);
            self.otherFutureHisAuth = ko.observable(param ? param.otherFutureHisAuth : 1);
            self.otherAllowDelHis = ko.observable(param ? param.otherAllowDelHis : 0);
            self.otherAllowAddHis = ko.observable(param ? param.otherAllowAddHis : 0);
            self.selfAllowDelMulti = ko.observable(param ? param.selfAllowDelMulti : 0);
            self.selfAllowAddMulti = ko.observable(param ? param.selfAllowAddMulti : 0);
            self.otherAllowDelMulti = ko.observable(param ? param.otherAllowDelMulti : 0);
            self.otherAllowAddMulti = ko.observable(param ? param.otherAllowAddMulti : 0);
        }

        setCategoryAuth(param: IPersonRoleCategory) {

            let self = this,
                screenModel = __viewContext['screenModel'];
            screenModel.allowPersonRef(param ? param.allowPersonRef : 0);
            screenModel.allowOtherRef(param ? param.allowOtherRef : 0);
            self.allowOtherCompanyRef = ko.observable(param ? param.allowOtherCompanyRef : 0);
            self.selfPastHisAuth = ko.observable(param ? param.selfPastHisAuth : 1);
            self.selfFutureHisAuth = ko.observable(param ? param.selfFutureHisAuth : 1);
            self.selfAllowDelHis = ko.observable(param ? param.selfAllowDelHis : 0);
            self.selfAllowAddHis = ko.observable(param ? param.selfAllowAddHis : 0);
            self.otherPastHisAuth = ko.observable(param ? param.otherPastHisAuth : 1);
            self.otherFutureHisAuth = ko.observable(param ? param.otherFutureHisAuth : 1);
            self.otherAllowDelHis = ko.observable(param ? param.otherAllowDelHis : 0);
            self.otherAllowAddHis = ko.observable(param ? param.otherAllowAddHis : 0);
            self.selfAllowDelMulti = ko.observable(param ? param.selfAllowDelMulti : 0);
            self.selfAllowAddMulti = ko.observable(param ? param.selfAllowAddMulti : 0);
            self.otherAllowDelMulti = ko.observable(param ? param.otherAllowDelMulti : 0);
            self.otherAllowAddMulti = ko.observable(param ? param.otherAllowAddMulti : 0);
        }

        loadRoleItems(roleId, CategoryId): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred(),
                grid = $("#item_role_table_body"),
                screenModel = __viewContext['screenModel'],
                switchString = `<div id='{0}_auth' class='selected_all_auth'
                                    data-bind="ntsSwitchButton: {
                                        options: itemListCbb,
                                        optionsValue:'code',
                                        optionsText: 'name',
                                        value: {0},
                                        enable: {1} }"></div>
                                <span id='selected_all_caret' class='caret-bottom outline'></span>`,
                checkboxString = `<div id='selected_all_ckb' data-bind='ntsCheckBox: { checked: checkboxSelectedAll, enable: !isDisableAll() }'></div>`,
                selectedAllString =   nts.uk.text.format(switchString, 'anotherSelectedAll', '!!allowOtherRef'),
                selfSelectedAllString = nts.uk.text.format(switchString, 'seftSelectedAll', '!!allowPersonRef');

            let array2E = [{
                value: '1',
                text: getText('Enum_PersonInfoAuthTypes_HIDE')
            }, {
                    value: '2',
                    text: getText('Enum_PersonInfoAuthTypes_REFERENCE')
                }],
                array3E = [{
                    value: '1',
                    text: getText('Enum_PersonInfoAuthTypes_HIDE')
                }, {
                        value: '2',
                        text: getText('Enum_PersonInfoAuthTypes_REFERENCE')
                    }, {
                        value: '3',
                        text: getText('Enum_PersonInfoAuthTypes_UPDATE')
                    }];



            service.getPersonRoleItemList(roleId, CategoryId).done(function(result: Array<IPersonRoleItem>) {
                self.roleItemDatas(_.map(result, x => new PersonRoleItem(x)));
                self.roleItemList(_.filter(_.map(result, x => new PersonRoleItem(x)), ['parrentCd', null]));
                if (self.roleItemList().length < 1) {
                    dialog({ messageId: "Msg_217" });
                }

                $("#item_role_table_body").ntsGrid({
                    width: '835px',
                    height: '315px',
                    dataSource: self.roleItemList(),
                    primaryKey: 'personItemDefId',
                    virtualization: true,
                    virtualizationMode: 'continuous',
                    enter: 'right',
                    columns: [
                        { headerText: checkboxString, key: 'isChecked', dataType: 'boolean', width: '48px',  ntsControl: 'Checkbox' },
                        { headerText: getText('CAS001_69'), key: 'setting', dataType: 'string', width: '48px', formatter: makeIcon },
                        { headerText: '', key: 'requiredAtr', dataType: 'string', width: '34px', hidden: true },
                        { headerText: '', key: 'personItemDefId', dataType: 'string', width: '34px', hidden: true },
                        { headerText: getText('CAS001_47'), key: 'itemName', dataType: 'string', width: '255px' },
                        { headerText: getText('CAS001_48') + selectedAllString, key: 'otherAuth', dataType: 'string', width: '232px', ntsControl: 'SwitchButtons' },
                        { headerText: getText('CAS001_52') + selfSelectedAllString , key: 'selfAuth', dataType: 'string', width: '232px', ntsControl: 'SwitchButtons' },
                    ],
                    ntsControls: [
                        { name: 'Checkbox', options: { value: 1, text: '' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true },
                        {
                            name: 'SwitchButtons',
                            options: array3E,
                            optionsValue: 'value',
                            optionsText: 'text',
                            controlType: 'SwitchButtons',
                            enable: true
                        }
                    ],
                    features: [{ name: 'Resizing' },
                        {
                            name: 'Selection',
                            mode: 'row',
                            multipleSelection: true
                        }
                    ],
                    ntsFeatures: [{
                        name: 'CellState',
                        rowId: 'rowId',
                        columnKey: 'columnKey',
                        state: 'state',
                        states: []
                    }],
                });
                
                ko.applyBindings(__viewContext['screenModel'], nts.uk.ui.ig.grid.header.getCell('item_role_table_body','isChecked')[0]);
                ko.applyBindings(__viewContext['screenModel'], nts.uk.ui.ig.grid.header.getCell('item_role_table_body','otherAuth')[0]);
                ko.applyBindings(__viewContext['screenModel'], nts.uk.ui.ig.grid.header.getCell('item_role_table_body','selfAuth')[0]);                

                dfd.resolve();

            }).always(() => {
                //register click change all event
                $(() => {
                    $('#anotherSelectedAll_auth, #seftSelectedAll_auth').on('click', '.nts-switch-button', function() {
                        let id = $(this).parent().attr('id');
                        screenModel.changeAll(id, id === 'anotherSelectedAll_auth' ? screenModel.anotherSelectedAll() : screenModel.seftSelectedAll());

                    });

                    $('.selected_all_auth').keyup(function(event) {
                        let id = $(this).attr('id');
                        let code = event.keyCode,
                            currentValue = id === 'anotherSelectedAll_auth' ? self.anotherSelectedAll() : self.seftSelectedAll(),
                            Value;
                        switch (code) {
                            case 37:
                            case 38: Value = currentValue - 1;
                                break;
                            case 39:
                            case 40: Value = currentValue + 1;
                                break;
                        }

                        if (!Value) {
                            return;
                        }

                        screenModel.changeAll(id, Value);

                    });

                    $('.ui-iggrid-header').on('focus', function() {

                        if ($(this).find('.nts-switch-button').is(':enabled')) {
                            $(this).find('.selected_all_auth').focus();
                        }
                    });
                });

            });

            return dfd.promise();
        }
    }

    export class PersonRoleItem {
        personItemDefId: string;
        isChecked: boolean = false;
        setting: boolean;
        requiredAtr: string;
        itemName: string;
        parrentCd: string;
        otherAuth: number;
        selfAuth: number;
        itemCd: string;
        dataType: number;

        constructor(param: IPersonRoleItem) {
            let self = this;
            self.personItemDefId = param ? param.personItemDefId : '';
            self.setting = param ? param.setting : false;
            self.requiredAtr = param ? param.requiredAtr : 'false';
            self.itemName = param ? param.itemName : '';
            self.parrentCd = param ? param.parrentCd : '';
            self.itemCd = param ? param.itemCd : '';
            self.otherAuth = this.setting === true ? param ? param.otherAuth : 1 : 1;
            self.selfAuth = this.setting === true ? param ? param.selfAuth : 1 : 1;
            self.dataType = param ? param.dataType : '';
        }
    }

    export class PersonRoleCommand {
        roleId: string;
        roleCode: string;
        roleName: string;
        allowMapBrowse: number;
        allowMapUpload: number;
        allowDocUpload: number;
        allowDocRef: number;
        allowAvatarUpload: number;
        allowAvatarRef: number;
        currentCategory: PersonRoleCategoryCommand = null;
        constructor(param: PersonRole) {
            this.roleId = param.roleId;
            this.roleCode = param.roleCode;
            this.roleName = param.roleName;
            this.allowMapBrowse = param.allowMapBrowse();
            this.allowMapUpload = param.allowMapUpload();
            this.allowDocUpload = param.allowDocUpload();
            this.allowDocRef = param.allowDocRef();
            this.allowAvatarUpload = param.allowAvatarUpload();
            this.allowAvatarRef = param.allowAvatarRef();
            this.currentCategory = new PersonRoleCategoryCommand(param.currentCategory());
        }
    }
    export class PersonRoleCategoryCommand {
        categoryId: string;
        categoryName: string;
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
        items: Array<PersonRoleItemCommand> = [];
        constructor(param: PersonRoleCategory) {

            let sm: ScreenModel = __viewContext['screenModel'];

            this.categoryId = param.categoryId;
            this.categoryName = param.categoryName;
            this.categoryType = param.categoryType;
            this.allowPersonRef = sm.allowPersonRef();
            this.allowOtherRef = sm.allowOtherRef();
            this.allowOtherCompanyRef = param.allowOtherCompanyRef();
            this.selfPastHisAuth = param.selfPastHisAuth();
            this.selfFutureHisAuth = param.selfFutureHisAuth();
            this.selfAllowDelHis = param.selfAllowDelHis();
            this.selfAllowAddHis = param.selfAllowAddHis();
            this.otherPastHisAuth = param.otherPastHisAuth();
            this.otherFutureHisAuth = param.otherFutureHisAuth();
            this.otherAllowDelHis = param.otherAllowDelHis();
            this.otherAllowAddHis = param.otherAllowAddHis();
            this.selfAllowDelMulti = param.selfAllowDelMulti();
            this.selfAllowAddMulti = param.selfAllowAddMulti();
            this.otherAllowDelMulti = param.otherAllowDelMulti();
            this.otherAllowAddMulti = param.otherAllowAddMulti();
            //add parrent item
            this.items = _.map(param.roleItemList(), x => new PersonRoleItemCommand(x));
            //add child item
            this.addChildItem(this.items);
        }

        addChildItem(items: Array<PersonRoleItemCommand>) {
            let sm: ScreenModel = __viewContext['screenModel'],
                itemDatas = sm.currentRole().currentCategory().roleItemDatas();
            //create loop parent for get child item 
            _.forEach(items, function(parentItem: PersonRoleItemCommand) {

                let childItems = _.filter(itemDatas, { 'parrentCd': parentItem.itemCd });
                //set atr same parent item
                _.forEach(childItems, function(childItem: PersonRoleItem) {
                    childItem.selfAuth = parentItem.selfAuth;
                    childItem.otherAuth = parentItem.otherAuth;

                    items.push(new PersonRoleItemCommand(childItem));
                });

            });



        }

    }
    export class PersonRoleItemCommand {
        personItemDefId: string;
        itemCd: string;
        parrentCd: string;
        otherAuth: number;
        selfAuth: number;
        constructor(param: PersonRoleItem) {
            this.personItemDefId = param.personItemDefId;
            this.parrentCd = param.parrentCd;
            this.otherAuth = param.otherAuth;
            this.selfAuth = param.selfAuth;
            this.itemCd = param.itemCd;
        }

    }

    export class CellState {
        rowId: number;
        columnKey: string;
        state: Array<any>
        constructor(rowId: any, columnKey: string, state: Array<any>) {
            this.rowId = rowId;
            this.columnKey = columnKey;
            this.state = state;
        }
    }

}

function makeIcon(value, row) {
    if (value == "true")
        return "●";
    return '';
}



