module nts.uk.at.view.kaf002.a {
    export module viewmodel {
        export class ScreenModel {
            openBCWindow(value: number){
                nts.uk.request.jump("../b/index.xhtml", { 'stampRequestMode': value, 'screenMode': 1 });
            }
            
            performanceReference(){
                // alert('KDL004');   
            }
        }
    }
}