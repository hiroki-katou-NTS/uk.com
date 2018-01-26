module nts.uk.at.view.kmf004.d.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        find: "at/shared/yearserviceset/findAll/",
        findCom: "at/shared/yearservicecom/findAll/",
        add: 'at/shared/yearservicecom/add',
        update: 'at/shared/yearservicecom/update'
    }

    export function findAll(specialHolidayCode: String) : JQueryPromise<any>{
        return nts.uk.request.ajax(paths.find + specialHolidayCode);
    }
    
    export function findCom(specialHolidayCode: String) : JQueryPromise<any>{
        return nts.uk.request.ajax(paths.findCom + specialHolidayCode);
    }

    export function add(command: Array<d.viewmodel.Item>): JQueryPromise<void>{
        return ajax("at", paths.add, command);
    }

    export function update(command): JQueryPromise<Array<string>>{
        return ajax("at", paths.update, command);
    }    
}