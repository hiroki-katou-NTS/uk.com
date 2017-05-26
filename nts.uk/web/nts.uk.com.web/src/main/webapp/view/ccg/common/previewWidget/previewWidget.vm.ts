module nts.uk.com.view.ccg.common.previewWidget.viewmodel {
    import model = nts.uk.com.view.ccg.model;
    import windows = nts.uk.ui.windows;
    import location = nts.uk.request.location;

    export class ScreenModel {
        layoutID: any;
        placements: KnockoutObservableArray<model.Placement>;
        isEmpty: KnockoutObservable<boolean>;

        constructor() {
            var self = this;
            self.layoutID = null;
            self.placements = ko.observableArray([]);
            self.placements.subscribe((changes) => {
                if (changes.length > 0)
                    self.isEmpty(false);
                else
                    self.isEmpty(true);
            });
            self.isEmpty = ko.observable(true);
        }

        /** Start Page */
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.layoutID = location.current.queryString.items['layoutid'];
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
            });
            return dfd.promise();
        }

        /** Close Dialog */
        closeDialog(): void {
            windows.close();
        }

        /** Init all Widget display & binding */
        private initDisplay(): void {
            var self = this;
            self.initReorderPlacements(_.clone(self.placements()), []);
            self.setupPositionAndSizeAll();
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
        
        /** Setup position and size for all Placements */
        private setupPositionAndSizeAll(): void {
            var self = this;
            _.forEach(self.placements(), (placement) => {
                self.setupPositionAndSize(placement);
            });
        }

        /** Setup position and size for a Placement */
        private setupPositionAndSize(placement: model.Placement): void {
            var $placement = $("#" + placement.placementID);
            $placement.css({
                top: ((placement.row - 1) * 150) + ((placement.row - 1) * 10),
                left: ((placement.column - 1) * 150) + ((placement.column - 1) * 10),
                width: (placement.width * 150) + ((placement.width - 1) * 10),
                height: (placement.height * 150) + ((placement.height - 1) * 10)
            });
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
    }
}