module jhn003.a.vm {
    import setShared = nts.uk.ui.windows.setShared;

    export class ViewModel {
        layout: KnockoutObservable<Layout> = ko.observable(new Layout({ id: '', code: '', name: '' }));

        constructor() {
            let self = this,
                layout = self.layout();

        
        }

        start() {
            let self = this,
                layout = self.layout(),
                dto: any = getShared('JHN011C_PARAM');

            layout.id = dto.id;
            layout.code = dto.reportCode;
            layout.name = dto.reportName;
            

            let cls: Array<any> = dto.classifications;

            if (cls && cls.length) {
                layout.itemsClassification(_.map(cls, x => _.omit(x, ["items", "renders"])));
            } else {
                layout.itemsClassification([]);
            }
        }

        pushData() {
            let self = this,
                layout: ILayout = ko.toJS(self.layout);

            // check item tren man hinh
            if (layout.itemsClassification.length == 0) {
                nts.uk.ui.dialog.alert({ messageId: "Msg_203" });
                return;
            }

            let listItemIds = _(layout.itemsClassification)
                .map(x => _.map(x.listItemDf, m => m))
                .flatten()
                .filter(x => !!x)
                .groupBy((x: any) => x.id)
                .pickBy(x => x.length > 1)
                .keys()
                .value();
            // エラーメッセージ（#Msg_289#,２つ以上配置されている項目名）を表示する
            if (!!listItemIds.length) {
                nts.uk.ui.dialog.alert({ messageId: "Msg_289" });
                return;
            }

            setShared("JHN011C_VALUE", _.map(layout.itemsClassification, m => _.omit(m, ["items", "renders"])));

            close();

        }

        close() {
            setShared('JHN011C_VALUE', null);
            close();
        }
    }

    interface IItemClassification {
        layoutID?: string;
        dispOrder?: number;
        className?: string;
        personInfoCategoryID?: string;
        layoutItemType: IT_CLA_TYPE;
        listItemDf: Array<IItemDefinition>;
    }

    interface IItemDefinition {
        id: string;
        perInfoCtgId?: string;
        itemCode?: string;
        itemName: string;
    }

    interface ILayout {
        id: string;
        code: string;
        name: string;
        editable?: boolean;
        itemsClassification?: Array<IItemClassification>;
    }

    class Layout {
        id: KnockoutObservable<string> = ko.observable('');
        code: KnockoutObservable<string> = ko.observable('');
        name: KnockoutObservable<string> = ko.observable('');
        editable: KnockoutObservable<boolean> = ko.observable(true);
        itemsClassification: KnockoutObservableArray<any> = ko.observableArray([]);

        constructor(param: ILayout) {
            let self = this;

            self.id(param.id);
            self.code(param.code);
            self.name(param.name);

            if (param.editable != undefined) {
                self.editable(param.editable);
            }

            // replace x by class that implement this interface
            self.itemsClassification(param.itemsClassification || []);
        }
    }

    // define ITEM_CLASSIFICATION_TYPE
    enum IT_CLA_TYPE {
        ITEM = <any>"ITEM", // single item
        LIST = <any>"LIST", // list item
        SPER = <any>"SeparatorLine" // line item
    }
}