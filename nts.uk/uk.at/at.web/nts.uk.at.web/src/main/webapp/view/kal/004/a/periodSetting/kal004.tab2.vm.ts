module nts.uk.at.view.kal004.tab2.viewModel {
    import getText = nts.uk.resource.getText;
    import share = nts.uk.at.view.kal004.share.model;

    export class ScreenModel {
        listCheckConditionCode: KnockoutObservableArray<share.CheckConditionCommand> = ko.observableArray([]);
        listCheckCondition: KnockoutObservableArray<share.CheckConditionCommand> = ko.observableArray([]);
        listStorageCheckCondition: KnockoutObservableArray<share.CheckConditionCommand> = ko.observableArray([]);
        ListView: KnockoutObservableArray<ModelCheckConditonCode>;
        isCreateMode: KnockoutObservable<boolean>;
        constructor() {
            var self = this;

            self.listCheckConditionCode.subscribe((newList) => {
                self.changeCheckCondition(newList);
            });
            self.ListView = ko.observableArray([]);
            $("#fixed-table").ntsFixedTable({ height: 320, width: 830 });
            self.isCreateMode = ko.observable(true);
        }


        private changeCheckCondition(listCheckCode: Array<share.CheckConditionCommand>): void {
            var self = this;
            var listConverToview = [];
            var listCheckConditionDto = [];
            if (listCheckCode.length == 0) {
                self.ListView.removeAll();
                self.listCheckCondition.removeAll();
                return;
            } else if (self.listStorageCheckCondition().length == 0) {
                _.forEach(listCheckCode, (category: share.CheckConditionCommand) => {
                    let checkCondition = new share.CheckConditionCommand(category.alarmCategory, category.checkConditionCodes, category.extractionPeriodDaily, category.extractionPeriodUnit, category.listExtractionMonthly);
                    listCheckConditionDto.push(checkCondition);
                    listConverToview.push(new ModelCheckConditonCode(checkCondition));
                    self.listStorageCheckCondition.push(category);
                });
            } else {
                _.forEach(listCheckCode, (category: share.CheckConditionCommand) => {
                    let checkCondition = new share.CheckConditionCommand(category.alarmCategory, category.checkConditionCodes, category.extractionPeriodDaily, category.extractionPeriodUnit, category.listExtractionMonthly);
                    var check = _.find(self.listStorageCheckCondition(), ['alarmCategory', category.alarmCategory]);
                    if (nts.uk.util.isNullOrUndefined(check)) {
                        listCheckConditionDto.push(checkCondition);
                        listConverToview.push(new ModelCheckConditonCode(checkCondition));
                        self.listStorageCheckCondition.push(category);
                    } else {
                        let checkConditionUpDate = new share.CheckConditionCommand(category.alarmCategory, category.checkConditionCodes, check.extractionPeriodDaily, category.extractionPeriodUnit, category.listExtractionMonthly);
                        listCheckConditionDto.push(checkConditionUpDate);
                        listConverToview.push(new ModelCheckConditonCode(checkConditionUpDate));
                    }
                });
            }
            self.listCheckCondition(listCheckConditionDto);
            self.ListView(_.orderBy(listConverToview, 'categoryId', 'asc'));
        }
        private openDialog(modelCheck: ModelCheckConditonCode): void {
            var self = this;

            var categoryId = modelCheck.categoryId;
            if (categoryId == 7) {
                
                let paramMonthly = _.find(modelCheck.listExtractionMonthly, ['unit', 3]);
                let extractionMonthDto = {
                    extractionId: paramMonthly.extractionId,
                    extractionRange: paramMonthly.extractionRange,
                    strMonth: paramMonthly.strMonth,
                    endMonth: paramMonthly.endMonth
                };                
                nts.uk.ui.windows.setShared("extractionMonthDto", extractionMonthDto);
                nts.uk.ui.windows.setShared("categoryId", categoryId);
                nts.uk.ui.windows.setShared("categoryName", modelCheck.categoryName);
                nts.uk.ui.windows.sub.modal("../d/index.xhtml").onClosed(() => {
                    let data = nts.uk.ui.windows.getShared("extractionMonthly");
                    if (!nts.uk.util.isNullOrUndefined(data)) {
                        let extractMonthlyCommand = {
                                 extractionId: "",
                                 extractionRange: 0,
                                 unit: 3,       
                                 strSpecify : 1,        
                                 yearType: 2,
                                 specifyMonth: 0,
                                 strMonth: data.strMonth,
                                 strCurrentMonth: 12,
                                 strPreviousAtr: 0,
                                 endSpecify: 0,
                                 extractPeriod: 12,
                                 endMonth: data.endMonth,
                                 endCurrentMonth: 0,
                                 endPreviousAtr: 0     
                }
                        self.changeExtractionMonthly(extractMonthlyCommand, categoryId);
                    }
                });
            }
            else if (categoryId == 2) {
                let paramUnit = modelCheck.extractionPeriodUnit.segmentationOfCycle;
                nts.uk.ui.windows.setShared("segmentationOfCycle", paramUnit);
                nts.uk.ui.windows.setShared("categoryName", modelCheck.categoryName);
                nts.uk.ui.windows.sub.modal("../f/index.xhtml").onClosed(() => {
                    let data = nts.uk.ui.windows.getShared("segmentationOfCycle");
                    if (!nts.uk.util.isNullOrUndefined(data) && !nts.uk.util.isNullOrUndefined(data.decisionEnum)) {
                        self.extractionPeriodUnit(categoryId, modelCheck.extractionPeriodUnit.extractionId, data.decisionEnum);
                    }
                });
            } else if (categoryId == 5 || categoryId == 13) {
                let paramDaily = modelCheck.extractionPeriodDaily;
                let extractionDailyDto = {
                    extractionId: paramDaily.extractionId,
                    extractionRange: paramDaily.extractionRange,
                    strSpecify: paramDaily.strSpecify,
                    strPreviousDay: paramDaily.strPreviousDay,
                    strMakeToDay: paramDaily.strMakeToDay,
                    strDay: paramDaily.strDay,
                    strPreviousMonth: paramDaily.strPreviousMonth,
                    strCurrentMonth: paramDaily.strCurrentMonth,
                    strMonth: paramDaily.strMonth,
                    endSpecify: paramDaily.endSpecify,
                    endPreviousDay: paramDaily.endPreviousDay,
                    endMakeToDay: paramDaily.endMakeToDay,
                    endDay: paramDaily.endDay,
                    endPreviousMonth: paramDaily.endPreviousMonth,
                    endCurrentMonth: paramDaily.endCurrentMonth,
                    endMonth: paramDaily.endMonth
                };
                nts.uk.ui.windows.setShared("extractionDailyDto", extractionDailyDto);
                nts.uk.ui.windows.setShared("categoryId", categoryId);
                nts.uk.ui.windows.setShared("categoryName", modelCheck.categoryName);
                nts.uk.ui.windows.sub.modal("../b/index.xhtml").onClosed(() => {
                    let data = nts.uk.ui.windows.getShared("extractionDaily");
                    if (!nts.uk.util.isNullOrUndefined(data)) {
                        self.changeExtractionDaily(data, categoryId);
                    }
                });
            } else if (categoryId == 12) {

                let param36 = modelCheck.extractionPeriodDaily;
                let extractionDailyDto = {
                    extractionId: param36.extractionId,
                    extractionRange: param36.extractionRange,
                    strSpecify: param36.strSpecify,
                    strPreviousDay: param36.strPreviousDay,
                    strMakeToDay: param36.strMakeToDay,
                    strDay: param36.strDay,
                    strPreviousMonth: param36.strPreviousMonth,
                    strCurrentMonth: param36.strCurrentMonth,
                    strMonth: param36.strMonth,
                    endSpecify: param36.endSpecify,
                    endPreviousDay: param36.endPreviousDay,
                    endMakeToDay: param36.endMakeToDay,
                    endDay: param36.endDay,
                    endPreviousMonth: param36.endPreviousMonth,
                    endCurrentMonth: param36.endCurrentMonth,
                    endMonth: param36.endMonth
                };

                nts.uk.ui.windows.setShared("categoryId", categoryId);
                nts.uk.ui.windows.setShared("categoryName", modelCheck.categoryName);
                nts.uk.ui.windows.setShared("extractionDailyDto", extractionDailyDto);
                nts.uk.ui.windows.sub.modal("../g/index.xhtml").onClosed(() => {
                    let data = nts.uk.ui.windows.getShared("extractionDaily");
                    if (!nts.uk.util.isNullOrUndefined(data)) {

                    }
                });
            }
        }

        private changeExtractionMonthly(extractionMonthlyDto: share.ExtractionPeriodMonthlyDto, categoryId: number): void {
            let self = this;
            let oldItem = _.find(self.listStorageCheckCondition(), ['alarmCategory', categoryId]);
            let monthlyDto :Array<share.ExtractionPeriodMonthlyCommand>  = oldItem.listExtractionMonthly;
            _.remove(monthlyDto, (item) =>{return item.unit==3 });
            monthlyDto.push(new share.ExtractionPeriodMonthlyCommand(extractionMonthlyDto));
            
            let listCheckConditionDto: Array<share.CheckConditionCommand> = [];
            _.forEach(self.listCheckCondition(), (category: share.CheckConditionCommand) => {
                if (category.alarmCategory == categoryId) {
                    
                    _.remove(category.listExtractionMonthly, (item) =>{return item.unit==3 });
                    category.listExtractionMonthly.push(new share.ExtractionPeriodMonthlyCommand(extractionMonthlyDto));                 
                }
                listCheckConditionDto.push(category);
            });
            self.changeCheckCondition(listCheckConditionDto);
        }        
        
        private changeExtractionDaily(extractionDailyDto: share.ExtractionDailyDto, categoryId: number): void {
            var self = this;
            var oldItem = _.find(self.listStorageCheckCondition(), ['alarmCategory', categoryId]);
            oldItem.setExtractPeriod(new share.ExtractionPeriodDailyCommand(extractionDailyDto));
            var listCheckConditionDto: Array<share.CheckConditionCommand> = [];
            _.forEach(self.listCheckCondition(), (category: share.CheckConditionCommand) => {
                if (category.alarmCategory == categoryId) {
                    category.setExtractPeriod(new share.ExtractionPeriodDailyCommand(extractionDailyDto));
                }
                listCheckConditionDto.push(category);
            });
            self.changeCheckCondition(listCheckConditionDto);
        }

        private extractionPeriodUnit(categoryId: number, extractionId: string, segmentationOfCycle: number): void {
            var self = this;
            var oldItem = _.find(self.listStorageCheckCondition(), ['alarmCategory', categoryId]);
            oldItem.setExtractUnit(new share.PeriodUnitCommand({ extractionId: extractionId, extractionRange: 3, segmentationOfCycle: segmentationOfCycle }));
            var listCheckConditionDto: Array<share.CheckConditionCommand> = [];
            _.forEach(self.listCheckCondition(), (category: share.CheckConditionCommand) => {
                if (category.alarmCategory == categoryId) {
                    category.setExtractUnit(new share.PeriodUnitCommand({ extractionId: extractionId, extractionRange: 3, segmentationOfCycle: segmentationOfCycle }));
                }
                listCheckConditionDto.push(category);
            });
            self.changeCheckCondition(listCheckConditionDto);
        }

    }
    export class ModelCheckConditonCode {
        categoryId: number;
        categoryName: string;
        extractionPeriod: string;
        ListSpecifiedMonth: Array<any> = __viewContext.enums.SpecifiedMonth;
        PreviousClassification: Array<any> = __viewContext.enums.PreviousClassification
        ListAlarmCategory: Array<any> = __viewContext.enums.AlarmCategory;
        SegmentationOfCycle: Array<any> = __viewContext.enums.SegmentationOfCycle;

        extractionPeriodDaily: share.ExtractionPeriodDailyCommand;
        extractionPeriodUnit: share.PeriodUnitCommand;
        listExtractionMonthly : Array<share.ExtractionPeriodMonthlyCommand>;
        
        constructor(CheckCondition: share.CheckConditionCommand) {
            var self = this;
            this.categoryId = CheckCondition.alarmCategory;
            this.categoryName = _.find(self.ListAlarmCategory, ['value', CheckCondition.alarmCategory]).name;
            if (nts.uk.util.isNullOrUndefined(CheckCondition.extractionPeriodDaily) && CheckCondition.alarmCategory == 2) {
                this.extractionPeriod = _.find(self.SegmentationOfCycle, ['value', CheckCondition.extractionPeriodUnit.segmentationOfCycle]).name;
            } else if (CheckCondition.alarmCategory == 5 || CheckCondition.alarmCategory == 13) {

                var str, end;
                if (CheckCondition.extractionPeriodDaily.strSpecify == 0) {
                    str = getText('KAL004_32') + CheckCondition.extractionPeriodDaily.strDay + getText('KAL004_34') + _.find(self.PreviousClassification, ['value', CheckCondition.extractionPeriodDaily.strPreviousDay]).name;
                } else {
                    let strMonth = _.find(self.ListSpecifiedMonth, ['value', CheckCondition.extractionPeriodDaily.strMonth]);
                    str = strMonth.name + getText('KAL004_37');
                }
                if (CheckCondition.extractionPeriodDaily.endSpecify == 0) {
                    end = getText('KAL004_32') + CheckCondition.extractionPeriodDaily.endDay + getText('KAL004_34') + _.find(self.PreviousClassification, ['value', CheckCondition.extractionPeriodDaily.endPreviousDay]).name;
                } else {
                    let endMonth = _.find(self.ListSpecifiedMonth, ['value', CheckCondition.extractionPeriodDaily.endMonth]);
                    end = endMonth.name + getText('KAL004_43');
                }
                this.extractionPeriod = str + ' ' + getText('KAL004_30') + ' ' + end;
            } else {
                this.extractionPeriod = "";
            }

            this.extractionPeriodDaily = CheckCondition.extractionPeriodDaily;
            this.extractionPeriodUnit = CheckCondition.extractionPeriodUnit;
            this.listExtractionMonthly = CheckCondition.listExtractionMonthly;
        }
    }
}