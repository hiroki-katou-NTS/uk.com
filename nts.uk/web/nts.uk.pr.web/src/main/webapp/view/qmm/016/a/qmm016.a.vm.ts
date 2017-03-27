module nts.uk.pr.view.qmm016.a {
    export module viewmodel {
        var elementTypes: Array<model.ElementTypeDto>;
        export class ScreenModel extends base.simplehistory.viewmodel.ScreenBaseModel<model.WageTable, model.WageTableHistory> {
            // For UI Tab.
            tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedTab: KnockoutObservable<any>; 

            wageTableList: any;
            monthRange: KnockoutComputed<string>;

            // Head part viewmodel.
            head: HeadViewModel;

            // History part viewmodel.
            history: HistoryViewModel;

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
                self.head = new HeadViewModel();
                self.history = new HistoryViewModel();

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
             * Start load data.
             */
            start(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                service.instance.loadElementList().done(res => {
                    elementTypes = res;
                    dfd.resolve();
                })
                return dfd.promise();
            }

             /**
             * Load wage table detail.
             */
            onSelectHistory(id: string): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                service.instance.loadHistoryByUuid(id).done(model => {
                    self.head.resetBy(model.head);
                    self.history.resetBy(model.head, model.history);
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
                if (self.isNewMode) {
                    // Reg new.
                    var wagetableDto = self.head.getWageTableDto();
                    service.instance.initWageTable({
                        wageTableHeadDto: wagetableDto,
                        startMonth: self.history.startYearMonth()
                    }).done(res => {
                        dfd.resolve(res.uuid);
                    });
                }
                return dfd.promise();
            }

            /**
             * Clear all input and switch to new mode.
             */
            onRegistNew(): void {
                var self = this;
                self.selectedTab('tab-1');
                self.head.reset();
            }
        }

        /**
         * Wage table head dto.
         */
        export class HeadViewModel {
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
                    self.demensionItemList(self.getDemensionItemListByType(val));
                })
                
                self.isNewMode = ko.observable(true);
                self.reset();
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
                self.demensionItemList(self.getDemensionItemListByType(self.demensionSet()));
                self.memo('');
            }

            /**
             * Wage table dto.
             */
            getWageTableDto(): model.WageTableHeadDto {
                var self = this;
                var dto = <model.WageTableHeadDto>{};
                dto.code = self.code();
                dto.name = self.name();
                dto.memo = self.memo();
                dto.mode = self.demensionSet();
                dto.elements = _.map(self.demensionItemList(), (item) => {
                    var elementDto = <model.ElementDto>{};
                    elementDto.demensionNo = item.demensionNo();
                    elementDto.type = item.elementType();
                    elementDto.referenceCode = item.elementCode();
                    return elementDto;
                });
                return dto;
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
                            let level = new DemensionItemViewModel(3);
                            level.elementType(9);
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
                self.demensionSet(head.mode);
                self.demensionItemList(_.map(head.elements, (item) => {
                    var itemViewModel = new DemensionItemViewModel(item.demensionNo);
                    itemViewModel.resetBy(item);
                    return itemViewModel;
                }));
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
            
            resetBy(element: model.ElementDto) {
                var self = this;
                self.elementType(element.type);
                self.elementCode(element.referenceCode);
                self.elementName('need load later');
            }
        }
        
        /**
         * History model.
         */
        export class HistoryViewModel {
            startYearMonth: KnockoutObservable<number>;
            endYearMonth: KnockoutObservable<number>;
            startYearMonthText: KnockoutObservable<string>;
            endYearMonthText: KnockoutObservable<string>;
            startYearMonthJpText: KnockoutObservable<string>;
            elements: KnockoutObservableArray<HistoryElementSettingViewModel>;
            detailViewModel: history.base.BaseHistoryViewModel;
            history: model.WageTableHistoryDto;

            constructor() {
                var self = this;
                self.startYearMonth = ko.observable(parseInt(nts.uk.time.formatDate(new Date(), 'yyyyMM')));
                self.endYearMonth = ko.observable(99999);
                self.startYearMonthText = ko.computed(() => {
                    return nts.uk.time.formatYearMonth(self.startYearMonth());
                })
                self.endYearMonthText = ko.computed(() => {
                    return nts.uk.time.formatYearMonth(self.endYearMonth());
                })
                self.startYearMonthJpText = ko.computed(() => {
                    return nts.uk.text.format('（{0}）', nts.uk.time.yearmonthInJapanEmpire(self.startYearMonth()).toString());
                })

                // Element info.
                self.elements = ko.observableArray<HistoryElementSettingViewModel>([]);
            }

            /**
             * Reset.
             */
            resetBy(head: model.WageTableHeadDto, history: model.WageTableHistoryDto) {
                var self = this;
                self.history = history;
                self.startYearMonth(history.startMonth);
                self.endYearMonth(history.endMonth);
                var elementSettingViewModel = _.map(history.elements, (el) => {
                    return new HistoryElementSettingViewModel(head, el);
                })
                self.elements(elementSettingViewModel);
                
                // Load detail.
                self.detailViewModel = new qmm016.a.history.OneDemensionViewModel(history);
                $('#detailContainer').load(self.detailViewModel.htmlPath, () => {
                    var element = $('#detailContainer').children().get(0);
                    ko.applyBindings(self.detailViewModel, element);
                })
            }
            
            /**
             * Generate item.
             */
            generateItem(): void {
                var self = this;
                service.instance.genearetItemSetting({
                    historyId: self.history.historyId,
                    settings: self.getElementSettings()})
                .done((res) => {
                    self.detailViewModel.refreshElementSettings(res);
                });
            }

            /**
             * Get element setting dto.
             */
            getElementSettings(): Array<model.ElementSettingDto> {
                var self = this;
                return _.map(self.elements(), (el) => {
                    var dto = <model.ElementSettingDto>{};
                    dto.type = el.elementType();
                    dto.demensionNo = el.demensionNo();
                    dto.upperLimit = el.upperLimit();
                    dto.lowerLimit = el.lowerLimit();
                    dto.interval = el.interval();
                    return dto;
                })
            }
            
            /**
             * Unapply bindings.
             */
            unapplyBindings($node:any, remove:boolean): void {
                // unbind events
                $node.find("*").each(function () {
                    $(this).unbind();
                });
            
                // Remove KO subscriptions and references
                if (remove) {
                    ko.removeNode($node[0]);
                } else {
                    ko.cleanNode($node[0]);
                }
            }
        }
        
        /**
         * Element history setting view model.
         */
        export class HistoryElementSettingViewModel {
            demensionNo: KnockoutObservable<number>;
            elementType: KnockoutObservable<number>;
            elementCode: KnockoutObservable<string>;
            elementName: KnockoutObservable<string>;
            lowerLimit: KnockoutObservable<number>;
            upperLimit: KnockoutObservable<number>;
            interval: KnockoutObservable<number>;
            type: model.ElementTypeDto;

            /**
             * Element setting view model.
             */
            constructor(head: model.WageTableHeadDto, element: model.ElementSettingDto) {
                var self = this;
                self.demensionNo = ko.observable(element.demensionNo);
                self.elementType = ko.observable(0);
                self.elementCode = ko.observable('');
                self.elementName = ko.observable('');
                self.lowerLimit = ko.observable(0);
                self.upperLimit = ko.observable(0);
                self.interval = ko.observable(0);
                self.resetBy(head, element);
            }

            /**
             * Reset by model.
             */
            resetBy(head: model.WageTableHeadDto, element: model.ElementSettingDto) {
                var self = this;
                self.elementType(element.type);

                // Get element from head.
                var elementDto = _.filter(head.elements, (el) => {
                    return el.demensionNo == element.demensionNo;
                })[0];
                self.elementCode(elementDto.referenceCode);
                self.elementName('need load later');

                // Set upper and lower limit.
                self.upperLimit(element.upperLimit);
                self.lowerLimit(element.lowerLimit);
                self.interval(element.interval);
                
                // Set type.
                self.type = _.filter(elementTypes, (type) => {
                    return type.value == self.elementType();
                })[0];
            }
        }
    }
}