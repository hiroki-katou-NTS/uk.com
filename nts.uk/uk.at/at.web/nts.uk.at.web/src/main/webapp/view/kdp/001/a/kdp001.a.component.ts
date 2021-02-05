module nts.uk.ui.kdp001.a {

    interface TimeClock {
        tick: number;
        now: KnockoutObservable<Date>;
    }

    const initTime = (): TimeClock => ({ tick: -1, now: ko.observable(new Date()) });

    @component({
        name: 'kdp-001-a',
        template: `
            <div class="kdp-001-a widget-title">
                <table>
                    <colgroup>
                        <col width="50%" />
                        <col width="50%" />
                    </colgroup>
                    <thead>
                        <tr>
                            <th class="text-center" data-bind="date: $component.time.now, format: 'YYYY/MM/DD(ddd)'"></th>
                            <th class="text-center" data-bind="date: $component.time.now, format: 'HH:mm:ss'"></th>
                        </tr>
                    </thead>
                </table>
            </div>
            <div class="kdp-001-a" data-bind="widget-content: $component.height">
                <div data-bind="component: { name: $component.viewMode, params: {} }"></div>
            </div>
            <style rel="stylesheet">
                .kdp-001-a.widget-title {
                    border-left-width: 0px;
                }
                .kdp-001-a .text-center {
                    text-align: center;
                }
                .kdp-001-a.widget-title th {
                    font-size: 16px;
                    font-weight: 700;
                }
            </style>
        `
    })
    export class KDP001WidgetComponent extends ko.ViewModel {
        widget: string = 'KDP001A';

        time: TimeClock = initTime();

        height!: KnockoutComputed<number>;
        viewMode!: KnockoutComputed<string>;

        constructor(private mode: 'a' | 'b' | 'c' | 'd' | KnockoutObservable<'a' | 'b' | 'c' | 'd'> = 'a') {
            super();

            const vm = this;

            vm.height = ko.computed({
                read: () => {
                    switch (ko.unwrap<'a' | 'b' | 'c' | 'd'>(this.mode)) {
                        case 'a':
                        case 'b':
                            return 300;
                        case 'c':
                        case 'd':
                            return 100;
                    }
                }
            });

            vm.viewMode = ko.computed({
                read: () => {
                    return `kdp001a-m${ko.unwrap<'a' | 'b' | 'c' | 'd'>(this.mode)}`;
                }
            });
        }

        created() {
            const vm = this;

            vm.$date.interval(1000);

            vm.time.tick = setInterval(() => vm.time.now(vm.$date.now()), 500);
        }

        mounted() {
            const vm = this;

            $(vm.$el)
                .removeAttr('data-bind')
                .find('[data-bind]')
                .removeAttr('data-bind');
        }

        destroyed() {
            const vm = this;

            vm.height.dispose();
            vm.viewMode.dispose();

            clearInterval(vm.time.tick);
        }
    }

    export module components {
        @component({
            name: 'kdp001a-ma',
            template: `a`
        })
        export class KDP001AComponent extends ko.ViewModel {

        }

        @component({
            name: 'kdp001a-mb',
            template: `b`
        })
        export class KDP001BComponent extends ko.ViewModel {

        }

        @component({
            name: 'kdp001a-mc',
            template: `b`
        })
        export class KDP001CComponent extends ko.ViewModel {

        }

        @component({
            name: 'kdp001a-md',
            template: `d`
        })
        export class KDP001DComponent extends ko.ViewModel {

        }
    }
}