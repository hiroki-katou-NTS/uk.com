/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kal013.b {
    import errors = nts.uk.ui.errors;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import NumberEditorOption = nts.uk.ui.option.NumberEditorOption;

    const PATH_API = {
       GET_ENUM_OPERATOR: "at/record/alarmwrkp/screen/getEnumCompareType",
        GET_ATTENDANCEITEM: "at/record/businesstype/attendanceItem/getListMonthlyByAttendanceAtr/",
        GET_ATTENDANCEITEMNAME_BYCODE: "at/record/divergencetime/setting/getMonthlyAttendanceDivergenceName",
        GET_BONUS_PAY_SET: "at/share/bonusPaySetting/getAllBonusPaySetting"

    };

    @bean()
    export class KAL013BViewModel extends ko.ViewModel {
        listTypeCheck    : KnockoutObservableArray<ItemEnumModel> = ko.observableArray([]);
        pattern : KnockoutObservable<Pattern> = ko.observable(new Pattern());
        listSingleValueCompareTypes: KnockoutObservableArray<EnumModel> = ko.observableArray([]);
        contrastTypeList:  KnockoutObservableArray<ItemEnumModel> = ko.observableArray([]);
        category: KnockoutObservable<number> = ko.observable(null);
        enableMaxValue: KnockoutObservable<boolean> = ko.observable(true);
        switchPatternA: KnockoutObservable<boolean> = ko.observable(true);
        timeControl: KnockoutObservable<boolean> = ko.observable(true);
        constraint : KnockoutObservable<string> = ko.observable("");
        lstItemNum: Array<AttdItemDto> = [];
        lstItemTime: Array<AttdItemDto> = [];
        lstItemDay: Array<AttdItemDto> = [];
        numberEditorOption: KnockoutObservable<NumberEditorOption> = ko.observable(new NumberEditorOption({
            grouplength: 0,
            decimallength: 0,
            placeholder: ''
        }));
        listOneDecimalNum: Array<string> = ["AverageNumberDays"];
        monthlyTimeControl: Array<number> = [1,5];
        dailyTimeControl: Array<number> = [2];
        dailyContraint: Map<number,string> = new Map();
        monthlyContraint: Map<number,string> = new Map();

        constructor(params: IParentParams) {
            super();
            const vm = this;
            vm.category(params.category);
            if (params.category == WorkplaceCategory.MONTHLY) {
                vm.listTypeCheck(__viewContext.enums.CheckMonthlyItemsType);
            } else {
                vm.listTypeCheck(__viewContext.enums.CheckDayItemsType);
            }

            vm.dailyContraint.set(0, "Comparison");
            vm.dailyContraint.set(1, "NumberOfPeople");
            vm.dailyContraint.set(2, "Time");
            vm.dailyContraint.set(3, "Amount");
            vm.dailyContraint.set(4, "RatioComparison");

            vm.monthlyContraint.set(1, "AverageTime");
            vm.monthlyContraint.set(2, "AverageNumberDays");
            vm.monthlyContraint.set(3, "AverageNumberTimes");
            vm.monthlyContraint.set(4, "AverageRatio");
            vm.monthlyContraint.set(5, "AverageTime");
            vm.monthlyContraint.set(6, "AverageNumberDays");
            vm.monthlyContraint.set(7, "AverageNumberTimes");
            vm.monthlyContraint.set(8, "AverageRatio");

            vm.pattern().checkItem.subscribe((value)=>{
                // Clear error
                nts.uk.ui.errors.clearAll();
                vm.pattern().clearCheckCod();

                // Kind of control
                vm.checkKindOfConrol(value);

                // Constraint
               vm.createConstraint(value);

                // Change pattern
                vm.createcontrastList(vm.category(), value);
            });

            vm.pattern().checkCondB.subscribe((value)=>{
                if (!value || vm.switchPatternA() || vm.category() != WorkplaceCategory.SCHEDULE_DAILY){
                    return;
                }
                nts.uk.ui.errors.clearAll();
                //Kind of control
                vm.checkKindOfConrol(value,false);
            })

            vm.constraint.subscribe((value)=>{
                const vm = this;
                if (_.indexOf(vm.listOneDecimalNum,value) != -1){
                    vm.numberEditorOption(new NumberEditorOption({
                        grouplength: 0,
                        decimallength: 1,
                        placeholder: ''
                    }));
                } else {
                    vm.numberEditorOption(new NumberEditorOption({
                        grouplength: 0,
                        decimallength: 0,
                        placeholder: ''
                    }));
                }

            });

        }

        created(params: IParentParams) {
            const vm = this;
            if (_.isNil(params.condition.checkItem)){
                params = vm.initParam(params);
            }
            vm.getEnum().done(()=>{
                vm.initScreen(params.condition.checkItem,params.condition.checkCondB,vm.switchPatternA());
                vm.pattern().update(params.condition);
                if (!_.isEmpty(vm.pattern().countableSubAtdItems()) || !_.isEmpty(vm.pattern().countableAddAtdItems())){
                    let codeList: Array<number>  = [];
                    codeList = codeList.concat(vm.pattern().countableSubAtdItems());
                    codeList = codeList.concat(vm.pattern().countableAddAtdItems());
                    vm.$ajax(PATH_API.GET_ATTENDANCEITEMNAME_BYCODE,codeList).done((data: Array<AttendanceNameDivergenceDto>)=>{
                        let nameAdd = _.map(_.filter(data, i => vm.pattern().countableAddAtdItems().indexOf(i.attendanceItemId) != -1),item => item.attendanceItemName);
                        let nameSub = _.map(_.filter(data, i => vm.pattern().countableSubAtdItems().indexOf(i.attendanceItemId) != -1),item => item.attendanceItemName);
                        vm.pattern().updateTextDis(nameAdd,nameSub);
                    }).fail((error)=>{
                        vm.$dialog.error({ messageId: error.messageId });
                    });

                } else if (!_.isNil(vm.pattern().checkCond())){
                    vm.$ajax(PATH_API.GET_BONUS_PAY_SET).done((data: Array<any>)=>{
                        if (data) {
                            let item  = _.find(data, i=>i.code == vm.pattern().checkCond());
                            vm.pattern().updateCheckCondDis(_.isNil(item) ? "" : item.name)
                        }else{
                            vm.pattern().updateCheckCondDis(vm.pattern().checkCond());
                        }

                    });
                }
            });

            vm.pattern().checkCond.subscribe((value)=>{
                if (vm.switchPatternA()){
                    // vm.$errors("clear","#check-condition");
                    $("#check-condition").ntsError("clear");

                }

            });

            vm.pattern().operator.subscribe((value)=>{
                vm.$errors("clear",".endValue");
                if (_.indexOf([6,7,8,9],value) != -1 ){
                    vm.enableMaxValue(true);
                } else {
                    vm.enableMaxValue(false);
                    vm.pattern().clearMaxValue();
                }
            });
            vm.pattern().minValue.subscribe(()=>{
                vm.$errors("clear",".endValue");
            });
        }

        initParam(params: IParentParams): IParentParams{
            const vm = this;

            if (params.category == WorkplaceCategory.MONTHLY) {
                params = {
                    category: params.category, condition: {
                        checkItem: 1,
                        checkCond: null,
                        checkCondB: null,
                        operator: 0,
                        minValue: null,
                        maxValue: null,
                        displayMessage: null
                    }
                };
                vm.switchPatternA(true);
            } else {
                params = {
                    category: params.category, condition: {
                        checkItem: 0,
                        checkCond: null,
                        checkCondB: 1,
                        operator: 0,
                        minValue: null,
                        maxValue: null,
                        displayMessage: null
                    }
                };
                vm.contrastTypeList(__viewContext.enums.ContrastType);
                vm.switchPatternA(false);
            }
            return params;
        }

        initScreen(value: number, valueCheck?: number, isPatternA: boolean = false){
            const vm = this;
            vm.checkKindOfConrol(value);
            // Constraint
            vm.createConstraint(value);
            // Change pattern
            vm.createcontrastList(vm.category(), value);

            if (!isPatternA){
                vm.checkKindOfConrol(valueCheck,false);
                // Constraint
                vm.createConstraint(valueCheck,false);
            }
        }

        createcontrastList(category: number, value: number):void {
            const vm = this;
            vm.switchPatternA(true)
            // 対比の場合 - スケジュール／日次
            if (category == WorkplaceCategory.SCHEDULE_DAILY && value == 0) {
                vm.switchPatternA(false);
                vm.contrastTypeList(__viewContext.enums.ContrastType);
            } else if (category == WorkplaceCategory.MONTHLY
                && _.indexOf([CheckMonthlyItemsType.AVERAGE_RATIO],value) != -1 ){
                vm.switchPatternA(false);
                switch (value){
                    case CheckMonthlyItemsType.AVERAGE_RATIO:
                        vm.contrastTypeList(__viewContext.enums.AverageRatio);
                        break;
                    default:
                        vm.contrastTypeList([]);
                        break;
                }
            }
        }

        checkKindOfConrol(value: number, isPatternA: boolean = true){
            const vm = this;
            // Kind of control
            if (isPatternA){
                // 月次 - 平均時間 ||  スケジュール／日次 - 時間対比
                if ((vm.category() == WorkplaceCategory.MONTHLY && _.indexOf(vm.monthlyTimeControl,value) != -1)
                    || (vm.category() == WorkplaceCategory.SCHEDULE_DAILY
                        && (_.indexOf(vm.dailyTimeControl,value) != -1))){
                            // || _.indexOf(vm.dailyTimeControlB, vm.pattern().checkCondB()) != -1))
                    vm.timeControl(true);
                } else{
                    vm.timeControl(false);
                }
            } else if (vm.category() == WorkplaceCategory.SCHEDULE_DAILY ) {
                vm.timeControl(false);
            }
        }

        createConstraint(value: number, isPatternA: boolean = true){
            const vm = this;
            // Contraint
            if (vm.category() == WorkplaceCategory.SCHEDULE_DAILY){
                vm.constraint(_.isNil(vm.dailyContraint.get(value))? vm.dailyContraint.get(0) : vm.dailyContraint.get(value));
            } else{
                vm.constraint(vm.monthlyContraint.get(value));
            }
        }

        mounted() {
            const vm = this;
            $('#cbxTypeCheckWorkRecordcategory5').focus();
        }
        
        //GET ALL MONTHLY
        getListItemByAtrDailyAndMonthly(typeCheck: number, mode: number) {
            const vm = this;
            let dfd = $.Deferred<any>();
            if (typeCheck == CheckMonthlyItemsType.AVERAGE_NUMBER_TIME) {
                //With type 回数 - Times , Number  = 2
                vm.$ajax(PATH_API.GET_ATTENDANCEITEM + MONTHLYATTENDANCEITEMATR.NUMBER).done((lstAtdItem) => {
                    vm.lstItemNum = lstAtdItem;
                    dfd.resolve(lstAtdItem);
                }).fail((error)=>{
                    dfd.reject(error);
                });
            } else if (typeCheck == CheckMonthlyItemsType.AVERAGE_TIME) {
                //With type 時間 - Time
                vm.$ajax(PATH_API.GET_ATTENDANCEITEM + MONTHLYATTENDANCEITEMATR.TIME).done((lstAtdItem) => {
                    vm.lstItemTime = lstAtdItem;
                    dfd.resolve(lstAtdItem);
                }).fail((error)=>{
                    dfd.reject(error);
                });
            } else if (typeCheck == CheckMonthlyItemsType.AVERAGE_NUMBER_DAY) {
                // 日数
                vm.$ajax(PATH_API.GET_ATTENDANCEITEM + MONTHLYATTENDANCEITEMATR.DAYS).done((lstAtdItem) => {
                    vm.lstItemDay = lstAtdItem;
                    dfd.resolve(lstAtdItem);
                }).fail((error)=>{
                    dfd.reject(error);
                });
            } else {
                dfd.resolve([]);
            }
            return dfd.promise();
        }

        //openSelectAtdItemDialogTarget() {
        btnSettingB2_2_click(params: any) {
            const vm = this;
            if (vm.category() == WorkplaceCategory.SCHEDULE_DAILY){
                let param: any = {
                    isMulti: false,
                    selecteds: [
                        params.checkCond()
                    ]
                }
                setShared('KDL007_PARAM', param, true);
                nts.uk.ui.windows.sub.modal('/view/kdl/007/a/index.xhtml').onClosed(() => {
                    let listResult = getShared('KDL007_VALUES');
                    vm.pattern().updateCheckCond("");
                    if (listResult.selecteds[0].length > 0 ) {
                        vm.$ajax(PATH_API.GET_BONUS_PAY_SET).done((data: Array<any>) => {
                            if (data) {
                                let item = _.find(data, i => i.code == vm.pattern().checkCond());
                                vm.pattern().updateCheckCondDis(_.isNil(item) ? "" : item.name)
                            }
                        });
                        vm.pattern().updateCheckCond(listResult.selecteds[0]);
                    }


                });
            } else {
                //Open dialog KDW007C
                vm.getListItemByAtrDailyAndMonthly(vm.pattern().checkItem(), 1).done((lstItem: Array<AttdItemDto>) => {
                    let lstItemCode = lstItem.map((item: any) => {
                        return item.attendanceItemId;
                    });
                    let params = {
                        attr: 1,
                        lstAllItems: lstItemCode,
                        lstAddItems: vm.pattern().countableAddAtdItems(),
                        lstSubItems: vm.pattern().countableSubAtdItems()
                    };
                    nts.uk.ui.windows.setShared("KDW007Params", params);
                    nts.uk.ui.windows.sub.modal("at", "/view/kdw/007/c/index.xhtml").onClosed(() => {
                        $(".nts-input").ntsError("clear");
                        let output = nts.uk.ui.windows.getShared("KDW007CResults");
                        if (output) {
                            vm.pattern().updateCheckCondKdw007(output.lstAddItems, output.lstSubItems);
                            vm.$ajax(PATH_API.GET_ATTENDANCEITEMNAME_BYCODE,output.lstAddItems.concat(output.lstSubItems)).done((data: Array<AttendanceNameDivergenceDto>)=>{
                                let nameAdd = _.map(_.filter(data, i => vm.pattern().countableAddAtdItems().indexOf(i.attendanceItemId) != -1),item => item.attendanceItemName);
                                let nameSub = _.map(_.filter(data, i => vm.pattern().countableSubAtdItems().indexOf(i.attendanceItemId) != -1),item => item.attendanceItemName);
                                vm.pattern().updateTextDis(nameAdd,nameSub);
                            }).fail((error)=>{
                                vm.$dialog.error({ messageId: error.messageId });
                            });
                        }
                    });
                }).fail((error)=>{
                    vm.$dialog.error({ messageId: error.messageId });
                });

            }
        }

        getEnum(): JQueryPromise<any>{
            const vm = this;
            let dfd = $.Deferred();
            vm.$ajax(PATH_API.GET_ENUM_OPERATOR).done((
                listSingleValueCompareTypse: Array<IEnumModel>) => {
                vm.listSingleValueCompareTypes(vm.getLocalizedNameForEnum(listSingleValueCompareTypse));
                dfd.resolve();

            }).fail(()=>{
                dfd.reject();
            }).always(() => {
            });
            return dfd.promise();
        }

        private getLocalizedNameForEnum(listEnum: Array<IEnumModel>): Array<EnumModel> {
            const vm = this;
            let result = _.map(listEnum,(item) =>{
                let enumValue: IEnumModel = {value: item.value, fieldName: item.fieldName,
                    localizedName: vm.$i18n(item.localizedName)};
                return new EnumModel(enumValue);
            });
            return result;
        }

        validate(): boolean{
            const vm = this;
            let result: boolean = true;
            if (vm.switchPatternA() && ((vm.category() == WorkplaceCategory.SCHEDULE_DAILY &&  _.isEmpty(vm.pattern().checkCond()))
                                    || (vm.category() == WorkplaceCategory.MONTHLY &&  _.isEmpty(vm.pattern().countableSubAtdItems())
                                            &&  _.isEmpty(vm.pattern().countableAddAtdItems())))){
                vm.$errors("#check-condition",{ messageId: 'MsgB_1', messageParams: [vm.$i18n("KAL003_59")] });
                result = false;
            }

            if  (!vm.validateStartEnd())
            {
                vm.$errors(".endValue", "Msg_927");
                result = false;
            }
            return result;
        }

        validateStartEnd(): boolean{
            const vm = this;
            if (_.isNil(vm.pattern().maxValue()) ){
                return true;
            }

            switch (vm.pattern().operator()) {
                case RangeCompareType.BETWEEN_RANGE_OPEN:
                case RangeCompareType.OUTSIDE_RANGE_OPEN:
                    if (parseFloat(vm.pattern().minValue().toString()) >= parseFloat(vm.pattern().maxValue().toString()))
                        return false;
                    break;
                case RangeCompareType.BETWEEN_RANGE_CLOSED:
                case RangeCompareType.OUTSIDE_RANGE_CLOSED:
                    if (parseFloat(vm.pattern().minValue().toString()) > parseFloat(vm.pattern().maxValue().toString()))
                        return false;
                    break;
                default:
                    break;
            }

            return true;
        }


        btnDecision() {
            const vm = this;
            $('.nts-input').filter(":enabled").trigger("validate");
            if (errors.hasError() === true || !vm.validate()) {
                return;
            }
            let shareParam: IParentParams = {
                category: vm.category(),
                condition: {
                    checkItem: vm.pattern().checkItem(),
                    checkCond: vm.pattern().checkCond(),
                    checkCondB: vm.pattern().checkCondB(),
                    countableAddAtdItems: vm.pattern().countableAddAtdItems(),
                    countableSubAtdItems: vm.pattern().countableSubAtdItems(),
                    operator: vm.pattern().operator(),
                    minValue: vm.pattern().minValue(),
                    maxValue: vm.pattern().maxValue(),
                    displayMessage: vm.pattern().displayMessage()
                }
            };
            console.log(shareParam);
            vm.$window.close({
                shareParam
            });
        }

        closeDialog() {
            const vm = this;
            vm.$window.close({
            });
        }
    }

    class Pattern{
        checkItem: KnockoutObservable<number> = ko.observable(0);
        checkCond: KnockoutObservable<string> = ko.observable("");
        countableAddAtdItems: KnockoutObservableArray<number> = ko.observableArray([]);
        countableSubAtdItems: KnockoutObservableArray<number> = ko.observableArray([]);
        checkCondDis: KnockoutObservable<string> = ko.observable("");
        checkCondB: KnockoutObservable<number> = ko.observable(0);
        operator: KnockoutObservable<number> = ko.observable(0);
        minValue: KnockoutObservable<number> = ko.observable(null);
        maxValue: KnockoutObservable<number> = ko.observable(null);
        displayMessage: KnockoutObservable<string> = ko.observable("");
        constructor(){}

        update(params: IPattern){
            this.checkItem(_.isNil(params.checkItem) ? 0: params.checkItem);
            this.checkCond(params.checkCond);
            this.countableAddAtdItems(_.isEmpty(params.countableAddAtdItems)? [] : params.countableAddAtdItems);
            this.countableSubAtdItems(_.isEmpty(params.countableSubAtdItems)? [] : params.countableSubAtdItems);
            this.checkCondDis(params.checkCond);
            this.checkCondB(_.isNil(params.checkCondB) ? 1: params.checkCondB);
            this.operator(params.operator);
            this.minValue(params.minValue);
            this.maxValue(params.maxValue);
            this.displayMessage(params.displayMessage);
        }

        updateCheckCond(checkCondItem: string){
            this.checkCond(checkCondItem);
        }

        updateCheckCondDis(itemName: string){
            this.checkCondDis(itemName);
        }

        updateCheckCondKdw007(addItems: Array<number>, subItems: Array<number>){
            this.countableAddAtdItems(addItems);
            this.countableSubAtdItems(subItems);
        }
        updateTextDis(addItem: Array<string>, subItems: Array<string>){
            this.checkCondDis(_.join(addItem,'+') + (_.isEmpty(subItems) ? " " : ("-" +_.join(subItems,'-'))));
        }

        clearMaxValue(){
            this.maxValue(null);
        }
        clearCheckCod(){
            this.checkCond(null);
            this.checkCondB(null);
            this.countableAddAtdItems([]);
            this.countableSubAtdItems([]);
            this.checkCondDis("");
            this.minValue(null);
            this.maxValue(null);
        }
        clearCheckCodB(){
            this.checkCond(null);
            this.checkCondDis("");
            this.countableAddAtdItems([]);
            this.countableSubAtdItems([]);
            this.minValue(null);
            this.maxValue(null);
        }
    }

    export interface IPattern{
        checkItem: number;
        checkCond: string;
        countableAddAtdItems: Array<number>;
        countableSubAtdItems: Array<number>;
        checkCondB: number;
        operator: number;
        minValue?: number;
        maxValue?: number;
        displayMessage: string;
    }


    class EnumModel {
        value: KnockoutObservable<number>;
        fieldName: string;
        localizedName: string;
        constructor(param: IEnumModel) {
            this.value = ko.observable(param.value);
            this.fieldName = param.fieldName || '';
            this.localizedName = param.localizedName || '';
        }
    }
    export class ItemModel {
        code: number;
        name: string;
        description: string = "";

        constructor(code: number, name: string, description?: string) {
            this.code = code;
            this.name = name;
            if (description) {
                this.description = description;
            }
        }
    }

    class ItemEnumModel {
        value: number;
        name: string;

        constructor(code: number, name: string, description?: string) {
            this.value = code;
            this.name = name;
        }
    }
    interface IEnumModel {
        value: number;
        fieldName: string;
        localizedName: string;
    }

    interface IParentParams {
        category: number;
        condition: IPattern;
    }

    enum RangeCompareType{
        // 範囲の間（境界値を含まない）（＜＞）
        BETWEEN_RANGE_OPEN = 6,
        /* 範囲の間（境界値を含む）（≦≧） */
        BETWEEN_RANGE_CLOSED = 7,
        /* 範囲の外（境界値を含まない）（＞＜） */
        OUTSIDE_RANGE_OPEN = 8,
        /* 範囲の外（境界値を含む）（≧≦） */
        OUTSIDE_RANGE_CLOSED = 9,
    }

    enum WorkplaceCategory {
        // スケジュール／日次
        SCHEDULE_DAILY = 3, // 月次
        MONTHLY = 4
    }

    enum CheckMonthlyItemsType {

        /* 平均時間 */
        AVERAGE_TIME  = 1,
        /* 平均日数 */
        AVERAGE_NUMBER_DAY = 2,
        /* 平均回数 */
        AVERAGE_NUMBER_TIME = 3,
        /* 平均比率 */
        AVERAGE_RATIO = 4

    }

    enum MONTHLYATTENDANCEITEMATR {
        TIME = 1,
        /* 回数 */
        NUMBER = 2,
        /* 日数 */
        DAYS = 3,
        /* 金額 */
        AMOUNT = 4,
        /* マスタを参照する */
        REFER_TO_MASTER = 5


    }

    class AttdItemDto {

        attendanceItemId : number;
        attendanceItemName: string;
        attendanceItemDisplayNumber: number;
        nameLineFeedPosition: number;
        dailyAttendanceAtr: number;
        displayNumber: number;
        userCanUpdateAtr: number;
    }

    class AttendanceNameDivergenceDto {
        attendanceItemId: number;
        attendanceItemName: string;
        attendanceItemDisplayNumber: number;
    }
}