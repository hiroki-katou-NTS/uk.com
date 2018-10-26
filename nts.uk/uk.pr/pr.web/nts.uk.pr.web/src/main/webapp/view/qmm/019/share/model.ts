module nts.uk.pr.view.qmm019.share.model {
    import getText = nts.uk.resource.getText;

    /**
     * 明細新規作成区分
     */
    export enum SpecCreateAtr{
        NEW = 0,
        COPY = 1
    }

    export function getSpecCreateAtr(): Array<BoxModel> {
        return [
            new model.BoxModel(SpecCreateAtr.NEW, getText('QMM019_178')),
            new model.BoxModel(SpecCreateAtr.COPY, getText('QMM019_179')),
        ];
    }

    export class BoxModel {
        id: number;
        name: string;
        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }

    export class ItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}