package nts.uk.ctx.hr.develop.app.careermgmt.careerpath.command;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.hr.develop.app.careermgmt.careerpath.dto.CareerDto;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.CareerPath;
import nts.uk.shr.com.context.AppContexts;

@Data
public class CareerPartCommand {

	private String historyId;
	
	private Integer careerLevel;
	
	private List<CareerDto> career;
	
	private Boolean isNotice;
	
	public CareerPath toDomain() {
		return CareerPath.createFromJavaType(AppContexts.user().companyId(), this.historyId, this.careerLevel, this.career.stream().map(c->c.toDomain()).collect(Collectors.toList()));
	}
}
