module nts.uk.at.view.kaf022.wkp.viewmodel {
    import flat = nts.uk.util.flatArray;
    import getText = nts.uk.resource.getText;
    import clearError = nts.uk.ui.errors.clearAll;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    let __viewContext: any = window["__viewContext"] || {};

    export class ScreenModel {
        title: KnockoutObservable<string> = ko.observable('');
        removeAble: KnockoutObservable<boolean> = ko.observable(true);
        tabs: KnockoutObservableArray<TabModel> = ko.observableArray([
            new TabModel({ id: 'com', name: getText('Com_Company'), active: true }),
            new TabModel({ id: 'wkp', name: getText('Com_Workplace') })

        ]);
        currentTab: KnockoutObservable<string> = ko.observable('com');

        constructor() {
            let self = this;
            self.changeTab(self.tabs()[0]);
        }

        changeTab(tab: TabModel) {
            let self = this,
                // view: any = __viewContext.viewModel,
                oldtab: TabModel = _.find(self.tabs(), t => t.active());

            // cancel action if tab self click
            if (oldtab.id == tab.id) {
                return;
            }
            //set not display remove button first when change tab
            //__viewContext.viewModel.tabView.removeAble(false);
            tab.active(true);
            self.title(tab.name);

            // self.tabs().map(t => (t.id != tab.id) && t.active(false));

            // call start function on view at here
            switch (tab.id) {
                case 'com':
                    self.currentTab('com');
                    nts.uk.ui.errors.clearAll();
                    // if (!!view.viewmodelA && typeof view.viewmodelA.start == 'function') {
                    //     view.viewmodelA.start();
                    // }
                    break;
                case 'wkp':
                    self.currentTab('wkp');
                    nts.uk.ui.errors.clearAll();
                    // if (!!view.viewmodelL && typeof view.viewmodelL.start == 'function') {
                    //     view.viewmodelL.start();
                    // }
                    break;
                default:
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
        display: KnockoutObservable<boolean> = ko.observable(true);

        constructor(param: ITabModel) {
            this.id = param.id;
            this.name = param.name;
            this.active(param.active || false);
            this.display(param.display || true);
        }

        changeTab() {
            // call parent view action
            __viewContext.viewModel.changeTab(this);
        }
    }
}