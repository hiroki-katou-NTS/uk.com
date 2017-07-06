module nts.uk.at.view.kmk008.e {
    export module viewmodel {

        export class ScreenModel {
            timeOfWorkPlace: KnockoutObservable<TimeOfWorkPlaceModel>;
            isUpdate: boolean;
            laborSystemAtr: number = 0;
            currentWorkplaceName: KnockoutObservable<string>;

            selectedWorkplaceId: KnockoutObservable<string>;
            selectedRowWorkplace: RowSelection;
            baseDate: KnockoutObservable<Date>;
            alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
            treeGrid: any;
            workplaceGridList: KnockoutObservableArray<UnitModel>;

            constructor(laborSystemAtr: number) {
                let self = this;
                self.laborSystemAtr = laborSystemAtr;
                self.isUpdate = true;
                self.timeOfWorkPlace = ko.observable(new TimeOfWorkPlaceModel(null));
                self.currentWorkplaceName = ko.observable("");

                self.workplaceGridList = ko.observableArray([]);
                self.baseDate = ko.observable(new Date());
                self.selectedWorkplaceId = ko.observable("");
                self.alreadySettingList = ko.observableArray([]);

                self.treeGrid = {
                    isShowAlreadySet: true,
                    isMultiSelect: false,
                    treeType: 1,
                    selectedWorkplaceId: self.selectedWorkplaceId,
                    baseDate: self.baseDate,
                    selectType: 1,
                    isShowSelectButton: true,
                    isDialog: false,
                    alreadySettingList: self.alreadySettingList
                };
                
                self.selectedWorkplaceId.subscribe(newValue => {
                    if (nts.uk.text.isNullOrEmpty(newValue)) return;
                    let WorkplaceSelect = self.findUnitModelByWorkplaceId(self.workplaceGridList(), newValue);
                    if (WorkplaceSelect) { self.currentWorkplaceName(WorkplaceSelect.name); }
                    self.getDetail(newValue);                   
                });
                self.startPage();
            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                $('#tree-grid-screen-e').ntsTreeComponent(self.treeGrid).done(function() {
                    self.workplaceGridList($('#tree-grid-screen-e').getDataList());
                    if (self.workplaceGridList().length > 0) {
                        self.selectedWorkplaceId(self.workplaceGridList()[0].workplaceId);
                    }
                    self.getalreadySettingList();
                    dfd.resolve();
                });
                return dfd.promise();
            }

            getalreadySettingList() {
                let self = this;
                let dfd = $.Deferred();
                self.alreadySettingList([]);
                new service.Service().getList(self.laborSystemAtr).done(data => {
                    if (data.workPlaceIds.length > 0) {
                        self.alreadySettingList(_.map(data.workPlaceIds, item => { return new UnitAlreadySettingModel(item.toString()) }));
                    }
                    dfd.resolve();
                })
                return dfd.promise();
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

                if (indexCodealreadySetting != -1) {
                    new service.Service().updateAgreementTimeOfWorkplace(timeOfWorkPlaceNew).done(listError => {
                        if (listError.length > 0) {
                            alert("Error");
                            return;
                        }
                        self.getDetail(self.selectedWorkplaceId());
                    });
                    return;
                }
                new service.Service().addAgreementTimeOfWorkPlace(timeOfWorkPlaceNew).done(listError => {
                    if (listError.length > 0) {
                        alert("Error");
                        return;
                    }
                    self.getalreadySettingList();
                    self.getDetail(self.selectedWorkplaceId());
                });
            }

            removeDataWorkPlace() {
                let self = this;
                let deleteModel = new DeleteTimeOfWorkPlaceModel(self.laborSystemAtr, self.selectedWorkplaceId());
                new service.Service().removeAgreementTimeOfWorkplace(deleteModel).done(function() {
                    self.getalreadySettingList();
                    self.getDetail(self.selectedWorkplaceId());
                });
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
            settingType: number = 2;
            constructor(workplaceId: string) {
                this.workplaceId = workplaceId;
            }
        }

    }
}
