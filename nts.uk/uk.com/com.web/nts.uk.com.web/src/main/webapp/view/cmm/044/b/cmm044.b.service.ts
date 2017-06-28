module cmm044.b.service {
    var paths: any = {
        findAgentByDate: "workflow/agent/findByDate",
        findAgent: "workflow/agent/findByCid"
    }
    export function findAgentByDate(startDate: string, endDate: string): JQueryPromise<Array<viewmodel.model.AgentDto>> {
        var option = {
            startDate: new Date(startDate),
            endDate: new Date(endDate) 
        }
        return nts.uk.request.ajax("com",paths.findAgentByDate, option);
    }
    export function findAgent(): JQueryPromise<Array<viewmodel.model.AgentDto>> {
        return nts.uk.request.ajax("com",paths.findAgent);
    }
}