import { Vue } from '@app/provider';
import { component, Watch } from '@app/core/component';

import { MobilePicker } from '@app/components/picker';
import { InfinityPickerComponent } from '@app/components';
@component({
    route: '/',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    components: {
        'mpkr': MobilePicker,
        'ipkr': InfinityPickerComponent
    },
    validations: {
        model: {
            numbers: {
                decimal: {
                    range: {
                        constraint: 'SampleDecimalRange'
                    },
                    minmax: {
                        constraint: 'SampleDecimalMinMax'
                    }
                },
                halfint: {
                    range: {
                        constraint: 'SampleHalfIntRange'
                    },
                    minmax: {
                        constraint: 'SampleHalfIntMinMax'
                    }
                },
                int: {
                    range: {
                        constraint: 'SampleIntRange'
                    },
                    minmax: {
                        constraint: 'SampleIntMinMax'
                    }
                },
                long: {
                    range: {
                        constraint: 'SampleLongRange'
                    },
                    minmax: {
                        constraint: 'SampleLongMinMax'
                    }
                }
            },
            strings: {
                alphaNumberic: {
                    constraint: 'SampleStringAlphaNumeric'
                },
                any: {
                    constraint: 'SampleStringAny'
                },
                anyhalf: {
                    constraint: 'SampleStringAnyHalf'
                },
                kana: {
                    constraint: 'SampleStringKana'
                },
                numeric: {
                    constraint: 'SampleStringNumeric'
                }
            },
            times: {
                clock: {
                    range: {
                        constraint: 'SampleClockRange'
                    },
                    minmax: {
                        constraint: 'SampleClockMinMax'
                    }
                },
                duration: {
                    range: {
                        constraint: 'SampleDurationRange'
                    },
                    minmax: {
                        constraint: 'SampleDurationMinMax'
                    }
                }
            }
        }
    },
    constraints: [
        'nts.uk.shr.sample.primitive.decimals.SampleDecimalMinMax',
        'nts.uk.shr.sample.primitive.decimals.SampleDecimalRange',
        'nts.uk.shr.sample.primitive.halfints.SampleHalfIntMinMax',
        'nts.uk.shr.sample.primitive.halfints.SampleHalfIntRange',
        'nts.uk.shr.sample.primitive.ints.SampleIntMinMax',
        'nts.uk.shr.sample.primitive.ints.SampleIntRange',
        'nts.uk.shr.sample.primitive.longs.SampleLongMinMax',
        'nts.uk.shr.sample.primitive.longs.SampleLongRange',

        'nts.uk.shr.sample.primitive.strings.SampleStringAlphaNumeric',
        'nts.uk.shr.sample.primitive.strings.SampleStringAny',
        'nts.uk.shr.sample.primitive.strings.SampleStringAnyHalf',
        'nts.uk.shr.sample.primitive.strings.SampleStringKana',
        'nts.uk.shr.sample.primitive.strings.SampleStringNumeric',

        'nts.uk.shr.sample.primitive.times.SampleClockMinMax',
        'nts.uk.shr.sample.primitive.times.SampleClockRange',
        'nts.uk.shr.sample.primitive.times.SampleDurationMinMax',
        'nts.uk.shr.sample.primitive.times.SampleDurationRange'
    ]
})
export class HomeComponent extends Vue {
    public show: boolean = false;
    public selecteds = {
        year: 2019,
        month: 1,
        day: 2
    };

    public years = [];

    public dataSources = {
        year: [],
        month: [],
        day: []
    };

    @Watch('show', { deep: true })
    public showWatcher(show: boolean) {
        if (show) {
            document.body.classList.add('modal-open');
        } else {
            document.body.classList.remove('modal-open');
        }
    }

    public created() {
        for (let i = 1900; i <= 2099; i++) {
            this.years.push(i);
            this.dataSources.year.push({ text: `${i}年`, value: i });
        }

        for (let i = 1; i <= 12; i++) {
            this.dataSources.month.push({ text: `${i}`, value: i });
        }

        for (let i = 1; i <= 31; i++) {
            this.dataSources.day.push({ text: `${i}`, value: i });
        }
    }

    public tets: number = 0;
    public viewPortClicked(value: any) {
        //console.log(value);

        this.tets = value;
    }

    /*public mounted() {
        let transY = -200,
            mtop = 0,
            count = 0,
            scr = this.$refs.scroll as HTMLElement,
            emulate = () => {
                scr.setAttribute('style', `transform: translateY(${transY}px); margin-top: ${mtop}px;`);

                if (transY >= -440) {
                    setTimeout(emulate, 200);
                }

                transY -= 1;
                count += 1;

                if (count == 40 || (count == 20 && transY >= -220)) {
                    mtop += 40;
                    count = 0;
                }
            };

        scr.addEventListener('click', () => {
            transY = -200;
            mtop = 0;
            count = 0;
        });

        emulate();
    }*/

    public showPicker() {

        this.$picker({
            h1: 0,
            h2: 1,
            m1: 0,
            m2: 0
        },
            {
                h1: [{
                    text: '0',
                    value: 0
                }, {
                    text: '1',
                    value: 1
                }, {
                    text: '2',
                    value: 2
                }],
                h2: [{
                    text: '0',
                    value: 0
                }, {
                    text: '1',
                    value: 1
                }, {
                    text: '2',
                    value: 2
                }],
                m1: [{
                    text: '0',
                    value: 0
                }, {
                    text: '1',
                    value: 1
                }, {
                    text: '2',
                    value: 2
                }],
                m2: [{
                    text: '0',
                    value: 0
                }, {
                    text: '1',
                    value: 1
                }, {
                    text: '2',
                    value: 2
                }]
            }, {
                onSelect() {
                    // sự kiện select ở đây
                }
            }).then((v) => {
                console.log(v);
            });
    }

    public onPan(evt: TouchEvent) {
        console.log(evt);

        evt.preventDefault();
    }
}