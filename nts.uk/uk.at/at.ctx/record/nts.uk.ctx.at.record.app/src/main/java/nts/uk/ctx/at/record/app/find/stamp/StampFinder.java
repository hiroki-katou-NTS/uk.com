package nts.uk.ctx.at.record.app.find.stamp;

import java.util.ArrayList;
//import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.at.record.app.find.stamp.stampcard.StampCardFinder;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordAdapter;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordImport;
import nts.uk.ctx.at.record.dom.stamp.StampItem;
import nts.uk.ctx.at.record.dom.stamp.StampRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class StampFinder {
//	@Inject
//	private StampRepository stampRepo;
//	@Inject
//	private StampCardFinder cardFinder;
//	@Inject
//	private EmployeeRecordAdapter empInfor;
	/**
	 * 
	 * Get list Stamp Info by StartDate, endDate, list Card Number
	 * 
	 * */
	public List<StampDto> getListStampDetail(StampDetailParamDto stampDeParaDto){
//		String companyId = AppContexts.user().companyId();
//		//打刻カード
//		List<String> cardInfors = cardFinder.findByLstSID(stampDeParaDto.sIDs);
		List<StampDto> lstStamp = new ArrayList<>();
//
//		//If stamp card not contain sid.
//		if(cardInfors.isEmpty()){
//			StampItem domain = new StampItem(null, null, null, null, null, null, null, null, null, null, null, null);
//			EmployeeRecordImport data = empInfor.getPersonInfor(stampDeParaDto.getSIDs().get(0));
//			StampDto dto = StampDto.fromDomain(domain, data);
//			lstStamp.add(dto);
//			return lstStamp;
//		}
//		//Get 打刻情報.
//		lstStamp = stampRepo.findByEmployeeID(companyId, 
//				cardInfors, 
//				stampDeParaDto.startDate, 
//				stampDeParaDto.endDate).stream()
//				.map(item -> {
//					//Get employeeName by EmployeeRequestAdapter.
//					EmployeeRecordImport data = empInfor.getPersonInfor(item.getEmployeeId());
//					//Set employeeName for StampDto.
//					return StampDto.fromDomain(item, data);
//				}).collect(Collectors.toList());
//		
//		//If list stamp null
//		if(lstStamp.isEmpty()){
//			StampItem domain = new StampItem(null, null, null, null, null, null, null, null, null, null, null, null);
//			EmployeeRecordImport data = empInfor.getPersonInfor(stampDeParaDto.getSIDs().get(0));
//			StampDto dto = StampDto.fromDomain(domain, data);
//			lstStamp.add(dto);
//			return lstStamp;
//		}
		
		return lstStamp;
	}
}
