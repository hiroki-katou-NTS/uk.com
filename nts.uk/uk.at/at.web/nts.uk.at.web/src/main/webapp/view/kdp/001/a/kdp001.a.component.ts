module nts.uk.ui.kdp001.a {

    interface TimeClock {
        tick: number;
        now: KnockoutObservable<Date>;
        style: KnockoutObservable<string>;
        displayTime: KnockoutObservable<number>;
    }

    const initTime = (): TimeClock => ({
        tick: -1,
        now: ko.observable(new Date()),
        style: ko.observable(''),
        displayTime: ko.observable(10)
    });
    const showMessage = (used: 0 | 1 | 2 | 3) => {
        switch (used) {
            case 0:
                return '';
            case 1:
                return 'Msg_1644';
            case 2:
                return 'Msg_1645';
            case 3:
                return 'Msg_1619';
        }
    };
    const $textAlign = (button: ButtonTypeAtr) => {
        const { GOING_TO_WORK, RESERVATION_SYSTEM, WORKING_OUT } = ButtonTypeAtr;

        switch (button) {
            default:
                return 'text-center';
            case GOING_TO_WORK:
            case RESERVATION_SYSTEM:
                return 'text-left';
            case WORKING_OUT:
                return 'text-right';
        }
    };

    const REST_API = {
        getEmployeeStampData: '/at/record/stamp/employment_system/get_employee_stamp_data',
        confirmUseOfStampInput: '/at/record/stamp/employment_system/confirm_use_of_stamp_input',
        registerStampInput: '/at/record/stamp/employment_system/register_stamp_input',
        getSettingStampInput: '/at/record/stamp/employment_system/get_setting_stamp_input',
        getOmissionContents: '/at/record/stamp/employment_system/get_omission_contents',
        getStampToSuppress: '/at/record/stamp/employment_system/get_stamp_to_suppress'
    };

    //個人
    const MODE_PERSON = 1;
    const DEFAULT_PAGE_NO = 1;
    const STAMP_MEANS_PORTAL = 4;
    // const DEFAULT_GRAY = '#E8E9EB';127D09
    const DEFAULT_GRAY = '#127D09';
    const D_FORMAT = 'YYYY/MM/DD HH:mm:ss';

    @component({
        name: 'kdp-001-frame',
        template: `<div data-bind="widget-content: 200, src: '/nts.uk.at.web/view/kdp/001/a/index.xhtml?mode=b'"></div>`
    })
    export class ViewModel extends ko.ViewModel {

    }

    @component({
        name: 'kdp-001-a',
        template: `
            <div class="kdp-001-a widget-title">
                <span class="text-time" data-bind="i18n: 'KDP001_5'"></span>
                <div class="date" data-bind="date: $component.time.now, format: 'YYYY/MM/DD(ddd)', attr: { style: $component.time.style }"></div>
                <div>
                    <span class="hours-minutes" data-bind="date: $component.time.now, format: 'HH:mm',attr: { style: $component.time.style }"></span>
                    <span class="seconds" data-bind="date: $component.time.now, format: ':ss', attr: { style: $component.time.style }"></span>
                </div>
                <div class="button-link">
                    <a href="#" data-bind="i18n: 'KDP001_4'"></a>
                </div>
            </div>
            <div class="kdp-001-a kdp-001-a-msg"data-bind="
                    css: { 
                        'hidden': !$component.message.display()
                    },
                    text: $component.message.display
                "></div>
            <div class="kdp-001-a kdp-001-a-btn text-center" data-bind="
                    foreach: { data: $component.buttons, as: 'btn' },
                    css: { 
                        'hidden': !!$component.message.display()
                    }
                ">
                <button data-bind="attr: { style: btn.style }, click: function() { $component.stamp(btn); }">
                    <!-- ko if: btn.buttonPositionNo == 1 -->
                        <!-- ko if: btn.buttonName.length < 6 -->
                            <i data-bind="ntsIcon: { no: 205, width: 100, height: 100 }"></i>
                        <!-- /ko -->
                        <!-- ko if: btn.buttonName.length == 6 -->
                            <i data-bind="ntsIcon: { no: 205, width: 95, height: 95 }"></i>
                        <!-- /ko -->
                        <!-- ko if: btn.buttonName.length == 7 -->
                            <i data-bind="ntsIcon: { no: 205, width: 90, height: 90 }"></i>
                        <!-- /ko -->
                        <!-- ko if: btn.buttonName.length == 8 -->
                            <i data-bind="ntsIcon: { no: 205, width: 85, height: 85 }"></i>
                        <!-- /ko -->
                        <!-- ko if: btn.buttonName.length == 9 -->
                            <i data-bind="ntsIcon: { no: 205, width: 80, height: 80 }"></i>
                        <!-- /ko -->
                        <!-- ko if: btn.buttonName.length == 10 -->
                            <i data-bind="ntsIcon: { no: 205, width: 85, height: 85 }"></i>
                        <!-- /ko -->
                    <!-- /ko -->

                    <!-- ko if: btn.buttonPositionNo == 2 -->
                        <!-- ko if: btn.buttonName.length < 6 -->
                            <i data-bind="ntsIcon: { no: 209, width: 100, height: 100 }"></i>
                        <!-- /ko -->
                        <!-- ko if: btn.buttonName.length == 6 -->
                            <i data-bind="ntsIcon: { no: 209, width: 95, height: 95 }"></i>
                        <!-- /ko -->
                        <!-- ko if: btn.buttonName.length == 7 -->
                            <i data-bind="ntsIcon: { no: 209, width: 90, height: 90 }"></i>
                        <!-- /ko -->
                        <!-- ko if: btn.buttonName.length == 8 -->
                            <i data-bind="ntsIcon: { no: 209, width: 85, height: 85 }"></i>
                        <!-- /ko -->
                        <!-- ko if: btn.buttonName.length == 9 -->
                            <i data-bind="ntsIcon: { no: 209, width: 80, height: 80 }"></i>
                        <!-- /ko -->
                        <!-- ko if: btn.buttonName.length == 10 -->
                            <i data-bind="ntsIcon: { no: 209, width: 85, height: 85 }"></i>
                        <!-- /ko -->
                    <!-- /ko -->

                    <!-- ko if: btn.buttonPositionNo == 3 -->
                        <i data-bind="ntsIcon: { no: 212, width: 50, height: 50 }"></i>
                    <!-- /ko -->

                    <!-- ko if: btn.buttonPositionNo == 4 -->
                        <i data-bind="ntsIcon: { no: 213, width: 50, height: 50 }"></i>
                    <!-- /ko -->

                    <!-- ko if: btn.buttonPositionNo == 1 || btn.buttonPositionNo == 2 -->
                        <!-- ko if: btn.buttonName.length < 6 -->
                            <div class="btn-start-1"
                                data-bind="attr: { style: btn.style }, text: btn.buttonName"></div>
                        <!-- /ko -->
                        <!-- ko if: btn.buttonName.length == 6 -->
                            <div class="btn-start-2"
                                data-bind="attr: { style: btn.style }, text: btn.buttonName"></div>
                        <!-- /ko -->
                        <!-- ko if: btn.buttonName.length == 7 -->
                            <div class="btn-start-3"
                                data-bind="attr: { style: btn.style }, text: btn.buttonName"></div>
                        <!-- /ko -->
                        <!-- ko if: btn.buttonName.length == 8 -->
                            <div class="btn-start-4"
                                data-bind="attr: { style: btn.style }, text: btn.buttonName"></div>
                        <!-- /ko -->
                        <!-- ko if: btn.buttonName.length == 9 -->
                            <div class="btn-start-5"
                                data-bind="attr: { style: btn.style }, text: btn.buttonName"></div>
                        <!-- /ko -->
                        <!-- ko if: btn.buttonName.length == 10 -->
                            <div class="btn-start-6"
                                data-bind="attr: { style: btn.style }, text: btn.buttonName"></div>
                        <!-- /ko -->
                    <!-- /ko -->

                    <!-- ko if: btn.buttonPositionNo == 3 || btn.buttonPositionNo == 4 -->
                        <div class="btn-start-1"
                            data-bind="attr: { style: btn.style }, text: btn.buttonName"></div>
                    <!-- /ko -->
                </button>
            </div>
            <div class="kdp-001-a" data-bind="
                    widget-content: 143,
                    ">
                <div>
                    <table>
                        <colgroup>
                            <col width="25%" />
                            <col width="25%" />
                            <col width="50%" />
                        </colgroup>
                        <tbody data-bind="foreach: { data: $component.stamps, as: 'stm' }">
                            <tr>
                                <td data-bind="css: stm.forceColor">
                                    <span data-bind="date: stm.date, format: 'MM/DD(ddd)'"></span>
                                </td>
                                <td>
                                    <span class="left-content" data-bind="text: stm.stampHow"></span>
                                    <span data-bind="date: stm.date, format: 'HH:mm'"></span>
                                </td>
                                <td data-bind="css: stm.textAlign">
                                    <span data-bind="text: stm.stampArt"></span>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <style rel="stylesheet">
                .kdp-001-a.widget-title {
                    margin: 1px;
                    border-radius: 2px;
                    border-left-width: 0px;
                    border-bottom-width: 0px;
                    text-align: center;
                }
                .kdp-001-a .text-left {
                    text-align: left;
                }
                .kdp-001-a .text-right {
                    text-align: right;
                }
                .kdp-001-a.text-center,
                .kdp-001-a .text-center {
                    text-align: center;
                }
                .kdp-001-a.widget-title th {
                    font-size: 16px;
                }
                .kdp-001-a.kdp-001-a-msg {
                    padding: 10px;
                }
                .kdp-001-a.kdp-001-a-btn{
                    width: 450px;
                    margin: auto;
                }
                .kdp-001-a.kdp-001-a-btn button {
                    width: 222px;
                    height: 200px;
                    border-radius: 5px;
                    box-shadow: 2px 2px 6px #808080;
                }
                .kdp-001-a.kdp-001-a-btn .btn-start-1 {
                    font-size: 40px;
                }
                .kdp-001-a.kdp-001-a-btn .btn-start-2 {
                    font-size: 34px;
                }
                .kdp-001-a.kdp-001-a-btn .btn-start-3 {
                    font-size: 29px;
                }
                .kdp-001-a.kdp-001-a-btn .btn-start-4 {
                    font-size: 25px;
                }
                .kdp-001-a.kdp-001-a-btn .btn-start-5 {
                    font-size: 22px;
                }
                .kdp-001-a.kdp-001-a-btn .btn-start-6 {
                    font-size: 20px;
                }
                .kdp-001-a.kdp-001-a-btn1 .btn-end {
                    font-size: 20px;
                }
                .kdp-001-a.kdp-001-a-btn button:nth-child(3) {
                    margin-top: 5px;
                    width: 222px;
                    height: 100px;
                }
                .kdp-001-a.kdp-001-a-btn button:nth-child(3) div {
                    font-size: 20px;
                }
                .kdp-001-a.kdp-001-a-btn button:nth-child(4) {
                    margin-top: 5px;
                    width: 222px;
                    height: 100px;
                }
                .kdp-001-a.kdp-001-a-btn button:nth-child(4) div {
                    font-size: 20px;
                }
                .kdp-001-a.widget-content {
                    border: 1px solid #b1b1b1;
                    max-height: 143px;
                    overflow-y: scroll;
                    width: 450px;
                    margin: 5px auto;
                    border-radius: 3px;
                }
                .kdp-001-a.widget-content table {
                    width: 100%;
                }
                .kdp-001-a.widget-content table td {
                    overflow: hidden;
                    position: relative;
                }
                .kdp-001-a.widget-content table td.sunday {
                    color: #FF0000;
                }
                .kdp-001-a.widget-content table td.saturday {
                    color: #0000FF;
                }
                .kdp-001-a.widget-content table td:not(:first-child):before {
                    content: '';
                    display: block;
                    width: 1px;
                    height: 16px;
                    border-left: 1px solid #ccc;
                    position: absolute;
                    top: calc(50% - 8px);
                    left: 0;
                }
                .kdp-001-a.widget-content table td {
                    padding: 7px 10px;
                }
                .kdp-001-a.widget-content table td:not(:last-child) {
                    text-align: center;
                }                
                .kdp-001-a.widget-title .text-time {
                    position: absolute;
                    font-size: 60px;
                    color: #E5F7F9;
                    top: -10px;
                    left: calc(100vw /2 + 35px);
                }
                .kdp-001-a.widget-title .date {
                    margin-right: 65px;
                    background-color: white !important;
                }
                .kdp-001-a.widget-title .hours-minutes {
                    box-sizing: border-box;
                    font-size: 50px;
                    background-color: white !important;
                }
                .kdp-001-a.widget-title .seconds {
                    box-sizing: border-box;
                    font-size: 30px;
                    background-color: white !important;
                }
                .kdp-001-a.widget-title .button-link {
                    text-align: center;
                    padding-left: 357px;
                    padding-bottom: 5px;
                }
                .kdp-001-a .left-content{
                    margin-right: 10px;
                }
            </style>
        `
    })
    export class KDP001WidgetComponent extends ko.ViewModel {
        time: TimeClock = initTime();

        show!: KnockoutComputed<boolean>;
        widget!: KnockoutComputed<string>;

        stamps: KnockoutObservableArray<Partial<StampRecord>> = ko.observableArray([]);
        buttons: KnockoutObservableArray<Partial<ButtonSetting>> = ko.observableArray([]);

        stampDisplay: KnockoutObservable<StampResultDisplay | null> = ko.observable(null);

        message: {
            data: KnockoutObservable<MessageData>;
            display: KnockoutComputed<string>;
        } = {
                data: ko.observable({ messageId: '', messageParams: [] }),
                display: null
            };

        constructor(private mode: 'a' | 'b' | 'c' | 'd' | KnockoutObservable<'a' | 'b' | 'c' | 'd'> = 'a') {
            super();

            const vm = this;

            vm.show = ko.computed({
                read: () => {
                    switch (ko.unwrap<'a' | 'b' | 'c' | 'd'>(vm.mode)) {
                        case 'a':
                        case 'b':
                            return true;
                        case 'c':
                        case 'd':
                            return false;
                    }
                }
            });

            vm.widget = ko.computed({
                read: () => {
                    switch (ko.unwrap<'a' | 'b' | 'c' | 'd'>(vm.mode)) {
                        case 'a':
                            return 'KDP001A_A';
                        case 'b':
                            return 'KDP001A_B';
                        case 'c':
                            return 'KDP001A_C';
                        case 'd':
                            return 'KDP001A_D';
                    }
                }
            });

            vm.message.display = ko.computed({
                read: () => {
                    const msg = ko.unwrap<MessageData>(vm.message.data);

                    if (!msg || !msg.messageId) {
                        return '';
                    }

                    return vm.$i18n.message(msg.messageId, msg.messageParams.map(vm.$i18n));
                }
            });

            if (ko.isObservable(mode)) {
                mode.subscribe(() => vm.loadData());
            }

            vm.loadData();
        }

        loadData() {
            const vm = this;
            const { mode } = vm;

            // get server time from cache
            vm.time.tick = setInterval(() => vm.time.now(vm.$date.now()), 500);

            const STAMP_INPUT = vm.$ajax('at', REST_API.getSettingStampInput);
            const CONFIRM_USE = vm.$ajax('at', REST_API.confirmUseOfStampInput, { stampMeans: STAMP_MEANS_PORTAL });
            const EMPLOYEE_DATA = vm.$ajax('at', REST_API.getEmployeeStampData);
            const STAMP_PRESS = vm.$ajax('at', REST_API.getStampToSuppress);

            vm
                .$blockui('invisibleView')
                .then(() => $.when(STAMP_INPUT, CONFIRM_USE, EMPLOYEE_DATA, STAMP_PRESS))
                .then((stamp: SettingStampInput, confirm: ConfirmSetting, employees: Employee[], suppress: SuppressSetting) => {
                    const { used } = confirm;
                    const messageId = showMessage(used);
                    const { portalStampSettings, stampResultDisplayDto } = stamp;

                    // show stamp data
                    vm.stampData(employees);

                    // show message or data
                    vm.message.data({ messageId, messageParams: ['KDP001_1'] });

                    vm.stampDisplay(stampResultDisplayDto);

                    if (portalStampSettings) {
                        const screen = ko.unwrap<string>(mode);
                        const { buttonSettings, displaySettingsStampScreen } = portalStampSettings;

                        const { resultDisplayTime, settingDateTimeColor, serverCorrectionInterval } = displaySettingsStampScreen || {};
                        const { backgroundColor, textColor } = settingDateTimeColor || {};

                        // set time for request update server time
                        vm.$date.interval((serverCorrectionInterval || 1) * 60000);

                        vm.time.displayTime(resultDisplayTime || 10);
                        vm.time.style(`color: ${textColor || '#000'}; background-color: ${backgroundColor || '#fff'};`);

                        const btns = _
                            .chain(buttonSettings)
                            .uniqBy('buttonPositionNo')
                            .filter(({ buttonPositionNo }) => 1 <= buttonPositionNo && buttonPositionNo <= 4)
                            .sortBy(['buttonPositionNo'])
                            // .filter((_: any, index: number) => {
                            //     if (['b', 'c'].indexOf(screen) > -1) {
                            //         return true;
                            //     }

                            //     return index <= 1;
                            // })
                            .map((btn: ButtonSetting) => {
                                const { buttonPositionNo, buttonDisSet } = btn;
                                const { backGroundColor, buttonNameSet } = buttonDisSet;
                                const { textColor, buttonName } = buttonNameSet;
                                const { departure, goOut, goingToWork, turnBack } = suppress;
                                const backgroundColor = (() => {
                                    switch (buttonPositionNo) {
                                        case 1:
                                            return goingToWork ? DEFAULT_GRAY : backGroundColor;
                                        case 2:
                                            return departure ? DEFAULT_GRAY : backGroundColor;
                                        case 3:
                                            return goOut ? DEFAULT_GRAY : backGroundColor;
                                        case 4:
                                            return turnBack ? DEFAULT_GRAY : backGroundColor;
                                    }
                                })();

                                return {
                                    ...btn,
                                    buttonName,
                                    style: `color: ${textColor}; background-color: ${backgroundColor};`
                                };
                            })
                            .value();
                            console.log(btns);
                        vm.buttons(btns);
                    }
                })
                .then(() => {
                    vm.$nextTick(() => {
                        $(vm.$el)
                            .find('[data-bind]')
                            .removeAttr('data-bind');
                    });
                })
                .always(() => vm.$blockui('clearView'));
        }

        stamp(btn: ButtonSetting) {
            console.log(btn); 
            const vm = this;
            const { time, stampDisplay } = vm;
            const date = ko.unwrap<Date>(time.now);
            const displayTime = ko.unwrap<number>(time.displayTime);
            const { buttonPositionNo } = btn;
            const cardNumberSupport: any = null;
            const workLocationCD: any = null;
            const workTimeCode: any = null;
            const overtimeDeclaration: any = null;
            const { employeeId, employeeCode } = vm.$user;
            const { notUseAttr, displayItemId } = ko.unwrap(stampDisplay) || {};
            const openDialogB = () => {
                return $
                    .when(
                        vm.$window.shared('resultDisplayTime', displayTime),
                        vm.$window.shared('infoEmpToScreenB', {
                            employeeId,
                            employeeCode,
                            mode: MODE_PERSON
                        })
                    )
                    .then(() => vm.$window.modal('at', '/view/kdp/002/b/index.xhtml'));
            };
            const openDialogC = (stampDate: string) => {
                return $
                    .when(
                        vm.$window.shared('KDP010_2C', displayItemId),
                        vm.$window.shared('infoEmpToScreenC', {
                            employeeId,
                            employeeCode,
                            mode: MODE_PERSON,
                            stampDate
                        })
                    )
                    .then(() => vm.$window.modal('at', '/view/kdp/002/c/index.xhtml'));
            };
            const command = {
                datetime: moment(date).format('YYYY/MM/DD HH:mm:ss'),
                buttonPositionNo,
                refActualResults: {
                    cardNumberSupport,
                    workLocationCD,
                    workTimeCode,
                    overtimeDeclaration
                }
            };

            vm
                .$blockui("invisibleView")
                .then(() => vm.$ajax('at', REST_API.registerStampInput, command))
                .then((stampDate: string) => {
                    switch (buttonPositionNo) {
                        case 1:
                        case 3:
                        case 4:
                            return openDialogB();
                        case 2:
                            if (notUseAttr !== 1) {
                                return openDialogB();
                            }

                            return openDialogC(stampDate);
                    }
                })
                .then(() => vm.$ajax('at', REST_API.getOmissionContents, {
                    pageNo: DEFAULT_PAGE_NO,
                    buttonDisNo: buttonPositionNo,
                    stampMeans: STAMP_MEANS_PORTAL
                }))
                .then((response: any) => {
                    if (response) {
                        const { dailyAttdErrorInfos } = response;

                        if (dailyAttdErrorInfos && dailyAttdErrorInfos.length) {
                            vm.$window
                                .shared('KDP010_2T', response)
                                .then(() => vm.$window.modal('/view/kdp/002/t/index.xhtml'))
                                .then(() => vm.$window.shared('KDP010_T'))
                                .then(({ isClose, errorDate, btn }) => {
                                    if (!isClose && errorDate) {
                                        const { transfer, screen } = btn;

                                        vm.$jump(screen, transfer);
                                    } else {
                                        vm.stampData();
                                    }
                                });
                        } else {
                            vm.stampData();
                        }
                    }
                })
                .fail(vm.$dialog.alert)
                .always(() => vm.$blockui("clearView"));
        }

        stampData(employees?: Employee[]) {
            const vm = this;

            return $.Deferred()
                .resolve(employees)
                // if stampData call without employee data, fetch data from api
                .then((employees: Employee[]) => employees || vm.$ajax('at', REST_API.getEmployeeStampData))
                .then((employees: Employee[]) => {
                    const [employee] = employees;

                    // stamp data
                    if (employee) {
                        const { stampRecords } = employee;

                        if (stampRecords && stampRecords.length) {
                            const mappeds = _
                                .chain(stampRecords)
                                .orderBy(['stampTimeWithSec'], ['desc'])
                                .map(({ stampTimeWithSec, stampArt, stampHow, buttonValueType }) => {
                                    const textAlign = $textAlign(buttonValueType);
                                    const mm = moment(stampTimeWithSec, D_FORMAT);
                                    const date = mm.toDate();
                                    const forceColor = mm.locale('en').format('dddd').toLowerCase();

                                    return { date, stampArt, stampHow, textAlign, forceColor };
                                })
                                .value();

                            vm.stamps(mappeds);
                        }
                    }
                });
        }

        destroyed() {
            const vm = this;

            vm.show.dispose();
            vm.widget.dispose();
            vm.message.display.dispose();

            clearInterval(vm.time.tick);
        }
    }

    interface MessageData {
        messageId: string;
        messageParams: string[];
    }

    interface SettingStampInput {
        portalStampSettings: PortalStampSetting;
        empInfos: any | null;
        stampResultDisplayDto: StampResultDisplay;
    }

    interface PortalStampSetting {
        cid: string;
        displaySettingsStampScreen: SettingStampScreen;
        buttonSettings: ButtonSetting[];
        suppressStampBtn: number;
        useTopMenuLink: number;
    }

    interface SettingStampScreen {
        serverCorrectionInterval: number;
        settingDateTimeColor: SettingDateTime;
        resultDisplayTime: number;
    }

    interface StampResultDisplay {
        companyId: string;
        notUseAttr: number;
        displayItemId: number[];
    }

    interface SettingDateTime {
        textColor: string;
        backgroundColor: string;
    }

    interface ButtonSetting {
        buttonPositionNo: 1 | 2 | 3 | 4;
        buttonDisSet: SettingButtonDisplay;
        buttonType: ButtonType;
        usrArt: number;
        audioType: number;
        buttonValueType: number;
    }

    interface SettingButtonDisplay {
        buttonNameSet: NameSet;
        backGroundColor: string;
    }

    interface NameSet {
        textColor: string;
        buttonName: string;
    }

    interface ButtonType {
        reservationArt: number;
        stampType: StampType;
    }

    interface StampType {
        changeHalfDay: boolean;
        goOutArt: number;
        setPreClockArt: number;
        changeClockArt: number;
        changeCalArt: number;
    }

    interface ConfirmSetting {
        systemDate: string;
        used: 0 | 1 | 2 | 3;
    }

    interface Employee {
        employeeId: string;
        date: string;
        stampRecords: StampRecord[];
    }

    interface StampRecord {
        stampNumber: string;
        stampDate: string;
        stampTime: string;
        stampTimeWithSec: string;
        stampHow: string;
        stampArt: string;
        stampArtName: string;
        revervationAtr: any;
        empInfoTerCode: any;
        timeStampType: string;
        authcMethod: number;
        stampMeans: number;
        changeHalfDay: boolean;
        goOutArt: any;
        setPreClockArt: number;
        changeClockArt: number;
        changeClockArtName: string;
        changeCalArt: number;
        cardNumberSupport: any;
        workLocationCD: any;
        workTimeCode: any;
        overTime: any;
        overLateNightTime: any;
        reflectedCategory: boolean;
        locationInfor: any;
        outsideAreaAtr: boolean;
        latitude: any;
        longitude: any;
        attendanceTime: any;
        correctTimeStampValue: number;
        buttonValueType: ButtonTypeAtr;
        corectTtimeStampType: string;
    }

    enum ButtonTypeAtr {
        // 系

        GOING_TO_WORK = 1,
        // 系

        WORKING_OUT = 2,
        // "外出系"

        GO_OUT = 3,
        // 戻り系

        RETURN = 4,
        // 予約系

        RESERVATION_SYSTEM = 5
    }

    interface SuppressSetting {
        departure: boolean;
        goOut: boolean;
        goingToWork: boolean;
        turnBack: boolean;
    }
}