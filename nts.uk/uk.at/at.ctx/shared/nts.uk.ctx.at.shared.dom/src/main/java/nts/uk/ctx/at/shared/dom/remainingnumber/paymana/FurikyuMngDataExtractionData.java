package nts.uk.ctx.at.shared.dom.remainingnumber.paymana;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FurikyuMngDataExtractionData {
	private List<PayoutManagementData> payoutManagementData;
	private List<SubstitutionOfHDManagementData> substitutionOfHDManagementData;
	private List<PayoutSubofHDManagement> payoutSubofHDManagementLinkToPayout;
	private List<PayoutSubofHDManagement> payoutSubofHDManagementLinkToSub;
	private int expirationDate;
	private Double numberOfDayLeft;
	private Integer closureId;
}
