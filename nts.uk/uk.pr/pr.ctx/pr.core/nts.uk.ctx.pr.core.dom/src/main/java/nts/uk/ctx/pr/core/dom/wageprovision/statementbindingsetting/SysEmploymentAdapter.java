package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;
import java.util.List;

public interface SysEmploymentAdapter {
    List<EmpCdNameImport> findAll(String companyId);
}