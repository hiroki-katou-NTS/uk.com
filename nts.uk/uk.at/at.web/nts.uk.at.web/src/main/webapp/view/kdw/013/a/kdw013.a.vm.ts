module nts.uk.ui.at.kdp013.a {
    const { randomId } = nts.uk.util;
    const { parseQuery } = nts.uk.ui.at.kdp013.share;
    import calendar = nts.uk.ui.components.fullcalendar;

    const DATE_FORMAT = 'YYYY-MM-DD';

    const API: API = {
        ADD: '/screen/at/kdw013/a/add',
        DELETE: '/screen/at/kdw013/a/delete'
    };

    const model: StartProcessDto = {};

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

        employees: KnockoutObservableArray<calendar.Employee> = ko.observableArray([]);
        confirmers: KnockoutObservableArray<calendar.Employee> = ko.observableArray([]);

        breakTime: KnockoutObservable<calendar.BreakTime> = ko.observable();

        businessHours: KnockoutObservableArray<calendar.BussinessHour> = ko.observableArray([]);

        weekends: KnockoutObservable<boolean> = ko.observable(true);
        editable: KnockoutObservable<boolean> = ko.observable(true);
        firstDay: KnockoutObservable<number> = ko.observable(1);
        scrollTime: KnockoutObservable<number> = ko.observable(480);
        slotDuration: KnockoutObservable<number> = ko.observable(30);
        initialDate: KnockoutObservable<Date> = ko.observable(new Date());
        initialView: KnockoutObservable<string> = ko.observable('fullWeek');
        availableView: KnockoutObservableArray<calendar.InitialView> = ko.observableArray(['oneDay', 'fullWeek']);
        validRange: KnockoutObservable<Partial<calendar.DatesSet>> = ko.observable({});

        // need map with [KDW013_21, KDW013_22, KDW013_23, KDW013_24] resource
        attendanceTimes: KnockoutObservableArray<calendar.AttendanceTime> = ko.observableArray([]);

        constructor() {
            super();

            const vm = this;
            const { mode } = parseQuery();

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
        }

        mounted() {
            const vm = this;

            _.extend(window, { vm });
        }

        saveData() {
            const vm = this;

        }

        datesSet(start: Date, end: Date) {
            console.log(start, end);
        }

        confirm() {
            const vm = this;
            const data: d.DataContent[] = [{
                id: '01',
                targetDate: '2021/01/01',
                description: 'Description 01',
                link: 'Link 01'
            }, {
                id: '02',
                targetDate: '2021/01/02',
                description: 'Description 02',
                link: 'Link 02'
            }, {
                id: '03',
                targetDate: '2021/01/03',
                description: 'Description 03',
                link: 'Link 03'
            }, {
                id: '04',
                targetDate: '2021/01/04',
                description: 'Description 04',
                link: 'Link 04'
            }];

            vm.$window
                .modal('at', '/view/kdw/013/d/index.xhtml', data)
                .then(() => {

                });
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
    }

    export module department {
        @component({
            name: 'kdw013-department',
            template: `<h3 data-bind="i18n: 'KDW013_4'"></h3>
            <div data-bind="ntsComboBox: {
                name: $component.$i18n('KDW013_5'),
                options: $component.departments,
                visibleItemsCount: 5,
                value: ko.observable(),
                editable: true,
                selectFirstIfNull: true,
                columns: [
                    { prop: 'code', length: 4 },
                    { prop: 'name', length: 10 }
                ]
            }"></div>
            <ul data-bind="foreach: { data: $component.params.employees, as: 'item' }">
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
            departments: KnockoutObservableArray<any> = ko.observableArray([]);

            constructor(private params: { mode: KnockoutObservable<boolean>; employees: KnockoutObservableArray<calendar.Employee> }) {
                super();
            }

            mounted() {
                const vm = this;
                const { params } = vm;
                const { mode, employees } = params;
                const subscribe = (mode: boolean) => {
                    if (mode === false) {
                        // reload data
                        $.Deferred()
                            .resolve([{
                                id: '000001',
                                code: '000001',
                                name: '日通　太郎',
                                selected: true
                            }, {
                                id: '000002',
                                code: '000002',
                                name: '日通　一郎',
                                selected: false
                            }, {
                                id: '000003',
                                code: '000003',
                                name: '鈴木　太郎',
                                selected: false
                            }, {
                                id: '000004',
                                code: '000004',
                                name: '加藤　良太郎',
                                selected: false
                            }, {
                                id: '000005',
                                code: '000005',
                                name: '佐藤　花子',
                                selected: false
                            }, {
                                id: '000006',
                                code: '000006',
                                name: '佐藤　龍馬',
                                selected: false
                            }, {
                                id: '000007',
                                code: '000007',
                                name: '日通　二郎',
                                selected: false
                            }])
                            .then((data: any) => employees(data));
                    }
                };

                subscribe(mode());
                mode.subscribe(subscribe);
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
            constructor(public params: { mode: KnockoutObservable<boolean>; confirmers: KnockoutObservableArray<calendar.Employee> }) {
                super();
            }

            mounted() {
                const vm = this;
                const { params } = vm;
                const { mode, confirmers } = params;
                const subscribe = (mode: boolean) => {
                    // reload data
                    $.Deferred()
                        .resolve([{
                            id: '000001',
                            code: '000001',
                            name: '日通　太郎',
                            selected: true
                        }, {
                            id: '000002',
                            code: '000002',
                            name: '日通　一郎',
                            selected: false
                        }, {
                            id: '000003',
                            code: '000003',
                            name: '鈴木　太郎',
                            selected: false
                        }, {
                            id: '000004',
                            code: '000004',
                            name: '加藤　良太郎',
                            selected: false
                        }, {
                            id: '000005',
                            code: '000005',
                            name: '佐藤　花子',
                            selected: false
                        }, {
                            id: '000006',
                            code: '000006',
                            name: '佐藤　龍馬',
                            selected: false
                        }, {
                            id: '000007',
                            code: '000007',
                            name: '日通　二郎',
                            selected: false
                        }])
                        .then((data: any) => confirmers(data));
                };

                subscribe(mode());
                mode.subscribe(subscribe);
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

                const vm = this;
                const { $user } = vm;
                const { } = $user;
                const { items } = params;

                $.Deferred()
                    // emulate ajax method
                    .resolve([])
                    .then((data: any[]) => items(data));
            }
        }

        type EventParams = {
            items: KnockoutObservableArray<any>;
            mode: KnockoutComputed<boolean>;
        };
    }
}