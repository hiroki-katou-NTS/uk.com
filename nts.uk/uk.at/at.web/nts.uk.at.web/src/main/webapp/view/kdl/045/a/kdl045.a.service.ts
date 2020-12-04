module nts.uk.at.view.kdl045.a {
    export module service {
        var paths: any = {
            getInformationStartup: "at/screen/kdl045/query/getInformationStartup",
            getMoreInformation: "at/screen/kdl045/query/getMoreInformation",
            checkTimeIsIncorrect: "ctx/at/shared/workrule/workinghours/checkTimeIsIncorrect"
        }
        
        export function getInformationStartup(command): JQueryPromise<any> {
            return nts.uk.request.ajax("at",paths.getInformationStartup,command);
        }
        
        export function getMoreInformation(command): JQueryPromise<any> {
            return nts.uk.request.ajax( "at", paths.getMoreInformation, command);
        }
        
         export function checkTimeIsIncorrect(command): JQueryPromise<any> {
            return nts.uk.request.ajax( "at", paths.checkTimeIsIncorrect, command);
        }
    }
}