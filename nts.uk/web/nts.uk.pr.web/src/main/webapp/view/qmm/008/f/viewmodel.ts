module nts.uk.pr.view.qmm008.f {
    export module viewmodel {
        import InsuranceOfficeItemDto = nts.uk.pr.view.qmm008.a.service.model.finder.InsuranceOfficeItemDto;
        export class ScreenModel {
            modalValue: KnockoutObservable<string>;
            isTransistReturnData: KnockoutObservable<boolean>;

            // for group radio button
            listOptions: KnockoutObservableArray<any>;
            selectedValue: KnockoutObservable<any>;
            officeCodeName: KnockoutObservable<string>;
            datePicker : any
            constructor(getFromParent: InsuranceOfficeItemDto) {
                var self = this;
                self.listOptions = ko.observableArray([new optionsModel(1, "履歴を削除する"), new optionsModel(2, "履歴を修正する")]);
                self.selectedValue = ko.observable(new optionsModel(1, ""));

                self.modalValue = ko.observable("Goodbye world!");
                self.isTransistReturnData = ko.observable(nts.uk.ui.windows.getShared("isTransistReturnData"));
                // Reset child value
                if (getFromParent != null) {
                    this.officeCodeName = ko.observable(getFromParent.code + " " + getFromParent.name);
                }
                this.datePicker = ko.observable(getFromParent.name);
            }

            CloseModalSubWindow() {
                // Set child value
                nts.uk.ui.windows.setShared("updateHistoryChildValue", this.modalValue(), this.isTransistReturnData());
                nts.uk.ui.windows.close();
            }
        }
        class optionsModel {
            id: number;
            name: string;
            constructor(id, name) {
                var self = this;
                self.id = id;
                self.name = name;
            }
        }
    }
}