/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.com.view.cdl014.a {
    import Option = nts.uk.com.view.kcp011.share.Option;
    @bean()
    export class ScreenModel  extends ko.ViewModel  {
        options: Option;
        currentIds: KnockoutObservable<any> = ko.observable([]);
        currentCodes: KnockoutObservable<any> = ko.observable([]);
        currentNames: KnockoutObservable<any> = ko.observable([]);
        alreadySettingList: KnockoutObservableArray<any> = ko.observableArray(['1']);
        listWorkPlace: KnockoutObservableArray<WorkPlaceReturn> = ko.observableArray([]);

        constructor(dataShare: Cdl014Param) {
            super();
            const vm = this;
            if (!_.isEmpty(dataShare.currentCodes)){
                vm.currentCodes(dataShare.currentCodes);
            }
            if (!_.isEmpty(dataShare.currentNames)){
                vm.currentNames(dataShare.currentNames);
            }
            vm.options = {
                // neu muon lay code ra tu trong list thi bind gia tri nay vao
                currentCodes: vm.currentCodes,
                currentNames: vm.currentNames,
                // tuong tu voi id
                currentIds: vm.currentIds,
                //
                multiple: dataShare.multiple,
                rows: 12,
                tabindex: 1,
                isAlreadySetting: false,
                alreadySettingList: ko.observableArray(dataShare.alreadySettingList),
                // show o tim kiem
                showSearch: true,
                showPanel: false,
                // show empty item
                showEmptyItem: dataShare.multiple ? false : dataShare.showEmptyItem,
                // trigger reload lai data cua component
                reloadData: ko.observable(''),
                reloadComponent: ko.observable({}),
                height: 370,
                // NONE = 0, FIRST = 1, ALL = 2
                selectedMode: dataShare.selectedMode,
                // TODO chờ code mới
                //selectedWkpGroupTypes: dataShare.selectedWkpGroupTypes.map(function (i) { return Number(i); },
            };
        }
        mounted(){
            $("#multi-list-nopanel_container").focus();
        }

        proceed(){
            const vm = this;
            let result:Array<WorkPlaceReturn> = [];
            // The cause is kcp011 return number when select none
            if (vm.currentCodes().length == 0 || !_.isNaN(new Number(vm.currentIds()))) {
                vm.$dialog.error({messageId: "Msg_2286"});
            } else {
                if (vm.options.multiple == true) {
                    for (let i = 0; i < vm.currentCodes().length; i++) {
                        result.push(new WorkPlaceReturn(vm.currentIds()[i], vm.currentCodes()[i], vm.currentNames()[i]));
                    }
                } else if (!_.isEmpty(vm.currentCodes())) {
                    result.push(new WorkPlaceReturn(vm.currentIds(), vm.currentCodes(), vm.currentNames()));
                }

                vm.listWorkPlace(result);
                vm.$window.close({
                    result: vm.listWorkPlace(), isCanceled: false
                });
            }
        }
        /**
         * cancel
         */
        cancel(){
            const vm = this;
            vm.$window.close({
                result: [],
                isCanceled: true
            });
        };

    }

    export class WorkPlaceReturn{
        workPlaceId: string = '';
        workPlaceCode: string = '';
        workPlaceName: string = '';
        constructor(id: string, code: string, name: string){
            this.workPlaceId = id;
            this.workPlaceCode = code;
            this.workPlaceName = name;
        }
    }

    export class Cdl014Param{
        multiple: boolean;
        showEmptyItem: boolean;
        selectedMode: number;
        alreadySettingList: any; // selected id
        currentCodes: any; // selected code - need to pass with id
        currentNames: any; // selected name - need to pass with id
        selectedWkpGroupTypes: any;

        constructor(multiple: boolean, showEmptyItem: boolean, selectedMode: number,alreadySettingList: any, selectedWkpGroupTypes: any, currentCodes: any,currentNames: any){
            this.multiple = multiple;
            this.showEmptyItem = showEmptyItem;
            this.selectedMode = selectedMode;
            this.alreadySettingList = alreadySettingList;
            this.selectedWkpGroupTypes = selectedWkpGroupTypes;
            this.currentCodes = currentCodes;
            this.currentNames = currentNames;
        }
    }
}