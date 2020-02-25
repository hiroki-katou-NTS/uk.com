package nts.uk.ctx.at.shared.dom.workrule.shiftmaster.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterDisInfor;

/**
 * @author anhdt
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
	}

	public ShiftMasterDto (String companyId, String shiftMasterName, String shiftMaterCode, String color, String remark, String workTypeCd, String workTypeName, String workTimeCd, String workTimeName) {
		this.companyId = companyId;
		this.shiftMasterName = shiftMasterName;
		this.shiftMasterCode = shiftMaterCode;
		this.color = color;
		this.remark = remark;
		this.workTypeCd = workTimeCd;
		this.workTypeName = workTypeName;
		this.workTimeCd = workTimeCd;
		this.workTimeName = workTimeName;
	}
	
}
