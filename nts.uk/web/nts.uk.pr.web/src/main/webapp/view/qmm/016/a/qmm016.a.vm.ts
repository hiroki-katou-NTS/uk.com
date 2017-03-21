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
                    {
                        id: 'tab-1',
                        title: '基本情報',
                        content: '#tab-content-1',
                        enable: ko.observable(true),
                        visible: ko.observable(true)},
                    {
                        id: 'tab-2',
                        title: '賃金テーブルの情報',
                        content: '#tab-content-2',
                        enable: ko.computed(() => {
                            return !self.isNewMode();
                        }),
                        visible: ko.observable(true)}
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
            
            
            demensionType: KnockoutObservable<model.DemensionElementCountType>;

            /** The memo. */
            memo: KnockoutObservable<string>;
            
            /**
             * The demension item list.
             */
            demensionItemList: KnockoutObservableArray<DemensionItemViewModel>;

            // Flag mode.
            isNewMode: KnockoutObservable<boolean>;

            /**
             * Const.
             */
            constructor() {
                var self = this;
                self.code = ko.observable(undefined);
                self.name = ko.observable(undefined);
                self.demensionSet = ko.observable(undefined);
                self.memo = ko.observable(undefined);
                self.demensionType = ko.computed(() => {
                    return model.demensionMap[self.demensionSet()];
                });
                self.demensionItemList = ko.observableArray<DemensionItemViewModel>([]);    
                
                self.demensionSet.subscribe(val => {
                    // Not new mode.
                    if (!self.isNewMode()) {
                        return
                    }

                    // Update.
                    self.demensionItemList(self.getDemensionItemListByType(val));
                })
                
                self.isNewMode = ko.observable(true);
            }
            
            /**
             * Reset.
             */
            reset(): void {
                var self = this;
                self.isNewMode(true);
                self.code('');
                self.name('');
                self.demensionSet(model.allDemension[0].code);
                self.demensionItemList([]);
                self.memo('');
            }

            /**
             * Get default demension item list by default.
             */
            getDemensionItemListByType(typeCode: number) : Array<DemensionItemViewModel> {
                // Regenerate.
                var newDemensionItemList = new Array<DemensionItemViewModel>();
                switch (typeCode) {
                    case 0:
                        newDemensionItemList.push(new DemensionItemViewModel(1));
                        break;
                    case 1:
                        newDemensionItemList.push(new DemensionItemViewModel(1));
                        newDemensionItemList.push(new DemensionItemViewModel(2));
                        break;

                    case 2: 
                        newDemensionItemList.push(new DemensionItemViewModel(1));
                        newDemensionItemList.push(new DemensionItemViewModel(2));
                        newDemensionItemList.push(new DemensionItemViewModel(3));
                        break;

                    // Certificate.
                    case 3:
                        {
                            let cert = new DemensionItemViewModel(1);
                            cert.elementType(6);
                            cert.elementName('資格名称');
                            newDemensionItemList.push(cert);
                        }
                        break;
                        
                    // Attendance.
                    case 4:
                        {
                            let workDay = new DemensionItemViewModel(1);
                            workDay.elementType(7);
                            workDay.elementName('欠勤日数');
                            let late = new DemensionItemViewModel(2);
                            late.elementType(8);
                            late.elementName('遅刻・早退回数');
                            let level = new DemensionItemViewModel(2);
                            level.elementType(8);
                            level.elementName('レベル');
                            newDemensionItemList.push(workDay);
                            newDemensionItemList.push(late);
                            newDemensionItemList.push(level);
                        }
                        break;
                }

                // Ret.
                return newDemensionItemList;
            }

            /**
             * Reset by wage table.
             */
            resetBy(head: model.WageTableHeadDto): void {
                var self = this;
                self.isNewMode(false);
                self.code(head.code);
                self.name(head.name);
                self.demensionSet(head.demensionSet);
                self.memo(head.memo);
            }
            
            /**
             * On select demension btn click.
             */
            onSelectDemensionBtnClick(demension: DemensionItemViewModel) {
                var self = this;
                var dlgOptions: k.viewmodel.Option = {
                    onSelectItem: (data) => {
                        demension.elementType(data.demension.type);
                        demension.elementCode(data.demension.code);
                        demension.elementName(data.demension.name);
                    }
                };
                nts.uk.ui.windows.setShared('options', dlgOptions);
                var ntsDialogOptions = { title: '要素の選択', dialogClass: 'no-close' }; 
                nts.uk.ui.windows.sub.modal('/view/qmm/016/k/index.xhtml', ntsDialogOptions);
            }
        }
        
        /**
         * Wage table demension detail dto.
         */
        export class DemensionItemViewModel {
            demensionNo: KnockoutObservable<number>;
            elementType: KnockoutObservable<number>;
            elementCode: KnockoutObservable<string>;
            elementName: KnockoutObservable<string>;

            /**
             * Demension item view model.
             */
            constructor(demensionNo: number) {
                var self = this;
                self.demensionNo = ko.observable(demensionNo);
                self.elementType = ko.observable(0);
                self.elementCode = ko.observable('');
                self.elementName = ko.observable('');
            }
        }
        
        /**
         * History model.
         */
        export class HistoryViewModel {
            startYearMonth: KnockoutObservable<number>;
            endYearMonth: KnockoutObservable<number>;
            constructor() {
                var self = this;
                self.startYearMonth = ko.observable(parseInt(nts.uk.time.formatDate(new Date(), 'yyyyMM')));
                self.endYearMonth = ko.observable(99999);
            }
        }
    }
}