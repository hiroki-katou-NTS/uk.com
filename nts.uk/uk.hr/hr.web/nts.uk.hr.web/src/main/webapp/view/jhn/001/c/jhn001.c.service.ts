module jhn001.c.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    

    let paths = {
        getAll: "hr/notice/report/findAll/{0}",
        getDetails: "hr/notice/report/item/findOne",
        remove:"hr/notice/report/delete/{0}",
        saveData: "hr/notice/report/regis/person/saveScreenC",
        layout: {
            getAll: "ctx/pereg/person/maintenance/findSimple/{0}",
            getDetails: "ctx/pereg/person/maintenance/findLayoutData",
            'register': 'facade/pereg/register'
        }
    };
    

    /**
    * Get list Maintenance Layout
    */
    export function getAll(abolition) {
        return ajax(format(paths.getAll, abolition));
    }

    export function getDetails(obj: any) {
        return ajax(paths.getDetails, obj);
    }
    
    export function removeData(reportClsId) {
        return ajax(format(paths.remove, reportClsId));
    }
    

   /**
    * add  Maintenance Layout
    */
    export function saveData(data: any) {
        return ajax(paths.saveData, data);
    }
    
    export function getCurrentLayout(query: any) {
        return nts.uk.request.ajax('com', "ctx/pereg/person/maintenance/findLayoutData" , query);
    }
}