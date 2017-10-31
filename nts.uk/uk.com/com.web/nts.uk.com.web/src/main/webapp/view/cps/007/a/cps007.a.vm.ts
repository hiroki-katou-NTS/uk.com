module cps007.a.vm {
    import info = nts.uk.ui.dialog.info;
    import alert = nts.uk.ui.dialog.alert;
    import error = nts.uk.ui.dialog.alertError;
    import text = nts.uk.resource.getText;

    let __viewContext: any = window['__viewContext'] || {},
        block = window["nts"]["uk"]["ui"]["block"]["grayout"],
        unblock = window["nts"]["uk"]["ui"]["block"]["clear"],
        invisible = window["nts"]["uk"]["ui"]["block"]["invisible"];

    export class ViewModel {
        layout: KnockoutObservable<Layout> = ko.observable(new Layout({ id: '', code: '', name: '' }));

        constructor() {
            let self = this,
                layout = self.layout();

            self.start();
        }

        start() {
            let self = this,
                layout = self.layout();

            // get layout info on startup
            service.getData().done((x: ILayout) => {
                layout.id(x.id);
                layout.code(x.code);
                layout.name(x.name);

                // remove all sibling sperators
                let maps = _(x.itemsClassification)
                    .map((x, i) => (x.layoutItemType == 2) ? i : -1)
                    .filter(x => x != -1).value();

                _.each(maps, (t, i) => {
                    if (maps[i + 1] == t + 1) {
                        _.remove(x.itemsClassification, (m: IItemClassification) => {
                            let item: IItemClassification = ko.unwrap(x.itemsClassification)[maps[i + 1]];
                            return item && item.layoutItemType == 2 && item.layoutID == m.layoutID;
                        });
                    }
                });

                layout.itemsClassification(x.itemsClassification);
            });
        }

        saveData() {
            let self = this,
                layout: ILayout = ko.toJS(self.layout),
                command: any = {
                    layoutID: layout.id,
                    layoutCode: layout.code,
                    layoutName: layout.name,
                    itemsClassification: _(layout.itemsClassification || []).map((item, i) => {
                        return {
                            dispOrder: i + 1,
                            personInfoCategoryID: item.personInfoCategoryID,
                            layoutItemType: item.layoutItemType,
                            listItemClsDf: _(item.listItemDf || []).map((def, j) => {
                                return {
                                    dispOrder: j + 1,
                                    personInfoItemDefinitionID: def.id
                                };
                            }).value()
                        };
                    }).value()
                };

            let itemids = _(command.itemsClassification)
                .map(x => x.listItemClsDf)
                .flatten()
                .filter(x => !!x)
                .map((x: any) => x.personInfoItemDefinitionID)
                .groupBy()
                .pickBy((x: Array<string>) => x.length > 1)
                .keys()
                .value();

            // エラーメッセージ（#Msg_202,２つ以上配置されている項目名）を表示する
            if (!!itemids.length) {
                error({ messageId: 'Msg_202' });
                return;
            }

            // push data layout to webservice
            invisible();
            service.saveData(command).done(() => {
                self.start();
                info({ messageId: "Msg_15" }).then(function() {
                    unblock();
                });
            }).fail((mes) => {
                unblock();
                console.log(mes);
                error({ messageId: mes.messageId, messageParams: mes.parameterIds });
            });
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