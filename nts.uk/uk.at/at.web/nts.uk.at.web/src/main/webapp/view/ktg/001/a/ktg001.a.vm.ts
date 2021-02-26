module nts.uk.at.view.ktg001.a {

	import windows = nts.uk.ui.windows;

	export const KTG001_API = {
		GET_APPROVED_DATA_EXCECUTION: 'screen/at/ktg001/display',
		UPDATE_APPROVED_DATA_EXCECUTION: 'screen/at/ktg001/setting',
	};

	//承認すべきデータの実行結果
	export interface IApprovedDataExecutionResult {
		haveParticipant: Boolean; //勤怠担当者である
		topPagePartName: string; //名称
		appDisplayAtr: Boolean; //承認すべき申請データ
		dayDisplayAtr: Boolean; //承認すべき日の実績が存在する
		monthDisplayAtr: Boolean; //承認すべき月の実績が存在する
		agrDisplayAtr: Boolean; //承認すべき36協定が存在する
		approvedAppStatusDetailedSettings: Array<IApprovedAppStatusDetailedSetting>; //承認すべき申請状況の詳細設定
		closingPeriods: Array<IClosureIdPresentClosingPeriod>; //締めID, 現在の締め期間
	}

	//承認すべき申請状況の詳細設定
	export interface IApprovedAppStatusDetailedSetting {
		displayType: number; //表示区分
		item: number; //項目
	}
	
	//List＜締めID, 現在の締め期間＞
	export interface IClosureIdPresentClosingPeriod {
		closureId: number; //締めID
		currentClosingPeriod: IPresentClosingPeriodImport; //現在の締め期間
	}

	//現在の締め期間
	export interface IPresentClosingPeriodImport {
		processingYm: number; //処理年月
		closureStartDate: String; //締め開始日
		closureEndDate: String; //締め終了日
	}

	//承認すべきデータのウィジェットを起動する
	export interface IResponse {
		approvedDataExecutionResultDto: IApprovedDataExecutionResult; //承認すべきデータのウィジェットを起動する
		approvalProcessingUseSetting: IApprovalProcessingUseSetting; //承認処理の利用設定を取得する
		agreementOperationSetting: any; //ドメインモデル「３６協定運用設定」を取得する
		
	}

	//承認処理の利用設定を取得する
	export interface IApprovalProcessingUseSetting {
		useDayApproverConfirm: Boolean; //日の承認者確認を利用する
		useMonthApproverConfirm: Boolean; //月の承認者確認を利用する
	}

	interface IParam {
		ym: number, //表示期間
		closureId: number //締めID
	}
	
	const USE = __viewContext.enums.NotUseAtr[1].value;
	const APP = __viewContext.enums.ApprovedApplicationStatusItem[0].value;
	const DAY = __viewContext.enums.ApprovedApplicationStatusItem[1].value;
	const MON = __viewContext.enums.ApprovedApplicationStatusItem[2].value;
	const AGG = __viewContext.enums.ApprovedApplicationStatusItem[3].value;


	@bean()
	class ViewModel extends ko.ViewModel {
	}
}