package nts.uk.ctx.at.shared.app.find.remainingnumber.nursingcareleave;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.CareLeaveDataInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.CareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.CareLeaveRemainingInfoRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.PeregQueryByListEmp;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.GridPeregDomainBySidDto;
import nts.uk.shr.pereg.app.find.dto.GridPeregDomainDto;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Stateless
public class CareLeaveInfoFinder implements PeregFinder<CareLeaveInfoDto> {

	@Inject
	private CareLeaveRemainingInfoRepository careInfoRepo;
	@Override
	public String targetCategoryCode() {
		return "CS00036";
	}

	@Override
	public Class<CareLeaveInfoDto> dtoClass() {
		return CareLeaveInfoDto.class;
	}

	@Override
	public DataClassification dataType() {
		return DataClassification.EMPLOYEE;
	}

	@Override
	public PeregDomainDto getSingleData(PeregQuery query) {
		Optional<CareLeaveDataInfo> data = careInfoRepo.getCareInfoDataBysId(query.getEmployeeId());

		return data.map(m -> CareLeaveInfoDto.createFromDomain(
				query.getEmployeeId(),
				Optional.ofNullable(m.getChildCareLeaveRemainingInfo()),
				Optional.ofNullable(m.getChildCareUsedNumberData()),
				Optional.ofNullable(m.getCareLeaveRemainingInfo()),
				Optional.ofNullable(m.getCareUsedNumberData())))
				.orElse(null);
	}

	@Override
	public List<PeregDomainDto> getListData(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ComboBoxObject> getListFirstItems(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GridPeregDomainDto> getAllData(PeregQueryByListEmp query) {
		String cid = AppContexts.user().companyId();

		List<GridPeregDomainDto> result = new ArrayList<>();

		List<String> sids = query.getEmpInfos().stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());

		query.getEmpInfos().forEach(c -> {
			result.add(new GridPeregDomainDto(c.getEmployeeId(), c.getPersonId(), null));
		});

		if(sids.isEmpty()) {
			return new ArrayList<>();
		}
		List<CareLeaveDataInfo> data = careInfoRepo.getAllCareInfoDataBysId(cid, sids);

		result.parallelStream().forEach(c ->{
			Optional<CareLeaveDataInfo> careInfoOpt = data.parallelStream().filter(
					item -> (item.getCareLeaveRemainingInfo() != null && item.getCareLeaveRemainingInfo().getSId().equals(c.getEmployeeId())) 
					|| (item.getChildCareLeaveRemainingInfo() != null && item.getChildCareLeaveRemainingInfo().getSId().equals(c.getEmployeeId())))
					.findFirst();
			if(careInfoOpt.isPresent()) {
				CareLeaveDataInfo careInfo = careInfoOpt.get();
				c.setPeregDomainDto(CareLeaveInfoDto.createFromDomain(
						c.getEmployeeId(),
						Optional.ofNullable(careInfo.getChildCareLeaveRemainingInfo()),
						Optional.ofNullable(careInfo.getChildCareUsedNumberData()),
						Optional.ofNullable(careInfo.getCareLeaveRemainingInfo()),
						Optional.ofNullable(careInfo.getCareUsedNumberData())));
			}
		});

		return result;
	}

	@Override
	public List<GridPeregDomainBySidDto> getListData(PeregQueryByListEmp query) {
		String cid = AppContexts.user().companyId();

		List<GridPeregDomainBySidDto> result = new ArrayList<>();

		Map<String, Object> enums = new HashMap<String, Object>();

		List<String> sids = query.getEmpInfos().stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());

		query.getEmpInfos().forEach(c -> {
			result.add(new GridPeregDomainBySidDto(c.getEmployeeId(), c.getPersonId(), new ArrayList<>()));
		});

		if(sids.isEmpty()) {
			return new ArrayList<>();
		}

		List<CareLeaveDataInfo> data = careInfoRepo.getAllCareInfoDataBysIdCps013(cid, sids, enums);

		result.stream().forEach(c ->{
			Optional<CareLeaveDataInfo> careInfoOpt = data.parallelStream().filter(
					item -> item.getCareLeaveRemainingInfo().getSId().equals(c.getEmployeeId())).findFirst();
			if(careInfoOpt.isPresent()) {
				CareLeaveDataInfo careInfo = careInfoOpt.get();
				c.setPeregDomainDto(Arrays.asList(CareLeaveInfoDto.createFromDomainCps013(
						c.getEmployeeId(),
						Optional.ofNullable(careInfo.getChildCareLeaveRemainingInfo()),
						Optional.ofNullable(careInfo.getChildCareUsedNumberData()),
						Optional.ofNullable(careInfo.getCareLeaveRemainingInfo()),
						Optional.ofNullable(careInfo.getCareUsedNumberData()), enums)));
			}
		});
		return result;
	}
}
