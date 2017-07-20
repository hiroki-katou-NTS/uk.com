module nts.uk.at.view.ksm003.a {
    export module service {
        var paths = {
            getAllPattCalender: "ctx/at/share/vacation/setting/patterncalendar/getallpattcal/",
            addPattCalender: "ctx/at/share/vacation/setting/patterncalendar/addpattcal/",
            //        save: 'ctx/at/share/vacation/setting/nursingleave/save',
            //        findSetting: 'ctx/at/share/vacation/setting/nursingleave/find/setting'
            //         getAllDivReason: "ctx/at/share/vacation/setting/patterncalendar/addpattcal/",
            //         addDivReason: "at/record/divergencetime/adddivreason",
            //         updateDivReason: "at/record/divergencetime/updatedivreason",
            //         deleteDivReason: "at/record/divergencetime/deletedivreason"
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
        //    export function deleteDivReason(divReason: viewmodel.model.Item): JQueryPromise<Array<viewmodel.model.Item>>{
        //        return nts.uk.request.ajax("at", padeleteDivReason,divReason);
        //    }
    
        export module model {

            export interface PatternCalendarDto {
                /** The pattern code. */
                patternCode: string;

                /** The pattern name. */
                patternName: string;

                /** The work type codes. */
                workTypeCodes: string[];

                /** The work house codes. */
                workHouseCodes: string[];

                /** The pattern calendar number day. */
                patternCalendarNumberDay: number;

            }
        }
    }
}