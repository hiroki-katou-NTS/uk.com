package nts.uk.ctx.pr.shared.dom.adapter.wageprovision.processdatecls;

import java.util.Optional;

public interface ProcessDateClassificationAdapter {
    Optional<ProcessDateClassificationImport> getCurrentProcessYearMonthAndExtraRefDate();
}