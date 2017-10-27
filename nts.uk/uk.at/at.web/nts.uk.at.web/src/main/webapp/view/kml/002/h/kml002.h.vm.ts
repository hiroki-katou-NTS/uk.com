module nts.uk.at.view.kml002 {
    import getText = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import confirm = nts.uk.ui.dialog.confirm;
    import href = nts.uk.request.jump;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    let __viewContext: any = window["__viewContext"] || {};

    export module viewmodel {
        export class TabScreenModel {
            title: KnockoutObservable<string> = ko.observable('');
            removeAble: KnockoutObservable<boolean> = ko.observable(true);
            tabs: KnockoutObservableArray<TabModel> = ko.observableArray([
                new TabModel({ id: 'H', name: getText('KML002_79'), active: true }),
                new TabModel({ id: 'A', name: getText('KML002_80') })
            ]);
            currentTab: KnockoutObservable<string> = ko.observable('H');
            //radio

            constructor() {
                let self = this;
                //get use setting 

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
                //set not display remove button first when change tab
                //__viewContext.viewModel.tabView.removeAble(false);
                tab.active(true);
                self.title(tab.name);
                self.tabs().map(t => (t.id != tab.id) && t.active(false));

                // call start function on view at here
                switch (tab.id) {
                    case 'H':
                        self.currentTab('H');
                        if (!!view.viewmodelH && typeof view.viewmodelH.start == 'function') {
                            view.viewmodelH.start();
                        }
                        break;
                    case 'A':
                        self.currentTab('A');
                        if (!!view.viewmodelA && typeof view.viewmodelA.start == 'function') {
                            view.viewmodelA.start();
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
            display: KnockoutObservable<boolean> = ko.observable(true);

            constructor(param: ITabModel) {
                this.id = param.id;
                this.name = param.name;
                this.active(param.active || false);
                this.display(param.display || true);
            }

            changeTab() {
                // call parent view action
                __viewContext.viewModel.tabView.changeTab(this);
            }
        }
    }
    
    export module h.viewmodel {
        export class ScreenModel {
            useCls: KnockoutObservableArray<any>;
            useClsSelected: any;
            
            constructor() {
                var self = this;
                
                self.useCls = ko.observableArray([
                    { code: '0', name: nts.uk.resource.getText("KML002_99") },
                    { code: '1', name: nts.uk.resource.getText("KML002_100") }
                ]);
                self.useClsSelected = ko.observable(0);     
            }

            /**
             * Start page.
             */
            start() {
                var self = this;
                var dfd = $.Deferred();
                                
                    dfd.resolve();
    
                return dfd.promise();
            }
            
            registrationBtn() {
                var self = this;
                
            }
        }
    }
}