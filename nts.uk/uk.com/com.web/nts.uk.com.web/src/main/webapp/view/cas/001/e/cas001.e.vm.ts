module nts.uk.com.view.cas001.e.viewmodel {
    import windows = nts.uk.ui.windows;
    import errors = nts.uk.ui.errors;
    import resource = nts.uk.resource;

    export class ScreenModel {
        roleList: KnockoutObservableArray<any>;
        roleCodeArray: KnockoutObservableArray<any>;

        constructor() {
            var self = this;
            self.init();
            $(function() {
                $(document).on('click', 'input[type="checkbox"]', function(evt) {
                    let id = $(evt.target).parents('tr').data('id');
                    self.roleCodeArray.push(id);
                    console.log(self.roleCodeArray());

                });
            });
        }
        init(): void {
            var self = this;
            self.roleList = ko.observableArray([]);
            self.roleCodeArray = ko.observableArray([]);
        }

        start(): JQueryPromise<any> {
            var dfd = $.Deferred();
            let self = this;
            service.getAllPersonRole().done(function(data: Array<any>) {
                if (data.length > 0) {
                    _.each(data, function(c) {
                        self.roleList.push(new PersonRole({check :false, roleCode: c.roleCode, roleName: c.roleName }));
                    });
                    self.bindGrid();
                }
            }).fail(function(mess) {
                dfd.resolve();
            })
            return dfd.promise();

        }

        bindGrid() {
            let self = this;
            $("#roles").igGrid({
                columns: [
                    {
                        headerText: resource.getText('CAS001_7'), key: 'check', width: "40px", height: "40px",
                        template: "<input type='checkbox' tabindex='1''/>"
                    }
                    ,
                    { headerText: resource.getText('CAS001_8'), key: "roleCode", dataType: "string", width: "90px", height: "40px" },
                    { headerText: resource.getText('CAS001_9'), key: "roleName", dataType: "string", width: "120px", height: "40px" },
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
                            { columnKey: "check", readOnly: true },
                            { columnKey: "roleCode", readOnly: true },
                            { columnKey: "roleName", readOnly: true }
                        ]
                    }, {

                        name: "Selection",
                        mode: "row"
                    }
                ]
            });
        }
        createCategory() {
            windows.close();
        }
        closeDialog() {
            windows.close();
        }
    }

    interface IPersonRole {
        check: boolean;
        roleCode: string;
        roleName: string;
    }

    export class PersonRole {
        check: boolean=false;
        roleCode: string;
        roleName: string;
        constructor(params: IPersonRole) {
            this.roleCode = params.roleCode;
            this.roleName = params.roleName;
        }
    }
}