 /// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf018.a.viewmodel {
	import KAF018BParam = nts.uk.at.view.kaf018.b.viewmodel.KAF018BParam;
	
	@bean()
	class Kaf018AViewModel extends ko.ViewModel {
		closureLst: KnockoutObservableArray<ClosureItem> = ko.observableArray([]);
		selectedClosureId: KnockoutObservable<number> = ko.observable(0);
		dateValue: KnockoutObservable<any> = ko.observable({});
		selectedIds: KnockoutObservableArray<number> = ko.observableArray([]);
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
		selectWorkplaceInfo: Array<DisplayWorkplace> = [];
		
		treeGrid: any;
		multiSelectedWorkplaceId: KnockoutObservableArray<string> = ko.observableArray([]);
		baseDate: KnockoutObservable<Date> = ko.observable(new Date());
		alreadySettingList: KnockoutObservableArray<any> = ko.observableArray([]);
		
		created() {
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
				alreadySettingList: vm.alreadySettingList,
				maxRows: 15,
				tabindex: 1,
				systemType: 2
			};
			// $('#tree-grid').ntsTreeComponent(vm.treeGrid).done(() => {});
			vm.multiSelectedWorkplaceId.subscribe(() => {
				vm.selectWorkplaceInfo = $('#tree-grid').getRowSelected();
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
			vm.$ajax(API.getApprovalStatusActivation).then((data) => {
				vm.closureLst(_.map(data.closureList, (o: any) => {
					return new ClosureItem(o.closureHistories[0].closureId, o.closureHistories[0].closeName);
				}));
				vm.selectedClosureId(_.head(vm.closureLst()).closureId);
				vm.dateValue().startDate = data.startDate;
				vm.dateValue().endDate = data.endDate;
				vm.dateValue.valueHasMutated();
				return $('#tree-grid').ntsTreeComponent(vm.treeGrid);
			}).then(() => {
				vm.$blockui('hide');
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
			let initDisplayOfApprovalStatus: InitDisplayOfApprovalStatus = vm.initDisplayOfApprovalStatus,
				closureItem = _.find(vm.closureLst(), o => o.closureId == vm.selectedClosureId()),
				startDate = vm.dateValue().startDate,
				endDate = vm.dateValue().endDate,
				selectWorkplaceInfo: Array<DisplayWorkplace> = vm.selectWorkplaceInfo,
				bParam: KAF018BParam = { initDisplayOfApprovalStatus, closureItem, startDate, endDate, selectWorkplaceInfo };
			vm.$jump("/view/kaf/018/b/index.xhtml", bParam);
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
	}

	export class ClosureItem {
		closureId: number; 
		closureName: string;
		
		constructor(closureId: number, closureName: string) {
			this.closureId = closureId;
			this.closureName = closureName;
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
	}

	const API = {
		getApprovalStatusActivation: "at/request/application/approvalstatus/getApprovalStatusActivation"
	}
}