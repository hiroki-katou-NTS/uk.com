/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kdp003.m {
    const KDP003_SAVE_DATA = 'loginKDP003';

    const API = {
        WORKPLACE_INFO: "screen/at/kdp003/workplace-info"
    };

    @handler({
        bindingName: 'firstFocus'
    })
    export class FocusButtonFirstBindingHandler implements KnockoutBindingHandler {
        init(element: HTMLElement,
            valueAccessor: () => KnockoutObservableArray<any>,
            allBindingsAccessor: KnockoutAllBindingsAccessor,
            viewModel: any,
            bindingContext: KnockoutBindingContext) {
            let focused: boolean = false;

            const accessor = valueAccessor();

            ko.computed({
                read: () => {
                    const buttons = ko.unwrap(accessor);

                    if (focused === false && buttons.length) {
                        ko.tasks
                            .schedule(() => {
                                focused = true;
                                $(element).find('button:first').focus();
                            });
                    }
                },
                disposeWhenNodeIsRemoved: element
            });
        }
    }

    @bean()
    export class ViewModel extends ko.ViewModel {

        model: KnockoutObservableArray<WorkPlaceInfo> = ko.observableArray([]);

        buttonDisplays!: KnockoutComputed<WorkPlaceInfo[][]>;

        position: KnockoutObservable<number> = ko.observable(0);

        check_back: KnockoutObservable<boolean> = ko.observable(false);

        check_next: KnockoutObservable<boolean> = ko.observable(true);

        workplace: WorkPlaceInfo[][] = [];

        constructor() {
            super();

            const vm = this;

            vm.buttonDisplays = ko.computed({
                read: () => {
                    const buttons = ko.unwrap<WorkPlaceInfo[]>(vm.model);

                    return _.chunk(buttons, 3);
                }
            })
        }

        created() {
            const vm = this;

            vm.reloadData();

        }

        mounted() {
            const vm = this;

            vm.position
                .subscribe(() => {
                    if (vm.workplace.length > 0) {
                        if (ko.unwrap(vm.position) <= vm.workplace.length - 1) {
                            vm.reload(ko.unwrap(vm.position));
                        }
                        if (ko.unwrap(vm.position) == 0) {
                            vm.check_back(false);
                        } else {
                            vm.check_back(true);
                        }
                        if (ko.unwrap(vm.position) < vm.workplace.length - 1) {
                            vm.check_next(true);
                        } else {
                            vm.check_next(false);
                        }
                    }
                });

        }

        // Reload data in storage when the data storage change
        reloadData() {
            const vm = this;
            // get data in storage
            vm.$blockui('invisible')
                .then(() => {
                    vm.$window
                        .storage(KDP003_SAVE_DATA)
                        .then((data: Data) => {
                            const param = { sid: data.SID, workPlaceIds: data.WKPID };
                            vm.$ajax(API.WORKPLACE_INFO, param)
                                .then((workPlace: workPlace) => {
                                    vm.workplace = _.chunk(workPlace.workPlaceInfo, 9);
                                    vm.position.valueHasMutated();
                                })
                        });
                })
                .always(() => {
                    vm.$blockui('clear');
                })
        }

        // Reload Data in view
        reload(index: number) {
            const vm = this;
            vm.$blockui('invisible')
                .then(() => {
                    vm.model(vm.workplace[index])
                })
                .always(() => {
                    vm.$blockui('clear');
                });
        }

        back() {
            const vm = this;
            vm.position(ko.unwrap(vm.position) - 1);
        }

        next() {
            const vm = this;
            vm.position(ko.unwrap(vm.position) + 1);
        }

        seleceted(workplaceId: string) {
            const vm = this;
            vm.$window.close(workplaceId);
        }

        close() {
            const vm = this;
            vm.$window.close();
        }
    }

    interface Data {
        CCD: string;
        CID: string;
        PWD: string;
        SCD: string;
        SID: string;
        WKLOC_CD: string;
        WKPID: [string];
    }

    interface workPlace {
        sWkpHistExport: SWkpHistExport;
        workPlaceInfo: WorkPlaceInfo[];
    }

    interface SWkpHistExport {
        wkpDisplayName: string;
        workplaceCode: string;
        workplaceId: string;
        workplaceName: string;
    }

    interface WorkPlaceInfo {
        displayName: string;
        externalCode: null | string;
        genericName: string;
        hierarchyCode: string;
        workplaceCode: string;
        workplaceId: string;
        s: string;
    }
}