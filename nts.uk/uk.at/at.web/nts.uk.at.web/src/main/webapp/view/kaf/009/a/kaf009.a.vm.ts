module nts.uk.at.view.kaf009.a.viewmodel {
    export class ScreenModel {
        //current Data
        curentGoBackDirect: KnockoutObservable<GoBackDirectData>;
        //申請者
        employeeName: KnockoutObservable<string>;
        //Pre-POST
        prePostSelected: KnockoutObservable<number>;
        // 申請日付
        appDate: KnockoutObservable<string>;
        //TIME LINE 1
        timeStart1: KnockoutObservable<number>;
        timeEnd1: KnockoutObservable<number>;
        //場所名前 
        workLocationCD: KnockoutObservable<string>;
        workLocationName: KnockoutObservable<string>;
        //switch button selected
        selectedBack: any;
        selectedGo: any;
        //TIME LINE 2
        timeStart2: KnockoutObservable<number>;
        timeEnd2: KnockoutObservable<number>;
        //場所名前 
        workLocationCD2: KnockoutObservable<string>;
        workLocationName2: KnockoutObservable<string>;
        //Back Home 2
        selectedBack2: any;
        //Go Work 2
        selectedGo2: any;
        //勤務を変更する 
        isWorkChange: KnockoutObservable<boolean>;
        //勤務種類
        workTypeCd: KnockoutObservable<string>;
        workTypeName: KnockoutObservable<string>;
        //勤務種類
        siftCd: KnockoutObservable<string>;
        siftName: KnockoutObservable<string>;
        //comboBox 定型理由
        reasonCombo: KnockoutObservableArray<ComboReason>;
        selectedReason: KnockoutObservable<string>;
        //MultilineEditor
        multilineeditor: any;

        constructor() {
            var self = this;
            //申請者
            self.employeeName = ko.observable("");
            //申請日付
            self.appDate = ko.observable(moment().format('YYYY/MM/DD'));
            //PRE_POST Switch Button
            self.prePostSelected = ko.observable(1);
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
            self.workLocationCD = ko.observable('001');
            self.workLocationName = ko.observable('Name1');
            //Work Location 2
            self.workLocationCD2 = ko.observable('002');
            self.workLocationName2 = ko.observable('Name2');

            //Checkbox 勤務を変更する
            self.isWorkChange = ko.observable(true);
            self.workTypeCd = ko.observable('');
            self.workTypeName = ko.observable('');
            self.siftCd = ko.observable('');
            self.siftName = ko.observable('');

            //ComboBox Reason
            self.reasonCombo = ko.observableArray([
                new ComboReason('0001', '基本給'),
                new ComboReason('0002', '役職手当'),
                new ComboReason('0003', '基本給')
            ]);
            //Selected Reason 
            self.selectedReason = ko.observable('0002');
            //MultilineEditor 
            self.multilineeditor = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                    resizeable: false,
                    placeholder: "Placeholder for text editor",
                    width: "500",
                    textalign: "left"
                })),
            };
            //勤務を変更する
            self.isWorkChange.subscribe(function() {
            });

        }
        /**
         * init Setting
         */
        initPage() {
            var self = this;

        }
        /**
         * 
         */
        startPage(): JQueryPromise<any> {
            var self = this;

            var dfd = $.Deferred();
            //get Common Setting
            service.getGoBackSetting().done(function(settingData: CommonSetting) {
                //get Setting common
                debugger;
                //Get data 
                service.getGoBackDirectly().done(function(goBackDirectData: GoBackDirectData) {
                    //Set Value of control
                    self.setValueControl(goBackDirectData);
                    dfd.resolve();
                }).fail(function(){
                    dfd.resolve();                    
                });
            });
            return dfd.promise();
        }
        
        /**
         * set data from Server 
         */
        setValueControl(data: GoBackDirectData) {
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
            self.workTypeCd(data.workTypeCD);
            self.siftCd(data.siftCd);
        }



        /**
         * KDL010_勤務場所選択を起動する
         */
        openLocationDialog() {
            var self = this;
            nts.uk.ui.block.invisible();
            nts.uk.ui.windows.setShared('KDL010SelectWorkLocation', self.workLocationCD());
            nts.uk.ui.windows.sub.modal("/view/kdl/010/a/index.xhtml", { dialogClass: "no-close" }).onClosed(() => {
                var self = this;
                var returnWorkLocationCD = nts.uk.ui.windows.getShared("KDL010workLocation");
                if (returnWorkLocationCD !== undefined) {
                    self.workLocationCD(returnWorkLocationCD);
                    nts.uk.ui.block.clear();
                }
                else {
                    self.workLocationCD = ko.observable("");
                    nts.uk.ui.block.clear();
                }
            });

        }

        /**
         * Set common Setting 
         */
        setCommonSetting(data: CommonSetting) {
            let self = this;
            if (data != undefined) {
                self.isWorkChange(data.workChangeFlg > 0 ? false : false);

            }
        }
        /**
         * New Screen Mode 
         */
        newScreenMode() {
            let self = this;
            self.selectedRuleCode(1);
            self.date(moment().format("YYYY/MM/DD"));

        }

    }
    /**
     * 直行直帰申請共通設定
     */
    class CommonSetting {
        workChangeFlg: number;
        workChangTimeAtr: number;
        perfomanceDisplayAtr: number;
        contraditionCheckAtr: number;
        workType: number;
        lateLeaveEarlySettingAtr: number;
        commentContent1: String;
        commentFontWeight1: number;
        commentFontColor1: String;
        commentContent2: String;
        commentFontWeight2: number;
        commentFontColor2: String;
        constructor(workChangeFlg: number, workChangTimeAtr: number, perfomanceDisplayAtr: number,
            contraditionCheckAtr: number, workType: number, lateLeaveEarlySettingAtr: number, commentContent1: String,
            commentFontWeight1: number, commentFontColor1: String, commentContent2: String, commentFontWeight2: number,
            commentFontColor2: String) {
            var self = this;
            self.workChangeFlg = workChangeFlg;
            self.workChangTimeAtr = workChangTimeAtr;
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
     * 直行直帰申請
     */
    class GoBackDirectData {
        appID: String;
        workTypeCD: String;
        siftCd: String;
        workChangeAtr: number;
        goWorkAtr1: number;
        backHomeAtr1: number;
        workTimeStart1: number;
        workTimeEnd1: number;
        workLocationCD1: String;
        goWorkAtr2: number;
        backHomeAtr2: number;
        workTimeStart2: number;
        workTimeEnd2: number;
        workLocationCD2: String;
        constructor(appID: String,
            workTypeCD: String,
            siftCd: String,
            workChangeAtr: number,
            goWorkAtr1: number,
            backHomeAtr1: number,
            workTimeStart1: number,
            workTimeEnd1: number,
            workLocationCD1: String,
            goWorkAtr2: number,
            backHomeAtr2: number,
            workTimeStart2: number,
            workTimeEnd2: number,
            workLocationCD2: String) {
            this.appID = appID;
            this.siftCd = siftCd;
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
    class ComboReason {
        reasonCode: string;
        reasonName: string;

        constructor(reasonCode: string, reasonName: string) {
            this.reasonCode = reasonCode;
            this.reasonName = reasonName;
        }
    }
}