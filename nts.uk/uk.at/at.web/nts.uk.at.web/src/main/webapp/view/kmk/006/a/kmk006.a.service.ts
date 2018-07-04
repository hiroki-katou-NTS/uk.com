module nts.uk.at.view.kmk006.a {
    export module service {
        var paths = {
            
            // Get enum list
            findEnumAutoCalAtrOvertime: "ctx/at/shared/ot/autocal/find/autocalatrovertime",          
            findEnumAutoCalAtrOvertimeWithoutTimeRecorder: "ctx/at/shared/ot/autocal/find/autocalatrovertimewithouttimerecorder",
            findEnumUseClassification: "ctx/at/shared/ot/autocal/find/autocaluseclassification",
            findEnumTimeLimitUpperLimitSetting: "ctx/at/shared/ot/autocal/find/autocaltimelimitsetting",
                
            // Get data
            getComAutoCal: "ctx/at/shared/ot/autocal/com/getautocalcom",
            
            getJobAutoCal: "ctx/at/shared/ot/autocal/job/getautocaljob",
            getAllJobAutoCal: "ctx/at/shared/ot/autocal/job/getallautocaljob",
            
            getWkpAutoCal: "ctx/at/shared/ot/autocal/wkp/getautocalwkp",
            getAllWkpAutoCal: "ctx/at/shared/ot/autocal/wkp/getallautocalwkp",
            
            getWkpJobAutoCal: "ctx/at/shared/ot/autocal/wkpjob/getautocalwkpjob",
            getAllWkpJobAutoCal: "ctx/at/shared/ot/autocal/wkpjob/getallautocalwkpjob",
                       
            // Save data
            saveComAutoCal: "ctx/at/shared/ot/autocal/com/save",           
            saveJobAutoCal: "ctx/at/shared/ot/autocal/job/save",
            saveWkpAutoCal: "ctx/at/shared/ot/autocal/wkp/save",
            saveWkpJobAutoCal: "ctx/at/shared/ot/autocal/wkpjob/save",
            
            // Delete data
            deleteJobAutoCal: "ctx/at/shared/ot/autocal/job/delete",
            deleteWkpAutoCal: "ctx/at/shared/ot/autocal/wkp/delete",
            deleteWkpJobAutoCal: "ctx/at/shared/ot/autocal/wkpjob/delete",
            
            //get detail wkpl
            detailWkpl: "bs/employee/workplace/info/findDetail"
        }
        
        export function getDetailWkpl(obj: any): JQueryPromise<any> {
            return nts.uk.request.ajax('com',paths.detailWkpl, obj);
        }
        
        export function findEnumAutoCalAtrOvertime(): JQueryPromise<Array<model.Enum>> {
            return nts.uk.request.ajax(paths.findEnumAutoCalAtrOvertime);
        }
        
        export function findEnumAutoCalAtrOvertimeWithoutTimeRecorder(): JQueryPromise<Array<model.Enum>> {
            return nts.uk.request.ajax(paths.findEnumAutoCalAtrOvertimeWithoutTimeRecorder);
        }

        export function findEnumUseClassification(): JQueryPromise<Array<model.Enum>> {
            return nts.uk.request.ajax(paths.findEnumUseClassification);
        }

        export function findEnumTimeLimitUpperLimitSetting(): JQueryPromise<Array<model.Enum>> {
            return nts.uk.request.ajax(paths.findEnumTimeLimitUpperLimitSetting);
        }

        export function getComAutoCal(): JQueryPromise<model.ComAutoCalSettingDto> {
            return nts.uk.request.ajax("at", paths.getComAutoCal);
        }
        
        export function getAllJobAutoCal(): JQueryPromise<Array<model.JobAutoCalSettingDto>> {
            return nts.uk.request.ajax("at", paths.getAllJobAutoCal);
        }
        export function getJobAutoCal(jobId: string): JQueryPromise<model.JobAutoCalSettingDto> {
            if(nts.uk.text.isNullOrEmpty(jobId)){
                jobId = null;
            }
            return nts.uk.request.ajax("at", paths.getJobAutoCal + '/' + jobId);
        }

        export function getWkpAutoCal(wkpId: string): JQueryPromise<model.WkpAutoCalSettingDto> {
            if(nts.uk.text.isNullOrEmpty(wkpId)){
                wkpId = null;
            }
            return nts.uk.request.ajax("at", paths.getWkpAutoCal + '/' + wkpId);
        }
        export function getAllWkpAutoCal(): JQueryPromise<Array<model.WkpAutoCalSettingDto>> {
            return nts.uk.request.ajax("at", paths.getAllWkpAutoCal);
        }

        export function getAllWplJobAutoCal(): JQueryPromise<Array<model.WkpJobAutoCalSettingDto>> {
            return nts.uk.request.ajax("at", paths.getAllWkpJobAutoCal);
        }
        export function getWkpJobAutoCal(wkpId: string, jobId: string): JQueryPromise<model.WkpJobAutoCalSettingDto> {
            if(nts.uk.text.isNullOrEmpty(jobId)){
                jobId = null;
            }
            if(nts.uk.text.isNullOrEmpty(wkpId)){
                wkpId = null;
            }
            return nts.uk.request.ajax("at", paths.getWkpJobAutoCal + '/' + wkpId + '/' + jobId);
        }

        export function saveComAutoCal(command: model.ComAutoCalSettingDto): JQueryPromise<void> {
            return nts.uk.request.ajax("at", paths.saveComAutoCal, command);
        }

        export function saveJobAutoCal(command: model.JobAutoCalSettingDto): JQueryPromise<void> {
            return nts.uk.request.ajax("at", paths.saveJobAutoCal, command);
        }

        export function saveWkpAutoCal(command: model.WkpAutoCalSettingDto): JQueryPromise<void> {
            return nts.uk.request.ajax("at", paths.saveWkpAutoCal, command);
        }

        export function saveWkpJobAutoCal(command: model.WkpJobAutoCalSettingDto): JQueryPromise<void> {
            return nts.uk.request.ajax("at", paths.saveWkpJobAutoCal, command);
        }
        
        export function deleteJobAutoCal(jobId: string): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.deleteJobAutoCal, { 'jobId': jobId });
        }

        export function deleteWkpAutoCal(wkpId: string): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.deleteWkpAutoCal, { 'wkpId': wkpId });
        }

        export function deleteWkpJobAutoCal(wkpId: string, jobId: string): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.deleteWkpJobAutoCal, { 'wkpId': wkpId, 'jobId': jobId });
        }
        
        export module model {
            //modelauto

            export interface BaseAutoCalSettingDto{
                normalOTTime: AutoCalOvertimeSettingDto;
                flexOTTime: AutoCalFlexOvertimeSettingDto;
                restTime: AutoCalRestTimeSettingDto;
                leaveEarly: AutoCalcOfLeaveEarlySettingDto;
                raisingSalary: AutoCalRaisingSalarySettingDto;
                divergenceTime: number; 
            }
            
            export interface WkpJobAutoCalSettingDto extends BaseAutoCalSettingDto{
                wkpId: string;
                jobId: string;
            }

            export interface WkpAutoCalSettingDto extends BaseAutoCalSettingDto {
                wkpId: string;
            }

            export interface JobAutoCalSettingDto extends BaseAutoCalSettingDto {
                jobId: string;
            }

            export interface ComAutoCalSettingDto extends BaseAutoCalSettingDto {
            }

            export interface AutoCalFlexOvertimeSettingDto {
                flexOtTime: AutoCalSettingDto;
            }

            export interface AutoCalRestTimeSettingDto {
                restTime: AutoCalSettingDto;
                lateNightTime: AutoCalSettingDto;
            }
            export interface AutoCalOvertimeSettingDto {
                earlyOtTime: AutoCalSettingDto;
                earlyMidOtTime: AutoCalSettingDto;
                normalOtTime: AutoCalSettingDto;
                normalMidOtTime: AutoCalSettingDto;
                legalOtTime: AutoCalSettingDto;
                legalMidOtTime: AutoCalSettingDto;
            }

            export interface AutoCalSettingDto {
                upLimitOtSet: number;
                calAtr: number;
            }
            
            export interface AutoCalcOfLeaveEarlySettingDto {
                late: boolean;
                leaveEarly: boolean;
            }

            export interface AutoCalRaisingSalarySettingDto {
                specificRaisingSalaryCalcAtr: boolean;
                raisingSalaryCalcAtr: boolean;
            }

            export class Enum {
                value: number;
                fieldName: string;
                localizedName: string;

                constructor(value: number, fieldName: string, localizedName: string) {
                    this.value = value;
                    this.fieldName = fieldName;
                    this.localizedName = localizedName;
                }
            }
        }
    }
}