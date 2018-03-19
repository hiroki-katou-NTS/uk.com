module nts.uk.com.view.cps001.g.vm {
        import getText = nts.uk.resource.getText;
        import info = nts.uk.ui.dialog.info;
        import alert = nts.uk.ui.dialog.alert;
        export class ScreenModel {

            // Store create/update mode
            createMode: KnockoutObservable<boolean>;
            items: KnockoutObservableArray<ItemModel>;
            columns: KnockoutObservableArray<any>;
            currentValue: KnockoutObservable<Date>;
            date: KnockoutObservable<string>;   
            expirationStatus: KnockoutObservableArray<any>;
            value: KnockoutObservable<number>;
            listAnnualLeaveGrantRemainData: KnockoutObservableArray<AnnualLeaveGrantRemainingData> = ko.observableArray([]);
            currentItem: KnockoutObservable<AnnualLeaveGrantRemainingData> = ko.observable(new AnnualLeaveGrantRemainingData(<IAnnualLeaveGrantRemainingData>{}));
            
            constructor() {
                let _self = this;
                
                _self.createMode = ko.observable(null);
                
                
                _self.expirationStatus = ko.observableArray([
                    { code: '0', name: '使用可能' },
                    { code: '1', name: '期限切れ' }
                ]);
                _self.createMode.subscribe((newValue) => {
                });
                this.columns = ko.observableArray([
                    { headerText:  getText('CPS001_110'), prop: 'grantDate', width: 100 },
                    { headerText: getText('CPS001_111'), prop: 'deadline', width: 100 },
                    { headerText: getText('CPS001_120'), prop: 'expirationStatus', width: 50 },
                    { headerText: getText('CPS001_128'), prop: 'grantDays', width: 50 },
                    { headerText: getText('CPS001_121'), prop: 'grantMinutes', width: 50 },
                    { headerText: getText('CPS001_122'), prop: 'usedDays', width: 50 },
                    { headerText: getText('CPS001_123'), prop: 'usedMinutes', width: 50 },
                    { headerText: getText('CPS001_124'), prop: 'remainingDays', width: 50 },
                     { headerText: getText('CPS001_129'),prop: 'remainingMinutes', width: 50 }
                ]);
                _self.items = ko.observableArray([]);
                let str = ['a0', 'b0', 'c0', 'd0'];
                for (let j = 0; j < 4; j++) {
                    for (let i = 1; i < 51; i++) {
                        let code = i < 10 ? str[j] + '0' + i : str[j] + i;
                        this.items.push(new ItemModel(code, code, code, code));
                    }
                }
                _self.currentValue = ko.observable(moment().toDate());
                _self.currentValue.subscribe(value =>{
                    service.getDetail(value).done((result: IAnnualLeaveGrantRemainingData)=>{
                        if(result){
                            _self.currentItem(new AnnualLeaveGrantRemainingData(result));
                        }
                    });
                   
                });
            }



            /**
             * Run after page loaded
             */
            public startPage(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();
                service.getAllList().done((data: Array<IAnnualLeaveGrantRemainingData>) => {
                    if (data && data.length > 0 ){
                        _.each(data, (item)=>{
                            _self.listAnnualLeaveGrantRemainData.push(item);
                            });
//                        data.forEach(item=>_self.listAnnualLeaveGrantRemainData.push(item));
                        // Set focus
                        _self.currentValue(_self.listAnnualLeaveGrantRemainData()[0].grantDate);
                    }
                    
                });
                // Load sequence data list
                return dfd.promise();
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
                _self.currentItem(new AnnualLeaveGrantRemainingData(<IAnnualLeaveGrantRemainingData>{})); 
            }
            /**
             * Save sequence
             */
            public save(): void {
                let _self = this,
                command = ko.toJS(_self.currentItem());
                service.register(command).done((message: string)=>{
                        info({ messageId: "Msg_15" }).then(function() {
                        self.startPage();
                    });
                }).fail((message) => {
                    alert(message.message);
                });
                if (_self.createMode()) {
                } else {
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
                let _self = this;

                nts.uk.ui.dialog.confirm({ messageId: "Msg_18" })
                    .ifYes(() => {

                    }).ifNo(() => {
                        // Nothing happen
                    })
            }
        }
        
        export class ItemModel {
            code: string;
            name: string;
            description: string;
            other1: string;
            other2: string;
            constructor(code: string, name: string, description: string, other1?: string, other2?: string) {
                this.code = code;
                this.name = name;
                this.description = description;
                this.other1 = other1;
                this.other2 = other2 || other1;
            }
        }
    
        export interface IAnnualLeaveGrantRemainingData{
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
                
        export interface AnnualLeaveNumberInfo{
            grantNumber: AnnualLeaveGrantNumber;
            usedNumber: AnnualLeaveUsedNumber;
            remainNumber: AnnualLeaveRemainingNumber;
        }
    
        export interface AnnualLeaveGrantNumber{
            
        }
    
        export interface AnnualLeaveUsedNumber{
               
        }
        
        export interface AnnualLeaveRemainingNumber {
                
        }
    
        export class AnnualLeaveGrantRemainingData{
            grantDate: KnockoutObservable<Date> = ko.observable(moment().toDate());
            deadline: KnockoutObservable<Date> = ko.observable(moment().toDate());
            expirationStatus: KnockoutObservable<number> = ko.observable(0);
            grantDays: KnockoutObservable<number> = ko.observable(0);
            grantMinutes: KnockoutObservable<number> = ko.observable(0);
            usedDays: KnockoutObservable<number> = ko.observable(0);
            usedMinutes: KnockoutObservable<number> = ko.observable(0);
            remainingDays: KnockoutObservable<number> = ko.observable(0);
            remainingMinutes: KnockoutObservable<number> = ko.observable(0);
            constructor(param? : IAnnualLeaveGrantRemainingData){
                let self = this;
                if (param){
                    self.grantDate(param.grantDate || moment().toDate());
                    self.deadline(param.deadline|| moment().toDate());
                    self.expirationStatus(param.expirationStatus || 0);
                    self.grantDays(param.grantDays || 0);
                    self.grantMinutes(param.grantMinutes || 0);
                    self.usedDays(param.usedDays || 0);
                    self.usedMinutes(param.usedMinutes || 0);
                    self.remainingDays(param.remainingDays || 0);
                    self.remainingMinutes(param.remainingMinutes || 0); 
                }
            }
        }

}