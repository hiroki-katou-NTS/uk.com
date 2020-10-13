module nts.uk.at.view.kdl045.a {
    export module service {
        var paths: any = {
            isMultipleManagement: "shared/selection/func/settingworkmultiple/get",
            startUp: "",
            getShiftMaster: "ctx/at/shared/workrule/shiftmaster/getlistByWorkPlace"
        }
        
        export function isMultipleManagement(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.isMultipleManagement);
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