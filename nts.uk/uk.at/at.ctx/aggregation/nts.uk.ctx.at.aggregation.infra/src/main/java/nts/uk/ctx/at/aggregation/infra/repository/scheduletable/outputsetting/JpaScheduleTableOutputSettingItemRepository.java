package nts.uk.ctx.at.aggregation.infra.repository.scheduletable.outputsetting;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.PersonalCounterCategory;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.WorkplaceCounterCategory;
import nts.uk.ctx.at.aggregation.dom.scheduletable.OneRowOutputItem;
import nts.uk.ctx.at.aggregation.dom.scheduletable.OutputItem;
import nts.uk.ctx.at.aggregation.dom.scheduletable.OutputSettingCode;
import nts.uk.ctx.at.aggregation.dom.scheduletable.OutputSettingName;
import nts.uk.ctx.at.aggregation.dom.scheduletable.ScheduleTableAttendanceItem;
import nts.uk.ctx.at.aggregation.dom.scheduletable.ScheduleTableOutputSetting;
import nts.uk.ctx.at.aggregation.dom.scheduletable.ScheduleTableOutputSettingRepository;
import nts.uk.ctx.at.aggregation.dom.scheduletable.ScheduleTablePersonalInfoItem;
import nts.uk.ctx.at.aggregation.infra.entity.scheduletable.outputsetting.KagmtRptSchedule;
import nts.uk.ctx.at.aggregation.infra.entity.scheduletable.outputsetting.KagmtRptScheduleItem;
import nts.uk.ctx.at.aggregation.infra.entity.scheduletable.outputsetting.KagmtRptScheduleItemPk;
import nts.uk.ctx.at.aggregation.infra.entity.scheduletable.outputsetting.KagmtRptSchedulePk;
import nts.uk.ctx.at.aggregation.infra.entity.scheduletable.outputsetting.KagmtRptScheduleTallyByPerson;
import nts.uk.ctx.at.aggregation.infra.entity.scheduletable.outputsetting.KagmtRptScheduleTallyByPersonPk;
import nts.uk.ctx.at.aggregation.infra.entity.scheduletable.outputsetting.KagmtRptScheduleTallyByWkp;
import nts.uk.ctx.at.aggregation.infra.entity.scheduletable.outputsetting.KagmtRptScheduleTallyByWkpPk;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 
 * @author quytb
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaScheduleTableOutputSettingItemRepository extends JpaRepository
		implements ScheduleTableOutputSettingRepository {

	private static final String GET_BY_CID_AND_CODE = "SELECT a, b.personalItem, b.additionalPersonalItem, b.attendanceItem, c.pk.categoryNo, d.pk.categoryNo FROM KagmtRptSchedule a "
			+ "LEFT JOIN KagmtRptScheduleItem b ON a.pk.companyId = b.pk.companyId AND a.pk.code = b.pk.code "
			+ "LEFT JOIN KagmtRptScheduleTallyByPerson c ON a.pk.companyId = c.pk.companyId AND a.pk.code = c.pk.code "
			+ "LEFT JOIN KagmtRptScheduleTallyByWkp d ON a.pk.companyId = d.pk.companyId AND a.pk.code = d.pk.code "
			+ "WHERE a.pk.companyId = :companyId " + "AND a.pk.code = :code";

	private static final String GET_BY_CID = "SELECT a, b.personalItem, b.additionalPersonalItem, b.attendanceItem, c.pk.categoryNo, d.pk.categoryNo FROM KagmtRptSchedule a "
			+ "LEFT JOIN KagmtRptScheduleItem b ON a.pk.companyId = b.pk.companyId AND a.pk.code = b.pk.code "
			+ "LEFT JOIN KagmtRptScheduleTallyByPerson c ON a.pk.companyId = c.pk.companyId AND a.pk.code = c.pk.code "
			+ "LEFT JOIN KagmtRptScheduleTallyByWkp d ON a.pk.companyId = d.pk.companyId AND a.pk.code = d.pk.code "
			+ "WHERE a.pk.companyId = :companyId";
	
	private ScheduleTableOutputSetting toDomainFromListObj(List<Object[]> objects) {
		List<PersonalCounterCategory> personalCounterCategories = new ArrayList<PersonalCounterCategory>();
		List<WorkplaceCounterCategory> workplaceCounterCategories = new ArrayList<WorkplaceCounterCategory>();
		List<ScheduleTableOutputSettingTemp> list = new ArrayList<>();
		List<OneRowOutputItem> oneRowOutputItems = new ArrayList<OneRowOutputItem>();
		KagmtRptSchedule entity = (KagmtRptSchedule) objects.get(0)[0];
		String code = entity.pk.code;
		String name = entity.name;
		int personalInfo = (Integer) objects.get(0)[1];
		int additionalInfo = (Integer) objects.get(0)[2];
		int attendanceItem = (Integer) objects.get(0)[3];
		int personalCatergoryNo = (Integer) objects.get(0)[4];
		int workplaceCatergoryNo = (Integer) objects.get(0)[5];
		
		objects.stream().forEach(object -> {
			list.add(new ScheduleTableOutputSettingTemp(code, name, personalInfo, additionalInfo, attendanceItem,
					personalCatergoryNo, workplaceCatergoryNo));
		});
		
		list.stream().forEach(x -> {
			personalCounterCategories.add(PersonalCounterCategory.of(x.getPersonalCatergoryNo()));
			workplaceCounterCategories.add(WorkplaceCounterCategory.of(x.getWorkplaceCatergoryNo()));
			
			OneRowOutputItem item = OneRowOutputItem.create(Optional.of(EnumAdaptor.valueOf(personalInfo, ScheduleTablePersonalInfoItem.class)),
					Optional.of(EnumAdaptor.valueOf(additionalInfo, ScheduleTablePersonalInfoItem.class)),
					Optional.of(EnumAdaptor.valueOf(attendanceItem, ScheduleTableAttendanceItem.class)));			
			oneRowOutputItems.add(item);
		});		
		

		OutputItem outputItem = new OutputItem(NotUseAtr.valueOf(entity.additionalColumUseAtr),
				NotUseAtr.valueOf(entity.shiftBackColorUseAtr), NotUseAtr.valueOf(entity.recordDispAtr),
				oneRowOutputItems);

		return new ScheduleTableOutputSetting(new OutputSettingCode(code), new OutputSettingName(name), outputItem,
				workplaceCounterCategories, personalCounterCategories);
	}
	
	private ScheduleTableOutputSetting toDomainFromObj(Object[] object) {
		List<PersonalCounterCategory> personalCounterCategories = new ArrayList<PersonalCounterCategory>();
		List<WorkplaceCounterCategory> workplaceCounterCategories = new ArrayList<WorkplaceCounterCategory>();
		List<ScheduleTableOutputSettingTemp> list = new ArrayList<>();
		List<OneRowOutputItem> oneRowOutputItems = new ArrayList<OneRowOutputItem>();

		KagmtRptSchedule entity = (KagmtRptSchedule) object[0];
		String code = entity.pk.code;
		String name = entity.name;
		int personalInfo = (Integer) object[1];
		int additionalInfo = (Integer) object[2];
		int attendanceItem = (Integer) object[3];
		int personalCatergoryNo = (Integer) object[4];
		int workplaceCatergoryNo = (Integer) object[5];

		list.add(new ScheduleTableOutputSettingTemp(code, name, personalInfo, additionalInfo, attendanceItem,
				personalCatergoryNo, workplaceCatergoryNo));
		list.stream().forEach(x -> {
			personalCounterCategories.add(PersonalCounterCategory.of(x.getPersonalCatergoryNo()));
			workplaceCounterCategories.add(WorkplaceCounterCategory.of(x.getWorkplaceCatergoryNo()));
			
			OneRowOutputItem item = OneRowOutputItem.create(Optional.of(EnumAdaptor.valueOf(personalInfo, ScheduleTablePersonalInfoItem.class)),
					Optional.of(EnumAdaptor.valueOf(additionalInfo, ScheduleTablePersonalInfoItem.class)),
					Optional.of(EnumAdaptor.valueOf(attendanceItem, ScheduleTableAttendanceItem.class)));			
			oneRowOutputItems.add(item);
		});		
		
		OutputItem outputItem = new OutputItem(NotUseAtr.valueOf(entity.additionalColumUseAtr),
				NotUseAtr.valueOf(entity.shiftBackColorUseAtr), NotUseAtr.valueOf(entity.recordDispAtr),
				oneRowOutputItems);

		return new ScheduleTableOutputSetting(new OutputSettingCode(code), new OutputSettingName(name), outputItem,
				workplaceCounterCategories, personalCounterCategories);
	}
	private List<ScheduleTableOutputSetting> toListDomainFromListObj(List<Object []> objects){
		List<ScheduleTableOutputSetting> scheduleTableOutputSettings = new ArrayList<ScheduleTableOutputSetting>();
		objects.stream().forEach(obj -> {
			scheduleTableOutputSettings.add(this.toDomainFromObj(obj));
		});
		return scheduleTableOutputSettings;		
	}
	
	private KagmtRptSchedule toKagmtRptScheduleEntity(ScheduleTableOutputSetting domain) {
		return new KagmtRptSchedule(
				new KagmtRptSchedulePk(AppContexts.user().companyId(),
						domain.getCode().v()),
				domain.getName().v(),
				domain.getOutputItem().getAdditionalColumnUseAtr().value,
				domain.getOutputItem().getShiftBackgroundColorUseAtr().value,
				domain.getOutputItem().getDailyDataDisplayAtr().value );				
	}
	
	private List<KagmtRptScheduleItem> toKagmtRptScheduleItemEntity(ScheduleTableOutputSetting domain) {
		List<KagmtRptScheduleItem> results = new ArrayList<KagmtRptScheduleItem>();
		List<OneRowOutputItem> list = domain.getOutputItem().getDetails();
		list.stream().forEach(row -> {
			results.add(new KagmtRptScheduleItem(
					new KagmtRptScheduleItemPk(AppContexts.user().companyId(),
							domain.getCode().v(),
							domain.getOutputItem().getDailyDataDisplayAtr().value),
					row.getPersonalInfo().get().value,
					row.getAdditionalInfo().get().value,
					row.getAttendanceItem().get().value
					));
		});
	
		return results;		
	}
	
	private List<KagmtRptScheduleTallyByPerson> toKagmtRptScheduleTallyByPersonEntity(ScheduleTableOutputSetting domain) {
		List<KagmtRptScheduleTallyByPerson> results = new ArrayList<KagmtRptScheduleTallyByPerson>();
		List<PersonalCounterCategory> list = domain.getPersonalCounterCategories();
		list.stream().forEach(item -> {
			results.add(new KagmtRptScheduleTallyByPerson(new KagmtRptScheduleTallyByPersonPk(
					AppContexts.user().companyId(), domain.getCode().v(), item.value)));
		});
		return results;
	}
	
	private List<KagmtRptScheduleTallyByWkp> toKagmtRptScheduleTallyByWkpEntity(ScheduleTableOutputSetting domain) {
		List<KagmtRptScheduleTallyByWkp> results = new ArrayList<KagmtRptScheduleTallyByWkp>();
		List<WorkplaceCounterCategory> list = domain.getWorkplaceCounterCategories();
		list.stream().forEach(item -> {
			results.add(new KagmtRptScheduleTallyByWkp(new KagmtRptScheduleTallyByWkpPk(
					AppContexts.user().companyId(), domain.getCode().v(), item.value)));
		});
		return results;
	}

	@Override
	public void insert(String companyId, ScheduleTableOutputSetting domain) {
		this.commandProxy().insert(this.toKagmtRptScheduleEntity(domain));
		this.commandProxy().insertAll(this.toKagmtRptScheduleItemEntity(domain));
		this.commandProxy().insertAll(this.toKagmtRptScheduleTallyByPersonEntity(domain));
		this.commandProxy().insertAll(this.toKagmtRptScheduleTallyByWkpEntity(domain));		
	}

	@Override
	public void update(String companyId, ScheduleTableOutputSetting domain) {
		Optional<ScheduleTableOutputSetting> kagmtRptSchedule = this.get(companyId, domain.getCode());
		if(kagmtRptSchedule.isPresent()) {
			this.commandProxy().update(this.toKagmtRptScheduleEntity(kagmtRptSchedule.get()));
			this.commandProxy().updateAll(this.toKagmtRptScheduleItemEntity(kagmtRptSchedule.get()));
			this.commandProxy().updateAll(this.toKagmtRptScheduleTallyByPersonEntity(kagmtRptSchedule.get()));
			this.commandProxy().updateAll(this.toKagmtRptScheduleTallyByWkpEntity(kagmtRptSchedule.get()));
		}
	}

	@Override
	public void delete(String companyId, OutputSettingCode code) {
		Optional<ScheduleTableOutputSetting> domainOpt = this.get(companyId, code);
		if(domainOpt.isPresent()) {
			this.commandProxy().remove(this.toKagmtRptScheduleEntity(domainOpt.get()));
			this.commandProxy().removeAll(this.toKagmtRptScheduleItemEntity(domainOpt.get()));
			this.commandProxy().removeAll(this.toKagmtRptScheduleTallyByPersonEntity(domainOpt.get()));
			this.commandProxy().removeAll(this.toKagmtRptScheduleTallyByWkpEntity(domainOpt.get()));				
		}
	}

	@Override
	public Optional<ScheduleTableOutputSetting> get(String companyId, OutputSettingCode code) {
		List<Object[]> results = this.queryProxy().query(GET_BY_CID_AND_CODE)
				.setParameter("companyId", companyId)
				.setParameter("code", code.v())
				.getList();	

//		String c = code.v();
//		List<ScheduleTableOutputSetting> a = this.toListDomainFromListObj(results);	
		return Optional.of(this.toDomainFromListObj(results));		

	}

	@Override
	public List<ScheduleTableOutputSetting> getList(String companyId) {
		List<Object[]> results = this.queryProxy().query(GET_BY_CID)
				.setParameter("companyId", companyId)
				.getList();		
		
		return this.toListDomainFromListObj(results);
	}

	@Override
	public boolean exists(String companyId, OutputSettingCode code) {
		Optional<ScheduleTableOutputSetting> domainOpt = this.get(companyId, code);
		return domainOpt.isPresent() ? true: false;
	}

}
