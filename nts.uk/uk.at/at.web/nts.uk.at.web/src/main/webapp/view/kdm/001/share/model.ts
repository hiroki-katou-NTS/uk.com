module nts.uk.at.view.kdm001.share.model {
    import getText = nts.uk.resource.getText;

    export function getNumberOfDays(): Array<ItemModel> {
        return [
            new model.ItemModel(1.0, getText('KDM001_127')),
            new model.ItemModel(0.5, getText('KDM001_128')) 
        ];
    }

    export class ItemModel {
        code: any;
        name: string;
        constructor(code: any, name: string) {
            this.code  = code;
            this.name  = name;
        }
    }
}