package nts.uk.ctx.at.function.dom.temporaryabsence.frame;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TempAbsenceFrameApdaterDto {

	/** The company id. */
	// 会社ID
	private String companyId;
	
	/** The temp absence fr no. */
	//休職休業枠NO
	private BigDecimal tempAbsenceFrNo;
	
	/** The use classification. */
	//使用区分
	private int useClassification;

	/** The temp absence fr name. */
	//休職休業枠名称
	private String tempAbsenceFrName;
}
