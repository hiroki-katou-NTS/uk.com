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
		dateBefore: any = null;
		frameOption: KnockoutObservableArray<any> = ko.observableArray([]);
		currentFrameNo: KnockoutObservable<number> = ko.observable(null);
		listOrder: any[] = [];
		timeLabel: KnockoutObservable<string> = ko.pureComputed(() => {
			let self = this;
			if(self.currentFrameNo()) {
				let currentFrame = _.find(self.frameOption(), (o: any) => o.frameNo == self.currentFrameNo());
				if(currentFrame) {
					let startTime = '',
						endTime = '';
					if(!_.isNil(currentFrame.receptionHours.startTime)) {
						startTime = nts.uk.time.format.byId("Clock_Short_HM", currentFrame.receptionHours.startTime);
					}
					if(!_.isNil(currentFrame.receptionHours.endTime)) {
						endTime = nts.uk.time.format.byId("Clock_Short_HM", currentFrame.receptionHours.endTime);
					}
					return startTime + getText('KMR002_19') + endTime;
				}
			}
			return '';
		});
		canOrder: KnockoutObservable<boolean> = ko.observable(true);
		bentoFrame1List: KnockoutObservableArray<Bento> = ko.observableArray([]);
		countAllFrame1: KnockoutObservable<number> = ko.pureComputed(() => {
			let self = this;
			if(_.isEmpty(self.bentoFrame1List())) {
				return 0;	
			}
			let countAll = _.chain(self.bentoFrame1List()).map((item: Bento) => _.toNumber(item.bentoCount())).sum().value();
			return countAll;
		});
		unitAllFrame1: KnockoutObservable<string> = ko.pureComputed(() => {
			let self = this;
			if(_.isEmpty(self.bentoFrame1List())) {
				return '';	
			}
			let listUnit = _.chain(self.bentoFrame1List()).map((item: Bento) => item.unit()).uniq().value();
			if(_.size(listUnit) > 1) {
				return '';
			}
			return getText('KMR002_28') + listUnit[0] + getText('KMR002_29');
		});
		priceAllFrame1: KnockoutObservable<number> = ko.pureComputed(() => {
			let self = this;
			if(_.isEmpty(self.bentoFrame1List())) {
				return 0;	
			}
			let countAll = _.chain(self.bentoFrame1List()).map((item: Bento) => item.sumFunction()).sum().value();
			return countAll;
		});
		bentoFrame2List: KnockoutObservableArray<Bento> = ko.observableArray([]);
		countAllFrame2: KnockoutObservable<number> = ko.pureComputed(() => {
			let self = this;
			if(_.isEmpty(self.bentoFrame2List())) {
				return 0;	
			}
			let countAll = _.chain(self.bentoFrame2List()).map((item: Bento) => _.toNumber(item.bentoCount())).sum().value();
			return countAll;
		});
		unitAllFrame2: KnockoutObservable<string> = ko.pureComputed(() => {
			let self = this;
			if(_.isEmpty(self.bentoFrame2List())) {
				return '';	
			}
			let listUnit = _.chain(self.bentoFrame2List()).map((item: Bento) => item.unit()).uniq().value();
			if(_.size(listUnit) > 1) {
				return '';
			}
			return getText('KMR002_28') + listUnit[0] + getText('KMR002_29');
		});
		priceAllFrame2: KnockoutObservable<number> = ko.pureComputed(() => {
			let self = this;
			if(_.isEmpty(self.bentoFrame2List())) {
				return 0;	
			}
			let countAll = _.chain(self.bentoFrame2List()).map((item: Bento) => item.sumFunction()).sum().value();
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

				let self = this,
					closingTimeFrameNo = self.currentFrameNo(),
					date = moment(self.dateBefore).format("YYYY/MM/DD"),
					details: Array<any> = [],
					positiveCountLst1 = _.filter(self.bentoFrame1List(), (o) => o.bentoCount()>0),
					positiveCountLst2 = _.filter(self.bentoFrame2List(), (o) => o.bentoCount()>0);

					if (self.dateBefore != null && self.checkChangeOrder(self.currentFrameNo() == 1 ? positiveCountLst1 : positiveCountLst2, self.currentFrameNo()) && 
						((self.currentFrameNo() == 1 && positiveCountLst1.length > 0) || (self.currentFrameNo() == 2 && positiveCountLst2.length > 0))) {
						confirm("Msg_3326").ifYes(() => {
							if(_.isEmpty(self.listOrder) && _.isEmpty(_.concat(positiveCountLst1, positiveCountLst2))) {
								return error({ messageId: 'Msg_1605' });
							}
							if (self.currentFrameNo() === 1) {
								_.forEach(positiveCountLst1, (item) => {
									details.push({
										closingTimeFrame: 1,
										frameNo: item.frameNo(),
										bentoCount: item.bentoCount()
									})		
								});
							}
							if (self.currentFrameNo() === 2) {
								_.forEach(positiveCountLst2, (item) => {
									details.push({
										closingTimeFrame: 2,
										frameNo: item.frameNo(),
										bentoCount: item.bentoCount()
									})		
								});
							}
							let command = { date, closingTimeFrameNo, details };
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
									info({ messageId: "Msg_15" });	
								}).fail((res: any) => {
									error({ messageId: res.messageId });
								}).always(() => {
									nts.uk.ui.block.clear();	
								});
							}
						}).then(() => {
							self.dateBefore = self.date()
							self.getData().done(() => {
								if(self.currentFrameNo()==1) {
									let reservationFrame1 = _.find(self.listOrder, o => o.closingTimeFrame==1);
									if(reservationFrame1) {
										if(reservationFrame1.ordered) {
											error({ messageId: 'Msg_2284' });	
										}
									}
								}
								if(self.currentFrameNo()==2) {
									let reservationFrame2 = _.find(self.listOrder, o => o.closingTimeFrame==2);
									if(reservationFrame2) {
										if(reservationFrame2.ordered) {
											error({ messageId: 'Msg_2284' });	
										}
									}
								}
							}).then(() => {
								if(moment(value).isBefore(moment(new Date()).format("YYYY/MM/DD"))) {
									self.modeFuture(false);
								} else {
									self.modeFuture(true);
								}
								if(!self.modeFuture()) {
									error({ messageId: 'Msg_2283' });	
								}
							});
						})
					} else {
						self.dateBefore = self.date()
						self.getData().done(() => {
							if(self.currentFrameNo()==1) {
								let reservationFrame1 = _.find(self.listOrder, o => o.closingTimeFrame==1);
								if(reservationFrame1) {
									if(reservationFrame1.ordered) {
										error({ messageId: 'Msg_2284' });	
									}
								}
							}
							if(self.currentFrameNo()==2) {
								let reservationFrame2 = _.find(self.listOrder, o => o.closingTimeFrame==2);
								if(reservationFrame2) {
									if(reservationFrame2.ordered) {
										error({ messageId: 'Msg_2284' });	
									}
								}
							}
						}).then(() => {
							if(moment(value).isBefore(moment(new Date()).format("YYYY/MM/DD"))) {
								self.modeFuture(false);
							} else {
								self.modeFuture(true);
							}
							if(!self.modeFuture()) {
								error({ messageId: 'Msg_2283' });	
							}
						});
					}
				
			});
			
			self.currentFrameNo.subscribe(() => {
				self.canOrder(self.getCanOrder());

				let date = moment(self.dateBefore).format("YYYY/MM/DD"),
				closingTimeFrameNo = self.currentFrameNo() == 1 ? 2 : 1,
				details: Array<any> = [],
				positiveCountLst1 = _.filter(self.bentoFrame1List(), (o) => o.bentoCount()>0),
				positiveCountLst2 = _.filter(self.bentoFrame2List(), (o) => o.bentoCount()>0);

				if (self.checkChangeOrder(self.currentFrameNo() == 1 ? positiveCountLst2 : positiveCountLst1, self.currentFrameNo() == 1 ? 2 : 1) && 
					((self.currentFrameNo() == 2 && positiveCountLst1.length > 0) || (self.currentFrameNo() == 1 && positiveCountLst2.length > 0))) {
					confirm("Msg_3326").ifYes(() => {
						if(_.isEmpty(self.listOrder) && _.isEmpty(_.concat(positiveCountLst1, positiveCountLst2))) {
							return error({ messageId: 'Msg_1605' });
						}
						if (self.currentFrameNo() === 2) {
							_.forEach(positiveCountLst1, (item) => {
								details.push({
									closingTimeFrame: 1,
									frameNo: item.frameNo(),
									bentoCount: item.bentoCount()
								})		
							});
						}
						if (self.currentFrameNo() === 1) {
							_.forEach(positiveCountLst2, (item) => {
								details.push({
									closingTimeFrame: 2,
									frameNo: item.frameNo(),
									bentoCount: item.bentoCount()
								})		
							});
						}
						let command = { date, closingTimeFrameNo, details };
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
								info({ messageId: "Msg_15" });	
							}).fail((res: any) => {
								error({ messageId: res.messageId });
							}).always(() => {
								nts.uk.ui.block.clear();	
							});
						}
					})
				}
			});
			dfd.resolve();
            return dfd.promise();
        }

		checkChangeOrder(countLst: any[], frameNo: number): boolean {
			let self = this;
			let result = false;

			// if (_.find(self.listOrder, (o: any) => o.closingTimeFrame==frameNo)) {
			// 	let frameItemsOrigin: any[] = _.find(self.listOrder, (o: any) => o.closingTimeFrame==frameNo).listBentoReservationDetail;
			// 	frameItemsOrigin.forEach((item: any) => {
			// 		let itemByFrame: any = _.find(countLst, (o: any) => o.frameNo() == item.frameNo);
			// 		if (itemByFrame) {
			// 			let bentoCount = itemByFrame.bentoCount();
			// 			if (item.bentoCount != bentoCount) {
			// 				result = true;
			// 			}
			// 		} else {
			// 			result = true;
			// 		}
			// 	});
			// } else {
			// 	result = true;
			// }
			if (_.find(self.listOrder, (o: any) => o.closingTimeFrame==frameNo)) {
				let frameItemsOrigin: any[] = _.find(self.listOrder, (o: any) => o.closingTimeFrame==frameNo).listBentoReservationDetail;
				let menuFrame: any[] = frameNo == 1 ? self.bentoFrame1List() : self.bentoFrame2List();
				menuFrame.forEach(o => {
					let itemOrigin = _.find(frameItemsOrigin, x => x.frameNo == o.frameNo());
					let itemScreen = _.find(countLst, x => x.frameNo() == o.frameNo());

					if (!itemOrigin && itemScreen) result = true;
					if (itemOrigin && !itemScreen) result = true;
					if (itemOrigin && itemScreen) {
						if (itemOrigin.bentoCount != itemScreen.bentoCount()) {
							result = true;
						}
					}
				})
			} else {
				if (countLst.length > 0) {
					return true;
				}
			}

			return result;
		}

		getData(): JQueryPromise<any> {
			let self = this,
                dfd = $.Deferred<any>(),
				date = moment(self.date()).format("YYYY/MM/DD"),
				param = { date }; 
			nts.uk.ui.block.invisible();
			service.startScreen(param).done((data: any) => {
				self.listOrder = data.listOrder;
				self.bentoMenuByClosingTimeDto = data.bentoMenuByClosingTimeDto;
				self.frameOption(data.bentoMenuByClosingTimeDto.reservationRecTimeZoneLst);
				if(!_.isEmpty(self.frameOption())) {
					if (!self.currentFrameNo()) {
						self.currentFrameNo(_.head(self.frameOption()).frameNo);
					}
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
				self.canOrder(self.getCanOrder());
				dfd.resolve();
			}).fail((res: any) => {
				error({ messageId: res.messageId });
				dfd.reject();
			}).always(() => {
				nts.uk.ui.block.clear();	
			});
            return dfd.promise();	
		}
		
		getCanOrder() {
			let self = this;
			let orderFrame1 = _.find(self.listOrder, o => o.closingTimeFrame==1);
			if(orderFrame1) {
				if(orderFrame1.ordered == false && self.currentFrameNo()==1 && self.bentoMenuByClosingTimeDto.reservationTime1) {
					return true;
				}				
			} else {
				if(self.currentFrameNo()==1 && self.bentoMenuByClosingTimeDto.reservationTime1) {
					return true;
				}
			}
			let orderFrame2 = _.find(self.listOrder, o => o.closingTimeFrame==2);
			if(orderFrame2) {
				if(orderFrame2.ordered == false && self.currentFrameNo()==2 && self.bentoMenuByClosingTimeDto.reservationTime2) {
					return true;
				}				
			} else {
				if(self.currentFrameNo()==2 && self.bentoMenuByClosingTimeDto.reservationTime2) {
					return true;
				}
			}
			return false;	
		}

		register() {
			let self = this,
				closingTimeFrameNo = self.currentFrameNo(),
				date = moment(self.date()).format("YYYY/MM/DD"),
				details: Array<any> = [],
				positiveCountLst1 = _.filter(self.bentoFrame1List(), (o) => o.bentoCount()>0),
				positiveCountLst2 = _.filter(self.bentoFrame2List(), (o) => o.bentoCount()>0);
			if(_.isEmpty(self.listOrder) && _.isEmpty(_.concat(positiveCountLst1, positiveCountLst2))) {
				return error({ messageId: 'Msg_1605' });
			}

			if (self.currentFrameNo() === 1) {
				_.forEach(positiveCountLst1, (item) => {
					details.push({
						closingTimeFrame: 1,
						frameNo: item.frameNo(),
						bentoCount: item.bentoCount()
					})		
				});
			}
			if (self.currentFrameNo() === 2) {
				_.forEach(positiveCountLst2, (item) => {
					details.push({
						closingTimeFrame: 2,
						frameNo: item.frameNo(),
						bentoCount: item.bentoCount()
					})		
				});
			}
			let command = { date, closingTimeFrameNo, details };
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

		toKMR005() {
			const self = this;
			let date = moment(self.dateBefore).format("YYYY/MM/DD"),
				closingTimeFrameNo = self.currentFrameNo(),
				details: Array<any> = [],
				positiveCountLst1 = _.filter(self.bentoFrame1List(), (o) => o.bentoCount()>0),
				positiveCountLst2 = _.filter(self.bentoFrame2List(), (o) => o.bentoCount()>0);

			if (self.checkChangeOrder(self.currentFrameNo() == 1 ? positiveCountLst1 : positiveCountLst2, self.currentFrameNo()) && ((self.currentFrameNo() == 1 && positiveCountLst1.length > 0) || (self.currentFrameNo() == 2 && positiveCountLst2.length > 0))) {
				confirm("Msg_3326").ifYes(() => {
					if(_.isEmpty(self.listOrder) && _.isEmpty(_.concat(positiveCountLst1, positiveCountLst2))) {
						return error({ messageId: 'Msg_1605' });
					}
					if (self.currentFrameNo() === 1) {
						_.forEach(positiveCountLst1, (item) => {
							details.push({
								closingTimeFrame: 1,
								frameNo: item.frameNo(),
								bentoCount: item.bentoCount()
							})		
						});
					}
					if (self.currentFrameNo() ===2) {
						_.forEach(positiveCountLst2, (item) => {
							details.push({
								closingTimeFrame: 2,
								frameNo: item.frameNo(),
								bentoCount: item.bentoCount()
							})		
						});
					}
					let command = { date, closingTimeFrameNo, details };
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
							nts.uk.request.jump("/view/kmr/005/a/index.xhtml", { 
								yearMonth: moment(self.date()).utc().format("YYYY/MM"),
								employeeID: __viewContext.user.employeeId 
							});	
						});
					} else {
						nts.uk.ui.block.invisible();
						service.update(command).done(() => {
							info({ messageId: "Msg_15" });	
						}).fail((res: any) => {
							error({ messageId: res.messageId });
						}).always(() => {
							nts.uk.ui.block.clear();
							nts.uk.request.jump("/view/kmr/005/a/index.xhtml", { 
								yearMonth: moment(self.date()).utc().format("YYYY/MM"),
								employeeID: __viewContext.user.employeeId 
							});	
						});
					}
				})
				// .then(() => {
				// 	nts.uk.request.jump("/view/kmr/005/a/index.xhtml", { 
				// 		yearMonth: moment(self.date()).utc().format("YYYY/MM"),
				// 		employeeID: __viewContext.user.employeeId 
				// 	});
				// })
			} else {
				nts.uk.request.jump("/view/kmr/005/a/index.xhtml", { 
					yearMonth: moment(self.date()).utc().format("YYYY/MM"),
					employeeID: __viewContext.user.employeeId 
				});
			}
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
			this.bentoCount.subscribe(() => {
				$('.bento-count-' + this.frameNo() + ' input').ntsError('clear');
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

