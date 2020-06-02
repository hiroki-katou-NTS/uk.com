module nts.uk.at.view.kdp.share {
    const DATE_FORMAT = 'YYYY年 M月 D日 (ddd)';
    const TIME_FORMAT = 'HH:mm';
    export class StampClock {
        time: KnockoutObservable<Date>;
        displayDate: KnockoutObservable<String> = ko.observable('');
        displayTime: KnockoutObservable<String> = ko.observable('');
        stampSetting: KnockoutObservable<any>;
        constructor(params) {
            console.log(params.setting());
            let self = this;
            self.stampSetting = ko.observable(params.setting());

            moment.locale('ja');
            nts.uk.request.syncAjax("com", "server/time/now/").done((res) => {
                self.displayTime(moment.utc(res).format(TIME_FORMAT));
                self.displayDate(moment.utc(res).format(DATE_FORMAT));
            });
            
            setInterval(() => {
                nts.uk.request.syncAjax("com", "server/time/now/").done((res) => {
                    self.displayTime(moment.utc(res).format(TIME_FORMAT));
                });
            }, 2000);

            self.addCorrectionInterval(self.stampSetting().correctionInterval);

        }

        public addCorrectionInterval(minute: number) {
            let self = this;
            setInterval(() => {
                nts.uk.request.syncAjax("com", "server/time/now/").done((res) => {
                    self.displayDate(moment.utc(res).format(DATE_FORMAT));
                });
            }, minute * 60000);
        }
    }
}

ko.components.register('stamp-clock', {
    viewModel: nts.uk.at.view.kdp.share.StampClock, template: `
    <div id="stamp-header">
        <div class="panel" id="stamp-date"
            data-bind="style: {'background-color' : stampSetting().backGroundColor, 'color': stampSetting().textColor }">
        <span id="stamp-date-text" data-bind="text: displayDate"></span>
        </div>
            <div class="panel" id="stamp-time"
            data-bind="style: {'background-color' : stampSetting().backGroundColor, 'color': stampSetting().textColor }">
            <span id="stamp-time-text" data-bind="text: displayTime"></span>
        </div>
    </div>
`});