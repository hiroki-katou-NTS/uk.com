package nts.uk.ctx.sys.env.infra.repository.mailnoticeset.company;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.EmailDestinationFunction;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethod_;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethod_Repository;
import nts.uk.ctx.sys.env.infra.entity.mailnoticeset.company.SevmtUserInfoUse;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;

import java.util.ArrayList;
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
		SevmtUserInfoUse oldEntity = this.queryProxy().find(entity.getCId(), SevmtUserInfoUse.class).get();
		oldEntity.setUseOfProfile(entity.getUseOfProfile());
		oldEntity.setUseOfPassword(entity.getUseOfPassword());
		oldEntity.setUseOfNotice(entity.getUseOfNotice());
		oldEntity.setUseOfLanguage(entity.getUseOfLanguage());
		oldEntity.setPhoneNumberComUse(entity.getPhoneNumberComUse());
		oldEntity.setPhoneNumberComUpdatable(entity.getPhoneNumberComUpdatable());
		oldEntity.setPhoneNumberPsUse(entity.getPhoneNumberPsUse());
		oldEntity.setPhoneNumberPsUpdatable(entity.getPhoneNumberPsUpdatable());
		oldEntity.setUrgentPhoneNumber1Use(entity.getUrgentPhoneNumber1Use());
		oldEntity.setUrgentPhoneNumber1Updatable(entity.getUrgentPhoneNumber1Updatable());
		oldEntity.setUrgentPhoneNumber2Use(entity.getUrgentPhoneNumber2Use());
		oldEntity.setUrgentPhoneNumber2Updatable(entity.getUrgentPhoneNumber2Updatable());
		oldEntity.setDialInNumberUse(entity.getDialInNumberUse());
		oldEntity.setDialInNumberUpdatable(entity.getDialInNumberUpdatable());
		oldEntity.setExtensionNumberUse(entity.getExtensionNumberUse());
		oldEntity.setExtensionNumberUpdatable(entity.getExtensionNumberUpdatable());
		oldEntity.setMailComUse(entity.getMailComUse());
		oldEntity.setMailComUpdatable(entity.getMailComUpdatable());
		oldEntity.setMailPsUse(entity.getMailPsUse());
		oldEntity.setMailPsUpdatable(entity.getMailPsUpdatable());
		oldEntity.setPhoneMailComUse(entity.getPhoneMailComUse());
		oldEntity.setPhoneMailComUpdatable(entity.getPhoneMailComUpdatable());
		oldEntity.setPhoneMailPsUse(entity.getPhoneMailPsUse());
		oldEntity.setPhoneMailPsUpdatable(entity.getPhoneMailPsUpdatable());
		oldEntity.setOtherContact1Name(entity.getOtherContact1Name());
		oldEntity.setOtherContact1Use(entity.getOtherContact1Use());
		oldEntity.setOtherContact2Name(entity.getOtherContact2Name());
		oldEntity.setOtherContact2Use(entity.getOtherContact2Use());
		oldEntity.setOtherContact3Name(entity.getOtherContact3Name());
		oldEntity.setOtherContact3Use(entity.getOtherContact3Use());
		oldEntity.setOtherContact4Name(entity.getOtherContact4Name());
		oldEntity.setOtherContact4Use(entity.getOtherContact4Use());
		oldEntity.setOtherContact5Name(entity.getOtherContact5Name());
		oldEntity.setOtherContact5Use(entity.getOtherContact5Use());
		oldEntity.setEmailDestinationFunctions(entity.getEmailDestinationFunctions());
		this.commandProxy().update(oldEntity);
	}

	@Override
	public Optional<UserInfoUseMethod_> findByCId(String cid) {
		return this.queryProxy().query(SELECT_BY_CID, SevmtUserInfoUse.class).setParameter("cId", cid)
				.getSingle(UserInfoUseMethod_::createFromMemento);
	}

	private static SevmtUserInfoUse toEntity(UserInfoUseMethod_ domain) {
		SevmtUserInfoUse entity = new SevmtUserInfoUse();
		domain.setMemento(entity);
		entity.setContractCd(AppContexts.user().contractCode());
		return entity;
	}
}
