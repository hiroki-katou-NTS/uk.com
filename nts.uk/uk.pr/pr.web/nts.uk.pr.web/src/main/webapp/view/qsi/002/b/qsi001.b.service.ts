module nts.uk.pr.view.qsi002.b {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            getEmpNameChangeNotiInfor: "ctx/pr/report/printdata/insurenamechangenoti/getEmpNameChangeNotiInfor/{0}",
            add: "ctx/pr/report/printdata/insurenamechangenoti/add"
        };

        export function getEmpNameChangeNotiInfor(empId : string){
            let _path = nts.uk.text.format(path.getEmpNameChangeNotiInfor, empId);
            return nts.uk.request.ajax("pr", _path);
        }

        export function add(data : any){
            return nts.uk.request.ajax(path.add, data);
        }


    }
}