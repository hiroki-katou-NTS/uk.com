/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.com.view.cmf001.b {
	
	@bean()
	class ViewModel extends ko.ViewModel {
    dateValue: KnockoutObservable<ImportInfo> = ko.observable(
			new ImportInfo({
      code: '001',
      name: 'NAME',
      group: 1,
      mode: 1,
      itemNameRow: 1,
      importStartRow: 2
    }));
		constructor(data: any) {
			super();
      const vm = this;
		}

		mounted() {
			const vm = this;
		}
	}
	class ImportInfo {
    code: string;
    name: string;
    group: number;
    mode: number;
    itemNameRow: number;
    importStartRow: number;

		constructor(init?: Partial<ImportInfo>) {
      $.extend(this, init);
    }
  }
}