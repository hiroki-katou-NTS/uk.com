module cps008.a.viewmodel {
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import showDialog = nts.uk.ui.dialog;
    import Text = nts.uk.resource.getText;


    export class ViewModel {
        layouts: KnockoutObservableArray<ILayout> = ko.observableArray([]);
        layout: KnockoutObservable<Layout> = ko.observable(new Layout({ id: '', code: '', name: '' }));

        constructor() {
            let self = this,
                layout: Layout = self.layout(),
                layouts = self.layouts;


            self.start();
            layout.id.subscribe(id => {
                if (id) {

                    // Gọi service tải dữ liệu ra layout
                    service.getDetails(id).done((data: any) => {
                        if (data) {
                            layout.code(data.layoutCode);
                            layout.name(data.layoutName);
                            layout.classifications(data.listItemClsDto || []);
                            layout.action(LAYOUT_ACTION.UPDATE);
                            $("#A_INP_NAME").focus();
                        }
                    });
                }
            });
        }

        start(code?: string) {
            let self = this,
                layout: Layout = self.layout(),
                layouts = self.layouts;
            // get all layout
            layouts.removeAll();
            service.getAll().done((data: Array<any>) => {
                if (data && data.length) {
                    let _data: Array<ILayout> = _.map(data, x => {
                        return {
                            id: x.maintenanceLayoutID,
                            name: x.layoutName,
                            code: x.layoutCode
                        }
                    });
                    _.each(_data, d => layouts.push(d));
                    if (!code) {
                        layout.id(_data[0].id);
                    }
                    else {
                        let _item: ILayout = _.find(ko.toJS(layouts), (x: ILayout) => x.code == code);
                        if (_item) {
                            layout.id(_item.id);
                        } else {
                            layout.id(_data[0].id);
                        }
                    }
                    layout.id.valueHasMutated();

                } else {
                    self.createNewLayout();
                }
            });
        }

        createNewLayout() {
            let self = this,
                layout: Layout = self.layout(),
                layouts = self.layouts;

            layouts.removeAll();

            layout.id(undefined);
            layout.code('');
            layout.name('');
            layout.classifications([]);

            layout.action(LAYOUT_ACTION.INSERT);
            $("#A_INP_CODE").focus();
        }

        saveDataLayout() {
            let self = this,
                data: ILayout = ko.toJS(self.layout),
                command: any = {
                    id: data.id,
                    code: data.code,
                    name: data.name,
                    action: data.action,
                    classifications: _(data.classifications || []).map((item, i) => {
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

            // check input
            if (data.code == '' || data.name == '') {
                if (data.code == '') {
                    $("#A_INP_CODE").focus();
                } else {
                    $("#A_INP_NAME").focus();
                }
                return;
            }

            // call service savedata

            service.saveData(command).done((_data: any) => {

                showDialog.info({ messageId: "Msg_15" }).then(function() {
                    $("#A_INP_NAME").focus();
                });

                self.start(data.code);


            }).fail((error: any) => {
                if (error.message == 'Msg_3') {
                    showDialog.alert(Text('Msg_3')).then(function() {
                        $("#A_INP_CODE").focus();
                    });
                }


            });
        }

        copyDataLayout() {
            let self = this,
                data: ILayout = ko.toJS(self.layout),
                layouts: Array<ILayout> = ko.toJS(self.layouts);

            setShared('CPS008_PARAM', data);
            modal('../c/index.xhtml').onClosed(() => {
                let _data = getShared('CPS008C_RESPONE');
                if (_data) {
                    var command: any = {
                        id: data.id,
                        code: _data.code,
                        name: _data.name,
                        classifications: (data.classifications || []).map((item, i) => {
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

                    if (_data.action) {
                        command.action = LAYOUT_ACTION.OVERRIDE;
                    } else {
                        command.action = LAYOUT_ACTION.COPY;
                    }


                    // call saveData service
                    service.saveData(command).done((data: any) => {

                        showDialog.info({ messageId: "Msg_20" }).then(function() {
                            self.start(_data.code);
                        });

                    }).fail((error: any) => {
                        if (error.message == 'Msg_3') {
                            showDialog.alert(Text('Msg_3')).then(function() {
                                self.start(data.code);
                            });
                        }


                    });

                } else {
                    $("#A_INP_NAME").focus();
                }
            });
        }

        removeDataLayout() {
            let self = this,
                data: ILayout = ko.toJS(self.layout);

            data.action = LAYOUT_ACTION.REMOVE;

            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {

                // call service remove
                service.saveData(data).done((data: any) => {
                    showDialog.info(Text('Msg_16')).then(function() {
                        self.start();
                    });
                }).fail((error: any) => {

                });

            }).ifCancel(() => {

            });
        }

        showDialogB() {
            let self = this,
                layout: Layout = self.layout(),
                data: ILayout = ko.toJS(self.layout);
            setShared('CPS008B_PARAM', data);
            modal('../b/index.xhtml').onClosed(() => {
                let dto: Array<any> = getShared('CPS008B_VALUE');


                if (dto && dto.length) {
                    layout.classifications.removeAll();
                    _.each(dto, x => layout.classifications.push(x));
                    layout.action(LAYOUT_ACTION.UPDATE);
                }


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
        dispOrder: number;

    }


    interface ILayout {
        id: string;
        code: string;
        name: string;
        classifications?: Array<IItemClassification>;
        action?: number;
    }

    class Layout {
        id: KnockoutObservable<string> = ko.observable('');
        code: KnockoutObservable<string> = ko.observable('');
        name: KnockoutObservable<string> = ko.observable('');
        classifications: KnockoutObservableArray<any> = ko.observableArray([]);
        action: KnockoutObservable<LAYOUT_ACTION> = ko.observable(LAYOUT_ACTION.INSERT);

        constructor(param: ILayout) {
            let self = this;

            if (param) {
                self.id(param.id || '');
                self.code(param.code || '');
                self.name(param.name || '');

                self.classifications(param.classifications || []);
            }
        }
    }

    enum LAYOUT_ACTION {
        INSERT = 0,
        UPDATE = 1,
        COPY = 2,
        OVERRIDE = 3,
        REMOVE = 4
    }
}