module nts.uk.at.view.kmk005 {
    import getText = nts.uk.resource.getText;
    let __viewContext: any = window["__viewContext"] || {};

    export module viewmodel {
        export class TabScreenModel {
            title: KnockoutObservable<string> = ko.observable('');
            tabs: KnockoutObservableArray<TabModel> = ko.observableArray([
                new TabModel({ id: 'G', name: getText('Com_Company'), active: true }),
                new TabModel({ id: 'H', name: getText('Com_Department') }),
                new TabModel({ id: 'I', name: getText('Com_Person') }),
                new TabModel({ id: 'K', name: getText('KMK005_44') }),
            ]);
            constructor() {
                let self = this;

                self.tabs().map((t) => {
                    // set title for tab
                    if (t.active() == true) {
                        self.title(t.name);
                    }
                });
            }


            changeTab(tab: TabModel) {
                let self = this, view: any = __viewContext.viewModel,
                    oldtab: TabModel = _.find(self.tabs(), t => t.active());

                // cancel action if tab self click
                if (oldtab.id == tab.id) {
                    return;
                }

                tab.active(true);
                self.title(tab.name);
                self.tabs().map(t => (t.id != tab.id) && t.active(false));

                // call start function on view at here
                switch (tab.id) {
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
                    case 'I':
                        if (!!view.viewmodelI && typeof view.viewmodelI.start == 'function') {
                            view.viewmodelI.start();
                        }
                        break;
                    case 'K':
                        if (!!view.viewmodelK && typeof view.viewmodelK.start == 'function') {
                            view.viewmodelK.start();
                        }
                        break;
                }
            }
        }


        interface ITabModel {
            id: string;
            name: string;
            active?: boolean;
        }

        class TabModel {
            id: string;
            name: string;
            active: KnockoutObservable<boolean> = ko.observable(false);

            constructor(param: ITabModel) {
                this.id = param.id;
                this.name = param.name;
                this.active(param.active || false);
            }

            changeTab() {
                // call parent view action
                __viewContext.viewModel.tabView.changeTab(this);
            }
        }
    }

    export module g.viewmodel {
        export class ScreenModel {
            constructor() {
                let self = this;
            }

            start() {
                let self = this,
                    dfd = $.Deferred();

                return dfd.promise();
            }
        }
    }
}