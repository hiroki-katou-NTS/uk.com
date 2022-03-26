/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module test.viewmodel {
    import WorkPlaceReturn = nts.uk.com.view.cdl014.a.WorkPlaceReturn;
    import Cdl014Param = nts.uk.com.view.cdl014.a.Cdl014Param;
    @bean()
    class ScreenModel extends ko.ViewModel {
        //選択済項目
        listSelectionType: KnockoutObservableArray<any>;
        enable: KnockoutObservable<boolean> = ko.observable(true);
        enableMulti: KnockoutObservable<boolean> = ko.observable(false);
        selectedSelectionType: KnockoutObservable<number> = ko.observable(0);

        //選択モード
        listTreeType: KnockoutObservableArray<any>;
        selectedTreeType: KnockoutObservable<number> = ko.observable(0);

        // 未選択表示
        listDisplay: KnockoutObservableArray<any>;
        selectedDisplay: KnockoutObservable<number> = ko.observable(1);

        // 絞込リスト
        listFilter: KnockoutObservableArray<any>;
        selectedFilters: KnockoutObservableArray<any> = ko.observableArray([]);

        // Return List＜｛職場グループID、職場グループコード、職場グループ名称}＞
        listWorkPlace: KnockoutObservableArray<WorkPlaceReturn> = ko.observableArray([]);
        selectedWrk : KnockoutObservable<any> = ko.observable([]);

        constructor() {
            super();
            const vm = this;
            vm.listSelectionType = ko.observableArray([
                { code: 2, name: '全選択', enable: vm.enableMulti },
                { code: 1, name: '先頭', enable: vm.enable },
                { code: 0, name: '選択なし', enable: vm.enable },
                { code: 3, name: '職場グループID（List）', enable: vm.enable }
            ]);
            vm.listTreeType = ko.observableArray([
                { code: 0, name: '単一選択' },
                { code: 1, name: '複数選択' }
            ]);
            vm.listDisplay = ko.observableArray([
                { code: 0, name: '非表示' },
                { code: 1, name: '表示' }
            ]);

            vm.listFilter = ko.observableArray([
                { code: '0', name: '通常' },
                { code: '1', name: '病棟' },
                { code: '2', name: '介護事業所' }
            ]);
            vm.selectedTreeType.subscribe((i:any)=>{
                vm.enableMulti(i==1);
                if (i==1){
                    vm.selectedWrk(new Array(vm.selectedWrk().toString()));
                }
            })
        }

        openCdl014() {
            const vm = this;
            let data: Cdl014Param = {
                multiple: vm.selectedTreeType() == 1 ? true : false,
                showEmptyItem: vm.selectedDisplay() == 1 ? true : false,
                selectedMode: vm.selectedSelectionType(),
                alreadySettingList: vm.selectedSelectionType() ==3 ? (vm.selectedTreeType() == 1) ? vm.selectedWrk(): new Array(vm.selectedWrk()) : [],
                currentCodes: _.map( _.filter(vm.listWorkPlace(),i=> vm.selectedWrk().contains(i.workPlaceId)), (item)=>{return item.workPlaceCode}),
                currentNames: _.map( _.filter(vm.listWorkPlace(),i=> vm.selectedWrk().contains(i.workPlaceId)), (item)=>{return item.workPlaceName}),
                selectedWkpGroupTypes: vm.selectedFilters()
            }
            let result: Array<WorkPlaceReturn> = [];
            vm.$window.modal('/view/cdl/014/a/index.xhtml', data)
                .then((result: any) => {
                    if (!result.isCanceled){
                        vm.listWorkPlace(result.result);
                        vm.selectedWrk(_.map(result.result,(item)=>{
                            return item.workPlaceId;
                        }))
                    }

                });
        }

    }
}