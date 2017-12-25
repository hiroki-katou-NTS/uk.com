module nts.uk.com.view.cas011.c.viewmodel {
    import block = nts.uk.ui.block;
    import errors = nts.uk.ui.errors;
    import dialog = nts.uk.ui.dialog;
    import windows = nts.uk.ui.windows;
    import resource = nts.uk.resource;

    export class ScreenModel {
        currentErrAlaAttendanceItemCondition: KnockoutObservable<ErrAlaAttendanceItemCondition> = ko.observable(new ErrAlaAttendanceItemCondition({
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
                
        }));
        // list item check
        listTypeCheckWorkRecords    : KnockoutObservable<model.IEnumModel> = ko.observableArray([]);
        listSingleValueCompareTypes : KnockoutObservable<model.IEnumModel> = ko.observableArray([]);
        listRangeCompareTypes       : KnockoutObservable<model.IEnumModel> = ko.observableArray([]);
        listCompareTypes            : KnockoutObservable<model.IEnumModel> = ko.observableArray([]);

        private defaultSetting: model.ISetting = {
            category: 1,
            typeCheckWorkRecord: 1,
            errAlaAttendaceItemCoditionId: ''
        };
        private setting: model.ISetting;
        constructor() {
            let self = this;
            var option = windows.getShared('dataKal003b');
            self.setting = $.extend({}, self.defaultSetting, option);
            self.buildListTypeCheckWorkRecords();
            self.buildlistCompareTypes();

            self.currentErrAlaAttendanceItemCondition.typeCheckWorkRecord(self.setting.typeCheckWorkRecord || self.defaultSetting.typeCheckWorkRecord);
            self.currentErrAlaAttendanceItemCondition.errAlaAttendanceItemConditionId.subscribe((x) => {
                //TODO
            });
            
            // change select item check
            self.currentErrAlaAttendanceItemCondition.typeCheckWorkRecord.subscribe((itemCheck) => {
                self.initialScreen();
            });
        }

        //initial screen
        start(): JQueryPromise<any> {
            
            let self = this,
                dfd = $.Deferred();

            errors.clearAll();

            //initial screen
            self.initialScreen().done(() => {
                dfd.resolve();
            });
            

            return dfd.promise();
        }
        // initial screen
        private initialScreen() : JQueryPromise<any> {
            let self = this;
            switch (self.setting.category) {
            case enCategory.Daily: // アルゴリズム「日次の初期起動」を実行する
                
                self.initialDaily().done((x) => {
                                dfd.resolve();
                            }).always(() => {
                                dfd.resolve();
                            });
                break;
                default:
                    break;
            }
        }
        // ===========common begin ===================
        // ============build enum for combobox item check B1-2: Begin ==============
        private buildListTypeCheckWorkRecords() {
            let self = this;
            /* 時間 */
            self.listTypeCheckWorkRecords.push(new model.EnumModel ({id : 0, code: 'TIME', name: resource.getText('Enum_TypeCheckWorkRecord_Time')}));
            /* 回数 */
            self.listTypeCheckWorkRecords.push(new model.EnumModel ({id : 1, code: 'TIMES', name: resource.getText('Enum_TypeCheckWorkRecord_Times')}));
            /* 金額 */
            self.listTypeCheckWorkRecords.push(new model.EnumModel ({id : 2, code: 'AMOUNT_OF_MONEY', name: resource.getText('Enum_TypeCheckWorkRecord_AmountOfMoney')}));
            /* 時刻 */
            self.listTypeCheckWorkRecords.push(new model.EnumModel ({id : 3, code: 'TIME_OF_DATE', name: resource.getText('Enum_TypeCheckWorkRecord_TimeOfDate')}));
            /* 連続時間 */
            self.listTypeCheckWorkRecords.push(new model.EnumModel ({id : 4, code: 'CONTINUOUS_TIME', name: resource.getText('Enum_TypeCheckWorkRecord_ContinuousTime')}));
            /* 連続勤務 */
            self.listTypeCheckWorkRecords.push(new model.EnumModel ({id : 5, code: 'CONTINUOUS_WORK', name: resource.getText('Enum_TypeCheckWorkRecord_ContinuousWork')}));
            /* 連続時間帯 */
            self.listTypeCheckWorkRecords.push(new model.EnumModel ({id : 6, code: 'CONTINUOUS_TIME_ZONE', name: resource.getText('Enum_TypeCheckWorkRecord_ContinuousTimeZone')}));
            /* 複合条件 */
            self.listTypeCheckWorkRecords.push(new model.EnumModel ({id : 7, code: 'COMPOUND_CONDITION', name: resource.getText('Enum_TypeCheckWorkRecord_CompoundCondition')}));
        }
        
        // ============build enum for combo box compare type "BA2-5": Begin ==============
        private buildListSingleValueCompareTypes() {
            let self = this;
            /* 等しい（＝） */
            self.listSingleValueCompareTypes.push(new model.EnumModel ({id : 0, code: 'EQUAL', name: resource.getText('Enum_SingleValueCompareType_Equal')}));
            /* 等しくない（≠） */
            self.listSingleValueCompareTypes.push(new model.EnumModel ({id : 1, code: 'NOT_EQUAL', name: resource.getText('Enum_SingleValueCompareType_NotEqual')}));
            /* より大きい（＞） */
            self.listSingleValueCompareTypes.push(new model.EnumModel ({id : 2, code: 'GREATER_THAN', name: resource.getText('Enum_SingleValueCompareType_GreaterThan')}));
            /* 以上（≧） */
            self.listSingleValueCompareTypes.push(new model.EnumModel ({id : 3, code: 'GREATER_OR_EQUAL', name: resource.getText('Enum_SingleValueCompareType_GreaterOrEqual')}));
            /* より小さい（＜） */
            self.listSingleValueCompareTypes.push(new model.EnumModel ({id : 4, code: 'LESS_THAN', name: resource.getText('Enum_SingleValueCompareType_LessThan')}));
            /* 以下（≦） */
            self.listSingleValueCompareTypes.push(new model.EnumModel ({id : 5, code: 'LESS_OR_EQUAL', name: resource.getText('Enum_SingleValueCompareType_LessOrEqual')}));
        }
        private buildlistRangeCompareTypes() {
            let self = this;
            /* 範囲の間（境界値を含まない）（＜＞） */
            self.listRangeCompareTypes.push(new model.EnumModel ({id : 6, code: 'BETWEEN_RANGE_OPEN', name: resource.getText('Enum_RangeCompareType_BetweenRangeOpen')}));
            /* 範囲の間（境界値を含む）（≦≧） */
            self.listRangeCompareTypes.push(new model.EnumModel ({id : 7, code: 'BETWEEN_RANGE_CLOSED', name: resource.getText('Enum_RangeCompareType_BetweenRangeClosed')}));
            /* 範囲の外（境界値を含まない）（＞＜） */
            self.listRangeCompareTypes.push(new model.EnumModel ({id : 8, code: 'OUTSIDE_RANGE_OPEN', name: resource.getText('Enum_RangeCompareType_OutsideRangeOpen')}));
            /* 範囲の外（境界値を含む）（≧≦） */
            self.listRangeCompareTypes.push(new model.EnumModel ({id : 9, code: 'OUTSIDE_RANGE_CLOSED', name: resource.getText('Enum_RangeCompareType_OutsideRangeClosed')}));
        }
        private buildlistCompareTypes() {
            let self = this;
            self.listCompareTypes.push(self.listSingleValueCompareTypes);
            self.listCompareTypes(self.listCompareTypes.concat(self.listRangeCompareTypes));
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
            switch (self.currentErrAlaAttendanceItemCondition.typeCheckWorkRecord) {
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
            dfd.promise();
        }
        
        private initialDailyItemChkTime() JQueryPromise<any> {
            let self = this,
            dfd = $.Deferred();
            //ドメインモデル「日次の勤怠項目」を取得する - Acquire domain model "DailyAttendanceItem"
            var jsItemCheckCmd : any = ko.toJS(self.itemCheck);
            service.getDailyAttendanceItemByChkItem(jsItemCheckCmd).done((itemAttendance: IDailyAttendanceItem) => {
                if (itemAttendance) {
                    self.currentErrAlaAttendanceItemCondition.dailyAttendanceItemId = itemAttendance.id;
                    self.currentErrAlaAttendanceItemCondition.dailyAttendanceItemName = itemAttendance.name;
                    //ドメインモデル「任意項目」を取得する  - Acquire domain model "OptionalItem"
                    var command : any = ko.toJS(itemAttendance.companyId, itemAttendance.attribute);
                    service.getOptionalItemBy(command).done((itemOptional: IOptionalItem) => {
                        if (itemOptional) {
                            //ドメインモデル「勤怠項目と枠の紐付け」を取得する (Acquire domain model  "AttendanceAndFrameLinking")
                            var frameCategory = ''; //TODO???
                            var itemType = "daily"; //TODO??
                            var frameNo = itemOptional.optionalItemNo
                            var command : any = ko.toJS(frameCategory, itemType, frameNo);
                            service.getAttendanceAndFrameLinking(command).done((itemAttendanceAndFrameLinking: IAttendanceAndFrameLinking) => {
                                if (itemAttendanceAndFrameLinking) {
                                    //in case exist default role set
                                  //取得しているドメインモデル「勤怠項目と枠の紐づけ」を元にドメインモデル「日次の勤怠項目」を取得する - Acquire the domain model "DailyAttendanceItem" based on the acquired domain model "AttendanceAndFrameLinking"
                                    if (itemAttendanceAndFrameLinking.attendanceId === itemAttendance.id) {
                                        //ドメインモデル「カテゴリ別アラームチェック条件」．抽出条件を元に勤務実績のエラーアラームチェックIDを取得する - Domain model "Alarm check condition by category". Acquire the error alarm check ID of the work record based on the extraction condition
                                        // 勤務実績のエラーアラームチェック - ErrorAlarmCondition
                                        var commandGetAlarmChkConditionByCategory = ko.toJs(); //TODO condition?
                                        var commandGetErrorAlarmCondition = ko.toJs(); //TODO condition?
                                        _.when(service.getAlarmChkConditionByCategory(commandGetAlarmChkConditionByCategory)
                                                service.getErrorAlarmCondition(commandGetErrorAlarmCondition), )
                                                .done((alarmChkConditionByCategory, errorAlarmCondition) => {
                                            // アルゴリズム「勤怠項目に対応する名称を生成する」を実行する - Execute algorithm "Generate name corresponding to attendance item"
                                            self.GenerateNameCorrespondingToAttendanceItem(itemAttendance.id, ); //CATEGORY.DAILY
                                            dfd.resolve();
                                        }).fail(error => {
                                            dfd.reject();
                                        });
                                        
                                        self.currentErrAlaAttendanceItemCondition.ErrAlaAttendanceItemConditionId(itemAttendance.id || '');
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
                self.currentErrAlaAttendanceItemCondition.ErrAlaAttendanceItemConditionId('');
                dfd.reject();
            });
            dfd.promise();
        }
        
        //TODO
        private initialDailyItemChkTimes() JQueryPromise<any> {
            let self = this,
            dfd = $.Deferred();
            dfd.resovle();
            dfd.promise();
        }
        
        //TODO
        private initialDailyItemChkAmountOfMoney() JQueryPromise<any> {
            let self = this,
            dfd = $.Deferred();
            dfd.resovle();
            dfd.promise();
        }
        
        //TODO
        private initialDailyItemChkTimeOfDate() JQueryPromise<any> {
            let self = this,
            dfd = $.Deferred();
            dfd.resovle();
            dfd.promise();
        }
        //TODO
        private initialDailyItemChkCountinuousTime() JQueryPromise<any> {
            let self = this,
            dfd = $.Deferred();
            dfd.resovle();
            dfd.promise();
        }
        //TODO
        private initialDailyItemChkCountinuousWork() JQueryPromise<any> {
            let self = this,
            dfd = $.Deferred();
            dfd.resovle();
            dfd.promise();
        }
        //TODO
        private initialDailyItemChkCountinuousTimeZone() JQueryPromise<any> {
            let self = this,
            dfd = $.Deferred();
            dfd.resovle();
            dfd.promise();
        }
        //TODO
        private initialDailyItemChkCompoundCondition() JQueryPromise<any> {
            let self = this,
            dfd = $.Deferred();
            dfd.resovle();
            dfd.promise();
        }
        /**
         * アルゴリズム「勤怠項目に対応する名称を生成する」を実行する - Execute algorithm "Generate name corresponding to attendance item"
         * @param itemAttendanceId
         */
        GenerateNameCorrespondingToAttendanceItem(string itemAttendanceId) {
            let self = this;
            var command = {
                attendanceId : itemAttendanceId
            }
            service.getAttendanceAndFrameLinkingByAttendanceId(command).done((item) => {
                if (item) {
                    //対応するドメインモデルを取得する - Acquire corresponding domain model
                    
                } else { // ドメインモデル「勤怠項目．名称」を使用する (Use the domain model "AttendanceItem. Name")
                    
                }
            });
            //.setting.categoty
        }
        
          //==========Daily session End====================
        closeDialog() {
            nts.uk.ui.windows.close()
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
    //module model
    export module model {
        //enum model
        export interface IEnumModel {
            id  : number;
            code : string;
            name : string;
        }
        
        export class EnumModel {
            id  : KnockoutObservable<number> = ko.observable(null);
            code : KnockoutObservable<string> = ko.observable('');
            name : KnockoutObservable<string> = ko.observable('');
            constructor(param: IEnumModel) {
                let self = this;
                self.id(param.id || 0);
                self.code(param.code || '');
                self.name(param.name || '');
            }
        }
        //Class Input parameter
        export interface ISetting {
            category: number;
            typeCheckWorkRecord: number; //item check
            errAlaAttendaceItemCoditionId: string;
        }

        // AlaAttendaceItemCodition
        export interface IErrAlaAttendaceItemCodition {
            //------common - begin------
            errAlaAttendaceItemCoditionId: string;
            category : number;
            typeCheckWorkRecord: number; // チェック項目 - item check
            messageColor: string;
            messageContent: string
            isBoldMessage: boolean;
            //------common - end------
            // 時間、回数、金額、時刻の場合
            targetServiceType : number;  // get by enum 勤務種類 : BA1-2
            targetServiceTypeWorkTypeSelection: string; //対象とする勤務種類 => for other: BA1-4 
            // チェック条件  - check condition
            dailyAttendanceItemId: string; // BA2-3 日次の勤怠項目 - DailyAttendanceItem
            dailyAttendanceItemName: string; // BA2-3 日次の勤怠項目 - DailyAttendanceItem
            comparisonOperatorId:   number; // 比較演算子:  単一値との比較演算の種別　＋　範囲との比較演算の種別 => enum BA2-4
            comparisonMinValue:     string; //BA2-6
            comparisonMaxValue:     string; //BA2-7
            // 連続時間の場合 , 連続時間帯の場合
            continuousPeriod: number; //BA3-2: 連続期間入力欄 - Continuous period input field
            //連続時間帯の場合 
            targetWorkingHoursCd: string; //BA5-1 - enum, 
            targetWorkingHoursTimeZoneSelection: string; //BA5-3
        }
    
        //ErrAlaAttendaceItemCodition
        export class ErrAlaAttendaceItemCodition {
            errAlaAttendaceItemCoditionId: string;
            category : number;
            typeCheckWorkRecord: KnockoutObservable<number> = ko.observable(null); // チェック項目 - item check
            messageColor: KnockoutObservable<string> = ko.observable('');
            messageContent: KnockoutObservable<string> = ko.observable('');
            isBoldMessage: KnockoutObservable<boolean> = ko.observable('');
            targetServiceType : KnockoutObservable<number> = ko.observable(null);  // get by enum : BA1-2
            targetServiceTypeWorkTypeSelection: KnockoutObservable<string> = ko.observable(''); //対象とする勤務種類 => for other: BA1-4 
            // チェック条件  - check condition
            dailyAttendanceItemId: KnockoutObservable<string> = ko.observable(''); // BA2-3 日次の勤怠項目 - DailyAttendanceItem
            dailyAttendanceItemName: KnockoutObservable<string> = ko.observable(''); // BA2-3 日次の勤怠項目 - DailyAttendanceItem
            comparisonOperatorId:   KnockoutObservable<number> = ko.observable(null); // 比較演算子:  単一値との比較演算の種別　＋　範囲との比較演算の種別 => enum BA2-4
            comparisonMinValue:     KnockoutObservable<string> = ko.observable(''); //BA2-6
            comparisonMaxValue:     KnockoutObservable<string> = ko.observable(''); //BA2-7
            // 連続時間の場合 , 連続時間帯の場合
            continuousPeriod: KnockoutObservable<number> = ko.observable(null); //BA3-2: 連続期間入力欄 - Continuous period input field
            //連続時間帯の場合 
            targetWorkingHoursCd: KnockoutObservable<string> = ko.observable(''); //BA5-1 - enum
            targetWorkingHoursTimeZoneSelection: KnockoutObservable<string> = ko.observable(''); //BA5-3
        
            constructor(param: IErrAlaAttendaceItemCodition) {
                let self = this;
                self.errAlaAttendaceItemCoditionId = param.errAlaAttendaceItemCoditionId || '';
                self.category = param.category || 0;
                self.typeCheckWorkRecord(param.typeCheckWorkRecord || '');
                self.messageColor(param.messageColor || '');
                self.messageContent(param.messageContent || '');
                self.isBoldMessage(param.isBoldMessage || false);
                self.targetServiceType(param.targetServiceType || '');
                self.targetServiceTypeWorkTypeSelection(param.targetServiceTypeWorkTypeSelection || '');
                self.dailyAttendanceItemId(param.dailyAttendanceItemId || '');
                self.dailyAttendanceItemName(param.dailyAttendanceItemName || '');
                self.comparisonOperatorId(param.comparisonOperatorId || '');
                self.comparisonMinValue(param.comparisonMinValue || '');
                self.comparisonMaxValue(param.comparisonMaxValue || '');
                self.continuousPeriod(param.continuousPeriod || '');
                self.targetWorkingHoursCd(param.targetWorkingHoursTimeZoneSelection || '');
            }
        }
    }
}
