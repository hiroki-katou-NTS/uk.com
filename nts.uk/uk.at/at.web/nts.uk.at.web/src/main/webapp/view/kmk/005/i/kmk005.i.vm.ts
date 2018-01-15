module nts.uk.at.view.kmk005.i {
    import getText = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import confirm = nts.uk.ui.dialog.confirm;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    let __viewContext: any = window["__viewContext"] || {};

    export module viewmodel {
        export class ScreenModel {
            ccgcomponent: any = {
                isOnlyMe: true,
                isMutipleCheck: true,
                isQuickSearchTab: true,
                isSelectAllEmployee: true,
                isAdvancedSearchTab: true,
                isEmployeeOfWorkplace: true,
                isAllReferableEmployee: true,
                isEmployeeWorkplaceFollow: true,
                baseDate: ko.observable(new Date())
            };

            kcpcompoment: IKCPCompoment = {
                listType: 4,
                selectType: 1,
                isDialog: false,
                isMultiSelect: false,
                isShowAlreadySet: true,
                isShowNoSelectRow: false,
                isShowWorkPlaceName: true,
                isShowSelectAllButton: false,
                selectedCode: undefined,
                employeeInputList: ko.observableArray([]),
                alreadySettingList: ko.observableArray([]),
            };

            model: KnockoutObservable<BonusPaySetting> = ko.observable(new BonusPaySetting({ bid: '', bname: '' }));
            constructor() {
                let self = this,
                    model = self.model();

                // define event for ccgcomponent
                $.extend(self.ccgcomponent, {
                    onSearchAllClicked: (datas: Array<any>) => {
                        self.kcpcompoment.employeeInputList(datas.map(x => {
                            return {
                                employeeId: x.employeeId,
                                code: x.employeeCode,
                                name: x.employeeName,
                                workplaceId: x.workplaceId,
                                workplaceCode: x.workplaceCode,
                                workplaceName: x.workplaceName
                            };
                        }));
                        model.ecd(datas[0].employeeCode);
                        model.bid.valueHasMutated();
                    },
                    onSearchOnlyClicked: (data: any) => {
                        self.kcpcompoment.employeeInputList([data].map(x => {
                            return {
                                employeeId: x.employeeId,
                                code: x.employeeCode,
                                name: x.employeeName,
                                workplaceId: x.workplaceId,
                                workplaceCode: x.workplaceCode,
                                workplaceName: x.workplaceName
                            };
                        }));
                        model.ecd(data.employeeCode);
                        model.bid.valueHasMutated();
                    },
                    onSearchOfWorkplaceClicked: (datas: Array<any>) => {
                        self.kcpcompoment.employeeInputList(datas.map(x => {
                            return {
                                employeeId: x.employeeId,
                                code: x.employeeCode,
                                name: x.employeeName,
                                workplaceId: x.workplaceId,
                                workplaceCode: x.workplaceCode,
                                workplaceName: x.workplaceName
                            };
                        }));
                        model.ecd(datas[0].employeeCode);
                        model.bid.valueHasMutated()
                    },
                    onSearchWorkplaceChildClicked: (datas: Array<any>) => {
                        self.kcpcompoment.employeeInputList(datas.map(x => {
                            return {
                                employeeId: x.employeeId,
                                code: x.employeeCode,
                                name: x.employeeName,
                                workplaceId: x.workplaceId,
                                workplaceCode: x.workplaceCode,
                                workplaceName: x.workplaceName
                            };
                        }));
                        model.ecd(datas[0].employeeCode);
                        model.bid.valueHasMutated()
                    },
                    onApplyEmployee: (datas: Array<any>) => {
                        self.kcpcompoment.employeeInputList(datas.map(x => {
                            return {
                                employeeId: x.employeeId,
                                code: x.employeeCode,
                                name: x.employeeName,
                                workplaceId: x.workplaceId,
                                workplaceCode: x.workplaceCode,
                                workplaceName: x.workplaceName
                            };
                        }));
                        model.ecd(datas[0].employeeCode);
                        model.bid.valueHasMutated()
                    }
                });

                // extend selectedCode for kcpcomponent 
                $.extend(self.kcpcompoment, {
                    selectedCode: model.ecd
                });

                model.ecd.subscribe(x => {
                    let emps = self.kcpcompoment.employeeInputList(),
                        item = _.find(emps, e => e.code == x);
                    if (item) {
                        model.eid(item.employeeId);
                        model.ename(item.name);

                        service.getData(item.employeeId).done(d => {
                            if (d) {
                                model.bid(d.bonusPaySettingCode);
                                service.getName(d.bonusPaySettingCode).done(m => {
                                    if (m) {
                                        model.bname(m.name)
                                    } else {
                                        model.bid('');
                                        model.bname(getText("KDL007_6"));
                                    }
                                }).fail(m => alert(m));
                            } else {
                                model.bid('');
                                model.bname(getText("KDL007_6"));
                            }
                        }).fail(x => alert(x));
                    } else {
                        model.eid('');
                        model.ename(getText("KDL007_6"));
                    }
                });

                self.kcpcompoment.employeeInputList.subscribe(lst => {
                    let eids = lst.map(x => x.employeeId);

                    self.kcpcompoment.alreadySettingList.removeAll();
                    if (eids && eids.length) {
                        service.getList(eids).done((resp: Array<any>) => {
                            if (resp && resp.length) {
                                _.each(resp, x => {
                                    let emp = _.find(lst, e => e.employeeId == x.employeeId);
                                    if (emp) {
                                        self.kcpcompoment.alreadySettingList.push({ code: emp.code, isAlreadySetting: true });
                                    }
                                });
                            }
                        }).fail(x => alert(x));
                    }
                });


                // binding component controls
                $('#ccgcomponent')['ntsGroupComponent'](self.ccgcomponent).done(() => {
                    $('#kcpcomponent')['ntsListComponent'](self.kcpcompoment).done(() => {
                        self.start();
                    });
                });
            }

            start() {
                let self = this,
                    model = self.model();
                model.ecd.valueHasMutated();
                self.kcpcompoment.employeeInputList.valueHasMutated();
            }

            openBonusPaySettingDialog() {
                let self = this,
                    model: BonusPaySetting = self.model();

                setShared("KDL007_PARAM", { isMulti: false, posibles: [], selecteds: [model.bid()] });

                modal('../../../kdl/007/a/index.xhtml').onClosed(() => {
                    let data: any = getShared('KDL007_VALUES');
                    if (data && data.selecteds) {
                        let code: string = data.selecteds[0];
                        if (code) {
                            model.bid(code);
                            service.getName(code).done(resp => {
                                if (resp) {
                                    model.bname(resp.name);
                                }
                                else {
                                    model.bid('');
                                    model.bname(getText("KDL007_6"));
                                }
                            }).fail(x => alert(x));
                        } else {
                            model.bid('');
                            model.bname(getText("KDL007_6"));
                        }
                    }
                });
            }

            saveData() {
                let self = this,
                    model: IBonusPaySetting = ko.toJS(self.model),
                    data: any = {
                        action: 0,
                        employeeId: model.eid,
                        bonusPaySettingCode: model.bid,
                    };
                if (model.bid !== '') {
                    if (model.eid !== '') {
                        service.saveData(data).done(() => {
                            nts.uk.ui.dialog.info(nts.uk.resource.getMessage("Msg_15", []));
                            self.start();
                        });
                    }
                } else {
                    alert(nts.uk.resource.getMessage("Msg_30", []));
                }
            }

            removeData() {
                let self = this,
                    model: IBonusPaySetting = ko.toJS(self.model),
                    data: any = {
                        action: 1,
                        employeeId: model.eid,
                        bonusPaySettingCode: model.bid,
                    };
                if (model.eid !== '') {
                    service.saveData(data).done(() => {
                        nts.uk.ui.dialog.info(nts.uk.resource.getMessage("Msg_16", []));
                        self.start();
                    });
                }
            }
        }
    }


    interface IBonusPaySetting {
        bid: string;
        bname: string;
        eid?: string;
        ecd?: string;
        ename?: string;
    }

    class BonusPaySetting {
        bid: KnockoutObservable<string> = ko.observable('');
        bname: KnockoutObservable<string> = ko.observable('');
        eid: KnockoutObservable<string> = ko.observable('');
        ecd: KnockoutObservable<string> = ko.observable('');
        ename: KnockoutObservable<string> = ko.observable('');

        constructor(param: IBonusPaySetting) {
            let self = this;

            self.bid(param.bid);
            self.bname(param.bname);
            self.eid(param.eid || '');
            self.ecd(param.ecd || '');
            self.ename(param.ename || '');

            self.bid.subscribe(x => {
                let view = __viewContext.viewModel && __viewContext.viewModel.tabView,
                    acts: any = view && _.find(view.tabs(), (t: any) => t.active());
                if (acts && acts.id == 'I') {
                    view.removeAble(!!x);
                }
            });
        }
    }

    interface IKCPCompoment {
        listType: number;
        selectType: number;
        isDialog: boolean;
        isMultiSelect: boolean;
        isShowAlreadySet: boolean;
        isShowNoSelectRow: boolean;
        isShowWorkPlaceName: boolean;
        isShowSelectAllButton: boolean;
        selectedCode: KnockoutObservable<string>;
        employeeInputList: KnockoutObservableArray<any>;
        alreadySettingList: KnockoutObservableArray<any>;
    }
}