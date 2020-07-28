/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

const KMP001D_API = {
	GET_START: 'screen/pointCardNumber/getStampCardDigit/'
};

@bean()
class ViewModel extends ko.ViewModel {

	itemList: KnockoutObservableArray<any> = ko.observableArray([]);
	selectedCode: KnockoutObservable<number> = ko.observable(-1);
	selectedName: KnockoutObservable<string> = ko.observable('');
	ischeck1: KnockoutObservable<boolean> = ko.observable(false);
	ischeck2: KnockoutObservable<boolean> = ko.observable(false);
	ischeck3: KnockoutObservable<boolean> = ko.observable(false);

	created(params: any) {

		var vm = this;
		vm.itemList([{
			code: 1
		}, {
			code: 2
		}, {
			code: 3
		}, {
			code: 4
		}, {
			code: 5
		}, {
			code: 6
		}, {
			code: 7
		}, {
			code: 8
		}, {
			code: 9
		}, {
			code: 10
		}, {
			code: 11
		}, {
			code: 12
		}, {
			code: 13
		}, {
			code: 14
		}, {
			code: 15
		}, {
			code: 16
		}, {
			code: 17
		}, {
			code: 18
		}, {
			code: 19
		}, {
			code: 20
		}]);

		vm.selectedCode = ko.observable(1);

		vm.selectedCode
			.subscribe((c: number) => {
				
				vm.determined(ko.unwrap(vm.selectedCode));
				
			});
	}

	mounted() {
		const vm = this;

		vm.$ajax(KMP001D_API.GET_START)
			.then((data: any) => {
				vm.selectedCode(data.stampCardDigitNumber);
				vm.determined(data.stampCardDigitNumber);

				switch (data.stampCardEditMethod) {
					case 1:
						vm.ischeck1(true);
						break;
					case 2:
						vm.ischeck2(true);
						break;
					case 3:
						vm.ischeck3(true);
						break;
				}

			});
	}

	closeDialog() {
		const vm = this;
		vm.$window.close();
	}

	determined(code: number) {
		const vm = this;
		
		switch (code) {
			case 1: {
				vm.selectedName(vm.$i18n('KMP001_50'));
				break;
			}
			case 2: {
				vm.selectedName(vm.$i18n('KMP001_51'));
				break;
			}
			case 3: {
				vm.selectedName(vm.$i18n('KMP001_52'));
				break;
			}
			case 4: {
				vm.selectedName(vm.$i18n('KMP001_53'));
				break;
			}
			case 5: {
				vm.selectedName(vm.$i18n('KMP001_54'));
				break;
			}
			case 6: {
				vm.selectedName(vm.$i18n('KMP001_55'));
				break;
			}
			case 7: {
				vm.selectedName(vm.$i18n('KMP001_56'));
				break;
			}
			case 8: {
				vm.selectedName(vm.$i18n('KMP001_57'));
				break;
			}
			case 9: {
				vm.selectedName(vm.$i18n('KMP001_58'));
				break;
			}
			case 10: {
				vm.selectedName(vm.$i18n('KMP001_59'));
				break;
			}
			case 11: {
				vm.selectedName(vm.$i18n('KMP001_60'));
				break;
			}
			case 12: {
				vm.selectedName(vm.$i18n('KMP001_61'));
				break;
			}
			case 13: {
				vm.selectedName(vm.$i18n('KMP001_62'));
				break;
			}
			case 14: {
				vm.selectedName(vm.$i18n('KMP001_63'));
				break;
			}
			case 15: {
				vm.selectedName(vm.$i18n('KMP001_64'));
				break;
			}
			case 16: {
				vm.selectedName(vm.$i18n('KMP001_65'));
				break;
			}
			case 17: {
				vm.selectedName(vm.$i18n('KMP001_66'));
				break;
			}
			case 18: {
				vm.selectedName(vm.$i18n('KMP001_67'));
				break;
			}
			case 19: {
				vm.selectedName(vm.$i18n('KMP001_68'));
				break;
			}
			case 20: {
				vm.selectedName(vm.$i18n('KMP001_69'));
				break;
			}

		}
	}

}

/*interface IStampSetting {
	stampCardDigitNumber: number;
	stampCardEditMethod: number;
}

class StampSetting {
	stampCardDigitNumber: KnockoutObservable<number> = ko.observable(1);
	stampCardEditMethod: KnockoutObservable<number> = ko.observable(1);

	constructor(params?: IStampSetting) {
		const seft = this;

		if (params) {
			seft.stampCardDigitNumber(params.stampCardDigitNumber);
			seft.stampCardEditMethod(params.stampCardEditMethod);
		}
	}
}*/