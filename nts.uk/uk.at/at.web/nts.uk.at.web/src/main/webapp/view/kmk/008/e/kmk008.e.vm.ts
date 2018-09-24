module nts.uk.at.view.kmk008.e {
        import text = nts.uk.resource.getText;
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
            
            nameErrorWeek: KnockoutObservable<string> = ko.observable(text("KMK008_22") + text("KMK008_42"));
            nameAlarmWeek: KnockoutObservable<string> = ko.observable(text("KMK008_22") + text("KMK008_43"));
            nameLimitWeek: KnockoutObservable<string> = ko.observable(text("KMK008_22") + text("KMK008_44"));
            nameErrorTwoWeeks: KnockoutObservable<string> = ko.observable(text("KMK008_23") + text("KMK008_42"));
            nameAlarmTwoWeeks: KnockoutObservable<string> = ko.observable(text("KMK008_23") + text("KMK008_43"));
            nameLimitTwoWeeks: KnockoutObservable<string> = ko.observable(text("KMK008_23") + text("KMK008_44"));
            nameErrorFourWeeks: KnockoutObservable<string> = ko.observable(text("KMK008_24") + text("KMK008_42"));
            nameAlarmFourWeeks: KnockoutObservable<string> = ko.observable(text("KMK008_24") + text("KMK008_43"));
            nameLimitFourWeeks: KnockoutObservable<string> = ko.observable(text("KMK008_24") + text("KMK008_44"));
            nameErrorOneMonth: KnockoutObservable<string> = ko.observable(text("KMK008_25") + text("KMK008_42"));
            nameAlarmOneMonth: KnockoutObservable<string> = ko.observable(text("KMK008_25") + text("KMK008_43"));
            nameLimitOneMonth: KnockoutObservable<string> = ko.observable(text("KMK008_25") + text("KMK008_44"));
            nameErrorTwoMonths: KnockoutObservable<string> = ko.observable(text("KMK008_26") + text("KMK008_42"));
            nameAlarmTwoMonths: KnockoutObservable<string> = ko.observable(text("KMK008_26") + text("KMK008_43"));
            nameLimitTwoMonths: KnockoutObservable<string> = ko.observable(text("KMK008_26") + text("KMK008_44"));
            nameErrorThreeMonths: KnockoutObservable<string> = ko.observable(text("KMK008_27") + text("KMK008_42"));
            nameAlarmThreeMonths: KnockoutObservable<string> = ko.observable(text("KMK008_27") + text("KMK008_43"));
            nameLimitThreeMonths: KnockoutObservable<string> = ko.observable(text("KMK008_27") + text("KMK008_44"));
            nameErrorOneYear: KnockoutObservable<string> = ko.observable(text("KMK008_28") + text("KMK008_42"));
            nameAlarmOneYear: KnockoutObservable<string> = ko.observable(text("KMK008_28") + text("KMK008_43"));
            nameLimitOneYear: KnockoutObservable<string> = ko.observable(text("KMK008_28") + text("KMK008_44"));

            constructor(laborSystemAtr: number) {
                let self = this;
                self.laborSystemAtr = laborSystemAtr;
                self.isUpdate = true;
                self.timeOfWorkPlace = ko.observable(new TimeOfWorkPlaceModel(null));
                self.currentWorkplaceName = ko.observable("");
                self.textOvertimeName = ko.observable(nts.uk.resource.getText("KMK008_12", ['#KMK008_8', '#Com_Workplace']));

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
                    selectedWorkplaceId: self.selectedWorkplaceId,
                    baseDate: self.baseDate,
                    selectType: 1,
                    isShowSelectButton: true,
                    isDialog: false,
                    alreadySettingList: self.alreadySettingList,
                    systemType:2
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
                    self.textOvertimeName(nts.uk.resource.getText("KMK008_12", ['{#KMK008_8}', '{#Com_Workplace}']));
                } else {
                    self.textOvertimeName(nts.uk.resource.getText("KMK008_12", ['{#KMK008_9}', '{#Com_Workplace}']));
                }
                self.selectedWorkplaceId('');
                $('#tree-grid-screen-e').ntsTreeComponent(self.treeGrid).done(function() {
                    self.getalreadySettingList();
                    // self.workplaceGridList($('#tree-grid-screen-e').getDataList());
                    if (self.workplaceGridList().length > 0) {
                        self.selectedWorkplaceId(self.workplaceGridList()[0].workplaceId);
                    }
                    dfd.resolve();
                });
                return dfd.promise();
            }

            getalreadySettingList() {
                let self = this;
                self.alreadySettingList([]);
                new service.Service().getList(self.laborSystemAtr).done(data => {
                    if (data.workPlaceIds.length > 0) {
                        self.alreadySettingList(_.map(data.workPlaceIds, item => { return new UnitAlreadySettingModel(item.toString(), true); }));
                        _.defer(() => self.workplaceGridList($('#tree-grid-screen-e').getDataList()));
                    }
                     if (self.workplaceGridList().length > 0) {
                            self.selectedWorkplaceId(self.workplaceGridList()[0].workplaceId);
                        }
                })
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
                let indexCodealreadySetting = _.findIndex(self.alreadySettingList(), item => { return item.workplaceId == self.selectedWorkplaceId() });
                let timeOfWorkPlaceNew = new UpdateInsertTimeOfWorkPlaceModel(self.timeOfWorkPlace(), self.laborSystemAtr, self.selectedWorkplaceId());
                nts.uk.ui.block.invisible();
                if (indexCodealreadySetting != -1) {
                    new service.Service().updateAgreementTimeOfWorkplace(timeOfWorkPlaceNew).done(listError => {
                        if (listError.length > 0) {
                            let errorCode = _.split(listError[0], ',');
                            let periodName = nts.uk.resource.getText(errorCode[1]);
                            let param1 = "期間: "+nts.uk.resource.getText(errorCode[1]) +"<br>"+nts.uk.resource.getText(errorCode[2]);
                            nts.uk.ui.dialog.alertError({ messageId: errorCode[0], messageParams: [param1, nts.uk.resource.getText(errorCode[3])] });
                            nts.uk.ui.block.clear();
                            return;
                        }
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                        self.getDetail(self.selectedWorkplaceId());
                        nts.uk.ui.block.clear();
                    });
                    return;
                }
                new service.Service().addAgreementTimeOfWorkPlace(timeOfWorkPlaceNew).done(listError => {
                    if (listError.length > 0) {
                        let errorCode = _.split(listError[0], ',');
                      let  periodName = nts.uk.resource.getText(errorCode[1]);
                            let param1 = "期間: "+nts.uk.resource.getText(errorCode[1]) +"<br>"+nts.uk.resource.getText(errorCode[2]);
                            nts.uk.ui.dialog.alertError({ messageId: errorCode[0], messageParams: [param1, nts.uk.resource.getText(errorCode[3])] });
                        nts.uk.ui.block.clear();
                        return;
                    }
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    self.getalreadySettingList();
                    self.getDetail(self.selectedWorkplaceId());
                });
                nts.uk.ui.block.clear();
            }

            removeDataWorkPlace() {
                let self = this;
                nts.uk.ui.dialog.confirm(nts.uk.resource.getMessage("Msg_18", []))
                    .ifYes(() => {
                        let deleteModel = new DeleteTimeOfWorkPlaceModel(self.laborSystemAtr, self.selectedWorkplaceId());
                        new service.Service().removeAgreementTimeOfWorkplace(deleteModel).done(function() {
                            self.getalreadySettingList();
                            self.getDetail(self.selectedWorkplaceId());
                            self.isRemove(false);
                        });
                        nts.uk.ui.dialog.info(nts.uk.resource.getMessage("Msg_16", []));
                    });
                nts.uk.ui.block.clear();

            }

            getDetail(workPlaceIds: string) {
                let self = this;
                new service.Service().getDetail(self.laborSystemAtr, workPlaceIds).done(data => {
                    self.timeOfWorkPlace(new TimeOfWorkPlaceModel(data));
                }).fail(error => {

                });
            }

        }

        export class TimeOfWorkPlaceModel {
            alarmWeek: KnockoutObservable<string> = ko.observable(null);
            errorWeek: KnockoutObservable<string> = ko.observable(null);
            limitWeek: KnockoutObservable<string> = ko.observable(null);
            alarmTwoWeeks: KnockoutObservable<string> = ko.observable(null);
            errorTwoWeeks: KnockoutObservable<string> = ko.observable(null);
            limitTwoWeeks: KnockoutObservable<string> = ko.observable(null);
            alarmFourWeeks: KnockoutObservable<string> = ko.observable(null);
            errorFourWeeks: KnockoutObservable<string> = ko.observable(null);
            limitFourWeeks: KnockoutObservable<string> = ko.observable(null);
            alarmOneMonth: KnockoutObservable<string> = ko.observable(null);
            errorOneMonth: KnockoutObservable<string> = ko.observable(null);
            limitOneMonth: KnockoutObservable<string> = ko.observable(null);
            alarmTwoMonths: KnockoutObservable<string> = ko.observable(null);
            errorTwoMonths: KnockoutObservable<string> = ko.observable(null);
            limitTwoMonths: KnockoutObservable<string> = ko.observable(null);
            alarmThreeMonths: KnockoutObservable<string> = ko.observable(null);
            errorThreeMonths: KnockoutObservable<string> = ko.observable(null);
            limitThreeMonths: KnockoutObservable<string> = ko.observable(null);
            alarmOneYear: KnockoutObservable<string> = ko.observable(null);
            errorOneYear: KnockoutObservable<string> = ko.observable(null);
            limitOneYear: KnockoutObservable<string> = ko.observable(null);
            constructor(data: any) {
                let self = this;
                if (!data) return;
                self.alarmWeek(data.alarmWeek);
                self.errorWeek(data.errorWeek);
                self.limitWeek(data.limitWeek);
                self.alarmTwoWeeks(data.alarmTwoWeeks);
                self.errorTwoWeeks(data.errorTwoWeeks);
                self.limitTwoWeeks(data.limitTwoWeeks);
                self.alarmFourWeeks(data.alarmFourWeeks);
                self.errorFourWeeks(data.errorFourWeeks);
                self.limitFourWeeks(data.limitFourWeeks);
                self.alarmOneMonth(data.alarmOneMonth);
                self.errorOneMonth(data.errorOneMonth);
                self.limitOneMonth(data.limitOneMonth);
                self.alarmTwoMonths(data.alarmTwoMonths);
                self.errorTwoMonths(data.errorTwoMonths);
                self.limitTwoMonths(data.limitTwoMonths);
                self.alarmThreeMonths(data.alarmThreeMonths);
                self.errorThreeMonths(data.errorThreeMonths);
                self.limitThreeMonths(data.limitThreeMonths);
                self.alarmOneYear(data.alarmOneYear);
                self.errorOneYear(data.errorOneYear);
                self.limitOneYear(data.limitOneYear);
            }
        }

        export class UpdateInsertTimeOfWorkPlaceModel {
            laborSystemAtr: number = 0;
            workPlaceId: string = "";
            alarmWeek: number = 0;
            errorWeek: number = 0;
            limitWeek: number = 0;
            alarmTwoWeeks: number = 0;
            errorTwoWeeks: number = 0;
            limitTwoWeeks: number = 0;
            alarmFourWeeks: number = 0;
            errorFourWeeks: number = 0;
            limitFourWeeks: number = 0;
            alarmOneMonth: number = 0;
            errorOneMonth: number = 0;
            limitOneMonth: number = 0;
            alarmTwoMonths: number = 0;
            errorTwoMonths: number = 0;
            limitTwoMonths: number = 0;
            alarmThreeMonths: number = 0;
            errorThreeMonths: number = 0;
            limitThreeMonths: number = 0;
            alarmOneYear: number = 0;
            errorOneYear: number = 0;
            limitOneYear: number = 0;
            constructor(data: TimeOfWorkPlaceModel, laborSystemAtr: number, workPlaceId: string) {
                let self = this;
                self.laborSystemAtr = laborSystemAtr;
                self.workPlaceId = workPlaceId;
                if (!data) return;
                self.alarmWeek = +data.alarmWeek() || 0;
                self.errorWeek = +data.errorWeek() || 0;
                self.limitWeek = +data.limitWeek() || 0;
                self.alarmTwoWeeks = +data.alarmTwoWeeks() || 0;
                self.errorTwoWeeks = +data.errorTwoWeeks() || 0;
                self.limitTwoWeeks = +data.limitTwoWeeks() || 0;
                self.alarmFourWeeks = +data.alarmFourWeeks() || 0;
                self.errorFourWeeks = +data.errorFourWeeks() || 0;
                self.limitFourWeeks = +data.limitFourWeeks() || 0;
                self.alarmOneMonth = +data.alarmOneMonth() || 0;
                self.errorOneMonth = +data.errorOneMonth() || 0;
                self.limitOneMonth = +data.limitOneMonth() || 0;
                self.alarmTwoMonths = +data.alarmTwoMonths() || 0;
                self.errorTwoMonths = +data.errorTwoMonths() || 0;
                self.limitTwoMonths = +data.limitTwoMonths() || 0;
                self.alarmThreeMonths = +data.alarmThreeMonths() || 0;
                self.errorThreeMonths = +data.errorThreeMonths() || 0;
                self.limitThreeMonths = +data.limitThreeMonths() || 0;
                self.alarmOneYear = +data.alarmOneYear() || 0;
                self.errorOneYear = +data.errorOneYear() || 0;
                self.limitOneYear = +data.limitOneYear() || 0;
            }
        }

        export class DeleteTimeOfWorkPlaceModel {
            laborSystemAtr: number = 0;
            workPlaceId: string;
            constructor(laborSystemAtr: number, workPlaceId: string) {
                let self = this;
                self.laborSystemAtr = laborSystemAtr;
                self.workPlaceId = workPlaceId;
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
