module nts.uk.at.view.kmf022.f.viewmodel {

    export class ScreenModelF {
        selectedValueF13: KnockoutObservable<number>;

        constructor() {
            const self = this;
            self.selectedValueF13 = ko.observable(0);

            $("#fixed-table-f").ntsFixedTable({});
        }

        initData(allData: any): void {
            const self = this;
            let data = allData.goBack;
            if (data) {
                self.selectedValueF13(data.workChangeFlg);
            }
        }

    }
}