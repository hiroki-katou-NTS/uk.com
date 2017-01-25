module nts.uk.pr.view.qmm008.b {
    export module viewmodel {
        import InsuranceOfficeItemDto = nts.uk.pr.view.qmm008.a.service.model.finder.InsuranceOfficeItemDto;
        export class ScreenModel {
            getInsuranceOfficeItemDto : KnockoutObservable<InsuranceOfficeItemDto>;
            returnInsuranceOfficeItemDto: KnockoutObservable<InsuranceOfficeItemDto>;
            isTransistReturnData: KnockoutObservable<boolean>;

            // for group radio button
            listOptions: KnockoutObservableArray<any>;
            selectedValue: KnockoutObservable<any>;
            //for input 
            officeCodeName: KnockoutObservable<string>;
            addDate: KnockoutObservable<string>;
            constructor(recivedVal: any) {
                var self = this;
                self.getInsuranceOfficeItemDto = ko.observable(recivedVal);
                self.returnInsuranceOfficeItemDto = ko.observable(null);
                
                //select options 
                self.listOptions = ko.observableArray([new optionsModel(1, "最新の履歴(2016/04)から引き継ぐ"), new optionsModel(2, "初めから作成する")]);
                
                self.selectedValue = ko.observable(new optionsModel(1, ""));

                self.isTransistReturnData = ko.observable(nts.uk.ui.windows.getShared("isTransistReturnData"));
                // Reset child value
                //            nts.uk.ui.windows.setShared("childValue", null);
                self.officeCodeName= ko.observable(recivedVal.code + " " +recivedVal.name );
                self.addDate = ko.observable('2016/04');
            }


            CloseModalSubWindow() {
                // Set child value
                this.getInsuranceOfficeItemDto().childs.push(new InsuranceOfficeItemDto('id', '2016/04 ~ 2017/03', 'chilcode2',[]));
                this.returnInsuranceOfficeItemDto(this.getInsuranceOfficeItemDto());
                nts.uk.ui.windows.setShared("addHistoryChildValue", this.returnInsuranceOfficeItemDto(),true);
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