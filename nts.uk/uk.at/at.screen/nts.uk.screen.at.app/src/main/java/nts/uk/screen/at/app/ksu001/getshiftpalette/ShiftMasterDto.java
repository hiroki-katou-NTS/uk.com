package nts.uk.screen.at.app.ksu001.getshiftpalette;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterDisInfor;

/**
 * @author laitv
 *
 */
@Data
@NoArgsConstructor
public class ShiftMasterDto {
	private String companyId;
	private String shiftMasterName;
	private String shiftMasterCode;
	private String color;
	private String remark;
	private String workTypeCd;
	private String workTypeName;
	private String workTimeCd;
	private String workTimeName;
	private String workTime1;
	private String workTime2;

	public ShiftMasterDto(ShiftMaster domain) {
		this.companyId = domain.getCompanyId();
		this.shiftMasterCode = domain.getShiftMasterCode().v();
		ShiftMasterDisInfor info = domain.getDisplayInfor();
		this.shiftMasterName = info.getName().v();
		this.color = info.getColor().v();
		this.remark = info.getRemarks().isPresent() ? info.getRemarks().get().v() : null;
		this.workTypeCd = domain.getWorkTypeCode().v();
		this.workTimeCd = domain.getWorkTimeCode() == null ? null : domain.getWorkTimeCode().v();
		this.workTime1 = "";
		this.workTime2 = "";
	}
}
