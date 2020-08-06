package nts.uk.screen.at.app.query.kcp013;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcquireWorkHours {
	private String code;
	private String name;
	private int tzStart1;
	private int tzEnd1;
	private int tzStart2;
	private int tzEnd2;
	private String workStyleClassfication;
	private String remark;
	private int useDistintion;

}
