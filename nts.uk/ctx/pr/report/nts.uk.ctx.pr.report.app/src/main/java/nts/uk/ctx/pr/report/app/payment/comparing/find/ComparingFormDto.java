package nts.uk.ctx.pr.report.app.payment.comparing.find;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComparingFormDto {

	private List<ComparingItemMasterDto> lstItemMasterForTab_0;
	private List<ComparingItemMasterDto> lstItemMasterForTab_1;
	private List<ComparingItemMasterDto> lstItemMasterForTab_3;
	private List<String> lstSelectForTab_0;
	private List<String> lstSelectForTab_1;
	private List<String> lstSelectForTab_3;
}
