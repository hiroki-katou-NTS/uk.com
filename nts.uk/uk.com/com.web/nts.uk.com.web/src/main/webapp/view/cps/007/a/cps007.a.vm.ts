module cps007.a.vm {
    import info = nts.uk.ui.dialog.info;
    import alert = nts.uk.ui.dialog.alert;
    import error = nts.uk.ui.dialog.alertError;
    import text = nts.uk.resource.getText;
    import lv = nts.layout.validate;
    import warning = nts.uk.ui.dialog.caution;

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
            service.getData().done((lt: ILayout) => {
                if (lt) {
                    layout.id(lt.id);
                    layout.code(lt.code);
                    layout.name(lt.name);

                    // remove all sibling sperators
                    lv.removeDoubleLine(lt.itemsClassification);

                    layout.itemsClassification(lt.itemsClassification);
                }
            });
        }

        saveData() {
            let self = this,
                layout: ILayout = ko.toJS(self.layout),
                command: any = {
                    layoutID: layout.id,
                    layoutCode: layout.code,
                    layoutName: layout.name,
                    itemsClassification: layout.outData
                };

            let itemids = _(command.itemsClassification)
                .map(x => _.map(x.listItemClsDf, m => m))
                .flatten()
                .filter(x => !!x)
                .groupBy((x: any) => x.personInfoItemDefinitionID)
                .pickBy((x: Array<any>) => x.length > 1)
                .keys()
                .value();

            // エラーメッセージ（#Msg_202,２つ以上配置されている項目名）を表示する
            if (!!itemids.length) {
                error({ messageId: 'Msg_202' });
                return;
            }
            // push data layout to webservice
            invisible();
            service.saveData(command).done((data) => {
                self.start();
                if (data.length > 0) {
                    let result = _.toString(data);
                    warning({ messageId: "Msg_1350", messageParams: [result] }).then(() => {
                        info({ messageId: "Msg_15" }).then(function() {
                            unblock();
                        });
                    });  
                } else {
                    info({ messageId: "Msg_15" }).then(function() {
                        unblock();
                    });    
                }
                
            }).fail((mes) => {
                unblock();
                error({ messageId: mes.messageId, messageParams: mes.parameterIds });
            }).done(x => {
                unblock();
            });
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
        outData?: Array<any>;
    }

    class Layout {
        id: KnockoutObservable<string> = ko.observable('');
        code: KnockoutObservable<string> = ko.observable('');
        name: KnockoutObservable<string> = ko.observable('');
        editable: KnockoutObservable<boolean> = ko.observable(true);
        itemsClassification: KnockoutObservableArray<IItemClassification> = ko.observableArray([]);
        outData: KnockoutObservableArray<any> = ko.observableArray([]);
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