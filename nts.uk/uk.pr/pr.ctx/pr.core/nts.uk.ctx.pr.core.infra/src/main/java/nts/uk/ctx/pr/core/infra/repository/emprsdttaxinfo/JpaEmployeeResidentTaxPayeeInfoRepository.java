package nts.uk.ctx.pr.core.infra.repository.emprsdttaxinfo;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.emprsdttaxinfo.EmployeeResidentTaxPayeeInfo;
import nts.uk.ctx.pr.core.dom.emprsdttaxinfo.EmployeeResidentTaxPayeeInfoRepository;
import nts.uk.ctx.pr.core.dom.emprsdttaxinfo.PayeeInfo;
import nts.uk.ctx.pr.core.infra.entity.emprsdttaxinfo.QpbmtEmpRsdtTaxPayee;

import javax.ejb.Stateless;
import java.util.Collections;
import java.util.List;

@Stateless
public class JpaEmployeeResidentTaxPayeeInfoRepository extends JpaRepository
		implements EmployeeResidentTaxPayeeInfoRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtEmpRsdtTaxPayee f";
	private static final String SELECT_BY_SID = SELECT_ALL_QUERY_STRING + " WHERE  f.empRsdtTaxPayeePk.sid IN :listSId "
			+ " AND  f.startYM <= :periodYM AND f.endYM >= :periodYM ";
	private static final String SELECT_BY_HIST_ID_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.empRsdtTaxPayeePk.histId =:histId ";
	private static final String SELECT_BY_HIST_IDS_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.empRsdtTaxPayeePk.histId IN :listHistId ";

	@Override
	public List<EmployeeResidentTaxPayeeInfo> getEmpRsdtTaxPayeeInfo(List<String> listSId, YearMonth periodYM) {
		if (listSId == null || listSId.isEmpty())
			return Collections.emptyList();
		return QpbmtEmpRsdtTaxPayee.toDomain(this.queryProxy().query(SELECT_BY_SID, QpbmtEmpRsdtTaxPayee.class)
				.setParameter("listSId", listSId).setParameter("periodYM", periodYM.v()).getList());
	}

	@Override
	public List<EmployeeResidentTaxPayeeInfo> getEmpRsdtTaxPayeeInfo(List<String> listSId, List<String> taxPayeeCodes) {
		if (listSId == null || listSId.isEmpty() || taxPayeeCodes == null || taxPayeeCodes.isEmpty())
			return Collections.emptyList();
		String query = SELECT_ALL_QUERY_STRING
				+ " WHERE  f.empRsdtTaxPayeePk.sid IN :listSId AND f.residentTaxPayeeCd IN :listTaxPayeeCode";
		return QpbmtEmpRsdtTaxPayee.toDomain(this.queryProxy().query(query, QpbmtEmpRsdtTaxPayee.class)
				.setParameter("listSId", listSId).setParameter("listTaxPayeeCode", taxPayeeCodes).getList());
	}

	@Override
	public List<PayeeInfo> getListPayeeInfo(List<String> listHistId) {
		if (listHistId == null || listHistId.isEmpty()) return Collections.emptyList();
		return this.queryProxy().query(SELECT_BY_HIST_IDS_STRING, QpbmtEmpRsdtTaxPayee.class)
				.setParameter("listHistId", listHistId)
				.getList(QpbmtEmpRsdtTaxPayee::toPayeeInfo);
	}

	@Override
	public void updatePayeeInfo(PayeeInfo payeeInfo) {
		List<QpbmtEmpRsdtTaxPayee> entitys = this.queryProxy().query(SELECT_BY_HIST_ID_STRING, QpbmtEmpRsdtTaxPayee.class)
				.setParameter("histId", payeeInfo.getHistId()).getList();
		for (QpbmtEmpRsdtTaxPayee entity : entitys) {
			entity.residentTaxPayeeCd = payeeInfo.getResidentTaxPayeeCd().v();
		}
		this.commandProxy().updateAll(entitys);
	}

}
