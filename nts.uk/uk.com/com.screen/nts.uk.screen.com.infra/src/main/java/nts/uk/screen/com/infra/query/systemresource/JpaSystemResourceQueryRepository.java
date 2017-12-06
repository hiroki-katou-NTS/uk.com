/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.com.infra.query.systemresource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.screen.com.app.repository.systemresource.SystemResourceData;
import nts.uk.screen.com.app.repository.systemresource.SystemResourceQueryRepository;
import nts.uk.shr.infra.i18n.resource.data.CisctI18NResource;
import nts.uk.shr.infra.i18n.resource.data.CisctI18NResourcePK;


/**
 * The Class JpaSystemResourceQueryRepository.
 */
@Stateless
public class JpaSystemResourceQueryRepository extends JpaRepository implements SystemResourceQueryRepository {
	
	/** The Constant QUERY_STRING. */
	private static final String QUERY_STRING = "SELECT rs From CisctI18NResource rs "
			                                   + "WHERE rs.pk.resourceId IN :resourceIdList";

	/* (non-Javadoc)
	 * @see nts.uk.screen.com.app.repository.systemresource.SystemResourceQueryRepository#findListResource()
	 */
	@Override
	public List<SystemResourceData> findListResource() {
		
		List<String> list = Arrays.asList("Com_Person", "Com_Employment", "Com_Class", "Com_Jobtitle",
										  "Com_Department", "Com_Workplace", "Com_Office", "Com_Company",
										  "Com_Contract", "Com_User", "Com_Project", "Com_AdHocWork",
										  "Com_BindingTime", "Com_AttendanceDays", "Com_AbsenceDays", "Com_PaidHoliday",
										  "Com_FundedPaidHoliday", "Com_SubstituteWork", "Com_CompensationHoliday", "Com_ExsessHoliday",
										  "Com_PlanedPaidHoliday","Com_SubstituteHoliday");
		
		List<CisctI18NResource> ab = this.queryProxy().query(QUERY_STRING, CisctI18NResource.class)
										.setParameter("resourceIdList", list).getList();
		
		List<SystemResourceData> result =  ab.stream().map(e -> this.toData(e)).collect(Collectors.toList());
		
		return result;
	}

	/* (non-Javadoc)
	 * @see nts.uk.screen.com.app.repository.systemresource.SystemResourceQueryRepository#update(nts.uk.screen.com.app.systemresource.dto.SystemResourceDto)
	 */
	@Override
	public void update(CisctI18NResource entity){
		this.commandProxy().update(entity);
	}

	/* (non-Javadoc)
	 * @see nts.uk.screen.com.app.repository.systemresource.SystemResourceQueryRepository#findByResourceId(java.lang.String)
	 */
	@Override
	public Optional<CisctI18NResource> findByResourceId(String resourceId) {
		return this.queryProxy().find(CisctI18NResourcePK.createForAllPrograms("ja" , "C" , resourceId), CisctI18NResource.class);
	}
	
	/**
	 * To data.
	 *
	 * @param entity the entity
	 * @return the system resource data
	 */
	private SystemResourceData toData(CisctI18NResource entity){
		SystemResourceData data = SystemResourceData.builder()
			.resourceContent(entity.content)
			.resourceId(entity.pk.resourceId).build();
		
		return data;
	}
}
