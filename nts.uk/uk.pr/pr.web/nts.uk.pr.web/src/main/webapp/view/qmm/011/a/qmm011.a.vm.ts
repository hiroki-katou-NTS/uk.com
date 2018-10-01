module nts.uk.pr.view.qmm011.a.viewmodel {
    export class ScreenModel {
        constructor() {
            let self = this;
        }
        onSelectedScreenB(){
            setTimeout(()=>{
                $("#B1_4_container").focus();
            },10);
        }
        onSelectedScreenC(){
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
