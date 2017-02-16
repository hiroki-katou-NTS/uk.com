module nts.uk.pr.view.qmm008.f {
    export module viewmodel {
        import InsuranceOfficeItemDto = nts.uk.pr.view.qmm008.a.service.model.finder.InsuranceOfficeItemDto;
        export class ScreenModel {
            OfficeItemModel: KnockoutObservable<InsuranceOfficeItemDto>;
            selectedHistoryCode: KnockoutObservable<string>;
            startMonth: KnockoutObservable<string>;
            endMonth: KnockoutObservable<string>;

            modalValue: KnockoutObservable<string>;
            isTransistReturnData: KnockoutObservable<boolean>;

            // for group radio button
            listOptions: KnockoutObservableArray<any>;
            selectedValue: KnockoutObservable<any>;
            officeCodeName: KnockoutObservable<string>;
            constructor(receiveOfficeItem: InsuranceOfficeItemDto, selectedHistoryCode: string) {
                var self = this;
                self.OfficeItemModel = ko.observable(receiveOfficeItem);
                self.selectedHistoryCode = ko.observable(selectedHistoryCode);

                self.listOptions = ko.observableArray([new optionsModel(1, "履歴を削除する"), new optionsModel(2, "履歴を修正する")]);
                self.selectedValue = ko.observable(new optionsModel(1, ""));

                self.modalValue = ko.observable("Goodbye world!");
                self.isTransistReturnData = ko.observable(nts.uk.ui.windows.getShared("isTransistReturnData"));
                // Reset child value
                if (receiveOfficeItem != null) {
                    this.officeCodeName = ko.observable(receiveOfficeItem.codeName);
                }
                self.startMonth = ko.observable('');
                self.endMonth = ko.observable('');
                self.getDate();
            }

            //get start and end  date
            getDate() {
                var self = this;
                self.OfficeItemModel().childs.forEach(function(item, index) {
                    if (item.code == self.selectedHistoryCode()) {
                        var viewRangeString = self.OfficeItemModel().childs[index].codeName;
                        var rangeCharIndex = viewRangeString.indexOf("~");
                        self.endMonth(viewRangeString.substr(rangeCharIndex + 1, viewRangeString.length));
                        self.startMonth(viewRangeString.substr(0, rangeCharIndex));
                    }
                });
            }

            // click button setting
            clickSettingButton() {
                var self = this;
                //check choice(delete or update history)
                //                if()
                //                {}
                //update
                self.OfficeItemModel().childs.forEach(function(item, index) {
                    //update history 
                    if (item.code == self.selectedHistoryCode()) {
                        //TODO validate update time
                        self.OfficeItemModel().childs[index].codeName = self.startMonth() + "~" + self.endMonth();
                    }
                });
                nts.uk.ui.windows.setShared("updateHistoryChildValue", self.OfficeItemModel(), true);
                nts.uk.ui.windows.close();
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