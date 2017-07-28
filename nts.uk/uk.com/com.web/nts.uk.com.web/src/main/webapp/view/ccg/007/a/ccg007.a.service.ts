module nts.uk.pr.view.ccg007.a {

    export module service {

        // Service paths.
        var servicePath = {
            copyPath: ""
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
            layoutId: string;
            languageNumber: number;
            isCheckOverwrite: boolean;
            copyCode: string;
        }
    }
}