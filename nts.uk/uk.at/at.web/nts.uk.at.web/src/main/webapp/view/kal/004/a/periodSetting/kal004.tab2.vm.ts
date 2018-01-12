module nts.uk.at.view.kal004.tab2.viewModel {
    import share = nts.uk.at.view.kal004.share.model;

    export class ScreenModel {
        listCheckConditionCode: KnockoutObservableArray<share.ModelCheckConditonCode> = ko.observableArray([]);
        listCheckCondition: KnockoutObservableArray<share.CheckCondition> = ko.observableArray([]);

        constructor() {
            var self = this;
            self.listCheckConditionCode.subscribe((newList) => {
                self.changeCheckCondition(newList);
            });
        }

        private changeCheckCondition(listCheckCode: Array<share.ModelCheckConditonCode>): void {
            var self = this;
            var uniqueCategory: Array<share.ModelCheckConditonCode> = _.uniqBy(listCheckCode, "category");
            var listCurrentCheckCondition: Array<share.CheckConditionDto> = __viewContext["viewmodel"].currentAlarm.checkConList;
            
            var listCheckCondition = [];
            _.forEach(uniqueCategory, (category: share.ModelCheckConditonCode) => {
                let matchCheckCondition = _.find(listCurrentCheckCondition, (item) => {
                    return item.alarmCategory == category.category;
                });
                let checkCondition = new share.CheckCondition(category.category, category.categoryName, matchCheckCondition.extractionDailyDto);
                listCheckCondition.push(checkCondition);
            });
            self.listCheckCondition(listCheckCondition);
        }

        private openDialog(): void {
            nts.uk.ui.windows.sub.modal("../b/index.xhtml").onClosed(() => {
                console.log("success!");
            });
        }
    }
}