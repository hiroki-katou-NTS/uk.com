module nts.uk.pr.view.qmm016.a.history.base {
    /**
     * Base view model.
     */
    export abstract class BaseHistoryViewModel {
        constructor(htmlPath: string, history: any) {
            
        }

        /**
         * Start
         */
        start(): JQueryPromise<any> {
            return null;
        }
        
        /**
         * Get element setting.
         */
        abstract getElementSetting(): Array<model.ElementSettingDto>;

        /**
         * Get setting cell item.
         */
        abstract getCellItem(): Array<model.CellItemDto>;

        /**
         * Paste data from excel.
         */
        abstract pasteFromExcel(): void;
    }
}