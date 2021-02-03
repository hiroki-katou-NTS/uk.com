package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.flex;

import java.io.Serializable;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 会社別フレックス勤務集計方法
 * @author shuichi_ishida
 */
@Getter
public class GetFlexPredWorkTime extends AggregateRoot implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 会社ID */
	private String companyId;
	/** 参照先 */
	private ReferencePredTimeOfFlex reference;
	
	/**
	 * コンストラクタ
	 * @param companyId 会社ID
	 */
	public GetFlexPredWorkTime(String companyId){
		
		super();
		this.companyId = companyId;
		this.reference = ReferencePredTimeOfFlex.FROM_MASTER;
	}

	/**
	 * ファクトリー
	 * @param companyId 会社ID
	 * @param reference 参照先
	 * @return 会社別フレックス勤務集計方法
	 */
	public static GetFlexPredWorkTime of(
			String companyId,
			ReferencePredTimeOfFlex reference){
		
		GetFlexPredWorkTime domain = new GetFlexPredWorkTime(companyId);
		domain.reference = reference;
		return domain;
	}
}
