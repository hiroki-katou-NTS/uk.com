module nts.uk.at.view.kal003.b.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    var paths = {
            //アルゴリズム「日次の初期起動」を実行する
            
            getAllDailyAttendanceItemBy:             "getAllDailyAttendanceItemBy",
            getOptionItemByAtr:                     "findByAtr",
            getAllAttendanceAndFrameLinking:         "getAllAttendanceAndFrameLinking",
            getAllEnums                     :         "getAllEnums"
                
                
    }
    ////アルゴリズム「日次の初期起動」を実行する

    export function getAllDailyAttendanceItemBy(command) : JQueryPromise<any> {
        return ajax(paths.getAllDailyAttendanceItemBy, command);
    }

    export function getCurrentDefaultRoleSet(command) : JQueryPromise<any> {
        return ajax(paths.getOptionItemByAtr, command);
    }

    // get all Enums:
    export function getAllEnums() : JQueryPromise<any> {
        return ajax(paths.getAllEnums);
    }
    
    
}

