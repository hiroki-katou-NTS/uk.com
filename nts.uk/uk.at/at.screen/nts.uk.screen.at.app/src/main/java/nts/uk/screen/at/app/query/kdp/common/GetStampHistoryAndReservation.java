package nts.uk.screen.at.app.query.kdp.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampDakokuRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.EmployeeStampInfo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.GetListStampEmployeeService;
import nts.uk.screen.at.app.query.kdp.kdp001.a.EmployeeStampInfoDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * DS: 打刻入力の打刻履歴と予約を取得する
 * UKDesign.UniversalK.就業.KDP_打刻.KDP_打刻共通.打刻入力機能.打刻入力の打刻履歴と予約を取得する
 * 
 * @author chungnt
 *
 */

@Stateless
public class GetStampHistoryAndReservation {

	@Inject
	private GetListStampEmployeeService getListStampEmployeeService;

	@Inject
	private StampCardRepository stampCardRepository;

	@Inject
	private StampDakokuRepository stampDakokuRepository;

	public List<EmployeeStampInfoDto> getStampHistoryAndReservation(GetStampHistoryAndReservationInput param) {
		// EmployeeStampInfoDto result = new EmployeeStampInfoDto();

		GetListStampEmployeeServiceRequireImpl require = new GetListStampEmployeeServiceRequireImpl(stampCardRepository,
				stampDakokuRepository);

		List<EmployeeStampInfo> employeeStampInfos = new ArrayList<>();

		for (GeneralDate i = param.getStartDate(); i.equals(param.getEndDate()); i.addDays(1)) {
			Optional<EmployeeStampInfo> employeeStampInfo = this.getListStampEmployeeService.get(require,
					param.getSid(), i);
			if (employeeStampInfo.isPresent()) {
				employeeStampInfos.add(employeeStampInfo.get());
			}
		}

		return employeeStampInfos.stream().map(m -> {
			return EmployeeStampInfoDto.fromDomain(m);
		}).collect(Collectors.toList());
	}

	@AllArgsConstructor
	private class GetListStampEmployeeServiceRequireImpl implements GetListStampEmployeeService.Require {

		private StampCardRepository stampCardRepository;

		private StampDakokuRepository stampDakokuRepository;

		@Override
		public List<StampCard> getListStampCard(String sid) {
			return stampCardRepository.getListStampCard(sid);
		}

		@Override
		public List<Stamp> getStamp(List<StampNumber> stampNumbers, GeneralDate stampDateTime) {
			return stampDakokuRepository.get(AppContexts.user().contractCode(), stampNumbers, stampDateTime);
		}

	}

}
