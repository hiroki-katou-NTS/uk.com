package nts.uk.file.at.infra.export.attendanceitemprepare;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktype.CalculateMethod;
import nts.uk.ctx.at.shared.dom.worktype.CloseAtr;
import nts.uk.ctx.at.shared.dom.worktype.HolidayAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;
import nts.uk.ctx.at.shared.infra.entity.worktype.KshmtWorkTypeOneToOne;
import nts.uk.ctx.at.shared.infra.entity.worktype.KshmtWorkTypeSet;
import nts.uk.ctx.at.shared.infra.entity.worktype.language.KshmtWorkTypeLanguage;
import nts.uk.file.at.app.export.worktype.WorkTypeReportData;
import nts.uk.file.at.app.export.worktype.WorkTypeReportRepository;

@Stateless
public class JpaWorkTypeReportRepository extends JpaRepository implements WorkTypeReportRepository {

	private String WORK_TYPE_SELECT_ALL = "SELECT c FROM KshmtWorkTypeOneToOne c"
			+ " WHERE c.kshmtWorkTypePK.companyId = :companyId";

	@Override
	public List<WorkTypeReportData> findAllWorkType(String companyId, String langId) {
		return this.queryProxy().query(WORK_TYPE_SELECT_ALL, KshmtWorkTypeOneToOne.class).setParameter("companyId", companyId)
				.getList(x -> toReportData(x,langId));
	}

	private WorkTypeReportData toReportData(KshmtWorkTypeOneToOne entity,String langId) {

		KshmtWorkTypeSet workTypeSetOneDay = new KshmtWorkTypeSet();
		KshmtWorkTypeSet workTypeSetMorning = new KshmtWorkTypeSet();
		KshmtWorkTypeSet workTypeSetAfternoon = new KshmtWorkTypeSet();
		if (!CollectionUtil.isEmpty(entity.worktypeSetList)) {
			if (entity.worktypeAtr == WorkTypeUnit.OneDay.value) {
				workTypeSetOneDay = entity.worktypeSetList.get(0);
			} else {
				workTypeSetMorning = entity.worktypeSetList.stream().filter(x -> x.kshmtWorkTypeSetPK.workAtr==1).findAny().orElse(null);
				workTypeSetAfternoon = entity.worktypeSetList.stream().filter(x -> x.kshmtWorkTypeSetPK.workAtr==2).findAny().orElse(null);
			}
		}

		KshmtWorkTypeLanguage workTypeLanguage = new KshmtWorkTypeLanguage();
		if (!CollectionUtil.isEmpty(entity.workTypeLanguage)) {
			Optional<KshmtWorkTypeLanguage> workTypeLanguageOpt = entity.workTypeLanguage.stream()
					.filter(x->x.kshmtWorkTypeLanguagePK.langId.equals(langId))
					.findFirst();
			workTypeLanguage = workTypeLanguageOpt.isPresent() ? workTypeLanguageOpt.get() : new KshmtWorkTypeLanguage();
		}

		Integer dispOrder = entity.kshmtWorkTypeOrder != null ? entity.kshmtWorkTypeOrder.dispOrder : null;

		return new WorkTypeReportData(entity.kshmtWorkTypePK.workTypeCode, entity.name, entity.symbolicName,
				entity.abbreviationName, entity.memo, entity.deprecateAtr,
				EnumAdaptor.valueOf(entity.calculatorMethod, CalculateMethod.class),
				EnumAdaptor.valueOf(entity.worktypeAtr, WorkTypeUnit.class),
				EnumAdaptor.valueOf(entity.oneDayAtr, WorkTypeClassification.class),
				EnumAdaptor.valueOf(entity.morningAtr, WorkTypeClassification.class),
				EnumAdaptor.valueOf(entity.afternoonAtr, WorkTypeClassification.class), 
				EnumAdaptor.valueOf(workTypeSetOneDay.hodidayAtr, HolidayAtr.class), 
				workTypeSetOneDay.dayNightTimeAsk, 
				workTypeSetOneDay.attendanceTime, 
				workTypeSetOneDay.timeLeaveWork, 
				workTypeSetOneDay.digestPublicHd,
				workTypeSetOneDay.genSubHoliday, 
				workTypeSetOneDay.sumAbsenseNo, 
				workTypeSetOneDay.sumSpHolidayNo, 
				workTypeSetOneDay.closeAtr != null ? EnumAdaptor.valueOf(workTypeSetOneDay.closeAtr, CloseAtr.class) : null, 
				workTypeSetMorning != null ? workTypeSetMorning.dayNightTimeAsk : null,
				workTypeSetMorning != null ? workTypeSetMorning.attendanceTime : null,
				workTypeSetMorning != null ? workTypeSetMorning.timeLeaveWork : null,
				workTypeSetMorning != null ? workTypeSetMorning.countHoliday : null,
				workTypeSetMorning != null ? workTypeSetMorning.digestPublicHd : null,
				workTypeSetMorning != null ? workTypeSetMorning.genSubHoliday : null, 
				workTypeSetMorning != null ? workTypeSetMorning.sumAbsenseNo : null,
				workTypeSetMorning != null ? workTypeSetMorning.sumSpHolidayNo : null,
				workTypeSetAfternoon != null ? workTypeSetAfternoon.dayNightTimeAsk : null,
				workTypeSetAfternoon != null ? workTypeSetAfternoon.attendanceTime : null,
				workTypeSetAfternoon != null ? workTypeSetAfternoon.timeLeaveWork : null,
				workTypeSetAfternoon != null ? workTypeSetAfternoon.countHoliday : null,
				workTypeSetAfternoon != null ? workTypeSetAfternoon.digestPublicHd : null,
				workTypeSetAfternoon != null ? workTypeSetAfternoon.genSubHoliday : null,
				workTypeSetAfternoon != null ? workTypeSetAfternoon.sumAbsenseNo : null,
				workTypeSetAfternoon != null ? workTypeSetAfternoon.sumSpHolidayNo : null, 
				workTypeLanguage.name, workTypeLanguage.abname, dispOrder);
	}
}
