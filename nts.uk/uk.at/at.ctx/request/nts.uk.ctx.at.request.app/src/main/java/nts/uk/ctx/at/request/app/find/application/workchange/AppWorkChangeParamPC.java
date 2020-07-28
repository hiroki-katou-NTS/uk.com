package nts.uk.ctx.at.request.app.find.application.workchange;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AppWorkChangeParamPC {
	
	private List<String> empLst;
	
	private List<String> dateLst;
	
	private AppDispInfoStartupDto appDispInfoStartupOutput;
	
}
