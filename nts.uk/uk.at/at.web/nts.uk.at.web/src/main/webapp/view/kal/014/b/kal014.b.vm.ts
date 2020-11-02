module nts.uk.at.kal014.b {

    const PATH_API = {}

    @bean()
    export class KSM008AViewModel extends ko.ViewModel {
        modalDTO: ModalDto = new ModalDto(null, "", "", "");
        itemList: KnockoutObservableArray<ItemModel>;
        selectedCode: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;
        strComboMonth: KnockoutObservableArray<any>;
        endComboMonth: KnockoutObservableArray<any>;

        constructor(props: any) {
            super();
            const vm = this;
            let modalData = nts.uk.ui.windows.getShared("KAL014BModalData");
            vm.modalDTO.categoryId(modalData.categoryId);
            vm.modalDTO.categoryName(modalData.categoryName);
            vm.modalDTO.startDate(modalData.startDate);
            vm.modalDTO.endDate(modalData.endDate);
            vm.itemList = ko.observableArray([
                new ItemModel('1', '基本給'),
                new ItemModel('2', '役職手当'),
                new ItemModel('3', '基本給ながい文字列ながい文字列ながい文字列')
            ]);

            for (let i = 1; i < 100; i++) {

            }

            vm.selectedCode = ko.observable('1');
            vm.isEnable = ko.observable(true);
            vm.isEditable = ko.observable(true);
            vm.strComboMonth = ko.observableArray(__viewContext.enums.SpecifiedMonth);
            vm.endComboMonth = ko.observableArray(__viewContext.enums.SpecifiedMonth);
        }

        created() {
            const vm = this;
        }

        mounted() {

        }

        

        setDefault() {
            var self = this;
            // nts.uk.util.value.reset($("#combo-box, #A_SEL_001"), self.defaultValue() !== '' ? self.defaultValue() : undefined);
        }

        cancel_Dialog(): any {
            nts.uk.ui.windows.close();
        }

        decide(): any {
            var vm = this;
            /*   if(self.strSelected()==0){
                   $(".input-str").trigger("validate");
               }
               if(self.endSelected()==0){
                   $(".input-end").trigger("validate");
               }
               if($(".nts-input").ntsError("hasError")){
                   return;
               }else if(self.checkPeriod()){
                   let dataSetShare = self.getData();
                   nts.uk.ui.windows.setShared("extractionDaily", dataSetShare);
                   self.cancel_Dialog();
               }*/
        }

    }

    class ModalDto {
        categoryId: KnockoutObservable<string>;
        categoryName: KnockoutObservable<string>;
        extractionPeriod: KnockoutObservable<string>;
        startDate: KnockoutObservable<any>;
        endDate: KnockoutObservable<any>;

        constructor(categoryId: string, categoryName: string, startDate: any, endDate: any) {
            this.categoryId = ko.observable(categoryId);
            this.categoryName = ko.observable(categoryName);
            this.categoryId = ko.observable(categoryId);
            this.startDate = ko.observable(startDate);
            this.endDate = ko.observable(endDate);
            this.extractionPeriod = ko.observable(startDate + "~" + endDate);
        }
    }

    class ItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

}