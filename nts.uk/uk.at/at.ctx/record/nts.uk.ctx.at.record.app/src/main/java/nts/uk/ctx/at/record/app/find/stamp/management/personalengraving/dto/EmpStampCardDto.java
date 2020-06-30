package nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;

/**
 * @author anhdt
 *
 */
@Data
@NoArgsConstructor
public class EmpStampCardDto {

	private String stampCardId;
	private String employeeId;
	private String stampNumber;
	private String registerDate;
	private String contractCd;

	public EmpStampCardDto(StampCard stampcard) {
		this.stampCardId = stampcard.getStampCardId();
		this.stampNumber = stampcard.getStampNumber().v();
		this.registerDate = stampcard.getRegisterDate().toString();
		this.contractCd = stampcard.getContractCd().v();
		this.employeeId = stampcard.getEmployeeId();
	}

}
