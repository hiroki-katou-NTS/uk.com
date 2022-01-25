module nts.uk.at.view.kmk005.h {
    import flat = nts.uk.util.flatArray;
    import getText = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import confirm = nts.uk.ui.dialog.confirm;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    let __viewContext: any = window["__viewContext"] || {};

    export module viewmodel {
        export class ScreenModel {
            //search
            baseDate: KnockoutObservable<Date> = ko.observable(new Date());

            treeGrid: ITreeGrid = {
                treeType: 1,
                selectType: 1,
                isDialog: false,
                isMultiSelect: false,
                isShowAlreadySet: true,
                isShowSelectButton: false,
                baseDate: ko.observable(new Date()),
                selectedId: undefined,
                alreadySettingList: ko.observableArray([]),
                systemType: 2
            };

            model: KnockoutObservable<BonusPaySetting> = ko.observable(new BonusPaySetting({ id: '', name: '' }));
            constructor() {
                let self = this,
                    tree = self.treeGrid,
                    model = self.model();

                $.extend(tree, {
                    selectedId: self.model().wid
                });

                tree.alreadySettingList.removeAll();

                //                self.model().wid.subscribe(wild => {
                //                    let data: Array<any> = flat(_.isFunction($('#tree-grid')['getDataList']) ? $('#tree-grid')['getDataList']() : [] || [], 'childs'),
                //                        item = _.find(data, m => m.workplaceId == wild);
                //
                //                    if (item) {
                //                        self.model().wname(item.name);
                //                    } else {
                //                        self.model().wname(getText("KDL007_6"));
                //                    }
                //
                //                    service.getSetting(wild).done(x => {
                //                        if (x) {
                //                            self.model().id(x.bonusPaySettingCode);
                //                            service.getName(x.bonusPaySettingCode).done(m => {
                //                                if (m) {
                //                                    self.model().name(m.name)
                //                                } else {
                //                                    self.model().id('');
                //                                    self.model().name(getText("KDL007_6"));
                //                                }
                //                            }).fail(x => alert(x));
                //                        } else {
                //                            self.model().id('');
                //                            self.model().name(getText("KDL007_6"));
                //                        }
                //
                //                    }).fail(x => alert(x));
                //
                //                });
                //
                //                // call start after tree-grid initial
                //                $('#tree-grid')['ntsTreeComponent'](self.treeGrid).done(() => { self.start(); });
            }



            start() {
                let self = this;
                let tree = self.treeGrid;
                let model = self.model();
                let wids: Array<string> = flat($('#tree-grid')['getDataList'](), 'children').map(x => x.id);
                model.wid(wids[0]);
                // get ready setting list
                tree.alreadySettingList.removeAll();
                service.getData(wids).done((resp: Array<any>) => {
                    if (resp && resp.length) {
                        _.each(resp, x => tree.alreadySettingList.push({ workplaceId: x.workplaceId, isAlreadySetting: true }));
                    }

                    // call subscribe function of wid
                    model.id.valueHasMutated();
                    model.wid.valueHasMutated();
                }).fail(x => alert(x));
            }

            loadFirst() {
                var self = this;
                console.log("loadfirst"); 
                self.model().wid.subscribe(wild => {
                    let data: Array<any> = flat(_.isFunction($('#tree-grid')['getDataList']) ? $('#tree-grid')['getDataList']() : [] || [], 'children');
                    let item = _.find(data, m => m.id == wild);

                    if (item) {
                        self.model().wname(item.name);
                    } else {
                        self.model().wname(getText("KDL007_6"));
                    }
 
                    service.getSetting(wild).done(x => {
                        if (x) {
                            self.model().id(x.bonusPaySettingCode);
                            service.getName(x.bonusPaySettingCode).done(m => {
                                if (m) {
                                    self.model().name(m.name)
                                } else {
                                    self.model().id('');
                                    self.model().name(getText("KDL007_6"));
                                }
                            }).fail(x => alert(x));
                        } else {
                            self.model().id('');
                            self.model().name(getText("KDL007_6"));
                        }

                    }).fail(x => alert(x)); 

                });

                // call start after tree-grid initial
                $('#tree-grid')['ntsTreeComponent'](self.treeGrid).done(() => { self.start(); });
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
                    model: IBonusPaySetting = ko.toJS(self.model),
                    command: any = {
                        workplaceId: model.wid,
                        bonusPaySettingCode: model.id,
                        action: 0
                    };
                if (model.id !== '') {
                    if (model.wid !== '') {
                        // call service to save setting
                        service.saveData(command).done(() => {
                            nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                            self.start();
                        });
                    }
                } else {
                    alert({ messageId: "Msg_30" });
                }
            }

            removeData() {
                let self = this,
                    model: IBonusPaySetting = ko.toJS(self.model),
                    command: any = {
                        workplaceId: model.wid,
                        bonusPaySettingCode: model.id,
                        action: 1
                    };
                if (model.wid !== '') {
                    // call service to delete setting
                    service.saveData(command).done(() => {
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                        self.start();
                    });
                }
            }
        }


        interface IBonusPaySetting {
            id: string;
            name: string;
            wid?: string; // workplace id
            wname?: string; // workplace name
        }

        class BonusPaySetting {
            id: KnockoutObservable<string> = ko.observable('');
            name: KnockoutObservable<string> = ko.observable('');
            wid: KnockoutObservable<string> = ko.observable('');
            wname: KnockoutObservable<string> = ko.observable('');

            constructor(param: IBonusPaySetting) {
                let self = this;

                self.id(param.id);
                self.name(param.name);
                self.wid(param.wid || '');
                self.wname(param.wname || '');

                self.id.subscribe(x => {
                    let view = __viewContext.viewModel && __viewContext.viewModel.tabView,
                        acts: any = view && _.find(view.tabs(), (t: any) => t.active());
                    if (acts && acts.id == 'H') {
                        view.removeAble(!!x);
                    }
                });
            }
        }

        interface ITreeGrid {
            treeType: number;
            selectType: number;
            isDialog: boolean;
            isMultiSelect: boolean;
            isShowAlreadySet: boolean;
            isShowSelectButton: boolean;
            baseDate: KnockoutObservable<any>;
            selectedWorkplaceId: KnockoutObservable<any>;
            alreadySettingList: KnockoutObservableArray<any>;
        }
    }
}