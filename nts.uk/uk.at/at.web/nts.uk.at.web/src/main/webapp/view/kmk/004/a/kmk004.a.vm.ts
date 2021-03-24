/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.a {

	const API = { URL_PROGRAM_NAME: 'sys/portal/standardmenu/findProgramName/' }

	@bean()
	export class ViewModel extends ko.ViewModel {
		public flexWorkManaging: KnockoutObservable<boolean> = ko.observable(true);
		public useDeformedLabor: KnockoutObservable<boolean> = ko.observable(true);

		create() {
			const vm = this;

		}

		mounted() {
			const vm = this;

			vm.$blockui('invisible')
				.then(() => vm.$ajax('screen/at/kmk004/viewA/init'))
				.then((data: any) => {
					vm.flexWorkManaging(data.flexWorkManaging);
					vm.useDeformedLabor(data.useDeformedLabor);
				})
				.then(() => vm.$blockui('clear'));
		}

		openDialog() {
			const vm = this;
			vm.$window
				.modal('/view/kmk/004/s/index.xhtml');
		}

		openViewB() {
			const vm = this;
			vm.$jump('/view/kmk/004/b/index.xhtml');
		}

		openViewG() {
			const vm = this;
			vm.$jump('/view/kmk/004/g/index.xhtml');
		}

		openViewL() {
			const vm = this;
			vm.$jump('/view/kmk/004/l/index.xhtml');
		}



		print() {
			const vm = this;

			let params = {
				date: null,
				mode: 5
			};

			if (!nts.uk.ui.windows.getShared("CDL028_INPUT")) {
				nts.uk.ui.windows.setShared("CDL028_INPUT", params);
			}
			nts.uk.ui.windows.sub.modal("../../../../../nts.uk.com.web/view/cdl/028/a/index.xhtml").onClosed(function() {
				var result = nts.uk.ui.windows.getShared('CDL028_A_PARAMS');
				if (result.status) {
						let startDate = moment.utc(result.startDateFiscalYear, "YYYY/MM/DD");
						let endDate = moment.utc(result.endDateFiscalYear, "YYYY/MM/DD");
						vm.$blockui('grayout');
						nts.uk.request.exportFile('/masterlist/report/print', { domainId: "SetWorkingHoursAndDays", domainType: "KMK004 法定労働時間の登録", languageId: 'ja', reportType: 0, mode: 4, startDate: startDate, endDate: endDate }).done(function() {
						}).fail(function(error) {
							vm.$dialog.error({ messageId: error.messageId });
						}).always(function() {
							vm.$blockui('clear')
						});

				}
			});
		}
	}
}
