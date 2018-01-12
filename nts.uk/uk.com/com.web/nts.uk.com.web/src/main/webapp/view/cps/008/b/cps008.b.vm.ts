module cps008.b.vm {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import close = nts.uk.ui.windows.close;
    let __viewContext: any = window['__viewContext'] || {};

    export class ViewModel {
        layout: KnockoutObservable<Layout> = ko.observable(new Layout({ id: '', code: '', name: '' }));

        constructor() {
            let self = this,
                layout = self.layout();

            self.start();

            var currentDialog = nts.uk.ui.windows.getSelf();
            var doit;
            $(currentDialog.parent.globalContext).resize(function() {
                clearTimeout(doit);
                doit = setTimeout(self.resizedw(), 1000);
            });
        }

        resizedw() {
            let self = this,
                currentDialog = nts.uk.ui.windows.getSelf();
           // $(currentDialog.parent.globalContext).css("overflow", "hidden");

            if (currentDialog.parent.globalContext.innerWidth <= 1250) {
                currentDialog.setWidth(currentDialog.parent.globalContext.innerWidth - 50);
            } else {
                currentDialog.setWidth(1280);
            }

            if (currentDialog.parent.globalContext.innerHeight <= 630) {
                currentDialog.setHeight(currentDialog.parent.globalContext.innerHeight - 100);
            } else {
                currentDialog.setHeight(660);
            }
        }

        start() {
            let self = this,
                layout = self.layout(),

                dto: any = getShared('CPS008B_PARAM');
            layout.id = dto.id;
            layout.code = dto.code;
            layout.name = dto.name;
            // lấy list items classification ra theo layoutid của maintainece layout truyền từ màn a lên
            // Không có thì gọi service dưới lấy list items classification của new layout rồi truyền vào layout ở view model

            let cls: Array<any> = dto.classifications;

            let initData = (arr: Array<any>) => {
                // remove all sibling sperators
                let maps = _(arr)
                    .map((x, i) => (x.layoutItemType == IT_CLA_TYPE.SPER) ? i : -1)
                    .filter(x => x != -1).value();

                _.each(maps, (t, i) => {
                    if (maps[i + 1] == t + 1) {
                        _.remove(arr, (m: IItemClassification) => {
                            let item: IItemClassification = ko.unwrap(arr)[maps[i + 1]];
                            return item && item.layoutItemType == IT_CLA_TYPE.SPER && item.layoutID == m.layoutID;
                        });
                    }
                });
                return arr;
            };

            if (cls && cls.length) {
                layout.itemsClassification.removeAll();
                _.each(cls, x => layout.itemsClassification.push(_.omit(x, ["items"])));
            } else if (dto.isNewLayout) {
                service.getData().done((x: ILayout) => {
                    layout.itemsClassification(initData(x.itemsClassification));
                });
            }else{
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

            setShared("CPS008B_VALUE", layout.itemsClassification);

            close();

        }

        close() {
            setShared('CPS008B_VALUE', null);
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