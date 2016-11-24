module qmm019.a {
    export module service {
        var paths: any = {
            getAllLayout: "pr/proto/layout/findalllayout"
        }


        /**
         * Get list payment date processing.
         */
        export function getAllLayout(): JQueryPromise<Array<model.LayoutMasterDto>> {
            var dfd = $.Deferred<Array<any>>();
            nts.uk.request.ajax(paths.getAllLayout)
                .done(function(res: Array<any>) {
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

}