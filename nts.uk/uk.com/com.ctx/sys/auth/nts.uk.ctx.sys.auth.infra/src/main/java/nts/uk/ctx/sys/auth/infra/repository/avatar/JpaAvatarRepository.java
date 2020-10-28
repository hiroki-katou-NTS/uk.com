package nts.uk.ctx.sys.auth.infra.repository.avatar;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.auth.dom.avatar.AvatarRepository;
import nts.uk.ctx.sys.auth.dom.avatar.UserAvatar;
import nts.uk.ctx.sys.auth.infra.entity.avatar.BpsdtPsAvatar;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JpaAvatarRepository extends JpaRepository implements AvatarRepository {

    //select by personal ID
    private static final String SELECT_BY_PERSONAL_ID = "SELECT avatar FROM BpsdtPsAvatar avatar WHERE avatar.bpsdtPsAvatarPK.personalId = :personalId";

    private static BpsdtPsAvatar toEntity(UserAvatar domain) {
        BpsdtPsAvatar entity = new BpsdtPsAvatar();
        domain.setMemento(entity);
        return entity;
    }

    @Override
    public void insert(UserAvatar userAvatar) {
        BpsdtPsAvatar entity = JpaAvatarRepository.toEntity(userAvatar);
        entity.setContractCd(AppContexts.user().contractCode());
        this.commandProxy().insert(entity);
    }

    @Override
    public void update(UserAvatar userAvatar) {
        BpsdtPsAvatar entity = JpaAvatarRepository.toEntity(userAvatar);
        Optional<BpsdtPsAvatar> oldEntity = this.queryProxy().find(entity.getBpsdtPsAvatarPK(), BpsdtPsAvatar.class);
        if (oldEntity.isPresent()) {
            BpsdtPsAvatar updateEntity = oldEntity.get();
            updateEntity.setFileId(entity.getFileId());
            this.commandProxy().update(updateEntity);
        }
    }

    @Override
    public void delete(UserAvatar userAvatar) {
        BpsdtPsAvatar entity = JpaAvatarRepository.toEntity(userAvatar);
        Optional<BpsdtPsAvatar> oldEntity = this.queryProxy().find(entity.getBpsdtPsAvatarPK(), BpsdtPsAvatar.class);
        if (oldEntity.isPresent()) {
            this.commandProxy().remove(oldEntity);
        }
    }

    @Override
    public Optional<UserAvatar> getAvatarByPersonalId(String personalId) {
        return this.queryProxy()
                .query(SELECT_BY_PERSONAL_ID, BpsdtPsAvatar.class)
                .setParameter("personalId", personalId)
                .getSingle(UserAvatar::createFromMemento);
    }
}
