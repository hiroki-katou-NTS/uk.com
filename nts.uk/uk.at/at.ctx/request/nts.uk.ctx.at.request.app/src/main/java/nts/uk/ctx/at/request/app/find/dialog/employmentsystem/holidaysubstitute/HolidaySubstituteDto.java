package nts.uk.ctx.at.request.app.find.dialog.employmentsystem.holidaysubstitute;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;

/**
 * 
 * @author phongtq
 *
 */
@Data
public class HolidaySubstituteDto {
	
	// 個人社員基本情報
	private EmployeeImport emailDto;
	
	// 残数確認ダイアログDTO
	private RemainNumberConfirmDto remainNumConfirmDto;
	
	// Mode 0 : single; 1 : multi
	private int mode; 
	
	public HolidaySubstituteDto(EmployeeImport emailDto, RemainNumberConfirmDto remainNumConfirmDto) {
		super();
		this.emailDto = emailDto;
		this.remainNumConfirmDto = remainNumConfirmDto;
	}
	
	public HolidaySubstituteDto(EmployeeImport emailDto, RemainNumberConfirmDto remainNumConfirmDto, int mode) {
		super();
		this.emailDto = emailDto;
		this.remainNumConfirmDto = remainNumConfirmDto;
		this.mode = mode;
	}
}
