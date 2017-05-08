module nts.uk.pr.view.ccg015.b {

    export module service {

        // Service paths.
        var servicePath = {
            loadMyPageSetting: "/mypage/getMyPageSetting",
            updateMyPageSetting: "/mypage/updateMyPageSetting"
        }
        
        /**
          * Function is used to load My Page Setting.
          */
        export function loadMyPageSetting(): JQueryPromise<model.MyPageSettingDto> {
            var self = this;
            var dfd = $.Deferred<any>();
            var path = servicePath.loadMyPageSetting;
            nts.uk.request.ajax(path).done(function(data: model.MyPageSettingDto) {
                dfd.resolve(data);
            });
            return dfd.promise();
        }

        /**
       * Function is used to update My Page Setting.
       */
        export function updateMyPageSetting(data: model.MyPageSettingDto): JQueryPromise<model.MyPageSettingDto> {
            return nts.uk.request.ajax(servicePath.updateMyPageSetting, data);
        }

        export module model {
            export interface MyPageSettingDto {
                companyId: string;
                useMyPage: number;
                useWidget: number;
                useDashboard: number;
                useFlowMenu: number;
                externalUrlPermission: number;
                topPagePartUseSettingDto: Array<TopPagePartUseSettingItemDto>;
            }
            export interface TopPagePartUseSettingItemDto {
                companyId: string;
                partItemCode: string;
                partItemName: string;
                useDivision: number;
                partType: string;
            }
        }
    }
}