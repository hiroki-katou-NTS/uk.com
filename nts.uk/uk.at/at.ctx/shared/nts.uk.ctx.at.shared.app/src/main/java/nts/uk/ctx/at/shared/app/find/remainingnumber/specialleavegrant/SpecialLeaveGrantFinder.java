package nts.uk.ctx.at.shared.app.find.remainingnumber.specialleavegrant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.app.find.remainingnumber.specialleavegrant.finder.SpecialLeaveGrantDto1;
import nts.uk.ctx.at.shared.app.find.remainingnumber.specialleavegrant.finder.SpecialLeaveGrantDto10;
import nts.uk.ctx.at.shared.app.find.remainingnumber.specialleavegrant.finder.SpecialLeaveGrantDto11;
import nts.uk.ctx.at.shared.app.find.remainingnumber.specialleavegrant.finder.SpecialLeaveGrantDto12;
import nts.uk.ctx.at.shared.app.find.remainingnumber.specialleavegrant.finder.SpecialLeaveGrantDto13;
import nts.uk.ctx.at.shared.app.find.remainingnumber.specialleavegrant.finder.SpecialLeaveGrantDto14;
import nts.uk.ctx.at.shared.app.find.remainingnumber.specialleavegrant.finder.SpecialLeaveGrantDto15;
import nts.uk.ctx.at.shared.app.find.remainingnumber.specialleavegrant.finder.SpecialLeaveGrantDto16;
import nts.uk.ctx.at.shared.app.find.remainingnumber.specialleavegrant.finder.SpecialLeaveGrantDto17;
import nts.uk.ctx.at.shared.app.find.remainingnumber.specialleavegrant.finder.SpecialLeaveGrantDto18;
import nts.uk.ctx.at.shared.app.find.remainingnumber.specialleavegrant.finder.SpecialLeaveGrantDto19;
import nts.uk.ctx.at.shared.app.find.remainingnumber.specialleavegrant.finder.SpecialLeaveGrantDto2;
import nts.uk.ctx.at.shared.app.find.remainingnumber.specialleavegrant.finder.SpecialLeaveGrantDto20;
import nts.uk.ctx.at.shared.app.find.remainingnumber.specialleavegrant.finder.SpecialLeaveGrantDto3;
import nts.uk.ctx.at.shared.app.find.remainingnumber.specialleavegrant.finder.SpecialLeaveGrantDto4;
import nts.uk.ctx.at.shared.app.find.remainingnumber.specialleavegrant.finder.SpecialLeaveGrantDto5;
import nts.uk.ctx.at.shared.app.find.remainingnumber.specialleavegrant.finder.SpecialLeaveGrantDto6;
import nts.uk.ctx.at.shared.app.find.remainingnumber.specialleavegrant.finder.SpecialLeaveGrantDto7;
import nts.uk.ctx.at.shared.app.find.remainingnumber.specialleavegrant.finder.SpecialLeaveGrantDto8;
import nts.uk.ctx.at.shared.app.find.remainingnumber.specialleavegrant.finder.SpecialLeaveGrantDto9;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantCode;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.PeregQueryByListEmp;
import nts.uk.shr.pereg.app.find.dto.GridPeregDomainBySidDto;
import nts.uk.shr.pereg.app.find.dto.GridPeregDomainDto;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Stateless
public class SpecialLeaveGrantFinder {

	@Inject
	private SpecialLeaveGrantRepository repo;

	public List<SpecialLeaveGrantDto> getListData(String employeeId, int specialCode) {
		List<SpecialLeaveGrantRemainingData> datalist = repo.getAll(employeeId, specialCode);
		return datalist.stream().map(domain -> SpecialLeaveGrantDto.createFromDomain(AppContexts.user().companyId(), domain))
				.collect(Collectors.toList());
	}

	public Optional<SpecialLeaveGrantRemainingData> getDetail(String specialid) {

		Optional<SpecialLeaveGrantRemainingData> data = repo.getBySpecialId(specialid);
		if (!data.isPresent()) {
			return Optional.empty();
		}
		return data;
	}

	public List<SpecialLeaveGrantDto> getListDataByCheckState(String employeeId, int specialCode, Boolean checkState) {
		List<SpecialLeaveGrantRemainingData> datalist = repo.getAll(employeeId, specialCode);
		return datalist.stream().map(domain -> SpecialLeaveGrantDto.createFromDomain(AppContexts.user().companyId(), domain))
				.collect(Collectors.toList());
	}

	public PeregDomainDto getSingleData(PeregQuery query, int specialLeaveCD) {

		String cid = AppContexts.user().companyId();
		List<SpecialLeaveGrantRemainingData> data = repo.getAll(query.getEmployeeId(), specialLeaveCD);

		switch (EnumAdaptor.valueOf(specialLeaveCD, SpecialLeaveGrantCode.class)) {
		case CS00039:
			SpecialLeaveGrantDto1 dto1 = new SpecialLeaveGrantDto1();
			if (!data.isEmpty()) {
				dto1 = SpecialLeaveGrantDto1.createFromDomain(cid, data.get(0));
			}
			return dto1;
		case CS00040:

			SpecialLeaveGrantDto2 dto2 = new SpecialLeaveGrantDto2();
			if (!data.isEmpty()) {
				dto2 = SpecialLeaveGrantDto2.createFromDomain(cid, data.get(0));
			}
			return dto2;

		case CS00041:

			SpecialLeaveGrantDto3 dto3 = new SpecialLeaveGrantDto3();
			if (!data.isEmpty()) {
				dto3 = SpecialLeaveGrantDto3.createFromDomain(cid, data.get(0));
			}
			return dto3;

		case CS00042:

			SpecialLeaveGrantDto4 dto4 = new SpecialLeaveGrantDto4();
			if (!data.isEmpty()) {
				dto4 = SpecialLeaveGrantDto4.createFromDomain(cid, data.get(0));
			}
			return dto4;

		case CS00043:

			SpecialLeaveGrantDto5 dto5 = new SpecialLeaveGrantDto5();
			if (!data.isEmpty()) {
				dto5 = SpecialLeaveGrantDto5.createFromDomain(cid, data.get(0));
			}
			return dto5;

		case CS00044:

			SpecialLeaveGrantDto6 dto6 = new SpecialLeaveGrantDto6();
			if (!data.isEmpty()) {
				dto6 = SpecialLeaveGrantDto6.createFromDomain(cid, data.get(0));
			}
			return dto6;

		case CS00045:

			SpecialLeaveGrantDto7 dto7 = new SpecialLeaveGrantDto7();
			if (!data.isEmpty()) {
				dto7 = SpecialLeaveGrantDto7.createFromDomain(cid, data.get(0));
			}
			return dto7;

		case CS00046:

			SpecialLeaveGrantDto8 dto8 = new SpecialLeaveGrantDto8();
			if (!data.isEmpty()) {
				dto8 = SpecialLeaveGrantDto8.createFromDomain(cid, data.get(0));
			}
			return dto8;

		case CS00047:

			SpecialLeaveGrantDto9 dto9 = new SpecialLeaveGrantDto9();
			if (!data.isEmpty()) {
				dto9 = SpecialLeaveGrantDto9.createFromDomain(cid, data.get(0));
			}
			return dto9;

		case CS00048:

			SpecialLeaveGrantDto10 dto10 = new SpecialLeaveGrantDto10();
			if (!data.isEmpty()) {
				dto10 = SpecialLeaveGrantDto10.createFromDomain(cid, data.get(0));
			}
			return dto10;

		case CS00059:

			SpecialLeaveGrantDto11 dto11 = new SpecialLeaveGrantDto11();
			if (!data.isEmpty()) {
				dto11 = SpecialLeaveGrantDto11.createFromDomain(cid, data.get(0));
			}
			return dto11;
		case CS00060:

			SpecialLeaveGrantDto12 dto12 = new SpecialLeaveGrantDto12();
			if (!data.isEmpty()) {
				dto12 = SpecialLeaveGrantDto12.createFromDomain(cid, data.get(0));
			}
			return dto12;
		case CS00061:

			SpecialLeaveGrantDto13 dto13 = new SpecialLeaveGrantDto13();
			if (!data.isEmpty()) {
				dto13 = SpecialLeaveGrantDto13.createFromDomain(cid, data.get(0));
			}
			return dto13;

		case CS00062:

			SpecialLeaveGrantDto14 dto14 = new SpecialLeaveGrantDto14();
			if (!data.isEmpty()) {
				dto14 = SpecialLeaveGrantDto14.createFromDomain(cid, data.get(0));
			}
			return dto14;

		case CS00063:

			SpecialLeaveGrantDto15 dto15 = new SpecialLeaveGrantDto15();
			if (!data.isEmpty()) {
				dto15 = SpecialLeaveGrantDto15.createFromDomain(cid, data.get(0));
			}
			return dto15;

		case CS00064:

			SpecialLeaveGrantDto16 dto16 = new SpecialLeaveGrantDto16();
			if (!data.isEmpty()) {
				dto16 = SpecialLeaveGrantDto16.createFromDomain(cid, data.get(0));
			}
			return dto16;

		case CS00065:

			SpecialLeaveGrantDto17 dto17 = new SpecialLeaveGrantDto17();
			if (!data.isEmpty()) {
				dto17 = SpecialLeaveGrantDto17.createFromDomain(cid, data.get(0));
			}
			return dto17;

		case CS00066:

			SpecialLeaveGrantDto18 dto18 = new SpecialLeaveGrantDto18();
			if (!data.isEmpty()) {
				dto18 = SpecialLeaveGrantDto18.createFromDomain(cid, data.get(0));
			}
			return dto18;

		case CS00067:

			SpecialLeaveGrantDto19 dto19 = new SpecialLeaveGrantDto19();
			if (!data.isEmpty()) {
				dto19 = SpecialLeaveGrantDto19.createFromDomain(cid, data.get(0));
			}
			return dto19;

		case CS00068:

			SpecialLeaveGrantDto20 dto20 = new SpecialLeaveGrantDto20();
			if (!data.isEmpty()) {
				dto20 = SpecialLeaveGrantDto20.createFromDomain(cid, data.get(0));
			}
			return dto20;

		default:
			return null;
		}
	}
	public List<GridPeregDomainDto> getAllData(PeregQueryByListEmp query, int specialLeaveCD) {
		String cid = AppContexts.user().companyId();
		List<String> sids = query.getEmpInfos().stream().map( c ->c.getEmployeeId()).collect(Collectors.toList());
		List<GridPeregDomainDto> result = new ArrayList<>();
		query.getEmpInfos().stream().forEach(c ->{
			result.add(new GridPeregDomainDto(c.getEmployeeId(), c.getPersonId(), null));
		});
		Map<String, List<SpecialLeaveGrantRemainingData>> data = repo.getAllByListEmpID(sids, specialLeaveCD).stream().collect(Collectors.groupingBy(c -> c.getEmployeeId()));
		switch (EnumAdaptor.valueOf(specialLeaveCD, SpecialLeaveGrantCode.class)) {
		case CS00039:
			result.stream().forEach(c ->{
				List<SpecialLeaveGrantRemainingData> remainingDataLst = data.get(c.getEmployeeId());
				if(remainingDataLst != null){
					if (!remainingDataLst.isEmpty()) {
						c.setPeregDomainDto(SpecialLeaveGrantDto1.createFromDomain(cid, remainingDataLst.get(0)));
					}
				}
			});
			return result;
		case CS00040:
			result.stream().forEach(c ->{
				List<SpecialLeaveGrantRemainingData> remainingDataLst = data.get(c.getEmployeeId());
				if(remainingDataLst != null){
					if (!remainingDataLst.isEmpty()) {
						c.setPeregDomainDto(SpecialLeaveGrantDto2.createFromDomain(cid, remainingDataLst.get(0)));
					}
				}
			});
			return result;
		case CS00041:
			result.stream().forEach(c ->{
				List<SpecialLeaveGrantRemainingData> remainingDataLst = data.get(c.getEmployeeId());
				if(remainingDataLst != null){
					if (!remainingDataLst.isEmpty()) {
						c.setPeregDomainDto(SpecialLeaveGrantDto3.createFromDomain(cid, remainingDataLst.get(0)));
					}
				}
			});
			return result;
		case CS00042:
			result.stream().forEach(c ->{
				List<SpecialLeaveGrantRemainingData> remainingDataLst = data.get(c.getEmployeeId());
				if(remainingDataLst != null){
					if (!remainingDataLst.isEmpty()) {
						c.setPeregDomainDto(SpecialLeaveGrantDto4.createFromDomain(cid, remainingDataLst.get(0)));
					}
				}
			});
			return result;
		case CS00043:
			result.stream().forEach(c ->{
				List<SpecialLeaveGrantRemainingData> remainingDataLst = data.get(c.getEmployeeId());
				if(remainingDataLst != null){
					if (!remainingDataLst.isEmpty()) {
						c.setPeregDomainDto(SpecialLeaveGrantDto5.createFromDomain(cid, remainingDataLst.get(0)));
					}
				}
			});
			return result;
		case CS00044:
			result.stream().forEach(c ->{
				List<SpecialLeaveGrantRemainingData> remainingDataLst = data.get(c.getEmployeeId());
				if(remainingDataLst != null){
					if (!remainingDataLst.isEmpty()) {
						c.setPeregDomainDto(SpecialLeaveGrantDto6.createFromDomain(cid, remainingDataLst.get(0)));
					}
				}
			});
			return result;
		case CS00045:
			result.stream().forEach(c ->{
				List<SpecialLeaveGrantRemainingData> remainingDataLst = data.get(c.getEmployeeId());
				if(remainingDataLst != null){
					if (!remainingDataLst.isEmpty()) {
						c.setPeregDomainDto(SpecialLeaveGrantDto7.createFromDomain(cid, remainingDataLst.get(0)));
					}
				}
			});
			return result;
		case CS00046:
			result.stream().forEach(c ->{
				List<SpecialLeaveGrantRemainingData> remainingDataLst = data.get(c.getEmployeeId());
				if(remainingDataLst != null){
					if (!remainingDataLst.isEmpty()) {
						c.setPeregDomainDto(SpecialLeaveGrantDto8.createFromDomain(cid, remainingDataLst.get(0)));
					}
				}
			});
			return result;
		case CS00047:
			result.stream().forEach(c ->{
				List<SpecialLeaveGrantRemainingData> remainingDataLst = data.get(c.getEmployeeId());
				if(remainingDataLst != null){
					if (!remainingDataLst.isEmpty()) {
						c.setPeregDomainDto(SpecialLeaveGrantDto9.createFromDomain(cid, remainingDataLst.get(0)));
					}
				}
			});
			return result;
		case CS00048:
			result.stream().forEach(c ->{
				List<SpecialLeaveGrantRemainingData> remainingDataLst = data.get(c.getEmployeeId());
				if(remainingDataLst != null){
					if (!remainingDataLst.isEmpty()) {
						c.setPeregDomainDto(SpecialLeaveGrantDto10.createFromDomain(cid, remainingDataLst.get(0)));
					}
				}
			});
			return result;
		case CS00059:
			result.stream().forEach(c ->{
				List<SpecialLeaveGrantRemainingData> remainingDataLst = data.get(c.getEmployeeId());
				if(remainingDataLst != null){
					if (!remainingDataLst.isEmpty()) {
						c.setPeregDomainDto(SpecialLeaveGrantDto11.createFromDomain(cid, remainingDataLst.get(0)));
					}
				}
			});
			return result;
		case CS00060:
			result.stream().forEach(c ->{
				List<SpecialLeaveGrantRemainingData> remainingDataLst = data.get(c.getEmployeeId());
				if(remainingDataLst != null){
					if (!remainingDataLst.isEmpty()) {
						c.setPeregDomainDto(SpecialLeaveGrantDto12.createFromDomain(cid, remainingDataLst.get(0)));
					}
				}
			});
			return result;
		case CS00061:
			result.stream().forEach(c ->{
				List<SpecialLeaveGrantRemainingData> remainingDataLst = data.get(c.getEmployeeId());
				if(remainingDataLst != null){
					if (!remainingDataLst.isEmpty()) {
						c.setPeregDomainDto(SpecialLeaveGrantDto13.createFromDomain(cid, remainingDataLst.get(0)));
					}
				}
			});
			return result;
		case CS00062:
			result.stream().forEach(c ->{
				List<SpecialLeaveGrantRemainingData> remainingDataLst = data.get(c.getEmployeeId());
				if(remainingDataLst != null){
					if (!remainingDataLst.isEmpty()) {
						c.setPeregDomainDto(SpecialLeaveGrantDto14.createFromDomain(cid, remainingDataLst.get(0)));
					}
				}
			});
			return result;
		case CS00063:
			result.stream().forEach(c ->{
				List<SpecialLeaveGrantRemainingData> remainingDataLst = data.get(c.getEmployeeId());
				if(remainingDataLst != null){
					if (!remainingDataLst.isEmpty()) {
						c.setPeregDomainDto(SpecialLeaveGrantDto15.createFromDomain(cid, remainingDataLst.get(0)));
					}
				}
			});
			return result;
		case CS00064:
			result.stream().forEach(c ->{
				List<SpecialLeaveGrantRemainingData> remainingDataLst = data.get(c.getEmployeeId());
				if(remainingDataLst != null){
					if (!remainingDataLst.isEmpty()) {
						c.setPeregDomainDto(SpecialLeaveGrantDto16.createFromDomain(cid, remainingDataLst.get(0)));
					}
				}
			});
			return result;
		case CS00065:
			result.stream().forEach(c ->{
				List<SpecialLeaveGrantRemainingData> remainingDataLst = data.get(c.getEmployeeId());
				if(remainingDataLst != null){
					if (!remainingDataLst.isEmpty()) {
						c.setPeregDomainDto(SpecialLeaveGrantDto17.createFromDomain(cid, remainingDataLst.get(0)));
					}
				}
			});
			return result;
		case CS00066:
			result.stream().forEach(c ->{
				List<SpecialLeaveGrantRemainingData> remainingDataLst = data.get(c.getEmployeeId());
				if(remainingDataLst != null){
					if (!remainingDataLst.isEmpty()) {
						c.setPeregDomainDto(SpecialLeaveGrantDto18.createFromDomain(cid, remainingDataLst.get(0)));
					}
				}
			});
			return result;
		case CS00067:
			result.stream().forEach(c ->{
				List<SpecialLeaveGrantRemainingData> remainingDataLst = data.get(c.getEmployeeId());
				if(remainingDataLst != null){
					if (!remainingDataLst.isEmpty()) {
						c.setPeregDomainDto(SpecialLeaveGrantDto19.createFromDomain(cid, remainingDataLst.get(0)));
					}
				}
			});
			return result;
		case CS00068:
			result.stream().forEach(c ->{
				List<SpecialLeaveGrantRemainingData> remainingDataLst = data.get(c.getEmployeeId());
				if(remainingDataLst != null){
					if (!remainingDataLst.isEmpty()) {
						c.setPeregDomainDto(SpecialLeaveGrantDto20.createFromDomain(cid, remainingDataLst.get(0)));
					}
				}
			});
			return result;
		default:
			return result;
		}
	}
	
	public List<GridPeregDomainBySidDto> getListData(PeregQueryByListEmp query, int specialLeaveCD){
		List<String> sids = query.getEmpInfos().stream().map( c ->c.getEmployeeId()).collect(Collectors.toList());
		List<GridPeregDomainBySidDto> result = new ArrayList<>();
		query.getEmpInfos().stream().forEach(c ->{
			result.add(new GridPeregDomainBySidDto(c.getEmployeeId(), c.getPersonId(), new ArrayList<>()));
		});
		Map<String, List<Object[]>> data = repo.getAllBySids(sids, specialLeaveCD).stream().collect(Collectors.groupingBy(c -> c[2].toString()));
		switch (EnumAdaptor.valueOf(specialLeaveCD, SpecialLeaveGrantCode.class)) {
		case CS00039:
			result.stream().forEach(c ->{
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> remainingDataLst = data.get(c.getEmployeeId());
				if (!CollectionUtil.isEmpty(remainingDataLst)) {
					remainingDataLst.stream().forEach(r -> {
						peregDto.add(SpecialLeaveGrantDto1.createFromDomain(r));
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		case CS00040:
			result.stream().forEach(c ->{
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> remainingDataLst = data.get(c.getEmployeeId());
				if (!CollectionUtil.isEmpty(remainingDataLst)) {
					remainingDataLst.stream().forEach(r -> {
						peregDto.add(SpecialLeaveGrantDto2.createFromDomain(r));
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		case CS00041:
			result.stream().forEach(c ->{
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> remainingDataLst = data.get(c.getEmployeeId());
				if (!CollectionUtil.isEmpty(remainingDataLst)) {
					remainingDataLst.stream().forEach(r -> {
						peregDto.add(SpecialLeaveGrantDto3.createFromDomain(r));
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		case CS00042:
			result.stream().forEach(c ->{
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> remainingDataLst = data.get(c.getEmployeeId());
				if (!CollectionUtil.isEmpty(remainingDataLst)) {
					remainingDataLst.stream().forEach(r -> {
						peregDto.add(SpecialLeaveGrantDto4.createFromDomain(r));
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		case CS00043:
			result.stream().forEach(c ->{
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> remainingDataLst = data.get(c.getEmployeeId());
				if (!CollectionUtil.isEmpty(remainingDataLst)) {
					remainingDataLst.stream().forEach(r -> {
						peregDto.add(SpecialLeaveGrantDto5.createFromDomain(r));
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		case CS00044:
			result.stream().forEach(c ->{
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> remainingDataLst = data.get(c.getEmployeeId());
				if (!CollectionUtil.isEmpty(remainingDataLst)) {
					remainingDataLst.stream().forEach(r -> {
						peregDto.add(SpecialLeaveGrantDto6.createFromDomain(r));
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		case CS00045:
			result.stream().forEach(c ->{
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> remainingDataLst = data.get(c.getEmployeeId());
				if (!CollectionUtil.isEmpty(remainingDataLst)) {
					remainingDataLst.stream().forEach(r -> {
						peregDto.add(SpecialLeaveGrantDto7.createFromDomain(r));
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		case CS00046:
			result.stream().forEach(c ->{
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> remainingDataLst = data.get(c.getEmployeeId());
				if (!CollectionUtil.isEmpty(remainingDataLst)) {
					remainingDataLst.stream().forEach(r -> {
						peregDto.add(SpecialLeaveGrantDto8.createFromDomain(r));
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		case CS00047:
			result.stream().forEach(c ->{
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> remainingDataLst = data.get(c.getEmployeeId());
				if (!CollectionUtil.isEmpty(remainingDataLst)) {
					remainingDataLst.stream().forEach(r -> {
						peregDto.add(SpecialLeaveGrantDto9.createFromDomain(r));
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		case CS00048:
			result.stream().forEach(c ->{
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> remainingDataLst = data.get(c.getEmployeeId());
				if (!CollectionUtil.isEmpty(remainingDataLst)) {
					remainingDataLst.stream().forEach(r -> {
						peregDto.add(SpecialLeaveGrantDto10.createFromDomain(r));
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		case CS00059:
			result.stream().forEach(c ->{
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> remainingDataLst = data.get(c.getEmployeeId());
				if (!CollectionUtil.isEmpty(remainingDataLst)) {
					remainingDataLst.stream().forEach(r -> {
						peregDto.add(SpecialLeaveGrantDto11.createFromDomain(r));
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		case CS00060:
			result.stream().forEach(c ->{
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> remainingDataLst = data.get(c.getEmployeeId());
				if (!CollectionUtil.isEmpty(remainingDataLst)) {
					remainingDataLst.stream().forEach(r -> {
						peregDto.add(SpecialLeaveGrantDto12.createFromDomain(r));
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		case CS00061:
			result.stream().forEach(c ->{
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> remainingDataLst = data.get(c.getEmployeeId());
				if (!CollectionUtil.isEmpty(remainingDataLst)) {
					remainingDataLst.stream().forEach(r -> {
						peregDto.add(SpecialLeaveGrantDto13.createFromDomain(r));
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		case CS00062:
			result.stream().forEach(c ->{
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> remainingDataLst = data.get(c.getEmployeeId());
				if (!CollectionUtil.isEmpty(remainingDataLst)) {
					remainingDataLst.stream().forEach(r -> {
						peregDto.add(SpecialLeaveGrantDto14.createFromDomain(r));
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		case CS00063:
			result.stream().forEach(c ->{
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> remainingDataLst = data.get(c.getEmployeeId());
				if (!CollectionUtil.isEmpty(remainingDataLst)) {
					remainingDataLst.stream().forEach(r -> {
						peregDto.add(SpecialLeaveGrantDto15.createFromDomain(r));
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		case CS00064:
			result.stream().forEach(c ->{
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> remainingDataLst = data.get(c.getEmployeeId());
				if (!CollectionUtil.isEmpty(remainingDataLst)) {
					remainingDataLst.stream().forEach(r -> {
						peregDto.add(SpecialLeaveGrantDto16.createFromDomain(r));
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		case CS00065:
			result.stream().forEach(c ->{
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> remainingDataLst = data.get(c.getEmployeeId());
				if (!CollectionUtil.isEmpty(remainingDataLst)) {
					remainingDataLst.stream().forEach(r -> {
						peregDto.add(SpecialLeaveGrantDto17.createFromDomain(r));
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		case CS00066:
			result.stream().forEach(c ->{
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> remainingDataLst = data.get(c.getEmployeeId());
				if (!CollectionUtil.isEmpty(remainingDataLst)) {
					remainingDataLst.stream().forEach(r -> {
						peregDto.add(SpecialLeaveGrantDto18.createFromDomain(r));
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		case CS00067:
			result.stream().forEach(c ->{
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> remainingDataLst = data.get(c.getEmployeeId());
				if (!CollectionUtil.isEmpty(remainingDataLst)) {
					remainingDataLst.stream().forEach(r -> {
						peregDto.add(SpecialLeaveGrantDto19.createFromDomain(r));
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		case CS00068:
			result.stream().forEach(c ->{
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> remainingDataLst = data.get(c.getEmployeeId());
				if (!CollectionUtil.isEmpty(remainingDataLst)) {
					remainingDataLst.stream().forEach(r -> {
						peregDto.add(SpecialLeaveGrantDto20.createFromDomain(r));
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		default:
			return result;
		}
	}
}
