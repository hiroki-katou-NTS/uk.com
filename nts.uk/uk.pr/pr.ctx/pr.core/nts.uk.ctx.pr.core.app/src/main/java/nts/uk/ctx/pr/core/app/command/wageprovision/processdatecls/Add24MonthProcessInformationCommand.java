package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import lombok.Value;

@Value
public class Add24MonthProcessInformationCommand {
    /**
     * CID
     */
    private String cid;

    /**
     * PROCESS_CATE_NO
     */
    private int processCateNo;

    private int printingMonth;
}
