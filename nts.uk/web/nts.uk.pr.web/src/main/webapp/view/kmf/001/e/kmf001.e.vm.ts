module nts.uk.pr.view.kmf001.e {
    export module viewmodel {
        export class ScreenModel {

            textEditorOption: KnockoutObservable<any>;
            empList: KnockoutObservableArray<ItemModel>;
            columnsSetting: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            selectedCode: KnockoutObservable<string>;
            managementOption: KnockoutObservableArray<ManagementModel>;
            selectedManagement: KnockoutObservable<number>;
            hasEmp: KnockoutObservable<boolean>;
            numberOfYear: KnockoutObservable<number>;
            maxOfDays: KnockoutObservable<number>;

            // Dirty checker
            dirtyChecker: nts.uk.ui.DirtyChecker;

            constructor() {
                var self = this;
                self.textEditorOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    width: "50px",
                    textmode: "text",
                    placeholder: "Type st",
                    textalign: "left"
                }));
                self.empList = ko.observableArray<ItemModel>([]);
                for (let i = 1; i < 10; i++) {
                    self.empList.push(new ItemModel('00' + i, '基本給', i % 3 === 0));
                }
                self.columnsSetting = ko.observableArray([
                    { headerText: 'コード', key: 'code', width: 100 },
                    { headerText: '名称', key: 'name', width: 150 },
                    { headerText: '設定済', key: 'alreadySet', width: 150 }
                ]);
                self.selectedCode = ko.observable(null);
                self.managementOption = ko.observableArray<ManagementModel>([
                    new ManagementModel(0, '管理す'),
                    new ManagementModel(1, '管理しな')
                ]);
                self.selectedManagement = ko.observable(1);
                self.hasEmp = ko.computed(function() {
                    return self.empList().length > 0;
                });
                
                self.numberOfYear = ko.observable(1);
                self.maxOfDays = ko.observable(40);
            }


            public startPage(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                dfd.resolve();
                return dfd.promise();
            }

            public backToHistorySelection() {

            }

            public register() {

            }
        }

        class ItemModel {

            code: string;
            name: string;
            alreadySet: boolean;
            constructor(code: string, name: string, alreadySet: boolean) {
                this.code = code;
                this.name = name;
                this.alreadySet = alreadySet;
            }
        }
        
        class ManagementModel {
            code: number;
            name: string;
            constructor(code: number, name: string) {
                this.code = code;
                this.name = name;
            }
        }
    }
}