package nts.uk.ctx.at.shared.app.query.scherec.dailyattdcal.declare;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OvertimeWorkFrameDto {
	/** The company id. */
	// 会社ID
	private String companyId;
	
	/** The overtime work fr no. */
	//残業枠NO
	private BigDecimal overtimeWorkFrNo;
	
	/** The use classification. */
	//使用区分
	private Integer useClassification;
	
	/** The transfer fr name. */
	//振替枠名称
	private String transferFrName;
	
	/** The overtime work fr name. */
	//残業枠名称
	private String overtimeWorkFrName;
	
	/** The role. */
	// 役割
	private Integer role;
	
	/** The transfer atr. */
	// 代休振替対象
	private Integer transferAtr;
}
