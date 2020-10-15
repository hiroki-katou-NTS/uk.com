 /// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf018.a.viewmodel {

    @bean()
    class Kaf018AViewModel extends ko.ViewModel {
        closureLst: KnockoutObservableArray<ClosureItem> = ko.observableArray([]);
        selectedClosureId: KnockoutObservable<string> = ko.observable("");
		dateValue: KnockoutObservable<any> = ko.observable({});
		selectedIds: KnockoutObservableArray<number> = ko.observableArray([]);
		initDisplayOfApprovalStatus: InitDisplayOfApprovalStatus = null;
		
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
			$('#tree-grid').ntsTreeComponent(vm.treeGrid).done(() => {
            });
			vm.$ajax(API.getApprovalStatusActivation).done((data) => {
				vm.closureLst(_.map(data.closureList, (o: any) => {
					return new ClosureItem(o.closureHistories[0].closureId, o.closureHistories[0].closeName);
				}));
				vm.selectedClosureId(_.head(vm.closureLst()).closureId);
				vm.dateValue().startDate = data.startDate;
				vm.dateValue().endDate = data.endDate;
                vm.dateValue.valueHasMutated();
				console.log(data);
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
			vm.$jump("/view/kaf/018/b/index.xhtml", vm.initDisplayOfApprovalStatus);
		}
        
		emailSetting() {
			const vm = this;
			vm.$window.modal('/view/kaf/018/i/index.xhtml');
		}
    }

	class ClosureItem {
		closureId: string; 
		closeName: string;
		
		constructor(closureId: string, closeName: string) {
			this.closureId = closureId;
			this.closeName = closeName;
		}
	}
	
	// 承認状況照会の初期表示
	class InitDisplayOfApprovalStatus {
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

    const API = {
		getAll: "at/request/application/setting/workplace/getall",
        findAllClosure: "at/request/application/approvalstatus/findAllClosure",
        getApprovalStatusPerior: "at/request/application/approvalstatus/getApprovalStatusPerior/{0}/{1}",
        getUseSetting: "at/record/application/realitystatus/getUseSetting",
		// refactor 5
		getApprovalStatusActivation: "at/request/application/approvalstatus/getApprovalStatusActivation"
    }
}