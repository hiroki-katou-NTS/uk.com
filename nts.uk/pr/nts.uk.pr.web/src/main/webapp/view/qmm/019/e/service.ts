module qmm019.e.service {
    var paths = {
        getLayoutInfor : "pr/proto/layout/findlayout"       
    }
    
    /**
     * Get list layout master new history
     */
    export function getLayoutWithMaxStartYm(): JQueryPromise<Array<model.LayoutMasterDto>> {
        var dfd = $.Deferred<Array<any>>();
        var objectLayout = [{'stmtCode':'1', 'startYm': 201605}]
        nts.uk.request.ajax(paths.getLayoutInfor + objectLayout)
            .done(function(res: Array<any>){
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise(); 
    }
    
    
   /**
           * Model namespace.
        */
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