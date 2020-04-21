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
	
	/**
	 * 打刻カードID
	 */
	private String stampCardId;


	/**
	 * 社員ID
	 */
	private String employeeId;

	/**
	 * 番号
	 */
	private final StampNumber stampNumber;

	/**
	 * 登録日付
	 */
	private GeneralDate registerDate;

	/** 契約コード */
	private final ContractCode contractCd;

	public static StampCard createFromJavaType(String stampCardId, String employeeId, String stampNumber,
			GeneralDate registerDate, String contractCd) {
		return new StampCard(stampCardId, new ContractCode(contractCd), new StampNumber(stampNumber), employeeId,
				registerDate);
	}

	/**
	 * [C-1] 作成する
	 * 
	 * @param 契約コード
	 *            contractCd
	 * @param 番号
	 *            stampNumber
	 * @param 社員ID
	 *            employeeId
	 * @param 登録日付
	 *            registerDate
	 */
	public StampCard(String stampCardId,ContractCode contractCd, StampNumber stampNumber, String employeeId, GeneralDate registerDate) {
		super();
		this.stampCardId = stampCardId;
		this.contractCd = contractCd;
		this.stampNumber = stampNumber;
		this.employeeId = employeeId;
		this.registerDate = registerDate;

	}
}
