module nts.uk.at.view.kaf002.a {
    export module viewmodel {
        export class ScreenModel {
            startPage(appType: number, appDate: string, isStartup: boolean, employeeID: string): JQueryPromise<any> {
                nts.uk.ui.block.invisible();
                var self = this;
                let dfd = $.Deferred<any>();
                nts.uk.at.view.kaf000.a.service.getAppDataDate({
                    appTypeValue: 7,
                    appDate: moment(new Date()).format("YYYY/MM/DD"),
                    isStartup: true,
                    employeeID: null
                }).done((data)=>{
                    if(!nts.uk.util.isNullOrEmpty(data.errorFlag)){
                        switch(data.errorFlag){
                            case 1:
                                nts.uk.ui.dialog.alertError({ messageId: "Msg_324" }).then(function(){
                                    nts.uk.request.jump("com", "/view/ccg/008/a/index.xhtml");
                                });
                                break;
                            case 2: 
                                nts.uk.ui.dialog.alertError({ messageId: "Msg_238" }).then(function(){
                                    nts.uk.request.jump("com", "/view/ccg/008/a/index.xhtml");
                                });
                                break;
                            case 3:
                                nts.uk.ui.dialog.alertError({ messageId: "Msg_237" }).then(function(){
                                    nts.uk.request.jump("com", "/view/ccg/008/a/index.xhtml");
                                });
                                break;
                            default: 
                                nts.uk.ui.block.clear(); 
                        } 
                    } else {
                        nts.uk.ui.block.clear();       
                    }
                    dfd.resolve(data);
                }).fail((res)=>{
                    nts.uk.ui.dialog.alertError({messageId: res.messageId}).then(function(){ 
                        nts.uk.request.jump("com", "view/ccg/008/a/index.xhtml");
                    });
                    dfd.reject(res);    
                });            
                return dfd.promise();
            }
            
            openBCWindow(value: number){
                nts.uk.request.jump("../b/index.xhtml", { 'stampRequestMode': value, 'screenMode': 1 });
            }
            
            performanceReference(){
                // alert('KDL004');   
            }
        }
    }
}