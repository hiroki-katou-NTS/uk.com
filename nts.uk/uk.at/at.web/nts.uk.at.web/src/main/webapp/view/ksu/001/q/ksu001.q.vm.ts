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
                { id: "cut", text: "切り取り", action: self.openDialogJA },
                { id: "copy", text: "名前を変更", action: self.openPopup },
                { id: "delete", text: "削除", action: self.remove }
            ];
            $("#test2").ntsButtonTable("init", { row: 3, column: 10, source: self.source(), contextMenu: self.contextMenu, disableMenuOnDataNotSet: [1, 2], mode: "normal" });
        }

        openPopup() {
            let dfd = $.Deferred();
            $("#popup-area").css('visibility', 'visible');
            return dfd.promise();
        }
        
        decision(): void {
            let self = this;
            
        }

        closePopup(): void {
            $("#popup-area").css('visibility', 'hidden');
        }

        openDialogJA() {
            let self = this, dfd = $.Deferred();
            nts.uk.ui.windows.sub.modal("/view/ksu/001/ja/index.xhtml").onClosed(() => {
                dfd.resolve(undefined);
            });
            return dfd.promise();
        }

        remove() {
            let dfd = $.Deferred();

            setTimeout(function() {
                dfd.resolve(undefined);
            }, 10);

            return dfd.promise();
        }

    }
}