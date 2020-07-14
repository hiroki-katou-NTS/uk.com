module nts.uk.at.view.kdl014.a {
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import info = nts.uk.ui.dialog.info;
    import error = nts.uk.ui.dialog.error;
    export class ScreenModel {
        empInfomationList = ko.observableArray([]);
        columns: any;
        currentCode = ko.observable();
        selectedItem = ko.observable( '' );
        listComponentOption: any;
        employeeInputList = ko.observableArray([]);
        
        //Param start
        start: string; //YYYY/MM/DD
        end:string; ////YYYY/MM/DD
        mode: number; //mode = 0: person, mode = 1: date
        listEmp = [];//{employeeId: 'ae7fe82e-a7bd-4ce3-adeb-5cd403a9d570', employeeCode: '', employeeName: '', affiliationName: ''}
        dataServer = [];
        
        constructor(){
            let self = this;
            self.mode = 0;
            
            if (self.mode == 1) {
                self.columns = ko.observableArray([
                    { headerText: getText("KDL014_11"), key: 'code', hidden: true },
                    { headerText: "<div style='text-align: center;'>"+getText("KDL014_12")+ "</div>" , key: 'name', width: 150},
                    { headerText: "<div style='text-align: center;'>"+getText("KDL014_13")+ "</div>" , key: 'dateShow', width: 115},
                    { headerText: "<div style='text-align: center;'>"+getText("KDL014_14")+ "</div>" , key: 'time', width: 80},
                    { headerText: "<div style='text-align: center;'>"+getText("KDL014_15")+ "</div>" , key: 'stampAtr', width: 100},
                    { headerText: "<div style='text-align: center;'>"+getText("KDL014_16")+ "</div>" , key: 'workLocationName', width: 500},
                    { headerText: "<div style='text-align: center;'>"+getText("KDL014_17")+ "</div>" , key: 'locationInfo', width: 150},
                ]);
            } else {
                self.columns = ko.observableArray([
                
                { headerText: getText("KDL014_11"), key: 'code', hidden: true },
                    { headerText: getText("KDL014_12"), key: 'name', hidden: true },
                    { headerText: "<div style='text-align: center;'>"+getText("KDL014_13")+ "</div>" , key: 'dateShow', width: 115},
                    { headerText: "<div style='text-align: center;'>"+getText("KDL014_14")+ "</div>" , key: 'time', width: 80},
                    { headerText: "<div style='text-align: center;'>"+getText("KDL014_15")+ "</div>" , key: 'stampAtr', width: 100},
                    { headerText: "<div style='text-align: center;'>"+getText("KDL014_16")+ "</div>" , key: 'workLocationName', width: 500},
                    { headerText: "<div style='text-align: center;'>"+getText("KDL014_17")+ "</div>" , key: 'locationInfo', width: 150},
                ]);
               
                self.employeeInputList.push({ id: 'ae7fe82e-a7bd-4ce3-adeb-5cd403a9d570', code: 'A000000000001', businessName: '日通　純一郎1', workplaceName: '名古屋支店', depName: 'Dep Name' },
                { id: '8f9edce4-e135-4a1e-8dca-ad96abe405d6', code: 'A000000000002', businessName: '日通　純一郎2', workplaceName: '名古屋支店', depName: 'Dep Name' });
                
                $('#emp-component').ntsLoadListComponent({
                    systemReference: SystemType.EMPLOYMENT,
                    isDisplayOrganizationName: true,
                    employeeInputList: self.employeeInputList,
                    targetBtnText: getText('KCP009_3'),
                    selectedItem: self.selectedItem,
                    tabIndex: 1
                });
                
                self.selectedItem.subscribe((newValue) => {
                    self.filterGrid(newValue)
                });
            }
        }
        
        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            block.grayout();
            let param = {
                start: new Date('2019-07-13'),
                end:new Date('2020-07-14'),
                mode: 0, 
                listEmp: [
                    {employeeId: 'ae7fe82e-a7bd-4ce3-adeb-5cd403a9d570', employeeCode: '', employeeName: '', affiliationName: ''},
                    {employeeId: '8f9edce4-e135-4a1e-8dca-ad96abe405d6', employeeCode: '', employeeName: '', affiliationName: ''}
                ] 
            };
            
            service.getInfo(param).done(function(data) {
                _.orderBy(data, ['name','stampDateTime'], ['asc','asc']);
                console.log(data);
                self.dataServer = data.listEmps;
                self.selectedItem(self.employeeInputList()[0].id);
                dfd.resolve();
            }).fail(function (res) {
                error({ messageId: res.messageId });
            }).always(function () {
                block.clear();
            });
            
            dfd.resolve();
            return dfd.promise();
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
            
            if ([1,2,3,4,7,8].includes(param.stampMeans)) {
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
            
            if (param.locationInfo !== null) {
                this.locationInfo = '<a href="https://www.google.co.jp/maps/place/' + param.locationInfo.latitude + ',' + param.locationInfo.longitude + '"><img src="../img/Mapアイコン画像.png" height="20" width="20"/></a>';
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
}