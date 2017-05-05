module ccg014.a.viewmodel {
    export class ScreenModel {
        // TitleMenu List
        listTitleMenu: KnockoutObservableArray<any>;
        selectedTitleMenuCode: KnockoutObservable<string>;
        columns: KnockoutObservableArray<any>;
        // TitleMenu Details
        selectedTitleMenu: KnockoutObservable<model.TitleMenu>;
        isCreate: KnockoutObservable<boolean>;
        // OpenDdialogB
        titleMenuCD: KnockoutObservableArray<string>;
        name: KnockoutObservableArray<string>
        constructor() {
            var self = this;
            // TitleMenu List
            self.listTitleMenu = ko.observableArray([]);
            self.selectedTitleMenuCode = ko.observable(null);
            self.selectedTitleMenuCode.subscribe((value) => {
                self.findSelectedTitleMenu(value);
            });
            self.columns = ko.observableArray([
               
                { headerText: 'コード', key: 'titleMenuCD', width: 100 },
                { headerText: '名称', key: 'name', width: 150 }
            ]);
            // TitleMenu Details
            self.selectedTitleMenu = ko.observable(null);
            self.isCreate = ko.observable(null);
            self.isCreate.subscribe((value) => {
                self.changeInitMode(value);
            });
        }

        /** Start Page */
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            /** Get list TitleMenu*/
            service.getAllTitleMenu().done(function(listTitleMenu: Array<model.TitleMenu>) {
                console.log(listTitleMenu);
                self.listTitleMenu(listTitleMenu);
                if (listTitleMenu.length > 0) {
                    self.selectFirstTitleMenu();
                    self.isCreate(false);
                }
                else {
                    self.isCreate(true);
                }
                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
            });
            dfd.resolve();
            return dfd.promise();
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

        /** Get First TitleMenu */
        selectFirstTitleMenu() {
            var self = this;
            var fistTitleMenu = _.head(self.listTitleMenu());
            if (fistTitleMenu !== undefined)
                self.selectedTitleMenuCode(fistTitleMenu.titleMenuCD);
            else
                self.selectedTitleMenuCode(null);
        }

        /** Create Button Click */
        createButtonClick() {
            this.isCreate(true);
        }

        /** Init Mode */
        changeInitMode(isCreate: boolean) {
            var self = this;
            if (isCreate === true) {
                self.selectedTitleMenuCode(null);
                $("#titleMenuCD").focus();
            }
        }

        /** Open Dialog */
        openBDialog() {
            nts.uk.ui.windows.sub.modal("/view/ccg/014/b/index.xhtml", { title: '他のタイトルメニューのコピー', dialogClass: "no-close" });
        }

        /** Open 030A Dialog */
        open030A_Dialog() {
            nts.uk.ui.windows.sub.modal("/view/ccg/030/a/index.xhtml", { title: 'フローメニューの設定', dialogClass: "no-close" });
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