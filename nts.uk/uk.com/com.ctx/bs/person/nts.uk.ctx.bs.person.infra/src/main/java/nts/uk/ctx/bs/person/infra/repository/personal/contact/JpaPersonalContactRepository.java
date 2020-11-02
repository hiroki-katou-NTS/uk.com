package nts.uk.ctx.bs.person.infra.repository.personal.contact;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.personal.contact.PersonalContact;
import nts.uk.ctx.bs.person.dom.person.personal.contact.PersonalContactRepository;
import nts.uk.ctx.bs.person.infra.entity.person.personal.BpsmtContactAddrPs;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JpaPersonalContactRepository extends JpaRepository implements PersonalContactRepository {

    //select by personal ID
    private static final String SELECT_BY_PERSONAL_ID = "SELECT m FROM BpsmtContactAddrPs m WHERE m.bpsmtContactAddrPsPK.personalId = :personalId";

    private static BpsmtContactAddrPs toEntity(PersonalContact domain) {
        BpsmtContactAddrPs entity = new BpsmtContactAddrPs();
        domain.setMemento(entity);
        return entity;
    }

    @Override
    public void insert(PersonalContact personalContact) {
        BpsmtContactAddrPs entity = JpaPersonalContactRepository.toEntity(personalContact);
        entity.setContractCd(AppContexts.user().contractCode());
        this.commandProxy().insert(entity);
    }

    @Override
    public void update(PersonalContact personalContact) {
        BpsmtContactAddrPs entity = JpaPersonalContactRepository.toEntity(personalContact);
        Optional<BpsmtContactAddrPs> oldEntity = this.queryProxy().find(entity.getBpsmtContactAddrPsPK(), BpsmtContactAddrPs.class);
        if (oldEntity.isPresent()) {
            BpsmtContactAddrPs updateEntity = oldEntity.get();
            updateEntity.setMailAddress(entity.getMailAddress());
            updateEntity.setIsMailAddressDisplay(entity.getIsMailAddressDisplay());
            updateEntity.setMobileEmailAddress(entity.getMobileEmailAddress());
            updateEntity.setIsMobileEmailAddressDisplay(entity.getIsMobileEmailAddressDisplay());
            updateEntity.setPhoneNumber(entity.getPhoneNumber());
            updateEntity.setIsPhoneNumberDisplay(entity.getIsPhoneNumberDisplay());
            updateEntity.setRemark1(entity.getRemark1());
            updateEntity.setContactName1(entity.getContactName1());
            updateEntity.setPhoneNumber1(entity.getPhoneNumber1());
            updateEntity.setIsEmergencyContact1Display(entity.getIsEmergencyContact1Display());
            updateEntity.setRemark2(entity.getRemark2());
            updateEntity.setContactName2(entity.getContactName2());
            updateEntity.setPhoneNumber2(entity.getPhoneNumber2());
            updateEntity.setIsEmergencyContact2Display(entity.getIsEmergencyContact2Display());
            updateEntity.setAddress1(entity.getAddress1());
            updateEntity.setAddress2(entity.getAddress2());
            updateEntity.setAddress3(entity.getAddress3());
            updateEntity.setAddress4(entity.getAddress4());
            updateEntity.setAddress5(entity.getAddress5());
            updateEntity.setIsDisplay1(entity.getIsDisplay1());
            updateEntity.setIsDisplay2(entity.getIsDisplay2());
            updateEntity.setIsDisplay3(entity.getIsDisplay3());
            updateEntity.setIsDisplay4(entity.getIsDisplay4());
            updateEntity.setIsDisplay5(entity.getIsDisplay5());
            this.commandProxy().update(updateEntity);
        }
    }

    @Override
    public Optional<PersonalContact> getByPersonalId(String personalId) {
        return this.queryProxy()
                .query(SELECT_BY_PERSONAL_ID, BpsmtContactAddrPs.class)
                .setParameter("personalId", personalId)
                .getSingle(PersonalContact::createFromMemento);
    }
}
