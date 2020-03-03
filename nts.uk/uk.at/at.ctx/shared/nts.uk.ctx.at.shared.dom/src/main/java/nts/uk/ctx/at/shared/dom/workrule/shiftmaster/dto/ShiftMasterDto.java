package nts.uk.ctx.at.shared.dom.workrule.shiftmaster.dto;

import org.apache.commons.lang3.StringUtils;

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
		this.remark = !StringUtils.isEmpty(remark) ? remark : "" ;
		this.workTypeCd = workTypeCd;
		this.workTypeName = workTypeName;
		this.workTimeCd = !StringUtils.isEmpty(workTimeCd) ? workTimeCd : "" ;
		this.workTimeName = !StringUtils.isEmpty(workTimeName) ? workTimeName : "" ;
		this.workTime1 = "";
		this.workTime2 = "";
	}
	
}
