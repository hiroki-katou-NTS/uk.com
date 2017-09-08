module nts.uk.at.view.kaf009.b.viewmodel {
    export class ScreenModel {
        // date editor
        date: KnockoutObservable<string>;
        //time editor
        time: KnockoutObservable<string>;
        //check late
        late: KnockoutObservable<boolean>;
        late2: KnockoutObservable<boolean>;
        //check early
        early: KnockoutObservable<boolean>;
        early2: KnockoutObservable<boolean>;
        //labor time
        laborTime: KnockoutObservable<string>;
        //combobox
        ListReason: KnockoutObservableArray<Reason>;
        itemName: KnockoutObservable<string>;
        currentCode: KnockoutObservable<number>
        selectedCode: KnockoutObservable<string>;
        //MultilineEditor
        multilineeditor: any;
        //Switch Button 
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
        //Back Home
        backHomeAtr: KnockoutObservableArray<any>;
        selectedBack: any;
        //Go Work
        goWorkAtr: KnockoutObservableArray<any>;
        selectedGo: any;
        //Back Home 2
        selectedBack2: any;
        //Go Work 2
        selectedGo2: any;
        //workLocation 
        workLocationCD: KnockoutObservable<string>;
        //comboBox 
        itemComboBox: KnockoutObservableArray<ItemCombo>;
        selectedComboCode: KnockoutObservable<string>;
        //CheckBox 
        isWorkChange: KnockoutObservable<boolean>;
        
        //Time with day 
        
        enable: KnockoutObservable<boolean>;
        readonly: KnockoutObservable<boolean>;
        timeOfDay: KnockoutObservable<number>;
        time: KnockoutObservable<number>;
        time2: KnockoutObservable<number>;
        
        
        constructor() {
            var self = this;
            //date editor
            self.date = ko.observable(moment().format('YYYY/MM/DD'));
            //self.date = ko.observable('20000101');
            //time editor
            self.time = ko.observable("12:00");
            //check late
            self.late = ko.observable(true);
            self.late2 = ko.observable(true);
            // check early
            self.early = ko.observable(false);
            self.early2 = ko.observable(false);
            //Checkbox 
            self.isWorkChange = ko.observable(true);
            //labor time 
            self.laborTime = ko.observable("00:00");
            //combobox
            self.ListReason = ko.observableArray([
                new Reason('1', 'name1'),
                new Reason('2', 'name2'),
                new Reason('3', 'name3')
            ]);
            self.itemName = ko.observable('');
            self.currentCode = ko.observable(3);
            self.selectedCode = ko.observable('0002');
            //Switch Button
            self.roundingRules = ko.observableArray([
                { code: '1', name: nts.uk.resource.getText("KAF009_9") },
                { code: '2', name: nts.uk.resource.getText("KAF009_10") }
            ]);
            self.selectedRuleCode = ko.observable(1);
            //Go Work
            self.goWorkAtr = ko.observableArray([
                { goCode: '1', goName: nts.uk.resource.getText("KAF009_16") },
                { goCode: '2', goName: nts.uk.resource.getText("KAF009_17") }
            ]);
            self.selectedGo = ko.observable(1);
            //BackHome 
            self.backHomeAtr = ko.observableArray([
                { backCode: '1', backName: nts.uk.resource.getText("KAF009_18") },
                { backCode: '2', backName: nts.uk.resource.getText("KAF009_19") }
            ]);
            self.selectedBack = ko.observable(1);
            //Go Work 2
            self.selectedGo2 = ko.observable(1);
            //BackHome 2
            self.selectedBack2 = ko.observable(1);
            //Work Location 
            self.workLocationCD = ko.observable('');
            //ComboBox 
            self.itemComboBox = ko.observableArray([
                new ItemCombo('基本給1', '基本給'),
                new ItemCombo('基本給2', '役職手当'),
                new ItemCombo('0003', '基本給')
            ]);
            //Time with Day 
            
            self.enable = ko.observable(true);
            self.readonly = ko.observable(false);
            
            self.timeOfDay = ko.observable(2400);
            self.time = ko.observable(1200);
            self.time2 = ko.observable(3200);
            //CheckBox 
           
            self.selectedComboCode = ko.observable('0002');
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
            
            self.isWorkChange.subscribe(function(){
                    
            
            });

        }

        startPage(): JQueryPromise<any> {
            var self = this;

            var dfd = $.Deferred();
            //String appID = "123456789";
            //Get data 
            //            service.getGoBackDirectly().done(function(goBackDirect: any) {
            //                console.log(goBackDirect);
            //          ;
            //      }

            //Get Setting
            service.getGoBackSetting().done(function(setting: any) {
                console.log(setting);
            });
            dfd.resolve();
            return dfd.promise();
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
            if(data != undefined){
               self.isWorkChange(data.workChangeFlg >0 ? false: false);
                
            }
        }
        /**
         * New Screen Mode 
         */
        newScreenMode(){
            let self = this;
            self.selectedRuleCode(1); 
            self.date(moment().format("YYYY/MM/DD"));   
        
        }

    }
    /**
     * 理由
     */
    class Reason {
        code: string;
        name: string;
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
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
    class ItemCombo {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}