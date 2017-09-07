package nts.uk.file.at.infra.worktype;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.infra.entity.worktype.KshmtWorkType;
import nts.uk.ctx.at.shared.infra.entity.worktype.KshmtWorkTypeSet;
import nts.uk.ctx.at.shared.infra.entity.worktype.language.KshmtWorkTypeLanguage;
import nts.uk.file.at.app.export.worktype.WorkTypeReportRepository;
import nts.uk.file.at.app.export.worktype.data.WorkTypeReportData;

@Stateless
public class JpaWorkTypeReportRepository extends JpaRepository implements WorkTypeReportRepository {

	private String WORK_TYPE_SELECT_ALL = "SELECT c FROM KshmtWorkType c"
			+ " WHERE c.kshmtWorkTypePK.companyId = :companyId";

	@Override
	public List<WorkTypeReportData> findAllWorkType(String companyId) {
		return this.queryProxy().query(WORK_TYPE_SELECT_ALL, KshmtWorkType.class)
				.setParameter("companyId", companyId).getList(x->toReportData(x));
	}
	
	private WorkTypeReportData toReportData(KshmtWorkType entity) {
		KshmtWorkTypeSet workTypeSet = new KshmtWorkTypeSet();
		if (!CollectionUtil.isEmpty(entity.worktypeSetList)) {
			workTypeSet = entity.worktypeSetList.get(0);
		}
		
		KshmtWorkTypeLanguage workTypeLanguage = new KshmtWorkTypeLanguage();
		if (entity.workTypeLanguage!= null) {
			workTypeLanguage = entity.workTypeLanguage;
		}
		
		Integer dispOrder = entity.kshmtWorkTypeOrder != null ? entity.kshmtWorkTypeOrder.dispOrder : null; 
		
		return new WorkTypeReportData(
				entity.kshmtWorkTypePK.workTypeCode, 
				entity.name, 
				entity.symbolicName, 
				entity.abbreviationName,
				entity.memo, 
				entity.deprecateAtr, 
				entity.calculatorMethod,
				entity.worktypeAtr, 
				entity.oneDayAtr, 
				entity.morningAtr, 
				entity.afternoonAtr, 
				workTypeSet.dayNightTimeAsk, 
				workTypeSet.attendanceTime, 
				workTypeSet.timeLeaveWork, 
				workTypeSet.countHoliday,
				workTypeSet.digestPublicHd, 
				workTypeSet.genSubHoliday, 
				workTypeSet.sumAbsenseNo, 
				workTypeSet.sumSpHolidayNo, 
				workTypeSet.closeAtr, 
				workTypeLanguage.name, 
				workTypeLanguage.abname,
				dispOrder);
	}
}
