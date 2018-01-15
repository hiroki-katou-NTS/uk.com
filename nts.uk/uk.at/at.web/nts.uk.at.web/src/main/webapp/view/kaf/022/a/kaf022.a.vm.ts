module nts.uk.at.view.kmf022 {
    import getText = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import confirm = nts.uk.ui.dialog.confirm;
    import href = nts.uk.request.jump;
    import modal = nts.uk.ui.windows.sub.modal;

    let __viewContext: any = window["__viewContext"] || {};

    export module viewmodel1 {
        export class TabScreenModel {
            title: KnockoutObservable<string> = ko.observable('');
            removeAble: KnockoutObservable<boolean> = ko.observable(true);
            tabs: KnockoutObservableArray<TabModel> = ko.observableArray([
                new TabModel({ id: 'a', name: getText('Com_Company'), active: true }),
                new TabModel({ id: 'l', name: getText('Com_Employment') }),
                new TabModel({ id: 'm', name: getText('Com_Workplace') })
                
            ]);
            currentTab: KnockoutObservable<string> = ko.observable('a');

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
                    case 'a':
                        self.currentTab('a');
                        if (!!view.viewmodelA && typeof view.viewmodelA.start == 'function') {
                            view.viewmodelA.start();
                        }
                        break;
                    case 'l':
                        self.currentTab('l');
                        if (!!view.viewmodelE && typeof view.viewmodelE.startPage == 'function') {
                            view.viewmodelE.startPage();
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

    export module a.viewmodel {
        export class ScreenModel {
            constructor() {
                let self = this;
                self.start();
            }
 
            start() {
                var self = this;
                var dfd = $.Deferred();
                service.findJobAssign().done((lstData) => {
                    console.log(lstData);
                    dfd.resolve();
                });
                return dfd.promise();
            }

            register() {
                nts.uk.ui.block.invisible();
                var self = this;
                let b = this.value();
                let a = self.items();
                let i = 0;
                var items = _.filter(self.items(), function(item: Item) {
                    return item.date() || item.month() || item.year();
                });

                var dataTranfer = {
                    specialHolidayCode: nts.uk.ui.windows.getShared('KMF004D_SPHD_CD'), // TODO
                    lengthServiceYearAtr: self.selectedId(),
                    yearServiceSets: ko.toJS(items)
                }

                service.update(dataTranfer).done(function(errors) {
                    self.start();
                    if (errors && errors.length > 0) {
                        self.addListError(errors);
                    } else {
                        nts.uk.ui.dialog.alert({ messageId: "Msg_15" }).then(function(){
                            $("#button_radio").focus();
                        });
                    }
                }).fail(function(error) {
                    alert(error.message);
                });
                nts.uk.ui.block.clear();
            }   
   
            closeDialog() {
                nts.uk.ui.windows.close();
            }
            
            /**
             * Set error
             */
            addListError(errorsRequest: Array<string>) {
                var messages = {};
                _.forEach(errorsRequest, function(err) {
                    messages[err] = nts.uk.resource.getMessage(err);
                });
    
                var errorVm = {
                    messageId: errorsRequest,
                    messages: messages
                };
                nts.uk.ui.dialog.bundledErrors(errorVm);
            }
        }
        
        class BoxModel {
            id: number;
            name: string;
            constructor(id, name) {
                var self = this;
                self.id = id;
                self.name = name;
            }
        }  
        
        export class Item {
            yearServiceNo: KnockoutObservable<number>;
            month: KnockoutObservable<number>;
            year: KnockoutObservable<number>;
            date: KnockoutObservable<number>;

            constructor(param: IItem) {
                var self = this;
                self.yearServiceNo = ko.observable(param.yearServiceNo);
                self.month = ko.observable(param.month);
                self.year = ko.observable(param.year);
                self.date = ko.observable(param.date);
            }
        }
        
        export interface IItem {
            yearServiceNo: number
            month: number;
            year: number;
            date: number;
        }
    }
}