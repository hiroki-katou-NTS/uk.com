module nts.uk.at.view.kdl036.a.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    export class ScreenModel {
        legendOptions: any;
        checked: KnockoutObservable<boolean> = ko.observable(false);
        employeeId: string = "";
        startDate: KnockoutObservable<string> = ko.observable(null);
        endDate: KnockoutObservable<string> = ko.observable(null);
        daysUnit: number = 0;
        targetSelectionAtr: TargetSelectionAtr = null;
        actualContentDisplayList: Array<any> = [];
        managementData: Array<HolidayWorkSubHolidayLinkingMng> = [];

        substituteHolidayList: KnockoutObservableArray<string> = ko.observableArray([]);
        substituteWorkInfoList: KnockoutObservableArray<HolidayWorkInfo> = ko.observableArray([]);
        requiredNumberOfDays: KnockoutObservable<number>;

        displayedPeriod: KnockoutObservable<string>;
        displayedRequiredNumberOfDays: KnockoutObservable<string>;

        constructor() {
            let self = this;

            const params: Kdl036Params = getShared("KDL036_PARAMS");
            if (params) {
                self.employeeId = params.employeeId;
                self.startDate(params.period.startDate);
                self.endDate(params.period.endDate);
                self.daysUnit = params.daysUnit;
                self.targetSelectionAtr = params.targetSelectionAtr;
                self.actualContentDisplayList = params.actualContentDisplayList || [];
                self.managementData = params.managementData || [];
            }

            self.requiredNumberOfDays = ko.computed(() => {
                const required = self.substituteHolidayList().length * self.daysUnit;
                let selected = 0;
                self.substituteWorkInfoList().forEach(info => {
                    if (info.checked()) selected += info.usedNumber;
                });
                return required - selected;
            }, self);
            self.displayedPeriod = ko.computed(() => {
                if (self.startDate() == self.endDate())
                    return self.startDate();
                else
                    return getText("KDL036_12", [self.startDate(), self.endDate()]);
            }, self);
            self.displayedRequiredNumberOfDays = ko.computed(() => {
                return getText("KDL036_4", [Math.max(self.requiredNumberOfDays(), 0)]);
            }, self);

            self.legendOptions = {
                items: [
                    {labelText: getText("KDL036_6")},
                    {labelText: getText("KDL036_7")}
                ],
                template : '<div class="label">#{labelText}</div>'
            };
            $("#fixed-table").ntsFixedTable({ height: 314 });
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            block.invisible();
            const initParams = {
                employeeId: self.employeeId,
                startDate: new Date(self.startDate()).toISOString(),
                endDate: new Date(self.endDate()).toISOString(),
                daysUnit: self.daysUnit,
                targetSelectionAtr: self.targetSelectionAtr,
                actualContentDisplayList: _.cloneDeep(self.actualContentDisplayList),
                managementData: _.cloneDeep(self.managementData)
            };
            initParams.managementData.forEach(d => {
                d.outbreakDay = new Date(d.outbreakDay).toISOString();
                d.dateOfUse = new Date(d.dateOfUse).toISOString();
            });
            service.initScreen(initParams).done(function(result: ParamsData) {
                self.daysUnit = result.daysUnit;
                self.targetSelectionAtr = result.targetSelectionAtr;
                self.substituteHolidayList(result.substituteHolidayList);
                self.substituteWorkInfoList(result.holidayWorkInfoList.map(info => {
                    const tmp = _.filter(self.managementData, i => i.outbreakDay == info.holidayWorkDate);
                    const totalUsed = _.sum(tmp.map(i => i.dayNumberUsed));
                    return new HolidayWorkInfo(!_.isEmpty(tmp), info, self.requiredNumberOfDays, self.startDate(), totalUsed);
                }));
                dfd.resolve();
            }).fail(function(error: any) {
                dialog.alert(error);
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }

        confirmLinking() {
            const self = this;
            if (self.requiredNumberOfDays() > 0) {
                dialog.alert({messageId: "Msg_1759"});
                return;
            }

            const data: ParamsData = {
                employeeId: self.employeeId,
                daysUnit: self.daysUnit,
                targetSelectionAtr: self.targetSelectionAtr,
                substituteHolidayList: self.substituteHolidayList().map(i => new Date(i).toISOString()),
                holidayWorkInfoList: self.substituteWorkInfoList().filter((i: HolidayWorkInfo) => i.checked()).map((i: HolidayWorkInfo) => i.toData())
            };
            data.holidayWorkInfoList.forEach(i => {
                i.expirationDate = new Date(i.expirationDate).toISOString();
                i.holidayWorkDate = new Date(i.holidayWorkDate).toISOString();
            });
            block.invisible();
            service.associate(data).done((mngData: Array<HolidayWorkSubHolidayLinkingMng>) => {
                setShared("KDL036_RESULT", mngData);
                nts.uk.ui.windows.close();
            }).fail((error: any) => {
                dialog.alert(error);
            }).always(() => {
                block.clear();
            });
        }

        closeDialog() {
            nts.uk.ui.windows.close();
        }

    }

    interface Kdl036Params {
        // ??????ID
        employeeId: string;

        // ????????????
        period: DatePeriod;

        // ???????????????1.0 / 0.5???
        daysUnit: number;

        // ??????????????????????????? / ?????? / ??????
        targetSelectionAtr: TargetSelectionAtr;

        // List<????????????????????????>
        actualContentDisplayList: Array<any>;

        // List<???????????????????????????>
        managementData: Array<HolidayWorkSubHolidayLinkingMng>;
    }

    interface DatePeriod {
        startDate: string; // YYYY/MM/DD
        endDate: string; // YYYY/MM/DD
    }

    enum TargetSelectionAtr {
        // ??????
        AUTOMATIC = 0,
        // ??????
        REQUEST = 1,
        // ??????
        MANUAL = 2
    }

    enum DataType {
        ACTUAL = 0,
        APPLICATION_OR_SCHEDULE = 1
    }

    interface ParamsData {
        employeeId: string;

        // ????????????
        daysUnit: number;

        // ??????????????????
        substituteHolidayList: Array<string>;

        // ??????????????????
        targetSelectionAtr: TargetSelectionAtr;

        // List<????????????????????????>
        holidayWorkInfoList: Array<IHolidayWorkInfo>;
    }

    interface IHolidayWorkInfo {
        dataType: DataType;
        expirationDate: string;
        expiringThisMonth: boolean;
        remainingNumber: number;
        holidayWorkDate: string;
    }

    class HolidayWorkInfo {
        checked: KnockoutObservable<boolean>;
        enabled: KnockoutObservable<boolean>;
        holidayWorkDate: string;
        displayedHolidayWorkDate: string;
        remainingNumber: number;
        displayedRemainingNumber: string;
        expiredDate: string;
        displayedExpiredDate: string;
        dataType: DataType;
        expiringThisMonth: boolean;
        usedNumber: number;

        constructor(checked: boolean, params: IHolidayWorkInfo, requiredNumberOfDays: KnockoutObservable<number>, startDate: string, usedNumber?: number) {
            this.enabled = ko.observable(new Date(params.expirationDate).getTime() >= new Date(startDate).getTime());
            this.checked = ko.observable(checked);
            this.holidayWorkDate = params.holidayWorkDate;
            this.displayedHolidayWorkDate = (params.expiringThisMonth ? "???" : "")
                + (params.dataType == DataType.APPLICATION_OR_SCHEDULE ? "???" : "")
                + nts.uk.time.formatPattern(params.holidayWorkDate, "YYYY/MM/DD", "YYYY/MM/DD(ddd)")
                + (params.dataType == DataType.APPLICATION_OR_SCHEDULE ? "???" : "")
                + (params.expiringThisMonth ? "???" : "");
            this.remainingNumber = params.remainingNumber;
            this.displayedRemainingNumber = getText("KDL036_4", [nts.uk.ntsNumber.formatNumber(this.remainingNumber, {decimallength: 1})]);
            this.expiredDate = params.expirationDate;
            this.displayedExpiredDate = nts.uk.time.formatPattern(params.expirationDate, "YYYY/MM/DD", "YYYY/MM/DD(ddd)");
            this.dataType = params.dataType;
            this.expiringThisMonth = params.expiringThisMonth;
            this.usedNumber = usedNumber || 0;
            this.checked.subscribe(value => {
                if (value) {
                    if (requiredNumberOfDays() <= 0) {
                        dialog.alert({messageId: "Msg_1758"}).then(() => {
                            this.checked(false);
                        });
                    } else {
                        this.usedNumber = Math.min(requiredNumberOfDays(), this.remainingNumber);
                    }
                } else {
                    this.usedNumber = 0;
                }
            });
        }

        toData(): IHolidayWorkInfo {
            return {
                dataType: this.dataType,
                expirationDate: this.expiredDate,
                expiringThisMonth: this.expiringThisMonth,
                remainingNumber: this.usedNumber,
                holidayWorkDate: this.holidayWorkDate
            }
        }
    }
    
    interface HolidayWorkSubHolidayLinkingMng {
        // ??????ID
        employeeId: string;

        // ?????????????????????????????? . ?????????
        outbreakDay: string;

        // ?????????????????????????????? . ?????????
        dateOfUse: string;

        // ?????????????????????????????? . ????????????
        dayNumberUsed: number;

        // ?????????????????????????????? . ??????????????????
        targetSelectionAtr: number;
    }
}