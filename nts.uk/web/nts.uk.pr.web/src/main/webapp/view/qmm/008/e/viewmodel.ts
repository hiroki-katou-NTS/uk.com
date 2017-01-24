module nts.uk.pr.view.qmm008.e {
    export module viewmodel {
        export class ScreenModel {

            leftShow: KnockoutObservable<boolean>;
            rightShow: KnockoutObservable<boolean>;
            leftBtnText: KnockoutComputed<string>;
            rightBtnText: KnockoutComputed<string>;

            constructor() {
                var self = this;

                self.leftShow = ko.observable(true);
                self.rightShow = ko.observable(true);
                self.leftBtnText = ko.computed(function() { if (self.leftShow()) return "-"; return "+"; });
                self.rightBtnText = ko.computed(function() { if (self.rightShow()) return "-"; return "+"; });
            }

            leftToggle() {
                this.leftShow(!this.leftShow());
            }
            rightToggle() {
                this.rightShow(!this.rightShow());
            }

            closeDialog() {
                nts.uk.ui.windows.close();
            }
        }
    }
}