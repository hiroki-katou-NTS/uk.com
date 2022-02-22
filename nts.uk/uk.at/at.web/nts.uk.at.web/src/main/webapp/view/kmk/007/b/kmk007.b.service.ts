module nts.uk.at.view.kmk007.b.service {
    var servicePath: any = {
        getAllAbsenceFrame: "at/share/worktype/absenceframe/findAll",
        getAllSpecialHolidayFrame: "at/share/worktype/specialholidayframe/findAll",
        findAbsenceFrameByCode: "at/share/worktype/absenceframe/findAbsenceFrameByCode/{0}",
        findHolidayFrameByCode: "at/share/worktype/specialholidayframe/findHolidayFrameByCode/{0}",
        updateAbsenceFrame: "at/share/worktype/absenceframe/updateAbsenceFrame",
        updateSpecialHolidayFrame: "at/share/worktype/specialholidayframe/updateSpecialHolidayFrame",
        findAll: "at/shared/scherec/leaveCount/findAll"
    }
    
    /**
     *  Get all Absence Frame
     */
    export function getAllAbsenceFrame(): JQueryPromise<Array<any>> {
        var path = servicePath.getAllAbsenceFrame;
        return nts.uk.request.ajax("at", path);
    }
    
    /**
     *  Get all Special Holiday Frame
     */
    export function getAllSpecialHolidayFrame(): JQueryPromise<Array<any>> {
        var path = servicePath.getAllSpecialHolidayFrame;
        return nts.uk.request.ajax("at", path);
    }
    
    /**
     *  Find Absence Frame by code
     */
    export function findAbsenceFrameByCode(frameNo: number): JQueryPromise<AbsenceFrameItem> {
        var path = nts.uk.text.format(servicePath.findAbsenceFrameByCode, frameNo);
        return nts.uk.request.ajax("at", path);
    } 
    
    /**
     *  Find Special Holiday Frame by code
     */
    export function findHolidayFrameByCode(frameNo: number): JQueryPromise<HolidayFrameItem> {
        var path = nts.uk.text.format(servicePath.findHolidayFrameByCode, frameNo);
        return nts.uk.request.ajax("at", path);
    } 
    
    /**
     *  Update Absence Frame
     */
    export function updateAbsenceFrame(data: AbsenceFrameDto): JQueryPromise<any> {
        return nts.uk.request.ajax("at", servicePath.updateAbsenceFrame, data);
    }  
    
    /**
     *  Update Special Holiday Frame
     */
    export function updateSpecialHolidayFrame(data: HolidayFrameDto): JQueryPromise<any> {
        return nts.uk.request.ajax("at", servicePath.updateSpecialHolidayFrame, data);
    }  

    export function getAllLeaveCount(): JQueryPromise<Array<any>> {
        var path = servicePath.findAll;
        return nts.uk.request.ajax("at", path);
    }
    
    export interface HolidayFrameItem {
        specialHdFrameName: string,
        deprecateSpecialHd: number
    }
    
    export interface AbsenceFrameItem {
        absenceFrameName: string,
        deprecateAbsence: number
    }
    
    export interface HolidayFrameDto {
        specialHdFrameNo: number,
        specialHdFrameName: string,
        deprecateSpecialHd: number
    }
    
    export interface AbsenceFrameDto {
        absenceFrameNo: number,
        absenceFrameName: string,
        deprecateAbsence: number
    }
}