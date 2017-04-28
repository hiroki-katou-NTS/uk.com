module qmm020.m.service {
    //duong dan   
    var paths = {
        getLayoutHistory: "pr/core/allot/findallotlayouthistory/{0}",
        getLayoutName: "pr/core/allot/findcompanyallotlayoutname/{0}"
    }

    export function getAllAllotLayoutHist(baseYm: number): JQueryPromise<Array<model.LayoutHistoryDto>> {
        var dfd = $.Deferred<Array<any>>();
        var _path = nts.uk.text.format(paths.getLayoutHistory, baseYm);
        nts.uk.request.ajax(_path)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    /**
     * Get layout master name 
     */
    export function getAllotLayoutName(stmtCode :string): JQueryPromise<string> {
        var dfd = $.Deferred<any>();
        var _path = nts.uk.text.format(paths.getLayoutName, stmtCode);
        var options = {
            dataType: 'text',
            contentType: 'text/plain'
        };
        nts.uk.request.ajax(_path, undefined , options)
            .done(function(res: string){
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise(); 
    }
    
    export module model {
        // layout
        export class LayoutHistoryDto {
            startYm: string;
            endYm: string;
            stmtCode: string;
        }
    }
    
}