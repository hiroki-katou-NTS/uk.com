package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support;

import java.util.Optional;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SupportCardRequireImpl implements SupportCard.Require {

	private SupportCardEditRepository repository;
	
	@Override
	public Optional<SupportCardEdit> getSupportCardEditSetting(String cid) {
		return this.repository.get(cid);
	}
}
