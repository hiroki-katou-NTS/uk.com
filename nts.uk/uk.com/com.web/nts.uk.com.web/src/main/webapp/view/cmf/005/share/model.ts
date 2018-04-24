module nts.uk.com.view.cmf005.share.model {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import modal = nts.uk.ui.windows.sub.modal;
    import getText = nts.uk.resource.getText;



    export enum SAVE_BEFOR_DELETE_ATR {
        YES = 0,
        NO = 1
    }
    
    export enum SYSTEM_TYPE {
        PERSON_SYS = 0,
        ATTENDANCE_SYS = 1,
        PAYROLL_SYS = 2,
        OFFICE_HELPER = 3
    }

    export class ItemModel {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    export class ItemCategory {
        cateItemNumber: number;
        cateId: string;
        cateName: string;
        timeDeletion: string;
        rangeDeletion: string;

        constructor(cateItemNumber: number, cateId: string, cateName: string,timeDeletion:string,rangeDeletion :string) {
            this.cateItemNumber = cateItemNumber;
            this.cateId = cateId;
            this.cateName = cateName;
            this.timeDeletion = timeDeletion;
            this.rangeDeletion = rangeDeletion;
        }
    }

    export class ItemDate {
        startDate: string;
        endDate: string;
        startYear: string;
        endYear: string;

        constructor(startDate: string, endDate: string, startYear: string, endYear: string, ) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.startYear = startYear;
            this.endYear = endYear;
        }
    }

    //screen B
    export class AcceptanceCodeConvert {
        convertCode: KnockoutObservable<string>;
        convertName: KnockoutObservable<string>;
        dispConvertCode: string;
        dispConvertName: string;
        acceptCodeWithoutSettings: KnockoutObservable<number>;

        constructor(code: string, name: string, acceptWithoutSettings: number) {
            this.convertCode = ko.observable(code);
            this.convertName = ko.observable(name);
            this.dispConvertCode = code;
            this.dispConvertName = name;
            this.acceptCodeWithoutSettings = ko.observable(acceptWithoutSettings);
        }
    }
}