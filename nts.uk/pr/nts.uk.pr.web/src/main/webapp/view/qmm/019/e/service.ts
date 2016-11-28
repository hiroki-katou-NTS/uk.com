module qmm019.e.service {
    var paths:any = {
        getLayoutInfor : "/pr/proto/layout/findlayout/{0}/{1}",
        pathDeleteLayout: "/pr/proto/layout/deletedata"   
    }
    
    /**
     * Get list layout master new history
     */
    export function getLayout(stmtCode: string, startYm: number): JQueryPromise<Array<model.LayoutMasterDto>> {
        var dfd = $.Deferred<Array<any>>();
        var objectLayout = {stmtCode: stmtCode, startYm: startYm};
        var _path = nts.uk.text.format(paths.getLayoutInfor, stmtCode, startYm);
        nts.uk.request.ajax(_path)
            .done(function(res: Array<any>){
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise(); 
    }
    
    export function deleteLayout(layoutMaster : model.LayoutMasterDto){
        var dfd = $.Deferred<Array<any>>();  
//        var dto : model.LayoutMasterDto = {
//            companyCode: layoutMaster.companyCode,
//            stmtCode: layoutMaster.stmtCode,
//            startYm: layoutMaster.startYm,
//            stmtName: layoutMaster.stmtName,
//            endYM: layoutMaster.endYM,
//            layoutAtr: layoutMaster.layoutAtr
//        };
        var _path = nts.uk.text.format(paths.pathDeleteLayout, layoutMaster);
        nts.uk.request.ajax(paths.pathDeleteLayout, layoutMaster).done(function(res: Array<any>){
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
                companyCode: string;
                stmtCode: string;
                startYm: number;
                stmtName: string;
                endYm: number;
                layoutAtr: number;
            }

        }
}