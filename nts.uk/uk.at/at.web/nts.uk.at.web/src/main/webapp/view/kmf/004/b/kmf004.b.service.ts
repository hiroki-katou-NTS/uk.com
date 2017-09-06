module nts.uk.at.view.kmf004.b.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    /**
     *  Service paths
     */
    var servicePath = {
        findByCode: "at/share/grantholidaytbl/findByCode/{0}",
    }  
    
    /**
     *  Find data by codes
     */
    export function findByCode(sphdCd: string): JQueryPromise<Item> {
        var path = nts.uk.text.format(servicePath.findByCode, sphdCd);
        return nts.uk.request.ajax(path);
    }  
    
    export interface Item {
        year: number;
        month: number;
    }
}