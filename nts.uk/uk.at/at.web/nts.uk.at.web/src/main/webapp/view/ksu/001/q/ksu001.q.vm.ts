module nts.uk.at.view.ksu001.q.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel> = ko.observableArray([
            { id: 'company', title: nts.uk.resource.getText("Com_Company"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
            { id: 'workplace', title: nts.uk.resource.getText("Com_Workplace"), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
        ]);
        selectedTab: KnockoutObservable<string> = ko.observable('company');

        contextMenu: Array<any>;
        source: KnockoutObservableArray<any> = ko.observableArray([
            [{}, {}, { text: "年/", tooltip: "[年/]→[/年]" }, {}],
            [{ text: '5出２休', tooltip: "[5出２休]→[/年]→[２休]" }, {}, { text: '/年', tooltip: "[/年]→[5出]→[5出２休]" }, {}],
            [{}, {}, {}, { text: '年/', tooltip: "[年/]→[/年]→[5出２休]" }]
        ]);

        checked: KnockoutObservable<boolean> = ko.observable(false);
        textName: KnockoutObservable<string> = ko.observable(null);
        selectedCell: KnockoutObservable<any> = ko.observable(null);
        listComPattern: KnockoutObservableArray<any> = ko.observableArray([]);
        listWkpPattern: KnockoutObservableArray<any> = ko.observableArray([]);

        text1Button1: KnockoutObservable<string> = ko.observable(nts.uk.resource.getText("KSU001_1603", ['１ ']));
        text1Button2: KnockoutObservable<string> = ko.observable(nts.uk.resource.getText("KSU001_1603", ['２　']));
        text1Button3: KnockoutObservable<string> = ko.observable(nts.uk.resource.getText("KSU001_1603", ['３　']));
        text1Button4: KnockoutObservable<string> = ko.observable(nts.uk.resource.getText("KSU001_1603", ['４　']));
        text1Button5: KnockoutObservable<string> = ko.observable(nts.uk.resource.getText("KSU001_1603", ['５　']));
        text1Button6: KnockoutObservable<string> = ko.observable(nts.uk.resource.getText("KSU001_1603", ['６　']));
        text1Button7: KnockoutObservable<string> = ko.observable(nts.uk.resource.getText("KSU001_1603", ['７　']));
        text1Button8: KnockoutObservable<string> = ko.observable(nts.uk.resource.getText("KSU001_1603", ['８　']));
        text1Button9: KnockoutObservable<string> = ko.observable(nts.uk.resource.getText("KSU001_1603", ['９　']));
        text1Button10: KnockoutObservable<string> = ko.observable(nts.uk.resource.getText("KSU001_1603", ['１０']));

        text2Button1: KnockoutObservable<string> = ko.observable(nts.uk.resource.getText("KSU001_1603", ['１ ']));
        text2Button2: KnockoutObservable<string> = ko.observable(nts.uk.resource.getText("KSU001_1603", ['２　']));
        text2Button3: KnockoutObservable<string> = ko.observable(nts.uk.resource.getText("KSU001_1603", ['３　']));
        text2Button4: KnockoutObservable<string> = ko.observable(nts.uk.resource.getText("KSU001_1603", ['４　']));
        text2Button5: KnockoutObservable<string> = ko.observable(nts.uk.resource.getText("KSU001_1603", ['５　']));
        text2Button6: KnockoutObservable<string> = ko.observable(nts.uk.resource.getText("KSU001_1603", ['６　']));
        text2Button7: KnockoutObservable<string> = ko.observable(nts.uk.resource.getText("KSU001_1603", ['７　']));
        text2Button8: KnockoutObservable<string> = ko.observable(nts.uk.resource.getText("KSU001_1603", ['８　']));
        text2Button9: KnockoutObservable<string> = ko.observable(nts.uk.resource.getText("KSU001_1603", ['９　']));
        text2Button10: KnockoutObservable<string> = ko.observable(nts.uk.resource.getText("KSU001_1603", ['１０']));

        constructor() {
            let self = this;

            self.contextMenu = [
                { id: "openDialog", text: nts.uk.resource.getText("KSU001_1705"), action: self.openDialogJB },
                { id: "openPopup", text: nts.uk.resource.getText("KSU001_1706"), action: self.openPopup },
                { id: "delete", text: nts.uk.resource.getText("KSU001_1707"), action: self.remove }
            ];

            $("#test1").bind("changenamepopupopening", function(evt, data) {
                self.textName(data.text);
            });

            $("#test1").ntsButtonTable("init", {
                row: 2,
                column: 10,
                source: self.source(),
                contextMenu: self.contextMenu,
                disableMenuOnDataNotSet: [1, 2],
                selectedCell: self.selectedCell(),
                mode: "normal"
            });

            $("#test2").bind("changenamepopupopening", function(evt, data) {
                self.textName(data.text);
            });

            $("#test2").ntsButtonTable("init", {
                row: 2,
                column: 10,
                source: self.source(),
                contextMenu: self.contextMenu, disableMenuOnDataNotSet: [1, 2],
                selectedCell: self.selectedCell(),
                mode: "normal"
            });

            self.clickLinkButton($('div.tab-content-1 a.hyperlink')[0]);
        }

        clickLinkButton(element: any, param?: any): void {
            let self = this;
            _.each($('a.hyperlink'), (a) => {
                $(a).removeClass('color-gray');
            })
            $(element).addClass('color-gray');
        }

        openPopup(button): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            $("#test2").trigger("changenamepopupopening", { text: button[0].innerText });
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
            nts.uk.ui.errors.clearAll()
            $("#popup-area").css('visibility', 'hidden');
            $("#test2").trigger("namechanged", undefined);
        }

        openDialogJA(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            setShared('dataForJA', {
                selectedTab: self.selectedTab(),
            });
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