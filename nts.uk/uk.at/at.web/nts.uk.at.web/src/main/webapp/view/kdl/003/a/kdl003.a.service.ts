module nts.uk.at.view.kdl003.a {
    export module service {
        var paths: any = {
            findAllWorkType: "at/share/worktype/findAllByOrder",
            findWorkTypeByCodes: "at/share/worktype/getpossibleworktype",
            findAllWorkTime: "at/shared/worktimesetting/findAll",
            findWorkTimeByCodes: "at/shared/worktimesetting/findByCodes",
            findByTime: "at/shared/worktimesetting/findByTime",
            isWorkTimeSettingNeeded: "at/schedule/basicschedule/isWorkTimeSettingNeeded",
            checkPairWorkTypeWorkTime: "at/schedule/basicschedule/checkPairWorkTypeWorkTime",
            checkPairWorkTypeWorkTime2: "at/schedule/basicschedule/checkPairWorkTypeWorkTime2",
            findBreakByCodes: "at/shared/worktimesetting/findBreakByCodes",

        }

        /**
         * Find all work type.
         */
        export function findAllWorkType(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findAllWorkType);
        }

        /**
         * Find work type by list codes.
         */
        export function findWorkTypeByCodes(command: Array<string>): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findWorkTypeByCodes, command);
        }

        /**
         * Find work time by list codes.
         */
        export function findWorkTimeByCodes(command: any): JQueryPromise<Array<WorkTimeSet>> {
            return nts.uk.request.ajax(paths.findWorkTimeByCodes, command);
        }

        /**
         * Find all work time.
         */
        export function findAllWorkTime(): JQueryPromise<Array<WorkTimeSet>> {
            return nts.uk.request.ajax(paths.findAllWorkTime);
        }

        /**
         * Search work time.
         */
        export function findByTime(command: any): JQueryPromise<Array<WorkTimeSet>> {
            return nts.uk.request.ajax(paths.findByTime, command);
        }

        /**
         * Check is work time setting needed
         */
        export function isWorkTimeSettingNeeded(workTypeCode: string): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.isWorkTimeSettingNeeded + '/' + workTypeCode);
        }

        /**
         * Check pair work type & work time.
         */
        export function checkPairWorkTypeWorkTime(workTypeCode: string, workTimeCode): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.checkPairWorkTypeWorkTime + '/' + workTypeCode + '/' + workTimeCode);
        }
        
        export function checkPairWorkTypeWorkTime2(workTypeCode: string): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.checkPairWorkTypeWorkTime2 + '/' + workTypeCode);
        }
        
        /**
         * Search break time
         */
        export function findBreakByCodes(workTimeCode: string): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findBreakByCodes+ '/' + workTimeCode);
        }
        
        interface WorkTimeSet {
            code: string;
            name: string;
            workTime1: string;
            workTime2: string;
            workAtr: string;
            remark: string;
            firstStartTime: number;
            firstEndTime: number;
            secondStartTime: number;
            secondEndTime: number;
        }

    }
}