/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />


@bean()
class KDP004TestViewModel extends ko.ViewModel {

	public openKDP004G() {
		nts.uk.ui.windows.sub.modal('/view/kdp/004/g/index.xhtml').onClosed(function(): any {

		});
	}
}