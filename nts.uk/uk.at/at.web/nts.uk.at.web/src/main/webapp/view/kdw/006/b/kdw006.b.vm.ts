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

            constructor() {
                let self = this;
                self.settingUnit = ko.observable(0);
                self.commentDaily = ko.observable(null);
                self.commentMonthly = ko.observable(null);
                self.roundingRules = ko.observableArray([]);
                let listRest = __viewContext.enums.SettingUnitType;
                _.forEach(listRest, (a) => {
                    self.roundingRules.push(new ItemModel(a.value, a.name));
                });

                self.daiPerformanceFunDto = ko.observable(new DaiPerformanceFunDto({
                    cid: '',
                    comment: '',
                    isCompleteConfirmOneMonth: null,
                    isDisplayAgreementThirtySix: null,
                    isFixClearedContent: null,
                    isDisplayFlexWorker: null,
                    isUpdateBreak: null,
                    isSettingTimeBreak: null,
                    isDayBreak: null,
                    isSettingAutoTime: null,
                    isUpdateEarly: null,
                    isUpdateOvertime: null,
                    isUpdateOvertimeWithinLegal: null,
                    isFixContentAuto: null,
                }));

                self.monPerformanceFunDto = ko.observable(new MonPerformanceFunDto({
                    cid: '',
                    comment: '',
                    isConfirmDaily: null
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
                }).always(()=>{
                    nts.uk.ui.errors.clearAll();
                    nts.uk.ui.block.clear();
                });;
                return dfd.promise();
            }


            getFormatPerformanceById(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                service.getFormatPerformanceById().done(function(data: IFormatPerformanceDto) {
                    self.settingUnit(data.settingUnitType);
                    self.formatPerformanceDto(new FormatPerformanceDto(data));
                    dfd.resolve();
                });
                return dfd.promise();
            }

            getDaiPerformanceFunById(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                service.getDaiPerformanceFunById().done(function(data: IDaiPerformanceFunDto) {
                    self.commentDaily(data.comment);
                    self.daiPerformanceFunDto(new DaiPerformanceFunDto(data));
                    dfd.resolve();
                });
                return dfd.promise();
            }

            getMonPerformanceFunById(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                service.getMonPerformanceFunById().done(function(data: IMonPerformanceFunDto) {
                    self.commentMonthly(data.comment);
                    self.monPerformanceFunDto(new MonPerformanceFunDto(data));
                    dfd.resolve();
                });
                return dfd.promise();
            }

            saveData() {
                let self = this;
                self.formatPerformanceDto().settingUnitType(self.settingUnit());
                self.daiPerformanceFunDto().comment(self.commentDaily());
                self.monPerformanceFunDto().comment(self.commentMonthly());
                    service.updateFormatPerformanceById(ko.toJS(self.formatPerformanceDto)).done(function() {
                        service.updatetDaiPerformanceFunById(ko.toJS(self.daiPerformanceFunDto)).done(function() {
                            service.updateMonPerformanceFunById(ko.toJS(self.monPerformanceFunDto)).done(function() {
                                self.start();
                                nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                            });
                        });
                    });
            }
        }

        interface IDaiPerformanceFunDto {
            cid: string;
            comment: string;
            isCompleteConfirmOneMonth: number;
            isDisplayAgreementThirtySix: number;
            isFixClearedContent: number;
            isDisplayFlexWorker: number;
            isUpdateBreak: number;
            isSettingTimeBreak: number;
            isDayBreak: number;
            isSettingAutoTime: number;
            isUpdateEarly: number;
            isUpdateOvertime: number;
            isUpdateOvertimeWithinLegal: number;
            isFixContentAuto: number;
        }

        class DaiPerformanceFunDto {
            cid: KnockoutObservable<string>;
            comment: KnockoutObservable<string>;
            isCompleteConfirmOneMonth: KnockoutObservable<number>;
            isDisplayAgreementThirtySix: KnockoutObservable<number>;
            isFixClearedContent: KnockoutObservable<number>;
            isDisplayFlexWorker: KnockoutObservable<number>;
            isUpdateBreak: KnockoutObservable<number>;
            isSettingTimeBreak: KnockoutObservable<number>;
            isDayBreak: KnockoutObservable<number>;
            isSettingAutoTime: KnockoutObservable<number>;
            isUpdateEarly: KnockoutObservable<number>;
            isUpdateOvertime: KnockoutObservable<number>;
            isUpdateOvertimeWithinLegal: KnockoutObservable<number>;
            isFixContentAuto: KnockoutObservable<number>;

            constructor(param: IDaiPerformanceFunDto) {
                let self = this;
                self.cid = ko.observable(param.cid);
                self.comment = ko.observable(param.comment);
                self.isCompleteConfirmOneMonth = ko.observable(param.isCompleteConfirmOneMonth);
                self.isDisplayAgreementThirtySix = ko.observable(param.isDisplayAgreementThirtySix);
                self.isFixClearedContent = ko.observable(param.isFixClearedContent);
                self.isDisplayFlexWorker = ko.observable(param.isDisplayFlexWorker);
                self.isUpdateBreak = ko.observable(param.isUpdateBreak);
                self.isSettingTimeBreak = ko.observable(param.isSettingTimeBreak);
                self.isDayBreak = ko.observable(param.isDayBreak);
                self.isSettingAutoTime = ko.observable(param.isSettingAutoTime);
                self.isUpdateEarly = ko.observable(param.isUpdateEarly);
                self.isUpdateOvertime = ko.observable(param.isUpdateOvertime);
                self.isUpdateOvertimeWithinLegal = ko.observable(param.isUpdateOvertimeWithinLegal);
                self.isFixContentAuto = ko.observable(param.isFixContentAuto);
            }
        }

        interface IMonPerformanceFunDto {
            cid: string;
            comment: string;
            isConfirmDaily: number;
        }
        class MonPerformanceFunDto {
            cid: KnockoutObservable<string>;
            comment: KnockoutObservable<string>;
            isConfirmDaily: KnockoutObservable<number>;
            constructor(param: IMonPerformanceFunDto) {
                let self = this;
                self.cid = ko.observable(param.cid);
                self.comment = ko.observable(param.comment);
                self.isConfirmDaily = ko.observable(param.isConfirmDaily);
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