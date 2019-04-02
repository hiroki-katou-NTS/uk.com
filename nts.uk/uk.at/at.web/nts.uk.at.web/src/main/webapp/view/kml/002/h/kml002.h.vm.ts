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
            oldtab: KnockoutObservable<any> = ko.observable(new TabModel({ id: 0, name: "" }));
            //radio

            constructor() {
                let self = this;
                //get use setting
                self.tabs().map((t) => {
                    // set title for tab

                    if (t.active() == true) {
                        self.title(t.name);
                    }
                });
            }

            changeTab(tab: TabModel) {
                let self = this,
                    view: any = __viewContext.viewModel;

                // cancel action if tab self click
                if (self.oldtab().id == tab.id) {
                    return;
                }
                //set not display remove button first when change tab
                //__viewContext.viewModel.tabView.removeAble(false);
                tab.active(true);
                self.title(tab.name);
                self.oldtab(tab);

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
            /**
             * Export excel
             */
            private exportExcel(): void {
                var self = this;
                nts.uk.ui.block.grayout();
                let langId = "ja";
                h.service.saveAsExcel(langId).done(function() {
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError({ messageId: error.messageId });
                }).always(function() {
                    nts.uk.ui.block.clear();
                });
            }
        }
    
        interface ITabModel {
            id: any;
            name: string;
            active?: boolean;
            display?: boolean;
        }

        class TabModel {
            id: any;
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
            items: KnockoutObservableArray<any>;
            currentItem: KnockoutObservable<TotalTime>;
            constructor() {
                var self = this;

                self.useCls = ko.observableArray([
                    { code: 1, name: nts.uk.resource.getText("KML002_99") },
                    { code: 0, name: nts.uk.resource.getText("KML002_100") }
                ]);
                self.items = ko.observableArray([]);
            }

            /**
             * Start page.
             */
            start() {
                var self = this;
                var dfd = $.Deferred();
                self.items.removeAll();
                self.getAllFixedVertical();
                dfd.resolve();

                return dfd.promise();
            }

            openDialog(fixItemNo: number) {
                
                let self = this;
                if(FixedItemAtr.TIME_ZONE == fixItemNo){
                    self.openIDialog(fixItemNo);
                }else if(FixedItemAtr.TOTAL_COUNT == fixItemNo){
                    self.openLDialog(fixItemNo);
                }else{
                    self.openMDialog();   
                }
            }
            openIDialog(fixItemNo: number) {
                let self = this;  
                nts.uk.ui.windows.setShared('KML002H_VERTICAL_ID', fixItemNo);
                nts.uk.ui.block.invisible();
                nts.uk.ui.windows.sub.modal('/view/kml/002/i/index.xhtml').onClosed(function(): any {
                    nts.uk.ui.block.clear();
                });
                
            }
            openLDialog(fixItemNo: number) {
                let self = this;
                nts.uk.ui.windows.setShared('KML002H_VERTICAL_ID', fixItemNo);
                nts.uk.ui.block.invisible();
                nts.uk.ui.windows.sub.modal('/view/kml/002/l/index.xhtml').onClosed(function(): any {
                nts.uk.ui.block.clear();
                });
                
            }
            
            openMDialog() {
                let self = this;
            }
            
            getAllFixedVertical(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                self.items.removeAll();
                service.findByCid().done(function(totalTimeArr: Array<ITotalTime>) {
                    _.forEach(totalTimeArr, function(res: ITotalTime) {
                        var totalTime: ITotalTime = {
                            fixedItemAtr: res.fixedItemAtr,
                            fixedVerticalName: res.fixedVerticalName,
                            useAtr: res.useAtr,
                            verticalDetailedSettings: res.verticalDetailedSettings
                        };
                        
                        self.items.push(new TotalTime(totalTime));
                    });
                });
                return dfd.promise();
            }

            addFixedVertical(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                nts.uk.ui.block.invisible();
                service.addFixedVertical(ko.toJS(self.items())).done(function(any) {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                }).always(()=>{
                    nts.uk.ui.block.clear();    
                });
                
                return dfd.promise();
            }
        }
        export interface ITotalTime {
            fixedItemAtr?: number;
            fixedVerticalName?: String;
            useAtr?: number;
            verticalDetailedSettings?: number;
        }
        class TotalTime {
            fixedItemAtr: KnockoutObservable<number> = ko.observable(null);
            fixedVerticalName: KnockoutObservable<String> = ko.observable(null);
            useAtr: KnockoutObservable<number> = ko.observable(null);
            verticalDetailedSettings: KnockoutObservable<number> = ko.observable(null);
            constructor(param: ITotalTime) {
                this.fixedItemAtr(param.fixedItemAtr || 0);
                this.fixedVerticalName(param.fixedVerticalName || null);
                this.useAtr(param.useAtr || 0);
                this.verticalDetailedSettings(param.verticalDetailedSettings || 0);
            }
        }
        enum FixedItemAtr {
            /** 0- 時間帯 **/
            TIME_ZONE = 0,
            
            /** 1- 回数集計 **/
            TOTAL_COUNT = 1,
            
            /** 6- 役割 **/
            ROLE = 6
        }
    }
}