package nts.uk.ctx.at.shared.app.query.scherec.dailyattdcal.declare;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkdayoffFrameDto {
	/** The company id. */
	// 会社ID
	private String companyId;
	
	/** The workdayoff fr no. */
	//休出枠NO
	private BigDecimal workdayoffFrNo;
	
	/** The use classification. */
	//使用区分
	private Integer useClassification;
	
	/** The transfer fr name. */
	//振替枠名称
	private String transferFrName;
	
	/** The workdayoff fr name. */
	//休出枠名称
	private String workdayoffFrName;
	
	/** The role */
	//役割
	private Integer role;
}
