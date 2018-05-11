/**
 * 
 */
package nts.uk.ctx.sys.assist.app.command.deletedata.manualsetting;

import java.util.List;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.assist.dom.deletedata.manualsetting.DelName;
import nts.uk.ctx.sys.assist.dom.deletedata.manualsetting.ManualSetDeletion;
import nts.uk.ctx.sys.assist.dom.deletedata.manualsetting.PasswordCompressFileEncrypt;
import nts.uk.ctx.sys.assist.dom.deletedata.manualsetting.SupplementExplanation;

/**
 * @author hiep.th
 *
 */
@Value
public class ManualSetDelCommand {
	
	private String delName;
	private String suppleExplanation;
	private int systemType;
	private GeneralDate dayStartDate;
	private GeneralDate dayEndDate;
	
	private int monthStartDate;
	private int monthEndDate;
	private int startYear;
	private int endYear;
	
	private int isSaveBeforeDeleteFlg;
	private int isExistCompressPasswordFlg;
	private String passwordForCompressFile;
	private int haveEmployeeSpecifiedFlg;
	private List<EmployeesDeletionCommand> employees;
	private List<CategoryDeletionCommand> category;
	
	public ManualSetDeletion toDomain(String storeProcessingId, String cid, String sid) {
		boolean isSaveBeforeDeleteFlg = this.isSaveBeforeDeleteFlg == 1;
		boolean isExistCompressPasswordFlg = this.isExistCompressPasswordFlg == 1;
		boolean haveEmployeeSpecifiedFlg = this.haveEmployeeSpecifiedFlg == 1;
		return new ManualSetDeletion(storeProcessingId, cid, systemType, new DelName(delName), isSaveBeforeDeleteFlg, 
				isExistCompressPasswordFlg, new PasswordCompressFileEncrypt(passwordForCompressFile), haveEmployeeSpecifiedFlg, 
				sid, new SupplementExplanation(suppleExplanation),
				null, null, dayStartDate, dayEndDate, monthStartDate, monthEndDate, startYear, endYear);			
	}
}
