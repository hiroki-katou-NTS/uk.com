module nts.uk.at.view.kmk005 {
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
            enableRegister: KnockoutObservable<boolean> = ko.observable(true);
            tabs: KnockoutObservableArray<TabModel> = ko.observableArray([
                new TabModel({ id: 'G', name: getText('Com_Company'), active: true }),
                new TabModel({ id: 'H', name: getText('Com_Workplace') }),
                new TabModel({ id: 'I', name: getText('Com_Person') }),
                new TabModel({ id: 'K', name: getText('KMK005_44') }),
            ]);
            showH: KnockoutObservable<boolean> = ko.observable(false);
            showI: KnockoutObservable<boolean> = ko.observable(false);
            showK: KnockoutObservable<boolean> = ko.observable(false);
            constructor() {
                let self = this,
                    tabs = self.tabs();
                //get use setting
                nts.uk.at.view.kmk005.g.service.getUseSetting()
                    .done(x => {
                        if (x) {
                            tabs[1].display(x.workplaceUseAtr);
                            tabs[2].display(x.personalUseAtr);
                            tabs[3].display(x.workingTimesheetUseAtr);
                        }
                    });

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
                    case 'G':
                        if (!!view.viewmodelG && typeof view.viewmodelG.start == 'function') {                            
                            view.viewmodelG.start();
                            self.enableRegister(true);
                        }
                        break;
                    case 'H':
                        if (!self.showH()) {
                            self.showH(true);                            
                            view.viewmodelH.loadFirst();
                            self.enableRegister(true);
                        } else if (!!view.viewmodelH && typeof view.viewmodelH.start == 'function') {
                            view.viewmodelH.start();
                        }
                        break;
                    case 'I':
                        self.enableRegister(false);
                        if (!self.showI()) {
                            self.showI(true);
                            view.viewmodelI.loadFirst();
                        } else if (!!view.viewmodelI && typeof view.viewmodelI.start == 'function') {
                            view.viewmodelI.start();                            
                        }

                        break;
                    case 'K':
                        if (!self.showK()) {
                            self.showK(true);
                            view.viewmodelK.loadFirst();
                            self.enableRegister(true);
                        } else if (!!view.viewmodelK && typeof view.viewmodelK.start == 'function') {
                            view.viewmodelK.start();
                        }
                        break;
                }
            }

            navigateView() {
                let self = this;

                // check dirty before navigate to view a                
                href("../a/index.xhtml");
            }

            saveData() {
                let self = this, view = __viewContext.viewModel,
                    activeTab = _.find(self.tabs(), t => t.active());

                switch (activeTab.id) {
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
                    case 'I':
                        if (typeof view.viewmodelI.saveData == 'function') {
                            view.viewmodelI.saveData();
                        }
                        break;
                    case 'K':
                        if (typeof view.viewmodelK.saveData == 'function') {
                            view.viewmodelK.saveData();
                        }
                        break;
                }
            }

            removeData() {
                let self = this, view = __viewContext.viewModel,
                    activeTab = _.find(self.tabs(), t => t.active());
                switch (activeTab.id) {
                    case 'G':
                        if (typeof view.viewmodelG.removeData == 'function') {
                            view.viewmodelG.removeData();
                        }
                        break;
                    case 'H':
                        if (typeof view.viewmodelH.removeData == 'function') {
                            view.viewmodelH.removeData();
                        }
                        break;
                    case 'I':
                        if (typeof view.viewmodelI.removeData == 'function') {
                            view.viewmodelI.removeData();
                        }
                        break;
                    case 'K':
                        if (typeof view.viewmodelK.removeData == 'function') {
                            view.viewmodelK.removeData();
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

    export module g.viewmodel {
        export class ScreenModel {
            model: KnockoutObservable<BonusPaySetting> = ko.observable(new BonusPaySetting({ id: '', name: '' }));
            constructor() {
                let self = this;

                self.start();
            }

            start() {
                let self = this,
                    model = self.model();

                service.getData().done(resp => {
                    if (resp) {
                        model.id(resp.bonusPaySettingCode);
                        service.getName(resp.bonusPaySettingCode).done(x => {
                            if (x) {
                                model.name(x.name);
                            } else {
                                __viewContext.viewModel.tabView.removeAble(false);
                                model.id('');
                                model.name(getText("KDL007_6"));
                            }
                        }).fail(x => alert(x));
                        model.id.valueHasMutated();
                    } else {
                        __viewContext.viewModel.tabView.removeAble(false);
                        model.id('');
                        model.name(getText("KDL007_6"));
                    }
                }).fail(x => alert(x));
            }

            openBonusPaySettingDialog() {
                let self = this,
                    model: BonusPaySetting = self.model();

                setShared("KDL007_PARAM", { isMulti: false, posibles: [], selecteds: [model.id()] });

                modal('../../../kdl/007/a/index.xhtml').onClosed(() => {
                    let data: any = getShared('KDL007_VALUES');
                    if (data && data.selecteds) {
                        let code: string = data.selecteds[0];
                        if (!(_.isEmpty(code))) {
                            model.id(code);
                            service.getName(code).done(resp => {
                                if (resp) {
                                    model.name(resp.name);
                                }
                                else {
                                    model.id('');
                                    model.name(getText("KDL007_6"));
                                }
                            }).fail(x => alert(x));
                        } else {
                            model.id('');
                            model.name(getText("KDL007_6"));
                        }
                    }
                });
            }

            saveData() {
                let self = this,
                    data: IBonusPaySetting = ko.toJS(self.model),
                    command = {
                        bonusPaySettingCode: data.id,
                        action: 0 // add/update mode
                    };
                if (data.id !== '') {
                    service.saveData(command)
                        .done(() => {
                            nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                            self.start();
                        })
                        .fail(x => alert(x));
                } else {
                    alert({ messageId: "Msg_30" });
                }
            }

            removeData() {
                let self = this,
                    data: IBonusPaySetting = ko.toJS(self.model),
                    command = {
                        bonusPaySettingCode: data.id,
                        action: 1 // remove mode
                    };

                service.saveData(command).done(() => {
                    nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                    self.start();
                }).fail(x => alert(x));
            }
        }


        interface IBonusPaySetting {
            id: string;
            name: string;
        }

        class BonusPaySetting {
            id: KnockoutObservable<string> = ko.observable('');
            name: KnockoutObservable<string> = ko.observable('');

            constructor(param: IBonusPaySetting) {
                let self = this;

                self.id(param.id);
                self.name(param.name);

                self.id.subscribe(x => {
                    let view = __viewContext.viewModel && __viewContext.viewModel.tabView,
                        acts: any = view && _.find(view.tabs(), (t: any) => t.active());
                    if (acts && acts.id == 'G') {
                        view.removeAble(!!x);
                    }
                });
            }
        }
    }
}