module nts.uk.com.view.ccg031.c.viewmodel {
    import model = nts.uk.com.view.ccg.model;
    import windows = nts.uk.ui.windows;
    import positionUtil = nts.uk.com.view.ccg.positionUtility;

    export class ScreenModel {
        placements: Array<model.Placement>;

        constructor() {
            this.placements = [];
        }

        /** Start Page */
        startPage(): void {
            var self = this;
            var placements: Array<model.Placement> = windows.getShared("placements");
            if (placements !== undefined)
                self.placements = placements;
            _.defer(() => { positionUtil.setupPositionAndSizeAll(self.placements); });
        }

        /** Close Dialog */
        closeDialog(): void {
            windows.close();
        }
    }
}