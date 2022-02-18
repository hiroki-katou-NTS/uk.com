
/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.com.view.cmm018.r.viewmodel {

	const API = {
		checkBootMode: 'screen/approvermanagement/workroot/check-boot-mode',
		changeOperationMode: 'screen/approvermanagement/workroot/change-operation-mode',
	};

	@bean()
	export class Cmm018RViewModel extends ko.ViewModel {

		row1: any[] = [];
		processMemo: KnockoutObservable<string> = ko.observable('');
		optionTextarea = { resizeable: false, width: '600', textalign: 'left' };

		created() {
			const vm = this;
			vm.row1 = [{ id: 0, common: vm.$i18n('CMM018_226'), level: 1}];
		}

		mounted() {
			const vm = this;
			vm.initGrid1();
			vm.initGrid2();
		}

		initGrid1() {
			const vm = this;
			const comboItems =  [
				{ code: 1, name: vm.$i18n('CMM018_228') },
				{ code: 2, name: vm.$i18n('CMM018_229') },
				{ code: 3, name: vm.$i18n('CMM018_230') },
				{ code: 4, name: vm.$i18n('CMM018_231') },
				{ code: 5, name: vm.$i18n('CMM018_232') },
			];
			$("#grid1").ntsGrid({
				dataSource: vm.row1,
				primaryKey: 'id',
				height: 55,
				virtualization: true,
				virtualizationMode: 'continuous',
				hidePrimaryKey: true,
				columns: [
					{ headerText: '', key: 'id' },
					{ headerText: '', key: 'common', width: '180px',  },
					{
						headerText: vm.$i18n('CMM018_225'), key: 'level', dataType: 'number',
					 	width: '160px', ntsControl: 'Combobox', headerCssClass: 'text-center', columnCssClass: 'full-height'
					},
				],
				features: [],
				ntsControls: [{
					name: 'Combobox',
					options: comboItems,
					optionsValue: 'code',
					optionsText: 'name',
					columns: [{ prop: 'name' }],
					controlType: 'ComboBox',
					enable: true,
				}],
			});
		}

		initGrid2() {
			const vm = this;
			$("#grid2").ntsGrid({
				dataSource: vm.row1,
				primaryKey: 'id',
				height: 450,
				virtualization: true,
				virtualizationMode: 'continuous',
				hidePrimaryKey: true,
				columns: [
					{ headerText: '', key: 'id' },
					{ headerText: '', key: 'common', width: '180px',  },
					{
						headerText: vm.$i18n('CMM018_225'), key: 'level', dataType: 'number',
					 	width: '160px', ntsControl: 'Checkbox', headerCssClass: 'text-center', columnCssClass: 'text-center'
					},
				],
				features: [],
				ntsControls: [{
					name: 'Checkbox',
					options: { value: 1, text: '' },
					optionsValue: 'value',
					optionsText: 'text',
					controlType: 'CheckBox',
					enable: true
				}],
			});
		}

	}

	export const SystemAtr = {
		EMPLOYMENT: 0,
		HUMAN_RESOURSE: 1
	}
	export const MODE_SYSTEM = 'SYSTEM_MODE';

	export enum OperationMode {
		/** 就業担当者が行う */
		PERSON_IN_CHARGE = 0,
		/** 上長・社員が行う */
		SUPERIORS_EMPLOYEE = 1,
	}
}
