module nts.uk.com.view.ccg013.f.service {
    let paths: any = {
        getAllJobTitle: "basic/company/organization/jobtitle/findall"
    }
    
    
    
     export function getAllJobTitle(baseDate): JQueryPromise<Array<any>>{
        var Kcp003Dto = {
          baseDate: baseDate 
        }
        return nts.uk.request.ajax("com", paths.getAllJobTitle, Kcp003Dto); 
    }
    
}