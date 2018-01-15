module nts.uk.at.view.kdw006 {
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
            tabs: KnockoutObservableArray<TabModel>;
            title: KnockoutObservable<string>;
           // enableCopyBtn : KnockoutObservable<boolean>; 

            constructor() {
                let self = this;
                self.tabs = ko.observableArray([
                    new TabModel({ id: 'B', name: getText('KDW006_20'), active: true }), 
                    new TabModel({ id: 'C', name: getText('KDW006_21') }),
                    new TabModel({ id: 'D', name: getText('KDW006_22') }),
                    new TabModel({ id: 'E', name: getText('KDW006_23') })
                    //new TabModel({ id: 'G', name: getText('KDW006_58') })
                ]);
                self.title = ko.observable('');
               // self.enableCopyBtn = ko.observable(false);
                self.tabs().map((t) => {
                    // set title for tab
                    if (t.active() == true) {
                        self.title(t.name);
                        self.changeTab(t);
                    }
                });
            }

            changeTab(tab: TabModel) {
                let self = this;
                let view = __viewContext.viewModel;
                
                //clear All error when switch screen.
                nts.uk.ui.errors.clearAll();
                
                let oldTab = _.find(self.tabs(), t => t.active());

                // cancel action if tab self click
                if (oldTab.id == tab.id) {
                    return;
                }
                //set not display remove button first when change tab
                tab.active(true);
                self.title(tab.name);
                self.tabs().map(t => (t.id != tab.id) && t.active(false));

                // call start function on view at here
                switch (tab.id) {
                    case 'B':
                        if (!!view.viewmodelB && typeof view.viewmodelB.start == 'function') {
                            view.viewmodelB.start();
                        }
                       // self.enableCopyBtn(false);
                        break;
                    case 'C':
                        if (!!view.viewmodelC && typeof view.viewmodelC.start == 'function') {
                            view.viewmodelC.start();
                        }
                      //  self.enableCopyBtn(false);
                        break;
                    case 'D':
                        if (!!view.viewmodelD && typeof view.viewmodelD.start == 'function') {
                            view.viewmodelD.start();
                        }
                       // self.enableCopyBtn(false);
                        break;
                    case 'E':
                        if (!!view.viewmodelE && typeof view.viewmodelE.start == 'function') {
                            view.viewmodelE.start();
                        }
                       // self.enableCopyBtn(true);
                        break;
                    case 'G':
                        if (!!view.viewmodelG && typeof view.viewmodelG.start == 'function') {
                            view.viewmodelG.start();
                        }
                       // self.enableCopyBtn(true);
                        break;
                }
            }

            navigateView() {
                //
                let self = this;
                // check dirty before navigate to view a                
                href("../a/index.xhtml");
            }
            
//            copyData() {
//                let self = this;
//            }

            saveData() {
                let self = this, view = __viewContext.viewModel,
                    activeTab = _.find(self.tabs(), t => t.active());

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
                    case 'G':
                        if (typeof view.viewmodelG.saveData == 'function') {
                            view.viewmodelG.saveData();
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

}