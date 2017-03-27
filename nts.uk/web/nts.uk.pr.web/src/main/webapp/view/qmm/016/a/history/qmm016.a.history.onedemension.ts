module nts.uk.pr.view.qmm016.a.history {
    /**
     * For one demension view.
     */
    export class OneDemensionViewModel extends base.BaseHistoryViewModel {
        igGrid: any;
        igGridDataSource: KnockoutObservableArray<ItemViewModel>;
        constructor(history: model.WageTableHistoryDto) {
            super('history/onedemension.xhtml', history);
            if (history.cellItems && history.cellItems.length > 0) {
                // Call on refresh.
                // super.refreshElementSettings(history.elements);
            }
            
            // Init grid.
            this.initIgGrid();
        }

        /**
         * Init ig grid.
         */
        initIgGrid(): void {
            var self = this;
            // Init first data.
            self.igGridDataSource = ko.observableArray<ItemViewModel>([]);

            // IgGrid
            self.igGrid = ko.observable({
                dataSource: self.igGridDataSource,
                width: '60%',
                primaryKey: 'uuid',
                height: '250px',
                features: [
                    {
                        name: 'Updating',
                        editMode: 'cell',
                        enableAddRow: false,
                        excelNavigatorMode: false,
                        enableDeleteRow: false,
                        columnSettings: [
                            {
                                columnKey: 'uuid',
                                readOnly: true
                            },
                            {
                                columnKey: 'name',
                                readOnly: true
                            },
                            {
                                columnKey: 'amount',
                                readOnly: false
                            }
                        ],
                    }
                ],
                autoCommit: true,
                columns: [
                    { headerText: 'Element Name', dataType: 'string', key: 'uuid', hidden: true},
                    { headerText: 'Element Name', dataType: 'string', key: 'name', width: '50%'},
                    { headerText: '値', dataType: 'number', key: 'amount', width: '50%', columnCssClass: "halign-right"}
                ]
            });
        }

        /**
         * On refresh element.
         */
        onRefreshElement(): void {
            var self = this;

            // First element.
            var element = self.elementSettings[0];
            var itemVmList = _.map(element.itemList, (item) => {
                return new ItemViewModel(item);
            })
            self.igGridDataSource(itemVmList);
        }

        /**
         * Get setting cell item.
         */
        getCellItem(): Array<model.CellItemDto> {
            return null;
        }

        /**
         * Paste data from excel.
         */
        pasteFromExcel(): void {
            return;
        }
    }

    /**
     * Item view model.
     */
    class ItemViewModel {
        uuid: string;
        name: string;
        amount: KnockoutObservable<number>;

        /**
         * Constructor.
         */
        constructor(item: model.ItemDto) {
            var self = this;
            self.uuid = item.uuid;
            self.name = 'Item Name (load...)';
            self.amount = ko.observable(0);
        }
    }
}