module cmm013.c.viewmodel {

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

        
        closeDialog(){
             nts.uk.ui.windows.close();   
        }
        add(){
            var self = this;
            if(self.checkTypeInput()==false){
                return;
            }else
            if(self.checkValueInput(self.inp_003())==false){
                return;
            }
            else {
                if(self.startDateLast()!=''&& self.startDateLast()!= null) {
                    var check = self.selectedId();
                }else{
                    var check = 2;
                }
                var date = new Date(self.inp_003());
                let dateNew = date.getFullYear()+'/'+(date.getMonth()+1)+'/'+ date.getDate();
                if(date.getMonth()<9 && date.getDate()<10){
                    dateNew = date.getFullYear()+'/'+0+(date.getMonth()+1)+'/'+0+ date.getDate();
                }else{
                    if(date.getDate()<10){
                        dateNew = date.getFullYear()+'/'+(date.getMonth()+1)+'/'+0+ date.getDate();    
                    }
                    if(date.getMonth()<9){
                        dateNew = date.getFullYear()+'/'+0+(date.getMonth()+1)+'/'+ date.getDate();   
                    }    
                }
                if(self.checkValueInput(dateNew)==false){
                    return;
                }

                nts.uk.ui.windows.setShared('cmm013C_startDateNew',dateNew, true);
                nts.uk.ui.windows.setShared('cmm013C_copy',check, true);
                nts.uk.ui.windows.close();
            }
        }
        checkTypeInput(): boolean{
            var self = this;
            var date = new Date(self.inp_003());
            if(date.toDateString()=='Invalid Date'){ 
                alert("nhap lai ngay theo dinh dang YYYY-MM-DD hoac YYYY/MM/DD hoac YYYY.MM.DD"); 
                return false;
            }else{
                return true;
            }
        }
        checkValueInput(value: string): boolean{
            var self = this;
            if(value <= self.startDateLast()){
                alert("nhap lai start Date");
                return false;
            }else{
                return true;    
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
