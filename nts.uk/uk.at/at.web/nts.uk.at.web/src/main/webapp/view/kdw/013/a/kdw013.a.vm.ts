module nts.uk.ui.at.kdp013.a {
    const mm = new ko.ViewModel();
    const { randomId } = nts.uk.util;

    const SAMPLE_DESCRIPTION = `
    TO　Lamくん、Vuongくん
    トップページ実装の件、大橋さんと相談しました。
    Knockout.jsで下記要件を満たせるなら、変更したいとのことです。
    ①②③とも問題ないかを教えてください。
    
    ■要件
    ①他システムのサイトを表示できる。
    　（ドメインが異なるサイトを表示するのは、脆弱性診断でNG（SamOriginポリシー）であるが、
    　　社内のポータルサイトを表示できる必要がある。）
    ②各ウィジェットの内容が表示できる。
    ③フローメニューを表示できる。
    　（フローメニューの作成で作ったhtmlファイルと、htmlファイルをアップロードしたフローメニューを表示できる。
    `;

    const events = [{
        title: "UK就業機能強化・テスト設計",
        start: moment().subtract(2, 'day').set('hour', 8).set('minute', 30).toDate(),// "2020-11-25T06:00:00.000Z",
        end: moment().subtract(2, 'day').set('hour', 10).set('minute', 30).toDate(),//"2020-11-25T10:30:00.000Z",
        backgroundColor: "#ff5050",
        extendedProps: {
            id: randomId(),
            relateId: randomId()
        }
    }, {
        title: 'Conference',
        start: moment().set('hour', 8).set('minute', 30).toDate(),
        end: moment().set('hour', 9).set('minute', 30).toDate(),
        extendedProps: {
            id: randomId(),
            relateId: randomId(),
            descriptions: SAMPLE_DESCRIPTION.trim()
        }
    }, {
        title: 'Meeting',
        start: moment().set('hour', 9).set('minute', 30).toDate(),
        end: moment().set('hour', 14).set('minute', 0).toDate(),
        extendedProps: {
            id: randomId(),
            relateId: randomId(),
            descriptions: SAMPLE_DESCRIPTION.trim()
        }
    }];

    const dragItems = [{
        title: 'UK就業・詳細設計',
        backgroundColor: '#ffc000',
        extendedProps: {
            id: '',
            relateId: randomId()
        }
    }, {
        title: 'UK就業機能強化・テスト設計',
        backgroundColor: '#ff5050',
        extendedProps: {
            id: '',
            relateId: randomId()
        }
    }, {
        title: 'UKプロジェクト・概要設計',
        backgroundColor: '#5b9bd5',
        extendedProps: {
            id: '',
            relateId: randomId()
        }
    }, {
        title: 'UKプロジェクト・統合テスト',
        backgroundColor: '#92d050',
        extendedProps: {
            id: '',
            relateId: randomId()
        }
    }];

    const employees = [{
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
    }];

    const attendanceTimes = [{
        date: moment().toDate(),
        events: [
            mm.$i18n('KDW013_21', ['8:00 ~ 17:00']),
            mm.$i18n('KDW013_22', ['1:00']),
            mm.$i18n('KDW013_23', ['8:00']),
            mm.$i18n('KDW013_24', ['8:00'])
        ]
    }, {
        date: moment().add(2, 'day').toDate(),
        events: [
            mm.$i18n('KDW013_21', ['8:00 ~ 17:00']),
            mm.$i18n('KDW013_22', ['1:00']),
            mm.$i18n('KDW013_23', ['8:00']),
            mm.$i18n('KDW013_24', ['8:00'])
        ]
    }, {
        date: moment().add(3, 'day').toDate(),
        events: [
            mm.$i18n('KDW013_21', ['8:00 ~ 17:00']),
            mm.$i18n('KDW013_22', ['1:00']),
            mm.$i18n('KDW013_23', ['8:00']),
            mm.$i18n('KDW013_24', ['8:00'])
        ]
    }];

    const DATE_FORMAT = 'YYYY-MM-DD';

    @bean()
    export class ViewModel extends ko.ViewModel {
        events: KnockoutObservableArray<any> = ko.observableArray(events);

        employees: KnockoutObservableArray<any> = ko.observableArray(employees);

        breakTime: KnockoutObservable<any> = ko.observable({
            startTime: 60 * 12,
            endTime: 60 * 13,
            backgroundColor: '#ddd'
        });

        businessHours: KnockoutObservableArray<any> = ko.observableArray([{
            // days of week. an array of zero-based day of week integers (0=Sunday)
            daysOfWeek: [1, 2, 3, 4, 5], // Monday - Thursday
            startTime: 60 * 8 + 30, // '08:30:00'
            endTime: 60 * 17 + 30 // '17:30:00'
        }, {
            daysOfWeek: [0, 6],
            startTime: 0,
            endTime: 0
        }]);

        weekends: KnockoutObservable<boolean> = ko.observable(true);
        editable: KnockoutObservable<boolean> = ko.observable(true);
        firstDay: KnockoutObservable<number> = ko.observable(1);
        scrollTime: KnockoutObservable<number> = ko.observable(480);
        slotDuration: KnockoutObservable<number> = ko.observable(30);
        initialDate: KnockoutObservable<Date> = ko.observable(new Date());
        initialView: KnockoutObservable<string> = ko.observable('fullWeek');
        availableView: KnockoutObservableArray<string> = ko.observableArray(['oneDay', 'fullWeek']);
        validRange: KnockoutObservable<{ start: Date; end: Date; }> = ko.observable({ start: moment('2021-02-12', DATE_FORMAT).toDate(), end: moment('2021-10-21', DATE_FORMAT).toDate() });

        // need map with [KDW013_21, KDW013_22, KDW013_23, KDW013_24] resource
        attendanceTimes: KnockoutObservableArray<any> = ko.observableArray(attendanceTimes);

        datesSet(start: Date, end: Date) {
            console.log(start, end);
        }

        mounted() {
            const vm = this;

            _.extend(window, { vm });
        }

        saveData() {
            const vm = this;

        }

        checkData() {
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

        confirmData() {
            const vm = this;

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
            <ul data-bind="foreach: { data: $component.employees, as: 'item' }">
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
            employees: KnockoutObservableArray<Employee> = ko.observableArray([]);
            departments: KnockoutObservableArray<any> = ko.observableArray([]);

            constructor(private params: {}) {
                super();

                this.employees(employees);
            }

            public selectEmployee(item: Employee) {
                const vm = this;
                const { employees } = vm;
                const unwraped = ko.toJS(employees);

                _.each(unwraped, (emp: Employee) => {
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
            <ul data-bind="foreach: { data: $component.employees, as: 'item' }">
                <li class="item">
                    <div data-bind="text: item.code"></div>
                    <div data-bind="text: item.name"></div>
                </li>
            </ul>`
        })
        export class Kdw013ApprovedComponent extends ko.ViewModel {
            employees: KnockoutObservableArray<Employee> = ko.observableArray([]);

            constructor(public params: any) {
                super();

                console.log(ko.toJS(params));

                this.employees(employees);
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
                    .resolve(dragItems)
                    .then((data: any[]) => items(data));
            }
        }

        type EventParams = {
            items: KnockoutObservableArray<any>;
            mode: KnockoutComputed<boolean>;
        };
    }

    type Employee = {
        code: string;
        name: string;
        selected: boolean;
    };
}

/*
copyDay(from: Date, to: Date) {
    const vm = this;
    const events = ko.unwrap(vm.events);

    const exists = events.filter(({ start }) => moment(start).isSame(to, 'date'));

    const processCopy = () => {
        // change date to destination
        const updateTime = (date: Date) => moment(to).set('hour', date.getHours()).set('minute', date.getMinutes()).toDate();

        const source = ko.unwrap(events)
            .filter((e: any) => moment(e.start).isSame(from, 'date'))
            .map(({
                start,
                end,
                title,
                backgroundColor,
                extendedProps
            }) => ({
                start: updateTime(start),
                end: updateTime(end),
                title,
                backgroundColor,
                extendedProps: {
                    ...extendedProps,
                    id: randomId(),
                    status: 'new'
                }
            }));

        vm.events.push(...source);
    };

    if (exists.length) {
        // exist data
        vm.$dialog
            .confirm({ messageId: 'OVERWRITE_CONFIRM' })
            .then((v: 'yes' | 'no') => {
                if (v === 'yes') {
                    // remove events of destionation day
                    vm.events.remove((e: any) => moment(e.start).isSame(to, 'date'));

                    processCopy();
                }
            });
    } else {
        // no overwrite data
        processCopy();
    }
}
*/