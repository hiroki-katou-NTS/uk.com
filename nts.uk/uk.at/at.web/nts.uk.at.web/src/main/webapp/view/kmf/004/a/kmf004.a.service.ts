module nts.uk.at.view.kmf004.a.service {
    var paths: any = {
        
    }
    export function findSpecialHoliday(parameter: any): JQueryPromise<viewmodel.model.AgentDto> {
        return nts.uk.request.ajax("com",paths.findAgent, parameter);
    }
}