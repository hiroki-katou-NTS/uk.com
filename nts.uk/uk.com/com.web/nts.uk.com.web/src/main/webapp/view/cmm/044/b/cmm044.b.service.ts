module cmm044.b.service {
    var paths: any = {
        findAgentByDate: "workflow/agent/find/",
        findAgent: "workflow/agent/findAll"
        
    }
    export function findAgentByDate(employeeId: string,startDate: string, endDate: string): JQueryPromise<Array<viewmodel.model.AgentDto>> {
        return nts.uk.request.ajax("com",paths.findAgentByDate + employeeId + startDate + endDate);
    }
    
 export function findAgent(parameter: any): JQueryPromise<viewmodel.model.AgentDto> {
        return nts.uk.request.ajax("com",paths.findAgent, parameter);
    }
    export function findAllAgent(employeeId: string): JQueryPromise<Array<viewmodel.model.AgentDto>> {
        return nts.uk.request.ajax("com",paths.findAllAgent + employeeId);
    }
 
}