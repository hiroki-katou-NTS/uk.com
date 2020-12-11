module nts.uk.at.kal011.a {

    import common = nts.uk.at.kal014.common;

    const API = {
        INIT: "at/function/alarm-workplace/alarm-list/init",
        GET_CHECK_CONDITION: "at/function/alarm-workplace/alarm-list/get-check-conditions",
        //TODO need delete
        EXPORT_EXCEL: "at/function/alarm-workplace/alarm-list/export-alarm-data",
    }

    @bean()
    export class Kal011AViewModel extends ko.ViewModel {
        employmentCode: string;
        processingYm: number;
        alarmPatterns: KnockoutObservableArray<AlarmPattern> = ko.observableArray([]);
        alarmPatternCode: KnockoutObservable<string> = ko.observable(null);
        conditions: KnockoutObservableArray<CheckCondition> = ko.observableArray([]);

        isCheckAll: KnockoutObservable<boolean> = ko.observable(false);
        isCheckAll_Temp: boolean = false;

        // work place list
        workplaceIds: KnockoutObservable<any> = ko.observableArray([]);
        baseDate: KnockoutObservable<Date> = ko.observable(this.$date.today());
        alreadySettingList: KnockoutObservableArray<any> = ko.observableArray([]);
        treeGrid: any;

        constructor() {
            super();
            const vm = this;

            vm.treeGrid = {
                isMultipleUse: false,
                isMultiSelect: true,
                startMode: 0, // WORKPLACE
                selectedId: vm.workplaceIds,
                baseDate: vm.baseDate,
                selectType: 3, // SELECT_FIRST_ITEM
                isShowSelectButton: true,
                isDialog: false,
                alreadySettingList: vm.alreadySettingList,
                maxRows: 12,
                tabindex: -1,
                systemType: 2 // EMPLOYMENT
            };
        }

        created() {
            const vm = this;
            vm.$blockui("invisible");
            $('#tree-grid').ntsTreeComponent(vm.treeGrid);
            $("#fixed-table").ntsFixedTable({ width: 435 });

            vm.init().done(() => {
                vm.alarmPatternCode.subscribe((code) => {
                    if (!code) return;
                    vm.$blockui("invisible");
                    vm.getCheckCondition()
                        .fail((err: any) => {
                            vm.$dialog.error(err);
                        }).always(() => {
                            vm.$blockui("clear");
                        });
                });
                vm.alarmPatternCode.valueHasMutated();
            }).fail((err: any) => {
                vm.$dialog.error(err);
            }).always(() => {
                vm.$blockui("clear");
            });

            _.extend(window, { vm });
        }

        init(): JQueryPromise<any> {
            const vm = this;
            let dfd = $.Deferred();

            vm.$ajax(API.INIT).done((res: IInitActiveAlarmList) => {
                if (res) {
                    if (!res.processingYm){
                        dfd.reject({messageId: "Msg_1143"});
                        return;
                    }
                    vm.employmentCode = res.employmentCode;
                    vm.processingYm = res.processingYm;

                    let parterns: Array<AlarmPattern> = [];
                    _.each(res.alarmPatterns, (item: IAlarmPatternSettingWorkPlace) => {
                        parterns.push(new AlarmPattern(item.alarmPatternCode, item.alarmPatternName));
                    })
                    vm.alarmPatterns(parterns);
                }
                dfd.resolve();
            }).fail((err: any) => {
                dfd.reject(err)
            })

            return dfd.promise();
        }

        getCheckCondition(): JQueryPromise<any> {
            const vm = this;
            let dfd = $.Deferred();

            vm.$ajax(API.GET_CHECK_CONDITION + "/" + vm.alarmPatternCode() + "/" + vm.processingYm).done((conditions: Array<ICheckCondition>) => {
                console.log(conditions)
                let conds: Array<CheckCondition> = [];
                if (conditions) {
                    _.each(conditions, (condition: ICheckCondition) => {
                        conds.push(new CheckCondition(condition));
                    })
                }
                vm.conditions(conds);
                dfd.resolve();
            }).fail((err: any) => {
                dfd.reject(err);
            })

            return dfd.promise();
        }

        checkBoxAllOrNot(condition: CheckCondition) {
            const vm = this;
            condition.isChecked(!condition.isChecked());
            let checkeds = _.filter(vm.conditions(), (condition: CheckCondition) => condition.isChecked());
            vm.isCheckAll_Temp = checkeds.length == vm.conditions().length
            vm.isCheckAll(vm.isCheckAll_Temp);
        }

        checkAll() {
            const vm = this;
            vm.isCheckAll_Temp = !vm.isCheckAll_Temp;
            vm.isCheckAll(vm.isCheckAll_Temp);
            _.each(vm.conditions(), (condition: CheckCondition) => {
                condition.isChecked(vm.isCheckAll_Temp);
            })
        }

        exportAlarmList() {
            const vm = this;

            // アラームリスト出力開始前チェック
            vm.$validate("#B3_2").then((valid: boolean) => {
                if (!valid) return;
                if (_.isEmpty(vm.workplaceIds())) {
                    vm.$dialog.error({ messageId: "Msg_834" }); //TODO Q&A 37827
                    return
                }
                let conditionSelecteds = _.filter(vm.conditions(), (condition: CheckCondition) => condition.isChecked());
                if (_.isEmpty(conditionSelecteds)) {
                    vm.$dialog.error({ messageId: "Msg_1167" });
                    return
                }
                let conditionSelectedInvalids = _.filter(conditionSelecteds, (condition: CheckCondition) => !condition.isValid());
                if (!_.isEmpty(conditionSelectedInvalids)) {
                    vm.$dialog.error({ messageId: "Msg_1168" });
                    return
                }

                let categoryPeriods = _.map(conditionSelecteds, (condition: CheckCondition) => {
                    return {
                        category: condition.category,
                        start: condition.dateRange().startDate.toISOString(),
                        emd: condition.dateRange().endDate.toISOString()
                    }
                })

                let modalData: any = {
                    alarmPatternCode: vm.alarmPatternCode(),
                    workplaceIds: vm.workplaceIds(),
                    categoryPeriods: categoryPeriods
                }
                vm.$window.storage('KAL011DModalData', modalData).done(() => {
                    vm.$window.modal('/view/kal/011/d/index.xhtml')
                        .then((result: any) => {
                            vm.openKal011BMOdal();
                        });
                });
            })
        }

        /*
         * This function is responsible to open Kal011B
         *
         * @return void
         * */
        openKal011BMOdal() {
            const vm = this;
            vm.$window.storage('KAL011DModalData').done((data) => {
                if (!data.isClose) {
                    vm.$window.storage('KAL011BModalData', data).done(() => {
                        vm.$window.modal('/view/kal/011/b/index.xhtml')
                            .then((result: any) => {
                                $('#grid > *').attr("tabindex", "-1");
                            })
                    });
                }
            });
        }

        //TODO need delete
        exportExcel() {
            const vm = this;
            vm.$blockui("grayout");
            vm.$ajax(API.EXPORT_EXCEL, {
                alarmCode: "alarmCode1",
                alarmName: "alarmName",
                data: [
                    {
                        alarmValueMessage: "alarmValueMessage1",
                        alarmValueDate: "alarmValueDate1",
                        alarmItemName: "alarmItemName1",
                        categoryName: "categoryName1",
                        checkTargetValue: "checkTargetValue1",
                        category: "category1",
                        startTime: "2020/11/04",
                        comment: "comment1",
                        workplaceId: "workplaceId1",
                        workplaceCode: "workplaceCode1",
                        workplaceName: "workplaceName1"
                    },
                    {
                        alarmValueMessage: "alarmValueMessage2",
                        alarmValueDate: "alarmValueDate2",
                        alarmItemName: "alarmItemName2",
                        categoryName: "categoryName2",
                        checkTargetValue: "checkTargetValue2",
                        category: "category2",
                        startTime: "2020/11/05",
                        comment: "comment2",
                        workplaceId: "workplaceId2",
                        workplaceCode: "workplaceCode2",
                        workplaceName: "workplaceName2"
                    }
                ]
            }).then(() => vm.$blockui("clear"))
        }
    }

    class AlarmPattern {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    interface IInitActiveAlarmList {
        employmentCode: string;
        alarmPatterns: Array<IAlarmPatternSettingWorkPlace>;
        processingYm: number
    }

    interface IAlarmPatternSettingWorkPlace {
        alarmPatternCode: string;
        alarmPatternName: string;
    }

    interface ICheckCondition {
        category: number;
        categoryName: string;
        checkConditionLis: Array<string>;
        startDate: any;
        endDate: any
    }

    class CheckCondition {
        isChecked: KnockoutObservable<boolean>;
        category: common.WORKPLACE_CATAGORY;
        categoryName: any;
        dateRange: KnockoutObservable<DateRangePickerModel>;
        type: KnockoutObservable<string>;

        constructor(data: ICheckCondition) {
            this.isChecked = ko.observable(false);
            this.category = data.category;
            this.categoryName = data.categoryName;
            this.dateRange = ko.observable(new DateRangePickerModel(data.startDate, data.endDate));
            this.type = ko.observable(this.getType());
        }

        getType(): string {
            if (this.category == common.WORKPLACE_CATAGORY.MONTHLY) {
                return 'yearmonth';
            }
            return 'date';
        }

        isValid() {
            if (!this.dateRange().startDate || !this.dateRange().endDate) {
                return false;
            }
            return true;
        }
    }

    class DateRangePickerModel {
        startDate: any;
        endDate: any;

        constructor(startDate: any, endDate: any) {
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public static isSamePeriod(a: DateRangePickerModel, b: DateRangePickerModel): boolean {
            return a.startDate === b.startDate && a.endDate === b.endDate
        }
    }
}