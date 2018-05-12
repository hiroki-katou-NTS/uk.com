package nts.uk.ctx.workflow.app.command.approvermanagement.workroot;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@NoArgsConstructor
public class AddHistoryDto {

	/**申請種類*/
	private List<AppType> lstAppType;
	/**開始日*/
	private String startDate;
	/**開始日 Old*/
	private String startDateOld;
	/**check 申請承認の種類区分*/
	private int mode;
	/**履歴から引き継ぐか、初めから作成するかを選択する*/
	private boolean copyDataFlag;
	private boolean overLap;
}
