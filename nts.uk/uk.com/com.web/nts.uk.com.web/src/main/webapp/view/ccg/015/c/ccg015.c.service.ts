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
        
        export interface TopPageDto {
            topPageCode: string;
            topPageName: string;
            layoutDisp: string;
            isCheckOverwrite: boolean;
            copyCode: string;
        }
    }
}