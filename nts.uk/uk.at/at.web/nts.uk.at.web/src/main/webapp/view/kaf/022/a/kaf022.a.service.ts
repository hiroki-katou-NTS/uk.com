module nts.uk.at.view.kmf022.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        //会社別申請承認設定
        findAll: "at/request/application/requestofearch/getrequestofearch",
        // update/insert: RequestOfEachCompanyCommandHandler
        //A4_5: 
        findAllClosure: "ctx/at/shared/workrule/closure/history/findAll",
        // A4_6
        find: "at/request/application/getalldatabyclosureId",
//        updateClosure: "at/request/application/update",
        addClosure: "at/request/application/add",
        // A5_14 -> A5_25
        findApproSet: "at/request/application/common/setting/appcommon",
        // A10_3
        findAppCom: "at/request/application/common/setting/appset",
        // A13_4 có 2 sự lựa chọn, có thể lấy list cty đang đăng nhập rồi lên UI lọc, hoặc lấy thẳng 1 object(nhớ truyền apptype) 
        findAllPro: "at/request/application/setting/proxy/findAll",
        findProByApp: "at/request/application/setting/proxy/findApp",
        // A14_3 
        findJobAssign: "job/assign/setting/getjob",
        //A15_4, truyền lần lượt job id trong list lấy được để lấy ra A15_4. (ver 2)
        findJobTitleSearch: "workflow/jobtitlesearchset/job/getbyId",
        // A16_7, A16_8
        findMail: "at/request/application/mail/holiday",
        // A16_9, A16_10
        findOt: "at/request/application/mail/ot",
        // A16_11
        findTemp: "at/request/application/mail/template",
        // A17_5
        findAppro: "approval/setting/approval",
        // A17_4, A9_5
        findAppSet: "at/request/application/setting/appset",
        // A6_24, A6_26, A6_35->A6_45 có thể lấy list cty đang đăng nhập rồi lên UI lọc, hoặc lấy thẳng 1 object(nhớ truyền apptype)
        findDisp: "at/request/application/displayname/disp",
        fidnDispApp: "at/request/application/displayname/app",
        // A6_27 -> A6_34 có thể lấy theo company rồi lên lọc hoặc lấy thẳng object bằng cả holiday app type
        findAllHdApp: "at/request/application/displayname/hd",
        findHdApp: "at/request/application/displayname/hdapp",
        //E14,15, E17,18 cũng lấy ở đây (ver2)   màn J cùng link này (ver 3)
        findStamp: "at/request/application/stamprequest/findByComID", //UpdateStampRequestSettingCommandHandler
        // F10->F15, F16, 16_1, 16_2 (ver2)
        findDirectlycommon: "at/request/application/gobackdirectlycommon/getGoBackCommonByCid", //GoBackDirectlyCommonSettingRepository

        //B18 -> B36
        findOvertime: "at/request/application/overtime/ot",
        
        // C27 -> C49 
        findAllVaca: "at/request/vacation/setting/hdapp",
        
        //D
        findWorkChange: "at/request/application/workchange/workChangeSet",
        
        // E9->E13, E16
        findTrip: "at/request/application/triprequest/disp",
        
        // G16 -> G29
        findWith: "at/approverset/hdwork/getbycom",
        
        // h
        findTimeHd: "at/hdapplication/setting/getbycid",
        
        // K15, 16, 21, 22 (hết màn K)
        findDraw: "at/request/application/withdrawalrequestset/findByCompanyID",
        
        // I
        findLateEarly: "at/request/application/applicationlatearrival/findByCompanyID",
        
        //jobId. Muốn lấy được jobid thì phải truyền companyId và baseDate
        findJobId: "bs/employee/jobtitle/findAll",
        // lấy xong list job Id thì có thể truyền cả list jobId để lấy list A15_4
        findJobTitleSearchList: "workflow/jobtitlesearchset/job/getbyCode",
        
//        add: 'at/shared/yearservicecom/add',
//        update: 'at/shared/yearservicecom/update'
    }
    
    export function findJobTitleSearchList(param: any): JQueryPromise<void>{
        return nts.uk.request.ajax("com", paths.findJobTitleSearchList, param);    
    }
    
    // view model cho thêm đối tượng Kcp003Dto chỉ có 1 trường baseDate: truyền ngày hiện tại
    export function findJobId(date: any): JQueryPromise<void>{
        return nts.uk.request.ajax("com", paths.findJobId, date);     
    }
    
    export function findLateEarly() {
        return nts.uk.request.ajax("at", paths.findLateEarly); 
    }
    
    export function findDraw() {
        return nts.uk.request.ajax("at", paths.findDraw); 
    }
    
    export function findTimeHd() {
        return nts.uk.request.ajax("at", paths.findTimeHd); 
    }
    
    export function findWith() {
        return nts.uk.request.ajax("at", paths.findWith); 
    }
    
    export function findTrip() {
        return nts.uk.request.ajax("at", paths.findTrip); 
    }
    
    export function findWorkChange() {
        return nts.uk.request.ajax("at", paths.findWorkChange); 
    }
    
    export function findOvertime() {
        return nts.uk.request.ajax("at", paths.findOvertime); 
    }
    
//    export function findVaca(hdAppType: number): JQueryPromise<void>{
//        return nts.uk.request.ajax("at", paths.findVaca + "/" + hdAppType);     
//    }
    
    export function findAllVaca() {
        return nts.uk.request.ajax("at", paths.findAllVaca); 
    }
    
    export function fidnDispApp(appType: number): JQueryPromise<void>{
        return nts.uk.request.ajax("at", paths.fidnDispApp + "/" + appType);     
    }
    
    export function findDisp() {
        return nts.uk.request.ajax("at", paths.findDisp); 
    }
    
    export function findHdApp(hdAppType: number): JQueryPromise<void>{
        return nts.uk.request.ajax("at", paths.findHdApp + "/" + hdAppType);     
    }
    
    export function findAllHdApp() {
        return nts.uk.request.ajax("at", paths.findAllHdApp); 
    }
    
    export function findOt() {
        return nts.uk.request.ajax("at", paths.findOt); 
    }
    
    export function findTemp() {
        return nts.uk.request.ajax("at", paths.findTemp); 
    }
    
    export function findMail() {
        return nts.uk.request.ajax("at", paths.findMail); 
    }
    
    export function findAppCom() {
        return nts.uk.request.ajax("at", paths.findAppCom); 
    }
    
    export function findProByApp(appType: number): JQueryPromise<void>{
        return nts.uk.request.ajax("at", paths.findProByApp + "/" + appType);     
    }
    
    export function findAllPro() {
        return nts.uk.request.ajax("at", paths.findAllPro); 
    }
    
    export function findApproSet() {
        return nts.uk.request.ajax("at", paths.findApproSet); 
    }
    
    export function findAllClosure() {
        return nts.uk.request.ajax("at", paths.findAllClosure); 
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