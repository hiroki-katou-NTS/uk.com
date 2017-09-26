module nts.uk.at.view.kaf009.b.viewmodel {
    export class ScreenModel extends kaf000.b.viewmodel.ScreenModel{
        //current Data
        curentGoBackDirect: KnockoutObservable<GoBackDirectData>;
        //申請者
        employeeName: KnockoutObservable<string>;
        //Pre-POST
        prePostSelected: KnockoutObservable<number>;
        workEnable: KnockoutObservable<boolean>;
        workVisible: KnockoutObservable<boolean>;
        typeSiftVisible: KnockoutObservable<boolean>;
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
        reasonCombo: KnockoutObservableArray<ComboReason>;
        selectedReason: KnockoutObservable<string>;
        //MultilineEditor
        multilContent: KnockoutObservable<string>;
        multiOption: any;
        //Insert command
        command: KnockoutObservable<GoBackCommand>;
        //list Work Location 
        locationData: Array<IWorkLocation>;
        constructor(appType:number) {
            super(appType);
            var self = this;
            self.command = ko.observable(null);
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
            self.start();
        }
        /**
         * 
         */
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            //get Common Setting
            service.getGoBackSetting().done(function(settingData: CommonSetting) {
                debugger;
                let appID : string = "e3ee58d6-4ed3-4b88-a6e9-e91e2545ea7d";
                //get Reason
                self.setReasonControl(settingData.listReasonDto);
                //set employee Name
                self.employeeName(settingData.employeeName);
                //set Common Setting
                self.setGoBackSetting(settingData.goBackSettingDto);
                //Get data 
                service.getGoBackDirectDetail(appID).done(function(detailData: any) {
                    debugger;
                    //get all Location 
                    self.getAllWorkLocation();
                    self.workTypeName(detailData.workTypeName);
                    self.siftName(detailData.workTimeName);
                    self.workLocationName(detailData.workLocationName1);
                    self.workLocationName2(detailData.workLocationName2);
                    self.prePostSelected(detailData.prePostAtr);
                    self.multilContent(detailData.appReason);
                    self.selectedReason(detailData.appReasonId);
                    self.appDate(detailData.appDate);
                    //Set Value of control
                    self.setValueControl(detailData.goBackDirectlyDto);
                }).fail(function() {
                    dfd.resolve();
                });
                dfd.resolve();
            });
            return dfd.promise();
        }
        /**
         * update 
         */
        update() {
            let self = this;
            service.updateGoBackDirect(self.getCommand()).done(function() {
                //self.startPage();
                alert("Update Done");
            }).fail(function() {

            })
        }
        /**
         * get All Work Location
         */
        getAllWorkLocation() {
            let self = this;
            let arrTemp: Array<IWorkLocation> = [];
            service.getAllLocation().done(function(data: any) {
                _.forEach(data, function(value) {
                    arrTemp.push({ workLocationCode: value.workLocationCD, workLocationName: value.workLocationName });
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
            let location: IWorkLocation = _.find(self.locationData, function(o) { return o.workLocationCode == code });
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
            let command: GoBackCommand = new GoBackCommand();
            command.appID = "e3ee58d6-4ed3-4b88-a6e9-e91e2545ea7d";
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
            command.appCommand = new ApplicationCommand(
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
            return command;
        }

        /**
         * Set common Setting 
         */
        setGoBackSetting(data: GoBackDirectSetting) {
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
                switch (data.workChangeFlg) {
                    case 0: {

                        break;
                    }
                    case 1: {
                        //self.workChangeAtr(true);
                        break;
                    }
                    case 2: {
                        self.workVisible(false);
                        break;
                    }
                    default: {
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
        setValueControl(data: GoBackDirectData) {
            var self = this;
            //self.prePostSelected(data.workChangeAtr);
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
        setReasonControl(data: Array<ReasonDto>) {
            var self = this;
            let comboSource: Array<ComboReason> = [];
            comboSource.push(new ComboReason(0, '選択してください',''));
            _.forEach(data, function(value: ReasonDto) {
                comboSource.push(new ComboReason(value.displayOrder, value.reasonTemp,value.reasonID));
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
    }


    /**
     * Setting Data from Server 
     * 
     */
    class CommonSetting {
        employeeName: string;
        goBackSettingDto: GoBackDirectSetting;
        listReasonDto: Array<ReasonDto>;
        constructor(employeeName: string, goBackSettingDto: GoBackDirectSetting, listReasonDto: Array<ReasonDto>) {
            var self = this;
            self.employeeName = employeeName;
            self.goBackSettingDto = goBackSettingDto;
            self.listReasonDto = listReasonDto;
        }
    }

    /**
     * 直行直帰申請共通設定
     */
    class GoBackDirectSetting {
        workChangeFlg: number;
        workChangeTimeAtr: number;
        perfomanceDisplayAtr: number;
        contraditionCheckAtr: number;
        workType: number;
        lateLeaveEarlySettingAtr: number;
        commentContent1: string;
        commentFontWeight1: number;
        commentFontColor1: string;
        commentContent2: string;
        commentFontWeight2: number;
        commentFontColor2: string;
        constructor(workChangeFlg: number, workChangeTimeAtr: number, perfomanceDisplayAtr: number,
            contraditionCheckAtr: number, workType: number, lateLeaveEarlySettingAtr: number, commentContent1: string,
            commentFontWeight1: number, commentFontColor1: string, commentContent2: string, commentFontWeight2: number,
            commentFontColor2: string) {
            var self = this;
            self.workChangeFlg = workChangeFlg;
            self.workChangeTimeAtr = workChangeTimeAtr;
            self.perfomanceDisplayAtr = perfomanceDisplayAtr;
            self.contraditionCheckAtr = contraditionCheckAtr;
            self.workType = workType;
            self.lateLeaveEarlySettingAtr = lateLeaveEarlySettingAtr;
            self.commentContent1 = commentContent1;
            self.commentFontWeight1 = commentFontWeight1;
            self.commentFontColor1 = commentFontColor1;
            self.commentContent2 = commentContent2;
            self.commentFontWeight2 = commentFontWeight2;
            self.commentFontColor2 = commentFontColor2;
        }
    }
    /**
     * 
     */
    class ReasonDto {
        companyId: string;
        appType: number;
        reasonID: string;
        displayOrder: number;
        reasonTemp: string;
        constructor(companyId: string, appType: number, reasonID: string, displayOrder: number, reasonTemp: string) {
            var self = this;
            self.companyId = companyId;
            self.appType = appType;
            self.reasonID = reasonID;
            self.displayOrder = displayOrder;
            self.reasonTemp = reasonTemp;
        }

    };
    /**
     * 
     */
    class ComboReason {
        reasonCode: number;
        reasonName: string;
        reasonId: string;

        constructor(reasonCode: number, reasonName: string, reasonId:string) {
            this.reasonCode = reasonCode;
            this.reasonName = reasonName;
            this.reasonId = reasonId;
        }
    }

    /**
     * 
     * 直行直帰申請
     */
    class GoBackDirectData {
        appID: string;
        workTypeCD: string;
        siftCD: string;
        workChangeAtr: number;
        goWorkAtr1: number;
        backHomeAtr1: number;
        workTimeStart1: number;
        workTimeEnd1: number;
        workLocationCD1: string;
        goWorkAtr2: number;
        backHomeAtr2: number;
        workTimeStart2: number;
        workTimeEnd2: number;
        workLocationCD2: string;
        constructor(appID: string,
            workTypeCD: string,
            siftCD: string,
            workChangeAtr: number,
            goWorkAtr1: number,
            backHomeAtr1: number,
            workTimeStart1: number,
            workTimeEnd1: number,
            workLocationCD1: string,
            goWorkAtr2: number,
            backHomeAtr2: number,
            workTimeStart2: number,
            workTimeEnd2: number,
            workLocationCD2: string) {
            this.appID = appID;
            this.siftCD = siftCD;
            this.workChangeAtr = workChangeAtr;
            this.goWorkAtr1 = goWorkAtr1;
            this.backHomeAtr1 = backHomeAtr1;
            this.workTimeStart1 = workTimeStart1;
            this.workTimeEnd1 = workTimeEnd1;
            this.workLocationCD1 = workLocationCD1;
            this.goWorkAtr2 = goWorkAtr2;
            this.backHomeAtr2 = backHomeAtr2;
            this.workTimeStart2 = workTimeStart2;
            this.workTimeEnd2 = workTimeEnd2;
            this.workLocationCD2 = workLocationCD2;
        }
    }
    /**
     * 
     */
    interface IWorkLocation {
        workLocationCode: string;
        workLocationName: string;
    }
    /**
     * Application detail
     */
    export class ApplicationCommand {
        applicationID: string;
        appReasonID: string;
        prePostAtr: number;
        inputDate: string;
        enteredPersonSID: string;
        reversionReason: string;
        applicationDate: string;
        applicationReason: string;
        applicationType: number;
        applicantSID: string;
        reflectPlanScheReason: number;
        reflectPlanTime: string;
        reflectPlanState: number;
        reflectPlanEnforce: number;
        reflectPerScheReason: number;
        reflectPerTime: string;
        reflectPerState: number;
        reflectPerEnforce: number;
        startDate: string;
        endDate: string;
        listPhase: any;
        constructor(
            appReasonID: string,
            prePostAtr: number,
            inputDate: string,
            enteredPersonSID: string,
            reversionReason: string,
            applicationDate: string,
            applicationReason: string,
            applicantSID: string,
            reflectPlanTime: string,
            reflectPerTime: string,
            startDate: string,
            endDate: string) {
            this.applicationID = "";
            this.appReasonID = appReasonID;
            this.prePostAtr = prePostAtr;
            this.inputDate = moment.utc(inputDate, "YYYY/MM/DD").toISOString();
            this.enteredPersonSID = enteredPersonSID;
            this.reversionReason = reversionReason;
            this.applicationDate = moment.utc(applicationDate, "YYYY/MM/DD").toISOString();
            this.applicationReason = applicationReason;
            this.applicationType = 4;
            this.applicantSID = applicantSID;
            this.reflectPlanScheReason = 1;
            this.reflectPlanTime = moment.utc(reflectPlanTime, "YYYY/MM/DD").toISOString();
            this.reflectPlanState = 1;
            this.reflectPlanEnforce = 1;
            this.reflectPerScheReason = 1;
            this.reflectPerTime = moment.utc(reflectPerTime, "YYYY/MM/DD").toISOString();
            this.reflectPerState = 1;
            this.reflectPerEnforce = 1;
            this.startDate = moment.utc(startDate, "YYYY/MM/DD").toISOString();
            this.endDate = moment.utc(endDate, "YYYY/MM/DD").toISOString();
            this.listPhase = null;
        }
    }
    /**
     * 
     */
    export class GoBackCommand {
        appID:string;
        workTypeCD: string;
        siftCD: string;
        workChangeAtr: number;
        goWorkAtr1: number;
        backHomeAtr1: number;
        workTimeStart1: number;
        workTimeEnd1: number;
        workLocationCD1: string;
        goWorkAtr2: number;
        backHomeAtr2: number;
        workTimeStart2: number;
        workTimeEnd2: number;
        workLocationCD2: string;
        appCommand: ApplicationCommand;
    }
}

