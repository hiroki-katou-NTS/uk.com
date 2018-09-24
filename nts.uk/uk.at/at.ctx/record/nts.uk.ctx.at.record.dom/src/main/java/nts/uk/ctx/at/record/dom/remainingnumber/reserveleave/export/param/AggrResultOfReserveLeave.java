package nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

/**
 * 積立年休の集計結果
 * @author shuichi_ishida
 */
@Getter
@Setter
public class AggrResultOfReserveLeave {

	/** 積立年休情報（期間終了日時点） */
	private ReserveLeaveInfo asOfPeriodEnd;
	/** 積立年休情報（期間終了日の翌日開始時点） */
	private ReserveLeaveInfo asOfStartNextDayOfPeriodEnd;
	/** 積立年休情報（付与時点） */
	private Optional<List<ReserveLeaveInfo>> asOfGrant;
	/** 積立年休情報（消滅） */
	private Optional<List<ReserveLeaveInfo>> lapsed;
	/** 積立年休エラー情報 */
	private List<ReserveLeaveError> reserveLeaveErrors;
	
	/**
	 * コンストラクタ
	 */
	public AggrResultOfReserveLeave(){
		
		this.asOfPeriodEnd = new ReserveLeaveInfo();
		this.asOfStartNextDayOfPeriodEnd = new ReserveLeaveInfo();
		this.asOfGrant = Optional.empty();
		this.lapsed = Optional.empty();
		this.reserveLeaveErrors = new ArrayList<>();
	}
	
	/**
	 * ファクトリー
	 * @param asOfPeriodEnd 積立年休情報（期間終了日時点）
	 * @param asOfStartNextDayOfPeriodEnd 積立年休情報（期間終了日の翌日開始時点）
	 * @param asOfGrant 積立年休情報（付与時点）
	 * @param lapsed 積立年休情報（消滅）
	 * @param reserveLeaveErrors 積立年休エラー情報
	 * @return 積立年休の集計結果
	 */
	public static AggrResultOfReserveLeave of(
			ReserveLeaveInfo asOfPeriodEnd,
			ReserveLeaveInfo asOfStartNextDayOfPeriodEnd,
			Optional<List<ReserveLeaveInfo>> asOfGrant,
			Optional<List<ReserveLeaveInfo>> lapsed,
			List<ReserveLeaveError> reserveLeaveErrors){
		
		AggrResultOfReserveLeave domain = new AggrResultOfReserveLeave();
		domain.asOfPeriodEnd = asOfPeriodEnd;
		domain.asOfStartNextDayOfPeriodEnd = asOfStartNextDayOfPeriodEnd;
		domain.asOfGrant = asOfGrant;
		domain.lapsed = lapsed;
		domain.reserveLeaveErrors = reserveLeaveErrors;
		return domain;
	}
	
	/**
	 * 積立年休エラー情報の追加
	 * @param error 積立年休エラー情報
	 */
	public void addError(ReserveLeaveError error){
		
		if (this.reserveLeaveErrors.contains(error)) return;
		this.reserveLeaveErrors.add(error);
	}
}
