module nts.uk.com.view.cps001.i.vm {
    import getText = nts.uk.resource.getText;
    import getShared = nts.uk.ui.windows.getShared;
    let __viewContext: any = window['__viewContext'] || {},
        block = window["nts"]["uk"]["ui"]["block"]["grayout"],
        unblock = window["nts"]["uk"]["ui"]["block"]["clear"],
        invisible = window["nts"]["uk"]["ui"]["block"]["invisible"];
    
    export class ScreenModel {
        
        createMode: KnockoutObservable<boolean>;

        enaBtnNew: KnockoutObservable<boolean> = ko.observable(true);
        enaBtnRemove: KnockoutObservable<boolean> = ko.observable(true);
        visibleGrant: KnockoutObservable<boolean> = ko.observable(true);
        visibleUse: KnockoutObservable<boolean> = ko.observable(true);
        visibleOver: KnockoutObservable<boolean> = ko.observable(true);
        visibleReam: KnockoutObservable<boolean> = ko.observable(true);

        checked: KnockoutObservable<boolean> = ko.observable(false);

        // list data
        listData: KnockoutObservableArray<ISpecialLeaveRemaining>;
        currentValue: KnockoutObservable<any> = ko.observable();
        //grant
        grantDateTitle: KnockoutObservable<string>;
        dateGrantInp: KnockoutObservable<string>;

        //exp
        expDateTitle: KnockoutObservable<string>;
        deadlineDateInp: KnockoutObservable<string>;

        expStateTitle: KnockoutObservable<string>;
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;

        statusOfUse: KnockoutObservable<string>;

        // grant detail
        dayNumberOfGrantsTitle: KnockoutObservable<string>;
        dayNumberOfGrants: KnockoutObservable<number>;
        grantTimeTitle: KnockoutObservable<string>;
        grantTime: KnockoutObservable<number>;

        //  use detail
        dayNumberOfUseTitle: KnockoutObservable<string>;
        dayNumberOfUse: KnockoutObservable<number>;
        useTimeTitle: KnockoutObservable<string>;
        useTime: KnockoutObservable<number>;

        // Over detail
        dayNumberOverTitle: KnockoutObservable<string>;
        dayNumberOver: KnockoutObservable<number>;
        timeOverTitle: KnockoutObservable<string>;
        timeOver: KnockoutObservable<number>;

        // Reaming detail
        dayNumberOfReamTitle: KnockoutObservable<string>;
        dayNumberOfReam: KnockoutObservable<number>;
        timeReamTitle: KnockoutObservable<string>;
        timeReam: KnockoutObservable<number>
        columns: KnockoutObservableArray<any>;
        useTimeH: KnockoutObservable<boolean>;
        timeExeededH: KnockoutObservable<boolean>;
        timeReamH: KnockoutObservable<boolean>;
        grantTimeH: KnockoutObservable<boolean>;

        constructor() {
            let self = this;

            self.createMode = ko.observable(null);
            
            self.grantDateTitle = ko.observable('grantDateTitle');
            self.dateGrantInp = ko.observable('20000101');

            self.expDateTitle = ko.observable('expDateTitle');
            self.deadlineDateInp = ko.observable('20000101');

            self.expStateTitle = ko.observable('expDateTitle');
            self.roundingRules = ko.observableArray([
                { code: '0', name: '四捨五入' },
                { code: '1', name: '切り上げ' }
            ]);
            self.selectedRuleCode = ko.observable(0);

            self.statusOfUse = ko.observable('statusOfUse');

            // detail of Grant
            self.dayNumberOfGrantsTitle = ko.observable('日数');
            self.dayNumberOfGrants = ko.observable(1);
            self.grantTimeTitle = ko.observable('時間');
            self.grantTime = ko.observable(1);

            // detail of Use
            self.dayNumberOfUseTitle = ko.observable('日数');
            self.dayNumberOfUse = ko.observable(2);
            self.useTimeTitle = ko.observable('時間');
            self.useTime = ko.observable(2);

            // Over detail
            self.dayNumberOverTitle = ko.observable('日数');
            self.dayNumberOver = ko.observable(3);
            self.timeOverTitle = ko.observable('時間');
            self.timeOver = ko.observable(3);

            // Reaming detail
            self.dayNumberOfReamTitle = ko.observable('日数');
            self.dayNumberOfReam = ko.observable(4);
            self.timeReamTitle = ko.observable('時間');
            self.timeReam = ko.observable(4);


            self.listData = ko.observableArray([]);

            self.currentValue.subscribe((newValue) => {
                console.log(newValue);


            });

            self.currentValue = ko.observable(moment().toDate());

            // Subsribe table
            self.currentValue.subscribe(value => {
                if (value) {
                    service.getDetail(value).done((result: ISpecialLeaveRemaining) => {
                        if (result) {
                            self.bindingData(result);
                        }
                    });
                    $('#idGrantDate').focus();
                }

            });

            // Subscribe checkbox
            self.checked.subscribe(value => {
                console.log(value);
                let sID = __viewContext.user.employeeId;

            });


        }



        public startPage(){

            let self = this,
                lstData = self.listData,
                currentValue = self.currentValue();

           
            service.getAllList().done((data: Array<ISpecialLeaveRemaining>) => {
               if (data && data.length > 0) {
                     self.listData(data);
                    // Set focus
                } else {
                    // Set to cr eate mode
                }
                $('#idGrantDate').focus();
                self.getItemDef();
            });
            
            
        }
        
       getItemDef(){
            let self = this;
            
            
            service.getItemDef().done((data) => {
                $("div[data-itemCode]").each(function(){ 
                    let itemCodes = $(this).attr('data-itemcode');
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
                                        self.grantTimeH = ko.observable(false);
                                        self.useTimeH = ko.observable(false);
                                        self.timeExeededH = ko.observable(false);
                                        self.timeReamH = ko.observable(false);
                                    }
                                    
                                }
                            });
                        }
                    });
                     self.columns = ko.observableArray([
                                { headerText: 'stt', key: 'code', width: 50 },
                                { headerText: nts.uk.resource.getText('CPS001_118'), key: 'grantDate', width: 80 },
                                { headerText: nts.uk.resource.getText('CPS001_119'), key: 'deadlineDate', width: 80 },
                                { headerText: nts.uk.resource.getText('CPS001_120'), key: 'numberDayGrant', width: 80 },
                                { headerText: nts.uk.resource.getText('CPS001_128'), key: 'timeGrant', width: 80, hidden: self.grantTimeH()},
                                { headerText: nts.uk.resource.getText('CPS001_121'), key: 'numberDayUse', width: 80 },
                                { headerText: nts.uk.resource.getText('CPS001_122'), key: 'timeUse', width: 80, hidden: self.useTimeH()},
                                { headerText: nts.uk.resource.getText('CPS001_130'), key: 'numberDaysOver', width: 80 },
                                { headerText: nts.uk.resource.getText('CPS001_131'), key: 'timeOver', width: 80, hidden: self.timeExeededH()},
                                { headerText: nts.uk.resource.getText('CPS001_123'), key: 'numberDayRemain', width: 80 },
                                { headerText: nts.uk.resource.getText('CPS001_124'), key: 'timeRemain', width: 80, hidden: self.timeReamH()},
                                { headerText: nts.uk.resource.getText('CPS001_129'), key: 'expStatus', width: 80 }
                            ]);
                    let table: string = '<table tabindex="5" id="sel_item_grid" data-bind="ntsGridList: { height: 282, options: listData, primaryKey:\'grantDate\',showNumbering: true,columns:columns,multiple: false, value: currentValue}"></table>';
                        $("#tbl").html(table);
                        ko.applyBindings(self, $("#tbl")[0]);
                });
        }
    }

    interface ISpecialLeaveRemaining {
        specialid: string;
        sid: string;
        specialLeaCode: string;
        grantDate: string;
        deadlineDate: string;
        expStatus: string;
        registerType: string;
        numberDayGrant: number;
        timeGrant: number;
        numberDayUse: number;
        useSavingDays: number;
        timeUse: number;
        numberDaysOver: number;
        timeOver: number;
        numberDayRemain: number;
        timeRemain: number;

    }

    class SpecialLeaveRemaining {
        specialid: string;
        sid: string;
        specialLeaCode: string;
        grantDate: string;
        deadlineDate: string;
        expStatus: string;
        registerType: string;
        numberDayGrant: number;
        timeGrant: number;
        numberDayUse: number;
        useSavingDays: number;
        timeUse: number;
        numberOverDays: number;
        timeOver: number;
        numberDayRemain: number;
        timeRemain: number;
        constructor(data: ISpecialLeaveRemaining) {
            this.specialid = data.specialid;
            this.sid = data.sid;
            this.specialLeaCode = data.specialLeaCode;
            this.grantDate = data.grantDate;
            this.deadlineDate = data.deadlineDate;
            this.expStatus = data.expStatus;
            this.registerType = data.registerType;
            this.numberDayGrant = data.numberDayGrant;
            this.timeGrant = data.timeGrant;
            this.numberDayUse = data.numberDayUse;
            this.useSavingDays = data.useSavingDays;
            this.timeUse = data.timeUse;
            this.numberOverDays = data.numberDaysOver;
            this.timeOver = data.timeOver;
            this.numberDayRemain = data.numberDayRemain;
            this.timeRemain = data.timeRemain;
        }
    }

}