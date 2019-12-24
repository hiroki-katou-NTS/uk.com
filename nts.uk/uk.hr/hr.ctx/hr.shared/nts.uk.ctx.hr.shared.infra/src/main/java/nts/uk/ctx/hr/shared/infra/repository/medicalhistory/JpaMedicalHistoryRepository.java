/**
 * 
 */
package nts.uk.ctx.hr.shared.infra.repository.medicalhistory;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.hr.shared.dom.personalinfo.medicalhistory.MedicalhistoryItem;
import nts.uk.ctx.hr.shared.dom.personalinfo.medicalhistory.MedicalhistoryItemResults;
import nts.uk.ctx.hr.shared.dom.personalinfo.medicalhistory.MedicalhistoryRepository;
import nts.uk.ctx.hr.shared.infra.entity.medicalhistory.PpedtMedicalHis;
import nts.uk.ctx.hr.shared.infra.entity.medicalhistory.PpedtMedicalHisItem;

@Stateless
public class JpaMedicalHistoryRepository extends JpaRepository implements MedicalhistoryRepository {
	
	private static final String SELECT_BY_LISTPID_AND_BASEDATE = "SELECT i, h FROM PpedtMedicalHisItem i inner join PpedtMedicalHis h "
			+ "ON i.PpedtMedicalHisItemPk.hisId = h.ppedtMedicalHisPk.hisId "
			+ "WHERE i.sid IN :listSId and h.startDate >= :baseDate ";

	@Override
	public List<MedicalhistoryItemResults> getListMedicalhistoryItem(List<String> listSId,
			GeneralDate baseDate) {
		
		if (listSId.isEmpty() || baseDate == null) {
			return new ArrayList<>();
		}
		
		List<MedicalhistoryItemResults> listEntity = new ArrayList<>();
		CollectionUtil.split(listSId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			listEntity.addAll(this.queryProxy().query(SELECT_BY_LISTPID_AND_BASEDATE,Object[].class)
					.setParameter("baseDate", baseDate)				 
					.setParameter("listSId", subList)
					.getList(c->joinObjectToDomain(c)));
		});
		
		return listEntity;
	}
	
	private MedicalhistoryItemResults joinObjectToDomain(Object[] entity) {
		PpedtMedicalHisItem item = (PpedtMedicalHisItem) entity[0];
		PpedtMedicalHis hist = (PpedtMedicalHis) entity[1];
		return new MedicalhistoryItemResults(item.cid, item.sid, hist.startDate, hist.endDate, item.resultNote);
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
