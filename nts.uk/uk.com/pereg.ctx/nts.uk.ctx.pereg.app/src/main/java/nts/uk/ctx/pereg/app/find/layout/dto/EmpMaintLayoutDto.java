/**
 * 
 */
package nts.uk.ctx.pereg.app.find.layout.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsDto;
import nts.uk.ctx.pereg.dom.person.layout.MaintenanceLayout;

/**
 * @author danpv
 *
 */
@Data

public class EmpMaintLayoutDto {

	private String companyId;

	private String maintenanceLayoutID;

	private String layoutCode;

	private String layoutName;
	
	private GeneralDate standardDate;

	private List<LayoutPersonInfoClsDto> classificationItems;
	
	public EmpMaintLayoutDto(){
		this.classificationItems = new ArrayList<>();
	}
	
	public EmpMaintLayoutDto(List<LayoutPersonInfoClsDto> classificationItems) {
		this.classificationItems = classificationItems;
	}

	public static EmpMaintLayoutDto createFromDomain(MaintenanceLayout domain) {
		EmpMaintLayoutDto dto = new EmpMaintLayoutDto();
		dto.setCompanyId(domain.getCompanyId());
		dto.setLayoutCode(domain.getLayoutCode().v());
		dto.setLayoutName(domain.getLayoutName().v());
		dto.setMaintenanceLayoutID(domain.getMaintenanceLayoutID());
		return dto;
	}
	
	

}
