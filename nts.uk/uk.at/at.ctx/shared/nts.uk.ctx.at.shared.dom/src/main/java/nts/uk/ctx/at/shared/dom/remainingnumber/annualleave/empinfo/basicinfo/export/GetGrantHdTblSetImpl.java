package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.export;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayRepository;

/**
 * 実装：年休付与テーブルを取得する
 * @author shuichi_ishida
 */
@Stateless
public class GetGrantHdTblSetImpl implements GetGrantHdTblSet {

	/** 年休社員基本情報 */
	@Inject
	private AnnLeaEmpBasicInfoRepository annLeaEmpBasicInfo;
	/** 年休付与テーブル設定 */
	@Inject
	private YearHolidayRepository yearHoliday;
	
	/** 年休付与テーブルを取得する */
	@Override
	public Optional<GrantHdTblSet> algorithm(String companyId, String employeeId) {

		// 年休社員基本情報を取得する
		Optional<AnnualLeaveEmpBasicInfo> annLeaEmpBasicInfoOpt = this.annLeaEmpBasicInfo.get(employeeId);
		if (!annLeaEmpBasicInfoOpt.isPresent()) return Optional.empty();
		String tableCode = annLeaEmpBasicInfoOpt.get().getGrantRule().getGrantTableCode().v();
		
		// 「年休付与テーブル設定」を取得する
		return this.yearHoliday.findByCode(companyId, tableCode);
	}
}
