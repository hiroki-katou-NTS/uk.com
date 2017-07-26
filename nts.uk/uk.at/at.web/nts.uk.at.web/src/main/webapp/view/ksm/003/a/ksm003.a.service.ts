module nts.uk.at.view.ksm003.a {
    export module service {
        var paths = {
            getAllPattCalender: "ctx/at/share/vacation/setting/patterncalendar/getallpattcal",
            addPattCalender: "ctx/at/share/vacation/setting/patterncalendar/addpattcal",
            getPatternValByPatternCd: "ctx/at/share/vacation/setting/patternval/find/patternval",
            //        save: 'ctx/at/share/vacation/setting/nursingleave/save',
            //        findSetting: 'ctx/at/share/vacation/setting/nursingleave/find/setting'
            //         getAllDivReason: "ctx/at/share/vacation/setting/patterncalendar/addpattcal/",
            //         addDivReason: "at/record/divergencetime/adddivreason",
            //         updateDivReason: "at/record/divergencetime/updatedivreason",
            deleteDailyPattern: "ctx/at/share/vacation/setting/patterncalendar/deleted/pattern"
        }

        /**
        * get all Patt Calender
        */
        export function getAllPattCalender(): JQueryPromise<Array<viewmodel.model.Item>> {
            return nts.uk.request.ajax("at", paths.getAllPattCalender);
        }
        /**
        * add Patt Calender
        */
        export function addPattCalender(dto: model.PatternCalendarDto): JQueryPromise<Array<viewmodel.model.Item>> {
            return nts.uk.request.ajax("at", paths.addPattCalender, { patternCalendarDto: dto });
        }

        /**
      * get Patt Val
      */
        export function getPatternValByPatternCd(patternCd: string): JQueryPromise<model.DailyPatternVal[]> {
            return nts.uk.request.ajax("at", paths.getPatternValByPatternCd + '/' + patternCd);
        }
        //        export function addPattCalender(divReason: viewmodel.model.Item): JQueryPromise<Array<viewmodel.model.Item>> {
        //            return nts.uk.request.ajax("at", paths.addPattCalender, divReason);
        //        }
        //    /**
        //    * update divergence reason
        //    */ 
        //    export function updateDivReason(divReason: viewmodel.model.Item): JQueryPromise<Array<viewmodel.model.Item>>{
        //        return nts.uk.request.ajax("at", paths.updateDivReason,divReason);
        //    }
        //    /**
        //    * delete divergence reason
        //    */ 
        export function deleteDailyPattern(patternCd: string): JQueryPromise<Array<viewmodel.model.Item>>{
           return nts.uk.request.ajax("at", paths.deleteDailyPattern + '/' + patternCd);
        }

        export module model {

            export interface PatternCalendarDto {
                /** The pattern code. */
                patternCode: string;

                /** The pattern name. */
                patternName: string;

                listDailyPatternVal: DailyPatternVal[];

            }

            export interface DailyPatternVal {
                /** The pattern code. */
                cid: string;

                /** The pattern name. */
                patternCode: string;

                /** The pattern name. */
                dispOrder: number;

                /** The work type codes. */
                workTypeSetCd: string;

                /** The work house codes. */
                workingHoursCd: string;

                /** The pattern name. */
                days: number;

            }

        }
    }
}