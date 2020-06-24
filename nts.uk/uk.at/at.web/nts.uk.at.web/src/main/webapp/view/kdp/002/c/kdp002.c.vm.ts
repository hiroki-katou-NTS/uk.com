module nts.uk.at.view.kdp002.c {
    export module viewmodel {
        export class ScreenModel {

            // B2_2
            employeeCodeName: KnockoutObservable<string> = ko.observable("");
            // B3_2
            dayName: KnockoutObservable<string> = ko.observable("");
            // B3_3
            timeName: KnockoutObservable<string> = ko.observable("");
            // G4_2
            checkHandName: KnockoutObservable<string> = ko.observable("");
            // G5_2
            numberName: KnockoutObservable<string> = ko.observable("");
            // G6_2
            laceName: KnockoutObservable<string> = ko.observable("");

            workName1: KnockoutObservable<string> = ko.observable("");

            workName2: KnockoutObservable<string> = ko.observable("");
            
            timeName1: KnockoutObservable<string> = ko.observable("");
            
            timeName2: KnockoutObservable<string> = ko.observable("");


            items: KnockoutObservableArray<model.ItemModels> = ko.observableArray([]);
            columns2: KnockoutObservableArray<NtsGridListColumn>;
            currentCode: KnockoutObservable<any> = ko.observable();
            currentCodeList: KnockoutObservableArray<any>;
            permissionCheck: KnockoutObservable<boolean> = ko.observable(false);
            displayButton: KnockoutObservable<boolean> = ko.observable(false);

            constructor() {
                let self = this;
   
                self.columns2 = ko.observableArray([
                    { headerText: nts.uk.resource.getText("KDP002_59"), key: 'itemId', width: 200, hidden: true },
                    { headerText: nts.uk.resource.getText("KDP002_59"), key: 'name', width: 175 },
                    { headerText: nts.uk.resource.getText("KDP002_60"), key: 'value', width: 175 }
                ]);
            }
            /**
             * start page  
             */
            public startPage(): JQueryPromise<any> {
                let self = this,
                    dfd = $.Deferred();
                let itemIds = nts.uk.ui.windows.getShared("KDP010_2C");
                let data = {
                    stampDate: moment().format("YYYY/MM/DD"),
                    attendanceItems: itemIds
                }
                self.getEmpInfo();
        
                service.startScreen(data).done((res) => {
                    console.log(res);
                    if (res) {
                        if(_.size(res.stampRecords) > 0){
                            let dateDisplay =res.stampRecords[0].stampDate;
                            res.stampRecords = _.orderBy(res.stampRecords, ['stampTimeWithSec'], ['desc'])
                            if (moment(res.stampRecords[0].stampDate).day() == 6) {
                                dateDisplay = "<span class='color-schedule-saturday' style='float:left;'>" + dateDisplay + "</span>";
                            } else if (moment(res.stampRecords[0].stampDate).day() == 0) {
                                dateDisplay = "<span class='color-schedule-sunday' style='float:left;'>" + dateDisplay + "</span>";
                            }
                            self.checkHandName(res.stampRecords.length > 0 ?  res.stampRecords[0].stampArtName : 0);
                            self.numberName();
                            self.laceName(res.stampRecords[0].workLocationCD +" "+res.workPlaceName);
                            self.dayName(dateDisplay);
                            self.timeName(res.stampRecords[0].stampTime);
                            
                            self.timeName1(res.attendance ? nts.uk.time.format.byId("ClockDay_Short_HM", parseInt(res.attendance)) + " ~ " : null);
                            self.timeName2(res.leave ? nts.uk.time.format.byId("ClockDay_Short_HM", parseInt(res.leave)): null);
                            self.workName1(res.workTypes.length > 0 ? res.workTypes[0].name : '');
                            self.workName2(res.workTimeTypes.length > 0 ? res.workTimeTypes[0].name : '');      
							
                            if(res.itemValues) {
                                // C4	実績の属性と表示書式について
                                res.itemValues.forEach(item => {
                                    if(item.itemId == 28 || item.itemId == 29 || item.itemId == 31 || item.itemId == 34) {
                                        item.value = '';
                                    } else if((item.valueType == "TIME") && item.value) {
                                        item.value = nts.uk.time.format.byId("Clock_Short_HM", parseInt(item.value));
                                    } else if (item.valueType == "AMOUNT") {
                                        item.value = nts.uk.ntsNumber.formatNumber(item.value, new nts.uk.ui.option.NumberEditorOption({grouplength: 3, decimallength: 2}));
                                    } else if ((item.valueType == "TIME_WITH_DAY" || item.valueType == "CLOCK" ) && item.value) {
                                        item.value = nts.uk.time.format.byId("ClockDay_Short_HM", parseInt(item.value));
                                    } else if ((item.valueType == "DAYS" || item.valueType == "COUNT") && item.value ) {
                                        item.valueType = nts.uk.ntsNumber.formatNumber(parseFloat(item.valueType), new nts.uk.ui.option.NumberEditorOption({grouplength: 3, decimallength: 1}));
                                    }
                                });
                            }

                            self.items(res.itemValues);
                        }
                    }
                    if(res.confirmResult){
                        self.permissionCheck(res.confirmResult.permissionCheck == 1 ? true: false);       
                    } else {
                        self.displayButton(false);
                    }
                });

                dfd.resolve();
                return dfd.promise();
            }
            getEmpInfo(): JQueryPromise<any> { 
                let self = this;
                let dfd = $.Deferred();
                let employeeId = __viewContext.user.employeeId;
                service.getEmpInfo(employeeId).done(function(data) {
                    self.employeeCodeName(data.employeeCode +" "+ data.personalName);
                    dfd.resolve();
                });
                return dfd.promise();
            }

            /**
             * Close dialog
             */
            public closeDialog(): void {
                nts.uk.ui.windows.close();
            }

            /**
             * Close dialog
             */
            public registerDailyIdentify(): void {
                service.registerDailyIdentify().done(() =>{
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" })
                    .then(() => {
                        nts.uk.ui.windows.close();
                    });
                });
            }
        }
    }
    export module model {
        export class ItemModels {
            itemId: string;
            name: string;
            value: string;
            constructor(code: string, name: string, value: string) {
                this.code = code;
                this.name = name;
                this.value = value;
            }
        }
    }
}