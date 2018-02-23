module nts.uk.com.view.cmf001.o.viewmodel {
    import model = cmf001.share.model;
    import getText = nts.uk.resource.getText;
    import lv = nts.layout.validate;
    import vc = nts.layout.validation;

    export class ScreenModel {
        //wizard
        stepList: Array<NtsWizardStep> = [];
        stepSelected: KnockoutObservable<NtsWizardStep> = ko.observable(null);
        activeStep: KnockoutObservable<number> = ko.observable(0);

        listSysType: KnockoutObservableArray<model.ItemModel> = ko.observableArray([]);
        selectedSysType: KnockoutObservable<string> = ko.observable('');

        listCondition: KnockoutObservableArray<model.ExternalAcceptanceCategoryItemData> = ko.observableArray([]);
        selectedCategoryItem: KnockoutObservable<string> = ko.observable('');
        selectedCategoryItemName: KnockoutObservable<string> = ko.observable('');

        //upload file
        stereoType: KnockoutObservable<string> = ko.observable('');
        fileId: KnockoutObservable<string> = ko.observable('');
        filename: KnockoutObservable<string> = ko.observable('');
        fileInfo: KnockoutObservable<any> = ko.observable("");
        textId: KnockoutObservable<string> = ko.observable("CMF001_447");
        accept: KnockoutObservableArray<string> = ko.observableArray(['.csv']);
        asLink: KnockoutObservable<boolean> = ko.observable(false);
        enable: KnockoutObservable<boolean> = ko.observable(true);

        //gridlist step2   
        acquisitionItems: KnockoutObservableArray<AcquisitionItems> = ko.observableArray([]);
        count: number = 0;

        constructor() {
            var self = this;
            self.stepList = [
                { content: '.step-1' },
                { content: '.step-2' }
            ];
            self.stepSelected = ko.observable({ id: 'step-1', content: '.step-1' });

            self.listSysType = ko.observableArray([
                new model.ItemModel(1, '基本給'),
                new model.ItemModel(2, '役職手当'),
                new model.ItemModel(3, '基本給ながい文字列ながい文字列ながい文字列')
            ]);

            self.selectedSysType = ko.observable(self.listSysType()[0].code);

            self.listCondition = ko.observableArray([
                new model.ExternalAcceptanceCategoryItemData('001', 'Item 1'),
                new model.ExternalAcceptanceCategoryItemData('002', 'Item 2'),
                new model.ExternalAcceptanceCategoryItemData('003', 'Item 3'),
                new model.ExternalAcceptanceCategoryItemData('004', 'Item 4'),
                new model.ExternalAcceptanceCategoryItemData('005', 'Item 5'),
                new model.ExternalAcceptanceCategoryItemData('006', 'Item 1'),
                new model.ExternalAcceptanceCategoryItemData('007', 'Item 2'),
                new model.ExternalAcceptanceCategoryItemData('008', 'Item 3'),
                new model.ExternalAcceptanceCategoryItemData('009', 'Item 4'),
                new model.ExternalAcceptanceCategoryItemData('010', 'Item 5'),
                new model.ExternalAcceptanceCategoryItemData('011', 'Item 1'),
                new model.ExternalAcceptanceCategoryItemData('012', 'Item 2'),
                new model.ExternalAcceptanceCategoryItemData('013', 'Item 3'),
                new model.ExternalAcceptanceCategoryItemData('014', 'Item 4'),
                new model.ExternalAcceptanceCategoryItemData('015', 'Item 5'),
                new model.ExternalAcceptanceCategoryItemData('016', 'Item 1'),
                new model.ExternalAcceptanceCategoryItemData('017', 'Item 2'),
                new model.ExternalAcceptanceCategoryItemData('018', 'Item 3'),
                new model.ExternalAcceptanceCategoryItemData('019', 'Item 4'),
                new model.ExternalAcceptanceCategoryItemData('020', 'Item 5')
            ]);

            self.selectedCategoryItem = ko.observable(self.listCondition()[0].itemCode());
            self.selectedCategoryItemName = ko.observable(self.listCondition()[0].itemName());
            self.selectedCategoryItem.subscribe(function(data: any) {
                if (data) {
                    let item = _.find(ko.toJS(self.listCondition), (x: model.ExternalAcceptanceCategoryItemData) => x.itemCode == data);
                    self.selectedCategoryItemName(item.itemName);
                }
            });

            //grid list step2
            self.count = 0;
            self.acquisitionItems = ko.observableArray([]);
            for (let i = 1; i < 12; i++) {
                self.acquisitionItems.push(new AcquisitionItems('00' + i, '基本給', "description " + i, "基本給" + i, "基本給" + i + i));
            }
            self.count = self.acquisitionItems().length;
            $("#grd_Acquisition").ntsGrid({
                height: '384',
                dataSource: this.acquisitionItems(),
                primaryKey: 'id',
                multiple: false,
                showNumbering: true,
                columns: [
                    { headerText: '', key: 'id', width: 50, hidden: true },
                    { headerText: getText('CMF001_471'), key: 'infoName', width: 130 },
                    { headerText: getText('CMF001_472'), key: 'itemName', width: 180 },
                    { headerText: getText('CMF001_473'), key: 'sampleData', width: 120 },
                    { headerText: getText('CMF001_474'), key: 'itemType', width: 120 },
                    { headerText: getText('CMF001_475'), key: 'id', width: 100, unbound: true, template: '<i data-bind="ntsIcon: { no: 5, width: 30, height: 30 }, click: previous"></i>' },
                    { headerText: getText('CMF001_476'), key: 'id', width: 100, unbound: true, template: '<i data-bind="ntsIcon: { no: 5, width: 30, height: 30 }, click: aa"></i>' }
                ]
            })
        }

        //wizard
        isError() {
            let self = this;
            debugger;
            if (self.activeStep() == 0) {
                $(".form_step0").trigger("validate");
            } else {
         
            }
            if (nts.uk.ui.errors.hasError()) {
                return true;
            }
            return false;
        }

        next() {
            let self = this;
            if (!self.isError()) {
                $('#ex_accept_wizard').ntsWizard("next");
            }

        }
        previous() {
            $('#ex_accept_wizard').ntsWizard("prev");
        }

        //        getCurrentStep() {
        //            return $('#ex_accept_wizard').ntsWizard("getCurrentStep")
        //        }

        //file upload
        upload() {
            var self = this;
            $("#file-upload").ntsFileUpload({ stereoType: "flowmenu" }).done(function(res) {
                self.fileId(res[0].id);
            }).fail(function(err) {
                nts.uk.ui.dialog.alertError(err);
            });
        }

        //gridlist step2
        aa(id) {
            alert("hey");
        }
    }

    class AcquisitionItems {
        id: string;
        infoName: string;
        itemName: string;
        sampleData: string;
        itemType: string;
        constructor(id: string, infoName: string, itemName: string, sampleData: string, itemType: string) {
            this.id = id;
            this.infoName = infoName;
            this.itemName = itemName;
            this.sampleData = sampleData;
            this.itemType = itemType;
        }
    }
}