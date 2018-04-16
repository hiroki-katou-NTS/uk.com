module nts.uk.com.view.cps001.g.vm {
    import getText = nts.uk.resource.getText;
    import info = nts.uk.ui.dialog.info;
    import alert = nts.uk.ui.dialog.alert;
    import block = nts.uk.ui.block.grayout;
    import unblock = nts.uk.ui.block.clear;
    export class ScreenModel {

        // Store create/update mode
        createMode: KnockoutObservable<boolean>;
        columns: KnockoutObservableArray<any>;
        currentValue: KnockoutObservable<string> = ko.observable('');
        date: KnockoutObservable<string>;
        expirationStatus: KnockoutObservableArray<any>;
        value: KnockoutObservable<number>;
        checked: KnockoutObservable<boolean>;

        alllist: KnockoutObservableArray<AnnualLeaveGrantRemainingData> = ko.observableArray([]);
        
        listAnnualLeaveGrantRemainData: KnockoutObservableArray<AnnualLeaveGrantRemainingData> = ko.observableArray([]);
        currentItem: KnockoutObservable<AnnualLeaveGrantRemainingData> = ko.observable(new AnnualLeaveGrantRemainingData(<IAnnualLeaveGrantRemainingData>{}));
        grantMinutesH: KnockoutObservable<boolean>;
        usedMinutesH: KnockoutObservable<boolean>;
        remainingMinutesH: KnockoutObservable<boolean>;
        init: boolean = true;
        itemDefs: any = [];
        constructor() {
            let _self = this;

            _self.createMode = ko.observable(null);

            _self.checked = ko.observable(false);
            _self.expirationStatus = ko.observableArray([
                { code: '0', name: '使用可能' },
                { code: '1', name: '期限切れ' }
            ]);

            // Subsribe table
            _self.currentValue.subscribe(value => {
                if (value) {
                    _self.createMode(false);
                    service.getDetail(value).done((result: IAnnualLeaveGrantRemainingData) => {
                        if (result) {
                            _self.currentItem(new AnnualLeaveGrantRemainingData(result));
                            if(_self.itemDefs.length > 0){
                                _self.setItemDefValue(_self.itemDefs).done(() => {
                                    console.log('done ---');
                                 });
                            }   
                        }
                         $('#id-grantDate').focus();
                    });
                    $('#idGrantDate').focus();
                }
            });

            // Subscribe checkbox
            _self.checked.subscribe(value => {
                console.log(value);
                if (value){
                    _self.listAnnualLeaveGrantRemainData(_self.alllist());
                } else {
                    _self.listAnnualLeaveGrantRemainData(_.filter(_self.alllist(),function(item){
                        return item.expirationStatus === 0;  
                    }));
                }
                if (_self.listAnnualLeaveGrantRemainData().length) {
                     _self.createMode(false);
                    // Set focus
                    _self.currentValue(_self.listAnnualLeaveGrantRemainData()[0].annLeavID);
                    _self.currentItem(new AnnualLeaveGrantRemainingData(_self.listAnnualLeaveGrantRemainData()[0]));
                    // Set to update mode
                } else {
                    _self.create();                    
                }
            });


        }

        /**
         * Run after page loaded
         */
        public startPage(annID? : string): JQueryPromise<any> {
            let _self = this, 
            sID = __viewContext.user.employeeId;
            service.getAllList(sID).done((data: Array<IAnnualLeaveGrantRemainingData>) => {
                if (data && data.length > 0) {
                    // Set to update mode
                    _self.createMode(false);
                    _self.alllist.removeAll();
                    _self.alllist(data);
                    _self.listAnnualLeaveGrantRemainData(_.filter(_self.alllist(), function(item){
                        return  item.expirationStatus === 0;
                    }));
                    // Set focus
                    if (annID) {
                         _self.currentValue(annID);
                    } else {
                        _self.currentValue(_self.listAnnualLeaveGrantRemainData()[0].annLeavID);
                        _self.currentItem(new AnnualLeaveGrantRemainingData(_self.listAnnualLeaveGrantRemainData()[0]));
                    }
                } else {
                    // Set to cr eate mode
                    _self.createMode(true);
                }
                $('#idGrantDate').focus();
                
                if(_self.init){
                    _self.getItemDef();
                    _self.init = false;
                 }
                 else {
                    if(_self.itemDefs.length > 0){
                    _self.setItemDefValue(_self.itemDefs).done(() => {
                        console.log('done ---');
                     });
                    }    
                }
                
            });
        }
         getItemDef(){
            let self = this;
            if(self.itemDefs.length > 0){
                self.setItemDefValue(self.itemDefs).done(() => {
                        self.setGridList();
                    });
            }else{
                service.getItemDef().done((data) => {
                    self.itemDefs = data;
                    self.setItemDefValue(data).done(() => {
                        self.setGridList();
                    });
                });
            }
            
        }
        setItemDefValue(data: any):JQueryPromise<any>{
            let self = this, dfd = $.Deferred();
            $("td[data-itemCode]").each(function(){ 
                    let itemCodes = $(this).attr('data-itemCode');
                    if(itemCodes){
                        let itemCodeArray = itemCodes.split(" ");
                            _.forEach(itemCodeArray, (itemCode) => {
                                let itemDef = _.find(data, (item)=>{
                                    return item.itemCode == itemCode;
                                });
                                if(itemDef){
                                    if(itemDef.display){
                                        $(this).children().first().html("<label>" + itemDef.itemName + "</label>");
                                    }else{
                                        $(this).parent().css("display", "none");
                                    }
                                    let timeType = itemCodeArray[itemCodeArray.length - 1];
                                        switch(timeType){
                                            case "grantMinutes": 
                                                self.grantMinutesH = ko.observable(!itemDef.display);
                                                break;
                                            case "usedMinutes":
                                                self.usedMinutesH = ko.observable(!itemDef.display);
                                                break;
                                            case "remainingMinutes":
                                                self.remainingMinutesH = ko.observable(!itemDef.display);
                                                break;
                                        }
                                }
                            });
                        }
                 dfd.resolve();
                    });
           
            return dfd.promise();
        }
        setGridList(){
            let self = this;
            self.columns = ko.observableArray([
                {type: 'string', key: 'annLeavID', hidden: true },
                { headerText: getText('CPS001_110'), type: 'date', key: 'grantDate', width: 100 },
                { headerText: getText('CPS001_111'), type: 'date', key: 'deadline', width: 100 },
                { headerText: getText('CPS001_120'), type: 'number', formatter: formatEnum, key: 'expirationStatus', width: 80 },
                { headerText: getText('CPS001_128'), type: 'string', formatter: formatDate, key: 'grantDays', width: 60 },
                { headerText: getText('CPS001_121'), key: 'grantMinutes', formatter: formatTime, width: 80, hidden: self.grantMinutesH()},
                { headerText: getText('CPS001_122'), type: 'string', formatter: formatDate, key: 'usedDays', width: 60 },
                { headerText: getText('CPS001_123'), key: 'usedMinutes', formatter: formatTime, width: 80, hidden: self.usedMinutesH()},
                { headerText: getText('CPS001_124'), type: 'string', formatter: formatDate, key: 'remainingDays', width: 60 },
                { headerText: getText('CPS001_129'), type: 'string', key: 'remainingMinutes', formatter: formatTime, width: 80, hidden: self.remainingMinutesH()}
            ]);
                    let table: string = '<table tabindex="6" id="single-list" data-bind="ntsGridList: { dataSource: listAnnualLeaveGrantRemainData,  primaryKey: \'annLeavID\', columns: columns, multiple: false,value: currentValue, showNumbering: true,rows:5}"></table>';
                    $("#tbl").html(table);
                    ko.applyBindings(self, $("#tbl")[0]);
        }

        /**
         * Start create mode
         */
        public startCreateMode(): void {
            let _self = this;
            _self.createMode(true);
        }

        public create(): void {
            let _self = this;
            _self.createMode(true);
            _self.currentItem(new AnnualLeaveGrantRemainingData(<IAnnualLeaveGrantRemainingData>{}));
            if(_self.itemDefs.length > 0){
                _self.setItemDefValue(_self.itemDefs).done(() => {
                    console.log('done ---');
                 });
            }
            $('#id-grantDate').focus();
        }
        /**
         * Save sequence
         */
        public save(): void {
            let _self = this,
                command = ko.toJS(_self.currentItem());
            
            $("#grantDate").trigger("validate");
            $("#deadline").trigger("validate");
            $("#grantDays").trigger("validate");
            $("#usedDays").trigger("validate");
            $("#remainingDays").trigger("validate");
            block();
            if (_self.createMode()) {
                
                service.add(command).done((message: string) => {
                    info({ messageId: "Msg_15" }).then(function() {
                        _self.startPage();
                    });
                    unblock();
                }).fail((message) => {
                    alert(message.message);
                    unblock();
                });
            } else {
                service.update(command).done((message: string) => {
                    info({ messageId: "Msg_15" }).then(function() {
                        _self.startPage(ko.toJS(_self.currentItem()).annLeavID);
                    });
                    unblock();
                }).fail((message) => {
                    alert(message.message);
                    unblock();
                });
            }
        }

        /**
         * Close this dialog
         */
        public close(): void {
            nts.uk.ui.windows.close();
        }

        /**
         * Remove sequence
         */
        public remove(): void {
            let _self = this,
                sID = __viewContext.user.employeeId,
                currentIndex = _.findIndex(_self.listAnnualLeaveGrantRemainData(), function(item: IAnnualLeaveGrantRemainingData) { 
                    return item.annLeavID == ko.toJS(_self.currentItem()).annLeavID;
                }),
                finalIndex = _self.listAnnualLeaveGrantRemainData().length -1;
            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" })
                .ifYes(() => {
                    block();
                    let command = {
                        annLeavID: ko.toJS(_self.currentItem()).annLeavID,
                        employeeId: sID,
                        grantDate: ko.toJS(_self.currentItem()).grantDate
                    };
                    service.deleteLeav(command).done((message: string) => {
                        info({ messageId: "Msg_16" }).then(function() {
                            
                            // set focus
                            if (currentIndex ===0 && currentIndex === finalIndex){
                                _self.create();    
                            } else if (currentIndex !==0 && currentIndex === finalIndex ){
                                currentIndex = currentIndex -1;
                            } else {
                                currentIndex = currentIndex + 1;    
                            }
                            _self.startPage(_self.listAnnualLeaveGrantRemainData()[currentIndex].annLeavID);
                        });
                        unblock();
                    })
                     unblock();
                }).ifNo(() => {
                        // Nothing happen
                });
        }
    }
    export interface IAnnualLeaveGrantRemainingData {
        annLeavID: string;
        employeeId: string;
        grantDate: Date;
        deadline: Date;
        expirationStatus: number;
        grantDays: number;
        grantMinutes: number;
        usedDays: number;
        usedMinutes: number;
        remainingDays: number;
        remainingMinutes: number;
    }

    export class AnnualLeaveGrantRemainingData {
        annLeavID: KnockoutObservable<string> = ko.observable(null);
        employeeId: KnockoutObservable<string> = ko.observable(null);
        grantDate: KnockoutObservable<Date> = ko.observable(null);
        deadline: KnockoutObservable<Date> = ko.observable(null);
        expirationStatus: KnockoutObservable<number> = ko.observable(null);
        grantDays: KnockoutObservable<number> = ko.observable(null);
        grantMinutes: KnockoutObservable<number> = ko.observable(null);
        usedDays: KnockoutObservable<number> = ko.observable(null);
        usedMinutes: KnockoutObservable<number> = ko.observable(null);
        remainingDays: KnockoutObservable<number> = ko.observable(null);
        remainingMinutes: KnockoutObservable<number> = ko.observable(null);
        constructor(param?: IAnnualLeaveGrantRemainingData) {
            let self = this;
            if (param) {
                self.annLeavID(param.annLeavID || null);
                self.grantDate(moment.utc(param.grantDate,"YYYY/MM/DD") || null);
                self.deadline(moment.utc(param.deadline,"YYYY/MM/DD") || null);
                self.expirationStatus(param.expirationStatus || 0);
                self.grantDays(param.grantDays || null);
                self.grantMinutes(param.grantMinutes || null);
                self.usedDays(param.usedDays || null);
                self.usedMinutes(param.usedMinutes || null);
                self.remainingDays(param.remainingDays || null);
                self.remainingMinutes(param.remainingMinutes || null);
                self.employeeId(__viewContext.user.employeeId);
            }
            // Subcribe grantDate
            self.grantDate.subscribe(value => {
                if (value && __viewContext.viewModel.createMode()) {
                    service.lostFocus(value).done((data: Date) => {
                        if (data){
                            self.deadline(moment.utc(data,"YYYY/MM/DD"));                            
                        }
                    });
                }
            });
        }
    }
    
    function formatTime(value, row) {
        if (value) {
            let hour = Math.floor(Math.abs(value)/60);
            let minutes = Math.floor(Math.abs(value)%60);
            return hour + ':' + (minutes < 10 ? ("0" + minutes) : minutes);
        } else {
            return '';    
        }
    }
    
    function formatDate(value, row) {
        if (value) {
            return value + '日';
        }
    }

    function formatEnum(value, row) {
        if (value && value === '0') {
            return '使用可能';
        } else if (value && value === '1') {
            return '期限切れ';
        }
    }

}