module nts.uk.at.view.kmf022.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        //会社別申請承認設定
        findAll: "at/request/application/requestofearch/getrequestofearch",
        // update/insert: RequestOfEachCompanyCommandHandler
        //A4_5: /at/shared/workrule/closure/history/findAll
        // A4_6
        find: "at/request/application/getalldatabyclosureId",
//        updateClosure: "at/request/application/update",
        addClosure: "at/request/application/add",
        // A14_3
        findJobAssign: "job/assign/setting/getjob",
        //A15_4
        findJobTitleSearch: "workflow/jobtitlesearchset/job/getbyId",
        // A17_5
        findAppro: "approval/setting/approval",
        // A17_4, A9_5
        findAppSet: "at/request/application/setting/appset",
        //E14,15
        findStamp: "at/request/application/stamprequest/findByComID", //UpdateStampRequestSettingCommandHandler
        // F10->F16
        findDirectlycommon: "at/request/application/gobackdirectlycommon/getGoBackCommonByCid" //GoBackDirectlyCommonSettingRepository
        
//        add: 'at/shared/yearservicecom/add',
//        update: 'at/shared/yearservicecom/update'
    }
    
    export function findDirectlycommon() {
        return nts.uk.request.ajax("at", paths.findDirectlycommon); 
    }
    
    export function findStamp() {
        return nts.uk.request.ajax("at", paths.findStamp); 
    }
    
    export function findAppSetting() {
        return nts.uk.request.ajax("at", paths.findAppSetting); 
    }
    
    export function findAll() {
        return nts.uk.request.ajax("at", paths.findAll); 
    }
    
    export function findApp(id: number): JQueryPromise<void>{
        return nts.uk.request.ajax("at", paths.find + "/" + id);     
    }
    
    export function findJobAssign() {
        return nts.uk.request.ajax("com", paths.findJobAssign); 
    }

    export function findJobTitle(id: string): JQueryPromise<void>{
        return nts.uk.request.ajax("com", paths.findJobTitleSearch + "/" + id);     
    }
    
    export function findAppro() {
        return nts.uk.request.ajax("com", paths.findAppro); 
    }
    
    export function findAppSet() {
        return nts.uk.request.ajax("at", paths.findAppSet); 
    }
    
    export function updateClosure(command: any): JQueryPromise<void>{
        return nts.uk.request.ajax(paths.updateClosure, command);    
    }
    
    export function addClosure(command: any): JQueryPromise<void>{
        return nts.uk.request.ajax(paths.addClosure, command);    
    }
//    export function add(command: Array<d.viewmodel.Item>): JQueryPromise<void>{
//        return ajax("at", paths.add, command);
//    }
//
//    export function update(command): JQueryPromise<Array<string>>{
//        return ajax("at", paths.update, command);
//    }    
}