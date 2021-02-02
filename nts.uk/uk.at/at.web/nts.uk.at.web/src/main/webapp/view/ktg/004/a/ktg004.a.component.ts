module nts.uk.ui.ktg004.a {
    export const STORAGE_KEY_TRANSFER_DATA = "nts.uk.request.STORAGE_KEY_TRANSFER_DATA";

    const KTG004_API = {
        GET_DATA: 'screen/at/ktg004/getData'
    };

    @component({
        name: 'ktg-004-a',
        template: `<div id="contents" style="display: none">
        <div  class="parent">
            <div class="header">
                <div data-bind="ntsFormLabel: {text: name}"></div>
                <!-- ko if: detailedWorkStatusSettings -->
                    <button id= "setting-kdp004" data-bind="click: setting" class="setting">
                        <i data-bind="ntsIcon: { no: 5 }"></i>
                    </button>
                <!-- /ko -->
            </div>
            <div id="scrollTable">
                <table class="widget-table">
                    <tbody data-bind="foreach: { data: $component.itemsDisplay, as: 'row' }">
                        <tr>
                            <td>
                                <span class="row-name" data-bind="i18n: row.name"></span>
                                <div class="row-data">
                                    <!-- ko if: row.btn -->
                                        <span class="go-button">
                                            <button data-bind="click: $component.openKDW003">
                                                <i data-bind="ntsIcon: { no: 145 }"></i>
                                            </button>
                                        </span>
                                    <!-- /ko -->
                                    <span class="data" data-bind="i18n: row.text"></span>
                                </div>
                            </td>
                        </tr>
                    </tbody>                    
                </table>
                <table class="widget-table">
                    <tbody data-bind="foreach: specialHolidaysRemainings()">
                        <!-- ko if: _.find($parent.itemsSetting(), { 'item': code}).displayType -->
                            <tr>
                                <td>
                                    <span data-bind="text: name" class="row-name"></span>
                                    <div class="row-data"><span data-bind="text: specialResidualNumber" class="data"></span></div>
                                </td>
                            </tr>
                        <!-- /ko -->
                    </tbody>
                </table>	
            </div>
        </div>
    </div>
            `
    })
    export class KTG004AComponent extends ko.ViewModel {
        name = ko.observable('');
        selectedSwitch = ko.observable(1);

        itemsSetting: KnockoutObservableArray<any> = ko.observableArray([]);

        attendanceInfor = new AttendanceInfor();
        remainingNumberInfor = new RemainingNumberInfor();
        detailedWorkStatusSettings = ko.observable(true);
        specialHolidaysRemainings: KnockoutObservableArray<SpecialHolidaysRemainings> = ko.observableArray([]);


        itemsDisplay: KnockoutObservableArray<{ name: string; text: string; btn?: boolean; }> = ko.observableArray([]);

        created() {
            const vm = this;

            vm.loadData();
        }

        mounted() {
            const vm = this;

            vm.$el.classList.add('ktg-004-a');
        }

        loadData() {
            const vm = this;

            var cacheCcg008 = windows.getShared("cache");

            if (!cacheCcg008 || !cacheCcg008.currentOrNextMonth) {
                vm.selectedSwitch(1);
            }
            else {
                vm.selectedSwitch(cacheCcg008.currentOrNextMonth);
            }

            const topPageYearMonthEnum = ko.unwrap<number>(vm.selectedSwitch);

            vm.$blockui('grayout')
                .then(() => vm.$ajax("at", KTG004_API.GET_DATA, { topPageYearMonthEnum: topPageYearMonthEnum }))
                .then(function (data: ResponseData) {
                    vm.itemsDisplay([]);
                    
                    const {
                        name,
                        detailedWorkStatusSettings,
                        itemsSetting,
                        attendanceInfor,
                        remainingNumberInfor
                    } = data;

                    vm.name(name || "");
                    vm.detailedWorkStatusSettings(detailedWorkStatusSettings);

                    _.chain(itemsSetting)
                        .orderBy(['item'], ['asc'])
                        .each(({ displayType, item }) => {
                            if (displayType) {
                                switch (item) {
                                    case 21:
                                        vm.itemsDisplay.push({ name: 'KTG004_1', text: attendanceInfor.dailyErrors ? 'KTG004_16' : 'KTG004_17', btn: attendanceInfor.dailyErrors ? true : false })
                                        break;
                                    case 22:
                                        vm.itemsDisplay.push({ name: 'KTG004_2', text: attendanceInfor.overTime })
                                        break;
                                    case 23:
                                        vm.itemsDisplay.push({ name: 'KTG004_3', text: attendanceInfor.flexCarryOverTime })
                                        break;
                                    case 24:
                                        vm.itemsDisplay.push({ name: 'KTG004_5', text: attendanceInfor.nigthTime })
                                        break;
                                    case 25:
                                        vm.itemsDisplay.push({ name: 'KTG004_6', text: attendanceInfor.holidayTime })
                                        break;
                                    case 26:
                                        vm.itemsDisplay.push({ name: 'KTG004_7', text: attendanceInfor.late })
                                        break;
                                    case 27:
                                        vm.itemsDisplay.push({ name: 'KTG004_9', text: ko.unwrap(vm.remainingNumberInfor.numberAccumulatedAnnualLeave) })
                                        break;
                                    case 28:
                                        vm.itemsDisplay.push({ name: 'KTG004_10', text: ko.unwrap(vm.remainingNumberInfor.numberAccumulatedAnnualLeave) })
                                        break;
                                    case 29:
                                        vm.itemsDisplay.push({ name: 'KTG004_11', text: ko.unwrap(vm.remainingNumberInfor.numberOfSubstituteHoliday) })
                                        break;
                                    case 30:
                                        vm.itemsDisplay.push({ name: 'KTG004_12', text: ko.unwrap(vm.remainingNumberInfor.remainingHolidays) })
                                        break;
                                    case 31:
                                        vm.itemsDisplay.push({ name: 'KTG004_13', text: ko.unwrap(vm.remainingNumberInfor.nursingRemainingNumberOfChildren) })
                                        break;
                                    case 32:
                                        vm.itemsDisplay.push({ name: 'KTG004_14', text: ko.unwrap(vm.remainingNumberInfor.longTermCareRemainingNumber) })
                                        break;
                                }
                            }
                        })
                        .value();

                    vm.itemsSetting(itemsSetting);

                    vm.attendanceInfor.update(attendanceInfor);
                    vm.remainingNumberInfor.update(remainingNumberInfor);

                    const tg = _.map(remainingNumberInfor.specialHolidaysRemainings, (c) => new SpecialHolidaysRemainings(c));

                    console.log(ko.unwrap(vm.itemsDisplay));

                    vm.specialHolidaysRemainings(tg);

                    let show = _.filter(itemsSetting, { 'displayType': true });

                    if (show && show.length > 14) {
                        $("#scrollTable").addClass("scroll");

                        $(".widget-table td").each(function () {
                            this.setAttribute("style", "width: 262px");
                        });
                    }

                    if (data.name == null) {
                        $('#setting').css("top", "-7px");
                    } else {
                        $('#setting').css("top", "6px");
                    }

                    $("#contents").css("display", "");
                })
                .always(() => vm.$blockui('clear'));
        }

        public setting() {
            const vm = this;

            vm.$window
                .modal('at', '/view/ktg/004/b/index.xhtml')
                .then(() => {
                    let data = nts.uk.ui.windows.getShared("KTG004B");

                    if (data) {
                        vm.loadData();
                    }
                });
        }

        public openKDW003() {
            const vm = this;

            window.top.location = window.location.origin + '/nts.uk.at.web/view/kdw/003/a/index.xhtml';
        }

    }

    const mock = new ko.ViewModel();

    class AttendanceInfor {
        //フレックス繰越時間
        flexCarryOverTime = ko.observable(mock.$i18n('KTG004_4', ['00:00']));
        //フレックス時間
        flexTime = ko.observable('00:00');
        //休出時間
        holidayTime = ko.observable('00:00');
        //残業時間
        overTime = ko.observable('00:00');
        //就業時間外深夜時間
        nigthTime = ko.observable('00:00');
        //早退回数/遅刻回数
        lateEarly = ko.observable(mock.$i18n('KTG004_8', ['0', '0']));
        //日別実績のエラー有無
        dailyErrors = ko.observable(false);

        update(param: Attendance) {
            const model = this;

            if (param) {
                model.flexCarryOverTime(mock.$i18n('KTG004_4', [model.convertToTime(param.flexCarryOverTime)]));
                model.flexTime(model.convertToTime(param.flexTime));
                model.holidayTime(model.convertToTime(param.holidayTime));
                model.overTime(model.convertToTime(param.overTime));
                model.nigthTime(model.convertToTime(param.nigthTime));
                model.lateEarly(mock.$i18n('KTG004_8', [param.late, param.early]));
                model.dailyErrors(param.dailyErrors);
            }
        }

        convertToTime(data: string | number): string {
            if (['0', 0, null, undefined, ''].indexOf(data) > -1) {
                return '00:00';
            } else {
                const numb = Number(data);
                const negative = numb < 0;
                const hour = Math.floor(numb / 60);
                const minute = Math.floor(numb % 60);

                return `${negative ? '-' : ''}${Math.abs(hour)}:${_.padStart(Math.abs(minute) + '', 2, '0')}`;
            }
        }
    }

    class RemainingNumberInfor {
        //年休残数
        numberOfAnnualLeaveRemain = ko.observable(mock.$i18n('KTG004_15', ['0']));
        //積立年休残日数
        numberAccumulatedAnnualLeave = ko.observable(mock.$i18n('KTG004_15', ['0']));
        //代休残数
        numberOfSubstituteHoliday = ko.observable(mock.$i18n('KTG004_15', ['0']));
        //振休残日数
        remainingHolidays = ko.observable(mock.$i18n('KTG004_15', ['0']));
        //子の看護残数
        nursingRemainingNumberOfChildren = ko.observable(mock.$i18n('KTG004_15', ['0']));
        //介護残数
        longTermCareRemainingNumber = ko.observable(mock.$i18n('KTG004_15', ['0']));

        constructor() { }

        update(param: any) {
            if (param) {
                let self = this;
                self.numberOfAnnualLeaveRemain(mock.$i18n('KTG004_15', [param.numberOfAnnualLeaveRemain.day]));
                self.numberAccumulatedAnnualLeave(mock.$i18n('KTG004_15', [param.numberAccumulatedAnnualLeave]));
                self.numberOfSubstituteHoliday(mock.$i18n('KTG004_15', [param.numberOfSubstituteHoliday.day]));
                self.remainingHolidays(mock.$i18n('KTG004_15', [param.remainingHolidays]));
                self.nursingRemainingNumberOfChildren(mock.$i18n('KTG004_15', [param.nursingRemainingNumberOfChildren.day]));
                self.longTermCareRemainingNumber(mock.$i18n('KTG004_15', [param.longTermCareRemainingNumber.day]));
            }
        }
    }

    class SpecialHolidaysRemainings {
        //特別休暇コード
        code: number;
        //特別休暇名称
        name: string;
        //特休残数
        specialResidualNumber: string;
        constructor(param: any) {
            if (param) {
                let self = this;
                self.code = param.code;
                self.name = param.name;
                self.specialResidualNumber = mock.$i18n('KTG004_15', [param.specialResidualNumber.day]);
            }
        }
    }

    interface ResponseData {
        attendanceInfor: Attendance;
        closingDisplay: Closing;
        closingThisMonth: Closing;
        closureId: number;
        detailedWorkStatusSettings: boolean;
        itemsSetting: ItemSetting[];
        name: any | null;
        nextMonthClosingInformation: any | null;

        remainingNumberInfor: {
            longTermCareRemainingNumber: NumberOfShift;
            numberAccumulatedAnnualLeave: number;
            numberOfAnnualLeaveRemain: NumberOfShift;
            numberOfSubstituteHoliday: NumberOfShift;
            nursingRemainingNumberOfChildren: NumberOfShift;
            remainingHolidays: number;
            specialHolidaysRemainings: {
                code: number;
                name: string;
                specialResidualNumber: NumberOfShift;
            }[];
        };
    }

    interface ItemSetting {
        displayType: boolean;
        item: 21 | 22 | 23 | 24 | 25 | 26 | 27 | 28 | 29 | 30 | 31 | 32;
        name: string;
    }

    interface Closing {
        endDate: string;
        processingYm: number;
        startDate: string;
    }

    interface NumberOfShift {
        day: number;
        time: string;
    }

    interface Attendance {
        dailyErrors: boolean;
        early: string;
        flexCarryOverTime: string;
        flexTime: string;
        holidayTime: string
        late: string;
        nigthTime: string;
        overTime: string;
    }
}


/*<tbody>
                            <!-- ko if: _.find(itemsSetting(), { 'item': 21 }).displayType -->
                            <tr>
                                <td>
                                    <span class="row-name" data-bind="i18n: 'KTG004_1'"></span>
                                    <div class="row-data">
                                        <!-- ko if: attendanceInfor.dailyErrors -->
                                            <span class="go-button">
                                                <button data-bind="click: openKDW003">
                                                    <i data-bind="ntsIcon: { no: 145 }"></i>
                                                </button>
                                            </span>
                                        <!-- /ko -->
                                        <span data-bind="i18n: attendanceInfor.dailyErrors() ? 'KTG004_16' : 'KTG004_17'" class="data"></span>
                                    </div>
                                </td>
                            </tr>
                            <!-- /ko -->
                            <!-- ko if: _.find(itemsSetting(), { 'item': 22 }).displayType -->
                            <tr>
                                <td>
                                    <span class="row-name" data-bind="i18n: 'KTG004_2'"></span>
                                    <div class="row-data"><span data-bind="text: attendanceInfor.overTime" class="data"></span></div>
                                </td>
                            </tr>
                            <!-- /ko -->
                            <!-- ko if: _.find(itemsSetting(), { 'item': 23 }).displayType -->
                            <tr>
                                <td>
                                    <span class="row-name" data-bind="i18n: 'KTG004_3'"></span>
                                    <div class="row-data">
                                        <span data-bind="text: attendanceInfor.flexTime" class="data"></span>
                                        <span data-bind="text: attendanceInfor.flexCarryOverTime" class="data"></span>
                                    </div>
                                </td>
                            </tr>
                            <!-- /ko -->
                            <!-- ko if: _.find(itemsSetting(), { 'item': 24 }).displayType -->
                            <tr>
                                <td>
                                    <span class="row-name" data-bind="i18n: 'KTG004_5'"></span>
                                    <div class="row-data"><span data-bind="text: attendanceInfor.nigthTime" class="data"></span></div>
                                </td>
                            </tr>
                            <!-- /ko -->
                            <!-- ko if: _.find(itemsSetting(), { 'item': 25 }).displayType -->
                            <tr>
                                <td>
                                    <span class="row-name" data-bind=""i18n: 'KTG004_6'></span>
                                    <div class="row-data"><span data-bind="text: attendanceInfor.holidayTime" class="data"></span></div>
                                </td>
                            </tr>
                            <!-- /ko -->
                            <!-- ko if: _.find(itemsSetting(), { 'item': 26 }).displayType -->
                            <tr>
                                <td>
                                    <span class="row-name" data-bind="i18n: 'KTG004_7'"></span>
                                    <div class="row-data"><span data-bind="text: attendanceInfor.lateEarly" class="data"></span></div>
                                </td>
                            </tr>
                            <!-- /ko -->
                            <!-- ko if: _.find(itemsSetting(), { 'item': 27 }).displayType -->
                            <tr>
                                <td>
                                    <span class="row-name" data-bind="i18n: 'KTG004_9'"></span>
                                    <div class="row-data"><span data-bind="text: remainingNumberInfor.numberOfAnnualLeaveRemain" class="data"></span></div>
                                </td>
                            </tr>
                            <!-- /ko -->
                            <!-- ko if: _.find(itemsSetting(), { 'item': 28 }).displayType -->
                            <tr>
                                <td>
                                    <span class="row-name" data-bind:"i18n: 'KTG004_10'"></span>
                                    <div class="row-data"><span data-bind="text: remainingNumberInfor.numberAccumulatedAnnualLeave" class="data"></span></div>
                                </td>
                            </tr>
                            <!-- /ko -->
                            <!-- ko if: _.find(itemsSetting(), { 'item': 29 }).displayType -->
                            <tr>
                                <td>
                                    <span class="row-name" data-bind="i18n: 'KTG004_11'"></span>
                                    <div class="row-data"><span data-bind="text: remainingNumberInfor.numberOfSubstituteHoliday" class="data"></span></div>
                                </td>
                            </tr>
                            <!-- /ko -->
                            <!-- ko if: _.find(itemsSetting(), { 'item': 30 }).displayType -->
                            <tr>
                                <td>
                                    <span class="row-name" data-bind="i18n: 'KTG004_12'"></span>
                                    <div class="row-data"><span data-bind="text: remainingNumberInfor.remainingHolidays" class="data"></span></div>
                                </td>
                            </tr>
                            <!-- /ko -->
                            <!-- ko if: _.find(itemsSetting(), { 'item': 31 }).displayType -->
                            <tr>
                                <td>
                                    <span class="row-name" data-bind="i18n: 'KTG004_13'"></span>
                                    <div class="row-data"><span data-bind="text: remainingNumberInfor.nursingRemainingNumberOfChildren" class="data"></span></div>
                                </td>
                            </tr>
                            <!-- /ko -->
                            <!-- ko if: _.find(itemsSetting(), { 'item': 32 }).displayType -->
                            <tr>
                                <td>
                                    <span class="row-name" data-bind="i18n: 'KTG004_14'"></span>
                                    <div class="row-data"><span data-bind="text: remainingNumberInfor.longTermCareRemainingNumber" class="data"></span></div>
                                </td>
                            </tr>
                            <!-- /ko -->
                    </tbody>*/