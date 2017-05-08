module nts.uk.pr.view.ccg015.c {

    export module service {

        // Service paths.
        var servicePath = {
            copyPath: "toppage/copy",
            copyLayout: "toppage/copyLayout"
        }

        /**
          * Function is used to copy new Top Page.
          */
        export function copyTopPage(data: TopPageDto): JQueryPromise<TopPageDto> {
            return nts.uk.request.ajax(servicePath.copyPath, data);
        }
        
        /**
          * Function is used to copy new Layout.
          */
        export function copyLayout(layoutId: string,topPageCode : string): JQueryPromise<string> {
            var self = this;
            var dfd = $.Deferred<string>();
            var path = servicePath.copyLayout+"/"+layoutId+"/"+topPageCode;
            nts.uk.request.ajax(path).done(function(layoutId: string) {
                dfd.resolve(layoutId);
            });
            return dfd.promise();
        }
        
        export interface TopPageDto {
            topPageCode: string;
            topPageName: string;
            layoutId: string;
        }
        export interface PlacementDto {
            topPagePartCode: string;
            row: number;
            column: number;
        }
    }
}