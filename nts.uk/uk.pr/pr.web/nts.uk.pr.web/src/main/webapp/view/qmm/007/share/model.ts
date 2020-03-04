module nts.uk.pr.view.qmm007.share.model {
    import getText = nts.uk.resource.getText;

    export class ItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }


}