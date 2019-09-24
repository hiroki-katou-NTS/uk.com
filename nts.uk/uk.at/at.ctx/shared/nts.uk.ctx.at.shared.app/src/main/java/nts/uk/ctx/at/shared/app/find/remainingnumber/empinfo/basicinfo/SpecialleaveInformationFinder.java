package nts.uk.ctx.at.shared.app.find.remainingnumber.empinfo.basicinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.SpecialLeaveCode;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.PeregQueryByListEmp;
import nts.uk.shr.pereg.app.find.dto.GridPeregDomainBySidDto;
import nts.uk.shr.pereg.app.find.dto.GridPeregDomainDto;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Stateless
public class SpecialleaveInformationFinder {
	@Inject 
	private SpecialLeaveBasicInfoRepository specialLeaveBasicInfoRepository;
	
	@Inject
	private SpecialLeaveGrantRemainService specialLeaveGrantRemainService;
	
	public PeregDomainDto getSingleData(PeregQuery query, int specialLeaveCD) {
		Optional<SpecialLeaveBasicInfo> spLeaBasicInfo = specialLeaveBasicInfoRepository.getBySidLeaveCd(query.getEmployeeId(), specialLeaveCD);
		
		String dayTime = specialLeaveGrantRemainService.calDayTime(query.getEmployeeId(),specialLeaveCD);
		
		switch (EnumAdaptor.valueOf(specialLeaveCD, SpecialLeaveCode.class)) {
		case CS00025:
			Specialleave1InformationDto dto1 = new Specialleave1InformationDto();
			if (spLeaBasicInfo.isPresent()){
				dto1 = Specialleave1InformationDto.createFromDomain(spLeaBasicInfo.get());
			}
			dto1.setSpHDRemain(dayTime);
			return dto1;
		case CS00026:
			Specialleave2informationDto dto2 = new Specialleave2informationDto();
			if (spLeaBasicInfo.isPresent()){
				dto2 = Specialleave2informationDto.createFromDomain(spLeaBasicInfo.get());
			}
			dto2.setSpHDRemain(dayTime);
			return dto2;
		case CS00027:
			Specialleave3informationDto dto3 = new Specialleave3informationDto();
			if (spLeaBasicInfo.isPresent()){
				dto3 = Specialleave3informationDto.createFromDomain(spLeaBasicInfo.get());
			}
			dto3.setSpHDRemain(dayTime);
			return dto3;
		case CS00028:
			Specialleave4informationDto dto4 = new Specialleave4informationDto();
			if (spLeaBasicInfo.isPresent()){
				dto4 = Specialleave4informationDto.createFromDomain(spLeaBasicInfo.get());
			}
			dto4.setSpHDRemain(dayTime);
			return dto4;
		case CS00029:
			Specialleave5informationDto dto5 = new Specialleave5informationDto();
			if (spLeaBasicInfo.isPresent()){
				dto5 = Specialleave5informationDto.createFromDomain(spLeaBasicInfo.get());
			}
			dto5.setSpHDRemain(dayTime);
				
			return dto5;
		case CS00030:
			Specialleave6informationDto dto6 = new Specialleave6informationDto();
			if (spLeaBasicInfo.isPresent()){
				dto6 = Specialleave6informationDto.createFromDomain(spLeaBasicInfo.get());
			}	
			dto6.setSpHDRemain(dayTime);
			return dto6;
		case CS00031:
			Specialleave7informationDto dto7 = new Specialleave7informationDto();
			if (spLeaBasicInfo.isPresent()){
				dto7 = Specialleave7informationDto.createFromDomain(spLeaBasicInfo.get());
			}
			dto7.setSpHDRemain(dayTime);
			return dto7;
		case CS00032:
			Specialleave8informationDto dto8 = new Specialleave8informationDto();
			if (spLeaBasicInfo.isPresent()){
				dto8 = Specialleave8informationDto.createFromDomain(spLeaBasicInfo.get());
			}
			dto8.setSpHDRemain(dayTime);
			return dto8;
		case CS00033:
			Specialleave9informationDto dto9 = new Specialleave9informationDto();
			if (spLeaBasicInfo.isPresent()){
				dto9 = Specialleave9informationDto.createFromDomain(spLeaBasicInfo.get());
			}
			dto9.setSpHDRemain(dayTime);
			return dto9;
		case CS00034:
			Specialleave10informationDto dto10 = new Specialleave10informationDto();
			if (spLeaBasicInfo.isPresent()){
				dto10 = Specialleave10informationDto.createFromDomain(spLeaBasicInfo.get());
			}
			dto10.setSpHDRemain(dayTime);
			return dto10;
		case CS00049:
			Specialleave11informationDto dto11 = new Specialleave11informationDto();
			if (spLeaBasicInfo.isPresent()){
				dto11 = Specialleave11informationDto.createFromDomain(spLeaBasicInfo.get());
			}
			dto11.setSpHDRemain(dayTime);
			return dto11;
		case CS00050:
			Specialleave12informationDto dto12 = new Specialleave12informationDto();
			if (spLeaBasicInfo.isPresent()){
				dto12 = Specialleave12informationDto.createFromDomain(spLeaBasicInfo.get());
			}
			dto12.setSpHDRemain(dayTime);
			return dto12;
		case CS00051:
			Specialleave13informationDto dto13 = new Specialleave13informationDto();
			if (spLeaBasicInfo.isPresent()){
				dto13 = Specialleave13informationDto.createFromDomain(spLeaBasicInfo.get());
			}
			dto13.setSpHDRemain(dayTime);
			return dto13;
		case CS00052:
			Specialleave14informationDto dto14 = new Specialleave14informationDto();
			if (spLeaBasicInfo.isPresent()){
				dto14 = Specialleave14informationDto.createFromDomain(spLeaBasicInfo.get());
			}
			dto14.setSpHDRemain(dayTime);
			return dto14;
		case CS00053:
			Specialleave15informationDto dto15 = new Specialleave15informationDto();
			if (spLeaBasicInfo.isPresent()){
				dto15 = Specialleave15informationDto.createFromDomain(spLeaBasicInfo.get());
			}
			dto15.setSpHDRemain(dayTime);
			return dto15;
		case CS00054:
			Specialleave16informationDto dto16 = new Specialleave16informationDto();
			if (spLeaBasicInfo.isPresent()){
				dto16 = Specialleave16informationDto.createFromDomain(spLeaBasicInfo.get());
			}
			dto16.setSpHDRemain(dayTime);
			return dto16;
		case CS00055:
			Specialleave17informationDto dto17 = new Specialleave17informationDto();
			if (spLeaBasicInfo.isPresent()){
				dto17 = Specialleave17informationDto.createFromDomain(spLeaBasicInfo.get());
			}
			dto17.setSpHDRemain(dayTime);
			return dto17;
		case CS00056:
			Specialleave18informationDto dto18 = new Specialleave18informationDto();
			if (spLeaBasicInfo.isPresent()){
				dto18 = Specialleave18informationDto.createFromDomain(spLeaBasicInfo.get());
			}
			dto18.setSpHDRemain(dayTime);
			return dto18;
		case CS00057:
			Specialleave19informationDto dto19 = new Specialleave19informationDto();
			if (spLeaBasicInfo.isPresent()){
				dto19 = Specialleave19informationDto.createFromDomain(spLeaBasicInfo.get());
			}
			dto19.setSpHDRemain(dayTime);
			return dto19;
		case CS00058:
			Specialleave20informationDto dto20 = new Specialleave20informationDto();
			if (spLeaBasicInfo.isPresent()){
				dto20 = Specialleave20informationDto.createFromDomain(spLeaBasicInfo.get());
			}
			dto20.setSpHDRemain(dayTime);
			return dto20;
		default:
			return null;
		}
		
	}
	
	public List<GridPeregDomainDto> getAllData(PeregQueryByListEmp query, int specialLeaveCD) {
		String cid = AppContexts.user().companyId();
		List<GridPeregDomainDto> result = new ArrayList<>();

		List<String> sids = query.getEmpInfos().stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());

		query.getEmpInfos().forEach(c -> {
			result.add(new GridPeregDomainDto(c.getEmployeeId(), c.getPersonId(), null));
		});

		Map<String, List<SpecialLeaveBasicInfo>> spLeaBasicInfoLst = specialLeaveBasicInfoRepository
				.getAllBySidsLeaveCd(cid, sids, specialLeaveCD).stream()
				.collect(Collectors.groupingBy(c -> c.getSID()));

		Map<String, String> dayTimeMap = specialLeaveGrantRemainService.calDayTime(cid, sids, specialLeaveCD);

		switch (EnumAdaptor.valueOf(specialLeaveCD, SpecialLeaveCode.class)) {
		case CS00025:
			result.stream().forEach(c -> {
				List<SpecialLeaveBasicInfo> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				Specialleave1InformationDto dto1 = new Specialleave1InformationDto();
				if (spLeaBasicInfo != null) {
					if (spLeaBasicInfo.size() > 0) {
						dto1 = Specialleave1InformationDto.createFromDomain(spLeaBasicInfo.get(0));
					}
				}
				dto1.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
				c.setPeregDomainDto(dto1);
			});
			break;
		case CS00026:
			result.stream().forEach(c -> {
				List<SpecialLeaveBasicInfo> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				Specialleave2informationDto dto = new Specialleave2informationDto();
				if (spLeaBasicInfo != null) {
					if (spLeaBasicInfo.size() > 0) {
						dto = Specialleave2informationDto.createFromDomain(spLeaBasicInfo.get(0));
					}
				}
				dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
				c.setPeregDomainDto(dto);
			});
			break;
		case CS00027:
			result.stream().forEach(c -> {
				List<SpecialLeaveBasicInfo> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				Specialleave3informationDto dto = new Specialleave3informationDto();
				if (spLeaBasicInfo != null) {
					if (spLeaBasicInfo.size() > 0) {
						dto = Specialleave3informationDto.createFromDomain(spLeaBasicInfo.get(0));
					}
				}
				dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
				c.setPeregDomainDto(dto);
			});
			break;
		case CS00028:
			result.stream().forEach(c -> {
				List<SpecialLeaveBasicInfo> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				Specialleave4informationDto dto = new Specialleave4informationDto();
				if (spLeaBasicInfo != null) {
					if (spLeaBasicInfo.size() > 0) {
						dto = Specialleave4informationDto.createFromDomain(spLeaBasicInfo.get(0));
					}
				}
				dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
				c.setPeregDomainDto(dto);
			});
			break;
		case CS00029:
			result.stream().forEach(c -> {
				List<SpecialLeaveBasicInfo> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				Specialleave5informationDto dto = new Specialleave5informationDto();
				if (spLeaBasicInfo != null) {
					if (spLeaBasicInfo.size() > 0) {
						dto = Specialleave5informationDto.createFromDomain(spLeaBasicInfo.get(0));
					}
				}
				dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
				c.setPeregDomainDto(dto);
			});
			break;
		case CS00030:
			result.stream().forEach(c -> {
				List<SpecialLeaveBasicInfo> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				Specialleave6informationDto dto = new Specialleave6informationDto();
				if (spLeaBasicInfo != null) {
					if (spLeaBasicInfo.size() > 0) {
						dto = Specialleave6informationDto.createFromDomain(spLeaBasicInfo.get(0));
					}
				}
				dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
				c.setPeregDomainDto(dto);
			});
			break;
		case CS00031:
			result.stream().forEach(c -> {
				List<SpecialLeaveBasicInfo> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				Specialleave7informationDto dto = new Specialleave7informationDto();
				if (spLeaBasicInfo != null) {
					if (spLeaBasicInfo.size() > 0) {
						dto = Specialleave7informationDto.createFromDomain(spLeaBasicInfo.get(0));
					}
				}
				dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
				c.setPeregDomainDto(dto);
			});
			break;
		case CS00032:
			result.stream().forEach(c -> {
				List<SpecialLeaveBasicInfo> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				Specialleave8informationDto dto = new Specialleave8informationDto();
				if (spLeaBasicInfo != null) {
					if (spLeaBasicInfo.size() > 0) {
						dto = Specialleave8informationDto.createFromDomain(spLeaBasicInfo.get(0));
					}
				}
				dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
				c.setPeregDomainDto(dto);
			});
			break;
		case CS00033:
			result.stream().forEach(c -> {
				List<SpecialLeaveBasicInfo> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				Specialleave9informationDto dto = new Specialleave9informationDto();
				if (spLeaBasicInfo != null) {
					if (spLeaBasicInfo.size() > 0) {
						dto = Specialleave9informationDto.createFromDomain(spLeaBasicInfo.get(0));
					}
				}
				dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
				c.setPeregDomainDto(dto);
			});
			break;
		case CS00034:
			result.stream().forEach(c -> {
				List<SpecialLeaveBasicInfo> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				Specialleave10informationDto dto = new Specialleave10informationDto();
				if (spLeaBasicInfo != null) {
					if (spLeaBasicInfo.size() > 0) {
						dto = Specialleave10informationDto.createFromDomain(spLeaBasicInfo.get(0));
					}
				}
				dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
				c.setPeregDomainDto(dto);
			});
			break;
		case CS00049:
			result.stream().forEach(c -> {
				List<SpecialLeaveBasicInfo> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				Specialleave11informationDto dto = new Specialleave11informationDto();
				if (spLeaBasicInfo != null) {
					if (spLeaBasicInfo.size() > 0) {
						dto = Specialleave11informationDto.createFromDomain(spLeaBasicInfo.get(0));
					}
				}
				dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
				c.setPeregDomainDto(dto);
			});
			break;
		case CS00050:
			result.stream().forEach(c -> {
				List<SpecialLeaveBasicInfo> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				Specialleave12informationDto dto = new Specialleave12informationDto();
				if (spLeaBasicInfo != null) {
					if (spLeaBasicInfo.size() > 0) {
						dto = Specialleave12informationDto.createFromDomain(spLeaBasicInfo.get(0));
					}
				}
				dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
				c.setPeregDomainDto(dto);
			});
			break;
		case CS00051:
			result.stream().forEach(c -> {
				List<SpecialLeaveBasicInfo> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				Specialleave13informationDto dto = new Specialleave13informationDto();
				if (spLeaBasicInfo != null) {
					if (spLeaBasicInfo.size() > 0) {
						dto = Specialleave13informationDto.createFromDomain(spLeaBasicInfo.get(0));
					}
				}
				dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
				c.setPeregDomainDto(dto);
			});
			break;
		case CS00052:
			result.stream().forEach(c -> {
				List<SpecialLeaveBasicInfo> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				Specialleave14informationDto dto = new Specialleave14informationDto();
				if (spLeaBasicInfo != null) {
					if (spLeaBasicInfo.size() > 0) {
						dto = Specialleave14informationDto.createFromDomain(spLeaBasicInfo.get(0));
					}
				}
				dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
				c.setPeregDomainDto(dto);
			});
			break;
		case CS00053:
			result.stream().forEach(c -> {
				List<SpecialLeaveBasicInfo> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				Specialleave15informationDto dto = new Specialleave15informationDto();
				if (spLeaBasicInfo != null) {
					if (spLeaBasicInfo.size() > 0) {
						dto = Specialleave15informationDto.createFromDomain(spLeaBasicInfo.get(0));
					}
				}
				dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
				c.setPeregDomainDto(dto);
			});
			break;
		case CS00054:
			result.stream().forEach(c -> {
				List<SpecialLeaveBasicInfo> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				Specialleave16informationDto dto = new Specialleave16informationDto();
				if (spLeaBasicInfo != null) {
					if (spLeaBasicInfo.size() > 0) {
						dto = Specialleave16informationDto.createFromDomain(spLeaBasicInfo.get(0));
					}
				}
				dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
				c.setPeregDomainDto(dto);
			});
			break;
		case CS00055:
			result.stream().forEach(c -> {
				List<SpecialLeaveBasicInfo> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				Specialleave17informationDto dto = new Specialleave17informationDto();
				if (spLeaBasicInfo != null) {
					if (spLeaBasicInfo.size() > 0) {
						dto = Specialleave17informationDto.createFromDomain(spLeaBasicInfo.get(0));
					}
				}
				dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
				c.setPeregDomainDto(dto);
			});
			break;
		case CS00056:
			result.stream().forEach(c -> {
				List<SpecialLeaveBasicInfo> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				Specialleave18informationDto dto = new Specialleave18informationDto();
				if (spLeaBasicInfo != null) {
					if (spLeaBasicInfo.size() > 0) {
						dto = Specialleave18informationDto.createFromDomain(spLeaBasicInfo.get(0));
					}
				}
				dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
				c.setPeregDomainDto(dto);
			});
			break;
		case CS00057:
			result.stream().forEach(c -> {
				List<SpecialLeaveBasicInfo> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				Specialleave19informationDto dto = new Specialleave19informationDto();
				if (spLeaBasicInfo != null) {
					if (spLeaBasicInfo.size() > 0) {
						dto = Specialleave19informationDto.createFromDomain(spLeaBasicInfo.get(0));
					}
				}
				dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
				c.setPeregDomainDto(dto);
			});
			break;
		case CS00058:
			result.stream().forEach(c -> {
				List<SpecialLeaveBasicInfo> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				Specialleave20informationDto dto = new Specialleave20informationDto();
				if (spLeaBasicInfo != null) {
					if (spLeaBasicInfo.size() > 0) {
						dto = Specialleave20informationDto.createFromDomain(spLeaBasicInfo.get(0));
					}
				}
				dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
				c.setPeregDomainDto(dto);
			});
			break;
		default:
			break;
		}
		return result;
	}
	
	public List<GridPeregDomainBySidDto> getListData(PeregQueryByListEmp query,  int specialLeaveCD){
		String cid = AppContexts.user().companyId();
		List<GridPeregDomainBySidDto> result = new ArrayList<>();

		List<String> sids = query.getEmpInfos().stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());

		query.getEmpInfos().forEach(c -> {
			result.add(new GridPeregDomainBySidDto(c.getEmployeeId(), c.getPersonId(), new ArrayList<>()));
		});

		Map<String, List<Object[]>> spLeaBasicInfoLst = specialLeaveBasicInfoRepository
				.getAllBySidsAndLeaveCd(cid, sids, specialLeaveCD).stream()
				.collect(Collectors.groupingBy(c -> c[0].toString()));

		Map<String, String> dayTimeMap = specialLeaveGrantRemainService.calDayTime(cid, sids, specialLeaveCD);

		switch (EnumAdaptor.valueOf(specialLeaveCD, SpecialLeaveCode.class)) {
		case CS00025:
			result.stream().forEach(c -> {
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				if(!CollectionUtil.isEmpty(spLeaBasicInfo)) {
					spLeaBasicInfo.stream().forEach(s ->{
						Specialleave1InformationDto dto = Specialleave1InformationDto.createFromDomain(s);
						dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
						peregDto.add(dto);
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		case CS00026:
			result.stream().forEach(c -> {
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				if(!CollectionUtil.isEmpty(spLeaBasicInfo)) {
					spLeaBasicInfo.stream().forEach(s ->{
						Specialleave2informationDto dto = Specialleave2informationDto.createFromDomain(s);
						dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
						peregDto.add(dto);
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		case CS00027:
			result.stream().forEach(c -> {
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				if(!CollectionUtil.isEmpty(spLeaBasicInfo)) {
					spLeaBasicInfo.stream().forEach(s ->{
						Specialleave3informationDto dto = Specialleave3informationDto.createFromDomain(s);
						dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
						peregDto.add(dto);
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		case CS00028:
			result.stream().forEach(c -> {
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				if(!CollectionUtil.isEmpty(spLeaBasicInfo)) {
					spLeaBasicInfo.stream().forEach(s ->{
						Specialleave4informationDto dto = Specialleave4informationDto.createFromDomain(s);
						dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
						peregDto.add(dto);
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		case CS00029:
			result.stream().forEach(c -> {
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				if(!CollectionUtil.isEmpty(spLeaBasicInfo)) {
					spLeaBasicInfo.stream().forEach(s ->{
						Specialleave5informationDto dto = Specialleave5informationDto.createFromDomain(s);
						dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
						peregDto.add(dto);
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		case CS00030:
			result.stream().forEach(c -> {
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				if(!CollectionUtil.isEmpty(spLeaBasicInfo)) {
					spLeaBasicInfo.stream().forEach(s ->{
						Specialleave6informationDto dto = Specialleave6informationDto.createFromDomain(s);
						dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
						peregDto.add(dto);
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		case CS00031:
			result.stream().forEach(c -> {
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				if(!CollectionUtil.isEmpty(spLeaBasicInfo)) {
					spLeaBasicInfo.stream().forEach(s ->{
						Specialleave7informationDto dto = Specialleave7informationDto.createFromDomain(s);
						dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
						peregDto.add(dto);
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		case CS00032:
			result.stream().forEach(c -> {
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				if(!CollectionUtil.isEmpty(spLeaBasicInfo)) {
					spLeaBasicInfo.stream().forEach(s ->{
						Specialleave8informationDto dto = Specialleave8informationDto.createFromDomain(s);
						dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
						peregDto.add(dto);
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		case CS00033:
			result.stream().forEach(c -> {
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				if(!CollectionUtil.isEmpty(spLeaBasicInfo)) {
					spLeaBasicInfo.stream().forEach(s ->{
						Specialleave9informationDto dto = Specialleave9informationDto.createFromDomain(s);
						dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
						peregDto.add(dto);
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		case CS00034:
			result.stream().forEach(c -> {
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				if(!CollectionUtil.isEmpty(spLeaBasicInfo)) {
					spLeaBasicInfo.stream().forEach(s ->{
						Specialleave10informationDto dto = Specialleave10informationDto.createFromDomain(s);
						dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
						peregDto.add(dto);
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		case CS00049:
			result.stream().forEach(c -> {
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				if(!CollectionUtil.isEmpty(spLeaBasicInfo)) {
					spLeaBasicInfo.stream().forEach(s ->{
						Specialleave11informationDto dto = Specialleave11informationDto.createFromDomain(s);
						dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
						peregDto.add(dto);
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		case CS00050:
			result.stream().forEach(c -> {
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				if(!CollectionUtil.isEmpty(spLeaBasicInfo)) {
					spLeaBasicInfo.stream().forEach(s ->{
						Specialleave12informationDto dto = Specialleave12informationDto.createFromDomain(s);
						dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
						peregDto.add(dto);
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		case CS00051:
			result.stream().forEach(c -> {
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				if(!CollectionUtil.isEmpty(spLeaBasicInfo)) {
					spLeaBasicInfo.stream().forEach(s ->{
						Specialleave13informationDto dto = Specialleave13informationDto.createFromDomain(s);
						dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
						peregDto.add(dto);
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		case CS00052:
			result.stream().forEach(c -> {
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				if(!CollectionUtil.isEmpty(spLeaBasicInfo)) {
					spLeaBasicInfo.stream().forEach(s ->{
						Specialleave14informationDto dto = Specialleave14informationDto.createFromDomain(s);
						dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
						peregDto.add(dto);
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		case CS00053:
			result.stream().forEach(c -> {
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				if(!CollectionUtil.isEmpty(spLeaBasicInfo)) {
					spLeaBasicInfo.stream().forEach(s ->{
						Specialleave15informationDto dto = Specialleave15informationDto.createFromDomain(s);
						dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
						peregDto.add(dto);
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		case CS00054:
			result.stream().forEach(c -> {
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				if(!CollectionUtil.isEmpty(spLeaBasicInfo)) {
					spLeaBasicInfo.stream().forEach(s ->{
						Specialleave16informationDto dto = Specialleave16informationDto.createFromDomain(s);
						dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
						peregDto.add(dto);
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		case CS00055:
			result.stream().forEach(c -> {
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				if(!CollectionUtil.isEmpty(spLeaBasicInfo)) {
					spLeaBasicInfo.stream().forEach(s ->{
						Specialleave17informationDto dto = Specialleave17informationDto.createFromDomain(s);
						dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
						peregDto.add(dto);
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		case CS00056:
			result.stream().forEach(c -> {
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				if(!CollectionUtil.isEmpty(spLeaBasicInfo)) {
					spLeaBasicInfo.stream().forEach(s ->{
						Specialleave18informationDto dto = Specialleave18informationDto.createFromDomain(s);
						dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
						peregDto.add(dto);
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		case CS00057:
			result.stream().forEach(c -> {
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				if(!CollectionUtil.isEmpty(spLeaBasicInfo)) {
					spLeaBasicInfo.stream().forEach(s ->{
						Specialleave19informationDto dto = Specialleave19informationDto.createFromDomain(s);
						dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
						peregDto.add(dto);
					});
				}
				c.setPeregDomainDto(peregDto);
			});
			return result;
		case CS00058:
			result.stream().forEach(c -> {
				List<PeregDomainDto> peregDto = new ArrayList<>();
				List<Object[]> spLeaBasicInfo = spLeaBasicInfoLst.get(c.getEmployeeId());
				if(!CollectionUtil.isEmpty(spLeaBasicInfo)) {
					spLeaBasicInfo.stream().forEach(s ->{
						Specialleave20informationDto dto = Specialleave20informationDto.createFromDomain(s);
						dto.setSpHDRemain(dayTimeMap.get(c.getEmployeeId()));
						peregDto.add(dto);
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
