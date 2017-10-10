module nts.uk.at.view.kdw006.e.viewmodel {
    export class ScreenModel {
        roleItems: KnockoutObservableArray<any>;
        functionalRestriction: KnockoutObservableArray<any>;
        selectedItem: KnockoutObservable<any>;
        columns1: KnockoutObservableArray<NtsGridListColumn>;
        columns2: KnockoutObservableArray<NtsGridListColumn>;

        constructor() {
            var self = this;
            self.roleItems = ko.observableArray([]);
            self.functionalRestriction = ko.observableArray([]);
            self.selectedItem = ko.observable();

            self.columns1 = ko.observableArray([
                { headerText: 'コード', key: 'roleId', width: 100 },
                { headerText: '名称', key: 'roleName', width: 150 }
            ]);

            self.selectedItem.subscribe(function(newValue) {
                self.getFuncRest(newValue);
            });

        }

        initGrid() {
            var self = this;
            $("#grid2").ntsGrid({
                width: '750px',
                height: '400px',
                dataSource: self.functionalRestriction(),
                primaryKey: 'functionNo',
                virtualization: true,
                virtualizationMode: 'continuous',
                columns: [
                    { headerText: 'コード', key: 'functionNo', dataType: 'number', width: '50px', hidden: true },
                    { headerText: '設定', key: 'displayName', dataType: 'string', width: '290px' },
                    { headerText: '利用区分', key: 'availability', dataType: 'boolean', width: '200px', ntsControl: 'Checkbox' },
                    { headerText: '説明', key: 'description', dataType: 'string', width: '230px' }
                ],
                features: [{ name: 'Resizing' }],
                ntsFeatures: [{ name: 'CopyPaste' }],
                ntsControls: [{ name: 'Checkbox', options: { value: 1, text: '' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true }]
            });
        }

        saveData() {
            let self = this;
            service.register(self.selectedItem(), self.functionalRestriction()).done(function(res: Array<RoleItem>) {
                nts.uk.ui.dialog.info({ messageId: "Msg_15" });
            });
        }

        start(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.getRoleList().done(function(res: Array<RoleItem>) {
                self.roleItems(res);
                self.selectedItem(self.roleItems()[0].roleId);
                self.initGrid();
                self.getFuncRest(self.selectedItem).done(function() {
                    dfd.resolve();
                });

            }).fail(function(res) {
                nts.uk.ui.dialog.alertError(res.message);
            });
            return dfd.promise();
        }

        getFuncRest(roleId: string): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.findFuncRest(roleId).done(function(res: Array<FuncRestItem>) {
                self.functionalRestriction(res);
                $("#grid2").igGrid("option", "dataSource", self.functionalRestriction());
                dfd.resolve();
            }).fail(function(res) {

            });
            return dfd.promise();
        }

    }

    class RoleItem {
        roleId: string;
        roleName: string;
        constructor(roleId: string, roleName: string) {
            this.roleId = roleId;
            this.roleName = roleName;
        }
    }

    class FuncRestItem {
        functionNo: number;
        displayName: string;
        availability: boolean;
        description: string;
        constructor(functionNo: number, displayName: string, availability: boolean, description: string) {
            this.functionNo = functionNo;
            this.displayName = displayName;
            this.availability = availability;
            this.description = description;
        }
    }
}
