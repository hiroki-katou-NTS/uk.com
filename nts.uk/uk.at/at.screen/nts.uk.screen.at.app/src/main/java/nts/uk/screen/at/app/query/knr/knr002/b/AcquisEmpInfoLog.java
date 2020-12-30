package nts.uk.screen.at.app.query.knr.knr002.b;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.ContractCode;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.EmpInfoTerComAbPeriod;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.repo.EmpInfoTerComAbPeriodRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KNR_就業情報端末.KNR002_就業情報端末の監視.Ｂ：就業情報端末のログ表示.メニュー別OCD.Ｂ：就業情報端末のログ情報の取得
 * 
 * @author xuannt
 *
 */

@Stateless
public class AcquisEmpInfoLog {

	//	就業情報端末通信異常期間Repository.[4] 期間で取得する
	@Inject
	EmpInfoTerComAbPeriodRepository empInfoTerComAbPeriodRepo;

	/**
	 * 期間で取得する
	 * @param empInfoTerCode
	 * @param sTime
	 * @param eTime
	 * @return
	 */
	public List<AcquisEmpInfoLogDto> getInPeriod(String empInfoTerCode, String sTime, String eTime) {

		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		GeneralDateTime start = GeneralDateTime.fromString(sTime, "yyyy/MM/dd HH:mm:ss");
		GeneralDateTime end = GeneralDateTime.fromString(eTime, "yyyy/MM/dd HH:mm:ss");
		List<EmpInfoTerComAbPeriod> getInPeriod = this.empInfoTerComAbPeriodRepo.getInPeriod(contractCode,
				new EmpInfoTerminalCode(empInfoTerCode), start, end);
		if (getInPeriod.size() <= 0) {
			return null;
		}
		return getInPeriod.stream().map(e -> 
					new AcquisEmpInfoLogDto(empInfoTerCode, e.getLastComSuccess().toString(), 
											e.getLastestComSuccess().toString()))
				.collect(Collectors.toList());
	}
}
