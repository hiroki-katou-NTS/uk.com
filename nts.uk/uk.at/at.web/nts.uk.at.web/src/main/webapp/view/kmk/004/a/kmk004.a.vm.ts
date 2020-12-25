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
					vm.$blockui('grayout');
					vm.$ajax('com', API.URL_PROGRAM_NAME + 'KMK004' + '/' + 'A').done(function(res) {
						let domainType = "KMK004";
						res = res.split(" ");
						if (res.length > 1) {
							res.shift();
							domainType = domainType + res.join(" ");
						}
						let startDate = moment.utc(result.startDateFiscalYear, "YYYY/MM/DD");
						let endDate = moment.utc(result.endDateFiscalYear, "YYYY/MM/DD");
						nts.uk.request.exportFile('/masterlist/report/print', { domainId: "SetWorkingHoursAndDays", domainType: domainType, languageId: 'ja', reportType: 0, mode: 4, startDate: startDate, endDate: endDate }).done(function() {
						}).fail(function(error) {
							vm.$dialog.error({ messageId: error.messageId });
						}).always(function() {
							vm.$blockui('clear')
						});

					}).always(function() {
						vm.$blockui('clear')
					});

				}
			});
		}
	}
}
