module qmm019.g.service {
    var paths = {
        getLayoutInfor : "pr/proto/layout/findlayoutwithmaxstartym",
        copylayoutPath: "pr/proto/layout/createlayout"
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
    
    export function createLayout(layoutMaster: model.LayoutMasterDto){
        var dfd = $.Deferred<Array<any>>();  
        nts.uk.request.ajax(paths.copylayoutPath, layoutMaster).done(function(res: Array<any>){
            dfd.resolve(res);    
        }).fail(function(res){
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
                checkCopy: Boolean;
                /** StmtCode của Layout được copy */
                stmtCodeCopied: string;
                /** startYm của Layout được copy */
                startYmCopied: number;
                stmtCode: string;
                startYm: number;
                layoutAtr: number;
                stmtName: string;
                endYm: number;
            }

        }
}