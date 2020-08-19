import { _, Vue, VueConstructor, moment } from '@app/provider';
import { TimeWithDay, TimePoint, TimeDuration } from '@app/utils/time';

const YM_FORMAT: string = 'YYYY/MM',
    DATE_FORMAT: string = 'YYYY/MM/DD';

const datetime = {
    install(vue: VueConstructor<Vue>) {
        const $date = vue.observable({ now: new Date() });

        setInterval(() => {
            const $diff = _.get(vue, '$sdt') as number;
            $date.now = moment().add($diff, 'ms').toDate();
        }, 100);

        vue.mixin({
            beforeCreate() {
                let self = this;

                Object.assign(self, {
                    $dt(d: Date, format: string) {
                        return moment(d).format(format || DATE_FORMAT);
                    }
                });

                Object.defineProperty(self.$dt, 'now', { get: () => $date.now });

                Object.assign(self.$dt, {
                    date(d: Date, format: string) {
                        return moment(d).format(format || DATE_FORMAT);
                    },
                    fromString(value: string, format: string) {
                        return moment(value, format || DATE_FORMAT).toDate();
                    },
                    fromUTCString(value: string, format: string) {
                        return moment.utc(value, format || DATE_FORMAT).toDate();
                    },
                    timewd(value: number) {
                        return TimeWithDay.toString(value);
                    },
                    timept(value: number) {
                        return TimePoint.toString(value);
                    },
                    timedr(value: number) {
                        return TimeDuration.toString(value);
                    },
                    yearmonth(d: number, format?: string) {
                        if (_.isNumber(d) && !_.isNaN(d)) {
                            let year: number = Math.floor(d / 100),
                                month: number = Math.floor(d % 100);

                            if (year && month) {
                                return moment(`${d}`, 'YYYYMM').format(format || 'YYYY/MM');
                            }
                        }

                        return d;
                    }
                });
            }
        });
    }
};

Vue.use(datetime);