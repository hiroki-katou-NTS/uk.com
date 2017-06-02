module nts.uk.at.view.kdl001.test {
    export module viewmodel {
        export class ScreenModel {
            searchOption: any;
            startTimeOption: any;
            startTime: any;
            endTimeOption: any;
            endTime: any;
            items: any;
            currentCodeList: KnockoutObservableArray<any>;
            constructor() {
                var self = this;
                self.searchOption = ko.observable(1);
                self.startTimeOption = ko.observable(1);
                self.startTime = ko.observable(1);
                self.endTimeOption = ko.observable(1);
                self.endTime = ko.observable(1);
                self.items = ko.observableArray([
                    new WorkTimeSet('0001','a', 'thu2', 'thu2', 'thu2', 'thu2'),
                    new WorkTimeSet('0002','b', 'thu3', 'thu2', 'thu2', 'thu2'),
                    new WorkTimeSet('0003','c', 'thu4', 'thu2', 'thu2', 'thu2'),
                    new WorkTimeSet('0004','d', 'thu5', 'thu2', 'thu2', 'thu2'),
                    new WorkTimeSet('0005','e', 'thu6', 'thu2', 'thu2', 'thu2'),
                    new WorkTimeSet('0006','f', 'thu7', 'thu2', 'thu2', 'thu2'),
                    new WorkTimeSet('0007','g', 'cn', 'cn', 'cn', 'cn'),
                    new WorkTimeSet('0008','h', 'thu2', 'thu2', 'thu2', 'thu2'),
                    new WorkTimeSet('0009','i', 'thu3', 'thu2', 'thu2', 'thu2'),
                    new WorkTimeSet('0010','j', 'thu4', 'thu2', 'thu2', 'thu2'),
                    new WorkTimeSet('0011','k', 'thu5', 'thu2', 'thu2', 'thu2'),
                    new WorkTimeSet('0012','l', 'thu6', 'thu2', 'thu2', 'thu2'),
                    new WorkTimeSet('0013','m', 'thu7', 'thu2', 'thu2', 'thu2'),
                    new WorkTimeSet('0014','n', 't', 'thu2', 'thu2', 'thu2'),
                    new WorkTimeSet('0015','o', 'thu2', 'thu2', 'thu2', 'thu2'),
                    new WorkTimeSet('0016','p', 'thu2', 'thu2', 'thu2', 'thu2'),
                    new WorkTimeSet('0017','q', 'thu2', 'thu2', 'thu2', 'thu2'),
                    new WorkTimeSet('0018','r', 'thu2', 'thu2', 'thu2', 'thu2'),
                    new WorkTimeSet('0019','s', 'thu2', 'thu2', 'thu2', 'thu2'),
                    new WorkTimeSet('0020','t', 'thu2', 'thu2', 'thu2', 'thu2'),
                ]);
                self.currentCodeList = ko.observableArray([]);
            }
            
            search(){
                var self = this;
                let inputString = self.searchOption().toString()+self.startTime().toString()+'~'+self.endTimeOption().toString()+self.endTime().toString();
                $('#day-list-tbl > tbody> tr').css('display','none');
                let allRow = $('#multi-list > tbody> tr');
                for(let i=1;i<=allRow.length;i++){
                    let tr = $('#multi-list > tbody> tr:nth-child('+i+')');
                    for(let j=2;j<=3;j++){
                        if(tr.find(":nth-child("+j+")").text().indexOf(inputString) > -1) {
                            tr.css('display','');
                            break;
                        }   
                    }    
                }        
            }
            
            returnData(){
                $('#day-list-tbl > tbody> tr').css('display','');        
            }
            
            testDialog(){
                nts.uk.ui.windows.sub.modal("/view/kdl/001/a/index.xhtml", { title: "割増項目の設定", dialogClass: "no-close" }).onClosed(function() {});        
            }
        }
        
        class WorkTimeSet {
            code: string;
            name: string;
            workTime1: string;
            workTime2: string;
            workAtr: string;
            remark: string;
            constructor(code: string, name: string, workTime1: string, workTime2: string, workAtr: string, remark: string) {
                var self = this;
                self.code = code;
                self.name = name;
                self.workTime1 = workTime1;
                self.workTime2 = workTime2;
                self.workAtr = workAtr;
                self.remark = remark;
            }
}
    }
}