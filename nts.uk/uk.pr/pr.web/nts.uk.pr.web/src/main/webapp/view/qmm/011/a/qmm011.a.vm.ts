module nts.uk.pr.view.qmm011.a.viewmodel {
    import error = nts.uk.ui.errors;
    import block = nts.uk.ui.block;
    export class ScreenModel {
        constructor() {
            let self = this;
        }
        
        onSelectedScreenB(){
            block.invisible();
            __viewContext.viewModel.viewmodelB.selectedEmpInsHisId('');
            __viewContext.viewModel.viewmodelB.initScreen(null);
            __viewContext.viewModel.viewmodelB.getEmpInsurPreRate();
            error.clearAll();
            block.clear();
            setTimeout(()=>{
                $("#B1_4_container").focus();
            },10);
        }
        
        onSelectedScreenC(){
            block.invisible();
            __viewContext.viewModel.viewmodelC.selectedEmpInsHisId('');
            __viewContext.viewModel.viewmodelC.initScreen(null);
            __viewContext.viewModel.viewmodelC.getAccInsurPreRate();
            error.clearAll();
            block.clear();
            setTimeout(()=>{
                $("#C1_4_container").focus();
            },10);
        }
        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            return dfd.promise();
        }
    }
}
