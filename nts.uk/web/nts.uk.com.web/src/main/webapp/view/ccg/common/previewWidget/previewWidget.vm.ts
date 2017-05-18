module previewWidget.viewmodel {
    import model = ccg.model;
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
                console.log(changes);
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
            this.setupPositionAndSizeAll();
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
    }
}