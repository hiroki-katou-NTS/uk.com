package nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.qualificationhistory;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Data
public class HoldingQualificationInput {

	private GeneralDate baseDate;
	private String cId;
	private List<String> sIds;
	private boolean getEndDate;
	private boolean getNameMaster;
	private boolean category;
	private boolean getRank;
	private boolean getnumber;
	private boolean getQualifiedOrganization;
}