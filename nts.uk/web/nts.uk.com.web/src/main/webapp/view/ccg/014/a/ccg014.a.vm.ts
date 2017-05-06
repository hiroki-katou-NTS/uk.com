module ccg014.a.viewmodel {
    export class ScreenModel {
        // TitleMenu List
        listTitleMenu: KnockoutObservableArray<any>;
        selectedTitleMenuCD: KnockoutObservable<string>;
        columns: KnockoutObservableArray<any>;
        // TitleMenu Details
        selectedTitleMenu: KnockoutObservable<model.TitleMenu>;
        isCreate: KnockoutObservable<boolean>;

        constructor() {
            var self = this;
            // TitleMenu List
            self.listTitleMenu = ko.observableArray([]);
            self.selectedTitleMenuCD = ko.observable(null);
            self.selectedTitleMenuCD.subscribe((value) => {
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
            return this.reloadData();
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
            this.isCreate(true);
        }

        /** Registry Button Click */
        registryButtonClick() {
            var self = this;
            var titleMenu = ko.mapping.toJS(self.selectedTitleMenu);
            if (self.isCreate() === true) {
                service.createTitleMenu(titleMenu).done((data) => {
                    if (data === true) {
                        nts.uk.ui.dialog.alert("Msg_15");
                        self.reloadData();
                    }
                    else {
                        nts.uk.ui.dialog.alert("Msg_3");
                    }
                }).fail((res) => {
                    nts.uk.ui.dialog.alert("Msg_3");
                });
            }
            else {
                service.updateTitleMenu(titleMenu).done((data) => {
                    nts.uk.ui.dialog.alert("Msg_15");
                    self.reloadData(false);
                });
            }
        }
        
        /**Delete Button Click */
        removeTitleMenu() {
            var self = this;
            if (self.selectedTitleMenuCD() !== null) {
                nts.uk.ui.dialog.confirm("Do you want to delete").ifYes(function(){
                    service.deleteTitleMenu(self.selectedTitleMenuCD()).done(() => {
                        self.reloadData(false);
                    }).fail((res) => {
                        nts.uk.ui.dialog.alert("Fail!")
                    });
                })
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
        
        /** Init Mode */
        private changeInitMode(isCreate: boolean) {
            var self = this;
            if (isCreate === true) {
                self.selectedTitleMenuCD(null);
                _.defer(()=>{$("#titleMenuCD").focus();});
            }
        }
        
        /** Open 030A Dialog */
        private reloadData(isStart?: boolean): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            isStart = (isStart !== undefined) ? isStart : true;
            var index = 0;
            if (!isStart) {
                index = _.findIndex(self.listTitleMenu(), ['titleMenuCD', self.selectedTitleMenu().titleMenuCD()]);
                index = _.min([self.listTitleMenu().length - 2, index]);
            }
            /** Get list TitleMenu*/
            service.getAllTitleMenu().done(function(listTitleMenu: Array<model.TitleMenu>) {
                self.listTitleMenu(listTitleMenu);
                if (listTitleMenu.length > 0) {
                    self.selectTitleMenu(index);
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
        
        /** Get First TitleMenu */
        private selectTitleMenu(index: number) {
            var self = this;
            var selectTitleMenu = _.nth(self.listTitleMenu(), index);
            if (selectTitleMenu !== undefined)
                self.selectedTitleMenuCD(selectTitleMenu.titleMenuCD);
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