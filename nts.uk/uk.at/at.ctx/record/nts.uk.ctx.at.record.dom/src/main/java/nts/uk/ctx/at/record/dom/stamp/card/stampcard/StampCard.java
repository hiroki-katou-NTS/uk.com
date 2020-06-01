package nts.uk.ctx.at.record.dom.stamp.card.stampcard;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

/**
 * 打刻カード
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻カード.打刻カード
 * 
 * @author sonnlb
 *
 */
@Getter
@Setter
public class StampCard extends AggregateRoot {
	
	/** 契約コード */
	private final ContractCode contractCd;
	
	/**
	 * 番号
	 */
	private final StampNumber stampNumber;
	
	/**
	 * 社員ID
	 */
	private String employeeId;
	
	/**
	 * 登録日付
	 */
	private GeneralDate registerDate;
	
	/**
	 * 打刻カードId
	 */
	private String stampCardId;
	
	/**
	 * [C-1] 作成する
	 * 
	 * @param 契約コード
	 *            contractCd
	 * @param 番号
	 *            stampNumber
	 * @param 社員ID
	 *            employeeId
	 */
	public StampCard(String contractCd, String stampNumber, String employeeId) {
		super();
		this.contractCd = new ContractCode(contractCd);
		this.stampNumber = new StampNumber(stampNumber);
		this.employeeId = employeeId;
		this.registerDate = GeneralDate.today();
	}
}
