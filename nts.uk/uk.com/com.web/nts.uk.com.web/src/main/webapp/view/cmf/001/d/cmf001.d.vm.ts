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
        systemType: model.ItemModel;
        
        listCategory: KnockoutObservableArray<model.ExternalAcceptanceCategory>;
        selectedCategory: KnockoutObservable<string>;
        
        listCategoryItem: KnockoutObservableArray<model.ExternalAcceptanceCategoryItemData>;
        listSelectedCategoryItem: KnockoutObservableArray<model.ExternalAcceptanceCategoryItemData> = ko.observableArray([]);
        selectedCategoryItem: KnockoutObservable<number>;
        
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
            let item = _.find(model.getSystemTypes(), x => {return x.code == data.systemType;});
            self.systemType = item;
            self.selectedStandardImportSetting = ko.observable(new model.StandardAcceptanceConditionSetting(data.conditionSetting.conditionSettingCode, data.conditionSetting.conditionSettingName, data.conditionSetting.deleteExistData, data.conditionSetting.acceptMode, data.conditionSetting.csvDataItemLineNumber, data.conditionSetting.csvDataStartLine, data.conditionSetting.deleteExistDataMethod));
            
            self.listCategory = ko.observableArray([
                new model.ExternalAcceptanceCategory('1', 'Category 1'),
                new model.ExternalAcceptanceCategory('2', 'Category 2'),
                new model.ExternalAcceptanceCategory('3', 'Category 3')
            ]);
            self.selectedCategory = ko.observable('1');
            
            self.listCategoryItem = ko.observableArray([
                new model.ExternalAcceptanceCategoryItemData(1, 'Item 1'),
                new model.ExternalAcceptanceCategoryItemData(2, 'Item 2'),
                new model.ExternalAcceptanceCategoryItemData(3, 'Item 3'),
                new model.ExternalAcceptanceCategoryItemData(4, 'Item 4'),
                new model.ExternalAcceptanceCategoryItemData(5, 'Item 5'),
                new model.ExternalAcceptanceCategoryItemData(6, 'Item 6'),
                new model.ExternalAcceptanceCategoryItemData(7, 'Item 7'),
                new model.ExternalAcceptanceCategoryItemData(8, 'Item 8'),
                new model.ExternalAcceptanceCategoryItemData(9, 'Item 9'),
                new model.ExternalAcceptanceCategoryItemData(10, 'Item 10'),
                new model.ExternalAcceptanceCategoryItemData(11, 'Item 11'),
                new model.ExternalAcceptanceCategoryItemData(12, 'Item 12'),
                new model.ExternalAcceptanceCategoryItemData(13, 'Item 13') 
            ]);
            
            self.selectedCategoryItem = ko.observable(1);
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
                $("#fixed-table tr").removeClass("my-active-row");
                $("#fixed-table tr[data-id='" + data + "']").addClass("my-active-row");
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
                self.listCategoryItem(_.sortBy(self.listCategoryItem(), ['dispItemNo'])); 
                self.listSelectedCategoryItem.remove(selectedCItem);
                for (var i = 0; i < self.listAcceptItem().length; i++) {
                    self.listAcceptItem()[i].acceptItemNumber(i + 1);
                }
                if (self.selectedAcceptItem() >= self.listAcceptItem().length) 
                    self.selectedAcceptItem(self.listAcceptItem().length);
                else
                    self.selectedAcceptItem.valueHasMutated();
            }
        }
        
        btnRightClick() {
            let self = this;
            if (self.selectedCategoryItem()) {
                let i = self.listAcceptItem().length + 1;
                let selectedItem = _.find(self.listCategoryItem(), x => {return x.itemNo() == self.selectedCategoryItem();});
                let selectedIndex = _.findIndex(self.listCategoryItem(), x => { return x.itemNo() == self.selectedCategoryItem(); });
                let item = new model.StandardAcceptItem("CSV Item Name " + i, i, 0, i, selectedItem.itemName(), self.selectedStandardImportSetting().conditionSettingCode(), selectedItem.itemNo());
                self.listAcceptItem.push(item);
                self.listSelectedCategoryItem.push(selectedItem);
                self.listCategoryItem.remove(selectedItem);
                if (selectedIndex >= self.listCategoryItem().length && self.listCategoryItem().length > 0)
                    self.selectedCategoryItem(self.listCategoryItem()[self.listCategoryItem().length - 1].itemNo());
                else
                    self.selectedCategoryItem(self.listCategoryItem()[selectedIndex] ? self.listCategoryItem()[selectedIndex].itemNo() : null);
            }
        }
        
        //open screen G, H, I, J
        dataTypeSetting(data: model.StandardAcceptItem) {
            let self = this, url = "", formatSetting = null, paramName = "";
            switch(data.itemType()) {
                case model.ITEM_TYPE.NUMERIC: 
                    url = "/view/cmf/001/g/index.xhtml";
                    paramName = "CMF001gParams";
                    if (data.numberFormatSetting)
                        formatSetting = data.numberFormatSetting();
                    break;
                case model.ITEM_TYPE.CHARACTER: 
                    url = "/view/cmf/001/h/index.xhtml";
                    paramName = "CMF001hParams";
                    if (data.charFormatSetting)
                        formatSetting = data.charFormatSetting();
                    break;
                case model.ITEM_TYPE.DATE: 
                    url = "/view/cmf/001/i/index.xhtml";
                    paramName = "CMF001iParams";
                    if (data.dateFormatSetting)
                        formatSetting = data.dateFormatSetting();
                    break;
                case model.ITEM_TYPE.INS_TIME: 
                    url = "/view/cmf/001/j/index.xhtml";
                    paramName = "CMF001jParams";
                    if (data.instTimeFormatSetting)
                        formatSetting = data.instTimeFormatSetting();
                    break;
            }
            setShared(paramName, {formatSetting: formatSetting, inputMode: true, lineNumber: data.acceptItemNumber()}, true);
            
            modal(url).onClosed(function() {
                var output = getShared('CMF001FormatOutput');
                if (output) {
                    
                }
            });
        }
        
        openCMF001l(data: model.StandardAcceptItem) {
            let self = this, condition = null;
            if (data.screenConditionSetting) condition = data.screenConditionSetting();
            setShared('CMF001lParams', {
                dataType: data.itemType(),
                condition: condition,
                inputMode: true
            }, true);
            
            modal("/view/cmf/001/l/index.xhtml").onClosed(function() {
                var output = getShared('CMF001lOutput');
                if (output) {
                    
                }
            });
        }
        
        openCMF001f() {
            let self = this;
            modal("/view/cmf/001/f/index.xhtml").onClosed(function() {
                
            });
        }
        
        openCMF001e(data: model.StandardAcceptItem) {
            let self = this;
            let listCsvData = [];
            for (let i = 1; i< 15; i++) {
                listCsvData.push({csvItemName: 'Column ' + i, csvItemNumber: i});
            }
            setShared('CMF001eParams', {
                listCsvData: listCsvData,
                selectedScvData: 3
            }, true);
            
            modal("/view/cmf/001/e/index.xhtml").onClosed(function() {
                var output = getShared('CMF001eOutput');
                if (output) {
                    
                }
            });
        }
        
        openCMF001b() {
            let self = this;
            nts.uk.request.jump("/view/cmf/001/b/index.xhtml", { 
                conditionCode: self.selectedStandardImportSetting().conditionSettingCode(),
                sysType: self.systemType.code 
            });
        }
    }
}

$(function() {
    $("#fixed-table").on("click", "tr", function() {
        var id = $(this).attr("data-id");
        nts.uk.ui._viewModel.content.selectedAcceptItem(id);
    })
})