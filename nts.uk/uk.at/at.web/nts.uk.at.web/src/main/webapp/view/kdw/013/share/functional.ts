module nts.uk.ui.at.kdp013.share {
    const { time } = nts.uk as any;
    const { byId } = time.format as { byId: (format: string, time: number) => string };

    // get time as minute in date
    export const getTimeOfDate = (date: Date) => {
        if (!_.isDate(date)) {
            return null;
        }

        const hour = date.getHours();
        const minute = date.getMinutes();

        return hour * 60 + minute;
    };

    // update time of date
    export const setTimeOfDate = (date: Date, time: number) => {
        if (!validateNumb(time)) {
            return moment(date).clone().toDate();
        }

        const hour = Math.floor(time / 60);
        const minute = Math.floor(time % 60);

        return moment(date).clone().set('hour', hour).set('minute', minute).toDate();
    };

    // validate (model) value range
    export const validateNumb = (value: number | null) => {
        return _.isNumber(value) && !_.isNaN(value) && 0 <= value && value <= 1440;
    };

    // convert value from model to view
    export const number2String = (value: number) => {
        const hour = Math.floor(value / 60);
        const minute = Math.floor(value % 60);

        return `${hour}:${_.padStart(`${minute}`, 2, '0')}`;
    };

    // convert value from view to model
    // null: null value
    // -3: invalid charactor
    // -2: minute invalid
    // -1: negative value
    // 1441: over 24 hours value
    export const string2Number = (value: string): number | null => {
        if (value === '') {
            return null;
        }

        const numb = Number(value.replace(/:/, ''));

        // input other charactor
        if (isNaN(numb)) {
            return -3;
        }

        // input lower 0
        if (numb < 0) {
            return -1;
        }

        const hour = Math.floor(numb / 100);
        const minute = Math.floor(numb % 100);

        // hour is not valid
        if (hour > 24) {
            return 1441;
        }

        // case is not equal 24:00
        if (hour === 24 && minute !== 0) {
            return 1441;
        }

        // minute is not valid
        if (minute > 59) {
            return -2;
        }

        return hour * 60 + minute;
    };

    export const formatTime = (value: number, format: 'Clock_Short_HM' | 'Time_Short_HM' | 'Time_Short_HM') => byId(format, value);
}