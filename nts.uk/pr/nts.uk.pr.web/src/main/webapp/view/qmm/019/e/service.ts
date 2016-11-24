module qmm019.e.service {
    var paths = {
        getLayoutInfor : "pr/proto/layout/findlayoutwithmaxstartym"       
    }
    
    /**
     * Get list layout master new history
     */
    export function getLayoutWithMaxStartYm(): JQueryPromise<Array<LayoutMasterModel>> {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.getLayoutInfor)
            .done(function(res: Array<any>){
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise(); 
    }
    
    
    
    /**
     * PaymentDateProcessingMasterModel.
     * Contain enum define information.
     */
    export interface LayoutMasterModel {
        /**
         * layout code
         */
        layoutCode: string;
    
        /**
         * layout name
         */
        layoutName: string;
    
        /**
         * layout attribute
         */
        layoutAtr: number;
    
        /**
         * start YearMonth
         */
        startYm: number;
    
        /**
         * end yearMonth
         */
        endYm: number;
    }
}