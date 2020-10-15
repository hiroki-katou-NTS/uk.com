package nts.uk.ctx.at.request.app.find.application.lateleaveearly.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoNoDateDto;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoWithDateDto;
import nts.uk.ctx.at.request.app.find.application.lateleaveearly.LateEarlyCancelAppSetDto;

@Data
@AllArgsConstructor
public class ChangeAppDateDto {
	private int appType;
	
	private List<String> appDates;
	
	private String baseDate;
	
	private AppDispInfoNoDateDto appDispNoDate;
	
	private AppDispInfoWithDateDto appDispWithDate;
	
	private LateEarlyCancelAppSetDto setting;
}
