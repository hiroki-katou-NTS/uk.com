module cps008.a.viewmodel {
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

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
                }
            });
        }

        createNewLayout() {
            let self = this,
                layout: Layout = self.layout();

            layout.id(undefined);
            layout.code('');
            layout.name('');
            layout.classifications([]);

            layout.action(LAYOUT_ACTION.INSERT);
            $("#A_INP_CODE").focus();
        }

        saveDataLayout() {
            let self = this,
                data: ILayout = ko.toJS(self.layout);

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
            service.saveData(data).done((_data: any) => {
                self.start(data.code);
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
                    data.code = _data.code;
                    data.name = _data.name;

                    if (_data.action) {
                        data.action = LAYOUT_ACTION.OVERRIDE;
                    } else {
                        data.action = LAYOUT_ACTION.COPY;
                    }


                    // call saveData service
                    service.saveData(data).done(() => {
                        self.start(data.code);
                    });

                }
            });
        }

        removeDataLayout() {
            let self = this,
                 data: ILayout = ko.toJS(self.layout);

            data.action = LAYOUT_ACTION.REMOVE;
            // call service remove
        }

        showDialogB() {
            let self = this,
                data: ILayout = ko.toJS(self.layout);
            setShared('CPS008B_PARAM', data);
            modal('../b/index.xhtml').onClosed(() => {

            });
        }
     
    }

    interface ILayout {
        id: string;
        code: string;
        name: string;
        classifications?: Array<any>;
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