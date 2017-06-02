module nts.uk.com.view.ccg031.c.viewmodel {
    import model = nts.uk.com.view.ccg.model;
    import windows = nts.uk.ui.windows;

    export class ScreenModel {
        placements: KnockoutObservableArray<model.Placement>;

        constructor() {
            this.placements = ko.observableArray([]);
        }

        /** Start Page */
        startPage(): void {
            var self = this;
            var placements: Array<model.Placement> = windows.getShared("placements");
            if (placements !== undefined)
                self.placements(placements);
            _.defer(() => { self.setupPositionAndSizeAll(); });
        }

        /** Close Dialog */
        closeDialog(): void {
            windows.close();
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