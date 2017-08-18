module nts.uk.at.view.kdl003.a {
    export module service {
        var paths: any = {
            findAllWorkType: "at/share/worktype/findNotDeprecated",
            findWorkTypeByCodes: "at/share/worktype/findNotDeprecatedByListCode",
            findAllWorkTime: "at/shared/worktime/findByCompanyID",
            findByCodeList: "at/shared/worktime/findByCodeList",
            findByTime: "at/shared/worktime/findByTime",
            isWorkTimeSettingNeeded: "at/schedule/basicschedule/isWorkTimeSettingNeeded",
            checkPairWorkTypeWorkTime: "at/schedule/basicschedule/checkPairWorkTypeWorkTime",

        }

        export function findAllWorkType(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findAllWorkType);
        }
        export function findWorkTypeByCodes(command: Array<string>): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findWorkTypeByCodes, command);
        }

        export function findByCodeList(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findByCodeList, command);
        }
        export function findAllWorkTime(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findAllWorkTime);
        }

        export function findByTime(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findByTime, command);
        }
        export function isWorkTimeSettingNeeded(workTypeCode: string): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.isWorkTimeSettingNeeded + '/' + workTypeCode);
        }
        export function checkPairWorkTypeWorkTime(workTypeCode: string, workTimeCode): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.checkPairWorkTypeWorkTime + '/' + workTypeCode + '/' + workTimeCode);
        }
        export module model {
        }
    }
}