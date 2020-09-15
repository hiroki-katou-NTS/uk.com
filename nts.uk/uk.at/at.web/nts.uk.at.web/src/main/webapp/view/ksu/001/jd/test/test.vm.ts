module test.viewmodel {

	import setShared = nts.uk.ui.windows.setShared;
	import IDataTransfer = nts.uk.at.view.kdl044.a.viewmodel.IDataTransfer;
	import getText = nts.uk.resource.getText;
	import getShared = nts.uk.ui.windows.getShared;

	export class ScreenModel {
	

		constructor() {
			let self = this;
		}

		startPage(): JQueryPromise<any> {
			let self = this;
			let dfd = $.Deferred();

			dfd.resolve();

			return dfd.promise();
		}
		
		openDialog() {
			let self = this;
			nts.uk.ui.block.invisible();
			setShared('dataForJD', {target: 2, targetID:"", pageNumber: 1});
			nts.uk.ui.windows.sub.modal("/view/ksu/001/jd/index.xhtml", { dialogClass: "no-close" })
				.onClosed(() => {
//					let isCancel = getShared('kdl044_IsCancel') != null ? getShared('kdl044_IsCancel') : true;
//					if (!isCancel) {
//						let returnedData = getShared('kdl044ShifutoCodes');
//						if (self.isMultiSelect()) {
//							self.selectedCodes([]);
//							self.selectedCodes(returnedData);
//						} else {
//							self.selectedCode(returnedData);
//						}
//					}
					nts.uk.ui.block.clear();
				});
		}
	}

}