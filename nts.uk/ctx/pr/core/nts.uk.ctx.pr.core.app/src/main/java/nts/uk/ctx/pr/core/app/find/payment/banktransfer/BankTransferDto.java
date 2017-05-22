package nts.uk.ctx.pr.core.app.find.payment.banktransfer;

import java.util.List;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class BankTransferDto {
	List<ListOfScreenDDto> listOfScreenDDto;
	List<ListOfScreenDDto> listOfScreenDDto0;
	List<ListOfScreenDDto> listOfScreenDDto1;
	GeneralDate payDate;
}
