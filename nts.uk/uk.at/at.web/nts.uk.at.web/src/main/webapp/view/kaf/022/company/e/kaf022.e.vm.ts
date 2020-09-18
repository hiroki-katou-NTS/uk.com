module nts.uk.at.view.kaf022.e.viewmodel {
    import text = nts.uk.resource.getText;

    export class ScreenModelE {
        texteditorD9: KnockoutObservable<string>;
        valueD10: KnockoutObservable<string>;
        enableD11: KnockoutObservable<boolean>;

        constructor() {
            const self = this;
            self.texteditorD9 = ko.observable(null);
            self.valueD10 = ko.observable("#000000");
            self.enableD11 = ko.observable(false);

            $("#fixed-table-e").ntsFixedTable({});
        }

        initData(allData: any): void {
            const self = this;
            let data = allData.tripRequestSetting;
            if (data && data.appCommentSet) {
                self.texteditorD9(data.appCommentSet.comment);
                self.valueD10(data.appCommentSet.colorCode);
                self.enableD11(data.appCommentSet.bold);
            }
        }

        collectData(): any {
            const self = this;
            return {
                commentContent: self.texteditorD9(),
                commentColor: self.valueD10(),
                commentBold: self.enableD11()
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