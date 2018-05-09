package nts.uk.ctx.at.record.dom.monthly.workform.flex;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * フレックス勤務の月別集計設定
 * @author shuichu_ishida
 */
@Getter
public class MonthlyAggrSetOfFlex extends AggregateRoot {

	/** 会社ID */
	private String companyId;
	/** 時間外加算設定 */
	private OutsideTimeFlexAddition outsideTimeAddSet;
	
	/**
	 * コンストラクタ
	 * @param companyId
	 */
	public MonthlyAggrSetOfFlex(String companyId){
		
		super();
		this.companyId = companyId;
		this.outsideTimeAddSet = new OutsideTimeFlexAddition();
	}

	/**
	 * ファクトリー
	 * @param companyId 会社ID
	 * @param outsideTimeAddSet 時間外加算設定
	 * @return フレックス勤務の月別集計設定
	 */
	public static MonthlyAggrSetOfFlex of(
			String companyId,
			OutsideTimeFlexAddition outsideTimeAddSet){
		
		MonthlyAggrSetOfFlex domain = new MonthlyAggrSetOfFlex(companyId);
		domain.outsideTimeAddSet = outsideTimeAddSet;
		return domain;
	}
}
