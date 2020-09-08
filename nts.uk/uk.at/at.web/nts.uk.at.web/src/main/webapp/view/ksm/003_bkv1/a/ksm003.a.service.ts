module nts.uk.at.view.ksm003.a {
    export module service {
        var paths = {
            getAllPattCalender: "ctx/at/schedule/shift/pattern/daily/getall",
            addPattCalender: "ctx/at/schedule/shift/pattern/daily/save",
            getPatternValByPatternCd: "ctx/at/schedule/shift/pattern/daily/find",
            deleteDailyPattern: "ctx/at/schedule/shift/pattern/daily/delete",
            findWorkTypeByCodes: "at/share/worktype/findNotDeprecatedByListCode",
            findWorkTimeByCodes: "at/shared/worktimesetting/findByCodes"

        }

        /**
        * get all Patt Calender
        */
        export function getAllPatterns(): JQueryPromise<Array<model.DailyPatternItemDto>> {
            return nts.uk.request.ajax("at", paths.getAllPattCalender);
        }
        /**
        * add Patt Calender
        */
        export function saveDailyPattern(dto: model.DailyPatternDetailDto): JQueryPromise<Array<model.DailyPatternDetailDto>> {
            dto.patternCode = nts.uk.text.padLeft(dto.patternCode, '0', 2);
            return nts.uk.request.ajax("at", paths.addPattCalender, dto);
        }

        /**
      * get Patt Val
      */
        export function getPatternValByPatternCd(patternCd: string): JQueryPromise<model.DailyPatternDetailDto> {
            return nts.uk.request.ajax("at", paths.getPatternValByPatternCd + '/' + patternCd);
        }

        /**
         * delete divergence reason
        */
        export function deleteDailyPattern(patternCd: string): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.deleteDailyPattern + '/' + patternCd);
        }


        /**
         * findByIdWorkTime
        */
        export function findWorkTimes(workTimeCodes: Array<string>): JQueryPromise<Array<model.WorkTimeDto>> {
            return nts.uk.request.ajax("at", paths.findWorkTimeByCodes, workTimeCodes);
        }


        /**
         * findByIdWorkType
        */
        export function findWorkTypes(workTypeCodes: Array<string>): JQueryPromise<Array<model.WorkTypeDto>> {
            return nts.uk.request.ajax("at", paths.findWorkTypeByCodes, workTypeCodes);
        }
        /**
         * Export File Excel
         */
        export function exportExcel(): JQueryPromise<any> {
            let program = nts.uk.ui._viewModel.kiban.programName().split(" ");
            let domainType = "KSM003";
            if (program.length > 1){
                program.shift();
                domainType = domainType + program.join(" ");
            }
            return nts.uk.request.exportFile('/masterlist/report/print', {domainId: "RegisterPattern", domainType: domainType, languageId: 'ja', reportType: 0});
        }

        export module model {

            export interface DailyPatternItemDto {
                patternCode: string;
                patternName: string;
            }

            export class DailyPatternDetailDto {
                isEditting: boolean = true;
                patternCode: string;
                patternName: string;
                dailyPatternVals: Array<DailyPatternValDto>;

                constructor(patternCode: string, patternName: string, dailyPatternVals: Array<DailyPatternValDto>) {
                    this.patternCode = patternCode;
                    this.patternName = patternName;
                    this.dailyPatternVals = dailyPatternVals;
                }
            }

            export class DailyPatternValDto {
                dispOrder: number;
                workTypeSetCd: string;
                workTypeName: string;
                workingHoursCd: string;
                workingHoursName: string;
                days: number;

                constructor(dispOrder: number, workTypeSetCd: string, workingHoursCd: string, days: number) {
                    this.dispOrder = dispOrder;
                    this.workTypeSetCd = workTypeSetCd;
                    this.workingHoursCd = workingHoursCd;
                    this.days = days;
                }

                updateDto(workTime: WorkTimeDto, workType: WorkTypeDto): void {
                    this.workTypeSetCd = workType.workTypeCode;
                    this.workTypeName = workType.name;
                    this.workingHoursCd = workTime.code;
                    this.workingHoursName = workTime.name;
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