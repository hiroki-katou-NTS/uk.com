module nts.uk.at.view.kaf000.a.component8.viewmodel {
	import ActualContentDisplayDto = nts.uk.at.view.kaf000.shr.viewmodel.ActualContentDisplayDto;

    @component({
        name: 'kaf000-a-component8',
        template: `
            <div id="kaf000-a-component8" class="right-panel-block">
				<div class="header">予定／実績内容</div>
				<div class="content">
					<div data-bind="foreach: actualContentDisplayDtoLst"">
						<div class="ui-iggrid">
							<div data-bind="if: $index">
								<div style="margin-bottom: 10px;"></div>
							</div>
							<table class="left-aligned">
								<tbody>
									<tr>
										<td><span data-bind="style: { color: $component.getColor(opAchievementDetail) }, i18n: 'KAF000_55'"></span></td>
										<td><span data-bind="style: { color: $component.getColor(opAchievementDetail) }, text: date"></span></td>
									</tr>
									<tr>
										<td><span data-bind="style: { color: $component.getColor(opAchievementDetail) }, i18n: 'KAF000_56'"></span></td>
										<td>
											<div data-bind="if: opAchievementDetail">
												<span data-bind="style: { color: $component.getColor(opAchievementDetail) }, text: opAchievementDetail.workTypeCD"></span>
												<span data-bind="style: { color: $component.getColor(opAchievementDetail) }, text: opAchievementDetail.opWorkTypeName"></span>
											</div>
										</td>
									</tr>
									<tr>
										<td><span data-bind="style: { color: $component.getColor(opAchievementDetail) }, i18n: 'KAF000_57'"></span></td>
										<td>
											<div data-bind="if: opAchievementDetail">
												<span data-bind="style: { color: $component.getColor(opAchievementDetail) }, text: opAchievementDetail.workTimeCD"></span>
												<span data-bind="style: { color: $component.getColor(opAchievementDetail) }, text: opAchievementDetail.opWorkTimeName"></span>
											</div>
										</td>
									</tr>
									<tr>
										<td><span data-bind="style: { color: $component.getColor(opAchievementDetail) }, i18n: 'KAF000_58'"></span></td>
										<td>
											<div data-bind="if: opAchievementDetail">
												<span data-bind="if: opAchievementDetail.opWorkTime">
													<span data-bind="style: { color: $component.getColor(opAchievementDetail) }, text: $parent.formatTime(opAchievementDetail.opWorkTime)"></span>
												</span>
												<span data-bind="if: !opAchievementDetail.opWorkTime">
													<span style="visibility: hidden;">null</span>
												</span>
												<span data-bind="if: opAchievementDetail.opWorkTime || opAchievementDetail.opLeaveTime">
													<span data-bind="style: { color: $component.getColor(opAchievementDetail) }"> ～ </span>
												</span>
												<span data-bind="if: opAchievementDetail.opLeaveTime">
													<span data-bind="style: { color: $component.getColor(opAchievementDetail) }, text: $parent.formatTime(opAchievementDetail.opLeaveTime)"></span>
												</span>
												<span data-bind="if: !opAchievementDetail.opLeaveTime">
													<span style="visibility: hidden;">null</span>
												</span>
											</div>
										</td>
									</tr>
								</tbody>
							</table>
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
		
		getColor(opAchievementDetail: any) {
			return opAchievementDetail && opAchievementDetail.trackRecordAtr == 1 ? 'green' : '';
		}
    }
}