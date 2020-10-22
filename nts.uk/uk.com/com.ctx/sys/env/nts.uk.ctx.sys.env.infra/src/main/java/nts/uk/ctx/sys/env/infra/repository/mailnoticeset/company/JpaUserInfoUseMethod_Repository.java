package nts.uk.ctx.sys.env.infra.repository.mailnoticeset.company;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethod_;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethod_Repository;
import nts.uk.ctx.sys.env.infra.entity.mailnoticeset.company.SevmtUserInfoUse;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JpaUserInfoUseMethod_Repository extends JpaRepository implements UserInfoUseMethod_Repository {

    // Select by cid
    private static final String SELECT_BY_CID = "SELECT m FROM SevmtUserInfoUse m WHERE m.cId = :cId";

    @Override
    public void insert(UserInfoUseMethod_ domain) {
        SevmtUserInfoUse entity = JpaUserInfoUseMethod_Repository.toEntity(domain);
        this.commandProxy().insert(entity);
    }

    @Override
    public void update(UserInfoUseMethod_ domain) {
        SevmtUserInfoUse entity = JpaUserInfoUseMethod_Repository.toEntity(domain);
        Optional<SevmtUserInfoUse> oldEntity = this.queryProxy().find(entity.getCId(), SevmtUserInfoUse.class);
        if (oldEntity.isPresent()) {
            SevmtUserInfoUse updateEntity = oldEntity.get();
            updateEntity.setUseOfProfile(entity.getUseOfProfile());
            updateEntity.setUseOfPassword(entity.getUseOfPassword());
            updateEntity.setUseOfNotice(entity.getUseOfNotice());
            updateEntity.setUseOfLanguage(entity.getUseOfLanguage());
            updateEntity.setPhoneNumberComUse(entity.getPhoneNumberComUse());
            updateEntity.setPhoneNumberComUpdatable(entity.getPhoneNumberComUpdatable());
            updateEntity.setPhoneNumberPsUse(entity.getPhoneNumberPsUse());
            updateEntity.setPhoneNumberPsUpdatable(entity.getPhoneNumberPsUpdatable());
            updateEntity.setUrgentPhoneNumber1Use(entity.getUrgentPhoneNumber1Use());
            updateEntity.setUrgentPhoneNumber1Updatable(entity.getUrgentPhoneNumber1Updatable());
            updateEntity.setUrgentPhoneNumber2Use(entity.getUrgentPhoneNumber2Use());
            updateEntity.setUrgentPhoneNumber2Updatable(entity.getUrgentPhoneNumber2Updatable());
            updateEntity.setDialInNumberUse(entity.getDialInNumberUse());
            updateEntity.setDialInNumberUpdatable(entity.getDialInNumberUpdatable());
            updateEntity.setExtensionNumberUse(entity.getExtensionNumberUse());
            updateEntity.setExtensionNumberUpdatable(entity.getExtensionNumberUpdatable());
            updateEntity.setMailComUse(entity.getMailComUse());
            updateEntity.setMailComUpdatable(entity.getMailComUpdatable());
            updateEntity.setMailPsUse(entity.getMailPsUse());
            updateEntity.setMailPsUpdatable(entity.getMailPsUpdatable());
            updateEntity.setPhoneMailComUse(entity.getPhoneMailComUse());
            updateEntity.setPhoneMailComUpdatable(entity.getPhoneMailComUpdatable());
            updateEntity.setPhoneMailPsUse(entity.getPhoneMailPsUse());
            updateEntity.setPhoneMailPsUpdatable(entity.getPhoneMailPsUpdatable());
            updateEntity.setOtherContact1Name(entity.getOtherContact1Name());
            updateEntity.setOtherContact1Use(entity.getOtherContact1Use());
            updateEntity.setOtherContact2Name(entity.getOtherContact2Name());
            updateEntity.setOtherContact2Use(entity.getOtherContact2Use());
            updateEntity.setOtherContact3Name(entity.getOtherContact3Name());
            updateEntity.setOtherContact3Use(entity.getOtherContact3Use());
            updateEntity.setOtherContact4Name(entity.getOtherContact4Name());
            updateEntity.setOtherContact4Use(entity.getOtherContact4Use());
            updateEntity.setOtherContact5Name(entity.getOtherContact5Name());
            updateEntity.setOtherContact5Use(entity.getOtherContact5Use());
            this.commandProxy().update(updateEntity);
        }
    }

    @Override
    public Optional<UserInfoUseMethod_> findByCId(String cid) {
        return this.queryProxy()
                .query(SELECT_BY_CID, SevmtUserInfoUse.class)
                .setParameter("cId", cid)
                .getSingle(UserInfoUseMethod_::createFromMemento);
    }

    private static SevmtUserInfoUse toEntity(UserInfoUseMethod_ domain) {
        SevmtUserInfoUse entity = new SevmtUserInfoUse();
        domain.setMemento(entity);
        entity.setContractCd(AppContexts.user().contractCode());
        return entity;
    }
}
