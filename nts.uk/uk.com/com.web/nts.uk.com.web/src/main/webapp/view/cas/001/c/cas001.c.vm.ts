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

        }
        init(): void {
            var self = this;
            self.roleList = ko.observableArray([]);
            self.companyCode = ko.observable('');


        }
        
        start(): JQueryPromise<any> {
            var dfd = $.Deferred();
            let self = this;
            service.getAllPersonRole().done(function(data: Array<any>) {
                if (data.length > 0) {
                    _.each(data, function(c) {
                        self.roleList.push(new PersonRole({ checkAll: true, roleCode: c.roleCode, roleName: c.roleName }));
                    });
                    $("#roles").igGrid({
                        columns: [
                            {
                                headerText: resource.getText('CAS001_7'), key: 'checkAll', width: "40px", height: "40px",
                                template: "<input type='checkbox'  checked='${checkAll} tabindex='1''/>"
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
                                    { columnKey: "checkAll", readOnly: true },
                                    { columnKey: "roleCode", readOnly: true },
                                    { columnKey: "roleName", readOnly: true }
                                ]
                            }]
                    });

                    console.log(self.roleList());
                }
            }).fail(function(mess) {
                dfd.resolve();
            })
            return dfd.promise();

        }
        creatCategory() {

            windows.close();
        }
        closeDialog() {
            service.update(null).done(function(data){
              console.log(data);  
            })
            windows.close();
        }
    }
    interface IPersonRole {
        checkAll: boolean;
        roleCode: string;
        roleName: string;
    }
    export class PersonRole {
        checkAll: boolean = false;
        roleCode: string;
        roleName: string;
        constructor(params: IPersonRole) {
            this.roleCode = params.roleCode;
            this.roleName = params.roleName;
            this.checkAll = params.checkAll || false;
        }
    }
}