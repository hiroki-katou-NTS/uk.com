module nts.uk.ui.at.kdp013.share {
    // validate (model) value range
    export const validateNumb = (value: number) => {
        return _.isNumber(value) && !_.isNaN(value) && 0 <= value && value <= 1440;
    };

    // convert value from model to view
    export const number2String = (value: number) => {
        const hour = Math.floor(value / 60);
        const minute = Math.floor(value % 60);

        return `${hour}:${_.padStart(`${minute}`, 2, '0')}`;
    };

    // convert value from view to model
    export const string2Number = (value: string): number | null => {
        if (value === '') {
            return null;
        }

        const numb = Number(value.replace(/:/, ''));

        // input is not a number
        if (_.isNaN(numb) || numb < 0) {
            return null;
        }

        const hour = Math.floor(numb / 100);
        const minute = Math.floor(numb % 100);

        // hour is not valid
        if (hour > 24) {
            return null;
        }

        // minute is not valid
        if (minute > 59) {
            return null;
        }

        // case is not equal 24:00
        if (hour === 24 && minute !== 0) {
            return null;
        }

        return hour * 60 + minute;
    };

}