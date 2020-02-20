package nts.uk.ctx.at.shared.app.find.workrule.shiftmaster;

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
	private String shiftMaterCode;
	private String color;
	private String remark;
	private String workTypeCd;
	private String workTypeName;
	
	public ShiftMasterDto(ShiftMaster domain) {
		this.companyId = domain.getCompanyId();
		this.shiftMaterCode = domain.getShiftMaterCode().v();
		ShiftMasterDisInfor info = domain.getDisplayInfor();
		this.shiftMasterName = info.getName().v();
		this.color = info.getColor().v();
		this.remark = info.getRemarks().isPresent() ? info.getRemarks().get().v() : null;
	}
}
