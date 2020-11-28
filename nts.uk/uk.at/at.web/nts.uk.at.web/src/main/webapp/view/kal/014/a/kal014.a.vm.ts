module nts.uk.at.kal014.a {

    import common=nts.uk.at.kal014.common;
    import container = nts.uk.ui.windows.container;
    const PATH_API = {
        GET_ALL_SETTING: 'at/record/alarmwrkp/screen/getAll',
        GET_ALL_CTG: 'at/record/alarmwrkp/screen/getAllCtg',
        GET_SETTING_BYCODE: 'alarmworkplace/patternsetting/getPatternSetting',
        REGISTER: 'alarmworkplace/patternsetting/register',
        DELETE: 'alarmworkplace/patternsetting/delete',

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
        itemsSwap: KnockoutObservableArray<AlarmCheckCategoryList>;
        listAllCtgCode: Array<AlarmCheckCategoryList> = [];
        tableItems: KnockoutObservableArray<TableItem>;
        columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        currentCodeListSwap: KnockoutObservableArray<any>;
        test: KnockoutObservableArray<any>;
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
        workPalceCategory: any;
        alarmPatterSet : KnockoutObservable<AlarmPatternObject>
            = ko.observable(new AlarmPatternObject(null,null,new WkpAlarmPermissionSettingDto(null),null,[]));

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

            vm.columns = ko.observableArray([
                {headerText: vm.$i18n('KAL014_22'), key: 'key', hidden: true},
                {headerText: vm.$i18n('KAL014_22'), key: 'categoryName', width: 70},
                {headerText: vm.$i18n('KAL014_23'), key: 'code', width: 70},
                {headerText: vm.$i18n('KAL014_24'), key: 'name', width: 180}
            ]);
            vm.currentCodeListSwap = ko.observableArray([]);
            vm.test = ko.observableArray([]);
            vm.roundingRules = ko.observableArray([
                {code: '1', name: vm.$i18n('KAL014_30')},
                {code: '0', name: vm.$i18n('KAL014_31')}
            ]);
            vm.selectedRuleCode = ko.observable(null);
            vm.isNewMode = ko.observable(true);
            vm.selectedExecutePermission = ko.observable("");
            $("#fixed-table").ntsFixedTable({height: 320, width: 830});
            vm.getAllSettingData(null).done(()=>{
                vm.itemsSwap( _.cloneDeep(vm.listAllCtgCode));
            })
        }

        created() {
            const vm = this;
            _.extend(window, {vm});
            vm.currentCode.subscribe((code:any)=>{
                if (_.isNil(code)){
                    return;
                }
                vm.getSelectedData(code).done(()=>{
                    // reset swap list
                    vm.itemsSwap.removeAll();
                    vm.itemsSwap(_.cloneDeep(vm.listAllCtgCode));
                    $("#A3_3").focus();
                });
            });

            vm.alarmPatterSet().allCtgCdSelected.subscribe(()=>{
                let listCtg: Array<ItemModel> =_.uniq( _.map(vm.alarmPatterSet().allCtgCdSelected(),i=> new ItemModel(i.category,i.categoryName))),
                    listCtgOld: Array<number> =_.uniq( _.map(vm.alarmPatterSet().checkConList(),i=>i.alarmCategory())),
                    newCtg = _.filter(listCtg, item => listCtgOld.indexOf(item.code) === -1),
                    newCtgList: Array<WkpCheckConditionDto> = [];

                newCtg.forEach((ctg: ItemModel)=>{
                    let listSelectedCode = _.map(_.filter(vm.alarmPatterSet().allCtgCdSelected(), i =>i.category === ctg.code   ),ctgCd => ctgCd.code);
                    var defautData : IWkpCheckConditionDto = { alarmCategory: ctg.code, alarmCtgName: ctg.name, checkConditionCodes: listSelectedCode, extractionDaily: null,listExtractionMonthly: null,singleMonth: null};
                    newCtgList.push(vm.createDataforCategory(defautData));
                });
                //  add category
                vm.alarmPatterSet().addConditionCtg(newCtgList);
            })
        }

        remove() {
            this.itemsSwap.shift();
        }

        getAllSettingData(selectedCd?: string): JQueryPromise<any>{
            const vm = this;
            let dfd = $.Deferred();
            $.when(vm.$ajax(PATH_API.GET_ALL_SETTING),vm.$ajax(PATH_API.GET_ALL_CTG))
                .done((data: Array<IAlarmPatternSet>, ctg: Array<IAlarmCheckCategoryList>) =>{
                    if (!data || data.length <= 0){
                        // Set to New Mode
                        vm.clickNewButton();
                        dfd.resolve();
                        return;
                    }
                    // Set to update mode
                    vm.isNewMode(false);
                    vm.gridItems( _.map(data, x=> new GridItem(x.alarmPatternCD, x.alarmPatternName)));
                    // In case of update mode select on updated item
                    if (selectedCd){
                        vm.currentCode(selectedCd);
                    } else {
                        // In case of init screen select on first item
                        vm.currentCode(vm.gridItems()[0].code);
                    }

                    // Set all value of category
                    let allCtgCd = _.map(ctg, i=> new AlarmCheckCategoryList(i) );
                    vm.listAllCtgCode = allCtgCd;
                    dfd.resolve();
            }).fail( (err: any) =>{
                vm.$dialog.error(err);
                dfd.reject();
            });


            return dfd.promise();

        }

        getSelectedData(alarmCd: string): JQueryPromise<any>{
            const vm = this;
            let dfd = $.Deferred();
            // Get Data of select item
            if (_.isNil(alarmCd)){
                return;
            }
            vm.$errors("clear");
            vm.$blockui("show");
            vm.$ajax(PATH_API.GET_SETTING_BYCODE,{patternCode: alarmCd})
                .done((data: IAlarmPatternObject)=>{
                    console.log(data);
                    let perSet: WkpAlarmPermissionSettingDto ;
                    perSet = new WkpAlarmPermissionSettingDto(data.alarmPerSet);
                    let checkCon: Array<WkpCheckConditionDto> = [];

                    _.forEach(data.checkConList, (value)=>{
                        checkCon.push(vm.createDataforCategory(value));
                    });
                    let checkConList: Array<AlarmCheckCategoryList> = _.flatMap(checkCon,(value: any )=>{
                        let item: AlarmCheckCategoryList  = _.find(vm.itemsSwap(),i=>i.code == value.checkConditionCodes() && i.category == value.alarmCategory());
                        return item;
                    });
                    vm.alarmPatterSet().update(data.alarmPatternCD,data.alarmPatternName,perSet,checkCon,checkConList);
                })
                .fail(()=>{

                }).always(()=>{
                    vm.$blockui("hide");
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
            return new WkpCheckConditionDto(data.alarmCategory,data.alarmCtgName,data.checkConditionCodes,
                extracDai, listExtracMon,extracSingMon,
                vm.getExtractionPeriod(data.alarmCategory,data.checkConditionCodes,
                    extracDai,listExtracMon,extracSingMon));
        }
        /**
         * This function is responsible for getting extraction Period
         *
         * @param TableItem
         * @return string
         * */
        getExtractionPeriod(alarmCategory: number, checkConditionCodes: Array<string>,
                            extractionDaily: WkpExtractionPeriodDailyDto,listExtractionMonthly: WkpExtractionPeriodMonthlyDto,
                            singleMonth: WkpSingleMonthDto): string {
            //   カテゴリ　＝＝　月次の場合
            // 　・抽出期間　＝　単月
            //             カテゴリ　＝＝　マスタチェック（基本）OR　マスタチェック（職場）の場合
            // 　・抽出期間　＝　抽出期間（月単位）
            // カテゴリ　＝＝　マスタチェック（日次）OR　スケジュール/日次　OR　申請承認　の場合
            // 　・抽出期間　＝　抽出期間（日単位）
            const vm = this;
            let extractionText = "";
            switch (alarmCategory) {
                // マスタチェック(基本)
                // マスタチェック(職場)
                case vm.workPalceCategory.MASTER_CHECK_BASIC:
                case vm.workPalceCategory.MASTER_CHECK_WORKPLACE:
                    extractionText = vm.buildExtracMon(listExtractionMonthly);
                    break;
                //マスタチェック(日次)
                case vm.workPalceCategory.MASTER_CHECK_DAILY:
                // スケジュール／日次
                case vm.workPalceCategory.SCHEDULE_DAILY:
                case vm.workPalceCategory.APPLICATION_APPROVAL:
                    extractionText = vm.buildExtracDay(extractionDaily);
                    break
                // 月次
                case vm.workPalceCategory.MONTHLY:
                    extractionText = vm.getMonthValue(singleMonth);
                    break
                // 申請承認
            }
            return extractionText;
        }


        buildExtracMon(data: WkpExtractionPeriodMonthlyDto): string {
            let start = "";
            if (data.strCurrentMonth()) {
                start = "当月";
            } else {
                start = data.strMonth() + "ヶ月"
                    + (data.strPreviousAtr() == 0? "前" : "先");
            }
            let end = "";
            if (data.endCurrentMonth()) {
                end = "当月";
            } else {
                end = data.endMonth() + "ヶ月"
                    + (data.endPreviousAtr() == 0? "前" : "先");
            }

            return start + " ～ " + end;
        }
        /**
         * This function is responsible for getting month value
         *
         * @param month
         * @return string
         * */
        getMonthValue(month: WkpSingleMonthDto): string {
            let result = "";
            if (month.curentMonth()) {
                result = "当月";
            } else {
                result = month.monthNo() + "ヶ月"
                    + (month.monthPrevious() == 0? "前" : "先");
            }
            return result;
        }

        buildExtracDay(data: WkpExtractionPeriodDailyDto): string {
            let start = "";
            if (data.strSpecify() == StartSpecify.MONTH) {
                if (data.strCurrentMonth()) {
                    start = "当月の締め開始日";
                } else {
                    start = data.strMonth() + "ヶ月"
                        + (data.strPreviousMonth() == 0? "前" : "先") + "の締め開始日";
                }
            } else{
                if(data.strMakeToDay()){
                    start = "当日から当日 ";
                } else {
                    start = "当日から"
                        + data.strDay() + (data.strPreviousDay() == 0? "前" : "先");
                }
            }

            let end = "";
            if (data.endSpecify() == EndSpecify.MONTH) {
                if (data.endCurrentMonth()) {
                    end = "当月の締め終了日";
                } else {
                    end = data.endMonth() + "ヶ月"
                        + (data.endPreviousMonth() == 0? "前" : "先") + "の締め終了日";
                }
            } else{
                if(data.endMakeToDay()){
                    end = "当日から当日 ";
                } else {
                    end = " 当日から"
                        + + (data.endDay() == 0? "前" : "先");
                }
            }

            return start + " ～ " + end;
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
                vm.initScreen().done(()=>{
                    vm.cleanInput();
                    $("#A3_2").focus();
                });

            });
        }

        /**
         * This function is responsible clean input data
         * @return type void
         *
         * */
        initScreen():JQueryPromise<any>{
            const vm = this;
            let dfd = $.Deferred();
            vm.alarmPatterSet().update(null,null,new WkpAlarmPermissionSettingDto(null),[],[]);
            dfd.resolve();

            return dfd.promise();
        }
        cleanInput() {
            const vm = this;
            vm.itemsSwap.removeAll();
            vm.itemsSwap(_.cloneDeep(vm.listAllCtgCode));

        }

        /**
         * This function is responsible to register data
         * @return type void
         *
         * */
        clickRegister() {
            const vm = this;
            vm.isNewMode(false);
            vm.$validate(".nts-input", ".ntsControl").then((valid: boolean) => {
                vm.$blockui("show")
                let param = ko.toJS(vm.alarmPatterSet());
                vm.$ajax(PATH_API.REGISTER,param).done(()=>{
                    vm.$dialog.info("Msg_15").done(()=>{
                        $("#A3_3").focus();
                    })
                }).fail((res: any) => {
                    vm.$dialog.error(res);
                }).always(() => vm.$blockui("hide"));
            });
        }

        /**
         * This function is responsible to delete data
         * @return type void
         *
         * */
        clickDeleteButton() {
            const vm = this;
            vm.$blockui("grayout");
            vm.$dialog.confirm({ messageId: "Msg_18" }).then((result: 'no' | 'yes' | 'cancel') => {
                if (result === 'yes') {
                    vm.$ajax(PATH_API.DELETE,{code: vm.currentCode()});
                }
            }).done(() => {
                vm.$dialog.info({ messageId: "Msg_16" }).then(() => {
                    vm.getAllSettingData(null);
                });
            }).fail((res: any) => {
                vm.$dialog.error(res);
            }).always(() => vm.$blockui("hide"));

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
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
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
            //this.extractionPeriod = (this.getExtractionPeriod(this));
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
        allCtgCdSelected: KnockoutObservableArray<AlarmCheckCategoryList>  = ko.observableArray(null);

        constructor(alarmPatternCD:string,alarmPatternName: string, alarmPerSet: WkpAlarmPermissionSettingDto, checkConList: Array<WkpCheckConditionDto>,
        allCtgCdSelected: Array<AlarmCheckCategoryList>){
            this.alarmPatternCD(alarmPatternCD);
            this.alarmPatternName(alarmPatternName);
            this.alarmPerSet(alarmPerSet);
            this.checkConList(checkConList || []);
            this.allCtgCdSelected(allCtgCdSelected || []);

        }

        update(alarmPatternCD:string,alarmPatternName: string, alarmPerSet: WkpAlarmPermissionSettingDto, checkConList: Array<WkpCheckConditionDto>,
                    allCtgCdSelected: Array<AlarmCheckCategoryList>){
            this.alarmPatternCD(alarmPatternCD);
            this.alarmPatternName(alarmPatternName);
            this.alarmPerSet(alarmPerSet);
            this.checkConList(checkConList || []);
            this.allCtgCdSelected(allCtgCdSelected || []);

        }


        // Add condition
        addConditionCtg(newCtg: Array<WkpCheckConditionDto>){
            let fullCtg: Array<WkpCheckConditionDto> = ko.unwrap(this.checkConList);
            newCtg.forEach((item: WkpCheckConditionDto)=>{
                fullCtg.push(item);
            })

            this.checkConList(fullCtg);
        }
    }

    class WkpAlarmPermissionSettingDto {
        authSetting: KnockoutObservable<boolean>= ko.observable(null);
        roleIds: KnockoutObservableArray<string>= ko.observableArray(null);
        roleIdDis: KnockoutObservable<string>= ko.observable(null);
        constructor(data: IWkpAlarmPermissionSettingDto){
            this.authSetting(_.isNil(data) ? false : data.authSetting);
            this.roleIds(_.isNil(data) ? [] : data.roleIds);
            this.roleIdDis(_.isNil(data) ? "" :_.join(data.roleIds, ','))
        }
    }

    class WkpCheckConditionDto {
        alarmCategory: KnockoutObservable<number>= ko.observable(null);
        alarmCtgName: KnockoutObservable<string>= ko.observable(null);
        checkConditionCodes: KnockoutObservableArray<string>= ko.observableArray(null);
        displayText: KnockoutObservable<string> = ko.observable(null);
        extractionDaily: KnockoutObservable<WkpExtractionPeriodDailyDto>= ko.observable(null);
        listExtractionMonthly: KnockoutObservable<WkpExtractionPeriodMonthlyDto>= ko.observable(null);
        singleMonth: KnockoutObservable<WkpSingleMonthDto>= ko.observable(null);

        constructor(alarmCategory: number, alarmCtgName: string, checkConditionCodes: Array<string>,
                    extractionDaily: WkpExtractionPeriodDailyDto,listExtractionMonthly: WkpExtractionPeriodMonthlyDto,
                    singleMonth: WkpSingleMonthDto, displayText: string) {
            this.alarmCategory(alarmCategory);
            this.alarmCtgName(alarmCtgName);
            this.checkConditionCodes(checkConditionCodes);
            this.extractionDaily(extractionDaily);
            this.listExtractionMonthly(listExtractionMonthly);
            this.singleMonth(singleMonth);
            this.displayText(displayText)
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
            this.strSpecify(_.isNil(data)? StartSpecify.MONTH :data.strSpecify);
            this.strMonth(_.isNil(data)? null : data.strMonth);
            this.strCurrentMonth(_.isNil(data)? true :data.strCurrentMonth);
            this.strPreviousDay(_.isNil(data)? null :data.strPreviousDay);
            this.strMakeToDay(_.isNil(data)? null :data.strMakeToDay);
            this.strDay(_.isNil(data)? null :data.strDay);

            this.endSpecify(_.isNil(data)? EndSpecify.MONTH  :data.endSpecify);
            this.endPreviousDay(_.isNil(data)? null :data.endPreviousDay);
            this.endMonth(_.isNil(data)? null :data.endMonth);
            this.endCurrentMonth(_.isNil(data)? true :data.endCurrentMonth);
            this.endPreviousMonth(_.isNil(data)? null :data.endPreviousMonth);
            this.endDay(_.isNil(data)? null :data.endDay);
            this.endMakeToDay(_.isNil(data)? null :data.endMakeToDay);
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
                this.strSpecify(_.isNil(data)? null : data.strSpecify);
                this.strMonth(_.isNil(data)? null :data.strMonth);
                this.strCurrentMonth(_.isNil(data)? true :data.strCurrentMonth);
                this.strPreviousAtr(_.isNil(data)? null :data.strPreviousAtr);
                this.yearType(_.isNil(data)? null :data.yearType);
                this.designatedMonth(_.isNil(data)? null :data.designatedMonth);

                this.endSpecify(_.isNil(data)? null :data.endSpecify);
                this.extractFromStartMonth(_.isNil(data)? null :data.extractFromStartMonth);
                this.endMonth(_.isNil(data)? null :data.endMonth);
                this.endCurrentMonth(_.isNil(data)? true :data.endCurrentMonth);
                this.endPreviousAtr(_.isNil(data)? null :data.endPreviousAtr);
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
            this.monthPrevious(_.isNil(data) ? null : data.monthPrevious);
            this.monthNo(_.isNil(data) ? null : data.monthNo);
            this.curentMonth(_.isNil(data) ? true : data.curentMonth);
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
        alarmCtgName: string;
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


    interface IAlarmCheckCategoryList {
        // カテゴリ
        category: number;
        categoryName: string;
        // アラームチェック条件コード
        code: string;
        // 名称
        name: string;
    }

    class AlarmCheckCategoryList {
        key :string;
        // カテゴリ
        category: number;

        categoryName: string;
        // アラームチェック条件コード
        code: string;
        // 名称
        name: string;

        constructor (data: IAlarmCheckCategoryList) {
            this.key = data.category + "-"+data.code;
            this.category = data.category;
            this.categoryName = data.categoryName;
            this.code = data.code;
            this.name = data.name;
        }
    }

    enum StartSpecify{
        // 実行日からの日数を指定する
        DAYS = 0,
        // 締め日を指定する
        MONTH = 1
    }

    enum EndSpecify{
        // 実行日からの日数を指定する
        DAYS = 0,
        // 締め日を指定する
        MONTH = 1
    }
}
