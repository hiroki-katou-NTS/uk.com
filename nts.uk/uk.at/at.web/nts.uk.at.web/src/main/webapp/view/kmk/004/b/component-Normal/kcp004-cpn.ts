/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.b {
    const template = `
		<div id="tree-grid">
		</div>
    `;

    const API = {
        GET_WORKPLACE_ID: 'screen/at/kmk004/viewc/wkp/getWorkPlaceId'
    };

    interface Params {
        selectedId: KnockoutObservable<string>;
        model: Model;
        isChange: KnockoutObservable<string>;
    }

    @component({
        name: 'kcp004',
        template
    })

    export class KCP004VM extends ko.ViewModel {

        public selectedId: KnockoutObservable<string> = ko.observable('');
        public alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel> = ko.observableArray([]);
        public model: Model;
        public data: UnitModel[];
        public isChange: KnockoutObservable<string> = ko.observable('');

        created(params: Params) {
            const vm = this;

            vm.selectedId = params.selectedId;
            vm.model = params.model;
            vm.isChange = params.isChange;

            vm.reloadData();
            vm.reloadIsCheck();

            vm.isChange
                .subscribe(() => {
                    vm.reloadIsCheck();
                });
        }

        reloadIsCheck() {
            const vm = this;
            vm.$ajax(API.GET_WORKPLACE_ID)
                .then((data: any) => {
                    console.log(data);
                    
                    let settings: UnitAlreadySettingModel[] = [];
                    _.forEach(data, ((value) => {
                        let s: UnitAlreadySettingModel = { workplaceId: value.workplaceId, isAlreadySetting: true };
                        settings.push(s);
                    }
                    ));
                    vm.alreadySettingList(settings);

                    if (ko.unwrap(vm.selectedId)) {
                        const exist = _.find(ko.unwrap(vm.alreadySettingList), (m: UnitAlreadySettingModel) => m.workplaceId === ko.unwrap(vm.model.id));

                        if (exist) {
                            vm.model.updateStatus(exist.isAlreadySetting);
                        } else {
                            vm.model.updateStatus(false);
                        }
                    }
                });
        }

        reloadData() {
            const vm = this;

            vm.$blockui("invisible")
                .then(() => $('#tree-grid')
                    .ntsTreeComponent({
                        isShowAlreadySet: true,
                        isMultipleUse: false,
                        isMultiSelect: false,
                        startMode: 0,
                        selectedId: vm.selectedId,
                        baseDate: ko.observable(new Date),
                        selectType: 3,
                        isShowSelectButton: true,
                        isDialog: false,
                        alreadySettingList: vm.alreadySettingList,
                        maxRows: 12,
                        tabindex: 1,
                        systemType: 2

                    }).done(() => {
                        vm.data = $('#tree-grid').getDataList();
                    }).then(() => {
                        vm.selectedId
                            .subscribe((selectedId) => {

                                vm.data = $('#tree-grid').getDataList();

                                let flat: any = function (wk: UnitModel) {
                                    return [wk, _.flatMap(wk.children, flat)];
                                },
                                    flatMapItems = _.flatMapDeep(vm.data, flat);

                                let selectedItem: UnitModel = _.find(flatMapItems, ['id', selectedId]);
                                vm.model.update({
                                    id: selectedItem.id,
                                    name: selectedItem.name,
                                    isAlreadySetting: selectedItem.isAlreadySetting
                                } as IModel);
                            });
                        vm.selectedId.valueHasMutated();
                    })
                    .then(() => {
                        vm.$blockui('clear');
                    }));
        }
    }

    class UnitModel {
        id: string;
        code: string;
        name: string;
        nodeText?: string;
        level: number;
        heirarchyCode: string;
        isAlreadySetting?: boolean;
        children: Array<UnitModel>;
    }

    interface IModel {
        id: string;
        name: string;
        isAlreadySetting: boolean;
    }

    interface UnitAlreadySettingModel {
        workplaceId: string;
        isAlreadySetting: boolean;
    }

    export class Model {
        id: KnockoutObservable<string> = ko.observable('');
        name: KnockoutObservable<string> = ko.observable('');
        isAlreadySetting: KnockoutObservable<boolean> = ko.observable(false);

        constructor(param?: IModel) {
            this.update(param);
        }

        public update(param?: IModel) {
            if (param) {
                this.id(param.id);
                this.name(param.name);
                this.isAlreadySetting(param.isAlreadySetting);
            }
        }
        public updateStatus(isAlreadySetting: boolean) {
            this.isAlreadySetting(isAlreadySetting);
        }
    }
}