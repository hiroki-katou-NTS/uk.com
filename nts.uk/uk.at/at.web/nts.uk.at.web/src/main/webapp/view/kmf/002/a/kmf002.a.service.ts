module nts.uk.at.view.kmf002.a {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
                save: "bs/employee/holidaysetting/config/save",
                findAll: "bs/employee/holidaysetting/config/find",
                getPubHDPeriodEnum: "bs/employee/holidaysetting/config/enum/publicholidayperiod",
                getDayOfPubHDEnum: "bs/employee/holidaysetting/config/enum/dayofpublicholiday",
                getPubHDManageClassificationEnum: "bs/employee/holidaysetting/config/enum/pubhdmanagementatr",
                getPublicHolidayCarryOverDeadline: "bs/employee/holidaysetting/config/enum/publicholidaycarryoverdeadline",
                getDaysOfTheWeek: "bs/employee/holidaysetting/config/enum/dayofweek",
                findAllManageUseUnit: "bs/employee/publicholidaymanagementusageunit/find",
                saveManageUnit: "bs/employee/publicholidaymanagementusageunit/save",
                getManageEnum: "bs/employee/holidaysetting/config/enum/manage"
            };
        
        export function save(pubHdSet: any, forwardSetOfPubHD: any, weekHDSet: any, fourWkFourHDNumSet: any): JQueryPromise<any> {
            let data:any = toDto(pubHdSet, forwardSetOfPubHD, weekHDSet, fourWkFourHDNumSet);
            return nts.uk.request.ajax("at", path.save, ko.toJSON(data));
        }
        
        export function findAll(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.findAll);
        }
        
        export function getPubHDPeriodEnum(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.getPubHDPeriodEnum);
        }
        
        export function getDayOfPubHDEnum(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.getDayOfPubHDEnum);
        }
        
        export function getPubHDManageClassificationEnum(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.getPubHDManageClassificationEnum);
        }
        
        export function getManageEnum(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.getManageEnum);
        }
        
        export function getPublicHolidayCarryOverDeadline(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.getPublicHolidayCarryOverDeadline);
        }
        
        export function getDaysOfTheWeek(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.getDaysOfTheWeek);
        }
        
        export function findAllManageUseUnit(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.findAllManageUseUnit);
        }  
        
        export function saveManageUnit(isManageEmployeePublicHd: number, isManageWkpPublicHd: number, isManageEmpPublicHd: number): JQueryPromise<any> {
            let data: any = {};
            data.isManageEmployeePublicHd = isManageEmployeePublicHd; 
            data.isManageWkpPublicHd = isManageWkpPublicHd;
            data.isManageEmpPublicHd = isManageEmpPublicHd;
            return nts.uk.request.ajax("at", path.saveManageUnit, data);
        }
        
        export function toDto(pubHdSet: any, forwardSetOfPubHD: any, weekHDSet: any, fourWkFourHDNumSet: any): any {
            let dto: any = {};
            dto.forwardSetOfPubHd = new Domain.ForwardSetOfPubHd(forwardSetOfPubHD.isTransferWhenPublicHdIsMinus(), forwardSetOfPubHD.carryOverDeadline());
            dto.fourWeekfourHdNumbSet = new Domain.FourWeekfourHdNumbSet
                                                        (
                                                            fourWkFourHDNumSet.isOneWeekHoliday(),
                                                            fourWkFourHDNumSet.isFourWeekHoliday(),
                                                            fourWkFourHDNumSet.oneWeek().inLegalHoliday(),
                                                            fourWkFourHDNumSet.oneWeek().outLegalHoliday(),
                                                            fourWkFourHDNumSet.oneWeek().lastWeekAddedDays().inLegalHoliday(),
                                                            fourWkFourHDNumSet.oneWeek().lastWeekAddedDays().outLegalHoliday(),
                                                            fourWkFourHDNumSet.fourWeek().inLegalHoliday(),
                                                            fourWkFourHDNumSet.fourWeek().outLegalHoliday(),
                                                            fourWkFourHDNumSet.fourWeek().lastWeekAddedDays().inLegalHoliday(),
                                                            fourWkFourHDNumSet.fourWeek().lastWeekAddedDays().outLegalHoliday()
                                                        );
            dto.weekHdSet = new Domain.WeekHdSet(weekHDSet.inLegalHoliday(), weekHDSet.outLegalHoliday(), weekHDSet.startDay());
            dto.pubHdSet = new Domain.PubHdSet(pubHdSet.isManageComPublicHd(), pubHdSet.publicHdManagementClassification(), 
                                    pubHdSet.isWeeklyHdCheck(), pubHdSet.pubHDGrantDate().period(), pubHdSet.pubHD().date(), 
                                    pubHdSet.pubHD().dayMonth(), pubHdSet.pubHD().determineStartDate());
            return dto;
        }
    }
    
    export module Domain{
        export class ForwardSetOfPubHd{
            isTransferWhenPublicHdIsMinus: number;
            carryOverDeadline: number;
        
            constructor(isTransferWhenPublicHdIsMinus: number, carryOverDeadline: number) {
                this.isTransferWhenPublicHdIsMinus = isTransferWhenPublicHdIsMinus;
                this.carryOverDeadline = carryOverDeadline;
            }
        }
        
        export class FourWeekfourHdNumbSet{
            isOneWeekHoliday: number;
            isFourWeekHoliday: number;
            inLegalHdLwhnoow: number;
            outLegalHdLwhnoow: number;
            inLegalHdOwph: number;
            outLegalHdOwph: number;
            inLegalHdLwhnofw: number;
            outLegalHdLwhnofw: number;
            inLegelHdFwph: number;
            outLegelHdFwph: number;
        
            constructor(isOneWeekHoliday: number, isFourWeekHoliday: number, inLegalHdOwph: number, 
                        outLegalHdOwph: number, inLegalHdLwhnoow: number, outLegalHdLwhnoow: number,  
                        inLegelHdFwph: number, outLegelHdFwph: number, inLegalHdLwhnofw: number, 
                        outLegalHdLwhnofw: number) {
                this.isOneWeekHoliday = isOneWeekHoliday;
                this.isFourWeekHoliday = isFourWeekHoliday;
                this.inLegalHdLwhnoow = inLegalHdLwhnoow;
                this.outLegalHdLwhnoow = outLegalHdLwhnoow;
                this.inLegalHdOwph = inLegalHdOwph;
                this.outLegalHdOwph = outLegalHdOwph;
                this.inLegalHdLwhnofw = inLegalHdLwhnofw;
                this.outLegalHdLwhnofw = outLegalHdLwhnofw;
                this.inLegelHdFwph = inLegelHdFwph;
                this.outLegelHdFwph = outLegelHdFwph;    
            }
        }
        
        export class WeekHdSet{
            inLegalHoliday: number;
            outLegalHoliday: number;
            startDay: number;
        
            constructor(inLegalHoliday: number, outLegalHoliday: number, startDay: number) {
                this.inLegalHoliday = inLegalHoliday;
                this.outLegalHoliday = outLegalHoliday;
                this.startDay = startDay;
            }
        }
        
        export class PubHdSet{
            isManageComPublicHd: number;
            publicHdManagementClassification: number;
            isWeeklyHdCheck: number;
            period: number;
            fullDate: number;
            dayMonth: number;
            determineStartD: number;
        
            constructor(isManageComPublicHd: number, publicHdManagementClassification: number, 
                        isWeeklyHdCheck: number, period: number, fullDate: number, dayMonth: number, 
                        determineStartD: number) {
                this.isManageComPublicHd = isManageComPublicHd;
                this.publicHdManagementClassification = publicHdManagementClassification;
                this.isWeeklyHdCheck = isWeeklyHdCheck;
                this.period = period;
                this.fullDate = fullDate;
                this.dayMonth = dayMonth;
                this.determineStartD = determineStartD;
            }
        }
    }
}