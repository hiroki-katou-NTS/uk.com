module nts.uk.com.view.cas001.d.viewmodel {
    import close = nts.uk.ui.windows.close;
    import errors = nts.uk.ui.errors;
    import resource = nts.uk.resource;

    export class ScreenModel {
        categoryList: KnockoutObservableArray<CategoryAuth> = ko.observableArray([]);;
        currentRoleCode: KnockoutObservable<string> = ko.observable('');
        currentRole: KnockoutObservable<PersonRole> = ko.observable(new PersonRole({ roleId: "99900000-0000-0000-0000-000000000001", roleCode: "0001", roleName: 'A1' }));

        constructor() {
            var self = this;
            self.categoryList.subscribe(data => {
                if (data) {
                    $("#grid").igGrid("option", "dataSource", data);
                }
            });
            self.start();

        }

        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.categoryList.removeAll();
            service.getAllCategory(self.currentRole().roleCode).done(function(data: Array<any>) {
                console.log(data);
                if (data.length > 0) {
                    _.each(data, function(obj) {
                        self.categoryList.push(new CategoryAuth({
                            categoryId: obj.categoryId,
                            categoryCode: obj.categoryCode,
                            categoryName: obj.categoryName,
                            selfAuth: obj.allowPersonRef == 1 ? true : false,
                            otherAuth: obj.allowOtherRef == 1 ? true : false
                        }));
                    })
                }

            });

            return dfd.promise();

        }

        creatCategory() {
            let self = this;

            close();
        }
        closeDialog() {
            close();
        }
    }
    interface IPersonRole {
        roleId: string;
        roleCode: string;
        roleName: string;
    }
    export class PersonRole {
        roleId: string;
        roleCode: string;
        roleName: string;

        constructor(params: IPersonRole) {
            this.roleId = params.roleId;
            this.roleCode = params.roleCode;
            this.roleName = params.roleName;
        }
    }
    interface ICategoryAuth {
        categoryId: string;
        categoryCode: string;
        categoryName: string;
        selfAuth?: boolean;
        otherAuth?: boolean;
    }
    class CategoryAuth {
        categoryId: string;
        categoryCode: string;
        categoryName: string;
        selfAuth: boolean;
        otherAuth: boolean;
        constructor(param: ICategoryAuth) {
            this.categoryId = param.categoryId;
            this.categoryCode = param.categoryCode;
            this.categoryName = param.categoryName;
            this.selfAuth = param.selfAuth;
            this.otherAuth = param.otherAuth;
        }
    }
}