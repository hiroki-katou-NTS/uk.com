module ksu001.q.viewmodel {

    export class ScreenModel {
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel> = ko.observableArray([
            { id: 'tab-1', title: nts.uk.resource.getText("Com_Company"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
            { id: 'tab-2', title: nts.uk.resource.getText("Com_Workplace"), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
        ]);
        selectedTab: KnockoutObservable<string> = ko.observable('tab-1');

        contextMenu: Array<any>;
        source: KnockoutObservableArray<any> = ko.observableArray([
            [{}, {}, { text: "年/", tooltip: "[年/]→[/年]" }, {}],
            [{ text: '5出２休', tooltip: "[5出２休]→[/年]→[２休]" }, {}, { text: '/年', tooltip: "[/年]→[5出]→[5出２休]" }, {}],
            [{}, {}, {}, { text: '年/', tooltip: "[年/]→[/年]→[5出２休]" }]
        ]);

        checked: KnockoutObservable<boolean> = ko.observable(true);
        textName: KnockoutObservable<string> = ko.observable('');
        selectedCell: KnockoutObservable<any> = ko.observable(null);

        constructor() {
            let self = this;

            self.contextMenu = [
                { id: "openDialog", text: nts.uk.resource.getText("KSU001_1705"), action: self.openDialogJB },
                { id: "openPopup", text: nts.uk.resource.getText("KSU001_1706"), action: self.openPopup },
                { id: "delete", text: nts.uk.resource.getText("KSU001_1707"), action: self.remove }
            ];

            $("#test2").ntsButtonTable("init", { row: 3, column: 10, source: self.source(), contextMenu: self.contextMenu, disableMenuOnDataNotSet: [1, 2], mode: "normal", selectedCell: self.selectedCell() });
        }

        openPopup(button): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            $("#test2").unbind("namechanged");
            $("#popup-area").css('visibility', 'visible');
            let buttonWidth = button.outerWidth(true) - 30;
            $("#popup-area").position({ "of": button, my: "left+" + buttonWidth + " top", at: "left+" + buttonWidth + " top" });
            $("#test2").bind("namechanged", function(evt, data) {
                $("#test2").unbind("namechanged");
                if (!nts.uk.util.isNullOrUndefined(data)) {
                    dfd.resolve(data);
                } else {
                    dfd.resolve(button.parent().data("cell-data"));
                }
            });

            return dfd.promise();
        }

        decision(): void {
            let self = this;
            $("#popup-area").css('visibility', 'hidden');
            $("#test2").trigger("namechanged", { text: self.textName(), tooltip: 'ahihi' });
        }

        closePopup(): void {
            $("#popup-area").css('visibility', 'hidden');
            $("#test2").trigger("namechanged", undefined);
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
                $("#test2").trigger("namechanged", undefined);
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