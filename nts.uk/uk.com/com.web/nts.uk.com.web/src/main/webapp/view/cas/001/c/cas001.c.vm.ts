module nts.uk.com.view.cas001.c.viewmodel {
    import windows = nts.uk.ui.windows;
    import errors = nts.uk.ui.errors;
    import resource = nts.uk.resource;

    export class ScreenModel {
        roleList: KnockoutObservableArray<any>;
        companyCode: KnockoutObservable<string>;
        itemSetting: KnockoutObservableArray<any>;
        selectItemCode: any;

        constructor() {
            var self = this;
            self.init();
            $("#roles").igGrid({
                columns: [
                    {
                        headerText: resource.getText('CAS001_7'), key: 'selfAuth', width: "40px", height: "40px", template: "<input type='checkbox' checked='${selfAuth} tabindex='1''/>"
                    }
                    ,
                    { headerText: resource.getText('CAS001_8'), key: "roleCode", dataType: "string", width: "90px", height: "40px" },
                    { headerText: resource.getText('CAS001_9'), key: "roleName", dataType: "string", width: "120px", height: "40px" },
                    { headerText: '説明', key: 'description', width: "35px", hidden: true, height: "40px" },

                ],
                primaryKey: 'roleCode',
                autoGenerateColumns: false,
                autoCommit: true,
                dataSource: self.roleList(),
                width: "300px",
                height: "300px",
                features: [
                    {
                        name: "Updating",
                        enableAddRow: false,
                        editMode: "row",
                        enableDeleteRow: false,
                        columnSettings: [
                            { columnKey: "selfAuth", readOnly: true },
                            { columnKey: "roleCode", readOnly: true },
                            { columnKey: "roleName", readOnly: true },
                            { columnKey: "description", readOnly: true },
                        ]
                    }]
            });

        }
        init(): void {
            var self = this;
            self.roleList = ko.observableArray([new PersonRole({ roleCode: "1", roleName: 'A2', selfAuth: true, otherAuth: true }), new PersonRole({ roleCode: '2', roleName: 'B', selfAuth: true, otherAuth: false })]);
            self.companyCode = ko.observable('');
            self.itemSetting = ko.observableArray([
                { code: '1', name: '非表示' },
                { code: '2', name: '参照のみ' },
                { code: '3', name: '更新' }
            ]);
            self.selectItemCode = ko.observable(1);

        }
        creatCategory() {
            windows.close();
        }
        closeDialog() {
            windows.close();
        }
    }
    interface IPersonRole {
        roleCode: string;
        roleName: string;
        selfAuth: boolean;
        otherAuth: boolean;
    }
    export class PersonRole {
        roleCode: string;
        roleName: string;
        selfAuth: boolean;
        otherAuth: boolean;
        description: string;

        constructor(params: IPersonRole) {
            this.roleCode = params.roleCode;
            this.roleName = params.roleName;
            this.selfAuth = params.selfAuth;
            this.otherAuth = params.otherAuth;
            this.description = this.roleCode + this.roleName;
        }
    }
    interface ICategoryAuth {
        categoryCode: string;
        categoryName: string;
        isSetting: boolean;
    }
    class CategoryAuth {
        categoryCode: string;
        categoryName: string;
        isSetting: boolean;
        constructor(param: ICategoryAuth) {
            this.categoryCode = param.categoryCode;
            this.categoryName = param.categoryName;
            this.isSetting = param.isSetting || false;
        }
    }
}