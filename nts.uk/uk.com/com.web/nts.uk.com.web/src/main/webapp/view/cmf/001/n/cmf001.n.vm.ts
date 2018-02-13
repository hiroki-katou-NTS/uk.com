module nts.uk.com.view.cmf001.n.viewmodel {
    import model = cmf001.share.model;
    export class ScreenModel {

        stepList: Array<NtsWizardStep>;
        stepSelected: KnockoutObservable<NtsWizardStep>;
        activeStep: KnockoutObservable<number>;

        itemList: KnockoutObservableArray<ItemModel>;
        selectedCode: KnockoutObservable<string>;

        listCategoryItem: KnockoutObservableArray<model.ExternalAcceptanceCategoryItemData>;
        selectedCategoryItem: KnockoutObservable<string>;

        //upload file
        stereoType: KnockoutObservable<string>;
        fileId: KnockoutObservable<string>;
        filename: KnockoutObservable<string>;
        fileInfo: KnockoutObservable<any>;
        textId: KnockoutObservable<string>;
        accept: KnockoutObservableArray<string>;
        asLink: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
        immediate: KnockoutObservable<boolean>;
        //        onchange: (filename) => void;
        //        onfilenameclick: (filename) => void;

        constructor() {
            var self = this;
            self.stepList = [
                { content: '.step-1' },
                { content: '.step-2' } 
            ];
            self.activeStep = ko.observable(0);
            self.stepSelected = ko.observable({ id: 'step-1', content: '.step-1' });

            self.itemList = ko.observableArray([
                new model.ItemModel(1, '基本給'),
                new model.ItemModel(2, '役職手当'),
                new model.ItemModel(3, '基本給ながい文字列ながい文字列ながい文字列')
            ]);

            self.selectedCode = ko.observable('1');

            self.listCategoryItem = ko.observableArray([
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
            self.selectedCategoryItem = ko.observable('001');

            //upload file
            this.fileId = ko.observable("");
            this.filename = ko.observable("");
            this.fileInfo = ko.observable("");
            this.accept = ko.observableArray([".png", '.gif', '.jpg', '.jpeg']);
            this.textId = ko.observable("CMF001_447");
            this.asLink = ko.observable(false);
            this.enable = ko.observable(true); 
            //            this.onchange = (filename) => {
            //                console.log(filename);
            //            };
            //            this.onfilenameclick = (filename) => {
            //                alert(filename);
            //            };
        }

        begin() {
            $('#O2_1_wizard').ntsWizard("begin");
        }
        end() {
            $('#O2_1_wizard').ntsWizard("end");
        }
        next() {
            $('#O2_1_wizard').ntsWizard("next");
        }
        previous() {
            $('#O2_1_wizard').ntsWizard("prev");
        }
        getCurrentStep() {
            alert($('#O2_1_wizard').ntsWizard("getCurrentStep"));
        }
        goto() {
            var index = this.stepList.indexOf(this.stepSelected());
            $('#O2_1_wizard').ntsWizard("goto", index);
        }

        //file upload
        upload() {
            var self = this;
            $("#file-upload").ntsFileUpload({ stereoType: "flowmenu" }).done(function(res) {
                self.fileId(res[0].id);
            }).fail(function(err) {
                nts.uk.ui.dialog.alertError(err);
            });
        }
    }
}