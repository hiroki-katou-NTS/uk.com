package nts.uk.ctx.pr.core.pub.wageprovision.processdatecls;

import java.util.Optional;

public interface ProcessDateClassificationPub {
    Optional<ProcessDateClassificationExport> getCurrentProcessYearMonthAndExtraRefDate();
}