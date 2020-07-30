/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmp001.d {
	const KMP001D_API = {
		GET_START: 'screen/pointCardNumber/getStampCardDigit/',
		UPDATE_EDITTING: 'at/record/register-stamp-card/view-d/editting'
	};

	export interface ReturnData {
		length: number;
		paddingType: StampCardEditMethod;
	}

	@bean()
	export class ViewModel extends ko.ViewModel {

		paddingType: KnockoutObservable<StampCardEditMethod | null> = ko.observable(null);
		selectedLength: KnockoutObservable<string> = ko.observable('');

		paddingTypes!: PaddingType[];
		cardLengths!: StampCardLength[];

		created() {
			var vm = this;

			vm.paddingTypes = [
				{
					value: StampCardEditMethod.PreviousZero,
					label: 'KMP001_42'
				}, {
					value: StampCardEditMethod.AfterZero,
					label: 'KMP001_43'
				}, {
					value: StampCardEditMethod.PreviousSpace,
					label: 'KMP001_44'
				}, {
					value: StampCardEditMethod.AfterSpace,
					label: 'KMP001_45'
				}];

			vm.cardLengths = [
				new StampCardLength('KMP001_50'),
				new StampCardLength('KMP001_51'),
				new StampCardLength('KMP001_52'),
				new StampCardLength('KMP001_53'),
				new StampCardLength('KMP001_54'),
				new StampCardLength('KMP001_55'),
				new StampCardLength('KMP001_56'),
				new StampCardLength('KMP001_57'),
				new StampCardLength('KMP001_58'),
				new StampCardLength('KMP001_59'),
				new StampCardLength('KMP001_60'),
				new StampCardLength('KMP001_61'),
				new StampCardLength('KMP001_62'),
				new StampCardLength('KMP001_63'),
				new StampCardLength('KMP001_64'),
				new StampCardLength('KMP001_65'),
				new StampCardLength('KMP001_66'),
				new StampCardLength('KMP001_67'),
				new StampCardLength('KMP001_68'),
				new StampCardLength('KMP001_69')
			];
		}

		mounted() {
			const vm = this;
			vm.$ajax(KMP001D_API.GET_START)
				.then((data: any) => {
					vm.selectedLength(data.stampCardDigitNumber);

					vm.paddingType(data.stampCardEditMethod);
				});
		}

		closeDialog() {
			const vm = this;

			vm.$window.close();
		}

		addSetting() {
			const vm = this;
			const length = ko.unwrap(vm.selectedLength);
			const paddingType = ko.unwrap(vm.paddingType);
			const command = { digitsNumber: length, stampMethod: paddingType };
			
			vm.$ajax(KMP001D_API.UPDATE_EDITTING, command);
			vm.$window.close({ length, paddingType });
		
	}

	export enum StampCardEditMethod {

		// 前ゼロ
		PreviousZero = 1,

		// 後ゼロ
		AfterZero = 2,

		// 前スペース
		PreviousSpace = 3,

		// 後スペース
		AfterSpace = 4
	}

	interface PaddingType {
		value: StampCardEditMethod;
		label: 'KMP001_42' | 'KMP001_43' | 'KMP001_44' | 'KMP001_45';
	}

	class StampCardLength {
		value: number = -1;
		text: string = '';

		constructor(resource: string) {
			const mock = new ko.ViewModel();

			if (resource) {
				this.text = mock.$i18n(resource);
			}

			if (this.text.match(/^\d+$/)) {
				this.value = parseInt(this.text);
			}
		}
	}
}