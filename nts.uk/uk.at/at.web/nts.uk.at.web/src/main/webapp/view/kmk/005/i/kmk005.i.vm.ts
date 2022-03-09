module nts.uk.at.view.kmk005.i {
    import getText = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import error = nts.uk.ui.dialog.error;
    import confirm = nts.uk.ui.dialog.confirm;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import blockUI = nts.uk.ui.block;
    import Ccg001ReturnedData = nts.uk.com.view.ccg.share.ccg.service.model.Ccg001ReturnedData;
    import EmployeeSearchDto = nts.uk.com.view.ccg.share.ccg.service.model.EmployeeSearchDto;

    let __viewContext: any = window["__viewContext"] || {};

    export module viewmodel {
        export class ScreenModel {
            ccg001ComponentOption: any;

            listHist: KnockoutObservableArray<IWorkingCondition> = ko.observableArray([]); 
            selectedHistCode: KnockoutObservable<string> = ko.observable();   

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
                
                self.selectedHistCode.subscribe(function (histId) {
                    if(histId === ""){                 
                        __viewContext.viewModel.tabView.enableRegister(false);
                    } else {
                        __viewContext.viewModel.tabView.enableRegister(true);
                        self.findHistItem(histId);
                    }                    
                });

                self.reloadCcg001();
                $.extend(self.kcpcompoment, {
                    selectedCode: model.ecd
                });
            }
            
            public reloadCcg001(): void {
                let self = this,
                    model = self.model();
                if ($('.ccg-sample-has-error').ntsError('hasError')) {
                    return;
                }
                self.ccg001ComponentOption = {
                    /** Common properties */
                    systemType: 2, // システム区分
                    showEmployeeSelection: false, // 検索タイプ
                    showQuickSearchTab: false, // クイック検索
                    showAdvancedSearchTab: true, // 詳細検索
                    showBaseDate: true, // 基準日利用
                    showClosure: false, // 就業締め日利用
                    showAllClosure: false, // 全締め表示
                    showPeriod: false, // 対象期間利用
                    periodFormatYM: false, // 対象期間精度

                    /** Required parameter */
                    baseDate: moment().toISOString(), // 基準日
                    inService: true, // 在職区分
                    leaveOfAbsence: false, // 休職区分
                    closed: false, // 休業区分
                    retirement: false, // 退職区分
                    
                    /** Quick search tab options */
                    showAllReferableEmployee: false, // 参照可能な社員すべて
                    showOnlyMe: false, // 自分だけ
                    showSameWorkplace: false, // 同じ職場の社員
                    showSameWorkplaceAndChild: false, // 同じ職場とその配下の社員

                    /** Advanced search properties */
                    showEmployment: true, // 雇用条件
                    showDepartment: false, //部門条件
                    showWorkplace: true, // 職場条件
                    showClassification: true, // 分類条件
                    showJobTitle: true, // 職位条件
                    showWorktype: true, // 勤種条件
                    isMutipleCheck: true, // 選択モード

                    /** Return data */
                    returnDataFromCcg001: function(data: Ccg001ReturnedData) {
                        self.kcpcompoment.employeeInputList(_.map(data.listEmployee, item  => {
                            return {
                                employeeId: item.employeeId,
                                code: item.employeeCode,
                                name: item.employeeName,
                                workplaceId: item.workplaceId,
                                workplaceCode: item.workplaceCode,
                                affiliationName: item.affiliationName
                            };
                        }));
                        if (self.kcpcompoment.employeeInputList() && self.kcpcompoment.employeeInputList().length > 0){
                            model.ecd(self.kcpcompoment.employeeInputList()[0].code);
                        }
                        //model.ecd(data[0].employeeCode);
                        model.bid.valueHasMutated();
                    }
                }

                // Start component
                $('#ccgcomponent').ntsGroupComponent(self.ccg001ComponentOption);
            }

            start() {
                let self = this,
                    model = self.model();

                model.ecd.valueHasMutated();
                self.kcpcompoment.employeeInputList.valueHasMutated();
            }

            loadFirst() {
                let self = this; 
                    self.model().ecd.subscribe(x => {
                    let emps = self.kcpcompoment.employeeInputList(),
                        item = _.find(emps, e => e.code == x);
                    if (item) {
                        self.model().eid(item.employeeId);
                        self.model().ename(item.name);
                        service.getHist(item.employeeId).done((data: Array<IWorkingCondition>) => {
                            if (data && data.length > 0) {
                                self.listHist(data);
                                self.selectedHistCode(data[0].histId);
                                __viewContext.viewModel.tabView.enableRegister(true);
                            } else {         
                                self.listHist([]);
                                self.model().bid('');
                                self.model().bname(getText("KDL007_6"));
                                __viewContext.viewModel.tabView.removeAble(false);
                                __viewContext.viewModel.tabView.enableRegister(false);                               
                            }
                        }).fail((res) => {
                            error({messageId: res.messageId});
                        });
                    } else {
                        self.model().eid('');
                        self.model().ename(getText("KDL007_6"));
                    }
                });

                self.kcpcompoment.employeeInputList.subscribe(lst => {
                    let eids = lst.map(x => x.employeeId);

                    self.kcpcompoment.alreadySettingList.removeAll();
                    if (eids && eids.length) {
                        service.getList(eids).done((resp: Array<any>) => {
                            if (resp && resp.length) {
                                _.each(resp, x => {
                                    let emp = _.find(lst, e => e.employeeId == x);
                                    if (emp) {
                                        self.kcpcompoment.alreadySettingList.push({ code: emp.code, isAlreadySetting: true });
                                    }
                                });
                            }
                        }).fail(x => alert(x));
                    }
                });

                // binding component controls
                $('#ccgcomponent')['ntsGroupComponent'](self.ccg001ComponentOption).done(() => {
                    $('#kcpcomponent')['ntsListComponent'](self.kcpcompoment).done(() => {
                        self.start();
                    });
                });
            }

            public findHistItem(histId: string): void {
                let self = this;
                blockUI.invisible();
                service.getHistItem(histId).done((data: IWorkingConditionItem) =>{
                    service.getName(data.bonusPaySettingCode).done(m => {
                        if (m) {
                            self.model().bid(m.code);
                            self.model().bname(m.name);
                        } else {
                            self.model().bid('');
                            self.model().bname(getText("KDL007_6"));
                        }
                    }).fail(m => alert(m));  

                }).fail(res => {
                    error({messageId: res.messageId});
                }).always(()=>{
                    blockUI.clear();
                });
            }

            openBonusPaySettingDialog() {
                let self = this,
                    model: BonusPaySetting = self.model();

                setShared("KDL007_PARAM", { isMulti: false, posibles: [], selecteds: [model.bid()] });

                modal('../../../kdl/007/a/index.xhtml').onClosed(() => {
                    let data: any = getShared('KDL007_VALUES');
                    if (data && data.selecteds) {
                        let arrCode: any = data.selecteds[0];
                        let code: string = Array.isArray(arrCode) ? arrCode[0] : arrCode;
                        if (!(_.isEmpty(code))) {
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

            public saveData(): void {
                let self = this,
                    command: any = {
                        employeeId: self.model().eid(),
                        histId: self.selectedHistCode(),
                        bonusPaySettingCode: self.model().bid()
                    };                
                if (self.model().bid() !== '') {
                    if (self.model().eid() !== '') {
                        blockUI.invisible();
                        service.register(command).done(() => {
                            nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                            self.start();
                        }).fail((res) => {
                            if (res.messageId == 'Msg_339') {
                                error({ messageId: res.messageId });
                            }
                        }).always(() => {
                            blockUI.clear();
                        });
                    }
                } else {
                    alert(nts.uk.resource.getMessage("Msg_30", []));
                }
            }

            removeData() {
                let self = this,
                    command: any = {
                        employeeId: self.model().eid(),
                        histId: self.selectedHistCode(),
                        bonusPaySettingCode: null
                    };                  
                
                if (self.model().bid() !== '') {
                    if (self.model().eid() !== '') {
                        blockUI.invisible();
                        service.register(command).done(() => {
                            nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                            self.model().bid('');
                            self.model().bname(getText("KDL007_6"));
                            self.start();
                        }).fail((res) => {
                            error({ messageId: res.messageId });
                        }).always(() => {
                            blockUI.clear();
                        });
                    }
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

    interface IWorkingCondition {
        /**履歴項目 */
        histId: string;        
        period: string;
    }

    class WorkingCondition {        
        histId: KnockoutObservable<string> = ko.observable('');
        period: KnockoutObservable<string> = ko.observable('');       

        constructor(param: IWorkingCondition) {
            let self = this;
            self.histId(param.histId);
            self.period(param.period);
        }
    }

    interface IWorkingConditionItem {
        /**加給時間帯 */
        bonusPaySettingCode: string;   
    }

    class WorkingConditionItem {        
        bonusPaySettingCode: KnockoutObservable<string> = ko.observable('');

        constructor(param: IWorkingConditionItem) {
            let self = this;
            self.bonusPaySettingCode(param.bonusPaySettingCode);
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