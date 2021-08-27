module nts.uk.at.view.kaf000.b.component1.viewmodel {
    import CommonProcess = nts.uk.at.view.kaf000.shr.viewmodel.CommonProcess;

    @component({
        name: 'kaf000-b-component1',
        template: `
            <div id="kaf000-b-component1">
				<div data-bind="if: displayMsg">
					<div style="padding-top: 20px" class="message-div">
						<div style="word-break: break-all; word-wrap: break-word; line-height: 1.6;" data-bind="html: message"></div>
					</div>
				</div>
				<div style="display: none;">
					<div class="message-div">
						<div data-bind="text: $i18n('KAF000_2')" style="padding-top: 20px; padding-bottom: 10px; font-weight: bold;"></div>
						<div data-bind="html: deadline" style="line-height: 1.8;"></div>
					</div>
				</div>
			</div>
        `
    })
    class Kaf000BComponent1ViewModel extends ko.ViewModel {
		appType: KnockoutObservable<number> = null;
        appDispInfoStartupOutput: any;
        message: KnockoutObservable<string>;
        deadline: KnockoutObservable<string>;
        displayMsg: KnockoutObservable<boolean>;
        displayDeadline: KnockoutObservable<boolean>;

        created(params: any) {
            const vm = this;
			vm.appType = params.appType;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.message = ko.observable("line111111111111111111");
            vm.deadline = ko.observable("line222222222222222222");
            vm.displayMsg = ko.observable(false);
            vm.displayDeadline = ko.observable(false);

			CommonProcess.initDeadlineMsg(vm.appDispInfoStartupOutput(), vm);
			
            vm.appDispInfoStartupOutput.subscribe(value => {
                CommonProcess.initDeadlineMsg(value, vm);
				vm.displayDeadline(false);
            });
        }

        mounted() {
            const vm = this;
        }
    }
}