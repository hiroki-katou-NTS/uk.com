package nts.uk.screen.at.app.ksm008.query.b;

import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.screen.at.app.ksm008.query.b.dto.CheckSimultaneousSet;
import nts.uk.screen.at.app.ksm008.query.b.dto.PersonInfoDto;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 社員コードとビジネスネームを取得する
 */
@Stateless
public class GetEmployeeInfoQuery {

    @Inject
    private EmployeeDataMngInfoRepository empMngDataRepo;
    @Inject
    private PersonRepository personRepository;


    public List<PersonInfoDto> get(CheckSimultaneousSet param) {

        // ドメインモデル「社員データ管理情報」を全て取得する
        List<EmployeeDataMngInfo> empInfos = empMngDataRepo.findByListEmployeeId(param.getSids());

        // ドメインモデル「個人基本情報」を全て取得する
        Map<String, Person> mapPerson = personRepository.getAllPersonByPids(
                empInfos.stream().map(EmployeeDataMngInfo::getPersonId).collect(Collectors.toList()
                )).stream().collect(Collectors.toMap(Person::getPersonId, i -> i));

        return empInfos.stream().map(i -> new PersonInfoDto(
                i.getEmployeeId(),
                i.getEmployeeCode().v(),
                mapPerson.containsKey(i.getPersonId()) ? mapPerson.get(i.getPersonId()).getPersonNameGroup().getBusinessName().v() : null
        )).collect(Collectors.toList());

    }

}
