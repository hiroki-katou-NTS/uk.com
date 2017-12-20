module nts.uk.at.view.kmk003.a {
    export module service {

        /**
        *  Service paths
        */
        let servicePath: any = {
            findAllWorktime: "at/shared/worktimeset/findAll",
            findWorktimeSetingInfoByCode: "at/shared/worktimeset/findByCode",
            getEnumWorktimeSeting: "at/shared/worktimeset/getenum",
            getPredByWorkTimeCode: "at/shared/pred/findByWorkTimeCode",
            savePred: "at/shared/pred/save",
            findByCodeFlexWorkSetting: "ctx/at/shared/worktime/flexset/findByCode",
            saveFlexWorkSetting: "ctx/at/shared/worktime/flexset/save"
        };

        /**
         * function find all work time set
         */
        export function findAllWorkTimeSet(): JQueryPromise<model.worktimeset.SimpleWorkTimeSettingDto[]> {
            return nts.uk.request.ajax(servicePath.findAllWorktime);
        }
        
         /**
         * function find work time set by code
         */
        export function findWorktimeSetingInfoByCode(workTimeCode: string): JQueryPromise<model.common.WorkTimeSettingInfoDto> {
            return nts.uk.request.ajax(servicePath.findWorktimeSetingInfoByCode + '/' + workTimeCode);
        }
         /**
         * function get all enum by setting work time
         */
        export function getEnumWorktimeSeting(): JQueryPromise<model.worktimeset.WorkTimeSettingEnumDto> {
            return nts.uk.request.ajax(servicePath.getEnumWorktimeSeting);
        }

        export function getPredByWorkTimeCode(workTimeCode: string): JQueryPromise<model.worktimeset.WorkTimeSettingDto> {
            return nts.uk.request.ajax(servicePath.getPredByWorkTimeCode + "/" + workTimeCode);
        }
        
        export function savePred(data: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.savePred, data);
        }
        /**
         * function find by work time code flex work setting data by call service
         */
        export function findByCodeFlexWorkSetting(worktimeCode: string): JQueryPromise<model.flexset.FlexWorkSettingDto> {
            return nts.uk.request.ajax(servicePath.findByCodeFlexWorkSetting + '/' + worktimeCode);
        } 
        
        /**
         * function save flex work setting by call service
         */
        export function saveFlexWorkSetting(command: model.command.FlexWorkSettingSaveCommand): JQueryPromise<void> {
            return nts.uk.request.ajax(servicePath.saveFlexWorkSetting, command);
        }

    }
}
