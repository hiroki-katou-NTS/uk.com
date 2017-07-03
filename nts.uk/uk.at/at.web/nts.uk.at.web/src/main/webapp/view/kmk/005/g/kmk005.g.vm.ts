module nts.uk.at.view.kmk005 {
    import getText = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import confirm = nts.uk.ui.dialog.confirm;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

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
                        self.changeTab(t);
                    }
                });
            }

            changeTab(tab: TabModel) {
                let self = this,
                    view: any = __viewContext.viewModel,
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
            model: KnockoutObservable<TimeZoneModel> = ko.observable(new TimeZoneModel({ id: '', name: '' }));
            constructor() {
                let self = this;

                self.start();
            }

            start() {
                service.get().done(resp => {
                    debugger;
                }).fail(x => alert(x));
            }

            openTimeZoneDialog() {
                let self = this,
                    model: TimeZoneModel = self.model();

                setShared("KDL007_PARAM", { isMulti: false, posibles: [], selecteds: ['005'] });

                modal('../../../kdl/007/a/index.xhtml').onClosed(() => {
                    let data: any = getShared('KDL007_VALUES');
                    if (data && data.selecteds) {
                        let code: string = data.selecteds[0];
                        if (code) {
                            model.id(code);
                            service.get().done(resp => {
                                debugger;
                            });
                        } else {
                            model.id('000');
                            model.name(getText("KDL007_6"));
                        }
                    }
                });
            }
        }

        interface ITimeZoneModel {
            id: string;
            name: string;
        }

        class TimeZoneModel {
            id: KnockoutObservable<string> = ko.observable('');
            name: KnockoutObservable<string> = ko.observable('');

            constructor(param: ITimeZoneModel) {
                let self = this;

                self.id(param.id);
                self.name(param.name);
            }
        }
    }
}