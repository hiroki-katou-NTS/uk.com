package nts.uk.screen.at.app.dailyperformance.correction.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkFixedDto {

	/** The closure id. */
	// 締めID
	private Integer closureId;
	
	/** The confirm pid. */
	//確定者ID
	private String confirmPid;

	/** The wkp id. */
	//職場ID
	private String wkpId;
	
	/** The confirm cls status. */
	//確定区分
	private Integer confirmClsStatus;
	
	/** The fixed date. */
	//確定日
	private Date fixedDate;
	
	/** The process date. */
	//処理年月
	private Integer processDate;
	
	/** The cid. */
    //会社ID
    private String cid;
	
}
