module nts.uk.at.view.kal004.tab2.viewModel {
    import getText = nts.uk.resource.getText;
    import share = nts.uk.at.view.kal004.share.model;

    export class ScreenModel {
        listCheckConditionCode: KnockoutObservableArray<share.ModelCheckConditonCode> = ko.observableArray([]);
        listCheckCondition: KnockoutObservableArray<share.CheckCondition> = ko.observableArray([]);
        ListView: KnockoutObservableArray<ModelCheckConditonCode>;
        
        constructor() {
            var self = this;
            
            self.listCheckConditionCode.subscribe((newList) => {
                self.changeCheckCondition(newList);
            });
            self.ListView = ko.observableArray([]);
          
        }

        private changeCheckCondition(listCheckCode: Array<share.ModelCheckConditonCode>): void {
            var self = this;
            var uniqueCategory: Array<share.ModelCheckConditonCode> = _.uniqBy(listCheckCode, "category");
            var listCurrentCheckCondition: Array<share.CheckConditionDto> = __viewContext["viewmodel"].currentAlarm.checkConList;
            
            var listConver: Array<ModelCheckConditonCode> = [];
            var listCheckCondition = [];
            _.forEach(uniqueCategory, (category: share.ModelCheckConditonCode) => {
                let matchCheckCondition = _.find(listCurrentCheckCondition, (item) => {
                    return item.alarmCategory == category.category;
                });
                if(nts.uk.util.isNullOrUndefined(matchCheckCondition)){
                    matchCheckCondition = { alarmCategory: 0, checkConditionCodes: '', extractionDailyDto: null};    
                }
                let checkCondition = new share.CheckCondition(category.category, category.categoryName, matchCheckCondition.extractionDailyDto);
                listCheckCondition.push(checkCondition);
                listConver.push(new ModelCheckConditonCode(checkCondition));
                
            });
            self.listCheckCondition(listCheckCondition);
            self.ListView(listConver);
        }

        private openDialog(ModelCheckConditonCode): void {
            console.log(ModelCheckConditonCode.extractionDailyDto);
            if(ModelCheckConditonCode.extractionDailyDto.extractionRange() == 0){
                var param = ModelCheckConditonCode.extractionDailyDto;
                var ExtractionDailyDto = {
                    extractionId: param.extractionId(),
                    extractionRange: param.extractionRange(),
                    strSpecify: param.strSpecify(),
                    strPreviousDay: param.strPreviousDay(),
                    strMakeToDay: param.strMakeToDay(),
                    strDay: param.strDay(),
                    strPreviousMonth: param.strPreviousMonth(),
                    strCurrentMonth: param.strCurrentMonth(),
                    strMonth: param.strMonth(),
                    endSpecify: param.endSpecify(),
                    endPreviousDay: param.endPreviousDay(),
                    endMakeToDay: param.endMakeToDay(),
                    endDay: param.endDay(),
                    endPreviousMonth: param.endPreviousMonth(),
                    endCurrentMonth: param.endCurrentMonth(),
                    endMonth: param.endMonth()
                }
                
                nts.uk.ui.windows.setShared("extractionDailyDto", ExtractionDailyDto);
                nts.uk.ui.windows.sub.modal("../b/index.xhtml").onClosed(() => {
                    console.log("success!");
                });
            }
        }
        
    }
    export class ModelCheckConditonCode {
        categoryId: number;
        categoryName: string;
        extractionPeriod: string;
        ListSpecifiedMonth:Array<any>;
        extractionDailyDto:any;
        constructor(CheckCondition: any) {
            var self =  this;
            self.ListSpecifiedMonth = __viewContext.enums.SpecifiedMonth;
            this.categoryId = CheckCondition.alarmCategory;
            this.categoryName = CheckCondition.categoryName;
            var str, end;
            if(CheckCondition.extractionDailyDto.strSpecify() == 0){ 
                str = CheckCondition.extractionDailyDto.strDay() + getText('KAL004_34') + getText('KAL004_35');
            }else{
                let strMonth = _.find(self.ListSpecifiedMonth, ['value' , CheckCondition.extractionDailyDto.strMonth()]);
                str = strMonth.name + getText('KAL004_37');
            }
            if(CheckCondition.extractionDailyDto.endSpecify() == 0){ 
                end = CheckCondition.extractionDailyDto.endDay() + getText('KAL004_34') + getText('KAL004_35');
            }else{
                let endMonth = _.find(self.ListSpecifiedMonth, ['value' , CheckCondition.extractionDailyDto.endMonth()]);
                end = endMonth.name + getText('KAL004_43');
            }  
            this.extractionPeriod = str + ' ' + getText('KAL004_30') +  ' ' +end;
            this.extractionDailyDto = CheckCondition.extractionDailyDto;
        }
    }
}