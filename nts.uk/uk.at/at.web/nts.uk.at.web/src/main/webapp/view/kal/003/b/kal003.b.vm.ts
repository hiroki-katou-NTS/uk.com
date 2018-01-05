module nts.uk.at.view.kal003.b{
    export module viewmodel {
        import block = nts.uk.ui.block;
        import errors = nts.uk.ui.errors;
        import dialog = nts.uk.ui.dialog;
        import windows = nts.uk.ui.windows;
        import resource = nts.uk.resource;
        import shareModel = nts.uk.at.view.kal003.share.model;

        export class ScreenModel {
            
            intGroupCondition = new shareModel.GroupCondition({
                groupOperator: 0
                , groupListCondition: ([])
            });
            initCompoundCondition = new  shareModel.CompoundCondition ({
                group1Condition: this.intGroupCondition
                , hasGroup2: false
                , group2Condition: this.intGroupCondition
                , operatorBetweenG1AndG2: 0
            });
            currentErrAlaCheckCondition: KnockoutObservable<shareModel.SettingCdlKal003B>;
            // list item check
            listTypeCheckWorkRecords    : KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);
            listSingleValueCompareTypes : KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);
            listRangeCompareTypes       : KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);
            listCompareTypes            : KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);
            itemListTargetServiceType_BA1_2         : KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);
            itemListTargetSelectionRange_BA1_5         : KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);
            itemListTargetSelectionRange_BA1_5_target_working_hours : KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);
            displayWorkTypeSelections_BA1_4 :       KnockoutObservable<string> = ko.observable('');
            displayWorkTimeItemSelections_BA2_3:    KnockoutObservable<string> = ko.observable('');
            displayWorkingTimeZoneSelections_BA5_3: KnockoutObservable<string> = ko.observable('');
            private defaultSetting: shareModel.ISettingCdlKal003B = {
                    errAlaCheckId:    ''
                    , category:                         0
                    , checkItem:                        0
                    , workTypeRange:                    []
                    , workTypeSelections:               []
                    , workTimeItemSelections:           []
                    , comparisonOperator:               0
                    , minimumValue:                     ''
                    , maximumValue:                     ''
                    , continuousPeriodInput:            ''
                    , workingTimeZoneSelections:        []
                    , color:                            ''
                    , message:                          ''
                    , isBold:                           false
                    , compoundCondition:                this.initCompoundCondition
            };
            targetServiceTypeSelected_BA1_2 : KnockoutObservable<number> = ko.observable(1);
            targetSelectionRangeSelected_BA1_5 : KnockoutObservable<number> = ko.observable(0);
            targetSelectionRangeSelected_BA1_5_target_working_hours : KnockoutObservable<number> = ko.observable(1);
            private setting: shareModel.ISettingCdlKal003B;
            swANDOR_B5_3: KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);
            swANDOR_B6_3: KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);
            swANDOR_B7_2: KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);
            enableComparisonMaxValue : KnockoutObservable<boolean> = ko.observable(false);
            constructor() {
                let self = this;
                var option = windows.getShared('dataKal003b');
                self.setting = $.extend({}, self.defaultSetting, option);
                self.currentErrAlaCheckCondition = ko.observable(new shareModel.SettingCdlKal003B(self.setting));

                // change select item check
                self.currentErrAlaCheckCondition().checkItem.subscribe((itemCheck) => {
                    errors.clearAll();
                    if (itemCheck && itemCheck != undefined) {
                        //self.initialScreen();
                    }
                });
                self.currentErrAlaCheckCondition().comparisonOperator.subscribe((comparisonOperatorId) => {
                    errors.clearAll();
                    self.settingEnableComparisonMaxValueField();
                });

                self.currentErrAlaCheckCondition().compoundCondition().hasGroup2.subscribe((item) => {
                    // show group 2
                    if (item === true) {
                        $('#div_b6_2').show();
                        $('#div_b6_3').show();
                        $('#div_b16').show();
                        $('#div_b7').show();
                    } else { // hide group 2
                        
                    }
                });
                
            }
    
            //initial screen
            start(): JQueryPromise<any> {
                
                let self = this,
                    dfd = $.Deferred();
    
                errors.clearAll();
                self.getAllEnums().done(function() {
                  //initial screen - in case update
                    if (self.setting.errAlaCheckId) {
                        //self.initialScreen(); //.done(() => {
                           // dfd.resolve();
                        //});
                    }
                    self.settingEnableComparisonMaxValueField();
                    errors.clearAll();
                    dfd.resolve();
               });
                
                return dfd.promise();
            }

            private settingEnableComparisonMaxValueField() {
                let self = this;
                self.enableComparisonMaxValue(self.currentErrAlaCheckCondition().comparisonOperator() > 5);
            }
            // initial screen
            private initialScreen() : JQueryPromise<any> {
                let self = this,
                    dfd = $.Deferred();
                switch (self.currentErrAlaCheckCondition().checkItem()) {
                case enItemCheck.Time: //時間
                    
                    self.initialDailyItemChkTime(); //.done((x) => {
                                   /* dfd.resolve();
                                }).always(() => {
                                    dfd.resolve();
                                });*/
                    break;
                case enItemCheck.Times: // 回数
                    
                    self.initialDailyItemChkTimes().done((x) => {
                                    dfd.resolve();
                                }).always(() => {
                                    dfd.resolve();
                                });
                    break;
                case enItemCheck.AmountOfMoney: // 金額
                    
                    self.initialDailyItemChkAmountOfMoney().done((x) => {
                                    dfd.resolve();
                                }).always(() => {
                                    dfd.resolve();
                                });
                    break;
                case enItemCheck.TimeOfDate: // 時刻の場合
                    
                    self.initialDailyItemChkTimeOfDate().done((x) => {
                                    dfd.resolve();
                                }).always(() => {
                                    dfd.resolve();
                                });
                    break;
                case enItemCheck.CountinuousTime: // 連続時間
                    
                    self.initialDailyItemChkCountinuousTime().done((x) => {
                                    dfd.resolve();
                                }).always(() => {
                                    dfd.resolve();
                                });
                    break;
                case enItemCheck.CountinuousWork: // 連続時間帯
                    
                    self.initialDailyItemChkCountinuousWork().done((x) => {
                                    dfd.resolve();
                                }).always(() => {
                                    dfd.resolve();
                                });
                    break;
                case enItemCheck.CountinuousTimeZone: // 連続勤務
                    
                    self.initialDailyItemChkCountinuousTimeZone().done((x) => {
                                    dfd.resolve();
                                }).always(() => {
                                    dfd.resolve();
                                });
                    break;
                case enItemCheck.CompoundCondition: // 複合条件
                    
                    self.initialDailyItemChkCompoundCondition().done((x) => {
                                    dfd.resolve();
                                }).always(() => {
                                    dfd.resolve();
                                });
                    break;
                default:
                    dfd.resolve();
                    break;
                }
                return dfd.promise();
            }
            
            // ===========common begin ===================
            getAllEnums() : JQueryPromise<any> {
                let self = this,
                dfd = $.Deferred();

                $.when(service.getEnumSingleValueCompareTypse(),
                        service.getEnumRangeCompareType(),
                        service.getEnumTypeCheckWorkRecord(),
                        service.getEnumTargetSelectionRange(),
                        service.getEnumTargetServiceType(),
                        service.getEnumLogicalOperator()).done((
                                listSingleValueCompareTypse : Array<model.EnumModel>,
                                lstRangeCompareType : Array<model.EnumModel>,
                                listTypeCheckWorkRecord : Array<model.EnumModel>,
                                listTargetSelectionRange : Array<model.EnumModel>,
                                listTargetServiceType : Array<model.EnumModel>,
                                listLogicalOperator : Array<model.EnumModel>) => {
                        self.listSingleValueCompareTypes(self.getLocalizedNameForEnum(listSingleValueCompareTypse));
                        self.listRangeCompareTypes(self.getLocalizedNameForEnum(lstRangeCompareType));
                        self.listTypeCheckWorkRecords(self.getLocalizedNameForEnum(listTypeCheckWorkRecord));
                        var listTargetRangeWithName = self.getLocalizedNameForEnum(listTargetSelectionRange);
                        self.itemListTargetSelectionRange_BA1_5(listTargetRangeWithName);
                        self.itemListTargetSelectionRange_BA1_5_target_working_hours(listTargetRangeWithName);
                        self.itemListTargetServiceType_BA1_2(self.getLocalizedNameForEnum(listTargetServiceType));
                        self.buildListCompareTypes();
                        var listANDOR = self.getLocalizedNameForEnum(listLogicalOperator)
                      //ENUM 論理演算子
                        self.swANDOR_B5_3 = ko.observableArray(listANDOR);
                        //ENUM 論理演算子
                        self.swANDOR_B6_3 = ko.observableArray(listANDOR);
                      //ENUM 論理演算子
                        self.swANDOR_B7_2 = ko.observableArray(listANDOR);
                        dfd.resolve();
                   
                }).always(() => {
                    dfd.resolve();
                });
                return dfd.promise();
            }
            
            private buildListCompareTypes() {
                let self = this;
                var listCompareTypes = self.listSingleValueCompareTypes().concat(self.listRangeCompareTypes());
                self.listCompareTypes(listCompareTypes);
                
            }
            
            private getLocalizedNameForEnum(listEnum : Array<model.EnumModel>) : Array<model.EnumModel> {
                if (listEnum) {
                    for (var i = 0, len = listEnum.length; i < len; i++) {
                        if (listEnum[i].localizedName) {
                            listEnum[i].localizedName = resource.getText(listEnum[i].localizedName);
                        }
                    }
                    return listEnum;
                }
                return [];
            }
         // ============build enum for combobox BA2-5: end ==============
            // ===========common end =====================
            //==========Daily session Begin====================
            /**
             * Initial Daily
             */
            private initialDaily() : JQueryPromise<any> {
                let self = this,
                dfd = $.Deferred();
                switch (self.currentErrAlaCheckCondition().checkItem()) {
                    case enItemCheck.Time: //時間
                        
                        self.initialDailyItemChkTime().done((x) => {
                                        dfd.resolve();
                                    }).always(() => {
                                        dfd.resolve();
                                    });
                        break;
                    case enItemCheck.Times: // 回数
                        
                        self.initialDailyItemChkTimes().done((x) => {
                                        dfd.resolve();
                                    }).always(() => {
                                        dfd.resolve();
                                    });
                        break;
                    case enItemCheck.AmountOfMoney: // 金額
                        
                        self.initialDailyItemChkAmountOfMoney().done((x) => {
                                        dfd.resolve();
                                    }).always(() => {
                                        dfd.resolve();
                                    });
                        break;
                    case enItemCheck.TimeOfDate: // 時刻の場合
                        
                        self.initialDailyItemChkTimeOfDate().done((x) => {
                                        dfd.resolve();
                                    }).always(() => {
                                        dfd.resolve();
                                    });
                        break;
                    case enItemCheck.CountinuousTime: // 連続時間
                        
                        self.initialDailyItemChkCountinuousTime().done((x) => {
                                            dfd.resolve();
                                        }).always(() => {
                                            dfd.resolve();
                                        });
                        break;
                    case enItemCheck.CountinuousWork: // 連続時間帯
                        
                        self.initialDailyItemChkCountinuousWork().done((x) => {
                                        dfd.resolve();
                                    }).always(() => {
                                        dfd.resolve();
                                    });
                        break;
                    case enItemCheck.CountinuousTimeZone: // 連続勤務
                        
                        self.initialDailyItemChkCountinuousTimeZone().done((x) => {
                                        dfd.resolve();
                                    }).always(() => {
                                        dfd.resolve();
                                    });
                        break;
                    case enItemCheck.CompoundCondition: // 複合条件
                        
                        self.initialDailyItemChkCompoundCondition().done((x) => {
                                        dfd.resolve();
                                    }).always(() => {
                                        dfd.resolve();
                                    });
                        break;
                    default:
                        dfd.resolve();
                        break;
                }
                return dfd.promise();
            }
            
            private initialDailyItemChkTime() : JQueryPromise<any> {
                let self = this,
                currentErrAlaCheckCondition = self.self.currentErrAlaCheckCondition(),
                dfd = $.Deferred();
                //ドメインモデル「日次の勤怠項目」を取得する - Acquire domain model "DailyAttendanceItem"
                var jsItemCheckCmd : any = ko.toJS(currentErrAlaCheckCondition.itemCheck);
                service.getDailyAttendanceItemByChkItem(jsItemCheckCmd).done((itemAttendance) => {
                    if (itemAttendance) {
                        //self.currentErrAlaCheckCondition().dailyAttendanceItemId(itemAttendance.id);
                        //self.currentErrAlaCheckCondition().dailyAttendanceItemName(itemAttendance.name);
                        //ドメインモデル「任意項目」を取得する  - Acquire domain model "OptionalItem"
                        var command : any = ko.toJS(itemAttendance.companyId, itemAttendance.attribute);
                        service.getOptionalItemBy(command).done((itemOptional) => {
                            if (itemOptional) {
                                //ドメインモデル「勤怠項目と枠の紐付け」を取得する (Acquire domain model  "AttendanceAndFrameLinking")
                                var frameCategory = ''; //TODO???
                                var itemType = "daily"; //TODO??
                                var frameNo = itemOptional.optionalItemNo
                                var command : any = ko.toJS(frameCategory, itemType, frameNo);
                                service.getAttendanceAndFrameLinking(command).done((itemAttendanceAndFrameLinking) => {
                                    if (itemAttendanceAndFrameLinking) {
                                        //in case exist default role set
                                      //取得しているドメインモデル「勤怠項目と枠の紐づけ」を元にドメインモデル「日次の勤怠項目」を取得する - Acquire the domain model "DailyAttendanceItem" based on the acquired domain model "AttendanceAndFrameLinking"
                                        if (itemAttendanceAndFrameLinking.attendanceId === itemAttendance.id) {
                                            //ドメインモデル「カテゴリ別アラームチェック条件」．抽出条件を元に勤務実績のエラーアラームチェックIDを取得する - Domain model "Alarm check condition by category". Acquire the error alarm check ID of the work record based on the extraction condition
                                            // 勤務実績のエラーアラームチェック - ErrorAlarmCondition
                                            var commandGetAlarmChkConditionByCategory = ko.toJs(); //TODO condition?
                                            var commandGetErrorAlarmCondition = ko.toJs(); //TODO condition?
                                            _.when(service.getAlarmChkConditionByCategory(commandGetAlarmChkConditionByCategory)
                                                    , service.getErrorAlarmCondition(commandGetErrorAlarmCondition) )
                                                    .done((alarmChkConditionByCategory, errorAlarmCondition) => {
                                                // アルゴリズム「勤怠項目に対応する名称を生成する」を実行する - Execute algorithm "Generate name corresponding to attendance item"
                                                self.GenerateNameCorrespondingToAttendanceItem(itemAttendance.id, ); //CATEGORY.DAILY
                                                dfd.resolve();
                                            }).fail(error => {
                                                dfd.reject();
                                            });
                                            
                                            currentErrAlaCheckCondition.errAlaCheckId(itemAttendance.id || '');
                                        } else {
                                            dfd.reject();
                                        }
                                    } else {
                                        dfd.reject();
                                    }
                                }).fail(error => {
                                    dfd.reject();
                                });
                            } else {
                                dfd.reject();
                            }
                        }).fail(error => {
                            dfd.reject();
                        });
                    }
                    dfd.reject();
                }).fail(error => {
                    currentErrAlaCheckCondition.errAlaCheckId('');
                    dfd.reject();
                });
                return dfd.promise();
            }
            
            //TODO
            private initialDailyItemChkTimes() : JQueryPromise<any> {
                let self = this,
                dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }
            
            //TODO
            private initialDailyItemChkAmountOfMoney() : JQueryPromise<any> {
                let self = this,
                dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }
            
            //TODO
            private initialDailyItemChkTimeOfDate() : JQueryPromise<any> {
                let self = this,
                dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }
            //TODO
            private initialDailyItemChkCountinuousTime() : JQueryPromise<any> {
                let self = this,
                dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }
            //TODO
            private initialDailyItemChkCountinuousWork() : JQueryPromise<any> {
                let self = this,
                dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }
            //TODO
            private initialDailyItemChkCountinuousTimeZone() : JQueryPromise<any> {
                let self = this,
                dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }
            //TODO
            private initialDailyItemChkCompoundCondition() : JQueryPromise<any> {
                let self = this,
                dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }
            /**
             * アルゴリズム「勤怠項目に対応する名称を生成する」を実行する - Execute algorithm "Generate name corresponding to attendance item"
             * @param itemAttendanceId
             */
            GenerateNameCorrespondingToAttendanceItem(itemAttendanceId : string) {
                let self = this;
                var command = {
                    attendanceId : itemAttendanceId
                }
                /*
                service.getAttendanceAndFrameLinkingByAttendanceId(command).done((item) => {
                    if (item) {
                        //対応するドメインモデルを取得する - Acquire corresponding domain model
                        
                    } else { // ドメインモデル「勤怠項目．名称」を使用する (Use the domain model "AttendanceItem. Name")
                        
                    }
                });
                */
                //.setting.categoty
            }
            
              //==========Daily session End====================
            /*
            list(str: string):Array<string>{
                return _.split(str, ',');
            }
            
            */
            getListCode(listKdl002Model : Array<model.ItemModelKdl002>) : Array<string>{
                let retListCode : Array<string> = [];
                if (listKdl002Model == null || listKdl002Model == undefined) {
                    return retListCode;
                }
                for(var i = 0; i < listKdl002Model.length; i++) {
                    retListCode.push(listKdl002Model[i].code);
                }
                return retListCode;
            }
            /**
             * open dialog for select working type
             */
            btnSettingBA1_3_click () {
                let self = this,
                    currentErrAlaCheckCondition = self.currentErrAlaCheckCondition();
                
                block.invisible();

                let lstSelectedCode = currentErrAlaCheckCondition.workTypeSelections();
                let lstSelectableCode = [];
                
                windows.setShared('KDL002_Multiple',false,true);
                //all possible items
                windows.setShared('KDL002_AllItemObj',lstSelectableCode,true);
                //selected items
                windows.setShared('KDL002_SelectedItemId',lstSelectedCode,true);
                
                windows.sub.modal('/view/kdl/002/a/index.xhtml', { title: '乖離時間の登録＞対象項目', }).onClosed(function(): any {
                  //get data from share window

                    let listCds = windows.getShared('KDL002_SelectedNewItem');
                    if (listCds == null || listCds == undefined) {
                        
                    } else {
                        //items: KnockoutObservableArray<model.ItemModelKdl002> = ko.observableArray(listCds);
                        currentErrAlaCheckCondition.workTypeSelections(self.getListCode(listCds));
                        self.displayWorkTypeSelections_BA1_4(listCds.toString());
                        
                    }
                    block.clear();
                });
                
            }
            /**
             * open dialog for select working time zone (KDL002)
             */
            btnSettingBA5_2_click() {
                let self = this;
                alert("open dialog btnSettingBA5_2");
                self.displayWorkingTimeZoneSelections_BA5_3("BA5_2");
            }

            /**
             * open dialog for select working time zone
             */
            btnSettingBA2_2_click() {
                let self = this;
                alert("open dialog btnSettingBA2_2_click");
                self.displayWorkTimeItemSelections_BA2_3("BA2_2");
            }
            
            /**
             * close dialog B and return result
             */
            btnDecision() {
                let self = this;
                windows.setShared('outputKal003b', ko.toJS(self.currentErrAlaCheckCondition()));
                nts.uk.ui.windows.close();
            }
            /**
             * close dialog B and return result
             */
            closeDialog() {
                windows.setShared('outputKal003b', null);
                nts.uk.ui.windows.close();
            }
        }
    }

    /**
     * The enum of ROLE TYPE 
     */
    export enum enCategory {
        Daily   = 1,
        Weekly  = 2,
        Monthly = 3

    }
    export enum enItemCheck {
        Time                = 0, // 時間
        Times               = 1, // 回数
        AmountOfMoney       = 2, // 金額
        TimeOfDate          = 3, // 時刻
        CountinuousTime     = 4, // 連続時間
        CountinuousWork     = 5, // 連続勤務
        CountinuousTimeZone = 6, // 連続時間帯
        CompoundCondition   = 7// 複合条件
    }
    module model {
        export class ItemModelKdl002 {
            code: string;
            name: string;
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }
        export interface IEnumModel {
            value : number;
            fieldName: string;
            localizedName: string;
        }
        
        export class EnumModel {
            value : KnockoutObservable<number>;
            fieldName: string;
            localizedName: string;
            constructor(param: IEnumModel) {
                let self = this;
                self.value =  ko.observable(param.value || -1);
                self.fieldName = param.fieldName || '';
                self.localizedName = param.localizedName || '';
            }
        }
    }
}
