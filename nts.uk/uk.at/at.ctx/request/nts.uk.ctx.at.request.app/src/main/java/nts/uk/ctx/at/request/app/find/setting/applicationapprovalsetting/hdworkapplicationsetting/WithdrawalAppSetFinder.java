package nts.uk.ctx.at.request.app.find.setting.applicationapprovalsetting.hdworkapplicationsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.WithdrawalAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.WithdrawalAppSetRepository;

@Stateless
public class WithdrawalAppSetFinder {
	@Inject
	private WithdrawalAppSetRepository withRep;
	public WithdrawalAppSetDto findByCid(){
		Optional<WithdrawalAppSet> with = withRep.getWithDraw();
		if(with.isPresent()){
			return WithdrawalAppSetDto.convertToDto(with.get());
		}
		return null;
	}
}
