module cmm045.a.viewmodel {
    import vmbase = cmm045.shr.vmbase;
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import character = nts.uk.characteristics;
    import request = nts.uk.request;
    export class ScreenModel {
        roundingRules: KnockoutObservableArray<vmbase.ApplicationDisplayAtr> = ko.observableArray([]);
        selectedRuleCode: KnockoutObservable<any> = ko.observable(0);// switch button
        //lst fill in grid list
        items: KnockoutObservableArray<vmbase.DataModeApp> = ko.observableArray([]);
        //lst full data get from db
        lstApp: KnockoutObservableArray<vmbase.DataModeApp> = ko.observableArray([]);
        lstAppCommon: KnockoutObservableArray<vmbase.ApplicationDto_New> = ko.observableArray([]);
        lstAppMaster: KnockoutObservableArray<vmbase.AppMasterInfo> = ko.observableArray([]);
        lstAppOt: KnockoutObservableArray<vmbase.AppOverTimeInfoFull> = ko.observableArray([]);
        lstAppGoBack: KnockoutObservableArray<vmbase.AppGoBackInfoFull> = ko.observableArray([]);
        displaySet: KnockoutObservable<vmbase.ApprovalListDisplaySetDto> = ko.observable(null);
        approvalMode: KnockoutObservable<boolean> = ko.observable(false);
        approvalCount: KnockoutObservable<vmbase.ApplicationStatus> = ko.observable(new vmbase.ApplicationStatus(0, 0, 0, 0, 0, 0));
        itemList: KnockoutObservableArray<any>;
        selectedIds: KnockoutObservableArray<any> = ko.observableArray([1, 2, 3, 4, 5, 6]);// check box
        dateValue: KnockoutObservable<vmbase.Date> = ko.observable({ startDate: '', endDate: '' });
        itemApplication: KnockoutObservableArray<vmbase.ChoseApplicationList> = ko.observableArray([]);
        selectedCode: KnockoutObservable<number> = ko.observable(-1);// combo box
        mode: KnockoutObservable<number> = ko.observable(1);
        startDateString: KnockoutObservable<string> = ko.observable("");
        endDateString: KnockoutObservable<string> = ko.observable("");
        constructor() {
            let self = this;
            self.itemList = ko.observableArray([
                { id: 1, name: getText('CMM045_20') },
                { id: 2, name: getText('CMM045_21') },
                { id: 3, name: getText('CMM045_22') },
                { id: 4, name: getText('CMM045_23') },
                { id: 5, name: getText('CMM045_24') },
                { id: 6, name: getText('CMM045_25') }
            ]);
            self.selectedRuleCode.subscribe(function(codeChanged) {
                self.filter();
            });
            self.selectedCode.subscribe(function(codeChanged) {
                self.filterByAppType(codeChanged);
            });
        }

        start(): JQueryPromise<any> {
            block.invisible();
            let self = this;
            var dfd = $.Deferred();
            //get param url
            let url = $(location).attr('search');
            let urlParam: number = url.split("=")[1];
            let characterData = null;
            character.restore("AppListExtractCondition").done((obj) => {
                console.log(obj);
                characterData = obj;
                if (obj !== undefined) {
                    let date: vmbase.Date = { startDate: obj.startDate, endDate: obj.endDate }
                    self.dateValue(date);
                    self.selectedIds([]);
                    if (obj.unapprovalStatus) {//未承認
                        self.selectedIds.push(1);
                    }
                    if (obj.approvalStatus) {//承認済み
                        self.selectedIds.push(2);
                    }
                    if (obj.denialStatus) {//否認
                        self.selectedIds.push(3);
                    }
                    if (obj.agentApprovalStatus) {//代行承認済み
                        self.selectedIds.push(4);
                    }
                    if (obj.remandStatus) {//差戻
                        self.selectedIds.push(5);
                    }
                    if (obj.cancelStatus) {//取消
                        self.selectedIds.push(6);
                    }
                    self.selectedRuleCode(obj.appDisplayAtr);
                }
                if (urlParam === undefined) {
                    self.mode(characterData.appListAtr);
                } else {
                    self.mode(urlParam);
                }

                let param: vmbase.AppListExtractConditionDto = new vmbase.AppListExtractConditionDto(self.dateValue().startDate, self.dateValue().endDate, self.mode(),
                    null, self.findcheck(self.selectedIds(), 1), self.findcheck(self.selectedIds(), 2), self.findcheck(self.selectedIds(), 3),
                    self.findcheck(self.selectedIds(), 4), self.findcheck(self.selectedIds(), 5), self.findcheck(self.selectedIds(), 6), self.selectedRuleCode(), [], '');

                service.getApplicationDisplayAtr().done(function(data1) {
                    _.each(data1, function(obj) {
                        self.roundingRules.push(new vmbase.ApplicationDisplayAtr(obj.value, obj.localizedName));
                    });
                    service.getApplicationList(param).done(function(data) {
                        //luu param
                        if (self.dateValue().startDate == '' || self.dateValue().endDate == '') {
                            let date: vmbase.Date = { startDate: data.startDate, endDate: data.endDate }
                            self.dateValue(date);
                        }
                        let paramSave: vmbase.AppListExtractConditionDto = new vmbase.AppListExtractConditionDto(self.dateValue().startDate, self.dateValue().endDate, self.mode(),
                            null, self.findcheck(self.selectedIds(), 1), self.findcheck(self.selectedIds(), 2), self.findcheck(self.selectedIds(), 3),
                            self.findcheck(self.selectedIds(), 4), self.findcheck(self.selectedIds(), 5), self.findcheck(self.selectedIds(), 6), self.selectedRuleCode(), [], '');
                        character.save('AppListExtractCondition', paramSave);
                        console.log(data);
                        let lstGoBack: Array<vmbase.AppGoBackInfoFull> = [];
                        let lstAppGroup: Array<vmbase.AppPrePostGroup> = [];
                        self.displaySet(new vmbase.ApprovalListDisplaySetDto(data.displaySet.advanceExcessMessDisAtr,
                            data.displaySet.hwAdvanceDisAtr, data.displaySet.hwActualDisAtr,
                            data.displaySet.actualExcessMessDisAtr, data.displaySet.otAdvanceDisAtr,
                            data.displaySet.otActualDisAtr, data.displaySet.warningDateDisAtr, data.displaySet.appReasonDisAtr));
                        _.each(data.lstApp, function(app) {
                            self.lstAppCommon.push(new vmbase.ApplicationDto_New(app.applicationID, app.prePostAtr, app.inputDate, app.enteredPersonSID,
                                app.reversionReason, app.applicationDate, app.applicationReason, app.applicationType, app.applicantSID,
                                app.reflectPlanScheReason, app.reflectPlanTime, app.reflectPlanState, app.reflectPlanEnforce,
                                app.reflectPerScheReason, app.reflectPerTime, app.reflectPerState, app.reflectPerEnforce,
                                app.startDate, app.endDate, app.version));
                        });
                        _.each(data.lstMasterInfo, function(master) {
                            self.lstAppMaster.push(new vmbase.AppMasterInfo(master.appID, master.appType, master.dispName, master.empName,
                                master.workplaceName, master.statusFrameAtr, master.phaseStatus, master.checkAddNote, master.checkTimecolor));
                        });
                        _.each(data.lstAppGoBack, function(goback) {
                            lstGoBack.push(new vmbase.AppGoBackInfoFull(goback.appID, goback.goWorkAtr1, goback.workTimeStart1,
                                goback.backHomeAtr1, goback.workTimeEnd1, goback.goWorkAtr2, goback.workTimeStart2, goback.backHomeAtr2, goback.workTimeEnd2));
                        });
                        _.each(data.lstAppOt, function(overTime) {
                            let lstFrame: Array<vmbase.OverTimeFrame> = []
                            _.each(overTime.lstFrame, function(frame) {
                                lstFrame.push(new vmbase.OverTimeFrame(frame.attendanceType, frame.frameNo, frame.name,
                                    frame.timeItemTypeAtr, frame.applicationTime));
                            });
                            self.lstAppOt.push(new vmbase.AppOverTimeInfoFull(overTime.appID, overTime.workClockFrom1, overTime.workClockTo1, overTime.workClockFrom2,
                                overTime.workClockTo2, overTime.total, lstFrame, overTime.overTimeShiftNight, overTime.flexExessTime));
                        });
                        _.each(data.lstAppGroup, function(group) {
                            lstAppGroup.push(new vmbase.AppPrePostGroup(group.preAppID, group.postAppID, group.time));
                        });
                        self.itemApplication([]);
                        self.itemApplication.push(new vmbase.ChoseApplicationList(-1, '全件表示'));
                        _.each(data.lstAppType, function(appType){
                            self.itemApplication.push(new vmbase.ChoseApplicationList(appType, self.findAppName(appType)));                          
                        });
                        let lstData = self.mapData(self.lstAppCommon(), self.lstAppMaster(), lstGoBack, self.lstAppOt(), lstAppGroup);
                        self.lstApp(lstData);
                        self.items(lstData);
                        //mode approval - count
                        if (data.appStatusCount != null) {
                            self.approvalCount(new vmbase.ApplicationStatus(data.appStatusCount.unApprovalNumber, data.appStatusCount.approvalNumber,
                                data.appStatusCount.approvalAgentNumber, data.appStatusCount.cancelNumber, data.appStatusCount.remandNumner,
                                data.appStatusCount.denialNumber));
                        }
                        if (self.mode() == 1) {
                            self.reloadGridApproval();
                        } else {
                            self.reloadGridApplicaion()
                        }
                        dfd.resolve();
                    });
                }).always(() => {
                    block.clear();
                });

            });
            return dfd.promise();
        }

        reloadGridApplicaion() {
            var self = this;
            $("#grid2").ntsGrid({
                width: '1100px',
                height: '500px',
                dataSource: self.items(),
                primaryKey: 'appId',
                virtualization: true,
                virtualizationMode: 'continuous',
                columns: [
                    { headerText: getText('CMM045_50'), key: 'appId', dataType: 'string', width: '80px', unbound: false, ntsControl: 'Button' },
                    { headerText: getText('CMM045_51'), key: 'applicant', dataType: 'string', width: '120px' },
                    { headerText: getText('CMM045_52'), key: 'appName', dataType: 'string', width: '120px' },
                    { headerText: getText('CMM045_53'), key: 'appAtr', dataType: 'string', width: '80px' },
                    { headerText: getText('CMM045_54'), key: 'appDate', dataType: 'string', width: '150px', ntsControl: 'Label'},
                    { headerText: getText('CMM045_55'), key: 'appContent', dataType: 'string', width: '280px' },
                    { headerText: getText('CMM045_56'), key: 'inputDate', dataType: 'string', width: '180px', ntsControl: 'Label'},
                    { headerText: getText('CMM045_57'), key: 'appStatus', dataType: 'string', width: '100px', ntsControl: 'Label' },
                    { headerText: 'ID', key: 'appId', dataType: 'string', width: '0px', hidden: true }
                ],
                features: [{ name: 'Resizing' },
                    {
                        name: 'Selection',
                        mode: 'row',
                        multipleSelection: true
                    }
                ],
                ntsControls: [{ name: 'Checkbox', options: { value: 1, text: '' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true },
                    { name: 'Button', text: getText('CMM045_50'), controlType: 'Button', enable: true },
                ]
            });
            $("#grid2").on("click", ".ntsButton", function(evt, ui) {
                let _this = $(this);
                let id = _this.parents('tr').data('id');
                nts.uk.sessionStorage.removeItem(request.STORAGE_KEY_TRANSFER_DATA);
                nts.uk.sessionStorage.setItemAsJson(request.STORAGE_KEY_TRANSFER_DATA, { appID: id });
                window.location.href = "../../../kaf/000/b/index.xhtml";
            });
            _.each(self.items(), function(item) {
                //fill color in 承認状況
                let id = ".nts-grid-control-appStatus-" + item.appId;
                if (item.appStatus == '未') {
                    $(id).parent().addClass('unapprovalCell');
                }
                if (item.appStatus == '承認済み') {
                    $(id).parent().addClass('approvalCell');
                }
                if (item.appStatus == '反映済み') {
                    $(id).parent().addClass('reflectCell');
                }
                if (item.appStatus == '取消') {
                    $(id).parent().addClass('cancelCell');
                }
                if (item.appStatus == '差戻') {
                   $(id).parent().addClass('remandCell');
                }
                if (item.appStatus == '否') {
                    $(id).parent().addClass('denialCell');
                }
                //fill color in 申請内容
                if (item.checkTimecolor == 1) {//1: xin truoc < xin sau; k co xin truoc; xin truoc bi denail
                    $(".nts-grid-control-appContent-" + item.appId).addClass('preAppExcess');
                }
                if (item.checkTimecolor == 2) {////2: thuc te < xin sau
                    $(".nts-grid-control-appContent-" + item.appId).addClass('workingResultExcess');
                }
                //fill color text
                let color = item.appDate.substring(11,12);
                if (color == '土') {//土
                    $(".nts-grid-control-appDate-" + item.appId).addClass('saturdayCell');
                }
                if (color == '日') {//日 
                    $(".nts-grid-control-appDate-" + item.appId).addClass('sundayCell');
                }
                //fill color text
                let colorIn = item.inputDate.substring(11,12);
                if (color == '土') {//土
                    $(".nts-grid-control-inputDate-" + item.appId).addClass('saturdayCell');
                }
                if (color == '日') {//日 
                    $(".nts-grid-control-inputDate-" + item.appId).addClass('sundayCell');
                }
            });
        }

        reloadGridApproval() {
            var self = this;
            $("#grid1").ntsGrid({
                width: '1280px',
                height: '700px',
                dataSource: self.items(),
                primaryKey: 'appId',
                virtualization: true,
                virtualizationMode: 'continuous',
                columns: [
                    { headerText: getText('CMM045_49'), key: 'check', dataType: 'boolean', width: '60px', ntsControl: 'Checkbox' },
                    { headerText: getText('CMM045_50'), key: 'details', dataType: 'string', width: '50px', unbound: false, ntsControl: 'Button' },
                    { headerText: getText('CMM045_51'), key: 'applicant', dataType: 'string', width: '120px' },
                    { headerText: getText('CMM045_52'), key: 'appName', dataType: 'string', width: '120px' },
                    { headerText: getText('CMM045_53'), key: 'appAtr', dataType: 'string', width: '120px' },
                    { headerText: getText('CMM045_54'), key: 'appDate', dataType: 'string', width: '150px', ntsControl: 'Label'},
                    { headerText: getText('CMM045_55'), key: 'appContent', dataType: 'string', width: '240px'},
                    { headerText: getText('CMM045_56'), key: 'inputDate', dataType: 'string', width: '180px', ntsControl: 'Label'},
                    { headerText: getText('CMM045_57'), key: 'appStatus', dataType: 'string', width: '120px', ntsControl: 'Label' },
                    { headerText: getText('CMM045_58'), key: 'displayAppStatus', dataType: 'string', width: '120px' },
                    { headerText: 'ID', key: 'appId', dataType: 'string', width: '0px', ntsControl: 'Label', hidden: true }
                ],
                features: [{ name: 'Resizing' },
                    {
                        name: 'Selection',
                        mode: 'row',
                        multipleSelection: true
                    }
                ],
                ntsControls: [{ name: 'Checkbox', options: { value: 1, text: '' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox' },
                    { name: 'Button', text: getText('CMM045_50'), controlType: 'Button', enable: true }],
            });

            $("#grid1").on("click", ".ntsButton", function(evt, ui) {
                let _this = $(this);
                let id = _this.parents('tr').data('id');
                nts.uk.sessionStorage.removeItem(request.STORAGE_KEY_TRANSFER_DATA);
                nts.uk.sessionStorage.setItemAsJson(request.STORAGE_KEY_TRANSFER_DATA, { appID: id });
                window.location.href = "../../../kaf/000/b/index.xhtml";
            });

            $("#grid1").setupSearchScroll("igGrid", true);

            _.each(self.items(), function(item) {
                //display check box
                if (item.checkAtr == false) {
                    $(".nts-grid-control-check-" + item.appId).css("display", "none");
                }
                //fill color in 承認状況
                let id = ".nts-grid-control-appStatus-" + item.appId;
                if (item.appStatus == '未') {
                    $(id).parent().addClass('unapprovalCell');
                }
                if (item.appStatus == '承認済み') {
                     $(id).parent().addClass('approvalCell');
                }
                if (item.appStatus == '取消') {
                     $(id).parent().addClass('cancelCell');
                }
                if (item.appStatus == '差戻') {
                    $(id).parent().addClass('remandCell');
                }
                if (item.appStatus == '否') {
                    $(id).parent().addClass('denialCell');
                }
                //fill color in 申請内容
                if (item.checkTimecolor == 1) {//1: xin truoc < xin sau; k co xin truoc; xin truoc bi denail
                    $(".nts-grid-control-appContent-" + item.appId).addClass('preAppExcess');
                }
                if (item.checkTimecolor == 2) {////2: thuc te < xin sau
                    $(".nts-grid-control-appContent-" + item.appId).addClass('workingResultExcess');
                }
                //fill color text
                let color = item.appDate.substring(11,12);
                if (color == '土') {//土
                    $(".nts-grid-control-appDate-" + item.appId).addClass('saturdayCell');
                }
                if (color == '日') {//日
                    $(".nts-grid-control-appDate-" + item.appId).addClass('sundayCell');
                }
                //fill color text
                let colorIn = item.inputDate.substring(11,12);
                if (color == '土') {//土
                    $(".nts-grid-control-inputDate-" + item.appId).addClass('saturdayCell');
                }
                if (color == '日') {//日
                    $(".nts-grid-control-inputDate-" + item.appId).addClass('sundayCell');
                }
            });
        }
        /**
         * format data: over time before
         * ※申請モード、承認モード(事前)用レイアウト
         */
        fomartOverTimeBf(app: vmbase.ApplicationDto_New, overTime: vmbase.AppOverTimeInfoFull, masterInfo: vmbase.AppMasterInfo): vmbase.DataModeApp {
            let self = this;
            let reason = self.displaySet().appReasonDisAtr == 1 ? ' ' + app.applicationReason : '';
            let applicant: string = masterInfo.workplaceName + '<br/>'  + masterInfo.empName;
            let appContent1111: string = getText('CMM045_268') + ' ' + overTime.workClockFrom1 + getText('CMM045_100') + overTime.workClockTo1 + ' 残業合計' + self.convertFrameTime(overTime.lstFrame) + reason;
            let prePost = app.prePostAtr == 0 ? '事前' : '事後';
            let prePostApp = masterInfo.checkAddNote == true ? prePost + getText('CMM045_101') : prePost;
            let a: vmbase.DataModeApp = new vmbase.DataModeApp(app.applicationID, app.applicationType, 'chi tiet', applicant,
                masterInfo.dispName, prePostApp, self.convertDate(app.applicationDate), appContent1111, self.convertDateTime(app.inputDate),
                self.mode() == 0 ? self.convertStatus(app.reflectPerState) : self.convertStatusAppv(app.reflectPerState), masterInfo.phaseStatus,
                masterInfo.statusFrameAtr, app.version, masterInfo.checkTimecolor);
            return a;
        }
        convertFrameTime(lstFrame: Array<vmbase.OverTimeFrame>): any {
            let self = this;
            let framName = '';
            let framName11 = '';
            let framName12 = '';
            let time = 0;
            let count = 0;
            let lstSort = _.sortBy(lstFrame, ["frameNo"], ["asc"]);
            //時間外深夜時間
            let frame11 = self.findFrameByNo(lstFrame, 11);
            if (frame11 !== undefined && frame11.applicationTime != 0) {
                framName11 = frame11.name + self.convertTime_Short_HM(frame11.applicationTime);
                time += frame11.applicationTime;
                count += 1;
            }
            //ﾌﾚｯｸｽ超過
            let frame12 = self.findFrameByNo(lstFrame, 11);
            if (frame12 !== undefined && frame12.applicationTime != 0) {
                framName12 = frame12.name + self.convertTime_Short_HM(frame12.applicationTime);
                time += frame12.applicationTime;
                count += 1;
            }
            _.each(lstSort, function(item, index) {
                if (index != 11 && index != 12 && item.applicationTime != 0) {//時間外深夜時間
                    if (count <= 3) {
                        framName += item.name + self.convertTime_Short_HM(item.applicationTime);
                    }
                    time += item.applicationTime;
                    count += 1;
                }
            });
            let other = count > 3 ? count - 3 : 0;
            let otherInfo = other > 0 ? '他' + other + '枠' : '';
            let result = self.convertTime_Short_HM(time) + '(' + framName11 + framName12 + framName + otherInfo + ')';
            return result;
        }
        findFrameByNo(lstFrame: Array<vmbase.OverTimeFrame>, frameNo: number): any {
            return _.find(lstFrame, function(frame) {
                return frame.frameNo == frameNo;
            });
        }
        /**
         * ※承認モード(事後)用レイアウト
         * format data: over time after
         */
        fomartOverTimeAf(app: vmbase.ApplicationDto_New, overTime: vmbase.AppOverTimeInfoFull, masterInfo: vmbase.AppMasterInfo, lstAppGroup: Array<vmbase.AppPrePostGroup>): vmbase.DataModeApp {
            let self = this;
            let contentPre = '';
            let contentResult = '';
            //find don xin truoc, thuc te
            let check: vmbase.AppPrePostGroup = self.findAppPre(lstAppGroup, app.applicationID);
            if (check !== undefined) {
                if (check.preAppID != '') {
                    let prRes = self.findContentPre(check.preAppID, check.lstFrameRes);
                    contentPre = prRes.appPre;
                    contentResult = prRes.appRes;
                }
            }
            let reason = self.displaySet().appReasonDisAtr == 1 ? ''  + app.applicationReason : '';
            let applicant: string = masterInfo.workplaceName + '<br/>' + masterInfo.empName;
            let appContentPost: string = getText('CMM045_272') + getText('CMM045_268') + ' ' + overTime.workClockFrom1 + getText('CMM045_100') + overTime.workClockTo1 + ' 残業合計' + self.convertFrameTime(overTime.lstFrame) + reason;
            let prePost = app.prePostAtr == 0 ? '事前' : '事後';
            let contentFull = appContentPost + '<br/>' + contentPre + '<br/>' + contentResult;
            let prePostApp = masterInfo.checkAddNote == true ? prePost + getText('CMM045_101') : prePost;
            let a: vmbase.DataModeApp = new vmbase.DataModeApp(app.applicationID, app.applicationType, 'chi tiet', applicant,
                masterInfo.dispName, prePostApp, self.convertDate(app.applicationDate), contentFull, self.convertDateTime(app.inputDate),
                self.mode() == 0 ? self.convertStatus(app.reflectPerState) : self.convertStatusAppv(app.reflectPerState), masterInfo.phaseStatus,
                masterInfo.statusFrameAtr, app.version, masterInfo.checkTimecolor);
            return a;
        }
        findAppPre(lstAppGroup: Array<vmbase.AppPrePostGroup>, appId: String): any {
            return _.find(lstAppGroup, function(app) {
                return app.postAppID == appId;
            });
        }
        findContentPre(appId: string, lstFrameRes: Array<vmbase.OverTimeFrame>): any {
            let self = this;
            let overTime = self.findOverTimeById(appId, self.lstAppOt());
            let masterInfo = self.findMasterInfo(self.lstAppMaster(), appId);
            let app = self.findCommon(self.lstAppCommon(), appId);
            let appPre = self.fomartOverTimeBf(app, overTime, masterInfo);
            let appResContent = '';
            //thuc te
            let appRes = self.convertFrameTime(lstFrameRes);
            appResContent = getText('CMM045_274') + appRes;


            let appInfor = {
                appPre: getText('CMM045_272') + appPre.appContent,
                appRes: lstFrameRes.length == 0 ? '' : appResContent
            }
            return appInfor;
        }
        findCommon(lstAppCommon: Array<vmbase.ApplicationDto_New>, appId: string): any {
            return _.find(lstAppCommon, function(app) {
                return app.applicationID == appId;
            });
        }
        formatGoBack(app: vmbase.ApplicationDto_New, goBack: vmbase.AppGoBackInfoFull, masterInfo: vmbase.AppMasterInfo): vmbase.DataModeApp {
            let self = this;
            let applicant: string = masterInfo.workplaceName + '<br/>' + masterInfo.empName;
            let go = goBack.goWorkAtr1 == 0 ? '' : ' ' + getText('CMM045_259') + goBack.workTimeStart1;
            //                        + self.convertTime_Short_HM(goBack.workTimeStart1);
            let back = goBack.backHomeAtr1 == 0 ? '' : ' ' + getText('CMM045_260') + goBack.workTimeEnd1;
            //                        + self.convertTime_Short_HM(goBack.workTimeEnd1);
            let reason = self.displaySet().appReasonDisAtr == 1 ? ' ' + app.applicationReason : '';
            let appContent2222 = getText('CMM045_258') + go + back + reason;
            let prePost = app.prePostAtr == 0 ? '事前' : '事後';
            let prePostApp = masterInfo.checkAddNote == true ? prePost + getText('CMM045_101') : prePost;
            let a: vmbase.DataModeApp = new vmbase.DataModeApp(app.applicationID, app.applicationType, 'chi tiet', applicant,
                masterInfo.dispName, prePostApp, self.convertDate(app.applicationDate), appContent2222, self.convertDateTime(app.inputDate),
                self.mode() == 0 ? self.convertStatus(app.reflectPerState) : self.convertStatusAppv(app.reflectPerState), masterInfo.phaseStatus,
                masterInfo.statusFrameAtr, app.version, masterInfo.checkTimecolor);
            return a;
        }

        mapData(lstApp: Array<vmbase.ApplicationDto_New>, lstMaster: Array<vmbase.AppMasterInfo>, lstGoBack: Array<vmbase.AppGoBackInfoFull>,
            lstOverTime: Array<vmbase.AppOverTimeInfoFull>, lstAppGroup: Array<vmbase.AppPrePostGroup>): Array<vmbase.DataModeApp> {
            let self = this;
            let lstData: Array<vmbase.DataModeApp> = [];
            _.each(lstApp, function(app: vmbase.ApplicationDto_New) {
                let masterInfo = self.findMasterInfo(lstMaster, app.applicationID);
                let data: vmbase.DataModeApp;
                if (app.applicationType == 0) {//over time
                    let overtTime = self.findOverTimeById(app.applicationID, lstOverTime);

                    if (self.mode() == 1 && app.prePostAtr == 1) {
                        data = self.fomartOverTimeAf(app, overtTime, masterInfo, lstAppGroup);
                    } else {
                        data = self.fomartOverTimeBf(app, overtTime, masterInfo);
                    }
                }
                if (app.applicationType == 4) {//goback
                    let goBack = self.findGoBack(app.applicationID, lstGoBack);
                    data = self.formatGoBack(app, goBack, masterInfo);
                }
                lstData.push(data);
            });
            return lstData;
        }
        findOverTimeById(appID: string, lstOverTime: Array<vmbase.AppOverTimeInfoFull>) {
            return _.find(lstOverTime, function(master) {
                return master.appID == appID;
            });
        }
        findGoBack(appID: string, lstGoBack: Array<vmbase.AppGoBackInfoFull>) {
            return _.find(lstGoBack, function(master) {
                return master.appID == appID;
            });
        }
        findMasterInfo(lstMaster: Array<vmbase.AppMasterInfo>, appId: string) {
            return _.find(lstMaster, function(master) {
                return master.appID == appId;
            });
        }

        convertStatus(status: number): string {
            switch (status) {
                case 0:
                    return '未';//下書き保存/未反映　=　未
                case 1:
                    return '承認済み';//反映待ち　＝　承認済み
                case 2:
                    return '反映済み';//反映済　＝　反映済み
                case 5:
                    return '差戻';//差し戻し　＝　差戻
                case 6:
                    return '否';//否認　=　否
                default:
                    return '取消';//取消待ち/取消済　＝　取消
            }
        }
        //UNAPPROVED:5
        //APPROVED: 4
        //CANCELED: 3
        //REMAND: 2
        //DENIAL: 1
        //-: 0
        convertStatusAppv(status: number): string {
            switch (status) {
                case 1:  //DENIAL: 1
                    return '否';
                case 2: //REMAND: 2
                    return '差戻';
                case 3: //CANCELED: 3
                    return '取消';
                case 4: //APPROVED: 4
                    return '承認済み';
                case 5: //UNAPPROVED:5
                    return '未';
                default: //-: 0
                    return '-';
            }
        }
        //yyyy/MM/dd
        convertDate(date: string) {
            let a: number = moment(date, 'YYYY/MM/DD').isoWeekday();
            switch (a) {
                case 1://Mon
                    return date + '(月)';
                case 2://Tue
                    return date + '(火)';
                case 3://Wed
                    return date + '(水)';
                case 4://Thu
                    return date + '(木)';
                case 5://Fri
                    return date + '(金)';
                case 6://Sat
                    return date + '(土)';
                default://Sun
                    return date + '(日)';
            }
        }
        //yyyy/MM/dd hh:mm
        convertDateTime(dateTime: string) {
            let a: number = moment(dateTime, 'YYYY/MM/DD hh:mm').isoWeekday();
            let date = dateTime.split(" ")[0];
            let time = dateTime.split(" ")[1];
            return this.convertDate(date) + ' ' + time;
        }
        /**
         * when click button 検索
         */
        filter() {
            block.invisible();
            if (nts.uk.ui.errors.hasError()) {
                block.clear();
                return;
            }
            let self = this;
            //check filter
            if (self.dateValue().startDate == null || self.dateValue().endDate == null) {//期間開始日付または期間終了日付が入力されていない
                nts.uk.ui.dialog.error({ messageId: "Msg_359" });
                block.clear();
                return;
            }
            if (self.mode() == 1 && self.selectedIds().length == 0) {//承認状況のチェックの確認
                nts.uk.ui.dialog.error({ messageId: "Msg_360" });
                block.clear();
                return;
            }
            let param: vmbase.AppListExtractConditionDto = new vmbase.AppListExtractConditionDto(self.dateValue().startDate, self.dateValue().endDate, self.mode(),
                null, self.findcheck(self.selectedIds(), 1), self.findcheck(self.selectedIds(), 2), self.findcheck(self.selectedIds(), 3),
                self.findcheck(self.selectedIds(), 4), self.findcheck(self.selectedIds(), 5), self.findcheck(self.selectedIds(), 6), self.selectedRuleCode(), [], '');
            service.getApplicationList(param).done(function(data) {
                console.log(data);
                //reset data
                self.lstAppCommon([]);
                self.lstAppMaster([]);
                self.lstAppOt([]);
                //luu
                character.save('AppListExtractCondition', param);
                let lstGoBack: Array<vmbase.AppGoBackInfoFull> = [];
                let lstAppGroup: Array<vmbase.AppPrePostGroup> = [];
                self.displaySet(new vmbase.ApprovalListDisplaySetDto(data.displaySet.advanceExcessMessDisAtr,
                    data.displaySet.hwAdvanceDisAtr, data.displaySet.hwActualDisAtr,
                    data.displaySet.actualExcessMessDisAtr, data.displaySet.otAdvanceDisAtr,
                    data.displaySet.otActualDisAtr, data.displaySet.warningDateDisAtr, data.displaySet.appReasonDisAtr));
                _.each(data.lstApp, function(app) {
                    self.lstAppCommon.push(new vmbase.ApplicationDto_New(app.applicationID, app.prePostAtr, app.inputDate, app.enteredPersonSID,
                        app.reversionReason, app.applicationDate, app.applicationReason, app.applicationType, app.applicantSID,
                        app.reflectPlanScheReason, app.reflectPlanTime, app.reflectPlanState, app.reflectPlanEnforce,
                        app.reflectPerScheReason, app.reflectPerTime, app.reflectPerState, app.reflectPerEnforce,
                        app.startDate, app.endDate, app.version));
                });
                _.each(data.lstMasterInfo, function(master) {
                    self.lstAppMaster.push(new vmbase.AppMasterInfo(master.appID, master.appType, master.dispName, master.empName, master.workplaceName,
                        master.statusFrameAtr, master.phaseStatus, master.checkAddNote, master.checkTimecolor));
                });
                _.each(data.lstAppGoBack, function(goback) {
                    lstGoBack.push(new vmbase.AppGoBackInfoFull(goback.appID, goback.goWorkAtr1, goback.workTimeStart1,
                        goback.backHomeAtr1, goback.workTimeEnd1, goback.goWorkAtr2, goback.workTimeStart2, goback.backHomeAtr2, goback.workTimeEnd2));
                });
                _.each(data.lstAppOt, function(overTime) {
                    let lstFrame: Array<vmbase.OverTimeFrame> = []
                    _.each(overTime.lstFrame, function(frame) {
                        lstFrame.push(new vmbase.OverTimeFrame(frame.attendanceType, frame.frameNo, frame.name,
                            frame.timeItemTypeAtr, frame.applicationTime));
                    });
                    self.lstAppOt.push(new vmbase.AppOverTimeInfoFull(overTime.appID, overTime.workClockFrom1, overTime.workClockTo1, overTime.workClockFrom2,
                        overTime.workClockTo2, overTime.total, lstFrame, overTime.overTimeShiftNight, overTime.flexExessTime));
                });
                _.each(data.lstAppGroup, function(group) {
                    lstAppGroup.push(new vmbase.AppPrePostGroup(group.preAppID, group.postAppID, group.time));
                });
                self.itemApplication([]);
                self.itemApplication.push(new vmbase.ChoseApplicationList(-1, '全件表示'));
                _.each(data.lstAppType, function(appType){
                    self.itemApplication.push(new vmbase.ChoseApplicationList(appType, self.findAppName(appType)));                          
                });
                let lstData = self.mapData(self.lstAppCommon(), self.lstAppMaster(), lstGoBack, self.lstAppOt(), lstAppGroup);
                self.lstApp(lstData);
                if (self.selectedCode() != -1) {
                    self.filterByAppType(self.selectedCode());
                } else {
                    self.items(lstData);
                    //mode approval - count
                    if (data.appStatusCount != null) {
                        self.approvalCount(new vmbase.ApplicationStatus(data.appStatusCount.unApprovalNumber, data.appStatusCount.approvalNumber,
                            data.appStatusCount.approvalAgentNumber, data.appStatusCount.cancelNumber, data.appStatusCount.remandNumner,
                            data.appStatusCount.denialNumber));
                    }
                    if (self.mode() == 1) {
                        $("#grid1").ntsGrid("destroy");
                        self.reloadGridApproval();
                    } else {
                        $("#grid2").ntsGrid("destroy");
                        self.reloadGridApplicaion();
                    }
                }


            }).always(() => {
                block.clear();
            });
        }
        findcheck(selectedIds: Array<any>, idCheck: number): boolean {
            let check = false;
            _.each(selectedIds, function(id) {
                if (id == idCheck) {
                    check = true;
                }
            });
            return check;
        }
        /**
         * When click button 承認
         */
        approval() {
            block.invisible();
            let self = this;
            let data = null;
            console.log(self.items());
            let lstApp = [];
            _.each(self.items(), function(item) {
                if (item.check) {
                    lstApp.push({ appId: item.appId, version: item.version });
                }
            });
            service.approvalListApp(lstApp).done(function() {
                self.filter();
            }).fail(function(res) {
                nts.uk.ui.dialog.alertError({ messageId: res.messageId });
            }).always(() => {
                block.clear();
            });
        }
        /**
         * When select combo box 申請種類
         */
        filterByAppType(appType: number) {
            let self = this;
            if (appType == -1) {//全件表示
                self.items(self.lstApp());
            } else {
                let lstAppFitler: Array<vmbase.DataModeApp> = _.filter(self.lstApp(), function(item) {
                    return item.appType == appType;
                });
                self.items([]);
                self.items(lstAppFitler);
            }
            if (self.mode() == 1) {
                self.approvalCount(self.countStatus(self.items()));
                $("#grid1").ntsGrid("destroy");
                self.reloadGridApproval();
            } else {
                $("#grid2").ntsGrid("destroy");
                self.reloadGridApplicaion();
            }

        }
        countStatus(lstApp: Array<vmbase.DataModeApp>): vmbase.ApplicationStatus{
            let unApprovalNumber = 0;
            let approvalNumber = 0;
            let approvalAgentNumber = 0;
            let cancelNumber = 0;
            let remandNumner = 0;
            let denialNumber = 0;
            _.each(lstApp, function(app){
                if(app.appStatus == '未'){ unApprovalNumber += 1; }//UNAPPROVED:5
                if(app.appStatus == '承認済み'){ approvalNumber += 1; }//APPROVED: 4
                if(app.appStatus == '-'){ approvalAgentNumber += 1; }//-: 0 
                if(app.appStatus == '取消'){ cancelNumber += 1; }//CANCELED: 3
                if(app.appStatus == '差戻'){ remandNumner += 1; }//REMAND: 2
                if(app.appStatus == '否'){ denialNumber += 1; }//DENIAL: 1
            })
            return new vmbase.ApplicationStatus(unApprovalNumber, approvalNumber,
                approvalAgentNumber, cancelNumber, remandNumner,denialNumber);
        }
        convertTime_Short_HM(time: number): string {
            let hh = Math.floor(time / 60);
            let min1: string = Math.floor(time % 60);
            let min = '';
            if (min1.length == 2) {
                min = min1;
            } else {
                min = '0' + min1;
            }
            return hh + ':' + min;
        }
        findAppName(appType: number){
            switch (appType) {
                case 0:
                    return '残業申請';
                case 2:
                    return '(火)';
                case 3:
                    return '(水)';
                case 4:
                    return '直行直帰申請';
                case 5:
                    return '(金)';
                case 6:
                    return '(土)';
                default:
                    return '全件表示';
            }
        }
        
    }

}
