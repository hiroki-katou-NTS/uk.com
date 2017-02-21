package nts.uk.ctx.pr.core.infra.repository.insurance.social.pensionavgearn;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.insurance.CommonAmount;
import nts.uk.ctx.pr.core.dom.insurance.InsuranceAmount;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearn;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnValue;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.pensionavgearn.QismtPensionAvgearn;

@Stateless
public class JpaPensionAvgearnRepository extends JpaRepository implements PensionAvgearnRepository {

	@Override
	public void add(PensionAvgearn pensionAvgearn) {
		this.commandProxy().insert(toEntity(pensionAvgearn));

	}

	@Override
	public void update(PensionAvgearn pensionAvgearn) {
		this.commandProxy().update(toEntity(pensionAvgearn));
	}

	@Override
	public void remove(String id) {
		// TODO lam the nao de xoa?
	}

	@Override
	public List<PensionAvgearn> find(String historyId) {
		return getMockData();
	}

	public List<PensionAvgearn> getMockData() {
		List<PensionAvgearn> list = new ArrayList<PensionAvgearn>();
		list.add(new PensionAvgearn(new PensionAvgearnGetMemento() {

			@Override
			public PensionAvgearnValue getPersonalPension() {
				return new PensionAvgearnValue(new CommonAmount(new BigDecimal(1234)),
						new CommonAmount(new BigDecimal(55)), new CommonAmount(new BigDecimal(1234)));
			}

			@Override
			public PensionAvgearnValue getPersonalFundExemption() {
				return new PensionAvgearnValue(new CommonAmount(new BigDecimal(1234)),
						new CommonAmount(new BigDecimal(55)), new CommonAmount(new BigDecimal(1234)));
			}

			@Override
			public PensionAvgearnValue getPersonalFund() {
				return new PensionAvgearnValue(new CommonAmount(new BigDecimal(1234)),
						new CommonAmount(new BigDecimal(55)), new CommonAmount(new BigDecimal(333)));
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
			public PensionAvgearnValue getCompanyPension() {
				return new PensionAvgearnValue(new CommonAmount(new BigDecimal(555)),
						new CommonAmount(new BigDecimal(55)), new CommonAmount(new BigDecimal(222)));
			}

			@Override
			public PensionAvgearnValue getCompanyFundExemption() {
				return new PensionAvgearnValue(new CommonAmount(new BigDecimal(1234)),
						new CommonAmount(new BigDecimal(55)), new CommonAmount(new BigDecimal(1234)));
			}

			@Override
			public PensionAvgearnValue getCompanyFund() {
				return new PensionAvgearnValue(new CommonAmount(new BigDecimal(1234)),
						new CommonAmount(new BigDecimal(55)), new CommonAmount(new BigDecimal(1234)));
			}

			@Override
			public InsuranceAmount getChildContributionAmount() {
				return new InsuranceAmount(new BigDecimal(5555));
			}
		}));
		list.add(new PensionAvgearn(new PensionAvgearnGetMemento() {

			@Override
			public PensionAvgearnValue getPersonalPension() {
				return new PensionAvgearnValue(new CommonAmount(new BigDecimal(1234)),
						new CommonAmount(new BigDecimal(55)), new CommonAmount(new BigDecimal(1234)));
			}

			@Override
			public PensionAvgearnValue getPersonalFundExemption() {
				return new PensionAvgearnValue(new CommonAmount(new BigDecimal(1234)),
						new CommonAmount(new BigDecimal(55)), new CommonAmount(new BigDecimal(1234)));
			}

			@Override
			public PensionAvgearnValue getPersonalFund() {
				return new PensionAvgearnValue(new CommonAmount(new BigDecimal(1234)),
						new CommonAmount(new BigDecimal(55)), new CommonAmount(new BigDecimal(1234)));
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
			public PensionAvgearnValue getCompanyPension() {
				return new PensionAvgearnValue(new CommonAmount(new BigDecimal(1234)),
						new CommonAmount(new BigDecimal(55)), new CommonAmount(new BigDecimal(1234)));
			}

			@Override
			public PensionAvgearnValue getCompanyFundExemption() {
				return new PensionAvgearnValue(new CommonAmount(new BigDecimal(1234)),
						new CommonAmount(new BigDecimal(55)), new CommonAmount(new BigDecimal(1234)));
			}

			@Override
			public PensionAvgearnValue getCompanyFund() {
				return new PensionAvgearnValue(new CommonAmount(new BigDecimal(1234)),
						new CommonAmount(new BigDecimal(55)), new CommonAmount(new BigDecimal(1234)));
			}

			@Override
			public InsuranceAmount getChildContributionAmount() {
				return new InsuranceAmount(new BigDecimal(5555));
			}
		}));
		list.add(new PensionAvgearn(new PensionAvgearnGetMemento() {

			@Override
			public PensionAvgearnValue getPersonalPension() {
				return new PensionAvgearnValue(new CommonAmount(new BigDecimal(1234)),
						new CommonAmount(new BigDecimal(55)), new CommonAmount(new BigDecimal(1234)));
			}

			@Override
			public PensionAvgearnValue getPersonalFundExemption() {
				return new PensionAvgearnValue(new CommonAmount(new BigDecimal(1234)),
						new CommonAmount(new BigDecimal(55)), new CommonAmount(new BigDecimal(1234)));
			}

			@Override
			public PensionAvgearnValue getPersonalFund() {
				return new PensionAvgearnValue(new CommonAmount(new BigDecimal(1234)),
						new CommonAmount(new BigDecimal(55)), new CommonAmount(new BigDecimal(1234)));
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
			public PensionAvgearnValue getCompanyPension() {
				return new PensionAvgearnValue(new CommonAmount(new BigDecimal(1234)),
						new CommonAmount(new BigDecimal(55)), new CommonAmount(new BigDecimal(1234)));
			}

			@Override
			public PensionAvgearnValue getCompanyFundExemption() {
				return new PensionAvgearnValue(new CommonAmount(new BigDecimal(1234)),
						new CommonAmount(new BigDecimal(55)), new CommonAmount(new BigDecimal(1234)));
			}

			@Override
			public PensionAvgearnValue getCompanyFund() {
				return new PensionAvgearnValue(new CommonAmount(new BigDecimal(1234)),
						new CommonAmount(new BigDecimal(55)), new CommonAmount(new BigDecimal(1234)));
			}

			@Override
			public InsuranceAmount getChildContributionAmount() {
				return new InsuranceAmount(new BigDecimal(5555));
			}
		}));
		list.add(new PensionAvgearn(new PensionAvgearnGetMemento() {

			@Override
			public PensionAvgearnValue getPersonalPension() {
				return new PensionAvgearnValue(new CommonAmount(new BigDecimal(1234)),
						new CommonAmount(new BigDecimal(55)), new CommonAmount(new BigDecimal(1234)));
			}

			@Override
			public PensionAvgearnValue getPersonalFundExemption() {
				return new PensionAvgearnValue(new CommonAmount(new BigDecimal(1234)),
						new CommonAmount(new BigDecimal(55)), new CommonAmount(new BigDecimal(1234)));
			}

			@Override
			public PensionAvgearnValue getPersonalFund() {
				return new PensionAvgearnValue(new CommonAmount(new BigDecimal(1234)),
						new CommonAmount(new BigDecimal(55)), new CommonAmount(new BigDecimal(1234)));
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
			public PensionAvgearnValue getCompanyPension() {
				return new PensionAvgearnValue(new CommonAmount(new BigDecimal(1234)),
						new CommonAmount(new BigDecimal(55)), new CommonAmount(new BigDecimal(1234)));
			}

			@Override
			public PensionAvgearnValue getCompanyFundExemption() {
				return new PensionAvgearnValue(new CommonAmount(new BigDecimal(1234)),
						new CommonAmount(new BigDecimal(55)), new CommonAmount(new BigDecimal(1234)));
			}

			@Override
			public PensionAvgearnValue getCompanyFund() {
				return new PensionAvgearnValue(new CommonAmount(new BigDecimal(1234)),
						new CommonAmount(new BigDecimal(55)), new CommonAmount(new BigDecimal(1234)));
			}

			@Override
			public InsuranceAmount getChildContributionAmount() {
				return new InsuranceAmount(new BigDecimal(5555));
			}
		}));
		list.add(new PensionAvgearn(new PensionAvgearnGetMemento() {

			@Override
			public PensionAvgearnValue getPersonalPension() {
				return new PensionAvgearnValue(new CommonAmount(new BigDecimal(1234)),
						new CommonAmount(new BigDecimal(55)), new CommonAmount(new BigDecimal(1234)));
			}

			@Override
			public PensionAvgearnValue getPersonalFundExemption() {
				return new PensionAvgearnValue(new CommonAmount(new BigDecimal(1234)),
						new CommonAmount(new BigDecimal(55)), new CommonAmount(new BigDecimal(1234)));
			}

			@Override
			public PensionAvgearnValue getPersonalFund() {
				return new PensionAvgearnValue(new CommonAmount(new BigDecimal(1234)),
						new CommonAmount(new BigDecimal(55)), new CommonAmount(new BigDecimal(1234)));
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
			public PensionAvgearnValue getCompanyPension() {
				return new PensionAvgearnValue(new CommonAmount(new BigDecimal(1234)),
						new CommonAmount(new BigDecimal(55)), new CommonAmount(new BigDecimal(1234)));
			}

			@Override
			public PensionAvgearnValue getCompanyFundExemption() {
				return new PensionAvgearnValue(new CommonAmount(new BigDecimal(1234)),
						new CommonAmount(new BigDecimal(55)), new CommonAmount(new BigDecimal(1234)));
			}

			@Override
			public PensionAvgearnValue getCompanyFund() {
				return new PensionAvgearnValue(new CommonAmount(new BigDecimal(1234)),
						new CommonAmount(new BigDecimal(55)), new CommonAmount(new BigDecimal(1234)));
			}

			@Override
			public InsuranceAmount getChildContributionAmount() {
				return new InsuranceAmount(new BigDecimal(5555));
			}
		}));
		list.add(new PensionAvgearn(new PensionAvgearnGetMemento() {

			@Override
			public PensionAvgearnValue getPersonalPension() {
				return new PensionAvgearnValue(new CommonAmount(new BigDecimal(1234)),
						new CommonAmount(new BigDecimal(55)), new CommonAmount(new BigDecimal(1234)));
			}

			@Override
			public PensionAvgearnValue getPersonalFundExemption() {
				return new PensionAvgearnValue(new CommonAmount(new BigDecimal(1234)),
						new CommonAmount(new BigDecimal(55)), new CommonAmount(new BigDecimal(1234)));
			}

			@Override
			public PensionAvgearnValue getPersonalFund() {
				return new PensionAvgearnValue(new CommonAmount(new BigDecimal(1234)),
						new CommonAmount(new BigDecimal(55)), new CommonAmount(new BigDecimal(1234)));
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
			public PensionAvgearnValue getCompanyPension() {
				return new PensionAvgearnValue(new CommonAmount(new BigDecimal(1234)),
						new CommonAmount(new BigDecimal(55)), new CommonAmount(new BigDecimal(1234)));
			}

			@Override
			public PensionAvgearnValue getCompanyFundExemption() {
				return new PensionAvgearnValue(new CommonAmount(new BigDecimal(1234)),
						new CommonAmount(new BigDecimal(55)), new CommonAmount(new BigDecimal(1234)));
			}

			@Override
			public PensionAvgearnValue getCompanyFund() {
				return new PensionAvgearnValue(new CommonAmount(new BigDecimal(1234)),
						new CommonAmount(new BigDecimal(55)), new CommonAmount(new BigDecimal(1234)));
			}

			@Override
			public InsuranceAmount getChildContributionAmount() {
				return new InsuranceAmount(new BigDecimal(5555));
			}
		}));
		return list;
	}

	@Override
	public Optional<PensionAvgearn> find(String historyId, Integer levelCode) {
		// TODO: find and return pensionAvgearn
		return null;
	}

	private QismtPensionAvgearn toEntity(PensionAvgearn pensionAvgearn) {
		QismtPensionAvgearn entity = new QismtPensionAvgearn();
		pensionAvgearn.saveToMemento(new JpaPensionAvgearnSetMemento(entity));
		return entity;
	}

}
