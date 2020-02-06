package nts.uk.file.pr.infra.core.empinsqualifiinfo.empinsofficeinfo;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.LaborInsuranceOffice;
import nts.uk.ctx.pr.core.infra.entity.laborinsurance.laborinsuranceoffice.QpbmtLaborInsuOffice;
import nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsofficeinfo.NotifiOfChangInNameInsPerExRepository;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.empinsofficeinfo.EmpEstabInsHist;
import nts.uk.ctx.pr.shared.infra.entity.empinsqualifiinfo.empinsofficeinfo.QqsmtEmpInsEsmHist;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.arc.time.calendar.period.DatePeriod;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class JpaEmpInsReportSettingExRepository extends JpaRepository implements NotifiOfChangInNameInsPerExRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtEmpInsEsmHist f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.empInsEsmHistPk.cid =:cid AND  f.empInsEsmHistPk.sid =:sid AND  f.empInsEsmHistPk.histId =:histId ";
    private static final String SELECT_BY_FILLING_DATE = SELECT_ALL_QUERY_STRING + " WHERE  f.empInsEsmHistPk.cid =:cid AND  f.empInsEsmHistPk.sid =:sid AND f.startDate <= :fillingDate AND f.endDate <= :fillingDate";
    private static final String SELECT_LABORINSUR = "SELECT li FROM QqsmtEmpInsEsmHist f INNER JOIN QpbmtLaborInsuOffice li ON f.empInsEsmHistPk.laborInsCd = li.laborInsuOfficePk.laborOfficeCode AND li.laborInsuOfficePk.cid = f.empInsEsmHistPk.cid " +
            "WHERE  f.empInsEsmHistPk.cid =:cid AND f.empInsEsmHistPk.sid =:sid AND f.startDate <= :fillingDate AND f.endDate >= :fillingDate";

    @Override
    public List<LaborInsuranceOffice> getListEmpInsHistByDate(String cid, String sid, GeneralDate fillingDate) {
        List<LaborInsuranceOffice> listHist =  this.queryProxy().query(SELECT_LABORINSUR, QpbmtLaborInsuOffice.class)
                .setParameter("cid", cid)
                .setParameter("sid", sid)
                .setParameter("fillingDate", fillingDate)
                .getList(item -> item.toDomain());
        return listHist;
    }

}
