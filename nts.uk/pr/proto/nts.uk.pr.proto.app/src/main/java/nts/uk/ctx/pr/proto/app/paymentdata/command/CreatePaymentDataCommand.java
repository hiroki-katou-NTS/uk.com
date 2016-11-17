package nts.uk.ctx.pr.proto.app.paymentdata.command;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.core.dom.company.Company;
import nts.uk.ctx.pr.proto.dom.paymentdata.Payment;

/**
 * Command: add payment data
 * @author chinhbv
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class CreatePaymentDataCommand {
	private List<String> personIdList;
}
