module cmm045.a.viewmodel {
    import vmbase = cmm045.shr.vmbase;
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import character = nts.uk.characteristics;
    export class ScreenModel {
        roundingRules: KnockoutObservableArray<vmbase.ApplicationDisplayAtr> = ko.observableArray([]);
        selectedRuleCode: KnockoutObservable<any> = ko.observable(1);
        items: KnockoutObservableArray<vmbase.DataModeApp> = ko.observableArray([]);
        displaySet: KnockoutObservable<vmbase.ApprovalListDisplaySetDto> = ko.observable(null);
        approvalMode:  KnockoutObservable<boolean> = ko.observable(false);
        approvalCount: KnockoutObservable<vmbase.ApplicationStatus> = ko.observable(new vmbase.ApplicationStatus(0,0,0,0,0,0));
        itemList: KnockoutObservableArray<any>;
        selectedIds: KnockoutObservableArray<any> = ko.observableArray([]);
        dateValue: KnockoutObservable<any> = ko.observable({});
        itemApplication: KnockoutObservableArray<vmbase.ChoseApplicationList>;
        selectedCode: KnockoutObservable<number> = ko.observable(1);
        mode: KnockoutObservable<number> = ko.observable(1);
        constructor(){
            let self = this;
            self.itemList = ko.observableArray([
                {id: 1, name: getText('CMM045_20')},
                {id: 2, name: getText('CMM045_21')},
                {id: 3, name: getText('CMM045_22')},
                {id: 4, name: getText('CMM045_23')},
                {id: 5, name: getText('CMM045_24')},
                {id: 6, name: getText('CMM045_25')}
            ]);
            self.itemApplication = ko.observableArray([
                new vmbase.ChoseApplicationList(0, '全件表示'),
                new vmbase.ChoseApplicationList(1, '残業申請'),
            ]);
            self.selectedRuleCode.subscribe(function(codeChanged) {
                self.filter();
            });
            self.selectedCode.subscribe(function(codeChanged){
                
            });
        }
   
        start(): JQueryPromise<any>{
            block.invisible();
            let self = this;
            var dfd = $.Deferred();
            //get param url
            let url = $(location).attr('search');
            let urlParam :number = url.split("=")[1];
            self.mode(urlParam);
            character.restore("AppListExtractCondition").done((data) => {
                let a = data;
            });
            let param: vmbase.AppListExtractConditionDto = new vmbase.AppListExtractConditionDto('2018/01/18', '2018/01/20', self.mode(),
                    null, true, true, true, true, true, true, self.selectedRuleCode(), [], '');
            service.getApplicationDisplayAtr().done(function(data){
                //luu
                character.save('AppListExtractCondition', param);
                _.each(data, function(obj){
                    self.roundingRules.push(new vmbase.ApplicationDisplayAtr(obj.value, obj.localizedName));
                });
                service.getApplicationList(param).done(function(data){
                    console.log(data);
                    let lstApp: Array<vmbase.ApplicationDto_New> = [];
                    let lstMaster: Array<vmbase.AppMasterInfo> = []
                    let lstGoBack: Array<vmbase.AppGoBackInfoFull> = [];
                    let lstOverTime: Array<vmbase.AppOverTimeInfoFull> = [];
                    self.displaySet(new vmbase.ApprovalListDisplaySetDto(data.displaySet.advanceExcessMessDisAtr,
                            data.displaySet.hwAdvanceDisAtr,  data.displaySet.hwActualDisAtr, 
                            data.displaySet.actualExcessMessDisAtr, data.displaySet.otAdvanceDisAtr, 
                            data.displaySet.otActualDisAtr, data.displaySet.warningDateDisAtr, data.displaySet.appReasonDisAtr));
                    _.each(data.lstApp, function(app){
                        lstApp.push(new vmbase.ApplicationDto_New(app.applicationID, app.prePostAtr, app.inputDate, app.enteredPersonSID, 
                        app.reversionReason, app.applicationDate, app.applicationReason, app.applicationType, app.applicantSID,
                        app.reflectPlanScheReason, app.reflectPlanTime, app.reflectPlanState, app.reflectPlanEnforce,
                        app.reflectPerScheReason, app.reflectPerTime, app.reflectPerState, app.reflectPerEnforce,
                        app.startDate, app.endDate));
                    });
                    _.each(data.lstMasterInfo, function(master){
                        lstMaster.push(new vmbase.AppMasterInfo(master.appID, master.appType, master.dispName, master.empName, master.workplaceName, master.statusFrameAtr));
                    });
                    _.each(data.lstAppGoBack, function(goback){
                        lstGoBack.push(new vmbase.AppGoBackInfoFull(goback.appID, goback.goWorkAtr1, goback.workTimeStart1,
                            goback.backHomeAtr1, goback.workTimeEnd1, goback.goWorkAtr2, goback.workTimeStart2, goback.backHomeAtr2, goback.workTimeEnd2));
                    });
                    _.each(data.lstAppOt, function(overTime){
                        let lstFrame: Array<vmbase.OverTimeFrame> = []
                        _.each(overTime.lstFrame, function(frame){
                            lstFrame.push(new vmbase.OverTimeFrame(frame.attendanceType, frame.frameNo, frame.name,
                                            frame.timeItemTypeAtr, frame.applicationTime));
                        });
                        lstOverTime.push(new vmbase.AppOverTimeInfoFull(overTime.appID, overTime.workClockFrom1, overTime.workClockTo1, overTime.workClockFrom2,
                                overTime.workClockTo2, overTime.total, lstFrame, overTime.overTimeShiftNight, overTime.flexExessTime));
                    });
                    let lstData = self.mapData(lstApp, lstMaster, lstGoBack, lstOverTime);
                    self.items(lstData);
                    //mode approval - count
                    if(data.appStatusCount != null){
                        self.approvalCount(new vmbase.ApplicationStatus(data.appStatusCount.unApprovalNumber, data.appStatusCount.approvalNumber, 
                            data.appStatusCount.approvalAgentNumber, data.appStatusCount.cancelNumber, data.appStatusCount.remandNumner, 
                            data.appStatusCount.denialNumber));
                    }
                    self.reloadGridApproval();
                    dfd.resolve();
                });
            }).always(()=>{
                    block.clear(); 
            });
            return dfd.promise();
        }
        
        reloadGridApplicaion(){
            var self = this;
            $("#grid2").ntsGrid({
            width: '1200px',
            height: '700px',
            dataSource: self.items(),
            primaryKey: 'appId',
            virtualization: true,
            virtualizationMode: 'continuous',
            columns: [
                { headerText: getText('CMM045_50'), key: 'appId', dataType: 'string', width: '50px', unbound: false, ntsControl: 'Button' },
                { headerText: getText('CMM045_51'), key: 'applicant', dataType: 'string', width: '120px' },
                { headerText: getText('CMM045_52'), key: 'appName', dataType: 'string', width: '120px' },
                { headerText: getText('CMM045_53'), key: 'appAtr', dataType: 'string', width: '120px' },
                { headerText: getText('CMM045_54'), key: 'appDate', dataType: 'string', width: '150px' },
                { headerText: getText('CMM045_55'), key: 'appContent', dataType: 'string', width: '200px' },
                { headerText: getText('CMM045_56'), key: 'inputDate', dataType: 'string', width: '120px' },
                { headerText: getText('CMM045_57'), key: 'appStatus', dataType: 'string', width: '120px' },
                { headerText: 'ID', key: 'appId', dataType: 'string', width: '50px', ntsControl: 'Label', hidden: true}
            ], 
            features: [{ name: 'Resizing' },
                        { 
                            name: 'Selection',
                            mode: 'row',
                            multipleSelection: true
                        }
            ],
            ntsControls: [{ name: 'Checkbox', options: { value: 1, text: '' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true },
                        { name: 'Button', text: getText('CMM045_50'), controlType: 'Button' , enable: true}, 
            ]});
            $("#grid2").on("click", ".ntsButton", function(evt, ui){
                let _this = $(this);
                let id = _this.parents('tr').data('id');
                nts.uk.request.jump("../../../kaf/000/b/index.xhtml", { 'appID': id });
            });
        }
        
        reloadGridApproval(){
            var self = this;
            $("#grid1").ntsGrid({
            width: '1200px',
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
                { headerText: getText('CMM045_54'), key: 'appDate', dataType: 'string', width: '150px' },
                { headerText: getText('CMM045_55'), key: 'appContent', dataType: 'string', width: '200px' },
                { headerText: getText('CMM045_56'), key: 'inputDate', dataType: 'string', width: '120px' },
                { headerText: getText('CMM045_57'), key: 'appStatus', dataType: 'string', width: '120px',ntsControl: 'Label' },
                { headerText: getText('CMM045_58'), key: 'displayAppStatus', dataType: 'string', width: '120px' },
                { headerText: 'ID', key: 'appId', dataType: 'string', width: '50px', ntsControl: 'Label', hidden: true}
            ], 
            features: [{ name: 'Resizing' },
                        { 
                            name: 'Selection',
                            mode: 'row',
                            multipleSelection: true
                        }
            ],
            ntsControls: [{ name: 'Checkbox', options: { value: 1, text:'' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox' },
                        { name: 'Button', text: getText('CMM045_50'), controlType: 'Button' , enable: true}], 
            });
            
            $("#grid1").on("click", ".ntsButton", function(evt, ui){
                let _this = $(this);
                let id = _this.parents('tr').data('id');
                nts.uk.request.jump("../../../kaf/000/b/index.xhtml", { 'appID': id });
            });
            
            $("#grid1").setupSearchScroll("igGrid", true);
            
            _.each(self.items(), function(item){
                if(item.checkAtr == true){
                    $(".nts-grid-control-check-"+ item.appId).css("display", "none");
                }
                if (item.appStatus == '未') {
                    $(".nts-grid-control-appStatus-" + item.appId).addClass('unapprovalCell');
                }
                if(item.appStatus == '承認済み'){
                    $(".nts-grid-control-appStatus-" + item.appId).addClass('approvalCell');
                }
                if(item.appStatus == '取消'){
                    $(".nts-grid-control-appStatus-" + item.appId).addClass('cancelCell');
                }
                if(item.appStatus == '差戻'){
                    $(".nts-grid-control-appStatus-" + item.appId).addClass('remandCell');
                }
                if(item.appStatus == '否'){
                    $(".nts-grid-control-appStatus-" + item.appId).addClass('denialCell');
                }
            });
        }
        /**
         * format data: over time before
         */
        fomartOverTimeBf(app: vmbase.ApplicationDto_New, overTime: vmbase.AppOverTimeInfoFull, masterInfo: vmbase.AppMasterInfo): vmbase.DataModeApp{
            let self = this;
            let reason = self.displaySet().appReasonDisAtr == 1 ? ' ' + app.applicationReason : '';
            let applicant: string = masterInfo.workplaceName + ' ' + masterInfo.empName;
//            let appContent: string = getText('CMM045_268') + ' ' + self.convertTime_Short_HM(overTime.workClockFrom1) + getText('CMM045_100')+ self.convertTime_Short_HM(overTime.workClockTo1) + ' 残業合計' + '4:00' + reason;
            let appContent1111: string = getText('CMM045_268') + ' ' + overTime.workClockFrom1 + getText('CMM045_100')+ overTime.workClockTo1 + ' 残業合計' + '4:00' + reason;
            let a: vmbase.DataModeApp = new vmbase.DataModeApp(app.applicationID, app.applicationType, 'chi tiet', applicant,
                        masterInfo.dispName, app.prePostAtr == 0 ? '事前' : '事後', self.convertDate(app.applicationDate),appContent1111, self.convertDateTime(app.inputDate), 
                        self.mode() == 0 ? self.convertStatus(app.reflectPerState): self.convertStatusAppv(app.reflectPerState),'', masterInfo.statusFrameAtr);
            return a;
        }
        /**
         * 
         * format data: over time after
         */
        fomartOverTimeAf(overtime: any){
             
        }
        
        formatGoBack(app: vmbase.ApplicationDto_New, goBack: vmbase.AppGoBackInfoFull, masterInfo: vmbase.AppMasterInfo): vmbase.DataModeApp{
            let self = this;
            let applicant: string = masterInfo.workplaceName + ' ' + masterInfo.empName;
            let go = goBack.goWorkAtr1 == 0 ? '' : ' ' + getText('CMM045_259')+ goBack.workTimeStart1;
//                        + self.convertTime_Short_HM(goBack.workTimeStart1);
            let back = goBack.backHomeAtr1 == 0 ? '' : ' ' + getText('CMM045_260') + goBack.workTimeEnd1;
//                        + self.convertTime_Short_HM(goBack.workTimeEnd1);
            let reason = self.displaySet().appReasonDisAtr == 1 ? ' ' + app.applicationReason : '';
            let appContent2222 = getText('CMM045_258') + go + back + reason;
            let a: vmbase.DataModeApp = new vmbase.DataModeApp(app.applicationID, app.applicationType, 'chi tiet', applicant,
                        masterInfo.dispName, app.prePostAtr == 0 ? '事前' : '事後', self.convertDate(app.applicationDate),appContent2222, self.convertDateTime(app.inputDate), 
                        self.mode() == 0 ? self.convertStatus(app.reflectPerState): self.convertStatusAppv(app.reflectPerState),'', masterInfo.statusFrameAtr);
            return a;
        }
        
        mapData(lstApp: Array<vmbase.ApplicationDto_New>, lstMaster: Array<vmbase.AppMasterInfo>, 
                        lstGoBack: Array<vmbase.AppGoBackInfoFull>, lstOverTime: Array<vmbase.AppOverTimeInfoFull>): Array<vmbase.DataModeApp>{
            let self = this;
            let lstData: Array<vmbase.DataModeApp> = [];
            _.each(lstApp, function(app: vmbase.ApplicationDto_New){
                let masterInfo = self.findMasterInfo(lstMaster, app.applicationID, app.applicationType);
                let data: vmbase.DataModeApp;
                if(app.applicationType == 0){//over time
                    let overtTime = self.findOverTimeById(app.applicationID, lstOverTime);
                    data = self.fomartOverTimeBf(app, overtTime ,masterInfo);
                }
                if(app.applicationType == 4){//goback
                    let goBack = self.findGoBack(app.applicationID, lstGoBack);
                    data = self.formatGoBack(app, goBack, masterInfo);
                }
                lstData.push(data);
            });
            return lstData;
        }
        findOverTimeById(appID: string, lstOverTime: Array<vmbase.AppOverTimeInfoFull>){
            return _.find(lstOverTime, function(master){
                return master.appID == appID;
            });
        }
        findGoBack(appID: string, lstGoBack: Array<vmbase.AppGoBackInfoFull>){
            return _.find(lstGoBack, function(master){
                return master.appID == appID;
            });
        }
        findMasterInfo(lstMaster: Array<vmbase.AppMasterInfo>, appId: string, appType: number){
            return _.find(lstMaster, function(master){
                return master.appID == appId && master.appType == appType;
            });
        }
        
        convertStatus(status: number):string{
            switch(status){
                case 0:
                    return '未';
                case 1: 
                    return '反映待ち';
                case 2: 
                    return '反映済';
                case 3: 
                    return '取消待ち';
                case 4: 
                    return '取消済';
                case 5: 
                    return '差し戻し';
                case 6: 
                    return '否認';
                default: 
                    return '';
            }
        }
        //UNAPPROVED:5
        //APPROVED: 4
        //CANCELED: 3
        //REMAND: 2
        //DENIAL: 1
        //-: 0
        convertStatusAppv(status: number):string{
            switch(status){
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
        convertDate(date: string){
            let a: number = moment(date,'YYYY/MM/DD').isoWeekday();
            switch(a){
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
        convertDateTime(dateTime: string){
            let a: number = moment(dateTime,'YYYY/MM/DD hh:mm').isoWeekday();
            let date = dateTime.split(" ")[0];
            let time = dateTime.split(" ")[1];
            return this.convertDate(date) + ' ' + time;
        }
        /**
         * when click button 検索
         */
        filter(){
            block.invisible();
            let self = this;
            let param: vmbase.AppListExtractConditionDto = new vmbase.AppListExtractConditionDto(self.dateValue().startDate, self.dateValue().endDate, self.mode(),
                    null, self.findcheck(self.selectedIds(), 1), self.findcheck(self.selectedIds(), 2), self.findcheck(self.selectedIds(), 3),
                    self.findcheck(self.selectedIds(), 4), self.findcheck(self.selectedIds(), 5), self.findcheck(self.selectedIds(), 6), self.selectedRuleCode(), [], '');
            service.getApplicationList(param).done(function(data){
                console.log(data);
                //luu
                character.save('AppListExtractCondition', param);
                let lstApp: Array<vmbase.ApplicationDto_New> = [];
                let lstMaster: Array<vmbase.AppMasterInfo> = []
                let lstGoBack: Array<vmbase.AppGoBackInfoFull> = [];
                let lstOverTime: Array<vmbase.AppOverTimeInfoFull> = [];
                self.displaySet(new vmbase.ApprovalListDisplaySetDto(data.displaySet.advanceExcessMessDisAtr,
                        data.displaySet.hwAdvanceDisAtr,  data.displaySet.hwActualDisAtr, 
                        data.displaySet.actualExcessMessDisAtr, data.displaySet.otAdvanceDisAtr, 
                        data.displaySet.otActualDisAtr, data.displaySet.warningDateDisAtr, data.displaySet.appReasonDisAtr));
                _.each(data.lstApp, function(app){
                    lstApp.push(new vmbase.ApplicationDto_New(app.applicationID, app.prePostAtr, app.inputDate, app.enteredPersonSID, 
                    app.reversionReason, app.applicationDate, app.applicationReason, app.applicationType, app.applicantSID,
                    app.reflectPlanScheReason, app.reflectPlanTime, app.reflectPlanState, app.reflectPlanEnforce,
                    app.reflectPerScheReason, app.reflectPerTime, app.reflectPerState, app.reflectPerEnforce,
                    app.startDate, app.endDate));
                });
                _.each(data.lstMasterInfo, function(master){
                    lstMaster.push(new vmbase.AppMasterInfo(master.appID, master.appType, master.dispName, master.empName, master.workplaceName, master.statusFrameAtr));
                });
                _.each(data.lstAppGoBack, function(goback){
                    lstGoBack.push(new vmbase.AppGoBackInfoFull(goback.appID, goback.goWorkAtr1, goback.workTimeStart1,
                        goback.backHomeAtr1, goback.workTimeEnd1, goback.goWorkAtr2, goback.workTimeStart2, goback.backHomeAtr2, goback.workTimeEnd2));
                });
                _.each(data.lstAppOt, function(overTime){
                    let lstFrame: Array<vmbase.OverTimeFrame> = []
                    _.each(overTime.lstFrame, function(frame){
                        lstFrame.push(new vmbase.OverTimeFrame(frame.attendanceType, frame.frameNo, frame.name,
                                        frame.timeItemTypeAtr, frame.applicationTime));
                    });
                    lstOverTime.push(new vmbase.AppOverTimeInfoFull(overTime.appID, overTime.workClockFrom1, overTime.workClockTo1, overTime.workClockFrom2,
                            overTime.workClockTo2, overTime.total, lstFrame, overTime.overTimeShiftNight, overTime.flexExessTime));
                });
                let lstData = self.mapData(lstApp, lstMaster, lstGoBack, lstOverTime);
                self.items(lstData);
                //mode approval - count
                if(data.appStatusCount != null){
                    self.approvalCount(new vmbase.ApplicationStatus(data.appStatusCount.unApprovalNumber, data.appStatusCount.approvalNumber, 
                        data.appStatusCount.approvalAgentNumber, data.appStatusCount.cancelNumber, data.appStatusCount.remandNumner, 
                        data.appStatusCount.denialNumber));
                }
                $("#grid1").ntsGrid("destroy");
                self.reloadGridApproval();      
            }).always(()=>{
                    block.clear(); 
            });
        }
        findcheck(selectedIds: Array<any>, idCheck: number): boolean{
            let check = false;
            _.each(selectedIds, function(id){
                if(id == idCheck){
                    check = true;
                }
            });
            return check;
        }
        
        approval(){
            
        }
    } 
    
}
