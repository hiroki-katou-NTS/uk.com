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
            [2, "AverageNumberOfDays"], // AVERAGE_NUMBER_DAY(2, "平均日数"),
            [3, "AverageNumberOfTimes"], //AVERAGE_NUMBER_TIME(3, "平均回数"),
            [4, "AverageRatio"], // AVERAGE_RATIO(4, "平均比率"),
            [5, "AverageTime"], // TIME_FREEDOM(5, "平均時間自由"),
            [6, "AverageNumberOfDays"], // AVERAGE_DAY_FREE(6, "平均日数自由")
            [7, "AverageNumberOfTimes"], // AVERAGE_TIME_FREE(7, "平均回数自由")
        ]);

        constructor(params: IParentParams) {
            super();
            const vm = this;

            // スケジュール／日次
            // SCHEDULE_DAILY(3, "KAL020_300"),
            // 月次
            // MONTHLY(4, "KAL020_401"),

            // let option: IPattern = {checkItem: 1, checkCond: ["1","2"], checkCondB:1,
            //     operator: 1, minValue: 1, maxValue:1, displayMessage:"dkdkdkd"};
            //
            // params = {category: 3, condition: option};

            if (params.category == 4) {
                vm.listTypeCheck(__viewContext.enums.CheckMonthlyItemsType);
            } else {
                vm.listTypeCheck(__viewContext.enums.CheckDayItemsType);
            }
            vm.contrastTypeList(__viewContext.enums.ContrastType);
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

                vm.switchPatternA(true)
                // 対比の場合 - スケジュール／日次
               if (vm.category() == 3 && value == 0) {
                    vm.switchPatternA(false);
               }
               // Kind of control
                vm.timeControl(false);
                // 月次 - 平均時間 ||  スケジュール／日次 - 時間対比
                if ((vm.category() == 4 && value == 1)
                    || (vm.category() == 3 && value == 2) ){
                    vm.timeControl(true);
                }

                // Contraint
                if (vm.category() == 3){
                    vm.constraint(vm.dailyContraint.get(value));
                } else{
                    vm.constraint(vm.monthlyContraint.get(value));
                }
                console.log("constraint: "+ vm.constraint());


            });

            vm.pattern().checkCondB.subscribe((value)=>{
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
                listSingleValueCompareTypse: Array<EnumModel>) => {
                vm.listSingleValueCompareTypes(vm.getLocalizedNameForEnum(listSingleValueCompareTypse));
                dfd.resolve();

            }).fail(()=>{
                dfd.reject();
            }).always(() => {
            });
            return dfd.promise();
        }

        private getLocalizedNameForEnum(listEnum: Array<EnumModel>): Array<EnumModel> {
            if (listEnum) {
                for (var i = 0; i < listEnum.length; i++) {
                    if (listEnum[i].localizedName) {
                        listEnum[i].localizedName = resource.getText(listEnum[i].localizedName);
                    }
                }
                return listEnum;
            }
            return [];
        }

        validate(){
            const vm = this;
            if  (vm.pattern().minValue() > vm.pattern().maxValue()){
                vm.$errors("#end1", "Msg_927");
            }
        }
        btnDecision() {
            const vm = this;
            $('.nts-input').filter(":enabled").trigger("validate");
            if (errors.hasError() === true) {
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
}