package nts.uk.ctx.basic.app.find.organization.workplace;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import lombok.Data;
import nts.uk.ctx.basic.app.find.organization.department.OrganizationTreeDto;

@Data
public class WorkPlaceDto extends OrganizationTreeDto<WorkPlaceDto>{

	private String companyCode;

	private String workPlaceCode;

	private String historyId;

	private Date endDate;

	private String externalCode;

	private String fullName;

	private String hierarchyCode;

	private String name;

	private BigDecimal parentChildAttribute1;

	private BigDecimal parentChildAttribute2;

	private String parentWorkCode1;

	private String parentWorkCode2;

	private String shortName;

	private Date startDate;

	public WorkPlaceDto(String workPlaceCode, Date endDate, String externalCode, String fullName, String hierarchyCode,
			String name, BigDecimal parentChildAttribute1, BigDecimal parentChildAttribute2, String parentWorkCode1,
			String parentWorkCode2, String shortName, Date startDate) {
		super();
		this.workPlaceCode = workPlaceCode;
		this.endDate = endDate;
		this.externalCode = externalCode;
		this.fullName = fullName;
		this.hierarchyCode = hierarchyCode;
		this.name = name;
		this.parentChildAttribute1 = parentChildAttribute1;
		this.parentChildAttribute2 = parentChildAttribute2;
		this.parentWorkCode1 = parentWorkCode1;
		this.parentWorkCode2 = parentWorkCode2;
		this.shortName = shortName;
		this.startDate = startDate;
	}

	public WorkPlaceDto(String workPlaceCode, String externalCode, String fullName, String hierarchyCode, String name) {
		super();
		this.workPlaceCode = workPlaceCode;
		this.externalCode = externalCode;
		this.fullName = fullName;
		this.hierarchyCode = hierarchyCode;
		this.name = name;
		this.children = new ArrayList<>();
	}

}
