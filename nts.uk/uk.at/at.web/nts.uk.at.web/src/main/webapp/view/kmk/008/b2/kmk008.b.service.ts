module nts.uk.at.view.kmk008.b {
    export module service {
        export class Service {
            paths = {
                getAgreementTimeOfCompany: "at/record/agreementTimeOfCompany/getAgreementTimeOfCompany/{0}",
                addAgreementTimeOfCompany: "at/record/agreementTimeOfCompany/addAgreementTimeOfCompany",
                updateAgreementTimeOfCompany: "at/record/agreementTimeOfCompany/updateAgreementTimeOfCompany",
				GET_B: "screen/at/kmk008/b/get",
				saveCpnAgrTime: 'monthly/estimatedtime/company/add',
            }
            
            constructor() {
                
            }
            
            getAgreementTimeOfCompany(laborSystemAtr: number): JQueryPromise<any> {
                let _path = nts.uk.text.format(this.paths.getAgreementTimeOfCompany, laborSystemAtr);
                return nts.uk.request.ajax("at", _path);
            };

			saveCpnAgrTime(data: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", 'monthly/estimatedtime/company/add', data);
            };

			addAgreementTimeOfCompany(UpdateInsertTimeOfCompanyModel: any): JQueryPromise<any> {
				return nts.uk.request.ajax("at", this.paths.addAgreementTimeOfCompany, UpdateInsertTimeOfCompanyModel);
			};
                
            updateAgreementTimeOfCompany(UpdateInsertTimeOfCompanyModel: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.updateAgreementTimeOfCompany, UpdateInsertTimeOfCompanyModel);
            };

			 getB(laborSystemAtr : number): JQueryPromise<any> {
			 	const vm = this;
				let dfd = $.Deferred();
				return nts.uk.request.ajax(vm.paths.GET_B, {laborSystemAtr: laborSystemAtr});

				// // let dummy: AgreementTimeOfCompanyDto = {
				// // 	overMaxTimes: 1,
				// // 	limitOneMonth: 1,
				// // 	errorOneMonth: 1,
				// // 	alarmOneMonth: 1,
				// // 	limitTwoMonths: 1,
				// // 	errorTwoMonths: 1,
				// // 	alarmTwoMonths: 1,
				// // 	errorOneYear: 1,
				// // 	alarmOneYear: 1,
				// // 	limitOneYear: 1,
				// // 	errorTwoYear: 1,
				// // 	alarmTwoYear: 1,
				// // 	errorMonthAverage: 1,
				// // 	alarmMonthAverage: 1
				// // };
				// dfd.resolve(dummy);
				// return dfd.promise();
			}
        }

		interface AgreementTimeOfCompanyDto {
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
