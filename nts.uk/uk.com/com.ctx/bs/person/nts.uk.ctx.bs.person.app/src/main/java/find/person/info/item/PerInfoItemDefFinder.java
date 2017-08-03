package find.person.info.item;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.info.item.PernfoItemDefRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.bs.person.dom.person.info.selectionitem.CodeNameReferenceType;
import nts.uk.ctx.bs.person.dom.person.info.selectionitem.EnumReferenceCondition;
import nts.uk.ctx.bs.person.dom.person.info.selectionitem.MasterReferenceCondition;
import nts.uk.ctx.bs.person.dom.person.info.selectionitem.ReferenceTypeState;

@Stateless
public class PerInfoItemDefFinder {

	@Inject
	private PernfoItemDefRepositoty pernfoItemDefRepositoty;

	public List<PerInfoItemDefNewLayoutDto> getAllPerInfoItemDefByCategoryId(String perInfoCtgId) {
		pernfoItemDefRepositoty.getAllPerInfoItemDefByCategoryId(perInfoCtgId);
		return null;

	};

	public Optional<PerInfoItemDefNewLayoutDto> getPerInfoItemDefById(String perInfoItemDefId) {
		return null;

	};

	public List<PerInfoItemDefNewLayoutDto> getPerInfoItemDefByListId(List<String> listItemDefId) {
		return null;

	};

	private PerInfoItemDefDto mappingFromDomaintoDto(PersonInfoItemDefinition itemDef) {

		return null;
	}

	private ReferenceTypeStateDto createRefTypeStateDto(ReferenceTypeState refTypeState) {
		MasterRefConditionDto masterRefCondition = null;
		CodeNameRefTypeDto codeNameRefType = null;
		EnumRefConditionDto enumRefCondition = null;
		int refType = refTypeState.getReferenceType().value;
		if (refType == 1) {
			MasterReferenceCondition masterRef = (MasterReferenceCondition) refTypeState;
			masterRefCondition = new MasterRefConditionDto(refType, masterRef.getMasterType().v());
		} else if (refType == 2) {
			CodeNameReferenceType codeNameRef = (CodeNameReferenceType) refTypeState;
			codeNameRefType = new CodeNameRefTypeDto(refType, codeNameRef.getTypeCode().v());
		} else {
			EnumReferenceCondition enumRef = (EnumReferenceCondition) refTypeState;
			enumRefCondition = new EnumRefConditionDto(refType, enumRef.getEnumName().v());
		}
		return new ReferenceTypeStateDto(masterRefCondition, codeNameRefType, enumRefCondition);
	}
}
