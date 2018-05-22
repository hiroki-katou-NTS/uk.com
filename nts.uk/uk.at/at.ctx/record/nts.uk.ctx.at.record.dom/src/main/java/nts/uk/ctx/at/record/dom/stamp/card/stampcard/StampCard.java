package nts.uk.ctx.at.record.dom.stamp.card.stampcard;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

@Getter
@Setter
public class StampCard extends AggregateRoot {


	// domain name: 打刻カード

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
	private StampNumber stampNumber;

	/**
	 * 登録日付
	 */
	private GeneralDate registerDate;
	
	
	/** 契約コード */
	private ContractCode contractCd;

	public static StampCard createFromJavaType(String stampCardId, String employeeId, String stampNumber,
			GeneralDate registerDate , String contractCd) {
		return new StampCard(stampCardId, employeeId, new StampNumber(stampNumber), registerDate , new ContractCode(contractCd));
	}

	public StampCard(String stampCardId, String employeeId, StampNumber stampNumber, GeneralDate registerDate, ContractCode contractCd) {
		super();
		this.stampCardId = stampCardId;
		this.employeeId = employeeId;
		this.stampNumber = stampNumber;
		this.registerDate = registerDate;
		this.contractCd = contractCd;
	}
}
