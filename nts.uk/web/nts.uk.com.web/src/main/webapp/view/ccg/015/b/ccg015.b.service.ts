module nts.uk.pr.view.ccg015.b {

    export module service {

        // Service paths.
        var servicePath = {
            loadMyPageSetting: "/mypage/myPageSetting"//TODO add find all top page path
        }
        export function loadMyPageSetting(CompanyId : string): JQueryPromise<model.MyPageSettingDto> {
            var self = this;
            var dfd = $.Deferred<any>();
            var path = servicePath.loadMyPageSetting+"/"+CompanyId;
            nts.uk.request.ajax(path).done(function(data: model.MyPageSettingDto) {
                dfd.resolve(data);
            });
            return dfd.promise();
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