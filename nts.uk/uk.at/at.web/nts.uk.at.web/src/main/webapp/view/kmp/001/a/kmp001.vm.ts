/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmp001 {
	export module a {
		@bean()
		export class ViewModel extends ko.ViewModel {
			/*tabs: KnockoutObservableArray<string> = ko.observableArray([]);*/

			mounted() {
				const vm = this;

				/*if (vm.$user.role.isInCharge.attendance) {
					vm.tabs(['KMP001_1', 'KMP001_2', 'KMP001_3']);
				} else {
					vm.tabs(['KMP001_1']);
				}*/
			}
		}
	}

	export interface IStampCardEdit {
		stampCardDigitNumber: number;
		stampCardEditMethod: number;
	}
	
	export class StampCardEdit {
		stampCardDigitNumber: KnockoutObservable<number> = ko.observable(0);
		stampCardEditMethod: KnockoutObservable<number> = ko.observable(0);
		
		public create(params?: IStampCardEdit) {
			const self = this;

			if (params) {
				self.update(params);
			}
		}

		public update(params?: IStampCardEdit) {
			const self = this;

			if (params) {
				self.stampCardDigitNumber(params.stampCardDigitNumber);
				self.stampCardEditMethod(params.stampCardEditMethod);
			}
		}
	}

	export interface IModel {
		code: string;
		affiliationId: string;
		birthDay: Date;
		businessName: string;
		employeeCode: string;
		employeeId: string;
		entryDate: Date;
		gender: number;
		pid: string;
		retiredDate: Date;
		workplaceId: string;
		workplaceName: string;
		stampCardDto: IStampCard[];
	}

	export class Model {
		code: KnockoutObservable<string> = ko.observable('');
		affiliationId: KnockoutObservable<string> = ko.observable('');
		birthDay: KnockoutObservable<Date | null> = ko.observable(null);
		businessName: KnockoutObservable<string> = ko.observable('');
		employeeCode: KnockoutObservable<string> = ko.observable('');
		employeeId: KnockoutObservable<string> = ko.observable('');
		entryDate: KnockoutObservable<Date | null> = ko.observable(null);
		gender: KnockoutObservable<number> = ko.observable(0);
		pid: KnockoutObservable<string> = ko.observable('');
		retiredDate: KnockoutObservable<Date | null> = ko.observable(null);
		stampCardDto: KnockoutObservableArray<StampCard> = ko.observableArray([]);

		workplaceId: KnockoutObservable<string> = ko.observable('');
		workplaceName: KnockoutObservable<string> = ko.observable('');

		selectedStampCardIndex: KnockoutObservable<number> = ko.observable(-1);
		public create(params?: IModel) {
			const self = this;

			if (params) {
				self.employeeId(params.employeeId);

				self.update(params);
			}
		}

		public update(params?: IModel) {
			const self = this;

			if (params) {
				self.birthDay(params.birthDay);
				self.businessName(params.businessName);
				self.employeeCode(params.employeeCode);
				self.entryDate(params.entryDate);
				self.gender(params.gender);
				self.pid(params.pid);
				self.retiredDate(params.retiredDate);
				self.workplaceId(params.workplaceId);
				self.workplaceName(params.workplaceName);

				self.stampCardDto(params.stampCardDto.map(m => new StampCard(m)));

				if (ko.unwrap(self.selectedStampCardIndex) === 0) {
					self.selectedStampCardIndex.valueHasMutated();
				} else {
					self.selectedStampCardIndex(0);
				}

				const vm = new ko.ViewModel();

				vm.$nextTick(() => {
					const $grid = $('#stampcard-list');

					if ($grid.length && $grid.data('igGrid')) {
						$grid.igGridSelection('clearSelection');
						
						// Select first row
						// $grid.igGridSelection('selectRow', 0);
					}
				});
			}
		}

		public clear() {
			const self = this;

			self.code('');
			self.affiliationId('');
			self.birthDay(null);
			self.businessName('');
			self.employeeCode('');
			self.employeeId('');
			self.entryDate(null);
			self.gender(-1);
			self.pid('');
			self.retiredDate(null);
			self.stampCardDto([]);
		}

		public addNewStampCard() {
			const model = this;

			model.stampCardDto.unshift(new StampCard({ checked: false, stampCardId: "", stampNumber: "" }));
			model.selectedStampCardIndex(model.stampCardDto.length - 1);
		}
	}

	export interface IStampCard {
		stampCardId: string;
		defaultValue?: string;
		stampNumber: string;
		checked: boolean;
	}

	export class StampCard {
		stampCardId: KnockoutObservable<string> = ko.observable('');
		defaultValue: string = '';
		stampNumber: KnockoutObservable<string> = ko.observable('');
		checked: KnockoutObservable<boolean> = ko.observable(false);

		constructor(params?: IStampCard) {
			const model = this;

			model.stampCardId(params.stampCardId);
			model.defaultValue = params.stampNumber;
			model.update(params);
		}

		public update(params?: IStampCard) {
			const model = this;

			if (params) {
				//model.stampCardId(params.stampCardId);
				model.stampNumber(params.stampNumber);
				model.checked(params.checked);
			}
		}
	}

	export interface ISetting {
		code: string;
		isAlreadySetting: boolean;
	}

	export class Setting {
		code: KnockoutObservable<string> = ko.observable('');
		isAlreadySetting: KnockoutObservable<boolean> = ko.observable(true);

		constructor(params?: ISetting) {
			const seft = this;

			if (params) {
				seft.code(params.code);
				seft.update(params);
			}
		}

		update(params: ISetting) {
			const seft = this;

			seft.isAlreadySetting(params.isAlreadySetting);
		}
	}

	export interface IEmployeeId {
		employee: string;
	}

	export type SELECT = 'select';
}