module nts.uk.at.view.kaf022.wkp.viewmodel {
    import getText = nts.uk.resource.getText;
    import ScreenModelZ = z.viewmodel.ScreenModelZ;
    import ScreenModelM = m.viewmodel.ScreenModelM;

    let __viewContext: any = window["__viewContext"] || {};

    export class ScreenModel {
        title: KnockoutObservable<string> = ko.observable('');
        removeAble: KnockoutObservable<boolean> = ko.observable(true);
        tabs: KnockoutObservableArray<TabModel> = ko.observableArray([
            new TabModel({ id: 'com', name: getText('Com_Company') }),
            new TabModel({ id: 'wkp', name: getText('Com_Workplace') })

        ]);
        currentTab: KnockoutObservable<string> = ko.observable('com');

        radioOptions: KnockoutObservableArray<any> = ko.observableArray([
            {code: 1, name: getText("KAF022_100")},
            {code: 0, name: getText("KAF022_101")}
        ]);

        viewmodelZ: ScreenModelZ;
        viewmodelM: ScreenModelM;

        constructor() {
            let self = this;
            self.viewmodelZ = new ScreenModelZ();
            self.viewmodelM = new ScreenModelM();
            self.changeTab(self.tabs()[0]);
        }

        changeTab(tab: TabModel) {
            let self = this,
                oldtab: TabModel = _.find(self.tabs(), t => t.active());

            if (oldtab && oldtab.id == tab.id) {
                return;
            }

            self.tabs().forEach(t => {
                if (t.id == tab.id) t.active(true);
                else t.active(false);
            });
            // tab.active(true);
            self.title(tab.name);

            // self.tabs().map(t => (t.id != tab.id) && t.active(false));

            // call start function on view at here
            switch (tab.id) {
                case 'com':
                    self.currentTab('com');
                    nts.uk.ui.errors.clearAll();
                    self.viewmodelZ.start();
                    // if (!!view.viewmodelA && typeof view.viewmodelA.start == 'function') {
                    //     view.viewmodelA.start();
                    // }
                    break;
                case 'wkp':
                    self.currentTab('wkp');
                    nts.uk.ui.errors.clearAll();
                    self.viewmodelM.start();
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