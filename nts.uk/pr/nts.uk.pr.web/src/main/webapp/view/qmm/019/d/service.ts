module qmm019.d.service {
    var paths = {
        getLayoutInfor : "pr/proto/layout/findlayoutwithmaxstartym"       
    }
    
    /**
     * Get list layout master new history
     */
    export function getLayoutWithMaxStartYm(): JQueryPromise<Array<model.LayoutMasterDto>> {
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
    
    export module model {
            // layout
            export class LayoutMasterDto {
                companyCode: string;
                stmtCode: string;
                startYm: number;
                stmtName: string;
                endYM: number;
                layoutAtr: number;
            }

        }
}