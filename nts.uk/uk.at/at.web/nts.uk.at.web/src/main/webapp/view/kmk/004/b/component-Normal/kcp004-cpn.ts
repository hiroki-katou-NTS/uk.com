/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.b {
    const template = `
		<div id="tree-grid">
		</div>
	`;

    interface Params {
    }

    @component({
        name: 'kcp004',
        template
    })

    export class KCP004VM extends ko.ViewModel {

        public selectedId: KnockoutObservable<string> = ko.observable('');
        public baseDate: KnockoutObservable<Date> = ko.observable(new Date('01/01/2020'));
        public alreadySettingList: KnockoutObservableArray<any> = ko.observableArray([]);

        created(params: Params) {
            const vm = this;

            $('#tree-grid')
                .ntsTreeComponent({

                    isShowAlreadySet: true,
						isMultipleUse: false,
						isMultiSelect: true,
						startMode: 0,
						selectedId: vm.selectedId,
						baseDate: vm.baseDate,
						selectType: 3,
						isShowSelectButton: true,
						isDialog: false,
						alreadySettingList: vm.alreadySettingList,
						maxRows: 12,
						tabindex: 1,
						systemType : 2

                    // returnDataFromLcp004: function (data: any) {
                    //     console.log(data);
                    // }
                });
        }
    }
}