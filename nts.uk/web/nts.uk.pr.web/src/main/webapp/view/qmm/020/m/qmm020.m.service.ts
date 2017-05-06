module qmm020.m.service {
    let paths = {
        getLayoutHistory: "pr/core/allotlayouthist/getall",
        getLayoutName: "pr/core/allotlayouthist/findname"
    };

    export function getAllAllotLayoutHist(baseYm: number): JQueryPromise<Array<ILayoutHistoryDto>> {
        // set default Yearmonth
        baseYm = baseYm || 197001;

        var dfd = $.Deferred();
        nts.uk.request.ajax(paths.getLayoutHistory + "/" + baseYm, { baseYm: baseYm })
            .done(function(res: Array<ILayoutHistoryDto>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

    export function getAllotLayoutName(stmtCode: string): JQueryPromise<string> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(paths.getLayoutName + "/" + stmtCode, { stmtCode: stmtCode }, { dataType: 'text', contentType: 'text/plain' })
            .done(function(res: string) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

    interface ILayoutHistoryDto {
        startYm: string;
        endYm: string;
        stmtCode: string;
    }
}