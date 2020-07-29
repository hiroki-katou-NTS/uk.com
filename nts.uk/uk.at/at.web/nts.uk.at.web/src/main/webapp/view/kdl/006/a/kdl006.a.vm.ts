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
        constructor(){
            let self = this;
            self.selectedCode.subscribe((newValue) => {
                
            });
            self.tighteningList.subscribe((newValue) => {
                if(self.tighteningList().length > 12){
                    $('.scroll').css({"width": "631"});
                }else{
                    $('.scroll').css({"width": "614"});
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
            for(let i = 0; i < 12; i ++){
                self.tighteningList.push(new Tightening({id: i, code: i+'日締め', periodDate:'2020/02/16 ~ 2020/03/15'}));
                self.workplaceList.push(new WorkPlace({id: i, workPlace: '人事'+i+'課', confirm: false, person:'日通　太郎', confirmedDateTime:'2020/10/05 10:15'}));
            }
            dfd.resolve();
            block.clear();
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
        id: number;
        workPlace: string;
        confirm = ko.observable(false);
        person: string;
        confirmedDateTime: string;        
        constructor(param: any) {
            let self = this;
            self.id = param.id;
            self.workPlace = param.workPlace;
            self.confirm(param.confirm);
            self.person = param.person;
            self.confirmedDateTime = param.confirmedDateTime;
        }
    }
    
    class Tightening {
        id: string
        code: string;
        periodDate: string;
        constructor(param: any) {
            let self = this;
            self.id = param.id;
            self.code = param.code;
            self.periodDate = param.periodDate;
        }
    }
}
