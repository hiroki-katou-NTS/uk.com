/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.pubimp.person;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.query.model.person.EmployeeInfoQueryModel;
import nts.uk.query.model.person.EmployeeInfoRepository;
import nts.uk.query.model.person.EmployeeInfoResultModel;
import nts.uk.query.pub.person.BusinessTypeHistoryExport;
import nts.uk.query.pub.person.ClassificationHistoryExport;
import nts.uk.query.pub.person.EmployeeInfoDto;
import nts.uk.query.pub.person.EmployeeInfoExport;
import nts.uk.query.pub.person.EmployeeInfoPublisher;
import nts.uk.query.pub.person.EmploymentHistoryExport;
import nts.uk.query.pub.person.JobTitleHistoryExport;
import nts.uk.query.pub.person.WorkPlaceHistoryExport;

/**
 * The Class EmployeeInfoPubImpl.
 */
@Stateless
public class EmployeeInfoPubImpl implements EmployeeInfoPublisher {
	
	/** The employee info repository. */
	@Inject
	private EmployeeInfoRepository employeeInfoRepository;

	/* (non-Javadoc)
	 * @see nts.uk.query.pub.person.EmployeeInfoPublisher#find(nts.uk.query.pub.person.EmployeeInfoDto)
	 */
	@Override
	public List<EmployeeInfoExport> find(EmployeeInfoDto dto) {
		List<EmployeeInfoResultModel> resultList = this.employeeInfoRepository.find(
				EmployeeInfoQueryModel.builder()
				.employeeIds(dto.employeeIds)
				.isFindBussinessTypeInfo(dto.isFindBussinessTypeInfo)
				.isFindClasssificationInfo(dto.isFindClasssificationInfo)
				.isFindEmploymentInfo(dto.isFindEmploymentInfo)
				.isFindJobTilteInfo(dto.isFindJobTilteInfo)
				.isFindWorkPlaceInfo(dto.isFindWorkPlaceInfo)
				.peroid(dto.peroid)
				.build());
		return resultList.parallelStream().map(res -> {
			EmployeeInfoExport data = new EmployeeInfoExport();
			data.businessTypeHistorys = res.getBusinessTypeHistorys().stream()
					.map(i -> new BusinessTypeHistoryExport(i)).collect(Collectors.toList());
			data.employeeId = res.getEmployeeId();
			data.classificationHistorys = res.getClassificationHistorys().stream()
					.map(i -> new ClassificationHistoryExport(i)).collect(Collectors.toList());
			data.employmentHistorys = res.getEmploymentHistorys().stream()
					.map(i -> new EmploymentHistoryExport(i)).collect(Collectors.toList());
			data.jobTitleHistorys = res.getJobTitleHistorys().stream()
					.map(i -> new JobTitleHistoryExport(i)).collect(Collectors.toList());
			data.workPlaceHistorys = res.getWorkPlaceHistorys().stream()
					.map(i -> new WorkPlaceHistoryExport(i)).collect(Collectors.toList());
			return data;
		}).collect(Collectors.toList());
	}

}
