package nts.uk.ctx.pr.core.pub.emprsdttaxinfo;

import java.util.List;

public interface PayeeInfoPub {
    List<PayeeInfoExport> getListPayeeInfo(List<String> listHistId);
}
