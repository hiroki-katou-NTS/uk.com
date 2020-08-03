/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kmr001.d {
	const API = {
		SETTING: 'at/record/stamp/management/personal/startPage',
		HIGHTLIGHT: 'at/record/stamp/management/personal/stamp/getHighlightSetting'
	};

	const PATH = {
	    REDIRECT : '/view/ccg/008/a/index.xhtml'
    }

	@bean()
	export class KMR001DViewModel extends ko.ViewModel {
		tabs: KnockoutObservableArray<any> = ko.observableArray([]);
		stampToSuppress: KnockoutObservable<any> = ko.observable({});
        itemList: KnockoutObservableArray<ItemModel>;
        itemName: KnockoutObservable<string>;
        currentCode: KnockoutObservable<number>
        selectedCode: KnockoutObservable<string>;
        selectedCodes: KnockoutObservableArray<string>;
        isEnable: KnockoutObservable<boolean>;
        date: KnockoutObservable<string>;
        yearMonth: KnockoutObservable<number>;

        constructor() {
        	super();
            var vm = this;
            vm.itemList = ko.observableArray([
                new ItemModel('2010/01/01', '9999/12/31'),
                new ItemModel('2000/01/01', '2009/12/31')
            ]);
            vm.itemName = ko.observable('');
            vm.currentCode = ko.observable(3);
            vm.selectedCode = ko.observable(null)
            vm.isEnable = ko.observable(true);
            vm.selectedCodes = ko.observableArray([]);

            $('#list-box').on('selectionChanging', function(event) {
                console.log('Selecting value:' + (<any>event.originalEvent).detail);
            })
            $('#list-box').on('selectionChanged', function(event: any) {
                console.log('Selected value:' + (<any>event.originalEvent).detail)
            })

            vm.date = ko.observable('20000101');
            vm.yearMonth = ko.observable(200001);
        }

        deselectAll() {
            $('#list-box').ntsListBox('deselectAll');
        }

        selectAll() {
            $('#list-box').ntsListBox('selectAll');
        }

		created() {
			const vm = this;
			_.extend(window, { vm });
		}


        /**
         * event Decide Data
         */
        public decideData(): void {

        }

        /**
         * event close dialog
         */
        cancel(){
            nts.uk.ui.windows.close();
        }
	}

    class ItemModel {
        from: string;
        to: string
        constructor(from: string, to: string) {
			this.from = from;
			this.to = to;
        }
    }
}
