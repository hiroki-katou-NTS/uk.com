module nts.uk.at.view.kaf000.a.component8.viewmodel {
	import ActualContentDisplayDto = nts.uk.at.view.kaf000.shr.viewmodel.ActualContentDisplayDto;

    @component({
        name: 'kaf000-a-component8',
        template: `
            <div id="kaf000-a-component8" style="width: 300px;">
                <div class="panel panel-frame panel-gray-bg">
                    <div class="table">
                        <div class="cell" data-bind="i18n: 'KAF000_54'"></div>
                    </div>
                    <div data-bind="foreach: actualContentDisplayDtoLst" style="overflow: auto; height: 200px; margin-left: 3px; margin-top: 5px;">
                        <div style="margin-top: 3px; margin-bottom: 3px;">
                            <div class="table" style="margin-bottom: 1px;">
                                <div class="cell" data-bind="i18n: 'KAF000_55'"></div>
                                <div class="cell" data-bind="text: date"></div>
                            </div>
                            <div class="table" style="margin-bottom: 1px; margin-top: 1px;">
                                <div class="cell" data-bind="i18n: 'KAF000_56'"></div>
                                <div class="cell" data-bind="if: opAchievementDetail">
                                    <span data-bind="text: opAchievementDetail.workTypeCD"></span>
                                    <span data-bind="text: opAchievementDetail.opWorkTypeName"></span>
                                </div>
                            </div>
                            <div class="table" style="margin-bottom: 1px; margin-top: 1px;">
                                <div class="cell" data-bind="i18n: 'KAF000_57'"></div>
                                <div class="cell" data-bind="if: opAchievementDetail">
                                    <span data-bind="text: opAchievementDetail.workTimeCD"></span>
                                    <span data-bind="text: opAchievementDetail.opWorkTimeName"></span>
                                </div>
                            </div>
                            <div class="table" style="margin-top: 1px;">
                                <div class="cell" data-bind="i18n: 'KAF000_58'"></div>
                                <div class="cell" data-bind="if: opAchievementDetail">
									<span data-bind="if: opAchievementDetail.opWorkTime">
										<span data-bind="text: $parent.formatTime(opAchievementDetail.opWorkTime)"></span>
									</span>
									<span data-bind="if: !opAchievementDetail.opWorkTime">
										<span style="visibility: hidden;">null</span>
									</span>
									<span> ~ </span>
									<span data-bind="if: opAchievementDetail.opLeaveTime">
										<span data-bind="text: $parent.formatTime(opAchievementDetail.opLeaveTime)"></span>
									</span>
									<span data-bind="if: !opAchievementDetail.opLeaveTime">
										<span style="visibility: hidden;">null</span>
									</span>
								</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        `
    })
    class Kaf000AComponent8ViewModel extends ko.ViewModel {
		appType: KnockoutObservable<number> = null;
        appDispInfoStartupOutput: any;
		actualContentDisplayDtoLst: KnockoutObservableArray<ActualContentDisplayDto> = ko.observableArray([]);
        created(params: any) {
            const vm = this;
			vm.appType = params.appType;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;

            vm.appDispInfoStartupOutput.subscribe(value => {
				vm.actualContentDisplayDtoLst(value.appDispInfoWithDateOutput.opActualContentDisplayLst);
				vm.actualContentDisplayDtoLst.valueHasMutated();
            });
        }

        mounted() {
            const vm = this;
        }

		formatTime(value: any) {
			let s = nts.uk.time.format.byId(`ClockDay_Short_HM`, value);
			return s.replace(/当日/g,'');
		}
    }
}