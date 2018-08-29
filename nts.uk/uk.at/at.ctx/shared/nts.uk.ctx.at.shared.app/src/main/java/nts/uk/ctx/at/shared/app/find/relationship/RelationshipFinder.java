package nts.uk.ctx.at.shared.app.find.relationship;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.relationship.repository.RelationshipRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author yennth
 *
 */
@Stateless
public class RelationshipFinder {
	@Inject
	private RelationshipRepository relaRep;

	/**
	 * get all relationship
	 * 
	 * @return
	 */
	public List<RelationshipDto> finder() {
		String companyId = AppContexts.user().companyId();
		return this.relaRep.findAll(companyId).stream().map(item -> {
			return RelationshipDto.fromJavaType(item.getRelationshipCode().v(), item.getRelationshipName().v(),
					item.isThreeParentOrLess());
		}).collect(Collectors.toList());
	}

	public List<RelationshipDto> findAllWithSetting(int sHENo) {
		String companyId = AppContexts.user().companyId();

		List<RelationshipDto> relpList = this.finder();
		if (CollectionUtil.isEmpty(relpList)) {
			throw new BusinessException("Msg_375");
		}

		List<String> relpCds = relpList.stream().map(x -> x.getRelationshipCode()).collect(Collectors.toList());

		if (!CollectionUtil.isEmpty(relpCds)) {
			List<String> settings = this.relaRep.findSettingWithCds(companyId, sHENo, relpCds);

			relpList.forEach(x -> {
				if (settings.contains(x.getRelationshipCode())) {
					x.setSetting(true);

				}
			});
		}
		return relpList;

	}
}
