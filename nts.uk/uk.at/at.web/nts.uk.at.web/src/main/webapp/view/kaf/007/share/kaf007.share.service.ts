module nts.uk.at.view.kaf007.share {
    export module service {
        import ajax = nts.uk.request.ajax;
        import common = nts.uk.at.view.kaf007.share.common;
        import format = nts.uk.text.format;
        var paths = {
                getWorkChangeCommonSetting: "/at/request/application/workchange/getWorkChangeCommonSetting",                
                checkChangeApplicationDate: "/at/request/application/workchange/checkChangeApplicationDate",
                getWorkchangeByAppID: "/at/request/application/workchange/getWorkchangeByAppID/{0}",
                addWorkChange: "/at/request/application/workchange/addworkchange",
                updateWorkChange: "/at/request/application/workchange/updateworkchange",
        }
        
        /**
         * 起動する
         * アルゴリズム「勤務変更申請画面初期（新規）」を実行する
         */
        export function getWorkChangeCommonSetting(): JQueryPromise<any> {
            return ajax("at", paths.getWorkChangeCommonSetting, {});
        }
        /**
         * 申請日を変更する
         * 共通アルゴリズム「申請日を変更する」を実行する
         */
        export function checkChangeApplicationDate(): JQueryPromise<Array<any>> {
            return ajax("at", paths.checkChangeApplicationDate, {});
        }
        /**
         * 勤務変更申請の登録を実行する
         */
        export function addWorkChange(workchange: common.AppWorkChangeCommand): JQueryPromise<any> {
            return ajax("at", paths.addWorkChange, workchange);
        }
        /**
         * 
         */
        export function getWorkchangeByAppID(appId: string): JQueryPromise<any> {
            return ajax("at", format(paths.getWorkchangeByAppID, appId));
        }
        /**
         * 
         */
        export function updateWorkChange(workchange: common.AppWorkChangeCommand): JQueryPromise<any> {
            return ajax("at", paths.updateWorkChange, workchange);
        }
    }
}
