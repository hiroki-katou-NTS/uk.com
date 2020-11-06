module  nts.uk.at.view.kdl046.a.service {
    var paths: any = {
        getData: "bs/employee/kdl046/getData " 
    }

    export function getData(workplaceID: any): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.getData , workplaceID);
    }

}