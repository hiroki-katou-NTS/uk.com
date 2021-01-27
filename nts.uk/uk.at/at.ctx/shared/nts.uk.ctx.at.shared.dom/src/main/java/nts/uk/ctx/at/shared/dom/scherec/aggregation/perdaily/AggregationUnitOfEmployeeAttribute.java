package nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;

/**
 * 社員属性の集計単位
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).集計処理.日単位集計.社員属性の集計単位
 * @author kumiko_otake
 */
@Getter
@RequiredArgsConstructor
public enum AggregationUnitOfEmployeeAttribute {

	/** 雇用 **/
	EMPLOYMENT( 0 ),
	/** 分類 **/
	CLASSIFICATION( 1 ),
	/** 職位 **/
	JOB_TITLE( 2 ),
	;


	/** 内部値 **/
	private final int value;


	/**
	 * 社員属性を取得する
	 * @param affInfo 所属情報
	 * @return 社員属性
	 */
	public String getAttribute(AffiliationInforOfDailyAttd affInfo) {

		switch( this ) {
			case EMPLOYMENT:		// 雇用：雇用コード
				return affInfo.getEmploymentCode().v();
			case CLASSIFICATION:	// 分類：分類コード
				return affInfo.getClsCode().v();
			case JOB_TITLE:			// 職位：職位ID
				return affInfo.getJobTitleID();
			default:
				throw new RuntimeException("Value is out of range.");
		}
	}

}
