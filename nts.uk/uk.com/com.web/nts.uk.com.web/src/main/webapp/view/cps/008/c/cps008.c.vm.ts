module cps008.c.viewmodel {

    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ViewModel {

        layout: KnockoutObservable<Layout> = ko.observable(new Layout({ id: '', code: '', name: '' }));

        constructor() {
            let self = this,
                layout: Layout = self.layout();
            let _data = getShared('CPS008_PARAM');

            self.layout().id(_data.id);
            self.layout().code(_data.code);
            self.layout().name(_data.name);

        }

        coppyBtn() {
            let self = this;
            self.validate();
            
            
        }

        cancelBtn() {

        }

        validate() {
            let self = this;
            // check code gioong nhau
            if (self.layout().INP_CODE() == "" || self.layout().INP_NAME() == "") {
                if (self.layout().INP_CODE() == "") {
                    $("#C_INP_CODE").focus();
                } else {
                    $("#C_INP_NAME").focus();
                }
                return;
            }

            if (self.layout().checked() && (self.layout().code() == self.layout().INP_CODE())) {
                nts.uk.ui.dialog.alert("#Msg_355#");
                $("#C_INP_CODE").focus();
                return;
            }


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
        INP_CODE: KnockoutObservable<string> = ko.observable('');
        INP_NAME: KnockoutObservable<string> = ko.observable('');
        checked: KnockoutObservable<boolean> = ko.observable(false);
        action: KnockoutObservable<LAYOUT_ACTION> = ko.observable(LAYOUT_ACTION.COPY);

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