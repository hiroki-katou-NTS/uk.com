module nts.uk.at.view.kmr002.a.model {
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import error = nts.uk.ui.dialog.error;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import textUK = nts.uk.text;
    import block = nts.uk.ui.block;
    import service = nts.uk.at.view.kmr002.a.service;
    export class ScreenModel {
		currentFrame: KnockoutObservable<number> = ko.observable(0);
		bentoFrame1List: KnockoutObservableArray<Bento> = ko.observableArray([
			new Bento(1, "bento1", 1000, 0, "数量")	
		]);
		countAllFrame1: KnockoutObservable<number> = ko.pureComputed(() => {
			if(_.isEmpty(this.bentoFrame1List())) {
				return 0;	
			}
			let countAll = _.chain(this.bentoFrame1List()).map((item: Bento) => _.toNumber(item.bentoCount())).sum().value();
			return countAll;
		});
		unitAllFrame1: KnockoutObservable<string> = ko.pureComputed(() => {
			if(_.isEmpty(this.bentoFrame1List())) {
				return '';	
			}
			let listUnit = _.chain(this.bentoFrame1List()).map((item: Bento) => item.unit()).uniq().value();
			if(_.size(listUnit) > 1) {
				return '';
			}
			return getText('KMR002_28') + listUnit[0] + getText('KMR002_29');
		});
		priceAllFrame1: KnockoutObservable<number> = ko.pureComputed(() => {
			if(_.isEmpty(this.bentoFrame1List())) {
				return 0;	
			}
			let countAll = _.chain(this.bentoFrame1List()).map((item: Bento) => item.sum()).sum().value();
			return countAll;
		});
		bentoFrame2List: KnockoutObservableArray<Bento> = ko.observableArray([
			new Bento(2, "bento2", 2000, 0, "数量")
		]);
		countAllFrame2: KnockoutObservable<number> = ko.pureComputed(() => {
			if(_.isEmpty(this.bentoFrame2List())) {
				return 0;	
			}
			let countAll = _.chain(this.bentoFrame2List()).map((item: Bento) => _.toNumber(item.bentoCount())).sum().value();
			return countAll;
		});
		unitAllFrame2: KnockoutObservable<string> = ko.pureComputed(() => {
			if(_.isEmpty(this.bentoFrame2List())) {
				return '';	
			}
			let listUnit = _.chain(this.bentoFrame2List()).map((item: Bento) => item.unit()).uniq().value();
			if(_.size(listUnit) > 1) {
				return '';
			}
			return getText('KMR002_28') + listUnit[0] + getText('KMR002_29');
		});
		priceAllFrame2: KnockoutObservable<number> = ko.pureComputed(() => {
			if(_.isEmpty(this.bentoFrame2List())) {
				return 0;	
			}
			let countAll = _.chain(this.bentoFrame2List()).map((item: Bento) => item.sum()).sum().value();
			return countAll;
		});

        constructor() {
            let self = this;
        }

        public startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred<any>();
            dfd.resolve();
            return dfd.promise();
        }

		register() {
			
		}
    }

	export class Bento {
		frameNo: KnockoutObservable<number>;
		name: KnockoutObservable<string>;
		amount1: KnockoutObservable<number>;
		bentoCount: KnockoutObservable<number>;
		unit: KnockoutObservable<string>;
		sum: KnockoutObservable<number>;
		constructor(frameNo: number, name: string, amount1: number, bentoCount: number, unit: string) {
			this.frameNo = ko.observable(frameNo);
			this.name = ko.observable(name);
			this.amount1 = ko.observable(amount1);
			this.bentoCount = ko.observable(bentoCount);
			this.unit = ko.observable(unit);
			this.sum = ko.pureComputed(() => {
				return this.amount1() * _.toNumber(this.bentoCount());
			});
		}
		
		increaseCount() {
			let bentoCount = _.toNumber(this.bentoCount());
			if(bentoCount >= 99) {
				this.bentoCount(99);	
			} else {
				this.bentoCount(bentoCount + 1);	
			}
		}
		
		decreaseCount() {
			let bentoCount = _.toNumber(this.bentoCount());
			if(bentoCount <= 0) {
				this.bentoCount(0);
			} else {
				this.bentoCount(bentoCount - 1);	
			}
		}
		
		resetCount() {
			this.bentoCount(0);
		}
	}
}

