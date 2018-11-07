module nts.uk.pr.view.qmm017.d.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        // tabs variables
        screenDtabs: KnockoutObservableArray<any>;
        screenDselectedTab: KnockoutObservable<string>;
        
        constructor() {
            var self = this;
            self.screenDtabs = ko.observableArray([
                {id: 'tab-1', title: getText('QMM017_6'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-2', title: getText('QMM017_6'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-3', title: getText('QMM017_6'), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-4', title: getText('QMM017_6'), content: '.tab-content-4', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-5', title: getText('QMM017_6'), content: '.tab-content-5', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-6', title: getText('QMM017_6'), content: '.tab-content-6', enable: ko.observable(false), visible: ko.observable(false)},
                {id: 'tab-7', title: getText('QMM017_7'), content: '.tab-content-7', enable: ko.observable(true), visible: ko.observable(true)}
            ]);
            self.screenDselectedTab = ko.observable('tab-1');
        }
        
        startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }
    }
    
}