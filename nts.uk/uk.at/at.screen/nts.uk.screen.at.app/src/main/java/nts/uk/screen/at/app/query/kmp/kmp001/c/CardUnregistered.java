package nts.uk.screen.at.app.query.kmp.kmp001.c;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampDakokuRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.GetNewestStampNotRegisteredService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampInfoDisp;
import nts.uk.screen.at.app.query.kdp.kdp003.f.dto.GetListCompanyHasStampedDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDP_打刻.KMP001_IDカードの登録.C：IDカード未登録打刻指定.メニュー別OCD.初期表示を行う
 * @author chungnt
 *
 */
@Stateless
public class CardUnregistered {
	
	@Inject
	private StampRecordRepository recordRepo;

	@Inject 
	private StampDakokuRepository dakokuRepo;
	
	public List<CardUnregisteredDto> getAll(DatePeriod period) {
		RetrieveNoStampCardRegisteredServiceRequireImpl require = new RetrieveNoStampCardRegisteredServiceRequireImpl(recordRepo, dakokuRepo);
		
		//1: 取得する(@Require, 期間): List<表示する打刻情報>
		List<StampInfoDisp> stampInfoDisps = GetNewestStampNotRegisteredService.get(require, period);
		
		if (stampInfoDisps.isEmpty()) {
			return new ArrayList<CardUnregisteredDto>();
		}
		
		return stampInfoDisps.stream().map(m -> {
			CardUnregisteredDto cardUnregisteredDto = new CardUnregisteredDto();
			cardUnregisteredDto.setStampNumber(m.getStampNumber().v());
			cardUnregisteredDto.setStampAtr(m.getStampAtr());
			cardUnregisteredDto.setStampDatetime(m.getStampDatetime());
			return cardUnregisteredDto;
		}).collect(Collectors.toList());
		
	}
	
	@AllArgsConstructor
	private class RetrieveNoStampCardRegisteredServiceRequireImpl implements GetNewestStampNotRegisteredService.Require {
		
		@Inject
		private StampRecordRepository recordRepo;
		
		@Inject 
		private StampDakokuRepository dakokuRepo;
		
		@Override
		public List<StampRecord> getStempRcNotResgistNumber(DatePeriod period) {
			return recordRepo.getStempRcNotResgistNumber(AppContexts.user().contractCode(), period);
		}

		@Override
		public List<Stamp> getStempRcNotResgistNumberStamp(String contractCode, DatePeriod period) {
			return dakokuRepo.getStempRcNotResgistNumberStamp(AppContexts.user().contractCode(), period);
		}
	}
	
}
