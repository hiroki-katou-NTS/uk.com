module nts.uk.at.view.kaf009.a.viewmodel {
    import common = nts.uk.at.view.kaf009.share.common;
    export class ScreenModel {
        //kaf000
        kaf000_a: kaf000.a.viewmodel.ScreenModel;
        //current Data
        curentGoBackDirect: KnockoutObservable<common.GoBackDirectData>;
        //申請者
        employeeName: KnockoutObservable<string>;
        //Pre-POST
        prePostSelected: KnockoutObservable<number>;
        workEnable: KnockoutObservable<boolean>;
        workVisible : KnockoutObservable<boolean>;
        typeSiftVisible : KnockoutObservable<boolean>;
        // 申請日付
        appDate: KnockoutObservable<string>;
        //TIME LINE 1
        timeStart1: KnockoutObservable<number>;
        timeEnd1: KnockoutObservable<number>;   
        //場所名前 
        workLocationCD: KnockoutObservable<string>;
        workLocationName: KnockoutObservable<string>;
        //comment 
        commentGo1: KnockoutObservable<string>;
        commentBack1: KnockoutObservable<string>;
        //switch button selected
        selectedBack: any;
        selectedGo: any;
        //TIME LINE 2
        timeStart2: KnockoutObservable<number>;
        timeEnd2: KnockoutObservable<number>;
        //場所名前 
        workLocationCD2: KnockoutObservable<string>;
        workLocationName2: KnockoutObservable<string>;
        //comment
        commentGo2: KnockoutObservable<string>;
        commentBack2: KnockoutObservable<string>;
        //color, font Weight
        colorGo: KnockoutObservable<string>;
        colorBack: KnockoutObservable<string>;
        fontWeightGo: KnockoutObservable<number>;
        fontWeightBack: KnockoutObservable<number>;
        //Back Home 2
        selectedBack2: any;
        //Go Work 2
        selectedGo2: any;
        //勤務を変更する 
        workChangeAtr: KnockoutObservable<boolean>;
        //勤務種類
        workTypeCd: KnockoutObservable<string>;
        workTypeName: KnockoutObservable<string>;
        //勤務種類
        siftCD: KnockoutObservable<string>;
        siftName: KnockoutObservable<string>;
        //comboBox 定型理由
        reasonCombo: KnockoutObservableArray<common.ComboReason>;
        selectedReason: KnockoutObservable<string>;
        //MultilineEditor
        multilContent: KnockoutObservable<string>;
        multiOption: any;
        //Insert command
        command: KnockoutObservable<common.GoBackCommand>;
        //list Work Location 
        locationData: Array<common.IWorkLocation>;
        //Approval 
        approvalSource: Array<common.AppApprovalPhase> = [];
        constructor() {
            let self = this;
            self.command = ko.observable(null);
            //KAF000_A
            self.kaf000_a = new kaf000.a.viewmodel.ScreenModel();
            self.locationData = [];
            //申請者
            self.employeeName = ko.observable("");
            //申請日付
            self.appDate = ko.observable(moment().format('YYYY/MM/DD'));
            //PRE_POST Switch Button
            self.prePostSelected = ko.observable(1);
            self.workEnable = ko.observable(true);
            self.workVisible = ko.observable(true);
            self.typeSiftVisible = ko.observable(true);
            //time Value 
            self.timeStart1 = ko.observable(0);
            self.timeEnd1 = ko.observable(0);
            self.timeStart2 = ko.observable(0);
            self.timeEnd2 = ko.observable(0);
            //switch button Selected
            self.selectedGo = ko.observable(1);
            self.selectedBack = ko.observable(1);
            //Go Work 2
            self.selectedGo2 = ko.observable(1);
            //BackHome 2
            self.selectedBack2 = ko.observable(1);
            //Work Location 
            self.workLocationCD = ko.observable('');
            self.workLocationName = ko.observable('');
            //Work Location 2
            self.workLocationCD2 = ko.observable('');
            self.workLocationName2 = ko.observable('');
            //comment 
            self.commentGo1 = ko.observable('');
            self.commentBack1 = ko.observable('');
            self.commentGo2 = ko.observable('');
            self.commentBack2 = ko.observable('');
            self.colorGo = ko.observable('#000000');
            self.colorBack = ko.observable('#000000');
            self.fontWeightGo = ko.observable(0);
            self.fontWeightBack = ko.observable(0);

            //Checkbox 勤務を変更する
            self.workChangeAtr = ko.observable(true);
            self.workTypeCd = ko.observable('');
            self.workTypeName = ko.observable('');
            self.siftCD = ko.observable('');
            self.siftName = ko.observable('');
            //ComboBox Reason
            self.reasonCombo = ko.observableArray([]);
            self.selectedReason = ko.observable('');
            //MultilineEditor 
            self.multilContent = ko.observable('');
            self.multiOption = ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                resizeable: false,
                placeholder: "Placeholder for text editor",
                width: "500",
                textalign: "left",
            }));
            //勤務を変更する
            self.workChangeAtr.subscribe(function(value) {
                self.workEnable(value);
            });
            //startPage 009a AFTER start 000_A
            self.kaf000_a.start().done(function(){
                debugger;
                self.getApprovalList(self.kaf000_a.approvalRoot().beforeApprovers);
                
            })
        }
        /**
         * 
         */
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            //get Common Setting
            service.getGoBackSetting().done(function(settingData: common.CommonSetting) {
                //get all work Location source
                self.getAllWorkLocation();
                if(!nts.uk.util.isNullOrEmpty(settingData)){
                    //get Reason
                    self.setReasonControl(settingData.listReasonDto);
                    //set employee Name
                    self.employeeName(settingData.employeeName);
                    //set Common Setting
                    self.setGoBackSetting(settingData.goBackSettingDto);
                }
                dfd.resolve();
            });
            return dfd.promise();
        }
        
        /**
         * get approver list 
         */
        getApprovalList(beforeApprovers: Array<common.AppApprovalPhase>) {
            let self = this;
            let approvalList = [];
            _.forEach(a, appPhase => {
                let b = new common.AppApprovalPhase(
                    "",
                    appPhase.approvalForm,
                    appPhase.orderNumber,
                    1,
                    []);
                _.forEach(appPhase.approvers, appFrame => {
                    let c = new common.ApprovalFrame(
                        "",
                        appFrame.orderNumber,
                        []);
                    let d = new common.ApproveAccepted(
                        "",
                        appFrame.sid,
                        0,
                        appFrame.confirmPerson ? 1 : 0,
                        self.appDate(),
                        "",
                        appFrame.sid);
                    c.approveAcceptedCmds.push(d);
                    b.approvalFrameCmds.push(c);
                });
                approvalList.push(b);
            });
            debugger;
            self.approvalSource = approvalList;
        }
        /**
         * insert
         */
        insert() {
            let self = this;
            service.insertGoBackDirect(self.getCommand()).done(function() {
                alert("Insert Done");
            }).fail(function() {

            })
        }
        /**
         * get All Work Location
         */
        getAllWorkLocation() {
            let self = this;
            let arrTemp: Array<common.IWorkLocation> = [];
            service.getAllLocation().done(function(data: any) {
                _.forEach(data, function(value) {
                    if(!nts.uk.util.isNullOrUndefined(value)){
                        arrTemp.push({ workLocationCode: value.workLocationCD, workLocationName: value.workLocationName });
                    };
                });
                self.locationData = arrTemp;
            }).fail(function() {

            })
        }
        /**
         * find Work Location Name from Work Location Code
         */
        findWorkLocationName(code: string) {
            let self = this;
            let locationName: string = "";
            let location: common.IWorkLocation = _.find(self.locationData, function(o) { return o.workLocationCode == code });
            locationName = location.workLocationName;
            return locationName;
        }

        /**
         * 1: insert 
         * 2: update 
         * 3: delete
         */
        getCommand() {
            let self = this; 
            let command: common.GoBackCommand = new common.GoBackCommand();
            command.workTypeCD = self.workTypeCd();
            command.siftCD = self.siftCD();
            command.workChangeAtr = self.workChangeAtr() == true ? 1 : 0;
            command.goWorkAtr1 = self.selectedGo();
            command.backHomeAtr1 = self.selectedBack();
            command.workTimeStart1 = self.timeStart1();
            command.workTimeEnd1 = self.timeEnd1();
            command.goWorkAtr2 = self.selectedGo2();
            command.backHomeAtr2 = self.selectedBack2();
            command.workTimeStart2 = self.timeStart2();
            command.workTimeEnd2 = self.timeEnd2();
            command.workLocationCD1 = self.workLocationCD();
            command.workLocationCD2 = self.workLocationCD2();
            
            let appCommand : common.ApplicationCommand  = new common.ApplicationCommand(
                self.selectedReason(),
                self.prePostSelected(),
                self.appDate(),
                self.employeeName(),
                self.multilContent(),
                self.appDate(),
                self.multilContent(),
                self.employeeName(),
                self.appDate(),
                self.appDate(),
                self.appDate(),
                self.appDate());
            
            let commandTotal = {
                goBackCommand : command,
                appCommand : appCommand,
                appApprovalPhaseCmds : self.approvalSource
                }
            return commandTotal;
            
        }

        /**
         * Set common Setting 
         */
        setGoBackSetting(data: common.GoBackDirectSetting) {
            let self = this;
            if (data != undefined) {
                self.commentGo1(data.commentContent1);
                self.commentGo2(data.commentContent2);
                self.commentBack1(data.commentContent1);
                self.commentBack2(data.commentContent2);
                self.colorGo(data.commentFontColor1);
                self.colorBack(data.commentFontColor2);
                self.fontWeightGo(data.commentFontWeight1);
                self.fontWeightBack(data.commentFontWeight2);
                switch(data.workChangeFlg){
                    case 0:{
                        
                        break;
                    }
                    case 1:{
                        //self.workChangeAtr(true);
                        break;
                    }
                    case 2:{
                        self.workVisible(false);
                        break;
                    }
                    default :{
                        //self.workChangeAtr(true);
                        self.workVisible(false);
                        self.typeSiftVisible(false);
                        break;
                    }
                }
                self.workEnable(data.workChangeFlg == 1 ? true : false);
            }
        }
        /**
         * set data from Server 
         */
        setValueControl(data: common.GoBackDirectData) {
            var self = this;
            self.prePostSelected(data.workChangeAtr);
            //Line 1
            self.timeStart1(data.workTimeStart1);
            self.timeEnd1(data.workTimeEnd1);
            self.selectedGo(data.goWorkAtr1);
            self.selectedBack(data.backHomeAtr1);
            self.workLocationCD(data.workLocationCD1);
            //LINe 2
            self.timeStart2(data.workTimeStart2);
            self.timeEnd2(data.workTimeEnd2);
            self.selectedGo2(data.goWorkAtr2);
            self.selectedBack2(data.backHomeAtr2);
            self.workLocationCD2(data.workLocationCD2);
            //workType, Sift
            self.workChangeAtr(data.workChangeAtr == 1 ? true : false);
            self.workTypeCd(data.workTypeCD);
            self.siftCD(data.siftCD);
        }
        /**
         * set reason 
         */
        setReasonControl(data: Array<common.ReasonDto>) {
            var self = this;
            let comboSource: Array<common.ComboReason> = [];
            comboSource.push(new common.ComboReason(0,'選択してください',""));
            _.forEach(data, function(value: common.ReasonDto) {
                comboSource.push(new common.ComboReason(value.displayOrder, value.reasonTemp, value.reasonID));
            });
            self.reasonCombo(_.orderBy(comboSource, 'reasonCode', 'asc'));
        }

        /**
         * KDL010_勤務場所選択を起動する
         */
        openLocationDialog(line: number) {
            var self = this;
            nts.uk.ui.block.invisible();
            if (line == 1) {
                nts.uk.ui.windows.setShared('KDL010SelectWorkLocation', self.workLocationCD());
            } else {
                nts.uk.ui.windows.setShared('KDL010SelectWorkLocation', self.workLocationCD2());
            };
            nts.uk.ui.windows.sub.modal("/view/kdl/010/a/index.xhtml", { dialogClass: "no-close" }).onClosed(() => {
                var self = this;
                var returnWorkLocationCD = nts.uk.ui.windows.getShared("KDL010workLocation");
                if (returnWorkLocationCD !== undefined) {
                    if (line == 1) {
                        debugger;
                        self.workLocationCD(returnWorkLocationCD);
                        self.workLocationName(self.findWorkLocationName(returnWorkLocationCD));
                    } else {
                        self.workLocationCD2(returnWorkLocationCD);
                        self.workLocationName2(self.findWorkLocationName(returnWorkLocationCD));
                    };
                    nts.uk.ui.block.clear();
                }
                else {
                    self.workLocationCD = ko.observable("");
                    nts.uk.ui.block.clear();
                }
            });
        }
        /**
         * KDL003
         */
        openDialogKdl003() {
            let self = this;
            let workTypeCodes = [];

            let workTimeCodes = [];
            nts.uk.ui.windows.setShared('parentCodes', {
                workTypeCodes: workTypeCodes,
                selectedWorkTypeCode: self.workTypeCd(),
                workTimeCodes: workTimeCodes,
                selectedWorkTimeCode: self.siftCD()
            }, true);

            nts.uk.ui.windows.sub.modal('/view/kdl/003/a/index.xhtml').onClosed(function(): any {
                //view all code of selected item 
                var childData = nts.uk.ui.windows.getShared('childData');
                if (childData) {
                    self.workTypeCd(childData.selectedWorkTypeCode);
                    self.workTypeName(childData.selectedWorkTypeName);
                    self.siftCD(childData.selectedWorkTimeCode);
                    self.siftName(childData.selectedWorkTimeName);
                }
            })
        }
        
        /**
         * Jump to CMM018 Screen
         */
        openCMM018(){
            nts.uk.request.jump("com", "/view/cmm/018/a/index.xhtml", {screen: 'Application', employeeId: "000100003"}); 
        }
    }
}

