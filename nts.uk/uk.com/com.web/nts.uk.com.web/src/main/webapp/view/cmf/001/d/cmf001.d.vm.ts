module nts.uk.com.view.cmf001.d.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import model = cmf001.share.model;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        systemTypes: KnockoutObservableArray<model.ItemModel> = ko.observableArray([
            new model.ItemModel(0, 'HR System'),
            new model.ItemModel(1, 'Attendance System'),
            new model.ItemModel(2, 'Payroll System'),
            new model.ItemModel(3, 'Office Helper')
        ]);
        systemType: model.ItemModel;
        
        listCategory: KnockoutObservableArray<model.ExternalAcceptanceCategory>;
        selectedCategory: KnockoutObservable<string>;
        
        listCategoryItem: KnockoutObservableArray<model.ExternalAcceptanceCategoryItemData>;
        listSelectedCategoryItem: KnockoutObservableArray<model.ExternalAcceptanceCategoryItemData> = ko.observableArray([]);
        selectedCategoryItem: KnockoutObservable<string>;
        
        listAcceptItem: KnockoutObservableArray<model.StandardAcceptItem> = ko.observableArray([]);
        selectedAcceptItem: KnockoutObservable<number> = ko.observable(0);
        
        selectedStandardImportSetting: KnockoutObservable<model.StandardAcceptanceConditionSetting>;
        dataTypes: KnockoutObservableArray<model.ItemModel> = ko.observableArray([
            new model.ItemModel(0, 'Numeric'),
            new model.ItemModel(1, 'Character'),
            new model.ItemModel(2, 'Date'),
            new model.ItemModel(3, 'Time')
        ]);
        
        selectedDataType: KnockoutObservable<number> = ko.observable(0);
        
        stereoType: KnockoutObservable<string>;
        fileId: KnockoutObservable<string>;
        filename: KnockoutObservable<string>;
        fileInfo: KnockoutObservable<any>;
        accept: KnockoutObservableArray<string>;
        onchange: (filename) => void;
        constructor(data: any) {
            var self = this;
            let item = _.find(self.systemTypes(), x => {return x.code == data.systemType;});
            self.systemType = item;
            self.selectedStandardImportSetting = ko.observable(new model.StandardAcceptanceConditionSetting(data.conditionSetting.conditionSettingCode, data.conditionSetting.conditionSettingName, data.conditionSetting.deleteExistData, data.conditionSetting.acceptMode, data.conditionSetting.csvDataItemLineNumber, data.conditionSetting.csvDataStartLine, data.conditionSetting.deleteExistDataMethod));
            
            self.listCategory = ko.observableArray([
                new model.ExternalAcceptanceCategory('1', 'Category 1'),
                new model.ExternalAcceptanceCategory('2', 'Category 2'),
                new model.ExternalAcceptanceCategory('3', 'Category 3')
            ]);
            self.selectedCategory = ko.observable('1');
            
            self.listCategoryItem = ko.observableArray([
                new model.ExternalAcceptanceCategoryItemData('001', 'Item 1'),
                new model.ExternalAcceptanceCategoryItemData('002', 'Item 2'),
                new model.ExternalAcceptanceCategoryItemData('003', 'Item 3'),
                new model.ExternalAcceptanceCategoryItemData('004', 'Item 4'),
                new model.ExternalAcceptanceCategoryItemData('005', 'Item 5'),
                new model.ExternalAcceptanceCategoryItemData('006', 'Item 6'),
                new model.ExternalAcceptanceCategoryItemData('007', 'Item 7'),
                new model.ExternalAcceptanceCategoryItemData('008', 'Item 8'),
                new model.ExternalAcceptanceCategoryItemData('009', 'Item 9'),
                new model.ExternalAcceptanceCategoryItemData('010', 'Item 10'),
                new model.ExternalAcceptanceCategoryItemData('011', 'Item 11'),
                new model.ExternalAcceptanceCategoryItemData('012', 'Item 12'),
                new model.ExternalAcceptanceCategoryItemData('013', 'Item 13') 
            ]);
            self.selectedCategoryItem = ko.observable('001');
            $("#fixed-table").ntsFixedTable({ height: 540 });
            
            this.fileId = ko.observable("");
            this.filename = ko.observable("");
            this.fileInfo = ko.observable(null);
            this.accept = ko.observableArray(['.csv', '.xls', '.xlsx']);
            self.stereoType = ko.observable("flowmenu");
            this.onchange = (filename) => {
                console.log(filename);
            };
            
            self.selectedAcceptItem.subscribe((data) => {
                $("#fixed-table tr").removeClass("ui-state-active");
                $("#fixed-table tr[data-id='" + data + "']").addClass("ui-state-active");
            });
        }
        
        upload() {
            var self = this;
            $("#file-upload").ntsFileUpload({ stereoType: "flowmenu" }).done(function(res) {
                self.fileId(res[0].id);
            }).fail(function(err) {
                nts.uk.ui.dialog.alertError(err);
            });
        }
    
//        download() {
//            console.log(nts.uk.request.specials.donwloadFile(this.fileId()));
//        }
        
        getInfo() {
            var self = this;
            nts.uk.request.ajax("/shr/infra/file/storage/infor/" + this.fileId()).done(function(res) {
                self.fileInfo(res);
            });
        }
    
        finished(fileInfo: any) {
            var self = this;
            self.fileId(fileInfo.id);
            console.log(fileInfo);
        }
        
        btnLeftClick() {
            let self = this;
            if (self.selectedAcceptItem() > 0 && self.selectedAcceptItem() <= self.listAcceptItem().length) {
                let selectedAItem = _.find(self.listAcceptItem(), x => {return x.acceptItemNumber() == self.selectedAcceptItem();});
                let selectedCItem = _.find(self.listSelectedCategoryItem(), x => {return x.itemName() == selectedAItem.acceptItemName();});
                self.listAcceptItem.remove(selectedAItem);
                self.listCategoryItem.push(selectedCItem);
                self.listSelectedCategoryItem.remove(selectedCItem);
                for (var i = 0; i < self.listAcceptItem().length; i++) {
                    self.listAcceptItem()[i].acceptItemNumber(i + 1);
                }
                if (self.selectedAcceptItem() >= self.listAcceptItem().length) {
                    self.selectedAcceptItem(self.listAcceptItem().length);
                }
                self.selectedAcceptItem.valueHasMutated();
            }
        }
        
        btnRightClick() {
            let self = this;
            if (self.selectedCategoryItem()) {
                let i = self.listAcceptItem().length + 1;
                let selectedItem = _.find(self.listCategoryItem(), x => {return x.itemCode() == self.selectedCategoryItem();});
                let selectedIndex = _.findIndex(self.listCategoryItem(), x => { return x.itemCode() == self.selectedCategoryItem(); });
                let item = new model.StandardAcceptItem("CSV Item Name " + i, i, 0, i, selectedItem.itemName(), self.selectedStandardImportSetting().conditionSettingCode());
                self.listAcceptItem.push(item);
                self.listSelectedCategoryItem.push(selectedItem);
                self.listCategoryItem.remove(selectedItem);
                if (selectedIndex >= self.listCategoryItem().length && self.listCategoryItem().length > 0)
                    self.selectedCategoryItem(self.listCategoryItem()[self.listCategoryItem().length - 1].itemCode());
                else
                    self.selectedCategoryItem(self.listCategoryItem()[selectedIndex] ? self.listCategoryItem()[selectedIndex].itemCode() : null);
            }
        }
    }
}

$(function() {
    $("#fixed-table").on("click", "tr", function() {
        var id = $(this).attr("data-id");
        nts.uk.ui._viewModel.content.selectedAcceptItem(id);
    })
})