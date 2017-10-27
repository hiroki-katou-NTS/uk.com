module nts.uk.at.view.kmk009.a {
    export module service {

        var paths = {
            getAllTotalTimes: "ctx/at/schedule/shift/totaltimes/getallitem",
            getAllTotalTimesDetail: "ctx/at/schedule/shift/totaltimes/getdetail/",
            totalClassification: "ctx/at/schedule/shift/totaltimes/find/totalclassification",
            totalUseEnum: "ctx/at/schedule/shift/totaltimes/find/totalUseEnum",
            saveAllTotalTimes: "ctx/at/schedule/shift/totaltimes/save",
            findByIdWorkType: "at/share/worktype/findById",
            findByIdWorkTime: "at/shared/worktime/findById",
            findByIdListWorkTypes: "at/share/worktype/getpossibleworktype",
            findByIdlistWorkTimes: "at/shared/worktime/findByCodeList",
            findAlldWorkType: "at/share/worktype/findAll",
            findAllWorkTime: "at/shared/worktime/findAll"

        }

        /**
        * get all total times
        */
        export function getAllTotalTimes(): JQueryPromise<Array<model.TotalTimesDto>> {
            return nts.uk.request.ajax("at", paths.getAllTotalTimes);
        }
        export function getTotalClsEnum(): JQueryPromise<Array<model.Enum>> {
            return nts.uk.request.ajax(paths.totalClassification);
        }
        
        export function getTotalUseEnum(): JQueryPromise<Array<model.EnumUse>> {
            return nts.uk.request.ajax(paths.totalUseEnum);
        }

        export function getAllTotalTimesDetail(totalTimesNo: number): JQueryPromise<model.TotalTimesDetailDto> {
            return nts.uk.request.ajax("at", paths.getAllTotalTimesDetail + totalTimesNo);
        }
        /**
       * save
       */
        export function saveAllTotalTimes(command: model.TotalTimesDetailDto): JQueryPromise<void> {
            console.log(command);
            return nts.uk.request.ajax("at", paths.saveAllTotalTimes, command);
        }

        /**
            * findByIdWorkTime
           */
        export function findByIdWorkTime(workTimeCode: string): JQueryPromise<model.WorkTimeDto> {
            return nts.uk.request.ajax("at", paths.findByIdWorkTime + '/' + workTimeCode);
        }


        /**
         * findByIdWorkType
        */
        export function findByIdWorkType(workTypeCode: string): JQueryPromise<model.WorkTypeDto> {
            return nts.uk.request.ajax("at", paths.findByIdWorkType + '/' + workTypeCode);
        }

        /**
          * findListByIdWorkTime
           */
        export function findListByIdWorkTimes(workTimeCodes: Array<string>): JQueryPromise<Array<model.WorkTimeDto>> {
            return nts.uk.request.ajax(paths.findByIdlistWorkTimes, workTimeCodes);
        }


        /**
         * findListByIdWorkType
        */
        export function findListByIdWorkTypes(workTypeCodes: string[]): JQueryPromise<Array<model.WorkTypeDto>> {
            return nts.uk.request.ajax("at", paths.findByIdListWorkTypes, workTypeCodes);
        }

        /**
         * findListByIdWorkTime
          */
        export function findAllWorkTimes(): JQueryPromise<Array<model.WorkTimeDto>> {
            return nts.uk.request.ajax(paths.findAllWorkTime);
        }


        /**
         * findListByIdWorkType
        */
        export function findAllWorkTypes(): JQueryPromise<Array<model.WorkTypeDto>> {
            return nts.uk.request.ajax("at", paths.findAlldWorkType);
        }



        export module model {
            
            export interface TotalTimesDto {
                totalCountNo: number;
                summaryAtr: number;
                useAtr: number;
                totalTimesName: string;
            }
            export interface TotalSubjectsDto{
                workTypeCode: string;
                workTypeAtr: number;    
            }
            
            export interface TotalTimesDetailDto {
                totalCountNo: number;
                countAtr: number;
                useAtr: number;
                totalTimesName: string;
                totalTimesABName: string;
                summaryAtr: number;
                totalCondition: TotalConditionDto;
                listTotalSubjects: TotalSubjectsDto[];
            }
            
            export interface TotalConditionDto {
                upperLimitSettingAtr: number;
                lowerLimitSettingAtr: number;
                thresoldUpperLimit: number;
                thresoldLowerLimit: number;
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
            
            export class EnumUse {
                value: number;
                fieldName: string;
                localizedName: string;

                constructor(value: number, fieldName: string, localizedName: string) {
                    this.value = value;
                    this.fieldName = fieldName;
                    this.localizedName = localizedName;
                }
            }

            export interface WorkTypeDto {
                workTypeCode: string;
                name: string;
            }

            export interface WorkTimeDto {
                code: string;
                name: string;

            }
        }
    }
}