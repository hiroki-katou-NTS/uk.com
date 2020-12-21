/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.ui.sample.layout.simple {

    @bean()
    export class ViewModel extends ko.ViewModel {
        text: KnockoutObservable<string> = ko.observable('編集モード');

        size: KnockoutObservable<number> = ko.observable(20);
        icon: KnockoutObservable<string> = ko.observable('SELECTED');

        state: KnockoutObservable<boolean> = ko.observable(false);

        click() {
            const vm = this;

            vm.$dialog.error({ messageId: 'MsgB_1', messageParams: [vm.text()] });
        }

        created() {
            const vm = this;

            _.extend(window, { vm });
        }
    }
}