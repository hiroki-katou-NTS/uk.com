 /// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf018.a.viewmodel {
	import character = nts.uk.characteristics;
	import KAF018BParam = nts.uk.at.view.kaf018.b.viewmodel.KAF018BParam;
	
	@bean()
	class Kaf018AViewModel extends ko.ViewModel {
		appNameLst: Array<any> = [];
		closureLst: KnockoutObservableArray<ClosureItem> = ko.observableArray([]);
		selectedClosureId: KnockoutObservable<number> = ko.observable(0);
		dateValue: KnockoutObservable<any> = ko.observable({});
		selectAbleIDLst: KnockoutObservableArray<any> = ko.observableArray([]);
		selectedIds: KnockoutObservableArray<number> = ko.observableArray([]);
		useSet: any = null;
		initDisplayOfApprovalStatus: InitDisplayOfApprovalStatus = {
			// ページング行数
			numberOfPage: 0,
			// ユーザーID
			userID: '',
			// 会社ID
			companyID: __viewContext.user.companyId,
			// 月別実績の本人確認・上長承認の状況を表示する
			confirmAndApprovalMonthFlg: false,
			// 就業確定の状況を表示する
			confirmEmploymentFlg: false,
			// 申請の承認状況を表示する
			applicationApprovalFlg: false,
			// 日別実績の本人確認・上長承認の状況を表示する
			confirmAndApprovalDailyFlg: false
		};
		fullWorkplaceInfo: Array<DisplayWorkplace> = [];
		
		treeGrid: any;
		multiSelectedWorkplaceId: KnockoutObservableArray<string> = ko.observableArray([]);
		baseDate: KnockoutObservable<Date> = ko.observable(new Date());
		
		created(params: KAF018BParam) {
			const vm = this;
			vm.treeGrid = {
				isShowAlreadySet: false,
				isMultipleUse: false,
				isMultiSelect: true,
				treeType: 1,
				selectedId: vm.multiSelectedWorkplaceId,
				baseDate: vm.baseDate,
				selectType: 1,
				isShowSelectButton: true,
				isDialog: true,
				showIcon: true,
				alreadySettingList: ko.observableArray([]),
				maxRows: 15,
				tabindex: 1,
				systemType: 2
			};
			vm.dateValue.subscribe(value => {
				vm.baseDate(new Date(value.endDate));
			});
			vm.selectedIds.subscribe(value => {
				if(_.includes(value, 1)) {
					vm.initDisplayOfApprovalStatus.applicationApprovalFlg = true;
				} else {
					vm.initDisplayOfApprovalStatus.applicationApprovalFlg = false;
				}
				if(_.includes(value, 2)) {
					vm.initDisplayOfApprovalStatus.confirmAndApprovalDailyFlg = true;
				} else {
					vm.initDisplayOfApprovalStatus.confirmAndApprovalDailyFlg = false;
				}
				if(_.includes(value, 3)) {
					vm.initDisplayOfApprovalStatus.confirmAndApprovalMonthFlg = true;
				} else {
					vm.initDisplayOfApprovalStatus.confirmAndApprovalMonthFlg = false;
				}
				if(_.includes(value, 4)) {
					vm.initDisplayOfApprovalStatus.confirmEmploymentFlg = true;
				} else {
					vm.initDisplayOfApprovalStatus.confirmEmploymentFlg = false;
				}
			});
			vm.$blockui('show');
			vm.$ajax(API.getUseSetting).then((useSetResult: any) => {
				let a = [{ 'id': 1, 'name': vm.$i18n('KAF018_318') }];
				if(useSetResult) {
					if((useSetResult.usePersonConfirm) || (useSetResult.useBossConfirm)) {
						a.push({ 'id': 2, 'name': 'select2' });
					}
					if((useSetResult.monthlyIdentityConfirm) || (useSetResult.monthlyConfirm)) {
						a.push({ 'id': 3, 'name': 'select3' });
					}
					if(useSetResult.employmentConfirm) {
						a.push({ 'id': 4, 'name': vm.$i18n('KAF018_321') });
					}
				}
				vm.useSet = useSetResult;
				vm.selectAbleIDLst(a);
				return character.restore('InitDisplayOfApprovalStatus');
			}).then((obj: InitDisplayOfApprovalStatus) => {
				if(obj) {
					let a = [];
					if(obj.applicationApprovalFlg) {
						a.push(1);	
					}
					if(obj.confirmAndApprovalDailyFlg) {
						a.push(2);	
					}
					if(obj.confirmAndApprovalMonthFlg) {
						a.push(3);	
					}
					if(obj.confirmEmploymentFlg) {
						a.push(4);	
					}
					vm.selectedIds(a);
					vm.initDisplayOfApprovalStatus = obj;
				} else {
					vm.selectedIds(_.map(vm.selectAbleIDLst(), (o: any) => o.id));
				}
				return vm.$ajax(API.getAppNameInAppList);
			}).then((appNameLst: any) => {
				vm.appNameLst = appNameLst;
				return vm.$ajax(API.getApprovalStatusActivation);
			}).then((data: any) => {
				vm.closureLst(_.map(data.closureList, (o: any) => {
					return new ClosureItem(o.closureHistories[0].closureId, o.closureHistories[0].closeName, o.closureMonth);
				}));
				if(params) {
					vm.multiSelectedWorkplaceId(_.map(params.selectWorkplaceInfo, o => o.id));
					vm.selectedClosureId(params.closureItem.closureId);
					vm.dateValue().startDate = params.startDate;
					vm.dateValue().endDate = params.endDate;
					vm.dateValue.valueHasMutated();
					let a = [];
					if(params.initDisplayOfApprovalStatus.applicationApprovalFlg) {
						a.push(1);	
					}
					if(params.initDisplayOfApprovalStatus.confirmAndApprovalDailyFlg) {
						a.push(2);	
					}
					if(params.initDisplayOfApprovalStatus.confirmAndApprovalMonthFlg) {
						a.push(3);	
					}
					if(params.initDisplayOfApprovalStatus.confirmEmploymentFlg) {
						a.push(4);	
					}
					vm.selectedIds(a);
					vm.initDisplayOfApprovalStatus = params.initDisplayOfApprovalStatus;
				} else {
					vm.selectedClosureId(_.head(vm.closureLst()).closureId);
					vm.dateValue().startDate = data.startDate;
					vm.dateValue().endDate = data.endDate;
					vm.dateValue.valueHasMutated();	
				}
				return $('#tree-grid').ntsTreeComponent(vm.treeGrid).done(() => {
					vm.fullWorkplaceInfo = vm.flattenWkpTree(_.cloneDeep($('#tree-grid').getDataList()));
					vm.selectedClosureId.subscribe(() => $('#tree-grid').focusTreeGridComponent());
				});
			}).then(() => {
				vm.$blockui('hide');
				$('#tree-grid').focusTreeGridComponent();
			});
		}
		
		extraction() {
			const vm = this;
			// 画面情報を保持する。（再表示した際に使用するため）
			
			// アルゴリズム「職場選択チェック」を実行する(thực hiện thuật toán 「職場選択チェック」)
			// 共通部品「KCP004_職場リスト」より選択職場を取得する(get workplace chọn từ commonComponent「KCP004_職場リスト」)
			if(_.isEmpty(vm.multiSelectedWorkplaceId())) {
				// メッセージ（Msg_786）を表示する(hiển thị message（Msg_786）)
				vm.$dialog.error({ messageId: 'Msg_786' });
				return;
			}
			// アルゴリズム「項目選択チェック」を実行する
			// 画面の項目選択チェックボックスを判別する
			if(_.isEmpty(vm.selectedIds())) {
				// メッセージ（Msg_1764）を表示する(hiển thị message（Msg_1764）)
				vm.$dialog.error({ messageId: 'Msg_1764' });
				return;
			}
			// Ｂ画面(承認・確認状況の照会)を実行する
			character.save('InitDisplayOfApprovalStatus', vm.initDisplayOfApprovalStatus).then(() => {
            	let closureItem = _.find(vm.closureLst(), o => o.closureId == vm.selectedClosureId()),
					startDate = vm.dateValue().startDate,
					endDate = vm.dateValue().endDate,
					selectWorkplaceInfo: Array<DisplayWorkplace> = _.chain(vm.fullWorkplaceInfo)
																	.filter((o: DisplayWorkplace) => _.includes(vm.multiSelectedWorkplaceId(), o.id))
																	.sortBy('hierarchyCode').value(),
					appNameLst: Array<any> = vm.appNameLst,
					useSet = vm.useSet,
					initDisplayOfApprovalStatus = vm.initDisplayOfApprovalStatus,
					bParam: KAF018BParam = { closureItem, startDate, endDate, selectWorkplaceInfo, appNameLst, useSet, initDisplayOfApprovalStatus };
				vm.$jump("/view/kaf/018/b/index.xhtml", bParam);
            });
			
		}

		emailSetting() {
			const vm = this;
			let height = screen.availHeight;
			if(screen.availHeight > 475) {
				height = 475
			}
			if(screen.availHeight < 400) {
				height = 400;
			}
			let dialogSize = {
				width: 850,
				height: height
			}
			vm.$window.modal('/view/kaf/018/i/index.xhtml', {}, dialogSize);
		}
		
		flattenWkpTree(wkpTree: Array<DisplayWorkplace>) {
            return wkpTree.reduce((wkp, x) => {
                wkp = wkp.concat(x);
                if (x.children && x.children.length > 0) {
                    wkp = wkp.concat(this.flattenWkpTree(x.children));
                    x.children = [];
                }
                return wkp;
            }, []);
        }
	}

	export class ClosureItem {
		closureId: number; 
		closureName: string;
		processingYm: number;
		
		constructor(closureId: number, closureName: string, processingYm: number) {
			this.closureId = closureId;
			this.closureName = closureName;
			this.processingYm = processingYm;
		}
	}
	
	// 承認状況照会の初期表示
	export class InitDisplayOfApprovalStatus {
		// ページング行数
		numberOfPage: number;
		// ユーザーID
		userID: string;
		// 会社ID
		companyID: string;
		// 月別実績の本人確認・上長承認の状況を表示する
		confirmAndApprovalMonthFlg: boolean;
		// 就業確定の状況を表示する
		confirmEmploymentFlg: boolean;
		// 申請の承認状況を表示する
		applicationApprovalFlg: boolean;
		// 日別実績の本人確認・上長承認の状況を表示する
		confirmAndApprovalDailyFlg: boolean;
	}
	
	export interface DisplayWorkplace {
		code: string;
		id: string;
		name: string;
		hierarchyCode: string;
		level: number;
		children: Array<DisplayWorkplace>;
	}

	const API = {
		getApprovalStatusActivation: "at/request/application/approvalstatus/getApprovalStatusActivation",
		getAppNameInAppList: "at/request/application/screen/applist/getAppNameInAppList",
		getUseSetting: "at/record/application/realitystatus/getUseSetting"
	}
}