module nts.uk.at.view.kdp010.d {
    import getText = nts.uk.resource.getText;
    export module viewmodel {
        export class ScreenModel {
            correcValue: KnockoutObservable<number> = ko.observable(10);
            letterColors: KnockoutObservable<string> = ko.observable("#ffffff");
            backgroundColors: KnockoutObservable<string> = ko.observable("#0033cc");
            stampValue: KnockoutObservable<number> = ko.observable(3);
            buttonHighlightOption: KnockoutObservableArray<any> = ko.observableArray([
                { id: 1, name: getText("KDP010_202") },
                { id: 0, name: getText("KDP010_203") }
            ]);
            buttonHighlight: KnockoutObservable<number> = ko.observable(0);
            
            attendanceButton: KnockoutObservable<string> = ko.observable(new Button("KDP010_212", "#000000", "#ffffff"));
            leaveWorkButton: KnockoutObservable<string> = ko.observable(new Button("KDP010_213", "#000000", "#ffffff"));
            goOutButton: KnockoutObservable<string> = ko.observable(new Button("KDP010_214", "#000000", "#ffffff"));
            returnButton: KnockoutObservable<string> = ko.observable(new Button("KDP010_215", "#000000", "#ffffff"));
            
            reasonOption: KnockoutObservableArray<any> = ko.observableArray([
                { id: 0, name: getText("KDP010_112") },
                { id: 1, name: getText("KDP010_113") },
                { id: 2, name: getText("KDP010_114") },
                { id: 3, name: getText("KDP010_115") }
            ]);
            reason: KnockoutObservable<number> = ko.observable(0);
            constructor(){
                let self = this;
            }
        }
        export class Button {
            text: KnockoutObservable<string>;
            letterColors: KnockoutObservable<string>;
            backgroundColors: KnockoutObservable<string>;
            constructor(text: string, letterColors: string, backgroundColors: string){
                let self = this;
                self.text = ko.observable(getText(text));
                self.letterColors = ko.observable(letterColors);
                self.backgroundColors = ko.observable(backgroundColors);
            }
         }  
    }
}