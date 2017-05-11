module kmk011.a.viewmodel {

    export class ScreenModel {
        //A_label_x
        label_002: KnockoutObservable<model.Labels>;
        label_003: KnockoutObservable<model.Labels>;
        label_004: KnockoutObservable<model.Labels>;
        label_005: KnockoutObservable<model.Labels>;
        label_006: KnockoutObservable<model.Labels>;
        columns: KnockoutObservableArray<any>;
        dataSource: KnockoutObservableArray<model.Item>;
        currentCode: KnockoutObservable<any>;
        useSet: KnockoutObservableArray<any>;
        selectUse: KnockoutObservable<any>;
        selectSel: KnockoutObservable<any>;
        selectInp: KnockoutObservable<any>;
        lstItemTime: KnockoutObservableArray<model.TimeItem>;
        divTimeName: KnockoutObservable<string>;
        timeItemName: KnockoutObservable<string>;
        alarmTime: KnockoutObservable<string>;
        errTime: KnockoutObservable<string>;
        checkErrInput: KnockoutObservable<boolean>;
        checkErrSelect: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
        divTimeId: KnockoutObservable<number>;
        itemDivTime: KnockoutObservable<service.model.DivergenceTime>;
        listDivItem: KnockoutObservableArray<service.model.DivergenceItem>;
        constructor() {
            var self = this;
            self.label_002 = ko.observable(new model.Labels());
            self.label_003 = ko.observable(new model.Labels());
            self.label_004 = ko.observable(new model.Labels());
            self.label_005 = ko.observable(new model.Labels());
            self.label_006 = ko.observable(new model.Labels());
            self.currentCode = ko.observable(1);
            self.columns = ko.observableArray([
                { headerText: 'コード', key: 'divTimeId', width: 100  },
                { headerText: '名称', key: 'divTimeName', width: 150 }
            ]);
            self.dataSource = ko.observableArray([]);
            self.lstItemTime = ko.observableArray([
                new model.TimeItem(1,'勤怠項目名称1'),
                new model.TimeItem(2,'勤怠項目名称2'),
                new model.TimeItem(3,'勤怠項目名称3')
                ]);
            self.useSet = ko.observableArray([
                    { code: '1', name: '使用する' },
                    { code: '0', name: '使用しない' },
                ]);
            self.divTimeName = ko.observable('');    
            self.timeItemName = ko.observable('');
            self.checkErrSelect = ko.observable(true);
            self.enable = ko.observable(true);
            self.divTimeId = ko.observable(1);
            self.itemDivTime = ko.observable(null);
            self.selectUse = ko.observable(0);
            self.alarmTime = ko.observable();
            self.errTime = ko.observable();
            self.selectSel = ko.observable();
            self.selectInp = ko.observable();
            self.checkErrInput = ko.observable(false);
            self.checkErrSelect = ko.observable(false);
            self.listDivItem = ko.observableArray([]);
            //subscribe currentCode
            self.currentCode.subscribe(function(codeChanged) {
                if(codeChanged==0){return;}
                else{
                self.itemDivTime(self.findDivTime(codeChanged));
                self.alarmTime(self.convertTime(self.itemDivTime().alarmTime));
                self.errTime(self.convertTime(self.itemDivTime().errTime));
                self.selectUse(self.itemDivTime().divTimeUseSet);
                self.selectSel(self.itemDivTime().selectSet.selectUseSet);
                self.selectInp(self.itemDivTime().inputSet.selectUseSet);
                self.divTimeId(self.itemDivTime().divTimeId);
                self.divTimeName(self.itemDivTime().divTimeName);
                self.findTimeName(self.divTimeId());
                if(self.itemDivTime().inputSet.cancelErrSelReason==1){
                    self.checkErrInput(true);
                }else{
                    self.checkErrInput(false);    
                }
                if(self.itemDivTime().selectSet.cancelErrSelReason==1){
                    self.checkErrSelect(true);
                }else{
                    self.checkErrSelect(false);    
                }
                    }
            });
        }
        /**
         * start page
         * get all divergence time
         * get all divergence name
         */
        startPage(): JQueryPromise<any>{
            var self = this;
            var dfd = $.Deferred();
            service.getAllDivTime().done(function(lstDivTime: Array<service.model.DivergenceTime>){
//                service.getAllDivItemId().done(function(lstDivItem: Array<service.model.DivergenceItem>){
//                    self.listDivItem(lstDivItem);
//                    console.log(self.listDivItem());
//                })
                if(lstDivTime=== undefined || lstDivTime.length == 0){
                    self.dataSource();
                }else{
                    service.getAllDivItemId().done(function(lstDivItem: Array<service.model.DivergenceItem>){
                        self.listDivItem(lstDivItem);
                        console.log(self.listDivItem());
                    })
                    self.currentCode(0);
                    self.dataSource(lstDivTime);
                    let rdivTimeFirst = _.first(lstDivTime);
                    self.currentCode(rdivTimeFirst.divTimeId);
                }

                dfd.resolve();
            })
            return dfd.promise();
        }
        /**
         * find Divergence Time is selected
         */
        findDivTime(value: number): any {
            let self = this;
            var itemModel = null;
            return _.find(self.dataSource(), function(obj: service.model.DivergenceTime) {
                return obj.divTimeId == value;
            })
        }
        openBDialog(){
            var self = this;
            nts.uk.ui.windows.setShared('KMK011_divTimeId', self.divTimeId(), true);
            console.log(self.divTimeId());
            nts.uk.ui.windows.sub.modal('/view/kmk/011/b/index.xhtml', { title: '選択肢の設定', })    
        }
        openDialog021(){
            var self = this;
            service.getDivItemIdSelected(self.divTimeId()).done(function(lstIdSel: number){
                var listIdSel = lstIdSel;
                nts.uk.ui.windows.setShared('AllAttendanceObj', self.listDivItem(), true);
                nts.uk.ui.windows.setShared('SelectedAttendanceId', listIdSel, true);
                nts.uk.ui.windows.setShared('Multiple', true, true);
                console.log(listIdSel);
                nts.uk.ui.windows.sub.modal('../../../../kdl/a/021/index.xhtml', { title: '乖離時間の登録＞対象項目', }).onClosed(function(): any {
                     var lst  = nts.uk.ui.windows.getShared('');
                    
                });  
            })
        }
        Registration(){
            var self = this;
            var dfd = $.Deferred();
            var select = new service.model.SelectSet(self.selectSel(),self.convert(self.checkErrSelect()));
            var input = new service.model.SelectSet(self.selectInp(),self.convert(self.checkErrInput()));
            var divTime = new service.model.DivergenceTime(self.divTimeId(),self.divTimeName(),self.selectUse(),self.convertInt(self.alarmTime()),self.convertInt(self.errTime()),select,input);
            service.updateDivTime(divTime).done(function(){
                self.getAllDivTimeNew();
                nts.uk.ui.dialog.alert('登録しました。');
            }).fail(function(error) {
                nts.uk.ui.dialog.alert(error.message);
            })
            dfd.resolve();
            return dfd.promise();
        }
        convert(value: boolean): number{
            if(value == true){
                return 1;
            }else
            if(value == false){
                return 0;
            }
        }
        convertTime(value: number): string{
            var hours = Math.floor(value/60); 
            var minutes = value % 60;
            return (hours < 10 ? "0" + hours : hours) + ":" + (minutes < 10 ? "0" + minutes : minutes);
        }
        convertInt(value: string): number{
            var hours = value.substring(0,2);
            var minutes = value.substring(3,5);
            return (parseFloat(hours)*60 + parseFloat(minutes));
        }
        //get all divergence time new
        getAllDivTimeNew(){
            var self = this;
            var dfd = $.Deferred<any>();
            self.dataSource();
            service.getAllDivTime().done(function(lstDivTime: Array<service.model.DivergenceTime>) {
                self.currentCode('');
                self.dataSource(lstDivTime);
                self.currentCode(self.divTimeId());
                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
            })
            dfd.resolve();
            return dfd.promise();
        }
        findTimeName(divTimeId: number){
            var self = this;
             service.getDivItemIdSelected(divTimeId).done(function(lstIdSel: Array<service.model.DivergenceTimeItem>){
                var strName = '';
                if(lstIdSel.length<1){
                    self.timeItemName('');
                }else{
                    for(let j=0;j<self.listDivItem().length;j++){
                        if(self.listDivItem()[j].divItemId == lstIdSel[0].attendanceId) 
                        {strName = self.listDivItem()[j].divItemName;}
                    }  
                    for(let i=1;i<lstIdSel.length;i++){
                        for(let j=0;j<self.listDivItem().length;j++){
                            if(self.listDivItem()[j].divItemId == lstIdSel[i].attendanceId) 
                            {strName = strName + ' + ' + self.listDivItem()[j].divItemName;}
                        }     
                    }
                     self.timeItemName(strName);
                }
            })
        }
    }
    export module model{ 
        export class Labels {
            constraint: string = 'LayoutCode';
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
    
        export class BoxModel {
            id: number;
            name: string;
            constructor(id, name) {
                var self = this;
                self.id = id;
                self.name = name;
            }
        }
        
        export class TimeItem{
            attendanceId: number;
            attendanceName: string;
            constructor(attendanceId: number, attendanceName: string){
                this.attendanceId = attendanceId;
                this.attendanceName = attendanceName;
            }     
        }
    }
}