module knr002.b {
    import blockUI = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import alertError = nts.uk.ui.alertError;
    import getText = nts.uk.resource.getText;
    import getShared = nts.uk.ui.windows.getShared;


    export module viewmodel{
        export class ScreenModel{
            displayLogList: KnockoutObservableArray<DisplayLog>;
            selectedLog: KnockoutObservable<number>;
            empInfoTerCode: KnockoutObservable<string>;
            empInfoTerName: KnockoutObservable<string>;
            modelEmpInfoTer: KnockoutObservable<string>;
            workLocationName: KnockoutObservable<string>;
            lastSuccessDate: KnockoutObservable<string>;
            status: KnockoutObservable<string>;
            sTime:  KnockoutObservable<any>;
            eTime:   KnockoutObservable<any>;
            colorText: any;
            
            
            constructor(){
                var self = this;
                let today = new Date();
                let year = today.getFullYear();
                let month = self.fillZero(`${today.getMonth() + 1}`);
                let date = today.getDate();
                let hours = self.fillZero(`${today.getHours()}`);
                let minutes = self.fillZero(`${today.getMinutes()}`);
                let seconds = self.fillZero(`${today.getSeconds()}`);
                if(date == 1){
                    today.setDate(today.getDate() - 1);
                    var yDate =  today.getDate();
                    var yMonth = self.fillZero(`${today.getMonth() + 1}`);
                }     
                let sSystemDateTime = date > 1 ? `${year}/${month}/${self.fillZero(`${date - 1}`)} ${hours}:${minutes}:${seconds}` : `${year}/${yMonth}/${self.fillZero(`${yDate}`)} ${hours}:${minutes}:${seconds}`;
                let eSystemDateTime = `${year}/${month}/${self.fillZero(`${date}`)} ${hours}:${minutes}:${seconds}`;
                self.sTime =  ko.observable(sSystemDateTime);
                self.eTime =  ko.observable(eSystemDateTime);
                self.selectedLog = ko.observable(null);
                self.displayLogList = ko.observableArray<DisplayLog>([]);
                self.empInfoTerCode = ko.observable('');
                self.empInfoTerName = ko.observable('');
                self.modelEmpInfoTer = ko.observable('');
                self.workLocationName = ko.observable('');
                self.lastSuccessDate = ko.observable('');
                self.status = ko.observable('');
                setTimeout(() => { $(".column-2 #multi-list_sDate").attr('colspan',5); }, 1); 
                self.colorText = (value: any, obj: any) => {
                    setTimeout(() => {
                        let cell1 = $("#multi-list").igGrid("cellById", obj.id, 'sDate');
                        let cell2 = $("#multi-list").igGrid("cellById", obj.id, 'sTime');
                        let cell3 = $("#multi-list").igGrid("cellById", obj.id, 'textPeriod');
                        let cell4 = $("#multi-list").igGrid("cellById", obj.id, 'eDate');
                        let cell5 = $("#multi-list").igGrid("cellById", obj.id, 'eTime');
                        let checkWeekDayIsSta = `${obj.id}`.lastIndexOf('a');
                        let checkWeekDayIsSun = `${obj.id}`.lastIndexOf('n');
                        if (checkWeekDayIsSta > -1) {
                            cell1.css("color", "#0086EA");
                            cell2.css("color", "#0086EA");
                            cell3.css("color", "#0086EA");
                            cell4.css("color", "#0086EA");
                            cell5.css("color", "#0086EA");
                        }
                        if (checkWeekDayIsSun > -1) {
                            cell1.css("color", "#FF2D2D");
                            cell2.css("color", "#FF2D2D");
                            cell3.css("color", "#FF2D2D");
                            cell4.css("color", "#FF2D2D");
                            cell5.css("color", "#FF2D2D");
                        }
                    }, 1);
                    return value;
                };          
            }
            /**
             * Start Page
             * 起動する
             */
            public startPage(): JQueryPromise<void>{
                var self = this;										
                var dfd = $.Deferred<void>();
                blockUI.invisible();
                //getShared from A Screen
                let data: any = getShared('knr002-b');
                self.empInfoTerCode(data.empInfoTerCode);
                self.empInfoTerName(data.empInfoTerName);
                self.modelEmpInfoTer(data.displayModelEmpInfoTer);
                self.workLocationName(data.workLocationName);
                self.lastSuccessDate(data.signalLastTime);
                self.status(data.displayCurrentState);
                // load List Log in the Period
                self.loadLogInPeriod(self.empInfoTerCode(), self.sTime(), self.eTime()); 

                dfd.resolve();											
                return dfd.promise();											
            }
           
            /**
             * B4_3
             * 抽出ボタン
             */
            private extract(): any {
                let self = this;
                // process
                let sDateTime = self.toDate(new Date(self.sTime()));
                let eDateTime = self.toDate(new Date(self.eTime()));
                // load List Log in the Period
                self.loadLogInPeriod(self.empInfoTerCode(), sDateTime, eDateTime); 
            } 

            /**
             * B6_1
             * 閉じるボタン
             */
            private cancel_Dialog(): any {
                nts.uk.ui.windows.close();
            }
            /**
             * load Data
             */
            private loadLogInPeriod(empInfoTerCode: string, sTime: string, eTime: string): void{
                var self = this; 
                if(self.hasError() || empInfoTerCode === undefined || empInfoTerCode === ''){
                    self.displayLogList([]);
                }else{
                    service.getInPeriod(empInfoTerCode, sTime, eTime).done((data: Array<any>) => {
                        if(!data){
                            //do something                 
                            self.displayLogList([]);
                        } else {
                            let displayLogListTemp = [];
                            for (let i = 0; i < data.length; i++) { 
                                try {
                                    var sDay = new Date(data[i].preTimeSuccDate).getDay();
                                    var eDay = new Date(data[i].lastestTimeSuccDate).getDay();
                                    let displayLog = new DisplayLog(`${self.getDate(data[i].preTimeSuccDate)}${self.getDayOfWeek(sDay)}`,
                                                                    self.getTime(data[i].preTimeSuccDate),
                                                                    `${self.getDate(data[i].lastestTimeSuccDate)}${self.getDayOfWeek(eDay)}`,
                                                                    self.getTime(data[i].lastestTimeSuccDate));
                                    switch(sDay){
                                        case 6: displayLog.id = `${i}_sta`; break;
                                        case 0: displayLog.id = `${i}_sun`; break;
                                        default: displayLog.id = `${i}`;
                                    }
                                    displayLogListTemp.push(displayLog);
                                } catch (error) {
                                    console.log("Can't convert string to date");
                                }                              
                            }

                            self.displayLogList(displayLogListTemp);
                        }    
                    });
                } 
                blockUI.clear();
                $('#B6_1').focus();  
            }
             /**
             * Check Input Errors
             */
            private hasError(): boolean{
                var self = this;
                //period time
                return $("#B4_2").ntsError("hasError");
            }
            /**
             * clear Errors
             */
            private clearErrors(): void{     
                $('#B4_2').ntsError('clear');
                $('.nts-input').ntsError('clear');
               // nts.uk.ui.errors.clearAll();
            }

            /**
             * The day of Week
             */
            private getDayOfWeek(dayOfWeek: Number): string{
                switch(dayOfWeek){
                    case 0: return '（日）';
                    case 1: return '（月）';
                    case 2: return '（火）';
                    case 3: return '（水）'; 
                    case 4: return '（木）';
                    case 5: return '（金）';
                    case 6: return '（土）';
                    default : return '';
                }
            }
            /**
             * get Date to Display
             */
            private getDate(dateTime: String): string{
                return dateTime.substr(0, 10);
            }
            /**
             * get Time to Display
             */
            private getTime(dateTime: String): string{
                return dateTime.substr(11, 5);
            }
            /**
             * fill '0' character to datetime
             */
            private fillZero(str: string): string{
                return str.length == 2 ? str : `0${str}`;
            }
            /**
             * to Date
             */
            private toDate(dateToString: Date): string{
                let self = this;
                let year = dateToString.getFullYear();
                let month = self.fillZero(`${dateToString.getMonth() + 1}`);
                var date = dateToString.getDate();
                let hours = self.fillZero(`${dateToString.getHours()}`);
                let minutes = self.fillZero(`${dateToString.getMinutes()}`);
                let seconds = self.fillZero(`${dateToString.getSeconds()}`);
                return `${year}/${month}/${self.fillZero(`${date}`)} ${hours}:${minutes}:${seconds}`;
            }
        }

        class DisplayLog{
            sDate: string;
            sTime: string;
            textPeriod: string;
            eDate: string;
            eTime: string;
            id: string;
            constructor(sDate: string, sTime: string, eDate: string, eTime: string){
                this.sDate = sDate;
                this.sTime = sTime;
                this.textPeriod = getText('KNR002_71');
                this.eDate = eDate;
                this.eTime = eTime;
            }
        }
    }
}