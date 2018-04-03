package nts.uk.ctx.at.record.app.find.remainingnumber.empinfo.basicinfo;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.SpecialLeaveCode;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfoRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainService;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRepository;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Stateless
public class SpecialleaveInformationFinder {
	@Inject 
	private SpecialLeaveBasicInfoRepository specialLeaveBasicInfoRepository;
	
	@Inject 
	private SpecialLeaveGrantRepository specialLeaveGrantRepo;
	
	@Inject
	private SpecialLeaveGrantRemainService specialLeaveGrantRemainService;
	
	@Inject
	private SpecialLeaveBasicInfoRepository specialLeaveBasicInfoRepo;
	
	public PeregDomainDto getSingleData(PeregQuery query, int specialLeaveCD) {
		Optional<SpecialLeaveBasicInfo> spLeaBasicInfo = specialLeaveBasicInfoRepository.getBySidLeaveCd(query.getEmployeeId(), specialLeaveCD);
		if (!spLeaBasicInfo.isPresent()){
			return null;
		}
		
		List<SpecialLeaveGrantRemainingData> grantRemain = specialLeaveGrantRepo.getAllByExpStatus(query.getEmployeeId(), specialLeaveCD, true);
		String dayTime = specialLeaveGrantRemainService.calDayTime(grantRemain);
		
		// TODO Item IS00300 QA 111
		Optional<SpecialLeaveBasicInfo> spLeave = specialLeaveBasicInfoRepo.getBySidLeaveCd(query.getEmployeeId(), specialLeaveCD);
		
		String grantDate = null;
		
		if (spLeave.isPresent()){
			grantDate = spLeave.get().getGrantSetting().getGrantDate().toString("yyyy/MM/dd");
		}
		switch (EnumAdaptor.valueOf(specialLeaveCD, SpecialLeaveCode.class)) {
		case CS00025:
			Specialleave1InformationDto dto1 = Specialleave1InformationDto.createFromDomain(spLeaBasicInfo.get());
			dto1.setSpHDRemain(dayTime);
			if (spLeave.isPresent()){
				dto1.setNextGrantDate(grantDate);
			}
			return dto1;
		case CS00026:
			Specialleave2informationDto dto2 = Specialleave2informationDto.createFromDomain(spLeaBasicInfo.get());
			dto2.setSpHDRemain(dayTime);
			if (spLeave.isPresent()){
				dto2.setNextGrantDate(grantDate);
			}
			return dto2;
		case CS00027:
			Specialleave3informationDto dto3 = Specialleave3informationDto.createFromDomain(spLeaBasicInfo.get());
			dto3.setSpHDRemain(dayTime);
			if (spLeave.isPresent()){
				dto3.setNextGrantDate(grantDate);
			}
			return dto3;
		case CS00028:
			Specialleave4informationDto dto4 = Specialleave4informationDto.createFromDomain(spLeaBasicInfo.get());
			dto4.setSpHDRemain(dayTime);
			if (spLeave.isPresent()){
				dto4.setNextGrantDate(grantDate);
			}
			return dto4;
		case CS00029:
			Specialleave5informationDto dto5 = Specialleave5informationDto.createFromDomain(spLeaBasicInfo.get());
			dto5.setSpHDRemain(dayTime);
			if (spLeave.isPresent()){
				dto5.setNextGrantDate(grantDate);
			}
			return dto5;
		case CS00030:
			Specialleave6informationDto dto6 = Specialleave6informationDto.createFromDomain(spLeaBasicInfo.get());
			dto6.setSpHDRemain(dayTime);
			if (spLeave.isPresent()){
				dto6.setNextGrantDate(grantDate);
			}
			return dto6;
		case CS00031:
			Specialleave7informationDto dto7 = Specialleave7informationDto.createFromDomain(spLeaBasicInfo.get());
			dto7.setSpHDRemain(dayTime);
			if (spLeave.isPresent()){
				dto7.setNextGrantDate(grantDate);
			}
			return dto7;
		case CS00032:
			Specialleave8informationDto dto8 = Specialleave8informationDto.createFromDomain(spLeaBasicInfo.get());
			dto8.setSpHDRemain(dayTime);
			if (spLeave.isPresent()){
				dto8.setNextGrantDate(grantDate);
			}
			return dto8;
		case CS00033:
			Specialleave9informationDto dto9 = Specialleave9informationDto.createFromDomain(spLeaBasicInfo.get());
			dto9.setSpHDRemain(dayTime);
			if (spLeave.isPresent()){
				dto9.setNextGrantDate(grantDate);
			}
			return dto9;
		case CS00034:
			Specialleave10informationDto dto10 = Specialleave10informationDto.createFromDomain(spLeaBasicInfo.get());
			dto10.setSpHDRemain(dayTime);
			if (spLeave.isPresent()){
				dto10.setNextGrantDate(grantDate);
			}
			return dto10;
		case CS00049:
			Specialleave11informationDto dto11 = Specialleave11informationDto.createFromDomain(spLeaBasicInfo.get());
			dto11.setSpHDRemain(dayTime);
			if (spLeave.isPresent()){
				dto11.setNextGrantDate(grantDate);
			}
			return dto11;
		case CS00050:
			Specialleave12informationDto dto12 = Specialleave12informationDto.createFromDomain(spLeaBasicInfo.get());
			dto12.setSpHDRemain(dayTime);
			if (spLeave.isPresent()){
				dto12.setNextGrantDate(grantDate);
			}
			return dto12;
		case CS00051:
			Specialleave13informationDto dto13 = Specialleave13informationDto.createFromDomain(spLeaBasicInfo.get());
			dto13.setSpHDRemain(dayTime);
			if (spLeave.isPresent()){
				dto13.setNextGrantDate(grantDate);
			}
			return dto13;
		case CS00052:
			Specialleave14informationDto dto14 = Specialleave14informationDto.createFromDomain(spLeaBasicInfo.get());
			dto14.setSpHDRemain(dayTime);
			if (spLeave.isPresent()){
				dto14.setNextGrantDate(grantDate);
			}
			return dto14;
		case CS00053:
			Specialleave15informationDto dto15 = Specialleave15informationDto.createFromDomain(spLeaBasicInfo.get());
			dto15.setSpHDRemain(dayTime);
			if (spLeave.isPresent()){
				dto15.setNextGrantDate(grantDate);
			}
			return dto15;
		case CS00054:
			Specialleave16informationDto dto16 = Specialleave16informationDto.createFromDomain(spLeaBasicInfo.get());
			dto16.setSpHDRemain(dayTime);
			if (spLeave.isPresent()){
				dto16.setNextGrantDate(grantDate);
			}
			return dto16;
		case CS00055:
			Specialleave17informationDto dto17 = Specialleave17informationDto.createFromDomain(spLeaBasicInfo.get());
			dto17.setSpHDRemain(dayTime);
			if (spLeave.isPresent()){
				dto17.setNextGrantDate(grantDate);
			}
			return dto17;
		case CS00056:
			Specialleave18informationDto dto18 = Specialleave18informationDto.createFromDomain(spLeaBasicInfo.get());
			dto18.setSpHDRemain(dayTime);
			if (spLeave.isPresent()){
				dto18.setNextGrantDate(grantDate);
			}
			return dto18;
		case CS00057:
			Specialleave19informationDto dto19 = Specialleave19informationDto.createFromDomain(spLeaBasicInfo.get());
			dto19.setSpHDRemain(dayTime);
			if (spLeave.isPresent()){
				dto19.setNextGrantDate(grantDate);
			}
			return dto19;
		case CS00058:
			Specialleave20informationDto dto20 = Specialleave20informationDto.createFromDomain(spLeaBasicInfo.get());
			dto20.setSpHDRemain(dayTime);
			if (spLeave.isPresent()){
				dto20.setNextGrantDate(grantDate);
			}
			return dto20;
		default:
			return null;
		}
		
	}
	
}
