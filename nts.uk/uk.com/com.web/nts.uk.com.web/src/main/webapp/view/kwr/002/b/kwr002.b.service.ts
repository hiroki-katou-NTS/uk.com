module cmm001.a.service {
    const paths = {
        getARES: "at/function/attendancerecord/export/setting/getAllAttendanceRecExpSet",
//        getComId: "bs/company/findComId",
//        findDiv: "bs/employee/workplacedifferinfor/findDiv",
//        findSys: "sys/env/usatr/findSys",
//        findPost: "contact/postalcode/findAll",
//        findPostId: "contact/postalcode/find",
//        findPostCd: "contact/postalcode/findByCode",
//        update: "screen/com/cmm001/update",
//        add: "screen/com/cmm001/add",
        
    }
       
     export function getARES() :JQueryPromise<Array<any>> {
        let dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.getARES)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
      
//    export function getDiv(param: any): JQueryPromise<void>{
//        return nts.uk.request.ajax(paths.findDiv, param);     
//    }
//     
//    export function getSys(vari: any): JQueryPromise<void>{
//        return nts.uk.request.ajax(paths.findSys, vari);     
//    }
//    
//    export function findPost(){
//        return nts.uk.request.ajax(paths.findPost);    
//    }
//    
//     export function findComId(id: String): JQueryPromise<void>{
//        return nts.uk.request.ajax("com", paths.getComId + "/" + id);     
//    }
//    
//    export function findPostId(vari: String): JQueryPromise<void>{
//        return nts.uk.request.ajax("com", paths.findPostId + "/" + vari);     
//    }
//    
//    export function findPostCd(vari: String): JQueryPromise<void>{
//        return nts.uk.request.ajax("com", paths.findPostCd + "/" + vari);     
//    }
//    
//    export function update(command: any): JQueryPromise<void>{
//        return nts.uk.request.ajax(paths.update, command);    
//    }
//    
//    export function add(command: any): JQueryPromise<void>{
//        return nts.uk.request.ajax(paths.add, command);    
//    }
}