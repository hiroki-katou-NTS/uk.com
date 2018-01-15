module nts.uk.at.view.kmk013.c {
    export module service {
        let paths: any = {
            findByCId : "shared/caculation/holiday/time/findByCid",
            save :"shared/caculation/holiday/time/add",
            findAllWorkDayOffFrame :"at/shared/workdayoffframe/findAll",
            findAllOvertimeWorkFrame:"at/shared/overtimeworkframe/findAll"
        }
        export function findByCompanyId(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findByCId);
        }
        export function save(obj): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.save,obj);
        }
        export function findAllWorkDayOffFrame() :JQueryPromise<any>{
            return nts.uk.request.ajax(paths.findAllWorkDayOffFrame);  
        }
        export function findAllOvertimeWorkFrame():JQueryPromise<any>{
            return nts.uk.request.ajax(paths.findAllOvertimeWorkFrame);  
        }

    }
}