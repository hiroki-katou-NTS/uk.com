module nts.uk.at.view.kdl044.a {
    export module service {
        var paths: any = {
            isMultipleManagement: "",
            startUp: "",
            getShiftMaster: "ctx/at/shared/workrule/shiftmaster/getlistByWorkPlace"
        }
        
        export function isMultipleManagement(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.isMultipleManagement, command);
        }
        
        export function getShiftMaster(command): JQueryPromise<any> {
            return nts.uk.request.ajax( "at", paths.getShiftMaster, command);
        }
        
        export function startUp(command): JQueryPromise<any> {
            let dfd = $.Deferred();
            dfd.resolve(true);
            return dfd.promise();
            //return nts.uk.request.ajax(paths.startUp, command);
        }
    }
}