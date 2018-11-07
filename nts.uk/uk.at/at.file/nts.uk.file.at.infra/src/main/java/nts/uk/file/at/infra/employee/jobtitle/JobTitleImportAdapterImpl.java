/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.at.infra.employee.jobtitle;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.SneakyThrows;
import lombok.val;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.file.at.app.export.employee.jobtitle.EmployeeJobHistExport;
import nts.uk.file.at.app.export.employee.jobtitle.JobTitleImportAdapter;
import nts.uk.file.at.app.export.employee.jobtitle.SimpleJobTitleExport;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class JobTitleImportAdapterImpl.
 */
@Stateless
public class JobTitleImportAdapterImpl extends JpaRepository implements JobTitleImportAdapter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.file.at.app.export.employee.jobtitle.JobTitleImportAdapter#
	 * findBySid(java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	@SneakyThrows
	public Optional<EmployeeJobHistExport> findBySid(String employeeId, GeneralDate baseDate) {
		// Get information of employee
		String companyId = AppContexts.user().companyId();

		String sqlJdbc = "select * from "
				+ "(select BAJH.SID, BAJH.START_DATE, BAJH.END_DATE, BAJHI.JOB_TITLE_ID "
				+ "from BSYMT_AFF_JOB_HIST BAJH " + "left join BSYMT_AFF_JOB_HIST_ITEM BAJHI "
				+ "on BAJH.HIST_ID = BAJHI.HIST_ID "
				+ "where BAJH.SID = ? and BAJH.START_DATE <= ? and BAJH.END_DATE >= ? " + ") A "
				+ "left join " + "(select BJI.JOB_ID, BJI.JOB_NAME " + "from BSYMT_JOB_INFO BJI "
				+ "left join BSYMT_JOB_HIST BJH "
				+ "on BJI.CID = BJH.CID AND BJI.HIST_ID = BJH.HIST_ID "
				+ "where BJI.CID = ? and BJH.START_DATE <= ? and BJH.END_DATE >= ?" + ") B "
				+ "on A.JOB_TITLE_ID = B.JOB_ID";

		try (val statement = this.connection().prepareStatement(sqlJdbc)) {
			statement.setString(1, employeeId);
			statement.setDate(2, Date.valueOf(baseDate.toLocalDate()));
			statement.setDate(3, Date.valueOf(baseDate.toLocalDate()));
			statement.setString(4, companyId);
			statement.setDate(5, Date.valueOf(baseDate.toLocalDate()));
			statement.setDate(6, Date.valueOf(baseDate.toLocalDate()));

			return new NtsResultSet(statement.executeQuery()).getSingle(rec -> EmployeeJobHistExport
					.builder().employeeId(rec.getString("SID")).jobTitleID(rec.getString("JOB_ID"))
					.jobTitleName(rec.getString("JOB_NAME"))
					.startDate(rec.getGeneralDate("START_DATE"))
					.endDate(rec.getGeneralDate("END_DATE")).build());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.file.at.app.export.employee.jobtitle.JobTitleImportAdapter#
	 * findByIds(java.lang.String, java.util.List, nts.arc.time.GeneralDate)
	 */
	@Override
	public List<SimpleJobTitleExport> findByIds(String companyId, List<String> jobIds,
			GeneralDate baseDate) {
		List<SimpleJobTitleExport> result = new ArrayList<>();
		CollectionUtil.split(jobIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			String sqlJdbc = "select BJI.JOB_ID, BJI.JOB_CD, BJI.JOB_NAME, BJSM.DISPORDER"
					+ "from BSYMT_JOB_INFO BJI " + "left join BSYMT_JOB_HIST BJH "
					+ "on BJI.CID = BJH.CID AND BJI.HIST_ID = BJH.HIST_ID "
					+ "left join BSYMT_JOB_SEQ_MASTER BJSM "
					+ "on BJI.CID = BJSM.CID AND BJI.SEQUENCE_CD = BJSM.SEQ_CD"
					+ "where BJI.CID = ? and BJH.START_DATE <= ? and BJH.END_DATE >= ? and BJI.JOB_ID in ("
					+ NtsStatement.In.createParamsString(subList) + ")";

			try (val statement = this.connection().prepareStatement(sqlJdbc)) {
				statement.setString(1, companyId);
				statement.setDate(2, Date.valueOf(baseDate.toLocalDate()));
				statement.setDate(3, Date.valueOf(baseDate.toLocalDate()));
				for (int i = 0; i < subList.size(); i++) {
					statement.setString(i + 4, subList.get(i));
				}

				result.addAll(new NtsResultSet(statement.executeQuery()).getList(
						rec -> SimpleJobTitleExport.builder().jobTitleId(rec.getString("JOB_ID"))
								.jobTitleCode(rec.getString("JOB_CD"))
								.jobTitleName(rec.getString("JOB_NAME"))
								.disporder(rec.getInt("DISPORDER")).build()));
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});

		return result;
	}
}
