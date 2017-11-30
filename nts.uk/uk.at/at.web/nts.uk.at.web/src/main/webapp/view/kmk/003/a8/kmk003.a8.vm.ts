module a8 {
    class ScreenModel {

        fixTableOptionOneDay: any;
        fixTableOptionMorning: any;
        fixTableOptionAfternoon: any;
        selectedSettingMethod: KnockoutObservable<string>;
        selectedTab: KnockoutObservable<string>;
        tabArrangements: KnockoutObservableArray<any>;
        roundingSettings: KnockoutObservableArray<any>;
        roundingOutSettings: KnockoutObservableArray<any>;
        selectedRoundingSetting: KnockoutObservable<any>;
        selectedRoundingOutSetting: KnockoutObservable<any>;
        timeoutSettingModel: GroubRoundingModel;
        deductionTimeSettingModel: GroubRoundingModel;
        publicSettingModel: GroubRoundingModel;
        data: KnockoutObservable<any>;
        /**
        * Constructor.
        */
        constructor(data: any) {
            let self = this;
            self.data = ko.observable(data());
            self.roundingSettings = ko.observableArray([
                { code: 1, name: nts.uk.resource.getText("KMK003_198") },
                { code: 2, name: nts.uk.resource.getText("KMK003_199") }
            ]);
            self.roundingOutSettings = ko.observableArray([
                { code: 1, name: nts.uk.resource.getText("KMK003_198") },
                { code: 2, name: nts.uk.resource.getText("KMK003_199") }
            ]);
            self.timeoutSettingModel = new GroubRoundingModel();
            self.deductionTimeSettingModel = new GroubRoundingModel();
            self.publicSettingModel = new GroubRoundingModel();
            self.selectedRoundingSetting = ko.observable(1);
            self.selectedRoundingOutSetting = ko.observable(1);
            self.tabArrangements = ko.observableArray([
                { id: 'tabArrangement-1', title: nts.uk.resource.getText("KMK003_194"), content: '.tabArrangement-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tabArrangement-2', title: nts.uk.resource.getText("KMK003_195"), content: '.tabArrangement-2', enable: ko.observable(true), visible: ko.observable(true) }
            ]);
            self.selectedTab = ko.observable('tabArrangement-1');
            
             // Create Customs handle For event rened nts grid.
            
        }
        
        /**
         * function update view by selected mode flow
         */
       /* public updateByFlowMode(): void {
            var self = this;
            (<any>ko.bindingHandlers).rended = {
                update: function(element: any, valueAccessor: any, allBindings: KnockoutAllBindingsAccessor) {
                    
                }
            }
        }*/
        //bind data to screen items
        public bindDataToScreen(data: any) {
            let self = this;
        }
    }
    
    export class RoundingModel {
        roundingBreakTimes: KnockoutObservableArray<any>;
        roundingTimes: KnockoutObservableArray<any>;
        roundingProcesses: KnockoutObservableArray<any>;
        selectedRoundingBreakTime: KnockoutObservable<any>;
        selectedRoundingTime: KnockoutObservable<any>;
        selectedRoundingProcess: KnockoutObservable<any>;
        constructor() {
            var self = this;
            self.roundingBreakTimes = ko.observableArray([
                { code: 1, name: nts.uk.resource.getText("KMK003_87") },
                { code: 2, name: nts.uk.resource.getText("KMK003_88") }
            ]);
            self.roundingTimes = ko.observableArray([
                { code: 1, name: nts.uk.resource.getText("KMK003_87") },
                { code: 2, name: nts.uk.resource.getText("KMK003_88") }
            ]);
            self.roundingProcesses = ko.observableArray([
                { code: 1, name: nts.uk.resource.getText("KMK003_91") },
                { code: 2, name: nts.uk.resource.getText("KMK003_92") }
            ]);
            self.selectedRoundingBreakTime = ko.observable(1);
            self.selectedRoundingTime = ko.observable(1);
            self.selectedRoundingProcess = ko.observable(1);
        }
    }
    
    export class GroubRoundingModel {
        workingHoursModel: RoundingModel;
        overtimeHoursModel: RoundingModel;
        restPeriodHoursModel: RoundingModel;
        constructor() {
            var self = this;
            self.workingHoursModel = new RoundingModel();
            self.overtimeHoursModel = new RoundingModel();
            self.restPeriodHoursModel = new RoundingModel();
        }
    }
    export class Item {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    class KMK003A8BindingHandler implements KnockoutBindingHandler {
        /**
         * Constructor.
         */
        constructor() {
        }

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
        }

        private getData() {
            let self = this;
            // service.findWorkTimeSetByCode()
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            var webserviceLocator = nts.uk.request.location.siteRoot
                .mergeRelativePath(nts.uk.request.WEB_APP_NAME["at"] + '/')
                .mergeRelativePath('/view/kmk/003/a8/index.xhtml').serialize();
            //get data
            let input = valueAccessor();
            let data = input.data;

            let screenModel = new ScreenModel(data);
            $(element).load(webserviceLocator, function() {
                ko.cleanNode($(element)[0]);
                ko.applyBindingsToDescendants(screenModel, $(element)[0]);
                screenModel.bindDataToScreen(data);
            });
        }

    }
    ko.bindingHandlers['ntsKMK003A8'] = new KMK003A8BindingHandler();

}
