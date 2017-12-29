module a7 {
    import EnumWorkForm = nts.uk.at.view.kmk003.a.viewmodel.EnumWorkForm;
    import SettingMethod = nts.uk.at.view.kmk003.a.viewmodel.SettingMethod;
    import WorkTimeSettingEnumDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeSettingEnumDto;
    import MainSettingModel = nts.uk.at.view.kmk003.a.viewmodel.MainSettingModel;
    import TabMode = nts.uk.at.view.kmk003.a.viewmodel.TabMode;
    import TimeRangeModel = nts.uk.at.view.kmk003.a.viewmodel.common.TimeRangeModel;
    import DeductionTimeModel = nts.uk.at.view.kmk003.a.viewmodel.common.DeductionTimeModel;
    class ScreenModel {

        tabMode: KnockoutObservable<TabMode>;

        mainSettingModel: MainSettingModel;
        fixTableOptionForFixedOrDiffTime: any;
        fixTableOptionForFlowOrFlexUse: any;
        fixTableOptionForFlowOrFlexNotUse1: any;
        fixTableOptionForFlowOrFlexNotUse2: any;

        dataSourceForFixedOrDiffTime: KnockoutObservableArray<TimeRangeModel>;
        dataSourceForFlowOrFlexUse: KnockoutObservableArray<any>;
        dataSourceForFlowOrFlexNotUse1: KnockoutObservableArray<any>;
        dataSourceForFlowOrFlexNotUse2: KnockoutObservableArray<any>;

        isFlowOrFlex: KnockoutObservable<boolean>;

        useFixedRestTimeOptions: KnockoutObservableArray<any>;
        useFixedRestTime: KnockoutObservable<number>;

        isFixedRestTime: KnockoutObservable<boolean>;
        isFlexOrFlowNotUse: KnockoutObservable<boolean>;
        /**
        * Constructor.
        */
        constructor(tabMode: any, enumSetting: WorkTimeSettingEnumDto, mainSettingModel: MainSettingModel, isLoading :KnockoutObservable<any>) {
            let self = this;

            self.tabMode = tabMode;

            //main model
            self.mainSettingModel = mainSettingModel;
            self.loadDataToScreen();

            /////////////
            self.fixTableOptionForFixedOrDiffTime = {
                maxRow: 10,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSourceForFixedOrDiffTime,
                isMultipleSelect: true,
                columns: self.columnSetting(),
                tabindex: 92
            };

            /////////////
            self.dataSourceForFlowOrFlexUse = ko.observableArray([]);
            self.fixTableOptionForFlowOrFlexUse = {
                maxRow: 10,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSourceForFlowOrFlexUse,
                isMultipleSelect: true,
                columns: self.columnSetting(),
                tabindex: 94
            };

            /////////////
            self.dataSourceForFlowOrFlexNotUse1 = ko.observableArray([]);
            self.fixTableOptionForFlowOrFlexNotUse1 = {
                maxRow: 5,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSourceForFlowOrFlexNotUse1,
                isMultipleSelect: true,
                columns: self.columnSetting2(),
                tabindex: 95
            };

            /////////////
            self.dataSourceForFlowOrFlexNotUse2 = ko.observableArray([]);
            self.dataSourceForFlowOrFlexNotUse2.subscribe((newList) => {
                console.log(newList);
            });
            self.fixTableOptionForFlowOrFlexNotUse2 = {
                maxRow: 7,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSourceForFlowOrFlexNotUse2,
                isMultipleSelect: true,
                columns: self.columnSetting2(),
                tabindex: 97
            };

            //subscrible worktime ssettingmethod
            self.isFlowOrFlex = ko.computed(function() {

                return mainSettingModel.workTimeSetting.isFlex() || mainSettingModel.workTimeSetting.isFlow();
            });

            self.useFixedRestTimeOptions = ko.observableArray([
                { code: UseDivision.USE, name: nts.uk.resource.getText("KMK003_142") },
                { code: UseDivision.NOTUSE, name: nts.uk.resource.getText("KMK003_143") },
            ]);
            self.useFixedRestTime = ko.observable(1);

            self.isFixedRestTime = ko.computed(function() {
                return self.useFixedRestTime() == UseDivision.USE && self.isFlowOrFlex();
            });
            self.isFlexOrFlowNotUse = ko.computed(function() {
                return self.useFixedRestTime() == UseDivision.NOTUSE && self.isFlowOrFlex();
            });
            //load data to screen 
            self.setDataFlexOrFlowToModel();
            isLoading.subscribe((isDone: boolean) => {
                if (isDone) {
                    self.updateDataModel();
                }
            });
        }

        private loadDataToScreen() {
            let self = this;

            //to screen 
            self.dataSourceForFixedOrDiffTime = self.mainSettingModel.fixedWorkSetting.offdayWorkTimezone.restTimezone.listTimeRange;
            //when UI change
            //self.dataSourceForFixedOrDiffTime.subscribe((v) => self.mainSettingModel.fixedWorkSetting.offdayWorkTimezone.restTimezone.lstTimezone(self.mainSettingModel.fixedWorkSetting.offdayWorkTimezone.restTimezone.fromListTimeRange(v)));

            //TODO not care difftime or flow

        }
        private setDataFlexOrFlowToModel() {
            let self = this;
            //休憩時間を固定にする=true
            if (self.mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.fixRestTime) {

                self.dataSourceForFlowOrFlexUse.subscribe((newDataSource: any) => {
//                    alert();
//                    console.log(newDataSource);
                    let listDeductionTimeModel: DeductionTimeModel[] = [];
                    for(let item of newDataSource)
                    {
                        let deduct = new DeductionTimeModel();
                        deduct.start(item.column1().startTime);
                        deduct.end(item.column1().endTime);
                        listDeductionTimeModel.push(deduct);
                    }
                    self.mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.fixedRestTimezone.timezones(listDeductionTimeModel);
                });

                //                    = self.mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.fixedRestTimezone.timezones;
//                self.dataSourceForFlowOrFlexUse.subscribe((v) => self.mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.fixedRestTimezone.timezones(v));
            }
            else {//休憩時間を固定にする=false

                self.dataSourceForFlowOrFlexNotUse1 = self.mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.flowRestTimezone.flowRestSets;
                self.dataSourceForFlowOrFlexNotUse1.subscribe((v) => self.mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.flowRestTimezone.flowRestSets(v));

                //                self.dataSourceForFlowOrFlexNotUse2 = self.mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.flowRestTimezone.hereAfterRestSet;
                //                self.dataSourceForFlowOrFlexNotUse2.subscribe((v) => self.mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.flowResteAfterRestSet(v));

            }
        }
        
        private updateDataModel()
        {
            let self = this;
            if(self.mainSettingModel.workTimeSetting.isFlex())
            {
                let data: any = [];
                
                for (let item of self.mainSettingModel.flexWorkSetting.offdayWorkTime.restTimezone.fixedRestTimezone.timezones()) {
                    data.push({
                        column1: ko.observable({ startTime: item.start(), endTime: item.end() })
                    });
                    }
                self.dataSourceForFlowOrFlexUse(data);
            }
        }
        
        private columnSetting(): Array<any> {
            let self = this;
            return [
                {
                    headerText: nts.uk.resource.getText("KMK003_54"), key: "column1", defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }), width: 243, template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`}
            ];
        }

        private columnSetting2(): Array<any> {
            let self = this;
            return [
                {
                    headerText: nts.uk.resource.getText("KMK003_174"), key: "column1", defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }), width: 243, template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`},
                {
                    headerText: nts.uk.resource.getText("KMK003_176"), key: "column2", defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }), width: 243, template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`}
            ];
        }
    }
    
    export enum UseDivision {
        NOTUSE,
        USE
    }
    class KMK003A7BindingHandler implements KnockoutBindingHandler {
        /**
         * Constructor.
         */
        constructor() {
        }

        private getData() {
            let self = this;
            //            service.findWorkTimeSetByCode()    
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any): void {
            var webserviceLocator = nts.uk.request.location.siteRoot
                .mergeRelativePath(nts.uk.request.WEB_APP_NAME["at"] + '/')
                .mergeRelativePath('/view/kmk/003/a7/index.xhtml').serialize();

            //get data
            let input = valueAccessor();
            let tabMode = input.tabMode;
            let enumSetting = input.enum;
            let mainSettingModel = input.mainSettingModel;
            let isLoading = input.isLoading;
            
            var screenModel = new ScreenModel(tabMode, enumSetting, mainSettingModel,isLoading);
            $(element).load(webserviceLocator, function() {
                ko.cleanNode($(element)[0]);
                ko.applyBindingsToDescendants(screenModel, $(element)[0]);
            });
        }

    }
     ko.bindingHandlers['ntsKMK003A7'] = new KMK003A7BindingHandler();
}