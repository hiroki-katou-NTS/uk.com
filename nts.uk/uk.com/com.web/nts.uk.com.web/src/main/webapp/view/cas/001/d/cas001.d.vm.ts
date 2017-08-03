module nts.uk.com.view.cas001.d.viewmodel {
    import windows = nts.uk.ui.windows;
    import errors = nts.uk.ui.errors;
    import resource = nts.uk.resource;

    export class ScreenModel {
        roleList: KnockoutObservableArray<any> = ko.observableArray([]);;
        currentRoleCode: KnockoutObservable<string> = ko.observable('');

        constructor() {
            var self = this;
            self.roleList.subscribe(data => {
                if (data) {
                    $("#grid").igGrid("option", "dataSource", data);
                }
            });
            self.roleList([new PersonRole({ roleCode: "1", roleName: 'A2', selfAuth: true, otherAuth: true }), new PersonRole({ roleCode: '2', roleName: 'B', selfAuth: true, otherAuth: false })]);

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