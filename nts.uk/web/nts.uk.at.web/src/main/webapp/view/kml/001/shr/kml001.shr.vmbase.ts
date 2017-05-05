module kml001.shr.vmbase {
    export class DateTimeProcess {
        static getOneDayBefore(date: string) {
            let numberDate = Date.parse(date);
            let dayBefore = new Date(numberDate - 24 * 60 * 60 * 1000);
            return dayBefore.getFullYear() + '/' + (dayBefore.getMonth() + 1) + '/' + dayBefore.getDate();
        }

        static getOneDayAfter(date: string) {
            let numberDate = Date.parse(date);
            let dayBefore = new Date(numberDate + 24 * 60 * 60 * 1000);
            return dayBefore.getFullYear() + '/' + (dayBefore.getMonth() + 1) + '/' + dayBefore.getDate();
        }
    }

    export enum CategoryAtr {
        PAYMENT = 0,
        DEDUCTION = 1,
        PERSONAL_TIME = 2,
        ARTICLES = 3,
        OTHER = 9
    }

    export enum Error {
        ER001 = <any>"が入力されていません。",
        ER007 = <any>"が選択されていません。",
        ER010 = <any>"対象データがありません。",
    }
}