package nts.uk.screen.at.app.mobi;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ToppageOvertimeData {
	private List<AgreementTimeToppage> agreementTimeToppageLst;
	private Integer dataStatus;
    private boolean visible;
    private Integer yearMonth;
}
