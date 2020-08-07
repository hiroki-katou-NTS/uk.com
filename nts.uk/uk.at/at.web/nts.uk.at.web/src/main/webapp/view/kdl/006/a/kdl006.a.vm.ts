module nts.uk.at.view.kdl006.a {
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import info = nts.uk.ui.dialog.info;
    import error = nts.uk.ui.dialog.error;
    import getShared = nts.uk.ui.windows.getShared;
    import windows = nts.uk.ui.windows;
    
    export class ScreenModel {
        tighteningList = ko.observableArray([]);
        selectedCode = ko.observable('');
        workplaceList = ko.observableArray([]);
        width = ko.observable('614');
        descriptive = ko.observable('');
        
        closureId : number;
        workPlaceComfirmList = [];
        
        constructor(){
            let self = this;
            self.closureId = getShared('KDL006-CLOSUREID');
            self.selectedCode.subscribe((newValue) => {
                self.descriptive(getText('KDL006_15',[newValue]));
            });
            self.workplaceList.subscribe((newValue) => {
                if(self.workplaceList().length > 12){
                    $('.scroll').css({"width": "647", "overflow-y": "scroll"});
                }else{
                    $('.scroll').css({"width": "630", "overflow-y": "hidden"});
                }
            });
            $(document).ready(function() {
                $('#combo-box').focus();
            });
        }
        
        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            block.grayout();
            service.startPage({closureId: self.closureId}).done(function(data) {
                let c = [];
                _.forEach(data.closureList, function(closure) {
                    c.push(new Closure(closure));
                });
                self.tighteningList(c);
                self.workPlaceComfirmList = data.workPlaceComfirmList;
                if(data.workPlaceComfirmList.length == 0){
                    error({ messageId: 'Msg_1653' });    
                }
                let w = [];
                for(let i = 0; i < (data.workPlaceComfirmList.length < 12 ? 12 : data.workPlaceComfirmList.length); i ++){
                    w.push(new WorkPlace(data.workPlaceComfirmList[i]));
                }
                self.workplaceList(w);
                dfd.resolve();
            }).fail(function(res) {
                error({ messageId: res.messageId }).then(() => {
                    self.close();
                });
            }).always(() =>{
                block.clear();
            });
            
            return dfd.promise();
        }
        
        save(){
            let self = this;
        }
        
        close(): any {
            windows.close();
        }
    }
    
    class WorkPlace {
        workPlaceId = '';
        workPlaceName = '';
        confirmEmployment = ko.observable(false);
        confirmEmployeeName = '';
        confirmationTime = '';       
        constructor(param: any) {
            let self = this;
            if(param){
                self.workPlaceId = param.workPlaceId;
                self.workPlaceName = param.workPlaceName;
                self.confirmEmployment(param.confirmEmployment);
                self.confirmEmployeeName = param.confirmEmployeeName;
                self.confirmationTime = param.confirmationTime;
            }
        }
    }
    
    class Closure {
        closureId: string
        yearMonth: number;
        closureName: string;
        start: Date;
        end: Date;
        periodDate: string;
        constructor(param: any) {
            let self = this;
            self.closureId = param.closureId;
            self.yearMonth = param.yearMonth;
            self.closureName = param.closureName;
            self.start = new Date(param.start);
            self.end = new Date(param.end);
            self.periodDate = param.start + getText('KDL006_16') + param.end;
        }
    }
}
