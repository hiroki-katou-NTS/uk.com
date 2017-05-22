module ccg030.a.service {
    var paths: any = {
        createFlowMenu: "sys/portal/flowmenu/create",
        fillAllFlowMenu: "sys/portal/flowmenu/findall",
        updateFlowMenu: "sys/portal/flowmenu/update",
        deleteFlowMenu: "sys/portal/flowmenu/delete",
    }
    //add new flow menu
    export function createFlowMenu(flowMenu: viewmodel.model.FlowMenu){
        return nts.uk.request.ajax("com", paths.createFlowMenu, flowMenu);
    }
    
    //fill all flow menu by companyId
    export function fillAllFlowMenu(): JQueryPromise<Array<viewmodel.model.FlowMenu>>{
        return nts.uk.request.ajax("com", paths.fillAllFlowMenu);
    }
    //update flowmenu
    export function updateFlowMenu(flowMenu: viewmodel.model.FlowMenu){
        return nts.uk.request.ajax("com", paths.updateFlowMenu, flowMenu);
    }
    //delete flow menu
    export function deleteFlowMenu(flowMenuID: string){
        var data = {
            toppagePartID: flowMenuID
        }
        return nts.uk.request.ajax("com", paths.deleteFlowMenu, data);
    }
    
 
}