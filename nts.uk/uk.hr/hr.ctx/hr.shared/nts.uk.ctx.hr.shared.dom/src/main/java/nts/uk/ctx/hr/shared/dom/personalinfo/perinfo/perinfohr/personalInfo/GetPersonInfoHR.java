package nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.personalInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.PersonalInformation;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.PersonalInformationRepository;

/**
 * 
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.人事.shared.個人情報（人事）.個人情報.アルゴリズム.個人情報（人事）(Thông tin cá nhân (Nhân sự).個人情報の取得 (Get thông tin cá nhân).個人情報の取得
 * @author chungnt
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GetPersonInfoHR {
	
	@Inject
	private PersonalInformationRepository personInfoRepo;
	
	public List<GetPersonInfoHROutput> getPersonInfo (GetPersonInfoHRInput input) {
		
		List<PersonalInformation> domains = this.personInfoRepo.get(input);
		List<GetPersonInfoHROutput> outputs = new ArrayList<>();
		
		outputs =  domains.stream().map(m -> {
			GetPersonInfoHROutput personInfoHROutput = mapping(m);
			return personInfoHROutput;
		}).collect(Collectors.toList());
		
		return outputs;
	}
	
	private GetPersonInfoHROutput mapping (PersonalInformation domain) {
		GetPersonInfoHROutput personInfoHROutput = new GetPersonInfoHROutput();
		
		personInfoHROutput.setContractCd(domain.getContractCd());
		personInfoHROutput.setCid(domain.getCid().map(m -> m).orElse(null));
		personInfoHROutput.setCompanyCode(domain.getCompanyCode().map(m ->m).orElse(null));
		personInfoHROutput.setPId(domain.getPId());
		personInfoHROutput.setSid(domain.getScd().map(m -> m).orElse(null));
		personInfoHROutput.setScd(domain.getScd().map(m -> m).orElse(null));
		personInfoHROutput.setPersonName(domain.getPersonName().map(m -> m).orElse(null));
		personInfoHROutput.setWorkId(domain.getWorkId());
		personInfoHROutput.setWorkName(domain.getWorkName().map(m -> m).orElse(null));
		personInfoHROutput.setHistId(domain.getHistId());
		personInfoHROutput.setStartDate(domain.getStartDate());
		personInfoHROutput.setEndDate(domain.getEndDate());
		personInfoHROutput.setReleaseDate(domain.getReleaseDate().map(m -> m).orElse(null));
		personInfoHROutput.setRequestFlg(domain.getRequestFlg());
		personInfoHROutput.setRptLayoutId(domain.getRptLayoutId());
		
		if(domain.getRptId() != null) {
			personInfoHROutput.setRptId(domain.getRptId().map(m -> m).orElse(null));
		}
		
		if (domain.getRptNumber() != null) {
		personInfoHROutput.setRptNumber(domain.getRptNumber().map(m -> m).orElse(null));
		}
	
		if(domain.getStr01() != null) {
		personInfoHROutput.setStr01(domain.getStr01().map(m -> m).orElse(null));
		}
		if(domain.getStr02() != null) {
		personInfoHROutput.setStr02(domain.getStr02().map(m -> m).orElse(null));
		}
		if(domain.getStr03() != null) {
		personInfoHROutput.setStr03(domain.getStr03().map(m -> m).orElse(null));
		}
		if(domain.getStr04() != null) {
		personInfoHROutput.setStr04(domain.getStr04().map(m -> m).orElse(null));
		}
		if(domain.getStr05() != null) {
		personInfoHROutput.setStr05(domain.getStr05().map(m -> m).orElse(null));
		}
		if(domain.getStr06() != null) {
		personInfoHROutput.setStr06(domain.getStr06().map(m -> m).orElse(null));
		}
		if(domain.getStr07() != null) {
		personInfoHROutput.setStr07(domain.getStr07().map(m -> m).orElse(null));
		}
		if(domain.getStr08() != null) {
		personInfoHROutput.setStr08(domain.getStr08().map(m -> m).orElse(null));
		}
		if(domain.getStr09() != null) {
		personInfoHROutput.setStr09(domain.getStr09().map(m -> m).orElse(null));
		}
		if(domain.getStr10() != null) {
		personInfoHROutput.setStr10(domain.getStr10().map(m -> m).orElse(null));
		}
		if(domain.getStr11() != null) {
		personInfoHROutput.setStr11(domain.getStr11().map(m -> m).orElse(null));
		}
		if(domain.getStr12() != null) {
		personInfoHROutput.setStr12(domain.getStr12().map(m -> m).orElse(null));
		}
		if(domain.getStr13() != null) {
		personInfoHROutput.setStr13(domain.getStr13().map(m -> m).orElse(null));
		}
		if(domain.getStr14() != null) {
		personInfoHROutput.setStr14(domain.getStr14().map(m -> m).orElse(null));
		}
		if(domain.getStr15() != null) {
		personInfoHROutput.setStr15(domain.getStr15().map(m -> m).orElse(null));
		}
		if(domain.getStr16() != null) {
		personInfoHROutput.setStr16(domain.getStr16().map(m -> m).orElse(null));
		}
		if(domain.getStr17() != null) {
		personInfoHROutput.setStr17(domain.getStr17().map(m -> m).orElse(null));
		}
		if(domain.getStr18() != null) {
		personInfoHROutput.setStr18(domain.getStr18().map(m -> m).orElse(null));
		}
		if(domain.getStr19() != null) {
		personInfoHROutput.setStr19(domain.getStr19().map(m -> m).orElse(null));
		}
		if(domain.getStr20() != null) {
		personInfoHROutput.setStr20(domain.getStr20().map(m -> m).orElse(null));
		}
		if(domain.getStr21() != null) {
		personInfoHROutput.setStr21(domain.getStr21().map(m -> m).orElse(null));
		}
		if(domain.getStr22() != null) {
		personInfoHROutput.setStr22(domain.getStr22().map(m -> m).orElse(null));
		}
		if(domain.getStr23() != null) {
		personInfoHROutput.setStr23(domain.getStr23().map(m -> m).orElse(null));
		}
		if(domain.getStr24() != null) {
		personInfoHROutput.setStr24(domain.getStr24().map(m -> m).orElse(null));
		}
		if(domain.getStr25() != null) {
		personInfoHROutput.setStr25(domain.getStr25().map(m -> m).orElse(null));
		}
		if(domain.getStr26() != null) {
		personInfoHROutput.setStr26(domain.getStr26().map(m -> m).orElse(null));
		}
		if(domain.getStr27() != null) {
		personInfoHROutput.setStr27(domain.getStr27().map(m -> m).orElse(null));
		}
		if(domain.getStr28() != null) {
		personInfoHROutput.setStr28(domain.getStr28().map(m -> m).orElse(null));
		}
		if(domain.getStr29() != null) {
		personInfoHROutput.setStr29(domain.getStr29().map(m -> m).orElse(null));
		}
		if(domain.getStr30() != null) {
		personInfoHROutput.setStr30(domain.getStr30().map(m -> m).orElse(null));
		}
		if(domain.getStr31() != null) {
		personInfoHROutput.setStr31(domain.getStr31().map(m -> m).orElse(null));
		}
		if(domain.getStr32() != null) {
		personInfoHROutput.setStr32(domain.getStr32().map(m -> m).orElse(null));
		}
		if(domain.getStr33() != null) {
		personInfoHROutput.setStr33(domain.getStr33().map(m -> m).orElse(null));
		}
		if(domain.getStr34() != null) {
		personInfoHROutput.setStr34(domain.getStr34().map(m -> m).orElse(null));
		}
		if(domain.getStr35() != null) {
		personInfoHROutput.setStr35(domain.getStr35().map(m -> m).orElse(null));
		}
		if(domain.getStr36() != null) {
		personInfoHROutput.setStr36(domain.getStr36().map(m -> m).orElse(null));
		}
		if(domain.getStr37() != null) {
		personInfoHROutput.setStr37(domain.getStr37().map(m -> m).orElse(null));
		}
		if(domain.getStr38() != null) {
		personInfoHROutput.setStr38(domain.getStr38().map(m -> m).orElse(null));
		}
		if(domain.getStr39() != null) {
		personInfoHROutput.setStr39(domain.getStr39().map(m -> m).orElse(null));
		}
		if(domain.getStr40() != null) {
		personInfoHROutput.setStr40(domain.getStr40().map(m -> m).orElse(null));
		}
		if(domain.getStr41() != null) {
		personInfoHROutput.setStr41(domain.getStr41().map(m -> m).orElse(null));
		}
		if(domain.getStr42() != null) {
		personInfoHROutput.setStr42(domain.getStr42().map(m -> m).orElse(null));
		}
		if(domain.getStr43() != null) {
		personInfoHROutput.setStr43(domain.getStr43().map(m -> m).orElse(null));
		}
		if(domain.getStr44() != null) {
		personInfoHROutput.setStr44(domain.getStr44().map(m -> m).orElse(null));
		}
		if(domain.getStr45() != null) {
		personInfoHROutput.setStr45(domain.getStr45().map(m -> m).orElse(null));
		}
		if(domain.getStr46() != null) {
		personInfoHROutput.setStr46(domain.getStr46().map(m -> m).orElse(null));
		}
		if(domain.getStr47() != null) {
		personInfoHROutput.setStr47(domain.getStr47().map(m -> m).orElse(null));
		}
		if(domain.getStr48() != null) {
		personInfoHROutput.setStr48(domain.getStr48().map(m -> m).orElse(null));
		}
		if(domain.getStr49() != null) {
		personInfoHROutput.setStr49(domain.getStr49().map(m -> m).orElse(null));
		}
		if(domain.getStr50() != null) {
		personInfoHROutput.setStr50(domain.getStr50().map(m -> m).orElse(null));
		}
		if(domain.getStr51() != null) {
		personInfoHROutput.setStr51(domain.getStr51().map(m -> m).orElse(null));
		}
		if(domain.getStr52() != null) {
		personInfoHROutput.setStr52(domain.getStr52().map(m -> m).orElse(null));
		}
		if(domain.getStr53() != null) {
		personInfoHROutput.setStr53(domain.getStr53().map(m -> m).orElse(null));
		}
		if(domain.getStr54() != null) {
		personInfoHROutput.setStr54(domain.getStr54().map(m -> m).orElse(null));
		}
		if(domain.getStr55() != null) {
		personInfoHROutput.setStr55(domain.getStr55().map(m -> m).orElse(null));
		}
		if(domain.getStr56() != null) {
		personInfoHROutput.setStr56(domain.getStr56().map(m -> m).orElse(null));
		}
		if(domain.getStr57() != null) {
		personInfoHROutput.setStr57(domain.getStr57().map(m -> m).orElse(null));
		}
		if(domain.getStr58() != null) {
		personInfoHROutput.setStr58(domain.getStr58().map(m -> m).orElse(null));
		}
		if(domain.getStr59() != null) {
		personInfoHROutput.setStr59(domain.getStr59().map(m -> m).orElse(null));
		}
		if(domain.getStr60() != null) {
		personInfoHROutput.setStr60(domain.getStr60().map(m -> m).orElse(null));
		}
		
		
		if (domain.getDate01() != null) {
		personInfoHROutput.setDate01(domain.getDate01().map(m -> m).orElse(null));
		}
		if (domain.getDate02() != null) {
		personInfoHROutput.setDate02(domain.getDate02().map(m -> m).orElse(null));
		}
		if (domain.getDate03() != null) {
		personInfoHROutput.setDate03(domain.getDate03().map(m -> m).orElse(null));
		}
		if (domain.getDate04() != null) {
		personInfoHROutput.setDate04(domain.getDate04().map(m -> m).orElse(null));
		}
		if (domain.getDate05() != null) {
		personInfoHROutput.setDate05(domain.getDate05().map(m -> m).orElse(null));
		}
		if (domain.getDate06() != null) {
		personInfoHROutput.setDate06(domain.getDate06().map(m -> m).orElse(null));
		}
		if (domain.getDate07() != null) {
		personInfoHROutput.setDate07(domain.getDate07().map(m -> m).orElse(null));
		}
		if (domain.getDate08() != null) {
		personInfoHROutput.setDate08(domain.getDate08().map(m -> m).orElse(null));
		}
		if (domain.getDate09() != null) {
		personInfoHROutput.setDate09(domain.getDate09().map(m -> m).orElse(null));
		}
		if (domain.getDate10() != null) {
		personInfoHROutput.setDate10(domain.getDate10().map(m -> m).orElse(null));
		}
		
		
		if (domain.getInt01() != null) {
			personInfoHROutput.setInt01(domain.getInt01());
		}
		if (domain.getInt02() != null) {
			personInfoHROutput.setInt01(domain.getInt02());
		}
		if (domain.getInt03() != null) {
			personInfoHROutput.setInt01(domain.getInt03());
		}
		if (domain.getInt04() != null) {
			personInfoHROutput.setInt01(domain.getInt04());
		}
		if (domain.getInt05() != null) {
			personInfoHROutput.setInt01(domain.getInt05());
		}
		if (domain.getInt06() != null) {
			personInfoHROutput.setInt01(domain.getInt06());
		}
		if (domain.getInt07() != null) {
			personInfoHROutput.setInt01(domain.getInt07());
		}
		if (domain.getInt08() != null) {
			personInfoHROutput.setInt01(domain.getInt08());
		}
		if (domain.getInt09() != null) {
			personInfoHROutput.setInt01(domain.getInt09());
		}
		if (domain.getInt10() != null) {
			personInfoHROutput.setInt01(domain.getInt10());
		}
		if (domain.getInt11() != null) {
			personInfoHROutput.setInt01(domain.getInt11());
		}
		if (domain.getInt12() != null) {
			personInfoHROutput.setInt01(domain.getInt12());
		}
		if (domain.getInt13() != null) {
			personInfoHROutput.setInt01(domain.getInt13());
		}
		if (domain.getInt14() != null) {
			personInfoHROutput.setInt01(domain.getInt14());
		}
		if (domain.getInt15() != null) {
			personInfoHROutput.setInt01(domain.getInt15());
		}
		if (domain.getInt16() != null) {
			personInfoHROutput.setInt01(domain.getInt16());
		}
		if (domain.getInt17() != null) {
			personInfoHROutput.setInt01(domain.getInt17());
		}
		if (domain.getInt18() != null) {
			personInfoHROutput.setInt01(domain.getInt18());
		}
		if (domain.getInt19() != null) {
			personInfoHROutput.setInt01(domain.getInt19());
		}
		if (domain.getInt20() != null) {
			personInfoHROutput.setInt01(domain.getInt20());
		}
		if (domain.getInt21() != null) {
			personInfoHROutput.setInt01(domain.getInt21());
		}
		if (domain.getInt22() != null) {
			personInfoHROutput.setInt01(domain.getInt22());
		}
		if (domain.getInt23() != null) {
			personInfoHROutput.setInt01(domain.getInt23());
		}
		if (domain.getInt24() != null) {
			personInfoHROutput.setInt01(domain.getInt24());
		}
		if (domain.getInt25() != null) {
			personInfoHROutput.setInt01(domain.getInt25());
		}
		if (domain.getInt26() != null) {
			personInfoHROutput.setInt01(domain.getInt26());
		}
		if (domain.getInt27() != null) {
			personInfoHROutput.setInt01(domain.getInt27());
		}
		if (domain.getInt28() != null) {
			personInfoHROutput.setInt01(domain.getInt28());
		}
		if (domain.getInt29() != null) {
			personInfoHROutput.setInt01(domain.getInt29());
		}
		if (domain.getInt30() != null) {
			personInfoHROutput.setInt01(domain.getInt30());
		}

		
		if (domain.getNumber01() != null) {
			personInfoHROutput.setNumber01(domain.getNumber01());
		}
		if (domain.getNumber02() != null) {
			personInfoHROutput.setNumber02(domain.getNumber02());
		}
		if (domain.getNumber03() != null) {
			personInfoHROutput.setNumber03(domain.getNumber03());
		}
		if (domain.getNumber04() != null) {
			personInfoHROutput.setNumber04(domain.getNumber04());
		}
		if (domain.getNumber05() != null) {
			personInfoHROutput.setNumber05(domain.getNumber05());
		}
		if (domain.getNumber06() != null) {
			personInfoHROutput.setNumber06(domain.getNumber06());
		}
		if (domain.getNumber07() != null) {
			personInfoHROutput.setNumber07(domain.getNumber07());
		}
		if (domain.getNumber08() != null) {
			personInfoHROutput.setNumber08(domain.getNumber08());
		}
		if (domain.getNumber09() != null) {
			personInfoHROutput.setNumber09(domain.getNumber09());
		}
		if (domain.getNumber10() != null) {
			personInfoHROutput.setNumber10(domain.getNumber10());
		}
		if (domain.getNumber11() != null) {
			personInfoHROutput.setNumber11(domain.getNumber11());
		}
		if (domain.getNumber12() != null) {
			personInfoHROutput.setNumber12(domain.getNumber12());
		}
		if (domain.getNumber13() != null) {
			personInfoHROutput.setNumber13(domain.getNumber13());
		}
		if (domain.getNumber14() != null) {
			personInfoHROutput.setNumber14(domain.getNumber14());
		}
		if (domain.getNumber15() != null) {
			personInfoHROutput.setNumber15(domain.getNumber15());
		}
		if (domain.getNumber16() != null) {
			personInfoHROutput.setNumber16(domain.getNumber16());
		}
		if (domain.getNumber17() != null) {
			personInfoHROutput.setNumber17(domain.getNumber17());
		}
		if (domain.getNumber18() != null) {
			personInfoHROutput.setNumber18(domain.getNumber18());
		}
		if (domain.getNumber19() != null) {
			personInfoHROutput.setNumber19(domain.getNumber19());
		}
		if (domain.getNumber20() != null) {
			personInfoHROutput.setNumber20(domain.getNumber20());
		}
		if (domain.getNumber21() != null) {
			personInfoHROutput.setNumber21(domain.getNumber21());
		}
		if (domain.getNumber22() != null) {
			personInfoHROutput.setNumber22(domain.getNumber22());
		}
		if (domain.getNumber23() != null) {
			personInfoHROutput.setNumber23(domain.getNumber23());
		}
		if (domain.getNumber24() != null) {
			personInfoHROutput.setNumber24(domain.getNumber24());
		}
		if (domain.getNumber25() != null) {
			personInfoHROutput.setNumber25(domain.getNumber25());
		}
		if (domain.getNumber26() != null) {
			personInfoHROutput.setNumber26(domain.getNumber26());
		}
		if (domain.getNumber27() != null) {
			personInfoHROutput.setNumber27(domain.getNumber27());
		}
		if (domain.getNumber28() != null) {
			personInfoHROutput.setNumber28(domain.getNumber28());
		}
		if (domain.getNumber29() != null) {
			personInfoHROutput.setNumber29(domain.getNumber29());
		}
		if (domain.getNumber30() != null) {
			personInfoHROutput.setNumber30(domain.getNumber30());
		}


		if (domain.getSelectId01() != null) {
			personInfoHROutput.setSelectId01(domain.getSelectId01());
		}
		if (domain.getSelectId02() != null) {
			personInfoHROutput.setSelectId02(domain.getSelectId02());
		}
		if (domain.getSelectId03() != null) {
			personInfoHROutput.setSelectId03(domain.getSelectId03());
		}
		if (domain.getSelectId04() != null) {
			personInfoHROutput.setSelectId04(domain.getSelectId04());
		}
		if (domain.getSelectId05() != null) {
			personInfoHROutput.setSelectId05(domain.getSelectId05());
		}
		if (domain.getSelectId06() != null) {
			personInfoHROutput.setSelectId06(domain.getSelectId06());
		}
		if (domain.getSelectId07() != null) {
			personInfoHROutput.setSelectId07(domain.getSelectId07());
		}
		if (domain.getSelectId08() != null) {
			personInfoHROutput.setSelectId08(domain.getSelectId08());
		}
		if (domain.getSelectId09() != null) {
			personInfoHROutput.setSelectId09(domain.getSelectId09());
		}
		if (domain.getSelectId10() != null) {
			personInfoHROutput.setSelectId10(domain.getSelectId10());
		}
		if (domain.getSelectId11() != null) {
			personInfoHROutput.setSelectId11(domain.getSelectId11());
		}
		if (domain.getSelectId12() != null) {
			personInfoHROutput.setSelectId12(domain.getSelectId12());
		}
		if (domain.getSelectId13() != null) {
			personInfoHROutput.setSelectId13(domain.getSelectId13());
		}
		if (domain.getSelectId14() != null) {
			personInfoHROutput.setSelectId14(domain.getSelectId14());
		}
		if (domain.getSelectId15() != null) {
			personInfoHROutput.setSelectId15(domain.getSelectId15());
		}
		if (domain.getSelectId16() != null) {
			personInfoHROutput.setSelectId16(domain.getSelectId16());
		}
		if (domain.getSelectId17() != null) {
			personInfoHROutput.setSelectId17(domain.getSelectId17());
		}
		if (domain.getSelectId18() != null) {
			personInfoHROutput.setSelectId18(domain.getSelectId18());
		}
		if (domain.getSelectId19() != null) {
			personInfoHROutput.setSelectId19(domain.getSelectId19());
		}
		if (domain.getSelectId20() != null) {
			personInfoHROutput.setSelectId20(domain.getSelectId20());
		}
		
		
		if (domain.getSelectCode01() != null) {
			personInfoHROutput.setSelectCode01(domain.getSelectCode01().map(m -> m).orElse(null));
		}
		if (domain.getSelectCode02() != null) {
			personInfoHROutput.setSelectCode02(domain.getSelectCode02().map(m -> m).orElse(null));
		}
		if (domain.getSelectCode03() != null) {
			personInfoHROutput.setSelectCode03(domain.getSelectCode03().map(m -> m).orElse(null));
		}
		if (domain.getSelectCode04() != null) {
			personInfoHROutput.setSelectCode04(domain.getSelectCode04().map(m -> m).orElse(null));
		}
		if (domain.getSelectCode05() != null) {
			personInfoHROutput.setSelectCode05(domain.getSelectCode05().map(m -> m).orElse(null));
		}
		if (domain.getSelectCode06() != null) {
			personInfoHROutput.setSelectCode06(domain.getSelectCode06().map(m -> m).orElse(null));
		}
		if (domain.getSelectCode07() != null) {
			personInfoHROutput.setSelectCode07(domain.getSelectCode07().map(m -> m).orElse(null));
		}
		if (domain.getSelectCode08() != null) {
			personInfoHROutput.setSelectCode08(domain.getSelectCode08().map(m -> m).orElse(null));
		}
		if (domain.getSelectCode09() != null) {
			personInfoHROutput.setSelectCode09(domain.getSelectCode09().map(m -> m).orElse(null));
		}
		if (domain.getSelectCode10() != null) {
			personInfoHROutput.setSelectCode10(domain.getSelectCode10().map(m -> m).orElse(null));
		}
		if (domain.getSelectCode11() != null) {
			personInfoHROutput.setSelectCode11(domain.getSelectCode11().map(m -> m).orElse(null));
		}
		if (domain.getSelectCode12() != null) {
			personInfoHROutput.setSelectCode12(domain.getSelectCode12().map(m -> m).orElse(null));
		}
		if (domain.getSelectCode13() != null) {
			personInfoHROutput.setSelectCode13(domain.getSelectCode13().map(m -> m).orElse(null));
		}
		if (domain.getSelectCode14() != null) {
			personInfoHROutput.setSelectCode14(domain.getSelectCode14().map(m -> m).orElse(null));
		}
		if (domain.getSelectCode15() != null) {
			personInfoHROutput.setSelectCode15(domain.getSelectCode15().map(m -> m).orElse(null));
		}
		if (domain.getSelectCode16() != null) {
			personInfoHROutput.setSelectCode16(domain.getSelectCode16().map(m -> m).orElse(null));
		}
		if (domain.getSelectCode17() != null) {
			personInfoHROutput.setSelectCode17(domain.getSelectCode17().map(m -> m).orElse(null));
		}
		if (domain.getSelectCode18() != null) {
			personInfoHROutput.setSelectCode18(domain.getSelectCode18().map(m -> m).orElse(null));
		}
		if (domain.getSelectCode19() != null) {
			personInfoHROutput.setSelectCode19(domain.getSelectCode19().map(m -> m).orElse(null));
		}
		if (domain.getSelectCode20() != null) {
			personInfoHROutput.setSelectCode20(domain.getSelectCode20().map(m -> m).orElse(null));
		}
		
		
		if (domain.getSelectName01() != null) {
			personInfoHROutput.setSelectName01(domain.getSelectName01().map(m -> m).orElse(null));
		}
		if (domain.getSelectName02() != null) {
			personInfoHROutput.setSelectName02(domain.getSelectName02().map(m -> m).orElse(null));
		}
		if (domain.getSelectName03() != null) {
			personInfoHROutput.setSelectName03(domain.getSelectName03().map(m -> m).orElse(null));
		}
		if (domain.getSelectName04() != null) {
			personInfoHROutput.setSelectName04(domain.getSelectName04().map(m -> m).orElse(null));
		}
		if (domain.getSelectName05() != null) {
			personInfoHROutput.setSelectName05(domain.getSelectName05().map(m -> m).orElse(null));
		}
		if (domain.getSelectName06() != null) {
			personInfoHROutput.setSelectName06(domain.getSelectName06().map(m -> m).orElse(null));
		}
		if (domain.getSelectName07() != null) {
			personInfoHROutput.setSelectName07(domain.getSelectName07().map(m -> m).orElse(null));
		}
		if (domain.getSelectName08() != null) {
			personInfoHROutput.setSelectName08(domain.getSelectName08().map(m -> m).orElse(null));
		}
		if (domain.getSelectName09() != null) {
			personInfoHROutput.setSelectName09(domain.getSelectName09().map(m -> m).orElse(null));
		}
		if (domain.getSelectName10() != null) {
			personInfoHROutput.setSelectName10(domain.getSelectName10().map(m -> m).orElse(null));
		}
		if (domain.getSelectName11() != null) {
			personInfoHROutput.setSelectName11(domain.getSelectName11().map(m -> m).orElse(null));
		}
		if (domain.getSelectName12() != null) {
			personInfoHROutput.setSelectName12(domain.getSelectName12().map(m -> m).orElse(null));
		}
		if (domain.getSelectName13() != null) {
			personInfoHROutput.setSelectName13(domain.getSelectName13().map(m -> m).orElse(null));
		}
		if (domain.getSelectName14() != null) {
			personInfoHROutput.setSelectName14(domain.getSelectName14().map(m -> m).orElse(null));
		}
		if (domain.getSelectName15() != null) {
			personInfoHROutput.setSelectName15(domain.getSelectName15().map(m -> m).orElse(null));
		}
		if (domain.getSelectName16() != null) {
			personInfoHROutput.setSelectName16(domain.getSelectName16().map(m -> m).orElse(null));
		}
		if (domain.getSelectName17() != null) {
			personInfoHROutput.setSelectName17(domain.getSelectName17().map(m -> m).orElse(null));
		}
		if (domain.getSelectName18() != null) {
			personInfoHROutput.setSelectName18(domain.getSelectName18().map(m -> m).orElse(null));
		}
		if (domain.getSelectName19() != null) {
			personInfoHROutput.setSelectName19(domain.getSelectName19().map(m -> m).orElse(null));
		}
		if (domain.getSelectName20() != null) {
			personInfoHROutput.setSelectName20(domain.getSelectName20().map(m -> m).orElse(null));
		}
		
		return personInfoHROutput;
	}
}