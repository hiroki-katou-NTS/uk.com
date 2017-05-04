module ccg030.a.service {
    var paths: any = {
        createFlowMenu: "sys/portal/flowmenu/create"
    }
    
    export function craeteFlowMenu(flowMenu: viewmodel.model.flowMenu){
        return nts.uk.request.ajax("at", paths.createFlowMenu, flowMenu);
    }
}