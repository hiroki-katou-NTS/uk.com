module jcm007.a {
    import ajax = nts.uk.request.ajax;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import liveView = nts.uk.request.liveView;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import close = nts.uk.ui.windows.close;
    import dialog = nts.uk.ui.dialog;
    import error = nts.uk.ui.dialog.error;
    import info = nts.uk.ui.dialog.info;

    var block = nts.uk.ui.block;
    export class ViewModel {

        currentEmployee: KnockoutObservable<EmployeeModel>;

        tabs : KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        selectedTab :  KnockoutObservable<string>;
        enable_btnRemove : KnockoutObservable<boolean>;
        enable_btnRegister : KnockoutObservable<boolean>;
        enable_btnExportExcel : KnockoutObservable<boolean>;

        // tab 2
        employeeListTab2: [];
        enable_tab2: KnockoutObservable<boolean>;
        visible_tab2: KnockoutObservable<boolean>;
        enable_tab1: KnockoutObservable<boolean>;
        visible_tab1: KnockoutObservable<boolean>;

        itemSelectedTab1: KnockoutObservable<any>;
        itemSelectedTab2: KnockoutObservable<any>;
        empInfoHeaderList: KnockoutObservableArray<IEmpInfoHeader>;
        interviewSummary: {};

        // ccg029
        input: Input;

        isNewMode: boolean;

        status_Unregistered: string;
        status_ApprovalPending: string;
        status_WaitingReflection: string;

        notify_Ctg_Normal: string;
        notify_Ctg_Report: string;

        constructor() {
            let self = this;
            
            self.enable_tab1 = ko.observable(true);
            self.visible_tab1 = ko.observable(true);

            self.enable_tab2 = ko.observable(true);
            self.visible_tab2 = ko.observable(true);

            self.tabs = ko.observableArray([
                { id: 'tab-1', title: getText('JCM007_A221_1_1'), content: '.tab-content-1', enable: self.enable_tab1, visible: self.visible_tab1 },
                { id: 'tab-2', title: getText('JCM007_A221_1_3'), content: '.tab-content-2', enable: self.enable_tab2, visible: self.visible_tab2 }
            ]);

            self.employeeListTab2 = [];
            self.bindData();

            self.selectedTab = ko.observable('tab-1');

            self.currentEmployee = ko.observable(new EmployeeModel(''));

            self.enable_btnRegister = ko.observable(true);
            self.enable_btnRemove = ko.observable(false);
            self.enable_btnExportExcel = ko.observable(false);

            self.itemSelectedTab1 = ko.observable(null);
            self.itemSelectedTab2 = ko.observable(null);
            self.empInfoHeaderList = ko.observableArray([]);
            self.interviewSummary = {};

            self.input = new Input(undefined);
            self.isNewMode = true;

            self.status_Unregistered = ''; // 0
            self.status_ApprovalPending = getText('JCM007_A3_2'); // 1
            self.status_WaitingReflection = getText('JCM007_A3_3'); // 2

            self.notify_Ctg_Normal = '';  // 0
            self.notify_Ctg_Report = getText('JCM007_A3_1'); // 1

            self.selectedTab.subscribe((value) => {
                if (value == 'tab-2') {
                    
                    if (nts.uk.ui.errors.hasError()) {
                        nts.uk.ui.errors.clearAll();
                    }

                    self.enable_tab1(false);
                    self.getListDataOfTab2().done(() => {
                        let itemSelectedTab2 = self.itemSelectedTab2();
                        if (itemSelectedTab2 != null) {
                            if (itemSelectedTab2.status == self.status_ApprovalPending || itemSelectedTab2.status == self.status_Unregistered) {
                                self.enable_btnRegister(true);
                                self.enable_btnRemove(true);
                                self.enable_disableInput(true);

                            } else if (itemSelectedTab2.status == self.status_WaitingReflection) {
                                self.enable_btnRegister(false);
                                self.enable_btnRemove(false);
                                self.enable_disableInput(false);
                            }

                            let objHeader = _.find(self.empInfoHeaderList, function(o) { return o.employeeId == itemSelectedTab2.sid; });
                            if (objHeader) {
                                let param = {
                                    employeeId: objHeader.employeeId,
                                    personId: objHeader.personID,
                                    baseDate: moment(new Date()).format("YYYY/MM/DD"),
                                    getDepartment: true,
                                    getPosition: true,
                                    getEmployment: true
                                };

                                self.setDataHeader(param);
                            }
                            
                            self.getInterViewRecord(itemSelectedTab2.sid);
                            
                            let dataDetail = _.find(self.employeeListTab2, function(o) { return o.sid == itemSelectedTab2.sid; });
                            if (dataDetail) {
                                self.setDataDetail(dataDetail);
                            }

                            $('#retirementDateId').focus();
                        } else {
                            self.initHeaderInfo();
                            self.initRetirementInfo();
                        }
                    });
                } else if (value == 'tab-1') {
                    self.enable_btnRemove(false);
                    self.enable_btnRegister(true);
                    if (self.itemSelectedTab1()) {
                        let itemSelectedTab1 = self.itemSelectedTab1();
                        let param = {
                            employeeId: itemSelectedTab1.employeeId,
                            personId: itemSelectedTab1.personalId,
                            baseDate: moment(new Date()).format("YYYY/MM/DD"),
                            getDepartment: true,
                            getPosition: true,
                            getEmployment: true
                        };

                        self.setDataHeader(param);

                        self.initRetirementInfo();
                    } else {
                        self.initHeaderInfo();
                        self.initRetirementInfo();
                    }
                    self.enable_disableInput(true);
                }
            });

            self.itemSelectedTab2.subscribe((value) => {
                if (value == null) return;
                if (value.status == self.status_ApprovalPending || value.status == self.status_Unregistered) {
                    // ??????????????????[??????????????????????????????????????????] (Th???c hi???n thu???t to??n [Hi???n th??? th??ng tin ng?????i ngh??? h??u???)
                    self.enable_btnRegister(true);
                    self.enable_btnRemove(true);
                    self.enable_disableInput(true);
                } else if (value.status == self.status_WaitingReflection) {
                    self.enable_btnRegister(false);
                    self.enable_btnRemove(false);
                    self.enable_disableInput(false);
                }
                let objHeader = _.find(self.empInfoHeaderList, function(o) { return o.employeeId == value.sid; });
                if (objHeader) {
                    let param = {
                        employeeId: objHeader.employeeId,
                        personId: objHeader.personID,
                        baseDate: moment(new Date()).format("YYYY/MM/DD"),
                        getDepartment: true,
                        getPosition: true,
                        getEmployment: true
                    };
                    self.setDataHeader(param);
                }

                self.getInterViewRecord(value.sid);

                let dataDetail = _.find(self.employeeListTab2, function(o) { return o.sid == value.sid; });
                if(dataDetail){
                    self.setDataDetail(dataDetail);
                }
                
                self.isNewMode = false;
                $('#retirementDateId').focus();
            });
        }

        public exportExcel(): void {
            block.grayout();
            service.exportExcel().always(() => {
                block.clear();
            });;
        }

        public getInterViewRecord(sid) {
            let self = this;

            if (self.interviewSummary && self.interviewSummary.listInterviewRecordAvailability && self.interviewSummary.listInterviewRecordAvailability.length > 0) {
                let showInterviewObj1 = _.find(self.interviewSummary.listInterviewRecordAvailability, function(o) { return o.employeeID == sid; });
                if (showInterviewObj1) {
                    if(showInterviewObj1.isPresence == true){
                        self.currentEmployee().shouldInterviewRecord(true);
                    }else{
                        self.currentEmployee().shouldInterviewRecord(false);    
                    }
                    
                } else {
                    block.grayout();
                    service.getInterviewRecord(sid).done((result: any) => {
                        console.log('getInterViewRecord done');
                        if (result && result.listInterviewRecordAvailability && result.listInterviewRecordAvailability.length > 0) {
                            let showInterviewObj = _.find(result.listInterviewRecordAvailability, function(o) { return o.employeeID == sid; });
                            if (showInterviewObj.isPresence == true) {
                                self.currentEmployee().shouldInterviewRecord(true);
                            } else {
                                self.currentEmployee().shouldInterviewRecord(false);
                            }
                        }
                        block.clear();
                    }).fail((mes) => {
                        console.log('getInterViewRecord fail');
                        block.clear();
                    });
                }
            }
        }

        // select emp ben tab-1
        // ??????????????????????????????????????????????????? (Select employee t??? common componen???searrch employee???  )
        public seletedEmployee(data): void {
            let self = this;
            self.itemSelectedTab1(data);
            if (self.isNewMode) {
                // ??????????????????[????????????????????????]???????????????(Th???c hi???n thu???t to??n "Check t??nh tr???ng ????ng k?? ")
                block.grayout();
                service.checkStatusRegistration(data.employeeId).done((result: any) => {
                    console.log('CheckStatusRegis DONE');
                    console.log('result: ' + result);
                    if (result) {
                        // ??????????????????[?????????????????????]???????????????(Th???c hi???n thu???t to??n [hi???n th??? m??n h??nh m???i])
                        this.initRetirementInfo();
                        let param = {
                            employeeId: data.employeeId,
                            personId: data.personalId,
                            baseDate: moment(new Date()).format("YYYY/MM/DD"),
                            getDepartment: true,
                            getPosition: true,
                            getEmployment: true
                        };

                        // ??????????????????[?????????????????????]??????????????? (Th???c hi???n thu???t to??n "Hi???n th??? th??ng tin employee")
                        self.setDataHeader(param);
                    }
                }).fail((error) => {
                    console.log('CheckStatusRegis FAIL');
                    block.clear();
                    nts.uk.ui.dialog.error(error);
                    return;
                }).always(() => {
                    block.clear();
                });
            }

            self.getInterViewRecord(data.employeeId);
        }

        /** start page */
        start(historyId: any) {
            let self = this;
            let dfd = $.Deferred<any>();
            self.getListData(historyId).done(() => {
                dfd.resolve();
            }).fail(() => {
                dfd.reject();
            });
            return dfd.promise();
        }

        getListData(historyId: any, isAfterRemove: boolean) {
            let self = this;
            let dfd = $.Deferred<any>();
            block.grayout();
            service.getData().done((result) => {
                // goi service ??????????????????[??????????????????????????????]???????????????
                // (Th???c hi???n thu???t to??n [Get list th??ng tin nh??n vi??n]) CCG029
                if (result.retiredEmployees.length != 0) {
                    self.enable_tab2(true);
                    self.visible_tab2(true);
                    self.enable_btnExportExcel(true);

                    self.empInfoHeaderList = result.employeeImports;
                    
                    self.interviewSummary = result.interviewSummary;

                    if (self.selectedTab() == 'tab-1') {
                        self.enable_btnRemove(false);
                    } else if (self.selectedTab() == 'tab-2') {
                        self.enable_btnRemove(true);
                    }

                    self.employeeListTab2 = result.retiredEmployees;

                    $("#gridListEmployeesJcm007").igGrid('option', 'dataSource', self.employeeListTab2);

                    self.newMode();
                } else {
                    self.enable_tab2(false);
                    self.visible_tab2(false);
                    self.enable_btnExportExcel(false);
                    self.newMode();
                }
                dfd.resolve();
                block.clear();
                
            }).fail((error) => {
                console.log('Get Data Tab-2 Fail');
                dfd.reject();
                nts.uk.ui.dialog.info(error);
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }
        
        getListDataOfTab2() {
            let self = this;
            let dfd = $.Deferred<any>();
            block.grayout();
            service.getData().done((result) => {
                // goi service ??????????????????[??????????????????????????????]???????????????
                // (Th???c hi???n thu???t to??n [Get list th??ng tin nh??n vi??n]) CCG029
                if (result.retiredEmployees.length != 0) {
                    self.enable_tab2(true);
                    self.visible_tab2(true);
                    self.enable_btnExportExcel(true);

                    self.empInfoHeaderList = result.employeeImports;

                    self.enable_btnRemove(true);

                    self.employeeListTab2 = result.retiredEmployees;

                    $("#gridListEmployeesJcm007").igGrid('option', 'dataSource', self.employeeListTab2);
                    
                    self.itemSelectedTab2(self.employeeListTab2[0]);
                    
                    $("#gridListEmployeesJcm007").igGridSelection("selectRow", 0);
                    
                } else {
                    self.enable_tab2(false);
                    self.visible_tab2(false);
                    self.enable_btnExportExcel(false);
                    self.newMode();
                }
                dfd.resolve();
                block.clear();
            }).fail((error) => {
                console.log('Get Data Tab-2 Fail');
                dfd.reject();
                nts.uk.ui.dialog.info(error);
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }

        
        public bindData(): void {
            var self = this;
            $("#gridListEmployeesJcm007").igGrid({
                autoGenerateColumns: false,
                primaryKey: 'historyId',
                columns: [
                    {
                        headerText: 'historyId', key: 'historyId', hidden: true
                    },
                    {
                        headerText: getText('JCM007_A221_5'), key: 'status', dataType: 'string', width: '70px'
                    },
                    {
                        headerText: getText('JCM007_A221_6'), key: 'scd', dataType: 'string', width: '70px'
                    },
                    {
                        headerText: getText('JCM007_A221_7'), key: 'employeeName', dataType: 'string', width: '140px'
                    },
                    {
                        headerText: getText('JCM007_A221_8'), key: 'retirementDate', dataType: 'date', width: '120px', dateInputFormat: 'yyyy/MM/dd'
                    },
                    {
                        headerText: getText('JCM007_A221_9'), key: 'releaseDate', dataType: 'date', width: '120px', dateInputFormat: 'yyyy/MM/dd'
                    },
                    {
                        headerText: getText('JCM007_A221_10'), key: 'notificationCategory', dataType: 'string', width: '80px'
                    }
                ],
                dataSource: self.employeeListTab2,
                dataSourceType: 'json',
                responseDataKey: 'results',
                height: '513px',
                width: '640px',
                tabIndex: 17,
                features: [
                    {
                        name: "Selection",
                        mode: "row",
                        multipleSelection: false,
                        rowSelectionChanged: function(evt, ui) {
                            let itemSelected = _.find(self.employeeListTab2, function(o) { return o.historyId == ui.row.id; });
                            if (itemSelected) {
                                self.itemSelectedTab2(itemSelected);
                            }
                        }
                    },
                    {
                        name: 'Filtering',
                        type: 'local',
                        mode: 'simple',
                        dataFiltered: function(evt, ui) {
                            $('#gridListEmployeesJcm007_scroll').css('height', '425px');
                        }
                    },
                    {
                        name: 'Sorting',
                        type: 'local'
                    },
                    {
                        name: 'Resizing'
                    },
                    {
                        name: "Tooltips"
                    }
                ]
            });
        }

        /** event when click register */
        register() {
            let self = this;
            let emp = ko.toJS(self.currentEmployee());
            let itemSelectedTab1 = self.itemSelectedTab1();
            let itemSelectedTab2 = self.itemSelectedTab2();
            console.log(itemSelectedTab1);
            console.log(itemSelectedTab2);
            
            // ????????????????????????????????????????????????(check xem employee c?? ??ang ???????c ch???n hay kh??ng)
            if (self.selectedTab() == 'tab-1') {
                if (itemSelectedTab1 == null) {
                    nts.uk.ui.dialog.error({ messageId: "MsgJ_JCM007_12" });
                    return;
                }
            }
            
            if (self.selectedTab() == 'tab-2') {
                if (itemSelectedTab2 == null) {
                    nts.uk.ui.dialog.error({ messageId: "MsgJ_JCM007_12" });
                    return;
                }
            }

            // validate check empty  
            if (((self.selectedTab() == 'tab-1') && (self.isNewMode) && (itemSelectedTab1 != null)) || (self.selectedTab() == 'tab-2')) {
                $("#retirementDateId").trigger("validate");
                $("#releaseDateId").trigger("validate");
                $("#selectedCode_Reason1Id").trigger("validate");
                $("#selectedCode_Reason2Id").trigger("validate");

                if (emp.selectedCode_Retiment == 3) {
                    $("#dismissalNoticeDateId").trigger("validate");
                }
            }

            if (nts.uk.ui.errors.hasError()) {
                return;
            }

            let command = {
                historyId: '',
                histId_Refer: '',
                contractCode: '',
                companyId: '',
                companyCode: '',
                workId: '',
                workName: '',
                sId: '',
                pId: '',
                scd: '',
                employeeName: '',

                notificationCategory: 0,
                pendingFlag: 0,
                status: 1,

                retirementDate: emp.retirementDate,
                releaseDate: emp.releaseDate,
                retirementType: emp.selectedCode_Retiment,
                selectedCode_Reason1: emp.selectedCode_Reason1,
                selectedCode_Reason2: emp.selectedCode_Reason2,
                retirementRemarks: emp.retirementRemarks,

                retirementReasonVal: emp.retirementReasonVal,

                dismissalNoticeDate: emp.dismissalNoticeDate,
                dismissalNoticeDateAllow: emp.dismissalNoticeDateAllow,
                reaAndProForDis: emp.reaAndProForDis,
                naturalUnaReasons_1: emp.naturalUnaReasons_1 == false ? 0 : 1,
                naturalUnaReasons_1Val: emp.naturalUnaReasons_1Val,
                businessReduction_2: emp.businessReduction_2 == false ? 0 : 1,
                businessReduction_2Val: emp.businessReduction_2Val,
                seriousViolationsOrder_3: emp.seriousViolationsOrder_3 == false ? 0 : 1,
                seriousViolationsOrder_3Val: emp.seriousViolationsOrder_3Val,
                unauthorizedConduct_4: emp.unauthorizedConduct_4 == false ? 0 : 1,
                unauthorizedConduct_4Val: emp.unauthorizedConduct_4Val,
                leaveConsiderableTime_5: emp.leaveConsiderableTime_5 == false ? 0 : 1,
                leaveConsiderableTime_5Val: emp.leaveConsiderableTime_5Val,
                other_6: emp.other_6 == false ? 0 : 1,
                other_6Val: emp.other_6Val
            }

            if (command.naturalUnaReasons_1 == 1 && command.naturalUnaReasons_1Val == '') {
                command.naturalUnaReasons_1 = 0;
            }
            if (command.businessReduction_2 == 1 && command.businessReduction_2Val == '') {
                command.businessReduction_2 = 0;
            }
            if (command.seriousViolationsOrder_3 == 1 && command.seriousViolationsOrder_3Val == '') {
                command.seriousViolationsOrder_3 = 0;
            }
            if (command.unauthorizedConduct_4 == 1 && command.unauthorizedConduct_4Val == '') {
                command.unauthorizedConduct_4 = 0;
            }
            if (command.leaveConsiderableTime_5 == 1 && command.leaveConsiderableTime_5Val == '') {
                command.leaveConsiderableTime_5 = 0;
            }
            if (command.other_6 == 1 && command.other_6Val == '') {
                command.other_6 = 0;
            }

            if ((self.selectedTab() == 'tab-1') && (self.isNewMode)) {

                //2.??????????????????????????????(????ng k?? m???i ng?????i ngh??? h??u)
                command.sId = itemSelectedTab1.employeeId;
                command.pId = itemSelectedTab1.personalId;
                command.scd = itemSelectedTab1.employeeCode;
                command.employeeName = itemSelectedTab1.businessName;
                // ??????????????????[???????????????????????????????????????????????? (Th???c hi???n thu???t to??n [????ng k?? m???i th??ng tin ng?????i ngh??? h??u???)
                self.preCheckAndRegisterNewEmp(command);

            } else if (self.selectedTab() == 'tab-2'
                && itemSelectedTab2.notificationCategory == self.notify_Ctg_Report
                && itemSelectedTab2.status == self.status_Unregistered) {

                // 3.??????????????????????????????????????????????????? (????ng k?? m???i ng?????i ngh??? h??u ???? ph?? duy???t ????n/notification)
                command.historyId = itemSelectedTab2.historyId;
                command.sId = itemSelectedTab2.sid;
                command.pId = itemSelectedTab2.pid;
                command.scd = itemSelectedTab2.scd;
                command.employeeName = itemSelectedTab2.employeeName;
                command.status = 1;
                self.preCheckAndRegisterNewEmpApproved(command).done(() => {
                    self.enable_disableInput(true);
                });

            } else if (self.selectedTab() == 'tab-2' && itemSelectedTab2.status != self.status_Unregistered) {

                // 4.??????????????????????????????(S???a th??ng tin ng?????i ngh??? h??u)
                command.historyId = itemSelectedTab2.historyId;
                command.sId = itemSelectedTab2.sid;
                command.pId = itemSelectedTab2.pid;
                command.scd = itemSelectedTab2.scd;
                command.employeeName = itemSelectedTab2.employeeName;
                command.status = 1;
                self.preCheckAndModifyRetireeInformation(command).done(() => {
                    self.enable_disableInput(false);
                });
            }
        }

        //2.??????????????????????????????(????ng k?? m???i ng?????i ngh??? h??u)
        //  ??????????????????[???????????????????????????????????????????????? (Th???c hi???n thu???t to??n [????ng k?? m???i th??ng tin ng?????i ngh??? h??u???)
        preCheckAndRegisterNewEmp(command) {
            let self = this;
            let dfd = $.Deferred<any>();
            let empCurrent = ko.toJS(self.currentEmployee());
            // format l???i date
            let releaseDate = new Date(empCurrent.releaseDate);
            let retirementDate = new Date(empCurrent.retirementDate);
            command.releaseDate = releaseDate.toISOString().substring(0, 10).replace("-", "/").replace("-", "/");
            command.retirementDate = retirementDate.toISOString().substring(0, 10).replace("-", "/").replace("-", "/");

            if (self.currentEmployee().dismissalNoticeDate()) {
                let dismissalNoticeDate = new Date(self.currentEmployee().dismissalNoticeDate());
                command.dismissalNoticeDate = dismissalNoticeDate.toISOString().substring(0, 10).replace("-", "/").replace("-", "/");
            }

            if (self.currentEmployee().dismissalNoticeDateAllow()) {
                let dismissalNoticeDateAllow = new Date(self.currentEmployee().dismissalNoticeDateAllow());
                command.dismissalNoticeDateAllow = dismissalNoticeDateAllow.toISOString().substring(0, 10).replace("-", "/").replace("-", "/");
            }

            block.grayout();
            // ??????????????????[??????????????????]??????????????? (TH???c hi???n thu???t to??n [Check tr?????c ] )
            service.preCheck(command).done(() => {
                block.clear();
                console.log('PRECHECK DONE!!');
                
                self.dateConfirmCheck(2, command);
                
            }).fail((mes) => {
                self.enable_disableInput(true);
                block.clear();
                nts.uk.ui.dialog.bundledErrors(mes);
                dfd.reject();
            });
            return dfd.promise();
        }

        // 2.??????????????????[????????????????????????]??????????????? (Th???c hi???n thu???t to??n [Th??m th??ng tin ng?????i ngh??? h??u])
        // truong hop ????ng k?? b??n tab-1
        registerNewEmployee(command: any) {
            let self = this;
            let dfd = $.Deferred<any>();
            block.grayout();
            service.registerNewEmployee(command).done((result : boolean) => {
                console.log('REGISTER NEW EMPINFO DONE!!');
                console.log(result);
                if(result){
                    self.enable_tab2(true);
                    self.visible_tab2(true);
                    self.enable_btnExportExcel(true);
                }else{
                    self.enable_tab2(false);
                    self.visible_tab2(false);
                    self.enable_btnExportExcel(false);    
                }
                
                self.getListDataAfterRegisterNewEmployee().done(() => {
                    dialog.info({ messageId: "Msg_15" });
                });
                block.clear();
                dfd.resolve();
            }).fail((mes) => {
                console.log('REGISTER NEW EMPINFO FAIL!!');
                block.clear();
                dfd.reject();
            });
            return dfd.promise();
        }

        // get l???i list khi ????ng k?? b??n tab-1
        getListDataAfterRegisterNewEmployee() {
            let self = this;
            let dfd = $.Deferred<any>();
            block.grayout();
            service.getData().done((result) => {
                if (result.retiredEmployees.length != 0) {
                    
                    self.enable_btnExportExcel(true);
                    
                    self.empInfoHeaderList = result.employeeImports;

                    self.employeeListTab2 = result.retiredEmployees;

                    $("#gridListEmployeesJcm007").igGrid('option', 'dataSource', self.employeeListTab2);

                } else {
                    self.setDataWhenListDataEmpty();
                }
                dfd.resolve();
                block.clear();
                
            }).fail((error) => {
                console.log('Get Data Tab-2 Fail');
                dfd.reject();
                nts.uk.ui.dialog.info(error);
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }

        // 3.??????????????????????????????????????????????????? (????ng k?? m???i ng?????i ngh??? h??u ???? ph?? duy???t ????n/notification)
        // tr????ng h???p ????ng k?? b??n tab-2
        preCheckAndRegisterNewEmpApproved(command: any) {
            let self = this;
            let dfd = $.Deferred<any>();
            let empCurrent = ko.toJS(self.currentEmployee());
            // format l???i date
            let releaseDate = new Date(empCurrent.releaseDate);
            let retirementDate = new Date(empCurrent.retirementDate);
            command.releaseDate = releaseDate.toISOString().substring(0, 10).replace("-", "/").replace("-", "/");
            command.retirementDate = retirementDate.toISOString().substring(0, 10).replace("-", "/").replace("-", "/");

            if (self.currentEmployee().dismissalNoticeDate()) {
                let dismissalNoticeDate = new Date(self.currentEmployee().dismissalNoticeDate());
                command.dismissalNoticeDate = dismissalNoticeDate.toISOString().substring(0, 10).replace("-", "/").replace("-", "/");
            }

            if (self.currentEmployee().dismissalNoticeDateAllow()) {
                let dismissalNoticeDateAllow = new Date(self.currentEmployee().dismissalNoticeDateAllow());
                command.dismissalNoticeDateAllow = dismissalNoticeDateAllow.toISOString().substring(0, 10).replace("-", "/").replace("-", "/");
            }

            block.grayout();
            service.preCheck(command).done(() => {
                block.clear();
                console.log('PRECHECK DONE!!');
                
                self.dateConfirmCheck(3, command);
                
            }).fail((mes) => {
                self.enable_disableInput(true);
                block.clear();
                nts.uk.ui.dialog.bundledErrors(mes);
                dfd.reject();
            });
            return dfd.promise();
        }
        

        registerNewRetireesApproved(command: any) {
            let self = this;
            let dfd = $.Deferred<any>();
            block.grayout();
            service.registerNewRetireesApproved(command).done((result) => {
                console.log('UPDATE DONE!');
                console.log(result);
                self.getListDataAfterRegisterInTab2(command.historyId).done(() => {
                    dialog.info({ messageId: "Msg_15" });
                });
                block.clear();
                dfd.resolve();
            }).fail((mes) => {
                console.log('UPDATE FAIL!!');
                block.clear();
                dfd.reject();
            });
            return dfd.promise();
        }

        // 4.??????????????????????????????(S???a th??ng tin ng?????i ngh??? h??u)
        preCheckAndModifyRetireeInformation(command: any): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred<any>();

            let empCurrent = ko.toJS(self.currentEmployee());
            // format l???i date
            let releaseDate = new Date(empCurrent.releaseDate);
            let retirementDate = new Date(empCurrent.retirementDate);
            command.releaseDate = releaseDate.toISOString().substring(0, 10).replace("-", "/").replace("-", "/");
            command.retirementDate = retirementDate.toISOString().substring(0, 10).replace("-", "/").replace("-", "/");

            if (self.currentEmployee().dismissalNoticeDate()) {
                let dismissalNoticeDate = new Date(self.currentEmployee().dismissalNoticeDate());
                command.dismissalNoticeDate = dismissalNoticeDate.toISOString().substring(0, 10).replace("-", "/").replace("-", "/");
            }

            if (self.currentEmployee().dismissalNoticeDateAllow()) {
                let dismissalNoticeDateAllow = new Date(self.currentEmployee().dismissalNoticeDateAllow());
                command.dismissalNoticeDateAllow = dismissalNoticeDateAllow.toISOString().substring(0, 10).replace("-", "/").replace("-", "/");
            }

            block.grayout();
            //??????????????????[??????????????????]??????????????? (TH???c hi???n thu???t to??n [Check tr?????c ] )
            service.preCheck(command).done(() => {
                block.clear();
                console.log('PRECHECK DONE!!');

                self.dateConfirmCheck(4 , command);

            }).fail((mes) => {
                self.enable_disableInput(true);
                block.clear();
                nts.uk.ui.dialog.bundledErrors(mes);
                dfd.reject();
            });
            return dfd.promise();
        }

        
        dateConfirmCheck(regisType, command) {
            let self = this;
            // ??????????????????[????????????????????????]???????????????(Th???c hi???n thu???t to??n [check x??c nh???n ng??y]) 
            // (thuat toan nay check ??? d?????i client s??? h???p l?? h??n)
            let objResultWhenRetimentChange = self.currentEmployee().objResultWhenRetimentChange;
            let releaseDateA22214 = moment.utc(self.currentEmployee().releaseDate(), DateFormat.DEFAULT_FORMAT);
            let dismissalNoticeDateA22235 = moment.utc(self.currentEmployee().dismissalNoticeDate(), DateFormat.DEFAULT_FORMAT);
            
            if (objResultWhenRetimentChange) {
                if (objResultWhenRetimentChange.releaseDate == null) {

                    self.registerData(regisType, command);

                } else if (objResultWhenRetimentChange.releaseDate != null) {

                    let releaseDateCalculator = moment.utc(objResultWhenRetimentChange.releaseDate, DateFormat.DEFAULT_FORMAT);
                    let dayDifferenceRelease = releaseDateA22214.diff(releaseDateCalculator, 'days');
                    if (dayDifferenceRelease != 0) {
                        nts.uk.ui.dialog.confirm({ messageId: "MsgJ_JCM007_13" }).ifYes(() => {

                            self.dismissalNoticeDateCheck(regisType, command);

                        }).ifNo(() => { return; });
                    } else {

                        self.dismissalNoticeDateCheck(regisType, command);

                    }
                }
            } else {
                self.registerData(regisType, command);    
            }
        }
        
        dismissalNoticeDateCheck(regisType, command) {
            let self = this;
            let objResultWhenRetimentChange = self.currentEmployee().objResultWhenRetimentChange;
            let dismissalNoticeDateA22235 = moment.utc(self.currentEmployee().dismissalNoticeDate(), DateFormat.DEFAULT_FORMAT);
            if (objResultWhenRetimentChange.dismissalNoticeDate == null) {
                
                self.registerData(regisType, command);

            } else if (objResultWhenRetimentChange.dismissalNoticeDate != null) {

                let dismissalNoticeDateCalculator = moment.utc(objResultWhenRetimentChange.dismissalNoticeDate, DateFormat.DEFAULT_FORMAT);
                let dayDifferenceDismissalNotice = dismissalNoticeDateA22235.diff(dismissalNoticeDateCalculator, 'days');

                if (dayDifferenceDismissalNotice != 0) {
                    if (dismissalNoticeDateA22235 < dismissalNoticeDateCalculator) {

                        if (self.currentEmployee().dismissalNoticeDateAllow()) {
                            self.showConfirmDialog("MsgJ_JCM007_9", command, regisType);
                        } else {
                            self.showConfirmDialog("MsgJ_JCM007_14", command, regisType);
                        }

                    } else if (dismissalNoticeDateA22235 > dismissalNoticeDateCalculator) {

                        if (self.currentEmployee().dismissalNoticeDateAllow()) {
                            self.showConfirmDialog("MsgJ_JCM007_14", command, regisType);
                        } else {
                            self.showConfirmDialog("MsgJ_JCM007_8", command, regisType);
                        }

                    } else if (dismissalNoticeDateA22235 == dismissalNoticeDateCalculator) {

                        if (self.currentEmployee().dismissalNoticeDateAllow()) {
                            self.showConfirmDialog("MsgJ_JCM007_9", command, regisType);
                        } else {
                            self.registerData(regisType, command);
                        }
                    }
                } else  if (dayDifferenceDismissalNotice == 0) {

                    self.registerData(regisType, command);
                }
            }
        }
        
        showConfirmDialog(messageId, command, regisType) {
            let self = this;
            nts.uk.ui.dialog.confirm({ messageId: messageId }).ifYes(() => {

                self.registerData(regisType, command);

            }).ifNo(() => { });
        }
        
        registerData(regisType, command) {
            let self = this;
            if (regisType == 2) {
                self.registerNewEmployee(command);
            } else if (regisType == 3) {
                self.registerNewRetireesApproved(command);
            } else if (regisType == 4) {
                self.modifyRetireeInformation(command);
            }
        }


        // ??????????????????[????????????????????????]??????????????? (Th???c hi???n thu???t to??n [Thay ?????i th??ng tin nh??n vi??n ng?????i  ngh??? h??u])
        modifyRetireeInformation(command: any): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred<any>();
            block.grayout();
            service.modifyRetireeInformation(command).done((result : boolean) => {
                console.log('UPDATE DONE!');
                console.log(result);
                self.getListDataAfterRegisterInTab2(command.historyId).done(() => {
                    dialog.info({ messageId: "Msg_15" });
                });

                block.clear();
                dfd.resolve();
            }).fail((mes) => {
                console.log('UPDATE FAIL!!');
                block.clear();
                dfd.reject();
            });
            return dfd.promise();
        }

        getListDataAfterRegisterInTab2(historyId) {
            let self = this;
            let dfd = $.Deferred<any>();
            block.grayout();
            service.getData().done((result) => {
                if (result.retiredEmployees.length != 0) {
                    
                    self.isNewMode = false;
                    
                    self.enable_btnExportExcel(true);

                    self.empInfoHeaderList = result.employeeImports;

                    self.employeeListTab2 = result.retiredEmployees;

                    $("#gridListEmployeesJcm007").igGrid('option', 'dataSource', self.employeeListTab2);
                    
                    if (historyId) {
                        let itemSelected = _.find(self.employeeListTab2, function(o) { return o.historyId == historyId; });
                        if (itemSelected) {
                            self.itemSelectedTab2(itemSelected);
                            self.setDataDetail(itemSelected);
                        } else {
                            self.initHeaderInfo();
                            self.initRetirementInfo();
                            self.itemSelectedTab2(null);
                        }
                    }
                } else {
                    self.setDataWhenListDataEmpty();
                }
                dfd.resolve();
                block.clear();

            }).fail((error) => {
                console.log('Get Data Tab-2 Fail');
                dfd.reject();
                nts.uk.ui.dialog.info(error);
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }

        remove() {
            let self = this;

            if (nts.uk.ui.errors.hasError()) {
                return;
            }

            let itemSelectedTab2 = self.itemSelectedTab2();
            let dataSource = $('#gridListEmployeesJcm007').igGrid('option', 'dataSource');

            if (self.selectedTab() == 'tab-2' && itemSelectedTab2 != null
                && itemSelectedTab2.status != self.status_Unregistered) {
                let histId = itemSelectedTab2.historyId;
                let lengthListItemTab2 = self.employeeListTab2.length;
                let indexItemDelete = _.findIndex(ko.toJS(dataSource), function(item: any) { return item.historyId == histId; });
                let itemEndOfList = (indexItemDelete == lengthListItemTab2 -1) ? true : false;
                let itemStartOfList = (indexItemDelete == 0) ? true : false;
                
                confirm({ messageId: "Msg_18" }).ifYes(() => {
                    block.grayout();
                    service.remove(histId).done((result : boolean) => {
                        console.log('REMOVE DONE!!');
                        if (result){
                            self.enable_btnExportExcel(true); 
                            self.getListDataAfterRemove().done(() => {
                                let lengthListItemTab2AfterDel = self.employeeListTab2.length;
                                if (lengthListItemTab2AfterDel == 0) {
                                    self.itemSelectedTab2(null);
                                    self.enable_tab2(false);
                                    self.visible_tab2(false);
                                    self.newMode();
                                } else if (lengthListItemTab2AfterDel == 1 || itemStartOfList) {
                                    self.itemSelectedTab2(self.employeeListTab2[0]);
                                    $("#gridListEmployeesJcm007").igGridSelection("selectRow", 0);
                                } else if (itemEndOfList) {
                                    self.itemSelectedTab2(self.employeeListTab2[lengthListItemTab2AfterDel - 1]);
                                     $("#gridListEmployeesJcm007").igGridSelection("selectRow", lengthListItemTab2AfterDel - 1);
                                } else {
                                    self.itemSelectedTab2(self.employeeListTab2[indexItemDelete]);
                                    $("#gridListEmployeesJcm007").igGridSelection("selectRow", indexItemDelete);
                                }
                                dialog.info({ messageId: "Msg_16" });
                            });
                        } else {
                            self.setDataWhenListDataEmpty();  
                            dialog.info({ messageId: "Msg_16" });
                        }
                        
                        block.clear();
                    }).fail((mes) => {
                        console.log('REMOVE FAIL!!');
                        block.clear();
                    });
                }).ifNo(() => { return; });
            }
        }
        
         // get l???i list sau khi xo??a
        getListDataAfterRemove() {
            let self = this;
            let dfd = $.Deferred<any>();
            block.grayout();
            service.getData().done((result) => {
                if (result.retiredEmployees.length != 0) {
                    self.enable_btnExportExcel(true);
                    self.empInfoHeaderList = result.employeeImports;

                    self.employeeListTab2 = result.retiredEmployees;

                    $("#gridListEmployeesJcm007").igGrid('option', 'dataSource', self.employeeListTab2);

                } else {
                    self.setDataWhenListDataEmpty();
                }
                dfd.resolve();
                block.clear();
                
            }).fail((error) => {
                console.log('Get Data Tab-2 Fail');
                dfd.reject();
                nts.uk.ui.dialog.info(error);
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }
        
        setDataWhenListDataEmpty() {
            let self = this;
            self.selectedTab('tab-1');
            self.enable_tab1(true);
            self.visible_tab1(true);
            self.itemSelectedTab1(null);
            self.itemSelectedTab2(null);
            self.enable_tab2(false);
            self.visible_tab2(false);
            self.enable_btnExportExcel(false);
            self.newMode();
        }

        setDataHeader(param) {
            let self = this;
            service.findEmployeeInfo(param).done((data) => {
                if (!data) {
                    return;
                }
                let departmentCode = data.department ? data.department.departmentCode : '';
                let departmentName = data.department ? data.department.departmentName : '';
                let positionName = data.position ? data.position.positionName : '';;
                let employmentName = data.employment ? data.employment.employmentName : '';

                self.currentEmployee().avatarPerson(data.avatarFile && data.avatarFile.facePhotoFileID ? liveView(data.avatarFile.facePhotoFileID) : 'images/avatar.png');
                self.currentEmployee().codeNameEmp(data.employeeCode + ' ' + data.businessName);
                self.currentEmployee().department(departmentCode + ' ' + departmentName);
                self.currentEmployee().position(positionName);
                self.currentEmployee().employmentCls(employmentName);
                self.currentEmployee().sid = data.employeeId;
            });

        }

        setDataDetail(dataDetail: any) {
            let self = this;
            self.currentEmployee().notCallRetirementDateChange = true;
            self.currentEmployee().retirementDate(dataDetail.retirementDate);
            self.currentEmployee().releaseDate(dataDetail.releaseDate);

            self.currentEmployee().selectedCode_Retiment(dataDetail.retirementCategory);
            self.currentEmployee().selectedCode_Reason1(dataDetail.retirementReasonCtg1);
            self.currentEmployee().selectedCode_Reason2(dataDetail.retirementReasonCtg2);
            
            
            self.currentEmployee().retirementRemarks(dataDetail.retirementRemarks == null ? '' : dataDetail.retirementRemarks);
            // ----------------- //
            self.currentEmployee().retirementReasonVal(dataDetail.retirementReasonVal == null ? '' : dataDetail.retirementReasonVal);
            // ----------------- //
            self.currentEmployee().dismissalNoticeDate(dataDetail.dismissalNoticeDate == null ? '' : dataDetail.dismissalNoticeDate);
            self.currentEmployee().dismissalNoticeDateAllow(dataDetail.dismissalNoticeDateAllow == null ? '' : dataDetail.dismissalNoticeDateAllow);
            self.currentEmployee().reaAndProForDis(dataDetail.reaAndProForDis == null ? '' : dataDetail.reaAndProForDis);

            self.currentEmployee().naturalUnaReasons_1(dataDetail.naturalUnaReasons_1 == 0 ? false : true);
            self.currentEmployee().naturalUnaReasons_1Val(dataDetail.naturalUnaReasons_1Val == null ? '' : dataDetail.naturalUnaReasons_1Val);
            self.currentEmployee().businessReduction_2(dataDetail.naturalUnaReasons_2 == 0 ? false : true);
            self.currentEmployee().businessReduction_2Val(dataDetail.naturalUnaReasons_2Val == null ? '' : dataDetail.naturalUnaReasons_2Val);
            self.currentEmployee().seriousViolationsOrder_3(dataDetail.naturalUnaReasons_3 == 0 ? false : true);
            self.currentEmployee().seriousViolationsOrder_3Val(dataDetail.naturalUnaReasons_3Val == null ? '' : dataDetail.naturalUnaReasons_3Val);
            self.currentEmployee().unauthorizedConduct_4(dataDetail.naturalUnaReasons_4 == 0 ? false : true);
            self.currentEmployee().unauthorizedConduct_4Val(dataDetail.naturalUnaReasons_4Val == null ? '' : dataDetail.naturalUnaReasons_4Val);
            self.currentEmployee().leaveConsiderableTime_5(dataDetail.naturalUnaReasons_5 == 0 ? false : true);
            self.currentEmployee().leaveConsiderableTime_5Val(dataDetail.naturalUnaReasons_5Val == null ? '' : dataDetail.naturalUnaReasons_5Val);
            self.currentEmployee().other_6(dataDetail.naturalUnaReasons_6 == 0 ? false : true);
            self.currentEmployee().other_6Val(dataDetail.naturalUnaReasons_6Val == null ? '' : dataDetail.naturalUnaReasons_6Val);
            self.currentEmployee().notCallRetirementDateChange  =  false;
        }

        newMode() {
            let self = this;
            self.isNewMode = true;
            self.enable_tab1(true);
            self.visible_tab1(true);
            
            self.itemSelectedTab1(null);
            self.itemSelectedTab2(null);
            $("#gridListEmployees").igGridSelection("clearSelection");
            $("#gridListEmployeesJcm007").igGridSelection("clearSelection");
            
            self.selectedTab('tab-1');
            self.initHeaderInfo();
            self.initRetirementInfo();
            nts.uk.ui.errors.clearAll();
        }

        initHeaderInfo() {
            let self = this;
            self.enable_btnRemove(false);
            self.currentEmployee().avatarPerson('images/avatar.png');
            self.currentEmployee().codeNameEmp('');
            self.currentEmployee().department('');
            self.currentEmployee().position('');
            self.currentEmployee().employmentCls('');
        }

        initRetirementInfo() {
            let self = this;
            self.currentEmployee().retirementDate('');
            self.currentEmployee().releaseDate('');
            self.currentEmployee().selectedCode_Retiment(1);
            self.currentEmployee().selectedCode_Reason1(null);
            self.currentEmployee().selectedCode_Reason2(null);
            self.currentEmployee().retirementRemarks('');
            // ----------------- //
            self.currentEmployee().retirementReasonVal('');
            // ----------------- //
            self.currentEmployee().dismissalNoticeDate('');
            self.currentEmployee().dismissalNoticeDateAllow('');
            self.currentEmployee().reaAndProForDis('');

            self.currentEmployee().naturalUnaReasons_1(false);
            self.currentEmployee().naturalUnaReasons_1Val('');
            self.currentEmployee().naturalUnaReasons_enable(false);

            self.currentEmployee().businessReduction_2(false);
            self.currentEmployee().businessReduction_2Val('');
            self.currentEmployee().businessReduction_enable(false);

            self.currentEmployee().seriousViolationsOrder_3(false);
            self.currentEmployee().seriousViolationsOrder_3Val('');
            self.currentEmployee().seriousViolationsOrder_enable(false);

            self.currentEmployee().unauthorizedConduct_4(false);
            self.currentEmployee().unauthorizedConduct_4Val('');
            self.currentEmployee().unauthorizedConduct_enable(false);

            self.currentEmployee().leaveConsiderableTime_5(false);
            self.currentEmployee().leaveConsiderableTime_5Val('');
            self.currentEmployee().leaveConsiderableTime_enable(false);

            self.currentEmployee().other_6(false);
            self.currentEmployee().other_6Val('');
            self.currentEmployee().other_enable(false);

            nts.uk.ui.errors.clearAll();
        }

        enable_disableInput(param: boolean) {
            let self = this;
            self.currentEmployee().enable_retirementDate(param);
            self.currentEmployee().enable_releaseDate(param);
            self.currentEmployee().enable_listRetirementCtg(param);
            self.currentEmployee().enable_listRetirementReason1(param);
            self.currentEmployee().enable_listRetirementReason2(param);
            self.currentEmployee().enable_retirementRemarks(param);
            self.currentEmployee().enable_retirementReasonVal(param);
            self.currentEmployee().enable_dismissalNoticeDate(param);
            self.currentEmployee().enable_dismissalNoticeDateAllow(param);
            self.currentEmployee().enable_reaAndProForDis(param);

            self.currentEmployee().enable_naturalUnaReasons1(param);
            if ((self.currentEmployee().naturalUnaReasons_1() == false) ||
                (self.currentEmployee().naturalUnaReasons_1() == true && self.currentEmployee().enable_naturalUnaReasons1() == false)) {
                self.currentEmployee().naturalUnaReasons_enable(false);
            } else if (self.currentEmployee().naturalUnaReasons_1() == true) {
                self.currentEmployee().naturalUnaReasons_enable(true);
            }

            self.currentEmployee().enable_businessReduction2(param);
            if ((self.currentEmployee().businessReduction_2() == false) ||
                (self.currentEmployee().businessReduction_2() == true && self.currentEmployee().enable_businessReduction2() == false)) {
                self.currentEmployee().businessReduction_enable(false);
            } else if (self.currentEmployee().businessReduction_2() == true && self.currentEmployee().enable_businessReduction2() == true) {
                self.currentEmployee().businessReduction_enable(true);
            }

            self.currentEmployee().enable_seriousViolationsOrder3(param);
            if ((self.currentEmployee().seriousViolationsOrder_3() == false) ||
                (self.currentEmployee().seriousViolationsOrder_3() == true && self.currentEmployee().enable_seriousViolationsOrder3() == false)) {
                self.currentEmployee().seriousViolationsOrder_enable(false);
            } else if (self.currentEmployee().seriousViolationsOrder_3() == true && self.currentEmployee().enable_seriousViolationsOrder3() == true) {
                self.currentEmployee().seriousViolationsOrder_enable(true);
            }

            self.currentEmployee().enable_unauthorizedConduct4(param);
            if ((self.currentEmployee().unauthorizedConduct_4() == false) ||
                (self.currentEmployee().unauthorizedConduct_4() == true && self.currentEmployee().enable_unauthorizedConduct4() == false)) {
                self.currentEmployee().unauthorizedConduct_enable(false);
            } else if (self.currentEmployee().unauthorizedConduct_4() == true && self.currentEmployee().enable_unauthorizedConduct4() == true) {
                self.currentEmployee().unauthorizedConduct_enable(true);
            }

            self.currentEmployee().enable_leaveConsiderableTime5(param);
            if ((self.currentEmployee().leaveConsiderableTime_5() == false) ||
                (self.currentEmployee().leaveConsiderableTime_5() == true && self.currentEmployee().enable_leaveConsiderableTime5() == false)) {
                self.currentEmployee().leaveConsiderableTime_enable(false);
            } else if (self.currentEmployee().leaveConsiderableTime_5() == true && self.currentEmployee().enable_leaveConsiderableTime5() == true) {
                self.currentEmployee().leaveConsiderableTime_enable(true);
            }

            self.currentEmployee().enable_other6(param);
            if ((self.currentEmployee().other_6() == false) ||
                (self.currentEmployee().other_6() == true && self.currentEmployee().enable_other6() == false)) {
                self.currentEmployee().other_enable(false);
            } else if (self.currentEmployee().other_6() == true && self.currentEmployee().enable_other6() == true) {
                self.currentEmployee().other_enable(true);
            }
        }
    }
    class EmployeeModel {

        avatarPerson: KnockoutObservable<string> = ko.observable('images/avatar.png');
        codeNameEmp: KnockoutObservable<string> = ko.observable('');   // A222_3
        department: KnockoutObservable<string> = ko.observable('');   // A222_5
        position: KnockoutObservable<string> = ko.observable('');   // A222_7
        employmentCls: KnockoutObservable<string> = ko.observable('');   // A222_9

        hisId: string;
        cid: string;
        sid: string;
        scd: string;
        pid: string;
        bussinessName: string;

        pendingFlag: number;
        status: string;
        notificationCategory: string;
        inputDate: string;

        retirementDate: KnockoutObservable<string> = ko.observable('');   // A222_12
        releaseDate: KnockoutObservable<string> = ko.observable(''); // A222_14
        // list Retirement Category
        listRetirementCtg: KnockoutObservable<Array<any>> = ko.observable([]);
        selectedCode_Retiment: KnockoutObservable<number> = ko.observable(1);

        // list Retirement Reason1
        listRetirementReason1: KnockoutObservable<Array<any>> = ko.observable([]);
        selectedCode_Reason1: KnockoutObservable<number> = ko.observable(1);

        // list Retirement Reason2
        listRetirementReason2: KnockoutObservable<Array<any>> = ko.observable([]);
        selectedCode_Reason2: KnockoutObservable<number> = ko.observable(1);

        shouldInterviewRecord: KnockoutObservable<boolean> = ko.observable(false);
        retirementRemarks: KnockoutObservable<string> = ko.observable('');

        visible_NotDismissal: KnockoutObservable<boolean> = ko.observable(false);
        retirementReasonVal: KnockoutObservable<string> = ko.observable('');

        visible_Dismissal: KnockoutObservable<boolean> = ko.observable(false);
        dismissalNoticeDate: KnockoutObservable<string> = ko.observable('');
        dismissalNoticeDateAllow: KnockoutObservable<string> = ko.observable('');
        reaAndProForDis: KnockoutObservable<string> = ko.observable('');

        naturalUnaReasons_1: KnockoutObservable<boolean> = ko.observable(false);
        naturalUnaReasons_1Val: KnockoutObservable<string> = ko.observable('');
        naturalUnaReasons_enable: KnockoutObservable<boolean> = ko.observable(false);

        businessReduction_2: KnockoutObservable<boolean> = ko.observable(false);
        businessReduction_2Val: KnockoutObservable<string> = ko.observable('');
        businessReduction_enable: KnockoutObservable<boolean> = ko.observable(false);

        seriousViolationsOrder_3: KnockoutObservable<boolean> = ko.observable(false);
        seriousViolationsOrder_3Val: KnockoutObservable<string> = ko.observable('');
        seriousViolationsOrder_enable: KnockoutObservable<boolean> = ko.observable(false);

        unauthorizedConduct_4: KnockoutObservable<boolean> = ko.observable(false);
        unauthorizedConduct_4Val: KnockoutObservable<string> = ko.observable('');
        unauthorizedConduct_enable: KnockoutObservable<boolean> = ko.observable(false);

        leaveConsiderableTime_5: KnockoutObservable<boolean> = ko.observable(false);
        leaveConsiderableTime_5Val: KnockoutObservable<string> = ko.observable('');
        leaveConsiderableTime_enable: KnockoutObservable<boolean> = ko.observable(false);

        other_6: KnockoutObservable<boolean> = ko.observable(false);
        other_6Val: KnockoutObservable<string> = ko.observable('');
        other_enable: KnockoutObservable<boolean> = ko.observable(false);

        // enabe input
        enable_retirementDate: KnockoutObservable<boolean> = ko.observable(true);
        enable_releaseDate: KnockoutObservable<boolean> = ko.observable(true);
        enable_listRetirementCtg: KnockoutObservable<boolean> = ko.observable(true);
        enable_listRetirementReason1: KnockoutObservable<boolean> = ko.observable(true);
        enable_listRetirementReason2: KnockoutObservable<boolean> = ko.observable(true);
        enable_retirementRemarks: KnockoutObservable<boolean> = ko.observable(true);
        enable_retirementReasonVal: KnockoutObservable<boolean> = ko.observable(true);
        enable_dismissalNoticeDate: KnockoutObservable<boolean> = ko.observable(true);
        enable_dismissalNoticeDateAllow: KnockoutObservable<boolean> = ko.observable(true);
        enable_reaAndProForDis: KnockoutObservable<boolean> = ko.observable(true);
        enable_naturalUnaReasons1: KnockoutObservable<boolean> = ko.observable(true);
        enable_businessReduction2: KnockoutObservable<boolean> = ko.observable(true);
        enable_seriousViolationsOrder3: KnockoutObservable<boolean> = ko.observable(true);
        enable_unauthorizedConduct4: KnockoutObservable<boolean> = ko.observable(true);
        enable_leaveConsiderableTime5: KnockoutObservable<boolean> = ko.observable(true);
        enable_other6: KnockoutObservable<boolean> = ko.observable(true);

        objResultWhenRetimentChange: any;
        notCallRetirementDateChange : boolean;

        constructor(param: IEmployee) {
            let self = this;

            self.hisId = param.hisId;
            self.cid = param.cid;
            self.sid = param.sid;
            self.scd = param.scd;
            self.pid = param.pid;
            self.bussinessName = param.bussinessName;
            self.notCallRetirementDateChange = false;

            self.pendingFlag = param.pendingFlag;
            self.status = param.status;
            self.notificationCategory = param.notificationCategory;
            self.inputDate = param.inputDate;

            self.retirementDate(param.retirementDate || '');
            self.releaseDate(param.releaseDate || '');
            self.listRetirementCtg = ko.observable([
                { value: 1, text: '??????' },
                { value: 2, text: '??????' },
                { value: 3, text: '??????' },
                { value: 4, text: '??????' }
            ]);
            self.selectedCode_Retiment(param.selectedCode_Retiment || 1);

            self.listRetirementReason1 = ko.observable([
                { value: 1, text: '???????????????????????????' },
                { value: 2, text: '?????????????????????' },
                { value: 3, text: '???????????????????????????' }
            ]);
            self.selectedCode_Reason1(param.selectedCode_Reason1 || null);

            self.listRetirementReason2 = ko.observable([
                { value: 1, text: '??????' },
                { value: 2, text: '?????????????????????' },
                { value: 3, text: '???????????????????????????' },
                { value: 4, text: '?????????????????????' },
                { value: 5, text: '?????????' }
            ]);
            self.selectedCode_Reason2(param.selectedCode_Reason2 || null);

            // multieditor 1
            self.retirementRemarks(param.retirementRemarks || '');

            // multieditor 2
            self.retirementReasonVal(param.retirementReasonVal || '');

            self.visible_NotDismissal(param.visible_NotDismissal || true);

            self.dismissalNoticeDate(param.retirementDate || '');
            self.dismissalNoticeDateAllow(param.retirementDate || '');

            // multieditor 3
            self.reaAndProForDis(param.reaAndProForDis || '');

            self.naturalUnaReasons_1(param.naturalUnaReasons_1 || false);
            self.businessReduction_2(param.naturalUnaReasons_1 || false);
            self.seriousViolationsOrder_3(param.naturalUnaReasons_1 || false);
            self.unauthorizedConduct_4(param.naturalUnaReasons_1 || false);
            self.leaveConsiderableTime_5(param.naturalUnaReasons_1 || false);
            self.other_6(param.naturalUnaReasons_1 || false);

            self.naturalUnaReasons_1Val(param.naturalUnaReasons_1Val || '');
            self.businessReduction_2Val(param.businessReduction_2Val || '');
            self.seriousViolationsOrder_3Val(param.seriousViolationsOrder_3Val || '');
            self.unauthorizedConduct_4Val(param.unauthorizedConduct_4Val || '');
            self.leaveConsiderableTime_5Val(param.leaveConsiderableTime_5Val || '');
            self.other_6Val(param.other_6Val || '');

            //x??? l?? subscribe
            self.retirementDate.subscribe((value) => {
                console.log("retirementDate change");
                if (self.notCallRetirementDateChange) {
                    console.log("notCallRetirementDateChange");
                    return;
                }
                if(self.sid == undefined || value == "")
                    return;
                
                block.grayout();
                let object = {
                    retirementDate : value, // A222_12  ?????????
                    retirementType : self.selectedCode_Retiment(),        // A222_16 ????????????
                    sid            : self.sid,                 // ??????ID = ????????????????????????ID(EmployeeID = EmployeeID c???a employee dang chon)
                    cid            : null,                 // ??????ID = ??????????????????ID(CompanyID = LoginCompanyID) l???y tr??n server
                    baseDate       : null                  // l???y tr??n server
                };
                
                service.eventChangeRetirementDate(object).done((result: any) => {
                    console.log('event retirementDate change done');
                    console.log(result);
                    self.objResultWhenRetimentChange = result;
                    if (result.processingResult == true) {
                        self.releaseDate(result.releaseDate);

                        if (result.dismissalNoticeAlerm == false) {
                            error({ messageId: result.errorMessageId });

                        } else {
                            if (result.dismissalNoticeDateCheckProcess && result.dismissalAllowance) {
                                self.dismissalNoticeDate(result.dismissalNoticeDate);
                            }
                        }
                    }
                }).fail((error) => {
                    console.log('event retirementDate change done fail');
                    console.log(error);
                    block.clear();
                    nts.uk.ui.dialog.info(error);
                    return;
                }).always(() => {
                    block.clear();
                });
            });

            self.selectedCode_Retiment.subscribe((value) => {
                let self = this;
                $('#dismissalNoticeDateId').ntsError('clear');
                if (value == 1 || value == 2 || value == 4) {
                    self.visible_NotDismissal(true);
                    self.visible_Dismissal(false);
                    // clear input
                    self.dismissalNoticeDate('');
                    self.dismissalNoticeDateAllow('');
                    self.reaAndProForDis('');
                    $('#reaAndProForDis').ntsError('clear');
                    self.naturalUnaReasons_1Val('');
                    self.businessReduction_2Val('');
                    self.seriousViolationsOrder_3Val('');
                    self.unauthorizedConduct_4Val('');
                    self.leaveConsiderableTime_5Val('');
                    self.other_6Val('');
                    self.naturalUnaReasons_1(false);
                    self.businessReduction_2(false);
                    self.seriousViolationsOrder_3(false);
                    self.unauthorizedConduct_4(false);
                    self.leaveConsiderableTime_5(false);
                    self.other_6(false);

                } else if (value == 3) {
                    self.visible_NotDismissal(false);
                    self.visible_Dismissal(true);
                    self.retirementReasonVal('');
                    $('#retirementReasonVal').ntsError('clear');

                    if (self.naturalUnaReasons_1() == false) {
                        self.naturalUnaReasons_1Val('');
                        self.naturalUnaReasons_enable(false);
                    }
                    if (self.businessReduction_2() == false) {
                        self.businessReduction_2Val('');
                        self.businessReduction_enable(false);
                    }
                    if (self.seriousViolationsOrder_3() == false) {
                        self.seriousViolationsOrder_3Val('');
                        self.seriousViolationsOrder_enable(false);
                    }
                    if (self.unauthorizedConduct_4() == false) {
                        self.unauthorizedConduct_4Val('');
                        self.unauthorizedConduct_enable(false);
                    }
                    if (self.leaveConsiderableTime_5() == false) {
                        self.leaveConsiderableTime_5Val('');
                        self.leaveConsiderableTime_enable(false);
                    }
                    if (self.other_6() == false) {
                        self.other_6Val('');
                        self.other_enable(false);
                    }
                }
            });

            self.naturalUnaReasons_1.subscribe((value) => {
                let self = this;
                if (value == true && self.enable_naturalUnaReasons1() == false) {
                    self.naturalUnaReasons_enable(false);
                    console.log('enable_disableInput 1');
                } else if (value == true) {
                    self.naturalUnaReasons_enable(true);
                    console.log('enable_disableInput 2');
                    $("#naturalUnaReasons_1Val").focus();
                } else if (value == false) {
                    self.naturalUnaReasons_1Val('');
                    self.naturalUnaReasons_enable(false);
                    $('#naturalUnaReasons_1Val').ntsError('clear');
                }
            });

            self.businessReduction_2.subscribe((value) => {
                let self = this;
                if (value == true && self.enable_businessReduction2() == false) {
                    self.businessReduction_enable(false);
                } else if (value == true) {
                    self.businessReduction_enable(true);
                    $("#businessReduction_2Val").focus();
                } else if (value == false) {
                    self.businessReduction_2Val('');
                    self.businessReduction_enable(false);
                    $('#businessReduction_2Val').ntsError('clear');
                }
            });

            self.seriousViolationsOrder_3.subscribe((value) => {
                let self = this;
                if (value == true && self.enable_seriousViolationsOrder3() == false) {
                    self.seriousViolationsOrder_enable(false);
                } else if (value == true) {
                    self.seriousViolationsOrder_enable(true);
                    $("#seriousViolationsOrder_3Val").focus();
                } else if (value == false) {
                    self.seriousViolationsOrder_3Val('');
                    self.seriousViolationsOrder_enable(false);
                    $('#seriousViolationsOrder_3Val').ntsError('clear');
                }
            });

            self.unauthorizedConduct_4.subscribe((value) => {
                let self = this;
                if (value == true && self.enable_unauthorizedConduct4() == false) {
                    self.unauthorizedConduct_enable(false);
                } else if (value == true) {
                    self.unauthorizedConduct_enable(true);
                    $("#unauthorizedConduct_4Val").focus();
                } else if (value == false) {
                    $('#unauthorizedConduct_4Val').ntsError('clear');
                    self.unauthorizedConduct_4Val('');
                    self.unauthorizedConduct_enable(false);
                }
            });

            self.leaveConsiderableTime_5.subscribe((value) => {
                let self = this;
                if (value == true && self.enable_leaveConsiderableTime5() == false) {
                    self.leaveConsiderableTime_enable(false);
                } else if (value == true) {
                    self.leaveConsiderableTime_enable(true);
                    $("#leaveConsiderableTime_5Val").focus();
                } else if (value == false) {
                    self.leaveConsiderableTime_5Val('');
                    self.leaveConsiderableTime_enable(false);
                    $('#leaveConsiderableTime_5Val').ntsError('clear');
                }
            });

            self.other_6.subscribe((value) => {
                let self = this;
                if (value == true && self.enable_other6() == false) {
                    self.other_enable(false);
                } else if (value == true) {
                    self.other_enable(true);
                    $("#other_6Val").focus();
                } else if (value == false) {
                    self.other_6Val('');
                    self.other_enable(false);
                    $('#other_6Val').ntsError('clear');
                }
            });
        }
    }
    

    interface IEmpInfoHeader {
        avartaFileId: string;
        businessName: string;
        businessNameKana: string;
        departmentCode: string;
        departmentName: string;
        employeeCode: string;
        employeeId: string;
        employmentCode: string;
        employmentName: string;
        mapFileId: string;
        personalId: string;
        positionCode: string;
        positionId: string;
        positionName: string;
        workplaceCode: string;
        workplaceId: string;
        workplaceName: string;
    }

    class EmpInfoHerder {
        avartaFileId: string;
        businessName: string;
        businessNameKana: string;
        departmentCode: string;
        departmentName: string;
        employeeCode: string;
        employeeId: string;
        employmentCode: string;
        employmentName: string;
        mapFileId: string;
        personalId: string;
        positionCode: string;
        positionId: string;
        positionName: string;
        workplaceCode: string;
        workplaceId: string;
        workplaceName: string;

        constructor(input: IEmpInfoHeader) {
            this.avartaFileId = input.avartaFileId;
            this.businessName = input.businessName;
            this.businessNameKana = input.businessNameKana;
            this.departmentCode = input.departmentCode;
            this.departmentName = input.departmentName
            this.employeeCode = input.employeeCode;
            this.employeeId = input.employeeId;
            this.employmentCode = input.employmentCode;
            this.employmentName = input.employmentName;
            this.mapFileId = input.mapFileId;
            this.personalId = input.personalId;
            this.positionCode = input.positionCode;
            this.positionId = input.positionId;
            this.positionName = input.positionName;
            this.workplaceCode = input.workplaceCode;
            this.workplaceId = input.workplaceId;
            this.workplaceName = input.workplaceName;
        }
    }

    interface IEmployee {
        hisId: string;
        cid: string;
        sid: string;
        scd: string;
        pid: string;
        bussinessName: string;
        department: string;
        position: string;
        employment: string;
        fileId: string;

        pendingFlag: number;
        status: string;
        notificationCategory: string;
        inputDate: string;

        retirementDate: string; // A222_12
        releaseDate: string;  // A222_14

        selectedCode_Retiment: number; // A222_16
        selectedCode_Reason1: number;  // A222_20
        selectedCode_Reason2: number;  // A222_23

        retirementRemarks: string; // A222_26

        retirementReasonVal: string; // A222_31

        dismissalNoticeDate: string; // A222_35
        dismissalNoticeDateAllow: string; // A222_37
        reaAndProForDis: string;  // A222_39

        naturalUnaReasons_1: boolean; // A222_41
        naturalUnaReasons_1Val: string;  // A222_42

        businessReduction_2: boolean; // A222_43
        businessReduction_2Val: string; // A222_44

        seriousViolationsOrder_3: boolean; // A222_45
        seriousViolationsOrder_3Val: string; // A222_46

        unauthorizedConduct_4: boolean; // A222_47
        unauthorizedConduct_4Val: string; // A222_48

        leaveConsiderableTime_5: boolean; // A222_49
        leaveConsiderableTime_5Val: string; // A222_50

        other_6: boolean;  // A222_51
        other_6Val: string;  // A222_52
    }

    class Input {
        systemType: number; //?????????????????????0????????????1????????????2????????????3????????????
        includePreEmployee: boolean; //???????????????????????????
        includeRetirement: boolean; //?????????????????????
        includeAbsence: boolean; //????????????????????? 
        includeClosed: boolean; //?????????????????????
        includeTransferEmployee: boolean; //????????????????????????
        includeAcceptanceTransferEmployee: boolean; //??????????????????????????????
        getPosition: boolean; //?????????????????????
        getEmployment: boolean; //?????????????????????
        getPersonalFileManagement: boolean; //???????????????????????????????????????

        constructor(input: any) {
            this.systemType = input ? input.systemType || 3 : 3;
            this.includePreEmployee = input ? input.includePreEmployee || false : false;
            this.includeRetirement = input ? input.includeRetirement || false : false;
            this.includeAbsence = input ? input.includeAbsence || true : true;
            this.includeClosed = input ? input.includeClosed || true : true;
            this.includeTransferEmployee = input ? input.includeTransferEmployee || true : true;
            this.includeAcceptanceTransferEmployee = input ? input.includeAcceptanceTransferEmployee || false : false;
            this.getPosition = input ? input.getPosition || true : true;
            this.getEmployment = input ? input.getEmployment || true : true;
            this.getPersonalFileManagement = input ? input.getPersonalFileManagement || true : true;
        }
    }

    export class DateFormat {
        static DEFAULT_FORMAT = 'YYYY/MM/DD';
    }

}