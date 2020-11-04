module nts.uk.at.view.kmk008.e {
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;

    export module viewmodel {
        export class ScreenModel  extends ko.ViewModel{
            timeOfClassification: KnockoutObservable<TimeOfClassificationModel>;
            isUpdate: boolean;
            laborSystemAtr: number = 0;
            currentItemDispName: KnockoutObservable<string>;
			currentItemName: KnockoutObservable<string>;
            textOvertimeName: KnockoutObservable<string>;

            listComponentOption: any;
            selectedCode: KnockoutObservable<string>;
            isShowAlreadySet: KnockoutObservable<boolean>;
            alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
            isDialog: KnockoutObservable<boolean>;
            isShowNoSelectRow: KnockoutObservable<boolean>;
            isMultiSelect: KnockoutObservable<boolean>;
            classificationList: KnockoutObservableArray<UnitModel>;
            isRemove: KnockoutObservable<boolean>;

            constructor(laborSystemAtr: number) {
                super();
                let self = this;
                self.laborSystemAtr = laborSystemAtr;
                self.isUpdate = true;
                self.timeOfClassification = ko.observable(new TimeOfClassificationModel(null));
                self.currentItemDispName = ko.observable("");
				self.currentItemName = ko.observable("");
                self.textOvertimeName = ko.observable(getText("KMK008_12", ['#KMK008_8', '#Com_Class']));

                self.selectedCode = ko.observable("");
                self.isShowAlreadySet = ko.observable(true);
                self.alreadySettingList = ko.observableArray([]);
                self.isRemove = ko.observable(false);

                self.isDialog = ko.observable(false);
                self.isShowNoSelectRow = ko.observable(false);
                self.isMultiSelect = ko.observable(false);
                self.listComponentOption = {
                    maxRows: 12,
                    isShowAlreadySet: self.isShowAlreadySet(),
                    isMultiSelect: self.isMultiSelect(),
                    listType: 2,
                    selectType: 1,
                    selectedCode: self.selectedCode,
                    isDialog: self.isDialog(),
                    isShowNoSelectRow: self.isShowNoSelectRow(),
                    alreadySettingList: self.alreadySettingList
                };
                self.classificationList = ko.observableArray<UnitModel>([]);
                self.selectedCode.subscribe(newValue => {
					if (nts.uk.text.isNullOrEmpty(newValue) || newValue == "undefined") {
						self.getDetail(null);
						self.currentItemDispName('');
						self.currentItemName('');
						self.isRemove(false);
						return;
					}

                    self.getDetail(newValue);
                    let selectedItem = _.find(self.classificationList(), emp => {
                        return emp.code == newValue;
                    });
                    if (selectedItem) {
						self.currentItemDispName(selectedItem.code + '　' + selectedItem.name);
						self.currentItemName(selectedItem.name);
						if (selectedItem.isAlreadySetting === true){
							self.isRemove(true);
						} else {
							self.isRemove(false);
						}
                    }

                });
            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();

                nts.uk.ui.errors.clearAll();
                if (self.laborSystemAtr == 0) {
                    self.textOvertimeName(getText("KMK008_12", ['{#KMK008_8}', '{#Com_Class}']));
                } else {
                    self.textOvertimeName(getText("KMK008_12", ['{#KMK008_9}', '{#Com_Class}']));
                }
                $('#empt-list-setting-screen-e').ntsListComponent(self.listComponentOption).done(function() {
					self.getAlreadySettingList(true);
                    if (self.classificationList().length > 0 && nts.uk.text.isNullOrEmpty(self.selectedCode())) {
                    	self.selectedCode(self.classificationList()[0].code);
                    }
                    dfd.resolve();
                });
                return dfd.promise();
            }

            getAlreadySettingList(startPate?: boolean) {
                let self = this;
                new service.Service().getList(self.laborSystemAtr).done(data => {
                    if (data.classificationCodes.length > 0) {
                        self.alreadySettingList(_.map(data.classificationCodes, item => {
                        	return new UnitAlreadySettingModel(item.toString(), true);
                        }));
                        _.defer(() => {
							self.classificationList($('#empt-list-setting-screen-e').getDataList());
							self.selectedCode.valueHasMutated();
                        });

						if (startPate) {
							self.initFocus();
						}
                    }
                });
            }

            addUpdateDataClassification() {
                let self = this;
                
                if(self.classificationList().length == 0) return;
                
                let timeOfClassificationNew = new UpdateInsertTimeOfClassificationModel(self.timeOfClassification(), self.laborSystemAtr, self.selectedCode());
                nts.uk.ui.block.invisible();
                if (self.selectedCode() != "") {
                    new service.Service().addAgreementTimeOfClassification(timeOfClassificationNew).done(() => {
						nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
							self.getAlreadySettingList();
						});
						nts.uk.ui.block.clear();
                    }).fail((error)=>{
                    	if (error.messageId == 'Msg_59') {
							error.parameterIds.unshift("Q&A 34201");
						}
						alertError({ messageId: error.messageId, messageParams: error.parameterIds});
						nts.uk.ui.block.clear();
					});
                }
            }

            removeDataClassification() {
                let self = this;
                nts.uk.ui.dialog.confirm({ messageId: "Msg_18" })
                    .ifYes(() => {
                        let deleteModel = new DeleteTimeOfClassificationModel(self.laborSystemAtr, self.selectedCode());
                        new service.Service().removeAgreementTimeOfEmployment(deleteModel).done(function() {
                            self.getAlreadySettingList();
                        });
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                    });
            }

            getDetail(classificationCode: string) {
                let self = this;

				if (!classificationCode) {
					self.timeOfClassification(new TimeOfClassificationModel(null));
					return;
				}

                new service.Service().getDetail(self.laborSystemAtr, classificationCode).done(data => {
                    self.timeOfClassification(new TimeOfClassificationModel(data));
                }).fail(error => {
					if (error.messageId == 'Msg_59') {
						error.parameterIds.unshift("Q&A 34201");
					}
					alertError({ messageId: error.messageId, messageParams: error.parameterIds});
					nts.uk.ui.block.clear();
                });
            }

			copySetting() {
				let self = this;

				const listSetting = _.map(self.alreadySettingList(), function (item: any) {
					return item.code;
				});

				//CDL023：複写ダイアログを起動する
				let param = {
					code: self.selectedCode(),
					name: self.currentItemName(),
					targetType: 2, // 分類 - CLASSIFICATION
					itemListSetting: listSetting
				};
				nts.uk.ui.windows.setShared("CDL023Input", param);
				nts.uk.ui.windows.sub.modal("com", "/view/cdl/023/a/index.xhtml").onClosed(() => {
					let data = nts.uk.ui.windows.getShared("CDL023Output");
					if (!nts.uk.util.isNullOrUndefined(data)) {
						nts.uk.ui.block.invisible();
						self.callCopySettingAPI(data).done(() => {
							nts.uk.ui.dialog.info({messageId: "Msg_15"}).then(() => {
								self.getAlreadySettingList();
							});
						}).fail((error)=> {
							alertError(error);
						}).always(()=>{
							nts.uk.ui.block.clear();
						});
					}
				});
			}

			initFocus() {
				_.defer(()=> {
					$('#E4_14 input').focus();
				});
			}

			callCopySettingAPI(data:any): JQueryPromise<any> {
				let self = this;
				let promises:any = [];

				_.forEach(data, classificationCdTarget => {
					let dfd = $.Deferred();
					let command = {
						classificationCdTarget: classificationCdTarget,
						classificationCdSource: self.selectedCode(),
						laborSystemAtr: self.laborSystemAtr
					};

					if (command.classificationCdTarget == command.classificationCdSource) {
						dfd.resolve();
						return;
					}

					new service.Service().copySetting(command).done((result) => {
						dfd.resolve(result);
					}).fail((error:any) => {
						dfd.reject(error);
					});
					promises.push(dfd);
				});

				return $.when.apply(undefined, promises).promise();
			}
        }

        export class TimeOfClassificationModel {
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
					data = nts.uk.at.view.kmk008.b.INIT_DEFAULT;
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

                    if ($("#E4_33").ntsError("hasError")) {
                        self.errorMonthAverage2(null);
                        return;
                    }

                    $('#E4_34').ntsError('clear');
                    self.errorMonthAverage2(newValue);
                });
                self.errorMonthAverage2.subscribe(newValue => {
                    if (self.isSubscribe2) {
                        self.isSubscribe2 = false;
                        return;
                    }
                    self.isSubscribe = true;

                    if ($("#E4_34").ntsError("hasError")) {
                        self.errorMonthAverage(null);
                        return;
                    }

                    $('#E4_33').ntsError('clear');
                    self.errorMonthAverage(newValue);
                });

				self.alarmMonthAverage(data.alarmMonthAverage);
            }
        }

		export class UpdateInsertTimeOfClassificationModel {
            laborSystemAtr: number = 0;
			overMaxTimes: number = 0;
			classificationCode: string = "";

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

            constructor(data: TimeOfClassificationModel, laborSystemAtr: number, classificationCode: string) {
                let self = this;
                self.laborSystemAtr = laborSystemAtr;
				self.classificationCode = classificationCode;
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

        export class DeleteTimeOfClassificationModel {
            laborSystemAtr: number = 0;
			classificationCode: string;
            constructor(laborSystemAtr: number, classificationCode: string) {
                let self = this;
                self.laborSystemAtr = laborSystemAtr;
                self.classificationCode = classificationCode;
            }
        }

        export interface UnitModel {
            code: string;
            name?: string;
            affiliationName?: string;
            isAlreadySetting?: boolean;
        }

        export class UnitAlreadySettingModel {
            code: string;
            isAlreadySetting: boolean;
            constructor(code: string, isAlreadySetting: boolean) {
                this.code = code;
                this.isAlreadySetting = isAlreadySetting;
            }
        }
    }
}
