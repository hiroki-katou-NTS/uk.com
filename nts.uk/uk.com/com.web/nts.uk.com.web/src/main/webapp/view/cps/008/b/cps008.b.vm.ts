module cps008.b.vm {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    let __viewContext: any = window['__viewContext'] || {};

    export class ViewModel {
        layout: KnockoutObservable<Layout> = ko.observable(new Layout({ id: '', code: '', name: '' }));

        constructor() {
            let self = this,
                layout = self.layout();

            self.start();
        }

        start() {
            let self = this,
                layout = self.layout(),
                dto: any = getShared('CPS008B_PARAM');
            _.map(dto, (x: ILayout) => {
                layout.id(dto.id);
                layout.code(dto.code);
                layout.name(dto.name);
                layout.itemsClassification(dto.classifications);
            });
            // lấy list items classification ra theo layoutid của maintainece layout truyền từ màn a lên
            // Không có thì gọi service dưới lấy list items classification của new layout rồi truyền vào layout ở view model
            service.getListCls(layout.id).done(x => {
                if (x && x.length) {
                    layout.itemsClassification(x.itemsClassification);
                } else {
                    service.getData().done((x: ILayout) => {
                        debugger;
                        layout.itemsClassification(x.itemsClassification);
                        
                    });
                }
                
            });
        }

        pushData() {
            let self = this,
                layout: ILayout = ko.toJS(self.layout),
                command: any = {
                    layoutID: layout.id,
                    layoutCode: layout.code,
                    layoutName: layout.name,
                    itemsClassification: (layout.itemsClassification || []).map((item, i) => {
                        return {
                            dispOrder: i + 1,
                            personInfoCategoryID: item.personInfoCategoryID,
                            layoutItemType: item.layoutItemType,
                            listItemClsDf: (item.listItemDf || []).map((def, j) => {
                                return {
                                    dispOrder: j + 1,
                                    personInfoItemDefinitionID: def.id
                                };
                            })
                        };
                    })
                };

            setShared("CPS008B_VALUE", command.itemsClassification);
            //service.saveData(command);
        }
    }

    interface IItemClassification {
        layoutID?: string;
        dispOrder?: number;
        className?: string;
        personInfoCategoryID?: string;
        layoutItemType: number;
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
        itemsClassification: KnockoutObservableArray<IItemClassification> = ko.observableArray([]);

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
}