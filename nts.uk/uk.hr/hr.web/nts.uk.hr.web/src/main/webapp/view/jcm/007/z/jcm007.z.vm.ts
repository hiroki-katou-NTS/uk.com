module jcm007.z {
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

        currentEmployee: KnockoutObservable<EmployeeModel> = ko.observable(new EmployeeModel(''));

        dataInfo: any;

        selectedEmp: KnockoutObservable<any> = ko.observable(null);
        empInfoHeaderList: KnockoutObservableArray<IEmpInfoHeader> = ko.observableArray([]);

        // ccg029
        input: Input = new Input(undefined);

        status_Unregistered: string = ''; // 0
        status_ApprovalPending: string = getText('JCM007_A3_2'); // 1
        status_WaitingReflection: string = getText('JCM007_A3_3'); // 2

        sendMail: KnockoutObservable<boolean> = ko.observable(false);

        comment: KnockoutObservable<string> = ko.observable();

        viewProcess: KnockoutObservable<boolean> = ko.observable(false);

        constructor() {
            let self = this;

            self.bindData();

            self.loadData();

            self.selectedEmp.subscribe((value) => {
                if (value == null) return;
                if (value.status == self.status_ApprovalPending || value.status == self.status_Unregistered) {
                    // アルゴリズム[退職者情報の表示」を実行する] (Thực hiện thuật toán [Hiển thị thông tin người nghỉ hưu」)
                } else if (value.status == self.status_WaitingReflection) {
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

                let dataDetail = _.find(self.employees(), function(o) { return o.sid == value.sid; });
                self.setDataDetail(dataDetail);
                $('#retirementDateId').focus();
            });

            self.viewProcess.subscribe((value) => {

                let dataSource = self.employees();

                $("#gridListEmployeesJcm007").igGrid('option', 'dataSource', dataSource);
                //đoạn này set selected ID
                
                 //đang không selected vào item nào thì return luôn
                if(!self.currentEmployee().hisId){
                    return;
                }
                //nếu item selected cũ không còn trong list thì mới phải set lại selected ID , không thì thôi
                if (!_.find(dataSource, ['historyId', self.currentEmployee().hisId])) {

                    //lấy item sau nó, vì data order theo retirementDate nên lấy date > date cũ
                    let items = _.filter(dataSource, function(o) { return new Date(o.retirementDate) >= new Date(self.currentEmployee().retirementDate()); }),

                        selectHistID = items.length ? items[0].historyId : null;
                    
                    //nếu không tìm được ID ở đây => 1.thằng item đang chọn là thằng cuối cùng của list cũ || 2.cái list mới không có item nào 
                    if (!selectHistID && dataSource.length) {
                        // xử lý th 1 = cách set chọn luôn thằng cuối cùng trong list mới
                        selectHistID = dataSource[dataSource.length - 1].historyId;
                    }
                    //làm thủ tục thay đổi selected ID
                    
                    $("#gridListEmployeesJcm007").igGridSelection("selectRowById", selectHistID);

                    if (!!selectHistID) {
                        let itemSelected = _.find(dataSource, function(o) { return o.historyId == selectHistID; });
                        self.selectedEmp(itemSelected);
                    } else {
                        //list mới không có item thì clear selected 
                        self.initHeaderInfo();
                        self.initRetirementInfo();
                        self.selectedEmp(null);
                    }
                }
            });

        }

        employees() {
            let self = this;

            if (!self.dataInfo) {
                return [];
            }
            return self.viewProcess() ? self.dataInfo.retiredEmployees : _.filter(self.dataInfo.retiredEmployees, ['status', getText('JCM007_B3_2')]);
        }



        loadData() {
            let self = this;
            self.getListData().done(() => {
                let selectedEmp = self.selectedEmp();
                if (selectedEmp != null) {
                    if (selectedEmp.status == self.status_ApprovalPending || selectedEmp.status == self.status_Unregistered) {


                    } else if (selectedEmp.status == self.status_WaitingReflection) {

                    }

                    let objHeader = _.find(self.empInfoHeaderList, function(o) { return o.employeeId == selectedEmp.sid; });
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

                    let dataDetail = _.find(self.employees(), function(o) { return o.sid == selectedEmp.sid; });
                    if (dataDetail) {
                        self.setDataDetail(dataDetail);
                    }

                    $('#retirementDateId').focus();
                } else {
                    self.initHeaderInfo();
                    self.initRetirementInfo();
                }

            });

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

        getListData(historyId?: any, isAfterRemove?: boolean) {
            let self = this;
            let dfd = $.Deferred<any>();
            block.grayout();
            service.startPage().done((result) => {
                // goi service アルゴリズム[社員情報リストを取得]を実行する
                // (Thực hiện thuật toán [Get list thông tin nhân viên]) CCG029

                self.empInfoHeaderList = result.employeeImports;


                self.dataInfo = result;

                $("#gridListEmployeesJcm007").igGrid('option', 'dataSource', self.employees());

                if (self.employees().length) {

                    let selectHistID = self.employees()[0].historyId;
                    $("#gridListEmployeesJcm007").igGridSelection("selectRowById", selectHistID);
                    self.selectedEmp(self.employees()[0]);
                }

                dfd.resolve();
                block.clear();

            }).fail((error) => {
                dfd.reject();
                nts.uk.ui.dialog.info(error);
                //để sẵn ở đây , chưa dùng đên nhưng trong EA có nói 
                // window.history.back();
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
                        headerText: getText('JCM007_B221_5'), key: 'status', dataType: 'string', width: '70px'
                    },
                    {
                        headerText: getText('JCM007_B221_6'), key: 'scd', dataType: 'string', width: '70px'
                    },
                    {
                        headerText: getText('JCM007_B221_7'), key: 'employeeName', dataType: 'string', width: '140px'
                    },
                    {
                        headerText: getText('JCM007_B221_8'), key: 'retirementDate', dataType: 'date', width: '120px', dateInputFormat: 'yyyy/MM/dd'
                    },
                    {
                        headerText: getText('JCM007_B221_9'), key: 'releaseDate', dataType: 'date', width: '120px', dateInputFormat: 'yyyy/MM/dd'
                    },
                    {
                        headerText: getText('JCM007_B221_10'), key: 'notificationCategory', dataType: 'string', width: '80px'
                    }
                ],
                dataSource: self.employees(),
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
                            let itemSelected = _.find(self.employees(), function(o) { return o.historyId == ui.row.id; });
                            if (itemSelected) {
                                self.selectedEmp(itemSelected);
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
            });

        }

        setDataDetail(param: any) {
            let self = this,
                emp = self.currentEmployee();
            emp.sid = param.sid;
            emp.hisId = param.historyId;
            emp.retirementDate(param.retirementDate);
            emp.releaseDate(param.releaseDate);

            emp.selectedCode_Retiment(param.retirementCategory);
            emp.selectedCode_Reason1(param.retirementReasonCtg1);
            emp.selectedCode_Reason2(param.retirementReasonCtg2);
            emp.retirementRemarks(param.retirementRemarks == null ? '' : param.retirementRemarks);
            // ----------------- //
            emp.retirementReasonVal(param.retirementReasonVal == null ? '' : param.retirementReasonVal);
            // ----------------- //
            emp.dismissalNoticeDate(param.dismissalNoticeDate == null ? '' : param.dismissalNoticeDate);
            emp.dismissalNoticeDateAllow(param.dismissalNoticeDateAllow == null ? '' : param.dismissalNoticeDateAllow);
            emp.reaAndProForDis(param.reaAndProForDis == null ? '' : param.reaAndProForDis);

            emp.naturalUnaReasons_1(param.naturalUnaReasons_1 == 0 ? false : true);
            emp.naturalUnaReasons_1Val(param.naturalUnaReasons_1Val == null ? '' : param.naturalUnaReasons_1Val);
            emp.businessReduction_2(param.naturalUnaReasons_2 == 0 ? false : true);
            emp.businessReduction_2Val(param.naturalUnaReasons_2Val == null ? '' : param.naturalUnaReasons_2Val);
            emp.seriousViolationsOrder_3(param.naturalUnaReasons_3 == 0 ? false : true);
            emp.seriousViolationsOrder_3Val(param.naturalUnaReasons_3Val == null ? '' : param.naturalUnaReasons_3Val);
            emp.unauthorizedConduct_4(param.naturalUnaReasons_4 == 0 ? false : true);
            emp.unauthorizedConduct_4Val(param.naturalUnaReasons_4Val == null ? '' : param.naturalUnaReasons_4Val);
            emp.leaveConsiderableTime_5(param.naturalUnaReasons_5 == 0 ? false : true);
            emp.leaveConsiderableTime_5Val(param.naturalUnaReasons_5Val == null ? '' : param.naturalUnaReasons_5Val);
            emp.other_6(param.naturalUnaReasons_6 == 0 ? false : true);
            emp.other_6Val(param.naturalUnaReasons_6Val == null ? '' : param.naturalUnaReasons_6Val);
            emp.approveSendMailFlg(param.approveSendMailFlg || false);
            emp.approveComment(param.approveComment || '');
        }


        approvedEnabled() {
            let self = this,
                status = !!self.selectedEmp() ? self.selectedEmp().status : null;

            return status == getText('JCM007_B3_2');
        }

        releaseEnabled() {
            let self = this,
                status = !!self.selectedEmp() ? self.selectedEmp().status : null;

            return status == getText('JCM007_B3_2');
        }

        returnEnabled() {
            let self = this,
                status = !!self.selectedEmp() ? self.selectedEmp().status : null;

            return status == getText('JCM007_B3_3') || status == getText('JCM007_A3_3');
        }

        checkboxAndCommentEnabled() {
            let self = this;
            // dù thêm releaseEnabled vào là thừa .Nhưng vẫn cứ nên cho, để nó đủ logic
            return self.approvedEnabled() || self.releaseEnabled() || self.returnEnabled();
        }

        showInteviewLinkButton() {

            let self = this,
                interVIewInfo = !!self.selectedEmp() ? _.find(self.dataInfo.interviewSummary.listInterviewRecordAvailability, ['employeeID', self.selectedEmp().sid]) : null;

            return !!interVIewInfo ? interVIewInfo.presence : false;
        }
        
        reLoadData(){
            let self = this;
              // load lại data
              block.grayout();
            
                service.startPage().done((result) => {
                    self.dataInfo = result;
                    $("#gridListEmployeesJcm007").igGrid('option', 'dataSource', self.employees());
                    
                    dfd.resolve();
                    block.clear();

                }).fail((error) => {
                    dfd.reject();
                    nts.uk.ui.dialog.info(error);
                    //để sẵn ở đây , chưa dùng đến nhưng trong EA có nói 
                    // window.history.back();
                }).always(() => {
                    block.clear();
                });
                
        
        }
        
        public approved() {
            let self = this,
                command = ko.toJS(self.currentEmployee());
            command.approvalType = ApprovalType.Approved;
            block.grayout();
            service.approved(command).done(() => {
                dialog.info({ messageId: "MsgJ_JCM007_10" });
                self.reLoadData();
                block.clear();

            }).fail((mes) => {

                block.clear();
                nts.uk.ui.dialog.bundledErrors(mes);
                dfd.reject();
            });
        }

        public release() {
            let self = this,
                command = ko.toJS(self.currentEmployee());

            command.approvalType = ApprovalType.Release;
            block.grayout();
            service.approved(command).done(() => {
                dialog.info({ messageId: "MsgJ_JCM007_10" });
                self.reLoadData();
                block.clear();

            }).fail((mes) => {

                block.clear();
                nts.uk.ui.dialog.bundledErrors(mes);
                dfd.reject();
            });
        }

        public returnEvent() {
            let self = this,
                command = ko.toJS(self.currentEmployee());
            command.approvalType = ApprovalType.Return;

            block.grayout();
            service.approved(command).done(() => {
                dialog.info({ messageId: "MsgJ_JCM007_10" });
                self.reLoadData();
                block.clear();

            }).fail((mes) => {

                block.clear();
                nts.uk.ui.dialog.bundledErrors(mes);
                dfd.reject();
            });
        }

        public backTopScreenTopReport(): void {
            let self = this;
            window.history.back();
        }

        initHeaderInfo() {
            let self = this;
            self.currentEmployee().avatarPerson('images/avatar.png');
            self.currentEmployee().codeNameEmp('');
            self.currentEmployee().department('');
            self.currentEmployee().position('');
            self.currentEmployee().employmentCls('');
            self.currentEmployee().approveSendMailFlg(false);
            self.currentEmployee().approveComment('');
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
        enable_retirementDate: KnockoutObservable<boolean> = ko.observable(false);
        enable_releaseDate: KnockoutObservable<boolean> = ko.observable(false);
        enable_listRetirementCtg: KnockoutObservable<boolean> = ko.observable(false);
        enable_listRetirementReason1: KnockoutObservable<boolean> = ko.observable(false);
        enable_listRetirementReason2: KnockoutObservable<boolean> = ko.observable(false);
        enable_retirementRemarks: KnockoutObservable<boolean> = ko.observable(false);
        enable_retirementReasonVal: KnockoutObservable<boolean> = ko.observable(false);
        enable_dismissalNoticeDate: KnockoutObservable<boolean> = ko.observable(false);
        enable_dismissalNoticeDateAllow: KnockoutObservable<boolean> = ko.observable(false);
        enable_reaAndProForDis: KnockoutObservable<boolean> = ko.observable(false);
        enable_naturalUnaReasons1: KnockoutObservable<boolean> = ko.observable(false);
        enable_businessReduction2: KnockoutObservable<boolean> = ko.observable(false);
        enable_seriousViolationsOrder3: KnockoutObservable<boolean> = ko.observable(false);
        enable_unauthorizedConduct4: KnockoutObservable<boolean> = ko.observable(false);
        enable_leaveConsiderableTime5: KnockoutObservable<boolean> = ko.observable(false);
        enable_other6: KnockoutObservable<boolean> = ko.observable(false);
        objResultOfterRetimentChange: any;

        approveSendMailFlg: KnockoutObservable<boolean> = ko.observable(false);
        approveComment: KnockoutObservable<string> = ko.observable('');

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


            self.approveSendMailFlg(param.approveSendMailFlg);
            self.approveComment(param.approveComment);
            //xử lý subscribe
              self.retirementDate.subscribe((value) => {
                  if (!value) {
                      return;
                  }
                console.log("retirementDate change");
                block.grayout();
                let object = {
                    retirementDate: self.retirementDate(), // A222_12  退職日
                    retirementType: self.selectedCode_Retiment(),        // A222_16 退職区分
                    sid: self.sid,                 // 社員ID = 選択中社員の社員ID(EmployeeID = EmployeeID của employee dang chon)
                    cid: null,                 // 会社ID = ログイン会社ID(CompanyID = LoginCompanyID)
                    baseDate: null
                };

                service.eventChangeRetirementDate(object).done((result: any) => {
                    console.log('event retirementDate change done');
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
                    debugger;
                    block.clear();
                    nts.uk.ui.dialog.info(error);
                    return;
                }).always(() => {
                    block.clear();
                });


                // update bien objResultOfterRetimentChange

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
                } else if (value == true) {
                    self.naturalUnaReasons_enable(true);
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

        approveSendMailFlg: boolean;
        approveComment: string;
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

    //処理区分
    enum ApprovalType {
        Return = 0,
        Approved = 2,
        Release = 4
    }

    export interface IApprove {
        // 処理区分
        approvalType: ApprovalType;

        selectedEmp: any;
    }

    export class DateFormat {
        static DEFAULT_FORMAT = 'YYYY/MM/DD';
    }

}