package nts.uk.screen.at.app.query.kcp013;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcquireWorkingHoursDto {
	private String code;
	private String name;
	private String tzStart1;
	private String tzEnd1;
	private String tzStart2;
	private String tzEnd2;
	private String workStyleClassfication;
	private String remark;
	private int useDistintion;

}
