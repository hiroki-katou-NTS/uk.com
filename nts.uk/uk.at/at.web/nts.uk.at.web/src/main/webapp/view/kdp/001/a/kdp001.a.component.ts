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

    type LENGTH = 'long' | 'short';

    const REST_API = {
        getEmployeeStampData: '/at/record/stamp/employment_system/get_employee_stamp_data',
        confirmUseOfStampInput: '/at/record/stamp/employment_system/confirm_use_of_stamp_input',
        registerStampInput: '/at/record/stamp/employment_system/register_stamp_input',
        getSettingStampInput: '/at/record/stamp/employment_system/get_setting_stamp_input',
        getOmissionContents: '/at/record/stamp/employment_system/get_omission_contents',
        getStampToSuppress: '/at/record/stamp/employment_system/get_stamp_to_suppress',
        getLocation: 'at/record/stamp/employment_system/get_location_stamp_input',
        WORKPLACE_INFO: "screen/at/kdp003/workplace-info",
        GetWorkLocationRagionalTime: "at/record/kdp/common/get-work-location-regional-time",
        GetWorkPlaceRegionalTime: "at/record/kdp/common/get-work-place-regional-time",
        GetIPAddress: "at/record/stamp/finger/get-ip-address"
    };

    //個人
    const MODE_PERSON = 1;
    const DEFAULT_PAGE_NO = 1;
    const STAMP_MEANS_PORTAL = 4;
    const DEFAULT_GRAY = '#E8E9EB';
    const DEFAULT_COLOR_TIME = '#7F7F7F';
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
            <div class="kdp-001-a kdp-001-a-title">
                <div class="time">
                    <div class="date">
                        <span class="ymd" data-bind="date: $component.time.now, format: 'YYYY/MM/DD', attr: { style: $component.time.style }"></span>
                        <span class="ddd" data-bind="date: $component.time.now, format: '（ddd）', attr: { style: $component.time.style }"></span>
                    </div>
                    <div class="hour-second">
                        <span class="hour" data-bind="date: $component.time.now, format: 'HH:mm',attr: { style: $component.time.style }"></span>
                        <span class="second" data-bind="date: $component.time.now, format: 'ss', attr: { style: $component.time.style }"></span>
                    </div>
                </div>
                <!-- ko if: $component.useTopMenuLink -->
                    <div style="color: blue !important;" data-bind="if: modeA" class="button-link">
                        <a href="#" data-bind="i18n: 'KDP001_4', click: toTopPage"></a>
                    </div>
                <!-- /ko -->
            </div>
            <div style="color: #ff0000 !important;" class="kdp-001-a kdp-001-a-msg"data-bind="
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
                    <!--    <i data-bind="ntsIcon: { no: 205, width: 85, height: 85 }"></i> -->
                    <!-- /ko -->

                    <!-- ko if: btn.buttonPositionNo == 2 -->
                    <!--    <i data-bind="ntsIcon: { no: 209, width: 85, height: 85 }"></i> -->
                    <!-- /ko -->

                    <!-- ko if: btn.buttonPositionNo == 3 -->
                    <!--    <i data-bind="ntsIcon: { no: 212, width: 50, height: 50 }"></i> -->
                    <!-- /ko -->

                    <!-- ko if: btn.buttonPositionNo == 4 -->
                    <!--    <i data-bind="ntsIcon: { no: 213, width: 50, height: 50 }"></i> -->
                    <!-- /ko -->

                    <!-- ko if: btn.buttonPositionNo == 1 || btn.buttonPositionNo == 2 -->
                        <div data-bind="attr: { style: btn.style }, text: btn.buttonName"></div>
                    <!-- /ko -->

                    <!-- ko if: btn.buttonPositionNo == 3 || btn.buttonPositionNo == 4 -->
                        <div data-bind="attr: { style: btn.style }, text: btn.buttonName"></div>
                    <!-- /ko -->
                </button>
            </div>
            <!-- ko if: $component.modeDisplayStampList -->
                <!-- ko if: !$component.message.display() -->
                    <!-- ko if: $component.poral -->
                        <div class="kdp-001-a-potal" data-bind="
                                widget-content: 149,
                                css: {  'has-info-long': $component.lengthStamps() === 'long' ,
                                        'has-info-short': $component.lengthStamps() === 'short'}
                                ">
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
                    <!-- /ko -->
                    <!-- ko ifnot: $component.poral -->
                        <div class="kdp-001-a" data-bind="
                                widget-content: 143,
                                css: {  'has-info-long': $component.lengthStamps() === 'long' ,
                                        'has-info-short': $component.lengthStamps() === 'short'}
                                ">
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
                    <!-- /ko -->
                <!-- /ko -->
            <!-- /ko -->
            <style rel="stylesheet">
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
                .kdp-001-a.kdp-001-a-msg {
                    padding: 10px 0px;
                    width: 450px;
                    margin: auto;
                }
                .kdp-001-a.kdp-001-a-btn{
                    width: 450px;
                    margin: auto;
                    margin-bottom: 20px;
                }
                .kdp-001-a.kdp-001-a-btn .btn-start-1 {
                    font-size: 25px;
                    position: relative;
                    white-space: break-spaces;
                    word-break: break-all;
                    top: 15px;
                }
                .kdp-001-a.kdp-001-a-btn .btn-start-2 {
                    font-size: 20px;
                    position: relative;
                    white-space: break-spaces;
                    word-break: break-all;
                    height: 40px;
                    top: 20px;
                }
                .kdp-001-a.kdp-001-a-btn .btn-start-3 {
                    font-size: 20px;
                    position: relative;
                    white-space: break-spaces;
                    word-break: break-all;
                    height: 40px;
                }
                .kdp-001-a.kdp-001-a-btn .btn-start-4 {
                    font-size: 15px;
                    position: relative;
                    white-space: break-spaces;
                    word-break: break-all;
                    height: 37px;
                    top: 2px;
                }
                .kdp-001-a.kdp-001-a-btn .btn-start-5 {
                    font-size: 15px;
                    position: relative;
                    white-space: break-spaces;
                    word-break: break-all;
                    height: 37px;
                    top: 20px;
                }
                .kdp-001-a.kdp-001-a-btn1 .btn-end {
                    font-size: 20px;
                }

                .kdp-001-a.kdp-001-a-btn button {
                    width: 208px;
                    border-radius: 10px;
                    border: solid 2px;
                }

                .kdp-001-a.kdp-001-a-btn button:nth-child(1),
                .kdp-001-a.kdp-001-a-btn button:nth-child(2) {
                    height: 113px;
                }

                .kdp-001-a.kdp-001-a-btn button:nth-child(1) *,
                .kdp-001-a.kdp-001-a-btn button:nth-child(2) * {
                    font-size: 34px;
                }

                .kdp-001-a.kdp-001-a-btn button:nth-child(3),
                .kdp-001-a.kdp-001-a-btn button:nth-child(4) {
                    margin-top: 18px;
                    height: 72px;
                }

                .kdp-001-a.kdp-001-a-btn button:nth-child(3) *,
                .kdp-001-a.kdp-001-a-btn button:nth-child(4) * {
                    font-size: 24px;
                }

                .kdp-001-a.kdp-001-a-btn button:nth-child(1),
                .kdp-001-a.kdp-001-a-btn button:nth-child(3) {
                    margin-right: 30px;
                }

                .kdp-001-a.widget-content {
                    border: 1px solid #b1b1b1;
                    max-height: 143px;
                    width: 448px;
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
                .kdp-001-a-potal.widget-content {
                    max-height: 149px;
                    width: 448px;
                    margin: 5px auto 20px auto;
                    border-radius: 3px;
                    position: inherit;
                }
                .kdp-001-a-potal.widget-content table {
                    width: 100%;
                }
                .kdp-001-a-potal.widget-content table td {
                    overflow: hidden;
                    position: relative;
                }
                .kdp-001-a-potal.widget-content table td.sunday {
                    color: #FF0000;
                }
                .kdp-001-a-potal.widget-content table td.saturday {
                    color: #0000FF;
                }
                .kdp-001-a-potal.widget-content table td:not(:first-child):before {
                    content: '';
                    display: block;
                    width: 1px;
                    height: 16px;
                    border-left: 1px solid #ccc;
                    position: absolute;
                    top: calc(50% - 8px);
                    left: 0;
                }
                .kdp-001-a-potal.widget-content table td {
                    padding: 7px 10px;
                }
                .kdp-001-a-potal.widget-content table td:not(:last-child) {
                    text-align: center;
                }
                
                .kdp-001-a-title {
                    position: relative;
                    margin-bottom: 20px;
                }

                .kdp-001-a-title .time {
                    width: 270px;
                    margin: auto;
                    position: relative;
                    white-space: nowrap;
                }

                .kdp-001-a-title .time * {
                    font-family: Quicksand !important;
                    font-weight: bold;
                }
                
                .kdp-001-a-title .time .date {
                    text-align: center;
                }
                
                .kdp-001-a-title .time .date .ymd {
                    font-size: 32px;
                }
                
                .kdp-001-a-title .time .date .ddd {
                    font-size: 26px;
                    font-family: "Noto Sans JP" !important;
                }

                .kdp-001-a-title .time .hour-second {
                    padding-top: 10px;
                }

                .kdp-001-a-title .time .hour-second .hour {
                    position: relative;
                    z-index: 1;
                    font-size: 80px;
                    line-height: 80px;
                }

                .kdp-001-a-title .time .hour-second .second {
                    font-size: 50px;
                    line-height: 50px;
                    position: relative;
                    left: 16px;
                    z-index: 1;
                }
                .kdp-001-a-title .button-link {
                    text-align: center;
                    padding-left: 340px;
                    padding-bottom: 5px;
                }
                .kdp-001-a .left-content{
                    margin-right: 10px;
                }
                .has-info-long {
                    overflow-y: scroll !important;
                }
                .has-info-short {
                    overflow-y: hidden;
                }
				.kdp-001-a.kdp-001-a-title .button-link a {
					color: #0000EE;
				}
            </style>

            <script src=
            "https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js">
                </script>
        `
    })
    export class KDP001WidgetComponent extends ko.ViewModel {
        time: TimeClock = initTime();

        show!: KnockoutComputed<boolean>;
        widget!: KnockoutComputed<string>;

        stamps: KnockoutObservableArray<Partial<StampRecord>> = ko.observableArray([]);
        buttons: KnockoutObservableArray<Partial<ButtonSetting>> = ko.observableArray([]);

        stampDisplay: KnockoutObservable<StampResultDisplay | null> = ko.observable(null);

        modeOutUse: KnockoutObservable<boolean> = ko.observable(true);
        modeDisplayStampList: KnockoutObservable<boolean> = ko.observable(true);
        useTopMenuLink: KnockoutObservable<boolean> = ko.observable(false);

        modeBasyo: KnockoutObservable<boolean> = ko.observable(false);
        workLocationName: KnockoutObservable<string | null> = ko.observable(null);
        workplaceId: KnockoutObservable<string | null> = ko.observable(null);
        modeA: KnockoutObservable<boolean> = ko.observable(false);
        workpalceCD: string | null = null;
        workPalceIds: string[] = null;

        regionalTime: KnockoutObservable<number> = ko.observable(0);
        ipAddress: string = '';

        message: {
            data: KnockoutObservable<MessageData>;
            display: KnockoutComputed<string>;
        } = {
                data: ko.observable({ messageId: '', messageParams: [] }),
                display: null
            };

        lengthStamps!: KnockoutComputed<LENGTH>;
        poral!: KnockoutComputed<boolean>;
        state!: KnockoutComputed<string>;

        constructor(private mode: 'a' | 'b' | 'c' | 'd' | KnockoutObservable<'a' | 'b' | 'c' | 'd'> = 'a') {
            super();
            const vm = this;

            vm.state = ko.computed({
                read: () => {
                    var isIE = /*@cc_on!@*/false || !!document.documentMode;

                    if (isIE) {
                        return 'IE';
                    }
                    return 'NOT_IE';
                }
            });

            if (mode === 'a') {
                vm.modeA(true);
            }

            vm.lengthStamps = ko.computed({
                read: () => {
                    const stamps = ko.unwrap(vm.stamps);

                    if (stamps.length < 5) {
                        return 'short';
                    }
                    return 'long';
                }
            });

            vm.basyo();
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

            vm.poral = ko.computed({
                read: () => {
                    const url = document.URL.toString();
                    const sortUrl = url.substring(url.length - 26);
                    if (sortUrl === 'view/ccg/008/a/index.xhtml') {
                        return true;
                    } else {
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

            vm.regionalTime.subscribe(() => {
                vm.loadData();
            })

            if (ko.isObservable(mode)) {
                mode.subscribe(() => vm.loadData());
            }
            vm.loadData();
        }

        loadData() {
            const vm = this;
            const { mode } = vm;

            // get server time from cache
            vm.time.tick = setInterval(() => vm.time.now(moment(vm.$date.now()).add(ko.unwrap(vm.regionalTime), 'm').toDate()), 500);

            const STAMP_INPUT = vm.$ajax('at', REST_API.getSettingStampInput, { regionalTimeDifference: ko.unwrap(vm.regionalTime) });
            const CONFIRM_USE = vm.$ajax('at', REST_API.confirmUseOfStampInput, { stampMeans: STAMP_MEANS_PORTAL });
            const EMPLOYEE_DATA = vm.$ajax('at', REST_API.getEmployeeStampData, { regionalTimeDifference: ko.unwrap(vm.regionalTime) });
            const STAMP_PRESS = vm.$ajax('at', REST_API.getStampToSuppress);

            vm
                .$blockui('invisibleView')
                .then(() => $.when(STAMP_INPUT, CONFIRM_USE, EMPLOYEE_DATA, STAMP_PRESS))
                .then((stamp: SettingStampInput, confirm: ConfirmSetting, employees: Employee[], suppress: SuppressSetting) => {
                    const { used } = confirm;
                    const messageId = showMessage(used);
                    const { portalStampSettings, stampResultDisplayDto } = stamp;

                    vm.modeOutUse(stamp.portalStampSettings.goOutUseAtr == 1 ? true : false);
                    vm.modeDisplayStampList(stamp.portalStampSettings.displayStampList == 1 ? true : false);
                    vm.useTopMenuLink(stamp.portalStampSettings.useTopMenuLink == 1 ? true : false)

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
                        let self = this;
                        vm.$date.interval(100);
                        setTimeout(() => {
                            vm.$date.interval((serverCorrectionInterval || 1) * 60000);
                        }, 1000);

                        vm.time.displayTime(resultDisplayTime || 0);

                        vm.time.style(`color: ${textColor || DEFAULT_COLOR_TIME};`);

                        if (ko.unwrap(vm.message.data).messageId === 'Msg_1645') {
                            vm.time.style(`color: DEFAULT_COLOR_TIME;`);
                        }

                        const btns = _
                            .chain(buttonSettings)
                            .uniqBy('buttonPositionNo')
                            .filter(({ buttonPositionNo }) => 1 <= buttonPositionNo && buttonPositionNo <= 4)
                            .sortBy(['buttonPositionNo'])
                            .map((btn: ButtonSetting) => {
                                const { buttonPositionNo, buttonDisSet } = btn;
                                const { backGroundColor, buttonNameSet } = buttonDisSet;
                                const { textColor, buttonName } = buttonNameSet;
                                const { departure, goOut, goingToWork, turnBack } = suppress;
                                let backgroundColor = null;

                                // Fix bug #123919
                                if (!goingToWork && !departure && !goOut && !turnBack) {
                                    backgroundColor = backGroundColor;
                                } else {
                                    if (!goingToWork) {
                                        backgroundColor = (() => {
                                            switch (buttonPositionNo) {
                                                case 1:
                                                    return backGroundColor;
                                                case 2:
                                                case 3:
                                                case 4:
                                                    return DEFAULT_GRAY;
                                            }
                                        })();
                                    } else {
                                        backgroundColor = (() => {
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
                                    }
                                }

                                return {
                                    ...btn,
                                    buttonName,
                                    style: `color: ${textColor}; background-color: ${backgroundColor};`
                                };
                            })
                            .value();
                        if (!ko.unwrap(vm.modeOutUse)) {
                            btns.forEach((value) => {
                                if (value.buttonPositionNo == 3 || value.buttonPositionNo == 4) {
                                    btns.splice(btns.indexOf(value));
                                }
                            });
                        }
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

        public toTopPage() {
            let vm = this;
            vm.$jump('com', '/view/ccg/008/a/index.xhtml');
        }

        stamp(btn: ButtonSetting) {
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
            var stampDate1: any;

            const openDialogB = () => {
                return $
                    .when(
                        vm.$window.shared('resultDisplayTime', displayTime),
                        vm.$window.shared('infoEmpToScreenB', {
                            employeeId,
                            employeeCode,
                            mode: MODE_PERSON,
                            workLocationName: ko.unwrap(vm.workLocationName),
                            workPlaceId: ko.unwrap(vm.workplaceId)
                        }),
                        vm.$window.shared('screenB', { screen: "KDP001" }),
                    )
                    .then(() => vm.$window.modal('at', '/view/kdp/002/b/index.xhtml', {
                        stampTime: moment(moment(vm.$date.now()).add(ko.unwrap(vm.regionalTime), 'm').toDate()).format("HH:mm"),
                        regionalTime: ko.unwrap(vm.regionalTime)
                    }));
            };
            const openDialogC = (stampDate: string, error: any) => {
                return $
                    .when(
                        vm.$window.shared('KDP010_2C', displayItemId),
                        vm.$window.shared('infoEmpToScreenC', {
                            employeeId,
                            employeeCode,
                            mode: MODE_PERSON,
                            stampDate,
                            workLocationName: ko.unwrap(vm.workLocationName),
                            workPlaceId: ko.unwrap(vm.workplaceId),
                            error: error,
                            regionalTime: ko.unwrap(vm.regionalTime)
                        }),
                        vm.$window.shared('screenC', { screen: "KDP001" }),
                    )
                    .then(() => vm.$window.modal('at', '/view/kdp/002/c/index.xhtml'));
            };

            const command = {
                datetime: moment(date).format('YYYY/MM/DD HH:mm:ss'),
                buttonPositionNo,
                refActualResults: {
                    cardNumberSupport,
                    workPlaceId: ko.unwrap(vm.workplaceId),
                    workLocationCD: vm.workpalceCD,
                    workTimeCode,
                    overtimeDeclaration
                }
            };
            vm
                .$blockui("invisibleView")
                .then(() => vm.$ajax('at', REST_API.registerStampInput, command))
                .then((stampDate: string) => {
                    stampDate1 = stampDate;
                })
                .then(() => vm.$ajax('at', REST_API.getOmissionContents, {
                    pageNo: DEFAULT_PAGE_NO,
                    buttonDisNo: buttonPositionNo,
                    stampMeans: STAMP_MEANS_PORTAL
                }))
                .then((res: any) => {

                    switch (buttonPositionNo) {
                        case 1:
                        case 3:
                        case 4:
                            return openDialogB();
                        case 2:
                            if (notUseAttr !== 1) {
                                return openDialogB();
                            }

                            return openDialogC(stampDate1, res);
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
                                .then(() => vm.$window.modal('at', '/view/kdp/002/t/index.xhtml'))
                                .then(() => vm.$window.shared('KDP010_T'))
                                .then(({ isClose, errorDate, btn }) => {
                                    if (!isClose && errorDate) {
                                        const { transfer, screen } = btn;

                                        vm.$jump.blank('at', screen, { baseDate: transfer.appDate });
                                    } else {
                                        vm.stampData();
                                    }
                                });
                        } else {
                            vm.stampData();
                        }
                    }
                })
                .then(() => {
                    vm.loadData();
                })
                .fail(vm.$dialog.alert)
                .always(() => vm.$blockui("clearView"));
        }

        basyo() {
            $.urlParam = function (name) {
                var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
                if (results == null) {
                    return null;
                }
                else {
                    return decodeURI(results[1]) || 0;
                }
            }

            const vm = this,
                locationCd = $.urlParam('basyo'),
                mode = $.urlParam('mode');
            vm.$ajax('at', REST_API.GetIPAddress, { contactCode: vm.$user.contractCode }).done((address: any) => {
                vm.ipAddress = address.ipaddress;
            }).then(() => {
                if (locationCd) {
                    const param = {
                        contractCode: vm.$user.contractCode,
                        workLocationCode: locationCd,
                        ipv4Address: vm.ipAddress
                    }

                    vm.$blockui('invisible')
                        .then(() => {
                            vm.$ajax('at', REST_API.GetWorkLocationRagionalTime, param)
                                .done((data: GetWorkPlaceRegionalTime) => {
                                    if (data) {
                                        if (data.workLocationName != null && data.workLocationName !== '') {
                                            vm.workplaceId(data.workPlaceId)
                                            vm.workLocationName(data.workLocationName);
                                            vm.regionalTime(data.regional);
                                            vm.workpalceCD = data.workLocationCD;
                                            vm.modeBasyo(true)
                                        } else {
                                            vm.getTimeZone();
                                        }
                                    }
                                })
                        })
                        .always(() => {
                            vm.$blockui('clear');
                        });
                } else {
                    vm.getTimeZone();
                }
            });

            if (mode) {
                if (mode === 'a') {
                    vm.modeA(true);
                }
            }
        }

        getTimeZone() {
            const vm = this;
            let param = {
                contractCode: vm.$user.contractCode,
                // workLocationCode: locationCd,
                ipv4Address: vm.ipAddress
            }
            vm.$ajax('at', REST_API.GetWorkLocationRagionalTime, param)
                .done((data: GetWorkPlaceRegionalTime) => {
                    if (data.workLocationName != null && data.workLocationName !== '') {
                        vm.workplaceId(data.workPlaceId)
                        vm.workLocationName(data.workLocationName);
                        vm.regionalTime(data.regional);
                        vm.workpalceCD = data.workLocationCD;
                    } else {
                        let inputWorkPlace = {
                            contractCode: vm.$user.contractCode,
                            cid: vm.$user.companyId,
                            sid: vm.$user.employeeId,
                            workPlaceId: ""
                        }
                        vm.$ajax('at', REST_API.GetWorkPlaceRegionalTime, inputWorkPlace).then((data: GetWorkPlaceRegionalTime) => {
                            if (data) {
                                vm.workplaceId(data.workPlaceId)
                                vm.workLocationName(data.workLocationName);
                                vm.regionalTime(data.regional);
                                vm.workpalceCD = data.workLocationCD;
                            }
                        })
                    }
                })
        }

        stampData(employees?: Employee[]) {
            const vm = this;

            return $.Deferred()
                .resolve(employees)
                // if stampData call without employee data, fetch data from api
                .then((employees: Employee[]) => employees || vm.$ajax('at', REST_API.getEmployeeStampData, { regionalTimeDifference: ko.unwrap(vm.regionalTime) }))
                .then((employees: Employee[]) => {
                    const [employee] = employees;
                    const result: any = [];

                    _.forEach(employees, (value) => {
                        // stamp data

                        if (employee) {
                            const { stampRecords } = value;
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
                                _.forEach(mappeds, (value1) => {
                                    result.push(value1);
                                })
                            }
                        }

                        vm.stamps(result);

                    })
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

    interface IBasyo {
        workLocationName: string;
        workpalceId: string[];
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
        goOutUseAtr: number;
        displayStampList: number;
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
        stampType: StampType;
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

    //打刻種類
    interface StampType {
        changeHalfDay: boolean; // 勤務種類を半休に変更する
        goOutArt: number; // 外出区分
        setPreClockArt: number; // 所定時刻セット区分
        changeClockArt: number; // 時刻変更区分
        changeCalArt: number; // 計算区分変更対象
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

    interface GetWorkPlaceRegionalTime {
        workPlaceId: string;
        workLocationCD: string;
        workLocationName: string;
        regional: number;
    }
}