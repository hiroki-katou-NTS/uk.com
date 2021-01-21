module nts.uk.at.view.kaf000.b.component1.viewmodel {
    import CommonProcess = nts.uk.at.view.kaf000.shr.viewmodel.CommonProcess;

    @component({
        name: 'kaf000-b-component1',
        template: `
            <div id="kaf000-b-component1" data-bind="if: displayArea">
                <div class="table" data-bind="if: displayMsg">
                    <div class="cell message-td-1" data-bind="text: $i18n('KAF000_1')"></div>
                    <div class="cell message-td" data-bind="text: message"></div>
                </div>
                <div class="table" data-bind="if: displayDeadline">
                    <div class="cell message-td-1" data-bind="text: $i18n('KAF000_2')"></div>
                    <div class="cell message-td" data-bind="text: deadline"></div>
                </div>
            </div>

        `
    })
    class Kaf000BComponent1ViewModel extends ko.ViewModel {
		appType: KnockoutObservable<number> = null;
        appDispInfoStartupOutput: any;
        message: KnockoutObservable<string>;
        deadline: KnockoutObservable<string>;
        displayArea: KnockoutObservable<boolean>;
        displayMsg: KnockoutObservable<boolean>;
        displayDeadline: KnockoutObservable<boolean>;

        created(params: any) {
            const vm = this;
			vm.appType = params.appType;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.message = ko.observable("line111111111111111111");
            vm.deadline = ko.observable("line222222222222222222");
            vm.displayArea = ko.pureComputed(() => {
                return vm.displayMsg() || vm.displayDeadline();
            });
            vm.displayMsg = ko.observable(false);
            vm.displayDeadline = ko.observable(false);

            CommonProcess.initDeadlineMsg(vm.appDispInfoStartupOutput(), vm);

			vm.appDispInfoStartupOutput.subscribe(value => {
                CommonProcess.initDeadlineMsg(value, vm);
            });
        }

        mounted() {
            const vm = this;
        }
    }
}