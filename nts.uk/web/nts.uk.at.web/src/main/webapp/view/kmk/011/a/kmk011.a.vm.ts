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
        lstItemSelected: KnockoutObservableArray<service.model.ItemSelected>;
        list: KnockoutObservable<string>;
        use: KnockoutObservable<string>;
        notUse: KnockoutObservable<string>;
        constructor() {
            var self = this;
            self.list = ko.observable();
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
//            self.use = ko.observable(nts.uk.resource.getText("Enum_UseAtr_Use"));
//            self.use = ko.observable('使用する')
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
            self.lstItemSelected = ko.observableArray([]);
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
                service.getNameItemSelected(self.divTimeId()).done(function(lstName: service.model.ItemSelected){
                    self.lstItemSelected(lstName);
                    self.findTimeName(self.divTimeId());
                    console.log(self.lstItemSelected());
                })
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
                if(lstDivTime=== undefined || lstDivTime.length == 0){
                    self.dataSource();
                }else{
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
            service.getAllAttItem(1).done(function(lstAllItem: Array<service.model.AttendanceType>){
                var listAllId = [];
                for(let j=0;j<lstAllItem.length;j++){
                    listAllId[j] = lstAllItem[j].attendanceItemId;
                }
                var listIdSelect =[];
                for(let i=0;i<self.lstItemSelected().length;i++){
                    listIdSelect[i] = self.lstItemSelected()[i].id;
                }
                nts.uk.ui.windows.setShared('AllAttendanceObj', listAllId, true);
                nts.uk.ui.windows.setShared('SelectedAttendanceId', listIdSelect, true);
                nts.uk.ui.windows.setShared('Multiple', true, true);
                console.log(listIdSelect);
                console.log(listAllId);
                nts.uk.ui.windows.sub.modal('../../../kdl/021/a/index.xhtml', { title: '乖離時間の登録＞対象項目', }).onClosed(function(): any {
                    var list  = nts.uk.ui.windows.getShared('selectedChildAttendace');
//                    var list = [101,104,109,113];
                self.list(list);
                    var listUpdate = new Array<service.model.DivergenceTimeItem>();
                    for(let i=0;i<list.length;i++){
                        let itemUpdate = new service.model.DivergenceTimeItem(self.divTimeId(),list[i]);
                        listUpdate.push(itemUpdate);
                    }
                        service.getName(list).done(function(lstName: service.model.ItemSelected){
                        self.lstItemSelected(lstName);
                            console.log(lstName);
                        self.findTimeName(self.divTimeId());
                        })
                    })
                });  
//            })
        }
        Registration(){
            var self = this;
             $('.nts-input').trigger("validate");
            _.defer(() => {
                if (nts.uk.ui.errors.hasError()===false) {
                    var dfd = $.Deferred();
                    var select = new service.model.SelectSet(self.selectSel(),self.convert(self.checkErrSelect()));
                    var input = new service.model.SelectSet(self.selectInp(),self.convert(self.checkErrInput()));
                    var divTime = new service.model.DivergenceTime(self.divTimeId(),self.divTimeName(),self.selectUse(),self.convertInt(self.alarmTime()),self.convertInt(self.errTime()),select,input);
                    var listAdd = new Array<service.model.TimeItemSet>();
                    if(self.list()!= null){
                        for(let k=0;k<self.list().length;k++){
                            let add = new service.model.TimeItemSet(self.divTimeId(),self.list()[k]);
                            listAdd.push(add);
                        }
                    }
                    var Object = new service.model.ObjectDivergence(divTime,listAdd);
                    service.updateDivTime(Object).done(function(){
                        self.getAllDivTimeNew();
                        nts.uk.ui.dialog.alert('登録しました。');
                    }).fail(function(error) {
                        if(error.message == 'Msg_82'){
                            $('#inpAlarmTime').ntsError('set', 'アラーム時間');
                        }else{
                            $('#inpDialog').ntsError('set', '選択肢の設定');
                        }
//                        nts.uk.ui.dialog.alert(error.message);
                    })
                    dfd.resolve();
                    return dfd.promise();
                }
            })
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
            if(value==''||value==null||value===undefined){
                return 0;
            }else{
                var hours = value.substring(0,2);
                var minutes = value.substring(3,5);
                return (parseFloat(hours)*60 + parseFloat(minutes));
            }
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
        //ghep ten hien thi 
        findTimeName(divTimeId: number){
            var self = this;
            self.timeItemName('');
            var strName = '';
            if(self.lstItemSelected().length<1){
                self.timeItemName('');
            }else{
                strName = self.lstItemSelected()[0].name;
                if(self.lstItemSelected().length>1){
                    for(let j=1;j<self.lstItemSelected().length;j++){
                        strName = strName + ' + ' + self.lstItemSelected()[j].name;}
                    }     
                 self.timeItemName(strName);
                strName ='';
            }
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