/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

interface Kdp003FParams {
	baseDate?: Date;
	multiSelect?: boolean;
}

interface Kdp003FReturn {
	selectedId: string | string[];
}

@bean()
class Kdp003kViewModel extends ko.ViewModel {
	selectedId!: KnockoutObservable<string> | KnockoutObservableArray<string>;

	constructor(private params?: Kdp003FParams) {
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