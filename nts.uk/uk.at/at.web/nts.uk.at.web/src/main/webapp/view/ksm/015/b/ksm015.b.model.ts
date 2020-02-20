let gridColums = [
       { headerText: nts.uk.resource.getText('KSM015_13'), key: 'code', width: 55 },
       { headerText: nts.uk.resource.getText('KSM015_14'), key: 'name', width: 125, formatter: _.escape },
       { headerText: nts.uk.resource.getText('KSM015_15'), key: 'typeOfWork', width: 125, formatter: _.escape },
       { headerText: nts.uk.resource.getText('KSM015_16'), key: 'workingHours', width: 125, formatter: _.escape }
];

let searchValueOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
       textmode: "text",
       placeholder: "Placeholder for text editor",
       width: "120px",
       textalign: "left"
}));

class ShiftGridOption {
       gridColums: Array<any>;
       searchValueOption: String;

       constructor() {
              let self = this;
              self.gridColums = gridColums;
              self.searchValueOption = searchValueOption;
           
       }
}

class RegistrationForm {
       selectedCode: KnockoutObservable<String>;
       shiftMasterName: KnockoutObservable<String>;
       color: KnockoutObservable<String>;
       note: KnockoutObservable<String>;
       newMode: KnockoutObservable<Boolean>;
       constructor() {
              this.selectedCode = ko.observable("");
              this.shiftMasterName = ko.observable("");
              this.color = ko.observable("#FFFFFF");
              this.note = ko.observable("");
              this.newMode = ko.observable(false);
       }
}