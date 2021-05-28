package nts.uk.ctx.at.shared.app.command.workrule.shiftmaster;

import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.*;
import org.apache.commons.lang3.StringUtils;

import lombok.Data;
import nts.uk.shr.com.context.AppContexts;

import java.util.Optional;

/**
 * @author anhdt 職場で使うシフトマスタを登録する
 */
@Data
public class RegisterShiftMasterCommand {
	private String companyId;
	private String shiftMasterName;
	private String shiftMasterCode;
	private String color;
	private String colorSmartphone;
	private String remark;
	private String importCode;
	private String workTypeCd;
	private String workTimeSetCd;
	private Boolean newMode;
	
	public ShiftMaster toDomain() {
		String companyId = AppContexts.user().companyId();
		ShiftMasterCode code = new ShiftMasterCode(shiftMasterCode);
		ShiftMasterName name = new ShiftMasterName(shiftMasterName);
		ColorCodeChar6 colorP = new ColorCodeChar6(color);
		ColorCodeChar6 colorSmartphoneP  = new ColorCodeChar6(colorSmartphone);
		Remarks remarks = !StringUtils.isEmpty(remark) ? new Remarks(remark) : null;
		Optional<ShiftMasterImportCode> importCode = !StringUtils.isEmpty(this.importCode) ? Optional.of(new ShiftMasterImportCode(this.importCode)) : Optional.empty();
		ShiftMasterDisInfor display = new ShiftMasterDisInfor(name, colorP,colorSmartphoneP, remarks);
		return new ShiftMaster(companyId, code, display, importCode, workTypeCd, workTimeSetCd);
	}
}
