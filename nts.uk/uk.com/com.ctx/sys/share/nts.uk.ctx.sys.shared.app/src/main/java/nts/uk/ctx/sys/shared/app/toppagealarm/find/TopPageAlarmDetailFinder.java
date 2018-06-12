package nts.uk.ctx.sys.shared.app.toppagealarm.find;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.shared.dom.toppagealarm.TopPageAlarmDetail;
import nts.uk.ctx.sys.shared.dom.toppagealarm.TopPageAlarmRepository;

@Stateless
public class TopPageAlarmDetailFinder {
	@Inject
	private TopPageAlarmRepository toppageRep;
	
//	@Inject
//	private IPersonInfoPub perPub;
	
	public List<TopPageAlarmDetailDto> findDetail(String executionLogId){
		List<TopPageAlarmDetailDto> listDto = new ArrayList<>();
		List<TopPageAlarmDetail> listDomain = toppageRep.findDetail(executionLogId);
		return null;
	}
}
