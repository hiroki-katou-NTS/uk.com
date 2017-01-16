module cmm009.d.viewmodel {
    export class ScreenModel {

        multilineeditor: any;
        startDate: KnockoutObservable<any>;
        endDate: KnockoutObservable<any>;
        itemList: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number>;
        enable: KnockoutObservable<boolean>;

        constructor() {
            var self = this;

            self.startDate = ko.observable(null);
            self.endDate = ko.observable(null);
            self.itemList = ko.observableArray([
                new BoxModel(1, 'box 1'),
                new BoxModel(2, 'box 2')
            ]);
            self.selectedId = ko.observable(1);
            self.enable = ko.observable(true);
            self.multilineeditor = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                    resizeable: true,
                    placeholder: "",
                    width: "",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
        }

        start() {
        }
    }

    class BoxModel {
        id: number;
        name: string;
        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }
}