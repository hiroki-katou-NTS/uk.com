module nts.uk.com.view.cas001.d.viewmodel {
    import close = nts.uk.ui.windows.close;
    import errors = nts.uk.ui.errors;
    import resource = nts.uk.resource;
    import alert = nts.uk.ui.dialog.alert;
    import getShared = nts.uk.ui.windows.getShared;
    export class ScreenModel {
        categoryList: KnockoutObservableArray<CategoryAuth> = ko.observableArray([]);
        categoryOrgin: KnockoutObservableArray<CategoryAuth> = ko.observableArray([]);
        currentRoleCode: KnockoutObservable<string> = ko.observable('');
        currentRole: KnockoutObservable<PersonRole> = ko.observable(getShared('personRole'));

        constructor() {
            var self = this;

            self.categoryList.subscribe(data => {
                if (data) {
                    $("#grid").igGrid("option", "dataSource", data);
                } else {
                    $("#grid").igGrid("option", "dataSource", []);
                }
            });

            self.start();
        }

        start() {
            let self = this,
                role: IPersonRole = ko.toJS(self.currentRole);

            self.categoryList.removeAll();
            service.getAllCategory(role.roleId).done(function(data: Array<any>) {
                if (data.length > 0) {
                    self.categoryList(_.map(data, x => new CategoryAuth({
                        categoryId: x.categoryId,
                        categoryCode: x.categoryCode,
                        categoryName: x.categoryName,
                        selfAuth: !!x.allowPersonRef,
                        otherAuth: !!x.allowOtherRef
                    })));
                }
            });
        }

        creatCategory() {
            let self = this,
                role: IPersonRole = ko.toJS(self.currentRole),
                data: Array<ICategoryAuth> = [],
                datas: Array<any> = [];
            if (self.categoryOrgin().length > 0) {
                data = _.uniqBy(self.categoryOrgin(), 'categoryId');
                datas = _(data)
                    .map((x: ICategoryAuth) => {
                        return {
                            roleId: role.roleId,
                            categoryId: x.categoryId,
                            allowPersonRef: Number(x.selfAuth),
                            allowOtherRef: Number(x.otherAuth)
                        };
                    })
                    .value();
                service.updateCategory({ lstCategory: datas }).done(function(data) {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                        close();
                    });
                }).fail(function(res) {
                    alert(res.message);
                })
            } else {
                data = _.uniqBy(self.categoryList(), 'categoryId');
                datas = _(data)
                    .map((x: ICategoryAuth) => {
                        return {
                            roleId: role.roleId,
                            categoryId: x.categoryId,
                            allowPersonRef: Number(x.selfAuth),
                            allowOtherRef: Number(x.otherAuth)
                        };
                    })
                    .value();
                service.updateCategory({ lstCategory: datas }).done(function(data) {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                        close();
                    });
                }).fail(function(res) {
                    alert(res.message);
                })
            }

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