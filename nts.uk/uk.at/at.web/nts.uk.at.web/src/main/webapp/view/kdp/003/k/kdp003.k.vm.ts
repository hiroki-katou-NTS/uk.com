/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kdp003.k {
	export interface Params {
		baseDate?: Date;
		multiSelect?: boolean;
	}

	export interface Return {
		selectedId: string | string[];
	}

	const WORKPLACES_STORAGE = 'WORKPLACES_STORAGE';

	@bean()
	export class ViewModel extends ko.ViewModel {
		selectedId!: KnockoutObservable<string> | KnockoutObservableArray<string>;
		dataStorage: IWorkPlaceInfo[] = [];

		constructor(private params?: Params) {
			super();
		}

		created() {
			const vm = this;
			const { params } = vm;

			if (!params || !params.multiSelect) {
				vm.selectedId = ko.observable('');
			} else {
				vm.selectedId = ko.observableArray([]);
			}
		}

		mounted() {
			const vm = this;
			const $tree = $(vm.$el).find('#tree-grid');
			const { params, selectedId } = vm;
			const isMultiSelect = !!params && !!params.multiSelect;
			const baseDate = ko.observable(params && params.baseDate || new Date());

			if (!isMultiSelect) {
				vm.$window.size(510, 470);
			} else {
				vm.$window.size(510, 530);
			}

			$tree
				.ntsTreeComponent({
					baseDate,
					selectedId,
					isMultiSelect,
					isDialog: true,
					isMultipleUse: true,
					isShowAlreadySet: false,
					isShowSelectButton: isMultiSelect,
					selectType: 4,
					systemType: 2,
					maxRows: 10
				} as any).done(() => {
					$tree.focusTreeGridComponent();
				});

		}

		pushData() {
			const vm = this;
			const selectedId: string | string[] = ko.toJS(vm.selectedId);

			let lwps = $('#tree-grid').getDataList();

			_.forEach(lwps, ((values) => {
				vm.pushDataStorage(values);
				if (values.children.length > 0) {
					_.forEach(values.children, ((values1) => {
						vm.pushDataStorage(values1);
						if (values1.children.length > 0) {
							_.forEach(values1.children, ((values2) => {
								vm.pushDataStorage(values2);
								if (values2.children.length > 0) {
									_.forEach(values2.children, ((values3) => {
										vm.pushDataStorage(values3);
										if (values3.children.length > 0) {
											_.forEach(values3.children, ((values4) => {
												vm.pushDataStorage(values4);
											}));
										}
									}));
								}
							}));
						}
					}));
				}
			}));

			vm.$window.storage(WORKPLACES_STORAGE, vm.dataStorage);

			if (!selectedId || !selectedId.length) {
				vm.$dialog.error({ messageId: 'Msg_643' });
			} else {
				const selectedId1:  string | string[] = [];

				_.forEach(vm.dataStorage, ((values) => {

					const exist = _.find(selectedId, ((value1) => {
						return value1 === values.id;
					}));

					if (exist) {
						selectedId1.push(exist);
					}
				}));

				selectedId = selectedId1;

				vm.$window.close({ selectedId });
			}
		}

		pushDataStorage(data: any) {
			const vm = this;
			if (data) {
				const input = {
					code: data.code,
					hierarchyCode: data.hierarchyCode,
					id: data.id,
					name: data.name,
					workplaceDisplayName: data.workplaceDisplayName,
					workplaceGeneric: data.workplaceGeneric
				} as IWorkPlaceInfo;

				vm.dataStorage.push(input);
			}
		}

		closeDialog() {
			const vm = this;

			vm.$window.close();
		}


	}

	interface IWorkPlaceInfo {
		code: string,
		hierarchyCode: string,
		id: string,
		name: string,
		workplaceDisplayName: string,
		workplaceGeneric: string
	}
}