module nts.uk.com.view.cmm049.a {

    export module service {

        /**
            *  Service paths
            */
        var servicePath: any = {
            getAllEnum: "sys/env/userinfoset/getAllEnum",
        }

        // Screen B
        export function getAllEnum(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.getAllEnum);
        }

//        export function findListWindowAccByUserId(userId: string): JQueryPromise<model.WindownAccountFinderDto[]> {
//            return nts.uk.request.ajax(servicePath.findListWindowAccByUserId, { userId: userId });
//        }
//
//        export function saveWindowAccount(saveWindowAcc: model.SaveWindowAccountCommand): JQueryPromise<any> {
//            return nts.uk.request.ajax(servicePath.saveWindowAccount, saveWindowAcc);
//        }


    }
}