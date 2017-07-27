module nts.uk.com.view.cas001.b.viewmodel {
    import windows = nts.uk.ui.windows;
    import errors = nts.uk.ui.errors;
    import resource = nts.uk.resource;

    export class ScreenModel {
        roleList: KnockoutObservableArray<any>;
        categoryList: KnockoutObservableArray<CategoryAuth>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        currentRoleCode: KnockoutObservable<string>;
        companyCode: KnockoutObservable<string>;
        itemSetting: KnockoutObservableArray<any>;
        selectItemCode: any;

        constructor() {
            var self = this;
            self.init();


        }
        init(): void {
            var self = this;
            self.roleList = ko.observableArray([new PersonRole({roleCode:"1", roleName:'A2',selfAuth:true,otherAuth:true}), new PersonRole({roleCode:'2', roleName:'B', selfAuth: true,otherAuth:false})]);
            self.categoryList = ko.observableArray([]);
            self.currentRoleCode = ko.observable('');
            self.columns = ko.observableArray([
                { headerText: 'コード', key: 'roleCode', width: 100, hidden: true },
                { headerText: '他人', key: 'selfAuth', width: 50,template: "<input type='checkbox' checked='${selfAuth}'/>"},
                { headerText: '本人', key: 'otherAuth', width: 50,template: "<input type='checkbox' checked='${otherAuth}'/>"},
                { headerText: 'カテゴリ名', key: 'roleName', width: 200 },
                { headerText: '説明', key: 'description', width: 50, hidden: true }
            ]);
            self.companyCode = ko.observable('');
            self.itemSetting = ko.observableArray([
                { code: '1', name: '非表示' },
                { code: '2', name: '参照のみ' },
                { code: '3', name: '更新' }
            ]);
            self.selectItemCode = ko.observable(1);

        }
        creatCategory(){
            windows.close();
        }
        closeDialog(){
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