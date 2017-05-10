module ccg014.a.viewmodel {
    import commonModel = ccg.model;
    import windows = nts.uk.ui.windows;
    import errors = nts.uk.ui.errors;

    export class ScreenModel {
        // TitleMenu List
        listTitleMenu: KnockoutObservableArray<any>;
        selectedTitleMenuCD: KnockoutObservable<string>;
        columns: KnockoutObservableArray<any>;
        // TitleMenu Details
        selectedTitleMenu: KnockoutObservable<model.TitleMenu>;
        isCreate: KnockoutObservable<boolean>;
        add: KnockoutObservable<string>;

        constructor() {
            var self = this;
            // TitleMenu List
            self.listTitleMenu = ko.observableArray([]);
            self.selectedTitleMenuCD = ko.observable(null);
            self.selectedTitleMenuCD.subscribe((value) => {
                self.findSelectedTitleMenu(value);
            });
            self.columns = ko.observableArray([
                { headerText: 'コード', key: 'titleMenuCD', width: 70 },
                { headerText: '名称', key: 'name', width: 260 }
            ]);
            // TitleMenu Details
            self.selectedTitleMenu = ko.observable(null);
            self.add = ko.observable(null);
            self.isCreate = ko.observable(null);
            self.isCreate.subscribe((value) => {
                self.changeInitMode(value);
            });
        }

        /** Start Page */
        startPage(): JQueryPromise<any> {
            var dfd = this.reloadData();
            dfd.done(() => {
                this.selectTitleMenuByIndex(0);
            });
            return dfd;
        }

        /** Get Selected TitleMenu */
        findSelectedTitleMenu(titleMenuCD: string) {
            var self = this;
            var selectedTitleMenu = _.find(self.listTitleMenu(), ['titleMenuCD', titleMenuCD]);
            if (selectedTitleMenu !== undefined) {
                self.isCreate(false);
                self.selectedTitleMenu(new model.TitleMenu(selectedTitleMenu.titleMenuCD, selectedTitleMenu.name, selectedTitleMenu.layoutID));
            }
            else {
                self.selectedTitleMenu(new model.TitleMenu("", "", ""));
            }
        }

        /** Create Button Click */
        createButtonClick() {
            var self = this;
            self.isCreate(true);
        }

        /** Registry Button Click */
        registryButtonClick() {
            var self = this;
            var titleMenu = ko.mapping.toJS(self.selectedTitleMenu);
            var titleMenuCD = titleMenu.titleMenuCD;
            if (self.isCreate() === true) {
                service.createTitleMenu(titleMenu).done((data) => {
                    nts.uk.ui.dialog.alert("登録しました。");
                    self.reloadData().done(() => {
                        self.selectTitleMenuByIndexByCode(titleMenuCD);
                    });
                }).fail((res) => {
                    nts.uk.ui.dialog.alert("入力したコードは、既に登録されています。");
                });
            }
            else {
                service.updateTitleMenu(titleMenu).done((data) => {
                    self.reloadData();
                    nts.uk.ui.dialog.alert("登録しました。");
                });
            }
        }

        /**Delete Button Click */
        removeTitleMenu() {
            var self = this;
            if (self.selectedTitleMenuCD() !== null) {
                nts.uk.ui.dialog.confirm("Msg_18 Co xoa ko").ifYes(function() {
                    service.deleteTitleMenu(self.selectedTitleMenuCD()).done(() => {
                        var index = _.findIndex(self.listTitleMenu(), ['titleMenuCD', self.selectedTitleMenu().titleMenuCD()]);
                        index = _.min([self.listTitleMenu().length - 2, index]);
                        self.reloadData().done(() => {
                            self.selectTitleMenuByIndex(index);
                        });
                    }).fail((res) => {
                        nts.uk.ui.dialog.alert("Fail!")
                    });
                }).then(function() { nts.uk.ui.dialog.alert("Msg_16 Da xoa") });
            }
        }

        /** Open  CCG 014B Dialog */
        openBDialog() {
            var self = this;
            windows.setShared("copyData", self.selectedTitleMenu());
            windows.sub.modal("/view/ccg/014/b/index.xhtml", { title: '他のタイトルメニューのコピー', dialogClass: "no-close" });
        }

        /** Open  CCG 031_1 Dialog */
        open031_1Dialog() {
            var self = this;
            var layoutInfo: commonModel.TransferLayoutInfo = {
                parentCode: self.selectedTitleMenuCD(),
                layoutID: self.selectedTitleMenu().layoutID(),
                pgType: 1
            };
            windows.setShared("layout", layoutInfo, false);
            windows.sub.modal("/view/ccg/014/b/index.xhtml", { title: '他のタイトルメニューのコピー', dialogClass: "no-close" });
        }

        /** Open 030A Dialog */
        open030A_Dialog() {
            windows.sub.modal("/view/ccg/030/a/index.xhtml", { title: 'フローメニューの設定', dialogClass: "no-close" });
        }

        /** Init Mode */
        private changeInitMode(isCreate: boolean) {
            var self = this;
            if (isCreate === true) {
                self.selectedTitleMenuCD(null);
                _.defer(() => { $("#titleMenuCD").focus(); });
            }
            else {
                _.defer(() => { errors.clearAll(); });
            }
        }

        /** Reload data from server */
        private reloadData(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            /** Get list TitleMenu*/
            service.getAllTitleMenu().done(function(listTitleMenu: Array<model.TitleMenu>) {
                self.listTitleMenu(listTitleMenu);
                if (listTitleMenu.length > 0) {
                    self.isCreate(false);
                }
                else {
                    self.findSelectedTitleMenu(null);
                    self.isCreate(true);
                }
                dfd.resolve();
            }).fail(function(error) {
                dfd.fail();
                alert(error.message);
            });
            return dfd.promise();
        }

        /** Select TitleMenu by Code: Create & Update case*/
        private selectTitleMenuByIndexByCode(code: string) {
            this.selectedTitleMenuCD(code);
        }

        /** Select TitleMenu by Index: Start & Delete case */
        private selectTitleMenuByIndex(index: number) {
            var self = this;
            var selectTitleMenuByIndex = _.nth(self.listTitleMenu(), index);
            if (selectTitleMenuByIndex !== undefined)
                self.selectedTitleMenuCD(selectTitleMenuByIndex.titleMenuCD);
            else
                self.selectedTitleMenuCD(null);
        }

    }

    export module model {
        export class TitleMenu {
            titleMenuCD: KnockoutObservable<string>;
            name: KnockoutObservable<string>;
            layoutID: KnockoutObservable<string>;
            constructor(titleMenuCD: string, name: string, layoutID: string) {
                this.titleMenuCD = ko.observable(titleMenuCD);
                this.name = ko.observable(name);
                this.layoutID = ko.observable(layoutID);
            }
        }
    }
}