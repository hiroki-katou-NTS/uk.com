package nts.uk.screen.at.app.cmm040.worklocation;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.command.worklocation.InsertUpdateWorkLocationCmd;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocation;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocationRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 勤務場所を取得する
 * UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM040_勤務場所の登録.A：勤務場所の登録.メニュー別OCD.勤務場所を取得する
 * 
 * @author tutk
 *
 */
@Stateless
public class GetPlaceOfWork {

	@Inject
	private WorkLocationRepository workLocationRepository;

	public GetPlaceOfWorkOutput get() {
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		List<WorkLocation> listData = workLocationRepository.findAll(contractCode);
		return new GetPlaceOfWorkOutput(companyId,
				listData.stream().map(c -> InsertUpdateWorkLocationCmd.toDto(c)).collect(Collectors.toList()));
	}
}
