module nts.uk.pr.view.qmm012.j.viewmodel {
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import setShared = nts.uk.ui.windows.setShared;

    export class ScreenModel {
        lstCustomes: KnockoutObservableArray<any> = ko.observableArray([]);
        currentCode: KnockoutObservable<string> = ko.observable('1');
        
        lstStatementItem: KnockoutObservableArray<any> = ko.observableArray([]);
        StatementItemName: KnockoutObservableArray<any> = ko.observableArray([]);
        
        itemNameCd: KnockoutObservable<string> = ko.observable('');
        name: KnockoutObservable<string> = ko.observable('');
        shortName: KnockoutObservable<string> = ko.observable('');
        englishName: KnockoutObservable<string> = ko.observable('');
        otherLanguageName: KnockoutObservable<string> = ko.observable('');
        constructor() {
            let self = this;
        }
        onSelectTabB(){};
        onSelectTabC(){};
        onSelectTabD(){};
        onSelectTabE(){};
        onSelectTabF(){};
        register(){};
        cancel(){};
    }
}