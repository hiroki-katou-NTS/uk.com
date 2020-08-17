package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.export.param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 特休の集計結果
 * @author masaaki_jinno
 *
 */
@AllArgsConstructor
@Getter
@Setter
public class InPeriodOfSpecialLeaveResultInfor {
	
	/** 特休情報（期間終了日時点） */
	private SpecialLeaveInfo asOfPeriodEnd;
	/** 特休情報（期間終了日の翌日開始時点） */
	private SpecialLeaveInfo asOfStartNextDayOfPeriodEnd;
	/** 特休情報（付与時点） */
	private Optional<List<SpecialLeaveInfo>> asOfGrant;
	/** 特休情報（消滅） */
	private Optional<List<SpecialLeaveInfo>> lapsed;
	/** 特休エラー情報 */
	private List<SpecialLeaveError> specialLeaveErrors;
	
	/**
	 * コンストラクタ
	 */
	public InPeriodOfSpecialLeaveResultInfor(){
		this.asOfPeriodEnd = new SpecialLeaveInfo();
		this.asOfStartNextDayOfPeriodEnd = new SpecialLeaveInfo();
		this.asOfGrant = Optional.empty();
		this.lapsed = Optional.empty();
		this.specialLeaveErrors = new ArrayList<>();
	}
	
	/**
	 * ファクトリー
	 * @param asOfPeriodEnd 特休情報（期間終了日時点）
	 * @param asOfStartNextDayOfPeriodEnd 特休情報（期間終了日の翌日開始時点）
	 * @param asOfGrant 特休情報（付与時点）
	 * @param lapsed 特休情報（消滅）
	 * @param specialLeaveErrors 特休エラー情報
	 * @return 特休の集計結果
	 */
	public static InPeriodOfSpecialLeaveResultInfor of(
			SpecialLeaveInfo asOfPeriodEnd,
			SpecialLeaveInfo asOfStartNextDayOfPeriodEnd,
			Optional<List<SpecialLeaveInfo>> asOfGrant,
			Optional<List<SpecialLeaveInfo>> lapsed,
			List<SpecialLeaveError> specialLeaveErrors){
		
		InPeriodOfSpecialLeaveResultInfor domain = new InPeriodOfSpecialLeaveResultInfor();
		domain.asOfPeriodEnd = asOfPeriodEnd;
		domain.asOfStartNextDayOfPeriodEnd = asOfStartNextDayOfPeriodEnd;
		domain.asOfGrant = asOfGrant;
		domain.lapsed = lapsed;
		domain.specialLeaveErrors = specialLeaveErrors;
		return domain;
	}
	
	/**
	 * 特休エラー情報の追加
	 * @param error 特休エラー情報
	 */
	public void addError(SpecialLeaveError error){
		
		if (this.specialLeaveErrors.contains(error)) return;
		this.specialLeaveErrors.add(error);
	}
}
