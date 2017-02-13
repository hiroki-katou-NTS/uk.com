package nts.uk.ctx.pr.core.infra.repository.insurance.social.healthavgearn;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearn;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnValue;

@Stateless
public class JpaHealthInsuranceAvgearn extends JpaRepository implements HealthInsuranceAvgearnRepository {

	@Override
	public void add(HealthInsuranceAvgearn healthInsuranceAvgearn) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(HealthInsuranceAvgearn healthInsuranceAvgearn) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String id, Long version) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<HealthInsuranceAvgearn> findById(String id) {
		List<HealthInsuranceAvgearn> list = new ArrayList<HealthInsuranceAvgearn>();
		list.add(new HealthInsuranceAvgearn(new HealthInsuranceAvgearnGetMemento() {

			@Override
			public Long getVersion() {
				return null;
			}

			@Override
			public HealthInsuranceAvgearnValue getPersonalAvg() {
				return new HealthInsuranceAvgearnValue(2372l, 2717l, 928l, 14420l);
			}

			@Override
			public Integer getLevelCode() {
				return 1;
			}

			@Override
			public String getHistoryId() {
				return "1";
			}

			@Override
			public HealthInsuranceAvgearnValue getCompanyAvg() {
				return new HealthInsuranceAvgearnValue(1234l, 5678l, 456l, 456l);
			}
		}));

//		list.add(new HealthInsuranceAvgearn("2", 2, new HealthInsuranceAvgearnValue(333l, 444l, 6666l, 9220l),
//				new HealthInsuranceAvgearnValue(111l, 221l, 331l, 441l)));
//		list.add(new HealthInsuranceAvgearn("3", 3, new HealthInsuranceAvgearnValue(774l, 4446l, 3338l, 220l),
//				new HealthInsuranceAvgearnValue(122l, 331l, 661l, 551l)));
//		list.add(new HealthInsuranceAvgearn("4", 4, new HealthInsuranceAvgearnValue(4534l, 456l, 678l, 550l),
//				new HealthInsuranceAvgearnValue(112l, 231l, 431l, 531l)));
//		list.add(new HealthInsuranceAvgearn("5", 5, new HealthInsuranceAvgearnValue(124l, 4256l, 528l, 112340l),
//				new HealthInsuranceAvgearnValue(2341l, 231l, 2341l, 5321l)));
//		list.add(new HealthInsuranceAvgearn("6", 6, new HealthInsuranceAvgearnValue(555l, 455l, 3328l, 230l),
//				new HealthInsuranceAvgearnValue(12341l, 12341l, 145l, 671l)));
		return list;
	}

}
