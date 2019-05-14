import { _ } from '@app/provider';

export const time = {
    timewd: {
        computeDay(value: number): string {
            if (value < 0) {
                return DAYS.TheDayBefore;
            } else if (value < 1439) {
                return DAYS.Today;
            } else if (value < 2879) {
                return DAYS.NextDay;
            } else if (value < 4319) {
                return DAYS.TwoDaysLater;
            }
        },
        computeHour(value: number): number {
            let minutesInDay = 0;

            if (value >= 0) {
                minutesInDay = value % 1440;
            } else {
                minutesInDay = 1440 + value;
            }

            return Math.floor(minutesInDay / 60);
        },
        computeMinute(value: number): number {
            let minutesInDay = 0;
            if (value >= 0) {
                minutesInDay = value % 1440;
            } else {
                minutesInDay = 1440 + value;
            }
            let hour = Math.floor(minutesInDay / 60);
            return minutesInDay - hour * 60;
        },
        computeValue(newDay: string, newHour: number, newMinute: number): number {
            let newMinutes = 0;
            switch (newDay) {
                case DAYS.TheDayBefore:
                    newMinutes = (newHour * 60 + newMinute) - 1440;
                    break;
                case DAYS.Today:
                    newMinutes = newHour * 60 + newMinute;
                    break;
                case DAYS.NextDay:
                    newMinutes = 1440 + newHour * 60 + newMinute;
                    break;
                case DAYS.TwoDaysLater:
                    newMinutes = 2880 + newHour * 60 + newMinute;
                    break;
            }
            return newMinutes;
        },
        computeTimePoint(value: number): TimeWithDayPoint {
            let day = time.timewd.computeDay(value);
            let hour = time.timewd.computeHour(value);
            let minute = time.timewd.computeMinute(value);
            return {
                day, hour, minute
            };
        },
        toString(value: number): string {
            let timePoint = time.timewd.computeTimePoint(value);
            return timePoint.day + ' ' + time.leftpad(timePoint.hour) + ' : ' + time.leftpad(timePoint.minute);
        }
    },
    timedr: {
        computeHour(value: number): number {
            if (value >= 0) {
                return Math.floor(value / 60);
            } else {
                return 0 - Math.floor(Math.abs(value) / 60);
            }
        },
        computeMinute(value: number): number {
            if (value > 0) {
                let hour = Math.floor(value / 60);
                return value - hour * 60;
            } else {
                let hour = 0 - Math.floor(Math.abs(value) / 60);
                if (hour == 0) {
                    return value;
                }
                return Math.abs(value) + hour * 60;
            }
        },
        computeTimePoint(value: number): TimeDurationPoint {
            let hour = 0;
            let minute = 0;
            if (value >= 0) {
                hour = Math.floor(value / 60);
                minute = value - hour * 60;
            } else {
                hour = 0 - Math.floor(Math.abs(value) / 60);
                minute = Math.abs(value) + hour * 60;
            }
            return {
                hour,
                minute
            };
        },
        computeValue(newHour: number, newMinute: number): number {
            if (newHour >= 0) {
                return newHour * 60 + newMinute;
            } else {
                return newHour * 60 - newMinute;
            }
        },
        toString(value: number) {
            let timePoint = time.timedr.computeTimePoint(value);

            if (timePoint.hour == 0 && value < 0) {
                return '-' + time.leftpad(timePoint.hour) + ' : ' + time.leftpad(timePoint.minute);
            }

            return time.leftpad(timePoint.hour) + ' : ' + time.leftpad(timePoint.minute);
        }

    },
    timept: {
        computeHour(value: number): number {
            if (value >= 0) {
                return Math.floor(value / 60);
            } else {
                return - Math.floor((1440 + value) / 60);
            }
        },
        computeMinute(value: number): number {
            let hour = 0;
            if (value >= 0) {
                hour = Math.floor(value / 60);
                return value - hour * 60;
            } else {
                let minutesInDay = 1440 + value;
                hour = Math.floor(minutesInDay / 60);

                return minutesInDay - hour * 60;
            }
        },
        computeTimePoint(value: number): TimePoint {
            let hour = 0;
            let minute = 0;

            if (value >= 0) {
                hour = Math.floor(value / 60);
                minute = value - hour * 60;

                return { hour, minute };
            } else {
                let minutesInDay = 1440 + value;

                hour = Math.floor(minutesInDay / 60);
                minute = minutesInDay - hour * 60;

                return { hour: -hour, minute };
            }
        },
        computeValue(newHour: number, newMinute: number): number {
            if (newHour >= 0) {
                return newHour * 60 + newMinute;
            } else {
                return -1440 - newHour * 60 + newMinute;
            }
        },
        toString(value: number) {
            let timePoint = time.timept.computeTimePoint(value);
            return time.leftpad(timePoint.hour) + ' : ' + time.leftpad(timePoint.minute);
        }
    },
    leftpad(value: number): string {
        if (value >= 0 && value < 10) {
            return '0' + value;
        } else if (value < 0 && value > -10) {
            return '-0' + (0 - value);
        }

        return '' + value;
    }
};

export enum DAYS {
    TheDayBefore = '前日',
    Today = '当日',
    NextDay = '翌日',
    TwoDaysLater = '翌々日',
}

export interface TimeWithDayPoint {
    day: string;
    hour: number;
    minute: number;
}

export interface TimePoint {
    hour: number;
    minute: number;
}

export interface TimeDurationPoint {
    hour: number;
    minute: number;
}

export enum TimeInputType {
    TimeWithDay = 'time-with-day',
    TimePoint = 'time-point',
    TimeDuration = 'time-duration'
}


export class TimeWithDay {
    public value: number | undefined = undefined;

    constructor(value: string | number) {
        if (typeof value === 'number') {
            this.value = value;
        } else {
            this.value = this.toNumber(value);
        }
    }

    public static from(value: string | number) {
        return new TimeWithDay(value);
    }

    public static toNumber(value: string): number {
        return TimeWithDay.from(value).value;
    }

    public static toString(value: number): string {
        return TimeWithDay.from(value).toString();
    }

    public static getDayOffset(value: string | number): number {
        return TimeWithDay.from(value).day;
    }

    public static getDayName(value: string | number): string {
        return TimeWithDay.from(value).dayName;
    }

    public static getHour(value: string | number): number {
        return TimeWithDay.from(value).hour;
    }

    public static getMinute(value: string | number): number {
        return TimeWithDay.from(value).minute;
    }

    public toNumber(value?: string): number {
        if (value === undefined) {
            return this.value;
        }

        let negative: boolean = value.indexOf('-') == 0,
            rawNumber: number = Number(value.replace(/[\:\-]/g, '')),
            hour: number = Math.floor(rawNumber / 100),
            minute: number = Math.floor(rawNumber % 100);

        if (!negative) {
            return 60 * hour + minute;
        } else {
            return -(1440 - (60 * hour + minute));
        }
    }

    public toString(): string {
        let negative = this.isNegative,
            value = Math.abs(this.value + (negative ? 1440 : 0)),
            hour = Math.floor(value / 60).toString(),
            minute = Math.floor(value % 60).toString();

        return `${negative ? '-' : ''}${_.padStart(hour, 2, '0')}:${_.padStart(minute, 2, '0')}`;
    }

    public toLocalString() {
        return `{${this.dayName}}${_.padStart(this.hour.toString(), 2, '0')}:${_.padStart(this.minute.toString(), 2, '0')}`;
    }

    get isNegative(): boolean {
        return this.day === -1;
    }

    get day(): number {
        if (this.value < 0) {
            return -1;  //  前日
        } else if (this.value < 1440) {
            return 0;   //  当日
        } else if (this.value < 2880) {
            return 1;   //  翌日
        } else if (this.value < 4320) {
            return 2;   //  翌々日
        }

        return 0;
    }

    get dayName(): string {
        switch (this.day) {
            case -1:
                return 'BeforeToday';
            case 0:
                return 'Today';
            case 1:
                return 'NextDay';
            case 2:
                return 'TwoDaysLater';
        }
    }

    get hour(): number {
        return Math.floor(this.positiveValue / 60);
    }

    get minute(): number {
        return Math.floor(this.positiveValue % 60);
    }

    private get positiveValue() {
        let value = this.value;

        switch (this.day) {
            case -1:
                value += 1440;
                break;
            case 0:
                value += 0;
                break;
            case 1:
                value -= 1440;
                break;
            case 2:
                value -= 2880;
                break;
        }

        return Math.abs(value);
    }
}


export class TimeDuration {
    public value: number | undefined = undefined;

    constructor(value: string | number) {
        if (typeof value === 'number') {
            this.value = value;
        } else {
            this.value = this.toNumber(value);
        }
    }

    public static from(value: string | number) {
        return new TimeDuration(value);
    }

    public toNumber(value?: string) {
        if (value) {
            let negative: boolean = value.indexOf('-') == 0,
                rawNumber: number = Number(value.replace(/[\:\-]/g, '')),
                hour: number = Math.floor(rawNumber / 100),
                minute: number = Math.floor(rawNumber % 100);

            if (!negative) {
                return hour * 60 + minute;
            } else {
                return -(hour * 60 + minute);
            }
        }

        return this.value;
    }

    public toString() {
        return `${this.isNegative ? '-' : ''}${_.padStart(this.hour.toString(), 2, '0')}:${_.padStart(this.minute.toString(), 2, '0')}`;
    }

    public toLocalString() {
        return this.toString();
    }

    get isNegative() {
        return this.value < 0;
    }

    get hour() {
        return Math.floor(this.positiveValue / 60);
    }

    get minute() {
        return Math.floor(this.positiveValue % 60);
    }

    private get positiveValue() {
        return Math.abs(this.value);
    }
}