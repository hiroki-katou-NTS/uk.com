module cmm044.b.service {


    var paths: any = {
        findAllAgent: "workflow/agent/findAll",
        
    }
    export function findAllAgent(employeeId: string,startDate: string, endDate: string): JQueryPromise<Array<viewmodel.AgentData>> {
        var dfd = $.Deferred<Array<viewmodel.AgentData>>();
        return nts.uk.request.ajax("com",paths.findAllAgent + employeeId + startDate + endDate);
    }

 
}