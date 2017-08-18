module nts.uk.com.view.cas001.c.viewmodel {
    import error = nts.uk.ui.errors;
    import text = nts.uk.resource.getText;
    import close = nts.uk.ui.windows.close;
    import alert = nts.uk.ui.dialog.alert;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        roleList: KnockoutObservableArray<any> = ko.observableArray([]);
        roleCodeArray = [];
        roleCopy: KnockoutObservable<PersonRole> = ko.observable(getShared('personRole'));

        constructor() {
            var self = this;

            self.roleList.subscribe(data => {
                if (data) {
                    $("#roles").igGrid("option", "dataSource", data);
                }else{
                    $("#roles").igGrid("option", "dataSource", []);
                }
            });

            self.start();
        }

        start() {
            let self = this;
            self.roleList.removeAll();

            service.getAllPersonRole().done(function(data: Array<any>) {
                if (data.length > 0) {
                    _.each(data, function(c) {
                        self.roleList.push(new PersonRole(c.roleId, c.roleCode, c.roleName));
                    });
                }
            });
        }

        createCategory() {
            let data = (__viewContext["viewModel"].roleList());
            let self = this;
            self.roleCodeArray = [];
            _.find(data, function(role:PersonRole) {
                if (role.selected === true) {
                    self.roleCodeArray.push(role.roleId);
                }
            });
            if (self.roleCodeArray.length > 0) {
                nts.uk.ui.dialog.confirm(text('Msg_64')).ifYes(() => {
                    let roleObj = { roleIdDestination: self.roleCopy().roleId, roleIds: self.roleCodeArray };
                    service.update(roleObj).done(function(obj) {
                        nts.uk.ui.dialog.info({ messageId: "Msg_20" }).then(function() {
                            close();
                        });
                    }).fail(function(res: any) {
                        alert(res.message);
                    })

                }).ifCancel(function() {
                })
            }else{
                alert(text('Msg_365'));
            }

        }
        
        closeDialog() {
            close();
        }
    }

    export class PersonRole {
        check: boolean = false;
        roleId: string;
        roleCode: string;
        roleName: string;
        constructor(roleId: string, roleCode: string, roleName: string) {
            this.roleId = roleId;
            this.roleCode = roleCode;
            this.roleName = roleName;
        }
    }
}