module nts.uk.com.view.ccg013.f.service {
    let paths: any = {
        
        getAllJobTitle: "basic/company/organization/jobtitle/findall",
        findWebMenuCode: "sys/portal/webmenu/jobtitletying/findWebMenuCode",
        updateWebMenuCode: "sys/portal/webmenu/jobtitletying/update"
    }   
    
     export function getAllJobTitle(baseDate): JQueryPromise<Array<any>>{
        var Kcp003Dto = {
          baseDate: baseDate 
        }
        return nts.uk.request.ajax("com", paths.getAllJobTitle, Kcp003Dto); 
    }
    
     export function findWebMenuCode(jobId: Array<String>): JQueryPromise<Array<viewmodel.JobTitleTying>> {
        return nts.uk.request.ajax("com", paths.findWebMenuCode, jobId);
    }
    
    export function updateWebMenuCode(data: Array<viewmodel.JobTitleTying>) {
        return nts.uk.request.ajax("com", paths.updateWebMenuCode, data);
    }
    
}