package nts.uk.ctx.basic.app.command.organization.workplace;

import java.util.Date;

import lombok.Data;

@Data
public class AddWorkPlaceCommand {

	private String workPlaceCode;

	private String historyId;

	private Date endDate;

	private String externalCode;

	private String fullName;

	private String hierarchyCode;

	private String name;

	private String shortName;

	private Date startDate;

}
