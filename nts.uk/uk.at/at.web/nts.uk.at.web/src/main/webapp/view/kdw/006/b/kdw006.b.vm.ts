module nts.uk.at.view.kdw006.b {
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
            //Daily perform id from other screen
            settingUnit: KnockoutObservable<number>;
            commentDaily: KnockoutObservable<string>;
            commentMonthly: KnockoutObservable<string>;
            formatPerformanceDto: KnockoutObservable<FormatPerformanceDto>;
            daiPerformanceFunDto: KnockoutObservable<DaiPerformanceFunDto>;
            monPerformanceFunDto: KnockoutObservable<MonPerformanceFunDto>;
            roundingRules: KnockoutObservableArray<ItemModel>;

            sideBar: KnockoutObservable<number>;
            constructor() {
                let self = this;
                self.settingUnit = ko.observable(0);
                self.commentDaily = ko.observable(null);
                self.commentMonthly = ko.observable(null);
                self.roundingRules = ko.observableArray([]);
                let listRest = __viewContext.enums.SettingUnitType;
                
                self.sideBar = ko.observable(0);
                
                _.forEach(listRest, (a) => {
                    self.roundingRules.push(new ItemModel(a.value, a.name));
                });

                self.daiPerformanceFunDto = ko.observable(new DaiPerformanceFunDto({
                    cid: '',
                    comment: null,
                    monthChkMsgAtr: null,
                    disp36Atr: null,
                    clearManuAtr: null,
                    flexDispAtr: null,
                    breakCalcUpdAtr: null,
                    
                    breakTimeAutoAtr: null,
                    breakClrTimeAtr: null,
                    autoSetTimeAtr: null,
                    
                    ealyCalcUpdAtr: null,
                    overtimeCalcUpdAtr: null,
                    lawOverCalcUpdAtr: null,
                    manualFixAutoSetAtr: null,
                }));

                self.monPerformanceFunDto = ko.observable(new MonPerformanceFunDto({
                    cid: '',
                    comment: null,
                    dailySelfChkDispAtr: null
                }));

                self.formatPerformanceDto = ko.observable(new FormatPerformanceDto({
                    cid: '',
                    settingUnitType: 0
                }));

            }

            start() {
                let self = this;
                nts.uk.ui.block.grayout();
                let dfd = $.Deferred();
                self.getFormatPerformanceById().done(function() {
                    dfd.resolve();
                });
                self.getDaiPerformanceFunById().done(function() {
                    dfd.resolve();
                });
                self.getMonPerformanceFunById().done(function() {
                    dfd.resolve();
                }).always(() => {
                    nts.uk.ui.errors.clearAll();
                    nts.uk.ui.block.clear();
                });
                return dfd.promise();
            }


            getFormatPerformanceById(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                service.getFormatPerformanceById().done(function(data: IFormatPerformanceDto) {
                    if (data) {
                        self.settingUnit(data.settingUnitType);
                        self.formatPerformanceDto(new FormatPerformanceDto(data));
                        dfd.resolve();
                    } else {
                        self.settingUnit(0);
                        dfd.resolve();
                    }
                });
                return dfd.promise();
            }

            getDaiPerformanceFunById(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                service.getDaiPerformanceFunById().done(function(data: IDaiPerformanceFunDto) {
                    if (data) {
                        self.commentDaily(data.comment);
                        self.daiPerformanceFunDto(new DaiPerformanceFunDto(data));
                        dfd.resolve();
                    } else {
                        self.commentDaily(null);
                        dfd.resolve();
                    }
                });
                return dfd.promise();
            }
            
            jumpTo(sidebar) : JQueryPromise<any>  {
                let self = this;
                nts.uk.request.jump("/view/kdw/006/a/index.xhtml", { ShareObject: sidebar() });
            }

            getMonPerformanceFunById(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                service.getMonPerformanceFunById().done(function(data: IMonPerformanceFunDto) {
                    if (data) {
                        self.commentMonthly(data.comment);
                        self.monPerformanceFunDto(new MonPerformanceFunDto(data));
                        dfd.resolve();
                    } else {
                        self.commentMonthly(null);
                        dfd.resolve();
                    }
                });
                return dfd.promise();
            }

            saveData() {
                nts.uk.ui.block.invisible();
                let self = this;
                self.formatPerformanceDto().settingUnitType(self.settingUnit());
                self.daiPerformanceFunDto().comment(self.commentDaily());
                self.monPerformanceFunDto().comment(self.commentMonthly());
                if (nts.uk.ui.errors.hasError() === false) {
                    service.updateFormatPerformanceById(ko.toJS(self.formatPerformanceDto)).done(function() {
                        service.updatetDaiPerformanceFunById(ko.toJS(self.daiPerformanceFunDto)).done(function() {
                            service.updateMonPerformanceFunById(ko.toJS(self.monPerformanceFunDto)).done(function() {
                                self.start();
                                nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                            });
                        });
                    });
                }
                nts.uk.ui.block.clear();
            }

        }

        interface IDaiPerformanceFunDto {
            cid: string;
            comment: string;
            monthChkMsgAtr: number;
            disp36Atr: number;
            clearManuAtr: number;
            flexDispAtr: number;
            breakCalcUpdAtr: number;
            breakTimeAutoAtr: number;
            breakClrTimeAtr: number;
            autoSetTimeAtr: number;
            ealyCalcUpdAtr: number;
            overtimeCalcUpdAtr: number;
            lawOverCalcUpdAtr: number;
            manualFixAutoSetAtr: number;
            checkErrRefDisp : number;
        }

        class DaiPerformanceFunDto {
            cid: KnockoutObservable<string>;
            comment: KnockoutObservable<string>;
            monthChkMsgAtr: KnockoutObservable<number>;
            disp36Atr: KnockoutObservable<number>;
            clearManuAtr: KnockoutObservable<number>;
            flexDispAtr: KnockoutObservable<number>;
            breakCalcUpdAtr: KnockoutObservable<number>;
            breakTimeAutoAtr: KnockoutObservable<number>;

            breakClrTimeAtr: KnockoutObservable<number>;

            autoSetTimeAtr: KnockoutObservable<number>;

            ealyCalcUpdAtr: KnockoutObservable<number>;

            overtimeCalcUpdAtr: KnockoutObservable<number>;

            lawOverCalcUpdAtr: KnockoutObservable<number>;

            manualFixAutoSetAtr: KnockoutObservable<number>;
            
            checkErrRefDisp : KnockoutObservable<number>;
            

            constructor(param: IDaiPerformanceFunDto) {
                let self = this;
                self.cid = ko.observable(param.cid);
                self.comment = ko.observable(param.comment);
                self.monthChkMsgAtr = ko.observable(param.monthChkMsgAtr);
                self.disp36Atr = ko.observable(param.disp36Atr);
                self.clearManuAtr = ko.observable(param.clearManuAtr);
                self.flexDispAtr = ko.observable(param.flexDispAtr);
                self.breakCalcUpdAtr = ko.observable(param.breakCalcUpdAtr);
                self.breakTimeAutoAtr = ko.observable(param.breakTimeAutoAtr);
                self.breakClrTimeAtr = ko.observable(param.breakClrTimeAtr);
                self.autoSetTimeAtr = ko.observable(param.autoSetTimeAtr);
                self.ealyCalcUpdAtr = ko.observable(param.ealyCalcUpdAtr);
                self.overtimeCalcUpdAtr = ko.observable(param.overtimeCalcUpdAtr);
                self.lawOverCalcUpdAtr = ko.observable(param.lawOverCalcUpdAtr);
                self.manualFixAutoSetAtr = ko.observable(param.manualFixAutoSetAtr);
                self.checkErrRefDisp = ko.observable(param.checkErrRefDisp);
            }
        }

        interface IMonPerformanceFunDto {
            cid: string;
            comment: string;
            dailySelfChkDispAtr: number;
        }
        class MonPerformanceFunDto {
            cid: KnockoutObservable<string>;
            comment: KnockoutObservable<string>;
            dailySelfChkDispAtr: KnockoutObservable<number>;
            constructor(param: IMonPerformanceFunDto) {
                let self = this;
                self.cid = ko.observable(param.cid);
                self.comment = ko.observable(param.comment);
                self.dailySelfChkDispAtr = ko.observable(param.dailySelfChkDispAtr);
            }
        }
        interface IFormatPerformanceDto {
            cid: string;
            settingUnitType: number;
        }

        class FormatPerformanceDto {
            cid: KnockoutObservable<string>;
            settingUnitType: KnockoutObservable<number>;
            constructor(param: IFormatPerformanceDto) {
                let self = this;
                self.cid = ko.observable(param.cid);
                self.settingUnitType = ko.observable(param.settingUnitType);
            }
        }

        class ItemModel {
            code: number;
            name: string;

            constructor(code: number, name: string) {
                this.code = code;
                this.name = name;
            }
        }
    }
}