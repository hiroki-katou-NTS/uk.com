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
        
        constructor() {
            let self = this;

            self.contextMenu = [
                { id: "cut", text: "切り取り", action: self.openDialogJA, style: "icon icon-dot" },
                { id: "copy", text: "名前を変更", action: self.openDialogJA, style: "icon icon-dot" },
                { id: "delete", text: "削除", action: self.remove, style: "icon icon-close" }
            ];
            $("#test2").ntsButtonTable("init", { row: 3, column: 10, source: self.source(), contextMenu: self.contextMenu, disableMenuOnDataNotSet: [1, 2], mode: "normal" });
        }

        openDialogJA(): void {
            let self = this;
            nts.uk.ui.windows.sub.modal("/view/ksu/001/ja/index.xhtml").onClosed(() => { });
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