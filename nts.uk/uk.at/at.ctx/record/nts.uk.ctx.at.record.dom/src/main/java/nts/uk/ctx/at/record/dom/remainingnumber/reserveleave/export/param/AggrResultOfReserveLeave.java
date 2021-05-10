package nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.erroralarm.ReserveLeaveError;

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

	/**
	 * 積休不足分として作成した年休付与データを削除する
	 */
	public void deleteShortageRemainData() {

		// 積休情報(期間終了日時点)の不足分年休残数データを削除
		asOfPeriodEnd.deleteDummy();

		// 積休情報(期間終了日の翌日開始時点)の不足分付与残数データを削除
		asOfStartNextDayOfPeriodEnd.deleteDummy();

		// 積休の集計結果．年休情報(付与時点)を取得
		if ( asOfGrant.isPresent() ){
			// 取得した年休情報(付与時点)でループ
			asOfGrant.get().forEach(info->{
				// 積休情報(付与時点)の不足分付与残数データを削除
				info.deleteDummy();
			});
		}

		// 積休の集計結果．年休情報(消滅時点)を取得
		if ( lapsed.isPresent() ){
			// 取得した積休情報(付与時点)でループ
			lapsed.get().forEach(info->{
				// 付与残数データから積休不足分の積休付与残数を削除
				info.deleteDummy();
			});
		}
	}
}
