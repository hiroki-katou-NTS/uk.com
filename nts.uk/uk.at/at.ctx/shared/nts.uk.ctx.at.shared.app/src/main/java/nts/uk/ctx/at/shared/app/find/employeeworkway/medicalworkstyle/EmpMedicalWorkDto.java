package nts.uk.ctx.at.shared.app.find.employeeworkway.medicalworkstyle;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistoryItem;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Setter
public class EmpMedicalWorkDto extends PeregDomainDto{
	
	/**
	 * 社員ID
	 */
	@PeregEmployeeId
	private String sId;

	/**
	 * 期間
	 */
	@PeregItem("IS01090")
	private String period;

	/**
	 * 開始日
	 */
	@Getter
	@PeregItem("IS01091")
	private GeneralDate startDate;

	/**
	 * 終了日
	 */
	@PeregItem("IS01092")
	private GeneralDate endDate;
	
	/**
	 * 夜勤専従者として扱う
	 */
	@PeregItem("IS01093")
	private int isOnlyNightShift;
	
	/**
	 * 医療勤務形態
	 */
	@PeregItem("IS01094")
	private int medicalWorkStyle;
	
	/**
	 * 他部署の兼務
	 */
	@PeregItem("IS01095")
	private int isConcurrently;
	
	/**
	 * 看護区分
	 */
	@PeregItem("IS01096")
	private String nurseClassifiCode;
	
	public EmpMedicalWorkDto(String recordId) {
		super(recordId);
	}
	
	public static EmpMedicalWorkDto createEmpMedicalWorkDto(String employeeId, DateHistoryItem dateHistoryItem, 
			EmpMedicalWorkStyleHistoryItem empMedicalWorkItem) {
		EmpMedicalWorkDto dto = new EmpMedicalWorkDto(dateHistoryItem.identifier());
		dto.setSId(employeeId);
		dto.setStartDate(dateHistoryItem.start());
		dto.setEndDate(dateHistoryItem.end());
		
		dto.setIsOnlyNightShift(empMedicalWorkItem.isOnlyNightShift() ? 1 : 0);
		
		dto.setIsConcurrently(empMedicalWorkItem.isConcurrently() ? 1 : 0);
		
		dto.setMedicalWorkStyle(empMedicalWorkItem.getMedicalWorkStyle().value);
		
		dto.setNurseClassifiCode(empMedicalWorkItem.getNurseClassifiCode().v());
		
		return dto;
	}
	
	public static EmpMedicalWorkDto createEmpMedicalWorkDtoCps013(String employeeId, DateHistoryItem dateHistoryItem, 
			EmpMedicalWorkStyleHistoryItem empMedicalWorkItem, Map<String, Object> enums) {
		return null;
	}
}
