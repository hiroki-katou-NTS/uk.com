module nts.uk.com.view.qmm011.share.model {
    import getText = nts.uk.resource.getText;
	
    export class ItemModel {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }

}