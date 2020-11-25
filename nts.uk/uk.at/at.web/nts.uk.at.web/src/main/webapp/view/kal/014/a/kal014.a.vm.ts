module nts.uk.at.kal014.a {

    import common=nts.uk.at.kal014.common;
    const PATH_API = {
        GET_ALL_SETTING: 'at/record/alarmwrkp/screen/getAll',
        GET_SETTING_BYCODE: 'alarmworkplace/patternsetting/getPatternSetting',
    }

    const SCREEN = {
        B: 'B',
        C: 'C'
    }

    @bean()
    export class Kal014AViewModel extends ko.ViewModel {

        backButon: string = "/view/kal/013/a/index.xhtml";
        gridItems: KnockoutObservableArray<GridItem> = ko.observableArray([]);
        currentCode: KnockoutObservable<string> = ko.observable(null);
        alarmPattern: AlarmPattern = new AlarmPattern('', '');
        selectedExecutePermission: KnockoutObservable<any>;
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;
        isNewMode: KnockoutObservable<boolean>;
        itemsSwap: KnockoutObservableArray<ItemModel>;
        tableItems: KnockoutObservableArray<TableItem>;
        columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        currentCodeListSwap: KnockoutObservableArray<any>;
        test: KnockoutObservableArray<any>;
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
        workPalceCategory: any;
        alarmPatterSet : KnockoutObservable<AlarmPatternObject>= ko.observable(null);

        constructor(props: any) {
            super();
            const vm = this;
            vm.workPalceCategory = common.WORKPLACE_CATAGORY;
            vm.tabs = ko.observableArray([
                {
                    id: 'tab-1',
                    title: vm.$i18n('KAL014_17'),
                    content: '.tab-content-1',
                    enable: ko.observable(true),
                    visible: ko.observable(true)
                },
                {
                    id: 'tab-2',
                    title: vm.$i18n('KAL014_18'),
                    content: '.tab-content-2',
                    enable: ko.observable(true),
                    visible: ko.observable(true)
                },
                {
                    id: 'tab-3',
                    title: vm.$i18n('KAL014_19'),
                    content: '.tab-content-3',
                    enable: ko.observable(true),
                    visible: ko.observable(true)
                }
            ]);
            vm.selectedTab = ko.observable('tab-1');
            vm.itemsSwap = ko.observableArray([]);
            vm.tableItems = ko.observableArray([]);
            var array = [];
            //TODO change mock data with master data
            for (var i = 0; i < 6; i++) {
                array.push(new TableItem(i, "マスタチェック" + i, i, i, "締め開始日", i, i, i, i + 1, vm.workPalceCategory));
            }
            this.tableItems(array);
            array = [];
            //TODO change mock data with master data
            for (var i = 0; i < 100; i++) {
                let value = i + "";
                array.push(new ItemModel(_.padStart(value, 3, '0'), '基本給', "カテゴリ"));
            }
            vm.itemsSwap(array);
            vm.columns = ko.observableArray([
                {headerText: vm.$i18n('KAL014_22'), key: 'category', width: 70},
                {headerText: vm.$i18n('KAL014_23'), key: 'code', width: 70},
                {headerText: vm.$i18n('KAL014_24'), key: 'name', width: 180}
            ]);
            vm.currentCodeListSwap = ko.observableArray([]);
            vm.test = ko.observableArray([]);
            vm.roundingRules = ko.observableArray([
                {code: '1', name: vm.$i18n('KAL014_30')},
                {code: '2', name: vm.$i18n('KAL014_31')}
            ]);
            vm.selectedRuleCode = ko.observable(null);
            vm.isNewMode = ko.observable(true);
            vm.selectedExecutePermission = ko.observable("");
            $("#fixed-table").ntsFixedTable({height: 320, width: 830});
            vm.getAllSettingData(null);
        }

        created() {
            const vm = this;
            _.extend(window, {vm});
            vm.currentCode.subscribe((code:any)=>{
                vm.getSelectedData(code).done(()=>{
                    $("#A3_3").focus();
                });
                //TODO write server side logic and call API here
            });
        }

        remove() {
            this.itemsSwap.shift();
        }

        getAllSettingData(selectedCd?: string): JQueryPromise<any>{
            const vm = this;
            let dfd = $.Deferred();
            vm.$ajax(PATH_API.GET_ALL_SETTING)
                .done((data: Array<IAlarmPatternSet> )=>{
                    if (!data || data.length <= 0){
                        // Set to New Mode
                        dfd.resolve();
                        return;
                    }
                    vm.gridItems( _.map(data, x=> new GridItem(x.alarmPatternCD, x.alarmPatternName)));
                    // In case of update mode select on updated item
                    if (selectedCd){
                        vm.currentCode(selectedCd);
                    } else {
                        // In case of init screen select on first item
                        vm.currentCode(vm.gridItems()[0].code);
                    }
                    dfd.resolve();
                })
                .fail( (err: any) =>{
                    vm.$dialog.error(err);
                    dfd.reject();
                });
            return dfd.promise();

        }

        getSelectedData(alarmCd: string): JQueryPromise<any>{
            const vm = this;
            let dfd = $.Deferred();
            // Get Data of select item
            vm.$ajax(PATH_API.GET_SETTING_BYCODE,{patternCode: alarmCd})
                .done((data: IAlarmPatternObject)=>{
                    console.log(data);
                    let perSet: WkpAlarmPermissionSettingDto ;
                    perSet = new WkpAlarmPermissionSettingDto(data.alarmPerSet);
                    let checkCon: Array<WkpCheckConditionDto> = [];

                    _.forEach(data.checkConList, (value)=>{
                        checkCon.push(vm.createDataforCategory(value));
                    });
                    vm.alarmPatterSet(new AlarmPatternObject(data.alarmPatternCD,data.alarmPatternName,perSet,checkCon));
                })
                .fail(()=>{

                });


            return dfd.promise();
        }


        createDataforCategory(data: IWkpCheckConditionDto): WkpCheckConditionDto{
            const vm = this;
            let extracDai: WkpExtractionPeriodDailyDto,
                extracSingMon: WkpSingleMonthDto,
                listExtracMon: WkpExtractionPeriodMonthlyDto;
            switch (data.alarmCategory) {
                // 月次
                case vm.workPalceCategory.MONTHLY:
                    extracSingMon = new WkpSingleMonthDto(data.singleMonth);
                    break

                // マスタチェック(基本)
                case vm.workPalceCategory.MASTER_CHECK_BASIC:
                // マスタチェック(職場)
                case vm.workPalceCategory.MASTER_CHECK_WORKPLACE:
                    listExtracMon = new WkpExtractionPeriodMonthlyDto(data.listExtractionMonthly);
                    break;

                //マスタチェック(日次)
                case vm.workPalceCategory.MASTER_CHECK_DAILY:
                // スケジュール／日次
                case vm.workPalceCategory.SCHEDULE_DAILY:
                // 申請承認
                case vm.workPalceCategory.APPLICATION_APPROVAL:
                    extracDai = new WkpExtractionPeriodDailyDto(data.extractionDaily);
                    break
            }

            return new WkpCheckConditionDto(data.alarmCategory,data.checkConditionCodes,extracDai,listExtracMon,extracSingMon);
        }
        /**
         * This function is responsible to click per item action and take decision which screen will open
         * @param item
         * @return type void
         *
         * */
        clickTableItem(item: any) {
            const vm = this;
            if (item.categoryId === vm.workPalceCategory.MASTER_CHECK_BASIC
                || item.categoryId === vm.workPalceCategory.MASTER_CHECK_WORKPLACE
                || item.categoryId === vm.workPalceCategory.MONTHLY) {
                vm.openScreen(item, SCREEN.B);
            } else {
                vm.openScreen(item, SCREEN.C);
            }
        }

        /**
         * This function is responsible open modal KAL014 B, C
         * @param item
         * @param type <screen type B and C>
         * @return type void
         *
         * */
        openScreen(item: any, type: any) {
            const vm = this;
            let modalPath = type === SCREEN.B ? '/view/kal/014/b/index.xhtml' : '/view/kal/014/c/index.xhtml';
            let modalDataKey = type === SCREEN.B ? 'KAL014BModalData' : 'KAL014CModalData';
            vm.$window.storage(modalDataKey, item).done(() => {
                vm.$window.modal(modalPath)
                    .then((result: any) => {
                        console.log(nts.uk.ui.windows.getShared(modalDataKey));
                        //TODO write businees login with return data and make sure server side logic
                    });
            });
        }

        /**
         * This function is responsible to make screen the new mode
         * @return type void
         *
         * */
        clickNewButton() {
            const vm = this;
            vm.isNewMode(true);
            vm.$errors("clear", ".nts-editor", "button").then(() => {
                $("#A3_2").focus();
                vm.currentCode("");
                vm.cleanInput();
            });
        }

        /**
         * This function is responsible clean input data
         * @return type void
         *
         * */
        cleanInput() {
            const vm = this;
            vm.alarmPattern.code("");
            vm.alarmPattern.name("");
        }

        /**
         * This function is responsible to register data
         * @return type void
         *
         * */
        clickRegister() {
            const vm = this;
            vm.isNewMode(false);
            //TODO server business logic
        }

        /**
         * This function is responsible to delete data
         * @return type void
         *
         * */
        clickDeleteButton() {
            //TODO server business logic
        }

        /**
         * This function is responsible to configuration action
         * @return type void
         *
         * */
        clickConfiguration() {
            alert("Configuration");
            //TODO server business logic
        }
    }

    class ItemModel {
        code: string;
        name: string;
        category: string;

        constructor(code: string, name: string, category: string) {
            this.code = code;
            this.name = name;
            this.category = category;
        }
    }

    class TableItem {
        categoryId: any;
        categoryName: string;
        extractionPeriod: string;
        startMonth: any;
        endMonth: any;
        classification: any;
        numberOfDayFromStart: any;
        numberOfDayFromEnd: any;
        beforeAndAfterStart: any;
        beforeAndAfterEnd: any;
        workPalceCategory: any;

        constructor(categoryId: any,
                    categoryName: string,
                    startMonth: any,
                    endMonth: any,
                    classification: any,
                    numberOfDayFromStart: any,
                    numberOfDayFromEnd: any,
                    beforeAndAfterStart: any,
                    beforeAndAfterEnd: any,
                    workPalceCategory: any) {
            this.categoryId = categoryId;
            this.categoryName = categoryName;
            this.startMonth = startMonth;
            this.endMonth = endMonth;
            this.classification = classification;
            this.numberOfDayFromStart = numberOfDayFromStart;
            this.numberOfDayFromEnd = numberOfDayFromEnd;
            this.beforeAndAfterStart = beforeAndAfterStart;
            this.beforeAndAfterEnd = beforeAndAfterEnd;
            this.workPalceCategory = workPalceCategory;
            this.extractionPeriod = (this.getExtractionPeriod(this));
        }

        /**
         * This function is responsible for getting extraction Period
         *
         * @param TableItem
         * @return string
         * */
        getExtractionPeriod(item: TableItem): string {
            const vm = this;
            let extractionText = "";
            switch (item.categoryId) {
                // マスタチェック(基本)
                case item.workPalceCategory.MASTER_CHECK_BASIC:
                    extractionText = this.getMonthValue(item.startMonth) + " ~ " + this.getMonthValue(item.endMonth);
                    break;
                // マスタチェック(職場)
                case item.workPalceCategory.MASTER_CHECK_WORKPLACE:
                    extractionText = this.getMonthValue(item.startMonth) + " ~ " + this.getMonthValue(item.endMonth);
                    break;
                //マスタチェック(日次)
                case item.workPalceCategory.MASTER_CHECK_DAILY:
                    extractionText = this.getMonthValue(item.startMonth) + "の" + item.classification + " ~ " + this.getMonthValue(item.endMonth) + "の締め終了日";
                    break;
                // スケジュール／日次
                case item.workPalceCategory.SCHEDULE_DAILY:
                    extractionText = this.getMonthValue(item.startMonth) + "の" + item.classification + " ~ " + this.getMonthValue(item.endMonth) + "の締め終了日";
                    break
                // 月次
                case item.workPalceCategory.MONTHLY:
                    extractionText = this.getMonthValue(item.startMonth);
                    break
                // 申請承認
                case item.workPalceCategory.APPLICATION_APPROVAL:
                    extractionText = this.getMonthValue(item.startMonth) + "の" + item.classification + " ~ " + this.getMonthValue(item.endMonth) + "の締め終了日";
                    break
            }
            return extractionText;
        }

        /**
         * This function is responsible for getting month value
         *
         * @param month
         * @return string
         * */
        getMonthValue(month: any): string {
            if (month === 0) {
                return "当月";
            } else {
                return month + "ヶ月前";
            }
        }
    }

    interface IAlarmPatternSet{
        /**
         * アラームリストパターンコード
         */
        alarmPatternCD: string;
        /**
         * アラームリストパターン名称
         */
        alarmPatternName: string;
    }

    class GridItem {
        code: string
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    class AlarmPattern {
        /** コード */
        code: KnockoutObservable<string>;
        /** 名称 */
        name: KnockoutObservable<string>;

        constructor(code: string, name: string) {
            this.code = ko.observable(code);
            this.name = ko.observable(name);
        };
    }

    class AlarmPatternObject{
        // アラームリストパターンコード
        alarmPatternCD: KnockoutObservable<string> = ko.observable(null);
        // アラームリストパターン名称
        alarmPatternName: KnockoutObservable<string> = ko.observable(null);
        // アラームリスト権限設定
        alarmPerSet: KnockoutObservable<WkpAlarmPermissionSettingDto> = ko.observable(null);
        // チェック条件
        checkConList: KnockoutObservableArray<WkpCheckConditionDto> = ko.observableArray(null);
        allCtgCdSelected: KnockoutObservableArray<ItemModel>  = ko.observableArray(null);

        constructor(alarmPatternCD:string,alarmPatternName: string, alarmPerSet: WkpAlarmPermissionSettingDto, checkConList:                        Array<WkpCheckConditionDto> ){
            this.alarmPatternCD(alarmPatternCD);
            this.alarmPatternName(alarmPatternName);
            this.alarmPerSet(alarmPerSet);
            this.checkConList(checkConList);
            this.allCtgCdSelected(_.flatMap(checkConList,(value: any )=>{
                return new ItemModel(value.checkConditionCodes(),'基本給', "カテゴリ");
            }))
        }
    }

    class WkpAlarmPermissionSettingDto {
        authSetting: KnockoutObservable<boolean>= ko.observable(null);
        roleIds: KnockoutObservableArray<string>= ko.observableArray(null);
        roleIdDis: KnockoutObservable<string>= ko.observable(null);
        constructor(data: IWkpAlarmPermissionSettingDto){
            this.authSetting(data.authSetting);
            this.roleIds(data.roleIds);
            this.roleIdDis(_.join(data.roleIds, ','))
        }
    }

    class WkpCheckConditionDto {
        alarmCategory: KnockoutObservable<number>= ko.observable(null);
        checkConditionCodes: KnockoutObservableArray<string>= ko.observableArray(null);
        extractionDaily: KnockoutObservable<WkpExtractionPeriodDailyDto>= ko.observable(null);
        listExtractionMonthly: KnockoutObservable<WkpExtractionPeriodMonthlyDto>= ko.observable(null);
        singleMonth: KnockoutObservable<WkpSingleMonthDto>= ko.observable(null);

        constructor(alarmCategory: number, checkConditionCodes: Array<string>,
                    extractionDaily: WkpExtractionPeriodDailyDto,listExtractionMonthly: WkpExtractionPeriodMonthlyDto,
                    singleMonth: WkpSingleMonthDto){
            this.alarmCategory(alarmCategory);
            this.checkConditionCodes(checkConditionCodes);
            this.extractionDaily(extractionDaily);
            this.listExtractionMonthly(listExtractionMonthly);
            this.singleMonth(singleMonth);

        }
    }
    class WkpExtractionPeriodDailyDto {

        //Start date
        strSpecify: KnockoutObservable<number>= ko.observable(null);
        strPreviousMonth?: KnockoutObservable<number>= ko.observable(null);
        strMonth?: KnockoutObservable<number>= ko.observable(null);
        strCurrentMonth?: KnockoutObservable<boolean>= ko.observable(null);
        strPreviousDay?: KnockoutObservable<number>= ko.observable(null);
        strMakeToDay?: KnockoutObservable<boolean>= ko.observable(null);
        strDay?: KnockoutObservable<number>= ko.observable(null);

        //End date
        endSpecify: KnockoutObservable<number>= ko.observable(null);
        endPreviousDay?: KnockoutObservable<number>= ko.observable(null);
        endMakeToDay?: KnockoutObservable<boolean>= ko.observable(null);
        endDay?: KnockoutObservable<number>= ko.observable(null);
        endPreviousMonth?:KnockoutObservable<number>= ko.observable(null);
        endCurrentMonth?: KnockoutObservable<boolean>= ko.observable(null);
        endMonth?:KnockoutObservable<number>= ko.observable(null);

        constructor(data: IWkpExtractionPeriodDailyDto){
            this.strSpecify(data.strSpecify);
            this.strMonth(data.strMonth);
            this.strCurrentMonth(data.strCurrentMonth);
            this.strPreviousDay(data.strPreviousDay);
            this.strMakeToDay(data.strMakeToDay);
            this.strDay(data.strDay);

            this.endSpecify(data.endSpecify);
            this.endPreviousDay(data.endPreviousDay);
            this.endMonth(data.endMonth);
            this.endCurrentMonth(data.endCurrentMonth);
            this.endPreviousMonth(data.endPreviousMonth);
            this.endDay(data.endDay);
            this.endMakeToDay(data.endMakeToDay);
        }

    }

     class WkpExtractionPeriodMonthlyDto {

        //Start month
        strSpecify: KnockoutObservable<number>= ko.observable(null);
        strMonth?:KnockoutObservable<number>= ko.observable(null);
        strCurrentMonth?: KnockoutObservable<boolean>= ko.observable(null);
        strPreviousAtr?: KnockoutObservable<number>= ko.observable(null);
        yearType?:KnockoutObservable<number>= ko.observable(null);
        designatedMonth?: KnockoutObservable<number>= ko.observable(null);

        //End Month
        endSpecify: KnockoutObservable<number>= ko.observable(null);
        extractFromStartMonth: KnockoutObservable<number>= ko.observable(null);
        endMonth?: KnockoutObservable<number>= ko.observable(null);
        endCurrentMonth?: KnockoutObservable<boolean>= ko.observable(null);
        endPreviousAtr?: KnockoutObservable<number> = ko.observable(null);

        constructor(data: IWkpExtractionPeriodMonthlyDto){
                this.strSpecify(data.strSpecify);
                this.strMonth(data.strMonth);
                this.strCurrentMonth(data.strCurrentMonth);
                this.strPreviousAtr(data.strPreviousAtr);
                this.yearType(data.yearType);
                this.designatedMonth(data.designatedMonth);

                this.endSpecify(data.endSpecify);
                this.extractFromStartMonth(data.extractFromStartMonth);
                this.endMonth(data.endMonth);
                this.endCurrentMonth(data.endCurrentMonth);
                this.endPreviousAtr(data.endPreviousAtr);
        }
    }
    class WkpSingleMonthDto {

        /** 前・先区分  */
        monthPrevious: KnockoutObservable<number>;
        /** 月数 */
        monthNo: KnockoutObservable<number>;
        /** 当月とする */
        curentMonth: KnockoutObservable<boolean>;

        constructor(data: IWkpSingleMonthDto){
            this.monthPrevious(data.monthPrevious);
            this.monthNo(data.monthNo);
            this.curentMonth(data.curentMonth);
        }
    }

    // Interface
    interface IAlarmPatternObject{
        /**
         * アラームリストパターンコード
         */
        alarmPatternCD: string;
        /**
         * アラームリストパターン名称
         */
        alarmPatternName: string;
        /**
         * アラームリスト権限設定
         */
        alarmPerSet: IWkpAlarmPermissionSettingDto;
        /**
         * チェック条件
         */
        checkConList: Array<IWkpCheckConditionDto> ;
    }

    interface IWkpAlarmPermissionSettingDto {
        authSetting: boolean;
        roleIds: Array<string>;
    }

    interface IWkpCheckConditionDto {
        alarmCategory: number;
        checkConditionCodes: Array<string>;
        extractionDaily: IWkpExtractionPeriodDailyDto;
        listExtractionMonthly: IWkpExtractionPeriodMonthlyDto;
        singleMonth: IWkpSingleMonthDto;
    }
    interface IWkpExtractionPeriodDailyDto {

        //Start date
        strSpecify: number;
        strPreviousMonth?: number;
        strMonth?: number;
        strCurrentMonth?: boolean;
        strPreviousDay?: number;
        strMakeToDay?: boolean;
        strDay?: number;

        //End date
        endSpecify: number;
        endPreviousDay?: number;
        endMakeToDay?: boolean;
        endDay?:number;
        endPreviousMonth?:number;
        endCurrentMonth?: boolean;
        endMonth?:number;
    }
    interface IWkpExtractionPeriodMonthlyDto {

        //Start month
        strSpecify: number;
        strMonth?:number;
        strCurrentMonth?: boolean;
        strPreviousAtr?: number;
        yearType?:number;
        designatedMonth?: number;

        //End Month
        endSpecify: number;
        extractFromStartMonth: number;
        endMonth?: number;
        endCurrentMonth?: boolean;
        endPreviousAtr?: number ;
    }
    interface IWkpSingleMonthDto {

        /** 前・先区分  */
        monthPrevious: number;
        /** 月数 */
        monthNo: number;
        /** 当月とする */
        curentMonth: boolean;
    }
}