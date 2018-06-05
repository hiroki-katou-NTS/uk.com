module nts.uk.at.view.kdl029.a.screenModel {

    import dialog = nts.uk.ui.dialog.info;
    import text = nts.uk.resource.getText;
    import formatDate = nts.uk.time.formatDate;
    import block = nts.uk.ui.block;
    import jump = nts.uk.request.jump;
    import alError = nts.uk.ui.dialog.alertError;
    import ListType = kcp.share.list.ListType;
    import UnitModel = kcp.share.list.UnitModel;
    import service = nts.uk.at.view.kdl029.a.service;

    export class ViewModel {

        columnHolidayGrantInfos = ko.observableArray([
            { headerText: text('KDL029_9'), prop: 'fundedDate', width: 90 },
            { headerText: text('KDL029_10'), prop: 'fundedNumber', width: 90 },
            { headerText: text('KDL029_11'), prop: 'numberOfUses', width: 90 },
            { headerText: text('KDL029_12'), prop: 'residualNumber', width: 90 },
            { headerText: text('KDL029_13'), prop: 'deadline', width: 90 }
        ]);
        dataHolidayGrantInfo: KnockoutObservableArray<DataHolidayGrantInfo> = ko.observableArray([]);
        columnSteadyUseInfors = ko.observableArray([
            { headerText: text('KDL029_17'), prop: 'date', width: 90 },
            { headerText: text('KDL029_18'), prop: 'steadNumberOfUser', width: 90 },
            { headerText: text('KDL029_19'), prop: 'typeOfStead', width: 125 }
        ]);
        dataSteadyUseInfor: KnockoutObservableArray<DataSteadyUseInfor> = ko.observableArray([]);
        
        value: KnockoutObservable<string> = ko.observable('');
        employeeCode: KnockoutObservable<string> = ko.observable('');
        employeeName: KnockoutObservable<string> = ko.observable('');
        listComponentOption: any;
        employeeList: KnockoutObservableArray<UnitModel>;
        employeeIDList: KnockoutObservableArray<string> = ko.observableArray([]);
        selectedType: KnockoutObservable<number> = ko.observable(0);
        multiSelect: KnockoutObservable<boolean> = ko.observable(false);
        inputDate: KnockoutObservable<string> = ko.observable('');
        constructor() {
            let self = this;
             self.employeeList = ko.observableArray<UnitModel>([
                { code: '1', name: 'Angela Baby', id: 'HN' },
                { code: '2', name: 'Xuan Toc Doaslkdhasklhdlashdhlashdl', id: 'HN' },
                { code: '3', name: 'Park Shin Hye', id: 'HCM' },
                { code: '4', name: 'Vladimir Nabokov', id: 'HN' }
            ]);
            self.employeeIDList(nts.uk.ui.windows.getShared('employeeIDList'));
            self.multiSelect(nts.uk.ui.windows.getShared('multiSelect'));
            self.inputDate(nts.uk.ui.windows.getShared('inputDate'));
            self.listComponentOption = {
                isShowAlreadySet: false,
                isMultiSelect: self.multiSelect(),
                listType: ListType.EMPLOYEE,
                employeeInputList: self.employeeList,
                selectType: self.selectedType(),
                selectedCode: self.employeeCode,
                isDialog: true,
                isShowNoSelectRow: false,
                alreadySettingList: ko.observable(false),
                isShowWorkPlaceName: false,
                isShowSelectAllButton: false,
                maxRows: 12
            };
             $('#component-items-list').ntsListComponent(self.listComponentOption).done(function() {
                $('#component-items-list').focusComponent();
            });
            self.start().done(function(){
               
            })
        }
        
        closeDialog() {
            nts.uk.ui.windows.close();
        }
        start(): JQueryPromise<any> {            block.invisible();
            var self = this,
            dfd = $.Deferred();
            block.clear();
            service.findAllEmploymentSystem({
                mode: self.multiSelect(),
                inputDate:  nts.uk.util.isNullOrEmpty(self.inputDate()) ? null : moment(self.inputDate()).format("yyyy/MM/dd"),
                employeeIDs: self.employeeIDList()
            }).done(function(data){
                self.employeeCode(data.employeeCode);
                self.employeeName(data.employeeName);
                 let dataHoliday = [];
                dataHoliday.push(new DataHolidayGrantInfo("2018/07/06",
                         5,
                          5,
                           5, 
                          "2018/0707"));
                self.dataHolidayGrantInfo(dataHoliday);
                self.getDataForTable(data);
                

                self.employeeCode.subscribe(function(value) {
                    let name = _.find(self.employeeList(), function(o) {
                        if (o.code == value) {
                            return o.name;
                        }
                    });
                    if(!nts.uk.util.isNullOrEmpty(name)){
                        let employeeIDs =[];
                        let dfd =  $.Deferred();
                        employeeIDs.push(name.id);
                        self.employeeName(name.name);
                        service.findByEmployee({
                            mode: self.multiSelect(),
                            inputDate:  nts.uk.util.isNullOrEmpty(self.inputDate()) ? null : moment(self.inputDate()).format("yyyy/MM/dd"),
                            employeeIDs: employeeIDs
                        }).done(data =>{
                            self.getDataForTable(data);
                            dfd.resolve();
                        }).fail(res =>{
                            dfd.reject();
                        });
                         return dfd.promise();
                    }
                });
                dfd.resolve();
            }).fail(function(error){
                dfd.reject();
            });
            return dfd.promise();

        }
        getDataForTable(data :any){
            let self = this;
            if (!nts.uk.util.isNullOrEmpty(data.reserveLeaveManagerImport)) {
                let dataRessult = data.reserveLeaveManagerImport.fundingYearHolidayGrantInfor;
                let dataHoliday = [];
                for (let i = 0; i < dataRessult.length; i++) {
                    dataHoliday.push(new DataHolidayGrantInfo(dataRessult[i].grantDate,
                        dataRessult[i].grandNumber,
                        dataRessult[i].daysUsedNo,
                        dataRessult[i].remainDays,
                        dataRessult[i].deadline));
                }
                self.dataHolidayGrantInfo(dataHoliday);
                let yearlyRessult = data.reserveLeaveManagerImport.yearlySupensionManageInfor;
                let dataYearly = [];
                for (let i = 0; i < yearlyRessult.length; i++) {
                    dataYearly.push(new DataSteadyUseInfor(yearlyRessult[i].ymd, yearlyRessult[i].dayUseNo, yearlyRessult[i].scheduleRecordAtr));
                }
                self.dataSteadyUseInfor(dataYearly);
            }
        }    }
    export class DataHolidayGrantInfo{
        fundedDate: string;
        fundedNumber: number;
        numberOfUses: number;
        residualNumber: number;
        deadline:string;
        constructor(fundedDate: string,fundedNumber: number,numberOfUses: number,residualNumber: number,deadline:string ){
            this.fundedDate = fundedDate;
            this.fundedNumber = fundedNumber;
            this.numberOfUses = numberOfUses;
            this.residualNumber = residualNumber;
            this.deadline = deadline;
        }
    }
    export class DataSteadyUseInfor{
        date: string;
        steadNumberOfUser: number;
        typeOfStead: string;
        constructor(date: string,steadNumberOfUser: number,typeOfStead: string){
            this.date = date;
            this.steadNumberOfUser = steadNumberOfUser;
            this.typeOfStead = typeOfStead;
        }
    }
}