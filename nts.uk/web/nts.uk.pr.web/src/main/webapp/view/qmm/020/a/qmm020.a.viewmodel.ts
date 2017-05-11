module qmm020.a.viewmodel {
    export class ScreenModel {
        title: KnockoutObservable<string> = ko.observable('');
        tabs: KnockoutObservableArray<TabModel> = ko.observableArray([
            new TabModel({ id: 'B', name: '会社', active: true, display: true }),
            new TabModel({ id: 'C', name: '雇用', display: true }),
            new TabModel({ id: 'D', name: '部門', display: true }),
            new TabModel({ id: 'E', name: '分類' }),
            new TabModel({ id: 'F', name: '職位' }),
            new TabModel({ id: 'G', name: '給与分類' }),
            new TabModel({ id: 'H', name: '個人', display: true })
        ]);

        constructor() {
            let self = this;

            self.tabs().map((t) => {
                // set title for tab
                if (t.active() == true) {
                    self.title(t.name);
                }

                // set display mode for tabs at here
                t.display(true);
            });
        };

        changeTab(tab: TabModel) {
            let self = this, view: any = __viewContext.viewModel,
                oldtab: TabModel = _.find(self.tabs(), function(t) { return t.active() == true; });

            // cancel action if tab self click
            if (oldtab.id == tab.id) {
                return;
            }
            console.log(tab.id);
            tab.active(true);
            self.title(tab.name);
            self.tabs().map((t) => { if (t.id != tab.id) t.active(false); });

            // call start function on view at here
            switch (tab.id) {
                case 'B':
                    if (!!view.viewmodelB && typeof view.viewmodelB.start == 'function') {
                        view.viewmodelB.start();
                    }
                    break;
                case 'C':
                    if (!!view.viewmodelC && typeof view.viewmodelC.start == 'function') {
                        view.viewmodelC.start();
                    }
                    break;
                case 'D':
                    if (!!view.viewmodelD && typeof view.viewmodelD.start == 'function') {
                        view.viewmodelD.start();
                    }
                    break;
                case 'E':
                    if (!!view.viewmodelE && typeof view.viewmodelE.start == 'function') {
                        view.viewmodelE.start();
                    }
                    break;
                case 'F':
                    if (!!view.viewmodelF && typeof view.viewmodelF.start == 'function') {
                        view.viewmodelF.start();
                    }
                    break;
                case 'G':
                    if (!!view.viewmodelG && typeof view.viewmodelG.start == 'function') {
                        view.viewmodelG.start();
                    }
                    break;
                case 'H':
                    if (!!view.viewmodelH && typeof view.viewmodelH.start == 'function') {
                        view.viewmodelH.start();
                    }
                    break;
            }

        }

        // call saveData on child view by active tab id
        saveData() {
            let self = this, view = __viewContext.viewModel,
                activeTab = _.find(self.tabs(), function(t) { return t.active() == true; });
            switch (activeTab.id) {
                case 'B':
                    if (typeof view.viewmodelB.saveData == 'function') {
                        view.viewmodelB.saveData();
                    }
                    break;
                case 'C':
                    if (typeof view.viewmodelC.saveData == 'function') {
                        view.viewmodelC.saveData();
                    }
                    break;
                case 'D':
                    if (typeof view.viewmodelD.saveData == 'function') {
                        view.viewmodelD.saveData();
                    }
                    break;
                case 'E':
                    if (typeof view.viewmodelE.saveData == 'function') {
                        view.viewmodelE.saveData();
                    }
                    break;
                case 'F':
                    if (typeof view.viewmodelF.saveData == 'function') {
                        view.viewmodelF.saveData();
                    }
                    break;
                case 'G':
                    if (typeof view.viewmodelG.saveData == 'function') {
                        view.viewmodelG.saveData();
                    }
                    break;
                case 'H':
                    if (typeof view.viewmodelH.saveData == 'function') {
                        view.viewmodelH.saveData();
                    }
                    break;
            }
        }
    }

    interface ITabModel {
        id: string;
        name: string;
        active?: boolean;
        display?: boolean;
    }

    class TabModel {
        id: string;
        name: string;
        active: KnockoutObservable<boolean> = ko.observable(false);
        display: KnockoutObservable<boolean> = ko.observable(false);

        constructor(param: ITabModel) {
            this.id = param.id;
            this.name = param.name;
            this.active(param.active || false);
            this.display(param.display || false);
        }

        changeTab() {
            // call parent view action
            __viewContext.viewModel.viewmodelA.changeTab(this);
        }
    }
}