module nts.uk.at.view.kaf000.a.component9.viewmodel {
	import ActualContentDisplayDto = nts.uk.at.view.kaf000.shr.viewmodel.ActualContentDisplayDto;

    @component({
        name: 'kaf000-b-component9',
        template: `
            <div id="kaf000-b-component9">
				<div class="table" style="padding-top: 20px; padding-bottom: 10px;">
				    <div class="cell" data-bind="i18n: 'KAF000_54'" style="font-weight: bold;"></div>
				</div>
				<div class="panel panel-frame" data-bind="foreach: actualContentDisplayDtoLst" style="overflow: auto; height: 200px; margin-left: 3px; margin-top: 5px;">
					<div style="margin-bottom: 10px;">
						<div data-bind="if: $index">
							<div style="border-bottom: 1px solid #B1B1B1; margin-bottom: 10px;"></div>
						</div>
						<div class="table" style="margin-bottom: 3px;">
			              	<div class="cell column1" data-bind="style: { color: $component.getColor(opAchievementDetail) }, i18n: 'KAF000_55'"></div>
			              	<div class="cell column2" data-bind="style: { color: $component.getColor(opAchievementDetail) }, text: date"></div>
			          	</div>
			          	<div class="table" style="margin-bottom: 3px; margin-top: 3px;">
			              	<div class="cell column1" data-bind="style: { color: $component.getColor(opAchievementDetail) }, i18n: 'KAF000_56'"></div>
			              	<div class="cell column2" data-bind="if: opAchievementDetail">
			                  	<span data-bind="style: { color: $component.getColor(opAchievementDetail) }, text: opAchievementDetail.workTypeCD"></span>
			                  	<span data-bind="style: { color: $component.getColor(opAchievementDetail) }, text: opAchievementDetail.opWorkTypeName"></span>
			              	</div>
			          	</div>
			          	<div class="table" style="margin-bottom: 3px; margin-top: 3px;">
			              	<div class="cell column1" data-bind="style: { color: $component.getColor(opAchievementDetail) }, i18n: 'KAF000_57'"></div>
			              	<div class="cell column2" data-bind="if: opAchievementDetail">
			                  	<span data-bind="style: { color: $component.getColor(opAchievementDetail) }, text: opAchievementDetail.workTimeCD"></span>
			                  	<span data-bind="style: { color: $component.getColor(opAchievementDetail) }, text: opAchievementDetail.opWorkTimeName"></span>
			              	</div>
			          	</div>
			          	<div class="table" style="margin-top: 3px;">
			              	<div class="cell column1" data-bind="style: { color: $component.getColor(opAchievementDetail) }, i18n: 'KAF000_58'"></div>
			              	<div class="cell column2" data-bind="if: opAchievementDetail">
								<span data-bind="if: opAchievementDetail.opWorkTime">
									<span data-bind="style: { color: $component.getColor(opAchievementDetail) }, text: $parent.formatTime(opAchievementDetail.opWorkTime)"></span>
								</span>
								<span data-bind="if: !opAchievementDetail.opWorkTime">
									<span style="visibility: hidden;">null</span>
								</span>
								<span data-bind="if: opAchievementDetail.opWorkTime || opAchievementDetail.opLeaveTime">
									<span data-bind="style: { color: $component.getColor(opAchievementDetail) }"> ~ </span>
								</span>
								<span data-bind="if: opAchievementDetail.opLeaveTime">
									<span data-bind="style: { color: $component.getColor(opAchievementDetail) }, text: $parent.formatTime(opAchievementDetail.opLeaveTime)"></span>
								</span>
								<span data-bind="if: !opAchievementDetail.opLeaveTime">
									<span style="visibility: hidden;">null</span>
								</span>
							</div>
			           	</div>
					</div>
				</div>
			</div>
        `
    })
    class Kaf000BComponent9ViewModel extends ko.ViewModel {
		appType: KnockoutObservable<number> = null;
        appDispInfoStartupOutput: any;
		actualContentDisplayDtoLst: KnockoutObservableArray<ActualContentDisplayDto> = ko.observableArray([]);
        created(params: any) {
            const vm = this;
			vm.appType = params.appType;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;

			vm.actualContentDisplayDtoLst(vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst);
			vm.actualContentDisplayDtoLst.valueHasMutated();

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