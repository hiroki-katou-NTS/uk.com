package nts.uk.ctx.pr.core.infra.repository.wageprovision.speclayout;

import nts.uk.ctx.pr.core.dom.wageprovision.speclayout.SpecificationLayoutHist;
import nts.uk.ctx.pr.core.dom.wageprovision.speclayout.SpecificationLayoutHistRepository;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaSpecificationLayoutHistRepository implements SpecificationLayoutHistRepository {
    @Override
    public List<SpecificationLayoutHist> getAllSpecificationLayoutHist() {
        return null;
    }

    @Override
    public Optional<SpecificationLayoutHist> getSpecificationLayoutHistById(String cid, int specCd, String histId) {
        return Optional.empty();
    }

    @Override
    public void add(SpecificationLayoutHist domain) {

    }

    @Override
    public void update(SpecificationLayoutHist domain) {

    }

    @Override
    public void remove(String cid, int specCd, String histId) {

    }
}
