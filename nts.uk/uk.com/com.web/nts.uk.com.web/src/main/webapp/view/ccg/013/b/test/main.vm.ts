module nts.uk.sys.view.ccg013.b.test.viewmodel {
    export class ScreenModel {
        nameMenuBar: KnockoutObservable<string>;
        pickerLetter: KnockoutObservable<string>;
        radioActlass: KnockoutObservable<string>;
        pickerBackground: KnockoutObservable<string>;
        selectSetting: KnockoutObservable<string>;
        codeStandardMenu: KnockoutObservable<string>;
        nameStandardMenu: KnockoutObservable<string>;

        constructor() {
            var self = this;
            self.nameMenuBar = ko.observable("");
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
        openDialog() {
            var self = this;
            var menuBar = new MenuBar(self.nameMenuBar(), self.pickerLetter() ,self.pickerBackground() , self.radioActlass())
            nts.uk.ui.windows.setShared('CCG013A_StandardMeNu', menuBar);
            nts.uk.ui.windows.sub.modal("/view/ccg/013/b/index.xhtml").onClosed(() => {
                var self = this;
                var returnItem = nts.uk.ui.windows.getShared("CCG013B_MenuBar");
                if (returnItem !== undefined) {
                    self.nameMenuBar(returnItem.nameMenuBar);
                    self.pickerLetter(returnItem.letterColor);
                    self.radioActlass(returnItem.selectedRadioAtcClass);
                    self.pickerBackground(returnItem.backgroundColor);
                    self.selectSetting(returnItem.selectSetting);
                    self.codeStandardMenu(returnItem.codeStandardMenu);
                    self.nameStandardMenu(returnItem.nameStandardMenu);
                }
                else{
                    nts.uk.ui.block.clear();}
            });
        }
    }
    class MenuBar{
       nameMenuBar : string;
       pickerLetter : string;
       pickerBackground :string; 
       radioActlass : string 
       constructor(nameMenuBar: string, pickerLetter: string, pickerBackground: string, radioActlass: number) {
            this.nameMenuBar = nameMenuBar;
            this.pickerLetter = pickerLetter;
            this.pickerBackground = pickerBackground;
            this.radioActlass = radioActlass;
        }
        
     }
}