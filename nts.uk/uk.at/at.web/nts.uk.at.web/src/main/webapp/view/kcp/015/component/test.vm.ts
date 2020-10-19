module test.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export class ScreenModel {
        
        input : any;
        
        constructor() {
            let self = this;
            self.input = {};
            
            let dataShare = getShared('dataShareKCP015');
            
            //định nghĩa param truyen vào component
            self.input = { 
                hasParams : dataShare.hasParams, 
                visibleA31: dataShare.checkedA3_1, 
                visibleA32: dataShare.checkedA3_2, 
                visibleA33: dataShare.checkedA3_3,  
                visibleA34: dataShare.checkedA3_4, 
                visibleA35: dataShare.checkedA3_5, 
                visibleA36: dataShare.checkedA3_6,
                listEmp   : dataShare.listEmp,
                baseDate  : dataShare.baseDate
            }
        }
        
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }
        
        close() {
            nts.uk.ui.windows.close();
        }
        
        OpenDialog0022(){
            var self = this;
        }
    }
}
