module cmmhoa013.c.viewmodel { 

    export class ScreenModel {
        label_002: KnockoutObservable<Labels>;
        inp_003: KnockoutObservable<string>;
        historyId: KnockoutObservable<string>;
        startDateLast: KnockoutObservable<string>;
        //C_SEL_001
        itemList: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number>;
        enable: KnockoutObservable<boolean>;
        res: KnockoutObservableArray<string>;

        constructor() {
            var self = this;
            self.label_002 = ko.observable(new Labels()); 
            self.inp_003 = ko.observable(null);
            self.historyId = ko.observable(null);
            self.startDateLast = ko.observable('');
            //C_SEL_001
            self.selectedId = ko.observable(1);
            self.enable = ko.observable(true); 
        }
        /**
         * Start page
         * get start date last from screen A
         */
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.historyId(nts.uk.ui.windows.getShared('CMM013_historyId'));
            self.startDateLast(nts.uk.ui.windows.getShared('CMM013_startDateLast'));     
            if(self.startDateLast() !='' && self.startDateLast()!= null){
                self.itemList = ko.observableArray([
                    new BoxModel(1, '最新の履歴（'+self.startDateLast()+'）から引き継ぐ  '),
                    new BoxModel(2, '全員参照不可')
                ]);
            }
            else {
                self.itemList = ko.observableArray([
                    new BoxModel(1, '全員参照不可')
                ]);
            }                

            dfd.resolve();
            return dfd.promise();                    
        }
        /**
         * decision add history
         * set start date new and send to screen A(main)
         * then close screen C
         */
        decision(){
            var self = this;
            if(self.inp_003() <= self.startDateLast()){
                alert("nhap lai start Date");    
                return;
            }
            else {
                if(self.startDateLast()!=''&& self.startDateLast()!= null) {
                    var check = self.selectedId();
                }else{
                    var check = 2;
                }
                console.log(check);
                nts.uk.ui.windows.setShared('cmm013C_startDateNew',self.inp_003(), true);
                nts.uk.ui.windows.setShared('cmm013C_copy',check, true);
                nts.uk.ui.windows.close();
            }
        }
    }

     export class Labels{
        constraint: string =  'LayoutCode';
        inline: KnockoutObservable<boolean>;
        required: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
    
        constructor() {
            var self = this;
            self.inline = ko.observable(true);
            self.required = ko.observable(true);
            self.enable = ko.observable(true);
        }    
    }

    export class BoxModel{
        id: number;
        name: string;
        constructor(id, name){
            var self = this;
            self.id = id;
            self.name = name;
        }    
    }    
        

}
