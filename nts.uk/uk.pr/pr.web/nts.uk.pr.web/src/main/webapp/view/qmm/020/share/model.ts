module nts.uk.pr.view.qmm020.share.model {
    import getText = nts.uk.resource.getText;

    export class ItemModel {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    export enum MODE {
        NEW = 0,
        UPDATE = 1,
        NO_REGIS = 2
    }

    export enum TRANSFER_MOTHOD {
        CREATE_NEW = 1,
        TRANSFER = 0
    }

}