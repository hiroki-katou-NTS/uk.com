module nts.uk.at.view.kmk008.d {
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
	import master = nts.uk.at.view.kmk008.b;

    export module viewmodel {
		export class ScreenModel  extends ko.ViewModel{
            timeOfWorkPlace: KnockoutObservable<WkpTimeSetting>;
            isUpdate: boolean;
            laborSystemAtr: number = 0;
            currentItemDispName: KnockoutObservable<string>;
			currentItemName: KnockoutObservable<string>;
			workplaceCode: KnockoutObservable<string>;
            textOvertimeName: KnockoutObservable<string>;

            maxRows: number;
            selectedCode: KnockoutObservable<string>;
            baseDate: KnockoutObservable<Date>;
            alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
            treeGrid: any;
            workplaceGridList: KnockoutObservableArray<UnitModel>;
            isRemove: KnockoutObservable<boolean>;
            isShowAlreadySet: KnockoutObservable<boolean>;

            constructor(laborSystemAtr: number) {
                super();
                let self = this;
                self.laborSystemAtr = laborSystemAtr;
                self.isUpdate = true;
                self.timeOfWorkPlace = ko.observable(new WkpTimeSetting(null));
                self.currentItemDispName = ko.observable("");
				self.currentItemName= ko.observable("");
				self.workplaceCode = ko.observable("");
                self.textOvertimeName = ko.observable(getText("KMK008_12", ['#KMK008_8', '#Com_Workplace']));

                self.workplaceGridList = ko.observableArray([]);
                self.baseDate = ko.observable(new Date());
                self.selectedCode = ko.observable("");
                self.alreadySettingList = ko.observableArray([]);
                self.isRemove = ko.observable(false);
                self.isShowAlreadySet = ko.observable(true);

                self.treeGrid = {
                    maxRows: 12,
                    isShowAlreadySet: self.isShowAlreadySet,
                    isMultiSelect: false,
                    treeType: 1,
                    selectedId: self.selectedCode,
                    baseDate: self.baseDate,
                    selectType: 1,
                    isShowSelectButton: true,
                    isDialog: false,
                    alreadySettingList: self.alreadySettingList,
                    systemType: 2
                };

                self.selectedCode.subscribe(newValue => {
					if (nts.uk.text.isNullOrEmpty(newValue) || newValue == "undefined") {
						self.getDetail(null);
						self.currentItemDispName('');
						self.currentItemName("");
						self.workplaceCode("");
						self.isRemove(false);
						return;
					}

                    self.getDetail(newValue);
                    let selectedItem = self.findUnitModelByWorkplaceId(self.workplaceGridList(), newValue);
                    if (selectedItem) {
						self.currentItemDispName(selectedItem.nodeText);
						self.currentItemName(selectedItem.name);
						self.workplaceCode(selectedItem.code);
						if (selectedItem.isAlreadySetting === true) {
							self.isRemove(true);
						} else {
							self.isRemove(false);
						}
                    }
                });
            }

            startPage(reloadScreen?: boolean): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();

				nts.uk.ui.errors.clearAll();
                if (self.laborSystemAtr == 0) {
                    self.textOvertimeName(getText("KMK008_12", ['{#KMK008_8}', '{#Com_Workplace}']));
                } else {
                    self.textOvertimeName(getText("KMK008_12", ['{#KMK008_9}', '{#Com_Workplace}']));
                }

                if (reloadScreen) self.selectedCode("");
                $('#tree-grid-screen-d').ntsTreeComponent(self.treeGrid).done(function() {
					self.workplaceGridList($('#tree-grid-screen-d').getDataList());
                    self.getAlreadySettingList(reloadScreen);
					if (self.workplaceGridList().length > 0 && nts.uk.text.isNullOrEmpty(self.selectedCode())){
						self.selectedCode(self.workplaceGridList()[0].id);
                    }
                    dfd.resolve();
                });

                return dfd.promise();
            }

            getAlreadySettingList(reloadScreen?: boolean) {
                let self = this;
                new service.Service().getList(self.laborSystemAtr).done(data => {
                    if (data.workPlaceIds.length > 0) {
						self.alreadySettingList(_.map(data.workPlaceIds, item => {
							return new UnitAlreadySettingModel(item.toString());
						}));
                    } else {
						self.alreadySettingList([]);
					}
					self.workplaceGridList($('#tree-grid-screen-d').getDataList());
					if (reloadScreen) {
						self.initFocus();
					} else {
						self.selectedCode.valueHasMutated();
					}
                });
            }

            initFocus() {
				_.defer(()=> {
					$('#D4_14 input').focus();
				});
			}

            findUnitModelByWorkplaceId(workplaceGridList: Array<UnitModel>, workplaceId: string): UnitModel {
                for (let item of workplaceGridList) {
                    if (item.id == workplaceId) {
                        return item;
                    }
                    if (item.children.length > 0) {
                        let workplaceChild = this.findUnitModelByWorkplaceId(item.children, workplaceId);
                        if (workplaceChild != null) {
                            return workplaceChild;
                        }
                    }
                }
                return null;
            }

			persisData() {
                let self = this;
                
                if(self.workplaceGridList().length == 0) return;

                let wkpTimeSettingForPersis = new WkpTimeSettingForPersis(
                	self.timeOfWorkPlace(),
					self.laborSystemAtr,
					self.selectedCode()
				);

				let validateErr = master.validateTimeSetting(wkpTimeSettingForPersis);
				if (validateErr) {
					alertError(validateErr).then(()=>{
						$("#D4_" + validateErr.errorPosition + " input").focus();
					});
					return;
				}

                nts.uk.ui.block.invisible();
                new service.Service().addAgreementTimeOfWorkPlace(wkpTimeSettingForPersis).done(() => {
					nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
						self.startPage();
						nts.uk.ui.block.clear();
					});
                }).fail((error)=>{
					alertError({ messageId: error.messageId, messageParams: error.parameterIds});
					nts.uk.ui.block.clear();
				});
            }
				
            removeData() {
                let self = this;
                nts.uk.ui.dialog.confirm({ messageId: "Msg_18" })
                    .ifYes(() => {
                        let deleteModel = new WkpTimeSettingForDelete(self.laborSystemAtr, self.selectedCode());
                        new service.Service().removeAgreementTimeOfWorkplace(deleteModel).done(function() {
                            self.startPage();
                        });
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                    });
                nts.uk.ui.block.clear();

            }

            getDetail(workplaceId: string) {
                let self = this;

				if (!workplaceId) {
					self.timeOfWorkPlace(new WkpTimeSetting(null));
					return;
				}

                new service.Service().getDetail(self.laborSystemAtr, workplaceId).done(data => {
                    self.timeOfWorkPlace(new WkpTimeSetting(data));
                }).fail(error => {
					alertError({ messageId: error.messageId, messageParams: error.parameterIds});
					nts.uk.ui.block.clear();
				});
            }

			copySetting() {
				let self = this;

				const listSetting = _.map(self.alreadySettingList(), function (item: any) {
					return item.workplaceId;
				});

				//CDL023：複写ダイアログを起動する
				let param = {
					code: self.workplaceCode(),
					name: self.currentItemName(),
					targetType: 4, // 職場 - WORKPLACE
					itemListSetting: listSetting,
					baseDate: ko.toJS(new Date())
				};

				nts.uk.ui.windows.setShared("CDL023Input", param);
				nts.uk.ui.windows.sub.modal("com", "/view/cdl/023/a/index.xhtml").onClosed(() => {
					let data = nts.uk.ui.windows.getShared("CDL023Output");
					if (!nts.uk.util.isNullOrUndefined(data)) {
						nts.uk.ui.block.invisible();
						self.callCopySettingAPI(data).done(() => {
							nts.uk.ui.dialog.info({messageId: "Msg_15"}).then(() => {
								self.startPage();
							});
						}).fail((error)=> {
							alertError(error);
						}).always(()=>{
							nts.uk.ui.block.clear();
						});
					}
				});
			}

			callCopySettingAPI(cds: string[]): JQueryPromise<any> {
				let self = this;

				let dfd = $.Deferred();
				let command = {
					workplaceIdTarget: cds,
					workplaceIdSource: self.selectedCode(),
					laborSystemAtr: self.laborSystemAtr
				};

				new service.Service().copySetting(command).done((result) => {
					dfd.resolve(result);
				}).fail((error:any) => {
					dfd.reject(error);
				});

				return dfd.promise();
			}
        }

        export class WkpTimeSetting {
            overMaxTimes: KnockoutObservable<string> = ko.observable(null);

			limitOneMonth: KnockoutObservable<string> = ko.observable(null);
			alarmOneMonth: KnockoutObservable<string> = ko.observable(null);
			errorOneMonth: KnockoutObservable<string> = ko.observable(null);

			limitTwoMonths: KnockoutObservable<string> = ko.observable(null);
			alarmTwoMonths: KnockoutObservable<string> = ko.observable(null);
			errorTwoMonths: KnockoutObservable<string> = ko.observable(null);

			alarmOneYear: KnockoutObservable<string> = ko.observable(null);
			errorOneYear: KnockoutObservable<string> = ko.observable(null);

			limitOneYear: KnockoutObservable<string> = ko.observable(null);
			errorTwoYear: KnockoutObservable<string> = ko.observable(null);
			alarmTwoYear: KnockoutObservable<string> = ko.observable(null);

			errorMonthAverage: KnockoutObservable<string> = ko.observable(null);
			errorMonthAverage2: KnockoutObservable<string> = ko.observable(null);
			isSubscribe: boolean = false;
			isSubscribe2: boolean = false;

			alarmMonthAverage: KnockoutObservable<string> = ko.observable(null);

            constructor(data: any) {
                let self = this;
				if (!data) {
					data = master.INIT_DEFAULT;
				}

				self.overMaxTimes(data.overMaxTimes);

				self.limitOneMonth(data.limitOneMonth);
                self.alarmOneMonth(data.alarmOneMonth);
                self.errorOneMonth(data.errorOneMonth);

                self.limitTwoMonths(data.limitTwoMonths);
                self.alarmTwoMonths(data.alarmTwoMonths);
                self.errorTwoMonths(data.errorTwoMonths);

                self.alarmOneYear(data.alarmOneYear);
                self.errorOneYear(data.errorOneYear);

				self.limitOneYear(data.limitOneYear);
				self.errorTwoYear(data.errorTwoYear);
				self.alarmTwoYear(data.alarmTwoYear);

				self.errorMonthAverage(data.errorMonthAverage);
				self.errorMonthAverage2(data.errorMonthAverage);

				self.errorMonthAverage.subscribe(newValue => {
					if (self.isSubscribe) {
						self.isSubscribe = false;
						return;
					}
					self.isSubscribe2 = true;

					if ($("#D4_33").ntsError("hasError")) {
						self.errorMonthAverage2(null);
						return;
					}

					$('#D4_34').ntsError('clear');
					self.errorMonthAverage2(newValue);
				});
				self.errorMonthAverage2.subscribe(newValue => {
					if (self.isSubscribe2) {
						self.isSubscribe2 = false;
						return;
					}
					self.isSubscribe = true;

					if ($("#D4_34").ntsError("hasError")) {
						self.errorMonthAverage(null);
						return;
					}

					$('#D4_33').ntsError('clear');
					self.errorMonthAverage(newValue);
				});

				self.alarmMonthAverage(data.alarmMonthAverage);
            }
        }

        export class WkpTimeSettingForPersis {
            laborSystemAtr: number = 0;
			overMaxTimes: number = 0;
			workplaceId: string = "";

			limitOneMonth: number = 0;
			alarmOneMonth: number = 0;
            errorOneMonth: number = 0;

			limitTwoMonths: number = 0;
            alarmTwoMonths: number = 0;
            errorTwoMonths: number = 0;

            alarmOneYear: number = 0;
            errorOneYear: number = 0;

			limitOneYear: number = 0;
			errorTwoYear: number = 0;
			alarmTwoYear: number = 0;

			upperMonthAverageError: number = 0;
			upperMonthAverageAlarm: number = 0;

            constructor(data: WkpTimeSetting, laborSystemAtr: number, workplaceId: string) {
                let self = this;
                self.laborSystemAtr = laborSystemAtr;
				self.workplaceId = workplaceId;
				if (!data) return;

				self.overMaxTimes = +data.overMaxTimes()||0;

				self.limitOneMonth = +data.limitOneMonth() || 0;
                self.alarmOneMonth = +data.alarmOneMonth() || 0;
                self.errorOneMonth = +data.errorOneMonth() || 0;

				self.limitTwoMonths = +data.limitTwoMonths() || 0;
                self.alarmTwoMonths = +data.alarmTwoMonths() || 0;
                self.errorTwoMonths = +data.errorTwoMonths() || 0;

                self.alarmOneYear = +data.alarmOneYear() || 0;
                self.errorOneYear = +data.errorOneYear() || 0;

				self.limitOneYear = +data.limitOneYear() || 0;
				self.errorTwoYear = +data.errorTwoYear() || 0;
				self.alarmTwoYear = +data.alarmTwoYear() || 0;

				self.upperMonthAverageError = +data.errorMonthAverage() || 0;
				self.upperMonthAverageAlarm = +data.alarmMonthAverage() || 0;
            }
        }

        export class WkpTimeSettingForDelete {
            laborSystemAtr: number = 0;
            workplaceId: string;
            constructor(laborSystemAtr: number, workplaceId: string) {
                let self = this;
                self.laborSystemAtr = laborSystemAtr;
                self.workplaceId = workplaceId;
            }
        }

        export interface UnitModel {
            id: string;
            code: string;
            name: string;
            nodeText: string;
            level: number;
			hierarchyCode: string;
			workplaceDisplayName: string;
			workplaceGeneric: string;
            isAlreadySetting?: boolean;
			children: Array<UnitModel>;
        }

        export class UnitAlreadySettingModel {
            workplaceId: string;
            isAlreadySetting: boolean = true;
            constructor(workplaceId: string) {
                this.workplaceId = workplaceId;
            }
        }
    }
}
