module nts.uk.at.view.kmk007.c.service {
    var servicePath: any = {
        order: "at/share/worktype/order",
    }
    
    /**
     *  Save order data
     */
    export function order(data: Array<viewmodel.IWorkTypeDispOrder>): JQueryPromise<any> {
        var path = nts.uk.text.format(servicePath.order);
        return nts.uk.request.ajax("at", path, data);
    } 
    
    
}