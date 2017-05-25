module ccg030.a.service {
    var paths: any = {
        createFlowMenu: "sys/portal/flowmenu/create",
        fillAllFlowMenu: "sys/portal/flowmenu/findall",
        updateFlowMenu: "sys/portal/flowmenu/update",
        deleteFlowMenu: "sys/portal/flowmenu/delete",
        deleteFile: "sys/portal/flowmenu/delete/file",
        getFlowMenuById: "sys/portal/flowmenu/findbycode",
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
    
    //fill by toppage part id
    export function getFlowMenuById(flowMenuID: string): JQueryPromise<Array<viewmodel.model.FlowMenu>>{
        return nts.uk.request.ajax("com", paths.getFlowMenuById, flowMenuID);
    }
    
    //delete flow menu
    export function deleteFlowMenu(flowMenuID: string){
        var data = {
            toppagePartID: flowMenuID
        }
        return nts.uk.request.ajax("com", paths.deleteFlowMenu, data);
    }
    
    //delete file
    export function deleteFile(fileID: string){
        var data = {
            fileId: fileID
        }
        return nts.uk.request.ajax("com", paths.deleteFile, data);
    }
 
}