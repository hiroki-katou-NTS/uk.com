module nts.uk.at.view.kdp010.i {
	export module viewmodel {
		export class ScreenModel {
			commentDaily: KnockoutObservable<string> = ko.observable("");
			letterColors: KnockoutObservable<string> = ko.observable("#000000");
			btn1: KnockoutObservable<any> = ko.observable(new ButtonSetting('出勤','#0033cc','#ccccff'));
            btn2: KnockoutObservable<any> = ko.observable(new ButtonSetting('退勤','#0033cc','#ccccff'));
            btn3: KnockoutObservable<any> = ko.observable(new ButtonSetting('外出','#0033cc','#05f9f9'));
            btn4: KnockoutObservable<any> = ko.observable(new ButtonSetting(undefined, undefined, undefined));
            btn5: KnockoutObservable<any> = ko.observable(new ButtonSetting(undefined, undefined, undefined));
            btn6: KnockoutObservable<any> = ko.observable(new ButtonSetting(undefined, undefined, undefined));
			constructor() {
				let self = this;
			}
			public startPage(): JQueryPromise<any> {
				let self = this;
				let dfd = $.Deferred();
				dfd.resolve();
				return dfd.promise();
			}
			public registration() {
				let self = this;
			}
			public deleteSetting() {
				let self = this;
			}
			public closeDialog(): void {
				nts.uk.ui.windows.close();
			}
		}

		export class ButtonSetting {
			text: KnockoutObservable<string>;
            textColor: KnockoutObservable<string>;
			bgColor: KnockoutObservable<string>;
			constructor(text: string, textColor: string, bgColor: string) {
				this.text = ko.observable(text || '');
                this.textColor = ko.observable(textColor || '#999');
				this.bgColor = ko.observable(bgColor|| '#999');
			}
            setting (): void {
                let self = this;
            }
		}
    }
}