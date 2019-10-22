module jcm007.a {
    import ajax = nts.uk.request.ajax;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import liveView = nts.uk.request.liveView;
    import getText = nts.uk.resource.getText;
    var block = nts.uk.ui.block;
    
    
    export class ViewModel {

        currentEmployee: KnockoutObservable<EmployeeModel>;
        
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;
        enable_btnRemove: KnockoutObservable<boolean>;

        // tab 2
        employeeListTab2 : [];
        itemSelected : KnockoutObservable<any>;
        empInfoHeaderList : KnockoutObservableArray<IEmpInfoHeader>;
        
        // ccg029
        input: Input;
        
        isNewMode : boolean;
        
        constructor() {
            let self = this;
            self.avatarPerson = ko.observable('images/avatar.svg');
            
            self.tabs = ko.observableArray([
                { id: 'tab-1', title: getText('JCM007_A221_1_1') , content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: getText('JCM007_A221_1_3') , content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) }
            ]);

            self.employeeListTab2 = [];
            this.bindData();
            
            self.selectedTab = ko.observable('tab-1');

            self.currentEmployee = ko.observable(new EmployeeModel(''));
            self.enable_btnRemove = ko.observable(false);

            self.itemSelected = ko.observable(null);
            self.empInfoHeaderList = ko.observableArray([]);

            self.input = new Input(undefined);
            self.isNewMode = false;
            
            self.selectedTab.subscribe((value) => {
                let self = this;
                if (value == 'tab-2') {
                    self.enable_btnRemove(true);
                    this.getListData();
                    
                }else{
                    self.enable_btnRemove(false);    
                }
            });

            self.itemSelected.subscribe((value) => {
                let self = this;
                console.log(value);
                if (value.notificationCategory == 1 && value.status == 0) {
                    // アルゴリズム[退職者情報の表示」を実行する] (Thực hiện thuật toán [Hiển thị thông tin người nghỉ hưu」)

                } else if (value.status != 0) {

                }
            });
        }
        
        // select emp ben tab-1
        public seletedEmployee(data): void {
            console.log(data);
            let self = this;
            if (self.isNewMode) {

                // アルゴリズム[登録状況チェック]を実行する(Thực hiện thuật toán "Check tình trạng đăng ký ")
                block.grayout();
                service.CheckStatusRegistration(data.employeeId).done(() => {
                    console.log('CheckStatusRegistration DONE');
                }).fail((error) => {
                    console.log('CheckStatusRegistration FAIL');
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
            self.currentEmployee().sid = data.employeeId;
            self.currentEmployee().scd = data.employeeCode;
            self.currentEmployee().pid = data.personalId;
            self.currentEmployee().bussinessName = data.businessName;
        }

        /** start page */
        start() {
            let self = this;
            let dfd = $.Deferred<any>();
            
            self.getListData();

            dfd.resolve();
            return dfd.promise();
        }
        
        getListData() {
            let self = this;
            
            block.grayout();
            
            service.getData().done((data1) => {
                
                // goi service アルゴリズム[社員情報リストを取得]を実行する
                // (Thực hiện thuật toán [Get list thông tin nhân viên]) CCG029
                if(data1.length != 0){
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
                        
                        self.enable_btnRemove(true);
                        
                        self.employeeListTab2 = data1;
                        
                        $("#gridListEmployeesJcm007").igGrid('option','dataSource',self.employeeListTab2);
                        
                        $("#gridListEmployeesJcm007").igGridSelection("selectRow", 0);
                        
                        self.itemSelected(self.employeeListTab2[0]);
                        block.clear();

                    }).fail((error) => {
                        nts.uk.ui.dialog.info(error);
                    }).always(() => {
                        block.clear();
                    });
                } else {
                    self.initHeaderInfo();
                    self.initRetirementInfo();
                }
                
            }).fail((error) => {
                console.log('Get Data Tab-2 Fail');
                nts.uk.ui.dialog.info(error);
            }).always(() => {
                block.clear();
            }); 
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
                        headerText: getText('JCM007_A221_5') , key: 'status', dataType: 'string', width: '100px'
                    },
                    {
                        headerText: getText('JCM007_A221_6') , key: 'scd', dataType: 'string', width: '100px'
                    },
                    {
                        headerText: getText('JCM007_A221_7') , key: 'employeeName', dataType: 'string', width: '100px'
                    },
                    {
                        headerText: getText('JCM007_A221_8') , key: 'retirementDate', dataType: 'date', width: '100px'
                    },
                    {
                        headerText: getText('JCM007_A221_9') , key: 'releaseDate', dataType: 'date', width: '100px'
                    },
                    {
                        headerText: getText('JCM007_A221_10') , key: 'companyCode', dataType: 'string', width: '100px'
                    },
                    {
                        headerText: getText('JCM007_A221_11') , key: 'inputDate', dataType: 'date', width: '100px'
                    }
                ],
                dataSource: self.employeeListTab2,
                dataSourceType: 'json',
                responseDataKey: 'results',
                height: '390px',
                width: '700px',
                tabIndex: 17,
                features: [
                    {
                        name: "Selection",
                        mode: "row",
                        multipleSelection: false,
                        rowSelectionChanged: function(evt, ui) {
                            debugger;
                            let itemSelected = _.find(self.employeeListTab2, function(o) { return o.historyId == ui.row.id; });
                            self.itemSelected(itemSelected);
                            if (!itemSelected) {
                                 self.itemSelected(self.employeeListTab2[0]);
                            }
                        }
                    },
                    {
                        name: 'Filtering',
                        type: 'local',
                        mode: 'simple'
                    },
                    {
                        name: 'Sorting',
                        type: 'local'
                    },
                    {
                        name: 'Resizing'
                    }
                ]
            });
        }
        
        /** event when click register */
        register() {
            let self = this;
            console.log(self.selectedTab());
            if ((self.selectedTab() == 'tab-1') && (self.isNewMode)) {
                // アルゴリズム[退職者情報の新規登録」を実行する (Thực hiện thuật toán [đăng ký mới thông tin người nghỉ hưu」)
                let emp = ko.toJS(self.currentEmployee());
                let command = {
                        historyId : '',  
                        histId_Refer : '',  
                        contractCode : '',  
                        companyId : '',  
                        companyCode : '',  
                        workId : '',  
                        workName : '',  
                        sId : emp.sid , 
                        pId : emp.pid ,
                        scd : emp.scd ,
                        employeeName : emp.bussinessName ,
                        
                        notificationCategory: 0,
                        pendingFlag:  0 ,
                        status: 1 ,
                        
                        retirementDate : emp.retirementDate ,
                        releaseDate : emp.releaseDate ,
                        retirementType : emp.selectedCode_Retiment , 
                        selectedCode_Reason1 : emp.selectedCode_Reason1 ,
                        selectedCode_Reason2 : emp.selectedCode_Reason2 ,
                        retirementRemarks : emp.retirementRemarks ,
                        
                        retirementReasonVal : emp.retirementReasonVal ,
                        
                        dismissalNoticeDate : emp.dismissalNoticeDate ,
                        dismissalNoticeDateAllow : emp.dismissalNoticeDateAllow ,
                        reaAndProForDis : emp.reaAndProForDis ,
                        naturalUnaReasons_1 : emp.naturalUnaReasons_1 == false ? 0 : 1 ,
                        naturalUnaReasons_1Val : emp.naturalUnaReasons_1Val ,
                        businessReduction_2 : emp.businessReduction_2 == false ? 0 : 1,
                        businessReduction_2Val : emp.businessReduction_2Val ,
                        seriousViolationsOrder_3 : emp.seriousViolationsOrder_3 == false ? 0 : 1,
                        seriousViolationsOrder_3Val : emp.seriousViolationsOrder_3Val ,
                        unauthorizedConduct_4 : emp.unauthorizedConduct_4 == false ? 0 : 1,
                        unauthorizedConduct_4Val : emp.unauthorizedConduct_4Val ,
                        leaveConsiderableTime_5 : emp.leaveConsiderableTime_5 == false ? 0 : 1,
                        leaveConsiderableTime_5Val : emp.leaveConsiderableTime_5Val ,
                        other_6 : emp.other_6 == false ? 0 : 1,
                        other_6Val : emp.other_6Val }

                // アルゴリズム[事前チェック]を実行する (THực hiện thuật toán [Check trước ] )
                service.preCheck(command).done(() => {
                    console.log('PRECHECK DONE!!');
                    // アルゴリズム[警告チェック]を実行する(Thực hiện thuật toán [Warning check] )
                    if (command.selectedCode_Retiment == 3) {
                        // アルゴリズム[警告チェック]を実行する(Thực hiện thuật toán [Warning check] )  
                        let retirementDate = moment.utc(self.currentEmployee().retirementDate(), DateFormat.DEFAULT_FORMAT);
                        let dismissalNoticeDate = moment.utc(self.currentEmployee().dismissalNoticeDate(), DateFormat.DEFAULT_FORMAT);
                        let dayDifference = retirementDate.diff(dismissalNoticeDate, 'days');
                        if (self.currentEmployee().dismissalNoticeDateAllow()) {
                            if (dayDifference > 30) {
                                nts.uk.ui.dialog.confirm({ messageId: "MsgJ_JCM007_9" }).ifYes(() => {
                                    this.addRetireeInformation(command);
                                }).ifNo(() => {
                                    unblock();
                                });
                            }
                        } else {
                            if (dayDifference < 30) {
                                nts.uk.ui.dialog.confirm({ messageId: "MsgJ_JCM007_8" }).ifYes(() => {
                                    this.addRetireeInformation(command);
                                }).ifNo(() => {
                                    unblock();
                                });
                            }

                        }
                    } else {
                        this.addRetireeInformation(command);
                    }
                }).fail((mes) => {
                    nts.uk.ui.dialog.bundledErrors(mes);
                });
                
            } else if (self.selectedTab() == 'tab-2') {
                // 3.届出承認済みの退職者を新規登録する 
                //(Đăng ký mới người nghỉ hưu đã phê duyệt đơn/notification)

            }
        }
        
        // アルゴリズム[退職者情報の追加]を実行する (Thực hiện thuật toán [Thêm thông tin người nghỉ hưu])
        addRetireeInformation(command : any){
            let self = this;
            let dfd = $.Deferred<any>();
            service.addRetireeInformation(command).done(() => {
                self.start();
                dfd.resolve();
            }).fail((mes) => {
                dfd.reject();
            });

            return dfd.promise();
        }

        
        remove() {

        }

        /** new mode */
        newMode() {
            let self = this;
            self.isNewMode = true;
            self.initHeaderInfo();
            self.initRetirementInfo();
            self.clearSelection();
        }
        
        setDataHeader(data){
            let self = this;
            self.currentEmployee().avatarPerson(data.avartaFileId ? liveView(data.avartaFileId) : liveView('5af04a80-2c77-4945-bf87-fc0ca4bbf930'));
            self.currentEmployee().codeNameEmp(data.employeeCode + ' ' + data.businessName);
            self.currentEmployee().department(data.departmentCode + ' ' + data.departmentName);
            self.currentEmployee().position(data.positionCode + ' ' + data.positionName);
            self.currentEmployee().employmentCls(data.workplaceCode + ' ' + data.workplaceName);
        }

        initHeaderInfo() {
            let self = this;
            self.enable_btnRemove(false);
            self.currentEmployee().avatarPerson('images/avatar.svg');
            self.currentEmployee().codeNameEmp('');
            self.currentEmployee().department('');
            self.currentEmployee().position('');
            self.currentEmployee().employmentCls('');
            // set avatar blank
            
        }
        
        initRetirementInfo() {
            let self = this;
            self.currentEmployee().retirementDate('');
            self.currentEmployee().releaseDate('');
            self.currentEmployee().selectedCode_Retiment(1);
            self.currentEmployee().selectedCode_Reason1(0);
            self.currentEmployee().selectedCode_Reason2(0);
            self.currentEmployee().retirementRemarks('');
            // ----------------- //
            self.currentEmployee().retirementReasonVal('');
            // ----------------- //
            self.currentEmployee().dismissalNoticeDate('');
            self.currentEmployee().dismissalNoticeDateAllow('');
            self.currentEmployee().reaAndProForDis('');
            self.currentEmployee().naturalUnaReasons_1(false);
            self.currentEmployee().naturalUnaReasons_1Val('');
            self.currentEmployee().businessReduction_2(false);
            self.currentEmployee().businessReduction_2Val('');
            self.currentEmployee().seriousViolationsOrder_3(false);
            self.currentEmployee().seriousViolationsOrder_3Val('');
            self.currentEmployee().unauthorizedConduct_4(false);
            self.currentEmployee().unauthorizedConduct_4Val('');
            self.currentEmployee().leaveConsiderableTime_5(false);
            self.currentEmployee().leaveConsiderableTime_5Val('');
            self.currentEmployee().other_6(false);
            self.currentEmployee().other_6Val('');
        }

        clearSelection() {
            $("#gridListEmployees").igGridSelection("clearSelection");
        }

    }
    class EmployeeModel {
        
        avatarPerson : KnockoutObservable<string> = ko.observable('images/avatar.svg');
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
        status: number;
        notificationCategory: number;
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
                { value: 1, text: '' },
                { value: 2, text: '自己都合による退職' },
                { value: 3, text: '定年による退職' },
                { value: 4, text: '会社都合による解雇' }
            ]);
            self.selectedCode_Reason1(param.selectedCode_Reason1 || 1);

            self.listRetirementReason2 = ko.observable([
                { value: 1, text: '' },
                { value: 2, text: '結婚' },
                { value: 3, text: '上司と合わない' },
                { value: 4, text: 'やる気がなくなった' },
                { value: 5, text: '会社の業績不振' },
                { value: 6, text: 'その他' }
            ]);
            self.selectedCode_Reason2(param.selectedCode_Reason2 || 1);

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
                if (value == 1 || value == 2 || value == 4) {
                    self.visible_NotDismissal(true);
                    self.visible_Dismissal(false);
                } else if (value == 3) {
                    self.visible_NotDismissal(false);
                    self.visible_Dismissal(true);
                }
                console.log(value);
            });

            self.naturalUnaReasons_1.subscribe((value) => {
                let self = this;
                if (value == true) {
                    self.naturalUnaReasons_enable(true);
                    $("#naturalUnaReasons_1Val").focus();
                } else if (value == false) {
                    self.naturalUnaReasons_1Val('');
                    self.naturalUnaReasons_enable(false);
                }
            });

            self.businessReduction_2.subscribe((value) => {
                let self = this;
                if (value == true) {
                    self.businessReduction_enable(true);
                    $("#businessReduction_2Val").focus();
                } else if (value == false) {
                    self.businessReduction_2Val('');
                    self.businessReduction_enable(false);
                }
            });


            self.seriousViolationsOrder_3.subscribe((value) => {
                let self = this;
                if (value == true) {
                    self.seriousViolationsOrder_enable(true);
                    $("#seriousViolationsOrder_3Val").focus();
                } else if (value == false) {
                    self.seriousViolationsOrder_3Val('');
                    self.seriousViolationsOrder_enable(false);
                }
            });


            self.unauthorizedConduct_4.subscribe((value) => {
                let self = this;
                if (value == true) {
                    self.unauthorizedConduct_enable(true);
                    $("#unauthorizedConduct_4Val").focus();
                } else if (value == false) {
                    self.unauthorizedConduct_4Val('');
                    self.unauthorizedConduct_enable(false);
                }
            });


            self.leaveConsiderableTime_5.subscribe((value) => {
                let self = this;
                if (value == true) {
                    self.leaveConsiderableTime_enable(true);
                    $("#leaveConsiderableTime_5Val").focus();
                } else if (value == false) {
                    self.leaveConsiderableTime_5Val('');
                    self.leaveConsiderableTime_enable(false);
                }
            });

            self.other_6.subscribe((value) => {
                let self = this;
                if (value == true) {
                    self.other_enable(true);
                    $("#other_6Val").focus();
                } else if (value == false) {
                    self.other_6Val('');
                    self.other_enable(false);
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
        status: number;
        notificationCategory: number;
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