module nts.uk.at.view.kaf000.b.component4.viewmodel {

    @component({
        name: 'kaf000-b-component4',
        template: `
            <div id="kaf000-b-component4" style="max-width: 185px">
				<div style="margin-top: 15px">
					<div class="col-1">
						<div class="cell valign-center" data-bind="ntsFormLabel:{}, text: $i18n('KAF000_44')"></div>
					</div>
					<div class="valign-center">
						<div style="">
							<div class='applicant limited-label' data-bind="text: applicant"></div>
							<div class='inputPerson limited-label' data-bind="text: inputPerson"></div>
						</div>	
					</div>
				</div>
			</div>
        `
    })
    class Kaf000BComponent4ViewModel extends ko.ViewModel {
		appType: KnockoutObservable<number> = null;
        appDispInfoStartupOutput: any;
        // employeeName: KnockoutObservable<string>;
		applicant: KnockoutObservable<string> = ko.observable('employeeName');
		inputPerson: KnockoutObservable<string> = ko.observable(null);
        created(params: any) {
            const vm = this;
			const startFormInputPerson = '（入力者： ';
			const endFormInputPerson = '）';
			vm.appType = params.appType;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            // vm.employeeName = ko.observable("employeeName");

			let inputPerson = 
				_.isNil(vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.opEmployeeInfo) ? 
				null : 
				(startFormInputPerson + vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.opEmployeeInfo.bussinessName + endFormInputPerson);
			if (_.isNil(inputPerson)) {
	            // vm.employeeName(vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.employeeInfoLst[0].bussinessName);	
				vm.applicant(vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.employeeInfoLst[0].bussinessName);
			} else {
				// vm.employeeName(vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.employeeInfoLst[0].bussinessName + inputPerson);
				vm.applicant(vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.employeeInfoLst[0].bussinessName);
				vm.inputPerson(inputPerson);
			}
            params.application().employeeIDLst(_.map(vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.employeeInfoLst, (o: any) => o.sid));

			vm.appDispInfoStartupOutput.subscribe((value: any) => {
				let inputPerson = 
				_.isNil(vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.opEmployeeInfo) ? 
				null : 
				(startFormInputPerson + vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.opEmployeeInfo.bussinessName + endFormInputPerson);
				if (_.isNil(inputPerson)) {
		            // vm.employeeName(vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.employeeInfoLst[0].bussinessName);		
					vm.applicant(vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.employeeInfoLst[0].bussinessName);		
				} else {
					// vm.employeeName(vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.employeeInfoLst[0].bussinessName + inputPerson);
					vm.applicant(vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.employeeInfoLst[0].bussinessName);
					vm.inputPerson(inputPerson);
				}
            	params.application().employeeIDLst(_.map(value.appDispInfoNoDateOutput.employeeInfoLst, (o: any) => o.sid));
            });
        }

        mounted() {
            const vm = this;
        }
    }
}