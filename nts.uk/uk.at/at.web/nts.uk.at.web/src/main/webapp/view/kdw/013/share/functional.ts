module nts.uk.ui.at.kdp013.share {
    // validate (model) value range
    export const validateNumb = (value: number) => {
        return _.isNumber(value) && 0 <= value && value <= 1440;
    };

    // convert value from model to view
    export const number2String = (value: number) => {
        const hour = Math.floor(value / 60);
        const minute = Math.floor(value % 60);

        return `${hour}:${_.padStart(`${minute}`, 2, '0')}`;
    };

    // convert value from view to model
    export const string2Number = (value: string) => {
        if (value === '') {
            return -1;
        }

        const numb = Number(value.replace(/:/, ''));

        if (_.isNaN(numb) || numb < 0) {
            return -1;
        }

        return Math.floor(numb / 100) * 60 + Math.floor(numb % 100);
    };

}