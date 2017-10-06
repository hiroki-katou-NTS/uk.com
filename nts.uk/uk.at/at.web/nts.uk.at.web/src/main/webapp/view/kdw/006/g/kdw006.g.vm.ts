module nts.uk.at.view.kdw006.g.viewmodel {
    export class ScreenModel {
        // declare
        fullWorkTypeList: KnockoutObservableArray<any>;
        groups1: KnockoutObservableArray<any>;
        groups2: KnockoutObservableArray<any>;

        // template
        listComponentOption: any;
        selectedCode: KnockoutObservable<string>;
        isShowAlreadySet: KnockoutObservable<boolean>;
        alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
        isShowNoSelectRow: KnockoutObservable<boolean>;
        isMultiSelect: KnockoutObservable<boolean>;
        employmentList: KnockoutObservableArray<UnitModel>;

        constructor() {
            var self = this;
            self.fullWorkTypeList = ko.observableArray([]);
            self.groups1 = ko.observableArray([]);
            self.groups2 = ko.observableArray([]);
            // template
            self.selectedCode = ko.observable('01');
            self.alreadySettingList = ko.observableArray([
                { code: '1', isAlreadySetting: true },
                { code: '2', isAlreadySetting: true }
            ]);
            self.listComponentOption = {
                isShowAlreadySet: false,
                isMultiSelect: false,
                listType: ListType.EMPLOYMENT,
                selectType: SelectType.SELECT_BY_SELECTED_CODE,
                selectedCode: self.selectedCode,
                isShowNoSelectRow: false,
                isDialog: false,
                alreadySettingList: self.alreadySettingList,
                maxRows: 12
            };
            self.employmentList = ko.observableArray<UnitModel>([]);

            self.selectedCode.subscribe(function(newValue) {
                self.getWorkType();
            });
        }

        start(): JQueryPromise<any> {
            var self = this;
            $('#empt-list-setting').ntsListComponent(self.listComponentOption);
            self.getFullWorkTypeList().done(function() {
                self.getWorkType();
            });

        }

        getFullWorkTypeList() {
            var self = this;
            var dfd = $.Deferred();
            service.findWorkType().done(function(res) {
                _.forEach(res, function(item) {
                    self.fullWorkTypeList.push({
                        workTypeCode: item.workTypeCode,
                        name: item.name,
                        memo: item.memo
                    });
                });
                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
                dfd.reject(error);
            });
            return dfd.promise();
        }

        getWorkType(): JQueryPromise<any> {
            let self = this;
            var dfd = $.Deferred();
            var fullWorkTypeCodes = _.map(self.fullWorkTypeList(), function(item: any) { return item.workTypeCode; });
            self.groups1.removeAll();
            self.groups1.removeAll();
            service.getWorkTypes(self.selectedCode()).done(function(res) {
                _.forEach(res, function(item) {
                    let names = _(item.workTypeList).map(x => (_.find(ko.toJS(self.fullWorkTypeList), z => z.workTypeCode == x) || {}).name).value();
                    let group = new WorkTypeGroup(item.no, item.name, item.workTypeList, names.join("、　"), fullWorkTypeCodes);
                    if (group.no < 5) {
                        self.groups1.push(group);
                    } else {
                        self.groups2.push(group);
                    }
                });
                dfd.resolve();
            }).fail(function(res) {
                nts.uk.ui.dialog.alertError(res.message);
            });
            return dfd.promise();
        }

        saveData(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            service.register(self.selectedCode(), self.groups1(), self.groups2()).done(function(res) {
                dfd.resolve();
            }).fail(function(res) {
                nts.uk.ui.dialog.alertError(res.message);
            });
            return dfd.promise();
        }

    }

    export class WorkTypeGroup {
        no: number;
        name: string;
        workTypeCodes: string[];
        workTypeName: string;
        fullWorkTypeCodes: string[];

        constructor(no: number, name: string, workTypeCodes: string[], workTypeName: string, fullWorkTypeCodes: string[]) {
            this.no = no;
            this.name = name;
            this.workTypeCodes = workTypeCodes;
            this.workTypeName = ko.observable(workTypeName);
            this.fullWorkTypeCodes = fullWorkTypeCodes;
        }

        openKDL002Dialog() {
            let self = this;
            nts.uk.ui.block.invisible();

            nts.uk.ui.windows.setShared('KDL002_Multiple', true);
            nts.uk.ui.windows.setShared('KDL002_AllItemObj', self.fullWorkTypeCodes);
            nts.uk.ui.windows.setShared('KDL002_SelectedItemId', self.workTypeCodes);

            nts.uk.ui.windows.sub.modal('/view/kdl/002/a/index.xhtml', { title: '' }).onClosed(function(): any {
                nts.uk.ui.block.clear();
                var data = nts.uk.ui.windows.getShared('KDL002_SelectedNewItem');
                var name = [];
                _.forEach(data, function(item: any) {
                    name.push(item.name);
                });
                self.workTypeName(name.join("、　"));
                self.workTypeCodes = _.map(data, function(item: any) { return item.code; });
            });
        }

        clear() {
            let self = this;
            self.workTypeCodes = [];
            self.workTypeName("");
        }
    }

    export interface IWorkTypeModal {
        workTypeCode: string;
        name: string;
        memo: string;
    }

    export class ListType {
        static EMPLOYMENT = 1;
        static Classification = 2;
        static JOB_TITLE = 3;
        static EMPLOYEE = 4;
    }

    export interface UnitModel {
        code: string;
        name?: string;
        workplaceName?: string;
        isAlreadySetting?: boolean;
    }

    export class SelectType {
        static SELECT_BY_SELECTED_CODE = 1;
        static SELECT_ALL = 2;
        static SELECT_FIRST_ITEM = 3;
        static NO_SELECT = 4;
    }

    export interface UnitAlreadySettingModel {
        code: string;
        isAlreadySetting: boolean;
    }

}
