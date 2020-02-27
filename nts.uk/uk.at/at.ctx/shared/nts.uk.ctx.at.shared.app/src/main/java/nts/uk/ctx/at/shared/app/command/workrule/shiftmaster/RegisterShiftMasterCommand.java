package nts.uk.ctx.at.shared.app.command.workrule.shiftmaster;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ColorCodeChar6;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.Remarks;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterDisInfor;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterName;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author anhdt 職場で使うシフトマスタを登録する
 */
@Data
public class RegisterShiftMasterCommand {
	private String companyId;
	private String shiftMasterName;
	private String shiftMasterCode;
	private String color;
	private String remark;
	private String workTypeCd;
	private String workTimeSetCd;
	private Boolean newMode;
	
	public ShiftMaster toDomain() {
		String companyId = AppContexts.user().companyId();
		ShiftMasterCode code = new ShiftMasterCode(shiftMasterCode);
		ShiftMasterName name = new ShiftMasterName(shiftMasterName);
		ColorCodeChar6 colorP = new ColorCodeChar6(color);
		Remarks remarks = !StringUtils.isEmpty(remark) ? new Remarks(remark) : null;
		ShiftMasterDisInfor display = new ShiftMasterDisInfor(name, colorP, remarks);
		return new ShiftMaster(companyId, code, display, workTypeCd, workTimeSetCd);
	}
}
