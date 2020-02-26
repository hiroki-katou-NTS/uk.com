package nts.uk.ctx.hr.notice.infra.repository.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.hr.notice.dom.report.PersonalReportClassification;
import nts.uk.ctx.hr.notice.dom.report.PersonalReportClassificationRepository;
import nts.uk.ctx.hr.notice.infra.entity.report.JhnmtRptLayout;
import nts.uk.ctx.hr.notice.infra.entity.report.JhnmtRptLayoutPk;

@Stateless
public class JpaPersonalReportClassificationRepository extends JpaRepository implements PersonalReportClassificationRepository{
	private final static String SEL_BY_CID = "SELECT c FROM JhnmtRptLayout c WHERE c.jhnmtRptLayoutPk.cid =:cid order by c.rptLayouNameYomi";
	
	private final static String SEL_BY_CID_AND_ABOLITON = "SELECT c FROM JhnmtRptLayout c WHERE c.jhnmtRptLayoutPk.cid =:cid AND c.abolition =:abolition order by c.rptLayouNameYomi";
	
	private final static String IS_DUPLICATE_LAYOUTCODE = "SELECT c FROM JhnmtRptLayout c WHERE c.jhnmtRptLayoutPk.cid =:cid AND c.rptLayouCd =:rptLayouCd order by c.rptLayouNameYomi";
	
	private final static String IS_DUPLICATE_LAYOUT_NAME = "SELECT c FROM JhnmtRptLayout c WHERE c.jhnmtRptLayoutPk.cid =:cid AND c.rptLayouName =:rptLayouName order by c.rptLayouNameYomi";
	
	private final static String MAX_ID_BY_CID = "SELECT MAX(c.jhnmtRptLayoutPk.rptLayoutId) FROM JhnmtRptLayout c WHERE c.jhnmtRptLayoutPk.cid =:cid";
	
	private final static String MAX_DIS_ORDER_BY_CID = "SELECT MAX(c.displayOrder) FROM JhnmtRptLayout c WHERE c.jhnmtRptLayoutPk.cid =:cid";
	
	
	@Override
	public List<PersonalReportClassification> getAllByCid(String cid, boolean abolition) {
		
		if(abolition == true) {
			return this.queryProxy().query(SEL_BY_CID, JhnmtRptLayout.class)
					.setParameter("cid", cid)
					.getList(c -> {
						return createDomainFromEntity(c);
					});
		}else {
			
			return this.queryProxy().query(SEL_BY_CID_AND_ABOLITON, JhnmtRptLayout.class)
					.setParameter("cid", cid)
					.setParameter("abolition", abolition)
					.getList(c -> {
						return createDomainFromEntity(c);
					});
		}
		
	}

	@Override
	public void insert(PersonalReportClassification domain) {
		this.commandProxy().insert(toEntity(domain));
		
	}

	@Override
	public void update(PersonalReportClassification domain) {
		this.commandProxy().update(toEntity(domain));
		
	}
	
	private PersonalReportClassification createDomainFromEntity(JhnmtRptLayout entity) {
		return PersonalReportClassification.createFromJavaType(entity.jhnmtRptLayoutPk.cid, 
				entity.jhnmtRptLayoutPk.rptLayoutId, entity.rptLayouCd, 
				entity.rptLayouName, entity.rptLayouNameYomi, 
				entity.displayOrder, entity.abolition, 
				entity.rptKind, entity.remark, entity.memo, 
				entity.message, entity.formPrint == null? false: entity.formPrint, entity.rptAgent == null? false:  entity.rptAgent);
	}
	
	private JhnmtRptLayout toEntity(PersonalReportClassification domain) {
		JhnmtRptLayoutPk primaryKey = new JhnmtRptLayoutPk(domain.getPReportClsId(), domain.getCompanyId());
		return new JhnmtRptLayout(primaryKey, domain.getWorkId(),
				domain.getPReportCode().v(), domain.getPReportName().v(),
				domain.getPReportNameYomi().v(), domain.getDisplayOrder(),
				domain.isAbolition(), domain.getReportType() == null? null: domain.getReportType().value,
				domain.getRemark() == null? null: domain.getRemark().v(),
				domain.getMemo() == null? null: domain.getMemo().v(),
				domain.getMessage() == null?  null: domain.getMessage().v(),
				domain.isFormReport(), domain.isAgentReportIsCan(),
				domain.isNoRankOrder());
	}

	@Override
	public Optional<PersonalReportClassification> getDetailReportClsByReportClsID(String cid, int reportClsID) {
		JhnmtRptLayoutPk key = new JhnmtRptLayoutPk(reportClsID, cid);
		Optional<JhnmtRptLayout> rptLayoutOpt = this.queryProxy().find(key, JhnmtRptLayout.class);
		if (rptLayoutOpt.isPresent()) {
			return Optional.of(createDomainFromEntity(rptLayoutOpt.get()));
		}
		return Optional.empty();

	}

	@Override
	public Map<String, Boolean> checkExist(String cid, String reportCode, String reportName) {
		Map<String, Boolean> result = new HashMap<>();
		List<?> reportClsDuplicateCd = this.queryProxy().query(IS_DUPLICATE_LAYOUTCODE, JhnmtRptLayout.class)
				.setParameter("cid", cid)
				.setParameter("rptLayouCd", reportCode)
				.getList(c -> {
					return createDomainFromEntity(c);
				});
		
		List<?> reportClsDuplicateName = this.queryProxy().query(IS_DUPLICATE_LAYOUT_NAME, JhnmtRptLayout.class)
				.setParameter("cid", cid)
				.setParameter("rptLayouName", reportName)
				.getList(c -> {
					return createDomainFromEntity(c);
				});
		
		if(!CollectionUtil.isEmpty(reportClsDuplicateCd)) {
			result.put("CODE", new Boolean(true));
		}
		
		if(!CollectionUtil.isEmpty(reportClsDuplicateName)) {
			result.put("NAME", new Boolean(true));
		}
		
		return result;
	}

	@Override
	public int maxId(String cid) {
		Integer max = this.queryProxy().query(MAX_ID_BY_CID, Integer.class)
				.setParameter("cid", cid).getSingleOrNull();
		if(max == null) return 0;
				
		return max.intValue();
	}

	@Override
	public List<PersonalReportClassification> getAllSameNameByCid(String cid, String reportName) {
		List<PersonalReportClassification> reportClsDuplicateName = this.queryProxy().query(IS_DUPLICATE_LAYOUT_NAME, JhnmtRptLayout.class)
				.setParameter("cid", cid)
				.setParameter("rptLayouName", reportName)
				.getList(c -> {
					return createDomainFromEntity(c);
				});
		return reportClsDuplicateName;
	}

	@Override
	public List<PersonalReportClassification> getAllSameCodeByCid(String cid, String reportCode) {
		List<PersonalReportClassification> reportClsDuplicateCd = this.queryProxy().query(IS_DUPLICATE_LAYOUTCODE, JhnmtRptLayout.class)
				.setParameter("cid", cid)
				.setParameter("rptLayouCd", reportCode)
				.getList(c -> {
					return createDomainFromEntity(c);
				});
		return reportClsDuplicateCd;
	}

	@Override
	public int maxDisorder(String cid) {
		Integer max = this.queryProxy().query(MAX_DIS_ORDER_BY_CID, Integer.class)
				.setParameter("cid", cid).getSingleOrNull();
		if(max == null) return 0;
				
		return max.intValue();
	}
		
	}

