/**
 * 
 */
package nts.uk.ctx.sys.assist.app.command.deletedata.manualsetting;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.dom.deletedata.BusinessName;
import nts.uk.ctx.sys.assist.dom.deletedata.CategoryDeletion;
import nts.uk.ctx.sys.assist.dom.deletedata.DelName;
import nts.uk.ctx.sys.assist.dom.deletedata.EmployeeCode;
import nts.uk.ctx.sys.assist.dom.deletedata.EmployeeDeletion;
import nts.uk.ctx.sys.assist.dom.deletedata.ManualSetDeletion;
import nts.uk.ctx.sys.assist.dom.deletedata.PasswordCompressFileEncrypt;
import nts.uk.ctx.sys.assist.dom.deletedata.SupplementExplanation;
import nts.uk.ctx.sys.assist.dom.storage.PatternCode;
import nts.uk.ctx.sys.assist.dom.storage.StorageClassification;

/**
 * @author hiep.th
 *
 */
@Setter
@Getter
public class ManualSetDelCommand {
	
	private String delName;
	private String suppleExplanation;
	private GeneralDate referenceDate;
	private GeneralDateTime executionDateAndTime;
	private GeneralDate dayStartDate;
	private GeneralDate dayEndDate;
	
	private GeneralDate monthStartDate;
	private GeneralDate monthEndDate;
	private Integer startYear;
	private Integer endYear;
	
	private int isSaveBeforeDeleteFlg;
	private int isExistCompressPasswordFlg;
	private String passwordForCompressFile;
	private int haveEmployeeSpecifiedFlg;
	private String delPattern;
	private List<EmployeesDeletionCommand> employees;
	private List<CategoryDeletionCommand> categories;
	
	public ManualSetDeletion toDomain(String delId, String cid, String sid) {
		boolean isSaveBeforeDeleteFlg = this.isSaveBeforeDeleteFlg == 1;
		boolean isExistCompressPasswordFlg = this.isExistCompressPasswordFlg == 1;
		boolean haveEmployeeSpecifiedFlg = this.haveEmployeeSpecifiedFlg == 1;
		return new ManualSetDeletion(delId, cid, new DelName(delName), isSaveBeforeDeleteFlg, 
				isExistCompressPasswordFlg, Optional.ofNullable(new PasswordCompressFileEncrypt(passwordForCompressFile)), 
				haveEmployeeSpecifiedFlg, 
				sid, Optional.ofNullable(new SupplementExplanation(suppleExplanation)),
				Optional.ofNullable(referenceDate), executionDateAndTime, 
				Optional.ofNullable(dayStartDate), Optional.ofNullable(dayEndDate), 
				Optional.ofNullable(monthStartDate), Optional.ofNullable(monthEndDate), 
				Optional.ofNullable(startYear), Optional.ofNullable(endYear),
				StorageClassification.MANUAL, new PatternCode(delPattern), null);			
	}
	
	public List<EmployeeDeletion> getEmployees(String delId) {
		return employees.stream().map(x -> {
			return new EmployeeDeletion(delId, x.getEmployeeId(), new EmployeeCode(x.getEmployeeCode()),
					new BusinessName(x.getBusinessName()));
		}).collect(Collectors.toList());
	}
	
	
	public List<CategoryDeletion> getCategories(String delId) {
		return categories.stream().map(x -> {
			return new CategoryDeletion(delId, x.getCategoryId(), x.getPeriodDeletion(), x.getSystemType());
		}).collect(Collectors.toList());
	}
}