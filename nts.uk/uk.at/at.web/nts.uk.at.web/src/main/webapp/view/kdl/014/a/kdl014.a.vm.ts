module nts.uk.at.view.kdl014.a {
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import info = nts.uk.ui.dialog.info;
    import error = nts.uk.ui.dialog.error;
    import getShared = nts.uk.ui.windows.getShared;
    
    export class ScreenModel {
        empInfomationList = ko.observableArray([]);
        columns: any;
        currentCode = ko.observable();
        selectedItem = ko.observable( '' );
        listComponentOption: any;
        employeeInputList = ko.observableArray([]);
        dataServer = [];
        paramFromParent: ParamFromParent;
        display: boolean;
        height: number;
        
        constructor(){
            let self = this;
            self.paramFromParent = getShared('KDL014-PARAM');
            
            let tg = [];
            _.forEach(self.paramFromParent.listEmp, function(item) {
                tg.push({ id: item.employeeId, code: item.employeeCode, businessName: item.employeeName, workplaceName: item.affiliationName, depName: '' });
            });
            
            self.employeeInputList(_.orderBy(tg, ['code'], ['asc']));
            
            self.selectedItem.subscribe((newValue) => {
                $(document).ready(function() {
                    $('#btnClose').focus();
                });
                self.filterGrid(newValue);
           
            });
        }
        
        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            block.grayout();

            if (self.paramFromParent) {
                let st = new Date(new Date(self.paramFromParent.startDate).setDate(new Date(new Date(self.paramFromParent.startDate)).getDate() - 1));
                let end = new Date(new Date(self.paramFromParent.endDate).setDate(new Date(new Date(self.paramFromParent.endDate)).getDate() + 2));
                
                let param = {
                  start: self.paramFromParent.startDate === self.paramFromParent.endDate ? st : new Date(self.paramFromParent.startDate),
                    end: self.paramFromParent.startDate === self.paramFromParent.endDate ? end : new Date(self.paramFromParent.endDate),
                    mode: self.paramFromParent.mode,
                    listEmp: self.paramFromParent.listEmp
                };
                service.getInfo(param).done(function(data) {
                    console.log(data);
                    self.dataServer = data.listEmps;
                    self.display = data.display;
            
                    if (self.paramFromParent.mode == 0) {
                        self.selectedItem(self.employeeInputList()[0].id);
                        self.bindComponent();
                    } else {
                        let tg = [];
                        _.forEach(self.dataServer, function(item) {
                            if (self.display == false) {
                                item.locationInfo = null;
                            }
                            tg.push(new EmpInfomation(item));
                        });
                        self.empInfomationList(tg);
                    }
                    self.bindingGrid();
                    $(document).ready(function() {
                        $('#btnClose').focus();
                    });
                }).fail(function(res) {
                    error({ messageId: res.messageId }).then(() => {
                        self.cancel_Dialog();
                    });
                }).always(function() {
                    self.bindingGrid();
                    block.clear();
                    dfd.resolve();
                });
            } else {
                dfd.resolve();
                block.clear();
            } 
            return dfd.promise();
        }
        
        bindComponent(){
            let self = this;
            $('#emp-component').ntsLoadListComponent({
                systemReference: SystemType.EMPLOYMENT,
                isDisplayOrganizationName: false,
                employeeInputList: self.employeeInputList,
                targetBtnText: getText('KCP009_3'),
                selectedItem: self.selectedItem,
                tabIndex: 1
            });
        }
        
        bindingGrid(){
            let self = this;
            if (self.paramFromParent.mode == 1) {
                self.columns = ko.observableArray([
                    { headerText: getText("KDL014_11"), key: 'code', hidden: true },
                    { headerText: "<div style='text-align: center;'>"+getText("KDL014_12")+ "</div>" , key: 'name', width: 150},
                    { headerText: "<div style='text-align: center;'>"+getText("KDL014_13")+ "</div>" , key: 'dateShow', width: 115},
                    { headerText: "<div style='text-align: center;'>"+getText("KDL014_14")+ "</div>" , key: 'time', width: 80},
                    { headerText: "<div style='text-align: center;'>"+getText("KDL014_15")+ "</div>" , key: 'stampAtr', width: 90},
                    { headerText: "<div style='text-align: center;'>"+getText("KDL014_16")+ "</div>" , key: 'workLocationName', width: 180 },
                    { headerText: "<div style='text-align: center;'>"+getText("KDL014_17")+ "</div>" , key: 'locationInfo', width: 50, hidden: !self.display }
                ]);
            } else if (self.paramFromParent.mode == 0) {
                self.columns = ko.observableArray([
                    { headerText: getText("KDL014_11"), key: 'code', hidden: true },
                    { headerText: getText("KDL014_12"), key: 'name', hidden: true },
                    { headerText: "<div style='text-align: center;'>" + getText("KDL014_13") + "</div>", key: 'dateShow', width: 115 },
                    { headerText: "<div style='text-align: center;'>" + getText("KDL014_14") + "</div>", key: 'time', width: 80 },
                    { headerText: "<div style='text-align: center;'>" + getText("KDL014_15") + "</div>", key: 'stampAtr', width: 90 },
                    { headerText: "<div style='text-align: center;'>" + getText("KDL014_16") + "</div>", key: 'workLocationName', width: 283},
                    { headerText: "<div style='text-align: center;'>" + getText("KDL014_17") + "</div>", key: 'locationInfo', width: 50, hidden: !self.display }
                ]);
            }
            
        }
        
        filterGrid(id:string){
            let self = this;
            let tg = [];
            self.empInfomationList([]);
            _.forEach(self.dataServer, function(item) {
                if (item.employeeId === id) {
                    if (self.display == false) {
                        item.locationInfo = null;
                    }
                    tg.push(new EmpInfomation(item));
                }
            });
            
            self.empInfomationList(_.orderBy(tg, ['code', 'dateShow', 'time'], ['asc']));
        }
        
        cancel_Dialog(): any {
            nts.uk.ui.windows.close();
        }
        
    }
    
    class EmpInfomation {
        code: number;
        name: string;
        stampDateTime: string;
        stampMeans: string;
        stampAtr: string;
        workLocationName: string;
        locationInfo: string;
        time: string;
        date: string;
        color: number;
        dateShow: string;
        
        constructor(param: any) {
            let self = this;
            self.code = param.code;
            self.name = param.name;
            self.stampDateTime = new Date().toString();
            self.date = param.date;
            self.time = param.time;
            param.stampAtr = param.stampAtr.trim();

            if (param.stampAtr === '出勤' || param.stampAtr === '入門' || param.stampAtr === '応援開始' || param.stampAtr === '直行' || param.stampAtr === '早出'
                || param.stampAtr === '応援休出' || param.stampAtr === '応援早出' || param.stampAtr === '休出' || param.stampAtr === '応援出勤' || param.stampAtr === '臨時出勤') {

                self.stampAtr = `<div style="text-align: left">` + param.stampAtr + '</div>';

            } else if (param.stampAtr === '退勤' || param.stampAtr === '退門' || param.stampAtr === '応援終了' || param.stampAtr === '臨時退勤' || param.stampAtr === '直帰' || param.stampAtr === '退勤+残業') {
                self.stampAtr = `<div style="text-align: right">` + param.stampAtr + '</div>';

            } else {
                self.stampAtr = `<div style="text-align: center">` + param.stampAtr + '</div>';
            }
             
            self.workLocationName = param.workLocationName;
            
            if (_.includes([0,1,2,3,4,8], param.stampMeans)) {
                self.time = "<span>" + getText("KDP002_120") + "</span><span class='time'>" + param.time + "</span>";
            
                // 5:スマホ打刻
            } else if (param.stampMeans == 5) {
                self.time = "<span>" + getText("KDP002_121") + "</span><span class='time'>" + param.time + "</span>";

                // 6:タイムレコーダー打刻
            } else if (param.stampMeans == 6) {
                self.time = "<span>" + getText("KDP002_122") + "</span><span class='time'>" + param.time + "</span>";

            } else {
                self.time = "<span class='time'>" + param.time + "</span>";
            }
            
            let date = moment(param.stampDateTime).format("YYYY/MM/DD");
            
            if(new Date(date).getDay() == 6){
                self.dateShow = "<span class='color-schedule-saturday'>" + nts.uk.time.applyFormat("Short_YMDW", date) + "</span>";
            
            } else if (new Date(date).getDay() == 0) {
                self.dateShow = "<span class='color-schedule-sunday'>" + nts.uk.time.applyFormat("Short_YMDW", date) + "</span>";
            
            } else {
                self.dateShow = "<span>"+ nts.uk.time.applyFormat("Short_YMDW", date) + "</span>"            
            }
            
            if (param.locationInfo !== null) {
                self.locationInfo = '<a onClick = "gotoMap(' + param.locationInfo.latitude + ',' + param.locationInfo.longitude + ')"><img src="../img/Mapアイコン画像.png" height="20" width="20"/></a>';
            }
        }
        
    }
    
    class SystemType {
        static EMPLOYMENT = 1;
        static SALARY = 2;
        static PERSONNEL = 3;
        static ACCOUNTING = 4;
        static OH = 6;
    }
    
    interface ParamFromParent {
        startDate: string; //YYYY/MM/DD
        endDate: string; ////YYYY/MM/DD
        mode: number; //mode = 0 => Person, mode = 1 => Date
        listEmp: EmployeeInfor[];//{employeeId: 'ae7fe82e-a7bd-4ce3-adeb-5cd403a9d570', employeeCode: '', employeeName: '', affiliationName: ''}
    }

    interface EmployeeInfor {
        employeeId: string;
        employeeCode: string;
        employeeName: string;
        affiliationName: string;
    }
    
}

function gotoMap(latitude: string, longitude: string): any {
    window.open('https://www.google.co.jp/maps/place/' + latitude + ',' + longitude);
}

