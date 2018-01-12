module nts.uk.at.view.kmf022.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        //会社別申請承認設定
        findAll: "at/request/application/requestofearch/getrequestofearch",
        // A4_6
        find: "at/request/application/getalldatabyclosureId",
        // A14_3
        findJobAssign: "job/assign/setting/getjob",
        //A15_4
        findJobTitleSearch: "workflow/jobtitlesearchset/job/getbyId",
        // A17_5
        findAppro: "approval/setting/approval",
        // A17_4
        findAppSet: "at/request/application/setting"
//        add: 'at/shared/yearservicecom/add',
//        update: 'at/shared/yearservicecom/update'
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
//    export function add(command: Array<d.viewmodel.Item>): JQueryPromise<void>{
//        return ajax("at", paths.add, command);
//    }
//
//    export function update(command): JQueryPromise<Array<string>>{
//        return ajax("at", paths.update, command);
//    }    
}