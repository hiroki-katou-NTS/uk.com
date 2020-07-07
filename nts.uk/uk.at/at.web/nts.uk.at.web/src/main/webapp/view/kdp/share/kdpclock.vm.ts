module nts.uk.at.view.kdp.share {
	const DATE_FORMAT = 'YYYY年 M月 D日 (ddd)';
	const TIME_FORMAT = 'HH:mm';

	@component({
		name: 'stamp-clock',
		template: `
        <div id="stamp-header">
            <div class="panel" id="stamp-date"
                data-bind="style: {'background-color' : stampSetting().backGroundColor, 'color': stampSetting().textColor }">
            <span id="stamp-date-text" data-bind="text: displayDate()"></span>
            </div>
                <div class="panel" id="stamp-time"
                data-bind="style: {'background-color' : stampSetting().backGroundColor, 'color': stampSetting().textColor }">
				<span id="stamp-time-text" data-bind="text: displayTime()"></span>
				<button class="btn-setting" type="button" tabindex="16"></button>
                <button class="proceed btnA4">打刻履歴</button>

            </div>
        </div>
    `})
	export class StampClock extends ko.ViewModel {
		systemDate: KnockoutObservable<any> = ko.observable(moment.utc());
		stampSetting: KnockoutObservable<any> ;
		countTime: number = 20;
		interval: any;
		constructor(params) {
			let self = this;
			let vm= new ko.ViewModel();
			self.stampSetting = params.setting;
			moment.locale('ja');
			self.systemDate(moment(vm.$date.now()));
			self.addCorrectionInterval();

			self.stampSetting.subscribe((data) =>  {
				self.addCorrectionInterval(self.stampSetting().correctionInterval);
			});
		}

		displayTime() {
			let self = this;
			return self.systemDate().format(TIME_FORMAT);
		}

		displayDate() {
			let self = this;
			return self.systemDate().format(DATE_FORMAT);
		}

		public addCorrectionInterval() {
			let self = this;
			let vm= new ko.ViewModel();
			clearInterval(self.interval);

			self.interval = setInterval(() => {
				if (self.stampSetting().correctionInterval === self.countTime) {
					self.systemDate(moment(vm.$date.now()));
						self.countTime = 0;
				} else {
					self.systemDate(self.systemDate().add(1, 'seconds'));
					self.countTime = self.countTime + 1;
				}

			}, 1000);
		}
	}
}