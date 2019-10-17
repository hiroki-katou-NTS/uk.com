module jcm007.a {
    import getText = nts.uk.resource.getText;
    import ajax = nts.uk.request.ajax;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import liveView = nts.uk.request.liveView;
    
    export class ViewModel {

        currentEmployee: KnockoutObservable<EmployeeModel>;
        // list company A2_4
        sel001Data: KnockoutObservableArray<IEmployee>;
        // list company copy
        listCom: KnockoutObservableArray<IEmployee>;
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;
        enable_btnRemove: KnockoutObservable<boolean>;

        // tab 2
        employeeList: [];
        itemSelected: KnockoutObservable<any>;
        
        // ccg029
        input: Input;
        
        //
        isNewMode : boolean;
        
        constructor() {
            let self = this;
            self.avatarPerson = ko.observable('images/avatar.svg');
            
            console.log('constructor');
            
            self.tabs = ko.observableArray([
                { id: 'tab-1', title: '社員検索', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: '退職者登録一覧', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(false) }
            ]);

            self.selectedTab = ko.observable('tab-1');

            self.sel001Data = ko.observableArray([]);
            self.listCom = ko.observableArray([]);
            self.currentEmployee = ko.observable(new EmployeeModel(''));
            self.enable_btnRemove = ko.observable(false);

            self.employeeList = [];
            self.itemSelected = ko.observable(null);

            self.input = new Input(undefined);
            self.isNewMode = false;
            
            self.selectedTab.subscribe((value) => {
                let self = this;
                if (value == 'tab-2') {
                    self.selectedTab('tab-2');
                    self.enable_btnRemove(true);
                    
                }else{
                    self.selectedTab('tab-1');
                    self.enable_btnRemove(false);    
                }
            });

            self.itemSelected.subscribe((value) => {
                let self = this;
            });
        }
        
        public seletedEmployee(data): void{
            console.log(data);
            let self = this; 
            if (self.isNewMode) {
                
                // アルゴリズム[登録状況チェック]を実行する(Thực hiện thuật toán "Check tình trạng đăng ký ")
                service.CheckStatusRegistration(data.employeeId).done(() => {
                    console.log('CheckStatusRegistration DONE');
                }).fail((mes) => {
                    console.log('CheckStatusRegistration FAIL');
                    return;
                });
                
                // アルゴリズム[社員情報の表示]を実行する (Thực hiện thuật toán "Hiển thị thông tin employee")
                self.currentEmployee().avatarPerson(data.avartaFileId ? liveView(data.avartaFileId) : liveView('5af04a80-2c77-4945-bf87-fc0ca4bbf930'));
                self.currentEmployee().codeNameEmp(data.employeeCode + ' ' + data.businessName);
                self.currentEmployee().department(data.departmentCode + ' ' + data.departmentName);
                self.currentEmployee().position(data.positionCode + ' ' + data.positionName);
                self.currentEmployee().employmentCls(data.workplaceCode + ' ' + data.workplaceName);
                self.currentEmployee().hisId = '';
                self.currentEmployee().sid = data.employeeId;
                self.currentEmployee().scd = data.employeeCode;
                self.currentEmployee().bussinessName = data.businessName;
                
            } else { 
                self.currentEmployee().avatarPerson(data.avartaFileId ? liveView(data.avartaFileId) : liveView('5af04a80-2c77-4945-bf87-fc0ca4bbf930'));
                self.currentEmployee().codeNameEmp(data.employeeCode + ' ' + data.businessName);
                self.currentEmployee().department(data.departmentCode + ' ' + data.departmentName);
                self.currentEmployee().position(data.positionCode + ' ' + data.positionName);
                self.currentEmployee().employmentCls(data.workplaceCode + ' ' + data.workplaceName);
                self.currentEmployee().hisId = '';
                self.currentEmployee().sid = data.employeeId;
                self.currentEmployee().scd = data.employeeCode;
                self.currentEmployee().bussinessName = data.businessName;
            }            
        }
        
        

        /** start page */
        start() {
            let self = this;
            let dfd = $.Deferred<any>();
            console.log('start');
            self.getListData();

            dfd.resolve();
            return dfd.promise();
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
                        sId : data.employeeId , 
                        pId : data.personalId ,
                        scd : data.employeeCode ,
                        employeeName : data.businessName ,
                        
                        notificationCategory: 0,
                        pendingFlag:  0 ,
                        inputDate : '' ,
                        status: 1 ,
                        
                        retirementDate : emp.retirementDate ,
                        releaseDate : emp.releaseDate ,
                        retirementType : emp.retirementType , 
                        selectedCode_Reason1 : emp.selectedCode_Reason1 ,
                        selectedCode_Reason2 : emp.selectedCode_Reason2 ,
                        retirementRemarks : emp.retirementRemarks ,
                        
                        retirementReasonVal : emp.retirementReasonVal ,
                        
                        dismissalNoticeDate : emp.dismissalNoticeDate ,
                        dismissalNoticeDateAllow : emp.dismissalNoticeDateAllow ,
                        reaAndProForDis : emp.reaAndProForDis ,
                        naturalUnaReasons_1 : emp.naturalUnaReasons_1 ,
                        naturalUnaReasons_1Val : emp.naturalUnaReasons_1Val ,
                        businessReduction_2 : emp.businessReduction_2 ,
                        businessReduction_2Val : emp.businessReduction_2Val ,
                        seriousViolationsOrder_3 : emp.seriousViolationsOrder_3 ,
                        seriousViolationsOrder_3Val : emp.seriousViolationsOrder_3Val ,
                        unauthorizedConduct_4 : emp.unauthorizedConduct_4 ,
                        unauthorizedConduct_4Val : emp.unauthorizedConduct_4Val ,
                        leaveConsiderableTime_5 : emp.leaveConsiderableTime_5 ,
                        leaveConsiderableTime_5Val : emp.leaveConsiderableTime_5Val ,
                        other_6 : emp.other_6 ,
                        other_6Val : emp.other_6Val 
                        
                    }

                debugger;
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
                    nts.uk.ui.dialog.bundledErrors(res);
                    return;
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
                
                dfd.resolve();
            }).fail((mes) => {
                dfd.reject();
            });

            return dfd.promise();
        }

        
        remove() {

        }

        getListData() {
            let self = this;

            service.getData().done((data) => {

                self.employeeList = data;

                if (self.employeeList.length != 0) {
                    self.enable_btnRemove(true);
                    self.tabs = ko.observableArray([
                        { id: 'tab-1', title: '社員検索', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                        { id: 'tab-2', title: '退職者登録一覧', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) }
                    ]);
                    
                    self.bindData();
                    
                } else {
                    self.tabs = ko.observableArray([
                        { id: 'tab-1', title: '社員検索', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                        { id: 'tab-2', title: '退職者登録一覧', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(false) }
                    ]);
                    self.init();
                }
            }).fail((mes) => {
                console.log('Get Data Tab-2 Fail');
            });


            /*for (var i = 0; i < 20; i++) {
                self.employeeList.push({
                    id: i, employeeCode: 'employeeCode', employeeName: 'employeeName', katakanaName: 'katakana',
                    departmentCode: 'departmentCode', department: 'department', position: 'position', workplace: 'workplace'
                });
            }*/
        }

        public bindData(): void {
            var self = this;
            $("#gridListEmployees").igGrid({
                autoGenerateColumns: false,
                primaryKey: 'id',
                columns: [
                    {
                        headerText: 'id', key: 'id', hidden: true
                    },
                    {
                        headerText: 'A1', key: 'employeeCode', dataType: 'string', width: '100px'
                    },
                    {
                        headerText: 'A2', key: 'employeeName', dataType: 'string', width: '100px'
                    },
                    {
                        headerText: 'A3', key: 'katakanaName', dataType: 'string', width: '100px'
                    },
                    {
                        headerText: 'A4', key: 'departmentCode', dataType: 'string', width: '100px'
                    },
                    {
                        headerText: 'A5', key: 'department', dataType: 'string', width: '100px'
                    },
                    {
                        headerText: 'A6', key: 'position', dataType: 'string', width: '100px'
                    },
                    {
                        headerText: 'A7', key: 'workplace', dataType: 'string', width: '100px'
                    }
                ],
                dataSource: self.employeeList,
                dataSourceType: 'json',
                responseDataKey: 'results',
                height: '100%',
                width: '100%',
                tabIndex: 7,
                features: [
                    {
                        name: "Selection",
                        mode: "row",
                        multipleSelection: false,
                        rowSelectionChanged: function(evt, ui) {
                            let itemSelected = _.find(self.employeeList, function(o) { return o.id == ui.row.id; });
                            self.itemSelected(itemSelected);
                            console.log();
                            if (!itemSelected) {
                                self.itemSelected(self.employeeList[0]);
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
                    },
                    {
                        name: "Hiding",
                        columnSettings: [
                            { columnKey: "employeeCode", allowHiding: false },
                            { columnKey: "employeeName", allowHiding: false },
                            { columnKey: "katakanaName", allowHiding: false },
                            { columnKey: "departmentCode", allowHiding: false },
                            { columnKey: "department", allowHiding: false },
                            { columnKey: "position", allowHiding: false },
                            { columnKey: "workplace", allowHiding: false }
                        ]
                    }
                ]
            });

            $("#gridListEmployees").igGridSelection("selectRow", 0);
            self.itemSelected(self.employeeList[0]);
            //console.log(self.currentEmployee());

        }

        /** new mode */
        newMode() {
            let self = this;
            self.isNewMode = true;
            self.init();
            self.clearSelection();
        }

        init() {
            let self = this;
            self.enable_btnRemove(false);
            self.currentEmployee().avatarPerson('images/avatar.svg');
            self.currentEmployee().codeNameEmp('');
            self.currentEmployee().department('');
            self.currentEmployee().position('');
            self.currentEmployee().employmentCls('');
            // set avatar blank
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
        bussinessName: string;

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
            
            self.hisId = '';
            self.cid = '';
            self.sid = '';
            self.scd = '';
            self.bussinessName = '';
            
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

    interface IEmployee {
        hisId: string;
        cid: string;
        sid: string;
        scd: string;
        bussinessName: string;
        department: string;
        position: string;
        employment: string;
        fileId: string;

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