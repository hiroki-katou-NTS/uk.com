module ksu001.q.viewmodel {

    export class ScreenModel {
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel> = ko.observableArray([
            { id: 'tab-1', title: nts.uk.resource.getText("Com_Company"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
            { id: 'tab-2', title: nts.uk.resource.getText("Com_Workplace"), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
        ]);
        selectedTab: KnockoutObservable<string> = ko.observable('tab-1');

        contextMenu: Array<any>;
        source: KnockoutObservableArray<any> = ko.observableArray([
            [{}, {}, { text: "test", tooltip: "test" }, {}],
            [],
            []
        ]);

        checked: KnockoutObservable<boolean> = ko.observable(true);
        textName: KnockoutObservable<string> = ko.observable('');

        constructor() {
            let self = this;

            self.contextMenu = [
                { id: "openDialog", text: nts.uk.resource.getText("KSU001_1705"), action: self.openDialogJB },
                { id: "openPopup", text: nts.uk.resource.getText("KSU001_1706"), action: self.openPopup },
                { id: "delete", text: nts.uk.resource.getText("KSU001_1707"), action: self.remove }
            ];
            $("#test2").ntsButtonTable("init", { row: 3, column: 10, source: self.source(), contextMenu: self.contextMenu, disableMenuOnDataNotSet: [1, 2], mode: "normal" });
        }

        openPopup() {
            let dfd = $.Deferred();
            $("#popup-area").css('visibility', 'visible');
            return dfd.promise();
        }

        decision(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            dfd.resolve({ text: self.textName(), tooltip: 'ahihi' });
            $("#popup-area").css('visibility', 'hidden');
            return dfd.promise();
        }

        closePopup(): void {
            $("#popup-area").css('visibility', 'hidden');
        }

        openDialogJA(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            nts.uk.ui.windows.sub.modal("/view/ksu/001/ja/index.xhtml").onClosed(() => {
                dfd.resolve(undefined);
            });
            return dfd.promise();
        }

        openDialogJB(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            nts.uk.ui.windows.sub.modal("/view/ksu/001/jb/index.xhtml").onClosed(() => {
                dfd.resolve(undefined);
            });
            return dfd.promise();
        }

        remove(): JQueryPromise<any> {
            let dfd = $.Deferred();

            setTimeout(function() {
                dfd.resolve(undefined);
            }, 10);

            return dfd.promise();
        }
    }
}