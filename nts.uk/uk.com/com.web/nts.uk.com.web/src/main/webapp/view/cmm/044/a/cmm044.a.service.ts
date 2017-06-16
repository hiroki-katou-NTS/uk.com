module cmm044.a.service {


    var paths: any = {
        findAgent: "workflow/agent/find",
        findAllAgent: "workflow/agent/find/",
        deleteAgent: "workflow/agent/delete/",
        addAgent: "workflow/agent/add/",
        updateAgent: "workflow/agent/update/"
    }

    export function findAgent(parameter: any): JQueryPromise<viewmodel.model.AgentDto> {
        var dfd = $.Deferred<viewmodel.model.AgentDto>();
        return nts.uk.request.ajax("com", paths.findAgent, parameter);
    }

    export function findAllAgent(employeeId: string): JQueryPromise<Array<viewmodel.model.AgentDto>> {
        var dfd = $.Deferred<Array<viewmodel.model.AgentDto>>();
        return nts.uk.request.ajax("com", paths.findAllAgent + employeeId);
    }

    export function deleteAgent(agent: any) {
        var dfd = $.Deferred<viewmodel.model.AgentDto>();
        return nts.uk.request.ajax("com", paths.deleteAgent, agent)
    }

    export function addAgent(agent: any) {
        return nts.uk.request.ajax("com", paths.addAgent, agent, {dataType: 'text'});
    }

    export function updateAgent(agent: any) {
        var dfd = $.Deferred<Array<viewmodel.model.AgentAppDto>>();
        return nts.uk.request.ajax("com", paths.updateAgent, agent);
    }
}