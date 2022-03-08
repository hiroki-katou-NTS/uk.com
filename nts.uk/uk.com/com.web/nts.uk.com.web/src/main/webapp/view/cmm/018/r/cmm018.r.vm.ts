/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.com.view.cmm018.r.viewmodel {

	const API = {
		init: 'screen/approvermanagement/workroot/init-registation-screen-setting',
		register: 'screen/approvermanagement/workroot/registration-screen-settings',
	};

	@bean()
	export class Cmm018RViewModel extends ko.ViewModel {

		levelData: any[] = [
			{ level: 1, name: this.$i18n('CMM018_228') },
			{ level: 2, name: this.$i18n('CMM018_229') },
			{ level: 3, name: this.$i18n('CMM018_230') },
			{ level: 4, name: this.$i18n('CMM018_231') },
			{ level: 5, name: this.$i18n('CMM018_232') },
		];

		level: KnockoutObservable<number> = ko.observable(1);
		selectionData: SelectionData[] = [];
		firstItemName: KnockoutObservable<string> = ko.observable('');
		secondItemName: KnockoutObservable<string> = ko.observable('');
		thirdItemName: KnockoutObservable<string> = ko.observable('');
		fourthItemName: KnockoutObservable<string> = ko.observable('');
		fifthItemName: KnockoutObservable<string> = ko.observable('');
		processMemo: KnockoutObservable<string> = ko.observable('');
		attentionMemo: KnockoutObservable<string> = ko.observable('');
		
		levelBackup: number = 1;
		selectionDataBackup: SelectionData[] = [];
		equalSelectionData: KnockoutObservable<boolean> = ko.observable(false);
		firstItemNameBackup: string = '';
		secondItemNameBackup: string = '';
		thirdItemNameBackup: string = '';
		fourthItemNameBackup: string = '';
		fifthItemNameBackup: string = '';
		processMemoBackup: string = '';
		attentionMemoBackup: string = '';

		canJumpToCMM030: KnockoutComputed<boolean>;
		
		created() {
			const vm = this;
			vm.canJumpToCMM030 = ko.computed(() => {
				return vm.level() === vm.levelBackup &&
				vm.equalSelectionData() &&
				vm.firstItemName() === vm.firstItemNameBackup &&
				vm.secondItemName() === vm.secondItemNameBackup &&
				vm.thirdItemName() === vm.thirdItemNameBackup &&
				vm.fourthItemName() === vm.fourthItemNameBackup &&
				vm.fifthItemName() === vm.fifthItemNameBackup &&
				vm.processMemo() === vm.processMemoBackup &&
				vm.attentionMemo() === vm.attentionMemoBackup
			});
		} 

		mounted() {
			const vm = this;
			vm.initData().then(() => {
				vm.initLevelSubcriber();
				vm.initGrid2();
			});
			$('#process-memo').focus();
		}

		initData(): JQueryPromise<any> {
			const vm = this;
			const dfd = $.Deferred();
			vm
				.$blockui('grayout')
				.then(() => vm.$ajax('com', API.init))
				.then((response: InitData) => {
					const { appUseAtrs, setting } = response;
					const listAppType: { value: number, name: string }[] = __viewContext.enums.ApplicationType;
					let settingTypeUseds: SettingTypeUsed[] = [];

					if (setting && setting.approvalLevelNo) {
						vm.levelBackup = setting.approvalLevelNo;
						vm.level(setting.approvalLevelNo);

						settingTypeUseds = setting.settingTypeUseds;
						vm.firstItemName(setting.approverSettingScreenInfor.firstItemName || '');
						vm.secondItemName(setting.approverSettingScreenInfor.secondItemName || '');
						vm.thirdItemName(setting.approverSettingScreenInfor.thirdItemName || '');
						vm.fourthItemName(setting.approverSettingScreenInfor.fourthItemName || '');
						vm.fifthItemName(setting.approverSettingScreenInfor.fifthItemName || '');
						vm.processMemo(setting.approverSettingScreenInfor.processMemo || '');
						vm.attentionMemo(setting.approverSettingScreenInfor.attentionMemo || '');

						vm.firstItemNameBackup = setting.approverSettingScreenInfor.firstItemName || '';
						vm.secondItemNameBackup = setting.approverSettingScreenInfor.secondItemName || '';
						vm.thirdItemNameBackup = setting.approverSettingScreenInfor.thirdItemName || '';
						vm.fourthItemNameBackup = setting.approverSettingScreenInfor.fourthItemName || '';
						vm.fifthItemNameBackup = setting.approverSettingScreenInfor.fifthItemName || '';
						vm.processMemoBackup = setting.approverSettingScreenInfor.processMemo || '';
						vm.attentionMemoBackup = setting.approverSettingScreenInfor.attentionMemo || '';
					}

					if (appUseAtrs) {
						_.forEach(appUseAtrs, attr => {
							const { appType, useAtr } = attr;
							if (!useAtr) return;
							const appName = _.find(listAppType, app => app.value === appType)?.name;
							const notUseAtr = _.find(
								settingTypeUseds,
								setting => setting.applicationType === appType && setting.employmentRootAtr === EmploymentRootAtr.APPLICATION
							)?.notUseAtr || NotUseAtr.NOT_DO;

							vm.selectionData.push(new SelectionData({
								name: appName,
								applicationType: appType,
								employmentRootAtr: EmploymentRootAtr.APPLICATION,
								notUseAtr: notUseAtr === NotUseAtr.DO,
							}));
						});
					}

					const useDaily = _.find(
						settingTypeUseds,
						setting => setting.confirmRootType === ConfirmationRootType.DAILY_CONFIRMATION && setting.employmentRootAtr === EmploymentRootAtr.CONFIRMATION
					)?.notUseAtr || NotUseAtr.NOT_DO;
					const useMonthly = _.find(
						settingTypeUseds,
						setting => setting.confirmRootType === ConfirmationRootType.MONTHLY_CONFIRMATION && setting.employmentRootAtr === EmploymentRootAtr.APPLICATION
					)?.notUseAtr || NotUseAtr.NOT_DO;

					vm.selectionData.push(
						new SelectionData({
							name: vm.$i18n('CMM018_255'),
							confirmRootType: ConfirmationRootType.DAILY_CONFIRMATION,
							employmentRootAtr: EmploymentRootAtr.CONFIRMATION,
							notUseAtr: useDaily === NotUseAtr.DO,
						}),
						new SelectionData({
							name: vm.$i18n('CMM018_256'),
							confirmRootType: ConfirmationRootType.MONTHLY_CONFIRMATION,
							employmentRootAtr: EmploymentRootAtr.CONFIRMATION,
							notUseAtr: useMonthly === NotUseAtr.DO,
						}),
					);

					vm.selectionDataBackup = _.cloneDeep(vm.selectionData);
					vm.equalSelectionData(true);
					dfd.resolve();
				})
				.fail(() => dfd.reject())
				.always(() => vm.$blockui('clear'));

				return dfd.promise();
		}

		initLevelSubcriber() {
			const vm = this;
			vm.level.subscribe(() => {
				const validateElement: string[] = vm.getElementValidate();
				vm.$errors('clear').then(() => vm.$validate(validateElement));
			});
		}

		initGrid2() {
			const vm = this;
			$('#grid2').ntsGrid({
				dataSource: vm.selectionData,
				primaryKey: 'id',
				height: 405,
				virtualization: true,
				virtualizationMode: 'continuous',
				hidePrimaryKey: true,
				columns: [
					{ headerText: '', key: 'id', hidden: true },
					{ headerText: '', key: 'name', width: '180px' },
					{
						headerText: vm.$i18n('CMM018_234'), key: 'notUseAtr', dataType: 'boolean',
					 	width: '160px', ntsControl: 'Checkbox', headerCssClass: 'text-center', columnCssClass: 'text-center'
					},
				],
				features: [{
					name: 'Selection',
					mode: 'row',
					multipleSelection: false
				}],
				ntsControls: [{
					name: 'Checkbox',
					options: { value: true, text: '' },
					optionsValue: 'value',
					optionsText: 'text',
					controlType: 'CheckBox',
				}],
			});

			$('#grid2').on('ntsgridcontrolvaluechanged', () => {
				const equal = _.isEqual(vm.selectionData, vm.selectionDataBackup);
				vm.equalSelectionData(equal);
			});

		}

		register() {
			const vm = this;
			const validateElement: string[] = vm.getElementValidate();

			const commandHandler = () => {
				const command = vm.createCommand();
				vm
					.$blockui('grayout')
					.then(() => vm.$ajax('com', API.register, command))
					.then(() => vm.$dialog.info({ messageId: 'Msg_15' }))
					.then(() => {
						vm.levelBackup = _.cloneDeep(vm.level());
						vm.selectionDataBackup = _.cloneDeep(vm.selectionData());
						vm.firstItemNameBackup = _.cloneDeep(vm.firstItemName());
						vm.secondItemNameBackup = _.cloneDeep(vm.secondItemName());
						vm.thirdItemNameBackup = _.cloneDeep(vm.thirdItemName());
						vm.fourthItemNameBackup = _.cloneDeep(vm.fourthItemName());
						vm.fifthItemNameBackup = _.cloneDeep(vm.fifthItemName());
						vm.processMemoBackup = _.cloneDeep(vm.processMemo());
						vm.attentionMemoBackup = _.cloneDeep(vm.attentionMemo());

						vm.level.valueHasMutated();
					})
					.always(() => vm.$blockui('clear'));
			};
			vm.$validate(validateElement).then((valid: boolean) => {
				if (!valid) return;
				const check = vm.checkForChanges();
				if (!check) {
					commandHandler();
					return;
				};

				vm.$dialog.confirm({ messageId: 'Msg_3310' }).then((result: 'yes' | 'no') => {
					if (result === 'no') return;
					commandHandler();
				});
			})
			.then(() => $('#process-memo').focus());
		}

		getElementValidate() {
			const vm = this;
			const level = ko.unwrap(vm.level);
			const result = [
				'#process-memo',
				'#first-name',
				'#second-name',
				'#third-name',
				'#fourth-name',
				'#fifth-name',
				'#attention-memo',
			];
			if (level < 5) _.remove(result, val => val === '#fifth-name');
			if (level < 4) _.remove(result, val => val === '#fourth-name');
			if (level < 3) _.remove(result, val => val === '#third-name');
			if (level < 2) _.remove(result, val => val === '#second-name');

			return result;
		}

		checkForChanges(): boolean {
			const vm = this;
			return !(vm.level() === vm.levelBackup && _.isEqual(vm.selectionData, vm.selectionDataBackup));
		}

		createCommand(): Command {
			const vm = this;
			const approvalLevelNo = vm.level();
			
			const settingTypeUseds: SettingTypeUsed[] = _.map(vm.selectionData, data => {
				return {
					employmentRootAtr: data.employmentRootAtr,
					applicationType: data.applicationType,
					confirmRootType: data.confirmRootType,
					notUseAtr: data.notUseAtr ? NotUseAtr.DO : NotUseAtr.NOT_DO,
				} as SettingTypeUsed;
			});
			settingTypeUseds.unshift({
				employmentRootAtr: EmploymentRootAtr.COMMON,
				applicationType: null,
				confirmRootType: null,
				notUseAtr: NotUseAtr.DO,
			});
			const level = ko.unwrap(vm.level);
			if (level < 5) vm.fifthItemName('');
			if (level < 4) vm.fourthItemName('');
			if (level < 3) vm.thirdItemName('');
			if (level < 2) vm.secondItemName('');

			const approverSettingScreenInfor: ApproverSettingScreenInfor = {
				firstItemName: _.isEmpty(vm.firstItemName()) ? null : vm.firstItemName(),
				secondItemName: _.isEmpty(vm.secondItemName()) ? null : vm.secondItemName(),
				thirdItemName: _.isEmpty(vm.thirdItemName()) ? null : vm.thirdItemName(),
				fourthItemName: _.isEmpty(vm.fourthItemName()) ? null : vm.fourthItemName(),
				fifthItemName: _.isEmpty(vm.fifthItemName()) ? null : vm.fifthItemName(),
				processMemo: _.isEmpty(vm.processMemo()) ? null : vm.processMemo(),
				attentionMemo: _.isEmpty(vm.attentionMemo()) ? null : vm.attentionMemo(),
			};

			return new Command({ approvalLevelNo, settingTypeUseds, approverSettingScreenInfor });
		}

		jumpToCMM030A() {
			const vm = this;
			if (!vm.canJumpToCMM030()) return;

			const params = { requestUrl: '/view/cmm/018/r/index.xhtml' };
			vm.$jump('com', '/view/cmm/030/a/index.xhtml', params);
		}

	}
	
	enum NotUseAtr {
		DO = 1,
		NOT_DO = 0,
	}

	enum EmploymentRootAtr {
		COMMON = 0,
		APPLICATION = 1,
		CONFIRMATION = 2,
	}

	enum ConfirmationRootType {
		DAILY_CONFIRMATION = 0,
		MONTHLY_CONFIRMATION = 1,
	}

	interface InitData {
		appUseAtrs: AppUseAtr[];
		setting: ApproverOperationSetting;
	}
	interface AppUseAtr {
		appType: number;
		useAtr: number;
	}

	interface ApproverOperationSetting {
		operationMode: number;
		approvalLevelNo: number;
		settingTypeUseds: SettingTypeUsed[];
		approverSettingScreenInfor: ApproverSettingScreenInfor;
	}

	interface SettingTypeUsed {
		employmentRootAtr: number;
		applicationType: number;
		confirmRootType: number;
		notUseAtr: number;
	}

	interface ApproverSettingScreenInfor {
		firstItemName: string;
		secondItemName: string;
		thirdItemName: string;
		fourthItemName: string;
		fifthItemName: string;
		processMemo: string;
		attentionMemo: string;
	}

	class SelectionData {
		id?: string;
		name: string;
		employmentRootAtr: number;
		applicationType?: number;
		confirmRootType?: number;
		notUseAtr?: boolean;

		constructor(init: SelectionData) {
			$.extend(this, init);
			this.id = nts.uk.util.randomId();
		}
	}

	class Command {
		approvalLevelNo: number;
		settingTypeUseds: any[];
		approverSettingScreenInfor: ApproverSettingScreenInfor;

		constructor(init: Command) {
			$.extend(this, init);
		}
	}

}
