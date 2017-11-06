module nts.uk.at.view.kml002.f.service {
    /**
     *  Service paths
     */
    var servicePath : any = {
        findAll: "at/schedule/budget/external/findallexternalbudget",
    }  
    export function findAll(): JQueryPromise<Array<any> > {
        return nts.uk.request.ajax("at",servicePath.findAll);
    }
}
