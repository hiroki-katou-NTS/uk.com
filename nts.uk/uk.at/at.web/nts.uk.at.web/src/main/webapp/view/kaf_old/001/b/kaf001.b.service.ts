module nts.uk.at.view.kaf001.b.service {
    var paths: any = {
        getNameDis : "at/request/application/displayname/app/"
    }

    //get name display from domain 
    export function getNameDis(appType: number): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getNameDis + appType);
    }
}