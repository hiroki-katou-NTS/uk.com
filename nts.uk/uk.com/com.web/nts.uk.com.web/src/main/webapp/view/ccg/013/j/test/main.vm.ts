module nts.uk.sys.view.ccg013.j.test.viewmodel {
    export class ScreenModel {
        nameTitleBar: KnockoutObservable<string>;
        letterColor: KnockoutObservable<string>;
        backgroundColor: KnockoutObservable<string>;
        imageId: KnockoutObservable<string>;

        constructor() {
            var self = this;
            self.nameTitleBar = ko.observable("");
            self.letterColor = ko.observable("");
            self.backgroundColor = ko.observable("");
            self.imageId = ko.observable("");
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();

            return dfd.promise();
        }
        openDialog() {
            var self = this;
            var titleBar = new TitleBar(self.nameTitleBar(), self.letterColor(), self.backgroundColor(), self.imageId());
            nts.uk.ui.windows.setShared('CCG013A_ToChild_TitleBar', titleBar);
            nts.uk.ui.windows.sub.modal("/view/ccg/013/j/index.xhtml").onClosed(() => {
                var self = this;
                var returnTitleMenu = nts.uk.ui.windows.getShared("CCG013J_ToMain_TitleBar");
                if (returnTitleMenu !== undefined) {
                    self.nameTitleBar(returnTitleMenu.nameTitleBar);
                    self.letterColor(returnTitleMenu.letterColor);
                    self.backgroundColor(returnTitleMenu.backgroundColor);
                    self.imageId(returnTitleMenu.imageId);
                }
            });
        }
    }
    class TitleBar {
        nameTitleBar: string;
        letterColor: string;
        backgroundColor: string;
        imageId: string;
        constructor(nameTitleBar: string, letterColor: string, backgroundColor: string, imageId: string) {
            this.nameTitleBar = nameTitleBar;
            this.letterColor = letterColor;
            this.backgroundColor = backgroundColor;
            this.imageId = imageId;
        }
    }
}