package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;

/**
 * 年休情報OUTPUT
 * @author shuichu_ishida
 */
@Getter
public class AnnualLeaveOutput {

	/** 年休情報（期間終了日時点） */
	private AnnualLeaveInfo asOfPeriodEnd;
	/** 年休情報（期間終了日の翌日開始時点） */
	private AnnualLeaveInfo asOfStartNextDayOfPeriodEnd;
	/** 年休情報（付与時点） */
	private Optional<Map<GeneralDate, AnnualLeaveInfo>> asOfGrant;
	/** 年休情報（消滅） */
	private Optional<Map<GeneralDate, AnnualLeaveInfo>> lapsed;
	/** 年休エラー情報 */
	private List<AnnualLeaveError> annualLeaveErrors;
	
	/**
	 * コンストラクタ
	 */
	public AnnualLeaveOutput(){
		
		this.asOfPeriodEnd = new AnnualLeaveInfo(GeneralDate.today());
		this.asOfStartNextDayOfPeriodEnd = new AnnualLeaveInfo(GeneralDate.today());
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
	 * @return 年休情報OUTPUT
	 */
	public static AnnualLeaveOutput of(
			AnnualLeaveInfo asOfPeriodEnd,
			AnnualLeaveInfo asOfStartNextDayOfPeriodEnd,
			Optional<List<AnnualLeaveInfo>> asOfGrant,
			Optional<List<AnnualLeaveInfo>> lapsed,
			List<AnnualLeaveError> annualLeaveErrors){
		
		AnnualLeaveOutput domain = new AnnualLeaveOutput();
		domain.asOfPeriodEnd = asOfPeriodEnd;
		domain.asOfStartNextDayOfPeriodEnd = asOfStartNextDayOfPeriodEnd;
		if (asOfGrant.isPresent()){
			domain.asOfGrant = Optional.of(new HashMap<>());
			for (val annualLeaveInfo : asOfGrant.get()){
				val ymd = annualLeaveInfo.getYmd();
				domain.asOfGrant.get().putIfAbsent(ymd, annualLeaveInfo);
			}
		}
		else {
			domain.asOfGrant = Optional.empty();
		}
		if (lapsed.isPresent()){
			domain.lapsed = Optional.of(new HashMap<>());
			for (val annualLeaveInfo : lapsed.get()){
				val ymd = annualLeaveInfo.getYmd();
				domain.lapsed.get().putIfAbsent(ymd, annualLeaveInfo);
			}
		}
		else {
			domain.lapsed = Optional.empty();
		}
		domain.annualLeaveErrors = annualLeaveErrors;
		return domain;
	}
}
