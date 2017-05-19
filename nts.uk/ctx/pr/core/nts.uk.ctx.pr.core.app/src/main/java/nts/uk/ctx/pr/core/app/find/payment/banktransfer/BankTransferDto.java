package nts.uk.ctx.pr.core.app.find.payment.banktransfer;

import java.util.List;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class BankTransferDto {
	List<ListOfScreenDDto> listOfScreenDDto;
	GeneralDate payDate;
}
