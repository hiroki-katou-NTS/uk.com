module jhn001.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths = {
        getAll: "hr/notice/person/report/findAll",
        getReportDetails: "hr/notice/report/item/findOne",
        remove:"hr/notice/report/delete/{0}",
        saveDraftData: "hr/notice/report/regis/person/save/draft/save",
        getListDoc: 'hr/notice/report/regis/person/document/findAll',
        getListReportSaveDraft: 'hr/notice/report/regis/person/save/draft/getAll',
        layout: {
            getAll: "ctx/pereg/person/maintenance/findSimple/{0}",
            getDetails: "ctx/pereg/person/maintenance/findLayoutData",
            'register': 'facade/pereg/register'
        }
    };
    

    export function getAll() {
        return ajax(format(paths.getAll));
    }
    
    export function getListReportSaveDraft() {
        return ajax('hr' , paths.getListReportSaveDraft);
    }

    export function getReportDetails(obj: any) {
        return ajax(format(paths.getReportDetails, obj));
    }
    
    export function removeData(reportClsId) {
        return ajax(format(paths.remove, reportClsId));
    }

   
    export function saveData(data: any) {
        return ajax(paths.saveData, data);
    }
    
    export function getCurrentLayout(query: any) {
        return nts.uk.request.ajax('com', "ctx/pereg/person/maintenance/findLayoutData" , query);
    }
    
    export function getListDoc(param: any) {
        return ajax('hr' , paths.getListDoc, param);
    }
    
    export function saveDraftData(command: any) {
        return ajax('hr' , paths.saveDraftData, command);
    }
}