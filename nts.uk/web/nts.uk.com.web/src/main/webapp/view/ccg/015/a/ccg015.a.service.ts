module nts.uk.com.view.ccg015.a {

    export module service {

        // Service paths.
        var servicePath = {
            findAllTopPageItem: "/toppage/findAll",
            getTopPageItemDetail: "/toppage/topPageDetail",
            registerTopPage: "/toppage/create",
            updateTopPage: "/toppage/update",
            removeTopPage: "/toppage/remove"
        }
        export function loadTopPage(): JQueryPromise<Array<model.TopPageItemDto>> {
            var self = this;
            var dfd = $.Deferred<Array<model.TopPageItemDto>>();
            var path = servicePath.findAllTopPageItem;
            nts.uk.request.ajax(path).done(function(data: Array<model.TopPageItemDto>) {
                dfd.resolve(data);
            });
            return dfd.promise();
        }

        export function loadDetailTopPage(topPageCode: string): JQueryPromise<model.TopPageItemDetailDto> {
            var self = this;
            var dfd = $.Deferred<model.TopPageItemDetailDto>();
            var path = servicePath.getTopPageItemDetail + '/' + topPageCode;
            nts.uk.request.ajax(path).done(function(data: model.TopPageItemDetailDto) {
                dfd.resolve(data);
            });
            return dfd.promise();
        }

        /**
          * Function is used to save new Top Page.
          */
        export function registerTopPage(data: model.TopPageItemDetailDto): JQueryPromise<model.TopPageItemDetailDto> {
            return nts.uk.request.ajax(servicePath.registerTopPage, data);
        }

        /**
        * Function is used to update Top Page.
        */
        export function updateTopPage(data: model.TopPageItemDetailDto): JQueryPromise<model.TopPageItemDetailDto> {
            return nts.uk.request.ajax(servicePath.updateTopPage, data);
        }

        /**
        * Function is used to delete Top Page.
        */
        export function deleteTopPage(topPageItemCode: string): JQueryPromise<model.TopPageItemDetailDto> {
            return nts.uk.request.ajax(servicePath.removeTopPage, topPageItemCode);
        }

        export module model {
            export interface TopPageItemDto {
                topPageCode: string;
                topPageName: string;
            }
            export interface TopPageItemDetailDto {
                topPageCode: string;
                topPageName: string;
            }
        }
    }
}