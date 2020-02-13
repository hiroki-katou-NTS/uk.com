package nts.uk.ctx.hr.develop.infra.repository.sysoperationset.businessrecognition;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.MenuApprovalSettingsRepository;
import nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.algorithm.dto.BusinessApprovalSettingsDto;

@Stateless
public class JpaMenuApprovalSettingsRepositoryImpl extends JpaRepository implements MenuApprovalSettingsRepository {

	private static final String GET_BUSINESS_APPROVAL_SETTINGS = "SELECT DISTINCT " 
			+ "ma.CID, "
			+ "ma.RPT_LAYOUT_ID, " 
			+ "l.RPT_LAYOUT_CD, "
			+ "l.RPT_LAYOUT_NAME, " 
			+ "ma.PROGRAM_ID, "
			+ "sm.DISPLAY_NAME, " 
			+ "sm.SCREEN_ID, "
			+ "mo.USE_APPROVAL, " 
			+ "ma.AVAILABLE_APR_ROOT, "
			+ "mo.NO_RANK_ORDER, " 
			+ "ma.AVAILABLE_APR_WORK1, "
			+ "ma.AVAILABLE_APR_WORK2, " 
			+ "ma.APR1_BUSINESS_NAME, "
			+ "ma.APR2_BUSINESS_NAME "

			+ "FROM JcmmtMenuApr ma JOIN CcgstStandardMenu sm "
			+ "ON ma.PROGRAM_ID = sm.PROGRAM_ID "
			+ "AND ma.SCREEN_ID = sm.SCREEN_ID " + "LEFT OUTER JOIN JhnmtRptLayout l "
			+ "ON ma.RPT_LAYOUT_ID = l.RPT_LAYOUT_ID  "
			+ "LEFT OUTER JOIN JcmctMenuOperation mo "
			+ "ON ma.PROGRAM_ID = mo.PROGRAM_ID "

			+ "WHERE " + "ma.CID = :cId ";
	
	/* (non-Javadoc)
	 * sql for 業務承認設定を取得する algorithm
	 */
	@Override
	public Optional<BusinessApprovalSettingsDto> getBusinessApprovalSettings(String cid) {
		List<Object[]> result = this.getEntityManager().createQuery(GET_BUSINESS_APPROVAL_SETTINGS, Object[].class)
				.setParameter("cId", cid)
				.getResultList();
		if(result.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(this.joinObjectToDomain(result.get(0)));
		
	}

	private BusinessApprovalSettingsDto joinObjectToDomain(Object[] object) {
		return new BusinessApprovalSettingsDto(
				object[0] == null ? "" : (String) object[0], object[1] == null ? "" : (String) object[1],
				object[2] == null ? "" : (String) object[2], object[3] == null ? "" : (String) object[3],
				object[4] == null ? "" : (String) object[4], object[5] == null ? "" : (String) object[5],
				object[6] == null ? "" : (String) object[6], object[7] == null ? "" : (String) object[7],
				object[8] == null ? "" : (String) object[8], object[9] == null ? "" : (String) object[9],
				object[10] == null ? "" : (String) object[10], object[11] == null ? "" : (String) object[11],
				object[12] == null ? "" : (String) object[12], object[13] == null ? "" : (String) object[13]);
	}
}
