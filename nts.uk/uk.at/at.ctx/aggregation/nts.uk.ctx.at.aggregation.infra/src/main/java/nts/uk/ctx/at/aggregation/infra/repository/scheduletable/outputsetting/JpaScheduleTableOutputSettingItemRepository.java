package nts.uk.ctx.at.aggregation.infra.repository.scheduletable.outputsetting;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

	private ScheduleTableOutputSetting toDomainFromEntity(KagmtRptSchedule kagmtRptSchedule,
			List<KagmtRptScheduleItem> kagmtRptScheduleItems, List<KagmtRptScheduleTallyByPerson> kagmtRptScheduleTallyByPersons,
			List<KagmtRptScheduleTallyByWkp> kagmtRptScheduleTallyByWkps) {
		List<OneRowOutputItem> oneRowOutputItems = new ArrayList<OneRowOutputItem>();
		List<PersonalCounterCategory> personalCounterCategories = new ArrayList<PersonalCounterCategory>();
		List<WorkplaceCounterCategory> workplaceCounterCategories = new ArrayList<WorkplaceCounterCategory>();
		if(!kagmtRptScheduleTallyByPersons.isEmpty()) {
			kagmtRptScheduleTallyByPersons.stream().forEach(x -> {
				if(x.pk.categoryNo != null) {
					personalCounterCategories.add(PersonalCounterCategory.of(x.pk.categoryNo));
				}	
			});
		}		
		
		if(!kagmtRptScheduleTallyByPersons.isEmpty()) {
			kagmtRptScheduleTallyByPersons.stream().forEach(x -> {
				if(x.pk.categoryNo != null) {
					workplaceCounterCategories.add(WorkplaceCounterCategory.of(x.pk.categoryNo));
				}	
			});			
		}
		
		kagmtRptScheduleItems.stream().forEach(x -> {
			if (x.personalItem == null && x.additionalPersonalItem == null && x.attendanceItem == null) {
				oneRowOutputItems.add(OneRowOutputItem.create(
						Optional.of(EnumAdaptor.valueOf(0, ScheduleTablePersonalInfoItem.class)),
						Optional.of(EnumAdaptor.valueOf(4, ScheduleTablePersonalInfoItem.class)),
						Optional.of(EnumAdaptor.valueOf(0, ScheduleTableAttendanceItem.class))));
			} else {
				OneRowOutputItem item = OneRowOutputItem.create(
						Optional.ofNullable(x.personalItem != null ? EnumAdaptor.valueOf(x.personalItem, ScheduleTablePersonalInfoItem.class) : null),
						Optional.ofNullable(x.additionalPersonalItem != null ? EnumAdaptor.valueOf(x.additionalPersonalItem, ScheduleTablePersonalInfoItem.class): null),
						Optional.ofNullable(x.attendanceItem != null ? EnumAdaptor.valueOf(x.attendanceItem, ScheduleTableAttendanceItem.class): null));
				oneRowOutputItems.add(item);
			}			
		});
		
		OutputItem outputItem = new OutputItem(NotUseAtr.valueOf(kagmtRptSchedule.additionalColumUseAtr),
				NotUseAtr.valueOf(kagmtRptSchedule.shiftBackColorUseAtr), NotUseAtr.valueOf(kagmtRptSchedule.recordDispAtr),
				oneRowOutputItems);
	
		return ScheduleTableOutputSetting.create(new OutputSettingCode(kagmtRptSchedule.pk.code), new OutputSettingName(kagmtRptSchedule.name), outputItem,
				workplaceCounterCategories, personalCounterCategories);
	}
	private ScheduleTableOutputSetting toDomainFromObjByCode(List<Object[]> objects) {
		if(objects.isEmpty()) {
			return ScheduleTableOutputSetting.create(null, null, null, null, null);
		}
		List<PersonalCounterCategory> personalCounterCategories = new ArrayList<PersonalCounterCategory>();
		List<WorkplaceCounterCategory> workplaceCounterCategories = new ArrayList<WorkplaceCounterCategory>();
		List<ScheduleTableOutputSettingTemp> list = new ArrayList<>();
		List<OneRowOutputItem> oneRowOutputItems = new ArrayList<OneRowOutputItem>();
		KagmtRptSchedule entity = (KagmtRptSchedule) objects.get(0)[0];
		String code = entity.pk.code;
		String name = entity.name;
		Integer personalInfo = objects.get(0) != null ?(Integer) objects.get(0)[1]: null;
		Integer additionalInfo =objects.get(0)!= null ? (Integer) objects.get(0)[2]: null;
		Integer attendanceItem = objects.get(0)!=null ? (Integer) objects.get(0)[3]: null;
		Integer personalCatergoryNo = (Integer) objects.get(0)[4];
		Integer workplaceCatergoryNo = (Integer) objects.get(0)[5];

		objects.stream().forEach(object -> {
			list.add(new ScheduleTableOutputSettingTemp(code, name, personalInfo, additionalInfo, attendanceItem,
					personalCatergoryNo, workplaceCatergoryNo, entity));
		});

		list.stream().forEach(x -> {
			if(x.getPersonalCatergoryNo() != null) {
				personalCounterCategories.add(PersonalCounterCategory.of(x.getPersonalCatergoryNo()));
			}			
			if(x.getWorkplaceCatergoryNo() != null) {
				workplaceCounterCategories.add(WorkplaceCounterCategory.of(x.getWorkplaceCatergoryNo()));
			}
			
			if (personalInfo == null && additionalInfo == null && attendanceItem == null) {
				oneRowOutputItems.add(OneRowOutputItem.create(
						Optional.of(EnumAdaptor.valueOf(0, ScheduleTablePersonalInfoItem.class)),
						Optional.of(EnumAdaptor.valueOf(4, ScheduleTablePersonalInfoItem.class)),
						Optional.of(EnumAdaptor.valueOf(0, ScheduleTableAttendanceItem.class))));
			} else {
				OneRowOutputItem item = OneRowOutputItem.create(
						Optional.ofNullable(personalInfo != null ? EnumAdaptor.valueOf(personalInfo, ScheduleTablePersonalInfoItem.class) : null),
						Optional.ofNullable(additionalInfo != null ? EnumAdaptor.valueOf(additionalInfo, ScheduleTablePersonalInfoItem.class): null),
						Optional.ofNullable(attendanceItem != null ? EnumAdaptor.valueOf(attendanceItem, ScheduleTableAttendanceItem.class): null));
				oneRowOutputItems.add(item);
			}			
		});

		OutputItem outputItem = new OutputItem(NotUseAtr.valueOf(entity.additionalColumUseAtr),
				NotUseAtr.valueOf(entity.shiftBackColorUseAtr), NotUseAtr.valueOf(entity.recordDispAtr),
				oneRowOutputItems);

		return new ScheduleTableOutputSetting(new OutputSettingCode(code), new OutputSettingName(name), outputItem,
				workplaceCounterCategories, personalCounterCategories);
	}

	private List<ScheduleTableOutputSetting> toDomainFromObj(List<Object[]> objects) {
		if(objects.isEmpty()) {
			return new ArrayList<ScheduleTableOutputSetting>();
		}
		List<ScheduleTableOutputSetting> results = new ArrayList<ScheduleTableOutputSetting>();
		List<ScheduleTableOutputSettingTemp> list = new ArrayList<>();
		Set<String> listCode = new HashSet<String>();
		objects.stream().forEach(object -> {
			KagmtRptSchedule entity = (KagmtRptSchedule) object[0];
			String code = entity.pk.code;
			String name = entity.name;
			Integer personalInfo = object[1]!=null ? (Integer) object[1]: null;
			Integer additionalInfo = object[2] !=null? (Integer) object[2] : null;
			Integer attendanceItem = object[3] !=null ? (Integer) object[3]:null;
			Integer personalCatergoryNo = (Integer) object[4];
			Integer workplaceCatergoryNo = (Integer) object[5];

			list.add(new ScheduleTableOutputSettingTemp(code, name, personalInfo, additionalInfo, attendanceItem,
					personalCatergoryNo, workplaceCatergoryNo, entity));
			listCode.add(code);		
		});

		Map<String, List<ScheduleTableOutputSettingTemp>> listData = list.stream()
				.collect(Collectors.groupingBy(ScheduleTableOutputSettingTemp::getCode));	
		
		listCode.stream().forEach(code -> {
			results.add(this.convertToDomainFromTmp(listData.get(code)));

		});

		return results;
	}
	
	private ScheduleTableOutputSetting convertToDomainFromTmp(List<ScheduleTableOutputSettingTemp> list) {
		List<PersonalCounterCategory> personalCounterCategories = new ArrayList<PersonalCounterCategory>();
		List<WorkplaceCounterCategory> workplaceCounterCategories = new ArrayList<WorkplaceCounterCategory>();
		List<OneRowOutputItem> oneRowOutputItems = new ArrayList<OneRowOutputItem>();

		list.stream().forEach(x -> {
			if(x.getPersonalCatergoryNo() != null) {
				personalCounterCategories.add(PersonalCounterCategory.of(x.getPersonalCatergoryNo()));
			}
			if(x.getWorkplaceCatergoryNo() != null) {
				workplaceCounterCategories.add(WorkplaceCounterCategory.of(x.getWorkplaceCatergoryNo()));
			}			

			OneRowOutputItem item = OneRowOutputItem.create(
					Optional.ofNullable(x.getPersonalInfo() != null ? EnumAdaptor.valueOf(x.getPersonalInfo(), ScheduleTablePersonalInfoItem.class) : null),
					Optional.ofNullable(x.getAdditionalInfo() != null ? EnumAdaptor.valueOf(x.getAdditionalInfo(), ScheduleTablePersonalInfoItem.class): null),
					Optional.ofNullable(x.getAttendanceItem() != null ? EnumAdaptor.valueOf(x.getAttendanceItem(), ScheduleTableAttendanceItem.class): null));				
			oneRowOutputItems.add(item);
		});

		OutputItem outputItem = new OutputItem(
				NotUseAtr.valueOf(list.get(0).getKagmtRptSchedule().additionalColumUseAtr),
				NotUseAtr.valueOf(list.get(0).getKagmtRptSchedule().shiftBackColorUseAtr),
				NotUseAtr.valueOf(list.get(0).getKagmtRptSchedule().recordDispAtr), oneRowOutputItems);
		return new ScheduleTableOutputSetting(new OutputSettingCode(list.get(0).getCode()),
				new OutputSettingName(list.get(0).getName()), outputItem, workplaceCounterCategories,
				personalCounterCategories);
	}


	private KagmtRptSchedule toKagmtRptScheduleEntity(ScheduleTableOutputSetting domain) {
		return new KagmtRptSchedule(new KagmtRptSchedulePk(AppContexts.user().companyId(), domain.getCode().v()),
				domain.getName().v(), domain.getOutputItem().getAdditionalColumnUseAtr().value,
				domain.getOutputItem().getShiftBackgroundColorUseAtr().value,
				domain.getOutputItem().getDailyDataDisplayAtr().value);
	}

	private List<KagmtRptScheduleItem> toKagmtRptScheduleItemEntity(ScheduleTableOutputSetting domain) {
		List<KagmtRptScheduleItem> results = new ArrayList<KagmtRptScheduleItem>();
		List<OneRowOutputItem> list = domain.getOutputItem().getDetails();
		int idx = 1;
		for(int i = 0; i < list.size(); i++) {
			results.add(new KagmtRptScheduleItem(
					new KagmtRptScheduleItemPk(AppContexts.user().companyId(), domain.getCode().v(),
							idx++),
					list.get(i).getPersonalInfo().isPresent() ?list.get(i).getPersonalInfo().get().value : null, 
					list.get(i).getAdditionalInfo().isPresent() ? list.get(i).getAdditionalInfo().get().value : null,
					list.get(i).getAttendanceItem().isPresent() ? list.get(i).getAttendanceItem().get().value : null));
		}

		return results;
	}

	private List<KagmtRptScheduleTallyByPerson> toKagmtRptScheduleTallyByPersonEntity(
			ScheduleTableOutputSetting domain) {
		List<KagmtRptScheduleTallyByPerson> results = new ArrayList<KagmtRptScheduleTallyByPerson>();
		List<PersonalCounterCategory> list = domain.getPersonalCounterCategories();
		if(!list.isEmpty()) {
			list.stream().forEach(item -> {
				results.add(new KagmtRptScheduleTallyByPerson(new KagmtRptScheduleTallyByPersonPk(
						AppContexts.user().companyId(), domain.getCode().v(), item.value)));
			});
		}		
		return results;
	}

	private List<KagmtRptScheduleTallyByWkp> toKagmtRptScheduleTallyByWkpEntity(ScheduleTableOutputSetting domain) {
		List<KagmtRptScheduleTallyByWkp> results = new ArrayList<KagmtRptScheduleTallyByWkp>();
		List<WorkplaceCounterCategory> list = domain.getWorkplaceCounterCategories();
		if(!list.isEmpty()) {
			list.stream().forEach(item -> {
				results.add(new KagmtRptScheduleTallyByWkp(new KagmtRptScheduleTallyByWkpPk(AppContexts.user().companyId(),
						domain.getCode().v(), item.value)));
			});
		}		
		return results;
	}

	@Override
	public void insert(String companyId, ScheduleTableOutputSetting domain) {
		this.commandProxy().insert(this.toKagmtRptScheduleEntity(domain));
		this.commandProxy().insertAll(this.toKagmtRptScheduleItemEntity(domain));
		
		if(!this.toKagmtRptScheduleTallyByPersonEntity(domain).isEmpty()) {
			this.commandProxy().insertAll(this.toKagmtRptScheduleTallyByPersonEntity(domain));
		}
		
		if(!this.toKagmtRptScheduleTallyByWkpEntity(domain).isEmpty()) {
			this.commandProxy().insertAll(this.toKagmtRptScheduleTallyByWkpEntity(domain));
		}		
	}

	@Override
	public void update(String companyId, ScheduleTableOutputSetting domain) {		
		String sql1 = "SELECT a From KagmtRptSchedule a WHERE a.pk.companyId = :companyId AND a.pk.code = :code";
		String sql2 = "SELECT a From KagmtRptScheduleItem a WHERE a.pk.companyId = :companyId AND a.pk.code = :code";	
		String sql3 = "SELECT a From KagmtRptScheduleTallyByPerson a WHERE a.pk.companyId = :companyId AND a.pk.code = :code";		
		String sql4 = "SELECT a From KagmtRptScheduleTallyByWkp a WHERE a.pk.companyId = :companyId AND a.pk.code = :code";
		
		Optional<KagmtRptSchedule> results1 = this.queryProxy().query(sql1, KagmtRptSchedule.class).setParameter("companyId", companyId)
				.setParameter("code", domain.getCode().v()).getSingle();
		if(results1.isPresent()) {
			results1.get().name =domain.getName().v();
			results1.get().additionalColumUseAtr = domain.getOutputItem().getAdditionalColumnUseAtr().value;
			results1.get().shiftBackColorUseAtr = domain.getOutputItem().getShiftBackgroundColorUseAtr().value;
			results1.get().recordDispAtr = domain.getOutputItem().getDailyDataDisplayAtr().value;
		}
	
		List<KagmtRptScheduleItem> results2 = this.queryProxy().query(sql2, KagmtRptScheduleItem.class).setParameter("companyId", companyId)
				.setParameter("code", domain.getCode().v()).getList();
		if(!results2.isEmpty()) {
			this.commandProxy().removeAll(results2);
		}
		
		List<KagmtRptScheduleTallyByPerson> results3 = this.queryProxy().query(sql3, KagmtRptScheduleTallyByPerson.class).setParameter("companyId", companyId)
				.setParameter("code", domain.getCode().v()).getList();		
		if(!results3.isEmpty()) {
			this.commandProxy().removeAll(results3);
		}
		
		List<KagmtRptScheduleTallyByWkp> results4 = this.queryProxy().query(sql4, KagmtRptScheduleTallyByWkp.class).setParameter("companyId", companyId)
				.setParameter("code", domain.getCode().v()).getList();
		if(!results4.isEmpty()) {
			this.commandProxy().removeAll(results4);
		}
		
		Optional<ScheduleTableOutputSetting> kagmtRptSchedule = this.get(companyId, domain.getCode());
		if (kagmtRptSchedule.isPresent()) {			
			this.commandProxy().updateAll(this.toKagmtRptScheduleItemEntity(domain));	
			this.commandProxy().updateAll(this.toKagmtRptScheduleTallyByPersonEntity(domain));
			this.commandProxy().updateAll(this.toKagmtRptScheduleTallyByWkpEntity(domain));
		}
		this.commandProxy().update(results1.get());
	}

	@Override
	public void delete(String companyId, OutputSettingCode code) {
		String sql1 = "SELECT a From KagmtRptSchedule a WHERE a.pk.companyId = :companyId AND a.pk.code = :code";
		String sql2 = "SELECT a From KagmtRptScheduleItem a WHERE a.pk.companyId = :companyId AND a.pk.code = :code";	
		String sql3 = "SELECT a From KagmtRptScheduleTallyByPerson a WHERE a.pk.companyId = :companyId AND a.pk.code = :code";		
		String sql4 = "SELECT a From KagmtRptScheduleTallyByWkp a WHERE a.pk.companyId = :companyId AND a.pk.code = :code";
		
		Optional<KagmtRptSchedule> results1 = this.queryProxy().query(sql1, KagmtRptSchedule.class).setParameter("companyId", companyId)
				.setParameter("code", code).getSingle();
		
		List<KagmtRptScheduleItem> results2 = this.queryProxy().query(sql2, KagmtRptScheduleItem.class).setParameter("companyId", companyId)
				.setParameter("code", code).getList();
		
		List<KagmtRptScheduleTallyByPerson> results3 = this.queryProxy().query(sql3, KagmtRptScheduleTallyByPerson.class).setParameter("companyId", companyId)
				.setParameter("code", code).getList();
		
		List<KagmtRptScheduleTallyByWkp> results4 = this.queryProxy().query(sql4, KagmtRptScheduleTallyByWkp.class).setParameter("companyId", companyId)
				.setParameter("code", code).getList();
		
		if(results1.isPresent()) {
			this.commandProxy().remove(results1.get());
		}		
		if(!results2.isEmpty()) {
			this.commandProxy().removeAll(results2);
		}
		
		if(!results3.isEmpty()) {
			this.commandProxy().removeAll(results3);
		}
		
		if(!results4.isEmpty()) {
			this.commandProxy().removeAll(results4);
		}
	}

	@Override
	public Optional<ScheduleTableOutputSetting> get(String companyId, OutputSettingCode code) {
//		List<Object[]> results = this.queryProxy().query(GET_BY_CID_AND_CODE).setParameter("companyId", companyId)
//				.setParameter("code", code.v()).getList();
//		if(results.isEmpty()) {
//			return Optional.ofNullable(null);
//		} else {
//			return Optional.of(this.toDomainFromObjByCode(results));
//		}
		
		String sql1 = "SELECT a From KagmtRptSchedule a WHERE a.pk.companyId = :companyId AND a.pk.code = :code";
		String sql2 = "SELECT a From KagmtRptScheduleItem a WHERE a.pk.companyId = :companyId AND a.pk.code = :code";	
		String sql3 = "SELECT a From KagmtRptScheduleTallyByPerson a WHERE a.pk.companyId = :companyId AND a.pk.code = :code";		
		String sql4 = "SELECT a From KagmtRptScheduleTallyByWkp a WHERE a.pk.companyId = :companyId AND a.pk.code = :code";
		
		Optional<KagmtRptSchedule> results1 = this.queryProxy().query(sql1, KagmtRptSchedule.class).setParameter("companyId", companyId)
				.setParameter("code", code).getSingle();
		
		List<KagmtRptScheduleItem> kagmtRptScheduleItems = this.queryProxy().query(sql2, KagmtRptScheduleItem.class).setParameter("companyId", companyId)
				.setParameter("code", code).getList();
		
		List<KagmtRptScheduleTallyByPerson> kagmtRptScheduleTallyByPersons = this.queryProxy().query(sql3, KagmtRptScheduleTallyByPerson.class).setParameter("companyId", companyId)
				.setParameter("code", code).getList();
		
		List<KagmtRptScheduleTallyByWkp> kagmtRptScheduleTallyByWkps = this.queryProxy().query(sql4, KagmtRptScheduleTallyByWkp.class).setParameter("companyId", companyId)
				.setParameter("code", code).getList();
		
		if(results1.isPresent()) {
			KagmtRptSchedule kagmtRptSchedule = results1.get();
			return Optional.of(this.toDomainFromEntity(kagmtRptSchedule, kagmtRptScheduleItems, kagmtRptScheduleTallyByPersons, kagmtRptScheduleTallyByWkps));
		} else {
			return Optional.ofNullable(null);
		}		
	}

	@Override
	public List<ScheduleTableOutputSetting> getList(String companyId) {
		List<Object[]> results = this.queryProxy().query(GET_BY_CID).setParameter("companyId", companyId).getList();
		if(!results.isEmpty()) {
			return this.toDomainFromObj(results);
		} else {
			return new ArrayList<ScheduleTableOutputSetting>();
		}
	
	}

	@Override
	public boolean exists(String companyId, OutputSettingCode code) {
		Optional<ScheduleTableOutputSetting> domainOpt = this.get(companyId, code);
		return domainOpt.isPresent() ? true : false;
	}

}
