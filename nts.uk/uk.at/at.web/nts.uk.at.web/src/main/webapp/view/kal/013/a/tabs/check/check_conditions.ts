module nts.uk.at.view.kal013.a.tab {
    import common = nts.uk.at.view.kal013.common;

    export class CheckCondition extends ko.ViewModel {

        checkConditions: KnockoutObservable<boolean> = ko.observable(false);
        checkConditionsList: KnockoutObservableArray<common.CheckConditionDto> = ko.observableArray([]);
        currentRowSelected: KnockoutObservable<number> = ko.observable(0);

        selectedAll: KnockoutObservable<boolean> = ko.observable(false);
        roundingRules: KnockoutObservableArray<any> = ko.observableArray([]);
        isEnableRemove: KnockoutObservable<boolean> = ko.observable(false);

        swithchButton: KnockoutObservableArray<any> = ko.observableArray([]);

        category: number;

        constructor(checkConditions?: boolean, category?: number) {
            super();

            const vm = this;

            vm.swithchButton([
                { code: 1, name: vm.$i18n('KAL003_189') },
                { code: 0, name: vm.$i18n('KAL003_190') }
            ]);

            vm.checkConditions(checkConditions);
            if (category) {
                vm.category = category;
            }

            vm.selectedAll = ko.pureComputed({
                read: function () {
                    let l = vm.checkConditionsList().length;
                    if (vm.checkConditionsList().filter((x) => {return x.isCheck()}).length == l && l > 0) {
                        return true;
                    } else {
                        return false;
                    }
                },
                write: function (value) {
                    for (var i = 0; i < vm.checkConditionsList().length; i++) {
                        vm.checkConditionsList()[i].isCheck(value);
                    }
                },
                owner: self
            });

            vm.isEnableRemove = ko.pureComputed({
                read: function() {
                    if (vm.checkConditionsList().filter((x) => { return x.isCheck() }).length > 0) {
                        return true;
                    } else {
                        return false;
                    }
                },
                owner: self
            });

            vm.currentRowSelected.subscribe((data) => {
                if (vm.category == 9) {
                    $("#fixedTableCCDT tr").removeClass("ui-state-active");
                    $("#fixedTableCCDT tr[data-id='" + data + "']").addClass("ui-state-active");
                } else {
                    $("#fixedTableCCDT tr").removeClass("ui-state-active");
                    $("#fixedTableCCDT tr[data-id='" + data + "']").addClass("ui-state-active");
                }
            });


            $(function() {
                $("#fixedTableCCDT").on("click", "tr", function() {
                    let id = $(this).attr("data-id");
                    vm.currentRowSelected(Number(id));
                })
            });

        }

        /**
         * Add new CheckCondition row
         * @param [item]
         * @returns
         */
        addNewItem(item?: common.CheckConditionDto) {
            const vm = this;

            if (_.isNil(item)) return;

            item.isCheck.subscribe((newValue) => {
                vm.checkConditionsList.valueHasMutated();
            });
            vm.checkConditionsList.push(item);
            $("#fixedTableCCDT tr")[vm.checkConditionsList().length - 1].scrollIntoView();
            vm.currentRowSelected(vm.checkConditionsList().length);
        }

        addNewCheckCondition() {
            const vm = this;
            let lastItem = _.last(vm.checkConditionsList());
            let index = vm.checkConditionsList().length + 1 ;

            let newCheckCondition = new common.CheckConditionDto(
                false,
                null,
                index,
                vm.category == common.WorkplaceCategory.MONTHLY ? 1 : 0,
                1,
                null,
                null,
                null,
                0,
                null,
                null,
                null,
                [],
                []
            );
            vm.addNewItem(newCheckCondition);
        }

        removeCheckCondition() {
            const vm = this;
            let currentList = _.clone(vm.checkConditionsList());
            // Remove Item
            let afterRemoveLst = _.filter(currentList, (x: any) => x.isCheck() === false);
            let selectedItems = _.filter(currentList, (x: any) => x.isCheck() === true);
            afterRemoveLst = _.sortBy(afterRemoveLst, i => i.no());
            _.forEach(afterRemoveLst, function (i: any, index) {
                i.no(index + 1);
            });
            _.forEach(selectedItems, function (i: any) {
                $('#' + i.no()).ntsError('clear');
            });
            vm.$dialog.info({ messageId: "Msg_16" }).then(() => {
                vm.checkConditionsList(afterRemoveLst);
                if (afterRemoveLst.length > 0) {
                    if (vm.currentRowSelected() > afterRemoveLst.length) {
                        vm.currentRowSelected(afterRemoveLst.length);
                    }
                    vm.currentRowSelected.valueHasMutated();
                }
            });
            // vm.isEnableRemove(false);
        }


        displayScreenKAL003B(data: common.CheckConditionDto) {
            const vm = this;

            let params = {
                category: vm.category,
                condition: {
                    checkItem: data.checkItem(),
                    checkCond: data.checkCond(),
                    checkCondB: data.checkCondB(),
                    countableAddAtdItems: data.additionAttendanceItems(),
                    countableSubAtdItems: data.substractionAttendanceItems(),
                    operator: data.operator(),
                    minValue: data.minValue(),
                    maxValue: data.maxValue(),
                    displayMessage: data.message()
                }
            };

            vm.$window.modal('/view/kal/013/b/index.xhtml', params)
                .then((result: any) => {
                    if (result && result.shareParam) {
                        console.log(result);
                        data.checkItem(result.shareParam.condition.checkItem);
                        data.minValue(result.shareParam.condition.minValue ? result.shareParam.condition.minValue + "" : null);
                        data.maxValue(result.shareParam.condition.maxValue ? result.shareParam.condition.maxValue + "" : null);
                        data.message(result.shareParam.condition.displayMessage);
                        data.checkCond(result.shareParam.condition.checkCond);
                        data.additionAttendanceItems(result.shareParam.condition.countableAddAtdItems);
                        data.substractionAttendanceItems(result.shareParam.condition.countableSubAtdItems);
                        data.operator(result.shareParam.condition.operator);
                        data.checkCondB(result.shareParam.condition.checkCondB);
                    }
                    // bussiness logic after modal closed

                });
        }
    }
}


