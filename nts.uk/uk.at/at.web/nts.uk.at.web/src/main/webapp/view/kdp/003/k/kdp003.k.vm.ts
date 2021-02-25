/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kdp003.k {
	export interface Params {
		baseDate?: Date;
		multiSelect?: boolean;
	}

	export interface Return {
		selectedId: string | string[];
	}

	@bean()
	export class ViewModel extends ko.ViewModel {
		selectedId!: KnockoutObservable<string> | KnockoutObservableArray<string>;

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

			if (!selectedId || !selectedId.length) {
				vm.$dialog.error({ messageId: 'Msg_643' });
			} else {
				vm.$window.close({ selectedId });
			}
		}

		closeDialog() {
			const vm = this;

			vm.$window.close();
		}
	}
}