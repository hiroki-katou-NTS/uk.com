module nts.uk.at.view.ksu001.ja.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        isVisibleWkpName: KnockoutObservable<boolean> = ko.observable(false);
        txtName: KnockoutObservable<string> = ko.observable('');
        txtName1: KnockoutObservable<string> = ko.observable('');
        roundingRules: KnockoutObservableArray<any> = ko.observableArray([
            { code: '1', name: '四捨五入' },
            { code: '2', name: '切り上げ' }
        ]);
        selectedRuleCode: KnockoutObservable<number> = ko.observable(1);
        selectedTab: KnockoutObservable<string> = ko.observable(getShared('dataForJA').selectedTab);
        contextMenu: Array<any>;
        source: KnockoutObservableArray<any> = ko.observableArray([
            [{}, {}, { text: "test", tooltip: "test" }, {}],
            [],
            []
        ]);

        constructor() {
            let self = this;

            self.contextMenu = [
                { id: "copy", text: nts.uk.resource.getText("KSU001_1706"), action: null },
                { id: "delete", text: nts.uk.resource.getText("KSU001_1708"), action: self.remove }
            ];

            $("#test2").ntsButtonTable("init", {
                row: 2,
                column: 10,
                source: self.source(),
                contextMenu: self.contextMenu,
                click: self.openDialogJB,
                mode: "master"
            });

            if (self.selectedTab() === 'company') {
                self.isVisibleWkpName(false);
                $('#group-link-button').css('margin-top', '0px');
            } else {
                self.isVisibleWkpName(true);
                nts.uk.ui.windows.getSelf().setSize(400, 845);
            }
        }

        /**
         * init
         */
        init(): void {
            let self = this;

            if (self.selectedTab() === 'company') {

            } else {

            }
        }

        handle(data): void {
            let self = this;
            if (data.length == 0) {
                self.clickLinkButton($('a.hyperlink')[0]);
            } else {

            }
        }

        clickLinkButton(element: any, param?: any): void {
            let self = this;
            _.each($('a.hyperlink'), (a) => {
                $(a).removeClass('color-gray');
            })
            $(element).addClass('color-gray');
        }

        /**
         * Clear all data of button table
         */
        clear() {
            let self = this;
            $("#test2").ntsButtonTable("dataSource", []);
        }

        /**
         * Close dialog
         */
        closeDialog(): void {
            nts.uk.ui.windows.close();
        }

        /**
         * open Dialog JB
         */
        openDialogJB(): JQueryPromise<any> {
            let dfd = $.Deferred();
            nts.uk.ui.windows.sub.modal("/view/ksu/001/jb/index.xhtml").onClosed(() => {
                dfd.resolve();
            });

            return dfd.promise();
        }

        /**
         * remove data of button table
         */
        remove(): JQueryPromise<any> {
            let dfd = $.Deferred();

            setTimeout(function() {
                dfd.resolve(undefined);
            }, 10);

            return dfd.promise();
        }
    }
}