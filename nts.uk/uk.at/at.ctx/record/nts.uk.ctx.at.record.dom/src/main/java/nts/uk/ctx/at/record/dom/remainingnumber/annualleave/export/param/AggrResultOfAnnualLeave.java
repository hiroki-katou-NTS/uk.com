package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

/**
 * 年休の集計結果
 * @author shuichi_ishida
 */
@Getter
@Setter
public class AggrResultOfAnnualLeave {

	/** 年休情報（期間終了日時点） */
	private AnnualLeaveInfo asOfPeriodEnd;
	/** 年休情報（期間終了日の翌日開始時点） */
	private AnnualLeaveInfo asOfStartNextDayOfPeriodEnd;
	/** 年休情報（付与時点） */
	private Optional<List<AnnualLeaveInfo>> asOfGrant;
	/** 年休情報（消滅） */
	private Optional<List<AnnualLeaveInfo>> lapsed;
	/** 年休エラー情報 */
	private List<AnnualLeaveError> annualLeaveErrors;
	
	/**
	 * コンストラクタ
	 */
	public AggrResultOfAnnualLeave(){
		
		this.asOfPeriodEnd = new AnnualLeaveInfo();
		this.asOfStartNextDayOfPeriodEnd = new AnnualLeaveInfo();
		this.asOfGrant = Optional.empty();
		this.lapsed = Optional.empty();
		this.annualLeaveErrors = new ArrayList<>();
	}
	
	/**
	 * ファクトリー
	 * @param asOfPeriodEnd 年休情報（期間終了日時点）
	 * @param asOfStartNextDayOfPeriodEnd 年休情報（期間終了日の翌日開始時点）
	 * @param asOfGrant 年休情報（付与時点）
	 * @param lapsed 年休情報（消滅）
	 * @param annualLeaveErrors 年休エラー情報
	 * @return 年休の集計結果
	 */
	public static AggrResultOfAnnualLeave of(
			AnnualLeaveInfo asOfPeriodEnd,
			AnnualLeaveInfo asOfStartNextDayOfPeriodEnd,
			Optional<List<AnnualLeaveInfo>> asOfGrant,
			Optional<List<AnnualLeaveInfo>> lapsed,
			List<AnnualLeaveError> annualLeaveErrors){
		
		AggrResultOfAnnualLeave domain = new AggrResultOfAnnualLeave();
		domain.asOfPeriodEnd = asOfPeriodEnd;
		domain.asOfStartNextDayOfPeriodEnd = asOfStartNextDayOfPeriodEnd;
		domain.asOfGrant = asOfGrant;
		domain.lapsed = lapsed;
		domain.annualLeaveErrors = annualLeaveErrors;
		return domain;
	}
	
	/**
	 * 年休エラー情報の追加
	 * @param error 年休エラー情報
	 */
	public void addError(AnnualLeaveError error){
		
		if (this.annualLeaveErrors.contains(error)) return;
		this.annualLeaveErrors.add(error);
	}
}
