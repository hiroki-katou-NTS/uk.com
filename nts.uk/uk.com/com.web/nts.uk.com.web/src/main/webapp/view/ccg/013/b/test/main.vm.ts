module nts.uk.sys.view.ccg013.b.test.viewmodel {
    export class ScreenModel {
        nameB1_3: KnockoutObservable<string>;
        pickerLetter: KnockoutObservable<string>;
        radioActlass: KnockoutObservable<string>;
        pickerBackground: KnockoutObservable<string>;
        selectSetting: KnockoutObservable<string>;
        codeStandardMenu: KnockoutObservable<string>;
        nameStandardMenu: KnockoutObservable<string>;

        constructor() {
            var self = this;
            self.nameB1_3 = ko.observable("");
            self.pickerLetter = ko.observable("");
            self.radioActlass = ko.observable("");
            self.pickerBackground = ko.observable("");
            self.selectSetting = ko.observable("");
            self.codeStandardMenu = ko.observable("");
            self.nameStandardMenu = ko.observable("");
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();

            return dfd.promise();
        }
        clickDialog() {
            var self = this;
            nts.uk.ui.block.invisible();
            nts.uk.ui.windows.setShared('Share',
                self.nameB1_3(),
                self.pickerLetter(),
                self.radioActlass(),
                self.pickerBackground(),
                self.selectSetting(),
                self.codeStandardMenu(),
                self.nameStandardMenu());
           nts.uk.ui.windows.sub.modal("/view/kdl/010/a/index.xhtml", { dialogClass: "no-close" }).onClosed(() => {
                var self = this;
                var returnItem = nts.uk.ui.windows.getShared("GetCCG_013");
                if (returnItem !== undefined) {
                    self.pickerLetter(return
                    self.nameB1_3(returnItem);Item);
                    self.radioActlass(returnItem);
                    self.pickerBackground(returnItem);
                    self.selectSetting(returnItem);
                    self.codeStandardMenu(returnItem);
                    self.nameStandardMenu(returnItem);
                    nts.uk.ui.block.clear();
                }
                else{
                    self.nameB1_3 = ko.observable("");
                    self.pickerLetter = ko.observable("");
                    self.radioActlass = ko.observable("");
                    self.pickerBackground = ko.observable("");
                    self.selectSetting = ko.observable("");
                    self.codeStandardMenu = ko.observable("");
                    self.nameStandardMenu = ko.observable("");
                    nts.uk.ui.block.clear();}
            }); 
        }
    }
}