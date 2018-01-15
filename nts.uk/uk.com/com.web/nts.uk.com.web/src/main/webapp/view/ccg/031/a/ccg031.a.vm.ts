module nts.uk.com.view.ccg031.a.viewmodel {
    import model = nts.uk.com.view.ccg.model;
    import windows = nts.uk.ui.windows;
    import ntsNumber = nts.uk.ntsNumber;
    import dialog = nts.uk.ui.dialog;
    import resource = nts.uk.resource;
    import block = nts.uk.ui.block;
    const MINROW: number = 4;
    const MINCOLUMN: number = 6;
    const ANIMATION_EASETYPE: string = "easeOutQuint";
    const ANIMATION_DURATION: number = 500;

    export class ScreenModel {
        // Layout Info
        parentCode: string;
        layoutID: string;
        pgType: number;
        // Column & Row
        layoutGrid: KnockoutObservable<LayoutGrid>;
        // Placement
        placements: KnockoutObservableArray<model.Placement>;

        constructor() {
            var self = this;
            // Layout Info
            self.parentCode = null;
            self.layoutID = null;
            self.pgType = null;
            // Layout Grid
            self.layoutGrid = ko.observable(new LayoutGrid(MINROW, MINCOLUMN));
            // Placement
            self.placements = ko.observableArray([]);
        }

        /** Start Page */
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            block.invisible();
            var layoutInfo: model.TransferLayoutInfo = windows.getShared("layout");
            if (layoutInfo !== undefined) {
                self.parentCode = layoutInfo.parentCode;
                self.layoutID = layoutInfo.layoutID;
                self.pgType = layoutInfo.pgType;
            }
            service.active(self.layoutID).done((data: model.LayoutDto) => {
                if (data !== undefined) {
                    let listPlacement: Array<model.Placement> = _.map(data.placements, (item) => {
                        return new model.Placement(item.placementID, item.placementPartDto.name,
                            item.row, item.column,
                            item.placementPartDto.width, item.placementPartDto.height, item.placementPartDto.externalUrl,
                            item.placementPartDto.topPagePartID, item.placementPartDto.type);
                    });
                    listPlacement = _.orderBy(listPlacement, ['row', 'column'], ['asc', 'asc']);
                    self.placements(listPlacement);
                }
                _.defer(() => { self.initDisplay(); });
                dfd.resolve();
            }).fail((res: any) => {
                dfd.fail();
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }

        /** Registry Layout */
        registryLayout(): void {
            var self = this;
            block.invisible();
            service.registry(self.parentCode, self.layoutID, self.pgType, self.placements())
                .done((data) => {
                    self.layoutID = data;
                    dialog.info({ messageId: "Msg_15" });
                }).fail((res) => {
                    dialog.alertError({ messageId: res.messageId });
                }).always(() => {
                    block.clear();
                });
        }

        /** Close Dialog */
        closeDialog(): void {
            var self = this;
            var layoutInfo: model.TransferLayoutInfo = {
                parentCode: self.parentCode,
                layoutID: self.layoutID,
                pgType: self.pgType
            };
            windows.setShared("layout", layoutInfo, false);
            windows.close();
        }

        /** Add Column button click */
        addColumnButtonClick(): void {
            this.layoutGrid().addColumn(1);
            this.setupDroppable();
        }

        /** Add Row button click */
        addRowButtonClick(): void {
            this.layoutGrid().addRow(1);
            this.setupDroppable();
        }

        /** Open Add TopPage-Part Dialog */
        openAddDialog(row: number, column: number, element: HTMLElement): void {
            var self = this;
            block.invisible();
            $(element).addClass("hover");
            windows.setShared("pgtype", self.pgType, false);
            windows.setShared("size", { row: row, column: column }, false);
            windows.sub.modal("/view/ccg/031/b/index.xhtml", { title: "ウィジェットの追加" }).onClosed(() => {
                block.clear();
                let placement: model.Placement = windows.getShared("placement");
                if (placement != undefined) {
                    self.placements.push(placement);
                    self.setupPositionAndSize(placement);
                    var movingPlacementIds = self.layoutGrid().markOccupied(placement);
                    self.reorderPlacements(movingPlacementIds, [placement.placementID]);
                    self.autoExpandLayout();
                    self.markOccupiedAll();
                    self.setupDragDrop();
                };
                $(element).removeClass("hover");
            });
        }

        /** Open Preview Dialog */
        openPreviewDialog(): void {
            block.invisible();
            windows.setShared("placements", this.placements(), false);
            windows.sub.modal("/view/ccg/031/c/index.xhtml", { title: "プレビュー" }).onClosed(() => {
                block.clear();
            });
        }

        /** Open Preview Dialog */
        removeWidget(placementID: string): void {
            var self = this;
            var placement = _.find(self.placements(), ['placementID', placementID]);
            self.layoutGrid().clearOccupied(placement);
            self.placements.remove(placement);
        }

        /** Init all Widget display & binding */
        private initDisplay(): void {
            var self = this;
            self.initReorderPlacements(_.clone(self.placements()), []);
            self.autoExpandLayout();
            self.markOccupiedAll();
            self.setupPositionAndSizeAll();
            self.setupDragDrop();
        }

        /** Re-order all Placements when init */
        private initReorderPlacements(listPlacements: Array<model.Placement>, checkingPlacements: Array<model.Placement>): void {
            var self = this;
            if (listPlacements.length > 0) {
                // Moving Placement
                var movingPlacement = listPlacements[0];
                self.shiftOverlapPart(movingPlacement, checkingPlacements);

                checkingPlacements.push(listPlacements[0]);
                _.pullAt(listPlacements, [0]);
                self.initReorderPlacements.call(self, listPlacements, checkingPlacements);
            }
        }

        /** Setup Draggable & Droppable */
        private setupDragDrop(): void {
            this.setupDragable();
            this.setupDroppable();
        }

        /** Setup Dragable */
        private setupDragable(): void {
            $(".widget-panel").draggable({
                opacity: .8,
                distance: 25,
                scroll: true,
                containment: ".placement-container",
                cancel: ".panel-content,.remove-button",
                revert: "invalid",
                stack: ".widget-panel",
                iframeFix: true
            });
        }

        /** Setup Droppable */
        private setupDroppable(): void {
            var self = this;
            $(".layout-cell").droppable({
                tolerance: "pointer",
                classes: { "ui-droppable-hover": "hover" },
                drop: function(event, ui) {
                    // Update Placement position
                    let $dropable = $(event.target);
                    let $dragable = ui.draggable;
                    let placement = _.find(self.placements(), ['placementID', $dragable.attr("id")]);
                    self.layoutGrid().clearOccupied(placement);
                    placement.row = ntsNumber.getDecimal($dropable.attr("id").split("-")[1], 0);
                    placement.column = ntsNumber.getDecimal($dropable.attr("id").split("-")[2], 0);
                    self.setupPositionAndSize(placement);
                    var movingPlacementIds = self.layoutGrid().markOccupied(placement);
                    self.reorderPlacements(movingPlacementIds, [placement.placementID]);
                    self.autoExpandLayout();
                    self.markOccupiedAll();
                    self.setupDragDrop();
                }
            });
        }

        /**
         * Re-order list Placements with a list checking Placements
         * @param movingPlacementIds list placementID need to move
         * @param checkingPlacementIds list placementID need to check overlap
         */
        private reorderPlacements(movingPlacementIds: Array<string>, checkingPlacementIds: Array<string>): Array<string> {
            var self = this;
            var movingPlacements = _.filter(self.placements(), (placement) => {
                return _.includes(movingPlacementIds, placement.placementID);
            });
            movingPlacements = _.orderBy(movingPlacements, ['row', 'column'], ['asc', 'asc']);
            var listOverlapPlacement: Array<string> = [];
            _.each(movingPlacements, (movingPlacement) => {
                self.layoutGrid().clearOccupied(movingPlacement);
                var checkingPlacements = _.filter(self.placements(), (placement) => {
                    return _.includes(checkingPlacementIds, placement.placementID);
                });
                self.shiftOverlapPart(movingPlacement, checkingPlacements);
                // Add that moving placement to checking so that Placement won't be move anymore
                checkingPlacementIds.push(movingPlacement.placementID);
                checkingPlacementIds = _.union(checkingPlacementIds);
                // List Placement need to moving because their parents were moved
                listOverlapPlacement = _.concat(listOverlapPlacement, self.layoutGrid().markOccupied(movingPlacement));
                self.autoExpandLayout();
                self.setupPositionAndSize(movingPlacement, ANIMATION_DURATION);
            });
            if (listOverlapPlacement.length > 0)
                self.reorderPlacements.call(self, listOverlapPlacement, checkingPlacementIds);
        }

        /** Recursive move overlap part */
        private shiftOverlapPart(movingPlacement: model.Placement, checkingPlacements: Array<model.Placement>): void {
            var self = this;
            var newColumnPosition: Array<number> = [];
            _.each(checkingPlacements, (checkingPlacement) => {
                if (!_.isEqual(movingPlacement, checkingPlacement)) {
                    if (self.checkIntersect(movingPlacement, checkingPlacement)) {
                        movingPlacement.column = checkingPlacement.column + checkingPlacement.width;
                        // Check if new position is overlap
                        self.shiftOverlapPart.call(self, movingPlacement, _.clone(checkingPlacements));
                    }
                }
            });
        }

        /** Check 2 placements is intersect */
        private checkIntersect(placeA: model.Placement, placeB: model.Placement): boolean {
            var AX1: number = placeA.column;
            var AY1: number = placeA.row;
            var AX2: number = placeA.column + placeA.width - 1;
            var AY2: number = placeA.row + placeA.height - 1;
            var BX1: number = placeB.column;
            var BY1: number = placeB.row;
            var BX2: number = placeB.column + placeB.width - 1;
            var BY2: number = placeB.row + placeB.height - 1;
            if (AX1 <= BX2 && AX2 >= BX1 &&
                AY1 <= BY2 && AY2 >= BY1) {
                return true;
            }
            return false;
        }

        /** Setup position and size for all Placements */
        private setupPositionAndSizeAll(): void {
            var self = this;
            _.forEach(self.placements(), (placement) => {
                self.setupPositionAndSize(placement);
            });
        }

        /**
         * Setup position and size for a Placement
         * @param placement placement need to setup
         * @param duration milliseconds moving a placement
         */
        private setupPositionAndSize(placement: model.Placement, duration?: number): void {
            var self = this;
            var $placement = $("#" + placement.placementID);
            duration = duration || 0;
            $placement.css({
                width: (placement.width * 150) + ((placement.width - 1) * 10),
                height: (placement.height * 150) + ((placement.height - 1) * 10)
            });
            if (duration === 0) {
                $placement.css({
                    top: ((placement.row - 1) * 150) + ((placement.row - 1) * 10),
                    left: ((placement.column - 1) * 150) + ((placement.column - 1) * 10)
                });
            }
            else {
                $placement.animate({
                    top: ((placement.row - 1) * 150) + ((placement.row - 1) * 10),
                    left: ((placement.column - 1) * 150) + ((placement.column - 1) * 10),
                }, duration, ANIMATION_EASETYPE);
            }
        }

        /** Expand layout */
        private autoExpandLayout(): void {
            var self = this;
            _.forEach(self.placements(), (placement) => {
                // Expand Row
                let totalRow: number = self.layoutGrid().rows();
                let expandRow: number = (placement.row + placement.height - 1) - totalRow;
                if (expandRow > 0) {
                    self.layoutGrid().addRow(expandRow);
                    self.autoExpandLayout();
                }
                // Expand Column
                let totalColumn: number = self.layoutGrid().columns();
                let expandColumn: number = (placement.column + placement.width - 1) - totalColumn;
                if (expandColumn > 0) {
                    self.layoutGrid().addColumn(expandColumn);
                    self.autoExpandLayout();
                }
            });
        }

        /** Setup occupied for all Placements */
        private markOccupiedAll(): void {
            var self = this;
            _.each(self.placements(), (placement) => {
                self.layoutGrid().markOccupied(placement);
            });
        }

    }

    /** The main layout grid information */
    class LayoutGrid {
        rows: KnockoutComputed<number>;
        columns: KnockoutObservable<number>;
        layoutRows: KnockoutObservableArray<LayoutRow>;
        constructor(rows: number, columns: number) {
            var self = this;
            self.columns = ko.observable(columns);
            self.layoutRows = ko.observableArray([]);
            for (var row = 1; row <= rows; row++) {
                self.layoutRows.push(new LayoutRow(row, columns));
            };
            self.rows = ko.computed(() => {
                return self.layoutRows().length;
            });
        }

        /** Add row(s) to LayoutGrid */
        addRow(row: number) {
            var self = this;
            for (var i = 1; i <= row; i++) {
                self.layoutRows.push(new LayoutRow(self.rows() + i, self.columns()));
            }
            self.addOverflowClass();
        }

        /** Add column(s) to LayoutGrid */
        addColumn(column: number) {
            var self = this;
            for (var i = 1; i <= column; i++) {
                _.each(self.layoutRows(), (layoutRow) => {
                    layoutRow.layoutCells.push(new LayoutCell(layoutRow.rowIndex, self.columns() + i));
                });
            }
            self.columns(self.columns() + column);
            self.addOverflowClass();
        }

        /** Get all LayoutCell in LayoutGrid */
        getAllCells(): Array<LayoutCell> {
            var self = this;
            var layoutCells: Array<LayoutCell> = [];
            _.each(self.layoutRows(), (layoutRow) => {
                layoutRow.layoutCells().forEach((v) => { layoutCells.push(v) });
            });
            return layoutCells;
        }

        /** Clear all cell Placement occupied */
        clearOccupied(placement: model.Placement) {
            var self = this;
            var occupiedCells = self.getOccupiedCells(placement);
            _.each(occupiedCells, (cell) => { cell.occupied = ""; });
        }

        /**
         * Mark LayoutCells occupied by placement
         * @param placement placement need to mark
         * @return Return all Placements Id occupied there
         */
        markOccupied(placement: model.Placement): Array<string> {
            var self = this;
            var occupiedCells = self.getOccupiedCells(placement);
            var occupiedPlacementIDs: Array<string> = [];
            _.each(occupiedCells, (cell) => {
                if (!nts.uk.util.isNullOrEmpty(cell.occupied) && cell.occupied !== placement.placementID)
                    occupiedPlacementIDs.push(cell.occupied);
                cell.occupied = placement.placementID;
            });
            return _.union(occupiedPlacementIDs);
        }

        /**
         * Get LayoutCells occupied by placement
         * @param placement placement need to mark
         * @return Return all LayoutCells occupied
         */
        private getOccupiedCells(placement: model.Placement): Array<LayoutCell> {
            var self = this;
            var occupiedCellIDs = [];
            for (var y = placement.row; y <= placement.row + placement.height - 1; y++) {
                for (var x = placement.column; x <= placement.column + placement.width - 1; x++) {
                    occupiedCellIDs.push("cell-" + y + "-" + x);
                }
            }
            var occupiedCells = _.filter(self.getAllCells(), (cell) => {
                return occupiedCellIDs.indexOf(cell.id) > -1;
            });
            return occupiedCells;
        }

        /** Add overflow class */
        private addOverflowClass(): void {
            if (this.rows() > MINROW) $(".placement-container").addClass("overflow-y");
            if (this.columns() > MINCOLUMN) $(".placement-container").addClass("overflow-x");
        }
    }

    class LayoutRow {
        rowIndex: number;
        layoutCells: KnockoutObservableArray<LayoutCell>;
        constructor(row: number, columns: number) {
            this.rowIndex = row;
            this.layoutCells = ko.observableArray([]);
            for (var column = 1; column <= columns; column++) {
                this.layoutCells.push(new LayoutCell(this.rowIndex, column));
            }
        }
    }

    class LayoutCell {
        id: string;
        column: number;
        row: number;
        occupied: string;
        constructor(row: number, column: number) {
            this.row = row;
            this.column = column;
            this.id = "cell-" + row + "-" + column;
            this.occupied = "";
        }
    }
}