package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.flex;

import java.io.Serializable;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * フレックス勤務所定労働時間取得
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
	 * @return フレックス勤務所定労働時間取得
	 */
	public static GetFlexPredWorkTime of(
			String companyId,
			ReferencePredTimeOfFlex reference){
		
		GetFlexPredWorkTime domain = new GetFlexPredWorkTime(companyId);
		domain.reference = reference;
		return domain;
	}
}
