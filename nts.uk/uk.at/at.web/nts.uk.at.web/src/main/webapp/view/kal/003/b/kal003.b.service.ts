module nts.uk.com.view.kal003.b.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    var paths = {
            //アルゴリズム「日次の初期起動」を実行する
            
            getAllDailyAttendanceItemBy:             "getAllDailyAttendanceItemBy",
            getOptionItemByAtr:                     "findByAtr",
            getAllAttendanceAndFrameLinking:         "getAllAttendanceAndFrameLinking"
                
                
    }
    ////アルゴリズム「日次の初期起動」を実行する

    export function getAllDailyAttendanceItemBy(command) : JQueryPromise<any> {
        return ajax(paths.getAllDailyAttendanceItemBy, command);
    }

    export function getCurrentDefaultRoleSet(command) : JQueryPromise<any> {
        return ajax(paths.getOptionItemByAtr, command);
    }

    // add Default Role Set:
    export function getAllAttendanceAndFrameLinking(command) : JQueryPromise<any> {
        return ajax(paths.getAllAttendanceAndFrameLinking, command);
    }
}

