package nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.qualificationhistory;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Data
public class HoldingQualificationInput {

	GeneralDate baseDate;
	String cId;
	List<String> sIds;
	boolean getEndDate;
	boolean getNameMaster;
	boolean category;
	boolean getRank;
	boolean getnumber;
	boolean getQualifiedOrganization;
}