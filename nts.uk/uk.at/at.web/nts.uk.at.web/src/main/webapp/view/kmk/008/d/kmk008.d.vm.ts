module nts.uk.at.view.kmk008.d {
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;

    export module viewmodel {
		export class ScreenModel  extends ko.ViewModel{
            timeOfWorkPlace: KnockoutObservable<TimeOfWorkPlaceModel>;
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
                self.timeOfWorkPlace = ko.observable(new TimeOfWorkPlaceModel(null));
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
                    maxRows: 15,
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

                if (self.laborSystemAtr == 0) {
                    self.textOvertimeName(getText("KMK008_12", ['{#KMK008_8}', '{#Com_Workplace}']));
                } else {
                    self.textOvertimeName(getText("KMK008_12", ['{#KMK008_9}', '{#Com_Workplace}']));
                }

                $('#tree-grid-screen-d').ntsTreeComponent(self.treeGrid).done(function() {
                    self.getAlreadySettingList();
					if (self.workplaceGridList().length > 0 && nts.uk.text.isNullOrEmpty(self.selectedCode())){
						self.selectedCode(self.workplaceGridList()[0].id);
                    }
					$('#D4_14 input').focus();
                    dfd.resolve();
                });

                return dfd.promise();
            }

            getAlreadySettingList() {
                let self = this;
                self.alreadySettingList([]);
                new service.Service().getList(self.laborSystemAtr).done(data => {
                    if (data.workPlaceIds.length > 0) {
						self.alreadySettingList(_.map(data.workPlaceIds, item => {
							return new UnitAlreadySettingModel(item.toString());
						}));
                        _.defer(() => {
                        	self.workplaceGridList($('#tree-grid-screen-d').getDataList());
							$('#D4_14 input').focus();
						});
                    }
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

            addUpdateWorkPlace() {
                let self = this;
                
                if(self.workplaceGridList().length == 0) return;

                let timeOfWorkPlaceNew = new UpdateInsertTimeOfWorkPlaceModel(
                	self.timeOfWorkPlace(),
					self.laborSystemAtr,
					self.selectedCode()
				);
                nts.uk.ui.block.invisible();
                new service.Service().addAgreementTimeOfWorkPlace(timeOfWorkPlaceNew).done(() => {
					nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
						self.startPage();
					});
					nts.uk.ui.block.clear();
                    self.getAlreadySettingList();
                    self.getDetail(self.selectedCode());
                }).fail((error)=>{
					if (error.messageId == 'Msg_59') {
						error.parameterIds.unshift("Q&A 34201");
					}
					alertError({ messageId: error.messageId, messageParams: error.parameterIds});
					nts.uk.ui.block.clear();
				});
            }
				
            removeDataWorkPlace() {
                let self = this;
                nts.uk.ui.dialog.confirm(nts.uk.resource.getMessage("Msg_18", []))
                    .ifYes(() => {
                        let deleteModel = new DeleteTimeOfWorkPlaceModel(self.laborSystemAtr, self.selectedCode());
                        new service.Service().removeAgreementTimeOfWorkplace(deleteModel).done(function() {
                            self.getAlreadySettingList();
                            self.getDetail(self.selectedCode());
                            self.isRemove(false);
                        });
                        nts.uk.ui.dialog.info(nts.uk.resource.getMessage("Msg_16", []));
                    });
                nts.uk.ui.block.clear();

            }

            getDetail(workplaceId: string) {
                let self = this;

				if (!workplaceId) {
					self.timeOfWorkPlace(new TimeOfWorkPlaceModel(null));
					return;
				}

                new service.Service().getDetail(self.laborSystemAtr, workplaceId).done(data => {
                    self.timeOfWorkPlace(new TimeOfWorkPlaceModel(data));
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
								self.startPage().done(() => {
									self.selectedCode.valueHasMutated();
								}).fail((error) => {
									alertError(error);
								});
							});
						}).fail((error)=> {
							alertError(error);
						}).always(()=>{
							nts.uk.ui.block.clear();
						});
					}
				});
			}

			callCopySettingAPI(data:any): JQueryPromise<any> {
				let self = this;
				let promises:any = [];

				_.forEach(data, workplaceIdTarget => {
					let dfd = $.Deferred();
					let command = {
						workplaceIdTarget: workplaceIdTarget,
						workplaceIdSource: self.selectedCode(),
						laborSystemAtr: self.laborSystemAtr
					};

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

        export class TimeOfWorkPlaceModel {
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
				self.alarmMonthAverage(data.alarmMonthAverage);
            }
        }

        export class UpdateInsertTimeOfWorkPlaceModel {
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

            constructor(data: TimeOfWorkPlaceModel, laborSystemAtr: number, workplaceId: string) {
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

        export class DeleteTimeOfWorkPlaceModel {
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
