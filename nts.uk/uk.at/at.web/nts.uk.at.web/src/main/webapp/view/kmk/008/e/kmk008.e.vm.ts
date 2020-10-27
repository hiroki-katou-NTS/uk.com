module nts.uk.at.view.kmk008.e {
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;

    export module viewmodel {
        export class ScreenModel {
            timeOfWorkPlace: KnockoutObservable<TimeOfWorkPlaceModel>;
            isUpdate: boolean;
            laborSystemAtr: number = 0;
            currentWorkplaceName: KnockoutObservable<string>;
            textOvertimeName: KnockoutObservable<string>;

            maxRows: number;
            selectedWorkplaceId: KnockoutObservable<string>;
            selectedRowWorkplace: RowSelection;
            baseDate: KnockoutObservable<Date>;
            alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
            treeGrid: any;
            workplaceGridList: KnockoutObservableArray<UnitModel>;
            isRemove: KnockoutObservable<boolean>;
            isShowAlreadySet: KnockoutObservable<boolean>;

			limitOptions: any;

            constructor(laborSystemAtr: number) {
                let self = this;
                self.laborSystemAtr = laborSystemAtr;
                self.isUpdate = true;
                self.timeOfWorkPlace = ko.observable(new TimeOfWorkPlaceModel(null));
                self.currentWorkplaceName = ko.observable("");
                self.textOvertimeName = ko.observable(getText("KMK008_12", ['#KMK008_8', '#Com_Workplace']));

				self.limitOptions = [
					{code: 0, name : getText('KMK008_190')},
					{code: 1, name : getText('KMK008_191')},
					{code: 2, name : getText('KMK008_192')},
					{code: 3, name : getText('KMK008_193')},
					{code: 4, name : getText('KMK008_194')},
					{code: 5, name : getText('KMK008_195')},
					{code: 6, name : getText('KMK008_196')},
					{code: 7, name : getText('KMK008_197')},
					{code: 8, name : getText('KMK008_198')},
					{code: 9, name : getText('KMK008_199')},
					{code: 10, name : getText('KMK008_200')},
					{code: 11, name : getText('KMK008_201')},
					{code: 12, name : getText('KMK008_202')}
				];

                self.workplaceGridList = ko.observableArray([]);
                self.baseDate = ko.observable(new Date());
                self.selectedWorkplaceId = ko.observable("");
                self.alreadySettingList = ko.observableArray([]);
                self.isRemove = ko.observable(false);
                self.isShowAlreadySet = ko.observable(true);

                self.treeGrid = {
                    maxRows: 15,
                    isShowAlreadySet: self.isShowAlreadySet,
                    isMultiSelect: false,
                    treeType: 1,
                    selectedId: self.selectedWorkplaceId,
                    baseDate: self.baseDate,
                    selectType: 1,
                    isShowSelectButton: true,
                    isDialog: false,
                    alreadySettingList: self.alreadySettingList,
                    systemType: 2
                };

                self.selectedWorkplaceId.subscribe(newValue => {
                    if (nts.uk.text.isNullOrEmpty(newValue)) return;
                    self.getDetail(newValue);
                    let WorkplaceSelect = self.findUnitModelByWorkplaceId(self.workplaceGridList(), newValue);
                    if (WorkplaceSelect) {
                        self.currentWorkplaceName(WorkplaceSelect.name);
                        self.isRemove(WorkplaceSelect.isAlreadySetting);
                    }
                });
            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();

                $('#work-place-base-date').prop('tabIndex', -1);
                $(document).ready(function() {
                    $('tabindex').removeAttr("tabindex");
                });

                if (self.laborSystemAtr == 0) {
                    self.textOvertimeName(getText("KMK008_12", ['{#KMK008_8}', '{#Com_Workplace}']));
                } else {
                    self.textOvertimeName(getText("KMK008_12", ['{#KMK008_9}', '{#Com_Workplace}']));
                }

                $('#tree-grid-screen-e').ntsTreeComponent(self.treeGrid).done(function() {
                    self.getAlreadySettingList();
                    self.workplaceGridList($('#tree-grid-screen-e').getDataList());
                    if (self.workplaceGridList().length > 0) {
						if (self.selectedWorkplaceId() == '') {
							self.selectedWorkplaceId(self.workplaceGridList()[0].workplaceId);
						}
                    }
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
							return new UnitAlreadySettingModel(item.toString(), true);
						}));
                        _.defer(() => self.workplaceGridList($('#tree-grid-screen-e').getDataList()));
                    }
                    if (self.workplaceGridList().length > 0 && !self.selectedWorkplaceId()) {
                        self.selectedWorkplaceId(self.workplaceGridList()[0].workplaceId);
                    }
                });
                self.isRemove(self.isShowAlreadySet());
            }

            findUnitModelByWorkplaceId(workplaceGridList: Array<UnitModel>, workplaceId: string): UnitModel {
                let self = this;
                for (let item of workplaceGridList) {
                    if (item.workplaceId == workplaceId) {
                        return item;
                    }
                    if (item.childs.length > 0) {
                        let WorkplaceChild = this.findUnitModelByWorkplaceId(item.childs, workplaceId);
                        if (WorkplaceChild != null) {
                            return WorkplaceChild;
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
					self.selectedWorkplaceId()
				);
                nts.uk.ui.block.invisible();
                new service.Service().addAgreementTimeOfWorkPlace(timeOfWorkPlaceNew).done(() => {
					nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
						self.startPage();
					});
					nts.uk.ui.block.clear();
                    self.getAlreadySettingList();
                    self.getDetail(self.selectedWorkplaceId());
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
                        let deleteModel = new DeleteTimeOfWorkPlaceModel(self.laborSystemAtr, self.selectedWorkplaceId());
                        new service.Service().removeAgreementTimeOfWorkplace(deleteModel).done(function() {
                            self.getAlreadySettingList();
                            self.getDetail(self.selectedWorkplaceId());
                            self.isRemove(false);
                        });
                        nts.uk.ui.dialog.info(nts.uk.resource.getMessage("Msg_16", []));
                    });
                nts.uk.ui.block.clear();

            }

            getDetail(workplaceId: string) {
                let self = this;
                new service.Service().getDetail(self.laborSystemAtr, workplaceId).done(data => {
                    self.timeOfWorkPlace(new TimeOfWorkPlaceModel(data));
                }).fail(error => {});
            }
            
            showDialogError(listError: any) {
                let errorCode = _.split(listError[0], ',');
                if (errorCode[0] === 'Msg_59') {
                    let periodName = getText(errorCode[1]);
                    let param1 = "期間: " + getText(errorCode[1]) + "<br>" + getText(errorCode[2]);
                    alertError({ messageId: errorCode[0], messageParams: [param1, getText(errorCode[3])] });
                } else {
                    alertError({ messageId: errorCode[0], messageParams: [getText(errorCode[1]), getText(errorCode[2]), getText(errorCode[3])] });
                }
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
                if (!data) return;
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
            workplaceId: string;
            code: string;
            name: string;
            nodeText: string;
            level: number;
            heirarchyCode: string;
            settingType: number;
            isAlreadySetting?: boolean;
            childs: Array<UnitModel>;
        }

        export class RowSelection {
            workplaceId: KnockoutObservable<string>;
            workplaceCode: KnockoutObservable<string>;
            constructor(workplaceId: string, workplaceCode: string) {
                let self = this;
                self.workplaceId = ko.observable(workplaceId);
                self.workplaceCode = ko.observable(workplaceCode);
            }
        }

        export class UnitAlreadySettingModel {
            workplaceId: string;
            isAlreadySetting: boolean = true;
            constructor(workplaceId: string, isAlreadySetting: boolean) {
                this.workplaceId = workplaceId;
            }
        }
    }
}
