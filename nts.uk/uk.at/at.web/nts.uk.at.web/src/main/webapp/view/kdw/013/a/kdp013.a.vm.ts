module nts.uk.ui.at.kdp013.a {
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

    const DATE_FORMAT = 'YYYY-MM-DD';

    @bean()
    export class ViewModel extends ko.ViewModel {
        events: KnockoutObservableArray<any> = ko.observableArray([{
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
        }]);

        dragItems: KnockoutObservableArray<any> = ko.observableArray([{
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
        }]);

        employees: KnockoutObservableArray<any> = ko.observableArray([{
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
        }]);

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
        scrollTime: KnockoutObservable<number> = ko.observable(420);
        slotDuration: KnockoutObservable<number> = ko.observable(30);
        initialDate: KnockoutObservable<Date> = ko.observable(new Date());
        initialView: KnockoutObservable<string> = ko.observable('fullWeek');
        availableView: KnockoutObservableArray<string> = ko.observableArray(['oneDay', 'fullWeek']);
        validRange: KnockoutObservable<{ start: Date; end: Date; }> = ko.observable({ start: moment('2021-02-12', DATE_FORMAT).toDate(), end: moment('2021-10-21', DATE_FORMAT).toDate() });

        attendanceTimes: KnockoutObservableArray<any> = ko.observableArray([{
            date: moment().toDate(),
            events: [
                '勤怠　8:00 ~ 17:00',
                '休憩　1:00',
                '総労働時間　8:00'
            ]
        }, {
            date: moment().add(2, 'day').toDate(),
            events: [
                '勤怠　8:00 ~ 17:00',
                '休憩　1:00',
                '総労働時間　8:00'
            ]
        }, {
            date: moment().add(3, 'day').toDate(),
            events: [
                '勤怠　8:00 ~ 17:00',
                '休憩　1:00',
                '総労働時間　8:00'
            ]
        }]);

        show: KnockoutObservable<boolean> = ko.observable(true);

        changeVisible() {
            const vm = this;

            vm.show(!vm.show());
        }

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

        datesSet(start: Date, end: Date) {
            console.log(start, end);
        }

        mounted() {
            const vm = this;

            _.extend(window, { vm });
        }
    }

    export module components {
        @component({
            name: 'fc-event-editor',
            template:
                `<div class="fc-editor-container">
                    <div class="toolbar">
                        <svg width="20" height="20" viewBox="0 0 24 24" focusable="false" title="Email">
                            <path d="M20 4H4c-1.1 0-2 .9-2 2v12c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V6c0-1.1-.9-2-2-2zm-.8 2L12 10.8 4.8 6h14.4zM4 18V7.87l8 5.33 8-5.33V18H4z"></path>
                        </svg>
                        <svg width="20" height="20" viewBox="0 0 24 24" focusable="false" title="Edit" data-bind="click: edit, timeClick: -1">
                            <path d="M20.41 4.94l-1.35-1.35c-.78-.78-2.05-.78-2.83 0L3 16.82V21h4.18L20.41 7.77c.79-.78.79-2.05 0-2.83zm-14 14.12L5 19v-1.36l9.82-9.82 1.41 1.41-9.82 9.83z"></path>
                        </svg>
                        <svg width="20" height="20" viewBox="0 0 24 24" focusable="false" title="Remove" data-bind="click: remove, timeClick: -1">
                            <path d="M15 4V3H9v1H4v2h1v13c0 1.1.9 2 2 2h10c1.1 0 2-.9 2-2V6h1V4h-5zm2 15H7V6h10v13z"></path>
                            <path d="M9 8h2v9H9zm4 0h2v9h-2z"></path>
                        </svg>
                        <svg width="20" height="20" viewBox="0 0 24 24" focusable="false" title="Close" data-bind="click: $component.close, timeClick: -1">
                            <path d="M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12 19 6.41z"></path>
                        </svg>
                    </div>
                    <div>
                    </div>
                </div>
                <style>
                    .fc-editor-container {
                        width: 300px;
                    }
                </style>`
        })
        export class FullCalendarEventDetail extends ko.ViewModel {
            constructor(private params: any) {
                super();
            }

            edit() {

            }

            remove() {
                const vm = this;
                const { params } = vm;

                params.remove();
            }

            close() {
                const vm = this;
                const { params } = vm;

                params.close();
            }
        }

        type COPYDAY_PARAMS = {
            close: () => void;
            data: KnockoutObservable<{
                from: Date;
                to: Date
            }>;
        };

        @component({
            name: 'fc-event-copy-day',
            template:
                `<div>
                    <table class="copy-day">
                        <tr>
                            <td>
                                <div data-bind="ntsFormLabel: {}">From</div>
                            </td>
                            <td>
                                <div data-bind="ntsDatePicker: { value: $component.model.from }"></div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div data-bind="ntsFormLabel: {}">To</div>
                            </td>
                            <td>
                                <div data-bind="ntsDatePicker: { value: $component.model.to }"></div>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2" style="text-align: center">
                                <button class="small" data-bind="i18n: 'Copy', click: $component.copy"></button>
                                <button class="small" data-bind="i18n: 'Close', click: $component.close"></button>
                            </td>
                        </tr>
                    </table>
                    <style>
                        table.copy-day,
                        table.copy-day td {
                            padding: 0;
                            padding-bottom: 5px;
                            border: 0px solid transparent;
                        }
                        table.copy-day tr:last-child td {
                            padding-bottom: 0;
                        }
                    </style>
                </div>`
        })
        export class FullCalendarEventCopyDay extends ko.ViewModel {
            public model: {
                from: KnockoutObservable<Date | null>;
                to: KnockoutObservable<Date | null>;
            } = {
                    from: ko.observable(null),
                    to: ko.observable(null)
                };

            constructor(private params: COPYDAY_PARAMS) {
                super();
            }

            copy() {
                const vm = this;
                const { model, params } = vm;
                const data = ko.toJS(model);

                // bind value to model
                params.data({
                    from: moment(data.from, 'YYYY/MM/DD').toDate(),
                    to: moment(data.to, 'YYYY/MM/DD').toDate()
                });

                params.close();
            }

            // close dialog (call from parent component)
            close() {
                const vm = this;
                const { params } = vm;

                params.close();
            }
        }
    }
}