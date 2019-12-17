/**
 * 
 */
package nts.uk.ctx.hr.shared.infra.repository.medicalhistory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.hr.shared.dom.personalinfo.medicalhistory.MedicalhistoryItem;
import nts.uk.ctx.hr.shared.dom.personalinfo.medicalhistory.MedicalhistoryRepository;
import nts.uk.ctx.hr.shared.infra.entity.medicalhistory.PpedtMedicalHisItem;

@Stateless
public class JpaMedicalHistoryRepository extends JpaRepository implements MedicalhistoryRepository {
	
	private static final String SELECT_BY_LISTPID_AND_BASEDATE = "SELECT a FROM PpedtMedicalHisItem a "
																+ "inner join PpedtMedicalHis b on a.PpedtMedicalHisItemPk.hisId = b.ppedtMedicalHisPk.hisId "
																+ "WHERE a.sid IN :listSId and b.startDate >= :baseDate ";

	@Override
	public List<MedicalhistoryItem> getListMedicalhistoryItem(List<String> listSId,
			GeneralDate baseDate) {
		
		if (listSId.isEmpty() || baseDate == null) {
			return new ArrayList<MedicalhistoryItem>();
		}
		
		List<PpedtMedicalHisItem> listEntity = new ArrayList<>();
		CollectionUtil.split(listSId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			listEntity.addAll(this.getEntityManager().createQuery(SELECT_BY_LISTPID_AND_BASEDATE,PpedtMedicalHisItem.class)
					.setParameter("baseDate", baseDate)				 
					.setParameter("listSId", subList).getResultList());
		});
		
		if (listEntity.isEmpty()) {
			return new ArrayList<MedicalhistoryItem>();
		}
		
		List<MedicalhistoryItem> result = listEntity.stream().map(e -> toDomain(e)).collect(Collectors.toList());
		
		return result;
	}

	private MedicalhistoryItem toDomain(PpedtMedicalHisItem entity) {
		return new MedicalhistoryItem(entity.cid, entity.sid, entity.PpedtMedicalHisItemPk.hisId,
				entity.visitNote, entity.abnormIllness,
				entity.consultationRemarks, entity.healthCheckupYear,
				entity.pastAndPresent, entity.height, entity.weight,
				entity.standWeight, entity.obesityBMI,
				entity.visionRight, entity.visionLeft, 
				entity.visionTestNote, entity.hear1Right,
				entity.hear1Left, entity.hear4Right, 
				entity.hear4Left, entity.hearingTestCls,
				entity.bloodPressureHigh, entity.bloodPressureLow, 
				entity.bloodPressureNote, entity.electroHeart, 
				entity.inSpecElectroHeartNote, entity.totalCholesterol, 
				entity.neutralFat, entity.hdlCholesterol, 
				entity.bloodLipidTestNote, entity.xLineChest, 
				entity.xRayStomach, entity.xRayInspecNote, 
				entity.got, entity.gpt, entity.ggtp, entity.urineTestNote, 
				entity.hematocritValue, entity.liverFunctionTestNote, 
				entity.bloodSuger, entity.hemoglobinAmount, 
				entity.urineSugar, entity.numberRedBloodCells, 
				entity.whiteBloodCellCount, entity.bloodSugerNote, 
				entity.uricAcidLevel, entity.anemiaTestNote, 
				entity.colonTestNote, entity.urinaryProtein, 
				entity.womanTestNote, entity.occultBlood, 
				entity.creatinine, entity.resultNote);
	}
}
