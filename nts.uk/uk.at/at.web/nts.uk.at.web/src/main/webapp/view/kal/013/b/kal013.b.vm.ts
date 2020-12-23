/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kal013.b {
    import errors = nts.uk.ui.errors;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    const PATH_API = {
       GET_ENUM_OPERATOR: "at/record/alarmwrkp/screen/getEnumCompareType",
    };

    @bean()
    export class KAL013BViewModel extends ko.ViewModel {
        listTypeCheck    : KnockoutObservableArray<ItemEnumModel> = ko.observableArray([]);
        pattern : KnockoutObservable<Pattern> = ko.observable(null);
        listSingleValueCompareTypes: KnockoutObservableArray<EnumModel> = ko.observableArray([]);
        contrastTypeList:  KnockoutObservableArray<ItemEnumModel> = ko.observableArray([]);
        category: KnockoutObservable<number> = ko.observable(null);
        enableMaxValue: KnockoutObservable<boolean> = ko.observable(true);
        switchPatternA: KnockoutObservable<boolean> = ko.observable(true);
        timeControl: KnockoutObservable<boolean> = ko.observable(true);
        constraint : KnockoutObservable<string> = ko.observable("");
        dailyContraint: Map<number,string> =new Map([
            [1, "NumberOfPeople"],
            [2, "Time"],
            [3, "Amount"],
            [4, "RatioComparison"]
        ]);
        monthlyContraint: Map<number,string> = new Map([
            [1, "AverageTime"], // AVERAGE_TIME(1, "平均時間"),
            [2, "AverageNumberDays"], // AVERAGE_NUMBER_DAY(2, "平均日数"),
            [3, "AverageNumberTimes"], //AVERAGE_NUMBER_TIME(3, "平均回数"),
            [4, "AverageRatio"], // AVERAGE_RATIO(4, "平均比率"),
            [5, "AverageTime"], // TIME_FREEDOM(5, "平均時間自由"),
            [6, "AverageNumberDays"], // AVERAGE_DAY_FREE(6, "平均日数自由")
            [7, "AverageNumberTimes"], // AVERAGE_TIME_FREE(7, "平均回数自由")
        ]);

        constructor(params: IParentParams) {
            super();
            const vm = this;

            let option: IPattern = {checkItem: 1, checkCond: ["1","2"], checkCondB:1,
                operator: 1, minValue: 1, maxValue:1, displayMessage:"dkdkdkd"};

            params = {category: WorkplaceCategory.MONTHLY, condition: option};

            if (params.category == WorkplaceCategory.MONTHLY) {
                vm.listTypeCheck(__viewContext.enums.CheckMonthlyItemsType);
            } else {
                vm.listTypeCheck(__viewContext.enums.CheckDayItemsType);
            }

            vm.getEnum().done(()=>{

            });

            vm.category(params.category);
            vm.pattern(new Pattern(params.condition));

        }

        created(params: IParentParams) {
            const vm = this;
            errors.clearAll();
            vm.pattern().operator.subscribe((value)=>{
               if (_.indexOf([6,7,8,9],value) != -1 ){
                   vm.enableMaxValue(true);
               } else {
                   vm.enableMaxValue(false);
                   vm.pattern().clearMaxValue();
               }
            });

            vm.pattern().checkItem.subscribe((value)=>{
                vm.$errors("clear",".endValue");
                vm.switchPatternA(true)
                // 対比の場合 - スケジュール／日次
               if (vm.category() == WorkplaceCategory.SCHEDULE_DAILY && value == 0) {
                    vm.switchPatternA(false);
                    vm.contrastTypeList(__viewContext.enums.ContrastType);
               } else if (vm.category() == WorkplaceCategory.MONTHLY
                   && _.indexOf([CheckMonthlyItemsType.TIME_FREEDOM, CheckMonthlyItemsType.AVERAGE_DAY_FREE,
                       CheckMonthlyItemsType.AVERAGE_TIME_FREE],value) != -1 ){
                   vm.switchPatternA(false);
                    switch (value){
                        case CheckMonthlyItemsType.TIME_FREEDOM:
                            vm.contrastTypeList(__viewContext.enums.AverageTime);
                            break;
                        case CheckMonthlyItemsType.AVERAGE_DAY_FREE:
                            vm.contrastTypeList(__viewContext.enums.AverageNumberOfDays);
                            break;
                        case CheckMonthlyItemsType.AVERAGE_TIME_FREE:
                            vm.contrastTypeList(__viewContext.enums.AverageNumberOfTimes);
                            break;
                        default:
                            vm.contrastTypeList([]);
                            break;
                    }
               }
               // Kind of control
                vm.timeControl(false);
                // 月次 - 平均時間 ||  スケジュール／日次 - 時間対比
                if ((vm.category() == WorkplaceCategory.MONTHLY && value == 1)
                    || (vm.category() == WorkplaceCategory.SCHEDULE_DAILY && value == 2) ){
                    vm.timeControl(true);
                }

                // Contraint
                if (vm.category() == WorkplaceCategory.SCHEDULE_DAILY){
                    vm.constraint(vm.dailyContraint.get(value));
                } else{
                    vm.constraint(vm.monthlyContraint.get(value));
                }
                console.log("constraint: "+ vm.constraint());


            });

            vm.pattern().checkCondB.subscribe((value)=>{
                vm.$errors("clear",".endValue");
                vm.timeControl(false);
                if (_.indexOf([2,5,8],value) != -1){
                    vm.timeControl(true);
                }

                if (_.indexOf([1,4,7],value) != -1){
                    vm.constraint(vm.dailyContraint.get(1));
                } else if (_.indexOf([2,5,8],value) != -1){
                    vm.constraint(vm.dailyContraint.get(2));
                } else if (_.indexOf([3,6,9],value) != -1){
                    vm.constraint(vm.dailyContraint.get(3));
                }
            })

            vm.pattern().operator.subscribe((value)=>{
                vm.$errors("clear",".endValue");
            });
        }

        mounted() {
            const vm = this;

            $("#table-group1condition").ntsFixedTable();
            $("#table-group2condition").ntsFixedTable();
            $('#cbxTypeCheckWorkRecordcategory5').focus();
            $('#cbxTypeCheckWorkRecordcategory7').focus();
            $('#cbxTypeCheckWorkRecordcategory9').focus();
        }

        //openSelectAtdItemDialogTarget() {
        btnSettingB2_2_click(params: any) {
            const vm = this;
            let param: any = {
                isMulti: true,
                selecteds: [
                    params.checkCond()
                ]
            }
            setShared('KDL007_PARAM', param, true);
            nts.uk.ui.windows.sub.modal('/view/kdl/007/a/index.xhtml').onClosed(() => {
                let listResult = getShared('KDL007_VALUES');
                vm.pattern().updateCheckCond(listResult.selecteds);
            });
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
            if  ((( _.indexOf([RangeCompareType.BETWEEN_RANGE_OPEN, RangeCompareType.OUTSIDE_RANGE_OPEN],vm.pattern().operator()) == -1
                    && vm.pattern().minValue() >= vm.pattern().maxValue() ))
                    || ( _.indexOf([RangeCompareType.BETWEEN_RANGE_OPEN, RangeCompareType.OUTSIDE_RANGE_OPEN],vm.pattern().operator()) != -1
                    && vm.pattern().minValue() > vm.pattern().maxValue() ))
            {
                vm.$errors(".endValue", "Msg_927");
                return false;
            }
            return true;
        }


        btnDecision() {
            const vm = this;
            $('.nts-input').filter(":enabled").trigger("validate");
            if (errors.hasError() === true || !vm.validate()) {
                return;
            }
            let shareParam: IPattern = ko.toJS(vm.pattern());
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
        checkCond: KnockoutObservableArray<string> = ko.observableArray([]);
        checkCondDis: KnockoutObservable<string> = ko.observable("");
        checkCondB: KnockoutObservable<number> = ko.observable(0);
        operator: KnockoutObservable<number> = ko.observable(0);
        minValue: KnockoutObservable<number> = ko.observable(0);
        maxValue: KnockoutObservable<number> = ko.observable(0);
        displayMessage: KnockoutObservable<string> = ko.observable("");
        constructor(params: IPattern){
            this.checkItem(params.checkItem);
            this.checkCond(params.checkCond);
            this.checkCondB(params.checkCondB);
            this.operator(params.operator);
            this.minValue(params.minValue);
            this.maxValue(params.maxValue);
            this.displayMessage(params.displayMessage);
        }

        update(params: IPattern){
            this.checkItem(params.checkItem);
            this.checkCond(params.checkCond);
            this.checkCondB(params.checkCondB);
            this.operator(params.operator);
            this.minValue(params.minValue);
            this.maxValue(params.maxValue);
            this.displayMessage(params.displayMessage);
        }

        updateCheckCond(checkCondItem: Array<string>){
            this.checkCond(checkCondItem);
            this.checkCondDis(_.join(checkCondItem,'、'));
        }

        clearMaxValue(){
            this.maxValue(null);
        }
    }

    interface IPattern{
        checkItem: number;
        checkCond: Array<string>;
        checkCondB: number;
        operator: number;
        minValue: number;
        maxValue: number;
        displayMessage: string;
    }


    class EnumModel {
        value: KnockoutObservable<number>;
        fieldName: string;
        localizedName: string;
        constructor(param: IEnumModel) {
            let self = this;
            self.value = ko.observable(param.value || -1);
            self.fieldName = param.fieldName || '';
            self.localizedName = param.localizedName || '';
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
        AVERAGE_RATIO = 4,
        /* 平均時間自由 */
        TIME_FREEDOM = 5,
        /* 平均日数自由 */
        AVERAGE_DAY_FREE = 6,
        /* 平均回数自由 */
        AVERAGE_TIME_FREE = 7
    }
}