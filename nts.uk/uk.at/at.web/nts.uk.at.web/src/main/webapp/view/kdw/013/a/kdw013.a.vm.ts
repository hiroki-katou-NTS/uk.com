module nts.uk.ui.at.kdp013.a {
    enum OverTimeLeaveAtr {
        // 0: 残業申請
        OVER_TIME_APPLICATION = 0,

        //1: 休日出勤申請
        HOLIDAY_WORK_APPLICATION = 1
    };

    const { formatTime } = share;
    import calendar = nts.uk.ui.components.fullcalendar;

    const DATE_FORMAT = 'YYYY-MM-DD';
    const DATE_TIME_FORMAT = 'YYYY-MM-DDT00:00:00.000\\Z';

    const API: API = {
        ADD: '/screen/at/kdw013/a/add',
        // DeleteWorkResultConfirmationCommand
        DELETE: '/screen/at/kdw013/a/delete',
        // 対象社員を選択する
        // Chọn nhân viên ở A2_4
        // same api: CHANGE_DATE
        SELECT: '/screen/at/kdw013/a/select',
        // 日付を変更する
        // Chọn ngày ở [画面イメージ]A6_1/[固有部品]A1_1
        CHANGE_DATE: '/screen/at/kdw013/a/changeDate',
        // RegisterWorkContentCommand
        REGISTER: '/screen/at/kdw013/a/register',
        REG_WORKTIME: '/screen/at/kdw013/registerWorkTime',
        START: '/screen/at/kdw013/a/start'
    };

    const initialCache = (): ChangeDateParam => ({
        displayPeriod: {
            end: '',
            start: ''
        },
        employeeId: '',
        refDate: ''
    })

    @handler({
        bindingName: 'kdw-toggle',
        validatable: true,
        virtual: false
    })
    export class KDWToggleBindingHandler implements KnockoutBindingHandler {
        init(element: HTMLButtonElement, valueAccessor: () => KnockoutComputed<boolean>) {
            const toggler = valueAccessor();
            const visible = ko.computed({
                read: () => {
                    const visible = ko.unwrap(toggler);

                    return visible;
                },
                disposeWhenNodeIsRemoved: element
            });
            const disable = ko.computed({
                read: () => {
                    const enable = ko.unwrap(toggler);

                    return !enable;
                },
                disposeWhenNodeIsRemoved: element
            });

            ko.applyBindingsToNode(element, { visible, disable });

            element.removeAttribute('data-bind');
        }
    }

    @bean()
    export class ViewModel extends ko.ViewModel {
        $toggle!: {
            save: KnockoutComputed<boolean>;
            remove: KnockoutComputed<boolean>;
            confirm: KnockoutComputed<boolean>;
        };

        events: KnockoutObservableArray<calendar.EventApi> = ko.observableArray([]);

        breakTime: KnockoutObservable<calendar.BreakTime> = ko.observable();

        businessHours: KnockoutObservableArray<calendar.BussinessHour> = ko.observableArray([]);

        weekends: KnockoutObservable<boolean> = ko.observable(true);
        editable: KnockoutObservable<boolean> = ko.observable(true);
        firstDay: KnockoutObservable<number> = ko.observable(1);
        scrollTime: KnockoutObservable<number> = ko.observable(480);
        slotDuration: KnockoutObservable<number> = ko.observable(30);
        initialDate: KnockoutObservable<Date> = ko.observable(new Date());
        dateRange: KnockoutObservable<Partial<calendar.DatesSet>> = ko.observable({});
        initialView: KnockoutObservable<string> = ko.observable('fullWeek');
        availableView: KnockoutObservableArray<calendar.InitialView> = ko.observableArray(['oneDay', 'fullWeek']);
        validRange: KnockoutObservable<Partial<calendar.DatesSet>> = ko.observable({});

        employees: KnockoutObservableArray<calendar.Employee> = ko.observableArray([]);
        confirmers!: KnockoutComputed<calendar.Employee[]>;
        // need map with [KDW013_21, KDW013_22, KDW013_23, KDW013_24] resource
        attendanceTimes!: KnockoutComputed<calendar.AttendanceTime[]>;

        // Change date range data model
        $datas: KnockoutObservable<ChangeDateDto | null> = ko.observable(null);

        // settings (first load data)
        $settings: KnockoutObservable<StartProcessDto | null> = ko.observable(null);

        constructor() {
            super();

            const vm = this;
            const { $query, employees } = vm;
            const { mode } = $query;
            const cache: ChangeDateParam = initialCache();
            const sameCache = (params: ChangeDateParam): 0 | 1 | 2 => {
                if (cache.displayPeriod.end === params.displayPeriod.end) {
                    if (cache.displayPeriod.start === params.displayPeriod.start) {
                        if (cache.refDate === params.refDate) {
                            return 2;
                        }

                        return 1;
                    }
                }

                return 0;
            };
            const updateCache = (params: ChangeDateParam) => {
                cache.displayPeriod.end = params.displayPeriod.end;
                cache.displayPeriod.start = params.displayPeriod.start;

                cache.refDate = params.refDate;
            };

            vm.$toggle = {
                save: ko.computed({
                    read: () => {
                        return true;
                    }
                }),
                remove: ko.computed({
                    read: () => {
                        const editable = ko.unwrap(vm.editable);

                        return !editable;
                    }
                }),
                confirm: ko.computed({
                    read: () => {
                        const editable = ko.unwrap(vm.editable);

                        return !editable;
                    }
                }),
            };

            if (mode) {
                // URLの値元に画面モードを判定する
                vm.editable(mode === '0');
            }

            ko.computed({
                read: () => {
                    const { employeeId } = vm.$user;
                    const date = ko.unwrap(vm.initialDate);
                    const dateRange = ko.unwrap(vm.dateRange);
                    const { start, end } = dateRange;

                    if (!!start && !!end) {
                        const params: ChangeDateParam = {
                            employeeId,
                            refDate: moment(date).format(DATE_TIME_FORMAT),
                            displayPeriod: {
                                start: moment(start).format(DATE_TIME_FORMAT),
                                end: moment(end).format(DATE_TIME_FORMAT)
                            }
                        };
                        const pair = sameCache(params);

                        if (pair !== 2) {
                            updateCache(params);
                        }

                        if (pair === 0) {
                            vm
                                .$ajax('at', API.CHANGE_DATE, params)
                                .then((data: ChangeDateDto) => vm.$datas(data));
                        }
                    }
                }
            }).extend({ rateLimit: 250 });

            vm.confirmers = ko.computed({
                read: () => {
                    const datas = ko.unwrap(vm.$datas);
                    const $date = ko.unwrap(vm.initialDate);
                    const $moment = moment($date).format(DATE_FORMAT);

                    if (datas) {
                        const { lstComfirmerDto } = datas;

                        return _
                            .chain(lstComfirmerDto)
                            .filter(({ confirmDateTime }) => (confirmDateTime || "").indexOf($moment) === 0)
                            .map(({
                                confirmSID: id,
                                confirmSCD: code,
                                businessName: name
                            }) => ({
                                id,
                                code,
                                name,
                                selected: false
                            }))
                            .value();
                    }

                    return [] as calendar.Employee[];
                }
            }).extend({ rateLimit: 500 });

            vm.attendanceTimes = ko.computed({
                read: () => {
                    const datas = ko.unwrap(vm.$datas);
                    const empls = ko.unwrap(vm.employees);

                    // need update by employId if: mode=1
                    const employee = _.find(empls, ({ selected }) => !!selected);
                    const employeeId = employee ? employee.id : vm.$user.employeeId;

                    if (datas) {
                        const { lstWorkRecordDetailDto } = datas;

                        return _
                            .chain(lstWorkRecordDetailDto)
                            // .orderBy(['date'])
                            .filter(({ sId }) => sId === employeeId)
                            .map(({
                                date,
                                actualContent,
                            }) => {
                                const events: string[] = [];
                                const { breakHours, end, start, totalWorkingHours } = actualContent;

                                if (start) {
                                    const { timeWithDay } = start;

                                    events.push(vm.$i18n('KDW013_21', [formatTime(timeWithDay, 'Time_Short_HM')]));
                                }

                                if (end) {
                                    const { timeWithDay } = end;

                                    events.push(vm.$i18n('KDW013_22', [formatTime(timeWithDay, 'Time_Short_HM')]));
                                }

                                if (breakHours) {
                                    events.push(vm.$i18n('KDW013_23', [formatTime(breakHours, 'Time_Short_HM')]));
                                }

                                if (totalWorkingHours) {
                                    events.push(vm.$i18n('KDW013_24', [formatTime(totalWorkingHours, 'Time_Short_HM')]));
                                }

                                return {
                                    date: moment(date, DATE_FORMAT).toDate(),
                                    events,
                                };
                            })
                            .value();
                    }

                    return [] as calendar.AttendanceTime[];
                }
            }).extend({ rateLimit: 500 });
        }

        mounted() {
            const vm = this;

            // get settings
            vm.$ajax('at', API.START).then(vm.$settings);

            _.extend(window, { vm });
        }

        saveData() {
            const vm = this;
            // sample data for open view d
            const data: OvertimeLeaveTime[] = [{
                date: '2021/01/01',
                overtimeLeaveAtr: OverTimeLeaveAtr.HOLIDAY_WORK_APPLICATION,
                time: 720
            }, {
                date: '2021/01/02',
                overtimeLeaveAtr: OverTimeLeaveAtr.OVER_TIME_APPLICATION,
                time: 720
            }];

            vm.openDialogCaculationResult(data);
        }

        // 日付を変更する
        // UKDesign.UniversalK.就業.KDW_日別実績.KDW013_工数入力.A:工数入力.メニュー別OCD.日付を変更する
        datesSet(start: Date, end: Date) {
            const vm = this;

            vm.dateRange({ start, end });
        }

        confirm() {
            const vm = this;
        }

        // 作業実績の確認を解除する
        removeConfirm() {
            const vm = this;
            const { $user, employees, initialDate } = vm;
            const date = ko.unwrap(initialDate);
            const selected = _.find(ko.unwrap(employees), (e) => e.selected);

            if (selected) {
                const command = {
                    //対象者
                    // get from A2_5 control
                    employeeId: selected.id,
                    //対象日
                    // get from initialDate
                    date: moment(date).toISOString(),
                    //確認者
                    // 作業詳細.作業グループ
                    confirmerId: $user.employeeId
                };

                vm
                    .$ajax('at', API.DELETE, command)
                    .then(() => {

                    })
                    // trigger reload event on child component
                    .then(() => vm.editable.valueHasMutated());
            }
        }

        private openDialogCaculationResult(data: OvertimeLeaveTime[]) {
            const vm = this;

            vm.$window
                .modal('at', '/view/kdw/013/d/index.xhtml', data).then(() => { });
        }
    }

    export module department {
        type EmployeeDepartmentParams = {
            mode: KnockoutObservable<boolean>;
            employees: KnockoutObservableArray<calendar.Employee>;
            $settings: KnockoutObservable<a.StartProcessDto | null>;
        };

        @component({
            name: 'kdw013-department',
            template: `<h3 data-bind="i18n: 'KDW013_4'"></h3>
            <div data-bind="ntsComboBox: {
                name: $component.$i18n('KDW013_5'),
                options: $component.departments,
                visibleItemsCount: 20,
                value: ko.observable(),
                editable: true,
                selectFirstIfNull: true,
                columns: [
                    { prop: 'code', length: 4 },
                    { prop: 'name', length: 10 }
                ]
            }"></div>
            <ul class="list-employee" data-bind="foreach: { data: $component.params.employees, as: 'item' }">
                <li class="item" data-bind="
                    click: function() { $component.selectEmployee(item) },
                    timeClick: -1,
                    css: {
                        'selected': item.selected
                    }">
                    <div data-bind="text: item.code"></div>
                    <div data-bind="text: item.name"></div>
                </li>
            </ul>`
        })
        export class EmployeeDepartmentComponent extends ko.ViewModel {
            departments!: KnockoutComputed<any[]>;

            constructor(private params: EmployeeDepartmentParams) {
                super();

                const vm = this;
                const { $settings } = params;

                vm.departments = ko.computed({
                    read: () => {
                        const $sets = ko.unwrap($settings);

                        if ($sets) {
                            const { refWorkplaceAndEmployeeDto } = $sets;

                            if (refWorkplaceAndEmployeeDto) {
                                const { workplaceInfos } = refWorkplaceAndEmployeeDto;

                                return workplaceInfos;
                            }
                        }

                        return [];
                    },
                    write: (value: any) => {

                    }
                });
            }

            mounted() {
                const vm = this;
                const { $el } = vm;

                $($el)
                    .removeAttr('data-bind')
                    .find('[data-bind]')
                    .removeAttr('data-bind');
            }

            public selectEmployee(item: calendar.Employee) {
                const vm = this;
                const { employees } = vm.params;
                const unwraped = ko.toJS(employees);

                _.each(unwraped, (emp: calendar.Employee) => {
                    if (emp.code === item.code) {
                        emp.selected = true;
                    } else {
                        emp.selected = false;
                    }
                });

                if (ko.isObservable(employees)) {
                    employees(unwraped);
                }
            }
        }
    }

    export module approved {
        type Kdw013ApprovedParams = {
            mode: KnockoutObservable<boolean>;
            confirmers: KnockoutObservableArray<calendar.Employee>;
            $settings: KnockoutObservable<a.StartProcessDto | null>;
        };

        @component({
            name: 'kdw013-approveds',
            template: `<h3 data-bind="i18n: 'KDW013_6'"></h3>
            <ul data-bind="foreach: { data: $component.params.confirmers, as: 'item' }">
                <li class="item">
                    <div data-bind="text: item.code"></div>
                    <div data-bind="text: item.name"></div>
                </li>
            </ul>`
        })
        export class Kdw013ApprovedComponent extends ko.ViewModel {
            constructor(public params: Kdw013ApprovedParams) {
                super();
            }
        }
    }

    export module event {
        @component({
            name: 'kdw013-events',
            template: `<h3 data-bind="i18n: 'KDW013_7'"></h3>
            <ul data-bind="foreach: { data: $component.params.items, as: 'item' }">
                <li class="title" data-bind="attr: {
                    'data-id': _.get(item.extendedProps, 'relateId', ''),
                    'data-color': item.backgroundColor
                }">
                    <div data-bind="style: {
                        'background-color': item.backgroundColor
                    }"></div>
                    <div data-bind="text: item.title"></div>
                </li>
            </ul>`
        })
        export class Kdw013EventComponent extends ko.ViewModel {
            constructor(public params: EventParams) {
                super();
            }
        }

        type EventParams = {
            items: KnockoutObservableArray<any>;
            mode: KnockoutComputed<boolean>;
        };
    }

    const initData = () => {
        const vm = new ko.ViewModel();
        const commands: AddAttendanceTimeZoneParam = {
            employeeId: vm.$user.employeeId,
            editStateSetting: 1,
            workDetails: [{
                date: moment().toISOString(),
                lstWorkDetailsParam: [{
                    remarks: '',
                    supportFrameNo: 1,
                    timeZone: {
                        end: {
                            reasonTimeChange: {
                                engravingMethod: 0,
                                timeChangeMeans: 0
                            },
                            timeWithDay: 0
                        },
                        start: {
                            reasonTimeChange: {
                                engravingMethod: 0,
                                timeChangeMeans: 0
                            },
                            timeWithDay: 0
                        },
                        workingHours: 1
                    },
                    workGroup: {
                        workCD1: '',
                        workCD2: '',
                        workCD3: '',
                        workCD4: '',
                        workCD5: ''
                    },
                    workLocationCD: ''
                }]
            }]
        };

        vm
            .$ajax('at', API.REG_WORKTIME, commands)
            .then(function () { });
    }

    _.extend(window, { initData });
}