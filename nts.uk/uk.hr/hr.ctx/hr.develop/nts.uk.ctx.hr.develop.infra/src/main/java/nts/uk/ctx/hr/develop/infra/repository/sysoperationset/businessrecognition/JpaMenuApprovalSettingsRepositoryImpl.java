package nts.uk.ctx.hr.develop.infra.repository.sysoperationset.businessrecognition;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.MenuApprovalSettingsRepository;
import nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.algorithm.dto.BusinessApprovalSettingsDto;
import nts.uk.ctx.hr.develop.infra.entity.sysoperationset.businessrecognition.JcmmtMenuApr;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaMenuApprovalSettingsRepositoryImpl extends JpaRepository implements MenuApprovalSettingsRepository {

	private static final String GET_BUSINESS_APPROVAL_SETTINGS = "SELECT DISTINCT " 
			+ "ma, " 
			+ "l.rptLayouCd, "
			+ "l.rptLayouName, " 
			+ "sm.displayName, " 
			+ "sm.screenID, "
			+ "mo.useApproval, " 
			+ "mo.noRankOrder " 

			+ "FROM JcmmtMenuApr ma JOIN CcgstStandardMenu sm "
			+ "ON ma.pkJcmmtMenuApr.programId = sm.programId "
			+ "AND ma.pkJcmmtMenuApr.screenId = sm.screenID "
			+ "AND ma.pkJcmmtMenuApr.cId = sm.ccgmtStandardMenuPK.companyId " 
			+ "LEFT OUTER JOIN JhnmtRptLayout l "
			+ "ON ma.rptLayoutId = l.jhnmtRptLayoutPk.rptLayoutId "
			+ "AND ma.pkJcmmtMenuApr.cId = l.jhnmtRptLayoutPk.cid "
			+ "LEFT OUTER JOIN JcmctMenuOperation mo "
			+ "ON ma.pkJcmmtMenuApr.programId = mo.jcmctMenuOperationPK.programId "
			+ "AND ma.pkJcmmtMenuApr.cId = mo.jcmctMenuOperationPK.companyId "

			+ "WHERE " + "ma.pkJcmmtMenuApr.cId = :cId ";
	
	/* (non-Javadoc)
	 * sql for 業務承認設定を取得する algorithm
	 */
	@Override
	public List<BusinessApprovalSettingsDto> getBusinessApprovalSettings(String cid) {
		return this.getEntityManager().createQuery(GET_BUSINESS_APPROVAL_SETTINGS, Object[].class)
				.setParameter("cId", cid)
				.getResultList().stream().map(c-> this.joinObjectToDomain(c)).collect(Collectors.toList());
	}

	private BusinessApprovalSettingsDto joinObjectToDomain(Object[] object) {
		JcmmtMenuApr menuApr = (JcmmtMenuApr) object[0];
		return new BusinessApprovalSettingsDto(
				menuApr.toDomain(),
				object[1] == null ? "" : (String) object[1], 
				object[2] == null ? "" : (String) object[2],
				object[3] == null ? "" : (String) object[3],
				object[4] == null ? "" : (String) object[4], 
				object[5] == null ? false : (int) object[5] == 1,
				object[6] == null ? null : (int) object[6] == 1);
				
	}

	@Override
	public void update(List<BusinessApprovalSettingsDto> domain) {
		String cid = AppContexts.user().companyId();
		String updateJcmctMenuOperation = "Update JcmctMenuOperation m "
				+ "Set m.noRankOrder =:noRankOrder "
				+ "Where m.jcmctMenuOperationPK.companyId = :cid "
				+ "And m.jcmctMenuOperationPK.programId = :programId ";
		String updateJhnmtRptLayout = "Update JhnmtRptLayout m "
				+ "Set m.noRankOrder =:noRankOrder "
				+ "Where m.jhnmtRptLayoutPk.cid = :cid "
				+ "And m.jhnmtRptLayoutPk.rptLayoutId = :rptLayoutId ";
		List<KeyDto> menuOperation = new ArrayList<>();
		List<KeyDto> rptLayout = new ArrayList<>();
		for (BusinessApprovalSettingsDto c : domain) {
			this.commandProxy().update(JcmmtMenuApr.toEntity(c.getMenuApprovalSettings()));
			this.getEntityManager().createQuery(updateJcmctMenuOperation)
				.setParameter("noRankOrder", c.getNoRankOrder()?1:0)
				.setParameter("cid", cid)
				.setParameter("programId", c.getMenuApprovalSettings().getProgramId())
				.executeUpdate();
			this.getEntityManager().createQuery(updateJhnmtRptLayout)
				.setParameter("noRankOrder", c.getNoRankOrder())
				.setParameter("cid", cid)
				.setParameter("rptLayoutId", c.getMenuApprovalSettings().getRptLayoutId())
				.executeUpdate();
		}
		
	}
}
