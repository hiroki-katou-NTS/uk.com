package nts.uk.ctx.sys.portal.dom.toppagealarm.service.registeralarmdata;

import java.util.Optional;

import lombok.AllArgsConstructor;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenuRepository;
import nts.uk.ctx.sys.portal.dom.toppagealarm.ToppageAlarmData;
import nts.uk.ctx.sys.portal.dom.toppagealarm.ToppageAlarmDataRepository;

@AllArgsConstructor
public class DefaultRequireImpl implements RegisterAlarmDataDs.Require {

	private ToppageAlarmDataRepository repo;

	private StandardMenuRepository StandardMenuRepo;

	@Override
	public Optional<ToppageAlarmData> get(String cid, String sid, int dispAtr, int alarmCls, Optional<String> patternCode, Optional<String> notificationId) {
		return repo.get(cid, sid, dispAtr, alarmCls, patternCode, notificationId);
	}

	@Override
	public void insert(ToppageAlarmData domain) {
		repo.insert(domain);
	}

	@Override
	public void update(ToppageAlarmData domain) {
		repo.update(domain);
	}

	@Override
	public Optional<String> getUrl(String cid, int system, int menuClassfication, String programId, String screenId) {
		return StandardMenuRepo.getUrl(cid, system, menuClassfication, programId, screenId);
	}

}
