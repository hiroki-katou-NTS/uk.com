module nts.uk.com.view.cps001.i.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        'getAllList': 'record/remainnumber/resv-lea/get-resv-lea/{0}',
        'getDetail': 'at/record/remainnumber/special/get-detail/{0}',
        'save': 'at/record/remainnumber/special/save',
        'delete': 'at/record/remainnumber/special/delete/{0}'
    };

    export function getAllList() {
        // param sid: string , spicialCode : number
        let employeeId: string = "a";
         return ajax('at',format(paths.getAllList, employeeId));
    }

    export function saveData(command: any) {
        return ajax(paths.save, command);
    }

    export function getDetail(specialid: any) {
        return ajax(paths.getDetails,specialid );
    }

    export function remove(specialid: any) {
        return ajax(format(paths.delete, specialid));
    }
     export function getItemDef(ctgCd : string){
        return ajax('com',format("ctx/pereg/person/info/ctgItem/findby/ctg-cd/{0}", ctgCd));
    }
}