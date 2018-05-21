module nts.uk.at.view.kdw008.d {
    export module viewmodel {
        export class ScreenModel {
            //is daily
            isDaily: boolean;
            
            constructor(dataShare: any) {
                let self = this;
//                debugger;
                self.isDaily = dataShare.ShareObject;
                    service.getFormatSetting().done((x)=>{
                        if(x){
                            if(x.settingUnitType==1){
                                nts.uk.request.jump("/view/kdw/008/b/index.xhtml", { ShareObject:  self.isDaily });
                            }else{
                              nts.uk.request.jump("/view/kdw/008/a/index.xhtml", { ShareObject:  self.isDaily });
                               
                            }
                        }
                    });    
                
            }

        }
    }
}
