module nts.uk.at.view.kafsample.b.viewmodel {
    import Application = nts.uk.at.view.kaf000.shr.viewmodel.Application;
    import PrintContentOfEachAppDto = nts.uk.at.view.kaf000.shr.viewmodel.PrintContentOfEachAppDto;
    import AppType = nts.uk.at.view.kaf000.shr.viewmodel.model.AppType;

    @component({
        name: 'kafsample-b',
        template: `
					<div>
						<div data-bind="component: { name: 'kaf000-b-component1', 
													params: {
														appType: appType,
														appDispInfoStartupOutput: appDispInfoStartupOutput	
													} }"></div>
						<div data-bind="component: { name: 'kaf000-b-component2', 
													params: {
														appType: appType,
														appDispInfoStartupOutput: appDispInfoStartupOutput
													} }"></div>
						<div data-bind="component: { name: 'kaf000-b-component3', 
													params: {
														appType: appType,
														approvalReason: approvalReason,
														appDispInfoStartupOutput: appDispInfoStartupOutput
													} }"></div>
						<div class="table">
							<div class="cell" style="width: 825px;" data-bind="component: { name: 'kaf000-b-component4',
												params: {
													appType: appType,
													application: application,
													appDispInfoStartupOutput: appDispInfoStartupOutput
												} }"></div>
					       	<div class="cell" style="position: absolute;" data-bind="component: { name: 'kaf000-b-component9',
												params: {
													appType: appType,
													application: application,
													appDispInfoStartupOutput: $vm.appDispInfoStartupOutput
												} }"></div>
						</div>
						<div data-bind="component: { name: 'kaf000-b-component5', 
													params: {
														appType: appType,
														application: application,
														appDispInfoStartupOutput: appDispInfoStartupOutput
													} }"></div>
						<div data-bind="component: { name: 'kaf000-b-component6', 
													params: {
														appType: appType,
														application: application,
														appDispInfoStartupOutput: appDispInfoStartupOutput
													} }"></div>
						<div data-bind="component: { name: 'kafsample-share', 
								params: {} }"></div>
						<div data-bind="component: { name: 'kaf000-b-component7', 
													params: {
														appType: appType,
														application: application,
														appDispInfoStartupOutput: appDispInfoStartupOutput
													} }"></div>
						<div data-bind="component: { name: 'kaf000-b-component8', 
													params: {
														appType: appType,
														appDispInfoStartupOutput: appDispInfoStartupOutput
													} }"></div>
					</div>
				`
    })
    class KafSampleBViewModel extends ko.ViewModel {

        appType: KnockoutObservable<number>;
        appDispInfoStartupOutput: any;
        application: KnockoutObservable<Application>;
        approvalReason: KnockoutObservable<string>;
        printContentOfEachAppDto: KnockoutObservable<PrintContentOfEachAppDto>;
		time: KnockoutObservable<number> = ko.observable(1);

        created(
            params: {
                appType: any,
                application: any,
                printContentOfEachAppDto: PrintContentOfEachAppDto,
                approvalReason: any,
                appDispInfoStartupOutput: any,
                eventUpdate: (evt: () => void) => void,
                eventReload: (evt: () => void) => void
            }
        ) {
            const vm = this;
			vm.appType = params.appType;
			vm.application = params.application;
            vm.printContentOfEachAppDto = ko.observable(params.printContentOfEachAppDto);
            vm.approvalReason = params.approvalReason;
			vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            // gui event con ra viewmodel cha
            // nhớ dùng bind(vm) để ngữ cảnh lúc thực thi
            // luôn là component
            params.eventUpdate(vm.update.bind(vm));
            params.eventReload(vm.reload.bind(vm));
			vm.initAppDetail();
        }

        initAppDetail() {
            let vm = this;
            vm.$blockui('show');
            let command = {};
            return vm.$ajax(API.initAppDetail, command)
            .done(res => {
                if (res) {
                    vm.printContentOfEachAppDto().opPrintContentOfWorkChange = res;
                }
            }).fail(err => {
	
            }).always(() => vm.$blockui('hide'));
        }

        reload() {
            const vm = this;
            if (vm.appType() == vm.application().appType) {
            	vm.initAppDetail();
            }
        }

        // event update cần gọi lại ở button của view cha
        update() {
            const vm = this;
			let command = {};
            vm.$blockui("show");
            let dfd = $.Deferred();
			// validate chung KAF000
			 vm.$validate('#kaf000-a-component4 .nts-input', '#kaf000-a-component3-prePost', '#kaf000-a-component5-comboReason')
            .then((isValid) => {
                if (isValid) {
					// validate riêng cho màn hình
                    return true;
                }
            }).then((result) => {
				// check trước khi update
                if (result) {
					return vm.$ajax(API.checkBeforeUpdateSample, ["Msg_197"]);
                }
            }).then((result) => {
                if (result) {
					// xử lý confirmMsg
                	return vm.handleConfirmMessage(result);
                }
            }).then((result) => {
                if (result) {
					// update
                	return vm.$ajax('at', API.updateSample, ["Msg_15"]).then(() => {
						return vm.$dialog.info({ messageId: "Msg_15"}).then(() => {
							return true;
						});	
					});
                }
            }).then((result) => {
                if(result) {
					// gửi mail sau khi update
					// return vm.$ajax('at', API.sendMailAfterUpdateSample);
					return true;
				}	
            }).then((result) => {
                if(result) {
					return dfd.resolve(true);
				}	
				return dfd.resolve(result);
            }).fail((failData) => {
				// xử lý lỗi nghiệp vụ riêng
				vm.handleErrorCustom(failData).then((result: any) => {
					if(result) {
						return dfd.reject(failData);	
					}	
					return dfd.reject(false);
				});
			});
			return dfd.promise();
        }

		handleErrorCustom(failData: any): any {
			const vm = this;
			if(failData.messageId == "Msg_26") {
				return vm.$dialog.error({ messageId: failData.messageId, messageParams: failData.parameterIds })
				.then(() => {
					return $.Deferred().resolve(false);	
				});	
			}
			return $.Deferred().resolve(true);
		}

		handleConfirmMessage(listMes: any): any {
			const vm = this;
			if(_.isEmpty(listMes)) {
				return $.Deferred().resolve(true);
			}
			let msg = listMes[0];

			return vm.$dialog.confirm({ messageId: msg.msgID, messageParams: msg.paramLst })
			.then((value) => {
				if (value === 'yes') {
					return vm.handleConfirmMessage(_.drop(listMes));
				} else {
					return $.Deferred().resolve(false);
				}
			});
		}
    }

    const API = {
        initAppDetail: "at/request/application/initApp",
        checkBeforeUpdateSample: "at/request/application/checkBeforeSample",
        updateSample: "at/request/application/changeDataSample",
		sendMailAfterUpdateSample: ""
    }
}