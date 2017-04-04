package nts.uk.ctx.basic.app.find.organization.workplace;

import java.math.BigDecimal;
import java.util.ArrayList;

import lombok.Data;
import nts.arc.time.GeneralDate;


@Data
public class WorkPlaceDto extends OrganizationTreeDto<WorkPlaceDto> {

	private String companyCode;

	private String departmentCode;

	private String historyId;

	private GeneralDate endDate;

	private String externalCode;

	private String genericName;

	private String hierarchyCode;

	private String name;

	private BigDecimal parentChildAttribute1;

	private BigDecimal parentChildAttribute2;

	private String parentWorkCode1;

	private String parentWorkCode2;

	private String shortName;

	private GeneralDate startDate;

	public WorkPlaceDto(String workPlaceCode, GeneralDate endDate, String externalCode, String genericName, String hierarchyCode,
			String name, BigDecimal parentChildAttribute1, BigDecimal parentChildAttribute2, String parentWorkCode1,
			String parentWorkCode2, String shortName, GeneralDate startDate) {
		super();
		this.departmentCode = workPlaceCode;
		this.endDate = endDate;
		this.externalCode = externalCode;
		this.genericName = genericName;
		this.hierarchyCode = hierarchyCode;
		this.name = name;
		this.parentChildAttribute1 = parentChildAttribute1;
		this.parentChildAttribute2 = parentChildAttribute2;
		this.parentWorkCode1 = parentWorkCode1;
		this.parentWorkCode2 = parentWorkCode2;
		this.shortName = shortName;
		this.startDate = startDate;
	}

	public WorkPlaceDto(String workPlaceCode, String externalCode, String genericName, String hierarchyCode, String name) {
		super();
		this.departmentCode = workPlaceCode;
		this.externalCode = externalCode;
		this.genericName = genericName;
		this.hierarchyCode = hierarchyCode;
		this.name = name;
		this.children = new ArrayList<>();
	}
}
