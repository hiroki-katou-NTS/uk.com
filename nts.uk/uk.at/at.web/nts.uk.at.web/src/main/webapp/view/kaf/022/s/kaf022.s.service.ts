module nts.uk.at.view.kaf022.s.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        find: "at/request/application/getalldatabyclosureId",
        //jobId. Mu盻創 l蘯･y ﾄ柁ｰ盻｣c jobid thﾃｬ ph蘯｣i truy盻］ companyId vﾃ� baseDate (com)
        findJobId: "bs/employee/jobtitle/findAll",
        // l蘯･y xong list job Id thﾃｬ cﾃｳ th盻� truy盻］ c蘯｣ list jobId ﾄ黛ｻ� l蘯･y list A15_4 (com)
        findJobTitleSearchList: "workflow/jobtitlesearchset/job/getbyCode",
        // update kaf022   
        update: 'screen/at/kaf022/update',
        finAllData : "screen/at/kaf022/findAll"
    }
        
    export function getReason(appType: number) {
        return ajax(`at/request/application-reason/find/reason/${appType}`);
    }
        
        
        
        
        
    export function update(command): JQueryPromise<Array<string>>{
        return ajax("at", paths.update, command);
    }
    export function findAllData():JQueryPromise<Array<string>>{
        return nts.uk.request.ajax("at", paths.finAllData); 
    }  
    export function findApp(ids: any): JQueryPromise<void> {
        return nts.uk.request.ajax("at", paths.find,ids);
    }
        // view model cho thﾃｪm ﾄ黛ｻ訴 tﾆｰ盻｣ng Kcp003Dto ch盻� cﾃｳ 1 trﾆｰ盻拵g baseDate: truy盻］ ngﾃ�y hi盻㌻ t蘯｡i
    export function findJobId(date: any): JQueryPromise<void>{
        return nts.uk.request.ajax("com", paths.findJobId, date);     
    }
    export function findJobTitleSearchList(param: any): JQueryPromise<void> {
        return nts.uk.request.ajax("com", paths.findJobTitleSearchList, param);
    }
     
}