module ccg031.a.viewmodel {
    import model = ccg.model;
    import windows = nts.uk.ui.windows;
    import ntsNumber = nts.uk.ntsNumber;

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
            if (layoutInfo !== undefined) {
                self.layoutID = layoutInfo.layoutID;
                self.pgType = layoutInfo.pgType;
            }
            service.active(self.layoutID).done((data: model.LayoutDto) => {
                console.log(data);
                self.reorderPartPosition();
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
                self.refreshWidget();
            }).fail((res) => {
                $("body").ntsError("set", "Server Error");
            });
        }

        /** Close Dialog */
        closeDialog(): void {
            windows.close();
        }

        /** Add Column button click */
        addColumnButtonClick(): void {
            this.addColumn(1);
            this.setupDropable();
            this.addOverflowClass();
        }

        /** Add Row button click */
        addRowButtonClick(): void {
            this.addRow(1);
            this.setupDropable();
            this.addOverflowClass();
        }

        /** Open Add TopPage-Part Dialog */
        openAddDialog(row: number, column: number, element: HTMLElement): void {
            var self = this;
            $(element).addClass("placeholder");
            windows.setShared("size", { row: row, column: column }, false);
            windows.sub.modal("/view/ccg/031/b/index.xhtml").onClosed(() => {
                let placement: model.Placement = windows.getShared("placement");
                if (placement != undefined) {
                    self.placements.push(placement);
                    self.refreshWidget();
                }
                $(element).removeClass("placeholder");
            });
        }

        /** Open Preview Dialog */
        openPreviewDialog(): void {

        }

        /** Open Preview Dialog */
        removeWidget(placementID: string): void {
            var self = this;
            self.placements.remove((item) => {
                return item.placementID == placementID;
            });
            self.refreshWidget();
        }

        /** Refresh all Widget display & binding */
        private refreshWidget(): void {
            var self = this;
            // Re-order
            self.reorderPartPosition();
            // Drag & Drop
            self.setupDragable();
            self.setupDropable();
        }

        /** Re-order parts position */
        private reorderPartPosition(): void {
            var self = this;

            // Move parts
            self.moveOverlapParts();

            // Expand row & column
            self.autoExpandLayout();

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

        /** Setup Dragable */
        private setupDragable(): void {
            $(".widget-panel").draggable({
                opacity: .8,
                distance: 20,
                containment: ".placement-container",
                cancel: ".panel-content,.remove-button",
                revert: "invalid",
                stack: ".widget-panel",
                iframeFix: true
            });
        }

        /** Setup Dropable */
        private setupDropable(): void {
            var self = this;
            $(".placement-item").droppable({
                tolerance: "pointer",
                classes: { "ui-droppable-hover": "hover" },
                drop: function(event, ui) {
                    let $dropable = $(event.target);
                    let $dragable = ui.draggable;
                    let placement = _.find(self.placements(), ['placementID', $dragable.attr("id")]);
                    placement.row = $dropable.data("row");
                    placement.column = $dropable.data("column");
                    $dragable.position({
                        my: "left top",
                        at: "left top",
                        of: $dropable,
                        collision: "none"
                    });
                    self.refreshWidget();
                }
            });
        }

        /** Move overlap part */
        private moveOverlapParts(): void {

        }

        /** Expand layout */
        private autoExpandLayout(): void {
            var self = this;
            _.forEach(self.placements(), (placement) => {
                let column: number = ntsNumber.getDecimal(placement.column, 0);
                let row: number = ntsNumber.getDecimal(placement.row, 0);
                let width: number = ntsNumber.getDecimal(placement.width, 0);
                let height: number = ntsNumber.getDecimal(placement.height, 0);
                // Column
                let totalColumn = self.totalColumn();
                let expandColumn = (column + width - 1) - totalColumn;
                if (expandColumn > 0) self.addColumn(expandColumn);
                // Row
                let totalRow = self.totalRow();
                let expandRow = (row + height - 1) - totalRow;
                if (expandRow > 0) self.addRow(expandRow);
            });
        }

        /** Add Column */
        private addColumn(column?: number): void {
            column = column || 1;
            this.totalColumn(this.totalColumn() + column);
        }

        /** Add Row */
        private addRow(row?: number): void {
            row = row || 1;
            this.totalRow(this.totalRow() + row);
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