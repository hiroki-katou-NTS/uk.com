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
		modeFuture: KnockoutObservable<boolean> = ko.observable(true);
		date: KnockoutObservable<any> = ko.observable(moment(new Date()).format("YYYY/MM/DD"));
		frameOption: KnockoutObservableArray<any> = ko.observableArray([]);
		currentFrameNo: KnockoutObservable<number> = ko.observable(null);
		timeLabel: KnockoutObservable<string> = ko.pureComputed(() => {
			if(this.currentFrameNo()) {
				let currentFrame = _.find(this.frameOption(), (o: any) => o.frameNo == this.currentFrameNo());
				if(currentFrame) {
					let startTime = '',
						endTime = '';
					if(currentFrame.receptionHours.startTime) {
						startTime = nts.uk.time.format.byId("Clock_Short_HM", currentFrame.receptionHours.startTime);
					}
					if(currentFrame.receptionHours.endTime) {
						endTime = nts.uk.time.format.byId("Clock_Short_HM", currentFrame.receptionHours.endTime);
					}
					return startTime + getText('KMR002_19') + endTime;
				}
			}
			return '';
		});
		canOrder: KnockoutObservable<boolean> = ko.pureComputed(() => {
			let orderFrame1 = _.find(this.listOrder, o => o.closingTimeFrame==1);
			if(orderFrame1) {
				if(orderFrame1.ordered == false && this.currentFrameNo()==1 && this.bentoMenuByClosingTimeDto.reservationTime1) {
					return true;
				}				
			} else {
				if(this.currentFrameNo()==1 && this.bentoMenuByClosingTimeDto.reservationTime1) {
					return true;
				}
			}
			let orderFrame2 = _.find(this.listOrder, o => o.closingTimeFrame==2);
			if(orderFrame2) {
				if(orderFrame2.ordered == false && this.currentFrameNo()==2 && this.bentoMenuByClosingTimeDto.reservationTime2) {
					return true;
				}				
			} else {
				if(this.currentFrameNo()==2 && this.bentoMenuByClosingTimeDto.reservationTime2) {
					return true;
				}
			}
			return false;
		});
		bentoFrame1List: KnockoutObservableArray<Bento> = ko.observableArray([]);
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
			let countAll = _.chain(this.bentoFrame1List()).map((item: Bento) => item.sumFunction()).sum().value();
			return countAll;
		});
		bentoFrame2List: KnockoutObservableArray<Bento> = ko.observableArray([]);
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
			let countAll = _.chain(this.bentoFrame2List()).map((item: Bento) => item.sumFunction()).sum().value();
			return countAll;
		});
		bentoMenuByClosingTimeDto: any = null;
		listOrder: Array<any> = [];

        public startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred<any>();
			self.date.subscribe((value) => {
				nts.uk.ui.errors.clearAll();
				$('#dateSelect').ntsError('check');
				if(nts.uk.ui.errors.hasError()) {
					return;
				}
				if(moment(value).isBefore(moment(new Date()).format("YYYY/MM/DD"))) {
					self.modeFuture(false);
				} else {
					self.modeFuture(true);
				}
				if(!self.modeFuture()) {
					error({ messageId: 'Msg_2283' });	
				}
				self.getData();
			});
			dfd.resolve();
            return dfd.promise();
        }

		getData(): JQueryPromise<any> {
			let self = this,
                dfd = $.Deferred<any>(),
				date = moment(self.date()).format("YYYY/MM/DD"),
				param = { date }; 
			nts.uk.ui.block.invisible();
			service.startScreen(param).done((data: any) => {
				self.bentoMenuByClosingTimeDto = data.bentoMenuByClosingTimeDto;
				self.frameOption(data.bentoMenuByClosingTimeDto.reservationRecTimeZoneLst);
				if(!_.isEmpty(self.frameOption())) {
					self.currentFrameNo(_.head(self.frameOption()).frameNo);
				}
				let menu1: Array<Bento> = [];
				_.forEach(data.bentoMenuByClosingTimeDto.menu1, (item: any) => {
					let bentoCount = 0;
					let frame1Item = _.find(data.listOrder, (o: any) => o.closingTimeFrame==1);
					if(frame1Item) {
						let reservationItem = _.find(frame1Item.listBentoReservationDetail, (o: any) => o.frameNo==item.frameNo);
						if(reservationItem) {
							bentoCount = reservationItem.bentoCount;
						}
					}
					menu1.push(new Bento(item.frameNo, item.name, item.amount1, bentoCount, item.unit));
				});
				self.bentoFrame1List(menu1);
				let menu2: Array<Bento> = [];
				_.forEach(data.bentoMenuByClosingTimeDto.menu2, (item: any) => {
					let bentoCount = 0;
					let frame2Item = _.find(data.listOrder, (o: any) => o.closingTimeFrame==2);
					if(frame2Item) {
						let reservationItem = _.find(frame2Item.listBentoReservationDetail, (o: any) => o.frameNo==item.frameNo);
						if(reservationItem) {
							bentoCount = reservationItem.bentoCount;
						}
					}
					menu2.push(new Bento(item.frameNo, item.name, item.amount1, bentoCount, item.unit));
				});
				self.bentoFrame2List(menu2);
				if(_.isEmpty(data.listOrder)) {
					self.listOrder = [];
				} else {
					self.listOrder = data.listOrder;
				}
				dfd.resolve();
			}).fail((res: any) => {
				error({ messageId: res.messageId });
				dfd.reject();
			}).always(() => {
				nts.uk.ui.block.clear();	
			});
            return dfd.promise();	
		}

		register() {
			let self = this,
				date = moment(self.date()).format("YYYY/MM/DD"),
				details: Array<any> = [],
				positiveCountLst1 = _.filter(self.bentoFrame1List(), (o) => o.bentoCount()>0),
				positiveCountLst2 = _.filter(self.bentoFrame2List(), (o) => o.bentoCount()>0);
			if(_.isEmpty(_.concat(positiveCountLst1, positiveCountLst2))) {
				return error({ messageId: 'Msg_1605' });
			}
			_.forEach(positiveCountLst1, (item) => {
				details.push({
					closingTimeFrame: 1,
					frameNo: item.frameNo(),
					bentoCount: item.bentoCount()
				})		
			});
			_.forEach(positiveCountLst2, (item) => {
				details.push({
					closingTimeFrame: 2,
					frameNo: item.frameNo(),
					bentoCount: item.bentoCount()
				})		
			});
			let command = { date, details };
			if(_.isEmpty(self.listOrder)) {
				nts.uk.ui.block.invisible();
				service.register(command).done(() => {
					info({ messageId: "Msg_15" }).then(() => {
						self.getData();
					});	
				}).fail((res: any) => {
					error({ messageId: res.messageId });
				}).always(() => {
					nts.uk.ui.block.clear();	
				});
			} else {
				nts.uk.ui.block.invisible();
				service.update(command).done(() => {
					info({ messageId: "Msg_15" }).then(() => {
						self.getData();	
					});	
				}).fail((res: any) => {
					error({ messageId: res.messageId });
				}).always(() => {
					nts.uk.ui.block.clear();	
				});
			}	
		}
		
		print(): void {
            nts.uk.request.jump("at", "/view/kmr/005/a/index.xhtml");
        }
    }

	export class Bento {
		frameNo: KnockoutObservable<number>;
		name: KnockoutObservable<string>;
		amount1: KnockoutObservable<number>;
		bentoCount: KnockoutObservable<number>;
		unit: KnockoutObservable<string>;
		constructor(frameNo: number, name: string, amount1: number, bentoCount: number, unit: string) {
			this.frameNo = ko.observable(frameNo);
			this.name = ko.observable(name);
			this.amount1 = ko.observable(amount1);
			this.bentoCount = ko.observable(bentoCount);
			this.unit = ko.observable(unit);
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
		
		sum() {
			return ko.pureComputed(() => {
				return this.sumFunction();
			});
		}
		
		sumFunction() {
			return this.amount1() * _.toNumber(this.bentoCount());	
		}
		
		enableResetCount() {
			return ko.pureComputed(() => {
				return this.bentoCount() > 0;	
			});
		}
	}
}

