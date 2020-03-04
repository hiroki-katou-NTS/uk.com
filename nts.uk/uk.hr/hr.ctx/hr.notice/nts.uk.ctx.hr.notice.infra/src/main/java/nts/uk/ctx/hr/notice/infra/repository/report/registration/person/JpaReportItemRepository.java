package nts.uk.ctx.hr.notice.infra.repository.report.registration.person;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ReportItem;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ReportItemRepository;
import nts.uk.ctx.hr.notice.infra.entity.report.registration.person.JhndtReportItem;
import nts.uk.ctx.hr.notice.infra.entity.report.registration.person.JhndtReportItemPK;

/**
 * @author laitv
 *
 */
@Stateless
public class JpaReportItemRepository extends JpaRepository implements ReportItemRepository{

	private static final String getDetailReport = "select c FROM  JhndtReportItem c Where c.pk.cid = :cid and c.pk.reportID = :reportId  ORDER BY c.dspOrder ASC";
	private static final String deleteListApprovalByReportId = "delete FROM JhndtReportItem c Where c.pk.cid = :cid and c.pk.reportID = :reportId";

	private ReportItem toDomain(JhndtReportItem entity) {
		return entity.toDomain();
	}
	
	private JhndtReportItem toEntity(ReportItem domain) {
		JhndtReportItem entity = new JhndtReportItem();
		JhndtReportItemPK pk   = new JhndtReportItemPK(domain.getCtgCode(), domain.getItemCd(), domain.getCid(), domain.getReportID());
		entity.pk 		       = pk;
		entity.workId          = domain.getWorkId();
		entity.reportLayoutID  = domain.getReportLayoutID();
		entity.reportName      = domain.getReportName(); 
		entity.layoutItemType  = domain.getLayoutItemType().value;
		entity.categoryId      = domain.getCategoryId();
		entity.ctgName         = domain.getCtgName(); 
		entity.fixedAtr        = domain.isFixedAtr(); 
		entity.itemId          = domain.getItemId();
		entity.itemName        = domain.getItemName(); 
		entity.dspOrder        = domain.getDspOrder();
		entity.layoutDisOrder  = domain.getLayoutDisOrder();
		entity.reflectID       = domain.getReflectID();
		entity.contractCode    = domain.getContractCode();
		entity.saveDataAtr     = domain.getSaveDataAtr();
		switch (domain.getSaveDataAtr()) {
		case 1:
			entity.stringVal = domain.getStringVal();
			break;
		case 2:
			entity.intVal    = domain.getIntVal();
			break;
		case 3:
			entity.dateVal = domain.getDateVal();
			break;
		}
		return entity;
	}
	
	@Override
	public List<ReportItem> getDetailReport(String cid, int reportId) {
		return this.queryProxy().query(getDetailReport, JhndtReportItem.class)
				.setParameter("cid", cid)
				.setParameter("reportId", reportId)
				.getList(c -> toDomain(c));
	}

	@Override
	public boolean checkExit(String cid, int reportId, String ctgCode, String itemCd) {
		Optional<JhndtReportItem> entityOpt = this.queryProxy().find(new JhndtReportItemPK(ctgCode, itemCd ,cid, reportId), JhndtReportItem.class);
		if (entityOpt.isPresent()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void registerItemData(ReportItem domain) {
		boolean isExit =  this.checkExit(domain.getCid(), domain.getReportID(), domain.getCtgCode(), domain.getItemCd());
		if (isExit) {
			update(domain);
		}else{
			add(domain);
		}
	}
	
	@Override
	public void add(ReportItem domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(ReportItem domain) {
		Optional<JhndtReportItem> entityOpt = this.queryProxy().find(new JhndtReportItemPK(domain.getCtgCode(), domain.getItemCd() ,domain.getCid(), domain.getReportID()), JhndtReportItem.class);
		if (entityOpt.isPresent()) {
			JhndtReportItem entity = entityOpt.get();
			switch (entity.saveDataAtr) {
			case 1:
				entity.stringVal = domain.getStringVal();
				break;
			case 2:
				entity.intVal    = domain.getIntVal();
				break;
			case 3:
				entity.dateVal = domain.getDateVal();
				break;
			}
			this.commandProxy().update(entity);
		}
	}

	@Override
	public void addAll(List<ReportItem> domains) {
		List<JhndtReportItem> entities = domains.stream().map(dm -> toEntity(dm)).collect(Collectors.toList());
		this.commandProxy().insertAll(entities);
	}

	@Override
	public void deleteByReportId(String cid, int reportId) {
		this.getEntityManager().createQuery(deleteListApprovalByReportId)
		.setParameter("cid", cid)
		.setParameter("reportId", reportId).executeUpdate();
	}
}
