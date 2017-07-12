module nts.uk.at.view.kmk005 {
    import getText = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import confirm = nts.uk.ui.dialog.confirm;
    import info = nts.uk.ui.dialog.info;
    import href = nts.uk.request.jump;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;


    let __viewContext: any = window["__viewContext"] || {};

    export module viewmodel {
        export class TabScreenModel {
            title: KnockoutObservable<string> = ko.observable('');
            tabs: KnockoutObservableArray<TabModel> = ko.observableArray([
                new TabModel({ id: 'G', name: getText('Com_Company'), active: true }),
                new TabModel({ id: 'H', name: getText('Com_Workplace') }),
                new TabModel({ id: 'I', name: getText('Com_Person') }),
                new TabModel({ id: 'K', name: getText('KMK005_44') }),
            ]);

            isEnable: KnockoutObservable<boolean> = ko.observable(false);

            constructor() {
                let self = this;
                //get use setting 
                nts.uk.at.view.kmk005.g.service.getUseSetting()
                    .done((useSetting) => {
                        self.tabs()[1].display(useSetting.workplaceUseAtr);
                        self.tabs()[2].display(useSetting.personalUseAtr);
                        self.tabs()[3].display(useSetting.workingTimesheetUseAtr);
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
                                __viewContext.viewModel.tabView.isEnable(true);
                            } else {
                                model.id('000');
                                model.name(getText("KDL007_6"));
                            }
                        }).fail(x => alert(x));
                    } else {
                        __viewContext.viewModel.tabView.isEnable(false);
                        model.id('000');
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
                        if (code) {
                            model.id(code);
                            service.getName(code).done(resp => {
                                if (resp) {
                                    model.name(resp.name);
                                }
                                else {
                                    model.id('000');
                                    model.name(getText("KDL007_6"));
                                }
                            }).fail(x => alert(x));
                        } else {
                            model.id('000');
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
                service.saveData(command)
                    .done(() => {
                        info(nts.uk.resource.getMessage("Msg_15"));
                        self.start();
                    })
                    .fail(x => alert(x));
            }

            removeData() {
                let self = this,
                    data: IBonusPaySetting = ko.toJS(self.model),
                    command = {
                        bonusPaySettingCode: data.id,
                        action: 1 // remove mode
                    };
                confirm({messageId:'Msg_18'}).ifYes(()=>{
                    service.saveData(command).done(x =>{
                  info(nts.uk.resource.getMessage("Msg_16"));     
                  self.start()  } ).fail(x => alert(x));
                });
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
            }
        }
    }
}