package nts.uk.ctx.at.record.dom.agreedtime;

import nts.arc.layer.infra.data.JpaRepository;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaEmploymenthoursRepository extends JpaRepository implements EmploymenthoursRepository {
    @Override
    public void insert(Employmenthours domain) {

    }

    @Override
    public void update(Employmenthours domain) {

    }

    @Override
    public void delete(Employmenthours domain) {

    }

    @Override
    public List<Employmenthours> getByCid(String cid) {
        return null;
    }

    @Override
    public Optional<Employmenthours> getByCidAndEmployCode(String cid, String employCode) {
        return Optional.empty();
    }
}
