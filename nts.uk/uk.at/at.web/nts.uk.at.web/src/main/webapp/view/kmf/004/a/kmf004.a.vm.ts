module nts.uk.at.view.kmf004.a.viewmodel {
    export class ScreenModel {
        // A2_2
        sphdList: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        columns: KnockoutObservableArray<any> = ko.observableArray([
            { headerText: nts.uk.resource.getText('KMF004_5'), key: 'specialHolidayCode', width: 100 },
            { headerText: nts.uk.resource.getText('KMF004_6'), key: 'specialHolidayName', width: 150, formatter: _.escape }
        ]);
        // A2_2 特別休暇リスト
        //  currentCode: KnockoutObservable<any> = ko.observable();
        // A3_2 コード
        specialHolidayCode: KnockoutObservable<number> = ko.observable(null);
        // A1_3 削除ボタン
        btnDeleteEnable: KnockoutObservable<boolean> = ko.observable(true);
        // A3_2 コード_enable
        specialHolidayCodeEnable: KnockoutObservable<boolean> = ko.observable(true);
        // the mode
        editMode: KnockoutObservable<boolean> = ko.observable(false);
        // A6_2 自動付与区分
        autoGrants: KnockoutObservableArray<any> = ko.observableArray([
                { code: 0, name: nts.uk.resource.getText('KAF008_55') },
                { code: 1, name: nts.uk.resource.getText('KAF008_54') }
            ]);
        // A6_2 自動付与区分
        autoGrant: KnockoutObservable<number> = ko.observable(1);
        // A3_3 名称
        specialHolidayName: KnockoutObservable<string> = ko.observable("");
        // A5_2 設定ボタン
        targetItemsName: KnockoutObservable<string> = ko.observable("");
        // A7_2 メモ入力欄
        memo: KnockoutObservable<string> = ko.observable("");
        // A8_1 タブ
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        // A8_1 タブ_active
        selectedTab: KnockoutObservable<string>;
        // 付与基準日(A10_15, A10_23)
        grantDate: KnockoutObservable<number>; //ok
        //  付与するタイミングの種類
        typeTimes: KnockoutObservableArray<any>;
        // A10_2 付与方法（選択肢１２）
        typeTime: KnockoutObservable<number> = ko.observable(2);
        // A10_3 指定付与（選択肢１）
        typeTime2: KnockoutObservable<any>;
        // A10_14, A10_8, A10_12
        method1ContentEnable: KnockoutObservable<boolean> = ko.observable(true);
        // A10_18 付与日グループ
        tGrantDates: KnockoutObservableArray<any>;
        // A10_12 付与月日を指定する
        tGrantDate0: KnockoutObservable<any>;
        // A10_12, A10_13
        grantDate0ContentEnable: KnockoutObservable<boolean> = ko.observable(true);
        // A10_13 付与月日
        grantMonthDay: KnockoutObservable<number>;
        // A10_13 付与月日
        enableMonthday: KnockoutObservable<boolean>;
        // A10_23, A10_15
        grantDates: KnockoutObservableArray<any>;
        // A10_14 付与基準日
        tGrantDate1: KnockoutObservable<any>;
        // A10_15 付与基準日
        grantDate1ContentEnable: KnockoutObservable<boolean> = ko.observable(false);
        // A10_12, A10_14
        selectedMGrantDate: KnockoutObservable<number>;
        // A10_11
        typeTime3: KnockoutObservable<any>;
        // A12_12, A12_12
        method2ContentEnable: KnockoutObservable<boolean> = ko.observable(false);
        // A10_4 テーブル使用（選択肢3）
        typeTime1: KnockoutObservable<any>;
        // A10_23, A10_9
        method0ContentEnable: KnockoutObservable<boolean> = ko.observable(false);
        // A10_8 付与日数入力欄
        fGrantDays: KnockoutObservable<number>= ko.observable(null);
        
        // A10_21 付与日数入力欄
        pGrantDays: KnockoutObservable<number> = ko.observable(null);
        // A10_9 付与テーブル設定ボタン
        dialogDEnable: KnockoutObservable<boolean> = ko.observable(false);
        // A12_2 有効期限（選択肢１２３４）
        timeSpecifyMethods: KnockoutObservableArray<any>;
        // A12_2 有効期限（選択肢１２３４）_enable
        enableTimeMethods: KnockoutObservable<boolean> = ko.observable(true);
        // A12_2 有効期限（選択肢１２３４）_active
        timeSpecifyMethod: KnockoutObservable<number>;
        // A12_16 上限日数を制限する
        limitEnable: KnockoutObservable<boolean> = ko.observable(true);
        // A12_16 上限日数を制限する
        limit: KnockoutObservable<boolean> = ko.observable(false);
        // A12_8 上限日数入力欄 
        limitCarryoverDays: KnockoutObservable<number>;
        // A12_8 上限日数入力欄
        limitCarryoverDaysEnable: KnockoutObservable<boolean>;
        // A12_10 有効期限入力欄（年）ok
        years: KnockoutObservable<number>;
        // A12_10 有効期限入力欄（年）ok
        yearsEnable: KnockoutObservable<boolean>;
        // A12_11 有効期限入力欄（月）ok
        months: KnockoutObservable<number>;
        // A12_11 有効期限入力欄（月）ok
        monthsEnable: KnockoutObservable<boolean>;
        // A12_12, A12_14
        startDateEnable: KnockoutObservable<boolean>;
        // same as startDateEnable 
        endDateEnable: KnockoutObservable<boolean>;
        // A16_1 性別
        genderSelected: KnockoutObservable<boolean>;
        // A17_1 雇用
        empSelected: KnockoutObservable<boolean>;
        // A18_1 分類
        clsSelected: KnockoutObservable<boolean>;
        // A15_1 年齢
        ageSelected: KnockoutObservable<boolean>;
        // A16_2 性別区分
        genderOptions: KnockoutObservableArray<any>;
        // A16_2 性別区分_enable
        genderOptionEnable: KnockoutObservable<boolean>;
        // A16_2 性別区分_active
        selectedGender: any;
        // A17_2 雇用設定ボタン
        empLst: KnockoutObservableArray<any> = ko.observableArray([]);
        // A18_2 分類設定ボタン
        clsLst: KnockoutObservableArray<any> = ko.observableArray([]);
        // A17_2 雇用設定ボタン
        empLstEnable: KnockoutObservable<boolean>;
        // A18_2 分類設定ボタン
        clsLstEnable: KnockoutObservable<boolean>;
        // A15_2 年齢下限
        startAge: KnockoutObservable<number>;
        // A15_2 年齢下限_enable
        startAgeEnable: KnockoutObservable<boolean>;
        // A15_4 年齢上限
        endAge: KnockoutObservable<number>;
        // A15_4 年齢上限_enable
        endAgeEnable: KnockoutObservable<boolean>;
        // A15_10 指定年
        ageCriteriaCls: KnockoutObservableArray<Items>;
        // A15_10 指定年_active
        selectedAgeCriteria: KnockoutObservable<number>;
        // A15_10 指定年_enable
        ageCriteriaClsEnable: KnockoutObservable<boolean>;
        // A15_12 指定月日
        ageBaseDate: KnockoutObservable<number> = ko.observable(null);
        // A15_12 指定月日 enable
        ageBaseDateEnable: KnockoutObservable<boolean>;
        // A5_3
        selectedTargetItems: any;
        // A5_3
        targetItems: KnockoutObservableArray<any> = ko.observableArray([]);
        // A17_2 雇用設定ボタン
        cdl002Name: KnockoutObservable<String> = ko.observable('');
        // A18_2 分類設定ボタン
        cdl003Name: KnockoutObservable<String> = ko.observable('');
        yearReq: KnockoutObservable<boolean> = ko.observable(true);
        // A10_8 付与日数入力欄
        dayReq: KnockoutObservable<boolean> = ko.observable(true);
        // A1_1 新規ボタン_enable
        newModeEnable: KnockoutObservable<boolean> = ko.observable(true);
        // A15_2, A15_12, A15_4
        ageBaseDateReq: KnockoutObservable<boolean>;
        // A15_12
        ageBaseDateDefaultValue: KnockoutObservable<boolean>;
        // period
        dateRange: KnockoutObservable<any> = ko.observable({});
        // A12_12 指定期間入力欄（年）
        start: KnockoutObservable<number> = ko.observable(); 
        // A12_14 指定期間入力欄（月）
        end: KnockoutObservable<number> = ko.observable();
        // A10_25 連続で取得する
        continuousAcquisition: KnockoutObservable<number> = ko.observable();

        constructor() {
            let self = this;
            self.specialHolidayCode.subscribe(function(value) {
                // clear all error
                nts.uk.ui.errors.clearAll();

                if (value > 0) {
                    nts.uk.ui.block.invisible();
                    service.getSpecialHoliday(value).done(function(data) {
                        if(!data)
                            return;
                        self.setData(data);
                    }).always(() => {
                        nts.uk.ui.block.clear();
                    });
                }
            });
            
            self.autoGrant.subscribe(function(value) {
                
                nts.uk.ui.errors.clearAll();
            });

            self.empLst.subscribe((newData) => {
                if (!_.size(newData)) {
                    self.cdl002Name("");
                    return;
                }

                service.findEmpByCodes(newData).done((datas) => {
                    self.cdl002Name(_.map(datas, item => { return item }).join(' + '));
                });
            });

            self.clsLst.subscribe((newData) => {
                if (!_.size(newData)) {
                    self.cdl003Name("");
                    return;
                }

                service.findClsByCodes(newData).done((datas) => {
                    self.cdl003Name(_.map(datas, item => { return item }).join(' + '));
                });
            });

            self.tabs = ko.observableArray([
                { id: 'tab-1', title: nts.uk.resource.getText('KMF004_11'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: nts.uk.resource.getText('KMF004_12'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-3', title: nts.uk.resource.getText('KMF004_13'), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) }
            ]);

            self.selectedTab = ko.observable('tab-1');

            self.grantDate = ko.observable(0);

            self.typeTimes = ko.observableArray([
                new BoxModel(2, nts.uk.resource.getText('KMF004_171')),
                new BoxModel(3, nts.uk.resource.getText('KMF004_172')),
                new BoxModel(1, nts.uk.resource.getText('KMF004_173'))
            ]);
            self.typeTime2 = ko.observable(new BoxModel(2, nts.uk.resource.getText('KMF004_171')));
            self.typeTime3 = ko.observable(new BoxModel(3, nts.uk.resource.getText('KMF004_172')));
            self.typeTime1 = ko.observable(new BoxModel(1, nts.uk.resource.getText('KMF004_173')));

            self.tGrantDates = ko.observableArray([
                new BoxModel(0, nts.uk.resource.getText('KMF004_174')),
                new BoxModel(1, nts.uk.resource.getText('KMF004_14'))
            ]);
            self.tGrantDate0 = ko.observable(new BoxModel(0, nts.uk.resource.getText('KMF004_174')));
            self.grantMonthDay = ko.observable(null);
            self.enableMonthday = ko.observable(true);
            self.grantDates = ko.observableArray([
                new Items(0, nts.uk.resource.getText('KMF004_15')),
                new Items(1, nts.uk.resource.getText('KMF004_16')),
                new Items(2, nts.uk.resource.getText('KMF004_17'))
            ]);
            self.grantDate = ko.observable(0);
            self.tGrantDate1 = ko.observable(new BoxModel(1, nts.uk.resource.getText('KMF004_14')));

            self.typeTime = ko.observable(2);
            self.selectedMGrantDate = ko.observable(0);

            self.typeTime.subscribe(function(value) {
                nts.uk.ui.errors.clearAll();

                switch(value) {
                    case undefined:
                        self.typeTime(2);
                        setTimeout(() => {
                            $('#A10_3').focus();
                        }, 27);
                        break;
                    case 2:
                        self.method1ContentEnable(true);
                        self.method0ContentEnable(false);
                        self.dialogDEnable(false);
                        self.method2ContentEnable(false);
                        self.limitEnable(true);
                        self.limitCarryoverDaysEnable(true);
                        self.enableTimeMethods(true);
                        self.yearReq(true);
                        self.dayReq(true); 
                        if(self.selectedMGrantDate() == 1){
                            self.grantDate1ContentEnable(true);
                        } else {
                            self.grantDate0ContentEnable(true);
                        }               
                        self.selectedMGrantDate.subscribe((tGrantDate) => {
                            nts.uk.ui.errors.clearAll();
                            if(tGrantDate == undefined){
                                self.selectedMGrantDate(0);
                                self.grantDate0ContentEnable(true);
                                setTimeout(() => {
                                    $('#A10_12').focus();
                                }, 27);
                            }
                            if(tGrantDate == 0){
                                self.grantDate0ContentEnable(true);
                                self.grantDate1ContentEnable(false);
                            } else if(tGrantDate == 1){
                                self.grantDate0ContentEnable(false);
                                self.grantDate1ContentEnable(true);
                            }
                        });
                        break;
                    case 3:
                        self.method2ContentEnable(true);
                        self.method1ContentEnable(false);
                        self.grantDate0ContentEnable(false);
                        self.dialogDEnable(false);
                        self.grantDate1ContentEnable(false);
                        self.method0ContentEnable(false);
                        self.enableTimeMethods(false);
                        self.limitEnable(false);
                        break;
                    case 1:
                        self.fGrantDays(0);
                        self.method0ContentEnable(true);
                        self.method1ContentEnable(false);
                        self.grantDate0ContentEnable(false);
                        self.grantDate1ContentEnable(false);
                        self.method2ContentEnable(false);
                        self.limitEnable(true);
                        self.enableTimeMethods(true);
                        self.yearReq(false);
                        self.dayReq(false);
                        if (!self.newModeEnable()) {
                            self.dialogDEnable(false);
                        } else {
                            self.dialogDEnable(true);
                        }
                        break;
                    }
                });

            self.limit.subscribe((val) => {
                if(val == true){
                    self.limitCarryoverDaysEnable(true);
                } else {
                    self.limitCarryoverDaysEnable(false);
                }

            });
            self.timeSpecifyMethods = ko.observableArray([
                new BoxModel(0, nts.uk.resource.getText('KMF004_28')),
                new BoxModel(1, nts.uk.resource.getText('KMF004_29')),
                new BoxModel(2, nts.uk.resource.getText('KMF004_30'))
            ]);

            self.timeSpecifyMethod = ko.observable(0);

            self.limitCarryoverDays = ko.observable(null);
            self.limitCarryoverDaysEnable = ko.observable(false);
            self.years = ko.observable(null);
            self.yearsEnable = ko.observable(false);
            self.months = ko.observable(null);
            self.monthsEnable = ko.observable(false);
            self.startDateEnable = ko.observable(false);
            self.endDateEnable = ko.observable(false);


            self.timeSpecifyMethod.subscribe(function(value) {
                nts.uk.ui.errors.clearAll();

                switch (value) {
                    case 0:
                        self.yearsEnable(false);
                        self.monthsEnable(false);
                        self.years('');
                        self.months('');
                        self.start(null);
                        self.end(null);
                        break;
                    case 1:
                        self.yearsEnable(true);
                        self.monthsEnable(true);
                        self.limitCarryoverDays('');
                        self.start();
                        self.end();
                        break;
                    case 2:
                        self.yearsEnable(false);
                        self.monthsEnable(false);
                        self.limitCarryoverDaysEnable('');
                        self.years('');
                        self.months('');
                        self.start();
                        self.end();
                        break;
                }
            });

            self.genderSelected = ko.observable(false);
            self.empSelected = ko.observable(false);
            self.clsSelected = ko.observable(false);
            self.ageSelected = ko.observable(false);

            self.genderOptions = ko.observableArray([
                { code: '1', name: nts.uk.resource.getText('KMF004_55') },
                { code: '2', name: nts.uk.resource.getText('KMF004_56') }
            ]);

            self.selectedGender = ko.observable(1);
            self.genderOptionEnable = ko.observable(false);

            self.empLstEnable = ko.observable(false);
            self.clsLstEnable = ko.observable(false);

            self.startAge = ko.observable();
            self.startAgeEnable = ko.observable(false);
            self.endAge = ko.observable();
            self.endAgeEnable = ko.observable(false);

            self.ageCriteriaCls = ko.observableArray([
                new Items(0, nts.uk.resource.getText('Enum_ReferenceYear_THIS_YEAR')),
                new Items(1, nts.uk.resource.getText('Enum_AgeBaseYearAtr_THIS_MONTH'))
            ]);

            self.selectedAgeCriteria = ko.observable(null);
            self.ageCriteriaClsEnable = ko.observable(false);

            self.ageBaseDate = ko.observable(null);
            self.ageBaseDateEnable = ko.observable(false);

            self.ageBaseDateReq = ko.observable(false);
            self.ageBaseDateDefaultValue = ko.observable(true);

            self.genderSelected.subscribe(function(value) {
                if (value) {
                    self.genderOptionEnable(true);
                } else {
                    self.genderOptionEnable(false);
                    self.selectedGender(1);
                }
            });

            self.empSelected.subscribe(function(value) {
                if (value) {
                    self.empLstEnable(true);
                } else {
                    self.empLstEnable(false);
                    self.empLst([]);
                }
            });

            self.clsSelected.subscribe(function(value) {
                if (value) {
                    self.clsLstEnable(true);
                } else {
                    self.clsLstEnable(false);
                    self.clsLst([]);
                }
            });

            self.ageSelected.subscribe(function(value) {
                $("#startAge").ntsError('clear');
                $("#endAge").ntsError('clear');
                if (value) {
                    self.startAgeEnable(true);
                    self.endAgeEnable(true);
                    self.ageCriteriaClsEnable(true);
                    self.ageBaseDateEnable(true);
                    self.ageBaseDateReq(true);
                    self.ageBaseDateDefaultValue(false);
                } else {
                    self.startAgeEnable(false);
                    self.endAgeEnable(false);
                    self.ageCriteriaClsEnable(false);
                    self.ageBaseDateEnable(false);
                    self.ageBaseDateReq(false);
                    self.ageBaseDateDefaultValue(true);
                }
            });
        }
        // for update mode
        setData(data: any) {
            let self = this;
            self.selectedTab('tab-1');
            self.btnDeleteEnable(true);
            self.specialHolidayCodeEnable(false);
            self.editMode(true);
            $("#input-name").focus();
            self.newModeEnable(true);
            
            self.selectedTargetItems = [];

            self.specialHolidayCode(data.specialHolidayCode);
            self.specialHolidayName(data.specialHolidayName);
            self.autoGrant(data.autoGrant);
            self.memo(data.memo);
            self.continuousAcquisition(data.continuousAcquisition);  
            if(data.grantRegularDto){
                self.typeTime(data.grantRegularDto.typeTime); 
                self.grantDate(data.grantRegularDto.grantDate);
                if(data.grantRegularDto.fixGrantDate){
                    self.fGrantDays(data.grantRegularDto.fixGrantDate.grantDays);
                    let grantMonthDay = data.grantRegularDto.fixGrantDate.grantMonthDay;
                    self.grantMonthDay(grantMonthDay.month*100 + grantMonthDay.day);
                    if(data.grantRegularDto.fixGrantDate.grantPeriodic){
                        if(self.typeTime() == 2)
                            self.timeSpecifyMethod(data.grantRegularDto.fixGrantDate.grantPeriodic.timeSpecifyMethod);
                        if(data.grantRegularDto.fixGrantDate.grantPeriodic.limitAccumulationDays){
                            if(self.typeTime() == 2 ){
                                self.limitCarryoverDays(data.grantRegularDto.fixGrantDate.grantPeriodic.limitAccumulationDays.limitCarryoverDays);
                                self.limit(data.grantRegularDto.fixGrantDate.grantPeriodic.limitAccumulationDays.limit);
                            }
                        }
                        if(data.grantRegularDto.fixGrantDate.grantPeriodic.expirationDate){
                            if(self.typeTime() == 2){
                                self.years(data.grantRegularDto.fixGrantDate.grantPeriodic.expirationDate.years);
                                self.months(data.grantRegularDto.fixGrantDate.grantPeriodic.expirationDate.months);
                            }
                        }
                    }
                }
                if(data.grantRegularDto.periodGrantDate){
                    self.pGrantDays(data.grantRegularDto.periodGrantDate.grantDays);
                }
                if(data.grantRegularDto.grantPeriodic){
                    if(self.typeTime() == 1)
                        self.timeSpecifyMethod(data.grantRegularDto.grantPeriodic.timeSpecifyMethod);
                    if(data.grantRegularDto.grantPeriodic.limitAccumulationDays){
                        if(self.typeTime() == 1){
                            self.limitCarryoverDays(data.grantRegularDto.grantPeriodic.limitAccumulationDays.limitCarryoverDays);
                            self.limit(data.grantRegularDto.grantPeriodic.limitAccumulationDays.limit);
                        }
                    }
                    if(data.grantRegularDto.grantPeriodic.expirationDate){
                        if(self.typeTime() == 1){
                            self.years(data.grantRegularDto.grantPeriodic.expirationDate.years);
                            self.months(data.grantRegularDto.grantPeriodic.expirationDate.months);
                        }
                    }
                }
            }
            if(data.grantPeriodicDto && data.grantPeriodicDto.availabilityPeriod){
                self.start(data.grantPeriodicDto.availabilityPeriod.startDate);
                self.end(data.grantPeriodicDto.availabilityPeriod.endDate);
            }
            if(data.specialLeaveRestrictionDto){
                self.genderSelected(data.specialLeaveRestrictionDto.genderRest == 0 ? true : false);
                self.selectedGender(data.specialLeaveRestrictionDto.gender);
                self.empSelected(data.specialLeaveRestrictionDto.restEmp == 0 ? true : false);
                self.empLst(_.map(data.specialLeaveRestrictionDto.listEmp, item => { return item }));
                self.clsSelected(data.specialLeaveRestrictionDto.restrictionCls == 0 ? true : false);
                self.clsLst(_.map(data.specialLeaveRestrictionDto.listCls, item => { return item }));
                self.ageSelected(data.specialLeaveRestrictionDto.ageLimit == 0 ? true : false);
                self.startAge(data.specialLeaveRestrictionDto.ageRange.ageLowerLimit);
                self.endAge(data.specialLeaveRestrictionDto.ageRange.ageHigherLimit);
                self.selectedAgeCriteria(data.specialLeaveRestrictionDto.ageStandard.ageCriteriaCls);
                let ageBaseDate = data.specialLeaveRestrictionDto.ageStandard.ageBaseDate;
                self.ageBaseDate(ageBaseDate.month * 100 + ageBaseDate.day);
            }
            let targetItems:any[] = [];
            if (data.targetItemDto.absenceFrameNo != null && data.targetItemDto.absenceFrameNo.length > 0) {
                _.forEach(data.targetItemDto.absenceFrameNo, function(item) {
                    targetItems.push("b" + item);
                });
            }

            if (data.targetItemDto && data.targetItemDto.frameNo != null && data.targetItemDto.frameNo.length > 0) {
                _.forEach(data.targetItemDto.frameNo, function(item) {
                    targetItems.push("a" + item);
                });
            }

            let temp:any[] = [];
            _.forEach(targetItems, function(code) {
                let selectedItem = _.find(self.targetItems(), function(o) { return o.code == code; });
                if (!selectedItem) {
                    let frameNo = code.substring(1, code.length),
                        itemType = code.charAt(0);
                    selectedItem = {
                        code: code,
                        itemType: itemType,
                        frameNo: frameNo,
                        name: frameNo + nts.uk.resource.getText("KMF004_163"),
                    }
                }
                temp.push(selectedItem);
            });

            let text = "";
            _.forEach(_.orderBy(temp, ['itemType', 'frameNo'], ['asc']), function(item) {
                text += item.name + " + ";
            });

            self.targetItemsName(text.substring(0, text.length - 3));

            if (self.selectedTargetItems == null || self.selectedTargetItems.length <= 0) {
                self.selectedTargetItems = targetItems;
            }

            nts.uk.ui.errors.clearAll();
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            nts.uk.ui.block.invisible();
            $.when(self.getSphdData(), self.findAllItemFrame()).done(function() {
                if (self.sphdList().length > 0) {
                    self.specialHolidayCode(self.sphdList()[0].specialHolidayCode);
                    self.specialHolidayCode.valueHasMutated();
                } else {
                    self.newModeEnable(false);
                    self.clearForm();
                }

                dfd.resolve();
            }).fail(function(res) {
                dfd.reject(res);
            }).always(() => {
                nts.uk.ui.block.clear();
            });

            return dfd.promise();
        }

        findAllItemFrame(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.findAllItemFrame().done(function(data) {
                self.targetItems(_.map(data, (item) => {
                    return new ItemFrame(item);
                }));
                dfd.resolve();
            }).fail(function(res) {
                dfd.reject(res);
            });
            return dfd.promise();
        }

        getSphdData(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();

            self.sphdList([]);
            service.findByCid().done(function(data) {
                _.forEach(data, function(item) {
                    self.sphdList.push(new ItemModel(item.specialHolidayCode, item.specialHolidayName));
                });

                dfd.resolve(data);
            }).fail(function(res) {
                dfd.reject(res);
            });

            return dfd.promise();
        }

        openDialogD() {
            let self = this;

            if (nts.uk.ui.errors.hasError()) {
                return;
            }

            nts.uk.ui.windows.setShared("KMF004_A_DATA", self.specialHolidayCode());

            nts.uk.ui.windows.sub.modal("/view/kmf/004/d/index.xhtml").onClosed(() => {

            });
        }

        openJDialog() {
            let self = this;

            let currentCodeList:any[] = [];

            _.forEach(self.selectedTargetItems, function(code) {
                currentCodeList.push(code);
            });

            nts.uk.ui.windows.setShared("KMF004_A_TARGET_ITEMS", {
                currentCodeList: currentCodeList,
                selectedCode: self.specialHolidayCode()
            });

            nts.uk.ui.windows.sub.modal("/view/kmf/004/j/index.xhtml").onClosed(() => {
                let selectedData = nts.uk.ui.windows.getShared("KMF004_J_SELECTED_ITEMS");
                self.selectedTargetItems = selectedData != null ? selectedData : self.selectedTargetItems;

                let temp:any[] = [];
                _.forEach(self.selectedTargetItems, function(code) {
                    let selectedItem : ItemFrame = _.find(self.targetItems(), function(o) { return o.code == code; });
                    if (selectedItem) {
                        temp.push(selectedItem);
                    }
                });

                let text = "";
                _.forEach(_.orderBy(temp, ['itemType', 'frameNo'], ['asc']), function(item) {
                    text += item.name + " + ";
                });

                self.targetItemsName(text.substring(0, text.length - 3));

                if (self.selectedTargetItems.length > 0) {
                    nts.uk.ui.errors.clearAll();
                }
            });
        }

        openCDL002Dialog() {
            let self = this;

            nts.uk.ui.windows.setShared('CDL002Params', {
                isMultiple: true,
                selectedCodes: self.empLst(),
                showNoSelection: false,
                isShowWorkClosure: false
            }, true);

            nts.uk.ui.windows.sub.modal("com", "/view/cdl/002/a/index.xhtml").onClosed(() => {
                let isCancel = nts.uk.ui.windows.getShared('CDL002Cancel');

                if (isCancel) {
                    return;
                }

                let output = nts.uk.ui.windows.getShared('CDL002Output');

                self.empLst(output);

            });
        }

        openCDL003Dialog() {
            let self = this;
            nts.uk.ui.windows.setShared('inputCDL003', {
                isMultiple: true,
                selectedCodes: self.clsLst(),
                showNoSelection: false,
            }, true);
            nts.uk.ui.windows.sub.modal("com", "/view/cdl/003/a/index.xhtml").onClosed(() => {
                let data = nts.uk.ui.windows.getShared('outputCDL003');
                if (data) {
                    self.clsLst(data);
                }
            });
        }

        preData(): service.SpecialHolidayCommand {
            let self = this;
            let expirationDate: service.SpecialVacationDeadlineCommand = {
                months: self.months(),
                years: self.years()
            };
            let today = new Date();
            let fullYear = today.getFullYear();
            let start: string = `${fullYear}/${self.fillZero((self.start()/100).toString())}/${self.start()%100}`;
            let end: string = `${fullYear}/${self.fillZero((self.end()/100).toString())}/${self.end()%100}`;;

            let period: service.DatePeriodCommand = {
                start: '2021/01/01',
                end: '2021/11/11'
                // start: start, // todo
                // end: end
            };
            let limitAccumulationDays: service.LimitAccumulationDaysCommand = {
                limit: self.limit(),
                limitCarryoverDays: self.limitCarryoverDays()               
            };
            let ageRange: service.AgeRangeCommand = {
                ageLowerLimit: self.startAge(),
                ageHigherLimit: self.endAge()
            };
            let ageStandard: service.AgeStandardCommand = {
                ageCriteriaCls: self.selectedAgeCriteria(),
                ageBaseDate: 111 // todo
            };
            
            
            let periodGrantDate: service.PeriodGrantDateCommand = {
                period: period,
                grantDays: self.pGrantDays()
            }
            let grantPeriodic: service.GrantDeadlineCommand = {
                timeSpecifyMethod: self.timeSpecifyMethod(),
                expirationDate: expirationDate,
                limitAccumulationDays: limitAccumulationDays
            }
            let fixGrantDate: service.FixGrantDateCommand = {
                grantDays: self.fGrantDays(),
                grantPeriodic: grantPeriodic,
                grantMonthDay: self.grantMonthDay()
            };

            
            let absence: any[] = [];
            let frame: any[] = [];
            _.forEach(self.selectedTargetItems, function(code) {
                if (code.indexOf("b") > -1) {
                    absence.push(code.slice(1));
                } else {
                    frame.push(code.slice(1));
                }
            });
            let targetItem: service.TargetItemCommand = {
                absenceFrameNo: absence,
                frameNo: frame
            };
            let specialLeaveRestriction: service.SpecialLeaveRestrictionCommand = {
                companyId: "",
                specialHolidayCode: self.specialHolidayCode(),
                restrictionCls: self.clsSelected() ? 0 : 1,
                ageLimit: self.ageSelected() ? 0 : 1,
                genderRest: self.genderSelected() ? 0 : 1,
                restEmp: self.empSelected() ? 0 : 1,
                listCls: self.clsLst(),
                ageStandard: ageStandard,
                ageRange: ageRange,
                gender: self.selectedGender(),
                listEmp: self.empLst()
            };
            let grantRegular: service.GrantRegularCommand = {
                typeTime: self.typeTime(),
                grantDate: self.grantDate(),
                fixGrantDate: fixGrantDate,
                grantPeriodic: grantPeriodic,
                periodGrantDate: periodGrantDate
            };

            let dataItem: service.SpecialHolidayCommand = {
                companyId: "",
                specialHolidayCode: self.specialHolidayCode(),
                specialHolidayName: self.specialHolidayName(),
                regularCommand: grantRegular,
                leaveResCommand: specialLeaveRestriction,
                targetItemCommand: targetItem,
                autoGrant: self.autoGrant(),
                continuousAcquisition: self.continuousAcquisition(),
                memo: self.memo()
            };
            return dataItem;
        }

        saveSpecialHoliday(): JQueryPromise<any> {
            let self = this;
            nts.uk.ui.errors.clearAll();
            nts.uk.ui.block.invisible();

            if (self.sphdList().length == 20 && !self.editMode()) {
                self.addListError(["Msg_669"]);
                nts.uk.ui.block.clear();
                return;
            }

            $("#input-code").trigger("validate");
            $("#input-name").trigger("validate");
            $(".period-date-inp").trigger("validate");
            let dataItem = self.preData();
            if (dataItem.targetItemCommand.absenceFrameNo.length <= 0 && dataItem.targetItemCommand.frameNo.length <= 0) {
                $("#target-items").ntsError('set', { messageId:'Msg_93' });
            }
            if (self.autoGrant() == 1) {
                $("#ageBaseDate").trigger("validate");
                $("#startDate").trigger("validate");
                $("#endDate").trigger("validate");
                $("#startAge").trigger("validate");
                $("#endAge").trigger("validate");
                // if (self.yearReq() && self.dayReq()) {
                //     if (dataItem.regularCommand.fixGrantDate.grantDays === 0 && dataItem.regularCommand.fixGrantDate.grantDays === 0 ){
                //         $("#years").ntsError("set", "付与周期を入力してください", "MsgB_1");
                //         $("#days").ntsError("set", "付与日数を入力してください", "MsgB_1");
                //     }
                // }
                // if (self.timeSpecifyMethod() == 0 && dataItem.regularCommand.grantPeriodic.limitAccumulationDays.limit == false) {
                //     $("#limitCarryoverDays").ntsError("set", "蓄積上限日数を入力してください", "MsgB_1");
                // }
                if(self.empSelected() && self.empLst().length <= 0)
                    $("#A17_2").ntsError('set', { messageId:'Msg_105' });
                if(self.clsSelected() && self.clsLst().length <= 0)
                    $("#A18_2").ntsError('set', { messageId:'Msg_108' });
                if(self.startAge() > self.endAge())
                    $("#startAge").ntsError('set', { messageId:'Msg_119' });
            }

            if (nts.uk.ui.errors.hasError()) {
                nts.uk.ui.block.clear();
                return;
            }

            if (!self.editMode()) {

                service.add(dataItem).done(function(errors) {
                    _.forEach(errors, function(err) {
                        if (err === "Msg_3") {
                            $("#input-code").ntsError("set", { messageId: "Msg_3" });
                        }
                    });

                    if (errors && errors.length > 0) {
                        self.addListError(errors);
                    } else {
                        self.getSphdData().done(() => {
                            nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => {
                                $("#input-name").focus();
                                self.specialHolidayCodeEnable(false);
                                self.editMode(true);
                                self.newModeEnable(true);
                            });
                        });
                    }
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError({ messageId: error.messageId }).then(() => {
                        if (error.messageId === "Msg_3") {
                            $('#input-code').focus();
                        }
                    });
                }).always(function() {
                    nts.uk.ui.block.clear();
                });
            } else {
                service.update(dataItem).done(function(errors) {
                    console.log("dataItem,: ", dataItem);
                    if (errors && errors.length > 0) {
                        self.addListError(errors);
                    } else {
                        self.getSphdData().done(() => {
                            self.specialHolidayCode.valueHasMutated();
                            nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => {
                                $("#input-name").focus();
                                self.specialHolidayCodeEnable(false);
                            });
                        });
                    }
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId });
                }).always(function() {
                    nts.uk.ui.block.clear();
                });
            }
        }

        deleteSpecialHoliday() {
            let self = this;


            let count = 0;
            for (let i = 0; i <= self.sphdList().length; i++) {
                if (self.sphdList()[i].specialHolidayCode == self.specialHolidayCode()) {
                    count = i;
                    break;
                }
            }

            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                nts.uk.ui.block.invisible();
                service.remove(self.specialHolidayCode()).done(function() {
                    self.getSphdData().done(function() {
                        // if number of item from list after delete == 0 
                        if (self.sphdList().length == 0) {
                            self.clearForm();
                            return;
                        }
                        // delete the last item
                        if (count == ((self.sphdList().length))) {
                            self.specialHolidayCode(self.sphdList()[count - 1].specialHolidayCode);
                            return;
                        }
                        // delete the first item
                        if (count == 0) {
                            self.specialHolidayCode(self.sphdList()[0].specialHolidayCode);
                            return;
                        }
                        // delete item at mediate list 
                        else if (count > 0 && count < self.sphdList().length) {
                            self.specialHolidayCode(self.sphdList()[count].specialHolidayCode);
                            return;
                        }
                    });

                    nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(() => {
                        if (self.editMode()) {
                            $("#input-name").focus();
                        } else {
                            $("#input-code").focus();
                        }
                    });
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError({ messageId: error.messageId });
                }).always(function() {
                    nts.uk.ui.block.clear();
                });
            })
        }

        initSpecialHoliday(): void {
            let self = this;
            self.clearForm();
        }

        clearForm() {
            let self = this;

            self.selectedTargetItems = [];

            self.editMode(false);
            self.specialHolidayCode(null);
            self.btnDeleteEnable(false);
            self.specialHolidayCodeEnable(true);
            self.specialHolidayName("");
            self.autoGrant(1);
            self.targetItemsName("");
            self.memo("");

            self.newModeEnable(false);

            self.selectedTab('tab-1');

            self.grantDate(0);
            self.typeTime(2);
            self.method1ContentEnable(true);
            self.selectedMGrantDate(0);
            self.grantDate0ContentEnable(true);
            self.method0ContentEnable(false);
            self.method2ContentEnable(false);
            self.dialogDEnable(false);
            self.fGrantDays(0);

            self.timeSpecifyMethod(0);
            self.limitCarryoverDays('');
            self.years('');
            self.months('');
            self.start();
            self.end();
            self.dateRange({ startDate: "", endDate: "" });
            self.genderSelected(false);
            self.empSelected(false);
            self.clsSelected(false);
            self.ageSelected(false);
            self.selectedGender(1);
            self.empLst([]);
            self.clsLst([]);
            self.startAge("");
            self.endAge("");
            self.selectedAgeCriteria(0);
            self.ageBaseDate(0);
            self.ageBaseDateReq(false);
            self.ageBaseDateDefaultValue(true);
            self.yearReq(true);
            self.dayReq(true);

            $("#input-code").focus();
        }

        /**
         * Set error
         */
        addListError(errorsRequest: Array<string>) {
            let self = this;

            let errors: any[] = [];
            _.forEach(errorsRequest, function(err) {
                errors.push({ message: nts.uk.resource.getMessage(err), messageId: err, supplements: {} });
            });

            nts.uk.ui.dialog.bundledErrors({ errors: errors }).then(() => {
                //                _.forEach(errors, function(err) {
                //                    if(err.messageId === "Msg_3") {
                //                        $("#input-code").ntsError("set", {messageId:"Msg_3"});
                //                    }
                //                });
            });
        }
        private fillZero(str: string): string{
            return str.length == 2 ? str : `0${str}`;
        }
    }

    class Items {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    class ItemModel {
        specialHolidayCode: number;
        specialHolidayName: string;

        constructor(specialHolidayCode: number, specialHolidayName: string) {
            this.specialHolidayCode = specialHolidayCode;
            this.specialHolidayName = specialHolidayName;
        }
    }

    class ItemFrame {
        code: string;
        itemType: string;
        frameNo: number;
        name: string;
        constructor(data: any) {
            if (data) {
                this.code = data.itemType + data.specialHdFrameNo;
                this.itemType = data.itemType;
                this.frameNo = data.specialHdFrameNo;
                this.name = data.specialHdFrameName;
            }
        }
    }

    class BoxModel {
        id: number;
        name: string;

        constructor(id: any, name: any) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }

    class ItemData {
        code: string;
        name: string;
        types: number;

        constructor(code: string, name: string, types: number) {
            this.code = code;
            this.name = name;
            this.types = types;
        }
    }
}