module nts.uk.at.view.kdw008.d {
    export module viewmodel {
        export class ScreenModel {
            constructor() {
                service.getSettingUnit().done((x)=>{
                    if(x){
                        if(x.settingUnit==1){
                            nts.uk.request.jump("/view/kdw/008/b/index.xhtml");
                         
                        }else{
                          nts.uk.request.jump("/view/kdw/008/a/index.xhtml");
                           
                        }
                    }
                });
                
            }

        }
    }
}
