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
                var today = new Date();
                var date = today.getDate();
                var sSystemDateTime = `${today.getFullYear()}/${today.getMonth() + 1}/${date- 1} ${today.getHours()}:${today.getMinutes()}:${today.getSeconds()}`;
                var eSystemDateTime = `${today.getFullYear()}/${today.getMonth() + 1}/${date} ${today.getHours()}:${today.getMinutes()}:${today.getSeconds()}`;
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
                setTimeout(()=>{$(".column-2 #multi-list_sDate").attr('colspan',5);}, 1); 
                self.colorText = (value: any, obj: any) => {
                    setTimeout(() => {
                        let cell1 = $("#multi-list").igGrid("cellById", obj.id, 'sDate');
                        let cell2 = $("#multi-list").igGrid("cellById", obj.id, 'sTime');
                        let cell3 = $("#multi-list").igGrid("cellById", obj.id, 'textPeriod');
                        let cell4 = $("#multi-list").igGrid("cellById", obj.id, 'eDate');
                        let cell5 = $("#multi-list").igGrid("cellById", obj.id, 'eTime');
                        let checkWeekDayIsSta = `${obj.id}`.lastIndexOf('a');
                        let checkWeekDayIsSun = `${obj.id}`.lastIndexOf('n');
                        if(checkWeekDayIsSta > -1){
                            cell1.css("color", "#0086EA");
                            cell2.css("color", "#0086EA");
                            cell3.css("color", "#0086EA");
                            cell4.css("color", "#0086EA");
                            cell5.css("color", "#0086EA");
                        }
                        if(checkWeekDayIsSun > -1){
                            cell1.css("color", "#FF2D2D");
                            cell2.css("color", "#FF2D2D");
                            cell3.css("color", "#FF2D2D");
                            cell4.css("color", "#FF2D2D");
                            cell5.css("color", "#FF2D2D");
                        }
                        cell5.css('border-right', '1px solid black');
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

                self.empInfoTerCode(getShared('KNR002B_empInfoTerCode'));
                self.empInfoTerName(getShared('KNR002B_empInfoTerName'));
                self.modelEmpInfoTer(getShared('KNR002B_modelEmpInfoTer'));
                self.workLocationName(getShared('KNR002B_workLocationName'));
                self.lastSuccessDate(getShared('KNR002B_lastSuccessDate'));
                self.status(getShared('KNR002B_status'));

                service.getInPeriod(self.empInfoTerCode(), self.sTime(), self.eTime()).done((data) => {
                    if(data.length <= 0){
                        //do something
                    } else {
                        let displayLogListTemp = [];
                        for (let i = 0; i < data.length; i++) { 
                            try {
                                var sDay = new Date(data[i].sTime).getDay();
                                var eDay = new Date(data[i].eTime).getDay();
                                let displayLog = new DisplayLog(`${data[i].sTime}${self.getDayOfWeek(sDay)}`, data[i].sTime, `${data[i].eTime}${self.getDayOfWeek(eDay)}`, data[i].eTime);
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
                    $('#B6_1').focus();
                });
                blockUI.clear();   																			
                dfd.resolve();											
                return dfd.promise();											
            }
           
            /**
             * B4_3
             * 抽出ボタン
             */
            private extract(): any {
                let self = this;
                if(self.hasError()){
                    return;
                }
                console.log("the start time: ", self.sTime());
                console.log("the end time: ", self.eTime());
                //process
                
                $('#B6_1').focus();
            } 

            /**
             * B6_1
             * 閉じるボタン
             */
            private cancel_Dialog(): any {
                let self = this;
                nts.uk.ui.windows.close();
            }
             /**
             * Check Input Errors
             */
            private hasError(): boolean{
                var self = this;
                self.clearErrors();
                //period time
                $('#B4_2').ntsEditor("validate");
                if ($('.nts-input').ntsError('hasError')) {
                    return true;
                }
                return false;
            }

            /**
             * clear Errors
             */
            private clearErrors(): void{     
                $('#B4_2').ntsError('clear');
                $('.nts-input').ntsError('clear');
                nts.uk.ui.errors.clearAll();
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