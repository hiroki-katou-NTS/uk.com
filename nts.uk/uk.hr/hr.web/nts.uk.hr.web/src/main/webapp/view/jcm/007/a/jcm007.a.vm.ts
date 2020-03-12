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
    
    var block = nts.uk.ui.block;
    export class ViewModel {

        currentEmployee: KnockoutObservable<EmployeeModel>;
        
        tabs       : KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;
        enable_btnRemove   : KnockoutObservable<boolean>;
        enable_btnRegister : KnockoutObservable<boolean>;

        // tab 2
        employeeListTab2  : [];
        enable_tab2 : KnockoutObservable<boolean>;
        visible_tab2 : KnockoutObservable<boolean>;
        
        itemSelectedTab1  : KnockoutObservable<any>;
        itemSelectedTab2  : KnockoutObservable<any>;
        empInfoHeaderList : KnockoutObservableArray<IEmpInfoHeader>;
        
        // ccg029
        input: Input;
        
        isNewMode : boolean;
        
        status_Unregistered : string;
        status_ApprovalPending : string;
        status_WaitingReflection : string;
        
        notify_Ctg_Normal: string;
        notify_Ctg_Report : string;
        
        constructor() {
            let self = this;
            
            self.enable_tab2  = ko.observable(true);
            self.visible_tab2  = ko.observable(true);
            
            self.tabs = ko.observableArray([
                { id: 'tab-1', title: getText('JCM007_A221_1_1') , content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: getText('JCM007_A221_1_3') , content: '.tab-content-2', enable: self.enable_tab2, visible: self.visible_tab2 }
            ]);
            
            self.employeeListTab2   = [];
            self.bindData();
            
            self.selectedTab        = ko.observable('tab-1');

            self.currentEmployee    = ko.observable(new EmployeeModel(''));
            
            self.enable_btnRegister = ko.observable(true);
            self.enable_btnRemove   = ko.observable(false);

            self.itemSelectedTab1   = ko.observable(null);
            self.itemSelectedTab2   = ko.observable(null);
            self.empInfoHeaderList  = ko.observableArray([]);

            self.input              = new Input(undefined);
            self.isNewMode          = false;
            
            self.status_Unregistered      = ''; // 0
            self.status_ApprovalPending   = getText('JCM007_A3_2'); // 1
            self.status_WaitingReflection = getText('JCM007_A3_3'); // 2
        
            self.notify_Ctg_Normal        = '';  // 0
            self.notify_Ctg_Report        = getText('JCM007_A3_1'); // 1
            
            self.selectedTab.subscribe((value) => {
                if (value == 'tab-2') {
                    self.isNewMode = false;
                    self.getListData().done(() => {
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
                            let dataHeader = _.find(self.empInfoHeaderList, function(o) { return o.employeeId == itemSelectedTab2.sid; });
                            if (dataHeader) {
                                self.setDataHeader(dataHeader);
                            }

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
                        let dataHeader = self.itemSelectedTab1();
                        self.setDataHeader(dataHeader);
                        self.initRetirementInfo();
                    } else {
                        self.initHeaderInfo();
                        self.initRetirementInfo();
                    }
                    self.enable_disableInput(true);
                }
            });

            self.itemSelectedTab2.subscribe((value) => {
                if(value == null) return;
                if (value.status == self.status_ApprovalPending || value.status == self.status_Unregistered) {
                    // アルゴリズム[退職者情報の表示」を実行する] (Thực hiện thuật toán [Hiển thị thông tin người nghỉ hưu」)
                    self.enable_btnRegister(true);
                    self.enable_btnRemove(true);
                    self.enable_disableInput(true);
                } else if (value.status == self.status_WaitingReflection) {
                    self.enable_btnRegister(false);
                    self.enable_btnRemove(false);
                    self.enable_disableInput(false);
                }
                let dataHeader = _.find(self.empInfoHeaderList, function(o) { return o.employeeId == value.sid; });
                self.setDataHeader(dataHeader);

                let dataDetail = _.find(self.employeeListTab2, function(o) { return o.sid == value.sid; });
                self.setDataDetail(dataDetail);
                $('#retirementDateId').focus();
            });
        }
        
        public exportExcel(): void {

            let self = this,
                param = _.map(self.employeeListTab2, item => { return { sid: item.sId, pid: item.pId } });

            block.grayout();
            service.exportExcel({ listParam: param }).done(() => {
                block.clear();
            });
        }
        
        // select emp ben tab-1
        public seletedEmployee(data): void {            
            let self = this;
            self.itemSelectedTab1(data); 
            if (self.isNewMode) {
                // アルゴリズム[登録状況チェック]を実行する(Thực hiện thuật toán "Check tình trạng đăng ký ")
                block.grayout();
                service.CheckStatusRegistration(data.employeeId).done(() => {
                    console.log('CheckStatusRegis DONE');
                }).fail((error) => {
                    console.log('CheckStatusRegis FAIL');
                    block.clear();
                    nts.uk.ui.dialog.info(error);
                    return;
                }).always(() => {
                    block.clear();
                });
                this.initRetirementInfo();
            }
            // アルゴリズム[社員情報の表示]を実行する (Thực hiện thuật toán "Hiển thị thông tin employee")
            this.setDataHeader(data);
        }
        
        /** start page */
        start(historyId : any) {
            let self = this;
            let dfd = $.Deferred<any>();
            self.getListData(historyId).done(() => {
                dfd.resolve();
            }).fail(() => {
                dfd.reject();
            });
            return dfd.promise();
        }
        
        getListData(historyId : any, isAfterRemove : boolean) {
            let self = this;
            let dfd = $.Deferred<any>();
            block.grayout();
            service.getData().done((data1) => {
                // goi service アルゴリズム[社員情報リストを取得]を実行する
                // (Thực hiện thuật toán [Get list thông tin nhân viên]) CCG029
                if(data1.length != 0){
                    self.enable_tab2(true);
                    self.visible_tab2(true);
                    
                    let listParam = [];
                    _.forEach(data1, function(value) {
                        listParam.push({
                            sid: value.sId,
                            pid: value.pId
                        });
                    });

                    let paramCcg029 = {
                        listParam: listParam
                    };
                    
                    nts.uk.request.ajax("com", "query/ccg029employee/getEmpInfo", paramCcg029).done(function(data2) {
                       
                        self.empInfoHeaderList = data2;
                        self.employeeListTab2 = data1;
                        
                        if (self.selectedTab() == 'tab-1') {
                            self.enable_btnRemove(false);
                        } else if (self.selectedTab() == 'tab-2') {
                            //set selected fist
                           
                            if (!self.itemSelectedTab2()) {
                                $('#gridListEmployeesJcm007').igGridSelection('selectRow', 0);
                                self.itemSelectedTab2(data1[0]);
                            }
                            self.enable_btnRemove(true);
                        }
                        
                       
                        
                        $("#gridListEmployeesJcm007").igGrid('option','dataSource',self.employeeListTab2);
                        
                        if(historyId){
                            let itemSelected = _.find(self.employeeListTab2, function(o) { return o.historyId == historyId; });
                            self.itemSelectedTab2(itemSelected);
                            self.setDataDetail(itemSelected);    
                        }
                        
                        if(historyId && isAfterRemove){
                            let itemSelected = _.find(self.employeeListTab2, function(o) { return o.historyId == historyId; });
                            let indexItemSelected = _.findIndex(self.employeeListTab2, function(o) { return o.historyId == historyId; });
                            self.itemSelectedTab2(itemSelected);
                            self.setDataDetail(itemSelected);
                            $("#gridListEmployeesJcm007").igGridSelection("selectRow", indexItemSelected);    
                        }
                        dfd.resolve();
                        block.clear();

                    }).fail((error) => {
                        dfd.reject();
                        nts.uk.ui.dialog.info(error);
                    }).always(() => {
                        block.clear();
                    });
                } else {
                    
                    self.enable_tab2(false);
                    self.visible_tab2(false);
                    
                    self.initHeaderInfo();
                    self.initRetirementInfo();
                    dfd.resolve();
                }
                
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
                        headerText: getText('JCM007_A221_5') , key: 'status', dataType: 'string', width: '70px'
                    },
                    {
                        headerText: getText('JCM007_A221_6') , key: 'scd', dataType: 'string', width: '70px'
                    },
                    {
                        headerText: getText('JCM007_A221_7') , key: 'employeeName', dataType: 'string', width: '140px'
                    },
                    {
                        headerText: getText('JCM007_A221_8') , key: 'retirementDate', dataType: 'date', width: '120px', dateInputFormat: 'yyyy/MM/dd'
                    },
                    {
                        headerText: getText('JCM007_A221_9') , key: 'releaseDate', dataType: 'date', width: '120px', dateInputFormat: 'yyyy/MM/dd'
                    },
                    {
                        headerText: getText('JCM007_A221_10') , key: 'notificationCategory', dataType: 'string', width: '80px'
                    }
//                    ,
//                    {
//                        headerText: getText('JCM007_A221_11') , key: 'inputDate', dataType: 'date', width: '100px', dateInputFormat: 'yyyy/MM/dd'
//                    }
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
                        dataFiltered: function (evt, ui) {
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
            
            // validate
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
            
            if(command.naturalUnaReasons_1 == 1 && command.naturalUnaReasons_1Val == ''){
                command.naturalUnaReasons_1 = 0;
            }
            if(command.businessReduction_2 == 1 && command.businessReduction_2Val == ''){
                command.businessReduction_2 = 0;
            }
            if(command.seriousViolationsOrder_3 == 1 && command.seriousViolationsOrder_3Val == ''){
                command.seriousViolationsOrder_3 = 0;
            }
            if(command.unauthorizedConduct_4 == 1 && command.unauthorizedConduct_4Val == ''){
                command.unauthorizedConduct_4 = 0;
            }
            if(command.leaveConsiderableTime_5 == 1 && command.leaveConsiderableTime_5Val == ''){
                command.leaveConsiderableTime_5 = 0;
            }
            if(command.other_6 == 1 && command.other_6Val == ''){
                command.other_6 = 0;
            }

            if ((self.selectedTab() == 'tab-1') && (self.isNewMode) && (itemSelectedTab1 != null)) {
                // アルゴリズム[退職者情報の新規登録」を実行する (Thực hiện thuật toán [đăng ký mới thông tin người nghỉ hưu」)

                // アルゴリズム[事前チェック]を実行する (THực hiện thuật toán [Check trước ] )
                
                command.sId = itemSelectedTab1.employeeId;
                command.pId = itemSelectedTab1.personalId;
                command.scd = itemSelectedTab1.employeeCode;
                command.employeeName = itemSelectedTab1.businessName;
                self.preCheckAndRegisterNewEmp(command);
                
            } else if (self.selectedTab() == 'tab-2' && itemSelectedTab2 != null 
                       && itemSelectedTab2.notificationCategory == "" 
                       && itemSelectedTab2.status == self.status_Unregistered) {
                        // 3.届出承認済みの退職者を新規登録する 
                        //(Đăng ký mới người nghỉ hưu đã phê duyệt đơn/notification)
               
                command.historyId = itemSelectedTab2.historyId;
                command.sId = itemSelectedTab2.sid;
                command.pId = itemSelectedTab2.pid;
                command.scd = itemSelectedTab2.scd;
                command.employeeName = itemSelectedTab2.employeeName;
                command.status = 1;
                self.preCheckAndRegisterNewEmpApproved(command).done(() => {
                    self.enable_disableInput(true);
                });

            } else if (self.selectedTab() == 'tab-2' && itemSelectedTab2 != null 
                       && itemSelectedTab2.status != self.status_Unregistered) {
                       // 4.退職者情報を修正する(Sửa thông tin người nghỉ hưu)
                
                command.historyId = itemSelectedTab2.historyId;
                command.sId = itemSelectedTab2.sid;
                command.pId = itemSelectedTab2.pid;
                command.scd = itemSelectedTab2.scd;
                command.employeeName = itemSelectedTab2.employeeName;
                command.status = 2;
                self.preCheckAndUpdateEmp(command).done(() => {
                    self.enable_disableInput(false);
                });
            }
        }
        
        //2.退職者を新規登録する(Đăng ký mới người nghỉ hưu)
        preCheckAndRegisterNewEmp(command) {
            let self = this;
            let dfd = $.Deferred<any>();
            let empCurrent = ko.toJS(self.currentEmployee());
            // format lại date
            let releaseDate = new Date(empCurrent.releaseDate);
            let retirementDate = new Date(empCurrent.retirementDate);
            command.releaseDate = releaseDate.toISOString().substring(0, 10).replace("-", "/").replace("-", "/");
            command.retirementDate = retirementDate.toISOString().substring(0, 10).replace("-", "/").replace("-", "/");
            
            if (self.currentEmployee().dismissalNoticeDate()) {
                let dismissalNoticeDate = new Date(self.currentEmployee().dismissalNoticeDate());
                command.dismissalNoticeDate = dismissalNoticeDate.toISOString().substring(0, 10).replace("-", "/").replace("-", "/");
            }
            
            if (self.currentEmployee().dismissalNoticeDateAllow()){
                let dismissalNoticeDateAllow = new Date(self.currentEmployee().dismissalNoticeDateAllow());
                command.dismissalNoticeDateAllow = dismissalNoticeDateAllow.toISOString().substring(0, 10).replace("-", "/").replace("-", "/");
            }
            
            block.grayout();
            service.preCheck(command).done(() => {
                block.clear();
                console.log('PRECHECK DONE!!');
                // アルゴリズム[警告チェック]を実行する(Thực hiện thuật toán [Warning check] )
                if (empCurrent.selectedCode_Retiment == 3) {
                    // アルゴリズム[警告チェック]を実行する(Thực hiện thuật toán [Warning check] )  
                    let retirementDate = moment.utc(self.currentEmployee().retirementDate(), DateFormat.DEFAULT_FORMAT);
                    let dismissalNoticeDate = moment.utc(self.currentEmployee().dismissalNoticeDate(), DateFormat.DEFAULT_FORMAT);
                    
                    let dayDifference = retirementDate.diff(dismissalNoticeDate, 'days');
                    if (empCurrent.dismissalNoticeDateAllow) {
                        if (dayDifference >= 30) {
                            nts.uk.ui.dialog.confirm({ messageId: "MsgJ_JCM007_9" }).ifYes(() => {
                                this.addRetireeInformation(command).done(() => {
                                    dfd.resolve();
                                });
                            }).ifNo(() => { });
                        } else {
                            this.addRetireeInformation(command).done(() => {
                                dfd.resolve();
                            });
                        }
                    } else {
                        if (dayDifference < 30) {
                            nts.uk.ui.dialog.confirm({ messageId: "MsgJ_JCM007_8" }).ifYes(() => {
                                this.addRetireeInformation(command).done(() => {
                                    dfd.resolve();
                                });
                            }).ifNo(() => { });
                        } else {
                            this.addRetireeInformation(command).done(() => {
                                dfd.resolve();
                            });
                        }
                    }
                } else {
                    this.addRetireeInformation(command).done(() => {
                        dfd.resolve();    
                    });
                }
            }).fail((mes) => {
                self.enable_disableInput(true);
                block.clear();
                nts.uk.ui.dialog.bundledErrors(mes);
                dfd.reject();
            });
            return dfd.promise();
        }
        
        // 3.届出承認済みの退職者を新規登録する (Đăng ký mới người nghỉ hưu đã phê duyệt đơn/notification)
        preCheckAndRegisterNewEmpApproved(command : any) {
            let self = this;
            let dfd = $.Deferred<any>();
            let empCurrent = ko.toJS(self.currentEmployee());
            // format lại date
            let releaseDate = new Date(empCurrent.releaseDate);
            let retirementDate = new Date(empCurrent.retirementDate);
            command.releaseDate = releaseDate.toISOString().substring(0, 10).replace("-", "/").replace("-", "/");
            command.retirementDate = retirementDate.toISOString().substring(0, 10).replace("-", "/").replace("-", "/");
            
            if (self.currentEmployee().dismissalNoticeDate()) {
                let dismissalNoticeDate = new Date(self.currentEmployee().dismissalNoticeDate());
                command.dismissalNoticeDate = dismissalNoticeDate.toISOString().substring(0, 10).replace("-", "/").replace("-", "/");
            }
            
            if (self.currentEmployee().dismissalNoticeDateAllow()){
                let dismissalNoticeDateAllow = new Date(self.currentEmployee().dismissalNoticeDateAllow());
                command.dismissalNoticeDateAllow = dismissalNoticeDateAllow.toISOString().substring(0, 10).replace("-", "/").replace("-", "/");
            }
            
            block.grayout();
            service.preCheck(command).done(() => {
                block.clear();
                console.log('PRECHECK DONE!!');
                // アルゴリズム[警告チェック]を実行する(Thực hiện thuật toán [Warning check] )
                if (empCurrent.selectedCode_Retiment == 3) {
                    // アルゴリズム[警告チェック]を実行する(Thực hiện thuật toán [Warning check] )  
                    let retirementDate = moment.utc(self.currentEmployee().retirementDate(), DateFormat.DEFAULT_FORMAT);
                    let dismissalNoticeDate = moment.utc(self.currentEmployee().dismissalNoticeDate(), DateFormat.DEFAULT_FORMAT);
                    
                    let dayDifference = retirementDate.diff(dismissalNoticeDate, 'days');
                    if (empCurrent.dismissalNoticeDateAllow) {
                        if (dayDifference >= 30) {
                            nts.uk.ui.dialog.confirm({ messageId: "MsgJ_JCM007_9" }).ifYes(() => {
                                this.registerNewEmpApproved(command).done(() => {
                                    dfd.resolve();
                                });
                            }).ifNo(() => { });
                        } else {
                            this.registerNewEmpApproved(command).done(() => {
                                dfd.resolve();
                            });
                        }
                    } else {
                        if (dayDifference < 30) {
                            nts.uk.ui.dialog.confirm({ messageId: "MsgJ_JCM007_8" }).ifYes(() => {
                                this.registerNewEmpApproved(command).done(() => {
                                    dfd.resolve();
                                });
                            }).ifNo(() => { });
                        } else {
                            this.registerNewEmpApproved(command).done(() => {
                                dfd.resolve();
                            });
                        }
                    }
                } else {
                    this.registerNewEmpApproved(command).done(() => {
                        dfd.resolve();    
                    });
                }
            }).fail((mes) => {
                self.enable_disableInput(true);
                block.clear();
                nts.uk.ui.dialog.bundledErrors(mes);
                dfd.reject();
            });
            return dfd.promise();
        
        }
        
        registerNewEmpApproved(command : any){
             let self = this;
            let dfd = $.Deferred<any>();
            block.grayout();
            service.updateRetireeInformation(command).done(() => {
                console.log('UPDATE DONE!!');
                self.start(command.historyId).done(() => {
                    self.enable_btnRegister(true);
                    self.enable_btnRemove(true);
                    
                });
                block.clear();
                dialog.info({ messageId: "Msg_15" });
                dfd.resolve();
            }).fail((mes) => {
                console.log('UPDATE FAIL!!');
                block.clear();
                dfd.reject();
            });
            return dfd.promise();
        }
        
        // 4.退職者情報を修正する(Sửa thông tin người nghỉ hưu)
        preCheckAndUpdateEmp(command : any) : JQueryPromise<any>{
            let self = this;
            let dfd = $.Deferred<any>();
            let empCurrent = ko.toJS(self.currentEmployee());
            // format lại date
            let releaseDate = new Date(empCurrent.releaseDate);
            let retirementDate = new Date(empCurrent.retirementDate);
            command.releaseDate = releaseDate.toISOString().substring(0, 10).replace("-", "/").replace("-", "/");
            command.retirementDate = retirementDate.toISOString().substring(0, 10).replace("-", "/").replace("-", "/");
            
            if (self.currentEmployee().dismissalNoticeDate()) {
                let dismissalNoticeDate = new Date(self.currentEmployee().dismissalNoticeDate());
                command.dismissalNoticeDate = dismissalNoticeDate.toISOString().substring(0, 10).replace("-", "/").replace("-", "/");
            }
            
            if (self.currentEmployee().dismissalNoticeDateAllow()){
                let dismissalNoticeDateAllow = new Date(self.currentEmployee().dismissalNoticeDateAllow());
                command.dismissalNoticeDateAllow = dismissalNoticeDateAllow.toISOString().substring(0, 10).replace("-", "/").replace("-", "/");
            }
            
            block.grayout();
            service.preCheck(command).done(() => {
                block.clear();
                console.log('PRECHECK DONE!!');
                // アルゴリズム[警告チェック]を実行する(Thực hiện thuật toán [Warning check] )
                if (empCurrent.selectedCode_Retiment == 3) {
                    // アルゴリズム[警告チェック]を実行する(Thực hiện thuật toán [Warning check] )  
                    let retirementDate = moment.utc(self.currentEmployee().retirementDate(), DateFormat.DEFAULT_FORMAT);
                    let dismissalNoticeDate = moment.utc(self.currentEmployee().dismissalNoticeDate(), DateFormat.DEFAULT_FORMAT);
                    
                    let dayDifference = retirementDate.diff(dismissalNoticeDate, 'days');
                    if (empCurrent.dismissalNoticeDateAllow) {
                        if (dayDifference >= 30) {
                            nts.uk.ui.dialog.confirm({ messageId: "MsgJ_JCM007_9" }).ifYes(() => {
                                this.updateRetireeInformation(command).done(() => {
                                    dfd.resolve();
                                });
                            }).ifNo(() => { });
                        } else {
                            this.updateRetireeInformation(command).done(() => {
                                dfd.resolve();
                            });
                        }
                    } else {
                        if (dayDifference < 30) {
                            nts.uk.ui.dialog.confirm({ messageId: "MsgJ_JCM007_8" }).ifYes(() => {
                                this.updateRetireeInformation(command).done(() => {
                                    dfd.resolve();
                                });
                            }).ifNo(() => { });
                        } else {
                            this.updateRetireeInformation(command).done(() => {
                                dfd.resolve();
                            });
                        }
                    }
                } else {
                    this.updateRetireeInformation(command).done(() => {
                        dfd.resolve();    
                    });
                }
            }).fail((mes) => {
                self.enable_disableInput(true);
                block.clear();
                nts.uk.ui.dialog.bundledErrors(mes);
                dfd.reject();
            });
            return dfd.promise();
        }
        
        updateRetireeInformation(command : any) : JQueryPromise<any>{
            let self = this;
            let dfd = $.Deferred<any>();
            block.grayout();
            service.updateRetireeInformation(command).done(() => {
                console.log('UPDATE DONE!!');
                self.start(command.historyId).done(() => {
                    self.enable_btnRegister(false);
                    self.enable_btnRemove(false);
                    
                });
                block.clear();
                dialog.info({ messageId: "Msg_15" });
                dfd.resolve();
            }).fail((mes) => {
                console.log('UPDATE FAIL!!');
                block.clear();
                dfd.reject();
            });
            return dfd.promise();
        }
        
        // 2.アルゴリズム[退職者情報の追加]を実行する (Thực hiện thuật toán [Thêm thông tin người nghỉ hưu])
        addRetireeInformation(command : any) {
            let self = this;
            let dfd = $.Deferred<any>();
            block.grayout();
            service.addRetireeInformation(command).done(() => {
                console.log('REGISTER DONE!!');
                self.newMode();
                self.start(null);
                block.clear();
                dialog.info({ messageId: "Msg_15" });
                dfd.resolve();
            }).fail((mes) => {
                console.log('REGISTER FAIL!!');
                block.clear();
                dfd.reject();
            });
            return dfd.promise();
        }

        remove() {
            let self = this;
            
            if (nts.uk.ui.errors.hasError()) {
                return;
            }

            let itemSelectedTab2 = self.itemSelectedTab2();
            let dataSource = $('#gridListEmployeesJcm007').igGrid('option','dataSource');

            if (self.selectedTab() == 'tab-2' && itemSelectedTab2 != null
                && itemSelectedTab2.status != self.status_Unregistered) {
                let histId = itemSelectedTab2.historyId;
                let lengthListItemTab2 = self.employeeListTab2.length;
                let indexItemDelete = _.findIndex(ko.toJS(dataSource), function(item: any) { return item.historyId == histId; });
                
                confirm({ messageId: "Msg_18" }).ifYes(() => {
                    block.grayout();
                    service.remove(histId).done(() => {
                        dialog.info({ messageId: "Msg_16" });
                        console.log('REMOVE DONE!!');
                        if (lengthListItemTab2 === 1) {
                            self.isNewMode = true;
                            self.itemSelectedTab2(null);
                            self.start(null);
                        } else if (lengthListItemTab2 - 1 == indexItemDelete) {
                            let itemSelectedAfterRemove  = dataSource[indexItemDelete - 1];
                            self.getListData(itemSelectedAfterRemove.historyId, true);
                        } else {
                            let itemSelectedAfterRemove = dataSource[indexItemDelete + 1];
                            self.getListData(itemSelectedAfterRemove.historyId, true);
                        }
                        
                        block.clear();
                        
                        
                        
                    }).fail((mes) => {
                        console.log('REMOVE FAIL!!');
                        block.clear();
                    });
                }).ifNo(() => {

                });
            }
        }

        setDataHeader(data){
            let self = this;
            let departmentCode = data.departmentCode == null ? '' : data.departmentCode;
            let departmentName = data.departmentName == null ? '' : data.departmentName;
            let positionName   = data.positionName == null ? '' : data.positionName;
            let employmentName  = data.employmentName == null ? '' : data.employmentName;
                
            self.currentEmployee().avatarPerson( data.avartaFileId ? liveView(data.avartaFileId) : 'images/avatar.png');
            self.currentEmployee().codeNameEmp( data.employeeCode + ' ' + data.businessName);
            self.currentEmployee().department( departmentCode + ' ' + departmentName );
            self.currentEmployee().position( positionName );
            self.currentEmployee().employmentCls( employmentName);
            self.currentEmployee().sid = data.employeeId;
        }
        
        setDataDetail(dataDetail : any){
            let self = this;
            self.currentEmployee().retirementDate(dataDetail.retirementDate);
            self.currentEmployee().releaseDate(dataDetail.releaseDate);
            
            self.currentEmployee().selectedCode_Retiment(dataDetail.retirementCategory);
            self.currentEmployee().selectedCode_Reason1(dataDetail.retirementReasonCtg1);
            self.currentEmployee().selectedCode_Reason2(dataDetail.retirementReasonCtg2);
            self.currentEmployee().retirementRemarks(dataDetail.retirementRemarks == null ? '' : dataDetail.retirementRemarks);
            // ----------------- //
            self.currentEmployee().retirementReasonVal(dataDetail.retirementReasonVal == null ? '' :  dataDetail.retirementReasonVal);
            // ----------------- //
            self.currentEmployee().dismissalNoticeDate(dataDetail.dismissalNoticeDate == null ? '' : dataDetail.dismissalNoticeDate);
            self.currentEmployee().dismissalNoticeDateAllow(dataDetail.dismissalNoticeDateAllow == null ? '' : dataDetail.dismissalNoticeDateAllow);
            self.currentEmployee().reaAndProForDis(dataDetail.reaAndProForDis == null ? '' :  dataDetail.reaAndProForDis);
            
            self.currentEmployee().naturalUnaReasons_1(dataDetail.naturalUnaReasons_1 == 0 ? false : true);
            self.currentEmployee().naturalUnaReasons_1Val(dataDetail.naturalUnaReasons_1Val == null ? '' :  dataDetail.naturalUnaReasons_1Val);
            self.currentEmployee().businessReduction_2(dataDetail.naturalUnaReasons_2 == 0 ? false : true);
            self.currentEmployee().businessReduction_2Val(dataDetail.naturalUnaReasons_2Val == null ? '' :  dataDetail.naturalUnaReasons_2Val);
            self.currentEmployee().seriousViolationsOrder_3(dataDetail.naturalUnaReasons_3 == 0 ? false : true);
            self.currentEmployee().seriousViolationsOrder_3Val(dataDetail.naturalUnaReasons_3Val == null ? '' :  dataDetail.naturalUnaReasons_3Val);
            self.currentEmployee().unauthorizedConduct_4(dataDetail.naturalUnaReasons_4 == 0 ? false : true);
            self.currentEmployee().unauthorizedConduct_4Val(dataDetail.naturalUnaReasons_4Val == null ? '' :  dataDetail.naturalUnaReasons_4Val);
            self.currentEmployee().leaveConsiderableTime_5(dataDetail.naturalUnaReasons_5 == 0 ? false : true);
            self.currentEmployee().leaveConsiderableTime_5Val(dataDetail.naturalUnaReasons_5Val == null ? '' :  dataDetail.naturalUnaReasons_5Val);
            self.currentEmployee().other_6(dataDetail.naturalUnaReasons_6 == 0 ? false : true);
            self.currentEmployee().other_6Val(dataDetail.naturalUnaReasons_6Val == null ? '' :  dataDetail.naturalUnaReasons_6Val);
        }

        initHeaderInfo() {
            let self = this;
            self.enable_btnRemove(false);
            self.currentEmployee().avatarPerson('images/avatar.png');
            self.currentEmployee().codeNameEmp('');
            self.currentEmployee().department('');
            self.currentEmployee().position('');
            self.currentEmployee().employmentCls('');
            // set avatar blank
            
        }
        
        /** new mode */
        newMode() {
            let self = this;
            self.isNewMode = true;
            self.enable_btnRemove(false);
            self.initHeaderInfo();
            self.initRetirementInfo();
            self.itemSelectedTab1(null);
            $("#gridListEmployees").igGridSelection("clearSelection");
            $("#gridListEmployeesJcm007").igGridSelection("clearSelection");
            self.itemSelectedTab2(null);
            self.selectedTab('tab-1');
            nts.uk.ui.errors.clearAll();
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
        
        enable_disableInput(param : boolean) {
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
            }else if (self.currentEmployee().naturalUnaReasons_1() == true){
                    self.currentEmployee().naturalUnaReasons_enable(true);
            }
            
            self.currentEmployee().enable_businessReduction2(param);
            if ((self.currentEmployee().businessReduction_2() == false) || 
                (self.currentEmployee().businessReduction_2() == true && self.currentEmployee().enable_businessReduction2() == false)) {
                    self.currentEmployee().businessReduction_enable(false);
            }else if (self.currentEmployee().businessReduction_2() == true && self.currentEmployee().enable_businessReduction2() == true){
                    self.currentEmployee().businessReduction_enable(true);
            }
            
            self.currentEmployee().enable_seriousViolationsOrder3(param);
            if ((self.currentEmployee().seriousViolationsOrder_3() == false) || 
                (self.currentEmployee().seriousViolationsOrder_3() == true && self.currentEmployee().enable_seriousViolationsOrder3() == false)) {
                    self.currentEmployee().seriousViolationsOrder_enable(false);
            }else if (self.currentEmployee().seriousViolationsOrder_3() == true && self.currentEmployee().enable_seriousViolationsOrder3() == true){
                    self.currentEmployee().seriousViolationsOrder_enable(true);
            }
            
            self.currentEmployee().enable_unauthorizedConduct4(param);
            if ((self.currentEmployee().unauthorizedConduct_4() == false) || 
                (self.currentEmployee().unauthorizedConduct_4() == true && self.currentEmployee().enable_unauthorizedConduct4() == false)) {
                    self.currentEmployee().unauthorizedConduct_enable(false);
            }else if (self.currentEmployee().unauthorizedConduct_4() == true && self.currentEmployee().enable_unauthorizedConduct4() == true){
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
        
        avatarPerson : KnockoutObservable<string> = ko.observable('images/avatar.png');
        codeNameEmp: KnockoutObservable<string> = ko.observable('');   // A222_3
        department: KnockoutObservable<string> = ko.observable('');   // A222_5
        position: KnockoutObservable<string> = ko.observable('');   // A222_7
        employmentCls: KnockoutObservable<string> = ko.observable('');   // A222_9

        hisId: string;
        cid: string;
        sid: string;
        scd: string;
        pid:string;
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
        enable_retirementDate : KnockoutObservable<boolean> = ko.observable(true);
        enable_releaseDate :  KnockoutObservable<boolean> = ko.observable(true);
        enable_listRetirementCtg :  KnockoutObservable<boolean> = ko.observable(true);
        enable_listRetirementReason1 :  KnockoutObservable<boolean> = ko.observable(true);
        enable_listRetirementReason2 :  KnockoutObservable<boolean> = ko.observable(true);
        enable_retirementRemarks :  KnockoutObservable<boolean> = ko.observable(true);
        enable_retirementReasonVal :  KnockoutObservable<boolean> = ko.observable(true);
        enable_dismissalNoticeDate :  KnockoutObservable<boolean> = ko.observable(true);
        enable_dismissalNoticeDateAllow :  KnockoutObservable<boolean> = ko.observable(true);
        enable_reaAndProForDis :  KnockoutObservable<boolean> = ko.observable(true);
        enable_naturalUnaReasons1 :  KnockoutObservable<boolean> = ko.observable(true);
        enable_businessReduction2 :  KnockoutObservable<boolean> = ko.observable(true);
        enable_seriousViolationsOrder3 :  KnockoutObservable<boolean> = ko.observable(true);
        enable_unauthorizedConduct4 :  KnockoutObservable<boolean> = ko.observable(true);
        enable_leaveConsiderableTime5 :  KnockoutObservable<boolean> = ko.observable(true);
        enable_other6 :  KnockoutObservable<boolean> = ko.observable(true);

        constructor(param: IEmployee) {
            let self = this;
            
            self.hisId = param.hisId;
            self.cid = param.cid;
            self.sid = param.sid;
            self.scd = param.scd;
            self.pid = param.pid;
            self.bussinessName = param.bussinessName;
            
            self.pendingFlag = param.pendingFlag;
            self.status = param.status;
            self.notificationCategory = param.notificationCategory;
            self.inputDate = param.inputDate;
            
            self.retirementDate(param.retirementDate || '');
            self.releaseDate(param.releaseDate || '');
            self.listRetirementCtg = ko.observable([
                { value: 1, text: '退職' },
                { value: 2, text: '転籍' },
                { value: 3, text: '解雇' },
                { value: 4, text: '定年' }
            ]);
            self.selectedCode_Retiment(param.selectedCode_Retiment || 1);

            self.listRetirementReason1 = ko.observable([
                { value: 1, text: '自己都合による退職' },
                { value: 2, text: '定年による退職' },
                { value: 3, text: '会社都合による解雇' }
            ]);
            self.selectedCode_Reason1(param.selectedCode_Reason1 || null);

            self.listRetirementReason2 = ko.observable([
                { value: 1, text: '結婚' },
                { value: 2, text: '上司と合わない' },
                { value: 3, text: 'やる気がなくなった' },
                { value: 4, text: '会社の業績不振' },
                { value: 5, text: 'その他' }
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

            //xử lý subscribe
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
                    
                    if(self.naturalUnaReasons_1() == false){
                        self.naturalUnaReasons_1Val('');
                        self.naturalUnaReasons_enable(false);
                    }
                    if(self.businessReduction_2() == false){
                        self.businessReduction_2Val('');
                        self.businessReduction_enable(false);
                    }
                    if(self.seriousViolationsOrder_3() == false){
                        self.seriousViolationsOrder_3Val('');
                        self.seriousViolationsOrder_enable(false);
                    }
                    if(self.unauthorizedConduct_4() == false){
                        self.unauthorizedConduct_4Val('');
                        self.unauthorizedConduct_enable(false);
                    }
                    if(self.leaveConsiderableTime_5() == false){
                        self.leaveConsiderableTime_5Val('');
                        self.leaveConsiderableTime_enable(false);
                    }
                    if(self.other_6() == false){
                        self.other_6Val('');
                        self.other_enable(false);
                    }
                }
            });
            
            self.naturalUnaReasons_1.subscribe((value) => {
                let self = this;
                if (value == true && self.enable_naturalUnaReasons1() == false){
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
                if (value == true && self.enable_businessReduction2() == false){
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
                if (value == true && self.enable_seriousViolationsOrder3() == false){
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
                if (value == true && self.enable_unauthorizedConduct4() == false){
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
                if (value == true && self.enable_leaveConsiderableTime5() == false){
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
                if (value == true && self.enable_other6() == false){
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
        avartaFileId:  string;
        businessName:  string;
        businessNameKana:  string;
        departmentCode:  string;
        departmentName:  string;
        employeeCode:  string;
        employeeId:  string;
        employmentCode:  string;
        employmentName:  string;
        mapFileId:  string;
        personalId:  string;
        positionCode:  string;
        positionId:  string;
        positionName:  string;
        workplaceCode:  string;
        workplaceId:  string;
        workplaceName:  string;   
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
            this.avartaFileId =
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
        systemType: number; //システム区分（0：共通、1：就業、2：給与、3：人事）
        includePreEmployee: boolean; //入社前社員を含める
        includeRetirement: boolean; //退職者を含める
        includeAbsence: boolean; //休職者を含める 
        includeClosed: boolean; //休業者を含める
        includeTransferEmployee: boolean; //出向社員を含める
        includeAcceptanceTransferEmployee: boolean; //受入出向社員を含める
        getPosition: boolean; //職位を取得する
        getEmployment: boolean; //雇用を取得する
        getPersonalFileManagement: boolean; //個人ファイル管理を取得する
        
        constructor(input: any) {
            this.systemType = input ? input.systemType || 3 : 3;
            this.includePreEmployee = input ? input.includePreEmployee || false: false;
            this.includeRetirement = input ? input.includeRetirement || false: false;
            this.includeAbsence = input ? input.includeAbsence || true: true;
            this.includeClosed = input ? input.includeClosed || true: true;
            this.includeTransferEmployee = input ? input.includeTransferEmployee || true: true;
            this.includeAcceptanceTransferEmployee = input ? input.includeAcceptanceTransferEmployee || false: false;
            this.getPosition = input ? input.getPosition || true: true;
            this.getEmployment = input ? input.getEmployment || true: true;
            this.getPersonalFileManagement = input ? input.getPersonalFileManagement || true: true;
        }
    }

    export class DateFormat {
        static DEFAULT_FORMAT = 'YYYY/MM/DD';
    }

}