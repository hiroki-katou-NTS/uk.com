module nts.uk.pr.view.qmm016.a {
    export module viewmodel {
        export class ScreenModel extends base.simplehistory.viewmodel.ScreenBaseModel<model.WageTable, model.WageTableHistory> {
            // For UI Tab.
            tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedTab: KnockoutObservable<any>; 

            wageTableList: any;
            monthRange: KnockoutComputed<string>;

            // Head part view model.
            head: WageTableHeadViewModel;

            // FIXED PART
            generalTableTypes: KnockoutObservableArray<model.DemensionElementCountType>;
            specialTableTypes: KnockoutObservableArray<model.DemensionElementCountType>;
            
            constructor() {
                super({
                    functionName: '賃金テープル',
                    service: service.instance,
                    removeMasterOnLastHistoryRemove: true});
                var self = this;

                // Head view model.
                self.head = new WageTableHeadViewModel();

                // Tabs.
                self.selectedTab = ko.observable('tab-1');
                self.tabs = ko.observableArray([
                    { id: 'tab-1', title: '基本情報', content: '#tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: '賃金テーブルの情報', content: '#tab-content-2', enable: ko.observable(true), visible: ko.observable(true) }
                ]);

                // General table type init.
                self.generalTableTypes = ko.observableArray(model.normalDemension);
                self.specialTableTypes = ko.observableArray(model.specialDemension);
            }
            
             /**
             * Load wage table detail.
             */
            onSelectHistory(id: string): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                service.instance.loadHistoryByUuid(id).done(model => {
                    self.head.resetBy(model.head);
                })
                dfd.resolve();
                return dfd.promise();
            }
            
            /**
             * Create or Update UnitPriceHistory.
             */
            onSave(): JQueryPromise<string> {
                var self = this;
                var dfd = $.Deferred<string>();
                dfd.resolve();
                return dfd.promise();
            }

            /**
             * Clear all input and switch to new mode.
             */
            onRegistNew(): void {
                var self = this;
                $('.save-error').ntsError('clear');
                self.head.reset();
            }
        }

        /**
         * Wage table head dto.
         */
        export class WageTableHeadViewModel {
            /** The code. */
            code: KnockoutObservable<string>;
            
            /** The name. */
            name: KnockoutObservable<string>;
            
            /** The demension set. */
            demensionSet: KnockoutObservable<number>;
            
            /** The memo. */
            memo: KnockoutObservable<string>;
            
            /**
             * Const.
             */
            constructor() {
                var self = this;
                self.code = ko.observable(undefined);
                self.name = ko.observable(undefined);
                self.demensionSet = ko.observable(undefined);
                self.memo = ko.observable(undefined);
                self.reset(); 
            }
            
            /**
             * Reset.
             */
            reset(): void {
                var self = this;
                self.code('');
                self.name('');
                self.demensionSet(model.allDemension[0].code);
                self.memo('');
            }

            /**
             * Void
             */
            resetBy(head: model.WageTableHeadDto): void {
                var self = this;
                self.code(head.code);
                self.name(head.name);
                self.demensionSet(head.demensionSet);
                self.memo(head.memo);
            }
        }
    }
}