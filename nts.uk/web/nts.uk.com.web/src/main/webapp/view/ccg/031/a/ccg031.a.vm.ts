module ccg031.a.viewmodel {
    import model = ccg.model;
    import windows = nts.uk.ui.windows;

    export class ScreenModel {
        // Layout Info
        layoutID: string;
        pgType: number;
        // Column & Row
        minRow: number = 4;
        minColumn: number = 6;
        totalRow: KnockoutObservable<number>;
        totalColumn: KnockoutObservable<number>;
        totalRowArray: KnockoutComputed<number[]>;
        totalColumnArray: KnockoutComputed<number[]>;
        // Placement
        placements: KnockoutObservableArray<model.Placement>;

        constructor() {
            var self = this;
            // Column & Row
            this.totalRow = ko.observable(this.minRow);
            this.totalColumn = ko.observable(this.minColumn);
            this.totalRowArray = ko.computed((): Array<number> => {
                return self.numberToArray(self.totalRow());
            });
            this.totalColumnArray = ko.computed((): Array<number> => {
                return self.numberToArray(self.totalColumn());
            });
            // Placement
            this.placements = ko.observableArray([]);
        }

        /** Start Page */
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            var layoutInfo: any = windows.getShared("layout");
            self.layoutID = null;
            self.pgType = null;
            if(layoutInfo !== undefined) {
                self.layoutID = layoutInfo.layoutID;
                self.pgType = layoutInfo.pgType;
            }
            service.active(self.layoutID).done((data: model.LayoutDto) => {
                console.log(data);
                dfd.resolve();
            }).fail((res: any) => {
                dfd.fail();
            });
            return dfd.promise();
        }

        /** Registry Layout */
        registryLayout(): void {
            var self = this;
            service.registry(self.layoutID, self.pgType, self.placements()).done((data: any) => {
                console.log(data);
            }).fail((res) => {
                $("body").ntsError("set", "Server Error");
            });
        }

        /** Close Dialog */
        closeDialog(): void {
            windows.close();
        }

        /** Add Column */
        addColumn(column?: number): void {
            var self = this;
            column = column || 1;
            self.totalColumn(self.totalColumn() + column);
            self.addOverflowClass();
        }

        /** Add Row */
        addRow(row?: number): void {
            var self = this;
            row = row || 1;
            self.totalRow(self.totalRow() + row);
            self.addOverflowClass();
        }

        /** Open Add TopPage-Part Dialog */
        openAddDialog(row: number, column: number, element: HTMLElement): void {
            var self = this;
            $(element).addClass("placeholder");
            windows.setShared("size", { row: row, column: column }, false);
            windows.sub.modal("/view/ccg/031/b/index.xhtml").onClosed(() => {
                let placement: model.Placement = windows.getShared("placement");
                console.log(placement);
                if (placement != undefined) {
                    self.placements.push(placement);
                    self.reorderPartPosition();
                }
                $(element).removeClass("placeholder");
            });
        }

        /** Open Preview Dialog */
        openPreviewDialog(): void {
            
        }

        /** Re-order parts position */
        private reorderPartPosition(): void {
            var self = this;
            // TODO: Move to right


            // Expand row & column
            self.expandLayout();

            // Calculate Position & Size
            _.forEach(self.placements(), (placement) => {
                $("#" + placement.placementID).css({
                    top: ((placement.row - 1) * 150) + ((placement.row - 1) * 10),
                    left: ((placement.column - 1) * 150) + ((placement.column - 1) * 10),
                    width: (placement.width * 150) + ((placement.width - 1) * 10),
                    height: (placement.height * 150) + ((placement.height - 1) * 10)
                });
            });
        }

        /** Expand layout */
        private expandLayout(): void {
            // TODO: Expand row & column
        }

        /** Add overflow class */
        private addOverflowClass(): void {
            if (this.totalRow() > this.minRow)
                $(".placement-container").addClass("overflow-y");
            if (this.totalColumn() > this.minColumn)
                $(".placement-container").addClass("overflow-x");
        }

        /** Convert number to Array<number> */
        private numberToArray(value: number): Array<number> {
            var temp = [];
            var length = _.toInteger(value);
            for (var i = 1; i <= length; i++) {
                temp.push(i);
            }
            return temp;
        }
    }
}