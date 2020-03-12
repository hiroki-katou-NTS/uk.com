package nts.uk.ctx.at.schedule.app.find.employeeinfo.medicalworkstyle;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author ThanhNX
 *
 */
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class NurseDetailCategoryDto extends NurseCategoryDto {

	/**
	 * 免許区分
	 */
	private int license;

	/**
	 * 事務的業務従事者か
	 */
	private boolean officeWorker;

	public NurseDetailCategoryDto(String nurseCode, String nurseClassifiName, int license, boolean officeWorker) {
		super(nurseCode, nurseClassifiName);
		this.license = license;
		this.officeWorker = officeWorker;
	}

}
