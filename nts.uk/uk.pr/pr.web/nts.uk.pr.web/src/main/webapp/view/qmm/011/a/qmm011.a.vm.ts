module nts.uk.pr.view.qmm011.a.viewmodel {
    import error = nts.uk.ui.errors;
   
    export class ScreenModel {
        constructor() {
            let self = this;
        }
        
        onSelectedScreenB(){
            let hisId = __viewContext.viewModel.viewmodelB.selectedEmpInsHisId();
            __viewContext.viewModel.viewmodelB.getEmpInsurPreRate(hisId);
            error.clearAll();
            setTimeout(()=>{
                $("#B1_4_container").focus();
            },10);
        }
        
        onSelectedScreenC(){
            let hisId = __viewContext.viewModel.viewmodelC.selectedEmpInsHisId();
            __viewContext.viewModel.viewmodelC.getAccInsurPreRate(hisId);
            error.clearAll();
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
