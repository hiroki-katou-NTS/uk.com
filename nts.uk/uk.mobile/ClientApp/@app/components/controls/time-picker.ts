import { TimeWithDay, TimePoint, TimeDuration, DAYS } from '@app/utils/time';
import { _ } from '@app/provider';

export class TimeWithDayHelper {

    public static getDataSource(value: number) {

        return {
            day: TimeWithDayHelper.Days, 
            hour: value < 0 ? TimeWithDayHelper.HoursFrom12 : TimeWithDayHelper.HoursFrom0, 
            minute: TimeWithDayHelper.Minutes
        };
    }

    public static Days: Array<Object> = [
        {
            text: DAYS.TheDayBefore,
            value: -1
        }, {
            text: DAYS.Today,
            value: 0
        }, {
            text: DAYS.NextDay,
            value: 1
        }, {
            text: DAYS.TwoDaysLater,
            value: 2
        }
    ];
    public static HoursFrom0: Array<Object> = TimeWithDayHelper.generateArray(0, 23);
    public static HoursFrom12: Array<Object> = TimeWithDayHelper.generateArray(12, 23);
    public static Minutes: Array<Object> = TimeWithDayHelper.generateArray(0, 59);

    public static generateArray(min: number, max: number): Array<Object> {

        return _.range(min, max).map((m: number) => ({ text: _.padStart(`${m}`, 2, '0'), value: m }));

    }


    public static computeSelecteds(value: number): Object {

        if (value === null) {

            return {
                day: 0,
                hour: 8,
                minute: 0
            };
        }

        return TimeWithDay.toObject(value);
    }

    public static onSelect(value: any, picker: { title: string, dataSources: any, selects: any }) {
        if (value.day === -1) {

            if (picker.dataSources.hour.length !== 12) {
                picker.dataSources.hour = TimeWithDayHelper.HoursFrom12;
            }

            if (value.hour < 12) {
                picker.selects.hour = 12;
            }

        } else {

            if (picker.dataSources.hour.length !== 24) {
                picker.dataSources.hour = TimeWithDayHelper.HoursFrom0;
            }
            
        }
    }
}

export class TimePointHelper {

    public static H1_0_7: Array<Object> = TimePointHelper.generateArray(0, 7);
    public static H1_1_2: Array<Object> = TimePointHelper.generateArray(1, 2);

    public static H2_2_9: Array<Object> = TimePointHelper.generateArray(2, 9);
    public static H2_0_3: Array<Object> = TimePointHelper.generateArray(0, 3);
    public static H2_0_9: Array<Object> = TimePointHelper.generateArray(0, 9);
    public static H2_0_1: Array<Object> = TimePointHelper.generateArray(0, 1);

    public static M1_LIST: Array<Object> = TimePointHelper.generateArray(0, 5);
    public static M2_LIST: Array<Object> = TimePointHelper.generateArray(0, 9);

    public static computeSelecteds(value: number): Object {

        if (value === null) {

            return {
                positive: true,
                h1: 0,
                h2: 8,
                m1: 0,
                m2: 0
            };
        }

        return TimePoint.toObject(value);
    }

    public static getDataSource(value: number) {

        return {
            positive: [ { text: ' ', value: true}, { text: 'ー', value: false}],
            h1: TimePointHelper.h1List(value),
            h2: TimePointHelper.h2List(value),
            m1: TimePointHelper.M1_LIST,
            m2: TimePointHelper.M2_LIST
        };
    }

    public static generateArray(min: number, max: number): Array<Object> {

        return _.range(min, max + 1).map(( number: number) => ({ text: number.toString(), value: number }));

    }

    public static h1List(value: number) {
        return value >= 0 ? this.H1_0_7 : this.H1_1_2;
    }

    public static h2List(value: number) {
        if (value <= -241) {
            return TimePointHelper.H2_2_9;
        } else if ( value < 0 ) {
            return TimePointHelper.H2_0_3;
        } else if (value <= 4199 ) {
            return TimePointHelper.H2_0_9;
        } else {
            return TimePointHelper.H2_0_1;
        }
    }

    public static onSelect(newSelect: { positive: boolean, h1: number, h2: number, m1: number, m2: number}, picker: { dataSources: any, selects: any }) {
        let pickerSelect = picker.selects;

        if (!newSelect.positive) {
            // filter h1 with positive
            if (picker.dataSources.h1.length !== 2) {
                picker.dataSources.h1 = TimePointHelper.H1_1_2;
            }

            if ( Array.of(1, 2).indexOf(newSelect.h1) === -1) {
                pickerSelect.h1 = 1;
            }

            // filter h2 with h1
            if (pickerSelect.h1 === 1) {
                if (picker.dataSources.h2.length !== 8) {
                    picker.dataSources.h2 = TimePointHelper.H2_2_9;

                    if (Array.of(2, 3, 4, 5, 6, 7, 8, 9).indexOf(pickerSelect.h2) === -1) {
                        pickerSelect.h2 = 2;
                    }
                }
            } else {
                if (picker.dataSources.h2.length !== 4) {
                    picker.dataSources.h2 = TimePointHelper.H2_0_3;

                    if ( Array.of( 4, 5, 6, 7, 8, 9).indexOf(pickerSelect.h2) !== -1) {
                        pickerSelect.h2 = 0;
                    }
                }
            }

        } else {

            // filter h1 with positive
            if ( picker.dataSources.h1.length !== 8) {
                picker.dataSources.h1 = TimePointHelper.H1_0_7;
            }

            // filter h2 with h1
            if ( pickerSelect.h1 === 7) {
                if (pickerSelect.h2.length !== 2) {
                    picker.dataSources.h2 = TimePointHelper.H2_0_1;
                }

                if (Array.of(0, 1).indexOf(pickerSelect.h2) === -1) {
                    pickerSelect.h2 = 0;
                }

            } else  {
                if (pickerSelect.h2.length !== 10) {
                    picker.dataSources.h2 = TimePointHelper.H2_0_9;
                }
            }
        }
    }
}

export class TimeDurationHelper {

    public static H1_0_1: Array<Object> = TimeDurationHelper.generateArray(0, 1);
    public static H1_0_7: Array<Object> = TimeDurationHelper.generateArray(0, 7);

    public static H2_0_2: Array<Object> = TimeDurationHelper.generateArray(0, 2);
    public static H2_0_9: Array<Object> = TimeDurationHelper.generateArray(0, 9);
    public static H2_0_1: Array<Object> = TimeDurationHelper.generateArray(0, 1);

    public static M1_LIST: Array<Object> = TimeDurationHelper.generateArray(0, 5);
    public static M2_LIST: Array<Object> = TimeDurationHelper.generateArray(0, 9);

    public static generateArray(min: number, max: number): Array<Object> {

        return _.range(min, max + 1).map(( number: number) => ({ text: number.toString(), value: number }));

    }

    public static computeSelecteds(value: number): Object {

        if (value === null) {

            return {
                positive: true,
                h1: 0,
                h2: 8,
                m1: 0,
                m2: 0
            };
        }

        return TimeDuration.toObject(value);
    }

    public static getDataSource(value: number) {

        return {
            positive: [ { text: ' ', value: true}, { text: 'ー', value: false}],
            h1: TimeDurationHelper.h1List(value),
            h2: TimeDurationHelper.h2List(value),
            m1: TimeDurationHelper.M1_LIST,
            m2: TimeDurationHelper.M2_LIST
        };
    }

    public static h1List(value: number) {
        return value < 0 ? TimeDurationHelper.H1_0_1 : TimeDurationHelper.H1_0_7;
    }

    public static h2List(value: number) {
        if (value < -600) {
            return TimeDurationHelper.H2_0_2;
        } else if (value < 4199) {
            return TimeDurationHelper.H2_0_9;
        } else {
            return TimeDurationHelper.H2_0_1;
        }
    }

    public static onSelect(newSelect: { positive: boolean, h1: number, h2: number, m1: number, m2: number}, picker: { dataSources: any, selects: any }) {
        let pickerSelect = picker.selects;

        if (!newSelect.positive) {
            // filter h1 with positive
            if (picker.dataSources.h1.length !== 2) {
                picker.dataSources.h1 = TimeDurationHelper.H1_0_1;
            }

            if ( Array.of(1, 0).indexOf(newSelect.h1) === -1) {
                pickerSelect.h1 = 1;
            }

            // filter h2 with h1
            if (pickerSelect.h1 === 1) {
                if (picker.dataSources.h2.length !== 3) {
                    picker.dataSources.h2 = TimeDurationHelper.H2_0_2;

                    if (Array.of(2, 1, 0).indexOf(pickerSelect.h2) === -1) {
                        pickerSelect.h2 = 2;
                    }
                }
            } else {
                if (picker.dataSources.h2.length !== 10) {
                    picker.dataSources.h2 = TimeDurationHelper.H2_0_9;
                }
            }

        } else {

            // filter h1 with positive
            if ( picker.dataSources.h1.length !== 8) {
                picker.dataSources.h1 = TimeDurationHelper.H1_0_7;
            }

            // filter h2 with h1
            if ( pickerSelect.h1 === 7) {
                if (pickerSelect.h2.length !== 2) {
                    picker.dataSources.h2 = TimeDurationHelper.H2_0_1;
                }

                if (Array.of(0, 1).indexOf(pickerSelect.h2) === -1) {
                    pickerSelect.h2 = 0;
                }

            } else  {
                if (pickerSelect.h2.length !== 10) {
                    picker.dataSources.h2 = TimeDurationHelper.H2_0_9;
                }
            }
        }
    }
    
}

