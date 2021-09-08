/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

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
    const DEFAULT_GRAY = '#E8E9EB';
    const D_FORMAT = 'YYYY/MM/DD HH:mm:ss';

@bean()
class KDP001AViewModel extends ko.ViewModel {

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
                            .filter((_: any, index: number) => {
                                if (['b', 'c'].indexOf(screen) > -1) {
                                    return true;
                                }

                                return index <= 1;
                            })
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
                            console.log(ko.unwrap(vm.stamps));
                            
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