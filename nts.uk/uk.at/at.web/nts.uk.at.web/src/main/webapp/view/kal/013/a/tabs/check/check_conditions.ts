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

            vm.selectedAll.subscribe((newValue) => {
                if (newValue === null) return;
                _.forEach(vm.checkConditionsList(), (item) => {
                    item.isChecked(newValue);
                });
            });

            vm.checkConditionsList.subscribe((newList) => {
                if (!newList || newList.length <= 0) {
                    vm.selectedAll(false);
                    return;
                }

                let isSelectedAll = _.every(vm.checkConditionsList(), (x) => x.isCheck() === true);
                if (isSelectedAll === false) isSelectedAll = null;
                vm.selectedAll(isSelectedAll);

                let checkSelected = _.some(vm.checkConditionsList(), (x) => x.isCheck() === true);
                vm.isEnableRemove(checkSelected);
            });

            vm.getCheckConditionsListByCategory();
        }

        getCheckConditionsListByCategory() {
            const vm = this;

            //vm.$blockui('show');

            let params = {};

            /* vm.$ajax(PATH.getAlarmList, params).done((data: any) => {
              vm.$blockui('hide');
            }).always(() => vm.$blockui('hide') ); */
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
                index + 1,
                null,
                0,
                null,
                null,
                null,
                null,
                null,
                null,
                null
            );
            vm.addNewItem(newCheckCondition);
        }

        removeCheckCondition() {
            const vm = this;
            let checkConditionsList = _.filter(vm.checkConditionsList(), (x) => x.isCheck() === false);
            vm.checkConditionsList(checkConditionsList);
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
                        data.minValue(result.shareParam.minValue);
                        data.maxValue(result.shareParam.maxValue);
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