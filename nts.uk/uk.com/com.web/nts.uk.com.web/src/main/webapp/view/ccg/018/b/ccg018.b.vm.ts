module ccg018.b.viewmodel {
    export class ScreenModel {
        items: KnockoutObservableArray<ItemModel>;
        items2: KnockoutObservableArray<ItemModel2>;
        currentCode: KnockoutObservable<any>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        selectedCode1: KnockoutObservable<string>;
        selectedCode2: KnockoutObservable<string>;
        employeeCode: KnockoutObservable<string>;
        employeeName: KnockoutObservable<string>;
        isVisible: KnockoutObservable<boolean>;
        categorySet: KnockoutObservable<any>;

        comboItemsAfterLogin: KnockoutObservableArray<ItemModel1>;
        comboItemsAsTopPage: KnockoutObservableArray<ItemModel1>;

        constructor() {
            var self = this;
            self.items = ko.observableArray([]);
            self.items2 = ko.observableArray([]);

            self.comboItemsAfterLogin = ko.observableArray([]);
            self.comboItemsAsTopPage = ko.observableArray([]);

            for (let i = 1; i < 10; i++) {
                self.items.push(new ItemModel('A00000' + i, '基本給' + i, "役職手当 " + i));
                self.items2.push(new ItemModel2('A00000' + i, !!(i % 2)));
            }
            self.currentCode = ko.observable(self.items()[0].code);
            self.employeeCode = ko.observable(self.items()[0].code);
            self.employeeName = ko.observable(self.items()[0].name);
            self.columns = ko.observableArray([ 
                { headerText: 'コード', key: 'code', width: 80 },
                { headerText: '名称', key: 'name', width: 80 },
                { headerText: '説明', key: 'description', width: 120 },
                { headerText: '説明1', key: 'other1', width: 120 },
            ]);

            self.selectedCode1 = ko.observable('');
            self.selectedCode2 = ko.observable('');
            self.categorySet = ko.observable();
            self.isVisible = ko.computed(function() {
                return !!self.categorySet();
            });
            self.currentCode.subscribe(function(codeChange: any) {
                self.employeeCode(codeChange);
                self.employeeName(_.find(self.items(), ['code', codeChange]).name);
            });

            self.findBySystemMenuCls();
        }

        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.categorySet(__viewContext.viewModel.viewmodelA.categorySet());
            self.findTopPagePersonSet();
            var listComponentOption = {
                isShowAlreadySet: true,
                alreadySettingList: self.items2,
                isMultiSelect: false,
                listType: 4,
                isShowWorkPlaceName: true,
                selectedCode: self.currentCode,
                isShowNoSelectRow: false,
                isDialog: false,
                selectType: 3,
                isShowSelectAllButton: false,
                employeeInputList: self.items
            };

            $('#sample-component').ntsListComponent(listComponentOption);

            dfd.resolve();
            return dfd.promise();
        }

        findTopPagePersonSet(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            var listSid = [];
            for (var i = 1; i < 10; i++) {
                listSid.push('90000000-0000-0000-0000-00000000000' + i);
            }
            ccg018.b.service.findTopPagePersonSet(listSid)
                .done(function(data) {
                    dfd.resolve();
                }).fail();
            return dfd.promise();
        }

        /**
         * Find data in table STANDARD_MENU base on CompanyId and System = 0(common) and MenuClassification = 8(top page)
         * Return 2 array comboItemsAsTopPage and comboItemsAfterLogin
         */
        findBySystemMenuCls(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.comboItemsAfterLogin.removeAll();
            self.comboItemsAsTopPage.removeAll();
            ccg018.b.service.findBySystemMenuCls()
                .done(function(data) {
                    if (data.length >= 0) {
                        self.comboItemsAsTopPage.push(new ItemModel1('', '未設定'));
                        self.comboItemsAfterLogin.push(new ItemModel1('', '未設定'));

                        _.forEach(_.filter(data, ['afterLoginDisplay', 1]), function(x) {
                            self.comboItemsAfterLogin.push(new ItemModel1(x.code, x.displayName));
                        });

                        _.forEach(data, function(x) {
                            self.comboItemsAsTopPage.push(new ItemModel1(x.code, x.displayName));
                        });
                    }
                    dfd.resolve();
                }).fail();
            return dfd.promise();
        }

        /**
         * Open dialog C
         */
        openDialogC(): void {
            var self = this;
            nts.uk.ui.windows.setShared('categorySet', self.categorySet());
            nts.uk.ui.windows.sub.modal("/view/ccg/018/c/index.xhtml", { dialogClass: "no-close" }).onClosed(() => {
                if (nts.uk.ui.windows.getShared('categorySetC') != undefined) {
                    if (self.categorySet() != nts.uk.ui.windows.getShared('categorySetC')) {
                        self.categorySet(nts.uk.ui.windows.getShared('categorySetC'));
                    }
                }
            });
        }


    }

    class ItemModel {
        code: string;
        name: string;
        workplaceName: string;
        constructor(code: string, name: string, workplaceName: string) {
            this.code = code;
            this.name = name;
            this.workplaceName = workplaceName;
        }
    }

    class ItemModel1 {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    class ItemModel2 {
        code: string;
        isAlreadySetting: boolean;

        constructor(code: string, isAlreadySetting: boolean) {
            this.code = code;
            this.isAlreadySetting = isAlreadySetting;
        }
    }
}