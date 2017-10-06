module nts.uk.at.view.kdw002.d {
    export module viewmodel {
        export class ScreenModel {
            constructor() {
                service.getSettingUnit().done((x)=>{
                    if(x){
                        if(x.settingUnit==1){
                            nts.uk.request.jump("/view/kdw/002/b/index.xhtml");
                         
                        }else{
                          nts.uk.request.jump("/view/kdw/002/c/index.xhtml");
                           
                        }
                    }
                });
                
            }

        }
    }
}
