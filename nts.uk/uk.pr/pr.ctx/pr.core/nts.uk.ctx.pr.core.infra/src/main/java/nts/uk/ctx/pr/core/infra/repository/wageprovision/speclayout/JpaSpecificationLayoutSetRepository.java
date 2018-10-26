package nts.uk.ctx.pr.core.infra.repository.wageprovision.speclayout;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.speclayout.SpecificationLayoutSet;
import nts.uk.ctx.pr.core.dom.wageprovision.speclayout.SpecificationLayoutSetRepository;

import javax.ejb.Stateless;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaSpecificationLayoutSetRepository extends JpaRepository implements SpecificationLayoutSetRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtSpecLayoutSet f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.specLayoutSetPk.histId =:histId ";

    @Override
    public List<SpecificationLayoutSet> getAllSpecificationLayoutSet(){
        return Collections.emptyList();
    }

    @Override
    public Optional<SpecificationLayoutSet> getSpecificationLayoutSetById(String histId){
        return Optional.empty();
    }

    @Override
    public void add(SpecificationLayoutSet domain){

    }

    @Override
    public void update(SpecificationLayoutSet domain){
    }

    @Override
    public void remove(String histId){
    }
}
