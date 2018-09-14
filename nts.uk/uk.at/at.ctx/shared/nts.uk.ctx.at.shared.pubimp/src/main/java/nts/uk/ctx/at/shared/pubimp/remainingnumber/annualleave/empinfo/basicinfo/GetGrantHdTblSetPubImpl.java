package nts.uk.ctx.at.shared.pubimp.remainingnumber.annualleave.empinfo.basicinfo;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.export.GetGrantHdTblSet;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;
import nts.uk.ctx.at.shared.pub.remainingnumber.annualleave.empinfo.basicinfo.GetGrantHdTblSetPub;
import nts.uk.ctx.at.shared.pub.remainingnumber.annualleave.empinfo.basicinfo.GrantHdTblSetExport;
import nts.uk.ctx.at.shared.pub.yearholidaygrant.CalculationMethod;
import nts.uk.ctx.at.shared.pub.yearholidaygrant.StandardCalculation;
import nts.uk.ctx.at.shared.pub.yearholidaygrant.UseSimultaneousGrant;

/**
 * 実装：年休付与テーブルを取得する
 * @author shuichi_ishida
 */
@Stateless
public class GetGrantHdTblSetPubImpl implements GetGrantHdTblSetPub {

	/** 年休付与テーブルを取得する */
	@Inject
	private GetGrantHdTblSet getGrantHdTblSet;
	
	/** 年休付与テーブルを取得する */
	@Override
	public Optional<GrantHdTblSetExport> algorithm(String companyId, String employeeId) {
		
		Optional<GrantHdTblSet> resultOpt = this.getGrantHdTblSet.algorithm(companyId, employeeId);
		if (!resultOpt.isPresent()) return Optional.empty();
		GrantHdTblSet result = resultOpt.get();
		
		return Optional.of(new GrantHdTblSetExport(
				result.getYearHolidayCode().v(),
				EnumAdaptor.valueOf(result.getCalculationMethod().value, CalculationMethod.class),
				EnumAdaptor.valueOf(result.getStandardCalculation().value, StandardCalculation.class),
				EnumAdaptor.valueOf(result.getUseSimultaneousGrant().value, UseSimultaneousGrant.class)));
	}
}
