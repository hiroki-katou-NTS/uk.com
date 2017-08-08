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
                    debugger;
                    layout.id(_data[0].id);
                    //layout.id.valueHasMutated();
                }
            });

            layout.id.subscribe(id => {
                if (id) {
                    // demo subscrible
                    let items: Array<ILayout> = ko.toJS(layouts),
                        item: ILayout = _.find(items, x => x.id == id);

                    if (item) {
                        layout.code(item.code);
                        layout.name(item.name);
                    }
                    // Gọi service tải dữ liệu ra layout
                    /*service.getDetails(id).done((data: ILayout) => {
                        if (data) {
                            layout.code(data.code);
                            layout.name(data.name);
                            layout.classifications(data.classifications);

                            layout.action(LAYOUT_ACTION.UPDATE);
                        }
                    });*/
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
        }

        saveDataLayout() {
            let self = this,
                data: ILayout = ko.toJS(self.layout);

            // call service savedata
        }

        copyDataLayout() {
            let self = this,
                layout: Layout = self.layout(),
                data: ILayout = ko.toJS(self.layout);

            setShared('CPS008_PARAM', data);
            modal('../c/index.xhtml').onClosed(() => {
                let _data = getShared('CPS008_VALUE');
                if (_data) {
                    layout.code(_data.code);
                    layout.name(_data.name);

                    if (_data.action == LAYOUT_ACTION.OVERRIDE) {
                        layout.action(LAYOUT_ACTION.OVERRIDE);
                    } else {
                        layout.action(LAYOUT_ACTION.COPY);
                    }
                    // call saveData service
                }
            });
        }

        removeDataLayout() {
            let self = this,
                layout: Layout = self.layout(),
                data: ILayout = ko.toJS(self.layout);

            data.action = LAYOUT_ACTION.REMOVE;
            // call service remove
        }

        openDialogD() {

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