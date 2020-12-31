module nts.uk.at.view.kal013.a.tab {
    import common = nts.uk.at.view.kal013.common;

    export class CheckCondition extends ko.ViewModel {

        checkConditions: KnockoutObservable<boolean> = ko.observable(false);
        checkConditionsList: KnockoutObservableArray<common.CheckConditionDto> = ko.observableArray([]);

        selectedAll: KnockoutObservable<boolean> = ko.observable(false);
        roundingRules: KnockoutObservableArray<any> = ko.observableArray([]);
        isEnableRemove: KnockoutObservable<boolean> = ko.observable(false);

        category: number;

        constructor(checkConditions?: boolean, category?: number) {
            super();

            const vm = this;

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
        }

        addNewCheckCondition() {
            const vm = this;
            let lastItem = _.last(vm.checkConditionsList());
            let index = vm.checkConditionsList().length;

            let newCheckCondition = new common.CheckConditionDto(
                false,
                null,
                index,
                vm.category == common.WorkplaceCategory.MONTHLY ? 1 : 0,
                0,
                null,
                "0",
                null,
                0,
                null,
                null,
                null
            );
            vm.addNewItem(newCheckCondition);
        }

        removeCheckCondition() {
            const vm = this;
            let currentList = _.clone(vm.checkConditionsList());
            // Remove Item
            let afterRemoveLst = _.filter(currentList, (x: any) => x.isCheck() === false);
            afterRemoveLst = _.sortBy(afterRemoveLst, i => i.no());
            _.forEach(afterRemoveLst, function (i: any, index) {
                i.no(index);
            });
            vm.checkConditionsList(afterRemoveLst);
            vm.isEnableRemove(false);
        }


        displayScreenKAL003B(data: common.CheckConditionDto) {
            const vm = this;

            let params = {
                category: vm.category,
                condition: {
                    checkItem: data.checkItem(),
                    checkCond: data.checkCond(),
                    checkCondB: data.checkCondB(),
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
                        data.checkItem(result.shareParam.checkItem);
                        data.minValue(result.shareParam.minValue ? result.shareParam.minValue + "" : null);
                        data.maxValue(result.shareParam.maxValue ? result.shareParam.maxValue + "" : null);
                        data.message(result.shareParam.displayMessage);
                        data.checkCond(result.shareParam.checkCondDis);
                        data.operator(result.shareParam.operator);
                        data.checkCondB(result.shareParam.checkCondB);
                    }
                    // bussiness logic after modal closed

                });
        }
    }
}