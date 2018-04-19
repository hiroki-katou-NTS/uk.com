module nts.uk.at.view.kal004.g.viewmodel {
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import textUK = nts.uk.text;
    import block = nts.uk.ui.block;
    import share = nts.uk.at.view.kal004.share.model;
    import service = nts.uk.at.view.kal004.a.service;
    import errors = nts.uk.ui.errors;
    
    export class ScreenModel {
        textlabel: KnockoutObservable<string>;
        tabs: KnockoutObservableArray<any>;
        selectedTab: KnockoutObservable<string>;
                
        constructor() {
            var self = this;
            self.textlabel = ko.observable(nts.uk.ui.windows.getShared("categoryName"));
            self.tabs = ko.observableArray([
                { id: 'tab-1', title: getText('KAL004_69'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: getText('KAL004_70'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-3', title: getText('KAL004_71'), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-4', title: getText('KAL004_72'), content: '.tab-content-4', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-5', title: getText('KAL004_73'), content: '.tab-content-5', enable: ko.observable(true), visible: ko.observable(true) },
                
            ]);
            self.selectedTab = ko.observable('tab-1');   
        }

        startPage(): JQueryPromise<any> {
            var self = this;
         

            var dfd = $.Deferred();

            dfd.resolve();

            return dfd.promise();
        }
        submit() {
            let self = this;
            nts.uk.ui.windows.close();
        }
        closeDialog(): void {
            nts.uk.ui.windows.close();
        }
    }
}