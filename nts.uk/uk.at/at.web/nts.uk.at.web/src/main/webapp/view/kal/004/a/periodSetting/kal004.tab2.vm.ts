module nts.uk.at.view.kal004.tab2.viewModel {
    import getText = nts.uk.resource.getText;
    import share = nts.uk.at.view.kal004.share.model;

    export class ScreenModel {
        listCheckConditionCode: KnockoutObservableArray<share.CheckConditionCommand> = ko.observableArray([]);
        listCheckCondition: KnockoutObservableArray<share.CheckConditionCommand> = ko.observableArray([]);
        listStorageCheckCondition: KnockoutObservableArray<share.CheckConditionCommand> = ko.observableArray([]);
        ListView: KnockoutObservableArray<ModelCheckConditonCode>;
        
        constructor() {
            var self = this;
            
            self.listCheckConditionCode.subscribe((newList) => {
                self.changeCheckCondition(newList);
            });
            self.ListView = ko.observableArray([]);
            $("#fixed-table").ntsFixedTable({ height: 320, width: 830 });
        }

        private changeCheckCondition(listCheckCode: Array<share.CheckConditionCommand>): void {
            var self = this;
            var listConverToview = [];
            var listCheckConditionDto = [];
            if(listCheckCode.length==0){
                self.ListView.removeAll();
                self.listCheckCondition.removeAll();
                return;
            }else if(self.listStorageCheckCondition().length == 0){
                _.forEach(listCheckCode, (category: share.CheckConditionCommand) =>{
                    let checkCondition = new share.CheckConditionCommand(category.alarmCategory, category.checkConditionCodes, category.extractionPeriodDaily);
                    listCheckConditionDto.push(checkCondition);
                    listConverToview.push(new ModelCheckConditonCode(checkCondition));
                    self.listStorageCheckCondition.push(category);
                });
            }else{
                _.forEach(listCheckCode, (category: share.CheckConditionCommand) =>{
                    let checkCondition = new share.CheckConditionCommand(category.alarmCategory, category.checkConditionCodes, category.extractionPeriodDaily);
                    var check = _.find(self.listStorageCheckCondition(), ['alarmCategory', category.alarmCategory]);
                    if(nts.uk.util.isNullOrUndefined(check)){
                        listCheckConditionDto.push(checkCondition); 
                        listConverToview.push(new ModelCheckConditonCode(checkCondition));
                        self.listStorageCheckCondition.push(category);
                    }else{
                        let checkConditionUpDate = new share.CheckConditionCommand(category.alarmCategory, category.checkConditionCodes, check.extractionPeriodDaily);
                        listCheckConditionDto.push(checkConditionUpDate);    
                        listConverToview.push(new ModelCheckConditonCode(checkConditionUpDate));   
                    }
                });        
            }
            self.listCheckCondition(listCheckConditionDto);
            self.ListView(listConverToview);
        }

        private openDialog(ModelCheckConditonCode): void {
            var self = this;
            var param = ModelCheckConditonCode.extractionPeriodDaily;
            var categoryId = ModelCheckConditonCode.categoryId;
            if(categoryId == 5 || categoryId == 13){
                var ExtractionDailyDto = {
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
                }
                nts.uk.ui.windows.setShared("extractionDailyDto", ExtractionDailyDto);
                nts.uk.ui.windows.setShared("categoryId", categoryId);
                nts.uk.ui.windows.setShared("categoryName", ModelCheckConditonCode.categoryName);
                nts.uk.ui.windows.sub.modal("../b/index.xhtml").onClosed(() => {
                    let data = nts.uk.ui.windows.getShared("extractionDaily");
                    
                    if(!nts.uk.util.isNullOrUndefined(data)){
                        self.changeExtractionDaily(data,categoryId);
                    }
                });
            }
        }
        private changeExtractionDaily(extractionDailyDto: share.ExtractionDailyDto, categoryId: number): void {
            var self = this;
            var oldItem = _.find(self.listStorageCheckCondition(), ['alarmCategory', categoryId]);
            var newItem = oldItem.setExtractPeriod(new share.ExtractionPeriodDailyCommand(extractionDailyDto));
            self.listStorageCheckCondition.replace(oldItem,newItem);
            var listCheckConditionDto: Array<share.CheckConditionCommand> = [];
            _.forEach(self.listCheckCondition(), (category: share.CheckConditionCommand) =>{
                if(category.alarmCategory == categoryId){
                    category.setExtractPeriod(new share.ExtractionPeriodDailyCommand(extractionDailyDto));
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
        ListSpecifiedMonth:Array<any> = __viewContext.enums.SpecifiedMonth;
        PreviousClassification: Array<any> = __viewContext.enums.PreviousClassification
        ListAlarmCategory:Array<any> = __viewContext.enums.AlarmCategory;
        
        extractionPeriodDaily: share.ExtractionPeriodDailyCommand;
        constructor(CheckCondition: share.CheckConditionCommand) {
            var self =  this;
            this.categoryId = CheckCondition.alarmCategory;
            this.categoryName = _.find(self.ListAlarmCategory, ['value', CheckCondition.alarmCategory]).name;
            var str, end;
            if(CheckCondition.extractionPeriodDaily.strSpecify == 0){ 
                str = getText('KAL004_32') + CheckCondition.extractionPeriodDaily.strDay + getText('KAL004_34') + _.find(self.PreviousClassification, ['value', CheckCondition.extractionPeriodDaily.strPreviousDay]).name;
            }else{
                let strMonth = _.find(self.ListSpecifiedMonth, ['value' , CheckCondition.extractionPeriodDaily.strMonth]);
                str = strMonth.name + getText('KAL004_37');
            }
            if(CheckCondition.extractionPeriodDaily.endSpecify == 0){ 
                end = getText('KAL004_32') + CheckCondition.extractionPeriodDaily.endDay + getText('KAL004_34') + _.find(self.PreviousClassification, ['value', CheckCondition.extractionPeriodDaily.endPreviousDay]).name;
            }else{
                let endMonth = _.find(self.ListSpecifiedMonth, ['value' , CheckCondition.extractionPeriodDaily.endMonth]);
                end = endMonth.name + getText('KAL004_43');
            }  
            this.extractionPeriod = str + ' ' + getText('KAL004_30') +  ' ' +end;
            this.extractionPeriodDaily = CheckCondition.extractionPeriodDaily;
        }
    }
}