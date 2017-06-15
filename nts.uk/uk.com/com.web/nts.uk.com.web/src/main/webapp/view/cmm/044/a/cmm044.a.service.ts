module cmm044.a.service {


    var paths: any = {
        findAllAgent: "workflow/agent/find/",
        deleteAgent: "workflow/agent/delete/",
        addAgent: "",
        updateAgent: ""
    }
    export function findAllAgent(employeeId: string): JQueryPromise<Array<viewmodel.model.AgentDto>> {
        var dfd = $.Deferred<Array<viewmodel.model.AgentDto>>();
        return nts.uk.request.ajax("com", paths.findAllAgent + employeeId);
    }

    export function deleteAgent(agent: viewmodel.DeleteAgent) {
        var dfd = $.Deferred<viewmodel.DeleteAgent>();
        return nts.uk.request.ajax("com", paths.deleteAgent, agent)
    }

    export function addAgent(): JQueryPromise<Array<viewmodel.model.AgentAppDto>> {
        var dfd = $.Deferred<Array<viewmodel.model.AgentAppDto>>();
        return nts.uk.request.ajax("com", paths.addAgent);
    }

    export function updateAgent(): JQueryPromise<Array<viewmodel.model.AgentDto>> {
        var dfd = $.Deferred<Array<viewmodel.model.AgentDto>>();
        return nts.uk.request.ajax("com", paths.updateAgent);
    }
}