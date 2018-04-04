package nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;

/**
 * 積立年休情報OUTPUT
 * @author shuichu_ishida
 */
@Getter
public class ReserveLeaveOutput {

	/** 積立年休情報（期間終了日時点） */
	private ReserveLeaveInfo asOfPeriodEnd;
	/** 積立年休情報（末日翌日付与時） */
	private Optional<ReserveLeaveInfo> asOfGrantNextDayOfLastDay;
	/** 積立年休情報（付与時点） */
	private Optional<Map<GeneralDate, ReserveLeaveInfo>> asOfGrant;
	/** 積立年休情報（消滅） */
	private Optional<Map<GeneralDate, ReserveLeaveInfo>> lapsed;
	/** 積立年休エラー情報 */
	private List<ReserveLeaveError> reserveLeaveErrors;
	
	/**
	 * コンストラクタ
	 */
	public ReserveLeaveOutput(){
		
		this.asOfPeriodEnd = new ReserveLeaveInfo(GeneralDate.today());
		this.asOfGrantNextDayOfLastDay = Optional.empty();
		this.asOfGrant = Optional.empty();
		this.lapsed = Optional.empty();
		this.reserveLeaveErrors = new ArrayList<>();
	}
	
	/**
	 * ファクトリー
	 * @param asOfPeriodEnd 積立年休情報（期間終了日時点）
	 * @param asOfGrantNextDayOfLastDay 積立年休情報（末日翌日付与時）
	 * @param asOfGrant 積立年休情報（付与時点）
	 * @param lapsed 積立年休情報（消滅）
	 * @param reserveLeaveErrors 積立年休エラー情報
	 * @return 積立年休情報OUTPUT
	 */
	public static ReserveLeaveOutput of(
			ReserveLeaveInfo asOfPeriodEnd,
			Optional<ReserveLeaveInfo> asOfGrantNextDayOfLastDay,
			Optional<List<ReserveLeaveInfo>> asOfGrant,
			Optional<List<ReserveLeaveInfo>> lapsed,
			List<ReserveLeaveError> reserveLeaveErrors){
		
		ReserveLeaveOutput domain = new ReserveLeaveOutput();
		domain.asOfPeriodEnd = asOfPeriodEnd;
		domain.asOfGrantNextDayOfLastDay = asOfGrantNextDayOfLastDay;
		if (asOfGrant.isPresent()){
			domain.asOfGrant = Optional.of(new HashMap<>());
			for (val reserveLeaveInfo : asOfGrant.get()){
				val ymd = reserveLeaveInfo.getYmd();
				domain.asOfGrant.get().putIfAbsent(ymd, reserveLeaveInfo);
			}
		}
		else {
			domain.asOfGrant = Optional.empty();
		}
		if (lapsed.isPresent()){
			domain.lapsed = Optional.of(new HashMap<>());
			for (val reserveLeaveInfo : lapsed.get()){
				val ymd = reserveLeaveInfo.getYmd();
				domain.lapsed.get().putIfAbsent(ymd, reserveLeaveInfo);
			}
		}
		else {
			domain.lapsed = Optional.empty();
		}
		domain.reserveLeaveErrors = reserveLeaveErrors;
		return domain;
	}
}
