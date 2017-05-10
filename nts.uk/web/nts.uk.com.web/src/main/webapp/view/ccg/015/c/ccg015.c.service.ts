module nts.uk.pr.view.ccg015.c {

    export module service {

        // Service paths.
        var servicePath = {
            copyPath: "toppage/copyTopPage",
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
        export function copyLayout(layoutId: string,topPageCode : string): JQueryPromise<StringDto> {
            var self = this;
            var dfd = $.Deferred<StringDto>();
            var path = servicePath.copyLayout+"/"+layoutId+"/"+topPageCode;
            nts.uk.request.ajax(path).done(function(newLayoutId: StringDto) {
                dfd.resolve(newLayoutId);
            });
            return dfd.promise();
        }
        
        export interface TopPageDto {
            topPageCode: string;
            topPageName: string;
            layoutId: string;
            languageNumber: number;
            isCheckOverwrite: boolean;
            copyCode: string;
        }
        export interface PlacementDto {
            topPagePartCode: string;
            row: number;
            column: number;
        }
        export interface StringDto{
            newLayoutId:string;
        }
    }
}