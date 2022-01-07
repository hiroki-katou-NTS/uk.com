package smilelinked.cooperationoutput;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
@AllArgsConstructor
public class LinkedPaymentConversion extends AggregateRoot {
	private final PaymentCategory paymentCode;
	private List<EmploymentAndLinkedMonthSetting> selectiveEmploymentCodes;
}
