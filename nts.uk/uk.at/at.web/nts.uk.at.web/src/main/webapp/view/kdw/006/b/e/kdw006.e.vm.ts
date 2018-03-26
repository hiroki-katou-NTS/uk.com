module nts.uk.at.view.kdw006.e.viewmodel {
    import getText = nts.uk.resource.getText;
    export class ScreenModelE {
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
            self.availability = ko.observable(true);

            self.columns1 = ko.observableArray([
                { headerText: 'ID', key: 'roleId', width: 100, hidden: true },
                { headerText: getText('KDW006_44'), key: 'roleCode', width: 100 },
                { headerText: getText('KDW006_45'), key: 'roleName', width: 150 }
            ]);


        }

        initGrid() {
            let self = this;
            $("#grid2").ntsGrid({
                //width: 780,
                height: 450,
                // rows: 15,
                dataSource: self.functionalRestriction(),
                primaryKey: 'functionNo',
                hidePrimaryKey: true,
                virtualization: true,
                virtualizationMode: 'continuous',
                columns: [
                    { headerText: getText('KDW006_44'), key: 'functionNo', dataType: 'number', width: '10px' },
                    { headerText: getText('KDW006_49'), key: 'displayName', dataType: 'string', width: '320px' },
                    { headerText: getText('KDW006_50'), key: 'availability', dataType: 'boolean', width: '80px', showHeaderCheckbox: false, ntsControl: 'availability' },
                    { headerText: getText('KDW006_51'), key: 'description', dataType: 'string', width: '370px' }
                ],
                features: [{
                    name: 'Selection',
                    mode: 'row',
                    multipleSelection: true
                }],
                // ntsFeatures: [{ name: 'CopyPaste' }],
                ntsControls: [{ name: 'availability', options: { value: 1, text: '' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true }]
            });
        }

        saveData(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.register(self.selectedItem(), self.functionalRestriction()).done(function(res: Array<RoleItem>) {
                nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                dfd.resolve();
            }).fail(function(res) {
                nts.uk.ui.dialog.alertError(res.message);
            });
            return dfd.promise();
        }

        start(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.getRoleList().done(function(res: Array<RoleItem>) {
                self.roleItems(_.sortBy(res, ['roleCode']));
                self.selectedItem(self.roleItems()[0].roleId);
                self.selectedItem.subscribe(function(newValue) {
                    self.getFuncRest(newValue);
                });
                service.findFuncRest(self.selectedItem()).done(function(res: Array<FuncRestItem>) {
                    //#90479 get dataSource before initGrid (keyindex will have data)
                    self.functionalRestriction(res);
                    self.initGrid();
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
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
        roleCode: string;
        roleName: string;
        constructor(roleId: string, roleCode: string, roleName: string) {
            this.roleId = roleId;
            this.roleCode = roleCode;
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
