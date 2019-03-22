export const time = {
    timewd : {
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
            var minutesInDay = 0;
            if (value >= 0) {
                minutesInDay = value % 1440;
            } else {
                minutesInDay = 1440 + value;
            }
            return Math.floor(minutesInDay / 60);
        },
        computeMinute(value: number): number {
            var minutesInDay = 0;
            if (value >= 0) {
                minutesInDay = value % 1440;
            } else {
                minutesInDay = 1440 + value;
            }
            var hour = Math.floor(minutesInDay / 60);
            return minutesInDay - hour * 60;
        }, 
        computeValue(newDay: string, newHour: number, newMinute: number): number {
            var newMinutes = 0;
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
        computeTimePoint(value: number): TimePoint {
            var day = time.timewd.computeDay(value);
            var hour = time.timewd.computeHour(value);
            var minute = time.timewd.computeMinute(value);
            return {
                day, hour, minute
            }
        }
    }, 
    timedr: {
        computeHour(value: number): number {
            if (value >= 0) {
                return Math.floor(value / 60)
            } else {
                return 0 - Math.floor(Math.abs(value) / 60);
            }
        },
        computeMinute(value: number): number {
            if (value >= 0) {
                var hour = Math.floor(value / 60)
                return value - hour * 60;
            } else {
                var hour = 0 - Math.floor(Math.abs(value) / 60);
                return Math.abs(value) + hour * 60;
            }
        },
        computeTimePoint(value: number): TimeDurationPoint {
            var hour = 0;
            var minute = 0;
            if (value >= 0) {
                hour = Math.floor(value / 60)
                minute = value - hour * 60;
            } else {
                hour = 0 - Math.floor(Math.abs(value) / 60);
                minute = Math.abs(value) + hour * 60;
            }
            return {
                hour,
                minute
            }
        },
        computeValue(newHour: number, newMinute: number): number {
            if (newHour >= 0 ) {
                return newHour * 60 + newMinute;
            } else {
                return newHour * 60 - newMinute;
            }
        }

    }
};

export enum DAYS {
    TheDayBefore = "前日",
    Today = "当日",
    NextDay = "翌日",
    TwoDaysLater = "翌々日",
}


export interface TimePoint {
    day: string;
    hour: number;
    minute: number;
}

export interface TimeDurationPoint {
    hour: number;
    minute: number;
}

export enum TimeInputType {
    TimeDuration = 'time-duration',
    TimePoint = 'time-point',
    TimeWithDay = 'time-with-day'
}