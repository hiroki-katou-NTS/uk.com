module nts.uk.at.view.kaf022.e.viewmodel {
    import text = nts.uk.resource.getText;

    export class ScreenModelE {
        texteditorD9: KnockoutObservable<string>;
        valueD10: KnockoutObservable<string>;
        enableD11: KnockoutObservable<boolean>;

        constructor() {
            const self = this;
            self.texteditorD9 = ko.observable(null);
            self.valueD10 = ko.observable(null);
            self.enableD11 = ko.observable(false);

            $("#fixed-table-e").ntsFixedTable({});
        }

        initData(allData: any): void {
            const self = this;
            let data = allData.tripReq;
            if (data) {
                self.texteditorD9(data.comment1);
                self.valueD10(data.color1);
                self.enableD11(data.weight1);
            }
        }

    }

    class ItemModel {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }

}