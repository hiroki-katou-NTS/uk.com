package nts.uk.ctx.at.function.dom.processexecution.repository;


import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogHistory;

public interface ProcessExecutionLogHistRepository {
	public Optional<ProcessExecutionLogHistory> getByExecId(String companyId, String execItemCd, String execId);
	public void insert(ProcessExecutionLogHistory domain);
	public void update(ProcessExecutionLogHistory domain);
	public void remove(String companyId, String execItemCd);
	public List<ProcessExecutionLogHistory> getByDate(String companyId,String execItemCd, GeneralDateTime prevExecDateTime);
	public List<ProcessExecutionLogHistory> getByDateRange(String companyId, String execItemCd,
			GeneralDateTime startDate,GeneralDateTime endDate);
	public List<ProcessExecutionLogHistory> getByCompanyIdAndDateAndEmployeeName(String companyId, GeneralDateTime startDate, GeneralDateTime endDate);
	public List<ProcessExecutionLogHistory> getByCompanyIdAndExecItemCd(String companyId, String execItemCd);
	public Optional<ProcessExecutionLogHistory> getByExecId(String execId);
}
