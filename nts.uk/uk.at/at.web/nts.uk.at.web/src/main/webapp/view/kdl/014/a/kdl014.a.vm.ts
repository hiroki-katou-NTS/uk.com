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
        
        constructor(){
            let self = this;
            self.paramFromParent = getShared('KDL014-PARAM');
            
              if (self.paramFromParent.mode == 1) {
                self.columns = ko.observableArray([
                    { headerText: getText("KDL014_11"), key: 'code', hidden: true },
                    { headerText: "<div style='text-align: center;'>"+getText("KDL014_12")+ "</div>" , key: 'name', width: 150},
                    { headerText: "<div style='text-align: center;'>"+getText("KDL014_13")+ "</div>" , key: 'dateShow', width: 115},
                    { headerText: "<div style='text-align: center;'>"+getText("KDL014_14")+ "</div>" , key: 'time', width: 80},
                    { headerText: "<div style='text-align: center;'>"+getText("KDL014_15")+ "</div>" , key: 'stampAtr', width: 70},
                    { headerText: "<div style='text-align: center;'>"+getText("KDL014_16")+ "</div>" , key: 'workLocationName', width: 200},
                    { headerText: "<div style='text-align: center;'>"+getText("KDL014_17")+ "</div>" , key: 'locationInfo', width: 50}
                ]);
            } else if (self.paramFromParent.mode == 0) {
                self.columns = ko.observableArray([
                    { headerText: getText("KDL014_11"), key: 'code', hidden: true },
                    { headerText: getText("KDL014_12"), key: 'name', hidden: true },
                    { headerText: "<div style='text-align: center;'>" + getText("KDL014_13") + "</div>", key: 'dateShow', width: 115 },
                    { headerText: "<div style='text-align: center;'>" + getText("KDL014_14") + "</div>", key: 'time', width: 80 },
                    { headerText: "<div style='text-align: center;'>" + getText("KDL014_15") + "</div>", key: 'stampAtr', width: 90 },
                    { headerText: "<div style='text-align: center;'>" + getText("KDL014_16") + "</div>", key: 'workLocationName', width: 250 },
                    { headerText: "<div style='text-align: center;'>" + getText("KDL014_17") + "</div>", key: 'locationInfo', width: 50 }
                ]);
            }
               
            let tg = [];
            _.forEach(self.paramFromParent.listEmp, function(item) {
                tg.push({ id: item.employeeId, code: item.employeeCode, businessName: item.employeeName, workplaceName: item.affiliationName, depName: '' });
            });
            self.employeeInputList(tg);
            
            self.selectedItem.subscribe((newValue) => {
                self.filterGrid(newValue)
            });
            
        }
        
        startPage(): JQueryPromise<any> {
            let self = this;
            
            let dfd = $.Deferred();
            block.grayout();
            
            if (self.paramFromParent) {
                let param = {
                    start: new Date(self.paramFromParent.startDate),
                    end: new Date(self.paramFromParent.endDate),
                    mode: self.paramFromParent.mode,
                    listEmp: self.paramFromParent.listEmp
                };
                service.getInfo(param).done(function(data) {
                    _.orderBy(data, ['name', 'stampDateTime'], ['asc', 'asc']);
                    console.log(data);
                    self.dataServer = data.listEmps;
                    self.selectedItem(self.employeeInputList()[0].id);
                    self.bindComponent();
                    dfd.resolve();
                }).fail(function(res) {
                    error({ messageId: res.messageId });
                }).always(function() {
                    block.clear();
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
        
        filterGrid(id:string){
            let self = this;
            let tg = [];
            self.empInfomationList([]);
            _.forEach(self.dataServer, function(item) {
                if(item.employeeId === id){
                    tg.push(new EmpInfomation(item));
                }
            });
            self.empInfomationList(tg);
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
            
            // 0:出勤
            if (param.stampAtr === '出勤') {
                self.stampAtr = `<div style="text-align: left">` + param.stampAtr + '</div>';

                // 1:退勤  
            } else if (param.stampAtr === '退勤') {
                self.stampAtr = `<div style="text-align: right">` + param.stampAtr + '</div>';

            } else {
                self.stampAtr = `<div style="text-align: center">` + param.stampAtr + '</div>';
            }
             
            self.workLocationName = param.workLocationName;
            
            if (_.includes([1,2,3,4,7,8], param.stampMeans)) {
                self.time = "<span>" + getText("KDP002_120") + "   " + param.time + "</span>";
            
                // 5:スマホ打刻
            } else if (param.stampMeans == 5) {
                self.time = "<span>" + getText("KDP002_121") + "   " + param.time + "</span>";

                // 6:タイムレコーダー打刻
            } else if (param.stampMeans == 6) {
                self.time = "<span>" + getText("KDP002_122") + "   " + param.time + "</span>";

            } else {
                self.time = "<span>     " + param.time + "</span>";
            }
            
            let date = moment(param.stampDateTime).format("YYYY/MM/DD");
            
            if(new Date(date).getDay() == 6){
                self.dateShow = "<span class='color-schedule-saturday'> " + nts.uk.time.applyFormat("Short_YMDW", date) + "</span>";
            
            } else if (new Date(date).getDay() == 0) {
                self.dateShow = "<span class='color-schedule-sunday'> " + nts.uk.time.applyFormat("Short_YMDW", date) + "</span>";
            
            } else {
                self.dateShow = "<span>"+ nts.uk.time.applyFormat("Short_YMDW", date) + "</span>"            
            }
            
//            if (param.locationInfo === null) {
//                self.locationInfo = '<a href="https://www.google.co.jp/maps/place/' + param.locationInfo.latitude + ',' + param.locationInfo.longitude + '"><img src="../img/Mapアイコン画像.png" height="20" width="20"/></a>';
//            }
            self.locationInfo = '<div style="text-align: center;"><a href="https://www.google.co.jp/maps/place/35.165556, 136.905333"><img src="../img/Mapアイコン画像.png" height="20" width="20"/></a></div>';
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