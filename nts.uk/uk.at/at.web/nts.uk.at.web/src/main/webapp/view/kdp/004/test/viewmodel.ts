/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />


@bean()
class KDP004TestViewModel extends ko.ViewModel {
	retry = 0;
	public openKDP004G() {
		let self = this;
		self.retry = Math.floor(Math.random() * 4);
		console.log(self.retry);
		self.$window.storage('ModelGParam', { retry: self.retry, errorMessage: 'deo co loi gi nhung thich show co duoc khong' });
		nts.uk.ui.windows.sub.modal('/view/kdp/004/g/index.xhtml');
	}
}