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
            title: KnockoutObservable<string> = ko.observable('');
            tabs: KnockoutObservableArray<TabModel> = ko.observableArray([
                new TabModel({ id: 'B', name: getText('KDW006_20')}),
                new TabModel({ id: 'C', name: getText('KDW006_21'), active: true }),
                new TabModel({ id: 'D', name: getText('KDW006_22') }),
                new TabModel({ id: 'E', name: getText('KDW006_23') }),
                new TabModel({ id: 'G', name: getText('KDW006_58') }),
            ]);

            constructor() {
                let self = this,
                    tabs = self.tabs();
                
                //Dung khi ghep vao server
                //get use setting
//                nts.uk.at.view.kmk005.g.service.getUseSetting()
//                    .done(x => {
//                        if (x) {
//                            tabs[1].display(x.workplaceUseAtr);
//                            tabs[2].display(x.personalUseAtr);
//                            tabs[3].display(x.workingTimesheetUseAtr);
//                        }
//                    });

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
                tab.active(true);
                self.title(tab.name);
                self.tabs().map(t => (t.id != tab.id) && t.active(false));

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
                    case 'G':
                        if (!!view.viewmodelG && typeof view.viewmodelG.start == 'function') {
                            view.viewmodelG.start();
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
                        if (typeof view.viewmodelE.saveData == 'function') {
                            view.viewmodelE.saveData();
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

    export module b.viewmodel {
        export class ScreenModel {
            //Daily perform id from other screen
            dailyPerformId = '';

            //Define textbox
            textAreaValue: KnockoutObservable<string> = ko.observable("123");
            multilineeditor: any = {
                value: this.textAreaValue,
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                    resizeable: true,
                    placeholder: "",
                    width: "380px",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };

            //Define switch button
            selectedRuleCode: any = ko.observable(1);

            constructor() {
                var self = this;
                
                self.startPage();
                
                self.textAreaValue('afafad');
            }

            saveData() {
                alert('screen b');
                var self = this;
                var perform = {
                    settingUnit: self.selectedRuleCode(),
                    comment: self.textAreaValue()
                };

                //Day len server tblUser
                service.update(perform).done(function(data) {
                    self.getDailyPerform();
                });
            };

            getDailyPerform() {
                let self = this;
                var dfd = $.Deferred();
                service.getDailyPerform().done(function(data) {

                    let perform = new DailyPerform(data);

                    self.selectedRuleCode(perform.settingUnit);
                    self.textAreaValue(perform.comment);

                    dfd.resolve();
                });
                return dfd.promise();

            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();

                dfd.resolve();

                //                self.getDailyPerform().done(function() {
                //                    dfd.res         //                });
                
                return dfd.promise();
            }

        }

        export class DailyPerform {
            settingUnit: number;
            comment: string;
            constructor(x: any) {
                let self = this;
                if (x) {
                    self.settingUnit = x.settingUnit;
                    self.comment = x.comment;
                } else {
                    self.settingUnit = 1;
                    self.comment = '';
                }
            }
        }
    }
}