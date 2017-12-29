module nts.uk.at.view.ksu001.ja.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {

        selectedTab: KnockoutObservable<string> = ko.observable(getShared('dataForJA').selectedTab);
        workplaceName: KnockoutObservable<string> = ko.observable(getShared('dataForJA').workplaceName);
        selectedLinkButton: KnockoutObservable<number> = ko.observable(getShared('dataForJA').selectedLinkButton);
        dataSource: KnockoutObservableArray<any> = ko.observableArray(getShared('dataForJA').dataSource);
        textButtonArr: KnockoutObservableArray<any> = ko.observableArray(getShared('dataForJA').listTextLink);
        listPattern: KnockoutObservableArray<any> = ko.observableArray(getShared('dataForJA').listPattern);
        isVisibleWkpName: KnockoutObservable<boolean> = ko.observable(false);
        groupName: KnockoutObservable<string> = ko.observable('');
        note: KnockoutObservable<string> = ko.observable('');
        groupUsageAtr: KnockoutObservableArray<any> = ko.observableArray([
            { code: 0, name: '使用区分' },
            { code: 1, name: '使用しない' }
        ]);
        selectedGroupUsageAtr: KnockoutObservable<number> = ko.observable(0);
        contextMenu: Array<any>;
        textName: KnockoutObservable<string> = ko.observable(null);
        tooltip: KnockoutObservable<string> = ko.observable(null);
        source: KnockoutObservableArray<any> = ko.observableArray([]);
        sourceEmpty: any[] = [{}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}];
        isDeleteEnable: KnockoutObservable<boolean> = ko.observable(true);
        initData: KnockoutObservable<any> = ko.observable(null);
        flag: boolean = false;

        constructor() {
            let self = this;

            self.contextMenu = [
                { id: "openPopup", text: nts.uk.resource.getText("KSU001_1706"), action: self.openPopup },
                { id: "delete", text: nts.uk.resource.getText("KSU001_1708"), action: self.remove }
            ];

            $("#test2").bind("getdatabutton", function(evt, data) {
                self.textName(data.text);
                self.tooltip(data.tooltip);
            });
        }

        /**
         * init
         */
        init(): void {
            let self = this;

            if (self.selectedTab() === 'company') {
                self.isVisibleWkpName(false);
            } else {
                self.isVisibleWkpName(true);
                nts.uk.ui.windows.getSelf().setSize(400, 845);
            }
            self.clickLinkButton(null, self.selectedLinkButton);
            self.flag = true;
        }

        /**
         * linkbutton has color gray when clicked
         */
        clickLinkButton(element?: any, param?: any): void {
            $('input#textName').focus();
            let self = this, index: number = param();
            if (self.flag && self.isChanged()) {
                nts.uk.ui.dialog.confirm({ messageId: "Msg_447" }).ifYes(() => {
                    alert("Yes");
                }).ifNo(() => {
                    return;
                });
            }

            //set enable/disable delete button
            self.isDeleteEnable(!!self.listPattern()[index]);
            nts.uk.ui.errors.clearAll();
            _.find($('#group-link-button-ja a.hyperlink.color-gray'), (a) => {
                $(a).removeClass('color-gray');
            })
            $($('a.hyperlink')[index]).addClass('color-gray');
            self.groupName(self.listPattern()[index] ? self.listPattern()[index].groupName : null);
            self.selectedGroupUsageAtr(self.listPattern()[index] ? self.listPattern()[index].groupUsageAtr : 0);
            self.note(self.listPattern()[index] ? self.listPattern()[index].note : null);
            self.source(self.dataSource()[index] || self.sourceEmpty);
            self.initData({
                groupName: self.groupName(),
                selectedGroupUsageAtr: self.selectedGroupUsageAtr(),
                note: self.note(),
                source: self.source()
            });
        }

        openPopup(button): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            $("#test2").trigger("getdatabutton", { text: button[0].innerText, tooltip: button[0].title });
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

        /**
         * decision
         */
        decision(): void {
            let self = this;
            $("#popup-area").css('visibility', 'hidden');
            $("#test2").trigger("namechanged", { text: self.textName(), tooltip: self.tooltip() });
        }

        /**
         * close popup
         */
        closePopup(): void {
            nts.uk.ui.errors.clearAll()
            $("#popup-area").css('visibility', 'hidden');
            $("#test2").trigger("namechanged", undefined);
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
        openDialogJB(evt, data): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            self.textName(data ? data.text : null);
            self.tooltip(data ? data.tooltip : null);
            setShared("dataForJB", {
                text: self.textName(),
                tooltip: self.tooltip()
            });
            nts.uk.ui.windows.sub.modal("/view/ksu/001/jb/index.xhtml").onClosed(() => {
                let data = getShared("dataFromJB");
                self.textName(data ? data.text : self.textName());
                self.tooltip(data ? data.tooltip : self.tooltip());
                dfd.resolve({ text: self.textName(), tooltip: self.tooltip() });
            });

            return dfd.promise();
        }

        /**
         * saveData
         */
        saveData(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            if (self.selectedGroupUsageAtr() == 0 && self.source().length == 0) {
                nts.uk.ui.dialog.alertError({ messageId: "Msg_510" });
            }
            dfd.resolve();
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

        deletePatternItem(): void {
            let self = this;
            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                alert("Yes");
            }).ifNo(() => {
                return;
            });
        }

        /**
         * check data is changed or not
         * return true if data is changed
         * return false if data is not changed
         */
        isChanged(): boolean {
            let self = this;
            if (self.initData().groupName == self.groupName()
                && self.initData().selectedGroupUsageAtr == self.selectedGroupUsageAtr()
                && self.initData().note == self.note()
                && _.isEqual(self.initData().source.sort(), self.source().sort())) {
                return false;
            }
            return true;
        }
    }
}