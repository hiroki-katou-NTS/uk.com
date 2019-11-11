module nts.uk.pr.view.qui001.share.model{
    import getText = nts.uk.resource.getText;

    export function getSubNameClassA(): Array<ItemModelA> {
        return [
            new ItemModelA(0, getText('QUI001_C222_5')),
            new ItemModelA(1, getText('QUI001_C222_6'))
        ];
    }
    export function getSubNameClassB(): Array<ItemModelB> {
        return [
            new ItemModelB(0, getText('QUI001_C222_28')),
            new ItemModelB(1, getText('QUI001_C222_29'))
        ];}


    export class ItemModelA {
        codeA: number;
        nameA: string;

        constructor(codeA: number, nameA: string) {
            this.codeA = codeA;
            this.nameA = nameA;
        }
    }

    export class ItemModelB {
        codeB: number;
        nameB: string;

        constructor(codeB: number, nameB: string) {
            this.codeB = codeB;
            this.nameB = nameB;
        }
    }

    export enum SCREEN_MODE {
        NEW = 0,
        UPDATE = 1
    }
}