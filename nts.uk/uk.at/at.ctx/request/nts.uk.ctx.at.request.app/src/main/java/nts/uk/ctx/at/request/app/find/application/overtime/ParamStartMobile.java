package nts.uk.ctx.at.request.app.find.application.overtime;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.application.overtime.AppOverTimeCommand;
import nts.uk.ctx.at.request.app.command.application.overtime.DisplayInfoOverTimeCommand;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;


@AllArgsConstructor
@NoArgsConstructor
public class ParamStartMobile {
	
	public Boolean mode;
	
	public String companyId;
	
	public String employeeIdOptional;
	
	public String dateOptional;
	
	public DisplayInfoOverTimeCommand disOptional;
	
	public AppOverTimeCommand appOptional;
	
	public AppDispInfoStartupDto appDispInfoStartupOutput;
	
	public Integer overtimeAppAtr;
}
