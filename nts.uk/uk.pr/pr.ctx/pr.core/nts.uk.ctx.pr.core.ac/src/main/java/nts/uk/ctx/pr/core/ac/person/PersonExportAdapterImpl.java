package nts.uk.ctx.pr.core.ac.person;

import nts.uk.ctx.bs.person.pub.person.PersonPub;
import nts.uk.ctx.pr.core.dom.adapter.person.FullNameSetExport;
import nts.uk.ctx.pr.core.dom.adapter.person.PersonExport;
import nts.uk.ctx.pr.core.dom.adapter.person.PersonExportAdapter;
import nts.uk.ctx.pr.core.dom.adapter.person.PersonNameGroupExport;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
@Stateless
public class PersonExportAdapterImpl implements PersonExportAdapter {
    @Inject
    private PersonPub personPub;

    @Override
    public List<PersonExport> findByPids(List<String> personIds) {
        return personPub.findByPids(personIds).stream().map(item -> {
           return new PersonExport(
                   item.getBirthDate(),
                   item.getGender(),
                   item.getPersonId(),
                   new PersonNameGroupExport(
                           item.getPersonNameGroup().getBusinessName(),
                           item.getPersonNameGroup().getBusinessNameKana(),
                           item.getPersonNameGroup().getBusinessOtherName(),
                           item.getPersonNameGroup().getBusinessEnglishName(),
                           new FullNameSetExport(item.getPersonNameGroup().getPersonName().getFullName(), item.getPersonNameGroup().getPersonName().getFullNameKana()),
                           new FullNameSetExport(item.getPersonNameGroup().getPersonalNameMultilingual().getFullName(), item.getPersonNameGroup().getPersonalNameMultilingual().getFullNameKana()),
                           new FullNameSetExport(item.getPersonNameGroup().getPersonRomanji().getFullName(), item.getPersonNameGroup().getPersonRomanji().getFullNameKana()),
                           new FullNameSetExport(item.getPersonNameGroup().getTodokedeFullName().getFullName(), item.getPersonNameGroup().getTodokedeFullName().getFullNameKana()),
                           new FullNameSetExport(item.getPersonNameGroup().getOldName().getFullName(), item.getPersonNameGroup().getOldName().getFullNameKana())
                   )
           );
        }).collect(Collectors.toList());
    }
}
