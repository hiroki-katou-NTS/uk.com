module nts.uk.at.view.kmk008.b {
	export module service {

		var paths: any = {
			// getData: "at/record/agreementUnitSetting/getAgreementUnitSetting",
			getData: "screen/at/kmk008/b/get",
		};

		export function getData(): JQueryPromise<any> {
			let dfd = $.Deferred();
			//return nts.uk.request.ajax(paths.getData);
			let dummy: AgreementTimeOfCompanyDto = {
				overMaxTimes: 1,
				limitOneMonth: 1,
				errorOneMonth: 1,
				alarmOneMonth: 1,
				limitTwoMonths: 1,
				errorTwoMonths: 1,
				alarmTwoMonths: 1,
				errorOneYear: 1,
				alarmOneYear: 1,
				limitOneYear: 1,
				errorTwoYear: 1,
				alarmTwoYear: 1,
				errorMonthAverage: 1,
				alarmMonthAverage: 1
			};
			dfd.resolve(dummy);
			return dfd.promise();
		}

		export interface AgreementTimeOfCompanyDto {
			overMaxTimes: number;
			limitOneMonth: number;
			errorOneMonth: number;
			alarmOneMonth: number;
			limitTwoMonths: number;
			errorTwoMonths: number;
			alarmTwoMonths: number;
			errorOneYear: number;
			alarmOneYear: number;
			limitOneYear: number;
			errorTwoYear: number;
			alarmTwoYear: number;
			errorMonthAverage: number;
			alarmMonthAverage: number;
		}
	}
}
