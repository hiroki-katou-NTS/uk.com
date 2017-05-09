module qmm020.a.viewmodel {
    export class ScreenModel {
        title: KnockoutObservable<string> = ko.observable('');
        tabs: KnockoutObservableArray<TabModel> = ko.observableArray([
            new TabModel({ id: 'B', name: '会社', display: true }),
            new TabModel({ id: 'C', name: '雇用', active: true, display: true }),
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
            let self = this;
            tab.active(true);
            self.title(tab.name);
            self.tabs().map((t) => { if (t.name != tab.name) t.active(false); });
        }

        // call saveData on child view by active tab id
        saveData() {
            let self = this, view = __viewContext.viewModel,
                activeTab = _.find(self.tabs(), function(t) { return t.active() == true; });
            switch (activeTab.id) {
                case 'B':
                    view.viewmodelB.saveData();
                    break;
                case 'C':
                    view.viewmodelC.saveData();
                    break;
                case 'D':
                    view.viewmodelD.saveData();
                    break;
                case 'E':
                    view.viewmodelE.saveData();
                    break;
                case 'F':
                    view.viewmodelF.saveData();
                    break;
                case 'G':
                    view.viewmodelG.saveData();
                    break;
                case 'H':
                    view.viewmodelH.saveData();
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