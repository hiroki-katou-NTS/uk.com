module nts.uk.at.view.kdl052.a {
    import getText = nts.uk.resource.getText;
    import getShared = nts.uk.ui.windows.getShared;

    const Paths = {
        GET_CHILD_NURSING_LEAVE: "at/record/monthly/nursingleave/getChildNursingLeave",
        GET_ATENDANCENAME_BY_IDS:"at/record/attendanceitem/daily/getattendnamebyids",
        EXPORT_CSV:"screen/at/kdl053/exportCsv"
    };

    @bean()
    class Kdl052ViewModel extends ko.ViewModel {
        listEmpId : any;
        dataOneEmp : KnockoutObservable<any> = ko.observable(null);
        employeeNameSelect ='社員000000000001';
        employeeCodeSelect= '000000000001';
        textA24 = '16日と2:00';
        textA26 = '2021/09/28';
        textA28 = '2021/12/31';
        textA10 = '10日';
        textA12 = '3日と2:00';

        // search
        items: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        searchText: KnockoutObservable<string> = ko.observable('');        

        //kcp
        employeeList: KnockoutObservableArray<UnitModel> = ko.observableArray<UnitModel>([]);
        listComponentOption: any = [];
        selectedCode: KnockoutObservable<string> = ko.observable('1');
        multiSelectedCode: KnockoutObservableArray<string>;
        isShowAlreadySet: KnockoutObservable<boolean> = ko.observable(false);
        alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
        isDialog: KnockoutObservable<boolean> = ko.observable(false);
        isShowNoSelectRow: KnockoutObservable<boolean> = ko.observable(false);;
        isMultiSelect: KnockoutObservable<boolean> = ko.observable(false);
        isShowWorkPlaceName: KnockoutObservable<boolean> = ko.observable(false);
        isShowSelectAllButton: KnockoutObservable<boolean> = ko.observable(false);
        disableSelection: KnockoutObservable<boolean> = ko.observable(false);

        listEmployeeImport: any = [];

        //data table top
        managementCheck: KnockoutObservable<number> =  ko.observable(1);
        currentRemainNumberSelect: KnockoutObservable<string> =  ko.observable("");
        expiredWithinMonthSelect: KnockoutObservable<string> =  ko.observable("");
        dayCloseDeadlineSelect: KnockoutObservable<string> =  ko.observable("");

        // grid
        itemLts: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        columns: KnockoutObservableArray<NtsGridListColumn>;
        currentCode: KnockoutObservable<any> = ko.observable('');
			

        constructor(private params: any) {            
            super();
            let self = this;        
            self.listEmpId = params.empIds;  
            self.managementCheck(params.managementCheck);
          
            self.listComponentOption = {
                isShowAlreadySet: self.isShowAlreadySet(),
                isMultiSelect: false,
                listType: 4,
                employeeInputList: self.employeeList,
                selectType: 3,
                selectedCode: self.selectedCode,
                isDialog: self.isDialog(),
                isShowNoSelectRow: self.isShowNoSelectRow(),
                alreadySettingList: self.alreadySettingList,
                isShowWorkPlaceName: self.isShowWorkPlaceName(),
                isShowSelectAllButton: self.isShowSelectAllButton(),
                disableSelection: self.disableSelection(),
                maxRows: 15,
                tabindex: -1
            };            

            self.columns = ko.observableArray([
                { headerText: getText('KDL052_25'), key: 'code', width: 180 },                
                { headerText: getText('KDL052_26'), key: 'name', width: 100 }
            ]); 

            for(let i = 1; i < 8; i++) {
                self.itemLts.push(new ItemModel('2021/05/12', '10日'));
            }

            self.getChildNursingLeave(self.listEmpId);
            self.loadData();
        }

        mounted(){
            const self = this;
            if (self.listEmpId.length > 1) {
                self.$window.size(580, 920);
                $("#left-content").show();
            } else {
                self.$window.size(580, 660);
                $("#left-content").hide();
            }           
        }


        loadData(): void {
            let self = this;     
            $('#component-items-list').ntsListComponent(self.listComponentOption);
        }

        public getChildNursingLeave(listEmp: any): any {
            let self = this;
            let request: any;
            self.$ajax(Paths.GET_CHILD_NURSING_LEAVE, listEmp).done((data: any) => {
                self.dataOneEmp(data);
                self.listEmployeeImport = data.lstEmployee;
                _.forEach(data.lstEmployee, (a: any, ind) => {
                    self.employeeList.push({ id: ind, code: a.employeeCode, name: a.employeeName })
                });
                
            }).fail(function (res: any) {
                self.$dialog.alert({ messageId: "" });
            });
           
        }

        findData(data: any): void {
            let self = this;
            
            let text = $("input.ntsSearchBox.nts-editor.ntsSearchBox_Component").val()
            if (text == "") {
                nts.uk.ui.dialog.info({ messageId: "MsgB_24" });
            } else {
                let lstFil = _.filter(self.employeeList(), (z: any) => {
                    return _.includes(z.code, text);
                });

                if (lstFil.length > 0) {              
                    let index = _.findIndex(lstFil, (z : any) => _.isEqual(z.code,self.listComponentOption.selectedCode()));
                      if (index == -1 || index == lstFil.length - 1){
                          self.listComponentOption.selectedCode(lstFil[0].code);
                      } else {
                          self.listComponentOption.selectedCode(lstFil[index + 1].code);
                     }
                    let scroll: any = _.findIndex(self.employeeList(), (z: any) => _.isEqual(z.code, self.listComponentOption.selectedCode())),
                    id = _.filter($("table > tbody > tr > td > div"), (x: any) => {
                        return _.includes(x.id, "_scrollContainer") && !_.includes(x.id, "single-list");
                    })
                $("#" + id[0].id).scrollTop(scroll * 24);
                } else {
                    nts.uk.ui.dialog.info({ messageId: "MsgB_25" });
                }
            }
        }

        closeDialog(): void {
            let self = this;
            nts.uk.ui.windows.close();
        }
    }

    export interface UnitModel {
		id?: string;
		code: string;
		name?: string;
		workplaceName?: string;
		isAlreadySetting?: boolean;
		optionalColumn?: any;
	}

    class ItemModel {
        code: string;
        name: string;       
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;            
        }
    }
}