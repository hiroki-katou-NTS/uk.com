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
            {headerText: 'ID', prop: 'id', hidden: true},
            { headerText: text('KDL029_9'), prop: 'fundedDate', width: 90 },//付与日
            { headerText: text('KDL029_10'), prop: 'fundedNumber', width: 90 },//付与日数
            { headerText: text('KDL029_11'), prop: 'numberOfUses', width: 90 },//使用数
            { headerText: text('KDL029_12'), prop: 'residualNumber', width: 90 },//残日数 
            { headerText: text('KDL029_13'), prop: 'deadline', width: 90 }//期限日
        ]);
        dataHolidayGrantInfo: KnockoutObservableArray<DataHolidayGrantInfo> = ko.observableArray([]);
        columnSteadyUseInfors = ko.observableArray([
            {headerText: 'ID', prop: 'id', hidden: true},
            { headerText: text('KDL029_17'), prop: 'date', width: 90 },
            { headerText: text('KDL029_18'), prop: 'steadNumberOfUser', width: 90 },
            { headerText: text('KDL029_19'), prop: 'typeOfStead', width: 125 }
        ]);
        dataSteadyUseInfor: KnockoutObservableArray<DataSteadyUseInfor> = ko.observableArray([]);
        
        value: KnockoutObservable<string> = ko.observable('');
        employeeCode: KnockoutObservable<string> = ko.observable('');
        employeeName: KnockoutObservable<string> = ko.observable('');
        listComponentOption: any;
        employeeList: KnockoutObservableArray<any> = ko.observableArray([]);
        employeeIDList: KnockoutObservableArray<string> = ko.observableArray([]);
        selectedType: KnockoutObservable<number> = ko.observable(0);
        multiSelect: KnockoutObservable<boolean> = ko.observable(false);
        inputDate: KnockoutObservable<string> = ko.observable('');
        totalRemain: KnockoutObservable<string> = ko.observable('0.0 日');
        displayKCP005: KnockoutObservable<boolean> = ko.observable(false);
        lstEmpFull: KnockoutObservableArray<any> = ko.observableArray([]);
        constructor() {
            let self = this;
            let param = nts.uk.ui.windows.getShared('KDL029_PARAM');
            self.employeeIDList(param.employeeIds);
            self.multiSelect(false);
            self.inputDate(param.baseDate);
            self.start().done(function(){
                if(self.employeeList().length >1){
                    self.employeeCode(self.employeeList()[0].code);
                    self.displayKCP005(true);
                    self.listComponentOption = {
                        isShowAlreadySet: false,
                        isMultiSelect: false,
                        listType: ListType.EMPLOYEE,
                        employeeInputList: self.employeeList,
                        selectType: 1,
                        selectedCode: self.employeeCode,
                        isDialog: true,
                        isShowNoSelectRow: false,
                        alreadySettingList: ko.observableArray([]),
                        isShowWorkPlaceName: false,
                        isShowSelectAllButton: false,
                        maxRows: 12
                    };
                    
                     $('#component-items-list').ntsListComponent(self.listComponentOption).done(function() {
                        $('#component-items-list').focusComponent();
                    });
                }
                if(self.displayKCP005()){
                    nts.uk.ui.windows.getSelf().setWidth(1000);
                }else{
                    nts.uk.ui.windows.getSelf().setWidth(860);
                }
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
                inputDate:  nts.uk.util.isNullOrEmpty(self.inputDate()) ? null : moment(self.inputDate()).format("YYYY/MM/DD"),
                listSID: self.employeeIDList()
            }).done(function(data){
                self.employeeCode(data.employeeCode);
                self.employeeName(data.employeeName);
                 let dataHoliday = [];
                let total = 0.0;
                _.each(data.rsvLeaManaImport.grantRemainingList, function(rsv, index){
                    dataHoliday.push(new DataHolidayGrantInfo(index, rsv.grantDate, rsv.grantNumber,
                            rsv.usedNumber, rsv.remainingNumber, rsv.deadline));
                    total = total + rsv.remainingNumber;
                });
                self.totalRemain(total.toFixed(1)+ '日');
                self.dataHolidayGrantInfo(dataHoliday);
//                self.getDataForTable(data);
                let dataYearly = [];
                _.each(data.rsvLeaManaImport.tmpManageList, function(tmp, index){
                    dataYearly.push(new DataSteadyUseInfor(index, tmp.ymd, tmp.useDays, tmp.creatorAtr));
                });
                self.dataSteadyUseInfor(dataYearly);
                _.each(data.employeeInfors, function(emp){
                    self.lstEmpFull().push({id: emp.sid, code: emp.scd, name: emp.bussinessName});
                    self.employeeList().push({code: emp.scd, name: emp.bussinessName});
                });

                self.employeeCode.subscribe(function(value) {
                    let empSelected = _.find(self.lstEmpFull(), function(emp) {
                        if (emp.code == value) {
                            return emp;
                        }
                    });
                    if(!nts.uk.util.isNullOrEmpty(empSelected)){
                        let employeeIDs =[];
                        let dfd =  $.Deferred();
                        employeeIDs.push(empSelected.id);
                        self.employeeName(empSelected.name);
                        service.findByEmployee({
                            mode: self.multiSelect(),
                            inputDate:  nts.uk.util.isNullOrEmpty(self.inputDate()) ? null : moment(self.inputDate()).format("YYYY/MM/DD"),
                            listSID: employeeIDs
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
            if (!nts.uk.util.isNullOrEmpty(data.rsvLeaManaImport)) {
                _.each(data.rsvLeaManaImport.tmpManageList, function(tmp, index){
                    dataYearly.push(new DataSteadyUseInfor(index, tmp.ymd, tmp.useDays, tmp.creatorAtr));
                });
                let dataYearly = [];
                _.each(data.rsvLeaManaImport.tmpManageList, function(tmp, index){
                    dataYearly.push(new DataSteadyUseInfor(index, tmp.ymd, tmp.useDays, tmp.creatorAtr));
                });
                self.dataSteadyUseInfor(dataYearly);
            }
        }    }
    export class DataHolidayGrantInfo{
        id: any;
        fundedDate: string;
        fundedNumber: number;
        numberOfUses: number;//使用数 - usedNumber
        residualNumber: number;
        deadline:string;//期限日 - deadline
        constructor(id: any,fundedDate: string,fundedNumber: number,numberOfUses: number,residualNumber: number,deadline:string ){
            this.id = id;
            this.fundedDate = fundedDate;
            this.fundedNumber = fundedNumber;
            this.numberOfUses = numberOfUses;
            this.residualNumber = residualNumber;
            this.deadline = deadline;
        }
    }
    export class DataSteadyUseInfor{
        id: any;
        date: string;
        steadNumberOfUser: number;
        typeOfStead: string;
        constructor(id: any,date: string,steadNumberOfUser: number,typeOfStead: string){
            this.id = id;
            this.date = date;
            this.steadNumberOfUser = steadNumberOfUser;
            this.typeOfStead = typeOfStead;
        }
    }
}