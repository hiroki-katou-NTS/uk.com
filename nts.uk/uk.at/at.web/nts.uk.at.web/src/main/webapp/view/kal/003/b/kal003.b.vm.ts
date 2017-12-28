module nts.uk.at.view.kal003.b{
    export module viewmodel {
        import block = nts.uk.ui.block;
        import errors = nts.uk.ui.errors;
        import dialog = nts.uk.ui.dialog;
        import windows = nts.uk.ui.windows;
        import resource = nts.uk.resource;

        export class ScreenModel {
            
            intGroupCondition = new model.GroupCondition({
                groupOperator: 0
                , groupListCondition: ([])
            });
            initCompoundCondition = new  model.CompoundCondition ({
                group1Condition: this.intGroupCondition
                , hasGroup2: false
                , group2Condition: this.intGroupCondition
                , operatorBetweenG1AndG2: 0
            });
            currentErrAlaAttendanceItemCondition: KnockoutObservable<model.ErrAlaAttendanceItemCondition> = ko.observable(new model.ErrAlaAttendanceItemCondition({
                    errAlaAttendanceItemConditionId:    ''
                    , category:                         0
                    , typeCheckWorkRecord:              0
                    , messageColor:                     ''
                    , messageContent:                   ''
                    , isBoldMessage:                    false
                    , targetServiceType:                1 // default selection
                    , targetServiceTypeWorkTypeSelection: ''
                    , dailyAttendanceItemId:            ''
                    , dailyAttendanceItemName:          ''
                    , comparisonOperatorId:             0
                    , comparisonMinValue:               ''
                    , comparisonMaxValue:               ''
                    , continuousPeriod:                 0
                    , targetWorkingHoursCd:             ''
                    , targetWorkingHoursTimeZoneSelection: ''
                    , compoundCondition: this.initCompoundCondition
                    
            }));
            // list item check
            listTypeCheckWorkRecords    : KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);
            listSingleValueCompareTypes : KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);
            listRangeCompareTypes       : KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);
            listCompareTypes            : KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);
            itemListTargetServiceType_BA1_2         : KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);
            itemListTargetSelectionRange_BA1_5         : KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);
            itemListTargetSelectionRange_BA1_5_target_working_hours : KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);

            private defaultSetting: model.ISetting = {
                category: 1,
                typeCheckWorkRecord: 0,
                errAlaAttendanceItemConditionId: ''
            };
            targetServiceTypeSelected_BA1_2 : KnockoutObservable<string> = ko.observable('1');
            targetSelectionRangeSelected_BA1_5 : KnockoutObservable<string> = ko.observable('1');
            targetSelectionRangeSelected_BA1_5_target_working_hours : KnockoutObservable<string> = ko.observable('0');
            private setting: model.ISetting;
            swANDOR_B5_3: KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);
            swANDOR_B6_3: KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);
            swANDOR_B7_2: KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);
            enableComparisonMaxValue : KnockoutObservable<boolean> = ko.observable(false);
            constructor() {
                let self = this,
                currentErrAlaAttendanceItemCondition = self.currentErrAlaAttendanceItemCondition();
                var option = windows.getShared('dataKal003b');
                self.setting = $.extend({}, self.defaultSetting, option);

                currentErrAlaAttendanceItemCondition.typeCheckWorkRecord(self.setting.typeCheckWorkRecord || self.defaultSetting.typeCheckWorkRecord);
                // change select item check
                currentErrAlaAttendanceItemCondition.typeCheckWorkRecord.subscribe((itemCheck) => {
                    if (itemCheck && itemCheck != undefined) {
                        self.initialScreen();
                        self.showAndHide();
                    }
                });
                currentErrAlaAttendanceItemCondition.comparisonOperatorId.subscribe((comparisonOperatorId) => {
                    self.settingEnableComparisonMaxValueField();
                });

                currentErrAlaAttendanceItemCondition.compoundCondition().hasGroup2.subscribe((item) => {
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
                    if (self.setting.errAlaAttendanceItemConditionId) {
                        //self.initialScreen(); //.done(() => {
                           // dfd.resolve();
                        //});
                    }
                    self.showAndHide();
                    self.settingEnableComparisonMaxValueField();
                    dfd.resolve();
               });
                
                return dfd.promise();
            }
            
            private showAndHide() {
                let self = this;
                switch (self.currentErrAlaAttendanceItemCondition().typeCheckWorkRecord()) {
                case enItemCheck.Time: //時間
                case enItemCheck.Times: // 回数
                case enItemCheck.AmountOfMoney: // 金額
                case enItemCheck.TimeOfDate: // 時刻の場合
                    $('#div_target_service_type_ba1').show();
                    $('#ba1_2').show();
                    $('#ba1_5').hide();
                    $('#div_check_conditions_ba2').show();
                    $('#div_target_working_hours_ba5').hide();
                    $('#div_continuous_period_ba3').hide();
                    $('#div_compound_condition').hide();
                    break;
                case enItemCheck.CountinuousTime: // 連続時間の場合
                    $('#div_target_service_type_ba1').show();
                    $('#ba1_2').show();
                    $('#ba1_5').hide();
                    $('#div_check_conditions_ba2').show();
                    $('#div_target_working_hours_ba5').hide();
                    $('#div_continuous_period_ba3').show();
                    $('#div_compound_condition').hide();
                    break;
                case enItemCheck.CountinuousTimeZone: // 連続勤務
                    $('#div_target_service_type_ba1').show();
                    $('#ba1_2').show();
                    $('#ba1_5').hide();
                    $('#div_check_conditions_ba2').hide();
                    $('#div_target_working_hours_ba5').show();
                    $('#div_continuous_period_ba3').show();
                    $('#div_compound_condition').hide();
                    break;
                case enItemCheck.CountinuousWork: // 連続時間帯 
                    $('#div_target_service_type_ba1').show();
                    $('#ba1_2').hide();
                    $('#ba1_5').show();
                    $('#div_check_conditions_ba2').hide();
                    $('#div_target_working_hours_ba5').hide();
                    $('#div_continuous_period_ba3').show();
                    $('#div_compound_condition').hide();
                    break;
                case enItemCheck.CompoundCondition: // 複合条件
                    $('#div_target_service_type_ba1').hide();
                    $('#ba1_2').hide();
                    $('#ba1_5').hide();
                    $('#div_check_conditions_ba2').hide();
                    $('#div_target_working_hours_ba5').hide();
                    $('#div_continuous_period_ba3').hide();
                    $('#div_compound_condition').show();
                    break;
                default:
                    break;
                }
            }
            
            private settingEnableComparisonMaxValueField() {
                let self = this;
                self.enableComparisonMaxValue(self.currentErrAlaAttendanceItemCondition().comparisonOperatorId() > 5);
            }
            // initial screen
            private initialScreen() : JQueryPromise<any> {
                let self = this,
                    dfd = $.Deferred();
                switch (self.currentErrAlaAttendanceItemCondition().typeCheckWorkRecord()) {
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
                switch (self.currentErrAlaAttendanceItemCondition().typeCheckWorkRecord()) {
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
                currentErrAlaAttendanceItemCondition = self.currentErrAlaAttendanceItemCondition(),
                dfd = $.Deferred();
                //ドメインモデル「日次の勤怠項目」を取得する - Acquire domain model "DailyAttendanceItem"
                var jsItemCheckCmd : any = ko.toJS(self.itemCheck);
                service.getDailyAttendanceItemByChkItem(jsItemCheckCmd).done((itemAttendance) => {
                    if (itemAttendance) {
                        currentErrAlaAttendanceItemCondition.dailyAttendanceItemId(itemAttendance.id);
                        currentErrAlaAttendanceItemCondition.dailyAttendanceItemName(itemAttendance.name);
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
                                            
                                            currentErrAlaAttendanceItemCondition.ErrAlaAttendanceItemConditionId(itemAttendance.id || '');
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
                    currentErrAlaAttendanceItemCondition.ErrAlaAttendanceItemConditionId('');
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
            btnSettingBA5_2_click() {
                alert("open dialog btnSettingBA5_2");
            }
            btnSettingBA1_3_click () {
                alert("open dialog btnSettingBA1_3_click");
            }
            btnSettingBA2_2_click() {
                alert("open dialog btnSettingBA2_2_click");
            }
            closeDialog() {
                nts.uk.ui.windows.close()
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
        //Class Input parameter
        export interface ISetting {
            category: number;
            typeCheckWorkRecord: number; //item check
            errAlaAttendanceItemConditionId: string;
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
        
        //Condition of group (C screen)
        export interface ICondition{
            itemCheck:  number;
            target:     number;
            operatorCd: string;
            comparisonOperatorId: number;
            itemConditionId: string;
        
        }
        export class Condition{
            itemCheck:  number;
            target:     number;
            operatorCd: string;
            comparisonOperatorId: number;
            itemConditionId: string;
            constructor(param: ICondition) {
                let self = this;
                self.itemCheck  = param.itemCheck;
                self.target     = param.target;
                self.operatorCd = param.operatorCd;
                self.comparisonOperatorId = param.comparisonOperatorId;
                self.itemConditionId = param.itemConditionId;
            }
        
        }
        // group condition
        export interface IGroupCondition {
            groupOperator:      number; //0: OR|1: AND
            groupListCondition: Array<Condition>;// max 3
        }
        
        export class GroupCondition {
            groupOperator:      KnockoutObservable<number>; //OR|AND B15-3, B17-3
            groupListCondition: KnockoutObservableArray<Condition>;// max 3 item, B16-1 -> B16-4
            constructor(param: IGroupCondition) {
                let self = this;
                self.groupOperator = ko.observable(param.groupOperator || 0);
                self.groupListCondition = ko.observableArray(param.groupListCondition || []);
            }
        }
        
        export interface ICompoundCondition {
            group1Condition:    GroupCondition;
            hasGroup2:          boolean; // B17-1
            group2Condition:    GroupCondition;
            operatorBetweenG1AndG2: number; // B18-2: 0: OR, 1: AND
        }
        export class CompoundCondition {
            group1Condition:    KnockoutObservable<GroupCondition>;
            hasGroup2:          KnockoutObservable<boolean>;
            group2Condition:    KnockoutObservable<GroupCondition>;
            operatorBetweenG1AndG2: KnockoutObservable<number>;
            constructor(param: ICompoundCondition) {
                let self = this;
                self.group1Condition = ko.observable(param.group1Condition);
                self.hasGroup2 = ko.observable(param.hasGroup2 || false);
                self.group2Condition = ko.observable(param.group2Condition);
                self.operatorBetweenG1AndG2 = ko.observable(param.operatorBetweenG1AndG2);
            }
        }
        // AlaAttendaceItemCodition
        export interface IErrAlaAttendanceItemCondition {
            //------common - begin------
            errAlaAttendanceItemConditionId:  string;
            category :                      number;
            typeCheckWorkRecord:            number; // チェック項目 - item check
            messageColor:                   string;
            messageContent:                 string
            isBoldMessage:                  boolean;
            //------common - end------
            // 時間、回数、金額、時刻の場合
            targetServiceType :             number;  // get by enum 勤務種類 : BA1-2
            targetServiceTypeWorkTypeSelection: string; //対象とする勤務種類 => for other: BA1-4 
            // チェック条件  - check condition
            dailyAttendanceItemId:          string; // BA2-3 日次の勤怠項目 - DailyAttendanceItem
            dailyAttendanceItemName:        string; // BA2-3 日次の勤怠項目 - DailyAttendanceItem
            comparisonOperatorId:           number; // 比較演算子:  単一値との比較演算の種別　＋　範囲との比較演算の種別 => enum BA2-4
            comparisonMinValue:             string; //BA2-6
            comparisonMaxValue:             string; //BA2-7
            // 連続時間の場合 , 連続時間帯の場合
            continuousPeriod:               number; //BA3-2: 連続期間入力欄 - Continuous period input field
            //連続時間帯の場合 
            targetWorkingHoursCd:           string; //BA5-1 - enum, 
            targetWorkingHoursTimeZoneSelection: string; //BA5-3
            compoundCondition :               CompoundCondition; 
        }
    
        //ErrAlaAttendanceItemCondition
        export class ErrAlaAttendanceItemCondition {
            errAlaAttendanceItemConditionId:  string;
            category :                      number;
            typeCheckWorkRecord:            KnockoutObservable<number>; // チェック項目 - item check
            messageColor:                   KnockoutObservable<string>;
            messageContent:                 KnockoutObservable<string>;
            isBoldMessage:                  KnockoutObservable<boolean>;
            targetServiceType :             KnockoutObservable<number>;  // get by enum : BA1-2
            targetServiceTypeWorkTypeSelection: KnockoutObservable<string>; //対象とする勤務種類 => for other: BA1-4 
            // チェック条件  - check condition
            dailyAttendanceItemId:          KnockoutObservable<string>; // BA2-3 日次の勤怠項目 - DailyAttendanceItem
            dailyAttendanceItemName:        KnockoutObservable<string>; // BA2-3 日次の勤怠項目 - DailyAttendanceItem
            comparisonOperatorId:           KnockoutObservable<number>; // 比較演算子:  単一値との比較演算の種別　＋　範囲との比較演算の種別 => enum BA2-4
            comparisonMinValue:             KnockoutObservable<string>; //BA2-6
            comparisonMaxValue:             KnockoutObservable<string>; //BA2-7
            // 連続時間の場合 , 連続時間帯の場合
            continuousPeriod:               KnockoutObservable<number>; //BA3-2: 連続期間入力欄 - Continuous period input field
            //連続時間帯の場合 
            targetWorkingHoursCd:           KnockoutObservable<string>; //BA5-1 - enum
            targetWorkingHoursTimeZoneSelection: KnockoutObservable<string>; //BA5-3
            compoundCondition :             KnockoutObservable<CompoundCondition>; //B15-1 -> B18-2
        
            constructor(param: IErrAlaAttendanceItemCondition) {
                let self = this;
                self.errAlaAttendanceItemConditionId = param.errAlaAttendanceItemConditionId || '';
                self.category                       = param.category || 0;
                self.typeCheckWorkRecord = ko.observable(param.typeCheckWorkRecord || 0);
                self.messageColor = ko.observable(param.messageColor || '');
                self.messageContent = ko.observable(param.messageContent || '');
                self.isBoldMessage = ko.observable(param.isBoldMessage || false);
                self.targetServiceType = ko.observable(param.targetServiceType || 0);
                self.targetServiceTypeWorkTypeSelection = ko.observable(param.targetServiceTypeWorkTypeSelection || '');
                self.dailyAttendanceItemId = ko.observable(param.dailyAttendanceItemId || '');
                self.dailyAttendanceItemName = ko.observable(param.dailyAttendanceItemName || '');
                self.comparisonOperatorId = ko.observable(param.comparisonOperatorId || 0);
                self.comparisonMinValue = ko.observable(param.comparisonMinValue || '');
                self.comparisonMaxValue = ko.observable(param.comparisonMaxValue || '');
                self.continuousPeriod = ko.observable(param.continuousPeriod || 0);
                self.targetWorkingHoursCd = ko.observable(param.targetWorkingHoursCd || '');
                self.targetWorkingHoursTimeZoneSelection = ko.observable(param.targetWorkingHoursTimeZoneSelection || '');
                self.compoundCondition = ko.observable(param.compoundCondition);
            }
        }
    }
}
