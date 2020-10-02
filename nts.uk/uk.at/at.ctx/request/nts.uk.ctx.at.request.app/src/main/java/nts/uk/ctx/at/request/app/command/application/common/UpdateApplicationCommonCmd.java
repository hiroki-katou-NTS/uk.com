package nts.uk.ctx.at.request.app.command.application.common;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateApplicationCommonCmd {
	
	private AppDispInfoStartupDto appDispInfoStartupOutput;

}
