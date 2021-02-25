module nts.uk.ui.at.kcp013.a {
    import k = nts.uk.ui.at.kcp013.shared;

    @bean()
    export class ViewModel extends ko.ViewModel {
        workTime: WorkTimeModel = {
            selected: ko.observable(''),
            dataSources: ko.observableArray([])
        };

        otherParams: OtherWorkTimeParam = {
            workplaceId: ko.observable(''),
            disabled: ko.observable(false),
            filter: ko.observable(false),
            showMode: ko.observable(k.SHOW_MODE.NOT_SET),
            width: ko.observable(450)
        };

        currentWorkTime: KnockoutObservable<k.WorkTimeModel | null> = ko.observable(null);

        isDialog: KnockoutObservable<boolean> = ko.observable(false);

        codes = {
            vm,
            view
        };

        public created() {
            const vm = this;
            const { workTime } = vm;

            workTime.selected
                .subscribe(c => {
                    const ds = ko.unwrap(workTime.dataSources);

                    const exist = _.find(ds, f => f.code === c);

                    if (!exist) {
                        vm.currentWorkTime(null);
                    } else {
                        vm.currentWorkTime(exist);
                    }
                });
        }

        public mounted() {
            const vm = this;

            vm.isDialog(window !== window.top);

            $('#tree-grid')
                .ntsTreeComponent({
                    isShowAlreadySet: true,
                    isMultipleUse: false,
                    isMultiSelect: false,
                    startMode: 1,
                    selectedId: vm.otherParams.workplaceId,
                    baseDate: ko.observable(new Date()),
                    selectType: 1,
                    isShowSelectButton: true,
                    isDialog: window !== window.top,
                    alreadySettingList: ko.observableArray([]),
                    maxRows: 10,
                    tabindex: 1,
                    systemType: 2
                });
        }

        processDialog() {
            const vm = this;

            if (vm.isDialog()) {
                vm.$window.close()
            } else {
                vm.$window.modal('/view/kcp/013/shared/index.xhtml');
            }
        }
    }


    interface WorkTimeModel {
        selected: KnockoutObservable<string>;
        dataSources: KnockoutObservableArray<k.WorkTimeModel>;
    }

    interface OtherWorkTimeParam {
        workplaceId: KnockoutObservable<string>;
        filter: KnockoutObservable<boolean>;
        disabled: KnockoutObservable<boolean>;
        showMode: KnockoutObservable<k.SHOW_MODE>;
        width: KnockoutObservable<number>;
    }

    const view = `<div data-bind="
        kcp013: $vm.workTime.selected,
        workplace-id: $vm.otherParams.workplaceId,
        dataSources: $vm.workTime.dataSources,
        filter: $vm.otherParams.filter,
        disabled: $vm.otherParams.disabled,
        show-mode: $vm.otherParams.showMode,
        tabindex: 0,
        width: $vm.otherParams.width,
    "></div>`;

    const vm = `import k = nts.uk.ui.at.kcp013.shared;

    @bean()
    export class ViewModel extends ko.ViewModel {
        workTime: WorkTimeModel = {
            selected: ko.observable(''),
            dataSources: ko.observableArray([])
        };

        otherParams: OtherWorkTimeParam = {
            workplaceId: ko.observable(''),
            disabled: ko.observable(false),
            filter: ko.observable(false),
            showMode: ko.observable(k.SHOW_MODE.NOT_SET),
            width: ko.observable(450)
        };

        currentWorkTime: KnockoutObservable<k.WorkTimeModel | null> = ko.observable(null);

        public created() {
            const vm = this;
            const { workTime } = vm;

            workTime.selected
                .subscribe(c => {
                    const ds = ko.unwrap(workTime.dataSources);

                    const exist = _.find(ds, f => f.code === c);

                    if (!exist) {
                        vm.currentWorkTime(null);
                    } else {
                        vm.currentWorkTime(exist);
                    }
                });
        }
    }`
}