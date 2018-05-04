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
                    let checkCondition = new share.CheckConditionCommand(category.alarmCategory, category.checkConditionCodes, category.extractionPeriodDaily, category.extractionPeriodUnit);
                    listCheckConditionDto.push(checkCondition);
                    listConverToview.push(new ModelCheckConditonCode(checkCondition));
                    self.listStorageCheckCondition.push(category);
                });
            } else {
                _.forEach(listCheckCode, (category: share.CheckConditionCommand) => {
                    let checkCondition = new share.CheckConditionCommand(category.alarmCategory, category.checkConditionCodes, category.extractionPeriodDaily, category.extractionPeriodUnit);
                    var check = _.find(self.listStorageCheckCondition(), ['alarmCategory', category.alarmCategory]);
                    if (nts.uk.util.isNullOrUndefined(check)) {
                        listCheckConditionDto.push(checkCondition);
                        listConverToview.push(new ModelCheckConditonCode(checkCondition));
                        self.listStorageCheckCondition.push(category);
                    } else {
                        let checkConditionUpDate = new share.CheckConditionCommand(category.alarmCategory, category.checkConditionCodes, check.extractionPeriodDaily, category.extractionPeriodUnit);
                        listCheckConditionDto.push(checkConditionUpDate);
                        listConverToview.push(new ModelCheckConditonCode(checkConditionUpDate));
                    }
                });
            }
            self.listCheckCondition(listCheckConditionDto);
            self.ListView(_.orderBy(listConverToview, 'categoryId', 'asc'));
        }

        private openDialog(ModelCheckConditonCode): void {
            var self = this;
            var param = ModelCheckConditonCode.extractionPeriodDaily;
            var extractionDailyDto = {
                extractionId: param.extractionId,
                extractionRange: param.extractionRange,
                strPreviousMonth: param.strPreviousMonth,
                strCurrentMonth: param.strCurrentMonth,
                strMonth: param.strMonth,
                endMakeToDay: param.endMakeToDay,
                endPreviousMonth: param.endPreviousMonth,
                endCurrentMonth: param.endCurrentMonth,
                endMonth: param.endMonth
            };
            nts.uk.ui.windows.setShared("extractionDailyDto", extractionDailyDto);
            nts.uk.ui.windows.setShared("categoryId", categoryId);
            nts.uk.ui.windows.setShared("categoryName", ModelCheckConditonCode.categoryName);
            var categoryId = ModelCheckConditonCode.categoryId;
            if (categoryId == 0) {
                nts.uk.ui.windows.sub.modal("../d/index.xhtml").onClosed(() => {
                    let data = nts.uk.ui.windows.getShared("extractionDaily");
                    if (!nts.uk.util.isNullOrUndefined(data)) {
                        self.changeExtractionDaily(data, categoryId);
                    }
                });
            }
            else if (categoryId == 2) {
                var param = ModelCheckConditonCode.extractionPeriodUnit.segmentationOfCycle;
                nts.uk.ui.windows.setShared("segmentationOfCycle", param);
                nts.uk.ui.windows.setShared("categoryName", ModelCheckConditonCode.categoryName);
                nts.uk.ui.windows.sub.modal("../f/index.xhtml").onClosed(() => {
                    let data = nts.uk.ui.windows.getShared("segmentationOfCycle");
                    if (!nts.uk.util.isNullOrUndefined(data) && !nts.uk.util.isNullOrUndefined(data.decisionEnum)) {
                        self.extractionPeriodUnit(categoryId, ModelCheckConditonCode.extractionPeriodUnit.extractionId, data.decisionEnum);
                    }
                });
            } else if (categoryId == 5 || categoryId == 13) {
                var param = ModelCheckConditonCode.extractionPeriodDaily;
                var extractionDailyDto = {
                    extractionId: param.extractionId,
                    extractionRange: param.extractionRange,
                    strSpecify: param.strSpecify,
                    strPreviousDay: param.strPreviousDay,
                    strMakeToDay: param.strMakeToDay,
                    strDay: param.strDay,
                    strPreviousMonth: param.strPreviousMonth,
                    strCurrentMonth: param.strCurrentMonth,
                    strMonth: param.strMonth,
                    endSpecify: param.endSpecify,
                    endPreviousDay: param.endPreviousDay,
                    endMakeToDay: param.endMakeToDay,
                    endDay: param.endDay,
                    endPreviousMonth: param.endPreviousMonth,
                    endCurrentMonth: param.endCurrentMonth,
                    endMonth: param.endMonth
                };
                nts.uk.ui.windows.setShared("extractionDailyDto", extractionDailyDto);
                nts.uk.ui.windows.setShared("categoryId", categoryId);
                nts.uk.ui.windows.setShared("categoryName", ModelCheckConditonCode.categoryName);
                nts.uk.ui.windows.sub.modal("../b/index.xhtml").onClosed(() => {
                    let data = nts.uk.ui.windows.getShared("extractionDaily");
                    if (!nts.uk.util.isNullOrUndefined(data)) {
                        self.changeExtractionDaily(data, categoryId);
                    }
                });
            }else if(categoryId ==12){
                
                let param = ModelCheckConditonCode.extractionPeriodDaily;
                let extractionDailyDto = {
                    extractionId: param.extractionId,
                    extractionRange: param.extractionRange,
                    strSpecify: param.strSpecify,
                    strPreviousDay: param.strPreviousDay,
                    strMakeToDay: param.strMakeToDay,
                    strDay: param.strDay,
                    strPreviousMonth: param.strPreviousMonth,
                    strCurrentMonth: param.strCurrentMonth,
                    strMonth: param.strMonth,
                    endSpecify: param.endSpecify,
                    endPreviousDay: param.endPreviousDay,
                    endMakeToDay: param.endMakeToDay,
                    endDay: param.endDay,
                    endPreviousMonth: param.endPreviousMonth,
                    endCurrentMonth: param.endCurrentMonth,
                    endMonth: param.endMonth
                };                
                
                nts.uk.ui.windows.setShared("categoryId", categoryId);
                nts.uk.ui.windows.setShared("categoryName", ModelCheckConditonCode.categoryName);
                nts.uk.ui.windows.setShared("extractionDailyDto", extractionDailyDto);
                nts.uk.ui.windows.sub.modal("../g/index.xhtml").onClosed(() => {
                    let data = nts.uk.ui.windows.getShared("extractionDaily");
                    if(!nts.uk.util.isNullOrUndefined(data)){
                        
                    }
                });                
            }
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
            var PeriodUnitCommand = ({ extractionId: extractionId, extractionRange: 3, segmentationOfCycle: segmentationOfCycle });
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
        constructor(CheckCondition: share.CheckConditionCommand) {
            var self = this;
            this.categoryId = CheckCondition.alarmCategory;
            this.categoryName = _.find(self.ListAlarmCategory, ['value', CheckCondition.alarmCategory]).name;
            if(nts.uk.util.isNullOrUndefined(CheckCondition.extractionPeriodDaily)  && CheckCondition.alarmCategory==2){
                this.extractionPeriod = _.find(self.SegmentationOfCycle, ['value', CheckCondition.extractionPeriodUnit.segmentationOfCycle]).name; 
            }else if(CheckCondition.alarmCategory==5 || CheckCondition.alarmCategory==13){

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
                this.extractionPeriod = str + ' ' + getText('KAL004_30') +  ' ' +end;
            }else{
                this.extractionPeriod="";
            }

            this.extractionPeriodDaily = CheckCondition.extractionPeriodDaily;
            this.extractionPeriodUnit = CheckCondition.extractionPeriodUnit;
        }
    }
}