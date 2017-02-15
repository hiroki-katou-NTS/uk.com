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
		return this.getMockData();
	}

	public List<HealthInsuranceAvgearn> getMockData() {
		List<HealthInsuranceAvgearn> list = new ArrayList<HealthInsuranceAvgearn>();
		list.add(new HealthInsuranceAvgearn(new HealthInsuranceAvgearnGetMemento() {

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
		list.add(new HealthInsuranceAvgearn(new HealthInsuranceAvgearnGetMemento() {

			@Override
			public HealthInsuranceAvgearnValue getPersonalAvg() {
				return new HealthInsuranceAvgearnValue(2372l, 2717l, 928l, 14420l);
			}

			@Override
			public Integer getLevelCode() {
				return 2;
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
		list.add(new HealthInsuranceAvgearn(new HealthInsuranceAvgearnGetMemento() {

			@Override
			public HealthInsuranceAvgearnValue getPersonalAvg() {
				return new HealthInsuranceAvgearnValue(2372l, 2717l, 928l, 14420l);
			}

			@Override
			public Integer getLevelCode() {
				return 3;
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
		list.add(new HealthInsuranceAvgearn(new HealthInsuranceAvgearnGetMemento() {

			@Override
			public HealthInsuranceAvgearnValue getPersonalAvg() {
				return new HealthInsuranceAvgearnValue(2372l, 2717l, 928l, 14420l);
			}

			@Override
			public Integer getLevelCode() {
				return 4;
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

		list.add(new HealthInsuranceAvgearn(new HealthInsuranceAvgearnGetMemento() {

			@Override
			public HealthInsuranceAvgearnValue getPersonalAvg() {
				return new HealthInsuranceAvgearnValue(2372l, 2717l, 928l, 14420l);
			}

			@Override
			public Integer getLevelCode() {
				return 5;
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
		list.add(new HealthInsuranceAvgearn(new HealthInsuranceAvgearnGetMemento() {

			@Override
			public HealthInsuranceAvgearnValue getPersonalAvg() {
				return new HealthInsuranceAvgearnValue(2372l, 2717l, 928l, 14420l);
			}

			@Override
			public Integer getLevelCode() {
				return 6;
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

		return list;
	}

	@Override
	public HealthInsuranceAvgearn find(String historyId, Integer levelCode) {
		// TODO Auto-generated method stub
		return null;
	}

}
